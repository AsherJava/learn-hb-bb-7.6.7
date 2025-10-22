/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.nr.jtable.params.base.CellExpression;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Cell {
    private int x;
    private int y;
    private List<CellExpression> cellExpressions;
    private Set<String> Order;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.cellExpressions = new ArrayList<CellExpression>();
        this.Order = new LinkedHashSet<String>();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public List<CellExpression> getCellExpressions() {
        return this.cellExpressions;
    }

    public void AddCellExpression(CellExpression cellExpression) {
        if (this.Order.contains(cellExpression.getOrder())) {
            return;
        }
        this.Order.add(cellExpression.getOrder());
        this.cellExpressions.add(cellExpression);
        this.cellExpressions.sort((b1, b2) -> {
            String t1 = b1.getOrder();
            String t2 = b2.getOrder();
            return t2.compareTo(t1);
        });
    }
}

