package uk.ac.ebi.atlas.web;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RedirectResourcesController {

    @Inject
    private Environment props;

    @RequestMapping(value = "/resources/**",
            method = RequestMethod.GET)
    public String redirectResources(HttpServletRequest request){
        return "redirect:"
                +request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString()
                .replace("/resources", "/resources-" + props.getProperty("projectVersion"));
    }
}
