/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.login.domain.NvwaContext
 *  com.jiuqi.nvwa.login.domain.NvwaContextOrg
 *  com.jiuqi.nvwa.login.domain.NvwaContextUser
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.shiro.SecurityUtils
 */
package com.jiuqi.dc.base.common.env;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.nvwa.login.domain.NvwaContext;
import com.jiuqi.nvwa.login.domain.NvwaContextOrg;
import com.jiuqi.nvwa.login.domain.NvwaContextUser;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import org.apache.shiro.SecurityUtils;

public class EnvCenter {
    public static NvwaContext getContext() {
        NvwaContext nvwaCtx = null;
        Object object = null;
        try {
            object = SecurityUtils.getSubject().getPrincipal();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (StringUtils.isEmpty((String)((String)object))) {
            return null;
        }
        nvwaCtx = (NvwaContext)JsonUtils.readValue((String)((String)object), NvwaContext.class);
        return nvwaCtx;
    }

    public static NvwaContextUser getContextUser() {
        NvwaContext context = EnvCenter.getContext();
        if (!Objects.isNull(context)) {
            return context.getConetxtUser();
        }
        return null;
    }

    public static String getContextUserName() {
        NvwaContextUser contextUser = EnvCenter.getContextUser();
        return contextUser == null ? null : contextUser.getName();
    }

    public static NvwaContextOrg getContextOrg() {
        NvwaContext context = EnvCenter.getContext();
        if (!Objects.isNull(context)) {
            return context.getContextOrg();
        }
        return null;
    }

    public static String getContextOrgCode() {
        NvwaContextOrg contextOrg = EnvCenter.getContextOrg();
        if (!Objects.isNull(contextOrg)) {
            return contextOrg.getCode();
        }
        return null;
    }

    public static String getTenantName() {
        NvwaContext context = EnvCenter.getContext();
        if (!Objects.isNull(context)) {
            return context.getTenantName();
        }
        return null;
    }

    public static Date getLoginDate() {
        NvwaContext context = EnvCenter.getContext();
        if (!Objects.isNull(context)) {
            return context.getLoginDate();
        }
        return null;
    }

    public static String getStandMoneyCode() {
        NvwaContextOrg contextOrg = EnvCenter.getContextOrg();
        if (contextOrg == null || StringUtils.isEmpty((String)contextOrg.getCode())) {
            return null;
        }
        OrgDTO orgDTO = new OrgDTO();
        OrgDTO orgDataDTO = new OrgDTO();
        orgDataDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDataDTO.setCategoryname("MD_ORG");
        OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
        if (orgDataClient == null) {
            return null;
        }
        PageVO pageVo = orgDataClient.list(orgDTO);
        if (CollectionUtils.isEmpty((Collection)pageVo.getRows())) {
            return null;
        }
        return pageVo.getRows().get(0) == null ? null : (String)((OrgDO)pageVo.getRows().get(0)).getValueOf("FINCURR");
    }
}

