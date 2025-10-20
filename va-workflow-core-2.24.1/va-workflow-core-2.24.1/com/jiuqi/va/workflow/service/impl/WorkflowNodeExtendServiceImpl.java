/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.extend.WorkflowGlobalProperty
 *  com.jiuqi.va.extend.WorkflowNodeProperty
 *  com.jiuqi.va.extend.WorkflowNodeStencil
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.extend.WorkflowGlobalProperty;
import com.jiuqi.va.extend.WorkflowNodeProperty;
import com.jiuqi.va.extend.WorkflowNodeStencil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.service.WorkflowNodeExtendService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Lazy(value=false)
@Service
public class WorkflowNodeExtendServiceImpl
implements WorkflowNodeExtendService {
    private Map<String, List<WorkflowNodeStencil>> pluginNodeStencils = new HashMap<String, List<WorkflowNodeStencil>>(16);
    private Map<String, List<WorkflowNodeProperty>> workflowNodePropertyPackageMap = new HashMap<String, List<WorkflowNodeProperty>>(16);
    private Map<String, WorkflowGlobalProperty> workflowGlobalPropertyMap = new HashMap<String, WorkflowGlobalProperty>(16);
    private List<Map<String, Object>> workflowNodePropertyPackageList = new ArrayList<Map<String, Object>>();

    public WorkflowNodeExtendServiceImpl(List<WorkflowNodeStencil> workflowNodeStencils, List<WorkflowNodeProperty> workflowNodeProperties, List<WorkflowGlobalProperty> workflowGlobalProperties) {
        this.stencilGather(workflowNodeStencils);
        this.propertyGather(workflowNodeProperties);
        this.globalPropertyGather(workflowGlobalProperties);
    }

    private void stencilGather(List<WorkflowNodeStencil> workflowNodeStencils) {
        if (CollectionUtils.isEmpty(workflowNodeStencils)) {
            return;
        }
        for (WorkflowNodeStencil workflowNodeStencil : workflowNodeStencils) {
            if (this.pluginNodeStencils.containsKey(workflowNodeStencil.getPluginName())) {
                this.pluginNodeStencils.get(workflowNodeStencil.getPluginName()).add(workflowNodeStencil);
                continue;
            }
            ArrayList<WorkflowNodeStencil> stencils = new ArrayList<WorkflowNodeStencil>();
            stencils.add(workflowNodeStencil);
            this.pluginNodeStencils.put(workflowNodeStencil.getPluginName(), stencils);
        }
    }

    private void propertyGather(List<WorkflowNodeProperty> workflowNodeProperties) {
        if (CollectionUtils.isEmpty(workflowNodeProperties)) {
            return;
        }
        for (WorkflowNodeProperty workflowNodeProperty : workflowNodeProperties) {
            if (this.workflowNodePropertyPackageMap.containsKey(workflowNodeProperty.getPackageName())) {
                this.workflowNodePropertyPackageMap.get(workflowNodeProperty.getPackageName()).add(workflowNodeProperty);
                continue;
            }
            ArrayList<WorkflowNodeProperty> propertyList = new ArrayList<WorkflowNodeProperty>();
            propertyList.add(workflowNodeProperty);
            this.workflowNodePropertyPackageMap.put(workflowNodeProperty.getPackageName(), propertyList);
        }
        for (String packageName : this.workflowNodePropertyPackageMap.keySet()) {
            HashMap<String, Object> propertyPackage = new HashMap<String, Object>(2);
            propertyPackage.put("name", packageName);
            propertyPackage.put("properties", this.workflowNodePropertyPackageMap.get(packageName));
            this.workflowNodePropertyPackageList.add(propertyPackage);
        }
    }

    private void globalPropertyGather(List<WorkflowGlobalProperty> workflowGlobalProperties) {
        if (CollectionUtils.isEmpty(workflowGlobalProperties)) {
            return;
        }
        for (WorkflowGlobalProperty workflowGlobalProperty : workflowGlobalProperties) {
            this.workflowGlobalPropertyMap.put(workflowGlobalProperty.getId(), workflowGlobalProperty);
        }
    }

    @Override
    public R list(TenantDO tenantDO) {
        R result = new R();
        result.put("extendProperties", this.workflowNodePropertyPackageList);
        List pluginNames = (List)tenantDO.getExtInfo("pluainNames");
        ArrayList extendStencils = new ArrayList();
        for (String pluginName : pluginNames) {
            if (!this.pluginNodeStencils.containsKey(pluginName)) continue;
            extendStencils.addAll(this.pluginNodeStencils.get(pluginName));
        }
        result.put("extendStencils", extendStencils);
        result.put("globalExtendProperties", this.workflowGlobalPropertyMap);
        return result;
    }
}

