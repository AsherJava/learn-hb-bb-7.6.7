/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.portal.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.portal.auth.service.IPortalAuthService;
import com.jiuqi.nr.portal.news2.impl.FileImpl;
import com.jiuqi.nr.portal.news2.impl.NewsAbstractInfo;
import com.jiuqi.nr.portal.news2.service.INews2Service;
import com.jiuqi.nr.portal.news2.service.IPortalFileService;
import com.jiuqi.nr.portal.news2.vo.NewsReturn;
import com.jiuqi.nr.portal.news2.vo.UserInfo;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags={"\u9996\u9875\u67e5\u8be2(\u5e26\u6743\u9650)"})
@RestController
@RequestMapping(value={"/api/portal/auth"})
public class PortalAuthController {
    private static final Logger logger = LoggerFactory.getLogger(PortalAuthController.class);
    @Autowired
    private INews2Service news2Service;
    @Autowired
    private IPortalFileService fileInfoService;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private IPortalAuthService portalAuthService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value={"group-news"})
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public NewsReturn findNewsByMid(String mid, String portalId, String type) {
        NewsReturn resultObj = new NewsReturn();
        if (StringUtils.isEmpty(mid) || StringUtils.isEmpty(portalId)) {
            resultObj.setState(false);
            resultObj.setMessage("mid\u548cportalId\u4e0d\u80fd\u4e3a\u7a7a\uff01");
            return resultObj;
        }
        ArrayList<NewsAbstractInfo> result = new ArrayList<NewsAbstractInfo>();
        List<NewsAbstractInfo> impls = this.news2Service.queryNewsByMidAndPortalId(mid, portalId, type);
        for (NewsAbstractInfo info : impls) {
            if (!info.getStartAuth().booleanValue()) {
                result.add(info);
                continue;
            }
            boolean hasDelegateAuth = this.privilegeService.hasAuth("portal_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)this.toResourceId("item-news_", info.getId()));
            if (!hasDelegateAuth) continue;
            boolean hasWriteAuth = this.privilegeService.hasAuth("portal_resource_write", NpContextHolder.getContext().getIdentityId(), (Object)this.toResourceId("item-news_", info.getId()));
            if (hasWriteAuth) {
                info.setEditable(true);
            }
            result.add(info);
        }
        boolean modelWriteAuth = this.privilegeService.hasAuth("portal_resource_write", NpContextHolder.getContext().getIdentityId(), (Object)("model_" + portalId + "$" + mid));
        Collections.sort(result, (o1, o2) -> o2.getOrder() - o1.getOrder());
        resultObj.setAbstractInfos(result.toArray(new NewsAbstractInfo[result.size()]));
        resultObj.setState(true);
        resultObj.setModelEditable(modelWriteAuth);
        return resultObj;
    }

    @GetMapping(value={"group-files"})
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public NewsReturn findFilesByMid(String mid, String portalId, String type) {
        NewsReturn resultObj = new NewsReturn();
        if (StringUtils.isEmpty(mid) || StringUtils.isEmpty(portalId)) {
            resultObj.setState(false);
            resultObj.setMessage("mid\u548cportalId\u4e0d\u80fd\u4e3a\u7a7a\uff01");
            return resultObj;
        }
        ArrayList<FileImpl> result = new ArrayList<FileImpl>();
        List<FileImpl> infos = this.fileInfoService.queryFileByMidAndPortalId(mid, portalId, type);
        for (FileImpl info : infos) {
            if (!info.getStartAuth().booleanValue()) {
                result.add(info);
                continue;
            }
            boolean hasDelegateAuth = this.privilegeService.hasAuth("portal_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)this.toResourceId("item-file_", info.getId()));
            if (!hasDelegateAuth) continue;
            boolean hasWriteAuth = this.privilegeService.hasAuth("portal_resource_write", NpContextHolder.getContext().getIdentityId(), (Object)this.toResourceId("item-file_", info.getId()));
            if (hasWriteAuth) {
                info.setEditable(true);
            }
            result.add(info);
        }
        boolean modelWriteAuth = this.privilegeService.hasAuth("portal_resource_write", NpContextHolder.getContext().getIdentityId(), (Object)("model_" + portalId + "$" + mid));
        resultObj.setFileImpls(result);
        resultObj.setState(true);
        resultObj.setModelEditable(modelWriteAuth);
        return resultObj;
    }

    @GetMapping(value={"write-auth"})
    public Boolean hasWriteAuth(String id) {
        if (!StringUtils.isEmpty(id)) {
            try {
                return this.privilegeService.hasAuth("portal_resource_write", NpContextHolder.getContext().getIdentityId(), (Object)id);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                return false;
            }
        }
        return false;
    }

    @GetMapping(value={"read-auth"})
    public Boolean hasReadAuth(String id) {
        if (!StringUtils.isEmpty(id)) {
            try {
                return this.privilegeService.hasAuth("portal_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)id);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                return false;
            }
        }
        return false;
    }

    @GetMapping(value={"getAllUsers"})
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public NewsReturn getAllUsers() {
        NewsReturn resultObj = new NewsReturn();
        ArrayList<UserInfo> result = new ArrayList<UserInfo>();
        try {
            List list = this.userService.getUsers();
            for (User user : list) {
                result.add(new UserInfo(user.getId(), user.getName()));
                result.add(new UserInfo(user.getId(), user.getName(), false, user.getNickname()));
            }
            resultObj.setUsers(result);
            resultObj.setState(true);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultObj.setState(false);
            resultObj.setMessage(e.getMessage());
        }
        return resultObj;
    }

    @GetMapping(value={"getAllRoles"})
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public NewsReturn getAllRoles() {
        NewsReturn resultObj = new NewsReturn();
        ArrayList<UserInfo> result = new ArrayList<UserInfo>();
        try {
            List allRoles = this.roleService.getAllRoles();
            for (Role user : allRoles) {
                result.add(new UserInfo(user.getId(), user.getName(), true));
                result.add(new UserInfo(user.getId(), user.getName(), true, user.getTitle()));
            }
            resultObj.setUsers(result);
            resultObj.setState(true);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultObj.setState(false);
            resultObj.setMessage(e.getMessage());
        }
        return resultObj;
    }

    @GetMapping(value={"owner-list"})
    public List<UserInfo> getOwnerList(String id, String authType) {
        if (!StringUtils.isEmpty(id) || !StringUtils.isEmpty(authType)) {
            List<UserInfo> ownerList = this.portalAuthService.getOwnerList(id, authType);
            Set hasAuthIdentities = this.privilegeService.getHasAuthIdentities(authType, (Object)id);
            ArrayList userIdByRole = new ArrayList();
            for (UserInfo role : ownerList) {
                userIdByRole.addAll(this.roleService.getIdentityIdByRole(role.getId()));
            }
            List list = this.userService.get(hasAuthIdentities.toArray(new String[0]));
            for (User user : list) {
                if (userIdByRole.contains(user.getId())) continue;
                ownerList.add(new UserInfo(user.getId(), user.getName(), false, user.getNickname()));
            }
            return ownerList;
        }
        return new ArrayList<UserInfo>();
    }

    private String toResourceId(String prefix, String objectId) {
        return prefix.concat(objectId);
    }
}

