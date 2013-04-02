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

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Geod26284HeatmapTablePage extends HeatmapTablePage {

    private static final String XPATH_TEMPLATE = "ul/li[%d]";

    @FindBy(xpath = "//ul[@id = 'filterBy']/li")
    private WebElement filterByMenu;

    public Geod26284HeatmapTablePage(WebDriver driver) {
        super(driver);
    }

    public Geod26284HeatmapTablePage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    @Override
    protected String getPageURI() {
        return "/gxa/experiments/E-GEOD-26284";
    }

    public String getFilterByMenuTopText() {
        return filterByMenu.getText();
    }

    public String getFilterByMenuText(int... indices) {

        // get last element in path
        WebElement lastMenuElement = extractLastMenuElement(indices);
        return lastMenuElement.getText();
    }

    public void clickFilterByMenuElement(int... indices) {

        // get last element in path
        WebElement lastMenuElement = extractLastMenuElement(indices);
        lastMenuElement.click();
    }

    protected List<WebElement> getWebElementPath(List<WebElement> list, WebElement element, int[] indices) {

        list.add(element);

        // for xpath indices seem to start at 1
        String xpath = String.format(XPATH_TEMPLATE, indices[0] + 1);
        WebElement childElement = element.findElement(By.xpath(xpath));

        // base case
        if (indices.length == 1) {
            list.add(childElement);
            return list;
        }

        // recursion case
        int[] newIndices = Arrays.copyOfRange(indices, 1, indices.length);
        return getWebElementPath(list, childElement, newIndices);
    }

    protected WebElement extractLastMenuElement(int... indices) {

        // get path to click through menu
        List<WebElement> menuElements = getWebElementPath(new ArrayList<WebElement>(), filterByMenu, indices);

        makeWholeMenuAppear();

        final WebElement lastMenuElement = menuElements.get(menuElements.size() - 1);

        // wait until jQuery has rendered the menu
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return !lastMenuElement.getText().equals("");
            }
        });

        // the last menu item does not use hyperlink
        if (lastMenuElement.getAttribute("data-serialized-factors") == null) {
            return lastMenuElement.findElement(By.xpath("a"));
        }
        return lastMenuElement;
    }

    protected void makeWholeMenuAppear() {
        // make whole menu appear
        ((JavascriptExecutor) driver).executeScript("$(\"ul:hidden\").show();");
    }
}