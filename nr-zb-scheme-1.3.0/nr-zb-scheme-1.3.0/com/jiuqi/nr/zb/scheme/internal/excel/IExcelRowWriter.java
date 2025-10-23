/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.excel;

import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface IExcelRowWriter {
    public void write(IExcelRowWrapper var1, List<String> var2, Row var3);
}

