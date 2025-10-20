/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.ManualOffsetTypeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  org.apache.commons.lang3.tuple.Pair
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.factory.action.manual;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.ManualOffsetTypeEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.enums.MatchingInformationEnum;
import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.FinancialCheckOffsetService;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dto.RelatedItemGcOffsetRelDTO;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.helper.FinancialCheckBalanceExecutorHelper;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.processor.executor.impl.inputdata.FinancialCheckRuleExecutorImpl;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class QueryUnOffsetFinancialCheck
implements GcOffSetItemAction {
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private FinancialCheckOffsetService financialCheckOffsetService;
    private final Logger logger = LoggerFactory.getLogger(QueryUnOffsetFinancialCheck.class);

    public String code() {
        return "manualOffset";
    }

    public String title() {
        return "\u624b\u52a8\u62b5\u9500\u67e5\u8be2";
    }

    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(gcOffsetExecutorVO.getParamObject());
            GcCalcArgmentsDTO gcCalcArgmentsDTO = (GcCalcArgmentsDTO)JsonUtils.readValue((String)jsonString, GcCalcArgmentsDTO.class);
            Assert.isNotEmpty((Collection)gcCalcArgmentsDTO.getRecordIds(), (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.noDataSelected"), (Object[])new Object[0]);
            List<GcOffsetRelatedItemEO> records = this.financialCheckOffsetService.listById(gcCalcArgmentsDTO.getRecordIds());
            records.forEach(item -> {
                item.addFieldValue("UNITID", (Object)item.getUnitId());
                item.addFieldValue("OPPUNITID", (Object)item.getOppUnitId());
                item.addFieldValue("SUBJECTCODE", (Object)item.getGcSubjectCode());
                item.setUnitId(item.getUnitId());
                item.setOppUnitId(item.getGcOppUnitId());
                item.setSubjectCode(item.getGcSubjectCode());
            });
            boolean isSupportCenterToDiff = false;
            ConsolidatedTaskVO taskBySchemeId = this.consolidatedTaskService.getTaskBySchemeId(gcCalcArgmentsDTO.getSchemeId(), gcCalcArgmentsDTO.getPeriodStr());
            if (!"0".equals(taskBySchemeId.getMoreUnitOffset())) {
                if (ManualOffsetTypeEnum.SUPPORT_BASE_TO_DIFFERENCE.getValue().equals(taskBySchemeId.getMoreUnitOffset())) {
                    isSupportCenterToDiff = true;
                }
                for (GcOffsetRelatedItemEO record : records) {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add(record.getUnitId());
                    arrayList.add(record.getOppUnitId());
                    this.getFinancialCheckUnitStatus(new ArrayList<Object>(arrayList), records, gcCalcArgmentsDTO);
                }
            } else {
                HashSet<String> unitGuids = new HashSet<String>();
                for (GcOffsetRelatedItemEO gcOffsetRelatedItemEO : records) {
                    unitGuids.add(gcOffsetRelatedItemEO.getUnitId());
                    unitGuids.add(gcOffsetRelatedItemEO.getOppUnitId());
                }
                Assert.isTrue((unitGuids.size() <= 2 ? 1 : 0) != 0, (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.moreThanTwo"), (Object[])new Object[0]);
                this.getFinancialCheckUnitStatus(new ArrayList<Object>(unitGuids), records, gcCalcArgmentsDTO);
            }
            List<Map<String, Object>> resultRecords = this.calMerge(records, gcCalcArgmentsDTO);
            if (isSupportCenterToDiff) {
                ArrayList<List<Map>> resultRecordsGroupBySubjects = new ArrayList<List<Map>>(resultRecords.stream().collect(Collectors.groupingBy(item -> Pair.of(item.get("SUBJECTCODE"), item.get("DC")))).values());
                resultRecords.clear();
                for (List list : resultRecordsGroupBySubjects) {
                    BigDecimal creditValue = BigDecimal.ZERO;
                    BigDecimal debitValue = BigDecimal.ZERO;
                    for (Map resultRecord : list) {
                        creditValue = creditValue.add(ConverterUtils.getAsBigDecimal(resultRecord.get("CREDITVALUE")));
                        debitValue = debitValue.add(ConverterUtils.getAsBigDecimal(resultRecord.get("DEBITVALUE")));
                    }
                    if (new Integer(-1).equals(((Map)list.get(0)).get("DC"))) {
                        ((Map)list.get(0)).put("CREDITVALUE", NumberUtils.doubleToString((double)creditValue.doubleValue()));
                        ((Map)list.get(0)).put("AMMOUNT", creditValue.doubleValue());
                        ((Map)list.get(0)).remove("DEBITVALUE");
                    }
                    if (new Integer(1).equals(((Map)list.get(0)).get("DC"))) {
                        ((Map)list.get(0)).put("DEBITVALUE", NumberUtils.doubleToString((double)debitValue.doubleValue()));
                        ((Map)list.get(0)).put("AMMOUNT", debitValue.doubleValue());
                        ((Map)list.get(0)).remove("CREDITVALUE");
                    }
                    resultRecords.add((Map<String, Object>)list.get(0));
                }
                resultRecords.sort((m1, m2) -> (Integer)m2.get("DC") - (Integer)m1.get("DC"));
            }
            String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(gcCalcArgmentsDTO.getSchemeId(), gcCalcArgmentsDTO.getPeriodStr());
            Assert.isNotEmpty((String)systemId, (String)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.systemNotExist"), (Object[])new Object[0]);
            boolean bl = this.optionService.getOptionItemBooleanBySchemeId(gcCalcArgmentsDTO.getSchemeId(), gcCalcArgmentsDTO.getPeriodStr(), "showDictCode");
            this.setTitles(resultRecords, systemId, gcCalcArgmentsDTO.getOrgType(), gcCalcArgmentsDTO.getPeriodStr());
            this.setZjTitles(resultRecords, bl);
            JSONObject jSONObject = this.calcDxje(resultRecords);
            jSONObject.put("recordIds", (Collection)records.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
            return jSONObject.toString();
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u624b\u52a8\u62b5\u9500\u6570\u636e\u5931\u8d25:" + e.getMessage(), e);
            throw new BusinessRuntimeException("\u83b7\u53d6\u624b\u52a8\u62b5\u9500\u6570\u636e\u5931\u8d25:" + e.getMessage(), (Throwable)e);
        }
    }

    private void setZjTitles(List<Map<String, Object>> contents, boolean showDictCode) {
        DimensionService service = (DimensionService)SpringContextUtils.getBean(DimensionService.class);
        List dimensionVOs = service.findDimFieldsVOByTableName("GC_OFFSETVCHRITEM");
        if (null == dimensionVOs) {
            return;
        }
        for (Map<String, Object> record : contents) {
            GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
            dimensionVOs.forEach(dimensionVO -> {
                GcBaseDataDO baseData;
                String fieldDictValue;
                String dictTableName = dimensionVO.getDictTableName();
                String fieldCode = dimensionVO.getCode().toUpperCase();
                if (!org.springframework.util.StringUtils.isEmpty(dictTableName) && !org.springframework.util.StringUtils.isEmpty(fieldDictValue = (String)record.get(fieldCode)) && null != (baseData = (GcBaseDataDO)tool.queryBasedataByCode(dictTableName, fieldDictValue))) {
                    String title = baseData.getTitle();
                    if (showDictCode && !org.springframework.util.StringUtils.isEmpty(title)) {
                        baseData.setTitle(fieldDictValue + "|" + title);
                    }
                    record.put(fieldCode, baseData);
                }
            });
        }
    }

    private JSONObject calcDxje(List<Map<String, Object>> records) {
        JSONObject jsonObject = new JSONObject();
        double totalJfJyje = 0.0;
        double totalDfJyje = 0.0;
        for (Map<String, Object> record : records) {
            Integer jdfx = (Integer)record.get("DC");
            if (null == jdfx) {
                jdfx = OrientEnum.D.getValue();
                record.put("DC", jdfx);
            }
            double jyje = MapUtils.doubleValue(record, (Object)"AMMOUNT");
            if (jdfx == OrientEnum.D.getValue()) {
                totalJfJyje += jyje;
                continue;
            }
            totalDfJyje += jyje;
        }
        double dxje = totalDfJyje;
        int biggerJdfx = OrientEnum.D.getValue();
        double biggerDxje = totalJfJyje;
        if (totalJfJyje < totalDfJyje) {
            dxje = totalJfJyje;
            biggerJdfx = OrientEnum.C.getValue();
            biggerDxje = totalJfJyje;
        }
        if (biggerDxje == 0.0) {
            biggerDxje = 1.0E-6;
        }
        double remainDxje = dxje;
        Map<String, Object> lastBiggerRecord = null;
        for (Map<String, Object> record : records) {
            Integer jdfx = (Integer)record.get("DC");
            double jyje = MapUtils.doubleValue(record, (Object)"AMMOUNT");
            if (jdfx == biggerJdfx) {
                lastBiggerRecord = record;
                double tempDxje = NumberUtils.round((double)(dxje * jyje / biggerDxje));
                remainDxje -= tempDxje;
                record.put("OFFSETVALUE", NumberUtils.doubleToString((double)tempDxje));
                continue;
            }
            record.put("OFFSETVALUE", NumberUtils.doubleToString((double)jyje));
        }
        if (remainDxje != 0.0 && lastBiggerRecord != null) {
            String dxjeStr = (String)lastBiggerRecord.get("OFFSETVALUE");
            double newDxje = Double.parseDouble(dxjeStr.replace(",", "")) + remainDxje;
            lastBiggerRecord.put("OFFSETVALUE", NumberUtils.doubleToString((double)newDxje));
        }
        jsonObject.put("recordData", records);
        jsonObject.put("totalJfJyje", (Object)NumberUtils.doubleToString((double)totalJfJyje));
        jsonObject.put("totalDfJyje", (Object)NumberUtils.doubleToString((double)totalDfJyje));
        jsonObject.put("dxje", (Object)NumberUtils.doubleToString((double)dxje));
        return jsonObject;
    }

    public void setTitles(List<Map<String, Object>> contents, String systemId, String orgType, String periodStr) {
        HashMap<String, String> ruleId2TitleCache = new HashMap<String, String>();
        HashMap<String, String> subject2TitleCache = new HashMap<String, String>();
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        HashMap<String, String> unitCode2TitleCache = new HashMap<String, String>();
        for (Map<String, Object> record : contents) {
            if (StringUtils.isEmpty((String)record.get("UNITID").toString())) continue;
            record.put("UNITTITLE", this.getUnitTitle(unitCode2TitleCache, (String)record.get("UNITID"), tool));
            record.put("OPPUNITTITLE", this.getUnitTitle(unitCode2TitleCache, (String)record.get("OPPUNITID"), tool));
            this.initSubjectCode(record, subject2TitleCache, systemId);
            String unionRuleId = (String)record.get("UNIONRULEID");
            if (null == unionRuleId) continue;
            String title = (String)ruleId2TitleCache.get(unionRuleId);
            if (null == title) {
                AbstractUnionRule rule = UnionRuleUtils.getAbstractUnionRuleById((String)unionRuleId);
                title = null == rule ? "" : rule.getTitle();
                ruleId2TitleCache.put(unionRuleId, title);
            }
            record.put("UNIONRULETITLE", title);
        }
    }

    private void initSubjectCode(Map<String, Object> record, Map<String, String> subject2TitleCache, String systemId) {
        ConsolidatedSubjectService subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        String subjectCode = (String)record.get("SUBJECTCODE");
        if (null != subjectCode) {
            String title = subject2TitleCache.get(subjectCode);
            if (null == title) {
                ConsolidatedSubjectEO subject = subjectService.getSubjectByCode(systemId, subjectCode);
                title = null == subject ? "" : subject.getTitle();
                subject2TitleCache.put(subjectCode, title);
            }
            record.put("SUBJECTTITLE", title);
        }
    }

    private String getUnitTitle(Map<String, String> unitCode2TitleCache, String unitCode, GcOrgCenterService tool) {
        if (org.springframework.util.StringUtils.isEmpty(unitCode)) {
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

    private List<Map<String, Object>> calMerge(List<GcOffsetRelatedItemEO> records, GcCalcArgmentsDTO gcCalcArgmentsDTO) {
        GcCalcEnvContextImpl env = new GcCalcEnvContextImpl();
        env.setCalcArgments(gcCalcArgmentsDTO);
        ArrayList<Integer> countList = new ArrayList<Integer>();
        for (int index = 0; index < records.size(); ++index) {
            GcOffsetRelatedItemEO entity = records.get(index);
            if (StringUtils.isEmpty((String)entity.getMatchingInformation()) || entity.getMatchingInformation().equals(MatchingInformationEnum.NO_FOUND_RULE.getMessage())) continue;
            countList.add(index);
        }
        if (!CollectionUtils.isEmpty(countList)) {
            throw new BusinessRuntimeException("\u9009\u62e9\u7684\u7b2c" + countList + "\u6761\u6570\u636e\u4e3a\u5339\u914d\u5931\u8d25\u6570\u636e\uff0c\u8bf7\u91cd\u65b0\u9009\u62e9\u3002");
        }
        ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        List<GcOffsetRelatedItemEO> hasRuleKeyRecords = records.stream().filter(record -> {
            if (StringUtils.isNull((String)record.getUnionRuleId())) {
                this.formatFieldValue((GcOffsetRelatedItemEO)record);
                resultList.add(record.getFields());
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        Map<String, List<GcOffsetRelatedItemEO>> ruleKey2DataList = this.initFinancialRule(hasRuleKeyRecords);
        ruleKey2DataList.forEach((ruleKey, gcOffsetRelatedItemEOList) -> {
            try {
                FinancialCheckRuleDTO financialCheckRuleDTO = this.initFinancialCheckRule((String)ruleKey);
                List<FinancialCheckRuleExecutorImpl.OffsetResult> offsetResults = new FinancialCheckBalanceExecutorHelper((AbstractUnionRule)financialCheckRuleDTO, gcCalcArgmentsDTO).autoCanOffsetItems((List<GcOffsetRelatedItemEO>)gcOffsetRelatedItemEOList);
                if (!CollectionUtils.isEmpty(offsetResults)) {
                    offsetResults.forEach(offsetResult -> {
                        this.putRecordDataSourcesID((FinancialCheckRuleExecutorImpl.OffsetResult)offsetResult);
                        resultList.addAll(offsetResult.getOffsetVchr().getItems().stream().map(gcOffSetVchrItemDTO -> {
                            gcOffSetVchrItemDTO.addFieldValue("DC", (Object)gcOffSetVchrItemDTO.getOrient().getValue());
                            gcOffSetVchrItemDTO.addFieldValue("CREDIT", (Object)gcOffSetVchrItemDTO.getOffSetCredit());
                            Integer direct = (Integer)gcOffSetVchrItemDTO.getFieldValue("DC");
                            if (null == direct) {
                                direct = OrientEnum.D.getValue();
                            }
                            if (direct.equals(OrientEnum.D.getValue())) {
                                gcOffSetVchrItemDTO.addFieldValue("AMMOUNT", gcOffSetVchrItemDTO.getFieldValue("OFFSETDEBIT"));
                                gcOffSetVchrItemDTO.addFieldValue("OFFSETVALUE", (Object)gcOffSetVchrItemDTO.getFieldValue("OFFSETDEBIT").toString());
                            } else {
                                gcOffSetVchrItemDTO.addFieldValue("AMMOUNT", gcOffSetVchrItemDTO.getFieldValue("OFFSETCREDIT"));
                                gcOffSetVchrItemDTO.addFieldValue("OFFSETVALUE", (Object)gcOffSetVchrItemDTO.getFieldValue("OFFSETCREDIT").toString());
                            }
                            return gcOffSetVchrItemDTO.getFields();
                        }).collect(Collectors.toList()));
                    });
                } else {
                    List result = gcOffsetRelatedItemEOList.stream().map(gcOffsetRelatedItemEO -> {
                        this.formatFieldValue((GcOffsetRelatedItemEO)gcOffsetRelatedItemEO);
                        return gcOffsetRelatedItemEO.getFields();
                    }).collect(Collectors.toList());
                    this.logger.info("\u83b7\u53d6\u624b\u52a8\u62b5\u9500\u6570\u636e\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u51ed\u8bc1\u7ea7\u5173\u8054\u4ea4\u6613\u89c4\u5219\uff0c\u8fd4\u56de\u539f\u59cb\u6570\u636e\uff1a{}", (Object)result);
                    resultList.addAll(result);
                }
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u624b\u52a8\u62b5\u9500\u6570\u636e\u8ba1\u7b97\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
        });
        this.initGcRelatedItemForInput(resultList, records);
        return resultList;
    }

    private void formatFieldValue(GcOffsetRelatedItemEO gcOffsetRelatedItemEO) {
        gcOffsetRelatedItemEO.addFieldValue("DATASOURCESID", (Object)gcOffsetRelatedItemEO.getRelatedItemId());
        if (gcOffsetRelatedItemEO.getDebit() != null && gcOffsetRelatedItemEO.getDebit() != 0.0) {
            gcOffsetRelatedItemEO.addFieldValue("AMMOUNT", (Object)gcOffsetRelatedItemEO.getDebit());
            gcOffsetRelatedItemEO.addFieldValue("OFFSETVALUE", (Object)gcOffsetRelatedItemEO.getDebit());
            gcOffsetRelatedItemEO.addFieldValue("DC", (Object)OrientEnum.D.getValue());
        } else {
            gcOffsetRelatedItemEO.addFieldValue("AMMOUNT", (Object)gcOffsetRelatedItemEO.getCredit());
            gcOffsetRelatedItemEO.addFieldValue("OFFSETVALUE", (Object)gcOffsetRelatedItemEO.getCredit());
            gcOffsetRelatedItemEO.addFieldValue("DC", (Object)OrientEnum.C.getValue());
        }
    }

    private void putRecordDataSourcesID(FinancialCheckRuleExecutorImpl.OffsetResult offsetResult) {
        List<Map> offsetVchrList = offsetResult.getOffsetVchr().getItems().stream().map(AbstractFieldDynamicDeclarator::getFields).collect(Collectors.toList());
        List<GcFcRuleUnOffsetDataDTO> offsetInputItemList = offsetResult.getOffsetedInputItems();
        offsetVchrList.forEach(offsetVchr -> {
            String id = (String)offsetVchr.get("SRCID");
            if (id == null) {
                return;
            }
            offsetInputItemList.forEach(offsetInputItem -> {
                if (offsetInputItem.getFieldValue("VCHROFFSETRELS") == null || !id.equals(offsetInputItem.getId())) {
                    return;
                }
                List relatedItemOffsetRelEOList = (List)offsetInputItem.getFieldValue("VCHROFFSETRELS");
                if (CollectionUtils.isEmpty(relatedItemOffsetRelEOList)) {
                    return;
                }
                String srcId = offsetVchr.getOrDefault("DATASOURCESID", "");
                String srcIdForItem = relatedItemOffsetRelEOList.stream().map(GcOffsetRelatedItemEO::getRelatedItemId).collect(Collectors.toList()).toString().replaceAll("\\[", "").replaceAll("]", "");
                offsetVchr.put("DATASOURCESID", StringUtils.isEmpty((String)srcId) ? srcIdForItem : srcId + "," + srcIdForItem);
                offsetVchr.put("CHECKSTATE", ((RelatedItemGcOffsetRelDTO)((Object)((Object)((Object)relatedItemOffsetRelEOList.get(0))))).getCheckState());
            });
        });
    }

    private void initGcRelatedItemForInput(List<Map<String, Object>> resultRecords, List<GcOffsetRelatedItemEO> records) {
        resultRecords.forEach(resultRecord -> {
            Double amt = (Double)resultRecord.get("AMT");
            Integer direct = (Integer)resultRecord.get("DC");
            if (null == direct) {
                direct = OrientEnum.D.getValue();
            }
            if (direct.equals(OrientEnum.D.getValue())) {
                resultRecord.put("DEBITVALUE", NumberUtils.doubleToString((Double)amt));
            } else {
                resultRecord.put("CREDITVALUE", NumberUtils.doubleToString((Double)amt));
            }
            if (resultRecord.containsKey("MDCODE")) {
                resultRecord.put("UNITID", resultRecord.get("MDCODE"));
            }
        });
    }

    private FinancialCheckRuleDTO initFinancialCheckRule(String ruleKey) {
        AbstractUnionRule abstractUnionRule = UnionRuleUtils.getAbstractUnionRuleById((String)ruleKey);
        return (FinancialCheckRuleDTO)abstractUnionRule;
    }

    private Map<String, List<GcOffsetRelatedItemEO>> initFinancialRule(List<GcOffsetRelatedItemEO> records) {
        return records.stream().collect(Collectors.groupingBy(GcOffsetRelatedItemEO::getUnionRuleId));
    }

    private void getFinancialCheckUnitStatus(List<Object> unitGuids, List<GcOffsetRelatedItemEO> records, GcCalcArgmentsDTO paramsVO) {
        if (unitGuids.size() == 0 || records.size() == 0) {
            Assert.isTrue((boolean)false, (String)"\u624b\u52a8\u62b5\u9500\u6570\u636e\u4e3a\u7a7a\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500", (Object[])new Object[0]);
        }
        DimensionParamsVO dimensionParamsVO = this.initFinancialCheckDimensionParamsVO(records.get(0), paramsVO);
        this.getUnitStatus(unitGuids, dimensionParamsVO);
    }

    private void getUnitStatus(List<Object> unitGuids, DimensionParamsVO dimensionParamsVO) {
        ReadWriteAccessDesc readWriteAccessDesc = this.getUnitReadWriteAccessDesc(unitGuids, dimensionParamsVO);
        Assert.isTrue((boolean)readWriteAccessDesc.getAble(), (String)readWriteAccessDesc.getDesc(), (Object[])new Object[0]);
    }

    private ReadWriteAccessDesc getUnitReadWriteAccessDesc(List<Object> unitGuids, DimensionParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        if (unitGuids.size() == 2) {
            GcOrgCacheVO org2;
            GcOrgCacheVO org1 = orgTool.getOrgByCode(unitGuids.get(0).toString());
            GcOrgCacheVO commonUnit = orgTool.getCommonUnit(org1, org2 = orgTool.getOrgByCode(unitGuids.get(1).toString()));
            if (commonUnit != null) {
                queryParamsVO.setOrgId(commonUnit.getCode());
                return new UploadStateTool().writeable(queryParamsVO);
            }
            return new ReadWriteAccessDesc(Boolean.valueOf(false), "\u624b\u52a8\u62b5\u9500\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u4e3a\u7a7a\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500");
        }
        if (unitGuids.size() == 1) {
            GcOrgCacheVO org = orgTool.getOrgByCode(unitGuids.get(0).toString());
            if (StringUtils.isEmpty((String)org.getParentId())) {
                return new ReadWriteAccessDesc(Boolean.valueOf(false), "\u624b\u52a8\u62b5\u9500\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u4e3a\u7a7a\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500");
            }
            queryParamsVO.setOrgId(org.getParentId());
            return new UploadStateTool().writeable(queryParamsVO);
        }
        return new ReadWriteAccessDesc(Boolean.valueOf(false), "\u624b\u52a8\u62b5\u9500\u5355\u4f4d\u6570\u636e\u5f02\u5e38\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500");
    }

    private DimensionParamsVO initFinancialCheckDimensionParamsVO(GcOffsetRelatedItemEO record, GcCalcArgmentsDTO paramsVO) {
        DimensionParamsVO queryParamsVO = new DimensionParamsVO();
        BeanUtils.copyProperties(record, queryParamsVO);
        queryParamsVO.setPeriodStr(paramsVO.getPeriodStr());
        queryParamsVO.setOrgTypeId(paramsVO.getOrgType());
        queryParamsVO.setOrgType(paramsVO.getOrgType());
        return queryParamsVO;
    }
}

