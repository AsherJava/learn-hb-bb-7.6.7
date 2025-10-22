/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.organization.common.OrgAuthRuleExtProvider
 *  com.jiuqi.va.organization.domain.OrgAuthDO
 */
package com.jiuqi.nr.entity.adapter.impl.org.auth;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.entity.adapter.impl.org.auth.Authority;
import com.jiuqi.nr.entity.adapter.impl.org.auth.dao.AuthQueryDao;
import com.jiuqi.nr.entity.adapter.provider.IEntityAuthProvider;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.organization.common.OrgAuthRuleExtProvider;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractOrgAuthProvider
implements IEntityAuthProvider {
    private final UserService<User> userService;
    private final OrgIdentityService orgIdentityService;
    private final SystemIdentityService systemIdentityService;
    private final AuthQueryDao authQueryDao;
    private final RoleService roleService;
    private final List<OrgAuthRuleExtProvider> orgAuthRuleExtProvider;

    public AbstractOrgAuthProvider(UserService<User> userService, OrgIdentityService orgIdentityService, SystemIdentityService systemIdentityService, AuthQueryDao authQueryDao, RoleService roleService, List<OrgAuthRuleExtProvider> orgAuthRuleExtProvider) {
        this.userService = userService;
        this.orgIdentityService = orgIdentityService;
        this.systemIdentityService = systemIdentityService;
        this.authQueryDao = authQueryDao;
        this.roleService = roleService;
        this.orgAuthRuleExtProvider = orgAuthRuleExtProvider;
    }

    @Override
    public Set<String> getCanOperateIdentities(String authType, String orgTable, String orgCode, Date versionEndDate) {
        List<OrgAuthDO> orgAuthDOS = this.authQueryDao.queryRule(authType);
        Map<String, List<OrgAuthDO>> authMap = orgAuthDOS.stream().collect(Collectors.groupingBy(OrgAuthDO::getBizname));
        Set<String> roleKeys = authMap.keySet();
        HashSet<String> userIds = new HashSet<String>();
        OrgDO orgDO = this.getOrgData(orgCode, orgTable, versionEndDate);
        if (orgDO == null) {
            return Collections.emptySet();
        }
        for (String roleKey : roleKeys) {
            List<OrgAuthDO> roleRule = authMap.get(roleKey);
            String roleId = this.getRoleId(roleKey);
            if (!StringUtils.hasText(roleId)) continue;
            if (this.existAllRuleAuth(roleRule)) {
                if (!StringUtils.hasText(roleId)) continue;
                List userIdsOfAllRule = this.roleService.getUserIdByRole(roleId);
                userIds.addAll(userIdsOfAllRule);
                continue;
            }
            Set<String> userIdsInRole = this.getUserIdsInRole(roleRule, roleId, orgDO);
            Set<String> userIdsInGranted = this.getUserIdsInGranted(roleRule, roleId, orgDO);
            userIds.addAll(userIdsInRole);
            userIds.addAll(userIdsInGranted);
            this.filterByExtendRule(authType, orgCode, userIds, roleRule);
        }
        Set<String> userIdsInRoleByDetailSettings = this.getUserIdsInRoleByDetailSettings(orgTable, orgCode, authType);
        userIds.addAll(userIdsInRoleByDetailSettings);
        EnumMap<Authority, List<String>> detailAuth = this.authQueryDao.queryDetail(orgTable, orgCode, authType);
        List<String> rejectUser = detailAuth.get((Object)Authority.REJCET);
        List<String> allowUser = detailAuth.get((Object)Authority.ALLOW);
        if (rejectUser != null) {
            rejectUser.forEach(userIds::remove);
        }
        if (allowUser != null) {
            userIds.addAll(allowUser);
        }
        userIds.addAll(this.systemIdentityService.getAllSystemIdentities());
        return userIds;
    }

    private Set<String> getUserIdsInRoleByDetailSettings(String orgTable, String orgCode, String authType) {
        HashSet<String> userIds = new HashSet<String>();
        Set<String> roleKeys = this.authQueryDao.queryRoleDetailAuth(authType, orgTable, orgCode);
        if (!CollectionUtils.isEmpty(roleKeys)) {
            for (String roleKey : roleKeys) {
                String roleId = this.getRoleId(roleKey);
                if (!StringUtils.hasText(roleId)) continue;
                List userIdsOfAllRule = this.roleService.getUserIdByRole(roleId);
                userIds.addAll(userIdsOfAllRule);
            }
        }
        return userIds;
    }

    private Set<String> getUserIdsInRole(List<OrgAuthDO> roleRule, String roleId, OrgDO orgDO) {
        Set<String> orgCodes = this.getOrgCodesByRule(roleRule, orgDO);
        return this.authQueryDao.queryUserByOrgCodesInRole(roleId, orgCodes);
    }

    private Set<String> getOrgCodesByRule(List<OrgAuthDO> roleRules, OrgDO orgDO) {
        HashSet<String> orgCodes = new HashSet<String>();
        for (OrgAuthDO roleRule : roleRules) {
            switch (roleRule.getOrgname()) {
                case "rule_belong": {
                    orgCodes.add(orgDO.getCode());
                    break;
                }
                case "rule_direct_children": {
                    String directChildren = this.getParentCode(orgDO);
                    if (directChildren == null) break;
                    orgCodes.add(directChildren);
                    break;
                }
                case "rule_all_children": {
                    List<String> allChildrenOrg = this.getParentCodes(orgDO);
                    orgCodes.addAll(allChildrenOrg);
                    break;
                }
            }
        }
        return orgCodes;
    }

    private Set<String> getUserIdsInGranted(List<OrgAuthDO> roleRule, String roleId, OrgDO orgDO) {
        HashSet<String> userIds = new HashSet<String>();
        Set<String> orgCodes = this.getOrgCodesByGranted(roleRule, orgDO);
        Map infos = this.orgIdentityService.getGrantedIdentity(orgCodes);
        List userIdsInRole = this.roleService.getUserIdByRole(roleId);
        Map<String, String> userIdMap = userIdsInRole.stream().collect(Collectors.toMap(e -> e, e -> e));
        infos.values().forEach(ids -> {
            for (String id : ids) {
                String exist = (String)userIdMap.get(id);
                if (!StringUtils.hasText(exist)) continue;
                userIds.add(id);
            }
        });
        return userIds;
    }

    protected abstract OrgDO getOrgData(String var1, String var2, Date var3);

    private String getRoleId(String roleKey) {
        List byName;
        if ("-".equals(roleKey)) {
            roleKey = "EVERYONE";
        }
        if (CollectionUtils.isEmpty(byName = this.roleService.getByName(roleKey))) {
            return null;
        }
        Role role = (Role)byName.get(0);
        return role.getId();
    }

    private boolean existAllRuleAuth(List<OrgAuthDO> roleRule) {
        return roleRule.stream().anyMatch(e -> "rule_all".equals(e.getOrgname()));
    }

    private void filterByExtendRule(String authType, String orgCode, Set<String> userIds, List<OrgAuthDO> roleRules) {
        if (this.orgAuthRuleExtProvider != null) {
            Map<String, OrgAuthDO> ruleMap = roleRules.stream().collect(Collectors.toMap(OrgAuthDO::getOrgname, e -> e));
            HashSet<String> orgCodes = new HashSet<String>(1);
            orgCodes.add(orgCode);
            for (OrgAuthRuleExtProvider authRuleExtProvider : this.orgAuthRuleExtProvider) {
                String ruleName = authRuleExtProvider.getRuleName();
                OrgAuthDO authDO = ruleMap.get(ruleName);
                if (authDO == null) continue;
                for (String userId : userIds) {
                    User user = this.userService.get(userId);
                    authRuleExtProvider.filterData(this.convertUser(user), OrgDataOption.AuthType.valueOf((String)authType), orgCodes);
                    if (!CollectionUtils.isEmpty(orgCodes)) continue;
                    userIds.remove(userId);
                }
            }
        }
    }

    private Set<String> getOrgCodesByGranted(List<OrgAuthDO> roleRules, OrgDO orgDO) {
        HashSet<String> orgCodes = new HashSet<String>();
        for (OrgAuthDO roleRule : roleRules) {
            switch (roleRule.getOrgname()) {
                case "rule_both": {
                    orgCodes.add(orgDO.getCode());
                    break;
                }
                case "rule_both_direct_childre": {
                    String directChildren = this.getParentCode(orgDO);
                    if (directChildren == null) break;
                    orgCodes.add(directChildren);
                    break;
                }
                case "rule_both_all_children": {
                    List<String> allChildrenOrg = this.getParentCodes(orgDO);
                    orgCodes.addAll(allChildrenOrg);
                    break;
                }
            }
        }
        return orgCodes;
    }

    private String getParentCode(OrgDO orgDO) {
        return StringUtils.hasLength(orgDO.getParentcode()) && "-".equals(orgDO.getParentcode()) ? null : orgDO.getParentcode();
    }

    private List<String> getParentCodes(OrgDO orgDO) {
        String parents = orgDO.getParents();
        String[] path = null;
        if (parents != null) {
            String[] splitParents = parents.split("/");
            path = Arrays.copyOf(splitParents, splitParents.length - 1);
        }
        return path == null ? Collections.emptyList() : Arrays.asList(path);
    }

    private UserDO convertUser(User u) {
        UserDO userDO = new UserDO();
        userDO.setId(u.getId());
        userDO.setUsername(u.getName());
        userDO.setUnitcode(u.getOrgCode());
        userDO.addExtInfo("npConverted", (Object)true);
        return userDO;
    }
}

