package org.quantumclient.commons.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class NetworkUtils {

    public static boolean isIpOnline(InetAddress ip, int port) {
        try (Socket s = new Socket(ip, port)) {
            return true;
        } catch (IOException ex) {
        }
        return false;
    }

    public static boolean isServerOnline(String domain, int port) {
        try {
            return isIpOnline(InetAddress.getByName(domain), port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static JsonElement getJsonFromURL(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(2500);
        conn.setReadTimeout(2500);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        return new JsonParser().parse(new InputStreamReader(conn.getInputStream()));
    }

    public static void downloadFile(URL url, Path path) throws IOException {
        Files.createDirectories(path.getParent());

        try (InputStream in = url.openStream()) {
            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
