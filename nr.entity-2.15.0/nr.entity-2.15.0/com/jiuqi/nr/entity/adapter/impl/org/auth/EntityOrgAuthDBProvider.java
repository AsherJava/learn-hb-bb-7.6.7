/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.organization.common.OrgAuthRuleExtProvider
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.entity.adapter.impl.org.auth;

import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.entity.adapter.impl.org.auth.AbstractOrgAuthProvider;
import com.jiuqi.nr.entity.adapter.impl.org.auth.dao.AuthQueryDao;
import com.jiuqi.nr.entity.adapter.impl.org.db.DataBaseQueryHelper;
import com.jiuqi.nr.entity.adapter.provider.IEntityAuthProvider;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.common.OrgAuthRuleExtProvider;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

public class EntityOrgAuthDBProvider
extends AbstractOrgAuthProvider
implements IEntityAuthProvider {
    private final JdbcTemplate jdbcTemplate;
    private final DataBaseQueryHelper dbQueryHelper;

    public EntityOrgAuthDBProvider(JdbcTemplate jdbcTemplate, DataBaseQueryHelper dataBaseQueryHelper, UserService<User> userService, OrgIdentityService orgIdentityService, SystemIdentityService systemIdentityService, AuthQueryDao authQueryDao, RoleService roleService, List<OrgAuthRuleExtProvider> orgAuthRuleExtProvider) {
        super(userService, orgIdentityService, systemIdentityService, authQueryDao, roleService, orgAuthRuleExtProvider);
        this.dbQueryHelper = dataBaseQueryHelper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean canAuthorityEntityKey(String authType, String entityId, String entityKeyData, Date queryVersionDate) {
        return this.canAuthorityEntityKey(authType, entityId, NpContextHolder.getContext().getUser().getId(), entityKeyData, queryVersionDate);
    }

    @Override
    public Map<String, Boolean> canAuthorityEntityKey(String authType, String entityId, Set<String> entityKeyDatas, Date queryVersionDate) {
        String identityId = NpContextHolder.getContext().getUser().getId();
        DataBaseQueryHelper.SqlBuildDTO sqlBuildDTO = this.dbQueryHelper.buildAuthSql(entityId, OrgDataOption.AuthType.valueOf((String)authType), identityId, "");
        StringBuilder sql = sqlBuildDTO.getSql();
        List<Object> arg = sqlBuildDTO.getArg();
        if (sql.length() == 0) {
            sql.append(" 1 = 1");
        }
        sql.append(" OR (");
        for (String keyData : entityKeyDatas) {
            sql.append(" CODE = ? OR ");
            arg.add(keyData);
        }
        this.dbQueryHelper.trimTrailing(sql, "OR");
        sql.append(" ) ");
        this.dbQueryHelper.buildCondition(queryVersionDate, sql, arg, "");
        Set<String> exist = this.query(entityId, sql.toString(), arg.toArray(new Object[0]));
        HashMap<String, Boolean> result = new HashMap<String, Boolean>(entityKeyDatas.size());
        for (String entityKeyData : entityKeyDatas) {
            result.put(entityKeyData, exist.contains(entityKeyData));
        }
        return result;
    }

    @Override
    public boolean canAuthorityEntityKey(String authType, String entityId, String identityId, String entityKeyData, Date queryVersionDate) {
        return this.getCodes(authType, identityId, entityId, entityKeyData, queryVersionDate).contains(entityKeyData);
    }

    @Override
    public Set<String> getCanOperateEntityKeys(String authType, String entityId, Date versionEndDate) {
        return this.getCodes(authType, NpContextHolder.getContext().getUser().getId(), entityId, null, versionEndDate);
    }

    @Override
    protected OrgDO getOrgData(String code, String table, Date versionEndDate) {
        String sql = String.format("SELECT %s, %s, %s FROM %s where %s = ? AND %s <= ? AND %s > ? AND %s = ? AND %s = ?", "code", "parentcode", "parents", table, "code", "validtime", "invalidtime", "stopflag", "recoveryflag");
        Object[] arg = new Object[]{code, versionEndDate, versionEndDate, 0, 0};
        this.jdbcTemplate.query(sql, rs -> {
            OrgDO orgDO = null;
            while (rs.next()) {
                orgDO = new OrgDO();
                orgDO.setCode(code);
                orgDO.setParentcode(rs.getString("parentcode"));
                orgDO.setParents(rs.getString("parents"));
            }
            return orgDO;
        }, arg);
        return null;
    }

    private Set<String> query(String entityId, String querySql, Object[] arg) {
        String sql = String.format("SELECT CODE FROM %s WHERE %s ", entityId, querySql);
        HashSet codes = new HashSet();
        return (Set)this.jdbcTemplate.query(sql, rs -> {
            while (rs.next()) {
                codes.add(rs.getString("CODE"));
            }
            return codes;
        }, arg);
    }

    private Set<String> getCodes(String authType, String identityId, String entityId, String entityKeyData, Date versionEndDate) {
        DataBaseQueryHelper.SqlBuildDTO sqlBuildDTO = this.dbQueryHelper.buildAuthSql(entityId, OrgDataOption.AuthType.valueOf((String)authType), identityId, "");
        StringBuilder sql = sqlBuildDTO.getSql();
        List<Object> arg = sqlBuildDTO.getArg();
        if (StringUtils.hasText(sql)) {
            sql.append(" AND ");
        }
        if (StringUtils.hasText(entityKeyData)) {
            sql.append(" CODE = ?");
            arg.add(entityKeyData);
        }
        if (sql.length() == 0) {
            sql.append(" 1=1 ");
        }
        this.dbQueryHelper.buildCondition(versionEndDate, sql, arg, "");
        return this.query(entityId, sql.toString(), arg.toArray(new Object[0]));
    }
}

