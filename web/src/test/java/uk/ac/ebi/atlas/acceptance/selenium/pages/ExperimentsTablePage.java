/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.acceptance.selenium.pages;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class ExperimentsTablePage extends AtlasPage{

    private static final String DEFAULT_PAGE_URI = "/gxa/experiments";

    private static final String TABLE_HEADERS_XPATH = "thead/tr/th";

    private static final String ROW_CELLS_XPATH_TEMPLATE = "tbody/tr[%d]/td";
    private static final String LAST_ROW_CELLS_XPATH = "tbody/tr[last()]/td";

    @FindBy(id = "experiments-table_info")
    private WebElement experimentsTableInfo;

    @FindBy(xpath = "//div[@id='experiments-table_filter']/label/input")
    private WebElement searchField;

    @FindBy(xpath = "//thead/tr[1]/th[1]")
    private WebElement firstColumnHeader;

    @FindBy(xpath = "//thead/tr[1]/th[2]")
    private WebElement secondColumnHeader;

    @FindBy(xpath = "//thead/tr[1]/th[4]")
    private WebElement fourthColumnHeader;

    public ExperimentsTablePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

    public void clickFirstColumnHeader() {
        firstColumnHeader.click();
    }

    public void clickSecondColumnHeader() {
        secondColumnHeader.click();
    }

    public void clickFourthColumnHeader() {
        fourthColumnHeader.click();
    }

    public String getSearchFieldValue() {
        return searchField.getAttribute("value");
    }

    public void setSearchFieldValue(String value) {
        searchField.click();
        searchField.sendKeys(value);
    }

    protected List<String> getTableHeaders(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(TABLE_HEADERS_XPATH));
        return toStrings(tableCells);
    }

    private List<String> toStrings(List<WebElement> tableCells){
        List<String> strings = Lists.newArrayList();
        for (WebElement webElement: tableCells){
            strings.add(webElement.getText());
        }
        return strings;
    }

    //We can't load the table at page loading time because it is ajax,
    //assertions could be executed when the page is still in an incomplete state if we did that
    private WebElement getExperimentTable(){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("tbody/tr[1]/td"),"loading..."));
        return driver.findElement(By.id("experiments-table"));
    }

    public List<String> getExperimentsTableHeader() {
        return getTableHeaders(getExperimentTable());
    }

    public List<String> getFirstExperimentInfo() {
        return getRowValues(getExperimentTable(), 1);
    }

    public List<String> getLastExperimentInfo() {
        return getLastRowValues(getExperimentTable());
    }

    public String getExperimentsTableInfo() {
        return experimentsTableInfo.getText();
    }

    protected List<String> getRowValues(WebElement table, int oneBasedRowIndex) {
        List<WebElement> tableCells = getRow(table, oneBasedRowIndex);
        return toStrings(tableCells);
    }

    protected List<String> getLastRowValues(WebElement table) {
        List<WebElement> tableCells = table.findElements(By.xpath(LAST_ROW_CELLS_XPATH));
        return toStrings(tableCells);
    }

    protected List<WebElement> getRow(WebElement table, int oneBasedRowIndex) {
        String xPath = String.format(ROW_CELLS_XPATH_TEMPLATE, oneBasedRowIndex);
        return table.findElements(By.xpath(xPath));
    }

}