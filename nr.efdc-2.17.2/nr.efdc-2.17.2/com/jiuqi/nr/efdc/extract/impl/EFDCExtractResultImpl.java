/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.extract.impl;

import com.jiuqi.nr.efdc.extract.ExtractDataRow;
import com.jiuqi.nr.efdc.extract.IExtractResult;
import java.util.ArrayList;
import java.util.List;

public class EFDCExtractResultImpl
implements IExtractResult {
    private List<ExtractDataRow> dataRows = new ArrayList<ExtractDataRow>();

    @Override
    public ExtractDataRow getRow(int rowIndex) {
        return this.dataRows.get(rowIndex);
    }

    @Override
    public int size() {
        return this.dataRows.size();
    }

    public void addRow(ExtractDataRow row) {
        this.dataRows.add(row);
    }
}

