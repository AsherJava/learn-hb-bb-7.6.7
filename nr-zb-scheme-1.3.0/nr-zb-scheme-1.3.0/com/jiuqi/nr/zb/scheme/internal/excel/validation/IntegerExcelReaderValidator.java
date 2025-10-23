/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.excel.validation;

import com.jiuqi.nr.zb.scheme.internal.dto.ZbInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelReaderValidator;

class IntegerExcelReaderValidator
implements ExcelReaderValidator {
    IntegerExcelReaderValidator() {
    }

    @Override
    public <T> void validate(T data, IExcelRowWrapper rowWrapper) {
        ZbInfoDTO zbInfo = (ZbInfoDTO)data;
        if (zbInfo.getGatherType() == null) {
            rowWrapper.addError("\u6c47\u603b\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
    }
}

