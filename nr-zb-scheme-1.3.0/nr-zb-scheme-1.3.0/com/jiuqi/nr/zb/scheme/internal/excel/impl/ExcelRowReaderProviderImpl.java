/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.excel.impl;

import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowReader;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowReaderProvider;
import com.jiuqi.nr.zb.scheme.internal.excel.impl.ExcelRowWrapperImpl;
import com.jiuqi.nr.zb.scheme.internal.excel.impl.GroupExcelRowReader;
import com.jiuqi.nr.zb.scheme.internal.excel.impl.ZbExcelRowReader;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class ExcelRowReaderProviderImpl
implements IExcelRowReaderProvider {
    private int groupIndex = -1;
    private final IExcelRowReader zbReader;
    private final IExcelRowReader groupReader;
    private final IExcelRowReader defaultReader = (rowNum, headers, row) -> {
        ExcelRowWrapperImpl wrapper = new ExcelRowWrapperImpl();
        wrapper.setRowNum(rowNum);
        wrapper.addError("\u662f\u5426\u4e3a\u5206\u7ec4\u5fc5\u586b");
        return wrapper;
    };

    public ExcelRowReaderProviderImpl(List<PropInfo> propInfos, Map<String, ZbInfo> oldInfoMap, String schemeKey, String versionKey) {
        this.zbReader = new ZbExcelRowReader(propInfos, oldInfoMap, schemeKey, versionKey);
        this.groupReader = new GroupExcelRowReader(schemeKey, versionKey);
    }

    @Override
    public IExcelRowReader getExcelRowReader(Row row, Map<String, Short> headers) {
        Cell cell;
        if (this.groupIndex == -1) {
            Short index = headers.get("\u662f\u5426\u4e3a\u5206\u7ec4");
            if (index == null) {
                throw new RuntimeException("excel\u4e0d\u5b58\u5728\uff1a\u662f\u5426\u4e3a\u5206\u7ec4");
            }
            this.groupIndex = index.shortValue();
        }
        if ((cell = row.getCell(this.groupIndex)) != null && cell.getCellType() == CellType.STRING) {
            String value = cell.getStringCellValue();
            if ("\u662f".equals(value)) {
                return this.groupReader;
            }
            if ("\u5426".equals(value)) {
                return this.zbReader;
            }
        }
        return this.defaultReader;
    }
}

