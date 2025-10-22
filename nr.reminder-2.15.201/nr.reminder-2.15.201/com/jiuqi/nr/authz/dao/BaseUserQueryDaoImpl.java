/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.authz.dao;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.authz.AuthErrorEnum;
import com.jiuqi.nr.authz.IUserEntity;
import com.jiuqi.nr.authz.UserQueryParam;
import com.jiuqi.nr.authz.dao.IUserQueryDao;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.CollectionUtils;

public abstract class BaseUserQueryDaoImpl<T extends IUserEntity>
implements IUserQueryDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(BaseUserQueryDaoImpl.class);
    private static final String TABLE_NAME_IDENTITY_ROLE = "NP_AUTHZ_IDENTITY_ROLE";
    private static final String TABLE_NAME_IDENTITY_ENTITY = "NP_AUTHZ_ENTITY_IDENTITY";
    private static final String TABLE_NAME_ROLE = "NP_AUTHZ_ROLE";
    private static final String TABLE_NAME_USER = "np_user";
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    protected UserService<User> userService;

    protected abstract RowMapper<T> getRowMapper();

    protected abstract String[] getColumn();

    protected abstract T buildEntity(User var1);

    @Override
    public List<T> queryUser(UserQueryParam queryParam) throws JQException {
        String querySql = this.getQuerySql(queryParam, true, false);
        MapSqlParameterSource parameters = this.getQueryParameters(queryParam);
        if (queryParam.getPage() != 0) {
            DataSource dataSource = this.jdbcTemplate.getDataSource();
            if (dataSource == null) {
                throw new JQException((ErrorEnum)AuthErrorEnum.AUTH, "\u67e5\u8be2\u7528\u6237\u5931\u8d25");
            }
            try (Connection connection = dataSource.getConnection();){
                IDatabase iDatabase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
                IPagingSQLBuilder sqlBuilder = iDatabase.createPagingSQLBuilder();
                sqlBuilder.setRawSQL(querySql);
                int startRow = (queryParam.getPage() - 1) * queryParam.getMaxResult();
                int endRow = queryParam.getPage() * queryParam.getMaxResult();
                querySql = sqlBuilder.buildSQL(startRow, endRow);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new JQException((ErrorEnum)AuthErrorEnum.AUTH, "\u67e5\u8be2\u7528\u6237\u5931\u8d25");
            }
        }
        return this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)parameters, this.getRowMapper());
    }

    protected String getQuerySql(UserQueryParam queryParam, boolean orderBy, boolean isCount) {
        List<String> entityKey;
        List<String> roleIds;
        String[] column = this.getColumn();
        String show3 = " count(distinct (TB_USER.ID)) ";
        String replace = "*_*_*_*";
        boolean innerRole = false;
        StringBuilder getUserSql = new StringBuilder(String.format("SELECT %s FROM %s TB_USER ", replace, TABLE_NAME_USER));
        if (queryParam.getRoleGroupId() != null) {
            getUserSql.append(String.format(" INNER JOIN %s TB_ROLE_LINK ON TB_ROLE_LINK.IDENTITY_ID_ = TB_USER.ID INNER JOIN %s TB_ROLE ON TB_ROLE.ID_ = TB_ROLE_LINK.ROLE_ID_ AND TB_ROLE.GROUP_ID_ = :roleGroupId ", TABLE_NAME_IDENTITY_ROLE, TABLE_NAME_ROLE));
            innerRole = true;
        }
        if ((roleIds = queryParam.getRoleIds()) != null && roleIds.size() != 0) {
            String roleDataKeyPrName = "roleIds";
            String inSql = this.getInSql("TB_ROLE2.ROLE_ID_", roleDataKeyPrName, roleIds.size(), true);
            getUserSql.append(String.format(" INNER JOIN %s TB_ROLE2 ON TB_ROLE2.IDENTITY_ID_ = TB_USER.ID  AND (", TABLE_NAME_IDENTITY_ROLE)).append(inSql).append(" ) ");
            innerRole = true;
        }
        StringBuilder whereBu = new StringBuilder(" where ");
        String keyword = queryParam.getKeyword();
        if (StringUtils.isNotBlank((CharSequence)keyword)) {
            String where = " (";
            String or = " UPPER(TB_USER.NAME) LIKE :username ESCAPE :escape OR UPPER(TB_USER.NICKNAME) LIKE :username  ESCAPE :escape ";
            where = where + or + " ) AND";
            whereBu.append(where);
        }
        if (queryParam.getLocked() != null) {
            whereBu.append(" TB_USER.LOCKED = :locked AND ");
        }
        if (queryParam.getEnabled() != null) {
            whereBu.append(" TB_USER.ENABLED = :enabled AND ");
        }
        if (!CollectionUtils.isEmpty(entityKey = queryParam.getEntityKey())) {
            String inSql = this.getInSql("TB_USER.ORG_CODE", "org_code", entityKey.size(), true);
            whereBu.append(inSql).append(" AND ");
        }
        String where = whereBu.toString();
        getUserSql.append(where);
        if (orderBy) {
            if (!innerRole) {
                getUserSql.append(" TB_USER.CREATE_TIME IS NOT NULL  ORDER BY TB_USER.CREATE_TIME DESC ");
            } else {
                getUserSql.append(" TB_USER.CREATE_TIME IS NOT NULL");
            }
        } else {
            getUserSql.append(" TB_USER.CREATE_TIME IS NOT NULL");
        }
        if (isCount) {
            return getUserSql.toString().replace(replace, show3);
        }
        if (!innerRole) {
            String uSelect = "TB_USER.ID AS ID_," + this.appendTableAliasToFields(column);
            return getUserSql.toString().replace(replace, uSelect);
        }
        String uSelect = this.appendTableAliasToFields(column);
        uSelect = " distinct (TB_USER.ID) AS IC," + uSelect;
        String getUserSqlS = getUserSql.toString().replace(replace, uSelect);
        if (orderBy) {
            getUserSqlS = getUserSqlS + " ORDER BY TB_USER.CREATE_TIME DESC ";
        }
        return getUserSqlS;
    }

    protected MapSqlParameterSource getQueryParameters(UserQueryParam queryParam) {
        String userName;
        List<String> roleIds;
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        if (queryParam.getRoleGroupId() != null) {
            parameters.addValue("roleGroupId", (Object)queryParam.getRoleGroupId());
        }
        if (queryParam.getRoleIds() != null && queryParam.getRoleIds().size() != 0 && (roleIds = queryParam.getRoleIds()) != null && roleIds.size() != 0) {
            String roleDataKeyPrName = "roleIds";
            this.setInParam(roleDataKeyPrName, parameters, roleIds);
        }
        if (StringUtils.isNotEmpty((CharSequence)(userName = queryParam.getKeyword()))) {
            userName = userName.replace("/", "//").replace("_", "/_").replace("%", "/%");
            parameters.addValue("username", (Object)("%" + userName.toUpperCase() + "%"));
            parameters.addValue("escape", (Object)"/");
        }
        if (!CollectionUtils.isEmpty(queryParam.getEntityKey())) {
            this.setInParam("org_code", parameters, queryParam.getEntityKey());
        }
        if (queryParam.getLocked() != null) {
            parameters.addValue("locked", (Object)(queryParam.getLocked() != false ? 1 : 0));
        }
        if (queryParam.getEnabled() != null) {
            parameters.addValue("enabled", (Object)(queryParam.getEnabled() != false ? 1 : 0));
        }
        return parameters;
    }

    @Override
    public long queryUserCount(UserQueryParam queryParam) {
        MapSqlParameterSource parameters;
        String querySql = this.getQuerySql(queryParam, false, true);
        Long aUserCount = (Long)this.namedParameterJdbcTemplate.queryForObject(querySql, (SqlParameterSource)(parameters = this.getQueryParameters(queryParam)), Long.class);
        aUserCount = aUserCount == null ? 0L : aUserCount;
        return aUserCount;
    }

    @Override
    public List<T> queryUserByIds(String ... userIds) {
        List users = this.userService.get(userIds);
        return users.stream().map(this::buildEntity).collect(Collectors.toList());
    }

    protected String appendTableAliasToFields(String[] fileds) {
        return Arrays.stream(fileds).map(o -> "TB_USER." + o).reduce((a, b) -> a + "," + b).orElseThrow(() -> new IllegalArgumentException("argument error, please check."));
    }

    protected String getInSql(String colName, String entityDataKeyPrName, int size, boolean inOrNo) {
        String in = " in ";
        String ine = " or ";
        if (!inOrNo) {
            in = " not in ";
            ine = " and ";
        }
        int count = this.getCount(size);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            sb.append(colName).append(in).append(" ( :").append(entityDataKeyPrName).append(i).append(" ").append(" )");
            if (i == count - 1) continue;
            sb.append(ine);
        }
        return sb.toString();
    }

    protected int getCount(int size) {
        int count = size % 1000 == 0 ? size / 1000 : size / 1000 + 1;
        return count;
    }

    protected void setInParam(String entityDataKeyPrName, MapSqlParameterSource parameters, List<String> entityKeys) {
        int count = this.getCount(entityKeys.size());
        for (int i = 0; i < count; ++i) {
            parameters.addValue(entityDataKeyPrName + i, this.groupListByQuantity(entityKeys, i));
        }
    }

    protected List<String> groupListByQuantity(List<String> list, int i) {
        if (list == null || list.size() == 0) {
            return list;
        }
        return list.subList(i * 1000, Math.min((i + 1) * 1000, list.size()));
    }
}

