
package uk.ac.ebi.atlas.controllers.rest.admin;

import uk.ac.ebi.atlas.trader.ExperimentTrader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;

@Controller
@Scope("request")
@RequestMapping("/admin")
public class ExperimentAdminDeprecatedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentAdminDeprecatedController.class);

    private ExperimentTrader trader;

    @Inject
    public ExperimentAdminDeprecatedController(ExperimentTrader trader) {
        this.trader = trader;
    }

    @RequestMapping(value = "/invalidateExperimentCache", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String invalidateExperimentCache() throws IOException {
        trader.removeAllExperimentsFromCache();
        return "All experiments removed from cache";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(Exception e) throws IOException {
        String lineSeparator = "<br>";
        LOGGER.error(e.getMessage(), e);

        StringBuilder sb = new StringBuilder();
        sb.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(lineSeparator);

        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString()).append(lineSeparator);
        }

        return sb.toString();
    }

}