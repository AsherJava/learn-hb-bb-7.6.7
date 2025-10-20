/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.excel.importexcel.common;

import java.math.BigDecimal;

public class RestType
implements Comparable<RestType> {
    private double score;
    private String name;
    private String code;

    public RestType(double score, String name, String code) {
        this.score = score;
        this.name = name;
        this.code = code;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int compareTo(RestType restType) {
        BigDecimal score1 = new BigDecimal(this.score);
        BigDecimal score2 = new BigDecimal(restType.getScore());
        return score1.compareTo(score2);
    }
}

