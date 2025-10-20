/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.gcreport.nr.impl.uploadstate.util.intern;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FormUploadState {
    private static final Logger logger = LoggerFactory.getLogger(FormUploadState.class);

    FormUploadState() {
    }

    public static UploadState queryUploadState(DimensionParamsVO param, String orgId, String formId) {
        ArrayList<String> formIds = new ArrayList<String>();
        formIds.add(formId);
        List<UploadState> uploadState = FormUploadState.queryUploadState(param, orgId, formIds);
        if (CollectionUtils.isEmpty(uploadState)) {
            return UploadState.ORIGINAL;
        }
        return uploadState.get(0);
    }

    public static List<UploadState> queryUploadState(DimensionParamsVO param, String orgId, List<String> formIds) {
        FormSchemeDefine formScheme;
        IRunTimeViewController authViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        try {
            formScheme = authViewController.getFormScheme(param.getSchemeId());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5931\u8d25");
        }
        DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)orgId, (Object)param.getPeriodStr(), (Object)param.getCurrency(), (Object)param.getOrgTypeId(), (String)param.getSelectAdjustCode(), (String)param.getTaskId());
        IBatchQueryUploadStateService batchQueryUploadStateService = (IBatchQueryUploadStateService)SpringContextUtils.getBean(IBatchQueryUploadStateService.class);
        IWorkflow iWorkflow = (IWorkflow)SpringContextUtils.getBean(IWorkflow.class);
        WorkFlowType workFlowType = iWorkflow.queryStartType(formScheme.getKey());
        List uploadStateNewList = new ArrayList();
        uploadStateNewList = WorkFlowType.ENTITY.equals((Object)workFlowType) ? batchQueryUploadStateService.queryUploadStateNew(formScheme, dimensionValueSet, null) : batchQueryUploadStateService.queryUploadStateNew(formScheme, dimensionValueSet, Collections.singletonList("11111111-1111-1111-1111-111111111111"));
        if (CollectionUtils.isEmpty(uploadStateNewList)) {
            return formIds.stream().map(item -> UploadState.ORIGINAL).collect(Collectors.toList());
        }
        Map<String, UploadState> formId2UploadStateMap = uploadStateNewList.stream().collect(Collectors.toMap(UploadStateNew::getFormId, uploadStateNew -> {
            ActionStateBean actionStateBean = uploadStateNew.getActionStateBean();
            if (null == actionStateBean) {
                return UploadState.ORIGINAL;
            }
            return UploadState.valueOf((String)actionStateBean.getCode());
        }));
        if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            uploadStateNewList.forEach(item -> {
                try {
                    List formDefines = iRunTimeViewController.getAllFormsInGroup(item.getFormId());
                    if (!CollectionUtils.isEmpty((Collection)formDefines)) {
                        formDefines.forEach(formDefine -> formId2UploadStateMap.put(formDefine.getKey(), (UploadState)formId2UploadStateMap.get(item.getFormId())));
                    }
                }
                catch (Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            });
        }
        List<UploadState> uploadStateList = formIds.stream().map(formId -> {
            UploadState uploadState = (UploadState)formId2UploadStateMap.get(formId);
            return null == uploadState ? UploadState.ORIGINAL : uploadState;
        }).collect(Collectors.toList());
        return uploadStateList;
    }
}

