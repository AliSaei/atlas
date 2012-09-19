package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

abstract class AtlasPage<T extends LoadableComponent<T>> extends LoadableComponent<T> {

    protected WebDriver driver;

    AtlasPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        String hostname = System.getProperty("selenium.test.host");
        if (hostname == null) {
            hostname = "localhost";
        }
        String portNumber = System.getProperty("selenium.test.portnumber");
        if (portNumber == null) {
            portNumber = "9090";
        }
        String URL = "http://" + hostname + ":" + portNumber + getPageURI();
        driver.get(URL);
    }


    protected abstract String getPageURI();

}
