/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.zb.scheme.internal.excel.validation;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.zb.scheme.common.ZbGatherType;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbInfoDTO;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import com.jiuqi.nr.zb.scheme.internal.excel.validation.ExcelReaderValidator;
import java.util.HashSet;
import java.util.Set;

class StringExcelReaderValidator
implements ExcelReaderValidator {
    private static final Set<ZbGatherType> gatherTypes = new HashSet<ZbGatherType>();

    StringExcelReaderValidator() {
    }

    @Override
    public <T> void validate(T data, IExcelRowWrapper rowWrapper) {
        FormatProperties formatProperties;
        ZbInfoDTO zbInfo = (ZbInfoDTO)data;
        if (!gatherTypes.contains((Object)zbInfo.getGatherType())) {
            rowWrapper.addError("\u6c47\u603b\u7c7b\u578b\u4e0d\u6b63\u786e");
        }
        if ((formatProperties = zbInfo.getFormatProperties()) != null && formatProperties.getFormatType() != 0) {
            rowWrapper.addError("\u663e\u793a\u683c\u5f0f\u7c7b\u578b\u4e0d\u6b63\u786e");
        }
    }

    @Override
    public <T> void afterValidate(T data) {
    }

    static {
        gatherTypes.add(ZbGatherType.NONE);
        gatherTypes.add(ZbGatherType.COUNT);
        gatherTypes.add(ZbGatherType.MIN);
    }
}

