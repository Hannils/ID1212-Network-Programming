import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Base64;

public class Send {
    static private PrintWriter pw;
    static private BufferedReader buffer;
    private static final String HOST = "smtp.kth.se";
    private static final int PORT = 587;
    private static String username;
    private static String receiver;
    private static String toSend;
    public static void main(String[] args) throws IOException, InterruptedException {
        Console console = System.console();
        username = console.readLine("Enter your username (without @kth.se): ");
        String password = new String(console.readPassword("Enter your password: "));
        String receiver = console.readLine("Enter username of receiver (without @kth.se): ");
        String toSend = console.readLine("Enter message to send: \n");


        Socket socket = new Socket(HOST, PORT);
        System.out.println("Connect to host");

        pw = new PrintWriter(socket.getOutputStream());
        buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        writeToServer("EHLO " + HOST);
        readFromServer();
        writeToServer("STARTTLS");
        readFromServer();

        //Takes some input from the buffer
        String msg = "";
        while((msg=buffer.readLine()) != null){
            System.out.println(msg);
            if(msg.contains("220") && msg.contains("2.0.0"))
                break;
        }

        SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) ssf.createSocket(socket, socket.getInetAddress().getHostAddress(), socket.getPort(), true);

        pw = new PrintWriter(sslSocket.getOutputStream(), true);
        buffer = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

        writeToServer("EHLO " + HOST);
        readFromServer();
        writeToServer("AUTH LOGIN");
        readFromServer();
        writeToServer(Base64.getEncoder().encodeToString(username.getBytes()));
        readFromServer();
        writeToServer(Base64.getEncoder().encodeToString(password.getBytes()));
        readFromServer();

        msg = "";
        while((msg=buffer.readLine()) != null){
            System.out.println(msg);
            if(msg.contains("334")){        //prints until 334
                System.out.println(buffer.readLine()+"\n"+buffer.readLine());
                writeToServer("MAIL FROM:<" + username + "@kth.se>");
                readFromServer();
                writeToServer("RCPT TO:<" + username + "@kth.se>");
                readFromServer();
                writeToServer("RCPT TO:<" + receiver + "@kth.se>");
                readFromServer();
                writeToServer("DATA");
                readFromServer();
                writeToServer("Date: "+ LocalTime.now());
                writeToServer("From: SMTP User <" + username + "@kth.se>");
                writeToServer("Subject: SMTP");
                writeToServer("To: " + receiver + "@kth.se");
                writeToServer("");
                writeToServer(toSend + "\r\n.");
                writeToServer("QUIT");
            }

        }
        System.out.println("END");
        pw.close();
        buffer.close();
        socket.close();
        sslSocket.close();

    }
    static void writeToServer(String msg) throws IOException {
        pw.println(msg);
        pw.flush();
        System.out.println(msg);
    }

    static void readFromServer() throws IOException {
        System.out.println(buffer.readLine());
    }
}

