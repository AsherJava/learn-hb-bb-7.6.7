/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.nr.impl.uploadstate.util.intern;

import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public class JtableContextConvertUtils {
    public static JtableContext convert2JtableContext(DataEntryContext dataEntryContext) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(dataEntryContext.getDimensionSet());
        jtableContext.setFormSchemeKey(dataEntryContext.getFormSchemeKey());
        jtableContext.setTaskKey(dataEntryContext.getTaskKey());
        return jtableContext;
    }

    public static JtableContext convert2JtableContext(DimensionParamsVO param, String orgId) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(DimensionUtils.buildDimensionMap((String)param.getTaskId(), (String)param.getCurrencyId(), (String)param.getPeriodStr(), (String)param.getOrgTypeId(), (String)orgId, (String)param.getSelectAdjustCode()));
        jtableContext.setFormSchemeKey(param.getSchemeId());
        jtableContext.setTaskKey(param.getTaskId());
        return jtableContext;
    }
}

