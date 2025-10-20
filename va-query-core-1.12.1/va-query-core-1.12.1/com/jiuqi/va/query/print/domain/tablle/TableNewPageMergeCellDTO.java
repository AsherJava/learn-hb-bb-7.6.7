/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.print.domain.tablle;

import com.jiuqi.va.query.print.BorderDTO;
import java.util.Map;

public class TableNewPageMergeCellDTO {
    private Map<String, BorderDTO> rowColBorderMap;
    private Map<String, Float> everyColumnMaxWidthMap;

    public Map<String, BorderDTO> getRowColBorderMap() {
        return this.rowColBorderMap;
    }

    public void setRowColBorderMap(Map<String, BorderDTO> rowColBorderMap) {
        this.rowColBorderMap = rowColBorderMap;
    }

    public Map<String, Float> getEveryColumnMaxWidthMap() {
        return this.everyColumnMaxWidthMap;
    }

    public void setEveryColumnMaxWidthMap(Map<String, Float> everyColumnMaxWidthMap) {
        this.everyColumnMaxWidthMap = everyColumnMaxWidthMap;
    }
}

