/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.engine.gather.GatherEntityFilterProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataentry.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.engine.gather.GatherEntityFilterProvider;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSumUnitEntityFilterProvider
implements GatherEntityFilterProvider {
    private static final Logger logger = LoggerFactory.getLogger(DataSumUnitEntityFilterProvider.class);
    private IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
    private EntityViewData targetEntityInfo;
    private IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
    private IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
    private BatchDataSumInfo batchDataSumInfo;
    private INvwaSystemOptionService iNvwaSystemOptionService;
    private IWorkflow workflow;

    public DataSumUnitEntityFilterProvider(EntityViewData targetEntityInfo, BatchDataSumInfo batchDataSumInfo) {
        this.targetEntityInfo = targetEntityInfo;
        this.batchDataSumInfo = batchDataSumInfo;
        this.iNvwaSystemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        this.workflow = (IWorkflow)BeanUtil.getBean(IWorkflow.class);
    }

    public Set<String> getFilterChildren(List<String> childKeys) {
        LinkedHashSet<String> childrenKeyList = new LinkedHashSet<String>();
        if (!this.batchDataSumInfo.isUploadAfterDataSum()) {
            return null;
        }
        WorkFlowType flowType = this.workflow.queryStartType(this.batchDataSumInfo.getContext().getFormSchemeKey());
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
            formScheme = this.runtimeView.getFormScheme(this.batchDataSumInfo.getContext().getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (null == formScheme) {
            throw new NotFoundFormSchemeException(new String[]{this.batchDataSumInfo.getContext().getFormSchemeKey() + "\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230"});
        }
        IDataentryFlowService dataFlowService = (IDataentryFlowService)BeanUtil.getBean(IDataentryFlowService.class);
        Map dimensionSet = this.batchDataSumInfo.getContext().getDimensionSet();
        DataEntryParam dataEntryParam = new DataEntryParam();
        dataEntryParam.setFormSchemeKey(formScheme.getKey());
        dataEntryParam.setDim(this.buildDimensionValueSet(dimensionSet, entityKey));
        return dataFlowService.queryUnitState(dataEntryParam);
    }

    private DimensionValueSet buildDimensionValueSet(Map<String, DimensionValue> dimensionSet, String entityKey) {
        if (this.targetEntityInfo != null && dimensionSet.containsKey(this.targetEntityInfo.getDimensionName())) {
            DimensionValue dimensionValue = dimensionSet.get(this.targetEntityInfo.getDimensionName());
            dimensionValue.setValue(entityKey);
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
        return dimensionValueSet;
    }

    public boolean filterCurrentEntity(String entityKeyData) {
        WorkFlowType flowType = this.workflow.queryStartType(this.batchDataSumInfo.getContext().getFormSchemeKey());
        if (flowType != WorkFlowType.ENTITY) {
            return false;
        }
        String value = this.iNvwaSystemOptionService.get("sum-upload-group", "DATASUM_AFTER_UPLOAD");
        boolean dataSumAfterUpload = "1".equals(value);
        if (dataSumAfterUpload) {
            return false;
        }
        ActionStateBean queryUploadState = this.queryUploadState(entityKeyData);
        if (queryUploadState == null) {
            return false;
        }
        return UploadState.UPLOADED.name().equals(queryUploadState.getCode()) || UploadState.SUBMITED.name().equals(queryUploadState.getCode()) || UploadState.CONFIRMED.name().equals(queryUploadState.getCode());
    }
}

