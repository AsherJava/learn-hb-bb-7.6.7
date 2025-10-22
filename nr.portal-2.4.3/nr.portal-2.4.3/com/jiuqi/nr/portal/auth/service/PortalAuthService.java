/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Authorization
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.authz2.privilege.AuthzType
 *  com.jiuqi.np.authz2.privilege.InheritPathProvider
 *  com.jiuqi.np.authz2.privilege.Privilege
 *  com.jiuqi.np.authz2.privilege.common.ParentIterator
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeMetaService
 *  com.jiuqi.np.authz2.service.RoleService
 */
package com.jiuqi.nr.portal.auth.service;

import com.jiuqi.np.authz2.Authorization;
import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.AuthzType;
import com.jiuqi.np.authz2.privilege.InheritPathProvider;
import com.jiuqi.np.authz2.privilege.Privilege;
import com.jiuqi.np.authz2.privilege.common.ParentIterator;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeMetaService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.nr.portal.auth.service.IPortalAuthService;
import com.jiuqi.nr.portal.news2.vo.UserInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class PortalAuthService
implements IPortalAuthService {
    @Autowired
    private RoleService roleService;
    @Autowired
    protected PrivilegeMetaService privilegeMetaService;
    @Autowired
    protected AuthorizationService authService;

    @Override
    public List<UserInfo> getOwnerList(String resource, String privilegeId) {
        ArrayList<UserInfo> result = new ArrayList<UserInfo>();
        Privilege privilege = this.getPrivilege(privilegeId);
        InheritPathProvider inheritPathProvider = this.privilegeMetaService.getInheritPathProvider(privilege);
        ParentIterator parentIterator = ParentIterator.create((InheritPathProvider)inheritPathProvider, (Privilege)privilege, (Object)resource);
        HashMap directGrantedGrantee = new HashMap();
        while (!parentIterator.isEmpty()) {
            Iterator iterator = parentIterator.iterator();
            HashMap<String, LinkedList<Authority>> auths2Grantee = new HashMap<String, LinkedList<Authority>>();
            while (iterator.hasNext()) {
                Collection auths = this.authService.queryByResource(privilege.getId(), (String)iterator.next());
                Iterator iterator2 = auths.iterator();
                while (iterator2.hasNext()) {
                    Authority authority;
                    Authorization auth = (Authorization)iterator2.next();
                    String granteeId = auth.getGranteeId();
                    if (directGrantedGrantee.containsKey(granteeId) || (authority = auth.getAuthority(AuthzType.ACCESS)) != Authority.ALLOW) continue;
                    LinkedList<Authority> l = (LinkedList<Authority>)auths2Grantee.get(granteeId);
                    if (l == null) {
                        l = new LinkedList<Authority>();
                        auths2Grantee.put(granteeId, l);
                    }
                    l.add(authority);
                }
            }
            for (Map.Entry entry : auths2Grantee.entrySet()) {
                Authority mergedAuth = Authority.merge((List)((List)entry.getValue()));
                if (mergedAuth != Authority.ALLOW) continue;
                directGrantedGrantee.put(entry.getKey(), mergedAuth);
            }
            parentIterator.nextLevel();
        }
        for (Map.Entry entry : directGrantedGrantee.entrySet()) {
            Optional optional = this.roleService.get((String)entry.getKey());
            if (!optional.isPresent()) continue;
            result.add(new UserInfo((String)entry.getKey(), ((Role)optional.get()).getName(), true, ((Role)optional.get()).getTitle()));
        }
        return result;
    }

    private Privilege getPrivilege(String privilegeId) {
        Assert.notNull((Object)privilegeId, "'privilegeId' must not be null");
        return this.privilegeMetaService.getPrivilege(privilegeId);
    }
}

