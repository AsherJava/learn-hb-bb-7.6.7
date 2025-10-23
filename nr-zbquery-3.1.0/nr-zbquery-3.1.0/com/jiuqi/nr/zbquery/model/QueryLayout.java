/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nr.zbquery.model.LayoutField;
import java.util.ArrayList;
import java.util.List;

public class QueryLayout {
    private List<LayoutField> rows = new ArrayList<LayoutField>();
    private List<LayoutField> cols = new ArrayList<LayoutField>();
    private boolean zbVertical = false;

    public List<LayoutField> getRows() {
        return this.rows;
    }

    public void setRows(List<LayoutField> rows) {
        this.rows = rows;
    }

    public List<LayoutField> getCols() {
        return this.cols;
    }

    public void setCols(List<LayoutField> cols) {
        this.cols = cols;
    }

    public boolean isZbVertical() {
        return this.zbVertical;
    }

    public void setZbVertical(boolean zbVertical) {
        this.zbVertical = zbVertical;
    }
}

