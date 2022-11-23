package game;

import java.net.*;
import java.io.*;
import java.util.*;

public class View {
    PrintWriter printer;
    private Map<Integer, String> httpResponseStatusMessages = Map.of(
            200, "OK",
            400, "Bad Request",
            404, "Not Found",
            405, "Method Not Allowed",
            500, "Internal Server Error",
            505, "HTTP Version Not Supported");

    public View(PrintWriter printer) {
        this.printer = printer;
    }

    /**
     * Function which sends the response when a request is done
     * 
     * @param code    Statuscode to send along
     * @param headers The headers to send along
     * @param message The message to send along with the code
     */
    public void sendResponse(int code, HashMap<String, String> headers, String message) {
        String firstLine = "HTTP/1.1 " + code + " " + httpResponseStatusMessages.get(code) + "\r\n";

        headers = headers != null ? headers : new HashMap<String, String>();

        if (message != null) {
            headers.put("Content-Length", "" + message.length());
        }

        ArrayList<String> headerLines = new ArrayList<String>();
        for (String key : headers.keySet()) {
            headerLines.add(key + ": " + headers.get(key));
        }

        String headerString = headerLines.size() == 0 ? "" : String.join("\r\n", headerLines);

        String finalMessage = firstLine + headerString + "\r\n\r\n" + message;

        System.out.println("responding: \n\n" + finalMessage);
        printer.write(finalMessage);
        printer.flush();

    }

    /**
     * Puts the necessary HTTP headers into a hashmap
     * 
     * @return The hashmap with the necessary HTTP headers
     */
    public static HashMap<String, String> getBasicHttpHeaders() {
        HashMap<String, String> basicHeaders = new HashMap<String, String>();
        basicHeaders.put("Content-Type", "text/plain");
        basicHeaders.put("Access-Control-Allow-Origin", "https://localhost:8090");
        basicHeaders.put("Referrer-Policy", "unsafe-url");
        basicHeaders.put("Server", "Custom-Java-Server-v1.0 X OS");
        basicHeaders.put("Access-Control-Allow-Credentials", "true");
        return basicHeaders;
    }
}
