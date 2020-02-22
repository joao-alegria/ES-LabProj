/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labProjectES.iss.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author joaoalegria
 */
@Service
public class IssService {
    
    private final Client client = ClientBuilder.newClient();
    private static final String URL = "https://api.wheretheiss.at/v1/satellites/25544";
    
    /**
     * 
     * @return 
     */
    public JSONObject getLocation(){
        JSONObject data = client
                            .target(URL)
                            .request(MediaType.APPLICATION_JSON)
                            .get(JSONObject.class);
        
        String locationKeys[] = new String[]{"longitude", "latitude", "altitude"};
        
        JSONObject locationCoordinates=new JSONObject();
        for(String key : locationKeys){
            locationCoordinates.put(key, data.get(key));
        }
        
        return locationCoordinates;
    }
    
}

