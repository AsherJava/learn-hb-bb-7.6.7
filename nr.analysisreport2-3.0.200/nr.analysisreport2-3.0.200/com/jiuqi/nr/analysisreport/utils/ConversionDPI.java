/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 */
package com.jiuqi.nr.analysisreport.utils;

import java.util.List;
import org.json.JSONArray;

public class ConversionDPI {
    private Integer deviceXDPI = null;
    private Integer deviceYDPI = null;

    public ConversionDPI(String dpiString) {
        List dpi = new JSONArray(dpiString).toList();
        this.deviceXDPI = Integer.parseInt(dpi.get(0).toString());
        this.deviceYDPI = Integer.parseInt(dpi.get(1).toString());
    }

    public ConversionDPI(int x, int y) {
        this.deviceXDPI = x;
        this.deviceYDPI = y;
    }

    public Integer getDeviceXDPI() {
        return this.deviceXDPI;
    }

    public void setDeviceXDPI(Integer deviceXDPI) {
        this.deviceXDPI = deviceXDPI;
    }

    public Integer getDeviceYDPI() {
        return this.deviceYDPI;
    }

    public void setDeviceYDPI(Integer deviceYDPI) {
        this.deviceYDPI = deviceYDPI;
    }

    public int mmConvertToPx(int mm, DEVICE device) {
        Float res = Float.valueOf(new Integer(mm).floatValue() / 25.4f * (float)(device.getIndex() == 0 ? this.deviceXDPI : this.deviceYDPI).intValue());
        return res.intValue();
    }

    public int pxConvertToMm(int px, DEVICE device) {
        Float res = Float.valueOf(new Integer(px).floatValue() / (float)(device.getIndex() == 0 ? this.deviceXDPI : this.deviceYDPI).intValue() * 25.4f);
        return res.intValue();
    }

    public float mmConvertToPx(float mm, DEVICE device) {
        return mm / 25.4f * (float)(device.getIndex() == 0 ? this.deviceXDPI : this.deviceYDPI).intValue();
    }

    public float pxConvertToMm(float px, DEVICE device) {
        return px / (float)(device.getIndex() == 0 ? this.deviceXDPI : this.deviceYDPI).intValue() * 25.4f;
    }

    public double mmConvertToPx(double mm, DEVICE device) {
        return mm / 25.4 * (double)(device.getIndex() == 0 ? this.deviceXDPI : this.deviceYDPI).intValue();
    }

    public double pxConvertToMm(double px, DEVICE device) {
        return px / (double)(device.getIndex() == 0 ? this.deviceXDPI : this.deviceYDPI).intValue() * 25.4;
    }

    public static enum DEVICE {
        X(0),
        Y(1);

        private int index;

        private DEVICE(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }
    }
}

