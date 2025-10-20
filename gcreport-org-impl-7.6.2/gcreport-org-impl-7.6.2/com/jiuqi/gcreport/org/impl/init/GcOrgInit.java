/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.gcreport.org.impl.init;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgQueryService;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class GcOrgInit
implements ModuleInitiator {
    private final FGcOrgQueryService<GcOrgCacheVO> cacheManager;
    private final SystemUserService sysUserService;

    public GcOrgInit(FGcOrgQueryService<GcOrgCacheVO> cacheManager, SystemUserService sysUserService) {
        this.cacheManager = cacheManager;
        this.sysUserService = sysUserService;
    }

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) {
        this.initAsAdmin();
        this.cacheManager.initOrgCache();
    }

    private void initAsAdmin() {
        SystemUser admin = (SystemUser)this.sysUserService.getByUsername("admin");
        Assert.notNull((Object)admin, "\u65e0\u6cd5\u83b7\u53d6\u7ba1\u7406\u5458\u4fe1\u606f\uff01");
        NpContextImpl context = new NpContextImpl();
        NpContextIdentity contextIdentity = new NpContextIdentity();
        contextIdentity.setId(admin.getId());
        contextIdentity.setTitle(admin.getName());
        context.setIdentity((ContextIdentity)contextIdentity);
        String tenantId = "__default_tenant__";
        context.setTenant(tenantId);
        NpContextUser contextUser = new NpContextUser();
        contextUser.setId(admin.getId());
        contextUser.setName(admin.getName());
        contextUser.setNickname(admin.getNickname());
        context.setUser((ContextUser)contextUser);
        NpContextHolder.setContext((NpContext)context);
    }
}

