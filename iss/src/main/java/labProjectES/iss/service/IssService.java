/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labProjectES.iss.service;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import labProjectES.iss.repository.Location;
import labProjectES.iss.repository.LocationRepository;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author joaoalegria
 */
@Service 
public class IssService {
    
    @Autowired
    LocationRepository lr;
    
    private final Client client = ClientBuilder.newClient();
    private static final String URL = "https://api.wheretheiss.at/v1/satellites/25544";
    private RestTemplate restTemplate= new RestTemplate();
    
    /**
     * 
     * @return 
     */
    public Location getLatestLocation(){
        List<Location> locations=lr.getOrderLocations();
        if(locations.size()==0){
            this.getLocation();
            locations=lr.getOrderLocations();
        }
        return locations.get(0);
    }
    
    @Scheduled(fixedRate = 5000)
    public void getLocation() {
        Location location= this.restTemplate.getForObject(this.URL, Location.class);
        lr.save(location);
    }
    
}

