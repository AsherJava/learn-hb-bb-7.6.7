/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.reader;

import com.jiuqi.nr.formula.utils.excel.reader.ReadSheet;
import com.jiuqi.nr.formula.utils.excel.reader.ReadWorkBook;
import java.util.List;
import java.util.Map;

public interface ExcelReadExecutor<T> {
    public List<ReadSheet> sheetList();

    public void execute(Map<String, List<T>> var1, ReadWorkBook var2) throws InstantiationException, IllegalAccessException;
}

