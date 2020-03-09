package labProjectES.iss.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.apache.kafka.clients.producer.Producer;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import labProjectES.iss.repository.Location;
import labProjectES.iss.repository.LocationRepository;
import org.springframework.web.client.RestTemplate;

@Service
public class IssService {
    
    @Autowired
    LocationRepository lr;
    private Map<String,Double> circlesLatitude;
    private Map<String, Integer> circlesCount;

    public IssService() {
        this.circlesLatitude= new HashMap();
        this.circlesCount= new HashMap();
        this.circlesLatitude.put("Arctic", 66.3348);
        this.circlesLatitude.put("Cancer", 23.2612);
        this.circlesLatitude.put("Equator", 0.0);
        this.circlesLatitude.put("Capricorn", -23.2612);
        this.circlesLatitude.put("Antarctic", -66.3348);
        
        this.circlesCount.put("Arctic", 0);
        this.circlesCount.put("Cancer", 0);
        this.circlesCount.put("Equator", 0);
        this.circlesCount.put("Capricorn", 0);
        this.circlesCount.put("Antarctic", 0);
    }
    
    
    private final Client client = ClientBuilder.newClient();
    private static final String URL = "https://api.wheretheiss.at/v1/satellites/25544";
    private RestTemplate restTemplate= new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(IssService.class);
    private static final String TOPIC = "iss";

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

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
        Location lastLocation = lr.getLastLocation();
        Location location= this.restTemplate.getForObject(this.URL, Location.class);
        lr.save(location);
        this.log(location.toString());
        if(lr.getOrderLocations().size()>1){
            checkCircles(lastLocation,location);
        }
    }
    
    public void log(String message){
        message = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()) + " : " + message;
        logger.info(String.format("%s",message));
    }

    public void checkCircles(Location lastLocation, Location location) {
        for(String key : this.circlesLatitude.keySet()){
            if((lastLocation.getLatitude()>this.circlesLatitude.get(key) &&  location.getLatitude()<this.circlesLatitude.get(key)) ||
                    (lastLocation.getLatitude()<this.circlesLatitude.get(key) &&  location.getLatitude()>this.circlesLatitude.get(key))){
                int currentCount = this.circlesCount.get(key);
                this.circlesCount.put(key, currentCount+1);
                this.kafkaTemplate.send(TOPIC,"ISS just passed the "+key+" Latitude Circle.");
            }
        }
    }
    
   public Map<String, Integer> getCirclesCount(){
       return this.circlesCount;
   }
    
}

