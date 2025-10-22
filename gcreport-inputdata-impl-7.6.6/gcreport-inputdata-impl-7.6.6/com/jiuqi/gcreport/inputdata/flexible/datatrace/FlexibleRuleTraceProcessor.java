/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor
 *  com.jiuqi.gcreport.datatrace.vo.FetchItemDTO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetExpressionExtendInfoVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.util.OffsetCoreConvertUtil
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig$Item
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.gcreport.inputdata.flexible.datatrace;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor;
import com.jiuqi.gcreport.datatrace.vo.FetchItemDTO;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO;
import com.jiuqi.gcreport.datatrace.vo.OffsetExpressionExtendInfoVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.inputdata.flexible.cache.FlexRuleCacheService;
import com.jiuqi.gcreport.inputdata.formula.GcInputDataFormulaEvalService;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.util.OffsetCoreConvertUtil;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class FlexibleRuleTraceProcessor
implements UnionRuleTraceProcessor {
    private GcOffSetVchrItemAdjustEO offsetItem;
    private FlexibleRuleDTO rule;
    private GcTaskBaseArguments taskArg;
    private ExecutorContext context;
    private static final String ALLLIST = "ALL";
    private DimensionValueSet dimensionValueSet;
    private GcInputDataFormulaEvalService inputDataFormulaEvalService;
    private FlexibleFetchConfig.Item currFetchItem;
    private Map<String, List<InputDataEO>> recordMap;
    private List<InputDataEO> dataSForTra;
    private InputDataEO inputData;
    private List<InputDataEO> dataSForFormula = new ArrayList<InputDataEO>();
    private String tableName;
    List<GcOffSetVchrItemDTO> offSetVchrItems;
    private GcOffSetItemAdjustCoreService gcOffSetItemAdjustCoreService;

    public static FlexibleRuleTraceProcessor newInstance(GcOffSetVchrItemAdjustEO offsetItem, AbstractUnionRule rule, GcTaskBaseArguments taskArg) {
        FlexibleRuleTraceProcessor processor = new FlexibleRuleTraceProcessor(offsetItem, (FlexibleRuleDTO)rule, taskArg);
        return processor;
    }

    public FlexibleRuleTraceProcessor(GcOffSetVchrItemAdjustEO offsetItem, FlexibleRuleDTO rule, GcTaskBaseArguments taskArg) {
        this.offsetItem = offsetItem;
        this.rule = ((FlexRuleCacheService)SpringBeanUtils.getBean(FlexRuleCacheService.class)).getFlexRule(rule);
        this.taskArg = taskArg;
        this.inputDataFormulaEvalService = (GcInputDataFormulaEvalService)SpringBeanUtils.getBean(GcInputDataFormulaEvalService.class);
        this.gcOffSetItemAdjustCoreService = (GcOffSetItemAdjustCoreService)SpringBeanUtils.getBean(GcOffSetItemAdjustCoreService.class);
        GcOrgCacheVO gcOrgCacheVO = this.getUnionCommonUnit();
        this.dimensionValueSet = DimensionUtils.generateDimSet((Object)gcOrgCacheVO.getCode(), (Object)offsetItem.getDefaultPeriod(), (Object)offsetItem.getOffSetCurr(), (Object)gcOrgCacheVO.getOrgTypeId(), (String)((String)offsetItem.getFieldValue("ADJUST")), (String)offsetItem.getTaskId());
        this.recordMap = this.groupByDc();
        this.offSetVchrItems = this.listOffSetVchrItem();
    }

    public List<FetchItemDTO> getFetchItem() {
        FlexibleFetchConfig flexibleFetchConfig = this.rule.getFetchConfigList().stream().filter(fetchConfig -> StringUtils.equalsAny((String)fetchConfig.getFetchSetGroupId(), (String[])new String[]{this.offsetItem.getFetchSetGroupId()})).findFirst().orElse(null);
        if (Objects.isNull(flexibleFetchConfig)) {
            return null;
        }
        List itemList = this.offsetItem.getOrient() == 1 ? flexibleFetchConfig.getDebitConfigList() : flexibleFetchConfig.getCreditConfigList();
        List<Object> fetchItems = itemList.stream().filter(item -> item.getSubjectCode().equals(this.offsetItem.getSubjectCode())).map(item -> new FetchItemDTO(item.getFetchFormula(), item)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(fetchItems)) {
            fetchItems = itemList.stream().filter(item -> !StringUtils.isEmpty((String)item.getFetchFormula()) && item.getFetchFormula().toUpperCase().contains("SUBJECTALLOCATION")).map(item -> new FetchItemDTO(item.getFetchFormula(), item)).collect(Collectors.toList());
        }
        return fetchItems;
    }

    public GcOffSetVchrItemAdjustEO getOffSetItem() {
        return this.offsetItem;
    }

    public ExecutorContext getExecutorContext() {
        if (Objects.isNull(this.context)) {
            this.context = new GcReportExceutorContext();
            try {
                this.context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            }
            catch (ParseException e) {
                throw new BusinessRuntimeException("\u516c\u5f0f\u6267\u884c\u73af\u5883\u51c6\u5907\u51fa\u9519\u3002", (Throwable)e);
            }
        }
        this.context.setDefaultGroupName(this.tableName);
        return this.context;
    }

    public AbstractData formulaEval(String formula) {
        if (CollectionUtils.isEmpty(this.recordMap)) {
            return AbstractData.valueOf((double)0.0);
        }
        FetchTypeEnum fetchType = this.currFetchItem.getFetchType();
        if (fetchType == FetchTypeEnum.DEBIT_SUM || fetchType == FetchTypeEnum.CREDIT_SUM || fetchType == FetchTypeEnum.SUM) {
            return this.getEvaluateFormulaValue(formula);
        }
        if (Objects.isNull((Object)this.inputData)) {
            return AbstractData.valueOf((double)0.0);
        }
        return this.inputDataFormulaEvalService.evaluateMxInputAbstractData(this.dimensionValueSet, formula, this.inputData, this.dataSForFormula);
    }

    public AbstractData formulaEval(OffsetAmtTraceItemVO offsetAmtTraceItemVO) {
        if (CollectionUtils.isEmpty(this.recordMap)) {
            return AbstractData.valueOf((double)0.0);
        }
        OffsetExpressionExtendInfoVO data = this.getSubjectAllocation(offsetAmtTraceItemVO.getExpression());
        if (Objects.nonNull(data)) {
            offsetAmtTraceItemVO.setOffsetAmtTraceItemShow(true);
            offsetAmtTraceItemVO.setExpressionExtendInfoShow(true);
            offsetAmtTraceItemVO.setOffsetExpressionExtendInfo(data);
            String value = NumberUtils.doubleToString((double)this.getOffSetItem().getOffsetAmt(), (int)10, (int)2, (boolean)true);
            offsetAmtTraceItemVO.setValue((Object)value);
            return new StringData(value);
        }
        return this.formulaEval(offsetAmtTraceItemVO.getExpression());
    }

    private GcOrgCacheVO getUnionCommonUnit() {
        YearPeriodObject yp = new YearPeriodObject(null, this.offsetItem.getDefaultPeriod());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)this.taskArg.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO localOrg = tool.getOrgByCode(this.offsetItem.getUnitId());
        Assert.isNotNull((Object)localOrg, (String)("\u83b7\u53d6\u4e0d\u5230\u5355\u4f4d\u4ee3\u7801\u201c" + this.offsetItem.getUnitId() + "\u201d\u65f6\u671f\uff1a\u201c" + this.offsetItem.getDefaultPeriod() + "\u201d\u7684\u5355\u4f4d\u4fe1\u606f\u3002"), (Object[])new Object[0]);
        GcOrgCacheVO oppOrg = tool.getOrgByCode(this.offsetItem.getOppUnitId());
        Assert.isNotNull((Object)oppOrg, (String)("\u83b7\u53d6\u4e0d\u5230\u5355\u4f4d\u4ee3\u7801\u201c" + this.offsetItem.getOppUnitId() + "\u201d\u65f6\u671f\uff1a\u201d" + this.offsetItem.getDefaultPeriod() + "\u201d\u7684\u5355\u4f4d\u4fe1\u606f\u3002"), (Object[])new Object[0]);
        GcOrgCacheVO mergeOrg = tool.getCommonUnit(localOrg, oppOrg);
        Assert.isNotNull((Object)mergeOrg, (String)("\u83b7\u53d6\u4e0d\u5230\u5355\u4f4d\u201c" + this.offsetItem.getUnitId() + "\u201d\u4e0e\u201c" + this.offsetItem.getOppUnitId() + "\u201d\u65f6\u671f\uff1a\u201d" + this.offsetItem.getDefaultPeriod() + "\u201d\u7684\u5355\u4f4d\u4fe1\u606f\u3002"), (Object[])new Object[0]);
        return mergeOrg;
    }

    private List<InputDataEO> getInputDataS() {
        this.tableName = ((InputDataNameProvider)SpringBeanUtils.getBean(InputDataNameProvider.class)).getTableNameByTaskId(this.offsetItem.getTaskId());
        EntNativeSqlDefaultDao inputDataSqlDao = EntNativeSqlDefaultDao.newInstance((String)this.tableName, InputDataEO.class);
        InputDataEO inputData = new InputDataEO();
        inputData.setOffsetGroupId(this.offsetItem.getSrcOffsetGroupId());
        String ColumnSql = SqlUtils.getColumnsSqlByTableDefine((String)this.tableName, (String)"i");
        String sql = "select " + ColumnSql + " from " + this.tableName + " i where i.OFFSETGROUPID = ?";
        List inputDataMaps = inputDataSqlDao.selectEntity(sql, new Object[]{this.offsetItem.getSrcOffsetGroupId()});
        return inputDataMaps;
    }

    public void initFetchItem(FetchItemDTO item) {
        this.currFetchItem = (FlexibleFetchConfig.Item)item.getFetchItem();
        FetchTypeEnum fetchType = this.currFetchItem.getFetchType();
        this.dataSForTra = this.getListForTra(fetchType);
        this.dataSForFormula = this.getListForFormual(fetchType);
        this.inputData = this.dataSForTra.stream().filter(inputData -> inputData.getId().equals(this.offsetItem.getSrcId())).findFirst().orElse(null);
    }

    public boolean takeOverTrace(FetchItemDTO item) {
        return ObjectUtils.isEmpty(item.getFormula());
    }

    public OffsetAmtTraceResultVO traceByTakeOver() {
        if (CollectionUtils.isEmpty(this.recordMap)) {
            return null;
        }
        OffsetAmtTraceResultVO result = new OffsetAmtTraceResultVO();
        OffsetAmtTraceItemVO offsetAmtTraceItem = new OffsetAmtTraceItemVO();
        result.setOffsetAmtTraceItems(Arrays.asList(offsetAmtTraceItem));
        FetchTypeEnum fetchType = this.currFetchItem.getFetchType();
        result.setFunction(fetchType.getName());
        offsetAmtTraceItem.setExpression(fetchType.getName());
        if (fetchType == FetchTypeEnum.DEBIT_SUM || fetchType == FetchTypeEnum.CREDIT_SUM || fetchType == FetchTypeEnum.SUM) {
            List allInputData = this.dataSForFormula.stream().filter(inputData -> inputData.getSubjectCode().equals(this.currFetchItem.getSubjectCode())).collect(Collectors.toList());
            double amtSum = 0.0;
            for (InputDataEO inputItem : allInputData) {
                amtSum += inputItem.getAmt().doubleValue();
            }
            result.setAmt(Double.valueOf(amtSum));
            offsetAmtTraceItem.setValue((Object)NumberUtils.doubleToString((double)amtSum, (int)10, (int)2, (boolean)true));
        } else {
            if (Objects.isNull((Object)this.inputData)) {
                return null;
            }
            Double offsetAmt = this.inputData.getAmt();
            result.setAmt(offsetAmt);
            offsetAmtTraceItem.setValue((Object)NumberUtils.doubleToString((double)offsetAmt, (int)10, (int)2, (boolean)true));
        }
        return result;
    }

    private Map<String, List<InputDataEO>> groupByDc() {
        List<InputDataEO> inputItems = this.getInputDataS();
        if (CollectionUtils.isEmpty(inputItems)) {
            return null;
        }
        HashMap<String, List<InputDataEO>> group = new HashMap<String, List<InputDataEO>>(16);
        ArrayList debitList = new ArrayList();
        ArrayList creditList = new ArrayList();
        group.put(OrientEnum.D.getCode(), debitList);
        group.put(OrientEnum.C.getCode(), creditList);
        group.put(ALLLIST, new ArrayList<InputDataEO>(inputItems));
        if (CollectionUtils.isEmpty(inputItems)) {
            return group;
        }
        inputItems.forEach(record -> {
            if (record.getDc().equals(OrientEnum.D.getValue())) {
                debitList.add(record);
            } else if (record.getDc().equals(OrientEnum.C.getValue())) {
                creditList.add(record);
            }
        });
        return group;
    }

    private List<InputDataEO> getListForFormual(FetchTypeEnum fetchType) {
        if (fetchType == FetchTypeEnum.DEBIT_SUM) {
            return this.recordMap.get(OrientEnum.D.getCode());
        }
        if (fetchType == FetchTypeEnum.CREDIT_SUM) {
            return this.recordMap.get(OrientEnum.C.getCode());
        }
        if (fetchType == FetchTypeEnum.ALL_DETAIL || fetchType == FetchTypeEnum.SUM || fetchType == FetchTypeEnum.DEBIT_DETAIL || fetchType == FetchTypeEnum.CREDIT_DETAIL) {
            return this.recordMap.get(ALLLIST);
        }
        return Collections.emptyList();
    }

    private List<InputDataEO> getListForTra(FetchTypeEnum fetchType) {
        if (fetchType == FetchTypeEnum.DEBIT_DETAIL) {
            return this.recordMap.get(OrientEnum.D.getCode());
        }
        if (fetchType == FetchTypeEnum.CREDIT_DETAIL) {
            return this.recordMap.get(OrientEnum.C.getCode());
        }
        if (fetchType == FetchTypeEnum.ALL_DETAIL) {
            return this.recordMap.get(ALLLIST);
        }
        return Collections.emptyList();
    }

    private List<GcOffSetVchrItemDTO> listOffSetVchrItem() {
        List<Object> offSetVchrItems = new ArrayList<GcOffSetVchrItemDTO>();
        if (Objects.isNull(this.offsetItem) || StringUtils.isEmpty((String)this.offsetItem.getSrcOffsetGroupId())) {
            return offSetVchrItems;
        }
        List vchrItemAdjustEOList = this.gcOffSetItemAdjustCoreService.listByWhere(new String[]{"srcOffsetGroupId"}, new Object[]{this.offsetItem.getSrcOffsetGroupId()});
        if (CollectionUtils.isEmpty(vchrItemAdjustEOList)) {
            return offSetVchrItems;
        }
        offSetVchrItems = vchrItemAdjustEOList.stream().map(item -> OffsetCoreConvertUtil.convertEO2DTO((GcOffSetVchrItemAdjustEO)item)).collect(Collectors.toList());
        return offSetVchrItems;
    }

    private OffsetExpressionExtendInfoVO getSubjectAllocation(String formula) {
        if (!StringUtils.isEmpty((String)formula) && formula.toUpperCase().contains("SUBJECTALLOCATION")) {
            String result = this.inputDataFormulaEvalService.evaluateInputDataSubjectAllocation(this.dimensionValueSet, formula, this.recordMap.get(ALLLIST));
            if (!StringUtils.isEmpty((String)result)) {
                Map resultObj = (Map)JsonUtils.readValue((String)result, (TypeReference)new TypeReference<Map<String, Object>>(){});
                List rateInfoBaseData = (List)resultObj.get("rateInfo");
                List tableDatas = rateInfoBaseData.stream().map(item -> item.get("fieldValMap")).collect(Collectors.toList());
                String tableName = (String)resultObj.get("tableName");
                OffsetExpressionExtendInfoVO offsetExpressionExtendInfo = new OffsetExpressionExtendInfoVO();
                offsetExpressionExtendInfo.setTableColumns(this.listTableColumns(tableName));
                offsetExpressionExtendInfo.setTableDatas(tableDatas);
                offsetExpressionExtendInfo.setCalcTitle("\u8ba1\u7b97\u8fc7\u7a0b:");
                offsetExpressionExtendInfo.setCalcProcessDes("\u6839\u636e\u5339\u914d\u6761\u4ef6\u83b7\u53d6\u5bf9\u5e94\u7684\u5206\u914d\u79d1\u76ee\u548c\u6bd4\u4f8b\uff0c\u751f\u6210\u5bf9\u5e94\u7684\u5206\u5f55");
                return offsetExpressionExtendInfo;
            }
            return null;
        }
        return null;
    }

    private List<GcOffSetVchrItemDTO> listOffSetVchrItemFilterCurrentOffsetItem(String formula) {
        if (!StringUtils.isEmpty((String)formula) && formula.toUpperCase().contains("YDXKMSUM")) {
            return this.offSetVchrItems.stream().filter(item -> !item.getId().equals(this.getOffSetItem().getId())).collect(Collectors.toList());
        }
        return this.offSetVchrItems;
    }

    private List<DesignFieldDefineVO> listTableColumns(String tableName) {
        ArrayList<DesignFieldDefineVO> tableColumns = new ArrayList<DesignFieldDefineVO>();
        DataModelDTO dataModelParam = new DataModelDTO();
        dataModelParam.setName(tableName);
        DataModelDO currDataModel = ((DataModelClient)SpringContextUtils.getBean(DataModelClient.class)).get(dataModelParam);
        BaseDataDefineDTO baseDataDefine = (BaseDataDefineDTO)JsonUtils.readValue((String)JsonUtils.writeValueAsString(currDataModel.getExtInfo().get("baseDataDefine")), BaseDataDefineDTO.class);
        Map defineObj = (Map)JsonUtils.readValue((String)baseDataDefine.getDefine(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        List showFields = (List)defineObj.get("showFields");
        if (CollectionUtils.isEmpty(showFields)) {
            showFields = (List)defineObj.get("defaultShowFields");
        }
        for (Object showField : showFields) {
            Map obj = (Map)showField;
            Object columnName = obj.get("columnName");
            Object columnTitle = obj.get("columnTitle");
            if (Objects.isNull(columnName) || Objects.isNull(columnTitle)) continue;
            DesignFieldDefineVO designFieldDefineVO = new DesignFieldDefineVO();
            designFieldDefineVO.setKey(columnName.toString());
            designFieldDefineVO.setLabel(columnTitle.toString());
            tableColumns.add(designFieldDefineVO);
        }
        return tableColumns;
    }

    private AbstractData getEvaluateFormulaValue(String formula) {
        if (!StringUtils.isEmpty((String)formula) && formula.toUpperCase().contains("SUBJECTALLOCATION")) {
            return new StringData(NumberUtils.doubleToString((double)this.getOffSetItem().getOffsetAmt(), (int)10, (int)2, (boolean)true));
        }
        List<GcOffSetVchrItemDTO> offsetItems = this.listOffSetVchrItemFilterCurrentOffsetItem(formula);
        return this.inputDataFormulaEvalService.evaluateSumInputAbstractData(this.dimensionValueSet, formula, this.dataSForFormula, offsetItems);
    }
}

