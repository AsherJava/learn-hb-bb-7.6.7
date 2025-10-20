/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nHelper
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nHelper;
import com.jiuqi.gcreport.offsetitem.enums.CheckStateEnum;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.util.BaseDataUtils;
import com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil;
import com.jiuqi.gcreport.offsetitem.util.OrgPeriodUtil;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.CollectionUtils;

public abstract class GcOffSetItemAdjustServiceAbstract {
    private static final Map<Integer, String> offSetSrcTypeValue2Title = new HashMap<Integer, String>(){
        {
            this.put(5, "Initial Entry for Fair Value Adjustments");
            this.put(21, "Initial Entry for Offsets");
            this.put(22, "Initial Entry for Investment Offsets");
            this.put(33, "Beginning of the Year--Brought Forward");
            this.put(34, "Beginning of the Year--Brought Forward");
        }
    };

    public Pagination<Map<String, Object>> assembleOffsetEntry(Pagination<Map<String, Object>> page, QueryParamsVO queryParamsVO, String tableName) {
        if (page.getContent().isEmpty()) {
            return page;
        }
        String systemId = this.getSystemId(page, queryParamsVO);
        page.getContent().forEach(record -> record.put("OFFSET_ITEM_INIT", true));
        this.setTitles(page, queryParamsVO, systemId, tableName);
        return page;
    }

    public Pagination<Map<String, Object>> assembleOffsetEntry(Pagination<Map<String, Object>> page, QueryParamsVO queryParamsVO) {
        if (page.getContent().isEmpty()) {
            return page;
        }
        String systemId = this.getSystemId(page, queryParamsVO);
        this.setTitles(page, queryParamsVO, systemId);
        return page;
    }

    private String getSystemId(Pagination<Map<String, Object>> page, QueryParamsVO queryParamsVO) {
        List<Map<String, Object>> records = page.getContent();
        page.setContent(this.setRowSpanAndSort(records));
        String systemId = queryParamsVO.getSystemId();
        if (org.springframework.util.StringUtils.isEmpty(systemId)) {
            ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
            systemId = consolidatedTaskService.getSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        }
        return systemId;
    }

    private void setTitles(Pagination<Map<String, Object>> page, QueryParamsVO queryParamsVO, String systemId, String tableName) {
        Map<String, String> fieldCode2DictTableMap = this.initFieldCode2DictTableMap(queryParamsVO.getOtherShowColumns(), tableName);
        Map<String, Integer> numberFieldCode2Decimal = this.getUnSysNumberField2Decimal(systemId, tableName);
        this.setTitles(page, queryParamsVO, systemId, fieldCode2DictTableMap, numberFieldCode2Decimal);
    }

    private void setTitles(Pagination<Map<String, Object>> page, QueryParamsVO queryParamsVO, String systemId) {
        Map<String, String> fieldCode2DictTableMap = this.initFieldCode2DictTableMap(queryParamsVO.getOtherShowColumns());
        Map<String, Integer> numberFieldCode2Decimal = this.getUnSysNumberField2Decimal(systemId);
        this.setTitles(page, queryParamsVO, systemId, fieldCode2DictTableMap, numberFieldCode2Decimal);
    }

