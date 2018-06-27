import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.net.InetAddress;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.sun.crypto.provider.HmacMD5;

import javafx.util.Pair;

import java.io.*;
import java.net.InetAddress;
import java.nio.file.Files;
        
class Server implements FTP {
    
    
    private File[] fi;
    private FileInputStream[] fii;
    private FileOutputStream[] fio;
    private int[] sent;
    private int[] got;
    public volatile Map<Pair<Integer,Integer>,Integer> ms;
    public volatile Map<Pair<Integer,Integer>,Integer> ds;
    private boolean[] append;

    public Server() 
    {
        fi = new File[100];
        fii = new FileInputStream[100];
        fio = new FileOutputStream[100];
        sent = new int[100];
        got = new int[100];
        ms = new HashMap<Pair<Integer,Integer>,Integer>();
        ds = new HashMap<Pair<Integer,Integer>,Integer>();
        append = new boolean[100];
    }

    public int addClient()
    {
        //System.out.println(Thread.currentThread().getId());
        if((int)Thread.currentThread().getId()>100)
        {
           return (int)Thread.currentThread().getId()%100; 
        }
        return (int)Thread.currentThread().getId();
    }

    public String sayHello() {
        String take=null;
        try
        {
        BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
        take = bi.readLine();
        }catch(Exception e){}
        return take;
    }

    public File[] getFiles(String dir)
    {
        String[] lof=null;
        File f = null;
        File[] gh = null;

        try
        {
            f = new File(dir);
            gh = f.listFiles();
        }catch(Exception e){return null;}



        return gh;

    }

    public String makeDir(String dir)
    {
        String k=null;
        try
        {
        //System.out.println("creating directory");
        Files.createDirectory((new File(dir).toPath()));
        k = "Success";
        }catch(Exception e){String ok = e.toString();
            int p1 = ok.lastIndexOf('.');
            int p2 = ok.lastIndexOf("Exception");
            String ok1 = ok.substring(p1+1,p2);
            return "The operation has failed because: "+ok1+" in Server";}

        return k;

    }

    public String rmDir(String dir)
    {
        String k=null;
        try
        {
        //System.out.println("creating directory");
        Files.delete((new File(dir).toPath()));
        k = "Success";
        }catch(Exception e){String ok = e.toString();
            int p1 = ok.lastIndexOf('.');
            int p2 = ok.lastIndexOf("Exception");
            String ok1 = ok.substring(p1+1,p2);
            return "The operation has failed because: "+ok1+" in Server";}

        return k;

    }

    public String rm(String dir)
    {
        String k=null;
        try
        {
        //System.out.println("creating directory");
        Files.delete((new File(dir).toPath()));
        k = "Success";
         }catch(Exception e){//String ok = e.toString();
        //     int p1 = ok.lastIndexOf('.');
        //     int p2 = ok.lastIndexOf("Exception");
        //     String ok1 = ok.substring(p1+1,p2);
            return "The operation has failed because: File Does not Exist";}

        return k;

    }


    public byte[] download(String dir)
    {
        byte[] buffer=null;
        File f=null;
        FileInputStream fin = null;
        try
        {
            f = new File(dir);
            fin = new FileInputStream(f);
            buffer = new byte[(int)f.length()];
            fin.read(buffer);
            fin.close();
        }catch(Exception e){}
        

        return buffer;
    }



    public String upload(String file,byte[] buffer)
    {
        String msg=null;
        try
        {
        File f = new File(file);
        FileOutputStream fo = new FileOutputStream(f);
        int i=0;
        while(i<buffer.length)
        {
            fo.write(buffer[i]);
            i+=1;
        }
        fo.close();
        msg = "File Uploaded Succesfully";
        }catch(Exception e){msg="Couldn't Upload File";return msg;}
        return msg;

    }

