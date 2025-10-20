/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.gcreport.nr.impl.uploadstate.util;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;

public class UploadStateTool {
    private static UploadStateTool tool;

    public static UploadStateTool getInstance() {
        if (null == tool) {
            tool = new UploadStateTool();
        }
        return tool;
    }

    public ReadWriteAccessDesc writeableNoAuth(Object paramsVO, String orgId) {
        DimensionParamsVO newParamsVO = new DimensionParamsVO();
        BeanUtils.copyProperties(paramsVO, newParamsVO);
        newParamsVO.setOrgId(orgId);
        return this.writeable(this.getUploadSate(newParamsVO, newParamsVO.getOrgId()));
    }

    public ReadWriteAccessDesc writeable(Object paramsVO, String orgId) {
        DimensionParamsVO newParamsVO = new DimensionParamsVO();
        BeanUtils.copyProperties(paramsVO, newParamsVO);
        newParamsVO.setOrgId(orgId);
        return this.writeable(newParamsVO);
    }

    public ReadWriteAccessDesc writeable(String formSchemeId, DimensionValueSet dimensionValueSet) {
        return this.writeable(this.queryUnitUploadState(formSchemeId, dimensionValueSet));
    }

    public ReadWriteAccessDesc writeable(DimensionParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCacheVO org = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgVersionType(), (GcAuthorityType)GcAuthorityType.WRITE, (YearPeriodObject)yp).getOrgByCode(queryParamsVO.getOrgId());
        if (null == org) {
            return new ReadWriteAccessDesc(Boolean.valueOf(false), GcI18nUtil.getMessage((String)"gc.org.nr.uploadstatetool.writeable.error"));
        }
        if (StringUtils.isEmpty((String)queryParamsVO.getPeriodStr()) || StringUtils.isEmpty((String)queryParamsVO.getSchemeId())) {
            return new ReadWriteAccessDesc(Boolean.valueOf(true), "");
        }
        return this.writeable(this.getUploadSate(queryParamsVO, queryParamsVO.getOrgId()));
    }

    protected ReadWriteAccessDesc writeable(UploadState status) {
        Boolean writeable = true;
        String unwriteableDesc = "";
        if (status == UploadState.SUBMITED) {
            writeable = false;
            unwriteableDesc = GcI18nUtil.getMessage((String)"gc.org.nr.uploadstatetool.submited.error");
        }
        if (status == UploadState.UPLOADED) {
            writeable = false;
            unwriteableDesc = GcI18nUtil.getMessage((String)"gc.org.nr.uploadstatetool.uploaded.error");
        }
        if (status == UploadState.CONFIRMED) {
            writeable = false;
            unwriteableDesc = GcI18nUtil.getMessage((String)"gc.org.nr.uploadstatetool.confirmed.error");
        }
        return new ReadWriteAccessDesc(writeable, unwriteableDesc);
    }

    public UploadState getUploadSate(Object paramsVO, String orgId) {
        DimensionParamsVO newParamsVO = new DimensionParamsVO();
        BeanUtils.copyProperties(paramsVO, newParamsVO);
        return this.getUploadSate(newParamsVO, orgId);
    }

    public UploadState getUploadSate(DimensionParamsVO paramsVO, String orgId) {
        DimensionValueSet dimensionValueSet = this.getDimensionValueMap(paramsVO, orgId);
        return this.queryUnitUploadState(paramsVO.getSchemeId(), dimensionValueSet);
    }

    public ActionState getActionState(DimensionParamsVO paramsVO, String orgId) {
        DimensionValueSet dimensionValueSet = this.getDimensionValueMap(paramsVO, orgId);
        ActionState actionState = this.getActionState(paramsVO.getSchemeId(), dimensionValueSet);
        return actionState;
    }

    public Map<String, UploadState> getUploadSates(Object paramsVO, List<String> orgIds) {
        DimensionParamsVO newParamsVO = new DimensionParamsVO();
        BeanUtils.copyProperties(paramsVO, newParamsVO);
        return this.getUploadSates(newParamsVO, orgIds);
    }

    public Map<String, UploadState> getUploadSates(DimensionParamsVO paramsVO, List<String> orgIds) {
        DimensionValueSet dimensionValueSet = this.getDimensionValueMap(paramsVO, null);
        dimensionValueSet.setValue("MD_ORG", orgIds);
        Map<DimensionValueSet, ActionStateBean> statusMap = this.getTreeWorkflowUploadState(dimensionValueSet, paramsVO.getSchemeId());
        HashMap<String, UploadState> orgId2StateMap = new HashMap<String, UploadState>();
        for (Map.Entry<DimensionValueSet, ActionStateBean> entry : statusMap.entrySet()) {
            DimensionValueSet dim = entry.getKey();
            String orgId = (String)dim.getValue("MD_ORG");
            ActionStateBean uploadState = entry.getValue();
            if (null == uploadState || StringUtils.isEmpty((String)uploadState.getCode())) continue;
            orgId2StateMap.put(orgId, UploadState.valueOf((String)uploadState.getCode()));
        }
        return orgId2StateMap;
    }

    public Map<DimensionValueSet, ActionStateBean> getTreeWorkflowUploadState(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        String formKey = "11111111-1111-1111-1111-111111111111";
        IQueryUploadStateService queryUploadStateService = (IQueryUploadStateService)BeanUtil.getBean(IQueryUploadStateService.class);
        return queryUploadStateService.queryUploadStateMap(formSchemeKey, dimensionValueSet, formKey, formKey);
    }

    private DimensionValueSet getDimensionValueMap(DimensionParamsVO paramsVO, String orgIds) {
        return DimensionUtils.generateDimSet((Object)orgIds, (Object)paramsVO.getPeriodStr(), (Object)paramsVO.getCurrency(), (Object)paramsVO.getOrgTypeId(), (String)paramsVO.getSelectAdjustCode(), (String)paramsVO.getTaskId());
    }

    private UploadState queryUnitUploadState(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        ActionState uploadState = this.getActionState(formSchemeKey, dimensionValueSet);
        if (null == uploadState || null == uploadState.getUnitState() || StringUtils.isEmpty((String)uploadState.getUnitState().getCode())) {
            return UploadState.ORIGINAL;
        }
        return UploadState.valueOf((String)uploadState.getUnitState().getCode());
    }

    public ActionState getActionState(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        DataEntryParam dataEntryParam = new DataEntryParam();
        dataEntryParam.setDim(dimensionValueSet);
        dataEntryParam.setFormSchemeKey(formSchemeKey);
        IDataentryFlowService dataFlowService = (IDataentryFlowService)BeanUtil.getBean(IDataentryFlowService.class);
        IFormConditionService formConditionService = (IFormConditionService)BeanUtil.getBean(IFormConditionService.class);
        IConditionCache conditionCache = formConditionService.getConditionForms(dimensionValueSet, formSchemeKey);
        dataEntryParam.setFormKeys(conditionCache.getSeeForms(dimensionValueSet));
        dataEntryParam.setGroupKeys(conditionCache.getSeeFormGroups(dimensionValueSet));
        ActionState actionState = dataFlowService.queryState(dataEntryParam);
        return actionState;
    }
}

