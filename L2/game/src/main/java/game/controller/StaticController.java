package game.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import game.View;

public class StaticController {
    View view;
    private Map<String, String> contentTypes = Map.of(
            ".js", "text/javascript",
            ".html", "text/html");

    public StaticController(View view) {
        this.view = view;
    }

    /**
     * Checks if the StaticController can handle the given method and path
     * @param method The method of which to check
     * @param path The path of which to check
     * @return true or false
     */
    public boolean canHandle(String method, String path) {
        if (!method.equals("GET"))
            return false;
        String requestedFile = path.substring(1);

        for (File file : getDirectoryFileList()) {
            if (file.isFile() && file.getName().equals(requestedFile)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Executes only if the controller can handle the given method and path
     * Renders the .html and .js static files
     * @param path the path for the file
     */
    public void handle(String path) {
        String requestedFileName = path.substring(1);
        File requestedFile = null;
        Scanner scanner;
        try {

            for (File file : getDirectoryFileList()) {
                if (file.isFile() && file.getName().equals(requestedFileName)) {
                    requestedFile = file;
                }
            }

            if (requestedFile == null)
                return;

            String fileExtension = requestedFileName.substring(requestedFileName.lastIndexOf("."));
            scanner = new Scanner(requestedFile);
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                response.append(data + "\n");
            }
            scanner.close();

            HashMap<String, String> headers = View.getBasicHttpHeaders();
            String contentType = contentTypes.get(fileExtension);
            headers.put("Content-Type", contentType == null ? "text/plain" : contentType);

            view.sendResponse(200, headers, response.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private File[] getDirectoryFileList() {
        URL url = getClass().getResource("../static");
        File folder = new File(url.getPath());
        return folder.listFiles();
    }
}
