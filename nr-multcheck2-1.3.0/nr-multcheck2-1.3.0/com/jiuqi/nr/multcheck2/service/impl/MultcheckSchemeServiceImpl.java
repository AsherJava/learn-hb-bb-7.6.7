/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.OrgType;
import com.jiuqi.nr.multcheck2.common.SchemeType;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.service.IMultcheckSchemeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Deprecated
public class MultcheckSchemeServiceImpl
implements IMultcheckSchemeService {
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private IMCEnvService envService;

    @Override
    public List<MultcheckScheme> getSchemeByForm(String formScheme) {
        return this.schemeService.getSchemeByForm(formScheme);
    }

    @Override
    @Transactional
    public void createDefaultScheme(String task, String formScheme) {
        MultcheckScheme scheme = new MultcheckScheme();
        scheme.setKey(UUID.randomUUID().toString());
        scheme.setCode(OrderGenerator.newOrder());
        scheme.setTitle("\u9ed8\u8ba4\u5ba1\u6838\u65b9\u6848");
        scheme.setTask(task);
        scheme.setFormScheme(formScheme);
        scheme.setOrgType(OrgType.ALL);
        scheme.setType(SchemeType.COMMON);
        scheme.setOrder(OrderGenerator.newOrder());
        this.schemeService.addScheme(scheme);
        this.schemeService.addItemsByScheme(scheme);
    }

    @Override
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
}

