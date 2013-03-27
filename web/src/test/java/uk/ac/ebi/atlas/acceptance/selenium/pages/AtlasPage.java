package uk.ac.ebi.atlas.acceptance.selenium.pages;

import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import uk.ac.ebi.atlas.acceptance.utils.URLBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

public abstract class AtlasPage extends LoadableComponent<AtlasPage> {

    protected WebDriver driver;
    private String httpParameters;

    private URLBuilder urlBuilder;

    private String pageURL;

    public AtlasPage(WebDriver driver) {
        this(driver, null);
    }

    AtlasPage(WebDriver driver, String httpParameters) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(StringUtils.isNotBlank(httpParameters)){
            this.httpParameters = httpParameters;
        }
    }

    @Override
    protected void load() {
        urlBuilder = new URLBuilder(getPageURI());
        pageURL = urlBuilder.buildURL(httpParameters);
        driver.get(pageURL);
    }

    protected abstract String getPageURI();

    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertThat(url, endsWith(Joiner.on("?").skipNulls().join(getPageURI(), httpParameters)));

    }
}
