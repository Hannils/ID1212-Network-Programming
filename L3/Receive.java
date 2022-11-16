import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.util.regex.*;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Test
 */
public class Receive {
    public final static String HOST = "webmail.kth.se";
    public final static int PORT = 993;
    private static BufferedReader in = null;
    private static PrintWriter out = null;
    private static SSLSocket client;
    private static int commandPrefix = 0;

    public static void main(String[] args) {
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        ArrayList<String> data;
        int totalNumberOfMessages = 0;
        try {
            Console console = System.console();
            String username = console.readLine("Enter your username (without @kth.se): ");
            String password = new String(console.readPassword("Enter your password: "));

            client = (SSLSocket) sslsocketfactory.createSocket(HOST, PORT);
            System.out.println("Connect to Host");

            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream());
            String response;
            response = in.readLine();
            System.out.println("Server: " + response);
            writeToServer("LOGIN " + username + " " + password);
            readFromServer();

            writeToServer("list \"inbox\" \"*\"");
            readFromServer();

            writeToServer("select inbox");
            data = readFromServer();
            for (String message : data) {
                if (message.contains("EXISTS")) {
                    Matcher matcher = Pattern.compile("\\d+").matcher(message);
                    matcher.find();
                    totalNumberOfMessages = Integer.valueOf(matcher.group());
                }
            }

            if (totalNumberOfMessages == 0) {
                System.out.println("Could not fetch amount of messages");
                return;
            }

            writeToServer("fetch " + totalNumberOfMessages + " body[1]");
            System.out.println("------------ START ------------");
            readFromServer();
            System.out.println("------------ END ------------");

            /*
             * response = in.readLine();
             * System.out.println("Server: " + response);
             * response = in.readLine();
             * System.out.println("Server: " + response);
             */

        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }

    private static void writeToServer(String message) {
        System.out.println("Client: " + message);
        out.print("a" + ++commandPrefix + " " + message + "\r\n");
        out.flush();
    }

    private static ArrayList<String> readFromServer() throws IOException {
        String message;
        ArrayList<String> messages = new ArrayList<String>();

        while ((message = in.readLine()) != null) {
            message = message.replace("=C3=A4", "ä").replace("=C3=A5", "å").replace("=C3=B6", "ö");
            System.out.println("Server: " + message);
            messages.add(message);

            if (message.toLowerCase().contains("bad") || message.toLowerCase().contains("failed")) {
                System.out.println("Bad command or failed log in. Exiting system");
                System.exit(1);
            }
            if (message.contains("a" + commandPrefix))
                break;
        }

        return messages;
    }
}