package com.aivco;

import java.io.*;
import java.net.*;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by johnanderson1 on 2/16/16.
 */
public class DatagramExe {
    static String sep = File.separator;
    static String workingDir = System.getProperty("user.dir");

    public int getLen() {
        return Len;
    }

    public void setLen(int len) {
        Len = len;
    }

    int Len;

public static void main(String args[])  {

    try {
        new DatagramExe().reciever();
    } catch (IOException e) {
        e.printStackTrace();
    }


}


    public void sender() throws IOException {
        DatagramSocket ds = new DatagramSocket();
        String str = "hello client";
        InetAddress address = InetAddress.getLocalHost();
        DatagramPacket dp = new DatagramPacket(outgoingAsFile(), getLen(), address,19214);
        ds.send(dp);
        ds.close();
    }

    public void reciever() throws IOException {

        DatagramSocket ds=null;
        DatagramPacket dp=null;
        FileOutputStream fos=null;

        ds=new DatagramSocket(19214);
        byte [] b=new byte[ds.getReceiveBufferSize()];
        dp=new DatagramPacket(b, b.length);
        System.out.println(b.length);
        ds.receive(dp);
        System.out.println("waiting for message");
        incomingAsFile(b);
        System.out.println("finished writing");


    }

    ////will return a bytearray,the byte could holds a file an image music or document etc///////
    private byte[] outgoingAsFile() {
        File file = new File(workingDir + sep + "images" + sep + "tommy-blouse224.jpg");
        FileInputStream fis = null;

        byte[] b = null;
        try {
            fis = new FileInputStream(file);

            this.setLen(fis.available());
            System.out.println("size of byte is " + getLen());
            b = new byte[getLen()];
            fis.read(b, 0, b.length);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return b;
    }


    private boolean incomingAsFile(byte [] b)
    {
        File file=new File(workingDir+sep+"images");
        boolean dir=file.mkdir();
        File textfile=new File(workingDir+sep+"images","imagefromDatagram.jpg");
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


}



