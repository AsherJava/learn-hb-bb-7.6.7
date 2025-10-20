/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.core.utils.AES128EncryptUtil
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nvwa.authority.user.service.NvwaUserService
 */
package com.jiuqi.common.systemparam.executor;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.systemparam.consts.EntSystemParamInitConst;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.core.utils.AES128EncryptUtil;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nvwa.authority.user.service.NvwaUserService;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class EntUserInitExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String INIT_USER_INFO_FILE_PATH = EntSystemParamInitConst.SYSTEM_PARAM_BASE_URL + "initUserInfo.xlsx";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        NpContext context = NpContextHolder.getContext();
        try {
            if (context == null || StringUtils.isEmpty((String)context.getIdentityId())) {
                this.initUserInfo();
            }
            ClassPathResource userResource = new ClassPathResource(this.INIT_USER_INFO_FILE_PATH);
            NvwaUserService userService = (NvwaUserService)SpringContextUtils.getBean(NvwaUserService.class);
            String initPwd = AES128EncryptUtil.encrypt((String)"Abcd@1234");
            String result = userService.importUser(userResource.getInputStream(), initPwd, "AES128", "MD_ORG", false, "ALL", true);
            this.logger.info("\u9884\u7f6e\u7528\u6237\u4fe1\u606f\u5bfc\u5165\u7ed3\u679c\uff1a{}", (Object)result);
        }
        finally {
            NpContextHolder.setContext((NpContext)context);
        }
    }

    private void initUserInfo() throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        NpContextUser contextUser = this.buildUserContext();
        npContext.setUser((ContextUser)contextUser);
        npContext.setIdentity((ContextIdentity)this.buildIdentityContext(contextUser));
        String tenantId = "__default_tenant__";
        npContext.setTenant(tenantId);
        NpContextHolder.setContext((NpContext)npContext);
    }

    private NpContextUser buildUserContext() {
        NpContextUser userContext = new NpContextUser();
        SystemUserService sysUserService = (SystemUserService)SpringContextUtils.getBean(SystemUserService.class);
        SystemUser user = (SystemUser)sysUserService.getByUsername("admin");
        if (user == null) {
            user = (SystemUser)sysUserService.getUsers().get(0);
        }
        userContext.setId("SYSTEM.ROOT");
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setOrgCode(user.getOrgCode());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getName());
        identity.setOrgCode(contextUser.getOrgCode());
        return identity;
    }
}

