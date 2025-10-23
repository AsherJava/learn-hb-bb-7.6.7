/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.excel;

import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowReader;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface IExcelRowReaderProvider {
    public IExcelRowReader getExcelRowReader(Row var1, Map<String, Short> var2);
}

