/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.excel;

import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelReaderValidator;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface IExcelRowReader {
    public IExcelRowWrapper read(int var1, Map<String, Short> var2, Row var3);

    default public ExcelReaderValidator getValidator() {
        return null;
    }

    default public <T> void validate(T object, IExcelRowWrapper rowWrapper) {
        ExcelReaderValidator validator = this.getValidator();
        validator.validate(object, rowWrapper);
    }

    default public <T> void afterValidate(T object) {
        ExcelReaderValidator validator = this.getValidator();
        validator.afterValidate(object);
    }
}

