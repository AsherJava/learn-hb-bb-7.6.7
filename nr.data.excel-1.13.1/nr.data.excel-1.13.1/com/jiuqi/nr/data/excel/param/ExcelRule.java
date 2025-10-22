/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.excel.extend.param.SheetNameType;
import com.jiuqi.nr.data.excel.param.DataInfo;
import com.jiuqi.nr.data.excel.param.Directory;
import com.jiuqi.nr.data.excel.param.GenerateParam;
import com.jiuqi.nr.data.excel.param.ParseParam;
import java.util.List;

public interface ExcelRule {
    public List<Directory> generateExportInfo(GenerateParam var1);

    public DataInfo parseDataInfo(ParseParam var1);

    public String name();

    default public SheetNameType getSheetNameType() {
        return SheetNameType.FORM;
    }
}

