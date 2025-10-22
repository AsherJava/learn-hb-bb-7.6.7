/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.service.SingleFormRejectService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.gather.bean.GatherParam
 *  com.jiuqi.nr.data.gather.listener.DataGatherUnitEntityFilterProvider
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.service.SingleFormRejectService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.gather.bean.GatherParam;
import com.jiuqi.nr.data.gather.listener.DataGatherUnitEntityFilterProvider;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSumUnitEntityFilterProvider
implements DataGatherUnitEntityFilterProvider {
    private static final Logger logger = LoggerFactory.getLogger(DataSumUnitEntityFilterProvider.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private SingleFormRejectService singleFormRejectService;
    private ThreadLocal<String> mainDimensionName = new ThreadLocal();
    private ThreadLocal<GatherParam> gatherParam = new ThreadLocal();
    private static final String UPLOAD_DATASUM = "1";

    public void setGatherParam(GatherParam gatherParam) {
        this.gatherParam.set(gatherParam);
    }

    public void setMainDimensionName(String dimensionName) {
        this.mainDimensionName.set(dimensionName);
    }

    public Set<String> getFilterChildren(List<String> childKeys) {
        LinkedHashSet<String> childrenKeyList = new LinkedHashSet<String>();
        if (!this.isUploadAfterDataSum()) {
            return null;
        }
        WorkFlowType flowType = this.workflow.queryStartType(this.gatherParam.get().getFormSchemeKey());
        for (String childKey : childKeys) {
            if (flowType != WorkFlowType.ENTITY) {
                childrenKeyList.add(childKey);
                continue;
            }
            ActionStateBean childUploadState = this.queryUploadState(childKey);
            if (childUploadState == null || !UploadState.UPLOADED.name().equals(childUploadState.getCode()) && !UploadState.CONFIRMED.name().equals(childUploadState.getCode())) continue;
            childrenKeyList.add(childKey);
        }
        return childrenKeyList;
    }

    private ActionStateBean queryUploadState(String entityKey) {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(this.gatherParam.get().getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (null == formScheme) {
            throw new NotFoundFormSchemeException(new String[]{this.gatherParam.get().getFormSchemeKey() + "\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230"});
        }
        IDataentryFlowService dataFlowService = (IDataentryFlowService)BeanUtil.getBean(IDataentryFlowService.class);
        DimensionCollection dimCollection = this.gatherParam.get().getDimensionCollection();
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimCollection.combineWithoutVarDim());
        DataEntryParam dataEntryParam = new DataEntryParam();
        dataEntryParam.setFormSchemeKey(formScheme.getKey());
        dataEntryParam.setDim(this.buildDimensionValueSet(dimensionSet, entityKey));
        return dataFlowService.queryUnitState(dataEntryParam);
    }

    private DimensionValueSet buildDimensionValueSet(Map<String, DimensionValue> dimensionSet, String entityKey) {
        if (this.mainDimensionName.get() != null && dimensionSet.containsKey(this.mainDimensionName.get())) {
            DimensionValue dimensionValue = dimensionSet.get(this.mainDimensionName.get());
            dimensionValue.setValue(entityKey);
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
        return dimensionValueSet;
    }

    public boolean filterCurrentEntity(String entityKeyData) {
        WorkFlowType flowType = this.workflow.queryStartType(this.gatherParam.get().getFormSchemeKey());
        if (flowType != WorkFlowType.ENTITY) {
            return false;
        }
        String value = this.iNvwaSystemOptionService.get("sum-upload-group", "DATASUM_AFTER_UPLOAD");
        boolean dataSumAfterUpload = UPLOAD_DATASUM.equals(value);
        if (dataSumAfterUpload) {
            return false;
        }
        ActionStateBean queryUploadState = this.queryUploadState(entityKeyData);
        if (queryUploadState == null) {
            return false;
        }
        return UploadState.UPLOADED.name().equals(queryUploadState.getCode()) || UploadState.SUBMITED.name().equals(queryUploadState.getCode()) || UploadState.CONFIRMED.name().equals(queryUploadState.getCode());
    }

    public Set<String> getCanGatherForms(DimensionValueSet dimensionSet, String formSchemeKey) {
        return this.singleFormRejectService.getFormKeysByAction(dimensionSet, formSchemeKey, null);
    }

    private boolean isUploadAfterDataSum() {
        String taskDataSumUploaded = this.iTaskOptionController.getValue(this.gatherParam.get().getTaskKey(), "SUMMARY_AFTER_UPLOAD");
        if (null == taskDataSumUploaded) {
            String sysDataSumUploaded = this.iNvwaSystemOptionService.get("sum-upload-group", "SUMMARY_AFTER_UPLOAD");
            if (null == sysDataSumUploaded) {
                return false;
            }
            return Integer.parseInt(sysDataSumUploaded) == 1;
        }
        return UPLOAD_DATASUM.equals(taskDataSumUploaded);
    }
}

