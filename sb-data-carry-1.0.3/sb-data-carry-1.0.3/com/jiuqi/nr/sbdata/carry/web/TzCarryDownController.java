/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.mapping2.service.MappingTransferService
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.sbdata.carry.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.mapping2.service.MappingTransferService;
import com.jiuqi.nr.sbdata.carry.bean.TzCarryDownRestParam;
import com.jiuqi.nr.sbdata.carry.bean.TzDataParam;
import com.jiuqi.nr.sbdata.carry.job.TzCarryDownRealTimeJob;
import com.jiuqi.nr.sbdata.carry.job.TzClearDataRealTimeJob;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/datafix"})
@Api(tags={"TzCarryDownController-\u53f0\u8d26\u7ed3\u8f6c"})
public class TzCarryDownController {
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private MappingTransferService mappingTransferService;

    @RequestMapping(value={"/sb/clear-data"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u53f0\u8d26\u6570\u636e\u6e05\u9664")
    public AsyncTaskInfo clearData(@Valid @RequestBody TzDataParam carryDownParam) {
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(carryDownParam.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(carryDownParam.getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)carryDownParam));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new TzClearDataRealTimeJob());
        String asyncTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asyncTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @RequestMapping(value={"/sb/carry-down"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u53f0\u8d26\u6570\u636e\u7ed3\u8f6c")
    public AsyncTaskInfo carryDownData(@RequestBody TzCarryDownRestParam carryDownParam) {
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)carryDownParam));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new TzCarryDownRealTimeJob());
        String asyncTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asyncTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @GetMapping(value={"/sb/get-mapping-scheme/{formSchemeKey}"})
    @ResponseBody
    @ApiOperation(value="\u53f0\u8d26\u6570\u636e\u7ed3\u8f6c")
    public List<MappingScheme> getMappingSchemeBy(@PathVariable String formSchemeKey) {
        List<Object> result = new ArrayList<MappingScheme>();
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            result = this.mappingTransferService.getMappingSchemeByFormScheme(formSchemeKey);
        }
        return result;
    }
}

