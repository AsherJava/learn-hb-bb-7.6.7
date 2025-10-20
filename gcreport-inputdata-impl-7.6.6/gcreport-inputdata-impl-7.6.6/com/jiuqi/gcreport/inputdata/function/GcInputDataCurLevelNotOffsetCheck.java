/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.nr.impl.function.NrFunction
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.nr.impl.function.NrFunction;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcInputDataCurLevelNotOffsetCheck
extends NrFunction {
    private static final long serialVersionUID = 7799439091105411047L;
    private static final String FUNC_NAME = "CurLevelNotOffsetCheck";
    private final Logger logger = LoggerFactory.getLogger(GcInputDataCurLevelNotOffsetCheck.class);
    @Autowired
    private InputDataService inputDataService;

    public String name() {
        return FUNC_NAME;
    }

    public String title() {
        return "\u5185\u90e8\u7248\u6570\u636e\u5f53\u524d\u5c42\u7ea7\u672a\u62b5\u6d88\u6570\u636e\u7684\u6821\u9a8c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
        try {
            return this.checkCurLevelNotOffsetInputData(env, queryContext.getCurrentMasterKey());
        }
        catch (Exception e) {
            this.logger.error("CurLevelNotOffsetCheck()\u516c\u5f0f\u6267\u884c\u5f02\u5e38", e);
            return false;
        }
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)1)).append("\uff1a").append(DataType.toString((int)1)).append("\uff1b").append("\u5408\u5e76\u6237\u5185\u90e8\u8868\u589e\u52a0\u5f53\u524d\u5c42\u7ea7\u672a\u62b5\u9500\u6570\u636e\u7684\u6821\u9a8c\u516c\u5f0f\uff0c\u82e5\u5b58\u5728\u672a\u62b5\u9500\u6570\u636e\uff0c\u5219\u9519\u8bef\u578b\u62a5\u9519\uff0c\u65e0\u6cd5\u4e0a\u62a5").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5185\u90e8\u7248\u6570\u636e\u5f53\u524d\u5c42\u7ea7\u672a\u62b5\u6d88\u6570\u636e\u7684\u6821\u9a8c\u3002 ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("CurLevelNotOffsetCheck()").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    private boolean checkCurLevelNotOffsetInputData(ReportFmlExecEnvironment env, DimensionValueSet dimensionValueSet) {
        FormSchemeDefine formSchemeDefine = env.getFormSchemeDefine();
        String orgId = String.valueOf(dimensionValueSet.getValue("MD_ORG"));
        String period = String.valueOf(dimensionValueSet.getValue("DATATIME"));
        YearPeriodObject yp = new YearPeriodObject(formSchemeDefine.getKey(), period);
        String orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)formSchemeDefine.getTaskKey());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgCategory, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO cacheVO = tool.getOrgByCode(orgId);
        if (Objects.isNull(cacheVO)) {
            this.logger.error("\u5185\u90e8\u7248\u6570\u636e\u5f53\u524d\u5c42\u7ea7\u672a\u62b5\u6d88\u6570\u636e\u7684\u6821\u9a8c\u83b7\u53d6\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0c\u7ef4\u5ea6\u4fe1\u606f:" + dimensionValueSet.toString());
            return false;
        }
        boolean isCheckFlag = this.checkOrgKind(cacheVO);
        if (isCheckFlag) {
            return true;
        }
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        queryParamsVO.setOrgId(orgId);
        if (DimensionUtils.isExistAdjust((String)formSchemeDefine.getTaskKey())) {
            String adjustCode = String.valueOf(dimensionValueSet.getValue("ADJUST"));
            queryParamsVO.setSelectAdjustCode(adjustCode);
        }
        String currency = String.valueOf(dimensionValueSet.getValue("MD_CURRENCY"));
        queryParamsVO.setCurrency(currency);
        queryParamsVO.setPeriodStr(period);
        queryParamsVO.setTaskId(formSchemeDefine.getTaskKey());
        queryParamsVO.setOrgType(orgCategory);
        int totalCount = this.inputDataService.getUnOffsetInputDataItemCount(queryParamsVO);
        return totalCount <= 0;
    }

    private boolean checkOrgKind(GcOrgCacheVO cacheVO) {
        return !GcOrgKindEnum.UNIONORG.equals((Object)cacheVO.getOrgKind());
    }
}

