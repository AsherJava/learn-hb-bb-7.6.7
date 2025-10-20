/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO
 *  com.jiuqi.bde.fetch.client.FetchDataRequestClient
 *  com.jiuqi.budget.component.domain.FormulaExeParam
 *  com.jiuqi.budget.component.service.FormulaExecService
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.gcreport.bde.fetch.client.dto.FetchExecuteMessage
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil
 */
package com.jiuqi.gcreport.bde.fetch.impl.syncfetch.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestContextDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO;
import com.jiuqi.bde.common.dto.fetch.result.FetchResultDTO;
import com.jiuqi.bde.fetch.client.FetchDataRequestClient;
import com.jiuqi.budget.component.domain.FormulaExeParam;
import com.jiuqi.budget.component.service.FormulaExecService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.gcreport.bde.fetch.client.dto.FetchExecuteMessage;
import com.jiuqi.gcreport.bde.fetch.impl.common.ExecuteStateEnum;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchItemTaskLogEO;
import com.jiuqi.gcreport.bde.fetch.impl.handler.FixedFetchResultHandler;
import com.jiuqi.gcreport.bde.fetch.impl.handler.FloatFetchResultHandler;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchTaskLogService;
import com.jiuqi.gcreport.bde.fetch.impl.syncfetch.service.GcSyncFetchExecuteService;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchFloatSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.AdaptCondiUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcSyncFetchExecuteServiceImpl
implements GcSyncFetchExecuteService {
    @Autowired
    private FetchSettingService fetchSettingService;
    @Autowired
    private FetchFloatSettingService fetchFloatSettingService;
    @Autowired
    private FormulaExecService formulaExecService;
    @Autowired
    private FetchDataRequestClient fetchRequestClient;
    @Autowired
    private GcFetchTaskLogService taskLogService;

    @Override
    public void doFetchExecute(FetchExecuteMessage message, GcFetchItemTaskLogEO itemTaskLog) {
        FetchSettingCond fetchSettingCond = new FetchSettingCond(message.getFetchSchemeId(), message.getFormSchemeId(), message.getFormId(), message.getRegionId());
        List listDataLinkFixedSettingRowRecords = this.fetchSettingService.listDataLinkFixedSettingRowRecords(fetchSettingCond);
        FloatRegionConfigVO fetchFloatSetting = this.fetchFloatSettingService.getFetchFloatSetting(fetchSettingCond);
        if (message.getFetchRangResult() != null) {
            Set dataLinkIds = message.getFetchRangResult().getFromDataLinkMap().computeIfAbsent(message.getFormId(), k -> new HashSet());
            listDataLinkFixedSettingRowRecords = listDataLinkFixedSettingRowRecords.stream().filter(item -> dataLinkIds.contains(item.getDataLinkId())).collect(Collectors.toList());
        }
        FetchRequestDTO fetchRequestDTO = new FetchRequestDTO(message.getRequestRunnerId(), message.getRequestInstcId(), message.getRequestTaskId(), message.getRequestSourceType());
        FetchRequestContextDTO fetchContext = (FetchRequestContextDTO)BeanConvertUtil.convert((Object)message, FetchRequestContextDTO.class, (String[])new String[0]);
        ArrayList<FetchRequestFixedSettingDTO> fixedSettingList = new ArrayList<FetchRequestFixedSettingDTO>();
        FetchRequestFixedSettingDTO fixedSetting = null;
        block2: for (FixedFieldDefineSettingDTO fieldDefineSettingVO : listDataLinkFixedSettingRowRecords) {
            fixedSetting = (FetchRequestFixedSettingDTO)BeanConvertUtil.convert((Object)fieldDefineSettingVO, FetchRequestFixedSettingDTO.class, (String[])new String[0]);
            FormulaExeParam formulaExeParam = null;
            for (FixedAdaptSettingDTO fixedAdaptSetting : fieldDefineSettingVO.getFixedSettingData()) {
                if (StringUtils.isEmpty((String)fixedAdaptSetting.getAdaptFormula()) || "#".equals(fixedAdaptSetting.getAdaptFormula())) {
                    fixedSetting.setLogicFormula(fixedAdaptSetting.getLogicFormula());
                    fixedSetting.setBizModelFormula((Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fixedAdaptSetting.getBizModelFormula()), (TypeReference)new TypeReference<Map<String, List<FixedFetchSourceRowSettingVO>>>(){}));
                    fixedSettingList.add(fixedSetting);
                    continue block2;
                }
                formulaExeParam = new FormulaExeParam();
                String orgType = FetchTaskUtil.getOrgTypeByTaskAndCtx((String)message.getTaskId());
                String adaptExpression = AdaptCondiUtils.expressionOrgTypeConvert((String)fixedAdaptSetting.getAdaptFormula(), (String)orgType);
                formulaExeParam.setFormulaExpress(adaptExpression);
                HashMap<String, String> adaptContext = new HashMap<String, String>(8);
                adaptContext.put(AdaptContextEnum.MD_ORG.getKey(), message.getUnitCode());
                adaptContext.put(AdaptContextEnum.DATATIME.getKey(), message.getPeriodScheme());
                adaptContext.put(orgType, message.getUnitCode());
                formulaExeParam.setDimValMap(adaptContext);
                if (!this.formulaExecService.getAdaptVal(formulaExeParam)) continue;
                fixedSetting.setLogicFormula(fixedAdaptSetting.getLogicFormula());
                fixedSetting.setBizModelFormula((Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fixedAdaptSetting.getBizModelFormula()), (TypeReference)new TypeReference<Map<String, List<FixedFetchSourceRowSettingVO>>>(){}));
                fixedSettingList.add(fixedSetting);
                continue block2;
            }
        }
        fetchRequestDTO.setFetchContext(fetchContext);
        if (fetchFloatSetting != null) {
            fetchRequestDTO.setFloatSetting(new FetchRequestFloatSettingDTO(fetchFloatSetting.getQueryType(), fetchFloatSetting.getQueryConfigInfo()));
        }
        fetchRequestDTO.setFixedSetting(fixedSettingList);
        BusinessResponseEntity fetchResponseEntity = this.fetchRequestClient.executeFetch(fetchRequestDTO);
        this.updateFetchItemTaskLog(itemTaskLog, ExecuteStateEnum.PROCESSING, 0.5, "\u53d6\u6570\u4efb\u52a1\u6267\u884c\u5df2\u5b8c\u6210");
        if (Boolean.TRUE.equals(fetchResponseEntity.isSuccess())) {
            try {
                if (fetchRequestDTO.getFloatSetting() == null && fetchResponseEntity.getData() != null && ((FetchResultDTO)fetchResponseEntity.getData()).getFixedResults() != null) {
                    new FixedFetchResultHandler(fetchRequestDTO, (FetchResultDTO)fetchResponseEntity.getData()).save();
                }
                new FloatFetchResultHandler(fetchRequestDTO, (FetchResultDTO)fetchResponseEntity.getData()).save();
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u56de\u5199\u62a5\u8868\u6570\u636e\u51fa\u73b0\u9519\u8bef\uff1a" + e.getMessage(), (Throwable)e);
            }
        } else {
            throw new BusinessRuntimeException("\u53d6\u6570\u51fa\u73b0\u9519\u8bef\uff1a" + fetchResponseEntity.getErrorMessage());
        }
        this.updateFetchItemTaskLog(itemTaskLog, ExecuteStateEnum.FINISHED, 1.0, "\u53d6\u6570\u540e\u56de\u5199\u5df2\u5b8c\u6210");
    }

    private void updateFetchItemTaskLog(GcFetchItemTaskLogEO itemTaskLogEO, ExecuteStateEnum executeState, double process, String resultContent) {
        itemTaskLogEO.setExecuteState(executeState.getCode());
        itemTaskLogEO.setProcess(process);
        itemTaskLogEO.setProcessTime(new Date());
        itemTaskLogEO.setResultContent(resultContent);
        this.taskLogService.updateItemTaskLog(itemTaskLogEO);
    }
}

