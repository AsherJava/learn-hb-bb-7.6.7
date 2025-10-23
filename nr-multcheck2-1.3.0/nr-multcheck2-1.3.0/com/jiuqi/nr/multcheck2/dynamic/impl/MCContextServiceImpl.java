/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.multcheck2.dynamic.impl;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.multcheck2.dynamic.IMCContextService;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.service.IMCOrgService;
import com.jiuqi.nr.multcheck2.service.dto.MCOrgTreeDTO;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MCContextServiceImpl
implements IMCContextService {
    @Autowired
    private IMCDimService dimService;
    @Autowired
    private IMCOrgService orgService;
    @Autowired
    private IMCEnvService envService;

    @Override
    public List<String> getDynamicFieldsByTask(String task) {
        return this.dimService.getDynamicFieldsByTask(task);
    }

    @Override
    public List<String> getDynamicDimNamesForPage(String dataSchemeKey) {
        return this.dimService.getDynamicDimNamesForPage(dataSchemeKey);
    }

    @Override
    public IEntityTable getTreeEntityTable(String taskKey, String period, String org, String formSchemeKey) throws Exception {
        return this.orgService.getTreeEntityTable(taskKey, period, org, formSchemeKey);
    }

    @Override
    public IEntityTable getTreeEntityTable(String taskKey, String period, String org, String formSchemeKey, List<String> orgList) throws Exception {
        return this.orgService.getTreeEntityTable(taskKey, period, org, orgList, formSchemeKey);
    }

    @Override
    public MCOrgTreeDTO getOrgTreeByTaskPeriodOrg(String taskKey, String period, String org, String formSchemeKey, List<String> orgList) throws Exception {
        return this.orgService.getOrgTreeByTaskPeriodOrg(taskKey, period, org, orgList, formSchemeKey);
    }

    @Override
    public List<MCLabel> getOrgLabels(String taskKey, String period, String org, List<String> orgList) throws Exception {
        return this.orgService.getOrgLabels(taskKey, period, org, orgList);
    }

    @Override
    public DimensionCollection buildDimensionCollection(String task, String period, List<String> orgs, Map<String, DimensionValue> dims) throws Exception {
        return this.envService.buildDimensionCollection(task, period, orgs, dims);
    }
}

