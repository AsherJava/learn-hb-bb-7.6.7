/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.designer.util.InitParamObjPropertyUtil;
import com.jiuqi.nr.designer.web.facade.FormAndGroupObjs;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.treebean.FormGroupObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags={"\u5efa\u6a21\u5237\u65b0"})
@JQRestController
@RequestMapping(value={"api/v1/designer/"})
public class DesignTimeViewRefreshing {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private InitParamObjPropertyUtil initParamObjPropertyUtil;

    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u62a5\u8868\u548c\u62a5\u8868\u5206\u7ec4")
    @GetMapping(value={"query_forms_groups/{formScheme}"})
    public FormAndGroupObjs getFormsAndFormGroups(@PathVariable String formScheme) throws JQException {
        FormAndGroupObjs formAndGroupObjs = new FormAndGroupObjs();
        ArrayList<FormGroupObject> groupList = new ArrayList<FormGroupObject>();
        ArrayList<FormObj> formDefineList = new ArrayList<FormObj>();
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(formScheme);
        DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(formSchemeDefine.getTaskKey());
        List allGroupsInFormScheme = this.nrDesignTimeController.queryAllGroupsByFormScheme(formScheme);
        List allGroupKeys = allGroupsInFormScheme.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        List formGroupLinks = this.nrDesignTimeController.getFormGroupLinks(allGroupKeys);
        List allFormKeys = formGroupLinks.stream().map(DesignFormGroupLink::getFormKey).collect(Collectors.toList());
        List formDefines = this.nrDesignTimeController.getSimpleFormDefines(allFormKeys);
        Map<String, List<DesignFormGroupLink>> groupToFormsMap = formGroupLinks.stream().collect(Collectors.groupingBy(DesignFormGroupLink::getGroupKey));
        Map<String, List<DesignFormGroupLink>> formToGroupMap = formGroupLinks.stream().collect(Collectors.groupingBy(DesignFormGroupLink::getFormKey));
        for (DesignFormGroupDefine formGroupDefine : allGroupsInFormScheme) {
            List designFormDefineList;
            List<DesignFormGroupLink> groupLinks = groupToFormsMap.get(formGroupDefine.getKey());
            if (groupLinks != null) {
                Map<String, String> formToOrderMap = groupLinks.stream().collect(Collectors.toMap(DesignFormGroupLink::getFormKey, DesignFormGroupLink::getFormOrder));
                designFormDefineList = formDefines.stream().filter(e -> {
                    String order = (String)formToOrderMap.get(e.getKey());
                    if (order != null) {
                        e.setOrder(order);
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
            } else {
                designFormDefineList = this.nrDesignTimeController.getAllFormsInGroupLazy(formGroupDefine.getKey(), false);
            }
            for (DesignFormDefine formDefine : designFormDefineList) {
                try {
                    List<String> ownGroupKeys = formToGroupMap.get(formDefine.getKey()).stream().map(e -> e.getGroupKey()).collect(Collectors.toList());
                    formDefineList.add(this.initParamObjPropertyUtil.setFormObjPropertyCopy(taskDefine, formSchemeDefine, formGroupDefine, formDefine, false, ownGroupKeys));
                }
                catch (Exception e2) {
                    throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_060, (Throwable)e2);
                }
            }
            groupList.add(this.initParamObjPropertyUtil.transFormGroupObject(taskDefine, formGroupDefine));
        }
        formAndGroupObjs.setFormGroupObjects(groupList);
        formAndGroupObjs.setFormObjs(formDefineList);
        return formAndGroupObjs;
    }

    public FormAndGroupObjs getFormsAndFormGroupsByTask(@PathVariable String taskKey) throws JQException {
        FormAndGroupObjs formAndGroupObjs = new FormAndGroupObjs();
        ArrayList<FormGroupObject> groupList = new ArrayList<FormGroupObject>();
        ArrayList<FormObj> formDefineList = new ArrayList<FormObj>();
        DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
        List formSchemeList = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
        for (DesignFormSchemeDefine formSchemeDefine : formSchemeList) {
            List queryAllGroupsByFormScheme = this.nrDesignTimeController.queryAllGroupsByFormScheme(formSchemeDefine.getKey());
            for (DesignFormGroupDefine formGroupDefine : queryAllGroupsByFormScheme) {
                List forms = this.nrDesignTimeController.getAllFormsInGroupLazy(formGroupDefine.getKey(), false);
                for (DesignFormDefine designFormDefine : forms) {
                }
                groupList.add(this.initParamObjPropertyUtil.transFormGroupObject(taskDefine, formGroupDefine));
            }
        }
        formAndGroupObjs.setFormGroupObjects(groupList);
        formAndGroupObjs.setFormObjs(formDefineList);
        return formAndGroupObjs;
    }
}

