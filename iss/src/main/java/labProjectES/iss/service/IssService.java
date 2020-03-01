package labProjectES.iss.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.apache.kafka.clients.producer.Producer;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import repository.Location;

@Service
public class IssService {
    
    private final Client client = ClientBuilder.newClient();
    private static final String URL = "https://api.wheretheiss.at/v1/satellites/25544";
    
    private static final Logger logger = LoggerFactory.getLogger(IssService.class);
    private static final String TOPIC = "iss";
    
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    
    public Location getLocation(){
        JSONObject data = client
                            .target(URL)
                            .request(MediaType.APPLICATION_JSON)
                            .get(JSONObject.class);
                
        Location location = new Location((Double)data.get("latitude"), (Double)data.get("longitude"), (Double)data.get("altitude"));
        
        this.log(location.toString());
        
        return location;
    }
    
    public void log(String message){
        message = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()) + " : " + message;
        logger.info(String.format("%s",message));
        //this.kafkaTemplate.send(TOPIC,message);
    }
    
}

