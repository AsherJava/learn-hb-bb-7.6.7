/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.de.dataflow.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflowService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowServiceImpl
implements IWorkflowService {
    @Autowired
    private IQueryUploadStateService queryUploadStateService;

    @Override
    public boolean isActionDone(FormSchemeDefine formScheme, UploadState uploadState) {
        List<UploadStateNew> uploadStates = this.queryUploadStateService.queryUploadStates(formScheme.getKey());
        if (uploadStates != null && uploadStates.size() > 0) {
            for (UploadStateNew state : uploadStates) {
                ActionStateBean actionStateBean = state.getActionStateBean();
                if (actionStateBean == null || actionStateBean.getCode() == null) continue;
                String code = actionStateBean.getCode();
                if (uploadState.name().equals(code)) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isActionDone(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, String fromKeys, String groupKeys, UploadState uploadStates) {
        ActionStateBean actionStateBean;
        UploadStateNew uploadStateNew = this.queryUploadStateService.queryUploadState(formScheme.getKey(), dimensionValueSet, fromKeys, groupKeys);
        if (uploadStateNew != null && uploadStateNew.getTaskId() != null && (actionStateBean = uploadStateNew.getActionStateBean()) != null && actionStateBean.getCode() != null) {
            String code = actionStateBean.getCode();
            if (uploadStates.name().equals(code)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<DimensionValueSet, Boolean> isActionDone(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, List<String> fromKeys, List<String> groupKeys, UploadState uploadStates) {
        HashMap<DimensionValueSet, Boolean> stateMap = new HashMap<DimensionValueSet, Boolean>();
        List<UploadStateNew> uploadStateList = this.queryUploadStateService.queryUploadStates(formScheme.getKey(), dimensionValueSet, fromKeys, groupKeys);
        if (uploadStateList != null && uploadStateList.size() > 0) {
            for (UploadStateNew uploadStateNew : uploadStateList) {
                DimensionValueSet entities = uploadStateNew.getEntities();
                ActionStateBean actionStateBean = uploadStateNew.getActionStateBean();
                if (actionStateBean == null || actionStateBean.getCode() == null) continue;
                if (uploadStates.name().equals(actionStateBean.getCode())) {
                    stateMap.put(entities, true);
                    continue;
                }
                stateMap.put(entities, false);
            }
        }
        return null;
    }

    @Override
    public List<UploadStateNew> getDataByActionCode(FormSchemeDefine formScheme, List<UploadState> uploadStates) {
        List<UploadStateNew> uploadStateList = this.queryUploadStateService.queryUploadStates(formScheme.getKey());
        if (uploadStateList != null && uploadStateList.size() > 0) {
            return this.filterResult(uploadStateList, uploadStates);
        }
        return new ArrayList<UploadStateNew>();
    }

    @Override
    public List<UploadStateNew> getDataByActionCode(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, List<UploadState> uploadStates) {
        List<UploadStateNew> uploadStateNewList = this.queryUploadStateService.queryUploadStates(formScheme.getKey(), dimensionValueSet);
        if (uploadStateNewList != null && uploadStateNewList.size() > 0) {
            return this.filterResult(uploadStateNewList, uploadStates);
        }
        return new ArrayList<UploadStateNew>();
    }

    @Override
    public List<UploadStateNew> getDataByActionCode(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, List<String> formKeys, List<String> groupKeys, List<UploadState> uploadStates) {
        List<UploadStateNew> uploadStateNewList = this.queryUploadStateService.queryUploadStates(formScheme.getKey(), dimensionValueSet, formKeys, groupKeys);
        if (uploadStateNewList != null && uploadStateNewList.size() > 0) {
            return this.filterResult(uploadStateNewList, uploadStates);
        }
        return new ArrayList<UploadStateNew>();
    }

    private List<UploadStateNew> filterResult(List<UploadStateNew> uploadStateNewList, List<UploadState> uploadStates) {
        List states = uploadStates.stream().map(Enum::name).collect(Collectors.toList());
        if (states.contains(UploadState.ORIGINAL_SUBMIT_PART_START.name())) {
            states.remove(UploadState.ORIGINAL_SUBMIT_PART_START.name());
            states.add(UploadState.ORIGINAL_SUBMIT.name());
            states.add(UploadState.PART_START.name());
            states.add(UploadState.PART_SUBMITED.name());
        }
        if (states.contains(UploadState.ORIGINAL_UPLOAD_PART_START.name())) {
            states.remove(UploadState.ORIGINAL_UPLOAD_PART_START.name());
            states.add(UploadState.ORIGINAL_UPLOAD.name());
            states.add(UploadState.PART_START.name());
            states.add(UploadState.PART_UPLOADED.name());
        }
        ArrayList<UploadStateNew> list = new ArrayList<UploadStateNew>();
        for (UploadStateNew uploadStateNew : uploadStateNewList) {
            String code;
            ActionStateBean actionStateBean = uploadStateNew.getActionStateBean();
            if (actionStateBean == null || actionStateBean.getCode() == null || !states.contains(code = actionStateBean.getCode())) continue;
            list.add(uploadStateNew);
        }
        return list;
    }
}

