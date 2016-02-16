package com.aivco;



import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by joel on 2/14/16.
 */
public class SocketC {

    private final int port;
    Socket socket=null;
    BufferedWriter bW;
    static String sep= File.separator;
    static String workingDir = System.getProperty("user.dir");
    private String url;


    int len=0;
    public SocketC(int port,String url) {
        this.port = port; this.url=url;
    }

    public static void main(String[] arg)
    {
        try {
            new SocketC(1090,"192.168.100.13").connectClientSocket();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public  void connectClientSocket() throws IOException {
        System.out.println("client is running");
        socket = new Socket(url, port);
       // writeBufferedStream(("hey server its i your new client  ").getBytes());
        writeBufferedStream((outgoingAsFile()));
       readStream();



    }


    private boolean isServerRunning()
    {
        return false;
    }

   private void readWithBufferReader() throws IOException {

       BufferedReader bR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       System.out.println("first input data " + bR.readLine());//because we are using the readline,it will only stop reading when it sees a newline
   }

   private void writeWithBufferedWriter(String message) throws IOException {
       System.out.println("...........writing buffer.......in client.....");
       bW=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
       bW.write(message);
       bW.newLine();//passes newline because it will not stop until it sees a newline
       bW.flush();

   }

  ///////////send string using dataOutputStream,will need to include a eof so the readers knows when to stop///
    private void writeStream(byte[] b) throws IOException {
        System.out.println("...........writing stream message.......in client..... " + "\n");
        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
        dos.write(b);
        dos.flush();



    }
    private void readStream() throws IOException {
        byte [] b=new byte[1024];
        DataInputStream dis=new DataInputStream(socket.getInputStream());
        int c=0;
        while((c=dis.read())!=-1){

            System.out.print((char) c);
        }
        dis.close();


    }

    /////writes data as stream,this could be a file or a raw string.////////////////////
    private void writeBufferedStream(byte[] b) throws IOException {
        System.out.println("...........writing stream message.......in client..... " + "\n");
        BufferedOutputStream dos=new BufferedOutputStream(socket.getOutputStream());
        dos.write(b);
        dos.flush();



    }


    ////will return a bytearray,the byte could holds a file an image music or document etc///////
    private byte[] outgoingAsFile(){
      File file=new File(workingDir+sep+"images"+sep+"tommy-blouse224.jpg");
        FileInputStream fis=null;
        byte [] b=null;
        try {
             fis=new FileInputStream(file);
            this.setLen(fis.available());
            System.out.println("size of byte is "+getLen());
           b=new byte[getLen()];
            fis.read(b,0,b.length);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return b;
    }

    public  int getLen() {
        return len;
    }

    public void setLen(int lent) {
        len = lent;
    }
}
