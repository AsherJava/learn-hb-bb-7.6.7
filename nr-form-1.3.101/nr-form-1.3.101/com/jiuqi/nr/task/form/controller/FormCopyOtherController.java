/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.task.form.controller;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.form.common.NrFormCopyErrorEnum;
import com.jiuqi.nr.task.form.dto.AsyncFormParamVO;
import com.jiuqi.nr.task.form.formcopy.IDesignFormCopyOtherManageService;
import com.jiuqi.nr.task.form.formcopy.common.FormCopyRecordType;
import com.jiuqi.nr.task.form.formcopy.service.FormCopyPullAsync;
import com.jiuqi.nr.task.form.formcopy.service.FormSyncPullAsync;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designers/"})
@Api(tags={"\u62a5\u8868\u590d\u5236\u53ca\u540c\u6b65\u6a21\u5757"})
public class FormCopyOtherController {
    private static final Logger logger = LoggerFactory.getLogger(FormCopyOtherController.class);
    @Autowired
    private IDesignFormCopyOtherManageService designFormCopyOtherManageService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @ApiOperation(value="\u662f\u5426\u6709\u540c\u6b65\u4fe1\u606f")
    @RequestMapping(value={"/has-formsync-info/{formSchemeKey}"}, method={RequestMethod.GET})
    public FormCopyRecordType hasFormSyncInfo(@PathVariable String formSchemeKey) throws JQException {
        try {
            return this.designFormCopyOtherManageService.getFormCopyType(formSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_190, (Throwable)e);
        }
    }

    @ApiOperation(value="\u62a5\u8868\u540c\u6b65\u62c9\u53d6\u5f02\u6b65\u6267\u884c")
    @PostMapping(value={"do-formsync-async"})
    @Transactional(rollbackFor={Exception.class})
    @TaskLog(operation="\u62a5\u8868\u540c\u6b65\u62c9\u53d6\u5f02\u6b65\u6267\u884c")
    public String formSyncPullAsync(@RequestBody AsyncFormParamVO asyncFormParamVO) throws JQException {
        try {
            NpRealTimeTaskInfo job = new NpRealTimeTaskInfo();
            job.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)asyncFormParamVO));
            job.setAbstractRealTimeJob((AbstractRealTimeJob)new FormSyncPullAsync());
            return this.asyncTaskManager.publishTask(job);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_192, e.getMessage());
        }
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u62c9\u53d6\u5f02\u6b65\u6267\u884c")
    @PostMapping(value={"do-formcopy-async"})
    @Transactional(rollbackFor={Exception.class})
    @TaskLog(operation="\u62a5\u8868\u590d\u5236\u62c9\u53d6\u5f02\u6b65\u6267\u884c")
    public String formCopyPullAsync(@RequestBody AsyncFormParamVO asyncFormParamVO) throws JQException {
        try {
            NpRealTimeTaskInfo job = new NpRealTimeTaskInfo();
            job.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)asyncFormParamVO));
            job.setAbstractRealTimeJob((AbstractRealTimeJob)new FormCopyPullAsync());
            return this.asyncTaskManager.publishTask(job);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_199, e.getMessage());
        }
    }
}

