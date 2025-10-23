/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 */
package com.jiuqi.nr.zb.scheme.internal.excel.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbGroupDTO;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowReader;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import com.jiuqi.nr.zb.scheme.internal.excel.impl.ExcelRowWrapperImpl;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelGroupReaderValidatorImpl;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelReaderValidator;
import java.time.Instant;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class GroupExcelRowReader
implements IExcelRowReader {
    private final String schemeKey;
    private final String versionKey;
    private final ExcelReaderValidator validator = new ExcelGroupReaderValidatorImpl();

    public GroupExcelRowReader(String schemeKey, String versionKey) {
        this.schemeKey = schemeKey;
        this.versionKey = versionKey;
    }

    @Override
    public ExcelReaderValidator getValidator() {
        return this.validator;
    }

    @Override
    public IExcelRowWrapper read(int rowNum, Map<String, Short> headers, Row row) {
        ExcelRowWrapperImpl rowWrapper = new ExcelRowWrapperImpl();
        ZbGroupDTO groupDTO = new ZbGroupDTO();
        groupDTO.setUpdateTime(Instant.now());
        groupDTO.setOrder(OrderGenerator.newOrder());
        groupDTO.setSchemeKey(this.schemeKey);
        groupDTO.setVersionKey(this.versionKey);
        for (Map.Entry<String, Short> entry : headers.entrySet()) {
            String head = entry.getKey();
            Short index = entry.getValue();
            Cell cell = row.getCell(index.shortValue());
            if (cell == null) continue;
            String value = cell.getStringCellValue();
            if ("\u540d\u79f0".equals(head)) {
                groupDTO.setTitle(value);
                continue;
            }
            if ("\u4ee3\u7801".equals(head)) {
                groupDTO.setKey(value);
                continue;
            }
            if (!"\u7236\u7ea7\u4ee3\u7801".equals(head)) continue;
            groupDTO.setParentKey(value);
        }
        rowWrapper.setData(groupDTO);
        rowWrapper.setGroup(true);
        rowWrapper.setRowNum(rowNum);
        this.validate(groupDTO, rowWrapper);
        this.afterValidate(groupDTO);
        return rowWrapper;
    }
}

