/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.log.enums.FetchTaskType
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO
 *  com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO
 *  com.jiuqi.gcreport.bde.bill.extract.client.dto.BillExtractDTO
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleCtx
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleDTO
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleParam
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandlerGather
 *  com.jiuqi.gcreport.bde.bill.extract.client.vo.BillExtractItemLogVO
 *  com.jiuqi.gcreport.bde.bill.extract.client.vo.BillExtractLogVO
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.billextract.client.BillExtractSettingClient
 *  com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO
 *  com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.gcreport.bde.bill.extract.impl.service.impl;

import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.log.enums.FetchTaskType;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO;
import com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import com.jiuqi.gcreport.bde.bill.extract.client.dto.BillExtractDTO;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleCtx;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleDTO;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleParam;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandlerGather;
import com.jiuqi.gcreport.bde.bill.extract.client.vo.BillExtractItemLogVO;
import com.jiuqi.gcreport.bde.bill.extract.client.vo.BillExtractLogVO;
import com.jiuqi.gcreport.bde.bill.extract.impl.service.BillExtractService;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.billextract.client.BillExtractSettingClient;
import com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO;
import com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.org.OrgDO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillExtractServiceImpl
implements BillExtractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BillExtractServiceImpl.class);
    @Autowired
    private RequestCertifyService certifyService;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private IBillExtractHandlerGather handlerGather;
    @Autowired
    private BillExtractSettingClient settingClient;
    @Autowired
    private INvwaSystemOptionService optionService;

    @Override
    public BillExtractLogVO doExtract(BillExtractDTO srcDto) {
        this.doBasicCheck(srcDto);
        BillExtractLogVO logVo = new BillExtractLogVO();
        ArrayList itemLogs = CollectionUtils.newArrayList();
        try {
            new BillExtractHandleCtx();
            BillExtractHandleDTO handleDto = (BillExtractHandleDTO)BeanConvertUtil.convert((Object)srcDto, BillExtractHandleDTO.class, (String[])new String[0]);
            handleDto.setBillDefine((String)srcDto.getBillDefines().get(0));
            MetaInfoDTO metaInfo = (MetaInfoDTO)BdeClientUtil.parseResponse((BusinessResponseEntity)this.settingClient.getMetaInfoByUniqueCode(handleDto.getBillDefine()));
            handleDto.setBillModel(metaInfo.getModelName());
            String masterTableName = (String)BdeClientUtil.parseResponse((BusinessResponseEntity)this.settingClient.getMasterTableName(handleDto.getBillDefine()));
            handleDto.setMasterTableName(masterTableName);
            DataModelColumn column = (DataModelColumn)BdeClientUtil.parseResponse((BusinessResponseEntity)this.settingClient.getDataModelColumn(masterTableName, "UNITCODE"));
            handleDto.setRpUnitType(BillExtractUtil.queryOrgTypeByColumn((DataModelColumn)column));
            BillExtractHandleParam handleParam = this.handlerGather.getHandlerByModel(handleDto.getBillModel()).parse(handleDto);
            Assert.isNotNull((Object)handleParam);
            Assert.isNotEmpty((String)handleParam.getEndDateStr());
            BillExtractHandleCtx handleCtx = (BillExtractHandleCtx)BeanConvertUtil.convert((Object)handleDto, BillExtractHandleCtx.class, (String[])new String[0]);
            BeanConvertUtil.copyProperties((Object)handleParam, (Object)handleCtx);
            handleCtx.setRequestSourceType(RequestSourceTypeEnum.BILL_FETCH.getCode());
            handleCtx.setUsername(NpContextHolder.getContext().getUserName());
            Map queryOrgMap = BillExtractUtil.queryOrgMap((String)handleCtx.getRpUnitType(), (Date)DateUtils.parse((String)handleParam.getEndDateStr()));
            for (String unitCode : handleParam.getUnitCodes()) {
                if (queryOrgMap.get(unitCode) == null) continue;
                BillExtractItemLogVO itemLogVo = new BillExtractItemLogVO();
                itemLogVo.setUnitCode(unitCode);
                itemLogVo.setUnitName(((OrgDO)queryOrgMap.get(unitCode)).getName());
                try {
                    handleCtx.setUnitCode(unitCode);
                    String taskId = this.doExtractByCtx(handleCtx, metaInfo);
                    itemLogVo.setRequestTaskId(taskId);
                    itemLogVo.setSuccess(Boolean.valueOf(true));
                    itemLogVo.setLog("\u53d6\u6570\u5b8c\u6210");
                    itemLogs.add(itemLogVo);
                }
                catch (Exception e) {
                    LOGGER.error("\u5355\u636e\u5b9a\u4e49\u3010{}\u3011\u5355\u4f4d\u3010{}\u3011\u53d6\u6570\u51fa\u73b0\u9519\u8bef,\u8be6\u7ec6\u539f\u56e0:{}", handleCtx.getBillDefine(), unitCode, e.getMessage(), e);
                    itemLogVo.setSuccess(Boolean.valueOf(false));
                    itemLogVo.setLog(e.getMessage());
                    itemLogs.add(itemLogVo);
                }
                logVo.addItemLog(itemLogVo);
            }
        }
        catch (Exception e) {
            LOGGER.error("\u5355\u636e\u53d6\u6570\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a{}", (Object)e.getMessage(), (Object)e);
            logVo.setErrorLog(e.getMessage());
        }
        return logVo;
    }

    private String doExtractByCtx(BillExtractHandleCtx handleCtx, MetaInfoDTO metaInfo) {
        BillSchemeConfigDTO schemeConfig = (BillSchemeConfigDTO)BdeClientUtil.parseResponse((BusinessResponseEntity)this.settingClient.getSchemeByOrgId(handleCtx.getBillDefine(), handleCtx.getUnitCode()));
        if (schemeConfig == null || schemeConfig.getFetchSchemeId() == null) {
            throw new BusinessRuntimeException(String.format("\u5355\u4f4d\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u53d6\u6570\u65b9\u6848\uff0c\u8bf7\u68c0\u67e5\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e", handleCtx.getUnitCode()));
        }
        handleCtx.setFetchSchemeId(schemeConfig.getFetchSchemeId());
        FetchFormDTO fetchForm = new FetchFormDTO();
        fetchForm.setId(handleCtx.getBillModel());
        fetchForm.setFormCode(handleCtx.getBillModel());
        fetchForm.setFormTitle(metaInfo.getTitle());
        handleCtx.setFetchForms((List)CollectionUtils.newArrayList((Object[])new FetchFormDTO[]{fetchForm}));
        this.handlerGather.getHandlerByModel(handleCtx.getBillModel()).doCheck(handleCtx);
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        String runnerId = null;
        TaskParamVO taskParam = new TaskParamVO();
        taskParam.setPreParam(JsonUtils.writeValueAsString((Object)handleCtx));
        taskParam.setExt_1("#");
        taskParam.setExt_2(handleCtx.getUnitCode());
        try {
            runnerId = (String)BdeClientUtil.parseResponse((BusinessResponseEntity)taskHandlerClient.startTaskWithExtInfo(FetchTaskType.BILL_FETCH.getCode(), taskParam));
        }
        catch (Exception e) {
            if (e.getMessage().contains("Feign - 500 Read Out Time")) {
                throw new BusinessRuntimeException("BDE\u5730\u5740\u8fde\u63a5\u8d85\u65f6\uff0c\u8bf7\u68c0\u67e5");
            }
            throw new BusinessRuntimeException((Throwable)e);
        }
        if (StringUtils.isEmpty((String)runnerId)) {
            throw new BusinessRuntimeException("\u6ca1\u6709\u53ef\u4ee5\u6267\u884c\u53d6\u6570\u7684\u6570\u636e");
        }
        double successVal = 1.0;
        long timeOutTime = this.getTimeOutTimeMillis();
        while (!(NumberUtils.compareDouble((double)this.getTaskProcess(taskHandlerClient, runnerId), (double)successVal) || timeOutTime != 0L && System.currentTimeMillis() >= timeOutTime)) {
            try {
                Thread.sleep(500L);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new BusinessRuntimeException((Throwable)e);
            }
        }
        try {
            Thread.sleep(3000L);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BusinessRuntimeException((Throwable)e);
        }
        TaskCountQueryDTO queryDto = new TaskCountQueryDTO();
        queryDto.setRunnerId(runnerId);
        queryDto.setTaskName(FetchTaskType.BILL_FETCH.getCode());
        ArrayList<DataHandleState> handleStateList = new ArrayList<DataHandleState>();
        handleStateList.add(DataHandleState.FAILURE);
        handleStateList.add(DataHandleState.CANCELED);
        queryDto.setDataHandleStates(handleStateList);
        List queryTaskLog = (List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.taskHandlerFactory.getMainTaskHandlerClient().queryTaskLog(queryDto));
        if (!CollectionUtils.isEmpty((Collection)queryTaskLog)) {
            HashSet logSet = CollectionUtils.newHashSet();
            for (LogManagerDetailVO taskLog : queryTaskLog) {
                String formatLog = null;
                formatLog = !StringUtils.isEmpty((String)taskLog.getResultLog()) && taskLog.getResultLog().indexOf("com") > -1 ? taskLog.getResultLog().substring(0, taskLog.getResultLog().indexOf("com")).replace("\n", "") : taskLog.getResultLog();
                if (StringUtils.isEmpty((String)formatLog)) continue;
                logSet.add(formatLog);
            }
            throw new BusinessRuntimeException(String.join((CharSequence)",", logSet));
        }
        return runnerId;
    }

    private long getTimeOutTimeMillis() {
        Integer timeOutOptionValue = ConverterUtils.getAsInteger((Object)this.optionService.findValueById("BDE_TIMEOUT_TIME"));
        if (timeOutOptionValue == null || timeOutOptionValue <= 0) {
            return 0L;
        }
        return System.currentTimeMillis() + (long)(timeOutOptionValue * 1000);
    }

    private Double getTaskProcess(TaskHandlerClient taskHandlerClient, String runnerId) {
        BusinessResponseEntity taskProcess = taskHandlerClient.getTaskProgress(runnerId);
        if (!taskProcess.isSuccess()) {
            throw new BusinessRuntimeException(taskProcess.getErrorMessage());
        }
        return (Double)taskProcess.getData();
    }

    private void doBasicCheck(BillExtractDTO srcDto) {
        if (BdeCommonUtil.isStandaloneServer()) {
            return;
        }
        String address = this.certifyService.getNvwaUrl();
        if (StringUtils.isEmpty((String)address)) {
            throw new BusinessRuntimeException("BDE\u53d6\u6570\u5730\u5740\u4e3a\u7a7a\u3002");
        }
        Assert.isNotNull((Object)srcDto);
        Assert.isNotEmpty((Collection)srcDto.getBillDefines());
        if (!Boolean.TRUE.equals(srcDto.getExtractAllUnit()) && CollectionUtils.isEmpty((Collection)srcDto.getUnitCodes())) {
            throw new BusinessRuntimeException("\u53d6\u6570\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u9009\u62e9");
        }
        srcDto.setIncludeUncharged(Boolean.valueOf(!Boolean.FALSE.equals(srcDto.getIncludeUncharged())));
    }
}

