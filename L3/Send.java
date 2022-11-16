import javax.net.ssl.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class Send {
    private static final String HOST = "smtp.kth.se";
    private static final int PORT = 587;
    private static BufferedReader in = null;
    private static PrintWriter out = null;
    private static Socket client;
    private static int commandPrefix = 0;
    private static String username;

    public static void main(String[] args) {
        ArrayList<String> data;
        try {
            Console console = System.console();
            username = console.readLine("Enter your username (without @kth.se): ");
            String password = new String(console.readPassword("Enter your password: "));

            client = new Socket(HOST, PORT);
            System.out.println("Connect to Host");

            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());

            writeToServer("EHLO " + HOST);
            readFromServer();
            writeToServer("STARTTLS");
            readFromServer();

            SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
            client = ssf.createSocket(client, client.getInetAddress().getHostAddress(), client.getPort(), true);
            out = new PrintWriter(client.getOutputStream());
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writeToServer("EHLO " + HOST);
            writeToServer("AUTH LOGIN");
            writeToServer(Base64.getEncoder().encodeToString(username.getBytes()));
            writeToServer(Base64.getEncoder().encodeToString(password.getBytes()));
            readFromServer();
            writeMail("Hejsan", "", "Nothing");
            readFromServer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToServer(String message) {
        System.out.println("Client: " + message);
        out.print(message + "\r\n");
        out.flush();
    }

    private static ArrayList<String> readFromServer() throws IOException {
        String message;
        ArrayList<String> messages = new ArrayList<String>();

        while ((message = in.readLine()) != null) {
            System.out.println("Server: " + message);
            messages.add(message);

            if (message.contains("220") || message.contains("235"))
                break;
        }

        return messages;
    }


    private static void writeMail(String message, String rcpt, String subject) throws IOException {
        out.println("MAIL FROM: <mail@kth.se>");
        out.flush();
        out.println("rcpt to: <mail@kth.se>");
        out.flush();
        out.print("data \r\n");
        out.flush();
        out.println("subject: ");
        out.print(".");
        //out.print("asddsa");
        //out.print(".");
        //out.print("\r\n.");
        out.flush();
        //out.println("Subject: " + subject + "\n" + message + "\n.");
        //out.flush();
    }
}
