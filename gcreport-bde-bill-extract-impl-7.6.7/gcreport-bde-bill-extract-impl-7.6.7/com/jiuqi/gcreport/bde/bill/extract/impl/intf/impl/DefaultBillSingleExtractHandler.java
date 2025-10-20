/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.constant.ArgumentValueEnum
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.fetch.client.FetchDataRequestClient
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.budget.component.domain.FormulaExeParam
 *  com.jiuqi.budget.component.service.FormulaExecService
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.single.BillSingleExtractHandleContext
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.single.BillSingleExtractSaveData
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.single.IBillSingleExtractHandler
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFetchCondiDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.FieldAdaptSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFetchCondiService
 *  com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFixedSettingService
 *  com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFloatSettingService
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils
 *  com.jiuqi.gcreport.billextract.client.BillExtractSettingClient
 *  com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.ruler.ModelFormulaHandle
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.utils.FormulaUtils
 */
package com.jiuqi.gcreport.bde.bill.extract.impl.intf.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.fetch.client.FetchDataRequestClient;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.budget.component.domain.FormulaExeParam;
import com.jiuqi.budget.component.service.FormulaExecService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.single.BillSingleExtractHandleContext;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.single.BillSingleExtractSaveData;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.single.IBillSingleExtractHandler;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFetchCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.FieldAdaptSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFetchCondiService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFixedSettingService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFloatSettingService;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils;
import com.jiuqi.gcreport.billextract.client.BillExtractSettingClient;
import com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.utils.FormulaUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultBillSingleExtractHandler
implements IBillSingleExtractHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBillSingleExtractHandler.class);
    @Autowired
    private BillExtractSettingClient billDefineClient;
    @Autowired
    private BillFetchCondiService fetchCondiService;
    @Autowired
    private BillFixedSettingService fixedSettingService;
    @Autowired
    private BillFloatSettingService floatSettingService;
    @Autowired
    private FetchDataRequestClient fetchRequestClient;
    @Autowired
    private FormulaExecService formulaExecService;
    private static final String FETCH_CONDI_FORMAT = "#FETCHCONDI[fetchCondiCode]";
    private static final String FETCH_CONDI_CODE = "fetchCondiCode";
    private static final String BILL_PARAM_FORMAT = "#MAIN_TABLE.COLUMN_CODE#";
    private static final Pattern BILL_LOGIC_PATTERN = Pattern.compile("BILL\\[(.*?)\\]");

    public String getModelCode() {
        return "DEFAULT";
    }

    public BillSingleExtractHandleContext parse(BillModel model, Map<String, Object> params) {
        BillSingleExtractHandleContext handleContext = new BillSingleExtractHandleContext();
        String unitCode = model.getMaster().getString("UNITCODE");
        String defineName = model.getDefine().getName();
        Assert.isNotEmpty((String)defineName, (String)"\u5355\u636e\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)unitCode, (String)"\u7ec4\u7ec7\u673a\u6784\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        BillSchemeConfigDTO schemeConfig = (BillSchemeConfigDTO)BdeClientUtil.parseResponse((BusinessResponseEntity)this.billDefineClient.getSchemeByOrgId(defineName, unitCode));
        if (schemeConfig == null || schemeConfig.getFetchSchemeId() == null) {
            throw new BusinessRuntimeException(String.format("\u5355\u4f4d\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u53d6\u6570\u65b9\u6848\uff0c\u8bf7\u68c0\u67e5\u53d6\u6570\u65b9\u6848\u914d\u7f6e", unitCode));
        }
        String fetchSchemeId = schemeConfig.getFetchSchemeId();
        BillFetchCondiDTO billFetchCondiDTO = this.fetchCondiService.queryBillFetchCondiDTOByFetchSchemeId(fetchSchemeId);
        String billDefineName = model.getDefine().getName();
        String billCode = model.getMaster().getString("BILLCODE");
        Map billData = model.getMaster().getData();
        String masterTableName = model.getMasterTable().getDefine().getName();
        String halfGUIDStr = UUIDUtils.newHalfGUIDStr();
        handleContext.setRequestRunnerId(halfGUIDStr);
        handleContext.setRequestInstcId(halfGUIDStr);
        handleContext.setRequestTaskId(halfGUIDStr);
        handleContext.setRequestSourceType(RequestSourceTypeEnum.BILL_FETCH.getCode());
        handleContext.setUsername(model.getContext().getUserCode());
        handleContext.setBillDefine(billDefineName);
        handleContext.setBillModel("DEFAULT");
        handleContext.setBillCode(billCode);
        handleContext.setFetchSchemeId(fetchSchemeId);
        handleContext.setMasterTableName(masterTableName);
        handleContext.setBblx("0");
        this.setContextFetchCondi(handleContext, billFetchCondiDTO, model);
        handleContext.setIncludeUncharged(Boolean.valueOf(true));
        handleContext.setBillData(billData);
        handleContext.getExtParam().putAll(this.buildExtParam(billData, fetchSchemeId, billDefineName, masterTableName));
        return handleContext;
    }

    private void setContextFetchCondi(BillSingleExtractHandleContext handleParam, BillFetchCondiDTO billFetchCondiDTO, BillModel model) {
        handleParam.setUnitCode(this.excuteBillFormula(model, billFetchCondiDTO.getUnitCode()));
        handleParam.setStartDateStr(this.excuteBillFormula(model, billFetchCondiDTO.getStartDate()));
        handleParam.setEndDateStr(this.excuteBillFormula(model, billFetchCondiDTO.getEndDate()));
        handleParam.setCurrency(this.excuteBillFormula(model, billFetchCondiDTO.getCurrency()));
        handleParam.setPeriodScheme(this.excuteBillFormula(model, billFetchCondiDTO.getReportPeriod()));
        handleParam.setGcUnitType(this.excuteBillFormula(model, billFetchCondiDTO.getGcUnitType()));
        HashMap<String, String> otherEntity = new HashMap<String, String>();
        otherEntity.put(ArgumentValueEnum.MD_CURRENCY.getCode(), handleParam.getCurrency());
        otherEntity.put(ArgumentValueEnum.MD_GCORGTYPE.getCode(), handleParam.getGcUnitType());
        otherEntity.put(ArgumentValueEnum.PERIODSCHEME.getCode(), handleParam.getPeriodScheme());
        handleParam.setOtherEntity(otherEntity);
        HashMap<String, String> extInfo = new HashMap<String, String>();
        List customFetchCondi = billFetchCondiDTO.getCustomFetchCondi();
        if (!CollectionUtils.isEmpty((Collection)customFetchCondi)) {
            for (String billMainTableFieldCode : customFetchCondi) {
                extInfo.put(billMainTableFieldCode, model.getMaster().getString(billMainTableFieldCode));
            }
        }
        handleParam.setExtParam(extInfo);
        handleParam.setBillFetchCondiDTO(billFetchCondiDTO);
    }

    private String excuteBillFormula(BillModel model, String formula) {
        if (StringUtils.isEmpty((String)formula)) {
            return "";
        }
        BillModelImpl billModel = (BillModelImpl)model;
        DataTableNodeContainerImpl tablesData = billModel.getData().getTables();
        DataTableImpl masterTable = (DataTableImpl)tablesData.getMasterTable();
        String tableName = model.getMasterTable().getDefine().getName();
        ListContainer rows = masterTable.getRows();
        DataRowImpl dataRow = (DataRowImpl)rows.get(0);
        Map<String, DataRow> rowMap = Stream.of(dataRow).collect(Collectors.toMap(k -> tableName, v -> v));
        FormulaUtils.adjustFormulaRows((Data)((Data)billModel.getPlugins().get(Data.class)), rowMap);
        try {
            IExpression expression = ModelFormulaHandle.getInstance().parse(new ModelDataContext((Model)model), formula, FormulaType.EXECUTE);
            Object evaluate = FormulaUtils.evaluate((Model)billModel, (IExpression)expression, rowMap);
            LOGGER.info("\u516c\u5f0f{}\u6267\u884c\u7ed3\u679c\uff1a{}", (Object)formula, (Object)JsonUtils.writeValueAsString((Object)evaluate));
            if (Objects.isNull(evaluate)) {
                return "";
            }
            if (evaluate instanceof GregorianCalendar) {
                GregorianCalendar calendar = (GregorianCalendar)evaluate;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
                return sdf.format(calendar.getTime());
            }
            return evaluate.toString();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5355\u636e\u516c\u5f0f\u89e3\u6790\u5f02\u5e38{}", (Throwable)e);
        }
    }

    private Map<String, String> buildExtParam(Map<String, Object> billData, String fetchSchemeId, String billType, String masterTableName) {
        BillSettingCondiDTO settingCondi = new BillSettingCondiDTO();
        settingCondi.setSchemeId(fetchSchemeId);
        settingCondi.setBillType(billType);
        settingCondi.setBillTable(masterTableName);
        List billFixedSettingList = this.fixedSettingService.getFixedSetting(settingCondi);
        BillFloatRegionConfigDTO billFloatSetting = this.floatSettingService.getFloatSetting(settingCondi);
        HashMap extParam = CollectionUtils.newHashMap();
        Set<String> extParamSet = this.parseExtParam(billFloatSetting, masterTableName);
        Set<String> logicExtParamSet = this.parseLogicExtParam(billFixedSettingList);
        for (String param : extParamSet) {
            extParam.put(param, String.valueOf(billData.get(param.replace(masterTableName + ".", ""))));
        }
        for (String logicCode : logicExtParamSet) {
            extParam.put(logicCode, String.valueOf(billData.get(logicCode)));
        }
        return extParam;
    }

    public void doCheck(BillSingleExtractHandleContext context) {
        Assert.isNotEmpty((String)context.getUnitCode(), (String)"BDE\u53d6\u6570\u5931\u8d25\uff0c\u7ec4\u7ec7\u673a\u6784\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)context.getStartDateStr(), (String)"BDE\u53d6\u6570\u5931\u8d25\uff0c\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)context.getEndDateStr(), (String)"BDE\u53d6\u6570\u5931\u8d25\uff0c\u7ed3\u675f\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Map extParam = context.getExtParam();
        for (Map.Entry entry : extParam.entrySet()) {
            if (!StringUtils.isEmpty((String)((String)entry.getValue()))) continue;
            LOGGER.warn("BDE\u53d6\u6570\u4f9d\u8d56\u53c2\u6570\u7f3a\u5931{}", entry.getKey());
        }
    }

    public BillSingleExtractSaveData fetchData(BillModel model, BillSingleExtractHandleContext context) {
        BillSingleExtractSaveData savedData = new BillSingleExtractSaveData();
        FetchResultDTO fetchResultDTO = this.mainTableFetchData(context);
        savedData.setMasterResult(fetchResultDTO);
        Map<String, FetchResultDTO> childrenTableFetchData = this.childrenTableFetchData(context);
        savedData.setItemResult(childrenTableFetchData);
        return savedData;
    }

    private Map<String, FetchResultDTO> childrenTableFetchData(BillSingleExtractHandleContext context) {
        BillSettingCondiDTO settingCondi = new BillSettingCondiDTO();
        settingCondi.setSchemeId(context.getFetchSchemeId());
        settingCondi.setBillType(context.getBillDefine());
        Set childrenTableNames = this.floatSettingService.listTableName(settingCondi);
        HashMap<String, FetchResultDTO> childrenTableFetchResult = new HashMap<String, FetchResultDTO>();
        for (String childrenTableName : childrenTableNames) {
            childrenTableFetchResult.put(childrenTableName, this.singleChildTableFetch(context, childrenTableName));
        }
        return childrenTableFetchResult;
    }

    private FetchResultDTO singleChildTableFetch(BillSingleExtractHandleContext context, String childrenTableName) {
        BillSettingCondiDTO settingCondi = new BillSettingCondiDTO();
        settingCondi.setSchemeId(context.getFetchSchemeId());
        settingCondi.setBillType(context.getBillDefine());
        settingCondi.setBillTable(childrenTableName);
        List billFixedSettingList = this.fixedSettingService.getFixedSetting(settingCondi);
        BillFloatRegionConfigDTO billFloatSetting = this.floatSettingService.getFloatSetting(settingCondi);
        List<BillFixedSettingDTO> parsedFixedSettingDTOS = this.replaceParamInFixedSetting(context, billFixedSettingList);
        BillFloatRegionConfigDTO billFloatRegionConfigDTO = this.replaceParamInFloatSetting(context, billFloatSetting);
        return this.doExtractSingleTable(context, context.getMasterTableName(), parsedFixedSettingDTOS, billFloatRegionConfigDTO);
    }

    private FetchResultDTO mainTableFetchData(BillSingleExtractHandleContext context) {
        BillSettingCondiDTO settingCondi = new BillSettingCondiDTO();
        settingCondi.setSchemeId(context.getFetchSchemeId());
        settingCondi.setBillType(context.getBillDefine());
        settingCondi.setBillTable(context.getMasterTableName());
        List billFixedSettingList = this.fixedSettingService.getFixedSetting(settingCondi);
        List<BillFixedSettingDTO> parsedFixedSettingDTOS = this.replaceParamInFixedSetting(context, billFixedSettingList);
        return this.doExtractSingleTable(context, context.getMasterTableName(), parsedFixedSettingDTOS, null);
    }

    private List<BillFixedSettingDTO> replaceParamInFixedSetting(BillSingleExtractHandleContext context, List<BillFixedSettingDTO> billFixedSettingList) {
        Map<String, String> billParamParsedMap = this.buildBillParamMap(context);
        Map<String, String> fetchConditionParsedMap = this.buildFetchCondiValueMap(context);
        HashMap<String, String> extendParamParsedMap = new HashMap<String, String>();
        extendParamParsedMap.putAll(billParamParsedMap);
        extendParamParsedMap.putAll(fetchConditionParsedMap);
        ArrayList<BillFixedSettingDTO> parsedSettings = new ArrayList<BillFixedSettingDTO>();
        for (BillFixedSettingDTO billFixedSettingDTO : billFixedSettingList) {
            String parsedSettingJsonStr = JsonUtils.writeValueAsString((Object)billFixedSettingDTO);
            for (Map.Entry extendParamEntry : extendParamParsedMap.entrySet()) {
                parsedSettingJsonStr = parsedSettingJsonStr.replace((CharSequence)extendParamEntry.getKey(), StringUtils.isEmpty((String)((String)extendParamEntry.getValue())) ? "0" : (CharSequence)extendParamEntry.getValue());
            }
            BillFixedSettingDTO parsedFixedSettingDTO = (BillFixedSettingDTO)JsonUtils.readValue((String)parsedSettingJsonStr, BillFixedSettingDTO.class);
            parsedSettings.add(parsedFixedSettingDTO);
        }
        return parsedSettings;
    }

    private Map<String, String> buildBillParamMap(BillSingleExtractHandleContext context) {
        Map billData = context.getBillData();
        String masterTableName = context.getMasterTableName();
        HashMap<String, String> billColumnMap = new HashMap<String, String>();
        for (Map.Entry nameValueEntry : billData.entrySet()) {
            Object value = nameValueEntry.getValue();
            if (!Objects.nonNull(value)) continue;
            billColumnMap.put(BILL_PARAM_FORMAT.replace("MAIN_TABLE", masterTableName).replace("COLUMN_CODE", (CharSequence)nameValueEntry.getKey()), value.toString());
        }
        return billColumnMap;
    }

    private Map<String, String> buildFetchCondiValueMap(BillSingleExtractHandleContext context) {
        String key;
        Map extParam = context.getExtParam();
        BillFetchCondiDTO billFetchCondiDTO = context.getBillFetchCondiDTO();
        HashMap<String, String> fetchCondiValueMap = new HashMap<String, String>();
        if (!StringUtils.isEmpty((String)billFetchCondiDTO.getUnitCode())) {
            key = FETCH_CONDI_FORMAT.replace(FETCH_CONDI_CODE, "unitCode");
            fetchCondiValueMap.put(key, billFetchCondiDTO.getUnitCode());
        }
        if (!StringUtils.isEmpty((String)billFetchCondiDTO.getStartDate())) {
            key = FETCH_CONDI_FORMAT.replace(FETCH_CONDI_CODE, "startDate");
            fetchCondiValueMap.put(key, billFetchCondiDTO.getStartDate());
        }
        if (!StringUtils.isEmpty((String)billFetchCondiDTO.getEndDate())) {
            key = FETCH_CONDI_FORMAT.replace(FETCH_CONDI_CODE, "endDate");
            fetchCondiValueMap.put(key, billFetchCondiDTO.getEndDate());
        }
        if (!StringUtils.isEmpty((String)billFetchCondiDTO.getCurrency())) {
            key = FETCH_CONDI_FORMAT.replace(FETCH_CONDI_CODE, "currency");
            fetchCondiValueMap.put(key, billFetchCondiDTO.getCurrency());
        }
        if (!StringUtils.isEmpty((String)billFetchCondiDTO.getReportPeriod())) {
            key = FETCH_CONDI_FORMAT.replace(FETCH_CONDI_CODE, "reportPeriod");
            fetchCondiValueMap.put(key, billFetchCondiDTO.getReportPeriod());
        }
        if (!StringUtils.isEmpty((String)billFetchCondiDTO.getGcUnitType())) {
            key = FETCH_CONDI_FORMAT.replace(FETCH_CONDI_CODE, "gcUnitType");
            fetchCondiValueMap.put(key, billFetchCondiDTO.getGcUnitType());
        }
        if (!CollectionUtils.isEmpty((Collection)billFetchCondiDTO.getCustomFetchCondi())) {
            for (String mainTableColumnName : billFetchCondiDTO.getCustomFetchCondi()) {
                String key2 = FETCH_CONDI_FORMAT.replace(FETCH_CONDI_CODE, mainTableColumnName);
                fetchCondiValueMap.put(key2, (String)extParam.get(mainTableColumnName));
            }
        }
        return fetchCondiValueMap;
    }

    private BillFloatRegionConfigDTO replaceParamInFloatSetting(BillSingleExtractHandleContext context, BillFloatRegionConfigDTO billFloatSetting) {
        Map<String, String> billParamParsedMap = this.buildBillParamMap(context);
        Map<String, String> fetchConditionParsedMap = this.buildFetchCondiValueMap(context);
        HashMap<String, String> extendParamParsedMap = new HashMap<String, String>();
        extendParamParsedMap.putAll(billParamParsedMap);
        extendParamParsedMap.putAll(fetchConditionParsedMap);
        String parsedSettingJsonStr = JsonUtils.writeValueAsString((Object)billFloatSetting);
        for (Map.Entry extendParamEntry : extendParamParsedMap.entrySet()) {
            parsedSettingJsonStr = parsedSettingJsonStr.replace((CharSequence)extendParamEntry.getKey(), StringUtils.isEmpty((String)((String)extendParamEntry.getValue())) ? "0" : (CharSequence)extendParamEntry.getValue());
        }
        BillFloatRegionConfigDTO parsedFixedSettingDTO = (BillFloatRegionConfigDTO)JsonUtils.readValue((String)parsedSettingJsonStr, BillFloatRegionConfigDTO.class);
        return parsedFixedSettingDTO;
    }

    private FetchResultDTO doExtractSingleTable(BillSingleExtractHandleContext context, String tableName, List<BillFixedSettingDTO> billFixedSettingList, BillFloatRegionConfigDTO billFloatSetting) {
        LOGGER.info("\u8bf7\u6c42\u4e0a\u4e0b\u6587:{}\n\u56fa\u5b9a\u53d6\u6570\u8bbe\u7f6e:{}\n\u6d6e\u52a8\u53d6\u6570\u8bbe\u7f6e:{}\n", JsonUtils.writeValueAsString((Object)context), JsonUtils.writeValueAsString(billFixedSettingList), JsonUtils.writeValueAsString((Object)billFloatSetting));
        FetchRequestDTO fetchRequestDTO = new FetchRequestDTO(context.getRequestRunnerId(), context.getRequestInstcId(), context.getRequestTaskId(), RequestSourceTypeEnum.BILL_FETCH.getCode());
        fetchRequestDTO.setRouteNum(Integer.valueOf(BdeCommonUtil.getRouteNum()));
        FetchRequestContextDTO fetchContext = (FetchRequestContextDTO)BeanConvertUtil.convert((Object)context, FetchRequestContextDTO.class, (String[])new String[0]);
        fetchContext.setStartDateStr(context.getStartDateStr());
        fetchContext.setEndDateStr(context.getEndDateStr());
        fetchContext.setFormSchemeId(context.getBillDefine());
        fetchContext.setFormId(tableName);
        fetchContext.setRegionId(tableName);
        fetchContext.setIncludeUncharged(Boolean.valueOf(!Boolean.FALSE.equals(fetchContext.getIncludeUncharged())));
        ArrayList<FetchRequestFixedSettingDTO> fixedSettingList = new ArrayList<FetchRequestFixedSettingDTO>();
        FetchRequestFixedSettingDTO fixedSetting = null;
        block0: for (BillFixedSettingDTO billFixedSetting : billFixedSettingList) {
            fixedSetting = (FetchRequestFixedSettingDTO)BeanConvertUtil.convert((Object)billFixedSetting, FetchRequestFixedSettingDTO.class, (String[])new String[0]);
            fixedSetting.setFieldDefineId(billFixedSetting.getDataField());
            fixedSetting.setFieldDefineTitle(billFixedSetting.getDataField());
            FormulaExeParam formulaExeParam = null;
            for (FieldAdaptSettingDTO fixedAdaptSetting : billFixedSetting.getFieldAdaptSettings()) {
                if (StringUtils.isEmpty((String)fixedAdaptSetting.getAdaptFormula()) || "#".equals(fixedAdaptSetting.getAdaptFormula())) {
                    fixedSetting.setLogicFormula(fixedAdaptSetting.getLogicFormula());
                    fixedSetting.setBizModelFormula((Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fixedAdaptSetting.getBizModelFormula()), (TypeReference)new TypeReference<Map<String, List<FixedFetchSourceRowSettingVO>>>(){}));
                    fixedSettingList.add(fixedSetting);
                    continue block0;
                }
                formulaExeParam = new FormulaExeParam();
                String gcOrgType = StringUtils.isEmpty((String)context.getGcUnitType()) ? "" : context.getGcUnitType();
                String adaptExpression = AdaptCondiUtils.expressionOrgTypeConvert((String)fixedAdaptSetting.getAdaptFormula(), (String)gcOrgType);
                formulaExeParam.setFormulaExpress(adaptExpression);
                HashMap<String, String> adaptContext = new HashMap<String, String>(8);
                adaptContext.put(AdaptContextEnum.MD_ORG.getKey(), context.getUnitCode());
                adaptContext.put(AdaptContextEnum.DATATIME.getKey(), context.getEndDateStr());
                adaptContext.put(gcOrgType, context.getUnitCode());
                formulaExeParam.setDimValMap(adaptContext);
                if (!this.formulaExecService.getAdaptVal(formulaExeParam)) continue;
                fixedSetting.setLogicFormula(fixedAdaptSetting.getLogicFormula());
                fixedSetting.setBizModelFormula((Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fixedAdaptSetting.getBizModelFormula()), (TypeReference)new TypeReference<Map<String, List<FixedFetchSourceRowSettingVO>>>(){}));
                fixedSettingList.add(fixedSetting);
                continue block0;
            }
        }
        fetchRequestDTO.setFetchContext(fetchContext);
        if (CollectionUtils.isEmpty(fixedSettingList) && (billFloatSetting == null || billFloatSetting.getQueryConfigInfo() == null)) {
            return null;
        }
        if (billFloatSetting != null && billFloatSetting.getQueryConfigInfo() != null) {
            fetchRequestDTO.setFloatSetting(new FetchRequestFloatSettingDTO(billFloatSetting.getQueryType(), billFloatSetting.getQueryConfigInfo()));
        }
        fetchRequestDTO.setFixedSetting(fixedSettingList);
        LOGGER.debug("BDE\u5355\u636e\u53d6\u6570\u62a5\u6587{}", (Object)JsonUtils.writeValueAsString((Object)fetchRequestDTO));
        BusinessResponseEntity fetchResponseEntity = this.fetchRequestClient.executeFetch(fetchRequestDTO);
        if (!Boolean.TRUE.equals(fetchResponseEntity.isSuccess())) {
            throw new BusinessRuntimeException(fetchResponseEntity.getErrorMessage());
        }
        FetchResultDTO fetchResultDTO = (FetchResultDTO)fetchResponseEntity.getData();
        LOGGER.debug("BDE\u5355\u636e\u53d6\u6570\u8fd4\u56de\u7ed3\u679c{}", (Object)JsonUtils.writeValueAsString((Object)fetchResultDTO));
        return fetchResultDTO;
    }

    private Set<String> parseExtParam(BillFloatRegionConfigDTO billFloatSetting, String masterTableName) {
        if (billFloatSetting == null || billFloatSetting.getQueryConfigInfo() == null) {
            return CollectionUtils.newHashSet();
        }
        Pattern CTX_PARAM_PATTERN = Pattern.compile(String.format("#%1$s\\.[a-zA-Z0-9_]+#", masterTableName));
        Matcher matcher = CTX_PARAM_PATTERN.matcher(billFloatSetting.getQueryConfigInfo().getPluginData());
        HashSet ctxParamSet = CollectionUtils.newHashSet();
        String ctxParam = "";
        while (matcher.find()) {
            ctxParam = matcher.group();
            ctxParamSet.add(ctxParam.toUpperCase().replace("#", ""));
        }
        HashSet sysArgSet = CollectionUtils.newHashSet();
        for (ArgumentValueEnum argumentValue : ArgumentValueEnum.values()) {
            sysArgSet.add(argumentValue.getCode());
        }
        HashSet extParamSet = CollectionUtils.newHashSet();
        for (String param : ctxParamSet) {
            if (sysArgSet.contains(param)) continue;
            extParamSet.add(param);
        }
        return extParamSet;
    }

    private Set<String> parseLogicExtParam(List<BillFixedSettingDTO> billFixedSettingList) {
        HashSet extParamSet = CollectionUtils.newHashSet();
        for (BillFixedSettingDTO billFixedSettingDTO : billFixedSettingList) {
            for (FieldAdaptSettingDTO fieldAdaptSetting : billFixedSettingDTO.getFieldAdaptSettings()) {
                String logicFormula = fieldAdaptSetting.getLogicFormula();
                if (StringUtils.isEmpty((String)logicFormula)) continue;
                Matcher matcher = BILL_LOGIC_PATTERN.matcher(logicFormula);
                while (matcher.find()) {
                    extParamSet.add(matcher.group(1));
                }
            }
        }
        return extParamSet;
    }

    public void doSave(BillModel model, BillSingleExtractHandleContext saveContext, BillSingleExtractSaveData saveData) {
        if (saveData == null || saveData.getMasterResult() == null) {
            return;
        }
        String saveDataJson = JsonUtils.writeValueAsString((Object)saveData);
        LOGGER.debug("\u3010\u5355\u636e\u53d6\u6570\u53d6\u6570\u7ed3\u679c\u6570\u636e\u3011saveData:{}", (Object)saveDataJson);
        for (Map.Entry masterEntry : saveData.getMasterResult().getFixedResults().entrySet()) {
            model.getMaster().setValue((String)masterEntry.getKey(), masterEntry.getValue());
        }
        Map itemResult = saveData.getItemResult();
        for (Map.Entry resultEntry : itemResult.entrySet()) {
            String tableName = (String)resultEntry.getKey();
            FetchResultDTO value = (FetchResultDTO)resultEntry.getValue();
            FloatRegionResultDTO floatResults = value.getFloatResults();
            if (Objects.isNull(floatResults)) continue;
            Map floatColumns = floatResults.getFloatColumns();
            ArrayList newRowsDatas = new ArrayList();
            for (Object[] rowData : floatResults.getRowDatas()) {
                HashMap rowResult = new HashMap();
                for (Map.Entry nameIndexEntry : floatColumns.entrySet()) {
                    rowResult.put(nameIndexEntry.getKey(), rowData[(Integer)nameIndexEntry.getValue()]);
                }
                newRowsDatas.add(rowResult);
            }
            newRowsDatas.forEach(row -> model.getTable(tableName).appendRow(row));
        }
    }
}

