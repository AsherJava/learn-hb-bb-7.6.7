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
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitService
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.gcreport.calculate.formula.service.impl.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitService;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class GetCSHFLFunction
extends Function
implements IGcFunction {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetCSHFLFunction.class);
    private static final String FUNCTION_NAME = "GETCSHFL";
    private static final String FIELD_SRCOFFSETGROUPID = "SRCOFFSETGROUPID";
    private static final String FIELD_GCBUSINESSTYPECODE = "GCBUSINESSTYPECODE";
    private static final String FIELD_OFFSETDEBIT = "OFFSETDEBIT";
    private static final String FIELD_OFFSETCREDIT = "OFFSETCREDIT";
    private static final String FIELD_SUBJECTCODE = "SUBJECTCODE";
    @Lazy
    @Autowired
    private GcOffSetItemAdjustCoreService gcOffSetItemAdjustCoreService;
    @Lazy
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Lazy
    @Autowired
    private GcOffSetInitService gcOffSetInitService;
    @Lazy
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Lazy
    @Autowired
    private IRunTimeViewController runTimeViewController;

    public GetCSHFLFunction() {
        this.parameters().add(new Parameter("subjectCode", 6, "\u79d1\u76ee\u4ee3\u7801\uff0c\u5b57\u7b26\u4e32\u7c7b\u578b\uff0c\u53ea\u652f\u63011\u4e2a\u79d1\u76ee\u4ee3\u7801\uff0c\u5fc5\u586b", false));
        this.parameters().add(new Parameter("businessTypeCode", 6, "\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u4ee3\u7801\uff0c\u5b57\u7b26\u4e32\u7c7b\u578b\uff0c\u652f\u6301\u591a\u4e2a\u4ee3\u7801\uff0c\u7528,\u9694\u5f00\uff0c\u53ef\u9009", true));
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u83b7\u53d6\u521d\u59cb\u5316\u5206\u5f55\u590d\u5236\u5230\u5f53\u671f\u7684\u62b5\u9500\u5206\u5f55\u4e2d\u6307\u5b9a\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u7684\u79d1\u76ee\u7684\u62b5\u9500\u91d1\u989d";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        List<Map<String, Double>> copyInitOffsetAmt;
        QueryContext queryContext = (QueryContext)context;
        String subjectCode = (String)parameters.get(0).evaluate((IContext)queryContext);
        String businessTypeCodeStr = ObjectUtils.isEmpty(parameters.get(1).evaluate((IContext)queryContext)) ? "" : (String)parameters.get(1).evaluate((IContext)queryContext);
        ArrayList businessTypeCodes = CollectionUtils.newArrayList((Object[])businessTypeCodeStr.split(","));
        GcCalcArgmentsDTO queryParamsVO = this.getGcCalcArgmentsDTO(queryContext);
        OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO = this.getOffsetItemInitQueryParamsVO(queryParamsVO, subjectCode);
        Set<String> offsetGroupIds = this.listCopyInitOffsetGroupId(offsetItemInitQueryParamsVO);
        if (CollectionUtils.isEmpty(offsetGroupIds)) {
            return 0.0;
        }
        List<Map<String, Object>> partFieldOffsetEntrys = this.listOffsetInitItem(offsetItemInitQueryParamsVO, offsetGroupIds);
        if (CollectionUtils.isEmpty(partFieldOffsetEntrys)) {
            return 0.0;
        }
        Set subjectCodes = this.consolidatedSubjectService.listAllChildrenCodes(subjectCode, offsetItemInitQueryParamsVO.getSystemId());
        if (CollectionUtils.isEmpty((Collection)subjectCodes)) {
            subjectCodes.add(subjectCode);
        }
        if (CollectionUtils.isEmpty(copyInitOffsetAmt = this.listCopyInitOffsetAmt(partFieldOffsetEntrys, businessTypeCodes, subjectCodes))) {
            return 0.0;
        }
        try {
            return this.getOffsetAmtSum(copyInitOffsetAmt, subjectCode, offsetItemInitQueryParamsVO.getSystemId());
        }
        catch (Exception e) {
            LOGGER.error("\u516c\u5f0fGETCSHFL\u6267\u884c\u5f02\u5e38", e);
            return 0.0;
        }
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u62b5\u9500\u91d1\u989d").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u521d\u59cb\u5316\u5206\u5f55\u590d\u5236\u5230\u5f53\u671f\u7684\u62b5\u9500\u5206\u5f55\u4e2d\u6307\u5b9a\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u7684\u79d1\u76ee\u7684\u62b5\u9500\u91d1\u989d").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GETCSHFL('\u79d1\u76ee\u4ee3\u7801','\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u4ee3\u7801')").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    private double getOffsetAmtSum(List<Map<String, Double>> copyInitOffsetAmt, String subjectCode, String systemId) {
        ConsolidatedSubjectEO consolidatedSubject = this.consolidatedSubjectService.getSubjectByCode(systemId, subjectCode);
        if (ObjectUtils.isEmpty(consolidatedSubject)) {
            LOGGER.error("\u516c\u5f0fGETCSHFL\u83b7\u53d6\u79d1\u76ee\u4fe1\u606f\u4e3a\u7a7a\uff0c subjectCode=" + subjectCode);
            return 0.0;
        }
        double sumOffsetAmt = 0.0;
        for (Map<String, Double> item : copyInitOffsetAmt) {
            if (OrientEnum.D.getValue().equals(consolidatedSubject.getOrient())) {
                sumOffsetAmt = sumOffsetAmt + item.get(FIELD_OFFSETDEBIT) - item.get(FIELD_OFFSETCREDIT);
                continue;
            }
            sumOffsetAmt = sumOffsetAmt + item.get(FIELD_OFFSETCREDIT) - item.get(FIELD_OFFSETDEBIT);
        }
        return sumOffsetAmt;
    }

    private List<Map<String, Double>> listCopyInitOffsetAmt(List<Map<String, Object>> partFieldOffsetEntrys, List<String> businessTypeCodes, Set<String> subjectCodes) {
        ArrayList<Map<String, Double>> copyInitOffsetAmt = new ArrayList<Map<String, Double>>();
        if (!CollectionUtils.isEmpty(partFieldOffsetEntrys)) {
            for (Map<String, Object> item : partFieldOffsetEntrys) {
                HashMap<String, Double> offsetAmtItem = new HashMap<String, Double>();
                offsetAmtItem.put(FIELD_OFFSETDEBIT, StringUtils.isNull((String)String.valueOf(item.get(FIELD_OFFSETDEBIT))) ? 0.0 : NumberUtils.parseDouble((Object)MapUtils.getStr(item, (Object)FIELD_OFFSETDEBIT).replace(",", "")));
                offsetAmtItem.put(FIELD_OFFSETCREDIT, StringUtils.isNull((String)String.valueOf(item.get(FIELD_OFFSETCREDIT))) ? 0.0 : NumberUtils.parseDouble((Object)MapUtils.getStr(item, (Object)FIELD_OFFSETCREDIT).replace(",", "")));
                String subjectCode = String.valueOf(item.get(FIELD_SUBJECTCODE));
                if (!subjectCodes.contains(subjectCode)) continue;
                String businessTypeCode = String.valueOf(item.get(FIELD_GCBUSINESSTYPECODE));
                if (!CollectionUtils.isEmpty(businessTypeCodes)) {
                    if (StringUtils.isNull((String)businessTypeCode) || !businessTypeCodes.contains(businessTypeCode)) continue;
                    copyInitOffsetAmt.add(offsetAmtItem);
                    continue;
                }
                copyInitOffsetAmt.add(offsetAmtItem);
            }
        }
        return copyInitOffsetAmt;
    }

    private List<Map<String, Object>> listOffsetInitItem(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO, Set<String> offsetGroupIds) {
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(offsetItemInitQueryParamsVO, queryParamsDTO);
        List partFieldOffsetEntrys = this.gcOffSetItemAdjustCoreService.listWithFullGroupBySrcOffsetGroupIdsAndSystemId(queryParamsDTO, offsetGroupIds);
        return partFieldOffsetEntrys;
    }

    private Set<String> listCopyInitOffsetGroupId(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        List initOffSetPartFieldDatas = this.gcOffSetInitService.getPartFieldOffsetEntry(offsetItemInitQueryParamsVO);
        Set<String> initSrcOffsetGroupIds = initOffSetPartFieldDatas.stream().filter(initItem -> !ObjectUtils.isEmpty(initItem.get(FIELD_SRCOFFSETGROUPID))).map(initItem -> String.valueOf(initItem.get(FIELD_SRCOFFSETGROUPID))).collect(Collectors.toSet());
        return initSrcOffsetGroupIds;
    }

    private GcCalcArgmentsDTO getGcCalcArgmentsDTO(QueryContext queryContext) {
        DimensionValueSet masterKeys = queryContext.getMasterKeys();
        String dataTime = (String)masterKeys.getValue("DATATIME");
        String currency = (String)masterKeys.getValue("MD_CURRENCY");
        String orgId = (String)masterKeys.getValue("MD_ORG");
        String selectAdjustCode = (String)masterKeys.getValue("ADJUST");
        String formSchemeId = this.getFormSchemKey(queryContext, dataTime);
        GcCalcArgmentsDTO queryParamsVO = new GcCalcArgmentsDTO();
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskBySchemeId(formSchemeId, dataTime);
        YearPeriodObject yp = new YearPeriodObject(null, dataTime);
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeId);
        queryParamsVO.setTaskId(formSchemeDefine.getTaskKey());
        queryParamsVO.setPeriodStr(dataTime);
        queryParamsVO.setAcctYear(Integer.valueOf(yp.getYear()));
        queryParamsVO.setAcctPeriod(Integer.valueOf(yp.getPeriod()));
        queryParamsVO.setSchemeId(consolidatedTaskVO.getSystemId());
        queryParamsVO.setOrgId(orgId);
        queryParamsVO.setCurrency(currency);
        queryParamsVO.setSelectAdjustCode(selectAdjustCode);
        return queryParamsVO;
    }

    private OffsetItemInitQueryParamsVO getOffsetItemInitQueryParamsVO(GcCalcArgmentsDTO paramsVO, String subjectCode) {
        OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO = new OffsetItemInitQueryParamsVO();
        BeanUtils.copyProperties(paramsVO, offsetItemInitQueryParamsVO);
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(paramsVO.getTaskId(), paramsVO.getPeriodStr());
        offsetItemInitQueryParamsVO.setSystemId(systemId);
        offsetItemInitQueryParamsVO.setOrgId(paramsVO.getOrgId());
        offsetItemInitQueryParamsVO.setOffSetSrcTypes((Collection)OffSetSrcTypeEnum.getAllInitOffSetSrcTypeValue());
        offsetItemInitQueryParamsVO.setSubjectCodes((List)CollectionUtils.newArrayList((Object[])new String[]{subjectCode}));
        return offsetItemInitQueryParamsVO;
    }

    private String getFormSchemKey(QueryContext context, String dataTime) {
        ReportFmlExecEnvironment reportFmlExecEnvironment = (ReportFmlExecEnvironment)context.getExeContext().getEnv();
        String formSchemeId = reportFmlExecEnvironment.getFormSchemeKey();
        if (StringUtils.isEmpty((String)formSchemeId)) {
            GcReportSimpleExecutorContext executorContext = (GcReportSimpleExecutorContext)context.getExeContext();
            formSchemeId = executorContext.getSchemeId();
        }
        return formSchemeId;
    }
}

