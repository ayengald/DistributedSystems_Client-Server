import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.*;

public interface FTP extends Remote {
    String sayHello() throws RemoteException;
    File[] getFiles(String dir) throws RemoteException;
    String makeDir(String dir) throws RemoteException;
    String rmDir(String dir) throws RemoteException;
    String rm(String dir) throws RemoteException;
    byte[] download(String dir) throws RemoteException;
    //byte[] download1(String dir,String in,int done) throws RemoteException;
    String upload(String file,byte[] buffer) throws RemoteException;
    byte getByte(long rec,int c) throws RemoteException;
    long setByte(byte b,int c) throws RemoteException; 
    long openFileR(String dir,int c) throws RemoteException;
    long openFileW(String dir,int c) throws RemoteException; 
    String closeFileR(int c) throws RemoteException;
    String closeFileW(int c) throws RemoteException;
    int addClient() throws RemoteException;
    void shutdown() throws RemoteException;
}