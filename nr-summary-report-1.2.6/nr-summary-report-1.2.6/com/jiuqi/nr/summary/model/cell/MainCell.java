/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.cell;

import com.jiuqi.nr.summary.model.caliber.CaliberInfo;
import com.jiuqi.nr.summary.model.cell.SummaryZb;
import java.io.Serializable;
import java.util.List;

public class MainCell
implements Serializable {
    private int x;
    private int y;
    private List<CaliberInfo> caliberInfos;
    private SummaryZb innerDimZb;

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

    public List<CaliberInfo> getCaliberInfos() {
        return this.caliberInfos;
    }

    public void setCaliberInfos(List<CaliberInfo> caliberInfos) {
        this.caliberInfos = caliberInfos;
    }

    public SummaryZb getInnerDimZb() {
        return this.innerDimZb;
    }

    public void setInnerDimZb(SummaryZb innerDimZb) {
        this.innerDimZb = innerDimZb;
    }
}

