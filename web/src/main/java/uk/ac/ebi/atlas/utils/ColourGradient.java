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

package uk.ac.ebi.atlas.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.awt.*;

import static java.lang.Double.parseDouble;

@Named("colourGradient")
@Scope("prototype")
public class ColourGradient {

    protected static final double SCALE_LOGARITHMIC = 0.3;

    protected static final double SCALE_LINEAR = 1.0;

    protected static final double SCALE_EXPONENTIAL = 3;

    // Heat map colour settings.
    private Color defaultHighValueColour;
    private Color defaultLowValueColour;
    private Color blankValueColour;

    private double colourScale;

    @Inject
    public ColourGradient(@Value("#{configuration['gradient.startColour']}") Color startColour,
                          @Value("#{configuration['gradient.endColour']}") Color endColour,
                          @Value("#{configuration['gradient.blankColour']}") Color blankColour,
                          @Value("#{configuration['gradient.colourScale']}") double colourScale) {

        this.defaultLowValueColour = startColour;
        this.defaultHighValueColour = endColour;
        this.blankValueColour = blankColour;
        this.colourScale = colourScale;
    }

    public String getGradientColour(double value, double min, double max) {
        return colorToHexString(getGradientColour(value, min, max, defaultLowValueColour, defaultHighValueColour));
    }

    public String getGradientColour(double value, double min, double max, String lowValueColourName, String highValueColourName) {

        Color lowValueColour = getColourByName(lowValueColourName);
        Color highValueColour = getColourByName(highValueColourName);

        return colorToHexString(getGradientColour(value, min, max, lowValueColour, highValueColour));
    }

    public Color getColourByName(String colourName){
        try {
            return (Color)Color.class.getField(colourName).get(null);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("NoSuchFieldException during the identification of colour: " + colourName);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("IllegalAccessException during the identification of colour: " + colourName);
        }
    }

    public String getHexByColourName(String colourName) {
        return colorToHexString(getColourByName(colourName));
    }

    /*
    * Determines what colour a heat map cell should be based upon the cell
    * values.
    */
    protected Color getGradientColour(double value, double min, double max, Color lowValueColour, Color highValueColour) {

        if (value == max) {
            return highValueColour;
        }

        double percentPosition = calculatePercentPosition(value, min, max);

        if (Double.isNaN(percentPosition) || Double.isInfinite(percentPosition)) {
            return blankValueColour;
        }

        // Which colour group does that put us in.
        int colourPosition = getColourPosition(percentPosition, lowValueColour, highValueColour);

        return calculateColorForPosition(colourPosition, lowValueColour, highValueColour);
    }


    /**
     * Calculates what proportion of the way through the possible values is that.
     *
     * @param data
     * @param min
     * @param max
     * @return values in range [0..1]
     */
    protected double calculatePercentPosition(double data, double min, double max) {
        double range = max - min;
        double position = data - min;

        return position / range;
    }

    protected Color calculateColorForPosition(int colourPosition, Color lowValueColour, Color highValueColour) {
        int r = lowValueColour.getRed();
        int g = lowValueColour.getGreen();
        int b = lowValueColour.getBlue();

        // Make n shifts of the colour, where n is the colourPosition.
        for (int i = 0; i < colourPosition; i++) {
            int rDistance = r - highValueColour.getRed();
            int gDistance = g - highValueColour.getGreen();
            int bDistance = b - highValueColour.getBlue();

            if ((Math.abs(rDistance) >= Math.abs(gDistance))
                    && (Math.abs(rDistance) >= Math.abs(bDistance))) {
                // Red must be the largest.
                r = updateColourValue(r, rDistance);
            } else if (Math.abs(gDistance) >= Math.abs(bDistance)) {
                // Green must be the largest.
                g = updateColourValue(g, gDistance);
            } else {
                // Blue must be the largest.
                b = updateColourValue(b, bDistance);
            }
        }

        return new Color(r, g, b);
    }

    /*
    * Returns how many colour shifts are required from the lowValueColour to
    * get to the correct colour position. The result will be different
    * depending on the colour scale used: LINEAR, LOGARITHMIC, EXPONENTIAL.
    */

    protected int getColourPosition(double percentPosition, Color lowValueColour, Color highValueColour) {
        Preconditions.checkArgument(percentPosition >= 0 && percentPosition <= 1);

        int colourDistance = calculateColourDistance(lowValueColour, highValueColour);

        return (int) Math.round(colourDistance * Math.pow(percentPosition, colourScale));
    }

    protected int updateColourValue(int colourValue, int colourDistance) {

        if (colourDistance < 0) {
            return colourValue + 1;
        } else if (colourDistance > 0) {
            return colourValue - 1;
        } else {
            // This shouldn't actually happen here.
            return colourValue;
        }
    }

    /*
    * Calculate and update the field for the distance between the low colour
    * and high colour. The distance is the number of steps between one colour
    * and the other using an RGB coding with 0-255 values for each of red,
    * green and blue. So the maximum colour distance is 255 + 255 + 255.
    */
    protected int calculateColourDistance(Color lowValueColour, Color highValueColour) {
        int r1 = lowValueColour.getRed();
        int g1 = lowValueColour.getGreen();
        int b1 = lowValueColour.getBlue();
        int r2 = highValueColour.getRed();
        int g2 = highValueColour.getGreen();
        int b2 = highValueColour.getBlue();

        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }

    protected String colorToHexString(Color colour) {
        return "#" + Integer.toHexString(colour.getRGB()).substring(2).toUpperCase();
    }
}

