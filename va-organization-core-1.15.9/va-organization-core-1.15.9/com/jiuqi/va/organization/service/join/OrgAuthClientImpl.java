/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgAuthFindDTO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.OrgAuthClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.organization.service.join;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgAuthFindDTO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgAuthClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.service.OrgAuthService;
import java.util.Set;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component(value="vaCoreOrgAuthClientImpl")
public class OrgAuthClientImpl
implements OrgAuthClient {
    private OrgAuthService orgAuthServer;

    public OrgAuthService getOrgAuthServer() {
        if (this.orgAuthServer == null) {
            this.orgAuthServer = (OrgAuthService)ApplicationContextRegister.getBean(OrgAuthService.class);
        }
        return this.orgAuthServer;
    }

    public R existCategoryAuth(OrgDTO orgDTO) {
        return this.getOrgAuthServer().existCategoryAuth(orgDTO);
    }

    public Set<String> findAuth(OrgAuthFindDTO orgAuthFindDTO) {
        return this.getOrgAuthServer().listAuthOrg(orgAuthFindDTO.getUserDO(), orgAuthFindDTO.getOrgDTO());
    }

    public R existDataAuth(OrgAuthFindDTO orgAuthFindDTO) {
        return this.getOrgAuthServer().existDataAuth(orgAuthFindDTO);
    }
}

