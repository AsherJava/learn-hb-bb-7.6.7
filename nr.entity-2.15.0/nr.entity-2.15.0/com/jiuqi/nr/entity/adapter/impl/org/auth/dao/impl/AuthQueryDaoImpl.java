/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.organization.dao.VaOrgAuthDao
 *  com.jiuqi.va.organization.domain.OrgAuthDO
 *  com.jiuqi.va.organization.domain.OrgAuthDTO
 *  org.springframework.jdbc.core.RowCallbackHandler
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 */
package com.jiuqi.nr.entity.adapter.impl.org.auth.dao.impl;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.impl.org.auth.AuthItem;
import com.jiuqi.nr.entity.adapter.impl.org.auth.Authority;
import com.jiuqi.nr.entity.adapter.impl.org.auth.dao.AuthQueryDao;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.dao.VaOrgAuthDao;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import com.jiuqi.va.organization.domain.OrgAuthDTO;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthQueryDaoImpl
implements AuthQueryDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private VaOrgAuthDao orgAuthDao;
    @Autowired
    private RoleService roleService;

    @Override
    public Set<String> queryUserByOrgCode(String orgCode) {
        String querySql = "SELECT ID FROM NP_USER WHERE ORG_CODE = :code";
        HashMap<String, String> ordCodes = new HashMap<String, String>(1);
        ordCodes.put("code", orgCode);
        return (Set)this.jdbcTemplate.query(querySql, ordCodes, rs -> {
            HashSet<String> ids = new HashSet<String>();
            while (rs.next()) {
                String id = rs.getString("id");
                ids.add(id);
            }
            return ids;
        });
    }

    @Override
    public List<OrgAuthDO> queryRule(String authType) {
        String querySql = String.format("SELECT ORGNAME, BIZNAME FROM AUTH_ORG_RIGHT WHERE BIZTYPE = 0 AND AUTHTYPE = 0 AND %s = 1", "at" + authType);
        return (List)this.jdbcTemplate.query(querySql, rs -> {
            ArrayList<OrgAuthDO> auth = new ArrayList<OrgAuthDO>();
            while (rs.next()) {
                OrgAuthDO authDO = new OrgAuthDO();
                authDO.setBizname(rs.getString("BIZNAME"));
                authDO.setOrgname(rs.getString("ORGNAME"));
                auth.add(authDO);
            }
            return auth;
        });
    }

    @Override
    public Set<String> queryUserByOrgCodesInRole(String roleId, Set<String> orgCodes) {
        String querySql = "SELECT DISTINCT uid_  FROM (    SELECT ui.USER_ID_ AS uid_    FROM NP_AUTHZ_USER_IDENTITY ui    INNER JOIN NP_AUTHZ_IDENTITY_ROLE ir ON ui.IDENTITY_ID_ = ir.IDENTITY_ID_    INNER JOIN NP_AUTHZ_IDENTITY ai ON ai.ID_ = ui.IDENTITY_ID_    WHERE ir.ROLE_ID_ = :ROLEID         AND ai.ORG_CODE_ IN (:ORGCODES)    UNION ALL    SELECT u.id AS uid_    FROM NP_USER u    INNER JOIN NP_AUTHZ_IDENTITY_ROLE ur ON u.ID = ur.IDENTITY_ID_    WHERE ur.ROLE_ID_ = :ROLEID         AND u.ORG_CODE IN (:ORGCODES)) uid_";
        int number = 1000;
        int limit = (orgCodes.size() + number - 1) / number;
        HashSet<String> userIds = new HashSet<String>();
        RowCallbackHandler rch = rs -> {
            String id = rs.getString("uid_");
            userIds.add(id);
        };
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            Set resource = orgCodes.stream().skip((long)i.intValue() * (long)number).limit(number).collect(Collectors.toSet());
            HashMap<String, Object> queryParam = new HashMap<String, Object>(2);
            queryParam.put("ROLEID", roleId);
            queryParam.put("ORGCODES", resource);
            this.jdbcTemplate.query(querySql, queryParam, rch);
        });
        return userIds;
    }

    @Override
    public EnumMap<Authority, List<String>> queryDetail(String orgTable, String orgCode, String authType) {
        String sql = String.format("SELECT a.at%s, u.id FROM AUTH_ORG_RIGHT a JOIN NP_USER u ON u.name = a.BIZNAME WHERE a.BIZTYPE = 1 AND a.ORGCATEGORY = :TABLE AND a.ORGNAME = :CODE AND a.AUTHTYPE = 1", authType);
        HashMap<String, String> queryParam = new HashMap<String, String>(1);
        queryParam.put("TABLE", orgTable);
        queryParam.put("CODE", orgCode);
        return (EnumMap)this.jdbcTemplate.query(sql, queryParam, rs -> {
            EnumMap<Authority, List> userAuthType = new EnumMap<Authority, List>(Authority.class);
            while (rs.next()) {
                String id = rs.getString("id");
                int atType = rs.getInt("at" + authType);
                userAuthType.computeIfAbsent(this.getAuthValue(atType), key -> new ArrayList()).add(id);
            }
            return userAuthType;
        });
    }

    @Override
    public Set<String> queryRoleDetailAuth(String authType, String table, String orgCode) {
        String sql = String.format("SELECT BIZNAME FROM AUTH_ORG_RIGHT WHERE BIZTYPE = 0 AND AUTHTYPE = 1 AND ORGCATEGORY =:TABLE AND ORGNAME = :ORGCODE AND at%s = 1", authType);
        HashMap<String, String> queryParam = new HashMap<String, String>(1);
        queryParam.put("TABLE", table);
        queryParam.put("ORGCODE", orgCode);
        return (Set)this.jdbcTemplate.query(sql, queryParam, rs -> {
            HashSet<String> res = new HashSet<String>();
            while (rs.next()) {
                res.add(rs.getString("BIZNAME"));
            }
            return res;
        });
    }

    @Override
    public List<AuthItem> listAuthItem(OrgDataOption.AuthType authType, String entityId, String identityId) {
        ContextUser user = NpContextHolder.getContext().getUser();
        OrgAuthDTO authDTO = new OrgAuthDTO();
        OrgDTO orgDataDTO = new OrgDTO();
        orgDataDTO.setCategoryname(entityId);
        orgDataDTO.setAuthType(authType);
        authDTO.setOrgDataDTO(orgDataDTO);
        StringBuilder sb = new StringBuilder();
        sb.append("((BIZTYPE=0 and BIZNAME in ('-'");
        List roles = this.roleService.getByIdentity(identityId);
        if (roles != null && !roles.isEmpty()) {
            int i = 0;
            for (Role role : roles) {
                if ("ffffffff-ffff-ffff-bbbb-ffffffffffff".equalsIgnoreCase(role.getId())) continue;
                authDTO.put("role_" + i, (Object)role.getName());
                sb.append(", #{").append("role_").append(i).append(",jdbcType=VARCHAR}");
                ++i;
            }
        }
        if (user != null) {
            authDTO.put("user_0", (Object)user.getName());
            sb.append(")) or (BIZTYPE=1 and BIZNAME = #{user_0, jdbcType=VARCHAR}) ) ");
        } else {
            sb.append(")) )");
        }
        authDTO.setOrgcategory(entityId);
        sb.append(" and ORGCATEGORY in('-', #{orgcategory, jdbcType=VARCHAR})");
        authDTO.setSqlCondition(sb.toString());
        List orgAuthItems = this.orgAuthDao.listAuthItem(authDTO);
        List<AuthItem> authItems = orgAuthItems.stream().map(e -> new AuthItem(e.getBiztype(), e.getBizname(), e.getAuthtype(), e.getOrgname(), e.getAuthflag())).collect(Collectors.toList());
        boolean hasIdentityRole = orgAuthItems.stream().anyMatch(e -> "identityRelOrg".equals(e.getOrgname()));
        if (hasIdentityRole && !NpContextHolder.getContext().getIdentityId().equals(identityId)) {
            return this.listAuthItem(authType, entityId, NpContextHolder.getContext().getIdentityId());
        }
        return authItems;
    }

    private Authority getAuthValue(int value) {
        switch (value) {
            case 1: {
                return Authority.ALLOW;
            }
            case 2: {
                return Authority.REJCET;
            }
        }
        return Authority.NONE;
    }
}

