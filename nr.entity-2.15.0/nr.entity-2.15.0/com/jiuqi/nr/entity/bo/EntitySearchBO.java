/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.bo;

import java.io.Serializable;

public class EntitySearchBO
implements Serializable {
    private static final long serialVersionUID = 148694902325179783L;
    private String keyWords;
    private Integer dimensionFlag;
    private Integer calibre;

    public String getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public Integer getDimensionFlag() {
        return this.dimensionFlag;
    }

    public void setDimensionFlag(int dimensionFlag) {
        this.dimensionFlag = dimensionFlag;
    }

    public Integer getCalibre() {
        return this.calibre;
    }

    public void setCalibre(Integer calibre) {
        this.calibre = calibre;
    }
}

