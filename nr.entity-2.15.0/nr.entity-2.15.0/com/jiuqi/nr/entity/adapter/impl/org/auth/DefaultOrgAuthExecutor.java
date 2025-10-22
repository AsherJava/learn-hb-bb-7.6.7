/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.feign.client.OrgAuthClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.organization.common.OrgAuthRuleExtProvider
 *  com.jiuqi.va.organization.service.OrgAuthService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.entity.adapter.impl.org.auth;

import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.entity.adapter.executor.IExtendAuthExecutor;
import com.jiuqi.nr.entity.adapter.impl.org.OrgVersionManager;
import com.jiuqi.nr.entity.adapter.impl.org.auth.EntityOrgAuthDBProvider;
import com.jiuqi.nr.entity.adapter.impl.org.auth.EntityOrgAuthProvider;
import com.jiuqi.nr.entity.adapter.impl.org.auth.dao.AuthQueryDao;
import com.jiuqi.nr.entity.adapter.impl.org.db.DataBaseQueryHelper;
import com.jiuqi.nr.entity.adapter.provider.IEntityAuthProvider;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.feign.client.OrgAuthClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.organization.common.OrgAuthRuleExtProvider;
import com.jiuqi.va.organization.service.OrgAuthService;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component(value="defaultOrgAuthExecutor")
public class DefaultOrgAuthExecutor
implements IExtendAuthExecutor {
    @Autowired
    private OrgAuthService orgAuthService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private OrgIdentityService orgIdentityService;
    @Autowired
    protected SystemIdentityService systemIdentityService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private AuthQueryDao authQueryDao;
    @Autowired
    private RoleService roleService;
    @Autowired(required=false)
    private List<OrgAuthRuleExtProvider> orgAuthRuleExtProvider;
    @Autowired
    private OrgAuthClient orgAuthClient;
    @Autowired
    private OrgVersionManager orgVersionManager;
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean isEnable(String entityId) {
        return true;
    }

    @Override
    public double getOrder() {
        return 10.0;
    }

    @Override
    public IEntityAuthProvider getProvider(String entityId, Date version) {
        if (this.isDBModel(entityId, version)) {
            DataBaseQueryHelper dataBaseQueryHelper = new DataBaseQueryHelper(this.userService, this.orgIdentityService, this.authQueryDao);
            return new EntityOrgAuthDBProvider(this.jdbcTemplate, dataBaseQueryHelper, this.userService, this.orgIdentityService, this.systemIdentityService, this.authQueryDao, this.roleService, this.orgAuthRuleExtProvider);
        }
        return new EntityOrgAuthProvider(this.userService, this.orgIdentityService, this.systemIdentityService, this.orgDataClient, this.authQueryDao, this.roleService, this.orgAuthRuleExtProvider, this.orgAuthClient);
    }

    private boolean isDBModel(String entityId, Date version) {
        ContextExtension extension = NpContextHolder.getContext().getExtension("NR_ENTITY_DB_MODEL");
        String format = this.sdf.format(version);
        Object value = extension.get(entityId + "_" + format);
        if (value == null) {
            OrgVersionDO versionDO = this.orgVersionManager.getVersion(entityId, version);
            if (versionDO != null && !versionDO.isActive() && this.systemOptionService.get("ORG_AUTH_EXT", "ORG_DB_MODE").equals("1")) {
                extension.put(entityId + "_" + format, (Serializable)Boolean.valueOf(true));
                return true;
            }
            extension.put(entityId + "_" + format, (Serializable)Boolean.valueOf(false));
            return false;
        }
        return Boolean.parseBoolean(value.toString());
    }
}

