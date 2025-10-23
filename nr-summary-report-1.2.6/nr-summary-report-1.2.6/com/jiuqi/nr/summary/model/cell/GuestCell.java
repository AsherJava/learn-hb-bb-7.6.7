/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 */
package com.jiuqi.nr.summary.model.cell;

import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.summary.model.caliber.CaliberInfo;
import com.jiuqi.nr.summary.model.cell.ZbProperty;
import java.io.Serializable;
import java.util.List;

public class GuestCell
implements Serializable {
    private int x;
    private int y;
    private int refy = -1;
    private List<CaliberInfo> caliberInfos;
    private ZbProperty property;
    private String exp;
    private String expTitle;
    private DataFieldGatherType summaryMode;

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRefy() {
        return this.refy;
    }

    public void setRefy(int refy) {
        this.refy = refy;
    }

    public List<CaliberInfo> getCaliberInfos() {
        return this.caliberInfos;
    }

    public void setCaliberInfos(List<CaliberInfo> caliberInfos) {
        this.caliberInfos = caliberInfos;
    }

    public ZbProperty getProperty() {
        return this.property;
    }

    public void setProperty(ZbProperty property) {
        this.property = property;
    }

    public String getExp() {
        return this.exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getExpTitle() {
        return this.expTitle;
    }

    public void setExpTitle(String expTitle) {
        this.expTitle = expTitle;
    }

    public DataFieldGatherType getSummaryMode() {
        return this.summaryMode;
    }

    public void setSummaryMode(DataFieldGatherType summaryMode) {
        this.summaryMode = summaryMode;
    }
}

