/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.layout.element.Table
 */
package com.jiuqi.va.query.print.domain.tablle;

import com.itextpdf.layout.element.Table;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomTable
extends Table {
    private String id;
    private Map<Integer, Float> everyRowMaxHeightMap = new HashMap<Integer, Float>();

    public CustomTable(float[] columnWidths, boolean largeTable) {
        super(columnWidths, largeTable);
    }

    public CustomTable(float[] pointColumnWidths) {
        super(pointColumnWidths);
        this.id = UUID.randomUUID().toString();
    }

    public CustomTable(int numColumns, boolean largeTable) {
        super(numColumns, largeTable);
    }

    public CustomTable(int numColumns) {
        super(numColumns);
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<Integer, Float> getEveryRowMaxHeightMap() {
        return this.everyRowMaxHeightMap;
    }

    public void setEveryRowMaxHeightMap(Map<Integer, Float> everyRowMaxHeightMap) {
        this.everyRowMaxHeightMap = everyRowMaxHeightMap;
    }
}

