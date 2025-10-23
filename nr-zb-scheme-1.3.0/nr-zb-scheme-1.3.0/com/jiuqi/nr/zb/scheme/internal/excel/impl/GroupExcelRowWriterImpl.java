/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.excel.impl;

import com.jiuqi.nr.zb.scheme.internal.dto.ZbGroupDTO;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class GroupExcelRowWriterImpl
implements IExcelRowWriter {
    private static final Map<String, BiConsumer<ZbGroupDTO, Cell>> map = new HashMap<String, BiConsumer<ZbGroupDTO, Cell>>();

    @Override
    public void write(IExcelRowWrapper rowWrapper, List<String> headers, Row row) {
        ZbGroupDTO group = (ZbGroupDTO)rowWrapper.getData();
        for (int i = 0; i < headers.size(); ++i) {
            Cell cell = row.createCell(i);
            BiConsumer<ZbGroupDTO, Cell> groupCellBiConsumer = map.get(headers.get(i));
            if (groupCellBiConsumer == null) continue;
            groupCellBiConsumer.accept(group, cell);
        }
    }

    static {
        map.put("\u540d\u79f0", (group, cell) -> cell.setCellValue(group.getTitle()));
        map.put("\u4ee3\u7801", (group, cell) -> cell.setCellValue(group.getKey()));
        map.put("\u7236\u7ea7\u4ee3\u7801", (group, cell) -> cell.setCellValue(group.getParentKey()));
        map.put("\u662f\u5426\u4e3a\u5206\u7ec4", (group, cell) -> cell.setCellValue("\u662f"));
    }
}

