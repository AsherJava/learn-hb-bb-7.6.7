/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.financialcheckapi.offset.MdSubjectAgingDTO
 *  com.jiuqi.gcreport.financialcheckapi.offset.SubjectInfoVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService
 *  com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.org.impl.period.PeriodTypeEnum
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.GcOffsetInputAdjustEntryService;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl.MdAgeLoader;
import com.jiuqi.gcreport.financialcheckapi.offset.MdSubjectAgingDTO;
import com.jiuqi.gcreport.financialcheckapi.offset.SubjectInfoVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService;
import com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.org.impl.period.PeriodTypeEnum;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class GcOffsetInputAdjustEntryServiceImpl
implements GcOffsetInputAdjustEntryService {
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private GcInputDataOffsetItemService gcOffsetAppInputDataService;

    @Override
    public List<SubjectInfoVO> listSubjectInfo(String systemId) {
        List consolidatedTasks = this.consolidatedTaskService.getConsolidatedTasks(systemId);
        List consolidatedSubjectVOS = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        return consolidatedSubjectVOS.stream().map(eo -> this.convertToSubjectInfo((ConsolidatedSubjectEO)eo, (ConsolidatedTaskVO)consolidatedTasks.get(0))).collect(Collectors.toList());
    }

    private SubjectInfoVO convertToSubjectInfo(ConsolidatedSubjectEO eo, ConsolidatedTaskVO consolidatedTaskVO) {
        SubjectInfoVO subjectInfo = new SubjectInfoVO();
        BeanUtils.copyProperties(eo, subjectInfo);
        PeriodTypeEnum periodTypeEnum = PeriodTypeEnum.findByName((String)consolidatedTaskVO.getPeriodTypeTitle());
        Map<String, MdSubjectAgingDTO> subjectCode2SubjectAgingMap = MdAgeLoader.getCode2SubjectAgingMap(String.valueOf(periodTypeEnum.getCode()));
        if (subjectCode2SubjectAgingMap.get(eo.getCode()) != null) {
            subjectInfo.setMdAgingList(subjectCode2SubjectAgingMap.get(eo.getCode()).getAgingList());
        }
        return subjectInfo;
    }

    @Override
    public void setTitles(Pagination<Map<String, Object>> page, QueryParamsVO queryParamsVO, String systemId) {
        HashMap ruleId2TitleCache = new HashMap();
        HashMap<String, String> subject2TitleCache = new HashMap<String, String>();
        Map<String, String> fieldCode2DictTableMap = this.initFieldCode2DictTableMap(queryParamsVO.getOtherShowColumns());
        Map<String, Integer> unSysNumberField2Decimal = this.getUnSysNumberField2Decimal(systemId);
        YearPeriodObject yp = new YearPeriodObject(null, OrgPeriodUtil.getQueryOrgPeriod((String)queryParamsVO.getPeriodStr()));
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        boolean showDictCode = false;
        ConsolidatedOptionVO consolidatedOptionVO = this.optionService.getOptionData(systemId);
        if (consolidatedOptionVO != null) {
            showDictCode = "1".equals(consolidatedOptionVO.getShowDictCode());
        }
        HashMap<Object, String> unitId2Title = new HashMap<Object, String>();
        HashMap<String, String> unitId2StopFlag = new HashMap<String, String>();
        List gcOrgCacheVOS = orgTool.listAllOrgByParentId(null);
        for (GcOrgCacheVO gcOrgCacheVO : gcOrgCacheVOS) {
            unitId2Title.put(gcOrgCacheVO.getId(), gcOrgCacheVO.getTitle());
            unitId2StopFlag.put(gcOrgCacheVO.getId(), String.valueOf(gcOrgCacheVO.isStopFlag()));
        }
        for (Map record : page.getContent()) {
            if (!record.containsKey("UNITID") || !record.containsKey("OPPUNITID")) continue;
            if (record.containsKey("CHECKSTATE") && record.get("CHECKSTATE") != null) {
                String chkState = CheckStateEnum.getTitleForCode((String)record.get("CHECKSTATE").toString());
                record.put("CHECKSTATE", chkState);
                record.put("CHKSTATETITLE", chkState);
            }
            this.initUnitTitle(unitId2Title, record, orgTool, unitId2StopFlag, "UNITID");
            this.initUnitTitle(unitId2Title, record, orgTool, unitId2StopFlag, "GCUNITID");
            this.initUnitTitle(unitId2Title, record, orgTool, unitId2StopFlag, "OPPUNITID");
            this.initUnitTitle(unitId2Title, record, orgTool, unitId2StopFlag, "GCOPPUNITID");
            record.put("UNITTITLE", unitId2Title.get(record.get("UNITID")));
            record.put("GCUNITTITLE", unitId2Title.get(record.get("GCUNITID")));
            record.put("OPPUNITTITLE", unitId2Title.get(record.get("OPPUNITID")));
            record.put("GCOPPUNITTITLE", unitId2Title.get(record.get("GCOPPUNITID")));
            record.put("unitStopFlag", unitId2StopFlag.get(record.get("UNITID")));
            record.put("oppUnitStopFlag", unitId2StopFlag.get(record.get("OPPUNITID")));
            this.setSubjectTitle(systemId, record, subject2TitleCache, "SUBJECTTITLE", "SUBJECTCODE");
            this.setSubjectTitle(systemId, record, subject2TitleCache, "GCSUBJECTTITLE", "GCSUBJECTCODE");
            this.updateUnitAndSubjectTitle(showDictCode, record);
            this.gcOffsetAppInputDataService.setRuleTitle(record, ruleId2TitleCache);
            Integer elmMode = ConverterUtils.getAsInteger(record.get("ELMMODE"));
            if (null == elmMode) {
                elmMode = 0;
            }
            record.put("GCUNITID", unitId2Title.get(record.get("GCUNITID")));
            record.put("GCOPPUNITID", unitId2Title.get(record.get("GCOPPUNITID")));
            record.put("ELMMODETITLE", OffsetElmModeEnum.getElmModeTitle((Integer)elmMode));
            this.gcOffsetAppInputDataService.setOtherShowColumnDictTitle(record, queryParamsVO.getOtherShowColumns(), fieldCode2DictTableMap, showDictCode);
            this.formatOtherShowNumberField(record, unSysNumberField2Decimal);
            this.formatOtherColumnTitle(record);
        }
    }

    private void formatOtherColumnTitle(Map<String, Object> record) {
        if (record.containsKey("ORIGINALCURR")) {
            this.formatCurrencyTitle(record, "ORIGINALCURR", "ORIGINALCURRTITLE");
        }
        if (record.containsKey("CURRENCY")) {
            this.formatCurrencyTitle(record, "CURRENCY", "CURRENCYTITLE");
        }
        if (record.containsKey("CHKCURR")) {
            this.formatCurrencyTitle(record, "CHKCURR", "CHKCURRTITLE");
        }
    }

    private void formatCurrencyTitle(Map<String, Object> record, String key, String titleKey) {
        GcBaseData curr;
        if (StringUtils.hasText((String)record.get(key)) && Objects.nonNull(curr = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", (String)record.get(key)))) {
            record.put(titleKey, curr.getTitle());
            return;
        }
        record.put(titleKey, record.get(key));
    }

    private void initUnitTitle(Map<Object, String> unitId2Title, Map<String, Object> record, GcOrgCenterService orgTool, Map<String, String> unitId2StopFlag, String key) {
        if (!unitId2Title.containsKey(record.get(key))) {
            GcOrgCacheVO vo = orgTool.getOrgByID((String)record.get(key));
            unitId2Title.put(record.get(key), vo == null ? (String)record.get(key) : vo.getTitle());
            unitId2StopFlag.put((String)record.get(key), vo == null ? String.valueOf(false) : String.valueOf(vo.isStopFlag()));
        }
    }

    private void formatOtherShowNumberField(Map<String, Object> record, Map<String, Integer> numberFieldCode2Decimal) {
        if (numberFieldCode2Decimal == null || numberFieldCode2Decimal.size() == 0) {
            return;
        }
        for (String code : numberFieldCode2Decimal.keySet()) {
            if (record.get(code) == null) continue;
            Double value = ConverterUtils.getAsDouble((Object)record.get(code));
            record.put(code, NumberUtils.doubleToString((Double)value, (int)numberFieldCode2Decimal.get(code)));
        }
    }

    public void updateUnitAndSubjectTitle(boolean showDictCode, Map<String, Object> record) {
        if (!showDictCode) {
            return;
        }
        record.put("UNITTITLE", record.get("UNITID") + "|" + record.get("UNITTITLE"));
        record.put("OPPUNITTITLE", record.get("OPPUNITID") + "|" + record.get("OPPUNITTITLE"));
        record.put("SUBJECTTITLE", record.get("SUBJECTCODE") + "|" + record.get("SUBJECTTITLE"));
        record.put("GCUNITTITLE", record.get("GCUNITID") + "|" + record.get("GCUNITTITLE"));
        record.put("GCOPPUNITTITLE", record.get("GCOPPUNITID") + "|" + record.get("GCOPPUNITTITLE"));
        record.put("GCSUBJECTCODE", record.get("GCSUBJECTCODE") + "|" + record.get("GCSUBJECTTITLE"));
        record.put("GCSUBJECTTITLE", record.get("GCSUBJECTCODE") + "|" + record.get("GCSUBJECTTITLE"));
    }

    private Map<String, Integer> getUnSysNumberField2Decimal(String systemId) {
        List dimensionsByTableName = this.optionService.getDimensionsByTableName("GC_RELATED_ITEM", systemId);
        if (CollectionUtils.isEmpty(dimensionsByTableName)) {
            return new HashMap<String, Integer>();
        }
        return dimensionsByTableName.stream().filter(item -> {
            if (item.getFieldType() == null) {
                return false;
            }
            return FieldType.FIELD_TYPE_DECIMAL.getValue() == item.getFieldType().intValue() || FieldType.FIELD_TYPE_FLOAT.getValue() == item.getFieldType().intValue();
        }).collect(Collectors.toMap(DimensionVO::getCode, item -> this.getFractionDigits("GC_RELATED_ITEM", item.getCode()), (o1, o2) -> o1));
    }

    private Integer getFractionDigits(String tableName, String fieldCode) {
        TableModelDefine tableModelDefineByName = this.dataModelService.getTableModelDefineByName(tableName, OuterDataSourceUtils.getOuterDataSourceCode((String)"jiuqi.gcreport.mdd.datasource"));
        if (Objects.isNull(tableModelDefineByName)) {
            throw new BusinessRuntimeException(tableName + "\u8868\u5b9a\u4e49\u4e0d\u5b58\u5728\u3002");
        }
        ColumnModelDefine columnModelDefineByCode = this.dataModelService.getColumnModelDefineByCode(tableModelDefineByName.getID(), fieldCode);
        if (Objects.isNull(columnModelDefineByCode)) {
            throw new BusinessRuntimeException(tableName + "\u8868\u4e2d" + fieldCode + "\u5b57\u6bb5\u4e0d\u5b58\u5728");
        }
        return columnModelDefineByCode.getDecimal();
    }

    public Map<String, String> initFieldCode2DictTableMap(List<String> otherShowColumns) {
        HashMap<String, String> fieldCode2DictTableMap = new HashMap<String, String>();
        if (!CollectionUtils.isEmpty(otherShowColumns)) {
            try {
                DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
                TableModelDefine tableDefine = dataModelService.getTableModelDefineByName("GC_RELATED_ITEM", OuterDataSourceUtils.getOuterDataSourceCode((String)"jiuqi.gcreport.mdd.datasource"));
                List defines = dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
                for (ColumnModelDefine designFieldDefine : defines) {
                    TableModelDefine dictField;
                    if (!StringUtils.hasText(designFieldDefine.getReferTableID()) || (dictField = dataModelService.getTableModelDefineById(designFieldDefine.getReferTableID())) == null || !StringUtils.hasText(designFieldDefine.getCode())) continue;
                    fieldCode2DictTableMap.put(designFieldDefine.getCode(), dictField.getName());
                }
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u521d\u59cb\u5316otherShowColumns\u7f13\u5b58Map\u5931\u8d25", (Throwable)e);
            }
        }
        return fieldCode2DictTableMap;
    }

    public void setSubjectTitle(String systemId, Map<String, Object> record, Map<String, String> subject2TitleCache, String titleKey, String codeKey) {
        String title;
        List allSubjectEos;
        String subjectCode = (String)record.get(codeKey);
        if (null == subjectCode) {
            return;
        }
        if (CollectionUtils.isEmpty(subject2TitleCache) && !CollectionUtils.isEmpty(allSubjectEos = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId))) {
            allSubjectEos.forEach(subjectEO -> subject2TitleCache.put(subjectEO.getCode(), subjectEO.getTitle()));
        }
        if (null == (title = subject2TitleCache.get(subjectCode))) {
            ConsolidatedSubjectEO subject = this.consolidatedSubjectService.getSubjectByCode(systemId, subjectCode);
            title = null == subject ? subjectCode : subject.getTitle();
            subject2TitleCache.put(subjectCode, title);
        }
        record.put(titleKey, title);
    }
}

