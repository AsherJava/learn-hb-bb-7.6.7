/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.nr.jtable.params.base.Cell;
import com.jiuqi.nr.jtable.params.base.TargetStyle;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class CStyleFile
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String StyleFormulaJS;
    private HashMap<String, TargetStyle> TargetByDataLink = new HashMap();
    private Set<String> otherFormDataLink = new LinkedHashSet<String>();
    private Set<String> otherDataLink;
    private HashMap<String, Cell> periodCells = new HashMap();

    public String getStyleFormulaJS() {
        return this.StyleFormulaJS;
    }

    public HashMap<String, Cell> getPeriodCells() {
        return this.periodCells;
    }

    public Set<String> getOtherDataLink() {
        return this.otherDataLink;
    }

    public void addOtherDataLink(String datalink) {
        this.otherDataLink.add(datalink);
    }

    public void AddPeriodCell(Cell cell) {
        String cellKey = cell.getX() + "_" + cell.getY();
        this.periodCells.put(cellKey, cell);
    }

    public Set<String> getOtherFormDataLink() {
        return this.otherFormDataLink;
    }

    public void AddOtherFormDataLink(String datalink) {
        this.otherFormDataLink.add(datalink);
    }

    public CStyleFile() {
        this.otherDataLink = new LinkedHashSet<String>();
    }

    public void setStyleFormulaJS(String styleFormulaJS) {
        this.StyleFormulaJS = styleFormulaJS;
    }

    public HashMap<String, TargetStyle> getTargetByDtaLink() {
        return this.TargetByDataLink;
    }

    public void AddTargetByDataLink(String datalink, TargetStyle targetStyle) {
        if (this.TargetByDataLink.containsKey(datalink)) {
            this.TargetByDataLink.get(datalink).AddCell(targetStyle);
        } else {
            this.TargetByDataLink.put(datalink, targetStyle);
        }
    }
}

