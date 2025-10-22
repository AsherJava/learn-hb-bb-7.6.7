/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.calculate.util.BaseDataUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.DeferredIncomeTax
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.i18n.util.GcI18nHelper
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.function.GcFormulaUtils
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcEndCarryForwardService
 *  com.jiuqi.gcreport.offsetitem.service.impl.EndCarryForwardDataSourceServiceImpl
 *  com.jiuqi.gcreport.offsetitem.task.DeferredIncomeTaxTaskImpl
 *  com.jiuqi.gcreport.offsetitem.task.IGcEndCarryForwardExportTask
 *  com.jiuqi.gcreport.offsetitem.task.LossGainTaskImpl
 *  com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryTableVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.invest.offsetitem.task;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.calculate.util.BaseDataUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.DeferredIncomeTax;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.i18n.util.GcI18nHelper;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio.InvestCalculator;
import com.jiuqi.gcreport.invest.investbill.executor.CompreEquityRatioTask;
import com.jiuqi.gcreport.invest.offsetitem.task.MinorityLossGainRecoveryTaskImpl;
import com.jiuqi.gcreport.nr.impl.function.GcFormulaUtils;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcEndCarryForwardService;
import com.jiuqi.gcreport.offsetitem.service.impl.EndCarryForwardDataSourceServiceImpl;
import com.jiuqi.gcreport.offsetitem.task.DeferredIncomeTaxTaskImpl;
import com.jiuqi.gcreport.offsetitem.task.IGcEndCarryForwardExportTask;
import com.jiuqi.gcreport.offsetitem.task.LossGainTaskImpl;
import com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryTableVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class GcEndCarryForwardExportImpl
implements IGcEndCarryForwardExportTask {
    private static final Logger logger = LoggerFactory.getLogger(GcEndCarryForwardExportImpl.class);
    private ConsolidatedOptionService optionService;
    private ConsolidatedTaskService consolidatedTaskCacheService;
    private ConsolidatedSubjectService subjectService;
    private String systemId;
    private ConsolidatedOptionVO optionVO;
    private Map<String, String> accountFieldCode2DictTableMap = new HashMap<String, String>();
    private String diffUnitId;
    private CompreEquityRatioTask compreEquityRatioTask;
    private DimensionService dimensionService;
    private GcI18nHelper gcI18nHelper;
    private LossGainTaskImpl lossGainTask;
    private EndCarryForwardDataSourceServiceImpl endCarryForwardDataSourceService;
    @Autowired
    private GcEndCarryForwardService gcEndCarryForwardService;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public GcEndCarryForwardExportImpl() {
        this.consolidatedTaskCacheService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        this.optionService = (ConsolidatedOptionService)SpringContextUtils.getBean(ConsolidatedOptionService.class);
        this.subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        this.compreEquityRatioTask = (CompreEquityRatioTask)SpringContextUtils.getBean(CompreEquityRatioTask.class);
        this.dimensionService = (DimensionService)SpringContextUtils.getBean(DimensionService.class);
        this.gcI18nHelper = (GcI18nHelper)SpringContextUtils.getBean(GcI18nHelper.class);
        this.lossGainTask = (LossGainTaskImpl)SpringContextUtils.getBean(LossGainTaskImpl.class);
        this.endCarryForwardDataSourceService = (EndCarryForwardDataSourceServiceImpl)SpringContextUtils.getBean(EndCarryForwardDataSourceServiceImpl.class);
    }

    public MinorityRecoveryTableVO queryDetail(QueryParamsVO queryParamsVO) {
        queryParamsVO = LossGainTaskImpl.lossGainSimpleParam((QueryParamsVO)queryParamsVO);
        this.initFieldValue(queryParamsVO);
        if (!this.allowDo(queryParamsVO)) {
            return null;
        }
        queryParamsVO.setQueryAllColumns(true);
        OffsetMatchRow offsetMatchRow = this.queryAndFilterData(queryParamsVO);
        List assetOffsetList = offsetMatchRow.headTwoRow;
        Map<ArrayKey, BigDecimal> combinedKey2offsetValueMap_headTwoRow = this.sumOffsetValueByManageAccDim(assetOffsetList, false, true, true);
        Map<ArrayKey, BigDecimal> combinedKey2offsetValueMap_tailRow = this.sumOffsetValueByManageAccDim(offsetMatchRow.tailRow, true, true);
        List<MinorityRecoveryRowVO> headTwoRowList = this.convert2row(combinedKey2offsetValueMap_headTwoRow, true, true);
        List<MinorityRecoveryRowVO> tailRowList = this.convert2row(combinedKey2offsetValueMap_tailRow, true);
        ArrayList<MinorityRecoveryRowVO> allRowList = new ArrayList<MinorityRecoveryRowVO>();
        allRowList.addAll(headTwoRowList);
        allRowList.addAll(tailRowList);
        this.appendTitleInfo(allRowList, queryParamsVO);
        this.appendRatioInfo(allRowList, queryParamsVO);
        MinorityRecoveryTableVO minorityRecoveryTableVO = this.classifyDetailRow(headTwoRowList, queryParamsVO);
        minorityRecoveryTableVO.getLossGain().addAll(tailRowList);
        boolean allowDeferredIncomeTax = this.gcEndCarryForwardService.allowDeferredIncomeTax(queryParamsVO);
        this.calcRowAmt(allRowList, minorityRecoveryTableVO, allowDeferredIncomeTax, queryParamsVO);
        minorityRecoveryTableVO.sumTotalRow();
        MinorityLossGainRecoveryTaskImpl recoverTask = (MinorityLossGainRecoveryTaskImpl)SpringContextUtils.getBean(MinorityLossGainRecoveryTaskImpl.class);
        minorityRecoveryTableVO.setFractionDigits(Integer.valueOf(recoverTask.getCompreEquityRatioFractionDigits()));
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            this.rebuildMinorityRecoveryTableVO(minorityRecoveryTableVO);
        }
        return minorityRecoveryTableVO;
    }

    private void rebuildMinorityRecoveryTableVO(MinorityRecoveryTableVO vo) {
        List downStream = vo.getDownStream();
        List againstStream = vo.getAgainstStream();
        List horizStream = vo.getHorizStream();
        vo.getDeferredIncomeTax().addAll(downStream);
        vo.getDeferredIncomeTax().addAll(againstStream);
        vo.getDeferredIncomeTax().addAll(horizStream);
        vo.getDownStream().clear();
        vo.getAgainstStream().clear();
        vo.getHorizStream().clear();
        vo.sumTotalRow();
    }

    private boolean filterHeadTwoRowByFilterFormula(GcOffSetVchrItemAdjustEO record, IExpression diTaxExpression, IExpression lgRecoveryExpression) {
        GcTaskBaseArguments taskBaseArguments = this.getTaskBaseArguments(record);
        boolean checkDiTaxFormulaResult = GcFormulaUtils.checkByExpression((IExpression)diTaxExpression, (GcTaskBaseArguments)taskBaseArguments, (DefaultTableEntity)record);
        if (checkDiTaxFormulaResult) {
            return true;
        }
        return GcFormulaUtils.checkByExpression((IExpression)lgRecoveryExpression, (GcTaskBaseArguments)taskBaseArguments, (DefaultTableEntity)record);
    }

    private GcTaskBaseArguments getTaskBaseArguments(GcOffSetVchrItemAdjustEO offsetEO) {
        GcTaskBaseArguments arguments = new GcTaskBaseArguments();
        arguments.setPeriodStr(offsetEO.getDefaultPeriod());
        arguments.setCurrency(null == offsetEO.getOffSetCurr() ? "CNY" : offsetEO.getOffSetCurr());
        arguments.setOrgType(null == offsetEO.getOrgType() ? "MD_ORG_CORPORATE" : offsetEO.getOrgType());
        arguments.setOrgId(offsetEO.getUnitId());
        arguments.setTaskId(offsetEO.getTaskId());
        arguments.setSelectAdjustCode(offsetEO.getAdjust());
        return arguments;
    }

    private List<DesignFieldDefineVO> getManageAccColumns() {
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        ArrayList<DesignFieldDefineVO> otherShowColumns = new ArrayList<DesignFieldDefineVO>();
        if (null == this.optionVO.getManagementAccountingFieldCodes()) {
            return otherShowColumns;
        }
        try {
            for (String code : this.optionVO.getManagementAccountingFieldCodes()) {
                TableModelDefine tableDefine = dataModelService.getTableModelDefineByCode("GC_OFFSETVCHRITEM");
                ColumnModelDefine columnModelDefine = dataModelService.getColumnModelDefineByCode(tableDefine.getID(), code);
                DesignFieldDefineVO designFieldDefineVO = new DesignFieldDefineVO();
                designFieldDefineVO.setKey(code);
                String localTitle = this.gcI18nHelper.getMessage(columnModelDefine.getID());
                designFieldDefineVO.setLabel(StringUtils.isEmpty((CharSequence)localTitle) ? columnModelDefine.getTitle() : localTitle);
                designFieldDefineVO.setType(columnModelDefine.getColumnType());
                TableModelDefine refTable = dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID());
                if (refTable != null) {
                    designFieldDefineVO.setDictTableName(refTable.getName());
                }
                otherShowColumns.add(designFieldDefineVO);
            }
        }
        catch (Exception e) {
            logger.warn("\u83b7\u53d6\u7ba1\u7406\u4f1a\u8ba1\u5b57\u6bb5\u5931\u8d25", e);
        }
        return otherShowColumns;
    }

    private void resetBaseUnitEquityRatio(List<MinorityRecoveryRowVO> minorityRecoveryRowList, QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String baseUnitCode = orgCenterService.getDeepestBaseUnitId(queryParamsVO.getOrgId());
        if (StringUtils.isEmpty((CharSequence)baseUnitCode)) {
            return;
        }
        for (MinorityRecoveryRowVO rowVO : minorityRecoveryRowList) {
            if (baseUnitCode.equals(rowVO.getUnitCode())) {
                rowVO.setUnitEquityRatio(BigDecimal.ONE);
                continue;
            }
            if (!baseUnitCode.equals(rowVO.getOppUnitCode())) continue;
            rowVO.setOppUnitEquityRatio(BigDecimal.ONE);
        }
    }

    private void calcRowAmt(List<MinorityRecoveryRowVO> minorityRecoveryRowList, MinorityRecoveryTableVO minorityRecoveryTableVO, boolean minusDeferredIncomeTax, QueryParamsVO queryParamsVO) {
        ConsolidatedTaskVO taskOption = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getTaskBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        for (MinorityRecoveryRowVO rowVO : minorityRecoveryRowList) {
            rowVO.doCalc(taskOption.getEnableDeferredIncomeTax());
        }
        minorityRecoveryTableVO.repairValue(minusDeferredIncomeTax, taskOption.getEnableDeferredIncomeTax());
    }

    private void appendRatioInfo(List<MinorityRecoveryRowVO> minorityRecoveryRowList, QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = orgCenterService.getOrgByCode(queryParamsVO.getOrgId());
        ArrayList<Map<String, Object>> investRecords = new ArrayList<Map<String, Object>>();
        InvestCalculator calculator = this.compreEquityRatioTask.getInvestCalculatorFromDb(queryParamsVO.getOrgType(), queryParamsVO.getPeriodStr(), queryParamsVO.getOrgId(), investRecords);
        HashMap<String, Double> unitCode2RatioCacheMap = new HashMap<String, Double>(64);
        boolean isFixRate = false;
        BigDecimal rate = BigDecimal.ZERO;
        if (StringUtils.isNumeric((CharSequence)this.optionVO.getDiTax().getZbCode())) {
            isFixRate = true;
            rate = new BigDecimal(this.optionVO.getDiTax().getZbCode()).divide(new BigDecimal(100));
        }
        HashMap unitCode2rateMap = new HashMap(16);
        Set<String> allDebitSubjectCode = this.listAllDebitSubjectCode();
        DeferredIncomeTaxTaskImpl deferredIncomeTaxTask = new DeferredIncomeTaxTaskImpl(this.optionVO);
        for (MinorityRecoveryRowVO rowVO : minorityRecoveryRowList) {
            Double unitEquityRatio = this.calcCompreEquityRatio(unitCode2RatioCacheMap, rowVO.getUnitCode(), calculator, queryParamsVO.getOrgType(), queryParamsVO.getPeriodStr(), investRecords);
            rowVO.setUnitEquityRatio(BigDecimal.valueOf(unitEquityRatio));
            Double oppUnitEquityRatio = this.calcCompreEquityRatio(unitCode2RatioCacheMap, rowVO.getOppUnitCode(), calculator, queryParamsVO.getOrgType(), queryParamsVO.getPeriodStr(), investRecords);
            rowVO.setOppUnitEquityRatio(BigDecimal.valueOf(oppUnitEquityRatio));
            if (!isFixRate) {
                String destUnitCode = allDebitSubjectCode.contains(rowVO.getSubjectCode()) ? rowVO.getUnitCode() : rowVO.getOppUnitCode();
                rate = deferredIncomeTaxTask.getRateZbValue(destUnitCode, unitCode2rateMap, queryParamsVO);
            }
            rowVO.setDiTaxRate(rate);
        }
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            return;
        }
        this.resetBaseUnitEquityRatio(minorityRecoveryRowList, queryParamsVO);
    }

    private Set<String> listAllDebitSubjectCode() {
        List subjectEOS = this.subjectService.listAllSubjectsBySystemId(this.systemId);
        Set<String> allDebitSubject = subjectEOS.stream().filter(subject -> null != subject.getOrient() && subject.getOrient() == OrientEnum.D.getValue()).map(ConsolidatedSubjectEO::getCode).collect(Collectors.toSet());
        return allDebitSubject;
    }

    private Double calcCompreEquityRatio(Map<String, Double> unitCode2RatioCacheMap, String unitCode, InvestCalculator calculator, String orgType, String periodStr, List<Map<String, Object>> investRecords) {
        if (null == unitCode) {
            return 1.0;
        }
        if (unitCode2RatioCacheMap.containsKey(unitCode)) {
            return unitCode2RatioCacheMap.get(unitCode);
        }
        double ratio = this.compreEquityRatioTask.getCompreEquityRatioValue(calculator.getBaseUnitId(), calculator, investRecords, orgType, periodStr, unitCode);
        ratio = new BigDecimal(String.valueOf(ratio)).divide(new BigDecimal(100), 16, 4).doubleValue();
        unitCode2RatioCacheMap.put(unitCode, ratio);
        return ratio;
    }

    private void appendTitleInfo(List<MinorityRecoveryRowVO> minorityRecoveryRowList, QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        HashMap unitCode2TitleCache = new HashMap(64);
        HashMap<String, String> subject2TitleCache = new HashMap<String, String>();
        for (MinorityRecoveryRowVO rowVO : minorityRecoveryRowList) {
            String unitTitle = OrgPeriodUtil.getUnitTitle(unitCode2TitleCache, (String)rowVO.getUnitCode(), (GcOrgCenterService)orgCenterService);
            rowVO.setUnitTitle(unitTitle);
            String oppUnitTitle = OrgPeriodUtil.getUnitTitle(unitCode2TitleCache, (String)rowVO.getOppUnitCode(), (GcOrgCenterService)orgCenterService);
            rowVO.setOppUnitTitle(oppUnitTitle);
            this.setSubjectTitle(this.systemId, rowVO, subject2TitleCache);
        }
    }

    private void setSubjectTitle(String systemId, MinorityRecoveryRowVO rowVO, Map<String, String> subject2TitleCache) {
        List allSubjectEos;
        String subjectCode = rowVO.getSubjectCode();
        if (null == subjectCode) {
            return;
        }
        if (org.springframework.util.CollectionUtils.isEmpty(subject2TitleCache) && !org.springframework.util.CollectionUtils.isEmpty(allSubjectEos = this.subjectService.listAllSubjectsBySystemId(systemId))) {
            allSubjectEos.forEach(subjectEO -> subject2TitleCache.put(subjectEO.getCode(), subjectEO.getTitle()));
        }
        if (null == subject2TitleCache.get(subjectCode)) {
            ConsolidatedSubjectEO subject = this.subjectService.getSubjectByCode(systemId, subjectCode);
            String title = null == subject ? subjectCode : subject.getTitle();
            subject2TitleCache.put(subjectCode, title);
        }
        rowVO.setSubjectTitle(subject2TitleCache.get(subjectCode));
    }

    private List<MinorityRecoveryRowVO> convert2row(Map<ArrayKey, BigDecimal> combinedKey2offsetValueMap, boolean needDetailKey) {
        return this.convert2row(combinedKey2offsetValueMap, needDetailKey, false);
    }

    private List<MinorityRecoveryRowVO> convert2row(Map<ArrayKey, BigDecimal> combinedKey2offsetValueMap, boolean needDetailKey, boolean isFilterByFormula) {
        ArrayList<MinorityRecoveryRowVO> minorityRecoveryRowList = new ArrayList<MinorityRecoveryRowVO>();
        List managementAccountingFieldCodes = this.optionVO.getManagementAccountingFieldCodes();
        for (Map.Entry<ArrayKey, BigDecimal> entry : combinedKey2offsetValueMap.entrySet()) {
            ArrayKey key = entry.getKey();
            BigDecimal offsetAmt = entry.getValue();
            if (BigDecimal.ZERO.compareTo(offsetAmt) == 0) continue;
            MinorityRecoveryRowVO minorityRecoveryRowVO = new MinorityRecoveryRowVO();
            int i = 0;
            minorityRecoveryRowVO.setOppUnitCode((String)key.get(i++));
            if (needDetailKey) {
                minorityRecoveryRowVO.setUnitCode((String)key.get(i++));
            }
            minorityRecoveryRowVO.setSubjectCode((String)key.get(i++));
            int keySize = isFilterByFormula ? key.size() - 1 : key.size();
            int beginIndex = i;
            while (i < keySize) {
                minorityRecoveryRowVO.addFieldValue((String)managementAccountingFieldCodes.get(i - beginIndex), (Object)this.getShowColumnDictTitle(key.get(i), (String)managementAccountingFieldCodes.get(i - beginIndex)));
                ++i;
            }
            if (isFilterByFormula) {
                minorityRecoveryRowVO.setLossGainRecovery(Boolean.valueOf("1".equals(key.get(i++))));
            }
            minorityRecoveryRowVO.setOffsetAmt(offsetAmt);
            minorityRecoveryRowList.add(minorityRecoveryRowVO);
        }
        return minorityRecoveryRowList;
    }

    private MinorityRecoveryTableVO classifyDetailRow(List<MinorityRecoveryRowVO> minorityRecoveryRowList, QueryParamsVO queryParamsVO) {
        MinorityRecoveryTableVO tableVO = new MinorityRecoveryTableVO();
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String baseUnitCode = orgCenterService.getDeepestBaseUnitId(queryParamsVO.getOrgId());
        HashSet lossGainRecoverySubjects = CollectionUtils.newHashSet((Collection)this.optionVO.getDiTax().getLossGainRecoverySubjects());
        for (MinorityRecoveryRowVO rowVO : minorityRecoveryRowList) {
            if (lossGainRecoverySubjects.contains(rowVO.getSubjectCode()) && (rowVO.getLossGainRecovery() == null || rowVO.getLossGainRecovery().booleanValue())) {
                if (StringUtils.isEmpty((CharSequence)baseUnitCode)) {
                    tableVO.getHorizStream().add(rowVO);
                    continue;
                }
                if (baseUnitCode.equals(rowVO.getUnitCode())) {
                    tableVO.getAgainstStream().add(rowVO);
                    continue;
                }
                if (baseUnitCode.equals(rowVO.getOppUnitCode())) {
                    tableVO.getDownStream().add(rowVO);
                    continue;
                }
                tableVO.getHorizStream().add(rowVO);
                continue;
            }
            tableVO.getDeferredIncomeTax().add(rowVO);
        }
        return tableVO;
    }

    private void initFieldValue(QueryParamsVO queryParamsVO) {
        this.systemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        Assert.isNotNull((Object)this.systemId, (String)"\u672a\u627e\u89c1\u4f53\u7cfb", (Object[])new Object[0]);
        this.optionVO = this.optionService.getOptionData(this.systemId);
        this.initFieldCode2DictTableMap();
        this.initManagementDimColumns(queryParamsVO);
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            return;
        }
        this.initDiffUnitId(queryParamsVO);
    }

    private void initDiffUnitId(QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = instance.getOrgByCode(queryParamsVO.getOrgId());
        this.diffUnitId = hbOrg.getDiffUnitId();
        Assert.isNotNull((Object)this.diffUnitId, (String)("\u5408\u5e76\u5355\u4f4d\u3010" + hbOrg.getTitle() + "\u3011\u6ca1\u6709\u8bbe\u7f6e\u5dee\u989d\u5355\u4f4d\uff0c\u65e0\u6cd5\u8fdb\u884c\u5c11\u6570\u80a1\u4e1c\u635f\u76ca\u8fd8\u539f"), (Object[])new Object[0]);
    }

    private boolean allowDo(QueryParamsVO paramsVO) {
        ConsolidatedTaskVO taskOption = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getTaskBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        if (!taskOption.getEnableMinLossGainRecovery().booleanValue()) {
            return false;
        }
        DeferredIncomeTax diTax = this.optionVO.getDiTax();
        if (null == diTax || CollectionUtils.isEmpty((Collection)diTax.getAssetSubjects()) || StringUtils.isEmpty((CharSequence)diTax.getZbCode())) {
            return false;
        }
        return !StringUtils.isEmpty((CharSequence)diTax.getMinorityEquitySubject()) && !StringUtils.isEmpty((CharSequence)diTax.getMinorityLossGainSubject());
    }

    private void initManagementDimColumns(QueryParamsVO queryParamsVO) {
        queryParamsVO.getOtherShowColumns().addAll(this.optionVO.getManagementAccountingFieldCodes());
    }

    private Map<ArrayKey, BigDecimal> sumOffsetValueByManageAccDim(List<GcOffSetVchrItemAdjustEO> offsetItems, boolean isProfit, boolean needDetailKey) {
        return this.sumOffsetValueByManageAccDim(offsetItems, isProfit, needDetailKey, false);
    }

    private Map<ArrayKey, BigDecimal> sumOffsetValueByManageAccDim(List<GcOffSetVchrItemAdjustEO> offsetItems, boolean isProfit, boolean needDetailKey, boolean filterByFormula) {
        ArrayList<String> manaAccFieldCodes = new ArrayList<String>(16);
        manaAccFieldCodes.add("OPPUNITID");
        if (needDetailKey) {
            manaAccFieldCodes.add("UNITID");
        }
        manaAccFieldCodes.add("SUBJECTCODE");
        manaAccFieldCodes.addAll(this.optionVO.getManagementAccountingFieldCodes());
        HashMap<ArrayKey, BigDecimal> offsetValueMap = new HashMap<ArrayKey, BigDecimal>();
        HashSet assetSubjectCodes = CollectionUtils.newHashSet((Collection)this.optionVO.getDiTax().getAssetSubjects());
        IExpression lgRecoveryExpression = !CollectionUtils.isEmpty(offsetItems) ? GcFormulaUtils.getExpression((GcTaskBaseArguments)this.getTaskBaseArguments(offsetItems.get(0)), (String)this.optionVO.getDiTax().getLgRecoveryFilterFormula()) : null;
        for (GcOffSetVchrItemAdjustEO record : offsetItems) {
            ArrayKey key;
            if (isProfit) {
                record.setSubjectCode(this.optionVO.getUndistributedProfitSubjectCode());
                key = this.getCombinedKey(manaAccFieldCodes, record);
                MapUtils.add(offsetValueMap, (Object)key, (BigDecimal)BigDecimal.valueOf(record.getOffSetDebit()));
                MapUtils.sub(offsetValueMap, (Object)key, (BigDecimal)BigDecimal.valueOf(record.getOffSetCredit()));
                continue;
            }
            if (!assetSubjectCodes.contains(record.getSubjectCode())) continue;
            key = this.getCombinedKey(manaAccFieldCodes, record);
            if (filterByFormula) {
                GcTaskBaseArguments taskBaseArguments = this.getTaskBaseArguments(record);
                boolean checkDiTaxFormulaResult = GcFormulaUtils.checkByExpression((IExpression)lgRecoveryExpression, (GcTaskBaseArguments)this.getTaskBaseArguments(record), (DefaultTableEntity)record);
                key = checkDiTaxFormulaResult && this.optionVO.getDiTax().getLossGainRecoverySubjects().contains(record.getSubjectCode()) ? key.append((Object)"1") : key.append((Object)"0");
            }
            MapUtils.add(offsetValueMap, (Object)key, (BigDecimal)BigDecimal.valueOf(record.getOffSetCredit()));
            MapUtils.sub(offsetValueMap, (Object)key, (BigDecimal)BigDecimal.valueOf(record.getOffSetDebit()));
        }
        return offsetValueMap;
    }

    private OffsetMatchRow queryAndFilterData(QueryParamsVO queryParamsVO) {
        Map<String, List<GcOffSetVchrItemAdjustEO>> mrecid2OffsetMap = this.queryData(queryParamsVO);
        return this.filterAssetByGroup(mrecid2OffsetMap);
    }

    private Map<String, List<GcOffSetVchrItemAdjustEO>> queryData(QueryParamsVO queryParamsVO) {
        List offsetDimensions;
        QueryParamsVO copyQueryParamsVO = new QueryParamsVO();
        BeanUtils.copyProperties(queryParamsVO, copyQueryParamsVO);
        copyQueryParamsVO.setOtherShowColumns(new ArrayList());
        copyQueryParamsVO.getOtherShowColumns().addAll(queryParamsVO.getOtherShowColumns());
        if (!copyQueryParamsVO.getOtherShowColumns().contains("MRECID")) {
            copyQueryParamsVO.getOtherShowColumns().add("MRECID");
        }
        if (!copyQueryParamsVO.getOtherShowColumns().contains("ELMMODE")) {
            copyQueryParamsVO.getOtherShowColumns().add("ELMMODE");
        }
        if (!copyQueryParamsVO.getOtherShowColumns().contains("UNITID")) {
            copyQueryParamsVO.getOtherShowColumns().add("UNITID");
        }
        if (!copyQueryParamsVO.getOtherShowColumns().contains("OPPUNITID")) {
            copyQueryParamsVO.getOtherShowColumns().add("OPPUNITID");
        }
        if (!CollectionUtils.isEmpty((Collection)(offsetDimensions = this.dimensionService.findDimFieldsByTableName("GC_OFFSETVCHRITEM")))) {
            List offsetDimensionCodes = offsetDimensions.stream().map(DimensionEO::getCode).collect(Collectors.toList());
            copyQueryParamsVO.getOtherShowColumns().addAll(offsetDimensionCodes);
        }
        copyQueryParamsVO.setOffSetSrcTypes(null);
        copyQueryParamsVO.setFilterDisableItem(true);
        List offsetVchrItems = this.endCarryForwardDataSourceService.listWithOnlyItems(copyQueryParamsVO, false);
        return offsetVchrItems.stream().collect(Collectors.groupingBy(GcOffSetVchrItemAdjustEO::getmRecid));
    }

    private OffsetMatchRow filterAssetByGroup(Map<String, List<GcOffSetVchrItemAdjustEO>> offSetMap) {
        OffsetMatchRow offsetQueryRow = new OffsetMatchRow();
        HashSet assetSubjectCodes = CollectionUtils.newHashSet((Collection)this.optionVO.getDiTax().getAssetSubjects());
        Set profitLossSubjectCodes = this.subjectService.listAllCodesByAttr(this.systemId, SubjectAttributeEnum.PROFITLOSS);
        HashSet assetProfitSubjectCodes = CollectionUtils.newHashSet((Collection)assetSubjectCodes);
        assetProfitSubjectCodes.addAll(profitLossSubjectCodes);
        for (List<GcOffSetVchrItemAdjustEO> offSets : offSetMap.values()) {
            int matchLevel = this.matchLevel(offSets, assetSubjectCodes, profitLossSubjectCodes);
            if (matchLevel == 1) {
                this.addOffsetAssetItem(offSets, assetSubjectCodes, offsetQueryRow.headTwoRow);
                for (GcOffSetVchrItemAdjustEO offSet : offSets) {
                    if (assetProfitSubjectCodes.contains(offSet.getSubjectCode())) continue;
                    offSet.setOffSetDebit(Double.valueOf(NumberUtils.sub((Double)offSet.getOffSetCredit(), (Double)offSet.getOffSetDebit())));
                    offSet.setOffSetCredit(Double.valueOf(0.0));
                    offsetQueryRow.tailRow.add(offSet);
                }
                continue;
            }
            if (matchLevel != 2) continue;
            for (GcOffSetVchrItemAdjustEO offSet : offSets) {
                if (!profitLossSubjectCodes.contains(offSet.getSubjectCode())) continue;
                offsetQueryRow.tailRow.add(offSet);
            }
        }
        return offsetQueryRow;
    }

    private void addOffsetAssetItem(List<GcOffSetVchrItemAdjustEO> srcOffSets, Set<String> assetSubjectCodes, List<GcOffSetVchrItemAdjustEO> destOffSetResult) {
        destOffSetResult.addAll(srcOffSets.stream().filter(o -> assetSubjectCodes.contains(o.getSubjectCode())).collect(Collectors.toList()));
    }

    private int matchLevel(List<GcOffSetVchrItemAdjustEO> offSets, Set<String> assetSubjectCodes, Set<String> profitLossSubjectCodes) {
        boolean hasAssetSubject = false;
        boolean hasProfitLossSubject = false;
        boolean onlyHasProfitLossSubject = true;
        IExpression diTaxExpression = GcFormulaUtils.getExpression((GcTaskBaseArguments)this.getTaskBaseArguments(offSets.get(0)), (String)this.optionVO.getDiTax().getDiTaxFilterFormula());
        IExpression lgRecoveryExpression = GcFormulaUtils.getExpression((GcTaskBaseArguments)this.getTaskBaseArguments(offSets.get(0)), (String)this.optionVO.getDiTax().getLgRecoveryFilterFormula());
        for (GcOffSetVchrItemAdjustEO offSet : offSets) {
            if (offSet.getOffSetSrcType().intValue() == OffSetSrcTypeEnum.BROUGHT_FORWARD_LOSS_GAIN.getSrcTypeValue() || offSet.getOffSetSrcType().intValue() == OffSetSrcTypeEnum.DEFERRED_INCOME_TAX.getSrcTypeValue() || offSet.getOffSetSrcType().intValue() == OffSetSrcTypeEnum.DEFERRED_INCOME_TAX_RULE.getSrcTypeValue() || offSet.getOffSetSrcType().intValue() == OffSetSrcTypeEnum.MINORITY_LOSS_GAIN_RECOVERY.getSrcTypeValue()) {
                return 0;
            }
            if (this.filterHeadTwoRowByFilterFormula(offSet, diTaxExpression, lgRecoveryExpression)) {
                hasAssetSubject = hasAssetSubject || assetSubjectCodes.contains(offSet.getSubjectCode());
            }
            boolean bl = hasProfitLossSubject = hasProfitLossSubject || profitLossSubjectCodes.contains(offSet.getSubjectCode());
            if (profitLossSubjectCodes.contains(offSet.getSubjectCode())) continue;
            onlyHasProfitLossSubject = false;
        }
        if (hasAssetSubject && hasProfitLossSubject) {
            return 1;
        }
        if (hasProfitLossSubject && !onlyHasProfitLossSubject) {
            return 2;
        }
        if (onlyHasProfitLossSubject) {
            return 3;
        }
        return 0;
    }

    private ArrayKey getCombinedKey(List<String> manaAccFieldCodes, GcOffSetVchrItemAdjustEO record) {
        ArrayList<String> keys = new ArrayList<String>(16);
        for (String upperCode : manaAccFieldCodes) {
            keys.add(ConverterUtils.getAsString((Object)record.getFieldValue(upperCode), (String)""));
        }
        return new ArrayKey(keys);
    }

    private void initFieldCode2DictTableMap() {
        if (!CollectionUtils.isEmpty((Collection)this.optionVO.getManagementAccountingFieldCodes())) {
            try {
                DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
                TableModelDefine tableDefine = dataModelService.getTableModelDefineByName("GC_OFFSETVCHRITEM");
                for (String column : this.optionVO.getManagementAccountingFieldCodes()) {
                    TableModelDefine tableModelDefine;
                    ColumnModelDefine columnModelDefine = dataModelService.getColumnModelDefineByCode(tableDefine.getID(), column);
                    if (StringUtils.isEmpty((CharSequence)columnModelDefine.getReferTableID()) || (tableModelDefine = dataModelService.getTableModelDefineById(columnModelDefine.getReferTableID())) == null || StringUtils.isEmpty((CharSequence)tableModelDefine.getCode())) continue;
                    this.accountFieldCode2DictTableMap.put(column, tableModelDefine.getName());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getShowColumnDictTitle(Object value, String otherShowColumn) {
        if (value == null || StringUtils.isEmpty((CharSequence)value.toString())) {
            return "";
        }
        if ("SUBJECTORIENT".equals(otherShowColumn)) {
            return Integer.valueOf(value.toString()) == 1 ? "\u501f" : "\u8d37";
        }
        if ("EFFECTTYPE".equals(otherShowColumn)) {
            return EFFECTTYPE.getTitleByCode((String)value.toString());
        }
        String dictTableName = this.accountFieldCode2DictTableMap.get(otherShowColumn);
        if (dictTableName == null) {
            return value.toString();
        }
        String dictTitle = BaseDataUtils.getDictTitle((String)dictTableName, (String)((String)value));
        if (!StringUtils.isEmpty((CharSequence)dictTitle)) {
            return value + "|" + dictTitle;
        }
        return value.toString();
    }

    public ExportExcelSheet exportMinorityRecoveryWorkPaper(QueryParamsVO queryParamsVO, MinorityRecoveryTableVO minorityRecoveryTableVO) {
        ExportExcelSheet exportExcelSheet = Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge()) ? new ExportExcelSheet(Integer.valueOf(0), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.workPaper"), Integer.valueOf(2)) : new ExportExcelSheet(Integer.valueOf(0), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.minLossGainRecoveryWorkPaper"), Integer.valueOf(2));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        List cellRangeAddresses = exportExcelSheet.getCellRangeAddresses();
        this.initFieldValue(queryParamsVO);
        if (Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge())) {
            this.createRyHeader(rowDatas, cellRangeAddresses);
            this.createRyDatasRegion(queryParamsVO, rowDatas, cellRangeAddresses);
        } else {
            this.createHeader(rowDatas, cellRangeAddresses);
            this.createDatasRegion(queryParamsVO, rowDatas, cellRangeAddresses, minorityRecoveryTableVO);
        }
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }

    private void createRyDatasRegion(QueryParamsVO queryParamsVO, List<Object[]> rowDatas, List<CellRangeAddress> cellRangeAddresses) {
        MinorityRecoveryTableVO minorityRecoveryTableVO = this.queryDetail(queryParamsVO);
        if (minorityRecoveryTableVO == null) {
            return;
        }
        int[] index = new int[]{1};
        int fractionDigits = minorityRecoveryTableVO.getFractionDigits();
        rowDatas.add(this.getRyTotalRowData(cellRangeAddresses, minorityRecoveryTableVO.getAllTotal(), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.sum"), index));
        rowDatas.add(this.getRyTotalRowData(cellRangeAddresses, minorityRecoveryTableVO.getDeferredIncomeTaxTotal(), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.deferredIncomeTaxSum"), index));
        rowDatas.addAll(this.listRyMinorityRecoveryDetails(minorityRecoveryTableVO.getDeferredIncomeTax(), fractionDigits, index, -1));
        rowDatas.add(this.getRyTotalRowData(cellRangeAddresses, minorityRecoveryTableVO.getLossGainTotal(), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.workPaperSum"), index));
        rowDatas.addAll(this.listRyMinorityRecoveryDetails(minorityRecoveryTableVO.getLossGain(), fractionDigits, index, 2));
    }

    private void createDatasRegion(QueryParamsVO queryParamsVO, List<Object[]> rowDatas, List<CellRangeAddress> cellRangeAddresses, MinorityRecoveryTableVO minorityRecoveryTableVO) {
        if (minorityRecoveryTableVO == null) {
            return;
        }
        int[] index = new int[]{1};
        int fractionDigits = minorityRecoveryTableVO.getFractionDigits();
        rowDatas.add(this.getTotalRowData(cellRangeAddresses, minorityRecoveryTableVO.getAllTotal(), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.sum"), index));
        rowDatas.add(this.getTotalRowData(cellRangeAddresses, minorityRecoveryTableVO.getUnrealizedGainLossTotal(), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.unrealized.gain.or.loss"), index));
        rowDatas.add(this.getTotalRowData(cellRangeAddresses, minorityRecoveryTableVO.getDownStreamTotal(), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.downStreamTotal"), index));
        rowDatas.addAll(this.listMinorityRecoveryDetails(minorityRecoveryTableVO.getDownStream(), fractionDigits, index, 1));
        rowDatas.add(this.getTotalRowData(cellRangeAddresses, minorityRecoveryTableVO.getAgainstStreamTotal(), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.againstStreamTotal"), index));
        rowDatas.addAll(this.listMinorityRecoveryDetails(minorityRecoveryTableVO.getAgainstStream(), fractionDigits, index, 2));
        rowDatas.add(this.getTotalRowData(cellRangeAddresses, minorityRecoveryTableVO.getHorizStreamTotal(), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.horizStreamTotal"), index));
        rowDatas.addAll(this.listMinorityRecoveryDetails(minorityRecoveryTableVO.getHorizStream(), fractionDigits, index, 3));
        rowDatas.add(this.getTotalRowData(cellRangeAddresses, minorityRecoveryTableVO.getDeferredIncomeTaxTotal(), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.deferredIncomeTaxTotal"), index));
        rowDatas.addAll(this.listMinorityRecoveryDetails(minorityRecoveryTableVO.getDeferredIncomeTax(), fractionDigits, index, 4));
        rowDatas.add(this.getTotalRowData(cellRangeAddresses, minorityRecoveryTableVO.getLossGainTotal(), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.lossGainTotal"), index));
        rowDatas.addAll(this.listMinorityRecoveryDetails(minorityRecoveryTableVO.getLossGain(), fractionDigits, index, 5));
    }

    private List<Object[]> listMinorityRecoveryDetails(List<MinorityRecoveryRowVO> minorityRecoveryRowVOList, int fractionDigits, int[] index, int typeIndex) {
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        List managementAccountingFieldCodes = this.optionVO.getManagementAccountingFieldCodes();
        for (MinorityRecoveryRowVO rowVO : minorityRecoveryRowVOList) {
            ArrayList rowList = new ArrayList();
            Collections.addAll(rowList, String.valueOf(index[0]), rowVO.getOppUnitTitle(), rowVO.getUnitTitle(), rowVO.getSubjectTitle(), this.formateRateValue(rowVO.getOppUnitEquityRatio(), fractionDigits), this.formateRateValue(rowVO.getUnitEquityRatio(), fractionDigits));
            managementAccountingFieldCodes.forEach(fieldCode -> rowList.add(rowVO.getFields().get(fieldCode)));
            Collections.addAll(rowList, this.df.format(rowVO.getOffsetAmt()), this.formateRateValue(rowVO.getDiTaxRate(), 2), this.df.format(rowVO.getDiTaxAmt()), this.df.format(rowVO.getMinorityOffsetAmt()), this.df.format(rowVO.getMinorityDiTaxAmt()), this.df.format(rowVO.getMinorityTotalAmt()), this.df.format(rowVO.getLossGainAmt()));
            Object[] row = rowList.toArray();
            int filedSize = managementAccountingFieldCodes.size();
            switch (typeIndex) {
                case 1: {
                    row[11 + filedSize] = "--";
                    row[10 + filedSize] = "--";
                    row[9 + filedSize] = "--";
                    row[4] = "--";
                    break;
                }
                case 2: {
                    row[5] = "--";
                    break;
                }
                case 4: {
                    row[11 + filedSize] = "--";
                    row[10 + filedSize] = "--";
                    row[9 + filedSize] = "--";
                    break;
                }
                case 5: {
                    row[11 + filedSize] = "--";
                    row[10 + filedSize] = "--";
                    row[9 + filedSize] = "--";
                    row[8 + filedSize] = "--";
                    row[7 + filedSize] = "--";
                    break;
                }
            }
            rowDatas.add(row);
            index[0] = index[0] + 1;
        }
        return rowDatas;
    }

    private List<Object[]> listRyMinorityRecoveryDetails(List<MinorityRecoveryRowVO> minorityRecoveryRowVOList, int fractionDigits, int[] index, int typeIndex) {
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        List managementAccountingFieldCodes = this.optionVO.getManagementAccountingFieldCodes();
        for (MinorityRecoveryRowVO rowVO : minorityRecoveryRowVOList) {
            ArrayList rowList = new ArrayList();
            Collections.addAll(rowList, String.valueOf(index[0]), rowVO.getOppUnitTitle(), rowVO.getUnitTitle(), rowVO.getSubjectTitle());
            managementAccountingFieldCodes.forEach(fieldCode -> rowList.add(rowVO.getFields().get(fieldCode)));
            Collections.addAll(rowList, this.df.format(rowVO.getOffsetAmt()), this.formateRateValue(rowVO.getDiTaxRate(), 2), this.df.format(rowVO.getDiTaxAmt()), this.df.format(rowVO.getLossGainAmt()));
            Object[] row = rowList.toArray();
            int filedSize = managementAccountingFieldCodes.size();
            switch (typeIndex) {
                case 1: {
                    row[11 + filedSize] = "--";
                    row[10 + filedSize] = "--";
                    row[9 + filedSize] = "--";
                    row[4] = "--";
                    break;
                }
                case 2: {
                    row[6] = "--";
                    row[5] = "--";
                    break;
                }
                case 4: {
                    row[11 + filedSize] = "--";
                    row[10 + filedSize] = "--";
                    row[9 + filedSize] = "--";
                    break;
                }
                case 5: {
                    row[11 + filedSize] = "--";
                    row[10 + filedSize] = "--";
                    row[9 + filedSize] = "--";
                    row[8 + filedSize] = "--";
                    row[7 + filedSize] = "--";
                    break;
                }
            }
            rowDatas.add(row);
            index[0] = index[0] + 1;
        }
        return rowDatas;
    }

    private String formateRateValue(BigDecimal rateValue, int fractionDigits) {
        if (rateValue == null || BigDecimal.ZERO.compareTo(rateValue) == 0) {
            return new BigDecimal(0.1).setScale(fractionDigits, 4).toString().replace("1", "0") + "%";
        }
        rateValue = rateValue.multiply(new BigDecimal(100)).setScale(fractionDigits, 4);
        return rateValue.toString() + "%";
    }

    private Object[] getTotalRowData(List<CellRangeAddress> cellRangeAddresses, MinorityRecoveryRowVO rowVO, String title, int[] index) {
        List managementAccountingFieldCodes = this.optionVO.getManagementAccountingFieldCodes();
        ArrayList<String> row = new ArrayList<String>();
        Collections.addAll(row, String.valueOf(index[0]), title, "2", "3", "--", "--");
        managementAccountingFieldCodes.forEach(fieldCode -> row.add((String)rowVO.getFields().get(fieldCode)));
        String offsetAmt = rowVO.getOffsetAmt() != null ? this.df.format(rowVO.getOffsetAmt()) : "--";
        Collections.addAll(row, offsetAmt, "--", this.df.format(rowVO.getDiTaxAmt()), this.df.format(rowVO.getMinorityOffsetAmt()), this.df.format(rowVO.getMinorityDiTaxAmt()), this.df.format(rowVO.getMinorityTotalAmt()), this.df.format(rowVO.getLossGainAmt()));
        if (GcI18nUtil.getMessage((String)"gc.calculate.lossGain.lossGainTotal").equals(title)) {
            row.set(7 + managementAccountingFieldCodes.size(), "--");
            row.set(8 + managementAccountingFieldCodes.size(), "--");
        }
        if (GcI18nUtil.getMessage((String)"gc.calculate.lossGain.deferredIncomeTaxTotal").equals(title) || GcI18nUtil.getMessage((String)"gc.calculate.lossGain.lossGainTotal").equals(title)) {
            row.set(9 + managementAccountingFieldCodes.size(), "--");
            row.set(10 + managementAccountingFieldCodes.size(), "--");
            row.set(11 + managementAccountingFieldCodes.size(), "--");
        }
        cellRangeAddresses.add(new CellRangeAddress(index[0] + 1, index[0] + 1, 1, 3));
        index[0] = index[0] + 1;
        return row.toArray();
    }

    private Object[] getRyTotalRowData(List<CellRangeAddress> cellRangeAddresses, MinorityRecoveryRowVO rowVO, String title, int[] index) {
        List managementAccountingFieldCodes = this.optionVO.getManagementAccountingFieldCodes();
        ArrayList row = new ArrayList();
        Collections.addAll(row, String.valueOf(index[0]), title, "2", "3");
        managementAccountingFieldCodes.forEach(fieldCode -> row.add(rowVO.getFields().get(fieldCode)));
        Collections.addAll(row, this.df.format(rowVO.getOffsetAmt()), "--", this.df.format(rowVO.getDiTaxAmt()), this.df.format(rowVO.getLossGainAmt()));
        cellRangeAddresses.add(new CellRangeAddress(index[0] + 1, index[0] + 1, 1, 3));
        index[0] = index[0] + 1;
        return row.toArray();
    }

    private void createHeader(List<Object[]> rowDatas, List<CellRangeAddress> cellRangeAddresses) {
        Map<String, String> otherShowColumnCode2TitleMap = this.getManageAccColumns().stream().collect(Collectors.toMap(DesignFieldDefineVO::getKey, DesignFieldDefineVO::getLabel, (e1, e2) -> e1));
        List managementAccountingFieldCodes = this.optionVO.getManagementAccountingFieldCodes();
        ArrayList<String> firstRowHeaderCols = new ArrayList<String>();
        Collections.addAll(firstRowHeaderCols, GcI18nUtil.getMessage((String)"gc.calculate.lossGain.index"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.transaction.unit.title"), "2", GcI18nUtil.getMessage((String)"gc.calculate.lossGain.subject"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.opp.unit.equity.ratio"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.unitEquityRatio"));
        ArrayList<String> secondRowHeaderCols = new ArrayList<String>();
        Collections.addAll(secondRowHeaderCols, "1", GcI18nUtil.getMessage((String)"gc.calculate.lossGain.sellOppUnit"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.buyThisUnit"), "2", "3", "4");
        cellRangeAddresses.add(new CellRangeAddress(0, 1, 0, 0));
        cellRangeAddresses.add(new CellRangeAddress(0, 0, 1, 2));
        cellRangeAddresses.add(new CellRangeAddress(0, 1, 3, 3));
        cellRangeAddresses.add(new CellRangeAddress(0, 1, 4, 4));
        cellRangeAddresses.add(new CellRangeAddress(0, 1, 5, 5));
        int i = 6;
        for (String accountingFieldCode : managementAccountingFieldCodes) {
            firstRowHeaderCols.add(otherShowColumnCode2TitleMap.get(accountingFieldCode));
            secondRowHeaderCols.add(String.valueOf(new Random().nextInt()));
            cellRangeAddresses.add(new CellRangeAddress(0, 1, i, i));
            ++i;
        }
        cellRangeAddresses.add(new CellRangeAddress(0, 1, i, i));
        cellRangeAddresses.add(new CellRangeAddress(0, 0, i + 1, i + 2));
        cellRangeAddresses.add(new CellRangeAddress(0, 0, i + 3, i + 5));
        cellRangeAddresses.add(new CellRangeAddress(0, 1, i + 6, i + 6));
        Collections.addAll(firstRowHeaderCols, GcI18nUtil.getMessage((String)"gc.calculate.lossGain.offsetAmt"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.deferredIncomeTax"), "8", GcI18nUtil.getMessage((String)"gc.calculate.lossGain.minLossGainRecovery"), "10", "11", GcI18nUtil.getMessage((String)"gc.calculate.lossGain.lossGainAmt"));
        Collections.addAll(secondRowHeaderCols, "5", GcI18nUtil.getMessage((String)"gc.calculate.lossGain.rate"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.amount"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.minorityOffsetAmt"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.minorityDiTaxAmt"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.sum"), "6");
        rowDatas.add(firstRowHeaderCols.toArray());
        rowDatas.add(secondRowHeaderCols.toArray());
    }

    private void createRyHeader(List<Object[]> rowDatas, List<CellRangeAddress> cellRangeAddresses) {
        Map<String, String> otherShowColumnCode2TitleMap = this.getManageAccColumns().stream().collect(Collectors.toMap(DesignFieldDefineVO::getKey, DesignFieldDefineVO::getLabel, (e1, e2) -> e1));
        List managementAccountingFieldCodes = this.optionVO.getManagementAccountingFieldCodes();
        ArrayList<String> firstRowHeaderCols = new ArrayList<String>();
        Collections.addAll(firstRowHeaderCols, GcI18nUtil.getMessage((String)"gc.calculate.lossGain.index"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.transaction.unit.title"), "2", GcI18nUtil.getMessage((String)"gc.calculate.lossGain.subject"));
        ArrayList<String> secondRowHeaderCols = new ArrayList<String>();
        Collections.addAll(secondRowHeaderCols, "1", GcI18nUtil.getMessage((String)"gc.calculate.lossGain.sellOppUnit"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.buyThisUnit"), "2");
        cellRangeAddresses.add(new CellRangeAddress(0, 1, 0, 0));
        cellRangeAddresses.add(new CellRangeAddress(0, 0, 1, 2));
        cellRangeAddresses.add(new CellRangeAddress(0, 1, 3, 3));
        int i = 4;
        for (String accountingFieldCode : managementAccountingFieldCodes) {
            firstRowHeaderCols.add(otherShowColumnCode2TitleMap.get(accountingFieldCode));
            secondRowHeaderCols.add(String.valueOf(new Random().nextInt()));
            cellRangeAddresses.add(new CellRangeAddress(0, 1, i, i));
            ++i;
        }
        cellRangeAddresses.add(new CellRangeAddress(0, 1, i, i));
        cellRangeAddresses.add(new CellRangeAddress(0, 0, i + 1, i + 2));
        cellRangeAddresses.add(new CellRangeAddress(0, 1, i + 3, i + 3));
        Collections.addAll(firstRowHeaderCols, GcI18nUtil.getMessage((String)"gc.calculate.lossGain.offsetAmt"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.deferredIncomeTax"), "6", GcI18nUtil.getMessage((String)"gc.calculate.lossGain.lossGainAmt"));
        Collections.addAll(secondRowHeaderCols, "5", GcI18nUtil.getMessage((String)"gc.calculate.lossGain.rate"), GcI18nUtil.getMessage((String)"gc.calculate.lossGain.amount"), "6");
        rowDatas.add(firstRowHeaderCols.toArray());
        rowDatas.add(secondRowHeaderCols.toArray());
    }

    class OffsetMatchRow {
        private List<GcOffSetVchrItemAdjustEO> headTwoRow = new ArrayList<GcOffSetVchrItemAdjustEO>();
        private List<GcOffSetVchrItemAdjustEO> tailRow = new ArrayList<GcOffSetVchrItemAdjustEO>();

        OffsetMatchRow() {
        }
    }
}

