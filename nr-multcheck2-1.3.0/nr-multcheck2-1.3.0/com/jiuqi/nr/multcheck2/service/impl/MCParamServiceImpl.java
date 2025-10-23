/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.nr.multcheck2.bean.MCSchemeParam;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg;
import com.jiuqi.nr.multcheck2.common.OrgType;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.service.IMCParamService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MCParamServiceImpl
implements IMCParamService {
    private static final Logger logger = LoggerFactory.getLogger(MCParamServiceImpl.class);
    @Autowired
    IMCSchemeService schemeService;
    @Autowired
    private IMCEnvService mcEnvService;

    @Override
    public List<MCSchemeParam> exportMCSchemeParams(String formScheme) {
        ArrayList<MCSchemeParam> list = new ArrayList<MCSchemeParam>();
        List<MultcheckScheme> schemes = this.schemeService.getSchemeByForm(formScheme);
        for (MultcheckScheme scheme : schemes) {
            MCSchemeParam param = new MCSchemeParam();
            param.setMcScheme(scheme);
            List<MultcheckSchemeOrg> mcsOrgList = this.schemeService.getOrgListByScheme(scheme.getKey());
            param.setMcsOrgList(mcsOrgList);
            List<MultcheckItem> mcItemList = this.schemeService.getItemList(scheme.getKey());
            param.setMcItemList(mcItemList);
            list.add(param);
        }
        return list;
    }

    @Override
    public List<MultcheckScheme> query(String formScheme) {
        return this.schemeService.getSchemeByForm(formScheme);
    }

    @Override
    public void batchDeleteSchemeByKeys(List<String> keys) {
        for (String key : keys) {
            this.schemeService.deleteSchemeByKey(key);
        }
    }

    @Override
    public void deleteSchemeByFormScheme(String formScheme) {
        this.schemeService.deleteSchemeByFormScheme(formScheme);
    }

    @Override
    public void batchAddMCSParams(List<MCSchemeParam> params) {
        for (MCSchemeParam param : params) {
            MultcheckScheme mcScheme = param.getMcScheme();
            List<MultcheckScheme> schemes = this.schemeService.getByFSAndCode(mcScheme.getFormScheme(), mcScheme.getCode());
            if (!CollectionUtils.isEmpty(schemes)) {
                logger.warn("\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u53c2\u6570\u5bfc\u5165\uff0c\u65b0\u589e\u5b58\u5728\u65b9\u6848\u6807\u8bc6\u76f8\u540c\u7684\u6570\u636e\uff0c\u65b9\u6848key\uff1a{} \uff0c\u65b9\u6848code\uff1a{} \uff0c\u62a5\u8868\u65b9\u6848\uff1a{}", mcScheme.getKey(), mcScheme.getCode(), mcScheme.getFormScheme());
                continue;
            }
            this.schemeService.addScheme(param.getMcScheme());
            if (param.getMcScheme().getOrgType() == OrgType.SELECT) {
                this.schemeService.batchAddOrg(param.getMcsOrgList());
            }
            this.schemeService.batchAddItem(this.getProviderItems(param), param.getMcScheme().getKey());
        }
    }

    @Override
    public void batchModifyMCSParams(List<MCSchemeParam> params) {
        for (MCSchemeParam param : params) {
            MultcheckScheme mcScheme = param.getMcScheme();
            String schemeKey = mcScheme.getKey();
            MultcheckScheme sourceScheme = this.schemeService.getSchemeByKey(schemeKey);
            List<MultcheckScheme> schemes = this.schemeService.getByFSAndCode(mcScheme.getFormScheme(), mcScheme.getCode());
            if (CollectionUtils.isEmpty(schemes) || schemes.size() == 1 && sourceScheme.getKey().equals(schemes.get(0).getKey())) {
                this.schemeService.modifyScheme(mcScheme);
            } else {
                logger.warn("\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u53c2\u6570\u5bfc\u5165\uff0c\u4fee\u6539\u5b58\u5728\u65b9\u6848\u6807\u8bc6\u76f8\u540c\u7684\u6570\u636e\uff0c\u65b9\u6848key\uff1a{} \uff0c\u65b9\u6848code\uff1a{} \uff0c\u62a5\u8868\u65b9\u6848\uff1a{}", mcScheme.getKey(), mcScheme.getCode(), mcScheme.getFormScheme());
                mcScheme.setCode(sourceScheme.getCode());
                this.schemeService.modifyScheme(mcScheme);
            }
            if (mcScheme.getOrgType() == OrgType.SELECT) {
                List<MultcheckSchemeOrg> sourceOrg = this.schemeService.getOrgListByScheme(schemeKey);
                if (!CollectionUtils.isEmpty(sourceOrg)) {
                    this.schemeService.deleteOrgByScheme(schemeKey);
                }
                this.schemeService.batchAddOrg(param.getMcsOrgList());
            }
            this.schemeService.batchAddItem(this.getProviderItems(param), schemeKey);
        }
    }

    private List<MultcheckItem> getProviderItems(MCSchemeParam param) {
        ArrayList<MultcheckItem> list = new ArrayList<MultcheckItem>();
        for (MultcheckItem item : param.getMcItemList()) {
            IMultcheckItemProvider provider = this.mcEnvService.getProvider(item.getType());
            if (provider == null) continue;
            list.add(item);
        }
        return list;
    }
}

