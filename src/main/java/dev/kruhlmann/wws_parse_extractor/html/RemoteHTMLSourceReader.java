package dev.kruhlmann.wws_parse_extractor.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class RemoteHTMLSourceReader {
    private final String url;

    public RemoteHTMLSourceReader(String url) {
        this.url = url;
    }

    private BufferedReader createBufferedReader() throws IOException {
        URL url = new URL(this.url);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        return new BufferedReader(reader);
    }

    public String read() throws IOException {
        BufferedReader in = this.createBufferedReader();

        String input;
        String result = "";
        while((input = in.readLine()) != null) {
            result += input;
        }
        in.close();

        return result;
    }
}
