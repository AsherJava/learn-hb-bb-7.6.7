/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.inputdata.function.BaseUnitFunction;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DFDWFunction
extends BaseUnitFunction
implements INrFunction {
    private static final long serialVersionUID = 1L;
    public final String FUNCTION_NAME = "DFDW";

    public String name() {
        return "DFDW";
    }

    public String title() {
        return "\u53d6\u5bf9\u65b9\u5355\u4f4d\u6307\u6807\u6570\u636e";
    }

    @Override
    String getUnitId(QueryContext queryContext) {
        DefaultTableEntity entity = this.getDefaultTableEntity(queryContext);
        if (entity == null) {
            return null;
        }
        if (entity instanceof InputDataEO) {
            InputDataEO inputdata = (InputDataEO)entity;
            return inputdata.getOppUnitId();
        }
        if (entity.getFields().get("OPPUNITID") != null) {
            return (String)entity.getFields().get("OPPUNITID");
        }
        if (entity.getFields().get("INVESTEDUNIT") != null) {
            return (String)entity.getFields().get("INVESTEDUNIT");
        }
        if (entity.getFields().get("OPPUNITCODE") != null) {
            return (String)entity.getFields().get("OPPUNITCODE");
        }
        Object unitId = MapUtils.getVal((Map)entity.getFields(), (Object)"UNITID", entity.getFields().get("UNITCODE"));
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
        buffer.append("    ").append("    ").append("DFDW('ZCOX_YB01[A06]','-1N')").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

