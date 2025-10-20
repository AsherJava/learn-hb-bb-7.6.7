/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.query;

import com.jiuqi.gcreport.inputdata.function.sumhb.dao.SumhbTempDao;
import com.jiuqi.gcreport.inputdata.function.sumhb.query.DiffInputQuery;
import com.jiuqi.gcreport.inputdata.function.sumhb.query.InputQuery;
import com.jiuqi.gcreport.inputdata.function.sumhb.query.SingleInputQuery;
import com.jiuqi.gcreport.inputdata.function.sumhb.query.UnionInputQuery;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.Collection;
import java.util.Map;

public class InputQueryFactory {
    public static InputQuery createQuery(QueryContext queryContext, String tableName, Collection<String> calcFieldNames, Collection<String> noCalcFieldNames, Map<String, String> dims, SumhbTempDao sumhbTempDao) {
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
        YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)env.getFormSchemeKey(), (String)dims.get("DATATIME"));
        String orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)env.getTaskDefine().getKey());
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)orgCategory, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)yp);
        GcOrgCacheVO org = orgCenterTool.getOrgByCode(dims.get("MDCODE"));
        if (GcOrgKindEnum.UNIONORG == org.getOrgKind()) {
            return new UnionInputQuery(queryContext, tableName, calcFieldNames, noCalcFieldNames, dims, sumhbTempDao);
        }
        if (GcOrgKindEnum.DIFFERENCE == org.getOrgKind()) {
            return new DiffInputQuery(queryContext, tableName, calcFieldNames, noCalcFieldNames, dims, sumhbTempDao);
        }
        return new SingleInputQuery(queryContext, tableName, calcFieldNames, noCalcFieldNames, dims, sumhbTempDao);
    }
}

