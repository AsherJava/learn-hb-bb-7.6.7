/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetAppOffsetServiceImpl
 *  com.jiuqi.gcreport.offsetitem.util.BaseDataUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.financialcheckImpl.datatrace.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.datatrace.service.FinancialCheckDataTraceService;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dao.OffsetRelatedItemDao;
import com.jiuqi.gcreport.financialcheckImpl.offsetvchr.service.GcRelatedOffsetVoucherItemService;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetAppOffsetServiceImpl;
import com.jiuqi.gcreport.offsetitem.util.BaseDataUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FinancialCheckDataTraceServiceImpl
implements FinancialCheckDataTraceService {
    @Autowired
    OffsetRelatedItemDao offsetRelatedItemDao;
    @Autowired
    GcOffSetItemAdjustCoreService offSetItemAdjustCoreService;
    @Autowired
    GcOffSetAppOffsetServiceImpl offSetAppOffsetService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private GcRelatedItemDao relatedItemDao;
    @Autowired
    private UnionRuleService ruleService;
    @Autowired
    private GcRelatedOffsetVoucherItemService gcRelatedOffsetVoucherItemService;

    @Override
    public Object listOffsetAndSourceItem(Map<String, Object> offsetIInfo) {
        if (Objects.isNull(offsetIInfo) || !offsetIInfo.containsKey("SRCOFFSETGROUPID") || !offsetIInfo.containsKey("MRECID")) {
            return null;
        }
        String srcoffsetgroupid = ConverterUtils.getAsString((Object)offsetIInfo.get("SRCOFFSETGROUPID"));
        if (StringUtils.isEmpty((String)srcoffsetgroupid)) {
            return null;
        }
        String mrecid = ConverterUtils.getAsString((Object)offsetIInfo.get("MRECID"));
        if (StringUtils.isEmpty((String)mrecid)) {
            return null;
        }
        String ruleId = ConverterUtils.getAsString((Object)offsetIInfo.get("RULEID"));
        if (StringUtils.isEmpty((String)ruleId)) {
            return null;
        }
        AbstractUnionRule rule = this.ruleService.selectUnionRuleDTOById(ruleId);
        if (Objects.isNull(rule)) {
            return null;
        }
        HashMap<String, Object> datas = new HashMap<String, Object>();
        List offSetItemS = this.offSetItemAdjustCoreService.listWithFullGroupByMrecids(Arrays.asList(mrecid));
        List records = this.offSetAppOffsetService.convertAdjustData2View(offSetItemS);
        datas.put("offsetRecords", this.offSetAppOffsetService.setRowSpanAndSort(records));
        List<Object> offsetRelatedItemS = this.offsetRelatedItemDao.listByOffsetGroupId(Arrays.asList(srcoffsetgroupid));
        if (CollectionUtils.isEmpty(offsetRelatedItemS)) {
            datas.put("unOffsetRecords", new ArrayList());
            return datas;
        }
        if (ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            Set<String> ids = offsetRelatedItemS.stream().map(GcOffsetRelatedItemEO::getRelatedItemId).collect(Collectors.toSet());
            Map<String, Double> rateMap = offsetRelatedItemS.stream().collect(Collectors.toMap(GcOffsetRelatedItemEO::getRelatedItemId, item -> Optional.ofNullable(item.getConversionRate()).orElse(0.0)));
            if (((FinancialCheckRuleDTO)rule).isChecked()) {
                List<Object> offsetVoucherItemS = this.gcRelatedOffsetVoucherItemService.queryByIds(ids);
                offsetVoucherItemS = offsetVoucherItemS.stream().sorted(Comparator.comparing(GcRelatedOffsetVoucherItemEO::getUnitId)).collect(Collectors.toList());
                datas.put("unOffsetRecords", this.convertOffsetVoucherItem2VO(offsetVoucherItemS, ((GcOffSetVchrItemAdjustEO)offSetItemS.get(0)).getSystemId(), rateMap));
            } else {
                List<GcRelatedItemEO> relatedItems = this.relatedItemDao.queryByIds(ids);
                relatedItems = relatedItems.stream().sorted(Comparator.comparing(GcRelatedItemEO::getUnitId)).collect(Collectors.toList());
                datas.put("unOffsetRecords", this.convertRelatedItem2VO(relatedItems, ((GcOffSetVchrItemAdjustEO)offSetItemS.get(0)).getSystemId(), rateMap));
            }
        } else {
            offsetRelatedItemS = offsetRelatedItemS.stream().sorted(Comparator.comparing(GcOffsetRelatedItemEO::getUnitId)).collect(Collectors.toList());
            datas.put("unOffsetRecords", this.convertOffsetRelatedItem2VO(offsetRelatedItemS, ((GcOffSetVchrItemAdjustEO)offSetItemS.get(0)).getSystemId()));
        }
        datas.put("CHECKWAY", FinancialCheckConfigUtils.getCheckWay().getCode());
        return datas;
    }

    public List<Map<String, Object>> convertRelatedItem2VO(List<GcRelatedItemEO> items, String systemId, Map<String, Double> rateMap) {
        List<String> otherShowColumns = this.getOtherShowColumns("GC_OFFSETVCHRITEM", systemId);
        Map<String, String> fieldCode2DictTableMap = this.initFieldCode2DictTableMap(otherShowColumns);
        boolean showDictCode = "1".equals(this.optionService.getOptionData(systemId).getShowDictCode());
        ArrayList<Map<String, Object>> vos = new ArrayList<Map<String, Object>>();
        String checkOrgType = FinancialCheckConfigUtils.getCheckOrgType();
        items.forEach(item -> {
            Map fields = item.getFields();
            YearPeriodObject yp = new YearPeriodObject(null, item.getAcctYear().intValue(), 4, PeriodUtils.standardPeriod((int)item.getAcctPeriod()));
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)checkOrgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO unit = tool.getOrgByCode((String)fields.get("UNITID"));
            fields.put("UNITTITLE", unit == null ? "" : unit.getTitle());
            GcOrgCacheVO oppUnit = tool.getOrgByCode((String)fields.get("OPPUNITID"));
            fields.put("OPPUNITTITLE", oppUnit == null ? "" : oppUnit.getTitle());
            GcBaseData subject = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", item.getSubjectCode());
            fields.put("SUBJECTTITLE", subject == null ? "" : subject.getTitle());
            if (CheckStateEnum.CHECKED.getCode().equals(item.getChkState())) {
                fields.put("DEBITAMT", NumberUtils.doubleToString((double)item.getChkAmtD(), (int)2, (boolean)true));
                fields.put("CREDITAMT", NumberUtils.doubleToString((double)item.getChkAmtC(), (int)2, (boolean)true));
            } else {
                fields.put("DEBITAMT", NumberUtils.doubleToString((double)item.getDebitOrig(), (int)2, (boolean)true));
                fields.put("CREDITAMT", NumberUtils.doubleToString((double)item.getCreditOrig(), (int)2, (boolean)true));
            }
            fields.put("GCNUMBER", "SystemDefault".equals(item.getGcNumber()) ? "" : item.getGcNumber());
            GcBaseData currency = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", item.getOriginalCurr());
            fields.put("ORIGINALCURR", Objects.isNull(currency) ? item.getOriginalCurr() : currency.getTitle());
            fields.put("CHKSTATETITLE", CheckStateEnum.getTitleForCode((String)item.getChkState()));
            fields.put("CONVERSIONRATE", NumberUtils.isZreo((Double)((Double)rateMap.get(item.getId()))) ? "-" : rateMap.get(item.getId()));
            this.setOtherShowColumnDictTitle(fields, otherShowColumns, fieldCode2DictTableMap, showDictCode);
            vos.add(fields);
        });
        return vos;
    }

    public List<Map<String, Object>> convertOffsetVoucherItem2VO(List<GcRelatedOffsetVoucherItemEO> items, String systemId, Map<String, Double> rateMap) {
        List<String> otherShowColumns = this.getOtherShowColumns("GC_OFFSETVCHRITEM", systemId);
        Map<String, String> fieldCode2DictTableMap = this.initFieldCode2DictTableMap(otherShowColumns);
        boolean showDictCode = "1".equals(this.optionService.getOptionData(systemId).getShowDictCode());
        ArrayList<Map<String, Object>> vos = new ArrayList<Map<String, Object>>();
        String checkOrgType = FinancialCheckConfigUtils.getCheckOrgType();
        items.forEach(item -> {
            Map fields = item.getFields();
            YearPeriodObject yp = new YearPeriodObject(null, item.getAcctYear().intValue(), 4, PeriodUtils.standardPeriod((int)item.getAcctPeriod()));
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)checkOrgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO unit = tool.getOrgByCode(item.getUnitId());
            fields.put("UNITTITLE", unit == null ? "" : unit.getTitle());
            GcOrgCacheVO oppUnit = tool.getOrgByCode(item.getOppUnitId());
            fields.put("OPPUNITTITLE", oppUnit == null ? "" : oppUnit.getTitle());
            GcBaseData subject = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", item.getOffsetSubject());
            fields.put("SUBJECTTITLE", subject == null ? "" : subject.getTitle());
            fields.put("DEBITAMT", NumberUtils.doubleToString((double)item.getDebitOffset(), (int)2, (boolean)true));
            fields.put("CREDITAMT", NumberUtils.doubleToString((double)item.getCreditOffset(), (int)2, (boolean)true));
            fields.put("GCNUMBER", "SystemDefault".equals(item.getGcNumber()) ? "" : item.getGcNumber());
            GcBaseData currency = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", item.getOffsetCurrency());
            fields.put("ORIGINALCURR", Objects.isNull(currency) ? item.getOffsetCurrency() : currency.getTitle());
            fields.put("CHKSTATETITLE", CheckStateEnum.CHECKED.getTitle());
            fields.put("CONVERSIONRATE", NumberUtils.isZreo((Double)((Double)rateMap.get(item.getId()))) ? "-" : rateMap.get(item.getId()));
            this.setOtherShowColumnDictTitle(fields, otherShowColumns, fieldCode2DictTableMap, showDictCode);
            vos.add(fields);
        });
        return vos;
    }

    public List<Map<String, Object>> convertOffsetRelatedItem2VO(List<GcOffsetRelatedItemEO> items, String systemId) {
        List<String> otherShowColumns = this.getOtherShowColumns("GC_OFFSETVCHRITEM", systemId);
        Map<String, String> fieldCode2DictTableMap = this.initFieldCode2DictTableMap(otherShowColumns);
        boolean showDictCode = "1".equals(this.optionService.getOptionData(systemId).getShowDictCode());
        ArrayList<Map<String, Object>> vos = new ArrayList<Map<String, Object>>();
        String checkOrgType = FinancialCheckConfigUtils.getCheckOrgType();
        items.forEach(item -> {
            Map fields = item.getFields();
            YearPeriodObject yp = new YearPeriodObject(null, item.getDataTime());
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)checkOrgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO unit = tool.getOrgByCode(item.getGcUnitId());
            fields.put("UNITTITLE", unit == null ? "" : unit.getTitle());
            GcOrgCacheVO oppUnit = tool.getOrgByCode(item.getGcOppUnitId());
            fields.put("OPPUNITTITLE", oppUnit == null ? "" : oppUnit.getTitle());
            GcBaseData subject = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", item.getGcSubjectCode());
            fields.put("SUBJECTTITLE", subject == null ? "" : subject.getTitle());
            fields.put("UNCHECKEDAMT", NumberUtils.doubleToString((double)item.getAmt(), (int)2, (boolean)true));
            fields.put("CHKSTATETITLE", CheckStateEnum.getTitleForCode((String)item.getCheckState()));
            fields.put("CONVERSIONRATE", Objects.isNull(item.getConversionRate()) ? "-" : item.getConversionRate());
            this.setOtherShowColumnDictTitle(fields, otherShowColumns, fieldCode2DictTableMap, showDictCode);
            vos.add(fields);
        });
        return vos;
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
                record.put(otherShowColumn, Integer.valueOf(value.toString()) == 1 ? "\u501f" : "\u8d37");
                continue;
            }
            if ("EFFECTTYPE".equals(otherShowColumn)) {
                record.put(otherShowColumn, EFFECTTYPE.getTitleByCode((String)value.toString()));
                continue;
            }
            if ("MODIFYTIME".equals(otherShowColumn) && value != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                record.put(otherShowColumn, sdf.format((Date)value));
                continue;
            }
            String dictTableName = fieldCode2DictTableMap.get(otherShowColumn);
            if (dictTableName == null || StringUtils.isEmpty((String)(dictTitle = BaseDataUtils.getDictTitle((String)dictTableName, (String)((String)value))))) continue;
            record.put(otherShowColumn, showDictCode ? value + "|" + dictTitle : dictTitle);
        }
    }

    private List<String> getOtherShowColumns(String tableName, String systemId) {
        List dimensionVOs = this.optionService.getDimensionsByTableName(tableName, systemId);
        if (CollectionUtils.isEmpty(dimensionVOs)) {
            return new ArrayList<String>();
        }
        return dimensionVOs.stream().map(dim -> dim.getCode()).collect(Collectors.toList());
    }

    protected Map<String, String> initFieldCode2DictTableMap(List<String> otherShowColumns) {
        return this.initFieldCode2DictTableMap(otherShowColumns, "GC_OFFSETVCHRITEM");
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
                throw new BusinessRuntimeException("\u521d\u59cb\u5316otherShowColumns\u7f13\u5b58Map\u5931\u8d25", (Throwable)e);
            }
        }
        return fieldCode2DictTableMap;
    }
}

