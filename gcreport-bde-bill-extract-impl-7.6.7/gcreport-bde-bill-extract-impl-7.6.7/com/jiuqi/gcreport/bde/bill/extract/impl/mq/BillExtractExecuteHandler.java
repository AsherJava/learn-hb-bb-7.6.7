/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.log.enums.FetchDimType
 *  com.jiuqi.bde.log.enums.FetchTaskType
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleCtx
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandlerGather
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.gcreport.bde.bill.extract.impl.mq;

import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.log.enums.FetchDimType;
import com.jiuqi.bde.log.enums.FetchTaskType;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleCtx;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandlerGather;
import com.jiuqi.gcreport.bde.bill.extract.impl.intf.BillExtractHandleMessage;
import com.jiuqi.gcreport.bde.bill.extract.impl.service.BillExtractExecuteService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillExtractExecuteHandler
implements ITaskHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BillExtractExecuteHandler.class);
    @Autowired
    private BillExtractExecuteService executeService;
    @Autowired
    private IBillExtractHandlerGather handlerGather;

    public String getName() {
        return FetchTaskType.BILL_FETCH.getCode();
    }

    public String getTitle() {
        return FetchTaskType.BILL_FETCH.getTitle();
    }

    public String getPreTask() {
        return null;
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public Map<String, String> getHandleParams(String preParam) {
        BillExtractHandleCtx handleCtx = (BillExtractHandleCtx)JsonUtils.readValue((String)preParam, BillExtractHandleCtx.class);
        HashMap<String, String> params = new HashMap<String, String>();
        BillExtractHandleMessage message = null;
        if (CollectionUtils.isEmpty((Collection)handleCtx.getBillCodeList())) {
            List billList = this.handlerGather.getHandlerByModel(handleCtx.getBillModel()).listBills(handleCtx);
            HashSet billCodeSet = CollectionUtils.newHashSet();
            for (Map billData : billList) {
                if (billData.get("BILLCODE") == null) continue;
                billCodeSet.add((String)billData.get("BILLCODE"));
            }
            handleCtx.setBillCodeList(billCodeSet.stream().collect(Collectors.toList()));
        }
        for (String billCode : handleCtx.getBillCodeList()) {
            message = (BillExtractHandleMessage)BeanConvertUtil.convert((Object)handleCtx, BillExtractHandleMessage.class, (String[])new String[0]);
            message.setBillCode(billCode);
            params.put(JsonUtils.writeValueAsString((Object)message), message.getBillDefine() + "," + message.getBillCode());
        }
        return params;
    }

    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        BillExtractHandleMessage message = (BillExtractHandleMessage)JsonUtils.readValue((String)param, BillExtractHandleMessage.class);
        BdeCommonUtil.initNpUser((String)message.getUsername());
        TaskHandleMsg handleMsg = null;
        try {
            handleMsg = (TaskHandleMsg)ThreadContext.get((Object)"TASKHANDLEMSG_KEY");
            message.setRequestRunnerId(handleMsg.getRunnerId());
            message.setRequestInstcId(handleMsg.getInstanceId());
            message.setRequestTaskId(handleMsg.getTaskItemId());
            this.executeService.doExecute(message);
        }
        catch (BillException billException) {
            String runnerId = handleMsg == null ? "" : handleMsg.getRunnerId();
            List checkMessages = billException.getCheckMessages();
            List<Object> collect = CollectionUtils.newArrayList();
            if (checkMessages != null && checkMessages.size() > 0) {
                collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
            }
            String checkResultMessage = String.join((CharSequence)",", collect);
            LOGGER.error("\u4efb\u52a1\u6807\u8bc6\u3010{}\u3011\u53d6\u6570\u6267\u884c\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a{} : {}", new Object[]{runnerId, billException.getMessage(), checkResultMessage, billException});
            throw new BusinessRuntimeException(billException.getMessage() + ":" + checkResultMessage, (Throwable)billException);
        }
        catch (Exception e) {
            String runnerId = handleMsg == null ? "" : handleMsg.getRunnerId();
            LOGGER.error("\u4efb\u52a1\u6807\u8bc6\u3010{}\u3011\u53d6\u6570\u6267\u884c\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u4fe1\u606f\uff1a{}", runnerId, e.getMessage(), e);
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        TaskHandleResult result = new TaskHandleResult();
        result.setPreParam(param);
        result.appendLog(String.format("%1$s\u62a5\u8868\u53d6\u6570\u4efb\u52a1\u5b8c\u6210\n", this.getTitle()));
        return result;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.FOLLOW;
    }

    public String getModule() {
        return "GC";
    }

    public IDimType getDimType() {
        return FetchDimType.FORM;
    }

    public boolean enable(String preTaskName, String preParam) {
        return true;
    }

    public TaskHandleResult handleTask(String param) {
        return null;
    }

    public String getSpecialQueueFlag() {
        return null;
    }
}