    private void setTitles(Pagination<Map<String, Object>> page, QueryParamsVO queryParamsVO, String systemId, Map<String, String> fieldCode2DictTableMap, Map<String, Integer> numberFieldCode2Decimal) {
        HashMap<String, AbstractUnionRule> ruleId2TitleCache = new HashMap<String, AbstractUnionRule>();
        HashMap<String, String> subject2TitleCache = new HashMap<String, String>();
        HashMap<String, String> businessTypeCode2TitleCache = new HashMap<String, String>();
        Map<String, GcOrgCenterService> fieldCode2OrgToolMap = this.initFieldCode2OrgToolMap(fieldCode2DictTableMap, queryParamsVO.getPeriodStr());
        YearPeriodObject yp = new YearPeriodObject(null, OrgPeriodUtil.getQueryOrgPeriod(queryParamsVO.getPeriodStr()));
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        boolean showDictCode = false;
        ConsolidatedOptionService optionService = (ConsolidatedOptionService)SpringContextUtils.getBean(ConsolidatedOptionService.class);
        ConsolidatedOptionVO consolidatedOptionVO = optionService.getOptionData(systemId);
        if (consolidatedOptionVO != null) {
            showDictCode = "1".equals(consolidatedOptionVO.getShowDictCode());
        }
        HashMap unitId2Title = new HashMap();
        HashMap<String, String> unitId2StopFlag = new HashMap<String, String>();
        List gcOrgCacheVOS = orgTool.listAllOrgByParentId(null);
        for (GcOrgCacheVO gcOrgCacheVO : gcOrgCacheVOS) {
            unitId2Title.put(gcOrgCacheVO.getId(), gcOrgCacheVO.getTitle());
            unitId2StopFlag.put(gcOrgCacheVO.getId(), String.valueOf(gcOrgCacheVO.isStopFlag()));
        }
        for (Map map : page.getContent()) {
            GcOrgCacheVO vo;
            if (!unitId2Title.containsKey(map.get("UNITID"))) {
                vo = orgTool.getOrgByID((String)map.get("UNITID"));
                unitId2Title.put(map.get("UNITID"), vo == null ? (String)map.get("UNITID") : vo.getTitle());
                unitId2StopFlag.put((String)map.get("UNITID"), vo == null ? String.valueOf(false) : String.valueOf(vo.isStopFlag()));
            }
            if (!unitId2Title.containsKey(map.get("OPPUNITID"))) {
                vo = orgTool.getOrgByID((String)map.get("OPPUNITID"));
                unitId2Title.put(map.get("OPPUNITID"), vo == null ? (String)map.get("OPPUNITID") : vo.getTitle());
                unitId2StopFlag.put((String)map.get("OPPUNITID"), vo == null ? String.valueOf(false) : String.valueOf(vo.isStopFlag()));
            }
            map.put("UNITTITLE", unitId2Title.get(map.get("UNITID")));
            map.put("OPPUNITTITLE", unitId2Title.get(map.get("OPPUNITID")));
            map.put("unitStopFlag", unitId2StopFlag.get(map.get("UNITID")));
            map.put("oppUnitStopFlag", unitId2StopFlag.get(map.get("OPPUNITID")));
            this.setSubjectTitle(systemId, map, subject2TitleCache, "SUBJECTTITLE", "SUBJECTCODE");
            this.updateUnitAndSubjectTitle(showDictCode, map);
            this.setRuleTitle(map, ruleId2TitleCache);
            Integer elmMode = ConverterUtils.getAsInteger(map.get("ELMMODE"));
            if (null == elmMode) {
                elmMode = 0;
            }
            map.put("ELMMODETITLE", OffsetElmModeEnum.getElmModeTitle(elmMode));
            if (map.containsKey("CHKSTATE") && map.get("CHKSTATE") != null) {
                String chkState = CheckStateEnum.getTitleForCode(map.get("CHKSTATE").toString());
                map.put("CHKSTATE", chkState);
                map.put("CHKSTATETITLE", chkState);
            }
            this.setBusinessTypeCodeTitle(map, businessTypeCode2TitleCache);
            this.formatOtherShowNumberField(map, numberFieldCode2Decimal);
            this.setOtherShowColumnDictTitle(map, queryParamsVO.getOtherShowColumns(), fieldCode2DictTableMap, showDictCode);
            this.setOtherShowColumnOrgTitle(map, queryParamsVO.getOtherShowColumns(), fieldCode2OrgToolMap, showDictCode);
        }
    }

    protected void updateUnitAndSubjectTitle(boolean showDictCode, Map<String, Object> record) {
        if (!showDictCode) {
            return;
        }
        record.put("UNITTITLE", record.get("UNITID") + "|" + record.get("UNITTITLE"));
        record.put("OPPUNITTITLE", record.get("OPPUNITID") + "|" + record.get("OPPUNITTITLE"));
        record.put("SUBJECTTITLE", record.get("SUBJECTCODE") + "|" + record.get("SUBJECTTITLE"));
    }

