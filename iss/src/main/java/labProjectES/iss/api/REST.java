/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labProjectES.iss.api;

import labProjectES.iss.service.IssService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author joaoalegria
 */
@RestController
@RequestMapping("api")
public class REST {
    
    @Autowired
    IssService is;
    
    /**
     * 
     * @return 
     */
    @GetMapping(value="/iss/location")
    public JSONObject weather() {
        return is.getLocation();
    }

}