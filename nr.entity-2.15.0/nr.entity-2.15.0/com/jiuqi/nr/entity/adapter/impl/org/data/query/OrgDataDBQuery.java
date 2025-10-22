/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.TempTableActuator
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.entity.adapter.impl.org.data.query;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.TempTableActuator;
import com.jiuqi.nr.entity.adapter.impl.org.OrgResultSet;
import com.jiuqi.nr.entity.adapter.impl.org.data.OrgDataSource;
import com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDataQuery;
import com.jiuqi.nr.entity.adapter.impl.org.db.DataBaseQueryHelper;
import com.jiuqi.nr.entity.adapter.provider.QueryOptions;
import com.jiuqi.nr.entity.common.db.DBUtils;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class OrgDataDBQuery
extends OrgDataQuery {
    private final JdbcTemplate jdbcTemplate;
    private final IEntityQueryParam query;
    private final OrgDataSource orgDataSource;
    private final ContextUser user;
    private final OrgQueryServer queryServer;
    private final DataBaseQueryHelper dbQueryHelper;
    private Set<String> zbs;
    private final int maxInSize;
    private Connection connection;
    private List<ITempTable> tempTables;
    private static final String MAIN_ALIAS = "MAIN";
    private static final String SUB_ALIAS = "SUB";

    public OrgDataDBQuery(JdbcTemplate jdbcTemplate, DataBaseQueryHelper dbQueryHelper, SystemIdentityService systemIdentityService, OrgCategoryClient orgCategoryClient, OrgDataSource orgDataSource, IEntityQueryParam query) {
        super(orgDataSource, query);
        this.jdbcTemplate = jdbcTemplate;
        this.dbQueryHelper = dbQueryHelper;
        this.query = query;
        this.orgDataSource = orgDataSource;
        this.user = NpContextHolder.getContext().getUser();
        boolean isAdmin = systemIdentityService.isSystemByUserId(this.user.getId()) || query.isIgnoreAuth();
        this.queryServer = isAdmin ? new AdminQuery() : new UserQuery();
        this.initZbs(orgCategoryClient);
        IDatabase database = DatabaseInstance.getDatabase();
        this.maxInSize = DBUtils.getMaxInSize(database);
        this.tempTables = new ArrayList<ITempTable>();
    }

    public void initZbs(OrgCategoryClient orgCategoryClient) {
        NpContext context = NpContextHolder.getContext();
        ContextExtension extension = context.getExtension("ENTITY_ZB_CACHE");
        Object category = extension.get(this.query.getEntityId());
        OrgCategoryDO categoryDO = null;
        if (category != null) {
            categoryDO = (OrgCategoryDO)category;
        } else {
            OrgCategoryDO orgCategory = new OrgCategoryDO();
            orgCategory.setName(this.query.getEntityId());
            PageVO list = orgCategoryClient.list(orgCategory);
            if (list.getTotal() != 0) {
                categoryDO = (OrgCategoryDO)list.getRows().get(0);
                extension.put(this.query.getEntityId(), (Serializable)categoryDO);
            }
        }
        if (categoryDO != null) {
            this.zbs = categoryDO.getAllZbs().stream().map(e -> e.getName().toLowerCase()).collect(Collectors.toSet());
        }
    }

    @Override
    public EntityResultSet getAllData() {
        List<OrgDO> allData = this.queryServer.getAllData();
        if (CollectionUtils.isEmpty(allData)) {
            return new OrgResultSet(allData, this.query, this.orgDataSource, new R());
        }
        Map<String, List<OrgDO>> parentMap = null;
        String[] keys = null;
        if (CollectionUtils.isEmpty(this.query.getMasterKey())) {
            parentMap = allData.stream().collect(Collectors.groupingBy(OrgDO::getParentcode));
        } else {
            keys = (String[])allData.stream().map(OrgDO::getCode).distinct().toArray(String[]::new);
        }
        this.setChildrenStatus(allData, parentMap, keys);
        return new OrgResultSet(allData, this.query, this.orgDataSource, new R());
    }

    @Override
    public EntityResultSet getRootData() {
        List<OrgDO> rootNodes = this.queryServer.getRootData();
        if (CollectionUtils.isEmpty(rootNodes)) {
            return new OrgResultSet(rootNodes, this.query, this.orgDataSource, new R());
        }
        String[] keys = (String[])rootNodes.stream().map(OrgDO::getCode).toArray(String[]::new);
        this.setChildrenStatus(rootNodes, null, keys);
        return new OrgResultSet(rootNodes, this.query, this.orgDataSource, new R());
    }

    @Override
    public int getChildCount(String entityKeyData, QueryOptions.TreeType treeType) {
        Map<String, Integer> childrenCount;
        Integer integer;
        int count = QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType) ? ((integer = (childrenCount = this.queryServer.getChildrenCount(false, entityKeyData)).get(entityKeyData)) == null ? 0 : integer) : this.queryServer.getAllChildrenCount(entityKeyData);
        return count;
    }

    @Override
    public Map<String, Integer> getChildCountByParent(String parentKey, QueryOptions.TreeType treeType) {
        return this.queryServer.getChildrenCountByParent(parentKey, treeType);
    }

    @Override
    public EntityResultSet getChildData(QueryOptions.TreeType treeType, String ... queryKeys) {
        List<OrgDO> childrenRows = this.queryServer.getChildrenData(treeType, true, queryKeys);
        if (CollectionUtils.isEmpty(childrenRows)) {
            return new OrgResultSet(childrenRows, this.query, this.orgDataSource, new R());
        }
        Map<String, List<OrgDO>> parentMap = null;
        if (QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType)) {
            String[] keys = (String[])childrenRows.stream().map(OrgDO::getCode).toArray(String[]::new);
            this.setChildrenStatus(childrenRows, parentMap, keys);
        } else if (QueryOptions.TreeType.ALL_CHILDREN.equals((Object)treeType)) {
            parentMap = childrenRows.stream().collect(Collectors.groupingBy(OrgDO::getParentcode));
            this.setChildrenStatus(childrenRows, parentMap, new String[0]);
        }
        return new OrgResultSet(childrenRows, this.query, this.orgDataSource, new R());
    }

    @Override
    public EntityResultSet findByEntityKeys() {
        String[] keys = this.query.getMasterKey().toArray(new String[0]);
        if (keys.length == 0) {
            return new OrgResultSet(Collections.emptyList(), this.query, this.orgDataSource, new R());
        }
        List<OrgDO> rows = this.queryServer.getByKeys(keys);
        if (CollectionUtils.isEmpty(rows)) {
            new OrgResultSet(rows, this.query, this.orgDataSource, new R());
        }
        this.setChildrenStatus(rows, null, keys);
        return new OrgResultSet(rows, this.query, this.orgDataSource, new R());
    }

    @Override
    public int getTotalCount() {
        return this.queryServer.getAllChildrenCount(null);
    }

    private void setChildrenStatus(List<OrgDO> rows, Map<String, List<OrgDO>> parentMap, String ... keys) {
        if (parentMap == null) {
            Map<String, Integer> childrenCount = this.queryServer.getChildrenCount(true, keys);
            for (OrgDO row : rows) {
                Integer count = childrenCount.get(row.getCode());
                if (count != null && count > 0) {
                    row.put("hasChildren", (Object)true);
                    row.put("isLeaf", (Object)false);
                    continue;
                }
                row.put("isLeaf", (Object)true);
                row.put("hasChildren", (Object)false);
            }
        } else {
            for (OrgDO row : rows) {
                List<OrgDO> list = parentMap.get(row.getCode());
                if (!CollectionUtils.isEmpty(list)) {
                    row.put("hasChildren", (Object)true);
                    row.put("isLeaf", (Object)false);
                    continue;
                }
                row.put("isLeaf", (Object)true);
                row.put("hasChildren", (Object)false);
            }
        }
    }

    private List<OrgDO> executeQuery(String querySql, Object[] arg, boolean simple, String alias) {
        String tableName = this.query.getEntityId();
        if (StringUtils.hasText(alias)) {
            tableName = tableName + " " + alias;
        }
        String sql = String.format("SELECT %s FROM %s WHERE %s ", simple ? "id,code,orgcode,name,parentcode" : " * ", tableName, querySql);
        this.query.getQueryContext().getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                e.trace("SQL:{}, PARAM:{}", sql, arg);
            }
        });
        return (List)this.jdbcTemplate.query(sql, rs -> {
            ArrayList<OrgDO> rows = new ArrayList<OrgDO>();
            while (rs.next()) {
                OrgDO orgDO = new OrgDO();
                if (simple) {
                    orgDO.setId(UUID.fromString(rs.getString("id")));
                    orgDO.setCode(rs.getString("code"));
                    orgDO.setOrgcode(rs.getString("orgcode"));
                    orgDO.setName(rs.getString("name"));
                    orgDO.setParentcode(rs.getString("parentcode"));
                } else {
                    for (String zb : this.zbs) {
                        orgDO.putVal(zb, rs.getObject(zb));
                    }
                }
                rows.add(orgDO);
            }
            return rows;
        }, arg);
    }

    private Map<String, Integer> executeCount(String condition, Object[] arg, String querySql, String countFiled, String alias) {
        String tableName = this.query.getEntityId();
        if (StringUtils.hasText(alias)) {
            tableName = tableName + " " + alias;
        }
        String sql = String.format("SELECT %s, count(*) as COUNT FROM %s WHERE %s GROUP BY %s", querySql, tableName, condition, countFiled);
        this.query.getQueryContext().getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                e.trace("SQL:{}, PARAM:{}", sql, arg);
            }
        });
        return (Map)this.jdbcTemplate.query(sql, rs -> {
            HashMap<String, Integer> countMap = new HashMap<String, Integer>();
            while (rs.next()) {
                String key = rs.getString(countFiled);
                int count = rs.getInt("COUNT");
                countMap.put(key, count);
            }
            return countMap;
        }, arg);
    }

    private int executeTotalCount(String querySql, Object[] arg, String alias) {
        String tableName = this.query.getEntityId();
        if (StringUtils.hasText(alias)) {
            tableName = tableName + " " + alias;
        }
        String sql = String.format("SELECT count(*) as COUNT FROM %s WHERE %s ", tableName, querySql);
        this.query.getQueryContext().getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                e.trace("SQL:{}, PARAM:{}", sql, arg);
            }
        });
        return (Integer)this.jdbcTemplate.query(sql, rs -> {
            int count = 0;
            while (rs.next()) {
                count = rs.getInt("COUNT");
            }
            return count;
        }, arg);
    }

    private void buildKeyCondition(List<String> masterKey, StringBuilder condition, List<Object> args, String alias, String filed, boolean equalModel) {
        if (CollectionUtils.isEmpty(masterKey)) {
            return;
        }
        condition.append(" ( ");
        if (masterKey.size() >= this.maxInSize) {
            try {
                ITempTable tempTable = TempTableActuator.getOneKeyTempTable((Connection)this.getConnection());
                String tableName = tempTable.getTableName();
                List primaryKeyFields = tempTable.getMeta().getPrimaryKeyFields();
                String code = (String)primaryKeyFields.get(0);
                String tempAlias = " TMP" + this.tempTables.size() + " ";
                condition.append("EXISTS ( SELECT 1").append(" FROM ").append(tableName).append(tempAlias).append(" WHERE ").append(alias).append(".").append(filed).append(equalModel ? " = " : " LIKE ").append(tempAlias).append(".").append(code).append(" )");
                ArrayList<Object[]> records = new ArrayList<Object[]>(masterKey.size());
                for (String key : masterKey) {
                    String value = equalModel ? key : "%" + key + "/%";
                    Object[] arg = new Object[]{value};
                    records.add(arg);
                }
                try {
                    tempTable.insertRecords(records);
                }
                catch (SQLException e) {
                    throw new RuntimeException("\u5411\u4e34\u65f6\u8868\u4e2d\u6dfb\u52a0\u6570\u636e\u65f6\u9519\u8bef", e);
                }
                this.tempTables.add(tempTable);
            }
            catch (SQLException e) {
                throw new RuntimeException("\u521b\u5efa\u4e34\u65f6\u8868\u5931\u8d25", e);
            }
        }
        for (String key : masterKey) {
            condition.append(" ").append(filed).append(equalModel ? "=" : " LIKE ").append(" ? ").append(" OR ");
            if (equalModel) {
                args.add(key);
                continue;
            }
            args.add("%" + key + "/%");
        }
        this.trimTrailing(condition, "OR");
        condition.append(" ) ");
    }

    private void trimTrailing(StringBuilder sql, String concat) {
        int pos = sql.lastIndexOf(concat);
        if (pos == sql.length() - (concat.length() + 1)) {
            sql.delete(pos, sql.length());
        }
    }

    private Connection getConnection() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("\u65e0\u6cd5\u83b7\u53d6\u6570\u636e\u5e93\u94fe\u63a5", e);
        }
        return this.connection;
    }

    private void release() {
        try {
            if (!CollectionUtils.isEmpty(this.tempTables)) {
                for (ITempTable tempTable : this.tempTables) {
                    tempTable.close();
                }
            }
        }
        catch (Exception e) {
            this.query.getQueryContext().getLogger().error("\u5220\u9664\u4e34\u65f6\u8868\u5931\u8d25", e);
        }
        DataSourceUtils.releaseConnection((Connection)this.connection, (DataSource)Objects.requireNonNull(this.jdbcTemplate.getDataSource()));
    }

    class UserQuery
    implements OrgQueryServer {
        UserQuery() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public List<OrgDO> getAllData() {
            List<Object> result = new ArrayList<OrgDO>();
            StringBuilder sql = new StringBuilder();
            DataBaseQueryHelper.SqlBuildDTO sqlBuildDTO = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.MAIN_ALIAS);
            sql.append((CharSequence)sqlBuildDTO.getSql());
            ArrayList<Object> arg = new ArrayList<Object>(sqlBuildDTO.getArg());
            if (sql.length() == 0) {
                return Collections.emptyList();
            }
            try {
                List<String> masterKey = OrgDataDBQuery.this.query.getMasterKey();
                if (!CollectionUtils.isEmpty(masterKey)) {
                    sql.append(" AND ");
                    OrgDataDBQuery.this.buildKeyCondition(masterKey, sql, arg, OrgDataDBQuery.MAIN_ALIAS, "CODE", true);
                }
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
                OrgDataDBQuery.this.dbQueryHelper.buildOrderBy(sql, OrgDataDBQuery.MAIN_ALIAS);
                result = OrgDataDBQuery.this.executeQuery(sql.toString(), arg.toArray(new Object[0]), false, OrgDataDBQuery.MAIN_ALIAS);
            }
            catch (Exception e) {
                OrgDataDBQuery.this.query.getQueryContext().getLogger().error("\u67e5\u8be2\u6240\u6709\u6570\u636e\u5931\u8d25", e);
            }
            finally {
                OrgDataDBQuery.this.release();
            }
            return result;
        }

        @Override
        public List<OrgDO> getRootData() {
            StringBuilder sql = new StringBuilder();
            DataBaseQueryHelper.SqlBuildDTO sqlBuildDTO = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.MAIN_ALIAS);
            if (sqlBuildDTO.getSql().length() == 0) {
                return Collections.emptyList();
            }
            sql.append((CharSequence)sqlBuildDTO.getSql());
            ArrayList<Object> arg = new ArrayList<Object>(sqlBuildDTO.getArg());
            sql.append(" AND  NOT EXISTS ( SELECT 1 FROM ").append(OrgDataDBQuery.this.query.getEntityId()).append(" ").append(OrgDataDBQuery.SUB_ALIAS).append(" WHERE ");
            DataBaseQueryHelper.SqlBuildDTO buildDTO = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.SUB_ALIAS);
            sql.append((CharSequence)buildDTO.getSql());
            arg.addAll(buildDTO.getArg());
            sql.append(" AND ").append(OrgDataDBQuery.SUB_ALIAS).append(".CODE ").append(" = ").append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTCODE");
            OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.SUB_ALIAS);
            sql.append(" ) ");
            OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
            OrgDataDBQuery.this.dbQueryHelper.buildOrderBy(sql, OrgDataDBQuery.MAIN_ALIAS);
            return OrgDataDBQuery.this.executeQuery(sql.toString(), arg.toArray(new Object[0]), false, OrgDataDBQuery.MAIN_ALIAS);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public List<OrgDO> getChildrenData(QueryOptions.TreeType treeType, boolean shorted, String ... keys) {
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> arg = new ArrayList<Object>();
            try {
                if (QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType)) {
                    OrgDataDBQuery.this.buildKeyCondition(Arrays.asList(keys), sql, arg, OrgDataDBQuery.MAIN_ALIAS, "parentcode".toUpperCase(), true);
                } else if (QueryOptions.TreeType.ALL_CHILDREN.equals((Object)treeType)) {
                    OrgDataDBQuery.this.buildKeyCondition(Arrays.asList(keys), sql, arg, OrgDataDBQuery.MAIN_ALIAS, "PARENTS", false);
                }
                sql.append(" AND ");
                DataBaseQueryHelper.SqlBuildDTO sqlBuildDTO = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.MAIN_ALIAS);
                sql.append((CharSequence)sqlBuildDTO.getSql());
                arg.addAll(sqlBuildDTO.getArg());
                OrgDataDBQuery.this.dbQueryHelper.trimTrailing(sql, "AND");
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
                if (shorted) {
                    OrgDataDBQuery.this.dbQueryHelper.buildOrderBy(sql, OrgDataDBQuery.MAIN_ALIAS);
                }
                List list = OrgDataDBQuery.this.executeQuery(sql.toString(), arg.toArray(new Object[0]), false, OrgDataDBQuery.MAIN_ALIAS);
                return list;
            }
            catch (Exception e) {
                OrgDataDBQuery.this.query.getQueryContext().getLogger().error("\u83b7\u53d6\u4e0b\u7ea7\u6570\u636e\u5931\u8d25", e);
            }
            finally {
                OrgDataDBQuery.this.release();
            }
            return Collections.emptyList();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public List<OrgDO> getByKeys(String ... keys) {
            List<Object> result = new ArrayList<OrgDO>();
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> arg = new ArrayList<Object>();
            try {
                OrgDataDBQuery.this.buildKeyCondition(Arrays.asList(keys), sql, arg, OrgDataDBQuery.MAIN_ALIAS, "CODE", true);
                sql.append(" AND ");
                DataBaseQueryHelper.SqlBuildDTO sqlBuildDTO = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.MAIN_ALIAS);
                sql.append((CharSequence)sqlBuildDTO.getSql());
                arg.addAll(sqlBuildDTO.getArg());
                OrgDataDBQuery.this.trimTrailing(sql, "AND");
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
                OrgDataDBQuery.this.dbQueryHelper.buildOrderBy(sql, OrgDataDBQuery.MAIN_ALIAS);
                result = OrgDataDBQuery.this.executeQuery(sql.toString(), arg.toArray(new Object[0]), false, OrgDataDBQuery.MAIN_ALIAS);
            }
            catch (Exception e) {
                OrgDataDBQuery.this.query.getQueryContext().getLogger().error("\u67e5\u8be2\u6307\u5b9akey\u7684\u6570\u636e\u5931\u8d25", e);
            }
            finally {
                OrgDataDBQuery.this.release();
            }
            return result;
        }

        @Override
        public int getAllChildrenCount(String key) {
            DataBaseQueryHelper.SqlBuildDTO sqlBuildDTO;
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> arg = new ArrayList<Object>();
            if (StringUtils.hasText(key)) {
                sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTS LIKE ? ");
                arg.add("%" + key + "/%");
            } else {
                sql.append(" 1 = 1");
            }
            ExecutorContext.CountModel countModel = OrgDataDBQuery.this.query.getContext().getCountModel();
            if (countModel == ExecutorContext.CountModel.LEAF_ONLY) {
                sql.append(" AND NOT EXISTS ( SELECT 1 FROM ").append(OrgDataDBQuery.this.query.getEntityId()).append(" ").append(OrgDataDBQuery.SUB_ALIAS).append(" WHERE ");
                sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTCODE = ").append(OrgDataDBQuery.MAIN_ALIAS).append(".CODE");
                if (StringUtils.hasText(key)) {
                    sql.append(" AND ");
                    sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTS LIKE ? ");
                    arg.add("%" + key + "/%");
                }
                sql.append(" AND ");
                sqlBuildDTO = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.SUB_ALIAS);
                sql.append((CharSequence)sqlBuildDTO.getSql());
                arg.addAll(sqlBuildDTO.getArg());
                if (!CollectionUtils.isEmpty(OrgDataDBQuery.this.dbQueryHelper.getRejectCodes())) {
                    sql.append(" AND ");
                    for (String code : OrgDataDBQuery.this.dbQueryHelper.getRejectCodes()) {
                        sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTS NOT LIKE ? AND ");
                        arg.add("%" + code + "/%");
                    }
                    OrgDataDBQuery.this.trimTrailing(sql, "AND");
                }
                OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.SUB_ALIAS);
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.SUB_ALIAS);
                sql.append(")");
            }
            sql.append(" AND ");
            sqlBuildDTO = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.MAIN_ALIAS);
            sql.append((CharSequence)sqlBuildDTO.getSql());
            arg.addAll(sqlBuildDTO.getArg());
            if (!CollectionUtils.isEmpty(OrgDataDBQuery.this.dbQueryHelper.getRejectCodes())) {
                sql.append(" AND ");
                for (String code : OrgDataDBQuery.this.dbQueryHelper.getRejectCodes()) {
                    sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTS NOT LIKE ? AND ");
                    arg.add("%" + code + "/%");
                }
                OrgDataDBQuery.this.trimTrailing(sql, "AND");
            }
            OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.MAIN_ALIAS);
            OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
            return OrgDataDBQuery.this.executeTotalCount(sql.toString(), arg.toArray(new Object[0]), OrgDataDBQuery.MAIN_ALIAS);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Map<String, Integer> getChildrenCount(boolean inner, String ... keys) {
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> args = new ArrayList<Object>();
            try {
                if (keys != null) {
                    OrgDataDBQuery.this.buildKeyCondition(Arrays.asList(keys), sql, args, OrgDataDBQuery.MAIN_ALIAS, "parentcode".toUpperCase(), true);
                    sql.append(" AND ");
                }
                DataBaseQueryHelper.SqlBuildDTO sqlBuildDTO = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.MAIN_ALIAS);
                sql.append((CharSequence)sqlBuildDTO.getSql());
                args.addAll(sqlBuildDTO.getArg());
                if (CollectionUtils.isEmpty(OrgDataDBQuery.this.dbQueryHelper.getRejectCodes())) {
                    OrgDataDBQuery.this.trimTrailing(sql, "AND");
                } else {
                    if (sql.lastIndexOf("AND") != sql.length() - 4) {
                        sql.append(" AND ");
                    }
                    for (String code : OrgDataDBQuery.this.dbQueryHelper.getRejectCodes()) {
                        sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTS NOT LIKE ? AND ");
                        args.add("%" + code + "/%");
                    }
                    OrgDataDBQuery.this.trimTrailing(sql, "AND");
                }
                ExecutorContext.CountModel countModel = OrgDataDBQuery.this.query.getContext().getCountModel();
                if (countModel == ExecutorContext.CountModel.LEAF_ONLY && !inner) {
                    sql.append(" AND NOT EXISTS ( SELECT 1 FROM ").append(OrgDataDBQuery.this.query.getEntityId()).append(" ").append(OrgDataDBQuery.SUB_ALIAS).append(" WHERE ");
                    sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTCODE = ").append(OrgDataDBQuery.MAIN_ALIAS).append(".CODE");
                    sql.append(" AND ");
                    DataBaseQueryHelper.SqlBuildDTO subSql = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.SUB_ALIAS);
                    sql.append((CharSequence)subSql.getSql());
                    args.addAll(subSql.getArg());
                    if (CollectionUtils.isEmpty(OrgDataDBQuery.this.dbQueryHelper.getRejectCodes())) {
                        OrgDataDBQuery.this.trimTrailing(sql, "AND");
                    } else {
                        if (sql.lastIndexOf("AND") != sql.length() - 4) {
                            sql.append(" AND ");
                        }
                        for (String code : OrgDataDBQuery.this.dbQueryHelper.getRejectCodes()) {
                            sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTS NOT LIKE ? AND ");
                            args.add("%" + code + "/%");
                        }
                        OrgDataDBQuery.this.trimTrailing(sql, "AND");
                    }
                    OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.SUB_ALIAS);
                    OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, args, OrgDataDBQuery.SUB_ALIAS);
                    sql.append(")");
                }
                OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.MAIN_ALIAS);
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, args, OrgDataDBQuery.MAIN_ALIAS);
                Map map = OrgDataDBQuery.this.executeCount(sql.toString(), args.toArray(new Object[0]), "parentcode", "parentcode", OrgDataDBQuery.MAIN_ALIAS);
                return map;
            }
            catch (Exception e) {
                OrgDataDBQuery.this.query.getQueryContext().getLogger().error("\u7edf\u8ba1\u4e0b\u7ea7\u6570\u91cf\u5931\u8d25", e);
            }
            finally {
                OrgDataDBQuery.this.release();
            }
            return Collections.emptyMap();
        }

        @Override
        public Map<String, Integer> getChildrenCountByParent(String parentKey, QueryOptions.TreeType treeType) {
            Map result;
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> arg = new ArrayList<Object>();
            String countFiled = "GROUPCODE";
            ExecutorContext.CountModel countModel = OrgDataDBQuery.this.query.getContext().getCountModel();
            if (QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType)) {
                String querySql;
                if (StringUtils.hasText(parentKey)) {
                    sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTCODE = ").append(String.format("split_part(split_part(%s.PARENTS, '%s/', 2), '/', 1)", OrgDataDBQuery.MAIN_ALIAS, parentKey));
                    sql.append(" AND ");
                    sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTS LIKE ? ");
                    arg.add("%" + parentKey + "/%");
                    sql.append(" AND ");
                    DataBaseQueryHelper.SqlBuildDTO sqlBuildDTO = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.MAIN_ALIAS);
                    sql.append((CharSequence)sqlBuildDTO.getSql());
                    arg.addAll(sqlBuildDTO.getArg());
                    if (!CollectionUtils.isEmpty(OrgDataDBQuery.this.dbQueryHelper.getRejectCodes())) {
                        sql.append(" AND ");
                        for (String code : OrgDataDBQuery.this.dbQueryHelper.getRejectCodes()) {
                            sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTS NOT LIKE ? AND ");
                            arg.add("%" + code + "/%");
                        }
                        OrgDataDBQuery.this.trimTrailing(sql, "AND");
                    }
                    querySql = String.format("split_part(split_part(%s.PARENTS, '%s/', 2), '/', 1) AS %s", OrgDataDBQuery.MAIN_ALIAS, parentKey, countFiled);
                    OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.MAIN_ALIAS);
                    OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
                    if (countModel == ExecutorContext.CountModel.LEAF_ONLY) {
                        sql.append(" AND NOT EXISTS ( SELECT 1 FROM ").append(OrgDataDBQuery.this.query.getEntityId()).append(" ").append(OrgDataDBQuery.SUB_ALIAS).append(" WHERE ");
                        sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTCODE = ").append(OrgDataDBQuery.MAIN_ALIAS).append(".CODE");
                        sql.append(" AND ");
                        if (!CollectionUtils.isEmpty(OrgDataDBQuery.this.dbQueryHelper.getRejectCodes())) {
                            for (String code : OrgDataDBQuery.this.dbQueryHelper.getRejectCodes()) {
                                sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTS NOT LIKE ? AND ");
                                arg.add("%" + code + "/%");
                            }
                            OrgDataDBQuery.this.trimTrailing(sql, "AND");
                        }
                        DataBaseQueryHelper.SqlBuildDTO subSql = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.SUB_ALIAS);
                        sql.append((CharSequence)subSql.getSql());
                        arg.addAll(subSql.getArg());
                        OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.SUB_ALIAS);
                        OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.SUB_ALIAS);
                        sql.append(" ) ");
                    }
                } else {
                    List<OrgDO> rootData = this.getRootData();
                    return this.getChildrenCount(false, (String[])rootData.stream().map(OrgDO::getCode).toArray(String[]::new));
                }
                result = OrgDataDBQuery.this.executeCount(sql.toString(), arg.toArray(new Object[0]), querySql, countFiled, OrgDataDBQuery.MAIN_ALIAS);
            } else {
                if (!StringUtils.hasText(parentKey)) {
                    List<OrgDO> rootData = this.getRootData();
                    if (CollectionUtils.isEmpty(rootData)) {
                        return new HashMap<String, Integer>();
                    }
                    HashMap<String, Integer> result2 = new HashMap<String, Integer>();
                    for (OrgDO root : rootData) {
                        int allChildrenCount = this.getAllChildrenCount(root.getCode());
                        result2.put(root.getCode(), allChildrenCount);
                    }
                    return result2;
                }
                sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTS LIKE ? ");
                arg.add("%" + parentKey + "/%");
                String querySql = String.format("split_part(split_part(parents, '%s/', 2), '/', 1) AS %s", parentKey, countFiled);
                sql.append(" AND ");
                DataBaseQueryHelper.SqlBuildDTO sqlBuildDTO = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.MAIN_ALIAS);
                sql.append((CharSequence)sqlBuildDTO.getSql());
                arg.addAll(sqlBuildDTO.getArg());
                if (!CollectionUtils.isEmpty(OrgDataDBQuery.this.dbQueryHelper.getRejectCodes())) {
                    if (sql.lastIndexOf("AND") != sql.length() - 4) {
                        sql.append(" AND ");
                    }
                    for (String code : OrgDataDBQuery.this.dbQueryHelper.getRejectCodes()) {
                        sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTS NOT LIKE ? AND ");
                        arg.add("%" + code + "/%");
                    }
                    OrgDataDBQuery.this.trimTrailing(sql, "AND");
                }
                OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.MAIN_ALIAS);
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
                if (countModel == ExecutorContext.CountModel.LEAF_ONLY) {
                    if (StringUtils.hasText(parentKey)) {
                        sql.append(" AND ").append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTCODE != ? ");
                        arg.add(parentKey);
                    }
                    sql.append(" AND NOT EXISTS ( SELECT 1 FROM ").append(OrgDataDBQuery.this.query.getEntityId()).append(" ").append(OrgDataDBQuery.SUB_ALIAS).append(" WHERE ");
                    sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTCODE = ").append(OrgDataDBQuery.MAIN_ALIAS).append(".CODE");
                    if (StringUtils.hasText(parentKey)) {
                        sql.append(" AND ");
                        sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTS LIKE ? ");
                        arg.add("%" + parentKey + "/%");
                        if (StringUtils.hasText(parentKey)) {
                            sql.append(" AND ").append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTCODE != ? ");
                            arg.add(parentKey);
                        }
                    }
                    sql.append(" AND ");
                    DataBaseQueryHelper.SqlBuildDTO subSql = OrgDataDBQuery.this.dbQueryHelper.buildAuthSql(OrgDataDBQuery.this.query.getEntityId(), OrgDataOption.AuthType.ACCESS, OrgDataDBQuery.this.user.getId(), OrgDataDBQuery.SUB_ALIAS);
                    sql.append((CharSequence)subSql.getSql());
                    arg.addAll(subSql.getArg());
                    if (!CollectionUtils.isEmpty(OrgDataDBQuery.this.dbQueryHelper.getRejectCodes())) {
                        if (sql.lastIndexOf("AND") != sql.length() - 4) {
                            sql.append(" AND ");
                        }
                        for (String code : OrgDataDBQuery.this.dbQueryHelper.getRejectCodes()) {
                            sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTS NOT LIKE ? AND ");
                            arg.add("%" + code + "/%");
                        }
                        OrgDataDBQuery.this.trimTrailing(sql, "AND");
                    }
                    OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.SUB_ALIAS);
                    OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.SUB_ALIAS);
                    sql.append(")");
                }
                result = OrgDataDBQuery.this.executeCount(sql.toString(), arg.toArray(new Object[0]), querySql, countFiled, OrgDataDBQuery.MAIN_ALIAS);
                if (countModel != ExecutorContext.CountModel.LEAF_ONLY) {
                    result.forEach((k, v) -> result.put(k, (Integer)result.get(k) - 1));
                }
            }
            return result;
        }
    }

    class AdminQuery
    implements OrgQueryServer {
        AdminQuery() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public List<OrgDO> getAllData() {
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> arg = new ArrayList<Object>();
            try {
                List<String> masterKey = OrgDataDBQuery.this.query.getMasterKey();
                if (CollectionUtils.isEmpty(masterKey)) {
                    sql.append(" 1 = 1 ");
                } else {
                    OrgDataDBQuery.this.buildKeyCondition(masterKey, sql, arg, OrgDataDBQuery.MAIN_ALIAS, "CODE", true);
                }
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
                OrgDataDBQuery.this.dbQueryHelper.buildOrderBy(sql, OrgDataDBQuery.MAIN_ALIAS);
                List list = OrgDataDBQuery.this.executeQuery(sql.toString(), arg.toArray(new Object[0]), false, OrgDataDBQuery.MAIN_ALIAS);
                return list;
            }
            catch (Exception e) {
                OrgDataDBQuery.this.query.getQueryContext().getLogger().error("\u67e5\u8be2\u6240\u6709\u884c\u6570\u636e\u5931\u8d25", e);
            }
            finally {
                OrgDataDBQuery.this.release();
            }
            return Collections.emptyList();
        }

        @Override
        public List<OrgDO> getRootData() {
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> arg = new ArrayList<Object>();
            sql.append(" 1=1 AND NOT EXISTS ( SELECT 1 FROM ");
            sql.append(OrgDataDBQuery.this.query.getEntityId()).append(" SUB ");
            sql.append(" WHERE 1 = 1 ");
            sql.append(" AND SUB.CODE = MAIN.PARENTCODE ");
            OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.SUB_ALIAS);
            sql.append(" ) ");
            OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
            OrgDataDBQuery.this.dbQueryHelper.buildOrderBy(sql, OrgDataDBQuery.MAIN_ALIAS);
            return OrgDataDBQuery.this.executeQuery(sql.toString(), arg.toArray(new Object[0]), false, OrgDataDBQuery.MAIN_ALIAS);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public List<OrgDO> getChildrenData(QueryOptions.TreeType treeType, boolean shorted, String ... keys) {
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> arg = new ArrayList<Object>();
            try {
                if (QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType)) {
                    OrgDataDBQuery.this.buildKeyCondition(Arrays.asList(keys), sql, arg, OrgDataDBQuery.MAIN_ALIAS, "parentcode".toUpperCase(), true);
                } else if (QueryOptions.TreeType.ALL_CHILDREN.equals((Object)treeType)) {
                    OrgDataDBQuery.this.buildKeyCondition(Arrays.asList(keys), sql, arg, OrgDataDBQuery.MAIN_ALIAS, "PARENTS", false);
                }
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
                if (shorted) {
                    OrgDataDBQuery.this.dbQueryHelper.buildOrderBy(sql, OrgDataDBQuery.MAIN_ALIAS);
                }
                List list = OrgDataDBQuery.this.executeQuery(sql.toString(), arg.toArray(new Object[0]), false, OrgDataDBQuery.MAIN_ALIAS);
                return list;
            }
            catch (Exception e) {
                OrgDataDBQuery.this.query.getQueryContext().getLogger().error("\u67e5\u8be2\u4e0b\u7ea7\u6570\u636e\u5931\u8d25", e);
            }
            finally {
                OrgDataDBQuery.this.release();
            }
            return Collections.emptyList();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public List<OrgDO> getByKeys(String ... keys) {
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> arg = new ArrayList<Object>();
            try {
                OrgDataDBQuery.this.buildKeyCondition(Arrays.asList(keys), sql, arg, OrgDataDBQuery.MAIN_ALIAS, "CODE", true);
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
                OrgDataDBQuery.this.dbQueryHelper.buildOrderBy(sql, OrgDataDBQuery.MAIN_ALIAS);
                List list = OrgDataDBQuery.this.executeQuery(sql.toString(), arg.toArray(new Object[0]), false, OrgDataDBQuery.MAIN_ALIAS);
                return list;
            }
            catch (Exception e) {
                OrgDataDBQuery.this.query.getQueryContext().getLogger().error("\u6839\u636e\u6307\u5b9akey\u83b7\u53d6\u6570\u636e\u5931\u8d25", e);
            }
            finally {
                OrgDataDBQuery.this.release();
            }
            return Collections.emptyList();
        }

        @Override
        public int getAllChildrenCount(String key) {
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> arg = new ArrayList<Object>();
            if (StringUtils.hasText(key)) {
                sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTS LIKE ? ");
                arg.add("%" + key + "/%");
            } else {
                sql.append(" 1 = 1");
            }
            OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.MAIN_ALIAS);
            ExecutorContext.CountModel countModel = OrgDataDBQuery.this.query.getContext().getCountModel();
            if (countModel == ExecutorContext.CountModel.LEAF_ONLY) {
                sql.append(" AND NOT EXISTS ( SELECT 1 FROM ").append(OrgDataDBQuery.this.query.getEntityId()).append(" ").append(OrgDataDBQuery.SUB_ALIAS).append(" WHERE ");
                sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTCODE = ").append(OrgDataDBQuery.MAIN_ALIAS).append(".CODE");
                if (StringUtils.hasText(key)) {
                    sql.append(" AND ");
                    sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTS LIKE ? ");
                    arg.add("%" + key + "/%");
                }
                OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.SUB_ALIAS);
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.SUB_ALIAS);
                sql.append(")");
            }
            OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
            return OrgDataDBQuery.this.executeTotalCount(sql.toString(), arg.toArray(new Object[0]), OrgDataDBQuery.MAIN_ALIAS);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Map<String, Integer> getChildrenCount(boolean inner, String ... keys) {
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> arg = new ArrayList<Object>();
            try {
                if (keys != null) {
                    OrgDataDBQuery.this.buildKeyCondition(Arrays.asList(keys), sql, arg, OrgDataDBQuery.MAIN_ALIAS, "parentcode".toUpperCase(), true);
                } else {
                    sql.append("1=1");
                }
                ExecutorContext.CountModel countModel = OrgDataDBQuery.this.query.getContext().getCountModel();
                if (countModel == ExecutorContext.CountModel.LEAF_ONLY && !inner) {
                    sql.append(" AND NOT EXISTS ( SELECT 1 FROM ").append(OrgDataDBQuery.this.query.getEntityId()).append(" ").append(OrgDataDBQuery.SUB_ALIAS).append(" WHERE ");
                    sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTCODE = ").append(OrgDataDBQuery.MAIN_ALIAS).append(".CODE");
                    if (keys != null) {
                        sql.append(" AND");
                        OrgDataDBQuery.this.buildKeyCondition(Arrays.asList(keys), sql, arg, OrgDataDBQuery.SUB_ALIAS, "parentcode".toUpperCase(), true);
                    }
                    OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.SUB_ALIAS);
                    OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.SUB_ALIAS);
                    sql.append(")");
                }
                OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.MAIN_ALIAS);
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
                Map map = OrgDataDBQuery.this.executeCount(sql.toString(), arg.toArray(new Object[0]), "parentcode", "parentcode", OrgDataDBQuery.MAIN_ALIAS);
                return map;
            }
            catch (Exception e) {
                OrgDataDBQuery.this.query.getQueryContext().getLogger().error("\u7edf\u8ba1\u4e0b\u7ea7\u6570\u91cf\u5931\u8d25", e);
            }
            finally {
                OrgDataDBQuery.this.release();
            }
            return Collections.emptyMap();
        }

        @Override
        public Map<String, Integer> getChildrenCountByParent(String parentKey, QueryOptions.TreeType treeType) {
            Map result;
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> arg = new ArrayList<Object>();
            String countFiled = "GROUPCODE";
            ExecutorContext.CountModel countModel = OrgDataDBQuery.this.query.getContext().getCountModel();
            if (QueryOptions.TreeType.DIRECT_CHILDREN.equals((Object)treeType)) {
                String querySql;
                if (StringUtils.hasText(parentKey)) {
                    sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTCODE = ").append(String.format("split_part(split_part(%s.PARENTS, '%s/', 2), '/', 1)", OrgDataDBQuery.MAIN_ALIAS, parentKey));
                    sql.append(" AND ");
                    sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTS LIKE ? ");
                    arg.add("%" + parentKey + "/%");
                    if (countModel == ExecutorContext.CountModel.LEAF_ONLY) {
                        sql.append(" AND NOT EXISTS ( SELECT 1 FROM ").append(OrgDataDBQuery.this.query.getEntityId()).append(" ").append(OrgDataDBQuery.SUB_ALIAS).append(" WHERE ");
                        sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTCODE = ").append(OrgDataDBQuery.MAIN_ALIAS).append(".CODE");
                        OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.SUB_ALIAS);
                        OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.SUB_ALIAS);
                        sql.append(")");
                    }
                    querySql = String.format("split_part(split_part(%s.PARENTS, '%s/', 2), '/', 1) AS %s", OrgDataDBQuery.MAIN_ALIAS, parentKey, countFiled);
                } else {
                    querySql = "parentcode";
                    countFiled = "parentcode";
                    sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTCODE in ( SELECT distinct split_part(SUB.PARENTS, '/', 1) FROM ").append(OrgDataDBQuery.this.query.getEntityId()).append(" ").append(OrgDataDBQuery.SUB_ALIAS).append(" WHERE 1 = 1");
                    if (countModel == ExecutorContext.CountModel.LEAF_ONLY) {
                        sql.append(" AND NOT EXISTS ( SELECT 1 FROM ").append(OrgDataDBQuery.this.query.getEntityId()).append(" ").append("SEC").append(" WHERE ");
                        sql.append("SEC").append(".PARENTCODE = ").append(OrgDataDBQuery.SUB_ALIAS).append(".CODE");
                        OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, "SEC");
                        sql.append(")");
                    }
                    OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.SUB_ALIAS);
                    sql.append(")");
                }
                if (countModel == ExecutorContext.CountModel.LEAF_ONLY) {
                    sql.append(" AND NOT EXISTS ( SELECT 1 FROM ").append(OrgDataDBQuery.this.query.getEntityId()).append(" ").append("THR").append(" WHERE ");
                    sql.append("THR").append(".PARENTCODE = ").append(OrgDataDBQuery.MAIN_ALIAS).append(".CODE");
                    OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, "THR");
                    sql.append(")");
                }
                OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.MAIN_ALIAS);
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
                result = OrgDataDBQuery.this.executeCount(sql.toString(), arg.toArray(new Object[0]), querySql, countFiled, OrgDataDBQuery.MAIN_ALIAS);
            } else {
                String querySql;
                if (StringUtils.hasText(parentKey)) {
                    sql.append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTS LIKE ? ");
                    arg.add("%" + parentKey + "/%");
                    querySql = String.format("split_part(split_part(%s.PARENTS, '%s/', 2), '/', 1) AS %s", OrgDataDBQuery.MAIN_ALIAS, parentKey, countFiled);
                } else {
                    querySql = String.format("split_part(%s.PARENTS, '/', 1) AS %s", OrgDataDBQuery.MAIN_ALIAS, countFiled);
                    sql.append(" 1 = 1 ");
                }
                if (countModel == ExecutorContext.CountModel.LEAF_ONLY) {
                    if (StringUtils.hasText(parentKey)) {
                        sql.append(" AND ").append(OrgDataDBQuery.MAIN_ALIAS).append(".PARENTCODE != ?");
                        arg.add(parentKey);
                    }
                    sql.append(" AND NOT EXISTS ( SELECT 1 FROM ").append(OrgDataDBQuery.this.query.getEntityId()).append(" ").append(OrgDataDBQuery.SUB_ALIAS).append(" WHERE ");
                    sql.append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTCODE = ").append(OrgDataDBQuery.MAIN_ALIAS).append(".CODE");
                    if (StringUtils.hasText(parentKey)) {
                        sql.append(" AND ").append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTS LIKE ? ");
                        arg.add("%" + parentKey + "/%");
                        sql.append(" AND ").append(OrgDataDBQuery.SUB_ALIAS).append(".PARENTCODE != ?");
                        arg.add(parentKey);
                    }
                    OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.SUB_ALIAS);
                    OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.SUB_ALIAS);
                    sql.append(")");
                }
                OrgDataDBQuery.this.dbQueryHelper.buildSqlExpression(OrgDataDBQuery.this.query.getFilter() == null ? "" : OrgDataDBQuery.this.query.getFilter().getExpression(), sql, OrgDataDBQuery.MAIN_ALIAS);
                OrgDataDBQuery.this.dbQueryHelper.buildCondition(OrgDataDBQuery.this.query.getVersionDate(), sql, arg, OrgDataDBQuery.MAIN_ALIAS);
                result = OrgDataDBQuery.this.executeCount(sql.toString(), arg.toArray(new Object[0]), querySql, countFiled, OrgDataDBQuery.MAIN_ALIAS);
                if (countModel != ExecutorContext.CountModel.LEAF_ONLY) {
                    result.forEach((k, v) -> result.put(k, (Integer)result.get(k) - 1));
                }
            }
            return result;
        }
    }

    static interface OrgQueryServer {
        public List<OrgDO> getAllData();

        public List<OrgDO> getRootData();

        public List<OrgDO> getChildrenData(QueryOptions.TreeType var1, boolean var2, String ... var3);

        public List<OrgDO> getByKeys(String ... var1);

        public Map<String, Integer> getChildrenCount(boolean var1, String ... var2);

        public int getAllChildrenCount(String var1);

        public Map<String, Integer> getChildrenCountByParent(String var1, QueryOptions.TreeType var2);
    }
}

