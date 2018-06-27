import java.net.*;
import java.net.ServerSocket;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.net.ssl.ExtendedSSLSession;

import java.io.*;

//import java.util.*;

class Server
{
    public static volatile Map<String,Long> ms;
    public static volatile Map<String,Long> ds;

    
    public static void Manage(Socket cs) 
    {
        System.out.println("The server is now connected to: "+cs.getLocalPort());
        try{
        PrintWriter pw = new PrintWriter(cs.getOutputStream(),true);
        BufferedReader br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream din = new DataInputStream(cs.getInputStream());
        ObjectInputStream oin = new ObjectInputStream(cs.getInputStream());
        ObjectOutputStream os =  new ObjectOutputStream(cs.getOutputStream());
        BufferedOutputStream bos = null;
        //Map<String,Long> ms = new HashMap<String,Long>();
        
        String il=null,ol=null;
        String ok=null,ok1=null;
        int p1=0,p2=0;

        
        //FileSystem fs = FileSystems.getDefault();
        //ADD Parsing for Commands
        
        //pw.println("Welcome You Are Connected:");
        //FileWriter fw = null;
        FileOutputStream fw = null;
        PrintStream ps = null;
        File f = null;
       // ObjectOutputStream os = null;
        System.out.println(cs.getLocalSocketAddress());

        try
        {
        while((il=br.readLine())!=null)
        {
            //System.out.println(il);
            StringTokenizer st = new StringTokenizer(il," ");
            int j=0;
            // Object i=null;
            // byte[] buffer;
            // byte b;
            // while(st.hasMoreTokens())
            // {
                
                // for (String key:ms.keySet()) {
                //     System.out.println(key);
                //     System.out.println(ms.get(key));
                // }
                String s;
                s = st.nextToken();
                if(s.equalsIgnoreCase("upload"))
                 {
                     //System.out.println("Entered"+il);
                     //receiveFile(cs,st.nextToken());
                    String kk = st.nextToken();
                    f = new File(kk);
                    boolean append = false;
                    long jj=0;
                    double ui=0;
                    long t1=0;
                    if(ms.containsKey(kk))
                    {
                        //System.out.println("Correct");
                        // for (String key:ms.keySet()) {
                        //     System.out.println(ms.get(key));
                        // }
                        append = true;
                        
                        //System.out.println("Resuming from :"+((double)ms.get(kk)/(double)bs)*100);
                        //bs = bs-ms.get(kk);
                        pw.println(ms.get(kk));
                        t1 = ms.get(kk);
                        ms.remove(kk);
                        //oin.skip(ms.get(kk));
                        //ms.remove(kk);
                        
                        //pw.println("Send from "+ms.get(kk));
                    }
                    else
                    {
                        pw.println("0");
                    }

                    long bs = Long.parseLong(br.readLine());
                    Long temp = bs;
                    System.out.println(bs);
                    double ii= (double)temp/100;
                    //fw = new FileWriter(f);
                    fw = new FileOutputStream(f,append);
                    //FileDescriptor fd = fw.getFD();

                    //bos = new BufferedOutputStream(fw);
                    //System.out.println(bs);
                    //os = new ObjectOutputStream(new FileOutputStream(f));
                    //ps = new PrintStream(f);
                   
                    while(bs>0)
                    {

                        //System.out.println("in"+il);
                        //fw = new FileWriter("D:\\code"+st.nextToken());
                        //fw.write(Integer.parseInt(il));
                        //System.out.println(Integer.parseInt(il));
                        //System.out.println("wjdnwjda");
                        //System.out.println(j);
                        //System.out.println("Entered");
                        try
                        {
                        j = oin.read();
                        fw.write(j);
                        jj = (long)(ui/ii);
                        ui+=1;
                        
                        
                        // {
                        if(!(jj==(long)(ui/ii)))
                        {
                            
                        pw.println("Uploading... "+(long)(ui/ii));
                        }
                        bs = bs - 1;
                        
                       
                        }catch(Exception e)  
                        {
                            System.out.println("Connection Time Out");
                            ms.put(kk,(t1+temp-bs));
                            for (String key:ms.keySet()) {
                                System.out.println(key);
                                System.out.println(ms.get(key));
                            }
                            break;
                        }
                        //System.out.println(j);
                         

                        //System.out.println("asfsfa");
                        //il = br.readLine();
                        // if(i instanceof String)
                        // {
                        //     break;
                        // }
                        //System.out.println("gfergeg");
                        //os.write(j);

                        
                        //System.out.println(Integer.parseInt(il));
                        //fw.write(Integer.parseInt(il));
                        

                        
                        //System.out.println("out");
                    }
                    fw.close();
                    //System.out.println("outssss");
                    pw.println("END");
                    pw.println("0");
                    pw.println("Upload Successfull");
                    //pw.println("");
                    //fw.flush();
                    
                    //os.close();
                    //ps.close();
                    //System.out.println(st.nextToken());
                }

               
               else if (s.equalsIgnoreCase("download"))
                {
                    try
                    {
                    String s4 = st.nextToken();
                    int k = s4.lastIndexOf('/');
                    String l = s4.substring(k+1);
                    File fl = new File(s4);
                    String ko = s4;
                    boolean ist = fl.exists();
                    long t1=0;
                    //System.out.println(ist);
                    //sendFile(s,s4);
                   




                    if(!ist)
                    {
                        pw.println("-1");
                        //throw (new NoSuchFileException(s4));
                    }
                    else
                    {
                        long lo = fl.length();
                        long hj = lo;
                        FileInputStream fs = new FileInputStream(fl);
                        if(ds.containsKey(ko))
                        {
                            // System.out.println("Correct");
                            // for (String key:ds.keySet()) {
                            //     System.out.println(ds.get(key));
                            // }
                            
                            //System.out.println("Resuming from :"+((double)ms.get(kk)/(double)bs)*100);
                            //bs = bs-ms.get(kk);
                            //pw.println(ds.get(ko));
                            t1 = ds.get(ko);
                            fs.skip(t1);
                            lo = lo-t1;
                            ds.remove(ko);
                            pw.println((long)(((double)t1/(double)lo)*100));
                            //oin.skip(ms.get(kk));
                            //ms.remove(kk);
                            
                            //pw.println("Send from "+ms.get(kk));
                        }
                        else
                        {
                            //System.out.println("iwehduhf");
                            pw.println("0");
                        }
                    
                    //double ij = lo;
                    int i=0;
                    long jj = 0;
                    double ii=0;
                    double kk = (double)lo/100;
                    double tt = 0;
                    //System.out.println(kk);
                    //FileReader fr = new FileReader(f);
                   
                    pw.println(lo);
                    //ObjectInputStream oin = new ObjectInputStream(new FileInputStream(f));
                    //pw.println("sending "+st.nextToken()+" "+lo);
                    //System.out.println(st.nextToken());
                    //System.out.println(lo);
                    while((i=fs.read())!=-1)
                    {
                        //System.out.println("Sending File Data");
                        
                        try
                        {
                        os.write(i);
                        jj+=1;
                        }catch(Exception e)
                        {
                            System.out.println("Connection Time Out");
                            //System.out.println(jj);
                            ds.put(ko,(t1+hj-jj));
                            for (String key:ds.keySet()) {
                                System.out.println(key);
                                System.out.println(ds.get(key));
                            }
                            break;

                        }

                        // if(tt%kk==0)
                        // jj = (long)(ii/kk);
                        // ii+=1;
                        // // {
                        // if(!(jj==(long)(ii/kk)))
                        // {
                        // System.out.println("Uploading Data Percent Complete"+(long)(ii/kk));
                        // }
                        
                        //}
                        //tt+=1;
                        //pw.println(i);
                        //bw.write(i);
                        //pw.flush();
                        //dos.writeInt(i);
                    }
                    os.flush();
                    fs.close();
                    pw.println("0");
                    pw.println("Download Successfull");
                }
                }catch(Exception e)
                {
                    ok = e.toString();
                    p1 = ok.lastIndexOf('.');
                    p2 = ok.lastIndexOf("Exception");
                    ok1 = ok.substring(p1+1,p2);
                    pw.println("98");
                    pw.println("The operation has failed because: "+ok1+" in Server");
                   
                }
                    //pw.println("END");
                    //os.writeObject("END");
                    
                    //dos.writeChar(-1);
                    //dos.flush();
                    //bw.flush();
                    //pw.flush();
                    //pw.flush();
                    //System.out.println("sent end data");
                    
                   
                }

                else if (s.equalsIgnoreCase("dir"))
                {
                    try
                    {
                     File fk = new File(st.nextToken());
                     File[] gh = fk.listFiles();
                     for (File g : gh) {
                         pw.println(g.getName());
                     }
                     pw.println("");
                     pw.println("0");
                     pw.println("Operation ListFiles SuccessFull");
                    }
                    catch(Exception e)
                    {
                        ok = e.toString();
                        p1 = ok.lastIndexOf('.');
                        p2 = ok.lastIndexOf("Exception");
                        ok1 = ok.substring(p1+1,p2);
                        pw.println("");
                        pw.println("99");
                        pw.println("The operation has failed because: "+ok1+" in Server");
                        
                    }
                }




               else if(s.equalsIgnoreCase("mkdir"))
                {
                    //FileSystem fs = FileSystems.getDefault();
                    try
                    {
                    //System.out.println("creating directory");
                    Files.createDirectory((new File(st.nextToken())).toPath());
                    pw.println("0");
                    pw.println("A new Directory has been Created Successfully");
                    
                    }
                    catch(Exception e)
                    {
                        ok = e.toString();
                        p1 = ok.lastIndexOf('.');
                        p2 = ok.lastIndexOf("Exception");
                        ok1 = ok.substring(p1+1,p2);
                        pw.println("100");
                        pw.println("The operation has failed because: "+ok1+" in Server");
                       
                        
                    }

                }

                else if(s.equalsIgnoreCase("rmdir"))
                {
                    try
                    {
                    Files.delete((new File(st.nextToken())).toPath());
                    pw.println("0");
                    pw.println("The Directory has been Removed Successfully");
                    
                    }
                    catch(Exception e)
                    {
                        ok = e.toString();
                        p1 = ok.lastIndexOf('.');
                        p2 = ok.lastIndexOf("Exception");
                        ok1 = ok.substring(p1+1,p2);
                        pw.println("101");
                        pw.println("The operation has failed because: "+ok1+" in Server");
                        
                    }
                }

                else if(s.equalsIgnoreCase("rm"))
                {
                    try
                    {
                        //System.out.println("saef");
                    Files.delete((new File(st.nextToken())).toPath());
                    pw.println("0");
                    pw.println("The File has been Removed Successfully");
                    
                    }

                    catch(Exception e)
                    {
                        
                        //System.out.println("saddsd");
                        ok = e.toString();
                        ok = ok.split(" ")[0];
                        // System.out.println(ok);
                        // System.out.println("dsajhd");
                        p1 = ok.lastIndexOf('.');
                        //System.out.println("s3");
                        p2 = ok.lastIndexOf("Exception");
                        //System.out.println("s4 "+p1+" "+p2);

                        ok1 = ok.substring(p1+1,p2);
                        //System.out.println("s5");
                        pw.println("102");
                        pw.println("The operation has failed because: "+ok1+" in Server");
                       
                        //System.out.println("s6");
                        
                    }
                }
                else if(s.equalsIgnoreCase("Shutdown"))
                {
                    try
                    {
                    System.exit(0);
                
                    //System.out.println("Closed Client:"+Thread.currentThread().getName());

                    //pw.println("Closed Successfully");
                    }
                    
                    catch(Exception e)
                    {
                        ok = e.toString();
                        p1 = ok.lastIndexOf('.');
                        p2 = ok.lastIndexOf("Exception");
                        ok1 = ok.substring(p1+1,p2);
                        pw.println("103");
                        pw.println("The operation has failed because: "+ok1+" in Server");
                        
                    }
                }
            //} 
            // else
            // {
                else
                {
                //System.out.println(il);
                pw.println("107");
                pw.println("Not a Valid Command");
                }
                //pw.println(bi.readLine());
            //}
        }}catch(Exception e)
        {
        ok = e.toString();
        p1 = ok.lastIndexOf('.');
        p2 = ok.lastIndexOf("Exception");
        ok1 = ok.substring(p1+1,p2);
        pw.println("97");
        pw.println("The operation has failed because: "+ok1+" in Server");

        }
        pw.close();
        br.close();
        cs.close();
    }catch(Exception e){
        // ok = e.toString();
        // p1 = ok.lastIndexOf('.');
        // p2 = ok.lastIndexOf("Exception");
        // ok1 = ok.substring(p1+1,p2);
        // pw.println("97");
        // pw.println("Operation Failed Because "+ok1);
        
    }
    }
    public static void main(String[] args) throws Exception
    {
        String jk  = args[0];
        int port =  Integer.parseInt(args[1]);
        if(jk.equalsIgnoreCase("Start"))
        {
        ServerSocket ss = new ServerSocket(port);
        //SocketAddress sa = new So
        System.out.println(ss.getLocalSocketAddress().toString());
        System.out.println("Waiting..." + ss.getInetAddress());
        
        ms = new HashMap<String,Long>();
        ds = new HashMap<String,Long>();
        
        
        while(true)
        {
        Socket cs = ss.accept();
        //cs.setSoTimeout(10000);
        (new Thread(()->Manage(cs))).start();
        //System.out.println("Client Closed");
        }
        // System.out.println("connected to:"+cs.getLocalPort());
        // PrintWriter pw = new PrintWriter(cs.getOutputStream(),true);
        // BufferedReader br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        // String il,ol;
        
        // pw.println("Welcome You Connected to :" + ss.getLocalSocketAddress().toString() + " to port:" + ss.getLocalPort());
        // System.out.println("hhiwdj");
        // while((il=br.readLine())!=null)
        // {
        //     System.out.println(il);
        //     pw.println("You Message Received");
        // }
        // pw.close();
        // br.close();
        // cs.close();
        // ss.close();
    }
    else
    {
        System.out.println("Wrong Command! Retype 'Start' To start The Server again");
    }
}}

