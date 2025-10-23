/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.task.form.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.form.common.NrFormCopyErrorEnum;
import com.jiuqi.nr.task.form.dto.FormCopyFormCodeSameCheckVO;
import com.jiuqi.nr.task.form.dto.FormCopyInfoCheckVO;
import com.jiuqi.nr.task.form.dto.FormCopyInfoVO;
import com.jiuqi.nr.task.form.dto.FormCopyLinksVO;
import com.jiuqi.nr.task.form.dto.FormCopyPushLinkVO;
import com.jiuqi.nr.task.form.dto.FormDoCopyParams;
import com.jiuqi.nr.task.form.dto.FormSyncPushExecuteVO;
import com.jiuqi.nr.task.form.dto.FormSyncRecordPushVO;
import com.jiuqi.nr.task.form.formcopy.FormSyncPushResult;
import com.jiuqi.nr.task.form.formcopy.IDesignFormCopyPushManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designers/"})
@Api(tags={"\u63a8\u9001\u62a5\u8868\u590d\u5236\u53ca\u540c\u6b65\u6a21\u5757"})
public class FormCopyPushController {
    @Autowired
    private IDesignFormCopyPushManageService demoFormCopyPushManageService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    private static final Logger logger = LoggerFactory.getLogger(FormCopyPushController.class);

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u516c\u5f0f\u548c\u6253\u5370\u65b9\u6848\u4fe1\u606f")
    @RequestMapping(value={"/get-formScheme-formula-print/{srcFormSchemeKey}"}, method={RequestMethod.GET})
    public FormCopyLinksVO getSrcScheme(@PathVariable String srcFormSchemeKey) throws JQException {
        try {
            return this.demoFormCopyPushManageService.getSrcScheme(srcFormSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_202, e.getMessage());
        }
    }

    @ApiOperation(value="\u6839\u636e\u6765\u6e90\u62a5\u8868\u65b9\u6848\u548c\u76ee\u6807\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u4e0d\u5e26\u6765\u6e90\u65b9\u6848\u4fe1\u606f\u7684\u6620\u5c04\u5173\u7cfb")
    @RequestMapping(value={"/get-links-without-src"}, method={RequestMethod.POST})
    public Map<String, FormCopyLinksVO> getSrcDesLinks(@RequestBody FormCopyPushLinkVO formCopyPushLinkVO) throws JQException {
        try {
            return this.demoFormCopyPushManageService.getSrcDesLinks(formCopyPushLinkVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_202, e.getMessage());
        }
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u63a8\u9001\u67e5\u627e\u5404\u8981\u590d\u5236\u7684\u62a5\u8868\u5728\u54e5\u54e5\u76ee\u6807\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u7684\u68c0\u67e5\u7ed3\u679c")
    @RequestMapping(value={"/get-form-copy-push-check"}, method={RequestMethod.POST})
    public Map<String, List<FormCopyInfoVO>> getFormCopyPushCheck(@RequestBody FormCopyInfoCheckVO formCopyInfoCheckVO) throws JQException {
        try {
            return this.demoFormCopyPushManageService.getFormCopyPushCheck(formCopyInfoCheckVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_203, (Throwable)e);
        }
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u63a8\u9001\u65f6\u5019\u65b0\u589e\u62a5\u8868\uff0c\u68c0\u67e5code\u5728\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0b\u662f\u5426\u5b58\u5728")
    @RequestMapping(value={"/get-form-copy-push-check-form-code/{desFormSchemeKey}/{formCode}"}, method={RequestMethod.GET})
    public boolean checkFormCodeSame(@PathVariable String desFormSchemeKey, @PathVariable String formCode) throws JQException {
        return this.demoFormCopyPushManageService.checkFormCodeSame(desFormSchemeKey, formCode);
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u63a8\u9001\u65f6\u5019\u6267\u884c\u590d\u5236\u3001\u63a8\u9001\uff0c\u6279\u91cf\u68c0\u67e5\u8981\u65b0\u589e\u7684\u62a5\u8868\uff0c\u5176codes\u5728\u65b0\u589e\u62a5\u8868\u6240\u5c5e\u7684\u62a5\u8868\u65b9\u6848\u4e0b\u5b58\u5728\u4e0d\u5b58\u5728")
    @RequestMapping(value={"/get-form-copy-push-check-form-codes"}, method={RequestMethod.POST})
    public List<String> checkFormCodeSames(@RequestBody FormCopyFormCodeSameCheckVO formCopyFormCodeSameCheckVO) throws JQException {
        try {
            return this.demoFormCopyPushManageService.checkFormCodeSames(formCopyFormCodeSameCheckVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_204, (Throwable)e);
        }
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u63a8\u9001\u65f6\u5019\u6279\u91cf\u6267\u884c\u590d\u5236")
    @RequestMapping(value={"/get-form-copy-push-do"}, method={RequestMethod.POST})
    @TaskLog(operation="\u62a5\u8868\u590d\u5236\u63a8\u9001\u65f6\u5019\u6279\u91cf\u6267\u884c\u590d\u5236")
    public List<FormSyncPushResult> doFormCopyPush(@RequestBody List<FormDoCopyParams> allFormDoCopyParams) throws JQException {
        try {
            return this.demoFormCopyPushManageService.doFormCopyPush(allFormDoCopyParams);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_199, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u63a8\u9001\u540c\u6b65\u4fe1\u606f")
    @RequestMapping(value={"/get-push-formsync-info/{srcFormSchemeKey}"}, method={RequestMethod.GET})
    public FormSyncPushExecuteVO getFormSyncInfo(@PathVariable String srcFormSchemeKey) throws JQException {
        try {
            return this.demoFormCopyPushManageService.getFormSyncInfo(srcFormSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_190, (Throwable)e);
        }
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u63a8\u9001\u540c\u6b65")
    @RequestMapping(value={"/do-push-formsync"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    @TaskLog(operation="\u62a5\u8868\u590d\u5236\u63a8\u9001\u540c\u6b65")
    public List<FormSyncPushResult> doFormPushSync(@RequestBody FormSyncPushExecuteVO formSyncPushExecuteVO) throws JQException {
        try {
            return this.demoFormCopyPushManageService.doFormPushSync(formSyncPushExecuteVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_192, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u540c\u6b65\u63a8\u9001\u8bb0\u5f55")
    @RequestMapping(value={"/get-formsync-push-records/{srcFormSchemeKey}"}, method={RequestMethod.GET})
    public List<FormSyncRecordPushVO> getFormSyncPushRecord(@PathVariable String srcFormSchemeKey) throws JQException {
        try {
            return this.demoFormCopyPushManageService.getFormSyncPushRecord(srcFormSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_191, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u6240\u5c5e\u5206\u7ec4key")
    @RequestMapping(value={"/get-formsync-group-key/{desFormKey}"}, method={RequestMethod.GET})
    public String getFormGroupByForm(@PathVariable String desFormKey) {
        List formGroupsByFormId = this.designTimeViewController.listFormGroupByForm(desFormKey);
        return ((DesignFormGroupDefine)formGroupsByFormId.get(0)).getKey();
    }
}

