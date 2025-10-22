/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.time.setting.bean.MsgReturn
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.dataflow.serviceImpl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.dataflow.service.IReadOnlyService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.SingleRejectFormActions;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.bpm.upload.utils.I18nUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.time.setting.bean.MsgReturn;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadOnlyServiceImpl
implements IReadOnlyService {
    private static final Logger logger = LoggerFactory.getLogger(ReadOnlyServiceImpl.class);
    @Autowired
    public DeSetTimeProvide deSetTimeProvide;
    @Autowired
    public SingleRejectFormActions singleRejectFormActions;
    @Autowired
    public IWorkflow workflow;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IQueryUploadStateService queryUploadStateService;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Resource
    private WorkflowSettingService settingService;

    @Override
    public ReadOnlyBean readOnly(DataEntryParam dataEntryParam) {
        ReadOnlyBean readOnlyBean = new ReadOnlyBean();
        boolean readOnly = false;
        String msg = "";
        try {
            boolean chinese = I18nUtil.isChinese();
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(dataEntryParam.getFormSchemeKey());
            WorkFlowType startType = this.workflow.queryStartType(dataEntryParam.getFormSchemeKey());
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(dataEntryParam.getFormSchemeKey());
            UploadStateNew uploadState = this.queryUploadStateService.queryUploadState(formScheme.getKey(), dataEntryParam.getDim(), dataEntryParam.getFormKey(), dataEntryParam.getGroupKey());
            if (uploadState != null && uploadState.getTaskId() != null) {
                String taskId = uploadState.getTaskId();
                String preEvent = uploadState.getPreEvent();
                if (defaultWorkflow) {
                    ActionStateBean actionStateBean = uploadState.getActionStateBean();
                    if (actionStateBean.getCode().equals(UploadState.UPLOADED.toString()) || actionStateBean.getCode().equals(UploadState.CONFIRMED.toString()) || actionStateBean.getCode().equals(UploadState.SUBMITED.toString())) {
                        if (WorkFlowType.ENTITY.equals((Object)startType)) {
                            readOnly = this.singleRejectFormActions.reportReadOnly(formScheme, dataEntryParam.getDim(), dataEntryParam.getFormKey());
                            readOnlyBean.setReadOnly(readOnly);
                        }
                        msg = this.i18nDes(actionStateBean.getCode(), chinese);
                        readOnlyBean.setReadOnly(readOnly);
                        readOnlyBean.setMsg(msg);
                    } else {
                        MsgReturn compareSetTime = this.deSetTimeProvide.compareSetTime(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim());
                        if (compareSetTime.isDisabled()) {
                            readOnly = this.singleRejectFormActions.reportReadOnly(formScheme, dataEntryParam.getDim(), dataEntryParam.getFormKey());
                            readOnlyBean.setReadOnly(readOnly);
                            readOnlyBean.setMsg(compareSetTime.getMsg());
                        } else {
                            readOnly = true;
                            msg = "\u53ef\u5199";
                            if (!chinese) {
                                msg = "writable";
                            }
                            readOnlyBean.setReadOnly(readOnly);
                            readOnlyBean.setMsg(msg);
                        }
                    }
                } else {
                    MsgReturn compareSetTime;
                    readOnlyBean = this.readOnlyBean(taskId, preEvent, formScheme);
                    if (readOnlyBean.isReadOnly() && (compareSetTime = this.deSetTimeProvide.compareSetTime(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim())).isDisabled()) {
                        readOnlyBean.setReadOnly(readOnly);
                        readOnlyBean.setMsg(compareSetTime.getMsg());
                    }
                }
            } else {
                MsgReturn compareSetTime;
                readOnly = true;
                msg = "\u53ef\u5199";
                if (!chinese) {
                    msg = "writable";
                }
                if ((compareSetTime = this.deSetTimeProvide.compareSetTime(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim())).isDisabled()) {
                    readOnly = false;
                    msg = compareSetTime.getMsg();
                }
                readOnlyBean.setReadOnly(readOnly);
                readOnlyBean.setMsg(msg);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return readOnlyBean;
    }

    @Override
    public Map<DimensionValueSet, Map<String, Boolean>> batchReadOnlyMap(DataEntryParam dataEntryParam) {
        HashMap<DimensionValueSet, Map<String, Boolean>> readOnlyMap = new HashMap<DimensionValueSet, Map<String, Boolean>>();
        HashMap<String, Boolean> childrenMap = null;
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(dataEntryParam.getFormSchemeKey());
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(dataEntryParam.getFormSchemeKey());
            List<DimensionValueSet> buildDimSets = this.dimensionUtil.buildDimensionValueSets(dataEntryParam.getDim(), dataEntryParam.getFormSchemeKey());
            List<UploadStateNew> uploadState = this.queryUploadStateService.queryUploadStates(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim(), dataEntryParam.getFormKeys(), dataEntryParam.getGroupKeys());
            if (buildDimSets.size() > 0) {
                for (DimensionValueSet dimensionValueSet : buildDimSets) {
                    childrenMap = new HashMap<String, Boolean>();
                    List<String> formKeys = dataEntryParam.getFormKeys();
                    List<String> groupKeys = dataEntryParam.getGroupKeys();
                    List<String> formOrGroupKey = this.workflow.getFormOrGroupKey(dataEntryParam.getFormSchemeKey(), formKeys, groupKeys);
                    for (String key : formOrGroupKey) {
                        childrenMap.put(key, true);
                    }
                    readOnlyMap.put(dimensionValueSet, childrenMap);
                }
                for (UploadStateNew uploadStateNew : uploadState) {
                    MsgReturn compareSetTime;
                    DimensionValueSet entities = uploadStateNew.getEntities();
                    DimensionValueSet dims = this.dimensionUtil.fliterProcessKey(entities, dataEntryParam.getFormSchemeKey());
                    ActionStateBean actionStateBean = uploadStateNew.getActionStateBean();
                    String taskId = uploadStateNew.getTaskId();
                    if (readOnlyMap.get(dims) == null) continue;
                    if (defaultWorkflow) {
                        if (UploadState.UPLOADED.toString().equals(actionStateBean.getCode()) || UploadState.CONFIRMED.toString().equals(actionStateBean.getCode()) || UploadState.SUBMITED.toString().equals(actionStateBean.getCode())) {
                            childrenMap.put(uploadStateNew.getFormId(), false);
                            readOnlyMap.put(dims, childrenMap);
                            continue;
                        }
                        MsgReturn compareSetTime2 = this.deSetTimeProvide.compareSetTime(dataEntryParam.getFormSchemeKey(), dims);
                        if (compareSetTime2.isDisabled()) {
                            childrenMap.put(uploadStateNew.getFormId(), false);
                            readOnlyMap.put(dims, childrenMap);
                            continue;
                        }
                        childrenMap.put(uploadStateNew.getFormId(), true);
                        readOnlyMap.put(dims, childrenMap);
                        continue;
                    }
                    ReadOnlyBean readOnlyBean = this.readOnlyBean(taskId, uploadStateNew.getPreEvent(), formScheme);
                    if (readOnlyBean.isReadOnly() && (compareSetTime = this.deSetTimeProvide.compareSetTime(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim())).isDisabled()) {
                        readOnlyBean.setReadOnly(false);
                        readOnlyBean.setMsg(compareSetTime.getMsg());
                    }
                    childrenMap.put(uploadStateNew.getFormId(), readOnlyBean.isReadOnly());
                    readOnlyMap.put(entities, childrenMap);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return readOnlyMap;
    }

    public ReadOnlyBean readOnlyBean(String taskId, String actionCode, FormSchemeDefine formScheme) {
        ReadOnlyBean readOnlyBean = new ReadOnlyBean();
        boolean readOnly = false;
        String msg = "";
        boolean chinese = I18nUtil.isChinese();
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(formScheme.getKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        if ("cus_upload".equals(actionCode)) {
            readOnly = false;
            msg = "\u6570\u636e\u5df2\u4e0a\u62a5\u4e0d\u53ef\u7f16\u8f91";
            if (!chinese) {
                msg = "Data has been uploaded and cannot be edited";
            }
            readOnlyBean.setReadOnly(readOnly);
            readOnlyBean.setMsg(msg);
        } else if ("cus_confirm".equals(actionCode)) {
            readOnly = false;
            msg = "\u6570\u636e\u5df2\u786e\u8ba4\u4e0d\u53ef\u7f16\u8f91";
            if (!chinese) {
                msg = "Data has been confirmed and cannot be edited";
            }
            readOnlyBean.setReadOnly(readOnly);
            readOnlyBean.setMsg(msg);
        } else if ("cus_submit".equals(actionCode)) {
            readOnly = false;
            msg = "\u6570\u636e\u5df2\u9001\u5ba1\u4e0d\u53ef\u7f16\u8f91";
            if (!chinese) {
                msg = "Data has been submited and cannot be edited";
            }
            readOnlyBean.setReadOnly(readOnly);
            readOnlyBean.setMsg(msg);
        } else if ("start".equals(actionCode)) {
            WorkFlowNodeSet workFlowNode = this.customWorkFolwService.getWorkFlowNodeSetByID(taskId, workFlowDefine.getLinkid());
            if (workFlowNode != null) {
                readOnly = workFlowNode.isWritable();
                readOnlyBean.setReadOnly(true);
                readOnlyBean.setMsg(msg);
            }
        } else {
            WorkFlowNodeSet workFlowNode = this.customWorkFolwService.getWorkFlowNodeSetByID(taskId, workFlowDefine.getLinkid());
            readOnly = workFlowNode.isWritable();
            readOnlyBean.setReadOnly(readOnly);
            readOnlyBean.setMsg(msg);
            if (!readOnly) {
                if (chinese) {
                    readOnlyBean.setMsg("\u6d41\u7a0b\u5c5e\u6027\u4e0d\u53ef\u8bfb");
                } else {
                    readOnlyBean.setMsg("Process properties are not readable");
                }
            }
        }
        return readOnlyBean;
    }

    private String i18nDes(String code, boolean chinese) {
        String msg = "";
        if (chinese) {
            if (code.equals(UploadState.UPLOADED.toString())) {
                msg = "\u6570\u636e\u5df2\u4e0a\u62a5\u4e0d\u53ef\u7f16\u8f91";
            } else if (code.equals(UploadState.CONFIRMED.toString())) {
                msg = "\u6570\u636e\u5df2\u786e\u8ba4\u4e0d\u53ef\u7f16\u8f91";
            } else if (code.equals(UploadState.SUBMITED.toString())) {
                msg = "\u6570\u636e\u5df2\u9001\u5ba1\u4e0d\u53ef\u7f16\u8f91";
            }
        } else if (code.equals(UploadState.UPLOADED.toString())) {
            msg = "Data has been uploaded and cannot be edited";
        } else if (code.equals(UploadState.CONFIRMED.toString())) {
            msg = "Data has been confirmed and cannot be edited";
        } else if (code.equals(UploadState.SUBMITED.toString())) {
            msg = "Data has been submited and cannot be edited";
        }
        return msg;
    }

    @Override
    public List<ReadOnlyBean> batchReadOnly(DataEntryParam dataEntryParam) {
        ArrayList<ReadOnlyBean> readOnly = new ArrayList<ReadOnlyBean>();
        WorkFlowType startType = this.workflow.queryStartType(dataEntryParam.getFormSchemeKey());
        Map<DimensionValueSet, Map<String, Boolean>> batchReadOnlyMap = this.batchReadOnlyMap(dataEntryParam);
        String mainDimName = this.dimensionUtil.getDwMainDimName(dataEntryParam.getFormSchemeKey());
        for (Map.Entry<DimensionValueSet, Map<String, Boolean>> readOnlyMap : batchReadOnlyMap.entrySet()) {
            Map<String, Boolean> value;
            DimensionValueSet dim = readOnlyMap.getKey();
            if (WorkFlowType.ENTITY.equals((Object)startType)) {
                value = readOnlyMap.getValue();
                String name = (String)dim.getValue(mainDimName);
                ReadOnlyBean readOnlyBean = new ReadOnlyBean();
                readOnlyBean.setReadOnly(value.get(name));
                readOnlyBean.setDim(dim);
                readOnly.add(readOnlyBean);
                continue;
            }
            if (WorkFlowType.FORM.equals((Object)startType)) {
                value = readOnlyMap.getValue();
                for (Map.Entry<String, Boolean> va : value.entrySet()) {
                    ReadOnlyBean readOnlyForm = new ReadOnlyBean();
                    readOnlyForm.setDim(dim);
                    readOnlyForm.setFormKey(va.getKey());
                    readOnlyForm.setReadOnly(va.getValue());
                    readOnly.add(readOnlyForm);
                }
                continue;
            }
            if (!WorkFlowType.GROUP.equals((Object)startType)) continue;
            value = readOnlyMap.getValue();
            for (Map.Entry<String, Boolean> va : value.entrySet()) {
                ReadOnlyBean readOnlyGroup = new ReadOnlyBean();
                readOnlyGroup.setDim(dim);
                readOnlyGroup.setGroupKey(va.getKey());
                readOnlyGroup.setReadOnly(va.getValue());
                readOnly.add(readOnlyGroup);
            }
        }
        return readOnly;
    }

    @Override
    public ReadOnlyBean readOnly(DataEntryParam dataEntryParam, UploadStateNew uploadState) {
        ReadOnlyBean readOnlyBean = new ReadOnlyBean();
        boolean readOnly = false;
        String msg = "";
        try {
            boolean chinese = I18nUtil.isChinese();
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(dataEntryParam.getFormSchemeKey());
            WorkFlowType startType = this.workflow.queryStartType(dataEntryParam.getFormSchemeKey());
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(dataEntryParam.getFormSchemeKey());
            if (uploadState != null && uploadState.getTaskId() != null) {
                String taskId = uploadState.getTaskId();
                String preEvent = uploadState.getPreEvent();
                if (defaultWorkflow) {
                    ActionStateBean actionStateBean = uploadState.getActionStateBean();
                    if (actionStateBean.getCode().equals(UploadState.UPLOADED.toString()) || actionStateBean.getCode().equals(UploadState.CONFIRMED.toString()) || actionStateBean.getCode().equals(UploadState.SUBMITED.toString())) {
                        if (WorkFlowType.ENTITY.equals((Object)startType)) {
                            readOnly = this.singleRejectFormActions.reportReadOnly(formScheme, dataEntryParam.getDim(), dataEntryParam.getFormKey());
                            readOnlyBean.setReadOnly(readOnly);
                        }
                        msg = this.i18nDes(actionStateBean.getCode(), chinese);
                        readOnlyBean.setReadOnly(readOnly);
                        readOnlyBean.setMsg(msg);
                    } else {
                        MsgReturn compareSetTime = this.deSetTimeProvide.compareSetTime(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim());
                        if (compareSetTime.isDisabled()) {
                            readOnly = this.singleRejectFormActions.reportReadOnly(formScheme, dataEntryParam.getDim(), dataEntryParam.getFormKey());
                            readOnlyBean.setReadOnly(readOnly);
                            readOnlyBean.setMsg(compareSetTime.getMsg());
                        } else {
                            readOnly = true;
                            msg = "\u53ef\u5199";
                            if (!chinese) {
                                msg = "writable";
                            }
                            readOnlyBean.setReadOnly(readOnly);
                            readOnlyBean.setMsg(msg);
                        }
                    }
                } else {
                    MsgReturn compareSetTime;
                    readOnlyBean = this.readOnlyBean(taskId, preEvent, formScheme);
                    if (readOnlyBean.isReadOnly() && (compareSetTime = this.deSetTimeProvide.compareSetTime(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim())).isDisabled()) {
                        readOnlyBean.setReadOnly(readOnly);
                        readOnlyBean.setMsg(compareSetTime.getMsg());
                    }
                }
            } else {
                MsgReturn compareSetTime;
                readOnly = true;
                msg = "\u53ef\u5199";
                if (!chinese) {
                    msg = "writable";
                }
                if ((compareSetTime = this.deSetTimeProvide.compareSetTime(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim())).isDisabled()) {
                    readOnly = false;
                    msg = compareSetTime.getMsg();
                }
                readOnlyBean.setReadOnly(readOnly);
                readOnlyBean.setMsg(msg);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return readOnlyBean;
    }
}

