import java.net.*;
import java.util.StringTokenizer;
import java.io.*;






/*Implement Resume operation and update existing with error
codes and standard error output*/


class Client
{


    // public static void sendFile(Socket s,String str) throws Exception
    // {
    //     PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
    //     FileReader fr = new FileReader(str);
    //     int j=0;
    //     while((j=fr.read())!=-1)
    //     {
    //         System.out.println("Sending File Data");
    //         pw.print(j);
    //     }
    //     fr.close();
    // } 
    public static void main(String[] args) throws Exception
    {
        String s1=null,s2=null,s3=null,s4=null;
        //s1 = args[0];
        //s2 = args[1];
        if(args.length<1)
        {
            System.out.println("Insufficient arguments supplied");
        }
        else if(args.length==1)
        {
            s1 = args[0];
            s3 = s1;
        }
        else if(args.length==2)
        {
            s1 = args[0];
            s2 = args[1];
            s3 = String.join(" ",s1,s2);
        }
        else if(args.length==3)
        {
            s1 = args[0];
            s2 = args[1];
            s4 = args[2];
            s3 = String.join(" ",s1,s2,s4);
        }
        else
        {
            System.out.println("Additional Arguments Supplied!");
        }
        
        //System.out.println(s3);
       
        




        //s1 = args[0];
        //s2 = args[1];
        String ev = System.getenv("PA1_SERVER");

        //FileOutputStream fg = new FileOutputStream(new File("D:/Errors.txt"));
        //System.out.println(ev);
        Socket s = new Socket(ev.substring(0, ev.lastIndexOf(':')), Integer.parseInt(ev.substring(ev.lastIndexOf(':')+1)));
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
        BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
        FileOutputStream fw = null;
        ObjectInputStream oin = new ObjectInputStream(s.getInputStream());
        File f = null;
        int i=0;
        byte b=0;
        // while((s3=bi.readLine())!=null)
        // {

            StringTokenizer st = new StringTokenizer(s3," ");
            // while(st.hasMoreTokens())
            // {
            String ki = st.nextToken();

                if (ki.equals("upload"))
                {
                    s4 = st.nextToken();
                    int k = s4.lastIndexOf('/');
                    String l = s4.substring(k+1);
                    //sendFile(s,s4);
                    File fl = new File(s4);
                    long lo = fl.length();

                    if(!fl.exists())
                    {
                        System.err.println("Upload Failed Because NoSuchFile in Client System");
                        System.out.println("System will Now will Exit with Exit Code:97");
                        System.exit(97);
                    }
                    //double ij = lo;
                   
                    long jj = 0;
                    double ii=0;
                    double kk = (double)lo;
                    double tt = 0;
                    long jk=0;
                    //System.out.println(kk);
                    //FileReader fr = new FileReader(f);
                    FileInputStream fs = new FileInputStream(fl);
                    pw.println("upload "+st.nextToken());
                    jj = Long.parseLong(br.readLine());

                    
                    if(jj!=0)
                    {
                        System.out.println("\n Skipping "+(long)(((double)jj/(double)lo)*100)+" Percent As it was Previously Uploaded \n \n");
                        //jk = Long.parseLong(br.readLine());
                        fs.skip(jj);
                    }

                    //ObjectInputStream oin = new ObjectInputStream(new FileInputStream(f));
                    pw.println(lo-jj);
                    //System.out.println(st.nextToken());
                    //System.out.println(lo);
                    while((i=fs.read())!=-1)
                    {
                        //System.out.println("Sending File Data");
                        
                        os.write(i);
                        os.flush();
                        if(br.ready())
                        {
                            System.out.println(br.readLine());
                        }
                        
                        // if(tt%kk==0)
                        // jj = (long)((ii/kk)*100);
                        
                        // ii+=1;
                        // // {
                        // if(!(jj==(long)(ii/kk)))
                        // {
                        // System.out.println("Uploading Data Percent Complete"+(long)((ii/kk)*100));
                        // }
                        
                        //}
                        //tt+=1;
                        //pw.println(i);
                        //bw.write(i);
                        //pw.flush();
                        //dos.writeInt(i);
                    }
                    //System.out.println("sent");
                    // if(br.ready())
                    // {
                     
                    //}
                //     os.flush();
                 String oi;
                   while(!(oi=br.readLine()).equalsIgnoreCase("END"))
                   {
                       System.out.println(oi);
                   }
                    //oin.close();
                    fs.close();
                    //oi = br.readLine();

                    
                    //pw.println("END");
                    //os.writeObject("END");
                    
                    //dos.writeChar(-1);
                    //dos.flush();
                    //bw.flush();
                    //pw.flush();
                    //pw.flush();
                    //System.out.println("sent end data");
                    
                   
                }
                // else if(ki.equalsIgnoreCase("download"))
                // {
                    
                //     String ss = br.readLine();
                //     //StringToken
                // }
                else if(ki.equalsIgnoreCase("download"))
                {
                    //System.out.println("Entered"+il);
                    //receiveFile(cs,st.nextToken());
                boolean append = false;
                long ui=0;
                   pw.println("download "+st.nextToken());
                   f = new File(st.nextToken());
                   //System.out.println("I am Here");
                   //System.out.println("hello");
                   ui = Long.parseLong(br.readLine());
                   //System.out.println(ui);
                   if(ui==-1)
                   {
                        System.err.println("Downlad Failed Because NoSuchFile in Server");
                        System.out.println("System will Now will Exit with Exit Code:98");
                        System.exit(97);
                   }
                   if(ui!=0)
                   {
                       //System.out.println("Skipped "+ui+" Percent As It Was Already Downloaded");
                       append = true;
                   }
                   long bs = Long.parseLong(br.readLine());
                   if(ui!=0)
                   {
                       System.out.println("Skipped "+(long)(((double)f.length()/(double)(bs+f.length()))*100) + " Percent As It Was Already Downloaded");
                   }
                   System.out.println(bs);
                   if(bs==-1)
                   {
                        System.err.println("Downlad Failed Because NoSuchFile in Server");
                        System.out.println("System will Now will Exit with Exit Code:98");
                        System.exit(97);
                   }
                   //System.out.println("Now Here");
                   //fw = new FileWriter(f);
                   fw = new FileOutputStream(f,append);
                   //System.out.println(bs);
                   int j=0;
                   long jj = 0;
                   double ii=0;
                   double kk = (double)bs/100;
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
                       j = oin.read(); 
                       
                       
                       jj = (long)(ii/kk);
                       ii+=1;
                       // {
                       if(!(jj==(long)(ii/kk)))
                       {
                       System.out.println("Downloading..."+(long)(ii/kk));
                       }

                       fw.write(j);
                       bs = bs - 1;

                       //System.out.println("asfsfa");
                       //il = br.readLine();
                       // if(i instanceof String)
                       // {
                       //     break;
                       // }
                       //System.out.println("gfergeg");
                       //os.write(j);
                       
                       //pw.println(ii);
                       //System.out.println(Integer.parseInt(il));
                       //fw.write(Integer.parseInt(il));
                       

                       
                       //System.out.println("out");
                   }
                   //System.out.println("outssss");
                   
                   //os.close();
                   fw.close();
                   //ps.close();
                   //System.out.println(st.nextToken());
               }

               else if(ki.equalsIgnoreCase("dir"))
               {
                   pw.println(s3);
                   String in;
                   while(!(in=br.readLine()).equalsIgnoreCase(""))
                   {
                       System.out.println(in);
                   }
                   
               }
            // }
            // else if(ki.equals("rmdir"))
            // {
            //     String jj;
            //     pw.println(s3);
            //     // while((jj=br.readLine())!=null)
            //     // {
            //     //     System.err.println(jj);
            //     // }
            //     //System.out.println(br.readLine());

            // }
            else
            {
            pw.println(s3);
            }

                try
                {
                String ec = br.readLine();
                if(Integer.parseInt(ec)==0)
                {
                    System.out.println(br.readLine());
                    System.out.println("System Will Now Exit With Exit Code:0");
                    //System.exit(0);
                }
                else
                {
                    System.err.println(br.readLine());
                    System.out.println("System Will Now Exit With Exit Code:"+ec);
                    System.exit(Integer.parseInt(ec));
                }
                }catch(Exception e)
                {
                    System.out.println("The Server Has Been Closed");
                    System.exit(0);
                }
            
            
        //}

        br.close();
        os.close();
        pw.close();
        bi.close();
        s.close();
    }
}