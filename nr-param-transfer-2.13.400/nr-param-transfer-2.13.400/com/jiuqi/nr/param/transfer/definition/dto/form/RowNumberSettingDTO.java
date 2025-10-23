/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.facade.RowNumberSetting
 *  com.jiuqi.nr.definition.internal.impl.DesignRowNumberSettingImpl
 */
package com.jiuqi.nr.param.transfer.definition.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.internal.impl.DesignRowNumberSettingImpl;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RowNumberSettingDTO {
    private int posX;
    private int posY;
    private int startNumber;
    private int increment;

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getStartNumber() {
        return this.startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public int getIncrement() {
        return this.increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public static RowNumberSettingDTO valueOf(RowNumberSetting rowNumberSetting) {
        if (rowNumberSetting == null) {
            return null;
        }
        RowNumberSettingDTO rowNumber = new RowNumberSettingDTO();
        rowNumber.setPosX(rowNumberSetting.getPosX());
        rowNumber.setPosY(rowNumberSetting.getPosY());
        rowNumber.setStartNumber(rowNumberSetting.getStartNumber());
        rowNumber.setIncrement(rowNumberSetting.getIncrement());
        return rowNumber;
    }

    public RowNumberSetting value2Define() {
        DesignRowNumberSettingImpl setting = new DesignRowNumberSettingImpl();
        setting.setPosX(this.getPosX());
        setting.setPosY(this.getPosY());
        setting.setStartNumber(this.getStartNumber());
        setting.setIncrement(this.getIncrement());
        return setting;
    }
}

