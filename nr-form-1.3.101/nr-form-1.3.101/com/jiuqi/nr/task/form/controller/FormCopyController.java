/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  com.jiuqi.nr.task.api.tree.UITreeNode
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
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.form.common.NrFormCopyErrorEnum;
import com.jiuqi.nr.task.form.dto.FormCopyInfoParams;
import com.jiuqi.nr.task.form.dto.FormCopyInfoVO;
import com.jiuqi.nr.task.form.dto.FormCopyLinksVO;
import com.jiuqi.nr.task.form.dto.FormCopyTaskTreeNode;
import com.jiuqi.nr.task.form.dto.FormDoCopyParams;
import com.jiuqi.nr.task.form.dto.FormSyncParamsVO;
import com.jiuqi.nr.task.form.dto.FormSyncRecordVO;
import com.jiuqi.nr.task.form.dto.FormSyncVO;
import com.jiuqi.nr.task.form.dto.SyncCoverForm;
import com.jiuqi.nr.task.form.formcopy.FormSyncResult;
import com.jiuqi.nr.task.form.formcopy.FormulaSyncResult;
import com.jiuqi.nr.task.form.formcopy.IDesignFormCopyManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
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
@Api(tags={"\u62a5\u8868\u590d\u5236\u53ca\u540c\u6b65\u6a21\u5757"})
public class FormCopyController {
    private static final Logger logger = LoggerFactory.getLogger(FormCopyController.class);
    @Autowired
    private IDesignFormCopyManageService designTimeFormCopyManageService;

    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u548c\u5bf9\u5e94\u62a5\u8868\u65b9\u6848\u7684\u6811\u5f62")
    @RequestMapping(value={"/get-tasktree"}, method={RequestMethod.GET})
    public List<UITreeNode<FormCopyTaskTreeNode>> getTaskTree() throws JQException {
        try {
            return this.designTimeFormCopyManageService.getTaskTree();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_193, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6839\u636e\u6765\u6e90\u62a5\u8868\u65b9\u6848\u548c\u76ee\u6807\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u6620\u5c04\u5173\u7cfb")
    @RequestMapping(value={"/get-links/{srcFormSchemeKey}/{desFormSchemeKey}"}, method={RequestMethod.GET})
    public FormCopyLinksVO getSrcDesLinks(@PathVariable String srcFormSchemeKey, @PathVariable String desFormSchemeKey) throws JQException {
        try {
            return this.designTimeFormCopyManageService.getSchemeCopyLinks(srcFormSchemeKey, desFormSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_194, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u590d\u5236\u62a5\u8868\u7684\u4fe1\u606f")
    @RequestMapping(value={"/get-formcopy_info"}, method={RequestMethod.POST})
    public List<FormCopyInfoVO> getFormCopyInfo(@RequestBody FormCopyInfoParams formCopyInfoParams) throws JQException {
        try {
            return this.designTimeFormCopyManageService.getFormCopyInfo(formCopyInfoParams);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_203, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6267\u884c\u62a5\u8868\u590d\u5236")
    @RequestMapping(value={"/do-formcopy"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    @TaskLog(operation="\u6267\u884c\u62a5\u8868\u590d\u5236")
    public List<FormulaSyncResult> doFormCopy(@RequestBody FormDoCopyParams formDoCopyParams) throws JQException {
        try {
            return this.designTimeFormCopyManageService.doFormCopy(formDoCopyParams, null, new StringBuilder());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_199, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u540c\u6b65\u4fe1\u606f")
    @RequestMapping(value={"/get-formsync-info/{desFormSchemeKey}"}, method={RequestMethod.GET})
    public List<FormSyncVO> getFormSyncInfo(@PathVariable String desFormSchemeKey) throws JQException {
        try {
            return this.designTimeFormCopyManageService.getFormSyncInfo(desFormSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_190, (Throwable)e);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u540c\u6b65\u8bb0\u5f55")
    @RequestMapping(value={"/get-formsync-records/{desFormSchemeKey}"}, method={RequestMethod.GET})
    public List<FormSyncRecordVO> getFormSyncRecord(@PathVariable String desFormSchemeKey) throws JQException {
        try {
            return this.designTimeFormCopyManageService.getFormSyncRecord(desFormSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_191, (Throwable)e);
        }
    }

    @ApiOperation(value="\u62a5\u8868\u590d\u5236\u6267\u884c\u540c\u6b65")
    @RequestMapping(value={"/do-formsync"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    @TaskLog(operation="\u62a5\u8868\u590d\u5236\u6267\u884c\u540c\u6b65")
    public List<FormulaSyncResult> doFormSync(@RequestBody List<FormSyncParamsVO> formSyncParamsVOList) throws JQException {
        try {
            List<FormSyncResult> formSyncResults = this.designTimeFormCopyManageService.doFormSync(formSyncParamsVOList, null, new StringBuilder());
            ArrayList<FormulaSyncResult> result = new ArrayList<FormulaSyncResult>();
            formSyncResults.stream().forEach(a -> result.addAll(a.getFormulaSyncResultList()));
            return result;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_192, e.getMessage());
        }
    }

    @ApiOperation(value="\u6267\u884c\u540c\u6b65\u8986\u76d6\u63d0\u793a")
    @RequestMapping(value={"/formsync-cover-message"}, method={RequestMethod.POST})
    public List<SyncCoverForm> messageBox(@RequestBody List<String> formKeys) throws JQException {
        try {
            return this.designTimeFormCopyManageService.messageBox(formKeys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormCopyErrorEnum.NRDESINGER_EXCEPTION_201, (Throwable)e);
        }
    }
}

