/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labProjectES.iss.api;

import labProjectES.iss.service.IssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author joaoalegria
 */
@Controller
public class UI {
    
    @Autowired
    IssService is;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String messages(Model model) {
        model.addAttribute("location", is.getLocation());
        return "map";
    }
}