    protected void setBusinessTypeCodeTitle(Map<String, Object> record, Map<String, String> businessTypeCode2TitleCache) {
        String businessTypeCode = (String)record.get("GCBUSINESSTYPECODE");
        if (null != businessTypeCode) {
            List baseDatas;
            if (CollectionUtils.isEmpty(businessTypeCode2TitleCache) && !CollectionUtils.isEmpty(baseDatas = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_GCBUSINESSTYPE"))) {
                baseDatas.forEach(baseData -> businessTypeCode2TitleCache.put(baseData.getCode(), baseData.getTitle()));
            }
            record.put("GCBUSINESSTYPE", businessTypeCode2TitleCache.get(businessTypeCode));
        }
    }

    public List<Map<String, Object>> setRowSpanAndSort(List<Map<String, Object>> unSortedRecords) {
        if (CollectionUtils.isEmpty(unSortedRecords)) {
            return unSortedRecords;
        }
        ArrayList<Map<String, Object>> sortedRecords = new ArrayList<Map<String, Object>>();
        ArrayList<Map<String, Object>> oneEntryRecords = new ArrayList<Map<String, Object>>();
        String mrecid = null;
        int entryIndex = 1;
        for (Map<String, Object> record : unSortedRecords) {
            String tempMrecid = (String)record.get("MRECID");
            if (null == mrecid || !mrecid.equals(tempMrecid)) {
                int size = oneEntryRecords.size();
                if (size > 0) {
                    if (((Map)oneEntryRecords.get(0)).get("ELMMODE") != null && OffsetElmModeEnum.INPUT_ITEM.getValue() != ConverterUtils.getAsInteger(((Map)oneEntryRecords.get(0)).get("ELMMODE")).intValue() && OffsetElmModeEnum.MANAGE_INPUT_ITEM.getValue() != ConverterUtils.getAsInteger(((Map)oneEntryRecords.get(0)).get("ELMMODE")).intValue()) {
                        OffsetItemComparatorUtil.mapSortComparator(oneEntryRecords);
                    }
                    sortedRecords.addAll(oneEntryRecords);
                    ((Map)oneEntryRecords.get(0)).put("rowspan", size);
                    ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
                    oneEntryRecords.clear();
                }
                mrecid = tempMrecid;
            }
            oneEntryRecords.add(record);
        }
        int size = oneEntryRecords.size();
        if (size > 0) {
            if (((Map)oneEntryRecords.get(0)).get("ELMMODE") != null && OffsetElmModeEnum.INPUT_ITEM.getValue() != ConverterUtils.getAsInteger(((Map)oneEntryRecords.get(0)).get("ELMMODE")).intValue() && OffsetElmModeEnum.MANAGE_INPUT_ITEM.getValue() != ConverterUtils.getAsInteger(((Map)oneEntryRecords.get(0)).get("ELMMODE")).intValue()) {
                OffsetItemComparatorUtil.mapSortComparator(oneEntryRecords);
            }
            sortedRecords.addAll(oneEntryRecords);
            ((Map)oneEntryRecords.get(0)).put("rowspan", size);
            ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
            oneEntryRecords.clear();
        }
        unSortedRecords.clear();
        return sortedRecords;
    }

    protected void setRuleTitle(Map<String, Object> record, Map<String, AbstractUnionRule> ruleId2TitleCache) {
        String unionRuleId = (String)record.get("RULEID");
        if (org.springframework.util.StringUtils.isEmpty(unionRuleId)) {
            unionRuleId = (String)record.get("UNIONRULEID");
        }
        UnionRuleService unionRuleService = (UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class);
        if (!org.springframework.util.StringUtils.isEmpty(unionRuleId)) {
            AbstractUnionRule vo;
            if (!ruleId2TitleCache.containsKey(unionRuleId)) {
                AbstractUnionRule rule = unionRuleService.selectUnionRuleDTOById(unionRuleId);
                ruleId2TitleCache.put(unionRuleId, rule);
            }
            if ((vo = ruleId2TitleCache.get(unionRuleId)) != null) {
                if (!StringUtils.isEmpty((String)this.getI18Title(vo.getId()))) {
                    vo.setTitle(this.getI18Title(vo.getId()));
                }
                String ruleTitle = vo.getTitle();
                if (RuleTypeEnum.FLEXIBLE.getCode().equals(vo.getRuleType()) || RuleTypeEnum.RELATE_TRANSACTIONS.getCode().equals(vo.getRuleType())) {
                    ruleTitle = this.getFetchSetTitle(record, vo, FlexibleRuleDTO.class, dto -> dto.getFetchConfigList(), config -> config.getFetchSetGroupId(), config -> config.getDescription());
                }
                if (RuleTypeEnum.FINANCIAL_CHECK.getCode().equals(vo.getRuleType())) {
                    ruleTitle = this.getFetchSetTitle(record, vo, FinancialCheckRuleDTO.class, dto -> dto.getFetchConfigList(), config -> config.getFetchSetGroupId(), config -> config.getDescription());
                }
                record.put("RULETITLE", ruleTitle);
                record.put("UNIONRULETITLE", ruleTitle);
                record.put("RULETYPE", vo.getRuleType());
            }
        }
    }

    private String getI18Title(String id) {
        return ((GcI18nHelper)SpringBeanUtils.getBean(GcI18nHelper.class)).getMessage(id);
    }

    protected Map<String, String> initFieldCode2DictTableMap(List<String> otherShowColumns) {
        return this.initFieldCode2DictTableMap(otherShowColumns, "GC_OFFSETVCHRITEM");
    }

    private <T extends AbstractUnionRule, C> String getFetchSetTitle(Map<String, Object> record, AbstractUnionRule vo, Class<T> dtoType, Function<T, List<C>> configListGetter, Function<C, String> groupIdGetter, Function<C, String> descGetter) {
        if (!dtoType.isInstance(vo)) {
            return vo.getTitle();
        }
        String fetchSetGroupId = Optional.ofNullable(record.get("FETCHSETGROUPID")).map(Object::toString).orElse("");
        if (StringUtils.isEmpty((String)fetchSetGroupId)) {
            return vo.getTitle();
        }
        AbstractUnionRule dto = (AbstractUnionRule)dtoType.cast(vo);
        List<C> configList = configListGetter.apply(dto);
        if (CollectionUtils.isEmpty(configList) || configList.size() <= 1) {
            return vo.getTitle();
        }
        String i18Title = this.getI18Title(fetchSetGroupId);
        if (!StringUtils.isEmpty((String)i18Title)) {
            return i18Title;
        }
        int i = 0;
        for (C config : configList) {
            String description = descGetter.apply(config);
            if (org.springframework.util.StringUtils.isEmpty(description)) {
                ++i;
            }
            if (!fetchSetGroupId.equals(groupIdGetter.apply(config))) continue;
            return org.springframework.util.StringUtils.isEmpty(description) ? dto.getTitle() + "--" + i : description;
        }
        return vo.getTitle();
    }

    protected Map<String, GcOrgCenterService> initFieldCode2OrgToolMap(Map<String, String> fieldCode2DictTableMap, String periodStr) {
        if (fieldCode2DictTableMap == null || fieldCode2DictTableMap.isEmpty()) {
            return new HashMap<String, GcOrgCenterService>(2);
        }
        HashMap<String, GcOrgCenterService> fieldCode2OrgToolMap = new HashMap<String, GcOrgCenterService>();
        for (String fieldCode : fieldCode2DictTableMap.keySet()) {
            String dictTableName = fieldCode2DictTableMap.get(fieldCode);
            if (StringUtils.isEmpty((String)dictTableName) || !dictTableName.toUpperCase().startsWith("MD_ORG_")) continue;
            GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)dictTableName, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, periodStr));
            fieldCode2OrgToolMap.put(fieldCode, orgTool);
        }
        return fieldCode2OrgToolMap;
    }

    protected Map<String, String> initFieldCode2DictTableMap(List<String> otherShowColumns, String tableName) {
        HashMap<String, String> fieldCode2DictTableMap = new HashMap<String, String>();
        if (!CollectionUtils.isEmpty(otherShowColumns)) {
            try {
                DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
                TableModelDefine tableDefine = dataModelService.getTableModelDefineByName(tableName);
                List defines = dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
                for (ColumnModelDefine designFieldDefine : defines) {
                    TableModelDefine dictField;
                    if (StringUtils.isEmpty((String)designFieldDefine.getReferTableID()) || (dictField = dataModelService.getTableModelDefineById(designFieldDefine.getReferTableID())) == null || StringUtils.isEmpty((String)designFieldDefine.getCode())) continue;
                    fieldCode2DictTableMap.put(designFieldDefine.getCode(), dictField.getName());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new BusinessRuntimeException("\u521d\u59cb\u5316otherShowColumns\u7f13\u5b58Map\u5931\u8d25", (Throwable)e);
            }
        }
        return fieldCode2DictTableMap;
    }

    protected void setOtherShowColumnOrgTitle(Map<String, Object> record, List<String> otherShowColumns, Map<String, GcOrgCenterService> fieldCode2OrgToolMap, boolean showDictCode) {
        if (CollectionUtils.isEmpty(otherShowColumns)) {
            return;
        }
        if (fieldCode2OrgToolMap.isEmpty()) {
            return;
        }
        for (String fieldCode : fieldCode2OrgToolMap.keySet()) {
            GcOrgCenterService orgTool;
            GcOrgCacheVO orgByCode;
            String value = ConverterUtils.getAsString((Object)record.get(fieldCode));
            if (StringUtils.isEmpty((String)value) || (orgByCode = (orgTool = fieldCode2OrgToolMap.get(fieldCode)).getOrgByCode(value)) == null || StringUtils.isEmpty((String)orgByCode.getTitle())) continue;
            record.put(fieldCode, showDictCode ? value + "|" + orgByCode.getTitle() : orgByCode.getTitle());
        }
    }

    protected void setOtherShowColumnDictTitle(Map<String, Object> record, List<String> otherShowColumns, Map<String, String> fieldCode2DictTableMap, boolean showDictCode) {
        if (CollectionUtils.isEmpty(otherShowColumns)) {
            return;
        }
        for (String otherShowColumn : otherShowColumns) {
            String dictTitle;
            Object value = record.get(otherShowColumn);
            if (value == null) continue;
            if ("SUBJECTORIENT".equals(otherShowColumn)) {
                record.put(otherShowColumn, Integer.parseInt(value.toString()) == 1 ? "\u501f" : "\u8d37");
                continue;
            }
            if ("EFFECTTYPE".equals(otherShowColumn)) {
                if (record.containsKey("OFFSET_ITEM_INIT")) {
                    if (EFFECTTYPE.LONGTERM.getCode().equals(value.toString())) {
                        record.put(otherShowColumn, "\u662f");
                        continue;
                    }
                    record.put(otherShowColumn, "\u5426");
                    continue;
                }
                record.put(otherShowColumn, EFFECTTYPE.getTitleByCode(value.toString()));
                continue;
            }
            if ("MODIFYTIME".equals(otherShowColumn)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                record.put(otherShowColumn, sdf.format((Date)value));
                continue;
            }
            if ("OFFSETSRCTYPE".equals(otherShowColumn)) {
                OffSetSrcTypeEnum offSetSrcTypeEnum = OffSetSrcTypeEnum.getEnumByValue((Integer)value);
                record.put("OFFSETSRCTYPENAME", offSetSrcTypeEnum == null ? value : offSetSrcTypeEnum.getSrcTypeName());
                NpContext context = NpContextHolder.getContext();
                Locale locale = null != context ? context.getLocale() : LocaleContextHolder.getLocale();
                if (offSetSrcTypeEnum == null || locale == null || "zh".equalsIgnoreCase(locale.getLanguage()) || !offSetSrcTypeValue2Title.containsKey((Integer)value)) continue;
                record.put("OFFSETSRCTYPENAME", offSetSrcTypeValue2Title.get((Integer)value));
                continue;
            }
            String dictTableName = fieldCode2DictTableMap.get(otherShowColumn);
            if (dictTableName == null || StringUtils.isEmpty((String)(dictTitle = BaseDataUtils.getDictTitle(dictTableName, (String)value)))) continue;
            record.put(otherShowColumn, showDictCode ? value + "|" + dictTitle : dictTitle);
        }
    }

    protected String getUnitTitle(String unitId, Map<String, String> unitId2Title) {
        if (unitId == null) {
            return "";
        }
        String title = unitId2Title.get(unitId);
        if (null == title) {
            GcOrgCacheVO organization = GcOrgPublicTool.getInstance().getOrgByCode(unitId);
            title = null == organization ? ((organization = GcOrgPublicTool.getInstance().getBaseOrgByCode(unitId)) == null ? "" : organization.getTitle()) : organization.getTitle();
            unitId2Title.put(unitId, title);
        }
        return title;
    }

    protected String getUnitTitle(String unitCode, Map<String, String> unitCode2TitleCache, GcOrgCenterService tool) {
        if (StringUtils.isEmpty((String)unitCode)) {
            return "";
        }
        String unitTitle = unitCode2TitleCache.get(unitCode);
        if (null == unitTitle) {
            GcOrgCacheVO cacheVO = tool.getOrgByCode(unitCode);
            unitTitle = cacheVO == null ? "" : cacheVO.getTitle();
            unitCode2TitleCache.put(unitCode, unitTitle);
        }
        return unitTitle;
    }

    public void setSubjectTitle(String systemId, Map<String, Object> record, Map<String, String> subject2TitleCache, String titleKey, String subjectKey) {
        ConsolidatedSubjectService consolidatedSubjectService;
        List allSubjectEos;
        String subjectCode = (String)record.get(subjectKey);
        if (null == subjectCode) {
            return;
        }
        if (CollectionUtils.isEmpty(subject2TitleCache) && !CollectionUtils.isEmpty(allSubjectEos = (consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class)).listAllSubjectsBySystemId(systemId))) {
            allSubjectEos.forEach(subjectEO -> subject2TitleCache.put(subjectEO.getCode(), subjectEO.getTitle()));
        }
        if (null == subject2TitleCache.get(subjectCode)) {
            ConsolidatedSubjectEO subject = ((ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class)).getSubjectByCode(systemId, subjectCode);
            String title = null == subject ? subjectCode : subject.getTitle();
            subject2TitleCache.put(subjectCode, title);
        }
        record.put(titleKey, subject2TitleCache.get(subjectCode));
    }

    protected Map<String, Integer> getUnSysNumberField2Decimal(String systemId, String tableName) {
        List dimensionsByTableName = ((ConsolidatedOptionService)SpringContextUtils.getBean(ConsolidatedOptionService.class)).getAllDimensionsByTableName(tableName, systemId);
        if (CollectionUtils.isEmpty(dimensionsByTableName)) {
            return new HashMap<String, Integer>();
        }
        return dimensionsByTableName.stream().filter(item -> {
            if (item.getFieldType() == null) {
                return false;
            }
            return FieldType.FIELD_TYPE_DECIMAL.getValue() == item.getFieldType().intValue() || FieldType.FIELD_TYPE_FLOAT.getValue() == item.getFieldType().intValue();
        }).collect(Collectors.toMap(DimensionVO::getCode, item -> this.getFractionDigits(tableName, item.getCode()), (o1, o2) -> o1));
    }

    protected Map<String, Integer> getUnSysNumberField2Decimal(String systemId) {
        return this.getUnSysNumberField2Decimal(systemId, "GC_OFFSETVCHRITEM");
    }

    private Integer getFractionDigits(String tableName, String fieldCode) {
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        TableModelDefine tableModelDefineByName = dataModelService.getTableModelDefineByName(tableName);
        if (Objects.isNull(tableModelDefineByName)) {
            throw new BusinessRuntimeException(tableName + "\u8868\u5b9a\u4e49\u4e0d\u5b58\u5728\u3002");
        }
        ColumnModelDefine columnModelDefineByCode = dataModelService.getColumnModelDefineByCode(tableModelDefineByName.getID(), fieldCode);
        if (Objects.isNull(columnModelDefineByCode)) {
            throw new BusinessRuntimeException(tableName + "\u8868\u4e2d" + fieldCode + "\u5b57\u6bb5\u4e0d\u5b58\u5728");
        }
        return columnModelDefineByCode.getDecimal();
    }

    protected void formatOtherShowNumberField(Map<String, Object> record, Map<String, Integer> numberFieldCode2Decimal) {
        if (numberFieldCode2Decimal == null || numberFieldCode2Decimal.size() == 0) {
            return;
        }
        for (String code : numberFieldCode2Decimal.keySet()) {
            if (record.get(code) == null) continue;
            Double value = ConverterUtils.getAsDouble((Object)record.get(code));
            record.put(code, NumberUtils.doubleToString((Double)value, (int)numberFieldCode2Decimal.get(code)));
        }
    }
}

