package project.app.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.app.model.ISBNResponse;

public class OpenLibraryAPIService {

    public static ISBNResponse searchISBNAPI(String ISBN) throws IOException {
        //keeping this here for reference
        // ISBN = "9780980200447";
        // /isbn does not give author names, only link to their author endpoint, 
        // so you'll have to run another query to fetch the name
        // also, some observed behaviors:
        // 1.
        // has no authors (when it should have 2): https://openlibrary.org/isbn/9780060853969.json
        // has the right authors: https://openlibrary.org/api/books?bibkeys=ISBN:9780060853969&jscmd=data&format=json
        // 2.
        // has 1 author (which is correct for the ISBN): https://openlibrary.org/isbn/9780140328721.json
        // has a lot of authors listed: https://openlibrary.org/api/books?bibkeys=ISBN:9780140328721&jscmd=data&format=json
        
        URL url = new URL("https://openlibrary.org/api/books?bibkeys=ISBN:" + ISBN + "&jscmd=data&format=json");      
        ISBNResponse OLISBNResponse;
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String output;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        output =  br.lines().collect(Collectors.joining());
        // OLISBNResponse = mapper.readValue(output, OLISBNResponse.class);
        Map<String, ISBNResponse> map = mapper.readValue(output, new TypeReference<Map<String,ISBNResponse>>(){});
        OLISBNResponse = map.get("ISBN:" + ISBN);
        conn.disconnect();
        System.out.println("THE STRING MAPPED OUT: " + OLISBNResponse.toString());
        return OLISBNResponse;
    }
}
