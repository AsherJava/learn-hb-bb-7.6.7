/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.fmdm.common;

import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.fmdm.domain.FMDMAttributeDO;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import org.springframework.util.StringUtils;

public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getEntityId(FormSchemeDefine formSchemeDefine, String dwEntityId) {
        if (StringUtils.hasText(dwEntityId)) {
            return dwEntityId;
        }
        return Utils.getEntityId(formSchemeDefine);
    }

    public static String getEntityId(FormSchemeDefine formSchemeDefine) {
        String entityId = Utils.getEntityId();
        if (!StringUtils.hasText(entityId) && formSchemeDefine != null) {
            entityId = formSchemeDefine.getDw();
        }
        return entityId;
    }

    public static String getEntityId() {
        String entityId = null;
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext != null) {
            entityId = dsContext.getContextEntityId();
        }
        return entityId;
    }

    public static String getFilterExpression() {
        String expression = null;
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext != null) {
            expression = dsContext.getContextFilterExpression();
        }
        return expression;
    }

    public static FMDMAttributeDO transferAttribute(ColumnModelDefine columnModelDefine) {
        FMDMAttributeDO attributeDO = new FMDMAttributeDO();
        attributeDO.setID(columnModelDefine.getID());
        attributeDO.setCode(columnModelDefine.getCode());
        attributeDO.setTableID(columnModelDefine.getTableID());
        attributeDO.setName(columnModelDefine.getName());
        attributeDO.setTitle(columnModelDefine.getTitle());
        attributeDO.setDesc(columnModelDefine.getDesc());
        attributeDO.setCatagory(columnModelDefine.getCatagory());
        attributeDO.setColumnType(columnModelDefine.getColumnType());
        attributeDO.setPrecision(columnModelDefine.getPrecision());
        attributeDO.setDecimal(columnModelDefine.getDecimal());
        if (columnModelDefine.getCode().equals("PARENTCODE")) {
            attributeDO.setNullAble(true);
        } else {
            attributeDO.setNullAble(columnModelDefine.isNullAble());
        }
        attributeDO.setDefaultValue(columnModelDefine.getDefaultValue());
        attributeDO.setReferTableID(columnModelDefine.getReferTableID());
        attributeDO.setReferColumnID(columnModelDefine.getReferColumnID());
        attributeDO.setFilter(columnModelDefine.getFilter());
        attributeDO.setMultival(columnModelDefine.isMultival());
        attributeDO.setAggrType(columnModelDefine.getAggrType());
        attributeDO.setAggrType(columnModelDefine.getAggrType());
        attributeDO.setShowFormat(columnModelDefine.getShowFormat());
        attributeDO.setMeasureUnit(columnModelDefine.getMeasureUnit());
        attributeDO.setKind(columnModelDefine.getKind());
        attributeDO.setOrder(columnModelDefine.getOrder());
        return attributeDO;
    }

    public static boolean emptyObject(Object obj) {
        return null == obj || "".equals(obj);
    }
}

