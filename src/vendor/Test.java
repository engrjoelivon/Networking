package vendor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Test {
    static String sep = File.separator;
    static String workingDir = System.getProperty("user.dir");
    public static final int BUFFER_SIZE = 1024 * 50;
    private byte[] buffer;

    public Test() {
        buffer = new byte[BUFFER_SIZE];
    }

    public void startServer() throws Exception {
        ServerSocket socket = new ServerSocket(9000);

        Socket client = socket.accept();
        BufferedInputStream in =
                new BufferedInputStream(
                        new FileInputStream(workingDir + sep + "images" + sep + "bigimage.jpg"));

        BufferedOutputStream out =
                new BufferedOutputStream(client.getOutputStream());


        int len = 0;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            System.out.print("#");
        }
        in.close();
        out.flush();
        out.close();
        client.close();
        socket.close();
        System.out.println("\nDone!");
    }

    public void startClient() throws Exception {
        Socket socket = new Socket("localhost", 9000);
        BufferedInputStream in =
                new BufferedInputStream(socket.getInputStream());

        BufferedOutputStream out =
                new BufferedOutputStream(new FileOutputStream(workingDir + sep + "images" + sep + "readwithtcp.jpg"));

        int len = 0;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            System.out.print("#");
        }
        in.close();
        out.flush();
        out.close();
        socket.close();
        System.out.println("\nDone!");
    }


    public static void main(String[] args) throws Exception {
        Test test = new Test();
        if (args.length == 0) {
            test.startServer();
        } else {
            test.startClient();
        }
    }

}