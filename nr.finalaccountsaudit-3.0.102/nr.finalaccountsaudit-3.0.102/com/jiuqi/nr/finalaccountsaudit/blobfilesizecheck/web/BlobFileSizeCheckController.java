/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2
 *  com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.asynctask.BlobFileSizeCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFieldStruct;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckParam;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFormStruct;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobTableAndFieldSession;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.service.BlobFileSizeCheckService;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u9644\u4ef6\u6587\u4ef6\u5927\u5c0f\u68c0\u67e5"})
@RequestMapping(value={"api/v1/finalaccount"})
public class BlobFileSizeCheckController {
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    DataDefinitionRuntimeController2 dataDefinitionRuntimeController;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    BlobTableAndFieldSession blobSession;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    BlobFileSizeCheckService service;

    @RequestMapping(value={"/getBlobTablesAndFields"}, method={RequestMethod.GET})
    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u5ba1\u6838\u4e8b\u9879\u5206\u7ec4")
    public List<BlobFormStruct> getBlobTablesAndFields(String formSchemeKey) throws Exception {
        ArrayList<BlobFormStruct> result = new ArrayList();
        Object obj = this.blobSession.getResult(formSchemeKey);
        obj = null;
        if (obj == null) {
            result = this.getItems(formSchemeKey);
            this.blobSession.saveResult(formSchemeKey, result);
        } else {
            result = (List)obj;
        }
        return result;
    }

    private List<BlobFormStruct> getItems(String formSchemeKey) throws Exception {
        ArrayList<BlobFormStruct> result = new ArrayList<BlobFormStruct>();
        List<FormDefine> forms = this.getFormsAllList(formSchemeKey);
        for (FormDefine form : forms) {
            if (form.getFormType() != FormType.FORM_TYPE_FIX && form.getFormType() != FormType.FORM_TYPE_FLOAT && form.getFormType() != FormType.FORM_TYPE_ATTACHED && form.getFormType() != FormType.FORM_TYPE_INTERMEDIATE) continue;
            List linkDefines = this.runTimeViewController.getAllLinksInForm(form.getKey());
            BlobFormStruct parentItem = null;
            ArrayList<BlobFieldStruct> childItems = null;
            for (DataLinkDefine linkDefine : linkDefines) {
                FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(linkDefine.getLinkExpression());
                if (field == null || field.getType() != FieldType.FIELD_TYPE_FILE) continue;
                if (parentItem == null) {
                    parentItem = new BlobFormStruct();
                    parentItem.setFlag(form.getFormCode());
                    parentItem.setTitle(form.getTitle());
                    parentItem.setKey(form.getKey());
                    parentItem.setGroupKey(form.getKey());
                    result.add(parentItem);
                    childItems = new ArrayList<BlobFieldStruct>();
                    parentItem.setChildren(childItems);
                }
                BlobFieldStruct childItem = new BlobFieldStruct();
                childItem.setFlag(field.getCode());
                childItem.setTitle(field.getTitle());
                childItem.setKey(field.getKey());
                childItem.setFormKey(form.getKey());
                childItem.setFormCode(form.getFormCode());
                childItem.setFormTitle(form.getTitle());
                childItem.setDataLinkKey(linkDefine.getKey());
                childItems.add(childItem);
            }
        }
        return result;
    }

    public List<FormDefine> getFormsAllList(String formSchemeKey) throws Exception {
        ArrayList<FormDefine> result = new ArrayList<FormDefine>();
        HashSet<String> formDic = new HashSet<String>();
        List allFormGroups = this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
        int formGroupIdx = 0;
        for (FormGroupDefine formGroup : allFormGroups) {
            List allFormsInGroup = this.runTimeViewController.getAllFormsInGroup(formGroup.getKey());
            for (FormDefine fd : allFormsInGroup) {
                if (fd.getFormType() != FormType.FORM_TYPE_FIX && fd.getFormType() != FormType.FORM_TYPE_FLOAT && fd.getFormType() != FormType.FORM_TYPE_ATTACHED && fd.getFormType() != FormType.FORM_TYPE_INTERMEDIATE || formDic.contains(fd.getKey())) continue;
                result.add(fd);
                formDic.add(fd.getKey());
            }
            ++formGroupIdx;
        }
        return result;
    }

    @RequestMapping(value={"/checkBlobFileSize"}, method={RequestMethod.POST})
    @ApiOperation(value="\u7efc\u5408\u5ba1\u6838\u5355\u4e2a\u5ba1\u6838\u9879\u9644\u4ef6\u5927\u5c0f\u68c0\u67e5")
    public AsyncTaskInfo checkBlobFileSize(@RequestBody BlobFileSizeCheckParam param) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(param.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(param.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)param));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BlobFileSizeCheckAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_BLOBFILESIZECHECK.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @RequestMapping(value={"/checkOneKeyBlobFileSize"}, method={RequestMethod.POST})
    @ApiOperation(value="\u7efc\u5408\u5ba1\u6838\u4e00\u952e\u5ba1\u6838\u65f6\u8c03\u7528\u9644\u4ef6\u5927\u5c0f\u68c0\u67e5")
    public AsyncTaskInfo checkOneKeyBlobFileSize(@RequestBody JtableContext context) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(context.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(context.getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)context));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BlobFileSizeCheckAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_BLOBFILESIZECHECK.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

