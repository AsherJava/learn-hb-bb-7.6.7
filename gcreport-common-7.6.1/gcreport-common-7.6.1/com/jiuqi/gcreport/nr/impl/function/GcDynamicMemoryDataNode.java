/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.nr.impl.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.GcFieldRelationUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import org.springframework.beans.BeanUtils;

public class GcDynamicMemoryDataNode
extends DynamicDataNode {
    private static final long serialVersionUID = -20860266924858710L;
    private static final String GC_TABLE_PREFIX = "GC_";

    public GcDynamicMemoryDataNode(DynamicDataNode child) {
        BeanUtils.copyProperties(child, (Object)this);
    }

    public int setValue(IContext context, Object value) throws SyntaxException {
        try {
            GcReportSimpleExecutorContext executorContext = this.getExecutorContext(context);
            executorContext.getData().addFieldValue(this.getQueryField().getFieldCode(), value);
            String fieldName = GcFieldRelationUtils.getFieldName(executorContext.getData(), this.queryField.getFieldName(), false);
            if (!StringUtils.isEmpty((String)fieldName)) {
                String setter = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                DefaultTableEntity entity = executorContext.getData();
                String filedType = entity.getClass().getDeclaredField(fieldName).getType().getName();
                Method curMethod = null;
                if (filedType.equals("java.lang.String")) {
                    curMethod = entity.getClass().getMethod(setter, String.class);
                    curMethod.invoke(entity, ConverterUtils.getAsString((Object)value));
                } else if (filedType.equals("java.lang.Double")) {
                    curMethod = entity.getClass().getMethod(setter, Double.class);
                    curMethod.invoke(entity, ConverterUtils.getAsDouble((Object)value));
                } else if (filedType.equals("java.lang.Integer")) {
                    curMethod = entity.getClass().getMethod(setter, Integer.class);
                    curMethod.invoke(entity, ConverterUtils.getAsDouble((Object)value));
                }
            }
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
        return 1;
    }

    private GcReportSimpleExecutorContext getExecutorContext(IContext qContext) {
        return (GcReportSimpleExecutorContext)((QueryContext)qContext).getExeContext();
    }

    public Object evaluate(IContext context) throws SyntaxException {
        if (this.queryField.getTableName().startsWith(GC_TABLE_PREFIX)) {
            GcReportSimpleExecutorContext executorContext = this.getExecutorContext(context);
            String fieldValue = GcFieldRelationUtils.getFieldName(executorContext.getData(), this.queryField.getFieldName(), true);
            Object value = executorContext.getData().getFieldValue(fieldValue.toUpperCase());
            if (value == null) {
                return null;
            }
            if (this.getType(context) == 10) {
                if (value instanceof Double) {
                    value = new BigDecimal((Double)value);
                } else if (value instanceof Integer) {
                    value = new BigDecimal((Integer)value);
                } else if (value instanceof Long) {
                    value = new BigDecimal((Long)value);
                }
            }
            return value;
        }
        return super.evaluate(context);
    }
}

