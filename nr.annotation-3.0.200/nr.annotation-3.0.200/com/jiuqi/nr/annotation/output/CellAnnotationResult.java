/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.output;

import com.jiuqi.nr.annotation.output.CellAnnotationContent;
import java.util.ArrayList;
import java.util.List;

public class CellAnnotationResult {
    private String dataLinkKey;
    private String rowId;
    private List<CellAnnotationContent> contents = new ArrayList<CellAnnotationContent>();

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public List<CellAnnotationContent> getContents() {
        return this.contents;
    }

    public void setContents(List<CellAnnotationContent> contents) {
        this.contents = contents;
    }
}