    public long openFileR(String dir,int c)
    {
        long l=0;
        try
        {
        fi[c] = new File(dir);
        if(!fi[c].exists())
        {
            return 999999999L;
        }
        //fi.setWritable(false);
        fii[c] = new FileInputStream(fi[c]);
        if(ms.containsKey(new Pair<Integer,Integer>(fi[c].hashCode(),c)))
        {
            //System.out.println("Resuming");
            //fii.skip(ms.get(fi.hashCode()));
            int s = ms.get(new Pair<Integer,Integer>(fi[c].hashCode(),c));
            while(s>0)
            {
                fii[c].read();
                s-=1;
            }
            l = fi[c].length() - ms.get(new Pair<Integer,Integer>(fi[c].hashCode(),c));
            //System.out.println(l);
            return -l;
        }
        else
        {
        l = fi[c].length();
        ms.put(new Pair<Integer,Integer>(fi[c].hashCode(),c),0);
        }
        
        }catch(Exception e){return 999999999L;}
        return l;
    }

    public long openFileW(String dir,int c)
    {
        
        try
        {
        //System.out.println("Uploading");
        fi[c] = new File(dir);
        if(ds.containsKey(new Pair<Integer,Integer>(fi[c].hashCode(),c)))
        {
            System.out.println("Resuming");
            append[c] = true;   
        }
        else
        {
            ds.put(new Pair<Integer,Integer>(fi[c].hashCode(),c), 0);
            fio[c] = new FileOutputStream(fi[c],append[c]);
        }
        //System.out.println("Uploading");
        }catch(Exception e){return 999999999L;}
        return fi[c].length();
    }

    public long setByte(byte b,int c)
    {
        try
        {
            fio[c].write(b);
            fio[c].flush();
            got[c]+=1;
            ds.replace(new Pair<Integer,Integer>(fi[c].hashCode(),c), got[c]);
        }catch(Exception e){return 999999999L;}

        return got[c];
    }

    public byte getByte(long rec,int c)
    {
        byte b = 0;
        
        try
        {
        
        sent[c]+=1;
        b = (byte)fii[c].read();
        ms.replace(new Pair<Integer,Integer>(fi[c].hashCode(),c), (int)rec);
        //System.out.println(b);
        }catch(Exception e){}
        // for(int key:ms.keySet())
        // {
        //     System.out.println(ms.get(key));
        // }
        //System.out.println(b);
        return b;
    }

    public String closeFileR(int c)
    {
        try
        {
            sent[c] = 0;
            fi[c].setWritable(false);
            fii[c].close();
            ms.remove(new Pair<Integer,Integer>(fi[c].hashCode(),c));
        }catch(Exception e){return "Couldn't Close File Correctly";}
        return "Success";
    }

    public String closeFileW(int c)
    {
        try
        {
            //System.out.println("Closing File");
            got[c] = 0;
            append[c] = false;
            fio[c].close();
            ds.remove(new Pair<Integer,Integer>(fi[c].hashCode(),c));
            //System.out.println("Closed File");
        }catch(Exception e){return "Couldn't Close File Correctly"+e;}
        return "Success";
    }

    public void shutdown()
    {
        System.out.println("Shutting Down Server");
        System.exit(0);
    }
        
    public static void main(String args[]) {
        
        try {
            if(args[0].equalsIgnoreCase("start"))
            {
            Server obj = new Server();
            FTP stub = (FTP) UnicastRemoteObject.exportObject(obj,8800);
            InetAddress in = InetAddress.getLocalHost();
            System.out.println(InetAddress.getLocalHost().getHostName());

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry(Integer.parseInt(args[1]));
            registry.rebind("localhost"+":"+args[1], stub);

            System.err.println("Server ready");
        }
    else
    {System.err.println("Type Start To start The Server");System.exit(106);}}catch(IndexOutOfBoundsException k)
    {
        System.err.println("Type Start To start The Server or You Forgot to enter the Port Number");System.exit(106);}   
     catch (Exception e) {

            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
