/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package com.jiuqi.nr.data.excel.export.fml;

import com.jiuqi.nr.definition.common.DataRegionKind;

public class FmlNode {
    private String dataLinkKey;
    private int posX;
    private int posY;
    private String formKey;
    private String regionKey;
    private DataRegionKind dataRegionKind;

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public DataRegionKind getDataRegionKind() {
        return this.dataRegionKind;
    }

    public void setDataRegionKind(DataRegionKind dataRegionKind) {
        this.dataRegionKind = dataRegionKind;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

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

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }
}

