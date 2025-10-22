/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.gcreport.samecontrol.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlBeginOffSetItemService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class SameCtrBeginAdjustOffsetFunction
extends Function
implements INrFunction {
    private transient SameCtrlBeginOffSetItemService sameCtrlBeginOffSetItemService;
    private transient ConsolidatedSubjectService subjectService;
    private transient ConsolidatedTaskService taskCacheService;

    public SameCtrBeginAdjustOffsetFunction() {
        this.parameters().add(new Parameter("subjectCode", 6, "\u79d1\u76ee\u4ee3\u7801"));
        this.subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        this.taskCacheService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        this.sameCtrlBeginOffSetItemService = (SameCtrlBeginOffSetItemService)SpringContextUtils.getBean(SameCtrlBeginOffSetItemService.class);
    }

    public String name() {
        return "QCTZDX";
    }

    public String title() {
        return "\u671f\u521d\u6570\u8c03\u6574\u8868\u4e2d\u5408\u5e76\u62b5\u9500\u8c03\u6574\u516c\u5f0f\u51fd\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Double evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        String subjectExpression = (String)list.get(0).evaluate(iContext);
        return this.calcResult((QueryContext)iContext, subjectExpression);
    }

    public double calcResult(QueryContext iContext, String subjectExpression) {
        if (StringUtils.isEmpty((String)subjectExpression)) {
            return 0.0;
        }
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)iContext.getExeContext().getEnv();
        String formSchemeKey = env.getFormSchemeKey();
        DimensionValueSet ds = iContext.getMasterKeys();
        String orgTypeCode = (String)ds.getValue("MD_GCORGTYPE");
        String dataTime = (String)ds.getValue("DATATIME");
        String orgCode = (String)ds.getValue("MD_ORG");
        String currencyCode = (String)ds.getValue("MD_CURRENCY");
        YearPeriodObject yp = new YearPeriodObject(null, dataTime);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgTypeCode, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO org = tool.getOrgByCode(orgCode);
        if (org.getOrgKind() != GcOrgKindEnum.DIFFERENCE) {
            return 0.0;
        }
        if (!"CNY".equals(currencyCode)) {
            return 0.0;
        }
        String reportSystemId = this.taskCacheService.getSystemIdBySchemeId(formSchemeKey, dataTime);
        List<String>[] subjectCodeList = this.parseSubjectExpress(subjectExpression, reportSystemId);
        Map<String, Double> subjectCode2OffsetMap = this.getCache(iContext, orgTypeCode, dataTime, org);
        double result = 0.0;
        for (String subjectCode : subjectCodeList[0]) {
            result += this.calcSubjectOffsetValue(subjectCode, subjectCode2OffsetMap, reportSystemId);
        }
        for (String subjectCode : subjectCodeList[1]) {
            result -= this.calcSubjectOffsetValue(subjectCode, subjectCode2OffsetMap, reportSystemId);
        }
        return result;
    }

    private Map<String, Double> getCache(QueryContext queryContext, String orgTypeCode, String dataTime, GcOrgCacheVO org) {
        String cacheName = "qcTzDxFunctionCache";
        Map cache = queryContext.getCache();
        if (cache.containsKey(cacheName)) {
            return (Map)cache.get(cacheName);
        }
        YearPeriodObject yp = new YearPeriodObject(null, dataTime);
        GcOrgCacheVO currMergeOrgCacheVO = GcOrgPublicTool.getInstance((String)orgTypeCode, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp).getOrgByCode(org.getParentId());
        Map<String, Double> subjectCode2OffsetMap = this.sameCtrlBeginOffSetItemService.calcBeginSubjectCode2OffsetLimitYear(dataTime, orgTypeCode, currMergeOrgCacheVO);
        cache.put(cacheName, subjectCode2OffsetMap);
        return subjectCode2OffsetMap;
    }

    private double calcSubjectOffsetValue(String subjectCode, Map<String, Double> subjectCode2OffsetMap, String reportSystemId) {
        double result = 0.0;
        Set subjectCodeSet = this.subjectService.listAllChildrenCodes(subjectCode, reportSystemId);
        if (subjectCodeSet.isEmpty()) {
            Double offsetValue = subjectCode2OffsetMap.get(subjectCode);
            if (null != offsetValue) {
                return offsetValue;
            }
            return 0.0;
        }
        for (String childSubjectCode : subjectCodeSet) {
            Double offsetValue = subjectCode2OffsetMap.get(childSubjectCode);
            if (null == offsetValue) continue;
            result += offsetValue.doubleValue();
        }
        return result;
    }

    private List<String>[] parseSubjectExpress(String subjectExpression, String reportSystemId) {
        String[] subjectCodes;
        ArrayList[] result = new ArrayList[]{new ArrayList(), new ArrayList()};
        for (String subjectCode : subjectCodes = subjectExpression.split(";")) {
            ConsolidatedSubjectEO subjectEO;
            if (StringUtils.isEmpty((String)subjectCode)) continue;
            subjectCode = subjectCode.trim();
            boolean changeOrient = false;
            if (subjectCode.startsWith("-")) {
                changeOrient = true;
                subjectCode = subjectCode.substring(1);
            }
            if ((subjectEO = this.subjectService.getSubjectByCode(reportSystemId, subjectCode)) == null) continue;
            if (subjectEO.getOrient() == OrientEnum.D.getValue() && !changeOrient) {
                result[0].add(subjectCode);
                continue;
            }
            result[1].add(subjectCode);
        }
        return result;
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u671f\u521d\u6570\u8c03\u6574\u8868\u4e2d\u5408\u5e76\u62b5\u9500\u8c03\u6574\u6570").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u79d1\u76ee\u4ee3\u7801\u4e3a1231\u7684\u6570\u636e\u7ed3\u679c\u3002 ").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("QCTZDX('1231')").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

