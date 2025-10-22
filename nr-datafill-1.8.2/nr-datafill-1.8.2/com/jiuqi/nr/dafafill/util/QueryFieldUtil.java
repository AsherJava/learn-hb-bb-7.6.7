/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 */
package com.jiuqi.nr.dafafill.util;

import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import org.springframework.util.StringUtils;

public class QueryFieldUtil {
    public static QueryField copyDimField(QueryField old, DataScheme scheme) {
        QueryField newOBJ = new QueryField();
        newOBJ.setId(old.getId());
        newOBJ.setCode(old.getCode());
        newOBJ.setDataSchemeCode(scheme.getCode());
        if (StringUtils.hasText(old.getFullCode())) {
            String oFullCode = old.getFullCode();
            newOBJ.setFullCode(scheme.getCode() + "." + oFullCode.split("\\.")[1]);
        }
        newOBJ.setTitle(old.getTitle());
        newOBJ.setFieldType(old.getFieldType());
        newOBJ.setDataType(old.getDataType());
        newOBJ.setColWidthType(old.getColWidthType());
        newOBJ.setEditorType(old.getEditorType());
        newOBJ.setShowFormat(old.getShowFormat());
        return newOBJ;
    }
}

