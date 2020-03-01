package labProjectES.iss.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
        Location location= this.restTemplate.getForObject(this.URL, Location.class);
        lr.save(location);
        this.log(location.toString());
    }
    
    public void log(String message){
        message = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()) + " : " + message;
        logger.info(String.format("%s",message));
        //this.kafkaTemplate.send(TOPIC,message);
    }
    
}

