/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.reader;

import com.jiuqi.nr.formula.utils.excel.reader.ExcelReadExecutor;
import com.jiuqi.nr.formula.utils.excel.reader.ReadSheet;
import com.jiuqi.nr.formula.utils.excel.reader.ReadWorkBook;
import java.util.List;
import java.util.Map;

public class XLSReaderExecutor<T>
implements ExcelReadExecutor<T> {
    @Override
    public List<ReadSheet> sheetList() {
        return null;
    }

    @Override
    public void execute(Map<String, List<T>> readResultMap, ReadWorkBook readWorkBook) {
    }
}

