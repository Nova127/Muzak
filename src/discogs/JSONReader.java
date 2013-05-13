package discogs;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class JSONReader {

private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

public static JSONObject readJsonFromUrl(String url) throws IOException, ParseException {
    JSONParser parser = new JSONParser();
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      Object obj = parser.parse(jsonText);
      JSONObject result = (JSONObject) obj;
      return result;

    } finally {
      is.close();
    }
  }

}