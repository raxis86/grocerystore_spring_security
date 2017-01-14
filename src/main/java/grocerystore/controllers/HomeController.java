package grocerystore.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by raxis on 31.12.2016.
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping({"/Index","/",""})
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
}
