package com.aivco;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by joel on 2/14/16.
 */
public class SocketS {

    private final int port;
    Socket socket=null;
    BufferedWriter bW;
    static String sep= File.separator;
    static String workingDir = System.getProperty("user.dir");
    private InetAddress address;

    public SocketS(int port,InetAddress address) {
        this.port = port;this.address=address;
    }

    public static void main(String[] arg)
    {
        try
        {
            new SocketS(1090,InetAddress.getLocalHost()).startServerSocket();
            System.out.println(InetAddress.getLocalHost().toString());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("there is error");
        }

    }

    public void startServerSocket() throws IOException
    {

        ServerSocket Sc=new ServerSocket(port,50,address);
        System.out.println("server is running");
        socket=Sc.accept();///blocks here
        System.out.println("......client has arrived.......");
       // handleIncomingStreamAsFile(readStreamAsJavaObject());
        //handleIncomingStreamAsFile(readBufferedStream());

       // readWithBufferReader();
        readStream();
        writeStream("hey client i got your message hope you are great  \r ".getBytes());
        socket.close();

    }


/////////the method below is used to read and write ascii ie strings//////////////////
   private void readWithBufferReader() throws IOException {
       System.out.println("......reading message from client.......");
       BufferedReader bR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       System.out.println("first input data " + bR.readLine());
   }

   private void writeWithBufferedWriter(String message) throws IOException {

       bW=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
       bW.write(message);
       bW.newLine();
       bW.flush();
       bW.close();
   }

    private void writeStream(byte[] b) throws IOException {
        System.out.println("......writing confirmation to client.......");
        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
        dos.write(b);
        dos.flush();


    }
    /*
    * uses dataInputStream to read bytes,as int from inputStream
    * */
    private void readStream() throws IOException
    {
        byte[] b = new byte[50];
////without the lines below it does not block///////////////////////////
        DataInputStream dis=new DataInputStream(socket.getInputStream());//does not have any effect on the stream
        int c;

        while((c=dis.read())!= 13)//reading begins here,decimal 10 reps \n EOF in ascii,at this point this socket stops reading,this allows execution to pass
        ////not reading the right value will block at this point because the reader wont know when to stop reading
        {

            System.out.print((char) c);
        }
        System.out.println("......finished reading.......");

    }



    private byte[] readBufferedStream() throws IOException
    {
        byte[] b = null;
        System.out.println("......reading message from client.......");
        BufferedInputStream dis = new BufferedInputStream(socket.getInputStream());
        int nob = dis.available();
        System.out.println("......available bytes......." + nob);
        b = new byte[1024*97];
        dis.read(b, 0, b.length);
        return b;

    }

   private String handleIncomingAsString(byte [] b)
   {



       System.out.println(new String(b));

       return new String(b);
   }

   private boolean handleIncomingStreamAsFile(byte [] b)
   {
       File file=new File(workingDir+sep+"images");
       boolean dir=file.mkdir();
       File textfile=new File(workingDir+sep+"images","myimage.jpg");
       try{
           textfile.createNewFile();
           FileOutputStream fos=new FileOutputStream(textfile) ;

           fos.write(b);
           fos.flush();
           fos.close();


       } catch (FileNotFoundException e) {
           e.printStackTrace();
           System.out.println("there is error");
       } catch (IOException e) {
           e.printStackTrace();
           System.out.println("there is error");
       }


       return dir;
   }


  public byte[] readStreamAsJavaObject()  {
      ObjectInputStream is= null;
      byte []b=null;
      try {
          is = new ObjectInputStream(socket.getInputStream());
          b=(byte [])is.readObject();
      } catch (IOException e) {
          e.printStackTrace();
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      }


      return b;
  }



}
