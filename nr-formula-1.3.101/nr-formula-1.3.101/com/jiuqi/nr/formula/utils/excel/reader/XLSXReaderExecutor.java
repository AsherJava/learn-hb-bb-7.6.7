/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.internal.controller2.DesignTimeViewController
 */
package com.jiuqi.nr.formula.utils.excel.reader;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.internal.controller2.DesignTimeViewController;
import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import com.jiuqi.nr.formula.utils.excel.core.ExcelField;
import com.jiuqi.nr.formula.utils.excel.reader.ExcelReadExecutor;
import com.jiuqi.nr.formula.utils.excel.reader.ReadSheet;
import com.jiuqi.nr.formula.utils.excel.reader.ReadWorkBook;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.cglib.beans.BeanMap;

public class XLSXReaderExecutor<T>
implements ExcelReadExecutor<T> {
    private IDesignTimeViewController designTimeViewController = new DesignTimeViewController();

    @Override
    public List<ReadSheet> sheetList() {
        return null;
    }

    @Override
    public void execute(Map<String, List<T>> readResultMap, ReadWorkBook readWorkBook) throws InstantiationException, IllegalAccessException {
        Workbook workbook = readWorkBook.getWorkbook();
        int sheetCount = workbook.getNumberOfSheets();
        List fields = Arrays.stream(readWorkBook.getHeadClass().getDeclaredFields()).filter(x -> x.isAnnotationPresent(ExcelField.class)).sorted(Comparator.comparing(x -> x.getAnnotation(ExcelField.class).order(), Integer::compare)).collect(Collectors.toList());
        List fieldNames = fields.stream().map(Field::getName).collect(Collectors.toList());
        ExcelEntity excelEntity = readWorkBook.getHeadClass().newInstance();
        BeanMap beanMap = BeanMap.create(excelEntity);
        for (int i = 0; i < sheetCount; ++i) {
            Sheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            int rowCount = sheet.getLastRowNum();
            int colCount = fields.size();
            ArrayList<ExcelEntity> list = new ArrayList<ExcelEntity>(rowCount * colCount);
            for (int j = 1; j <= rowCount; ++j) {
                Row row = sheet.getRow(j);
                for (int k = 0; k < colCount; ++k) {
                    Cell cell = row.getCell(k);
                    System.out.println();
                    beanMap.put(fieldNames.get(k), cell == null ? null : cell.getStringCellValue());
                }
                list.add(excelEntity.clone());
            }
            readResultMap.put(sheetName, list);
        }
    }
}

