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
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.fetch.client.FetchDataRequestClient
 *  com.jiuqi.budget.component.domain.FormulaExeParam
 *  com.jiuqi.budget.component.service.FormulaExecService
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveContext
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveData
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandlerGather
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.FieldAdaptSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFixedSettingService
 *  com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFloatSettingService
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils
 *  com.jiuqi.gcreport.billextract.client.BillExtractSettingClient
 *  com.jiuqi.va.bill.intf.BillModel
 */
package com.jiuqi.gcreport.bde.bill.extract.impl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.fetch.client.FetchDataRequestClient;
import com.jiuqi.budget.component.domain.FormulaExeParam;
import com.jiuqi.budget.component.service.FormulaExecService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveContext;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveData;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandlerGather;
import com.jiuqi.gcreport.bde.bill.extract.impl.intf.BillExtractHandleMessage;
import com.jiuqi.gcreport.bde.bill.extract.impl.service.BillExtractExecuteService;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.FieldAdaptSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFixedSettingService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFloatSettingService;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils;
import com.jiuqi.gcreport.billextract.client.BillExtractSettingClient;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillExtractExecuteServiceImpl
implements BillExtractExecuteService {
    @Autowired
    private BillFixedSettingService fixedSettingService;
    @Autowired
    private BillFloatSettingService floatSettingService;
    @Autowired
    private FetchDataRequestClient fetchRequestClient;
    @Autowired
    private FormulaExecService formulaExecService;
    @Autowired
    private BillExtractSettingClient settingClient;
    @Autowired
    private IBillExtractHandlerGather handlerGather;
    private static final Pattern BILL_LOGIC_PATTERN = Pattern.compile("BILL\\[(.*?)\\]");
    private static final Logger LOGGER = LoggerFactory.getLogger(BillExtractExecuteServiceImpl.class);

    @Override
    public void doExecute(BillExtractHandleMessage message) {
        BillSettingCondiDTO settingCondi = new BillSettingCondiDTO();
        settingCondi.setSchemeId(message.getFetchSchemeId());
        settingCondi.setBillType(message.getBillDefine());
        HashSet tableSet = CollectionUtils.newHashSet();
        tableSet.addAll(this.fixedSettingService.listTableName(settingCondi));
        tableSet.addAll(this.floatSettingService.listTableName(settingCondi));
        tableSet.remove(message.getMasterTableName());
        Map billData = (Map)BdeClientUtil.parseResponse((BusinessResponseEntity)this.settingClient.getBill(message.getBillDefine(), message.getBillCode()));
        LOGGER.info("\u8bf7\u6c42\u4e0a\u4e0b\u6587:\n{}\u67e5\u8be2\u5230\u7684\u5355\u636e\u4fe1\u606f:{}\n", (Object)JsonUtils.writeValueAsString((Object)message), (Object)JsonUtils.writeValueAsString((Object)billData));
        BillExtractSaveContext saveContext = (BillExtractSaveContext)BeanConvertUtil.convert((Object)message, BillExtractSaveContext.class, (String[])new String[0]);
        BillExtractSaveData saveData = new BillExtractSaveData(billData);
        FetchResultDTO masterResult = this.doExtractSingleTable(message, billData, message.getMasterTableName(), message.getMasterTableName());
        saveData.setMasterResult(masterResult);
        for (String table : tableSet) {
            FetchResultDTO res = this.doExtractSingleTable(message, billData, message.getMasterTableName(), table);
            saveData.addItemResult(table, res);
        }
        this.handlerGather.getHandlerByModel(message.getBillModel()).doSave(saveContext, saveData);
    }

    @Override
    public void doExecute(BillModel model, BillExtractHandleMessage message) {
        BillSettingCondiDTO settingCondi = new BillSettingCondiDTO();
        settingCondi.setSchemeId(message.getFetchSchemeId());
        settingCondi.setBillType(message.getBillDefine());
        HashSet tableSet = CollectionUtils.newHashSet();
        tableSet.addAll(this.fixedSettingService.listTableName(settingCondi));
        tableSet.addAll(this.floatSettingService.listTableName(settingCondi));
        tableSet.remove(message.getMasterTableName());
        Map billData = model.getMaster().getData();
        LOGGER.info("\u8bf7\u6c42\u4e0a\u4e0b\u6587:\n{}\u67e5\u8be2\u5230\u7684\u5355\u636e\u4fe1\u606f:{}\n", (Object)JsonUtils.writeValueAsString((Object)message), (Object)JsonUtils.writeValueAsString((Object)billData));
        BillExtractSaveContext saveContext = (BillExtractSaveContext)BeanConvertUtil.convert((Object)message, BillExtractSaveContext.class, (String[])new String[0]);
        BillExtractSaveData saveData = new BillExtractSaveData(billData);
        FetchResultDTO masterResult = this.doExtractSingleTable(message, billData, message.getMasterTableName(), message.getMasterTableName());
        saveData.setMasterResult(masterResult);
        for (String table : tableSet) {
            FetchResultDTO res = this.doExtractSingleTable(message, billData, message.getMasterTableName(), table);
            saveData.addItemResult(table, res);
        }
        this.handlerGather.getHandlerByModel(message.getBillModel()).doSave(model, saveContext, saveData);
    }

    private FetchResultDTO doExtractSingleTable(BillExtractHandleMessage message, Map<String, Object> billData, String masterTableName, String tableName) {
        BillSettingCondiDTO settingCondi = new BillSettingCondiDTO();
        settingCondi.setSchemeId(message.getFetchSchemeId());
        settingCondi.setBillType(message.getBillDefine());
        settingCondi.setBillTable(tableName);
        List billFixedSettingList = this.fixedSettingService.getFixedSetting(settingCondi);
        BillFloatRegionConfigDTO billFloatSetting = this.floatSettingService.getFloatSetting(settingCondi);
        LOGGER.info("\u8bf7\u6c42\u4e0a\u4e0b\u6587:\n{}\u56fa\u5b9a\u53d6\u6570\u8bbe\u7f6e:{}\n\u6d6e\u52a8\u53d6\u6570\u8bbe\u7f6e:{}\n", JsonUtils.writeValueAsString((Object)message), JsonUtils.writeValueAsString((Object)billFixedSettingList), JsonUtils.writeValueAsString((Object)billFloatSetting));
        FetchRequestDTO fetchRequestDTO = new FetchRequestDTO(message.getRequestRunnerId(), message.getRequestInstcId(), message.getRequestTaskId(), RequestSourceTypeEnum.BILL_FETCH.getCode());
        fetchRequestDTO.setRouteNum(Integer.valueOf(BdeCommonUtil.getRouteNum()));
        FetchRequestContextDTO fetchContext = (FetchRequestContextDTO)BeanConvertUtil.convert((Object)message, FetchRequestContextDTO.class, (String[])new String[0]);
        fetchContext.setStartDateStr(message.getStartDateStr());
        fetchContext.setEndDateStr(message.getEndDateStr());
        fetchContext.setFormSchemeId(message.getBillDefine());
        fetchContext.setFormId(tableName);
        fetchContext.setRegionId(tableName);
        fetchContext.setIncludeUncharged(Boolean.valueOf(!Boolean.FALSE.equals(fetchContext.getIncludeUncharged())));
        HashMap extParam = CollectionUtils.newHashMap();
        Set<String> extParamSet = this.parseExtParam(billFloatSetting, masterTableName);
        Set<String> logicExtParamSet = this.parseLogicExtParam(billFixedSettingList);
        for (String param : extParamSet) {
            extParam.put(param, String.valueOf(billData.get(param.replace(masterTableName + ".", ""))));
        }
        for (String logicCode : logicExtParamSet) {
            extParam.put(logicCode, String.valueOf(billData.get(logicCode)));
        }
        fetchContext.setExtParam((Map)extParam);
        ArrayList<FetchRequestFixedSettingDTO> fixedSettingList = new ArrayList<FetchRequestFixedSettingDTO>();
        FetchRequestFixedSettingDTO fixedSetting = null;
        block2: for (BillFixedSettingDTO billFixedSetting : billFixedSettingList) {
            fixedSetting = (FetchRequestFixedSettingDTO)BeanConvertUtil.convert((Object)billFixedSetting, FetchRequestFixedSettingDTO.class, (String[])new String[0]);
            fixedSetting.setFieldDefineId(billFixedSetting.getDataField());
            fixedSetting.setFieldDefineTitle(billFixedSetting.getDataField());
            FormulaExeParam formulaExeParam = null;
            for (FieldAdaptSettingDTO fixedAdaptSetting : billFixedSetting.getFieldAdaptSettings()) {
                if (StringUtils.isEmpty((String)fixedAdaptSetting.getAdaptFormula()) || "#".equals(fixedAdaptSetting.getAdaptFormula())) {
                    fixedSetting.setLogicFormula(fixedAdaptSetting.getLogicFormula());
                    fixedSetting.setBizModelFormula((Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fixedAdaptSetting.getBizModelFormula()), (TypeReference)new TypeReference<Map<String, List<FixedFetchSourceRowSettingVO>>>(){}));
                    fixedSettingList.add(fixedSetting);
                    continue block2;
                }
                formulaExeParam = new FormulaExeParam();
                String gcOrgType = message.getRpUnitType();
                String adaptExpression = AdaptCondiUtils.expressionOrgTypeConvert((String)fixedAdaptSetting.getAdaptFormula(), (String)gcOrgType);
                formulaExeParam.setFormulaExpress(adaptExpression);
                HashMap<String, String> adaptContext = new HashMap<String, String>(8);
                adaptContext.put(AdaptContextEnum.MD_ORG.getKey(), message.getUnitCode());
                adaptContext.put(AdaptContextEnum.DATATIME.getKey(), message.getEndDateStr());
                adaptContext.put(gcOrgType, message.getUnitCode());
                formulaExeParam.setDimValMap(adaptContext);
                if (!this.formulaExecService.getAdaptVal(formulaExeParam)) continue;
                fixedSetting.setLogicFormula(fixedAdaptSetting.getLogicFormula());
                fixedSetting.setBizModelFormula((Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fixedAdaptSetting.getBizModelFormula()), (TypeReference)new TypeReference<Map<String, List<FixedFetchSourceRowSettingVO>>>(){}));
                fixedSettingList.add(fixedSetting);
                continue block2;
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
        BusinessResponseEntity fetchResponseEntity = this.fetchRequestClient.executeFetch(fetchRequestDTO);
        if (!Boolean.TRUE.equals(fetchResponseEntity.isSuccess())) {
            throw new BusinessRuntimeException(fetchResponseEntity.getErrorMessage());
        }
        return (FetchResultDTO)fetchResponseEntity.getData();
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
}

