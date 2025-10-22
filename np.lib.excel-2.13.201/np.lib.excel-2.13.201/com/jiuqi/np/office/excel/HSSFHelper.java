/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.GridColor
 */
package com.jiuqi.np.office.excel;

import com.jiuqi.np.grid.GridColor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.util.HSSFColor;

public class HSSFHelper {
    private static final int[] WIN32_COLORS = new int[]{0xC8C8C8, 0, 13743257, 14405055, 0xF0F0F0, 0xFFFFFF, 0x646464, 0, 0, 0, 0xB4B4B4, 16578548, 0xABABAB, 0xFF9933, 0xFFFFFF, 0xF0F0F0, 0xA0A0A0, 0x808080, 0, 5525059, 0xFFFFFF, 0x696969, 0xE3E3E3, 0, 0xE1FFFF, 0, 0xCC6600, 15389113, 15918295, 0xFF9933, 0xF0F0F0, 0};
    private static List colorElements = new ArrayList();
    private static Map colorFinder = new Hashtable();

    private HSSFHelper() {
    }

    public static int pixelToWidth(int value) {
        if (value <= 0) {
            return 0;
        }
        if (value == 1) {
            return 19;
        }
        int width = 19;
        width += --value / 7 * 256;
        int delta = value % 7;
        if (delta > 0) {
            width += delta / 2 * 73;
            if (delta % 2 == 1) {
                width += 37;
            }
        }
        return width;
    }

    public static int widthToPixel(short value) {
        return (short)(value / 32);
    }

    public static short pixelToHeight(int value) {
        if (value <= 0) {
            return 0;
        }
        int height = 7 + (value - 1) * 15;
        return (short)height;
    }

    public static int heightToPixel(short value) {
        return value / 15;
    }

    public static HSSFColor getApproximateColor(int rgbColor) {
        Integer key = new Integer(rgbColor);
        HSSFColor retColor = (HSSFColor)colorFinder.get(key);
        if (retColor == null && (retColor = HSSFHelper.findApproximateColor(rgbColor)) != null) {
            colorFinder.put(key, retColor);
        }
        return retColor;
    }

    public static HSSFColor getApproximateTextColor(int rgbColor) {
        if (rgbColor == -16777211) {
            rgbColor = 0;
        }
        return HSSFHelper.getApproximateColor(rgbColor);
    }

