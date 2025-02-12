package net.ilexiconn.llibrary.client.gui.element.color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iLexiconn
 * @since 1.4.0
 */
public class ColorMode {
    private static final List<ColorMode> COLOR_MODE_LIST = new ArrayList<>();

    public static final ColorMode DARK = ColorMode.create("dark", 0xFF212121, 0xFF363636, 0xFF464646, 0xFF212121, 0xFF1F1F1F, 0xFFFFFFFF, 0xFF000000);
    public static final ColorMode LIGHT = ColorMode.create("light", 0xFFCDCDCD, 0xFFACACAC, 0xFFECECEC, 0xFFCDCDCD, 0xFFC2C2C2, 0xFF000000, 0xFFFFFFFF);

    private final String name;
    private final int primaryColor;
    private final int secondaryColor;
    private final int tertiaryColor;
    private final int primarySubcolor;
    private final int secondarySubcolor;
    private final int textColor;
    private final int invertedTextColor;

    private ColorMode(String name, int primaryColor, int secondaryColor, int tertiaryColor, int primarySubcolor, int secondarySubcolor, int textColor, int invertedTextColor) {
        this.name = name;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.tertiaryColor = tertiaryColor;
        this.primarySubcolor = primarySubcolor;
        this.secondarySubcolor = secondarySubcolor;
        this.textColor = textColor;
        this.invertedTextColor = invertedTextColor;
    }

    /**
     * Create a new color mode that can be used by elements.
     *
     * @param name              the color mode name
     * @param primaryColor      the primary color
     * @param secondaryColor    the secondary color
     * @param tertiaryColor     the tertiary color
     * @param primarySubcolor   the primary subcolor
     * @param secondarySubcolor the secondary subcolor
     * @param textColor         the text color
     * @param invertedTextColor the inverted text color
     * @return the new color mode instance
     */
    public static ColorMode create(String name, int primaryColor, int secondaryColor, int tertiaryColor, int primarySubcolor, int secondarySubcolor, int textColor, int invertedTextColor) {
        ColorMode colorMode = new ColorMode(name, primaryColor, secondaryColor, tertiaryColor, primarySubcolor, secondarySubcolor, textColor, invertedTextColor);
        ColorMode.COLOR_MODE_LIST.add(colorMode);
        return colorMode;
    }

    public static ColorMode getColorMode(String name) {
        for (ColorMode colorMode : ColorMode.COLOR_MODE_LIST) {
            if (colorMode.getName().equals(name)) {
                return colorMode;
            }
        }
        return ColorMode.LIGHT;
    }

    public String getName() {
        return name;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public int getSecondaryColor() {
        return secondaryColor;
    }

    public int getTertiaryColor() {
        return tertiaryColor;
    }

    public int getPrimarySubcolor() {
        return primarySubcolor;
    }

    public int getSecondarySubcolor() {
        return secondarySubcolor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getInvertedTextColor() {
        return invertedTextColor;
    }
}

