package uk.ac.ebi.atlas.web.controllers.rest;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

@Controller
public class ExperimentImageController {

    private static final Logger LOGGER = Logger.getLogger(ExperimentImageController.class);

    @Value("#{configuration['experiment.extra-info-image.path.template']}")
    private String extraInfoPathTemplate;

    @ResponseBody
    @RequestMapping("/experiments/{experimentAccession}/extra-info")
    public void getImage(HttpServletResponse response, @PathVariable String experimentAccession){

        try{

            String extraInfoFileLocation = MessageFormat.format(extraInfoPathTemplate, experimentAccession);

            File extraInfoFile = new File(extraInfoFileLocation);
            BufferedImage image = ImageIO.read(extraInfoFile);

            response.setContentType("image/png");
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);
            out.close();

        } catch (IOException e){
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Error loading extra info for experiment " + experimentAccession);
        }
    }
}
