/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.reader;

import com.jiuqi.nr.formula.exception.FormulaResourceException;
import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import com.jiuqi.nr.formula.utils.excel.core.ExcelTypeEnum;
import com.jiuqi.nr.formula.utils.excel.reader.ExcelReadExecutor;
import com.jiuqi.nr.formula.utils.excel.reader.ReadSheet;
import com.jiuqi.nr.formula.utils.excel.reader.ReadWorkBook;
import com.jiuqi.nr.formula.utils.excel.reader.XLSReaderExecutor;
import com.jiuqi.nr.formula.utils.excel.reader.XLSXReaderExecutor;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader
implements Closeable {
    private final ReadWorkBook readWorkBook;
    private ExcelReadExecutor<ExcelEntity> excelReadExecutor;
    private final Map<String, List<ExcelEntity>> readResultMap;

    public ExcelReader(ReadWorkBook readWorkBook) {
        this.readWorkBook = readWorkBook;
        this.readResultMap = new HashMap<String, List<ExcelEntity>>();
        try {
            this.solutionType();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void solutionType() throws IOException {
        ExcelTypeEnum excelType = ExcelTypeEnum.valueOf(this.readWorkBook);
        if (excelType != null) {
            switch (excelType) {
                case XLSX: {
                    this.readWorkBook.setExcelTypeEnum(ExcelTypeEnum.XLSX);
                    this.readWorkBook.setWorkbook(new XSSFWorkbook(this.readWorkBook.getInputStream()));
                    this.excelReadExecutor = new XLSXReaderExecutor<ExcelEntity>();
                    break;
                }
                case XLS: {
                    this.readWorkBook.setExcelTypeEnum(ExcelTypeEnum.XLS);
                    this.readWorkBook.setWorkbook(new HSSFWorkbook(this.readWorkBook.getInputStream()));
                    this.excelReadExecutor = new XLSReaderExecutor<ExcelEntity>();
                    break;
                }
                default: {
                    throw new FormulaResourceException("\u5bfc\u5165\u7684excel\u6587\u4ef6\u683c\u5f0f\u89e3\u6790\u5931\u8d25! ");
                }
            }
        }
    }

    public ExcelReader read(ReadSheet readSheet) {
        this.readWorkBook.getSheetList().add(readSheet);
        return this;
    }

    public ExcelReader read(List<ReadSheet> readSheetList) {
        this.readWorkBook.getSheetList().addAll(readSheetList);
        return this;
    }

    public Map<String, List<ExcelEntity>> getReadResult() throws IOException {
        this.close();
        return this.readResultMap;
    }

    @Override
    public void close() throws IOException {
        this.readWorkBook.getWorkbook().close();
    }

    public void doRead() {
        try {
            this.excelReadExecutor.execute(this.readResultMap, this.readWorkBook);
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}

