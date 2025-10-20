/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.GCAdjTypeEnum
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.GcDiffRewriteWayEnum
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.function.GcFormulaUtils
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.rewritesetting.consts.RewriteSettingConst$FieldMappingEnum
 *  com.jiuqi.gcreport.rewritesetting.dao.RewriteSettingDao
 *  com.jiuqi.gcreport.rewritesetting.dto.RewriteFieldMappingDTO
 *  com.jiuqi.gcreport.rewritesetting.entity.RewriteSettingEO
 *  com.jiuqi.gcreport.rewritesetting.service.RewriteSettingService
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteSubjectSettingVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.util.FloatOrderGenerator
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.GcDiffRewriteWayEnum;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.function.GcFormulaUtils;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.onekeymerge.dao.FloatBalanceDao;
import com.jiuqi.gcreport.onekeymerge.service.FloatBalanceDiffService;
import com.jiuqi.gcreport.onekeymerge.service.GcDiffProcess;
import com.jiuqi.gcreport.onekeymerge.service.IFloatBalanceService;
import com.jiuqi.gcreport.onekeymerge.task.GcDiffUnitReWriteTask;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.GcDiffUnitReWriteSubTaskGather;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.IGcDiffUnitReWriteSubTask;
import com.jiuqi.gcreport.onekeymerge.util.EfdcUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.rewritesetting.consts.RewriteSettingConst;
import com.jiuqi.gcreport.rewritesetting.dao.RewriteSettingDao;
import com.jiuqi.gcreport.rewritesetting.dto.RewriteFieldMappingDTO;
import com.jiuqi.gcreport.rewritesetting.entity.RewriteSettingEO;
import com.jiuqi.gcreport.rewritesetting.service.RewriteSettingService;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteSubjectSettingVO;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.jtable.util.FloatOrderGenerator;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Primary
@Component
public class GcReltxnDiffUnitReWriteTaskImpl
extends GcDiffProcess
implements GcDiffUnitReWriteTask {
    private Logger logger = LoggerFactory.getLogger(GcReltxnDiffUnitReWriteTaskImpl.class);
    @Autowired
    private IFloatBalanceService floatBalanceService;
    @Autowired
    private FloatBalanceDao floatBalanceDao;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private RewriteSettingDao rewriteSettingDao;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private FloatBalanceDiffService floatBalanceDiffService;
    @Autowired
    private RewriteSettingService rewriteSettingService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Resource
    private GcDiffUnitReWriteSubTaskGather gcDiffUnitReWriteSubTaskGather;
    @Resource
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    ReportFormulaParseUtil reportFormulaParseUtil;

    private IGcDiffUnitReWriteSubTask getGcDiffUnitReWriteSubTask(GcActionParamsVO paramsVO, List<String> diffUnitIds, String diffRewriteWay) {
        if (GcDiffRewriteWayEnum.SUBJECT_MAPPING.getCode().equals(diffRewriteWay)) {
            return this.gcDiffUnitReWriteSubTaskGather.getByName(diffRewriteWay);
        }
        String fetchScheme = EfdcUtils.getFetchScheme(paramsVO, diffUnitIds.get(0));
        if (StringUtils.isEmpty((String)fetchScheme)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.GcReltxnDiffUnitReWriteTaskImpl.getGcDiffUnitReWriteSubTask.error.1"));
        }
        if (fetchScheme.length() > 16) {
            return this.gcDiffUnitReWriteSubTaskGather.getByName("efdc");
        }
        return this.gcDiffUnitReWriteSubTaskGather.getByName("bde");
    }

    @Override
    public ReturnObject doTask(GcActionParamsVO paramsVO, List<String> hbUnitIds, List<String> diffUnitIds, TaskLog taskLog) {
        ReturnObject returnObject = new ReturnObject(true);
        if (CollectionUtils.isEmpty(diffUnitIds)) {
            return returnObject;
        }
        try {
            ConsolidatedTaskVO taskOption = this.consolidatedTaskService.getTaskBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
            String diffRewriteWay = taskOption.getDiffRewriteWay();
            Assert.isNotEmpty((String)diffRewriteWay, (String)GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.GcReltxnDiffUnitReWriteTaskImpl.doTask.error.1"), (Object[])new Object[0]);
            IGcDiffUnitReWriteSubTask gcDiffUnitReWriteSubTask = this.getGcDiffUnitReWriteSubTask(paramsVO, diffUnitIds, diffRewriteWay);
            String rewriteTaskTitle = "formula".equals(diffRewriteWay) ? GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.GcReltxnDiffUnitReWriteTaskImpl.doTask.msg.1.1") : GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.GcReltxnDiffUnitReWriteTaskImpl.doTask.msg.1.2");
            taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.GcReltxnDiffUnitReWriteTaskImpl.doTask.msg.1", (Object[])new Object[]{rewriteTaskTitle}), Float.valueOf(38.0f)).syncTaskLog();
            Date dateEfdc = DateUtils.now();
            returnObject = gcDiffUnitReWriteSubTask.doTask(paramsVO, hbUnitIds, diffUnitIds, taskLog);
            taskLog.writeInfoLog(rewriteTaskTitle + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.GcReltxnDiffUnitReWriteTaskImpl.doTask.msg.2") + DateUtils.diffOf((Date)dateEfdc, (Date)DateUtils.now(), (int)13) + "s", Float.valueOf(42.0f));
            YearPeriodObject yp = new YearPeriodObject(null, paramsVO.getPeriodStr());
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)paramsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO diffOrg = tool.getOrgByCode(diffUnitIds.get(0));
            ConsolidatedOptionVO optionVO = this.optionService.getOptionDataBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
            List finishCalcRuleIds = optionVO.getFinishCalcRewriteRuleIds();
            if (!CollectionUtils.isEmpty((Collection)finishCalcRuleIds)) {
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.GcReltxnDiffUnitReWriteTaskImpl.doTask.msg.3") + diffUnitIds.get(0) + "|" + diffOrg.getTitle(), Float.valueOf(42.0f)).syncTaskLog();
                Date dateFloat = DateUtils.now();
                this.dataRewriteBalance(paramsVO, hbUnitIds, diffUnitIds, taskLog, finishCalcRuleIds);
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.GcReltxnDiffUnitReWriteTaskImpl.doTask.msg.4") + DateUtils.diffOf((Date)dateFloat, (Date)DateUtils.now(), (int)13) + "s", Float.valueOf(50.0f));
            }
        }
        catch (Exception e) {
            returnObject.setSuccess(false);
            returnObject.setErrorMessage(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.GcReltxnDiffUnitReWriteTaskImpl.doTask.error.2") + e.getMessage());
            this.logger.error("\u5dee\u989d\u5355\u4f4d\u6570\u636e\u56de\u5199\u5f02\u5e38\uff1a", e);
        }
        return returnObject;
    }

    private void dataRewriteBalance(GcActionParamsVO paramsVO, List<String> hbUnitIds, List<String> diffUnitIds, TaskLog taskLog, List<String> finishCalcRuleIds) {
        FloatOrderGenerator floatOrderGenerator = new FloatOrderGenerator();
        Set entityTableNames = NrTool.getEntityTableNames((String)paramsVO.getSchemeId());
        List rewriteSettings = this.rewriteSettingDao.queryRewriteSettings(paramsVO.getSchemeId());
        List rewriteSubjectSettings = this.rewriteSettingService.queryRewriteSubjectSettings(paramsVO.getSchemeId());
        HashMap<String, Integer> subjectOrientMap = new HashMap<String, Integer>();
        HashMap<String, Set<String>> rewSetGroupId2SubjectCodes = new HashMap<String, Set<String>>();
        for (int i = 0; i < hbUnitIds.size(); ++i) {
            List<GcOffSetVchrItemAdjustEO> rewriteOffsets = this.listRewriteOffsets(paramsVO, hbUnitIds.get(i), finishCalcRuleIds);
            Map<String, String> insideTableCodeMap = this.getSubject2TableNameMap(diffUnitIds.get(i), paramsVO, rewriteSettings, subjectOrientMap, rewSetGroupId2SubjectCodes, taskLog);
            List<GcOffSetVchrItemAdjustEO> insideOffsets = rewriteOffsets.stream().filter(data -> data.getOffSetSrcType().intValue() != OffSetSrcTypeEnum.PHS.getSrcTypeValue() && data.getOffSetSrcType().intValue() != OffSetSrcTypeEnum.MANUAL_OFFSET_INPUT.getSrcTypeValue()).collect(Collectors.toList());
            this.dataRewriteToInsideTable(paramsVO, diffUnitIds.get(i), floatOrderGenerator, entityTableNames, insideTableCodeMap, insideOffsets, rewriteSubjectSettings, rewriteSettings, rewSetGroupId2SubjectCodes);
            List<GcOffSetVchrItemAdjustEO> outsideOffsets = rewriteOffsets.stream().filter(data -> data.getOffSetSrcType().intValue() == OffSetSrcTypeEnum.PHS.getSrcTypeValue() || data.getOffSetSrcType().intValue() == OffSetSrcTypeEnum.MANUAL_OFFSET_INPUT.getSrcTypeValue()).collect(Collectors.toList());
            Set<String> filterOutsideFormIdSet = this.filterOutsideFormIdSet(diffUnitIds.get(i), paramsVO, rewriteSettings, taskLog);
            this.dataRewriteToOutsideTable(paramsVO, diffUnitIds.get(i), floatOrderGenerator, entityTableNames, rewriteSettings, outsideOffsets, subjectOrientMap, rewSetGroupId2SubjectCodes, filterOutsideFormIdSet);
        }
    }

    private void dataRewriteToOutsideTable(GcActionParamsVO paramsVO, String diffUnitId, FloatOrderGenerator floatOrderGenerator, Set<String> entityTableNames, List<RewriteSettingEO> rewriteSettings, List<GcOffSetVchrItemAdjustEO> outsideOffsets, Map<String, Integer> subjectOrientMap, Map<String, Set<String>> rewSetGroupId2SubjectCodes, Set<String> filterFormIdSet) {
        Map<String, List<RewriteSettingEO>> floatBalacesMap = rewriteSettings.stream().filter(item -> {
            if (filterFormIdSet.contains(item.getOutsideFormKey())) {
                return true;
            }
            this.logger.info("\u56de\u5199\u8bbe\u7f6e-\u96c6\u56e2\u5916\u6d6e\u52a8\u884c-{}\u8868\u5df2\u9501\u5b9a\uff0c\u8df3\u8fc7", (Object)item.getOutsideTableName());
            return false;
        }).collect(Collectors.groupingBy(item -> item.getRewSetGroupId()));
        YearPeriodObject yp = new YearPeriodObject(null, paramsVO.getPeriodStr());
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)paramsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO diffOrgCacheVO = orgCenterTool.getOrgByCode(diffUnitId);
        this.batchDeleteAllOutsideTableDatas(paramsVO, diffUnitId, "\u5f80\u6765\u62b5\u9500\u5dee\u5f02", floatBalacesMap);
        for (Map.Entry<String, List<RewriteSettingEO>> entry : floatBalacesMap.entrySet()) {
            BigDecimal sumAmt;
            List<GcOffSetVchrItemAdjustEO> currOffsets;
            RewriteSettingEO rewriteSettingEO = entry.getValue().get(0);
            Set<String> subjectCodes = rewSetGroupId2SubjectCodes.get(entry.getKey());
            if (StringUtils.isEmpty((String)rewriteSettingEO.getOutsideTableName()) || CollectionUtils.isEmpty(subjectCodes) || (currOffsets = outsideOffsets.stream().filter(offset -> subjectCodes.contains(offset.getSubjectCode())).collect(Collectors.toList())).isEmpty()) continue;
            Map<String, String> outsideFieldMapping = this.getFieldMappingByType(rewriteSettingEO, RewriteSettingConst.FieldMappingEnum.OUTSIDE.getCode(), false);
            ArrayList<String> currTableAllFieldCodes = new ArrayList<String>();
            HashMap<String, String> fieldDefaultValueMap = new HashMap<String, String>();
            List<String> numberAndGatherFiledCodes = this.getNumberAndGatherFiledCodes(this.getTableNameByCode(rewriteSettingEO.getOutsideTableName()), currTableAllFieldCodes, fieldDefaultValueMap);
            HashMap<String, Object> fields = new HashMap<String, Object>();
            if (ObjectUtils.isEmpty(outsideFieldMapping)) {
                fields.put("SUBJECTCODE", rewriteSettingEO.getSubjectCode());
                sumAmt = this.getGroupOutsideSumAmt(currOffsets, subjectOrientMap);
                fields.put("AMT", sumAmt.doubleValue());
                fields.put("OPPUNITTITLE", "\u5f80\u6765\u62b5\u9500\u5dee\u5f02");
                String acctOrgTitle = diffOrgCacheVO == null ? "" : diffOrgCacheVO.getCode() + "|" + diffOrgCacheVO.getTitle();
                fields.put("ACCTORGTITLE", acctOrgTitle);
            }
            if (outsideFieldMapping.containsKey("SUBJECTCODE")) {
                fields.put(outsideFieldMapping.get("SUBJECTCODE"), rewriteSettingEO.getSubjectCode());
            }
            if (outsideFieldMapping.containsKey("AMT")) {
                sumAmt = this.getGroupOutsideSumAmt(currOffsets, subjectOrientMap);
                fields.put(outsideFieldMapping.get("AMT"), sumAmt.doubleValue());
            }
            if (outsideFieldMapping.containsKey("OPPUNITID")) {
                fields.put(outsideFieldMapping.get("OPPUNITID"), "\u5f80\u6765\u62b5\u9500\u5dee\u5f02");
            }
            if (outsideFieldMapping.containsKey("UNITID")) {
                String acctOrgTitle = diffOrgCacheVO == null ? "" : diffOrgCacheVO.getCode() + "|" + diffOrgCacheVO.getTitle();
                fields.put(outsideFieldMapping.get("UNITID"), acctOrgTitle);
            }
            this.appendBaseInfo(diffUnitId, paramsVO, fields, floatOrderGenerator, entityTableNames);
            numberAndGatherFiledCodes.stream().filter(fieldCode -> !fields.containsKey(fieldCode)).forEach(fieldCode -> fields.put((String)fieldCode, 0));
            this.setFieldDefaultValue(currTableAllFieldCodes, fieldDefaultValueMap, fields);
            this.floatBalanceDiffService.addBatchFloatBalanceDiffDatas(fields, this.getTableNameByCode(rewriteSettingEO.getOutsideTableName()), currTableAllFieldCodes);
        }
    }

    private Set<String> filterOutsideFormIdSet(String diffUnitId, GcActionParamsVO paramsVO, List<RewriteSettingEO> rewriteSettings, TaskLog taskLog) {
        List<String> formKeys = rewriteSettings.stream().map(RewriteSettingEO::getOutsideFormKey).collect(Collectors.toList());
        DimensionParamsVO dimensionParamsVO = GcReltxnDiffUnitReWriteTaskImpl.getDimensionParamsVO(diffUnitId, paramsVO);
        Set<String> filterFormIdSet = this.getWritableFormIdSet(dimensionParamsVO, formKeys, taskLog, false);
        return filterFormIdSet;
    }

    private void setFieldDefaultValue(List<String> currTableAllFieldCodes, Map<String, String> fieldDefaultValueMap, Map<String, Object> fields) {
        for (String fieldName : currTableAllFieldCodes) {
            if (fields.get(fieldName) != null || fieldDefaultValueMap.get(fieldName) == null) continue;
            fields.put(fieldName, fieldDefaultValueMap.get(fieldName));
        }
    }

    private BigDecimal getGroupOutsideSumAmt(List<GcOffSetVchrItemAdjustEO> offsetSumData, Map<String, Integer> subjectOrientMap) {
        BigDecimal sumAmt = BigDecimal.ZERO;
        for (GcOffSetVchrItemAdjustEO sumData : offsetSumData) {
            BigDecimal creditValue = new BigDecimal(sumData.getOffSetCredit());
            BigDecimal debitValue = new BigDecimal(sumData.getOffSetDebit());
            BigDecimal amt = BigDecimal.ZERO.add(debitValue).subtract(creditValue);
            int subjectOrient = subjectOrientMap.get(sumData.getSubjectCode()) == null ? 0 : subjectOrientMap.get(sumData.getSubjectCode());
            sumAmt = sumAmt.add(amt.multiply(new BigDecimal(subjectOrient)));
        }
        return sumAmt.setScale(2, 4);
    }

    private void dataRewriteToInsideTable(GcActionParamsVO paramsVO, String diffUnitId, FloatOrderGenerator floatOrderGenerator, Set<String> entityTableNames, Map<String, String> tableCodeMap, List<GcOffSetVchrItemAdjustEO> insideOffsets, List<RewriteSubjectSettingVO> subjectSettings, List<RewriteSettingEO> rewriteSettings, Map<String, Set<String>> rewSetGroupId2SubjectCodes) {
        Map<String, List<RewriteSettingEO>> groupId2RewriteSettings = rewriteSettings.stream().collect(Collectors.groupingBy(item -> item.getRewSetGroupId()));
        List<RewriteSubjectSettingVO> badDebitSettings = subjectSettings.stream().filter(setting -> !StringUtils.isEmpty((String)setting.getOriginSubjectCodes()) && !StringUtils.isEmpty((String)setting.getConvertedSubjectCode())).collect(Collectors.toList());
        Set<String> rewriteFieldCodes = subjectSettings.stream().map(RewriteSubjectSettingVO::getRewriteFieldCode).collect(Collectors.toSet());
        HashMap<String, String> bizFieldTableName2DefaultValue = new HashMap<String, String>();
        for (String rewSetGroupId : rewSetGroupId2SubjectCodes.keySet()) {
            this.logger.info("\u56de\u5199\u8bbe\u7f6ekey\uff1a{}", (Object)rewSetGroupId);
            Set<String> subjectCodes = rewSetGroupId2SubjectCodes.get(rewSetGroupId);
            String subjectCode = subjectCodes.iterator().next();
            this.floatBalanceService.batchDeleteAllBalance(diffUnitId, tableCodeMap.get(subjectCode), paramsVO);
            Set<String> badDebitSubjectCodes = this.listBadDebitSubjectCodes(badDebitSettings, subjectCodes);
            List<GcOffSetVchrItemAdjustEO> currOffsets = insideOffsets.stream().filter(offset -> subjectCodes.contains(offset.getSubjectCode()) || badDebitSubjectCodes.contains(offset.getSubjectCode())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(currOffsets)) continue;
            this.logger.info("\u62b5\u6d88\u5206\u5f55\u6570\u636e\uff1a{}", (Object)JsonUtils.writeValueAsString(currOffsets));
            ArrayList<String> currTableAllFieldCodes = new ArrayList<String>();
            HashMap<String, String> fieldDefaultValueMap = new HashMap<String, String>();
            List<String> numberAndGatherFiledCodes = this.getNumberAndGatherFiledCodes(tableCodeMap.get(subjectCode), currTableAllFieldCodes, fieldDefaultValueMap);
            RewriteSettingEO rewriteSettingEO = groupId2RewriteSettings.get(rewSetGroupId).get(0);
            Map<String, String> insideOffset2ZbMapping = this.getFieldMappingByType(rewriteSettingEO, RewriteSettingConst.FieldMappingEnum.INSIDE.getCode(), false);
            this.logger.info("\u96c6\u56e2\u5185\u5b57\u6bb5\u6620\u5c04\uff1a{}", (Object)JsonUtils.writeValueAsString(insideOffset2ZbMapping));
            String[] masterColumnCodes = StringUtils.isEmpty((String)rewriteSettingEO.getMasterColumnCodes()) ? new String[]{} : rewriteSettingEO.getMasterColumnCodes().split(";");
            List<String> summaryColumnCodes = this.listSummaryColumnCodes(masterColumnCodes, insideOffset2ZbMapping);
            this.setBizKeyFieldDefaultValue(bizFieldTableName2DefaultValue, rewriteSettingEO, fieldDefaultValueMap, summaryColumnCodes);
            Map<String, List<Map<String, Object>>> directChildFloatDataMap = this.listDierctChildFloatDataMap(paramsVO, tableCodeMap.get(subjectCode), currTableAllFieldCodes, summaryColumnCodes);
            this.logger.info("\u76f4\u63a5\u4e0b\u7ea7\u5206\u7ec4\u8282\u70b9\uff1a{}", (Object)JsonUtils.writeValueAsString(directChildFloatDataMap));
            List<Map<String, Object>> floatDatasDetails = this.listFloatDataDetails(paramsVO, diffUnitId, subjectSettings, currOffsets, currTableAllFieldCodes, numberAndGatherFiledCodes, masterColumnCodes, directChildFloatDataMap, rewriteFieldCodes, insideOffset2ZbMapping);
            this.logger.info("\u83b7\u53d6\u6d6e\u52a8\u884c\u660e\u7ec6\uff1a{}", (Object)JsonUtils.writeValueAsString(floatDatasDetails));
            Map<String, List<Map<String, Object>>> summaryCodes2FloatDatas = this.groupFloatDatasBySummaryCodes(summaryColumnCodes, floatDatasDetails);
            this.logger.info("\u6d6e\u52a8\u884c\u5206\u7ec4\u6570\u636e\uff1a{}", (Object)JsonUtils.writeValueAsString(summaryCodes2FloatDatas));
            ArrayList<String> needSunField = new ArrayList<String>(rewriteFieldCodes);
            needSunField.add(insideOffset2ZbMapping.getOrDefault("AMT", "AMT"));
            List<Map<String, Object>> rewriteFloatDatas = this.listSummaryedFloatDatas(needSunField, summaryCodes2FloatDatas);
            this.logger.info("\u6d6e\u52a8\u884c\u6c47\u603b\u6570\u636e\uff1a{}", (Object)JsonUtils.writeValueAsString(rewriteFloatDatas));
            for (Map<String, Object> fields : rewriteFloatDatas) {
                this.appendBaseInfo(diffUnitId, paramsVO, fields, floatOrderGenerator, entityTableNames);
                this.setFieldDefaultValue(currTableAllFieldCodes, fieldDefaultValueMap, fields);
                this.floatBalanceDao.intertFloatBalanceDetail(tableCodeMap.get(subjectCode), currTableAllFieldCodes, fields);
            }
        }
    }

    private Map<String, String> getFieldMappingByType(RewriteSettingEO eo, String type, boolean reverseMapping) {
        return Optional.ofNullable(eo.getFieldMapping()).map(fieldMappingStr -> (List)JsonUtils.readValue((String)fieldMappingStr, (TypeReference)new TypeReference<List<RewriteFieldMappingDTO>>(){})).map(fieldMappings -> fieldMappings.stream().collect(Collectors.groupingBy(RewriteFieldMappingDTO::getType))).map(typeMap -> (List)typeMap.get(type)).orElseGet(Collections::emptyList).stream().collect(Collectors.toMap(dto -> reverseMapping ? dto.getZbField() : dto.getOffsetField(), dto -> reverseMapping ? dto.getOffsetField() : dto.getZbField(), (existing, replacement) -> replacement));
    }

    private void setBizKeyFieldDefaultValue(Map<String, String> bizFieldTableName2DefaultValue, RewriteSettingEO rewriteSettingEO, Map<String, String> fieldDefaultValueMap, List<String> summaryColumnCodes) {
        try {
            DataRegionDefine dataRegionDefine = this.iRunTimeViewController.queryDataRegionDefine(rewriteSettingEO.getInsideReginonKey());
            String[] bizKeyFieldsID = dataRegionDefine.getBizKeyFields().split(";");
            DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            List columnModelDefines = dataModelService.getColumnModelDefinesByIDs(Arrays.asList(bizKeyFieldsID));
            columnModelDefines = columnModelDefines.stream().filter(fieldDefine -> !summaryColumnCodes.contains(fieldDefine.getCode())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(columnModelDefines)) {
                return;
            }
            List referFieldKeys = columnModelDefines.stream().map(ColumnModelDefine::getReferColumnID).collect(Collectors.toList());
            List referFields = dataModelService.getColumnModelDefinesByIDs(referFieldKeys);
            Map<String, String> refreFieldKey2TableName = referFields.stream().collect(Collectors.toMap(IModelDefineItem::getID, fieldDefine -> dataModelService.getTableModelDefineById(fieldDefine.getTableID()).getCode(), (e1, e2) -> e1));
            HashMap<String, String> fieldCode2DefaultValue = new HashMap<String, String>();
            for (ColumnModelDefine fieldDefine2 : columnModelDefines) {
                List baseDataList;
                String referFieldTableName = refreFieldKey2TableName.get(fieldDefine2.getReferColumnID());
                if (!bizFieldTableName2DefaultValue.containsKey(referFieldTableName) && !CollectionUtils.isEmpty((Collection)(baseDataList = GcBaseDataCenterTool.getInstance().queryBasedataItems(referFieldTableName)))) {
                    String defaultValue = ((GcBaseData)baseDataList.get(0)).getCode();
                    bizFieldTableName2DefaultValue.put(referFieldTableName, defaultValue);
                }
                fieldCode2DefaultValue.put(fieldDefine2.getName(), bizFieldTableName2DefaultValue.get(referFieldTableName));
            }
            fieldDefaultValueMap.putAll(fieldCode2DefaultValue);
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u4e1a\u52a1\u4e3b\u952e\u9ed8\u8ba4\u503c\u9519\u8bef\uff1a" + e.getMessage(), e);
        }
    }

    private Set<String> listBadDebitSubjectCodes(List<RewriteSubjectSettingVO> badDebitSettings, Set<String> subjectCodes) {
        List<RewriteSubjectSettingVO> currSubjectSettings = badDebitSettings.stream().filter(setting -> subjectCodes.contains(setting.getConvertedSubjectCode())).collect(Collectors.toList());
        HashSet<String> badDebitSubjectCodes = new HashSet<String>();
        currSubjectSettings.forEach(setting -> badDebitSubjectCodes.addAll(Arrays.asList(setting.getOriginSubjectCodes().split(";"))));
        return badDebitSubjectCodes;
    }

    private List<Map<String, Object>> listSummaryedFloatDatas(List<String> numberAndGatherFiledCodes, Map<String, List<Map<String, Object>>> summaryCodes2FloatDatas) {
        ArrayList<Map<String, Object>> rewriteFloatDatas = new ArrayList<Map<String, Object>>();
        for (String summaryCode : summaryCodes2FloatDatas.keySet()) {
            List<Map<String, Object>> floatDatas = summaryCodes2FloatDatas.get(summaryCode);
            HashMap<String, BigDecimal> filedCode2Value = new HashMap<String, BigDecimal>();
            for (Map<String, Object> floatData : floatDatas) {
                for (String fieldCode : numberAndGatherFiledCodes) {
                    BigDecimal amountFieldValue;
                    BigDecimal bigDecimal = amountFieldValue = floatData.get(fieldCode) == null ? new BigDecimal(0) : new BigDecimal(floatData.get(fieldCode).toString());
                    if (!filedCode2Value.containsKey(fieldCode)) {
                        filedCode2Value.put(fieldCode, BigDecimal.ZERO);
                    }
                    amountFieldValue = amountFieldValue.add((BigDecimal)filedCode2Value.get(fieldCode)).setScale(2, 4);
                    filedCode2Value.put(fieldCode, amountFieldValue);
                }
            }
            floatDatas.get(0).putAll(filedCode2Value);
            rewriteFloatDatas.add(floatDatas.get(0));
        }
        return rewriteFloatDatas;
    }

    private List<Map<String, Object>> listFloatDataDetails(GcActionParamsVO paramsVO, String diffUnitId, List<RewriteSubjectSettingVO> subjectSettings, List<GcOffSetVchrItemAdjustEO> currOffsets, List<String> currTableAllFieldCodes, List<String> numberAndGatherFiledCodes, String[] masterColumnCodes, Map<String, List<Map<String, Object>>> directChildFloatDataMap, Set<String> rewriteFieldCodes, Map<String, String> fieldMapping) {
        ArrayList<Map<String, Object>> floatDatasDetails = new ArrayList<Map<String, Object>>();
        Map<String, BigDecimal> summaryCode2OffsetSumAmt = this.getSummaryCode2OffsetSumAmt(currOffsets, masterColumnCodes, paramsVO.getCurrency().toUpperCase());
        this.logger.info("\u62b5\u9500\u5206\u5f55\u6c47\u603b\u91d1\u989d:{}", (Object)JsonUtils.writeValueAsString(summaryCode2OffsetSumAmt));
        HashSet<String> summaryGroupKeySet = new HashSet<String>();
        HashMap<String, IExpression> formula2Express = new HashMap<String, IExpression>();
        GcTaskBaseArguments args = this.getTaskBaseArguments(currOffsets.get(0), paramsVO);
        for (RewriteSubjectSettingVO subjectSetting : subjectSettings) {
            String formula = subjectSetting.getRewriteFilter();
            if (StringUtils.isEmpty((String)formula)) continue;
            IExpression expression = GcFormulaUtils.getExpression((GcTaskBaseArguments)args, (String)formula);
            formula2Express.put(formula, expression);
        }
        for (GcOffSetVchrItemAdjustEO offset : currOffsets) {
            String rewriteFieldCode = "AMT";
            if (fieldMapping.containsKey(rewriteFieldCode)) {
                rewriteFieldCode = fieldMapping.get(rewriteFieldCode);
            }
            Map<String, Object> offsetDetails = this.floatBalanceService.batchRewriteToBalance(diffUnitId, offset.getFields(), rewriteFieldCode, directChildFloatDataMap, paramsVO, masterColumnCodes, numberAndGatherFiledCodes, currTableAllFieldCodes, summaryCode2OffsetSumAmt, summaryGroupKeySet, fieldMapping);
            this.logger.info("\u56de\u5199amt\u5b57\u6bb5\u7684\u503c\uff1a{}", (Object)JsonUtils.writeValueAsString(offsetDetails));
            if (offsetDetails == null) continue;
            floatDatasDetails.add(offsetDetails);
            for (RewriteSubjectSettingVO subjectSetting : subjectSettings) {
                if (rewriteFieldCodes.contains(subjectSetting.getRewriteFieldCode())) {
                    offsetDetails.put(subjectSetting.getRewriteFieldCode(), 0.0);
                }
                if (!StringUtils.isEmpty((String)subjectSetting.getOriginSubjectCodes()) && !subjectSetting.getOriginSubjectCodes().contains(offset.getSubjectCode())) continue;
                String fomula = subjectSetting.getRewriteFilter();
                String offsetSubjectCode = null;
                if (!StringUtils.isEmpty((String)fomula) && !GcFormulaUtils.checkByExpression((IExpression)((IExpression)formula2Express.get(fomula)), (GcTaskBaseArguments)args, (DefaultTableEntity)offset)) continue;
                if (!StringUtils.isEmpty((String)subjectSetting.getConvertedSubjectCode())) {
                    offsetSubjectCode = offset.getSubjectCode();
                    offset.setSubjectCode(subjectSetting.getConvertedSubjectCode());
                    floatDatasDetails.remove(offsetDetails);
                }
                Map<String, Object> otherFieldRewriteDetail = this.floatBalanceService.batchRewriteToBalance(diffUnitId, offset.getFields(), subjectSetting.getRewriteFieldCode(), directChildFloatDataMap, paramsVO, masterColumnCodes, numberAndGatherFiledCodes, currTableAllFieldCodes, null, summaryGroupKeySet, fieldMapping);
                this.logger.info("\u56de\u5199\u5176\u4ed6\u5b57\u6bb5\u503c\uff1a{}, \u56de\u5199\u8bbe\u7f6e\uff1a{}", (Object)JsonUtils.writeValueAsString(otherFieldRewriteDetail), (Object)JsonUtils.writeValueAsString((Object)subjectSetting));
                floatDatasDetails.add(otherFieldRewriteDetail);
                if (offsetSubjectCode == null) continue;
                offset.setSubjectCode(offsetSubjectCode);
            }
        }
        return floatDatasDetails.stream().filter(detail -> detail != null).collect(Collectors.toList());
    }

    private Map<String, BigDecimal> getSummaryCode2OffsetSumAmt(List<GcOffSetVchrItemAdjustEO> currOffsets, String[] masterColumnCodes, String currency) {
        return currOffsets.stream().map(AbstractFieldDynamicDeclarator::getFields).collect(Collectors.groupingBy(item -> {
            String summaryKey = "";
            for (int i = 0; i < masterColumnCodes.length; ++i) {
                summaryKey = "SUBJECTCODE".equals(masterColumnCodes[i]) ? summaryKey + item.get(String.valueOf(masterColumnCodes[i])) + "||" + item.get("SYSTEMID") : summaryKey + item.get(String.valueOf(masterColumnCodes[i]));
                if (i == masterColumnCodes.length - 1) continue;
                summaryKey = summaryKey + "_";
            }
            return summaryKey;
        })).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> this.getGroupInsideSumAmt((List)entry.getValue(), currency)));
    }

    private BigDecimal getGroupInsideSumAmt(List<Map<String, Object>> offsetSumData, String currency) {
        BigDecimal sumAmt = BigDecimal.ZERO;
        for (Map<String, Object> sumData : offsetSumData) {
            String offsetCreditField = "OFFSET_CREDIT";
            String offsetDebitField = "OFFSET_DEBIT";
            BigDecimal creditValue = BigDecimal.valueOf(sumData.get(offsetCreditField) == null ? 0.0 : (Double)sumData.get(offsetCreditField));
            BigDecimal debitValue = BigDecimal.valueOf(sumData.get(offsetDebitField) == null ? 0.0 : (Double)sumData.get(offsetDebitField));
            BigDecimal amt = BigDecimal.ZERO.add(debitValue).subtract(creditValue);
            int subjectOrient = (Integer)sumData.get("SUBJECTORIENT");
            sumAmt = sumAmt.add(amt.multiply(new BigDecimal(subjectOrient)));
        }
        return sumAmt.setScale(2, 4);
    }

    private List<String> listSummaryColumnCodes(String[] masterColumnCodes, Map<String, String> insideOffset2ZbMapping) {
        ArrayList<String> summaryColumnCodes = new ArrayList<String>();
        if (ObjectUtils.isEmpty(insideOffset2ZbMapping)) {
            for (String masterColumnCode : masterColumnCodes) {
                if (masterColumnCode.equals("UNITID")) {
                    summaryColumnCodes.add("MDCODE");
                    continue;
                }
                if (masterColumnCode.equals("OPPUNITID")) {
                    summaryColumnCodes.add("OPPUNITCODE");
                    continue;
                }
                summaryColumnCodes.add(masterColumnCode);
            }
        } else {
            for (String masterColumnCode : masterColumnCodes) {
                summaryColumnCodes.add(insideOffset2ZbMapping.getOrDefault(masterColumnCode, masterColumnCode));
            }
        }
        return summaryColumnCodes;
    }

    private GcTaskBaseArguments getTaskBaseArguments(GcOffSetVchrItemAdjustEO offsetEO, GcActionParamsVO paramsVO) {
        GcTaskBaseArguments arguments = new GcTaskBaseArguments();
        arguments.setPeriodStr(offsetEO.getDefaultPeriod());
        arguments.setCurrency(null == offsetEO.getOffSetCurr() ? "CNY" : offsetEO.getOffSetCurr());
        arguments.setOrgType(null == offsetEO.getOrgType() ? "MD_ORG_CORPORATE" : offsetEO.getOrgType());
        arguments.setOrgId(offsetEO.getUnitId());
        arguments.setTaskId(offsetEO.getTaskId());
        arguments.setSelectAdjustCode(paramsVO.getSelectAdjustCode());
        return arguments;
    }

    private Map<String, String> getSubject2TableNameMap(String diffUnitId, GcActionParamsVO paramsVO, List<RewriteSettingEO> rewriteSettings, Map<String, Integer> subjectOrientMap, Map<String, Set<String>> rewSetGroupId2SubjectCodes, TaskLog taskLog) {
        List<String> formKeys = rewriteSettings.stream().map(RewriteSettingEO::getInsideFormKey).collect(Collectors.toList());
        DimensionParamsVO dimensionParamsVO = GcReltxnDiffUnitReWriteTaskImpl.getDimensionParamsVO(diffUnitId, paramsVO);
        Set<String> filterFormIdSet = this.getWritableFormIdSet(dimensionParamsVO, formKeys, taskLog, true);
        String reportSystemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(paramsVO.getTaskId(), paramsVO.getPeriodStr());
        HashMap<String, String> subject2TableNameMap = new HashMap<String, String>();
        Map<String, List<RewriteSettingEO>> rewriteSettingsMapByGroupId = rewriteSettings.stream().collect(Collectors.groupingBy(item -> item.getRewSetGroupId()));
        for (RewriteSettingEO eo : rewriteSettings) {
            if (!filterFormIdSet.contains(eo.getInsideFormKey())) {
                this.logger.info("\u56de\u5199\u8bbe\u7f6e-\u96c6\u56e2\u5185\u6d6e\u52a8\u884c-{}\u8868\u5df2\u9501\u5b9a\uff0c\u8df3\u8fc7", (Object)eo.getInsideTableName());
                continue;
            }
            String subjectCode = eo.getSubjectCode();
            Set childrenCodes = this.subjectService.listAllChildrenCodes(subjectCode, reportSystemId);
            String insideTableName = this.getTableNameByCode(eo.getInsideTableName());
            subject2TableNameMap.put(subjectCode, insideTableName);
            subject2TableNameMap.putAll(childrenCodes.stream().filter(code -> !subject2TableNameMap.containsKey(code)).collect(Collectors.toMap(code -> code, code1 -> insideTableName)));
            List<RewriteSettingEO> rewriteSettingEOS = rewriteSettingsMapByGroupId.get(eo.getRewSetGroupId());
            String firstSubjectCode = rewriteSettingEOS.get(0).getSubjectCode();
            ConsolidatedSubjectEO firstSubject = this.subjectService.getSubjectByCode(reportSystemId, firstSubjectCode);
            subjectOrientMap.put(subjectCode, firstSubject.getOrient());
            subjectOrientMap.putAll(childrenCodes.stream().filter(code -> !subject2TableNameMap.containsKey(code)).collect(Collectors.toMap(code -> code, code1 -> firstSubject.getOrient())));
            if (!rewSetGroupId2SubjectCodes.containsKey(eo.getRewSetGroupId())) {
                rewSetGroupId2SubjectCodes.put(eo.getRewSetGroupId(), new HashSet());
            }
            Set<String> subjectCodes = rewSetGroupId2SubjectCodes.get(eo.getRewSetGroupId());
            subjectCodes.add(eo.getSubjectCode());
            subjectCodes.addAll(childrenCodes);
        }
        return subject2TableNameMap;
    }

    private Set<String> getWritableFormIdSet(DimensionParamsVO dimensionParamsVO, List<String> formKeys, TaskLog taskLog, boolean isInside) {
        FormUploadStateTool formUploadStateTool = FormUploadStateTool.getInstance();
        List writeAccessDescs = formUploadStateTool.writeable(dimensionParamsVO, formKeys);
        HashSet<String> filterFormIdSet = new HashSet<String>();
        HashSet<String> readOnlyFormTitleSet = new HashSet<String>();
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        for (int i = 0; i < formKeys.size(); ++i) {
            ReadWriteAccessDesc accessDesc = (ReadWriteAccessDesc)writeAccessDescs.get(i);
            if (accessDesc.getAble().booleanValue()) {
                filterFormIdSet.add(formKeys.get(i));
                continue;
            }
            FormDefine formDefine = runTimeViewController.queryFormById(formKeys.get(i));
            if (null == formDefine) continue;
            readOnlyFormTitleSet.add(formDefine.getTitle());
        }
        if (!readOnlyFormTitleSet.isEmpty()) {
            String readOnlyFormTitles = String.join((CharSequence)",", readOnlyFormTitleSet);
            taskLog.writeWarnLog(GcI18nUtil.getMessage((String)(isInside ? "gc.calculate.onekeymerge.calcdone.gcreltxndiffunitrewritetaskimpl.getwritableformidset.inside.error.1" : "gc.calculate.onekeymerge.calcdone.gcreltxndiffunitrewritetaskimpl.getwritableformidset.outside.error.1")) + readOnlyFormTitles, null);
        }
        return filterFormIdSet;
    }

    private static DimensionParamsVO getDimensionParamsVO(String orgCode, GcActionParamsVO paramsVO) {
        DimensionParamsVO dimensionParamsVO = new DimensionParamsVO();
        dimensionParamsVO.setCurrency(paramsVO.getCurrency());
        dimensionParamsVO.setCurrencyId(paramsVO.getCurrency());
        dimensionParamsVO.setOrgId(orgCode);
        dimensionParamsVO.setOrgType(paramsVO.getOrgType());
        dimensionParamsVO.setOrgTypeId(paramsVO.getOrgType());
        dimensionParamsVO.setPeriodStr(paramsVO.getPeriodStr());
        dimensionParamsVO.setSchemeId(paramsVO.getSchemeId());
        dimensionParamsVO.setTaskId(paramsVO.getTaskId());
        dimensionParamsVO.setSelectAdjustCode(paramsVO.getSelectAdjustCode());
        return dimensionParamsVO;
    }

    private void appendBaseInfo(String unitCode, GcActionParamsVO paramsVO, Map<String, Object> fields, FloatOrderGenerator floatOrderGenerator, Set<String> entityTableNames) {
        fields.put("MDCODE", unitCode);
        fields.put("DATATIME", paramsVO.getPeriodStr());
        if (entityTableNames.contains("MD_GCORGTYPE")) {
            GcOrgCacheVO currentUnit = OrgUtils.getCurrentUnit(paramsVO.getOrgType(), paramsVO.getPeriodStr(), unitCode);
            fields.put("MD_GCORGTYPE", currentUnit.getOrgTypeId());
        }
        if (entityTableNames.contains("MD_CURRENCY")) {
            fields.put("MD_CURRENCY", paramsVO.getCurrency());
        }
        if (entityTableNames.contains("MD_GCADJTYPE")) {
            fields.put("MD_GCADJTYPE", GCAdjTypeEnum.getEnumByTaskID((String)paramsVO.getTaskId()).getCode());
        }
        fields.put("BIZKEYORDER", UUIDOrderUtils.newUUIDStr());
        fields.put("FLOATORDER", floatOrderGenerator.next());
    }

    private Map<String, List<Map<String, Object>>> listDierctChildFloatDataMap(GcActionParamsVO paramsVO, String tableName, List<String> currTableAllFieldCodes, List<String> summaryColumnCodes) {
        List<Map<String, Object>> directChildFloatDatas = this.floatBalanceService.queryByMergeCode(paramsVO, tableName, currTableAllFieldCodes);
        this.logger.info("\u76f4\u63a5\u4e0b\u7ea7\u8282\u70b9\u6570\u636e\uff1a{}", (Object)JsonUtils.writeValueAsString(directChildFloatDatas));
        YearPeriodObject yp = new YearPeriodObject(null, paramsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)paramsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO mergeOrg = tool.getOrgByCode(paramsVO.getOrgId());
        String diffUnitId = mergeOrg.getDiffUnitId();
        directChildFloatDatas = directChildFloatDatas.stream().filter(item -> item != null && this.isNeedRewritre((Map)item, diffUnitId, summaryColumnCodes)).collect(Collectors.toList());
        return this.groupFloatDatasBySummaryCodes(summaryColumnCodes, directChildFloatDatas);
    }

    private Map<String, List<Map<String, Object>>> groupFloatDatasBySummaryCodes(List<String> summaryColumnCodes, List<Map<String, Object>> directChildFloatDatas) {
        return directChildFloatDatas.stream().collect(Collectors.groupingBy(item -> {
            String summaryKey = "";
            for (int i = 0; i < summaryColumnCodes.size(); ++i) {
                summaryKey = summaryKey + item.get(String.valueOf(summaryColumnCodes.get(i)));
                if (i == summaryColumnCodes.size() - 1) continue;
                summaryKey = summaryKey + "_";
            }
            return summaryKey;
        }));
    }
}

