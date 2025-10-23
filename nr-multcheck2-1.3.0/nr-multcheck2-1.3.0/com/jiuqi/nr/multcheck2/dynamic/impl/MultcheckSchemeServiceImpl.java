/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.multcheck2.dynamic.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.multcheck2.bean.MCSchemeParam;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.OrgType;
import com.jiuqi.nr.multcheck2.common.SchemeType;
import com.jiuqi.nr.multcheck2.dynamic.IMultcheckSchemeService;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.service.IMCParamService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MultcheckSchemeServiceImpl
implements IMultcheckSchemeService {
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private IMCEnvService envService;
    @Autowired
    private IMCParamService mcParamService;

    @Override
    public List<MultcheckScheme> getSchemeByForm(String formScheme, String org) {
        return this.schemeService.getSchemeByFSAndOrg(formScheme, org);
    }

    @Override
    @Transactional
    public void createDefaultScheme(String task, String formScheme, String org) {
        MultcheckScheme scheme = new MultcheckScheme();
        scheme.setKey(UUID.randomUUID().toString());
        scheme.setCode(OrderGenerator.newOrder());
        scheme.setTitle("\u9ed8\u8ba4\u5ba1\u6838\u65b9\u6848");
        scheme.setTask(task);
        scheme.setFormScheme(formScheme);
        scheme.setOrgType(OrgType.ALL);
        scheme.setType(SchemeType.COMMON);
        scheme.setOrder(OrderGenerator.newOrder());
        scheme.setOrg(org);
        this.schemeService.addScheme(scheme);
        this.schemeService.addItemsByScheme(scheme);
        this.envService.initReportDim(scheme.getKey(), task);
    }

    @Override
    public List<MultcheckScheme> getAllMCSchemes() {
        return this.schemeService.getAllSchemes();
    }

    @Override
    public List<MultcheckScheme> getMCSchemes(String formScheme) {
        return this.mcParamService.query(formScheme);
    }

    @Override
    public List<MCSchemeParam> getMCSchemeParams(String formScheme) {
        return this.mcParamService.exportMCSchemeParams(formScheme);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void deleteMCSchemeParams(List<String> keys) {
        this.mcParamService.batchDeleteSchemeByKeys(keys);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void addMCSchemeParams(List<MCSchemeParam> params) {
        this.mcParamService.batchAddMCSParams(params);
    }
}

