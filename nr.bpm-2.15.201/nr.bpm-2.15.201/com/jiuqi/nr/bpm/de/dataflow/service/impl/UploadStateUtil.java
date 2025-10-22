/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.service.impl;

import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.bean.UploadStateBean;
import com.jiuqi.nr.bpm.de.dataflow.constont.StateEnum;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.bpm.de.dataflow.service.IActionAlias;
import com.jiuqi.nr.bpm.upload.UploadState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadStateUtil {
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private IActionAlias actionAlias;
    Map<String, String> actionStateCodeAndStateName = new HashMap<String, String>();
    Map<String, String> actionCodeAndStateName = new HashMap<String, String>();

    public List<UploadStateBean> queryState(UploadState state, String formSchemekey) {
        ArrayList<UploadStateBean> uploadStateList = new ArrayList();
        this.actionStateCodeAndStateName = this.actionAlias.actionStateCodeAndStateName(formSchemekey);
        this.actionCodeAndStateName = this.actionAlias.actionCodeAndStateName(formSchemekey);
        uploadStateList = this.queryAllactionCode(state);
        return uploadStateList;
    }

    public List<UploadStateBean> queryAllactionCode(UploadState state) {
        ArrayList<UploadStateBean> uploadStateList = new ArrayList<UploadStateBean>();
        UploadStateBean uploadStateBean = null;
        switch (state) {
            case ORIGINAL: 
            case ORIGINAL_UPLOAD: 
            case ORIGINAL_SUBMIT: {
                uploadStateBean = new UploadStateBean();
                uploadStateBean.setActionCode("start".toString());
                uploadStateBean.setActionName(UploadStateEnum.ACTION_START.getName());
                uploadStateBean.setUploadStateCode(UploadState.ORIGINAL_SUBMIT.toString());
                uploadStateBean.setUploadStateName(UploadStateEnum.ORIGINAL_SUBMIT.getName());
                uploadStateList.add(uploadStateBean);
                return uploadStateList;
            }
            case SUBMITED: {
                return this.submitAction();
            }
            case RETURNED: {
                return this.returnAction();
            }
            case UPLOADED: {
                return this.uploadedAction();
            }
            case CONFIRMED: {
                return this.confirmedAction();
            }
            case REJECTED: {
                return this.rejectedAction();
            }
        }
        List<WorkFlowAction> allWorkflowAction = this.customWorkFolwService.getAllWorkflowAction();
        if (allWorkflowAction.size() > 0) {
            for (WorkFlowAction workFlowAction : allWorkflowAction) {
                uploadStateBean = new UploadStateBean();
                uploadStateBean.setActionCode(workFlowAction.getActionCode());
                uploadStateBean.setActionName(workFlowAction.getActionTitle());
                uploadStateBean.setUploadStateCode(workFlowAction.getStateCode());
                uploadStateBean.setUploadStateName(workFlowAction.getStateName());
                uploadStateList.add(uploadStateBean);
            }
            return uploadStateList;
        }
        return null;
    }

    private List<UploadStateBean> submitAction() {
        StateEnum.Submit[] values;
        ArrayList<UploadStateBean> uploadStateList = new ArrayList<UploadStateBean>();
        UploadStateBean uploadStateBean = null;
        for (StateEnum.Submit value : values = StateEnum.Submit.values()) {
            String code = value.getCode();
            String stateCode = value.getStateCode();
            String name = value.getName();
            String stateName = value.getStateName();
            uploadStateBean = new UploadStateBean();
            uploadStateBean.setActionCode(code);
            if (this.actionCodeAndStateName != null && this.actionCodeAndStateName.size() > 0) {
                uploadStateBean.setActionName(this.actionCodeAndStateName.get(code));
            } else {
                uploadStateBean.setActionName(name);
            }
            uploadStateBean.setUploadStateCode(stateCode);
            if (this.actionStateCodeAndStateName != null && this.actionStateCodeAndStateName.size() > 0) {
                uploadStateBean.setUploadStateName(this.actionStateCodeAndStateName.get(stateCode));
            } else {
                uploadStateBean.setUploadStateName(stateName);
            }
            uploadStateList.add(uploadStateBean);
        }
        return uploadStateList;
    }

    private List<UploadStateBean> returnAction() {
        StateEnum.Return[] values;
        ArrayList<UploadStateBean> uploadStateList = new ArrayList<UploadStateBean>();
        UploadStateBean uploadStateBean = null;
        for (StateEnum.Return value : values = StateEnum.Return.values()) {
            String code = value.getCode();
            String stateCode = value.getStateCode();
            String name = value.getName();
            String stateName = value.getStateName();
            uploadStateBean = new UploadStateBean();
            uploadStateBean.setActionCode(code);
            if (this.actionCodeAndStateName != null && this.actionCodeAndStateName.size() > 0) {
                uploadStateBean.setActionName(this.actionCodeAndStateName.get(code));
            } else {
                uploadStateBean.setActionName(name);
            }
            uploadStateBean.setUploadStateCode(stateCode);
            if (this.actionStateCodeAndStateName != null && this.actionStateCodeAndStateName.size() > 0) {
                uploadStateBean.setUploadStateName(this.actionStateCodeAndStateName.get(stateCode));
            } else {
                uploadStateBean.setUploadStateName(stateName);
            }
            uploadStateList.add(uploadStateBean);
        }
        return uploadStateList;
    }

    private List<UploadStateBean> uploadedAction() {
        StateEnum.Upload[] values;
        ArrayList<UploadStateBean> uploadStateList = new ArrayList<UploadStateBean>();
        UploadStateBean uploadStateBean = null;
        for (StateEnum.Upload value : values = StateEnum.Upload.values()) {
            String code = value.getCode();
            String stateCode = value.getStateCode();
            String name = value.getName();
            String stateName = value.getStateName();
            uploadStateBean = new UploadStateBean();
            uploadStateBean.setActionCode(code);
            if (this.actionCodeAndStateName != null && this.actionCodeAndStateName.size() > 0) {
                uploadStateBean.setActionName(this.actionCodeAndStateName.get(code));
            } else {
                uploadStateBean.setActionName(name);
            }
            uploadStateBean.setUploadStateCode(stateCode);
            if (this.actionStateCodeAndStateName != null && this.actionStateCodeAndStateName.size() > 0) {
                uploadStateBean.setUploadStateName(this.actionStateCodeAndStateName.get(stateCode));
            } else {
                uploadStateBean.setUploadStateName(stateName);
            }
            uploadStateList.add(uploadStateBean);
        }
        return uploadStateList;
    }

    private List<UploadStateBean> confirmedAction() {
        StateEnum.Confirm[] values;
        ArrayList<UploadStateBean> uploadStateList = new ArrayList<UploadStateBean>();
        UploadStateBean uploadStateBean = null;
        for (StateEnum.Confirm value : values = StateEnum.Confirm.values()) {
            String code = value.getCode();
            String stateCode = value.getStateCode();
            String name = value.getName();
            String stateName = value.getStateName();
            uploadStateBean = new UploadStateBean();
            uploadStateBean.setActionCode(code);
            if (this.actionCodeAndStateName != null && this.actionCodeAndStateName.size() > 0) {
                uploadStateBean.setActionName(this.actionCodeAndStateName.get(code));
            } else {
                uploadStateBean.setActionName(name);
            }
            uploadStateBean.setUploadStateCode(stateCode);
            if (this.actionStateCodeAndStateName != null && this.actionStateCodeAndStateName.size() > 0) {
                uploadStateBean.setUploadStateName(this.actionStateCodeAndStateName.get(stateCode));
            } else {
                uploadStateBean.setUploadStateName(stateName);
            }
            uploadStateList.add(uploadStateBean);
        }
        return uploadStateList;
    }

    private List<UploadStateBean> rejectedAction() {
        StateEnum.Reject[] values;
        ArrayList<UploadStateBean> uploadStateList = new ArrayList<UploadStateBean>();
        UploadStateBean uploadStateBean = null;
        for (StateEnum.Reject value : values = StateEnum.Reject.values()) {
            String code = value.getCode();
            String stateCode = value.getStateCode();
            String name = value.getName();
            String stateName = value.getStateName();
            uploadStateBean = new UploadStateBean();
            uploadStateBean.setActionCode(code);
            if (this.actionCodeAndStateName != null && this.actionCodeAndStateName.size() > 0) {
                uploadStateBean.setActionName(this.actionCodeAndStateName.get(code));
            } else {
                uploadStateBean.setActionName(name);
            }
            uploadStateBean.setUploadStateCode(stateCode);
            if (this.actionStateCodeAndStateName != null && this.actionStateCodeAndStateName.size() > 0) {
                uploadStateBean.setUploadStateName(this.actionStateCodeAndStateName.get(stateCode));
            } else {
                uploadStateBean.setUploadStateName(stateName);
            }
            uploadStateList.add(uploadStateBean);
        }
        return uploadStateList;
    }
}

