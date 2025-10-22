/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.billcore.service.CommonBillService
 *  com.jiuqi.gcreport.consolidatedsystem.common.CSConst
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperDataVO
 *  com.jiuqi.gcreport.investworkpaper.vo.QueryCondition
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.invest.investworkpaper.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.billcore.service.CommonBillService;
import com.jiuqi.gcreport.consolidatedsystem.common.CSConst;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.investbill.enums.InvestInfoEnum;
import com.jiuqi.gcreport.invest.investbill.service.InvestBillService;
import com.jiuqi.gcreport.invest.investworkpaper.util.GcInvestWorkpaperUtils;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperColumnVO;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperDataVO;
import com.jiuqi.gcreport.investworkpaper.vo.QueryCondition;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvestWorkPaperQueryTask {
    @Autowired
    private InvestBillService investBillService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskCacheService;
    @Autowired
    private GcOffSetAppOffsetService offSetItemAdjustService;
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private DataModelService dataModelService;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    private static List<InvestWorkPaperColumnVO> listDefaultColumns(List<Map<String, Object>> i18nColumns) {
        ArrayList<InvestWorkPaperColumnVO> defaultColumns = new ArrayList<InvestWorkPaperColumnVO>();
        defaultColumns.add(new InvestWorkPaperColumnVO("INVESTEDUNIT", InvestWorkPaperQueryTask.getI18nColumnTitle(i18nColumns, "INVESTEDUNIT", GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.head.investEdunit")), "left", (Object)"left", 200));
        defaultColumns.add(new InvestWorkPaperColumnVO("ACCOUNTINGMETHOD", InvestWorkPaperQueryTask.getI18nColumnTitle(i18nColumns, "ACCOUNTINGMETHOD", GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.head.accountingmethod")), "left"));
        defaultColumns.add(new InvestWorkPaperColumnVO("ENDBOOKBALANCE", InvestWorkPaperQueryTask.getI18nColumnTitle(i18nColumns, "ENDBOOKBALANCE", GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.head.endbookbalance")), "right"));
        defaultColumns.add(new InvestWorkPaperColumnVO("ENDINVSTDEVALUEPREP", InvestWorkPaperQueryTask.getI18nColumnTitle(i18nColumns, "ENDINVSTDEVALUEPREP", GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.head.endinvestdevalueprep")), "right"));
        defaultColumns.add(new InvestWorkPaperColumnVO("ENDEQUITYRATIO", InvestWorkPaperQueryTask.getI18nColumnTitle(i18nColumns, "ENDEQUITYRATIO", GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.head.endequityratio")), "right"));
        return defaultColumns;
    }

    protected void getInvestPaperShowColumns(List<Map<String, String>> showColumns, List<InvestWorkPaperColumnVO> columns) {
        CommonBillService commonBillService = (CommonBillService)SpringContextUtils.getBean(CommonBillService.class);
        List i18nColumns = commonBillService.listColumns("GC_INVESTBILL", "GCBILL_B_INVESTBILL");
        if (CollectionUtils.isEmpty(showColumns)) {
            columns.addAll(InvestWorkPaperQueryTask.listDefaultColumns(i18nColumns));
        } else {
            Set<String> investNumberFieldSet = this.listInvestNumberFieldSet();
            for (Map<String, String> fairValueAdjustShowColumn : showColumns) {
                String columnCode = fairValueAdjustShowColumn.get("columnCode");
                String defalutColumnTitle = fairValueAdjustShowColumn.get("columnTitle");
                String columnTitle = InvestWorkPaperQueryTask.getI18nColumnTitle(i18nColumns, columnCode, defalutColumnTitle);
                if ("UNITCODE".equals(columnCode) || "INVESTEDUNIT".equals(columnCode)) {
                    columns.add(new InvestWorkPaperColumnVO(columnCode, columnTitle, "left", (Object)"left", 200));
                    continue;
                }
                String align = investNumberFieldSet.contains(columnCode) ? "right" : "left";
                columns.add(new InvestWorkPaperColumnVO(columnCode, columnTitle, align));
            }
        }
    }

    private static String getI18nColumnTitle(List<Map<String, Object>> i18nColumns, String columnCode, String defalutColumnTitle) {
        Optional<Map> i18nColumnsOptional = i18nColumns.stream().filter(i18nColumn -> columnCode.equals(i18nColumn.get("key"))).findFirst();
        String columnTitle = null;
        if (i18nColumnsOptional.isPresent()) {
            columnTitle = ConverterUtils.getAsString(i18nColumnsOptional.get().get("label"));
        }
        if (StringUtils.isEmpty(columnTitle)) {
            columnTitle = defalutColumnTitle;
        }
        return columnTitle;
    }

    protected void getInvestWorkPaperSubjectColumns(QueryCondition condition, List<Map<String, Object>> showSubjects, List<InvestWorkPaperColumnVO> columns, RuleTypeEnum ruleTypeEnum) {
        ConsolidatedSubjectService subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        String reportSystemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
        ArrayList<InvestWorkPaperColumnVO> subjectChildrenColumns = new ArrayList<InvestWorkPaperColumnVO>();
        if (CollectionUtils.isEmpty(showSubjects)) {
            List<AbstractUnionRule> rules = this.listCurrShowTypeAllRules(condition, ruleTypeEnum);
            List<ConsolidatedSubjectEO> list = this.listAllSubjects(condition);
            Set<String> secondaryCodes = this.listSecondaryCodes(list);
            Map<String, ConsolidatedSubjectEO> subjectCode2SubjectEOMap = list.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, eo -> eo, (key1, key2) -> key1));
            List<String> subjectColumnCodeList = this.listCurrRuleRelateSubjectCodes(rules, secondaryCodes, subjectCode2SubjectEOMap);
            for (String columnCode : subjectColumnCodeList) {
                String subjectTitle = subjectCode2SubjectEOMap.get(columnCode) != null ? subjectCode2SubjectEOMap.get(columnCode).getTitle() : columnCode;
                subjectChildrenColumns.add(new InvestWorkPaperColumnVO(columnCode, subjectTitle, "right"));
            }
        } else {
            for (Map map : showSubjects) {
                String subjectCode = ConverterUtils.getAsString(map.get("code"), (String)"");
                String subjectTitle = subjectService.getTitleByCode(reportSystemId, subjectCode);
                subjectChildrenColumns.add(new InvestWorkPaperColumnVO(subjectCode, subjectTitle, "right"));
            }
        }
        InvestWorkPaperColumnVO subjectColumn = new InvestWorkPaperColumnVO("OFFSETAMT", GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.head.offsetamt"), "center");
        subjectChildrenColumns.stream().forEach(subjectChildColumn -> subjectChildColumn.setParentProp(subjectColumn.getProp()));
        subjectColumn.setChildren(subjectChildrenColumns);
        columns.add(subjectColumn);
    }

    private Set<String> listSecondaryCodes(List<ConsolidatedSubjectEO> allSubjectList) {
        HashSet<String> secondaryCodes = new HashSet<String>(16);
        Map<String, Set<ConsolidatedSubjectEO>> parentSubjectCode2DirectChildMap = this.parentSubjectCode2DirectChildMap(allSubjectList);
        List primaryNodeCodeList = allSubjectList.stream().filter(suject -> StringUtils.isEmpty((String)suject.getParentCode()) || CSConst.SUBJECT_TOP_CODE.equals(suject.getParentCode())).map(ConsolidatedSubjectEO::getCode).collect(Collectors.toList());
        for (String code : primaryNodeCodeList) {
            Set<ConsolidatedSubjectEO> childrenSubjectSet = parentSubjectCode2DirectChildMap.get(code);
            if (CollectionUtils.isEmpty(childrenSubjectSet)) continue;
            secondaryCodes.addAll(childrenSubjectSet.stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toSet()));
        }
        return secondaryCodes;
    }

    protected List<String> listCurrRuleRelateSubjectCodes(List<AbstractUnionRule> rules, Set<String> secondaryCodes, Map<String, ConsolidatedSubjectEO> subjectCode2SubjectEOMap) {
        ArrayList subjectCodes = new ArrayList();
        for (AbstractUnionRule rule : rules) {
            subjectCodes.addAll(rule.getSrcDebitSubjectCodeList());
            subjectCodes.addAll(rule.getSrcCreditSubjectCodeList());
        }
        HashSet<String> subjectCodesSet = new HashSet<String>();
        Iterator iterator = subjectCodes.iterator();
        block1: while (iterator.hasNext()) {
            String subjectCode;
            String parentId = subjectCode = (String)iterator.next();
            while (parentId != null && !CSConst.SUBJECT_TOP_CODE.equals(parentId)) {
                ConsolidatedSubjectEO parentSubject = subjectCode2SubjectEOMap.get(parentId);
                if (parentSubject != null && secondaryCodes.contains(parentSubject.getCode())) {
                    subjectCodesSet.add(parentSubject.getCode());
                    continue block1;
                }
                parentId = parentSubject == null ? null : parentSubject.getParentCode();
            }
        }
        return new ArrayList(subjectCodesSet).stream().sorted(Comparator.comparing(code -> code)).collect(Collectors.toList());
    }

    protected List<InvestWorkPaperDataVO> listInvestWorkPaperDataVOS(QueryCondition condition, List<AbstractUnionRule> rules, List<Map<String, Object>> showSubjects, List<Map<String, String>> showColumns, List<Map<String, Object>> investBills) {
        Map<String, List<Map<String, Object>>> offsetGroups = this.getOffsetGroups(condition, rules);
        String reportSystemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
        List<String> subjectColumnCodeList = this.listSubjectColumnCodes(condition, rules, showSubjects);
        Map<String, Set<String>> subjectAllChildrenCacheMap = this.getAllChildrenSubjectCacheMap(subjectColumnCodeList, reportSystemId);
        List<InvestWorkPaperDataVO> investWorkPaperDataVOList = this.assembleInvestWorkPaperDataVOS(condition, rules, showColumns, investBills, offsetGroups, subjectColumnCodeList, subjectAllChildrenCacheMap);
        return investWorkPaperDataVOList;
    }

    private List<InvestWorkPaperDataVO> assembleInvestWorkPaperDataVOS(QueryCondition condition, List<AbstractUnionRule> rules, List<Map<String, String>> showColumns, List<Map<String, Object>> investBills, Map<String, List<Map<String, Object>>> offsetGroups, List<String> subjectColumnCodeList, Map<String, Set<String>> subjectAllChildrenCacheMap) {
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        ArrayList<InvestWorkPaperDataVO> investWorkPaperDataVOList = new ArrayList<InvestWorkPaperDataVO>();
        Set<String> investNumberFieldSet = this.listInvestNumberFieldSet();
        HashMap<String, BigDecimal> totalOffsetValue = new HashMap<String, BigDecimal>();
        for (Map<String, Object> investBill : investBills) {
            if (condition.getShowRuleDetails().booleanValue()) {
                totalOffsetValue = new HashMap();
                for (AbstractUnionRule rule : rules) {
                    InvestWorkPaperDataVO vo = this.initInvestWorkPaperDataVo(showColumns, tool, investBill, investNumberFieldSet);
                    if (vo == null) continue;
                    vo.setRuleId(rule.getId());
                    vo.setRuleTitle(rule.getLocalizedName());
                    this.setOffsetValue(offsetGroups, subjectColumnCodeList, vo, subjectAllChildrenCacheMap, totalOffsetValue);
                    investWorkPaperDataVOList.add(vo);
                }
                investWorkPaperDataVOList.add(this.getTotalWorkPaperRow(totalOffsetValue, showColumns, tool, investBill, investNumberFieldSet));
                continue;
            }
            InvestWorkPaperDataVO vo = this.initInvestWorkPaperDataVo(showColumns, tool, investBill, investNumberFieldSet);
            if (vo == null) continue;
            this.setOffsetValue(offsetGroups, subjectColumnCodeList, vo, subjectAllChildrenCacheMap, totalOffsetValue);
            investWorkPaperDataVOList.add(vo);
        }
        return investWorkPaperDataVOList;
    }

    protected InvestWorkPaperDataVO getTotalWorkPaperRow(Map<String, BigDecimal> totalOffsetValue, List<Map<String, String>> showColumns, GcOrgCenterService tool, Map<String, Object> investBill, Set<String> investNumberFieldSet) {
        InvestWorkPaperDataVO vo = this.initInvestWorkPaperDataVo(showColumns, tool, investBill, investNumberFieldSet);
        vo.setRuleTitle(GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.export.total"));
        Map otherColumns = vo.getOtherColumnsValue();
        for (Map.Entry<String, BigDecimal> entry : totalOffsetValue.entrySet()) {
            otherColumns.put(entry.getKey(), this.df.format(entry.getValue()));
        }
        vo.setOtherColumnsValue(otherColumns);
        return vo;
    }

    protected Map<String, Set<String>> getAllChildrenSubjectCacheMap(List<String> subjectColumnCodeList, String reportSystemId) {
        HashMap<String, Set<String>> childrenSubjectCacheMap = new HashMap<String, Set<String>>();
        for (String columnCode : subjectColumnCodeList) {
            Set childrenCodes = this.subjectService.listAllChildrenCodes(columnCode, reportSystemId);
            if (CollectionUtils.isEmpty((Collection)childrenCodes)) continue;
            childrenSubjectCacheMap.put(columnCode, childrenCodes);
        }
        return childrenSubjectCacheMap;
    }

    protected Set<String> listInvestNumberFieldSet() {
        Set<String> investNumberFieldSet;
        try {
            TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByName("GC_INVESTBILL");
            if (tableDefine == null) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.msg.noMasterTable") + "GC_INVESTBILL");
            }
            List fieldDefines = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            investNumberFieldSet = fieldDefines.stream().filter(field -> field.getColumnType() == ColumnModelType.DOUBLE || field.getColumnType() == ColumnModelType.INTEGER || field.getColumnType() == ColumnModelType.BIGDECIMAL).map(ColumnModelDefine::getName).collect(Collectors.toSet());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.investworkapper.msg.masterTableException") + e.getMessage());
        }
        return investNumberFieldSet;
    }

    protected List<AbstractUnionRule> listCurrShowTypeAllRules(QueryCondition condition, RuleTypeEnum ruleTypeEnum) {
        return this.listAbstractUnionRules(condition, Arrays.asList(ruleTypeEnum.getCode()));
    }

    protected List<AbstractUnionRule> listAbstractUnionRules(QueryCondition condition, List<String> ruleTypes) {
        List rules = this.unionRuleService.selectRuleListBySchemeIdAndRuleTypes(condition.getSchemeId(), condition.getPeriodStr(), ruleTypes);
        List<AbstractUnionRule> initRules = rules.stream().filter(rule -> rule.getInitTypeFlag()).collect(Collectors.toList());
        initRules.addAll(rules.stream().filter(rule -> rule.getInitTypeFlag() == false).collect(Collectors.toList()));
        return initRules;
    }

    private List<ConsolidatedSubjectEO> listAllSubjects(QueryCondition condition) {
        String reportSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
        return this.subjectService.listAllSubjectsBySystemId(reportSystemId);
    }

    private Map<String, Set<ConsolidatedSubjectEO>> parentSubjectCode2DirectChildMap(List<ConsolidatedSubjectEO> allSubjects) {
        HashMap<String, Set<ConsolidatedSubjectEO>> parentSubjectCode2DirectChildCodeMap = new HashMap<String, Set<ConsolidatedSubjectEO>>(128);
        for (ConsolidatedSubjectEO subject : allSubjects) {
            String parentCode = subject.getParentCode();
            if (StringUtils.isEmpty((String)parentCode) || CSConst.SUBJECT_TOP_CODE.equals(parentCode)) continue;
            if (!parentSubjectCode2DirectChildCodeMap.containsKey(parentCode)) {
                parentSubjectCode2DirectChildCodeMap.put(parentCode, new HashSet());
            }
            ((Set)parentSubjectCode2DirectChildCodeMap.get(parentCode)).add(subject);
        }
        return parentSubjectCode2DirectChildCodeMap;
    }

    protected void setOffsetValue(Map<String, List<Map<String, Object>>> offsetGroups, List<String> subjectColumnCodeList, InvestWorkPaperDataVO vo, Map<String, Set<String>> subjectAllChildrenCacheMap, Map<String, BigDecimal> totalOffsetValue) {
        Map otherColumnKey2ValueMap = vo.getOtherColumnsValue();
        for (String columnCode : subjectColumnCodeList) {
            Set<String> childrenCodes = subjectAllChildrenCacheMap.get(columnCode);
            BigDecimal offsetValue = this.sumOffsetGroupValue(offsetGroups, vo, columnCode, childrenCodes);
            if (!totalOffsetValue.containsKey(columnCode)) {
                totalOffsetValue.put(columnCode, BigDecimal.ZERO);
            }
            totalOffsetValue.put(columnCode, totalOffsetValue.get(columnCode).add(offsetValue));
            otherColumnKey2ValueMap.put(columnCode, this.df.format(offsetValue));
        }
    }

    protected Map<String, List<Map<String, Object>>> getOffsetGroups(QueryCondition condition, List<AbstractUnionRule> rules) {
        QueryParamsVO queryParamsVO = this.convertQueryParams(condition, rules);
        List<Map<String, Object>> offsetDatas = this.sumOffsetValueGroupByRule(queryParamsVO);
        return this.groupOffsetDats(condition, offsetDatas);
    }

    protected Map<String, List<Map<String, Object>>> groupOffsetDats(QueryCondition condition, List<Map<String, Object>> currOffsetDatas) {
        Map<String, List<Map<String, Object>>> offsetGroups = condition.getShowRuleDetails() != false ? currOffsetDatas.stream().collect(Collectors.groupingBy(objectMap -> objectMap.get("UNITID") + "_" + objectMap.get("OPPUNITID") + "_" + objectMap.get("RULEID") + "_" + objectMap.get("SUBJECTCODE"))) : currOffsetDatas.stream().collect(Collectors.groupingBy(objectMap -> objectMap.get("UNITID") + "_" + objectMap.get("OPPUNITID") + "_" + objectMap.get("SUBJECTCODE")));
        return offsetGroups;
    }

    protected List<String> listSubjectColumnCodes(QueryCondition condition, List<AbstractUnionRule> rules, List<Map<String, Object>> showSubjects) {
        List<String> subjectColumnCodeList;
        if (!CollectionUtils.isEmpty(showSubjects)) {
            subjectColumnCodeList = showSubjects.stream().map(item -> (String)item.get("code")).collect(Collectors.toList());
        } else {
            List<ConsolidatedSubjectEO> allSubjectList = this.listAllSubjects(condition);
            Set<String> secondaryCodes = this.listSecondaryCodes(allSubjectList);
            Map<String, ConsolidatedSubjectEO> subjectCode2SubjectEOMap = allSubjectList.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, eo -> eo, (key1, key2) -> key1));
            subjectColumnCodeList = this.listCurrRuleRelateSubjectCodes(rules, secondaryCodes, subjectCode2SubjectEOMap);
        }
        return subjectColumnCodeList;
    }

    protected BigDecimal sumOffsetGroupValue(Map<String, List<Map<String, Object>>> offsetGroups, InvestWorkPaperDataVO vo, String columnCode, Set<String> childrenCodes) {
        ArrayList<Map<String, Object>> offsetGroup = new ArrayList<Map<String, Object>>();
        String offsetGroupKey = vo.getInvestUnitId() + "_" + vo.getInvestedUnitId();
        this.addOffsetGroup(offsetGroups, offsetGroup, offsetGroupKey, vo, columnCode);
        offsetGroupKey = vo.getInvestedUnitId() + "_" + vo.getInvestUnitId();
        this.addOffsetGroup(offsetGroups, offsetGroup, offsetGroupKey, vo, columnCode);
        if (childrenCodes != null) {
            for (String childrenSubjectCode : childrenCodes) {
                offsetGroupKey = vo.getInvestUnitId() + "_" + vo.getInvestedUnitId();
                this.addOffsetGroup(offsetGroups, offsetGroup, offsetGroupKey, vo, childrenSubjectCode);
                offsetGroupKey = vo.getInvestedUnitId() + "_" + vo.getInvestUnitId();
                this.addOffsetGroup(offsetGroups, offsetGroup, offsetGroupKey, vo, childrenSubjectCode);
            }
        }
        BigDecimal offsetValue = BigDecimal.ZERO;
        for (Map map : offsetGroup) {
            BigDecimal creditValue = new BigDecimal(map.get("CREDITVALUE").toString());
            BigDecimal debitvalue = new BigDecimal(map.get("DEBITVALUE").toString());
            offsetValue = offsetValue.add(debitvalue.subtract(creditValue).multiply(new BigDecimal(map.get("SUBJECTORIENT").toString())));
        }
        return offsetValue;
    }

    private void addOffsetGroup(Map<String, List<Map<String, Object>>> offsetGroups, List<Map<String, Object>> offsetGroup, String offsetGroupKey, InvestWorkPaperDataVO vo, String columnCode) {
        List<Map<String, Object>> offsets;
        if (!StringUtils.isEmpty((String)vo.getRuleId())) {
            offsetGroupKey = offsetGroupKey + "_" + vo.getRuleId();
        }
        if ((offsets = offsetGroups.get(offsetGroupKey = offsetGroupKey + "_" + columnCode)) != null) {
            offsetGroup.addAll(offsets);
        }
    }

    protected InvestWorkPaperDataVO initInvestWorkPaperDataVo(List<Map<String, String>> showColumns, GcOrgCenterService tool, Map<String, Object> investBill, Set<String> numberFieldSet) {
        InvestWorkPaperDataVO vo = new InvestWorkPaperDataVO();
        vo.setInvestBillId((String)investBill.get("ID"));
        GcOrgCacheVO investOrg = tool.getOrgByCode((String)investBill.get("UNITCODE_ID"));
        GcOrgCacheVO investedOrg = tool.getOrgByCode((String)investBill.get("INVESTEDUNIT_ID"));
        if (investOrg == null || investedOrg == null) {
            return null;
        }
        vo.setInvestUnitTitle(investOrg.getTitle());
        vo.setInvestUnitId(investOrg.getId());
        vo.setInvestedUnitTitle(investedOrg.getTitle());
        vo.setInvestedUnitId(investedOrg.getId());
        Map otherColumnKey2ValueMap = vo.getOtherColumnsValue();
        if (CollectionUtils.isEmpty(showColumns)) {
            otherColumnKey2ValueMap.put("ACCOUNTINGMETHOD", (String)investBill.get("ACCOUNTINGMETHOD"));
            otherColumnKey2ValueMap.put("ENDBOOKBALANCE", (String)investBill.get("ENDBOOKBALANCE"));
            otherColumnKey2ValueMap.put("ENDINVSTDEVALUEPREP", (String)investBill.get("ENDINVSTDEVALUEPREP"));
            otherColumnKey2ValueMap.put("ENDEQUITYRATIO", (String)investBill.get("ENDEQUITYRATIO"));
            otherColumnKey2ValueMap.put("COLUMNNUMBERS", "5");
        } else {
            otherColumnKey2ValueMap.put("COLUMNNUMBERS", String.valueOf(showColumns.size()));
            for (Map<String, String> column : showColumns) {
                Object columnValue = investBill.get(column.get("columnCode"));
                if (columnValue == null) {
                    columnValue = numberFieldSet.contains(column.get("columnCode")) ? this.df.format(0L) : "";
                }
                otherColumnKey2ValueMap.put(column.get("columnCode"), columnValue.toString());
            }
        }
        return vo;
    }

    public QueryParamsVO convertQueryParams(QueryCondition condition, List<AbstractUnionRule> rules) {
        List ruleIds = rules.stream().map(AbstractUnionRule::getId).collect(Collectors.toList());
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        BeanUtils.copyProperties(condition, queryParamsVO);
        queryParamsVO.setOrgId(condition.getOrgId());
        queryParamsVO.setRules(ruleIds);
        queryParamsVO.setSelectAdjustCode(condition.getSelectAdjustCode());
        String reportSystemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
        queryParamsVO.setSystemId(reportSystemId);
        return queryParamsVO;
    }

    protected List<Map<String, Object>> getInvestBills(QueryCondition condition, String baseUnitId) {
        return this.getInvestBills(condition, true, baseUnitId);
    }

    protected List<Map<String, Object>> getInvestBills(QueryCondition condition, boolean isBaseUnit, String baseUnitId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        YearPeriodDO yearPeriodDO = yp.formatYP();
        params.put("acctYear", String.valueOf(yearPeriodDO.getYear()));
        params.put("acctPeriod", String.valueOf(yearPeriodDO.getPeriod()));
        params.put("periodStr", condition.getPeriodStr());
        params.put("mergeUnit", condition.getOrgId());
        params.put("pageNum", -1);
        params.put("pageSize", -1);
        HashMap<String, String> mergeRange = new HashMap<String, String>();
        mergeRange.put("mergeRange", "mergeLevel");
        params.put("filterParam", mergeRange);
        List investBills = this.investBillService.listInvestBills(params).getList();
        if (isBaseUnit) {
            return investBills.stream().filter(item -> InvestInfoEnum.DIRECT.getTitle().equals(item.get("MERGETYPE"))).collect(Collectors.toList());
        }
        return investBills.stream().filter(item -> InvestInfoEnum.INDIRECT.getTitle().equals(item.get("MERGETYPE"))).collect(Collectors.toList());
    }

    protected void createInvestWorkExcelHeader(ExportExcelSheet exportExcelSheet, Map<String, Object> cellStyleMap, List<Object[]> rowDatas, List<InvestWorkPaperColumnVO> investWorkPaperColumns) {
        AtomicInteger col = new AtomicInteger();
        ArrayList<String> headerCols = new ArrayList<String>();
        ArrayList<String> headerChildrenCols = new ArrayList<String>();
        Map headCellStyles = exportExcelSheet.getHeadCellStyleCache();
        List rangeAddresses = exportExcelSheet.getCellRangeAddresses();
        for (int i = 0; i < investWorkPaperColumns.size(); ++i) {
            InvestWorkPaperColumnVO column = investWorkPaperColumns.get(i);
            headerCols.add(column.getLabel());
            List children = investWorkPaperColumns.get(i).getChildren();
            if (children == null) {
                headerChildrenCols.add(String.valueOf(i));
                rangeAddresses.add(new CellRangeAddress(0, 1, col.get(), col.get()));
                col.getAndIncrement();
                if ("left".equals(column.getAlign())) {
                    headCellStyles.put(i, (CellStyle)cellStyleMap.get("headText"));
                    continue;
                }
                if (!"right".equals(column.getAlign())) continue;
                headCellStyles.put(i, (CellStyle)cellStyleMap.get("headAmount"));
                continue;
            }
            int start = col.get();
            for (InvestWorkPaperColumnVO aChildren : children) {
                int childrenIndex = children.indexOf(aChildren);
                if (childrenIndex != 0) {
                    headerCols.add(childrenIndex + "_temp");
                }
                headCellStyles.put(col.get(), (CellStyle)cellStyleMap.get("headAmount"));
                col.getAndIncrement();
                headerChildrenCols.add(String.valueOf(aChildren.getLabel()));
            }
            rangeAddresses.add(new CellRangeAddress(0, 0, start, col.get() - 1));
        }
        headerCols.add("1");
        headerChildrenCols.add("2");
        rowDatas.add(headerCols.toArray());
        rowDatas.add(headerChildrenCols.toArray());
    }

    protected void exportInvestWokrPaperDatsRange(ExportExcelSheet exportExcelSheet, List<Object[]> rowDatas, List<InvestWorkPaperColumnVO> headerColumns, List<InvestWorkPaperDataVO> workPaperDatas) {
        List cellRangeAddresses = exportExcelSheet.getCellRangeAddresses();
        headerColumns = this.listAllHeaderColumns(headerColumns);
        String originInvestBillId = "";
        int startRowIndex = 2;
        boolean interval = false;
        for (int i = 0; i < workPaperDatas.size(); ++i) {
            boolean notEqualsLastData;
            InvestWorkPaperDataVO data = workPaperDatas.get(i);
            Object[] dataRow = new Object[headerColumns.size() + 1];
            Map columnsValueMap = data.getOtherColumnsValue();
            boolean bl = notEqualsLastData = originInvestBillId == null || !originInvestBillId.equals(data.getInvestBillId());
            if (notEqualsLastData) {
                originInvestBillId = data.getInvestBillId();
                interval = !interval;
            }
            dataRow[headerColumns.size()] = interval;
            for (int j = 0; j < headerColumns.size(); ++j) {
                InvestWorkPaperColumnVO column = headerColumns.get(j);
                dataRow[j] = columnsValueMap.get(column.getProp());
                if (!notEqualsLastData || i == 0 || columnsValueMap.get("COLUMNNUMBERS") == null || j >= Integer.valueOf((String)columnsValueMap.get("COLUMNNUMBERS"))) continue;
                cellRangeAddresses.add(new CellRangeAddress(startRowIndex, i + 1, j, j));
            }
            if (notEqualsLastData && i != 0) {
                startRowIndex = i + 2;
            }
            rowDatas.add(dataRow);
        }
        if (workPaperDatas.size() > 1) {
            int columnNum = Integer.valueOf((String)workPaperDatas.get(1).getOtherColumnsValue().get("COLUMNNUMBERS"));
            for (int j = 0; j < headerColumns.size(); ++j) {
                if (j >= columnNum) continue;
                cellRangeAddresses.add(new CellRangeAddress(startRowIndex, workPaperDatas.size() + 1, j, j));
            }
        }
    }

    private List<InvestWorkPaperColumnVO> listAllHeaderColumns(List<InvestWorkPaperColumnVO> headerColumns) {
        ArrayList<InvestWorkPaperColumnVO> columns = new ArrayList<InvestWorkPaperColumnVO>();
        for (InvestWorkPaperColumnVO column : headerColumns) {
            if (CollectionUtils.isEmpty((Collection)column.getChildren())) {
                columns.add(column);
                continue;
            }
            columns.addAll(column.getChildren());
        }
        return columns;
    }

    protected QueryParamsVO getOffsetQueryParams(QueryCondition condition, List<AbstractUnionRule> rules) {
        QueryParamsVO queryParamsVO = this.convertQueryParams(condition, rules);
        if (condition.getShowRuleDetails().booleanValue() && !StringUtils.isEmpty((String)condition.getRuleId())) {
            queryParamsVO.setRules(Collections.singletonList(condition.getRuleId()));
        }
        queryParamsVO.setSubjectCodes(Collections.singletonList(condition.getColumnProp()));
        ArrayList<String> unitIds = new ArrayList<String>();
        unitIds.add(condition.getInvestUnitId());
        unitIds.add(condition.getInvestedUnitId());
        queryParamsVO.setUnitIdList(unitIds);
        queryParamsVO.setOppUnitIdList(unitIds);
        return queryParamsVO;
    }

    protected Pagination<Map<String, Object>> listOffsetDatas(QueryCondition condition, Pagination<Map<String, Object>> offsetPage) {
        List offsetDats = offsetPage.getContent();
        if ("2".equals(condition.getPentrateType())) {
            String reportSystemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
            Set childrenCodes = this.subjectService.listAllChildrenCodesContainsSelf(Collections.singletonList(condition.getColumnProp()), reportSystemId);
            offsetDats = offsetDats.stream().filter(data -> childrenCodes.contains(data.get("SUBJECTCODE"))).collect(Collectors.toList());
        }
        if (condition.getShowRuleDetails().booleanValue() && !StringUtils.isEmpty((String)condition.getRuleId())) {
            offsetDats = offsetDats.stream().filter(offsetData -> condition.getRuleId().equals(offsetData.get("RULEID"))).collect(Collectors.toList());
        }
        this.pageOffset(condition, offsetPage, offsetDats);
        return offsetPage;
    }

    protected void pageOffset(QueryCondition condition, Pagination<Map<String, Object>> offsetPage, List<Map<String, Object>> offsetDats) {
        Set<String> mRecids = offsetDats.stream().map(offset -> (String)offset.get("MRECID")).collect(Collectors.toSet());
        offsetPage.setTotalElements(Integer.valueOf(mRecids.size()));
        GcInvestWorkpaperUtils.pageOffsetByMrecids(condition.getPageNum(), condition.getPageSize(), mRecids);
        offsetDats = offsetDats.stream().filter(offset -> mRecids.contains(offset.get("MRECID"))).collect(Collectors.toList());
        offsetDats = GcInvestWorkpaperUtils.setRowSpanAndSort(offsetDats);
        offsetPage.setContent(offsetDats);
    }

    public List<Map<String, Object>> sumOffsetValueGroupByRule(QueryParamsVO queryParamsVO) {
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        String offsetValueStr = "\nsum(record.offset_DEBIT) as debitValue,sum(record.offset_Credit) as creditValue  ";
        String selectFields = " record.unitid,record.oppUnitId,record.ruleId,record.subjectCode,record.subjectOrient," + offsetValueStr;
        String groupStr = " record.unitid,record.oppUnitId,record.ruleId,record.subjectCode,record.subjectOrient";
        return this.offsetCoreService.sumOffsetValueGroupBy(queryParamsDTO, selectFields, groupStr);
    }
}

