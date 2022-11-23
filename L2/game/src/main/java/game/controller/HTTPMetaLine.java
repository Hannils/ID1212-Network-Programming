package game.controller;

import java.net.http.HttpResponse;
import java.util.*;

import game.HTTPException;

import java.net.*;

public class HTTPMetaLine {
    private String method, path, protocol;

    /**
     * Indicates if the first line of the HTTP request is valid
     * 
     * @param line the first line of an HTTP request
     */
    public HTTPMetaLine(String line) throws HTTPException {

        String[] meta = line.split("\\s");
        if (meta.length != 3) {
            throw new HTTPException(400, "Bad request");
        }

        method = meta[0];
        path = meta[1];
        protocol = meta[2];

        if (!protocol.equals("HTTP/1.1")) {
            throw new HTTPException(505, "HTTP Version Not Supported");
        }
        if (!path.startsWith("/")) {
            throw new HTTPException(404, "Not Found");
        }

        if (!meta[0].equals("GET") && !meta[0].equals("POST")) {
            throw new HTTPException(405, "Method Not Allowed");
        }
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String toString() {
        return method + " " + path + " " + protocol;
    }
}
