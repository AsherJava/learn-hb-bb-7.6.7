/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.excel.ISheetDescriptor
 */
package com.jiuqi.bi.quickreport.engine.export;

import com.jiuqi.bi.quickreport.engine.export.SheetDescriptor;
import com.jiuqi.bi.quickreport.engine.result.SheetData;
import com.jiuqi.bi.quickreport.print.ExcelInfo;
import com.jiuqi.bi.syntax.excel.ISheetDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class SheetIterator
implements Iterator<ISheetDescriptor> {
    private List<SheetData> sheetDatas;
    private int index;
    private ExcelInfo excelInfo;

    public SheetIterator(SheetData primaryData, ExcelInfo excelInfo) {
        this.sheetDatas = new ArrayList<SheetData>(1);
        this.sheetDatas.add(primaryData);
        this.index = 0;
        this.excelInfo = excelInfo;
    }

    public SheetIterator(List<SheetData> sheetDatas, ExcelInfo excelInfo) {
        this.sheetDatas = sheetDatas;
        this.index = 0;
        this.excelInfo = excelInfo;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.sheetDatas.size();
    }

    @Override
    public ISheetDescriptor next() {
        SheetData sheetData = this.sheetDatas.get(this.index);
        ++this.index;
        return new SheetDescriptor(sheetData.getSheetName(), sheetData.getGridData(), this.excelInfo, sheetData.getPrintSetting());
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

