import java.rmi.UnmarshalException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.Unreferenced;

import javax.net.ssl.ExtendedSSLSession;

import java.io.*;


class Client {

    private Client() {}

    private static int lastPercent;
   public static void updatePercentageBar(double progress)  throws Exception{
       int percent = (int) Math.round(progress * 100);
       if (Math.abs(percent - lastPercent) >= 1) {
           //String sk = new String("\r[".getBytes(),"UTF8");
           StringBuilder template = new StringBuilder("\r[");
           for (int i = 0; i < 50; i++) {
               if (i < percent * .5) {
                   template.append("=");
               } else if (i == percent * .5) {
                   template.append(">");
               } else {
                   template.append(" ");
               }
           }
           template.append("] %s   ");
           if (percent >= 100) {
               template.append("%n");
           }
           System.out.printf(template.toString(), percent + "%");
           lastPercent = percent;
       }
   }

    public static void progressPercentage(int remain, int total) {
        if (remain > total) {
            throw new IllegalArgumentException();
        }
        int maxBareSize = 50; // 10unit for 100%
        int remainProcent = ((100 * remain) / total) / maxBareSize;
        char defaultChar = '-';
        String icon = "*";
        String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
        StringBuilder bareDone = new StringBuilder();
        bareDone.append("[");
        for (int i = 0; i < remainProcent; i++) {
            bareDone.append(icon);
        }
        String bareRemain = bare.substring(remainProcent, bare.length());
        //System.out.print("\r" + bareDone + bareRemain + " " + remainProcent * 50 + "%");
        if (remain == total) {
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {

        //String host = (args.length < 1) ? null : args[0];
        String host = System.getenv("PA2_SERVER");
        int j=0;
        String cmd = args[j++];
        String tk=null;
        BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
        byte[] buff = null;
        File f=null;
        FileOutputStream fo = null;
        FileInputStream fin = null;
        DataInputStream din = null;
        DataOutputStream dout = null;
        int n=0;
        try {
            Registry registry = LocateRegistry.getRegistry(Integer.parseInt(host.substring(host.indexOf(':')+1)));
            //Hello stub = (Hello) registry.lookup("Hello");
            FTP stub = (FTP) registry.lookup(host);
            n = stub.addClient();
            if(cmd.equalsIgnoreCase("send"))
            {
            String response = stub.sayHello();
            System.out.println("Response: " + response);
            }
            else if(cmd.equalsIgnoreCase("dir"))
            {
            //String tk = bi.readLine();
            tk = args[j++];
            File[] lof = stub.getFiles(tk);
            // if(lof.equals(null) || lof==null)
            // {
            //     System.out.println("Failed to get directory");
            //     System.exit(103);
            // }
            try
            {
            for(File g:lof)
            {
                
                System.out.println(g.getName());
                
            }
        }catch(Exception e){System.err.println("Failed to get directory");System.exit(103);}
            }
            else if(cmd.equalsIgnoreCase("mkdir"))
            {
            //tk = bi.readLine();
            tk = args[j++];
            String msg = stub.makeDir(tk);
            //System.out.println(msg);
            if(msg.equalsIgnoreCase("Success"))
            {
                System.out.println("Directory Created SuccessFully");
            }
            else
            {
                System.err.println("Directory Creation Failure "+msg);
                System.exit(102);
            }
            }
            
            else if(cmd.equalsIgnoreCase("rmdir"))
            {
            //tk = bi.readLine();
            tk = args[j++];
            String msg = stub.rmDir(tk);
            //System.out.println(msg);
            if(msg.equalsIgnoreCase("Success"))
            {
                System.out.println("Directory Removed SuccessFully");
            }
            else
            {
                System.err.println("Directory Removal Failure "+msg);
                System.exit(102);
            }
            }

            else if(cmd.equalsIgnoreCase("rm"))
            {
            //tk = bi.readLine();
            tk = args[j++];
            String msg = stub.rm(tk);
            //System.out.println(msg);
            if(msg.equalsIgnoreCase("Success"))
            {
                System.out.println("File Removed SuccessFully");
            }
            else
            {
                System.err.println("File Removal Failure");
                System.exit(101);
            }
            }


            else if(cmd.equalsIgnoreCase("fdownload"))
            {
            //tk = bi.readLine();
            //String jk = bi.readLine();
            String in = args[j++];
            String out = args[j++];
            buff = stub.download(in);
            System.out.println(buff.length);
            f = new File(out);
            fo = new FileOutputStream(f);
            int i=0;
            while(i<buff.length)
            {
                fo.write(buff[i]);
                progressPercentage(buff.length-i,buff.length);
                i+=1;
            }
            fo.flush();
            fo.close();
            }

            else if(cmd.equalsIgnoreCase("fupload"))
            {
                String in = args[j++];
                String out = args[j++];
                f = new File(in);
                fin = new FileInputStream(f);
                byte[] buffer = new byte[(int)f.length()];
                fin.read(buffer);
                fin.close();
                String msg = stub.upload(out,buffer);
                System.out.println(msg);
            }

            else if(cmd.equalsIgnoreCase("download"))
            {
                String in = args[j++];
                String out = args[j++];
                byte b=0;
                boolean append = false;
                long s = stub.openFileR(in,n);
                RandomAccessFile raf = new RandomAccessFile(out, "rw");
                if(s==999999999L)
                {
                    System.err.println("Couldn't Download File Exiting with Error Code 104");
                    System.exit(104);
                }
                long temp = 0;
                
                //ProgressBar pb = new ProgressBar();
                
                f = new File(out);
                

                if(s<0)
                {
                    append = true;
                    //RandomAccessFile ra = new RandomAccessFile(f, "rw");
                    temp = Math.abs(s) + (f.length());
                    //ra.setLength(ra.length()-5);
                    s = Math.abs(s);
                    raf.seek(f.length());
                    //ra.close();
                }
                else
                {
                    temp = s;
                }
                
                //System.out.println(s);

                
                
                //fo = new FileOutputStream(f);
                //pb.update(0,(int)temp);
                fo = new FileOutputStream(f,append);
                dout = new DataOutputStream(fo);
                while(s>0)
                {
                    byte h = stub.getByte(f.length(),n);
                    // if(Double.isNaN(h))
                    // {
                    //     System.out.println("Couldn't Download File Exiting with Error Code 104");
                    //     System.exit(104);
                    // }
                    dout.writeByte(h);
                    dout.flush();
                    //raf.writeByte(h);


                    //fo.write(stub.getByte(f.length()));
                    //fo.flush();
                    
                    updatePercentageBar(((double)(f.length())/(double)temp));
                    //pb.update((int)f.length(),(int)temp);
                    //progressPercentage((int)(temp-f.length()),(int)temp);
                    s-=1;
                    //fo.write(b);
                }
                //fo.close();
                //dout.close();
                raf.close();

                if(stub.closeFileR(n).equalsIgnoreCase("Success"))
                {
                    System.out.println("Downloaded File SuccessFully");
                }
                else
                {
                    System.err.println(stub.closeFileR(n));
                    System.exit(104);
                }


            }
            
            else if(cmd.equalsIgnoreCase("upload"))
            {
                String in = args[j++];
                String out = args[j++];
                
                f = new File(in);
                fin = new FileInputStream(f);


                long s = stub.openFileW(out,n);
                long as = f.length() - s; 

                // if(!f.exists())
                // {
                //     System.out.println("File Does not Exist. File cannot be uploaded");
                //     System.exit(104);
                // }
                //System.out.println("upll");
                //System.out.println(s);
                if(s>0)
                {
                    while(s>0)
                    {
                        fin.read();
                        s-=1;
                    }

                }
                
                while(as>0)
                {
                    long jk = stub.setByte((byte)fin.read(),n);
                    updatePercentageBar(((double)jk/(double)f.length()));
                    as-=1;
                }
                //System.out.println("dpll");
                fin.close();
                // stub.closeFileW();
                // System.out.println("dplp");
                if(stub.closeFileW(n).equalsIgnoreCase("Success"))
                {
                    System.out.println("Uploaded File SuccessFully");
                }
                else
                {
                    System.err.println(stub.closeFileW(n));
                    System.exit(104);
                }
            }


            else if(cmd.equalsIgnoreCase("shutdown"))
            {
                stub.shutdown();
            }

            else
            {
                System.err.println("Invalid Option type help for the available Options");
            }
            
           
            
        }
        catch(FileNotFoundException o)
        {
            System.err.println("Couldn't Find The File Upload Failure");
            System.exit(103);   
        }
        catch (UnmarshalException k)
        {
         System.err.println("Server has been Closed");
         System.exit(103);   
        } 
        catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}