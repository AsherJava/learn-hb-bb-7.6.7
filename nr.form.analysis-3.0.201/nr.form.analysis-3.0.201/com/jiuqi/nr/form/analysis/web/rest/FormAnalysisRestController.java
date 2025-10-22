/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.form.analysis.web.rest;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.form.analysis.service.impl.FormAnalysisExecuterImpl;
import com.jiuqi.nr.form.analysis.web.facade.FormAnalysisParamVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/formanalysis/"})
@Api(tags={"\u5d4c\u5165\u5f0f\u5206\u6790\u8868\u5206\u6790\u670d\u52a1"})
public class FormAnalysisRestController {
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @ApiOperation(value="\u5f02\u6b65\u5206\u6790")
    @PostMapping(value={"new/analysis/task"})
    public String createBatchAnalysisTask(@RequestBody FormAnalysisParamVO params) throws JQException {
        NpRealTimeTaskInfo info = new NpRealTimeTaskInfo();
        info.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)params));
        info.setAbstractRealTimeJob((AbstractRealTimeJob)new FormAnalysisExecuterImpl());
        return this.asyncTaskManager.publishTask(info, "ASYNCTASK_FORMANALYSIS_BATCHANALYSIS");
    }
}

