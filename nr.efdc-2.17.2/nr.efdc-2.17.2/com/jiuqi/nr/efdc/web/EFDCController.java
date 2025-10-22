/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.exception.TaskExsitsException
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.dataentry.bean.AuthorityOptions
 *  com.jiuqi.nr.dataentry.funcVerificated.annotation.FuncVerificated
 *  com.jiuqi.nr.dataentry.paramInfo.TaskData
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.dataentry.util.authUtil.CheckAuthOfResourceUtil
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.MouldDefineImpl
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.efdc.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.exception.TaskExsitsException;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.dataentry.bean.AuthorityOptions;
import com.jiuqi.nr.dataentry.funcVerificated.annotation.FuncVerificated;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.util.authUtil.CheckAuthOfResourceUtil;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.MouldDefineImpl;
import com.jiuqi.nr.efdc.asynctask.BatchEfdcAsyncTaskExecutor;
import com.jiuqi.nr.efdc.asynctask.EFDCAsyncTaskExecutor;
import com.jiuqi.nr.efdc.param.EfdcBatchEnableInfo;
import com.jiuqi.nr.efdc.param.EfdcEnableInfo;
import com.jiuqi.nr.efdc.param.EfdcNewRequestInfo;
import com.jiuqi.nr.efdc.param.EfdcRequestInfo;
import com.jiuqi.nr.efdc.param.EfdcResponseInfo;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.efdc.service.IEFDCService;
import com.jiuqi.nr.efdc.service.impl.EfdcPierceServiceImpl;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import io.swagger.annotations.ApiOperation;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EFDCController {
    @Resource
    IEFDCService efdcService;
    @Resource
    AsyncTaskManager asyncTaskManager;
    @Autowired
    EfdcPierceServiceImpl efdcPierceService;
    @Autowired
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private CheckAuthOfResourceUtil checkAuthOfResourceUtil;
    private static final Logger log = LoggerFactory.getLogger(EFDCController.class);

    @JLoggable(value="efdc\u53d6\u6570")
    @FuncVerificated(value="efdcFetch")
    @RequestMapping(value={"/api/efdcData"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public AsyncTaskInfo efdcData(@Valid @RequestBody EfdcInfo efdcInfo) throws InterruptedException {
        boolean hasAuth = this.checkAuthOfResourceUtil.checkResourceAuthOfType(efdcInfo.getTaskKey(), AuthorityOptions.EFDC);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        if (!hasAuth) {
            asyncTaskInfo.setId("");
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setDetail((Object)"noAuth");
            asyncTaskInfo.setState(TaskState.ERROR);
            asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        } else {
            String masterKey = "";
            masterKey = masterKey + "efdc" + efdcInfo.getTaskKey() + efdcInfo.getFormKey() + efdcInfo.getFormSchemeKey();
            Map<String, DimensionValue> dimensionSet = efdcInfo.getDimensionSet();
            for (String dimensionSetKey : dimensionSet.keySet()) {
                masterKey = masterKey + dimensionSetKey + dimensionSet.get(dimensionSetKey);
            }
            String guid = Guid.newGuid();
            try {
                NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
                npRealTimeTaskInfo.setTaskKey(efdcInfo.getContext().getTaskKey());
                npRealTimeTaskInfo.setFormSchemeKey(efdcInfo.getContext().getFormSchemeKey());
                npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)efdcInfo)));
                npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new EFDCAsyncTaskExecutor());
                String efdcAsyncTaskID = this.asyncTaskManager.publishUniqueTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_EFDC.getName(), guid);
                asyncTaskInfo.setId(efdcAsyncTaskID);
                asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
            }
            catch (TaskExsitsException e) {
                asyncTaskInfo.setId(e.getTaskId());
                asyncTaskInfo.setState(TaskState.ERROR);
                asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
                asyncTaskInfo.setResult("\u60a8\u521b\u5efa\u7684\u4efb\u52a1\u5df2\u7ecf\u5728\u7b49\u5f85\u6216\u6267\u884c\u4e2d\u3002");
            }
        }
        return asyncTaskInfo;
    }

    @JLoggable(value="\u6279\u91cfefdc\u53d6\u6570")
    @FuncVerificated(value="batchEfdcFetch")
    @RequestMapping(value={"/api/batchEfdcData"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public AsyncTaskInfo BatchEfdcData(@Valid @RequestBody EfdcInfo efdcInfo) throws InterruptedException {
        boolean hasAuth = this.checkAuthOfResourceUtil.checkResourceAuthOfType(efdcInfo.getTaskKey(), AuthorityOptions.EFDC);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        if (!hasAuth) {
            asyncTaskInfo.setId("");
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setDetail((Object)"noAuth");
            asyncTaskInfo.setState(TaskState.ERROR);
            asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        } else {
            String masterKey = "";
            masterKey = masterKey + "efdc" + efdcInfo.getTaskKey() + efdcInfo.getFormKey() + efdcInfo.getFormSchemeKey();
            Map<String, DimensionValue> dimensionSet = efdcInfo.getDimensionSet();
            for (String dimensionSetKey : dimensionSet.keySet()) {
                masterKey = masterKey + dimensionSetKey + dimensionSet.get(dimensionSetKey);
            }
            String guid = Guid.newGuid();
            try {
                NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
                npRealTimeTaskInfo.setTaskKey(efdcInfo.getContext().getTaskKey());
                npRealTimeTaskInfo.setFormSchemeKey(efdcInfo.getContext().getFormSchemeKey());
                npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)efdcInfo)));
                npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchEfdcAsyncTaskExecutor());
                String batchEfdcAsyncTaskID = this.asyncTaskManager.publishUniqueTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_BATCHEFDC.getName(), guid);
                asyncTaskInfo.setId(batchEfdcAsyncTaskID);
                asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
            }
            catch (TaskExsitsException e) {
                asyncTaskInfo.setId(e.getTaskId());
                asyncTaskInfo.setState(TaskState.ERROR);
                asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
                asyncTaskInfo.setResult("\u60a8\u521b\u5efa\u7684\u4efb\u52a1\u5df2\u7ecf\u5728\u7b49\u5f85\u6216\u6267\u884c\u4e2d\u3002");
            }
        }
        return asyncTaskInfo;
    }

    public static UUID tofakeUUID(String string) {
        int i;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException nsae) {
            throw new InternalError("MD5 not supported", nsae);
        }
        byte[] data = md.digest(string.getBytes());
        long msb = 0L;
        long lsb = 0L;
        assert (data.length == 16) : "data must be 16 bytes in length";
        for (i = 0; i < 8; ++i) {
            msb = msb << 8 | (long)(data[i] & 0xFF);
        }
        for (i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long)(data[i] & 0xFF);
        }
        return new UUID(msb, lsb);
    }

    @PostMapping(value={"/api/efdcPierce"})
    @ApiOperation(value="efdc\u7a7f\u900f\u67e5\u8be2\u53c2\u6570\u83b7\u53d6")
    public EfdcResponseInfo queryEfdcPierceInfo(@Valid @RequestBody EfdcRequestInfo requestInfo) {
        try {
            return this.efdcPierceService.getResponseInfo(requestInfo);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return new EfdcResponseInfo();
        }
    }

    @PostMapping(value={"/api/newEfdcPierce"})
    @NRContextBuild
    @ApiOperation(value="efdc\u7a7f\u900f\u67e5\u8be2\u53c2\u6570\u83b7\u53d6")
    public EfdcResponseInfo newQueryEfdcPierceInfo(@Valid @RequestBody EfdcNewRequestInfo efdcNewRequestInfo) {
        try {
            return this.efdcPierceService.getResponseInfoNew(efdcNewRequestInfo);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return new EfdcResponseInfo();
        }
    }

    @PostMapping(value={"/api/queryEFDCEnable"})
    @ApiOperation(value="efdc\u7a7f\u900f\u662f\u5426\u53ef\u7528\u9ed8\u8ba4\u5b9e\u73b0")
    @NRContextBuild
    public EfdcEnableInfo queryEfdcEnable(@Valid @RequestBody EfdcNewRequestInfo efdcNewRequestInfo) {
        try {
            return this.efdcPierceService.getEFDCEnable(efdcNewRequestInfo);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return new EfdcEnableInfo();
        }
    }

    @PostMapping(value={"api/queryBatchEFDCEnable"})
    @ApiOperation(value="efdc\u7a7f\u900f\u662f\u5426\u53ef\u7528\u6279\u91cf\u5224\u65ad")
    @NRContextBuild
    public boolean[][] queryBatchEfdcEnable(@Valid @RequestBody EfdcBatchEnableInfo efdcBatchEnableInfo) {
        return this.efdcPierceService.queryBatchEfdcEnable(efdcBatchEnableInfo);
    }

    @RequestMapping(value={"/api/getEFDCTask"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u4efb\u52a1")
    @ResponseBody
    public List<TaskData> efdcTask() {
        List runtimeTaskList = this.dataEntryParamService.getRuntimeTaskList();
        List<TaskData> collect = runtimeTaskList.stream().filter(list -> list.isEfdcSwitch()).collect(Collectors.toList());
        return collect;
    }

    @JLoggable(value="\u83b7\u53d6\u5f53\u524d\u5355\u4f4dEFDC\u53d6\u6570\u516c\u5f0f")
    @RequestMapping(value={"/api/getFormulaSchemeDefine"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public List<MouldDefineImpl> getFormulaSchemeDefine(@Valid @RequestBody JtableContext jtableContext) throws InterruptedException {
        FormulaSchemeDefine efdcFormula = this.efdcService.getEfdcFormula(jtableContext);
        List data = new ArrayList();
        if (efdcFormula != null && efdcFormula.getEfdcPeriodSettingDefineImpl() != null) {
            EFDCPeriodSettingDefineImpl efdcPeriodSettingDefineImpl = efdcFormula.getEfdcPeriodSettingDefineImpl();
            data = efdcPeriodSettingDefineImpl.getMouldDataDefine().getData();
        }
        ArrayList<MouldDefineImpl> resData = new ArrayList<MouldDefineImpl>(data);
        Iterator iterator = resData.iterator();
        while (iterator.hasNext()) {
            MouldDefineImpl curr = (MouldDefineImpl)iterator.next();
            if (!StringUtils.hasText(curr.getNewCode())) continue;
            String[] spStr = curr.getNewCode().split(",");
            if (spStr.length == 1) {
                if (spStr[0].equals(((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue())) continue;
                iterator.remove();
                continue;
            }
            if (spStr[0].equals(((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue()) && spStr[1].equals(((DimensionValue)jtableContext.getDimensionSet().get("ADJUST")).getValue())) continue;
            iterator.remove();
        }
        return resData;
    }
}

