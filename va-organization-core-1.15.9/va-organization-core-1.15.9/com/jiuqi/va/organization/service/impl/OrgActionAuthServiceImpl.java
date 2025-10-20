/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.extend.OrgDataMgrMenuOverride
 *  com.jiuqi.va.extend.OrgDataMgrUiMenuExtend
 *  com.jiuqi.va.feign.client.AuthRoleClient
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.organization.service.impl;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.extend.OrgDataMgrMenuOverride;
import com.jiuqi.va.extend.OrgDataMgrUiMenuExtend;
import com.jiuqi.va.feign.client.AuthRoleClient;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.dao.VaOrgActionAuthDao;
import com.jiuqi.va.organization.domain.OrgActionAuthDO;
import com.jiuqi.va.organization.domain.OrgActionAuthDTO;
import com.jiuqi.va.organization.domain.OrgActionAuthVO;
import com.jiuqi.va.organization.service.OrgActionAuthService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrgActionAuthServiceImpl
implements OrgActionAuthService {
    @Autowired
    private VaOrgActionAuthDao vaOrgActionAuthDao;
    @Autowired
    private AuthRoleClient authRoleClient;
    @Autowired(required=false)
    private List<OrgDataMgrUiMenuExtend> menuExtends;

    @Override
    public PageVO<OrgActionAuthVO> listDetail(OrgActionAuthDTO param) {
        ArrayList<OrgActionAuthVO> actAuthVOList = new ArrayList<OrgActionAuthVO>();
        ArrayList<OrgActionAuthVO> extendActAuthVOList = null;
        HashSet<String> overrideNameSet = new HashSet<String>();
        if (this.menuExtends != null) {
            extendActAuthVOList = new ArrayList<OrgActionAuthVO>();
            for (OrgDataMgrUiMenuExtend menuExtend : this.menuExtends) {
                boolean isApplyTo;
                boolean bl = isApplyTo = menuExtend.getApplyTo() == null || menuExtend.getApplyTo().contains(param.getOrgcategory());
                if (isApplyTo && menuExtend.isActAuthEnable()) {
                    this.getActionAuth(menuExtend.getName(), menuExtend.getTitle(), extendActAuthVOList, param, true, overrideNameSet);
                }
                if (menuExtend.getOverrideMenu() == null || menuExtend.getOverrideMenu() == OrgDataMgrMenuOverride.None) continue;
                overrideNameSet.add(menuExtend.getOverrideMenu().getMenuName());
            }
        }
        this.getActionAuth("action_addSub", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.newSubordinate", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_addSameLevel", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.newSameLevel", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_relAdd", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.referenceCreate", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_update", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.modify", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_save", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.save", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_delete", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.delete", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_stop", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.disable", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_start", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.enable", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_moveUp", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.moveUp", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_moveDown", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.moveDown", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_moveFast", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.quickMovement", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_transfer", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.move", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_import_templateMgr", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.impTemplateMgr", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_import", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.import", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_export", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.export", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        if (extendActAuthVOList != null) {
            actAuthVOList.addAll(extendActAuthVOList);
        }
        this.getActionAuth("action_versionManage", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.versionManagement", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        this.getActionAuth("action_recover", OrgCoreI18nUtil.getMessage("org.attribute.auth.action.recycleBin", new Object[0]), actAuthVOList, param, false, overrideNameSet);
        PageVO page = new PageVO();
        page.setRs(R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])));
        page.setRows(actAuthVOList);
        page.setTotal(actAuthVOList.size());
        return page;
    }

    private void getActionAuth(String name, String title, List<OrgActionAuthVO> actAuthVOList, OrgActionAuthDO param, boolean isExtend, Set<String> overrideNameSet) {
        if (!isExtend && overrideNameSet.contains(name)) {
            return;
        }
        OrgActionAuthVO actAuthVO = new OrgActionAuthVO();
        actAuthVO.setOrgcategory(param.getOrgcategory());
        actAuthVO.setActname(name);
        actAuthVO.setActTitle(title);
        actAuthVO.setBiztype(param.getBiztype());
        actAuthVO.setBizname(param.getBizname());
        actAuthVO.setAuthtype(1);
        param.setActname(name);
        OrgActionAuthDO orgActionAuthDO = (OrgActionAuthDO)this.vaOrgActionAuthDao.selectOne(param);
        if (orgActionAuthDO != null) {
            actAuthVO.setAuthflag(orgActionAuthDO.getAuthflag());
        } else {
            actAuthVO.setAuthflag(0);
        }
        actAuthVOList.add(actAuthVO);
    }

    @Override
    @Transactional
    public R updateDetail(List<OrgActionAuthDO> actAuths) {
        if (actAuths == null || actAuths.isEmpty()) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.data.empty", new Object[0]));
        }
        for (OrgActionAuthDO actAuth : actAuths) {
            Integer authflag = actAuth.getAuthflag();
            actAuth.setAuthflag(null);
            OrgActionAuthDO orgActionAuthDO = (OrgActionAuthDO)this.vaOrgActionAuthDao.selectOne(actAuth);
            if (orgActionAuthDO != null) {
                if (authflag.equals(orgActionAuthDO.getAuthflag())) continue;
                if (authflag == 0) {
                    this.vaOrgActionAuthDao.delete(orgActionAuthDO);
                    continue;
                }
                orgActionAuthDO.setAuthflag(authflag);
                this.vaOrgActionAuthDao.updateByPrimaryKeySelective(orgActionAuthDO);
                continue;
            }
            if (authflag == 0) continue;
            actAuth.setId(UUID.randomUUID());
            actAuth.setAuthflag(authflag);
            this.vaOrgActionAuthDao.insert(actAuth);
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    @Override
    public Set<String> getUserAuth(OrgActionAuthDTO param) {
        UserLoginDTO user = ShiroUtil.getUser();
        StringBuilder sb = new StringBuilder();
        sb.append(" ( (BIZTYPE=0 and BIZNAME in ('-'");
        List roles = this.authRoleClient.listNameByUser((UserDO)user);
        if (roles != null && !roles.isEmpty()) {
            for (String roleName : roles) {
                sb.append(",'").append(roleName).append("'");
            }
        }
        sb.append(")) or (BIZTYPE=1 and BIZNAME='").append(user.getUsername()).append("') ) ");
        param.setAuthtype(1);
        param.setSqlCondition(sb.toString());
        List<OrgActionAuthDO> auths = this.vaOrgActionAuthDao.listAuthority(param);
        return auths.stream().map(OrgActionAuthDO::getActname).collect(Collectors.toSet());
    }
}

