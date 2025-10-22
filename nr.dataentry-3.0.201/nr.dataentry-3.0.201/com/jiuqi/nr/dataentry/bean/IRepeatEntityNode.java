/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentity_ext.dto.IEntityDataRow
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentity_ext.dto.IEntityDataRow;
import com.jiuqi.nr.dataentry.bean.ISingleUnitInfo;
import java.math.BigDecimal;
import java.util.List;

public class IRepeatEntityNode
extends ISingleUnitInfo
implements IEntityDataRow {
    private static final long serialVersionUID = -7706408068369576324L;
    private String netCode;
    private String netTitle;
    private String netQYDM;
    private String netBBLX;
    private String netPeriod;
    private String netParent;
    private String netMapCode;
    private List<String> path;
    private int repeatMode;
    private boolean isLeaf;

    public void setLeaf(boolean leaf) {
        this.isLeaf = leaf;
    }

    public List<String> getPath() {
        return this.path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public String getNetCode() {
        return this.netCode;
    }

    public void setNetCode(String netCode) {
        this.netCode = netCode;
    }

    public String getNetTitle() {
        return this.netTitle;
    }

    public void setNetTitle(String netTitle) {
        this.netTitle = netTitle;
    }

    public String getNetQYDM() {
        return this.netQYDM;
    }

    public void setNetQYDM(String netQYDM) {
        this.netQYDM = netQYDM;
    }

    public String getNetBBLX() {
        return this.netBBLX;
    }

    public void setNetBBLX(String netBBLX) {
        this.netBBLX = netBBLX;
    }

    public int getRepeatMode() {
        return this.repeatMode;
    }

    public void setRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
    }

    public String getNetPeriod() {
        return this.netPeriod;
    }

    public void setNetPeriod(String netPeriod) {
        this.netPeriod = netPeriod;
    }

    public String getNetParent() {
        return this.netParent;
    }

    public void setNetParent(String netParent) {
        this.netParent = netParent;
    }

    public String getNetMapCode() {
        return this.netMapCode;
    }

    public void setNetMapCode(String netMapCode) {
        this.netMapCode = netMapCode;
    }

    public String getKey() {
        return this.getSingleZdm();
    }

    public String getCode() {
        return this.getSingleCode();
    }

    public String getTitle() {
        return this.getSingleTitle();
    }

    public String getParent() {
        return this.getSingleParent();
    }

    public BigDecimal getOrder() {
        return null;
    }
}

