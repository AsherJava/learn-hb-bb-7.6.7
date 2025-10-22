/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.sql.internal.Filter
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.filter;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.sql.internal.Filter;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionTabFilter
implements Filter<RegionTab> {
    private static final Logger logger = LoggerFactory.getLogger(RegionTabFilter.class);
    private IJtableDataEngineService jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
    private JtableContext jtableContext;
    private DimensionValueSet dimensionValueSet;

    public RegionTabFilter(JtableContext jtableContext, DimensionValueSet dimensionValueSet) {
        this.jtableContext = jtableContext;
        this.dimensionValueSet = dimensionValueSet;
    }

    public boolean accept(RegionTab regionTab) {
        if (StringUtils.isNotEmpty((String)regionTab.getDisp())) {
            boolean evaluat = true;
            AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(regionTab.getDisp(), this.jtableContext, this.dimensionValueSet);
            if (expressionEvaluat != null && expressionEvaluat instanceof BoolData) {
                try {
                    evaluat = expressionEvaluat.getAsBool();
                }
                catch (DataTypeException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            return evaluat;
        }
        return true;
    }
}

