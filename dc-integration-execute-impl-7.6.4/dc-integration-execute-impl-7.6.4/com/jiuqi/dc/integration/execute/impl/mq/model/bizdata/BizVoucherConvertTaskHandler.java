/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.intf.impl.VchrMasterDim
 *  com.jiuqi.dc.base.common.utils.LogUtil
 *  com.jiuqi.dc.base.common.utils.Pair
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather
 *  com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg
 *  com.jiuqi.dc.taskscheduling.lockmgr.common.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.integration.execute.impl.mq.model.bizdata;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.intf.impl.VchrMasterDim;
import com.jiuqi.dc.base.common.utils.LogUtil;
import com.jiuqi.dc.base.common.utils.Pair;
import com.jiuqi.dc.integration.execute.impl.data.DataConvertDim;
import com.jiuqi.dc.integration.execute.impl.data.DataConvertResult;
import com.jiuqi.dc.integration.execute.impl.intf.IBizDataConvertHandler;
import com.jiuqi.dc.integration.execute.impl.intf.IBizDataConvertHandlerGather;
import com.jiuqi.dc.integration.execute.impl.intf.IDirectVoucherConvertHandler;
import com.jiuqi.dc.integration.execute.impl.intf.IVoucherConvertHandler;
import com.jiuqi.dc.integration.execute.impl.mq.BizDataExecuteParam;
import com.jiuqi.dc.integration.execute.impl.mq.BizModelExecuteParam;
import com.jiuqi.dc.integration.execute.impl.mq.model.bizdata.AbstractDcBizBaseTaskHandler;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.lockmgr.common.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Component
public class BizVoucherConvertTaskHandler
extends AbstractDcBizBaseTaskHandler {
    public static final String VOUCHER_CONVERT_NUM_OPTION_CODE = "DC_CONVERT_TRANSACTION_SIZE";
    @Autowired
    private IPluginTypeGather pluginGather;
    @Autowired
    private IBizDataConvertHandlerGather gather;
    @Autowired
    private BizDataRefDefineService defineService;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private TaskManageService taskMangeService;
    @Autowired
    private DataSchemeOptionService dataSchemeOptionService;

    public String getName() {
        return "BizVoucherConvert";
    }

    public String getTitle() {
        return "\u3010\u6570\u636e\u6574\u5408\u3011\u51ed\u8bc1\u8f6c\u6362";
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        List<VchrMasterDim> vchrMasterDims;
        TaskHandleResult result = new TaskHandleResult(monitor.getLogger());
        BizModelExecuteParam modelExecuteParam = (BizModelExecuteParam)JsonUtils.readValue((String)param, BizModelExecuteParam.class);
        BizDataExecuteParam executeParam = (BizDataExecuteParam)JsonUtils.readValue((String)modelExecuteParam.getExecuteParam(), BizDataExecuteParam.class);
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.findByCode(executeParam.getDataSchemeCode());
        DataMappingDefineDTO define = this.defineService.getByCode(executeParam.getDataSchemeCode(), executeParam.getDefineCode());
        IBizDataConvertHandler handler = this.gather.getHandler(this.pluginGather.getPluginType(define.getPluginType()), define.getCode(), dataSchemeDTO.getSourceDataType());
        String log = "";
        if (handler == null) {
            log = String.format("\u63d2\u4ef6\u7c7b\u578b\u3010%1$s\u3011\u4e1a\u52a1\u7c7b\u578b\u3010%2$s\u3011\u6570\u636e\u65b9\u6848\u3010%3$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u4e1a\u52a1\u6570\u636e\u5904\u7406\u5668", define.getPluginType(), define.getCode(), define.getDataSchemeCode());
            result.appendLog(log);
            result.setPreParam(param);
            result.setSuccess(Boolean.valueOf(false));
            return result;
        }
        if (!(handler instanceof IVoucherConvertHandler)) {
            log = String.format("\u63d2\u4ef6\u7c7b\u578b\u3010%1$s\u3011\u4e1a\u52a1\u7c7b\u578b\u3010%2$s\u3011\u6570\u636e\u65b9\u6848\u3010%3$s\u3011\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u4e1a\u52a1\u6570\u636e\u5904\u7406\u5668\u5e76\u672a\u5b9e\u73b0IVoucherConvertHandler\u63a5\u53e3", define.getPluginType(), define.getCode(), define.getDataSchemeCode());
            result.appendLog(log);
            result.setPreParam(param);
            result.setSuccess(Boolean.valueOf(false));
            return result;
        }
        result.setSendPostTaskMsgWhileHandleTask(Boolean.valueOf(true));
        DataConvertDim dataConvertDim = (DataConvertDim)JsonUtils.readValue((String)modelExecuteParam.getConvertParam(), DataConvertDim.class);
        this.taskMangeService.updateBeginHandle(TaskTypeEnum.BIZVCHRINTEGRATION, dataConvertDim.getUnitCode());
        monitor.progressAndLog(0.05, "\u51ed\u8bc1\u8f6c\u6362\u53c2\u6570\u51c6\u5907\u5b8c\u6210\n");
        ThreadContext.put((Object)"MONITOR_KEY", (Object)monitor);
        if (handler instanceof IDirectVoucherConvertHandler) {
            Pair<Boolean, String> resultPair = ((IDirectVoucherConvertHandler)handler).handleProcessByOdsUnitId(executeParam.getDataSchemeCode(), dataConvertDim);
            if (!((Boolean)resultPair.getFirst()).booleanValue()) {
                log = String.format("\u6309\u5355\u4f4d\u3010%1$s\u3011\u8c03\u7528ETL\u6d41\u7a0b,\u83b7\u53d6\u589e\u91cf\u7ed3\u679c\u5931\u8d25\n%2$s", dataConvertDim.getOdsUnitCode(), resultPair.getSecond());
                result.appendLog(log);
                result.setSuccess(Boolean.FALSE);
                result.setPreParam(param);
                return result;
            }
            monitor.progressAndLog(0.1, "\u6309\u5355\u4f4d\u8c03\u7528ETL\u6d41\u7a0b,\u83b7\u53d6\u589e\u91cf\u7ed3\u679c\u5b8c\u6210\n");
            ((IDirectVoucherConvertHandler)handler).handleBeforeVchrChangeByOdsUnitId(dataSchemeDTO.getDataSourceCode(), dataSchemeDTO.getCode(), dataConvertDim.getOdsUnitCode(), dataConvertDim.getCreateDate());
        }
        if (!CollectionUtils.isEmpty(vchrMasterDims = ((IVoucherConvertHandler)handler).handleVchrChangeInfo(define, handler, dataConvertDim, dataSchemeDTO.getDataSourceCode()))) {
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
            taskHandlerClient.startSubTask("VchrPeriodSplit", new TaskHandleMsg("VchrPeriodSplit", dataConvertDim.getUnitCode(), UUIDUtils.newHalfGUIDStr(), null, JsonUtils.writeValueAsString(vchrMasterDims), 1, ThreadContext.get((Object)"TASKHANDLER_RUNNERID_KEY").toString()));
        }
        monitor.progressAndLog(0.15, "\u51ed\u8bc1\u53d8\u66f4\u8868\u8bb0\u5f55\u5904\u7406\u5b8c\u6210\n");
        if (!CollectionUtils.isEmpty(vchrMasterDims)) {
            for (VchrMasterDim dim : vchrMasterDims) {
                monitor.progressAndLog(0.15, String.format("\u7ec4\u7ec7\u673a\u6784\u3010%1$s\u3011\u5e74\u5ea6\u3010%2$d\u3011\u671f\u95f4\u3010%3$s\u3011\u51ed\u8bc1\u8f6c\u6362\u5171\u51b2\u9500\u4e86%4$d\u884c\u8f85\u52a9\u5206\u5f55\u3002\n", dim.getUnitCode(), dim.getAcctYear(), dim.getAcctPeriod(), dim.getCount()));
            }
        }
        List<DataConvertDim> convertDimList = ((IVoucherConvertHandler)handler).getVchrConvertParam(executeParam, dataConvertDim);
        Map<String, Integer> acctPeriodRefMap = ((IVoucherConvertHandler)handler).getAcctPeriodRefMap();
        convertDimList.forEach(e -> e.setDcAcctPeriod(ObjectUtils.isEmpty(acctPeriodRefMap.get(e.getAcctPeriod())) ? Integer.parseInt(e.getAcctPeriod()) : (Integer)acctPeriodRefMap.get(e.getAcctPeriod())));
        convertDimList.sort((o1, o2) -> {
            if (o1.getAcctYear() != o2.getAcctYear()) {
                return o1.getAcctYear() - o2.getAcctYear();
            }
            return o1.getDcAcctPeriod() - o2.getDcAcctPeriod();
        });
        monitor.progressAndLog(0.2, "\u589e\u91cf\u51ed\u8bc1\u5e74\u5ea6\u671f\u95f4\u4fe1\u606f\u83b7\u53d6\u5b8c\u6210\n");
        ArrayList<DataConvertDim> batchDimList = new ArrayList<DataConvertDim>();
        DataSchemeOptionDTO optionValue = this.dataSchemeOptionService.getValueByDataSchemeCode(dataSchemeDTO.getCode(), VOUCHER_CONVERT_NUM_OPTION_CODE);
        int batchSize = optionValue.getOptionValue().getIntegerValue();
        for (DataConvertDim dim : convertDimList) {
            if (dim.getCount() <= batchSize) {
                batchDimList.add(dim);
                continue;
            }
            int remainder = dim.getCount() % batchSize;
            int group = remainder == 0 ? dim.getCount() / batchSize : dim.getCount() / batchSize + 1;
            for (int i = 0; i < group; ++i) {
                int count = batchSize;
                if (remainder > 0 && i == group - 1) {
                    count = remainder;
                }
                DataConvertDim batchDim = new DataConvertDim();
                BeanUtils.copyProperties(dim, batchDim);
                dim.setCount(count);
                batchDimList.add(dim);
            }
        }
        double stepSize = 0.8 / (double)batchDimList.size();
        for (DataConvertDim dim : batchDimList) {
            DataConvertResult convertResult = handler.doConvert(executeParam.getDataSchemeCode(), JsonUtils.writeValueAsString((Object)dim));
            try {
                this.sendPostTaskMsg(convertResult.getParam());
            }
            catch (Exception e2) {
                result.appendLog("\u53d1\u9001\u540e\u7f6e\u4efb\u52a1\u6d88\u606f\u51fa\u73b0\u5f02\u5e38\uff1a");
                result.appendLog(LogUtil.getExceptionStackStr((Throwable)e2));
            }
            monitor.progressAndLogByStepSize(stepSize, convertResult.getLog());
        }
        result.appendLog("\u672c\u6b21\u51ed\u8bc1\u8f6c\u6362\u5904\u7406\u5b8c\u6210\n");
        return result;
    }
}

