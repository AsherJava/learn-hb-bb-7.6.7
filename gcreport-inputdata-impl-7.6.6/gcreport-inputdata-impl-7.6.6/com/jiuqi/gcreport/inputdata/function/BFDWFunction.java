/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.inputdata.function.BaseUnitFunction;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.np.dataengine.query.QueryContext;
import org.springframework.stereotype.Component;

@Component
public class BFDWFunction
extends BaseUnitFunction {
    private static final long serialVersionUID = 1L;
    public final String FUNCTION_NAME = "BFDW";

    public String name() {
        return "BFDW";
    }

    public String title() {
        return "\u53d6\u672c\u65b9\u5355\u4f4d\u6307\u6807\u6570\u636e";
    }

    @Override
    String getUnitId(QueryContext queryContext) {
        DefaultTableEntity entity = this.getDefaultTableEntity(queryContext);
        if (entity == null) {
            return null;
        }
        if (entity instanceof InputDataEO) {
            InputDataEO inputdata = (InputDataEO)entity;
            return inputdata.getUnitId();
        }
        Object unitId = entity.getFields().get("UNITID") == null ? entity.getFields().get("UNITCODE") : entity.getFields().get("UNITID");
        return (String)unitId;
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u6307\u6807\u6570\u503c").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u4e0a\u4e00\u5e74\u6307\u6807\u6570\u503c ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("BFDW('ZCOX_YB01[A06]','-1N')").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

