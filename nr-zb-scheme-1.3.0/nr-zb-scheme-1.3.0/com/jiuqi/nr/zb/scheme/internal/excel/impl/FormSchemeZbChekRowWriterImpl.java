/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.excel.impl;

import com.jiuqi.nr.zb.scheme.internal.dto.ZbCheckItemDTO;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

public class FormSchemeZbChekRowWriterImpl
implements IExcelRowWriter {
    private static final Map<String, BiConsumer<ZbCheckItemDTO, Cell>> map = new HashMap<String, BiConsumer<ZbCheckItemDTO, Cell>>();
    private final CellStyle cellStyle;

    public FormSchemeZbChekRowWriterImpl(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    @Override
    public void write(IExcelRowWrapper rowWrapper, List<String> headers, Row row) {
        ZbCheckItemDTO zbCheckItemDTO = (ZbCheckItemDTO)rowWrapper.getData();
        for (int i = 0; i < headers.size(); ++i) {
            Cell cell = row.createCell(i);
            BiConsumer<ZbCheckItemDTO, Cell> zbCheckItemBiConsumer = map.get(headers.get(i));
            if (zbCheckItemBiConsumer == null) continue;
            zbCheckItemBiConsumer.accept(zbCheckItemDTO, cell);
            cell.setCellStyle(this.cellStyle);
        }
    }

    static {
        map.put("\u6240\u5c5e\u62a5\u8868", (checkItem, cell) -> cell.setCellValue(checkItem.getFormCode() + "|" + checkItem.getFormTitle()));
        map.put("\u6307\u6807\u4ee3\u7801", (checkItem, cell) -> cell.setCellValue(checkItem.getFormatCode()));
        map.put("\u6307\u6807\u540d\u79f0", (checkItem, cell) -> cell.setCellValue(checkItem.getFormatTitle()));
        map.put("\u6570\u636e\u7c7b\u578b", (checkItem, cell) -> cell.setCellValue(checkItem.getFormatDataType()));
        map.put("\u957f\u5ea6/\u7cbe\u5ea6", (checkItem, cell) -> cell.setCellValue(Integer.parseInt(checkItem.getFormatPrecision())));
        map.put("\u5c0f\u6570\u4f4d", (checkItem, cell) -> cell.setCellValue(Integer.parseInt(checkItem.getFormatDecimal())));
        map.put("\u6765\u6e90\u6570\u636e\u65b9\u6848", (checkItem, cell) -> cell.setCellValue(checkItem.getPath()));
        map.put("\u5dee\u5f02\u7c7b\u578b", (checkItem, cell) -> cell.setCellValue(checkItem.getFormatDiffType()));
        map.put("\u64cd\u4f5c", (checkItem, cell) -> cell.setCellValue(checkItem.getOperationType().getTitle()));
    }
}