    public static HSSFColor getApproximateEdgeColor(int rgbColor) {
        if (rgbColor == -16777201) {
            rgbColor = 0x808080;
        }
        return HSSFHelper.getApproximateColor(rgbColor);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void buildColorList() {
        List list = colorElements;
        synchronized (list) {
            if (colorElements.size() == 0) {
                Map<Integer, HSSFColor> colorMaps = HSSFColor.getIndexHash();
                Collection<HSSFColor> colorList = colorMaps.values();
                Iterator<HSSFColor> colorItr = colorList.iterator();
                while (colorItr.hasNext()) {
                    ColorElement element = new ColorElement();
                    element.color = colorItr.next();
                    short[] rgbVals = element.color.getTriplet();
                    element.rgb = HSSFHelper.rgbToColor(rgbVals[0], rgbVals[1], rgbVals[2]);
                    element.hsb = HSSFHelper.color2HSB(element.rgb);
                    colorElements.add(element);
                    colorFinder.put(new Integer(element.rgb), element.color);
                }
            }
        }
    }

    private static HSSFColor findApproximateColor(int rgbColor) {
        HSSFHelper.buildColorList();
        HSB Hsb = HSSFHelper.color2HSB(rgbColor);
        double L = Math.sqrt(20000.0);
        ColorElement flagElement = null;
        for (int i = 0; i < colorElements.size(); ++i) {
            double E;
            ColorElement elem = (ColorElement)colorElements.get(i);
            if (rgbColor == elem.rgb) {
                return elem.color;
            }
            double Q = Hsb.h - elem.hsb.h;
            if (Q > 180.0) {
                Q = 360.0 - Q;
            }
            if (!(L > (E = Math.sqrt(Math.pow(elem.hsb.s - Hsb.s, 2.0) + Math.pow(elem.hsb.b - Hsb.b * Math.cos(Q * Math.PI / 180.0), 2.0) + Math.pow(Hsb.b * Math.sin(Q * Math.PI / 180.0), 2.0))))) continue;
            L = E;
            flagElement = elem;
        }
        if (flagElement == null) {
            return null;
        }
        return flagElement.color;
    }

    private static int rgbToColor(int r, int g, int b) {
        return (r <<= 16) | (g <<= 8) | b;
    }

    private static HSB color2HSB(int value) {
        HSB hsb = new HSB();
        RGB rgb = HSSFHelper.colorToRGB(value);
        double M0 = (double)HSSFHelper.maxColorValue(rgb) / 2.55;
        hsb.setDefault();
        hsb.b = M0;
        if (hsb.b == 0.0) {
            hsb.s = 0.0;
        } else {
            double M1 = (double)HSSFHelper.minColorValue(rgb) / 2.55;
            hsb.s = (M0 - M1) * 100.0 / M0;
        }
        hsb.h = HSSFHelper.RGBToHue(rgb);
        return hsb;
    }

    private static RGB colorToRGB(int value) {
        int p;
        if (value < 0 && (p = value & 0xFF) < WIN32_COLORS.length) {
            value = WIN32_COLORS[p];
        }
        int r = (value & 0xFF0000) >> 16;
        int g = (value & 0xFF00) >> 8;
        int b = value & 0xFF;
        return new RGB(r, g, b);
    }

    private static double RGBToHue(RGB rgb) {
        double H;
        int M2;
        int M0 = HSSFHelper.minColorValue(rgb);
        if (M0 == (M2 = HSSFHelper.maxColorValue(rgb))) {
            return 0.0;
        }
        int M1 = HSSFHelper.centerColorValue(rgb);
        if (M2 == M1) {
            if (rgb.r == rgb.g) {
                return 60.0;
            }
            if (rgb.g == rgb.b) {
                return 180.0;
            }
            return 300.0;
        }
        double F = 60 * (M1 - M0) / (M2 - M0);
        if (rgb.r == M2) {
            H = rgb.g < rgb.b ? -F : F;
        } else if (rgb.g == M2) {
            H = rgb.b < rgb.r ? -F : F;
            H += 120.0;
        } else {
            H = rgb.r < rgb.g ? -F : F;
            H += 240.0;
        }
        if (H < 0.0) {
            H += 360.0;
        }
        return H;
    }

    private static int maxColorValue(RGB rgb) {
        int max = rgb.r;
        if (rgb.g > max) {
            max = rgb.g;
        }
        if (rgb.b > max) {
            max = rgb.b;
        }
        return max;
    }

    private static int minColorValue(RGB rgb) {
        int min = rgb.r;
        if (rgb.g < min) {
            min = rgb.g;
        }
        if (rgb.b < min) {
            min = rgb.b;
        }
        return min;
    }

    private static int centerColorValue(RGB rgb) {
        if (rgb.r < rgb.g && rgb.r < rgb.b) {
            if (rgb.g < rgb.b) {
                return rgb.g;
            }
            return rgb.b;
        }
        if (rgb.g < rgb.r && rgb.g < rgb.b) {
            if (rgb.r < rgb.b) {
                return rgb.r;
            }
            return rgb.b;
        }
        if (rgb.r < rgb.g) {
            return rgb.r;
        }
        return rgb.g;
    }

    static {
        colorFinder.put(new Integer(GridColor.HEADCELL.value()), HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getColor());
        colorFinder.put(new Integer(GridColor.DARK_GREEN.value()), HSSFColor.HSSFColorPredefined.GREEN.getColor());
        colorFinder.put(new Integer(GridColor.BLACKISH_GREEN.value()), HSSFColor.HSSFColorPredefined.GREEN.getColor());
        colorFinder.put(new Integer(GridColor.WHITE.value()), HSSFColor.HSSFColorPredefined.WHITE.getColor());
        colorFinder.put(new Integer(GridColor.BLACK.value()), HSSFColor.HSSFColorPredefined.BLACK.getColor());
    }

    protected static class ColorElement {
        public int rgb;
        public HSB hsb;
        public HSSFColor color;

        protected ColorElement() {
        }
    }

    protected static class HSB {
        public double h;
        public double s;
        public double b;

        protected HSB() {
        }

        public void setDefault() {
            this.h = 65535.0;
            this.s = 0.0;
            this.b = 0.0;
        }
    }

    private static class RGB {
        public int r;
        public int g;
        public int b;

        public RGB(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }
}

