/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.organization.controller;

import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.organization.common.OrgCategoryMenuUtil;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.common.OrgDataMenuUtil;
import com.jiuqi.va.organization.service.OrgAuthService;
import com.jiuqi.va.organization.service.impl.help.OrgContextService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaOrgContextController")
@RequestMapping(value={"/org/context"})
public class OrgContextController {
    private static Logger logger = LoggerFactory.getLogger(OrgContextController.class);
    @Autowired
    private OrgContextService orgContextService;
    @Autowired
    private OrgAuthService orgAuthService;

    @GetMapping(value={"/getLoginUnit"})
    Object getLoginUnit() {
        String loginUnit = null;
        try {
            loginUnit = ShiroUtil.getUser().getLoginUnit();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        R r = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        r.put("loginUnit", (Object)loginUnit);
        return MonoVO.just((Object)r);
    }

    @GetMapping(value={"/getLoginToken"})
    Object getLoginToken() {
        String token = "-";
        try {
            token = ShiroUtil.getSubjct().getSession().getId().toString();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        R r = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        r.put("token", (Object)token);
        return MonoVO.just((Object)r);
    }

    @PostMapping(value={"/getModifyEnv"})
    Object getModifyEnv() {
        R rs = R.ok();
        rs.put("canMgr", (Object)this.orgContextService.canMgr());
        rs.put("isSuper", (Object)this.orgContextService.isSuperMan());
        rs.put("isModifyOrgcodeAllow", (Object)this.orgContextService.isModifyOrgcodeAllow());
        rs.put("isAddFromOtherAllow", (Object)this.orgContextService.isAddFromOtherAllow());
        rs.put("isModifyHistoricalDataAllow", (Object)this.orgContextService.isModifyHistoricalDataAllow());
        return MonoVO.just((Object)rs);
    }

    @GetMapping(value={"/getCategoryMenuExtend"})
    @RequiresPermissions(value={"vaOrg:category:mgr"})
    Object getCategoryMenuExtend() {
        return MonoVO.just(OrgCategoryMenuUtil.getMenuExtends());
    }

    @GetMapping(value={"/getDataMenuExtend"})
    Object getDataMenuExtend() {
        if (!this.orgContextService.canMgr()) {
            return MonoVO.just(null);
        }
        return MonoVO.just(OrgDataMenuUtil.getMenuExtends());
    }

    @GetMapping(value={"/getAuthTypeExtend"})
    Object getAuthTypeExtend() {
        List<Map<String, String>> authTypeMap = this.orgAuthService.getAuthType();
        if (authTypeMap == null) {
            authTypeMap = Collections.emptyList();
        }
        return MonoVO.just(authTypeMap);
    }
}

