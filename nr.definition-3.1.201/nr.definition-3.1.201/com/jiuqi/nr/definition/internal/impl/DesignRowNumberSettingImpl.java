/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.nr.definition.facade.DesignRowNumberSetting;
import java.io.Serializable;

public class DesignRowNumberSettingImpl
implements DesignRowNumberSetting,
Serializable {
    private static final long serialVersionUID = -8610842393192720038L;
    private int posX;
    private int posY;
    private int startNumber;
    private int increment;

    @Override
    public int getPosX() {
        return this.posX;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public int getPosY() {
        return this.posY;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public int getStartNumber() {
        return this.startNumber;
    }

    @Override
    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    @Override
    public int getIncrement() {
        return this.increment;
    }

    @Override
    public void setIncrement(int increment) {
        this.increment = increment;
    }
}

