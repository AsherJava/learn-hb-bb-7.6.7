/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.message;

import java.io.Serializable;

public class DifferenceData
implements Serializable {
    private static final long serialVersionUID = 2176440339519650500L;
    private String initialValue;
    private String compareValue;
    private double difference;
    private double scale;

    public DifferenceData() {
    }

    public DifferenceData(String initialValue, String compareValue, double difference, double scale) {
        this.initialValue = initialValue;
        this.compareValue = compareValue;
        this.difference = difference;
        this.scale = scale;
    }

    public String getInitialValue() {
        return this.initialValue;
    }

    public void setInitialValue(String initialValue) {
        this.initialValue = initialValue;
    }

    public String getCompareValue() {
        return this.compareValue;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

    public double getDifference() {
        return this.difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }

    public double getScale() {
        return this.scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}

