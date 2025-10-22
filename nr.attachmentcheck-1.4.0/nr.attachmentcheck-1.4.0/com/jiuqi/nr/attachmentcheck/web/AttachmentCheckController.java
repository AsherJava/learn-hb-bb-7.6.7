/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.attachmentcheck.web;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentFieldStruct;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentFormStruct;
import com.jiuqi.nr.attachmentcheck.common.AttachmentTableAndFieldSession;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u9644\u4ef6\u6587\u4ef6\u5927\u5c0f\u68c0\u67e5"})
@RequestMapping(value={"api/v2/finalaccount/attachmentcheck"})
public class AttachmentCheckController {
    @Autowired
    private DataDefinitionRuntimeController2 dataDefinitionRuntimeController;
    @Autowired
    private AttachmentTableAndFieldSession blobSession;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @RequestMapping(value={"/getBlobTablesAndFields"}, method={RequestMethod.GET})
    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u5ba1\u6838\u4e8b\u9879\u5206\u7ec4")
    public List<AttachmentFormStruct> getBlobTablesAndFields(String formSchemeKey) throws Exception {
        List<AttachmentFormStruct> result;
        Object obj = this.blobSession.getResult(formSchemeKey);
        if (null == obj) {
            result = this.getItems(formSchemeKey);
            this.blobSession.saveResult(formSchemeKey, result);
        } else {
            result = (List<AttachmentFormStruct>)obj;
        }
        return result;
    }

    private List<AttachmentFormStruct> getItems(String formSchemeKey) throws Exception {
        ArrayList<AttachmentFormStruct> result = new ArrayList<AttachmentFormStruct>();
        List<FormDefine> forms = this.getFormsAllList(formSchemeKey);
        for (FormDefine form : forms) {
            if (form.getFormType() != FormType.FORM_TYPE_FIX && form.getFormType() != FormType.FORM_TYPE_FLOAT && form.getFormType() != FormType.FORM_TYPE_ATTACHED && form.getFormType() != FormType.FORM_TYPE_INTERMEDIATE) continue;
            List linkDefines = this.runTimeViewController.getAllLinksInForm(form.getKey());
            AttachmentFormStruct parentItem = null;
            ArrayList<AttachmentFieldStruct> childItems = null;
            for (DataLinkDefine linkDefine : linkDefines) {
                FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(linkDefine.getLinkExpression());
                if (null == field || field.getType() != FieldType.FIELD_TYPE_FILE) continue;
                if (null == parentItem) {
                    parentItem = new AttachmentFormStruct();
                    parentItem.setFlag(form.getFormCode());
                    parentItem.setTitle(form.getTitle());
                    parentItem.setKey(form.getKey());
                    parentItem.setGroupKey(form.getKey());
                    result.add(parentItem);
                    childItems = new ArrayList<AttachmentFieldStruct>();
                    parentItem.setChildren(childItems);
                }
                AttachmentFieldStruct childItem = new AttachmentFieldStruct();
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
        for (FormGroupDefine formGroup : allFormGroups) {
            List allFormsInGroup = this.runTimeViewController.getAllFormsInGroup(formGroup.getKey());
            for (FormDefine fd : allFormsInGroup) {
                if (fd.getFormType() != FormType.FORM_TYPE_FIX && fd.getFormType() != FormType.FORM_TYPE_FLOAT && fd.getFormType() != FormType.FORM_TYPE_ATTACHED && fd.getFormType() != FormType.FORM_TYPE_INTERMEDIATE || formDic.contains(fd.getKey())) continue;
                result.add(fd);
                formDic.add(fd.getKey());
            }
        }
        return result;
    }
}

