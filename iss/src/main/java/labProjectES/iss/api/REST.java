package labProjectES.iss.api;

import labProjectES.iss.service.IssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import labProjectES.iss.repository.Location;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("api")
public class REST {
    
    @Autowired
    IssService iss;
    
    @GetMapping(value="/iss/location")
    public Location weather() {
        return iss.getLatestLocation();
    }

}