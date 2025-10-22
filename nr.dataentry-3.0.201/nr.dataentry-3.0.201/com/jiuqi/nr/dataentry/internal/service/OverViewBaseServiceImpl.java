/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.bpm.de.dataflow.tree.TreeWorkflow
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeWorkflow;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo;
import com.jiuqi.nr.dataentry.paramInfo.DimensionValueFormInfo;
import com.jiuqi.nr.dataentry.service.IOverViewBaseService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OverViewBaseServiceImpl
implements IOverViewBaseService {
    private static final Logger log = LoggerFactory.getLogger(OverViewBaseServiceImpl.class);
    @Resource
    IRunTimeViewController runtimeView;
    @Autowired
    private FormGroupProvider formGroupProvider;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    TreeWorkflow treeWorkFlow;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;

    @Override
    public List<IEntityRow> filterAuthByEntity(JtableContext jtableContext, List<IEntityRow> sourceList, String form, WorkFlowType queryStartType) {
        ArrayList<IEntityRow> res = new ArrayList<IEntityRow>();
        String mainKey = sourceList.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.joining(";"));
        Map dimensionSet = jtableContext.getDimensionSet();
        HashMap newDimensionSet = new HashMap();
        EntityViewData masterEntityViewData = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        List dimEntityList = this.jtableParamService.getDimEntityList(jtableContext.getFormSchemeKey());
        boolean isMulti = !dimEntityList.isEmpty();
        String formKeyStr = "";
        for (String key : dimensionSet.keySet()) {
            if (!key.equals(masterEntityViewData.getDimensionName())) {
                newDimensionSet.put(key, dimensionSet.get(key));
                continue;
            }
            DimensionValue newDimensionValue = new DimensionValue();
            newDimensionValue.setName(((DimensionValue)dimensionSet.get(key)).getName());
            newDimensionValue.setType(((DimensionValue)dimensionSet.get(key)).getType());
            newDimensionValue.setValue(mainKey);
            newDimensionSet.put(key, newDimensionValue);
        }
        if (isMulti) {
            // empty if block
        }
        JtableContext context = new JtableContext();
        if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
            List allFormsInGroup = null;
            try {
                allFormsInGroup = this.runtimeView.getAllFormsInGroup(form);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            formKeyStr = allFormsInGroup.stream().map(r -> r.getKey()).distinct().collect(Collectors.joining(";"));
        } else {
            formKeyStr = form;
            context.setFormKey(jtableContext.getFormKey());
        }
        context.setDimensionSet(newDimensionSet);
        context.setFormGroupKey(jtableContext.getFormGroupKey());
        context.setFormSchemeKey(jtableContext.getFormSchemeKey());
        context.setTaskKey(jtableContext.getTaskKey());
        context.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
        BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(context, formKeyStr, Consts.FormAccessLevel.FORM_READ);
        List<DimensionValueFormInfo> acessFormInfos = batchDimensionValueFormInfo.getAccessFormInfos();
        for (DimensionValueFormInfo acessForm : acessFormInfos) {
            List<String> forms = acessForm.getForms();
            if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                List formGroupsByFormKey = new ArrayList();
                for (String acessFormKey : forms) {
                    formGroupsByFormKey = this.runtimeView.getFormGroupsByFormKey(acessFormKey);
                }
                String collect2 = formGroupsByFormKey.stream().map(r -> r.getKey()).distinct().collect(Collectors.joining(";"));
                if (!collect2.contains(form)) continue;
                Map<String, DimensionValue> dimensionValue = acessForm.getDimensionValue();
                String dimensionName = masterEntityViewData.getDimensionName();
                String value = dimensionValue.get(dimensionName).getValue();
                ArrayList collect = new ArrayList();
                String[] split = value.split(";");
                if (split.length > 0) {
                    for (int i = 0; i < split.length; ++i) {
                        String onValue = split[i];
                        collect.addAll(sourceList.stream().filter(s -> s.getEntityKeyData().equals(onValue)).collect(Collectors.toList()));
                    }
                }
                if (collect.size() <= 0) continue;
                res.addAll(collect);
                continue;
            }
            String collect2 = forms.stream().distinct().collect(Collectors.joining(";"));
            if (!collect2.contains(form)) continue;
            Map<String, DimensionValue> dimensionValue = acessForm.getDimensionValue();
            String dimensionName = masterEntityViewData.getDimensionName();
            String value = dimensionValue.get(dimensionName).getValue();
            ArrayList collect = new ArrayList();
            String[] split = value.split(";");
            if (split.length > 0) {
                for (int i = 0; i < split.length; ++i) {
                    String onValue = split[i];
                    collect.addAll(sourceList.stream().filter(s -> s.getEntityKeyData().equals(onValue)).collect(Collectors.toList()));
                }
            }
            if (collect.size() <= 0) continue;
            res.addAll(collect);
        }
        return res;
    }

    @Override
    public List<String> filterAuth(JtableContext jtableContext, List<String> sourceList, String form, EntityViewData dwEntity, WorkFlowType queryStartType) {
        String mainKey = sourceList.stream().collect(Collectors.joining(";"));
        Map dimensionSet = jtableContext.getDimensionSet();
        HashMap newDimensionSet = new HashMap();
        String formKeyStr = "";
        EntityViewData masterEntityView = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        List dimEntityList = this.jtableParamService.getDimEntityList(jtableContext.getFormSchemeKey());
        boolean isMulti = !dimEntityList.isEmpty();
        for (String key : dimensionSet.keySet()) {
            if (!key.equals(masterEntityView.getDimensionName())) {
                newDimensionSet.put(key, dimensionSet.get(key));
                continue;
            }
            DimensionValue newDimensionValue = new DimensionValue();
            newDimensionValue.setName(((DimensionValue)dimensionSet.get(key)).getName());
            newDimensionValue.setType(((DimensionValue)dimensionSet.get(key)).getType());
            newDimensionValue.setValue(mainKey);
            newDimensionSet.put(key, newDimensionValue);
        }
        if (isMulti) {
            // empty if block
        }
        JtableContext context = new JtableContext();
        if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
            List allFormsInGroup = null;
            try {
                allFormsInGroup = this.runtimeView.getAllFormsInGroup(form);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            formKeyStr = allFormsInGroup.stream().map(r -> r.getKey()).distinct().collect(Collectors.joining(";"));
        } else if (WorkFlowType.FORM.equals((Object)queryStartType)) {
            formKeyStr = form;
            context.setFormKey(jtableContext.getFormKey());
        } else {
            return sourceList;
        }
        context.setDimensionSet(newDimensionSet);
        context.setFormGroupKey(jtableContext.getFormGroupKey());
        context.setFormSchemeKey(jtableContext.getFormSchemeKey());
        context.setTaskKey(jtableContext.getTaskKey());
        context.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        String entityName = targetEntityInfo.getDimensionName();
        List<String> FormKeyList = new ArrayList();
        if (!StringUtils.isEmpty((String)formKeyStr)) {
            for (String formKey : formKeyStr.split(";")) {
                FormKeyList.add(formKey);
            }
        }
        if (FormKeyList.size() == 0) {
            FormKeyList = this.runtimeView.queryAllFormKeysByFormScheme(jtableContext.getFormSchemeKey());
        }
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey());
        DimensionCollection dimCollection = DimensionValueSetUtil.buildDimensionCollection(newDimensionSet, (String)jtableContext.getFormSchemeKey());
        IBatchAccessResult batchAccessResult = dataAccessService.getVisitAccess(dimCollection, FormKeyList);
        List dimCollectionList = dimCollection.getDimensionCombinations();
        LinkedHashSet<String> entityKeyList = new LinkedHashSet<String>();
        for (DimensionCombination dimensionComin : dimCollectionList) {
            String[] dimValuesAry;
            Map currDimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionComin.toDimensionValueSet());
            String dimensionValues = ((DimensionValue)currDimensionSet.get(entityName)).getValue();
            for (String entityKey : dimValuesAry = dimensionValues.split(";")) {
                for (String key : FormKeyList) {
                    IAccessResult accessResult = batchAccessResult.getAccess(dimensionComin, key);
                    try {
                        if (!accessResult.haveAccess()) continue;
                        entityKeyList.add(entityKey);
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return new ArrayList<String>(entityKeyList);
    }

    @Override
    public List<String> getAuthFormGroup(JtableContext jtableContext) {
        String formKeyStr = "";
        List<Object> formGroupKeys = new ArrayList();
        if (formGroupKeys.size() == 0) {
            List allFormGroupsInFormScheme = this.runtimeView.getAllFormGroupsInFormScheme(jtableContext.getFormSchemeKey());
            formGroupKeys = allFormGroupsInFormScheme.stream().distinct().map(r -> r.getKey()).collect(Collectors.toList());
        }
        for (String formGroupKey : formGroupKeys) {
            List allFormsInGroup = null;
            try {
                allFormsInGroup = this.runtimeView.getAllFormsInGroup(formGroupKey);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            formKeyStr = formKeyStr + allFormsInGroup.stream().map(r -> r.getKey()).distinct().collect(Collectors.joining(";")) + ";";
        }
        BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(jtableContext, formKeyStr, Consts.FormAccessLevel.FORM_READ);
        List<DimensionValueFormInfo> acessFormInfos = batchDimensionValueFormInfo.getAccessFormInfos();
        ArrayList<String> allFormGroup = new ArrayList<String>();
        for (DimensionValueFormInfo acessFormInfo : acessFormInfos) {
            List<String> forms = acessFormInfo.getForms();
            for (String form : forms) {
                List allFormGroupsByFormKey = this.runtimeView.getFormGroupsByFormKey(form);
                for (FormGroupDefine formGroup : allFormGroupsByFormKey) {
                    if (allFormGroup.contains(formGroup.getKey()) || !formGroup.getFormSchemeKey().equals(jtableContext.getFormSchemeKey())) continue;
                    allFormGroup.add(formGroup.getKey());
                }
            }
        }
        return allFormGroup;
    }

    @Override
    public Map<String, String> getFilterForm(WorkFlowType queryStartType, String mainKey, String mainDim, JtableContext context) {
        HashMap<String, String> res = new HashMap<String, String>();
        Map dimensionSet = context.getDimensionSet();
        HashMap newDimensionSet = new HashMap();
        List dimEntityList = this.jtableParamService.getDimEntityList(context.getFormSchemeKey());
        boolean isMulti = !dimEntityList.isEmpty();
        String formKeyStr = "";
        for (String key : dimensionSet.keySet()) {
            newDimensionSet.put(key, dimensionSet.get(key));
        }
        if (isMulti) {
            // empty if block
        }
        List<Object> formGroupKeys = new ArrayList();
        if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
            if (formGroupKeys.size() == 0) {
                List allFormGroupsInFormScheme = this.runtimeView.getAllFormGroupsInFormScheme(context.getFormSchemeKey());
                formGroupKeys = allFormGroupsInFormScheme.stream().distinct().map(r -> r.getKey()).collect(Collectors.toList());
            }
            for (String formGroupKey : formGroupKeys) {
                List allFormsInGroup = null;
                try {
                    allFormsInGroup = this.runtimeView.getAllFormsInGroup(formGroupKey);
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                formKeyStr = formKeyStr + allFormsInGroup.stream().map(r -> r.getKey()).distinct().collect(Collectors.joining(";")) + ";";
            }
        } else {
            ArrayList formKeys = new ArrayList();
            formKeyStr = formKeys.stream().distinct().collect(Collectors.joining(";"));
        }
        JtableContext jtableContext = new JtableContext(context);
        jtableContext.setDimensionSet(newDimensionSet);
        BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(jtableContext, formKeyStr, Consts.FormAccessLevel.FORM_READ);
        List<DimensionValueFormInfo> acessFormInfos = batchDimensionValueFormInfo.getAccessFormInfos();
        for (DimensionValueFormInfo acessFormInfo : acessFormInfos) {
            ArrayList<String> allFormGroup = new ArrayList<String>();
            List<String> forms = acessFormInfo.getForms();
            if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                for (String form : forms) {
                    List allFormGroupsByFormKey = this.runtimeView.getFormGroupsByFormKey(form);
                    for (FormGroupDefine formGroup : allFormGroupsByFormKey) {
                        if (allFormGroup.contains(formGroup.getKey())) continue;
                        String str = res.get(formGroup.getKey()) == null ? "" : (String)res.get(formGroup.getKey());
                        res.put(formGroup.getKey(), acessFormInfo.getDimensionValue().get(mainDim).getValue() + ";" + str);
                        allFormGroup.add(formGroup.getKey());
                    }
                }
                continue;
            }
            if (!WorkFlowType.FORM.equals((Object)queryStartType)) continue;
            for (String form : forms) {
                String str = res.get(form) == null ? "" : (String)res.get(form);
                res.put(form, acessFormInfo.getDimensionValue().get(mainDim).getValue() + ";" + str);
            }
        }
        return res;
    }

    @Override
    public Map<String, String> getTitleMap(String formschemeKey, String period, boolean flowsType) {
        Map actionInfo = this.treeWorkFlow.getActionCodeAndStateName(formschemeKey);
        Map actionCodeAndActionName = this.treeWorkFlow.getActionCodeAndActionName(formschemeKey);
        HashMap<String, String> result = new HashMap<String, String>();
        if (actionInfo == null) {
            return result;
        }
        if (actionInfo.containsKey("start")) {
            if ((actionInfo.containsKey("act_submit") || actionInfo.containsKey("cus_submit")) && flowsType) {
                result.put("unSubmitedNum", (String)actionInfo.get("start"));
            } else if (!flowsType) {
                result.put("originalNum", "\u672a\u4e0a\u62a5");
            } else {
                result.put("originalNum", (String)actionInfo.get("start"));
            }
        }
        if (flowsType) {
            if (actionInfo.containsKey("act_submit") || actionInfo.containsKey("cus_submit")) {
                if (actionInfo.containsKey("act_submit")) {
                    result.put("submitedNum", (String)actionInfo.get("act_submit"));
                } else if (actionInfo.containsKey("cus_submit")) {
                    result.put("submitedNum", (String)actionInfo.get("cus_submit"));
                }
            }
            if (actionInfo.containsKey("act_return") || actionInfo.containsKey("cus_return")) {
                if (actionInfo.containsKey("act_return")) {
                    result.put("returnedNum", (String)actionInfo.get("act_return"));
                } else if (actionInfo.containsKey("cus_return")) {
                    result.put("returnedNum", (String)actionInfo.get("cus_return"));
                }
            }
            if (actionCodeAndActionName.containsKey("act_submit")) {
                result.put("submitedrate", (String)actionCodeAndActionName.get("act_submit") + "\u7387");
            } else {
                result.put("submitedrate", "\u9001\u5ba1\u7387");
            }
        }
        if (actionInfo.containsKey("act_reject") || actionInfo.containsKey("cus_reject")) {
            if (actionInfo.containsKey("act_reject")) {
                result.put("rejectedNum", (String)actionInfo.get("act_reject"));
            } else if (actionInfo.containsKey("cus_reject")) {
                result.put("rejectedNum", (String)actionInfo.get("cus_reject"));
            }
        }
        if (actionInfo.containsKey("act_upload") || actionInfo.containsKey("cus_upload")) {
            if (actionInfo.containsKey("act_upload")) {
                result.put("uploadedNum", (String)actionInfo.get("act_upload"));
            } else if (actionInfo.containsKey("cus_upload")) {
                result.put("uploadedNum", (String)actionInfo.get("cus_upload"));
            }
        }
        if (actionInfo.containsKey("act_confirm") || actionInfo.containsKey("cus_confirm")) {
            if (actionInfo.containsKey("act_confirm")) {
                result.put("confirmedNum", (String)actionInfo.get("act_confirm"));
            } else if (actionInfo.containsKey("cus_confirm")) {
                result.put("confirmedNum", (String)actionInfo.get("cus_confirm"));
            }
        }
        if (actionInfo.containsKey("act_cancel_confirm")) {
            result.put("cancelConfirmNum", (String)actionInfo.get("act_cancel_confirm"));
        }
        if (actionCodeAndActionName.containsKey("act_upload") || actionCodeAndActionName.containsKey("cus_upload")) {
            if (actionCodeAndActionName.containsKey("act_upload")) {
                result.put("ReportRate", (String)actionCodeAndActionName.get("act_upload") + "\u7387");
            } else {
                result.put("ReportRate", (String)actionCodeAndActionName.get("cus_upload") + "\u7387");
            }
        } else {
            result.put("ReportRate", "\u4e0a\u62a5\u7387");
        }
        if (actionCodeAndActionName.containsKey("act_confirm") || actionCodeAndActionName.containsKey("cus_confirm")) {
            if (actionCodeAndActionName.containsKey("act_confirm")) {
                result.put("confirmrate", (String)actionCodeAndActionName.get("act_confirm") + "\u7387");
            } else {
                result.put("confirmrate", (String)actionCodeAndActionName.get("cus_confirm") + "\u7387");
            }
        } else {
            result.put("confirmrate", "\u786e\u8ba4\u7387");
        }
        result.put("delayRate", "\u5ef6\u8fdf\u7387");
        result.put("delayNum", "\u5df2\u5ef6\u8fdf");
        return result;
    }

    private void setDefaultValue(Map<String, DimensionValue> newDimensionSet) {
        Iterator<String> iterator = newDimensionSet.keySet().iterator();
        while (iterator.hasNext()) {
            String key;
            switch (key = iterator.next()) {
                case "MD_CURRENCY_CODE": 
                case "MD_CURRENCY": {
                    newDimensionSet.get(key).setValue("CNY");
                    break;
                }
                case "MD_GCADJTYPE_CODE": 
                case "MD_GCADJTYPE": {
                    newDimensionSet.get(key).setValue("BEFOREADJ");
                    break;
                }
                case "MD_GCORGTYPE_CODE": 
                case "MD_GCORGTYPE": {
                    newDimensionSet.get(key).setValue("MD_ORG_CORPORATE");
                }
            }
        }
    }

    @Override
    public Map<String, String> getActionTitleMap(String formschemeKey, String period, boolean flowsType) {
        Map actionInfo = this.treeWorkFlow.getActionCodeAndStateName(formschemeKey);
        HashMap<String, String> result = new HashMap<String, String>();
        if (actionInfo == null) {
            return result;
        }
        if (actionInfo.containsKey("start")) {
            if ((actionInfo.containsKey("act_submit") || actionInfo.containsKey("cus_submit")) && flowsType) {
                result.put("ORIGINAL_SUBMIT", (String)actionInfo.get("start"));
            } else if (!flowsType) {
                result.put("ORIGINAL_UPLOAD", "\u672a\u4e0a\u62a5");
            } else {
                result.put("ORIGINAL_UPLOAD", (String)actionInfo.get("start"));
            }
        }
        if (flowsType) {
            if (actionInfo.containsKey("act_submit") || actionInfo.containsKey("cus_submit")) {
                if (actionInfo.containsKey("act_submit")) {
                    result.put("SUBMITED", (String)actionInfo.get("act_submit"));
                } else if (actionInfo.containsKey("cus_submit")) {
                    result.put("SUBMITED", (String)actionInfo.get("cus_submit"));
                }
            }
            if (actionInfo.containsKey("act_return") || actionInfo.containsKey("cus_return")) {
                if (actionInfo.containsKey("act_return")) {
                    result.put("RETURNED", (String)actionInfo.get("act_return"));
                } else if (actionInfo.containsKey("cus_return")) {
                    result.put("RETURNED", (String)actionInfo.get("cus_return"));
                }
            }
        }
        if (actionInfo.containsKey("act_reject") || actionInfo.containsKey("cus_reject")) {
            if (actionInfo.containsKey("act_reject")) {
                result.put("REJECTED", (String)actionInfo.get("act_reject"));
            } else if (actionInfo.containsKey("cus_reject")) {
                result.put("REJECTED", (String)actionInfo.get("cus_reject"));
            }
        }
        if (actionInfo.containsKey("act_upload") || actionInfo.containsKey("cus_upload")) {
            if (actionInfo.containsKey("act_upload")) {
                result.put("UPLOADED", (String)actionInfo.get("act_upload"));
            } else if (actionInfo.containsKey("cus_upload")) {
                result.put("UPLOADED", (String)actionInfo.get("cus_upload"));
            }
        }
        if (actionInfo.containsKey("act_confirm") || actionInfo.containsKey("cus_confirm")) {
            if (actionInfo.containsKey("act_confirm")) {
                result.put("CONFIRMED", (String)actionInfo.get("act_confirm"));
            } else if (actionInfo.containsKey("cus_confirm")) {
                result.put("CONFIRMED", (String)actionInfo.get("cus_confirm"));
            }
        }
        return result;
    }
}

