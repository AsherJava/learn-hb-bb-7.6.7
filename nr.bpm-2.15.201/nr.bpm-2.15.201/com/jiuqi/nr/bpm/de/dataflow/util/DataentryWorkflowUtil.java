/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.systemoptions.WorkflowOptionsResult;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityQueryManager;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataentryWorkflowUtil {
    private static final Logger logger = LoggerFactory.getLogger(DataentryWorkflowUtil.class);
    @Autowired
    private NvwaUserClient nvwaUserClient;
    @Autowired
    private OrgIdentityService orgIdentityService;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private DeEntityQueryManager entityQueryManager;
    @Autowired
    private CustomWorkFolwService workFolwService;
    @Autowired
    private WorkflowSettingService workflowSettingService;

    public boolean isFliter(FormSchemeDefine schemeDefine, String nodeId, String actionCode) {
        boolean forceOpt = this.getForceOpt(schemeDefine, nodeId, actionCode);
        if (forceOpt) {
            boolean ignoreCurrentUnit = WorkflowOptionsResult.ignoreCurrentUnit();
            return ignoreCurrentUnit;
        }
        return forceOpt;
    }

    public Set<String> getFliterUnitKeys(String formSchemeKey, Map<String, DimensionValue> dimensionSet) {
        DimensionValue dimensionValue;
        HashSet<String> fliterUnitKeys = new HashSet<String>();
        List<String> unitKeysByCurrentUser = this.getUnitKeysByCurrentUser();
        List<String> fliterChildrens = this.getChildrens(formSchemeKey, unitKeysByCurrentUser, (dimensionValue = dimensionSet.get("DATATIME")).getValue());
        if (fliterChildrens != null && fliterChildrens.size() > 0) {
            unitKeysByCurrentUser.removeAll(fliterChildrens);
        }
        if (unitKeysByCurrentUser != null && unitKeysByCurrentUser.size() > 0) {
            for (String unitKey : unitKeysByCurrentUser) {
                fliterUnitKeys.add(unitKey);
            }
        }
        return fliterUnitKeys;
    }

    public boolean isEnable(String formSchemeKey, DimensionValueSet dimensionValueSet, boolean forceUpload) {
        Object periodValue;
        if (!forceUpload) {
            return false;
        }
        boolean ignoreCurrentUnit = WorkflowOptionsResult.ignoreCurrentUnit();
        if (ignoreCurrentUnit && (periodValue = dimensionValueSet.getValue("DATATIME")) != null) {
            String period = periodValue.toString();
            List<String> unitKeysByCurrentUser = this.getUnitKeysByCurrentUser();
            List<String> childrens = this.getChildrens(formSchemeKey, unitKeysByCurrentUser, period);
            if (unitKeysByCurrentUser == null || unitKeysByCurrentUser.size() == 0) {
                return true;
            }
            if (childrens == null || childrens.size() == 0) {
                return true;
            }
            unitKeysByCurrentUser.removeAll(childrens);
            String dwMainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
            Object value = dimensionValueSet.getValue(dwMainDimName);
            if (value != null && unitKeysByCurrentUser.contains(value.toString())) {
                return false;
            }
        }
        return true;
    }

    public List<String> getUnitKeysByCurrentUser() {
        ArrayList<String> unitKeys = new ArrayList<String>();
        String identityId = NpContextHolder.getContext().getIdentityId();
        UserDTO userDTO = this.nvwaUserClient.find(identityId);
        if (userDTO != null) {
            String orgCode = userDTO.getOrgCode();
            unitKeys.add(orgCode);
        }
        Collection grantedOrg = this.orgIdentityService.getGrantedOrg(identityId);
        unitKeys.addAll(grantedOrg);
        return unitKeys;
    }

    public List<String> getChildrens(String formSchemeKey, List<String> unitKeys, String period) {
        ArrayList<String> childrens = new ArrayList<String>();
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        EntityViewDefine entityViewDefine = this.dimensionUtil.getDwEntityView(formSchemeKey);
        try {
            IEntityTable entDataQuerySet = this.entityQueryManager.buildFullEntityTable(entityViewDefine, dimensionValueSet, formSchemeKey);
            List allRows = entDataQuerySet.getAllRows();
            if (allRows != null && allRows.size() > 0) {
                for (String unitKey : unitKeys) {
                    List allChildRows = entDataQuerySet.getAllChildRows(unitKey);
                    if (allChildRows == null || allChildRows.size() <= 0) continue;
                    List childs = allChildRows.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                    childrens.addAll(childs);
                }
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return childrens;
    }

    private boolean getForceOpt(FormSchemeDefine schemeDefine, String nodeId, String actionCode) {
        boolean forceUpload = false;
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(schemeDefine.getKey());
        if (defaultWorkflow) {
            TaskFlowsDefine flowsSetting = schemeDefine.getFlowsSetting();
            forceUpload = flowsSetting.isUnitSubmitForForce();
        } else {
            try {
                WorkflowSettingDefine workflowSettingDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(schemeDefine.getKey());
                WorkFlowDefine workFlowDefine = this.workFolwService.getWorkFlowDefineByID(workflowSettingDefine.getWorkflowId(), 1);
                List<WorkFlowAction> workFlowNodeAction = this.workFolwService.getWorkFlowNodeAction(nodeId, workFlowDefine.getLinkid());
                for (WorkFlowAction workFlowAction : workFlowNodeAction) {
                    String exset;
                    ObjectMapper objectMapper;
                    Map readValue;
                    if (workFlowAction == null || workFlowAction.getId() == null || !workFlowAction.getActionCode().equals(actionCode) || (readValue = (Map)(objectMapper = new ObjectMapper()).readValue(exset = workFlowAction.getExset(), (TypeReference)new TypeReference<Map<String, Object>>(){})) == null || !readValue.containsKey("forceUpload")) continue;
                    forceUpload = DataentryWorkflowUtil.convertBoolean(readValue.get("forceUpload"));
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return forceUpload;
    }

    private static Boolean convertBoolean(Object obj) {
        String str;
        Boolean valueOf = false;
        if (obj != null && null != (str = obj.toString()) && !"".equals(str)) {
            valueOf = Boolean.valueOf(str);
            return valueOf;
        }
        return valueOf;
    }
}

