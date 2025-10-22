/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.bi.database.paging.OrderField
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.paging.OrderField;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.exception.DBParaException;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import java.sql.Connection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractDataFieldDao<DO extends DataFieldDO>
extends BaseDao
implements IDataFieldDao<DO> {
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(AbstractDataFieldDao.class);
    private static final String ROW_NUM = "RPN";

    @Override
    public String insert(DO dataFieldDO) throws DataAccessException {
        Assert.notNull(dataFieldDO, "dataFieldDO must not be null.");
        if (((DataFieldDO)dataFieldDO).getKey() == null) {
            ((DataFieldDO)dataFieldDO).setKey(UUID.randomUUID().toString());
        }
        if (((DataFieldDO)dataFieldDO).getUpdateTime() == null) {
            ((DataFieldDO)dataFieldDO).setUpdateTime(Instant.now());
        }
        super.insert(dataFieldDO);
        return ((DataFieldDO)dataFieldDO).getKey();
    }

    @Override
    public void delete(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete(key);
    }

    @Override
    public void deleteByDataScheme(String schemeKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        super.deleteBy(new String[]{"DF_DS_KEY"}, new Object[]{schemeKey});
    }

    @Override
    public void update(DO dataFieldDO) throws DataAccessException {
        Assert.notNull(dataFieldDO, "dataFieldDO must not be null.");
        Assert.notNull((Object)((DataFieldDO)dataFieldDO).getKey(), "dataFieldKey must not be null.");
        if (((DataFieldDO)dataFieldDO).getUpdateTime() == null) {
            ((DataFieldDO)dataFieldDO).setUpdateTime(Instant.now());
        }
        super.update(dataFieldDO);
    }

    @Override
    public DO get(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        return (DO)((DataFieldDO)super.getByKey(key, this.getClz()));
    }

    @Override
    public List<DO> getByCode(String code) throws DataAccessException {
        Assert.notNull((Object)code, "code must not be null.");
        return super.list(new String[]{"DF_CODE"}, (Object[])new String[]{code}, this.getClz());
    }

    @Override
    public String[] batchInsert(List<DO> dataFieldDO) throws DataAccessException {
        Assert.notNull(dataFieldDO, "dataFieldDO must not be null.");
        if (dataFieldDO.isEmpty()) {
            return new String[0];
        }
        String[] keys = new String[dataFieldDO.size()];
        for (int i = 0; i < dataFieldDO.size(); ++i) {
            DataFieldDO dataField = (DataFieldDO)dataFieldDO.get(i);
            Assert.notNull((Object)dataField, "Collection should not contain null.");
            if (dataField.getKey() == null) {
                dataField.setKey(UUID.randomUUID().toString());
            }
            if (dataField.getUpdateTime() == null) {
                dataField.setUpdateTime(Instant.now());
            }
            keys[i] = dataField.getKey();
        }
        if (dataFieldDO.isEmpty()) {
            return new String[0];
        }
        super.insert(dataFieldDO.toArray());
        return keys;
    }

    @Override
    public void batchDelete(List<String> keys) throws DataAccessException {
        Assert.notNull(keys, "keys must not be null.");
        for (String key : keys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        super.delete(keys.toArray());
    }

    @Override
    public void batchUpdate(List<DO> dataFieldDO) throws DataAccessException {
        Assert.notNull(dataFieldDO, "dataFieldDO must not be null.");
        for (DataFieldDO dataField : dataFieldDO) {
            Assert.notNull((Object)dataField, "Collection should not contain null.");
            Assert.notNull((Object)dataField.getKey(), "dataFieldKey must not be null.");
            if (dataField.getUpdateTime() != null) continue;
            dataField.setUpdateTime(Instant.now());
        }
        if (dataFieldDO.isEmpty()) {
            return;
        }
        super.update(dataFieldDO.toArray());
    }

    @Override
    public List<DO> batchGet(List<String> keys) throws DataAccessException {
        Assert.notNull(keys, "keys must not be null.");
        for (String key : keys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("keys", keys);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " where " + "DF_KEY" + " in (:keys) ";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public List<DO> getAll() throws DataAccessException {
        return super.list(this.getClz());
    }

    @Override
    public List<DO> getByTable(String table) throws DataAccessException {
        Assert.notNull((Object)table, "table must not be null.");
        return super.list(new String[]{"DF_DT_KEY"}, (Object[])new String[]{table}, this.getClz());
    }

    @Override
    public List<DO> batchGetByTable(List<String> tableKeys) throws DataAccessException {
        Assert.notNull(tableKeys, "tables must not be null.");
        for (String key : tableKeys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("tableKeys", tableKeys);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " where " + "DF_DT_KEY" + " in (:tableKeys) ";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public void deleteByTable(String tableKey) throws DataAccessException {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        super.deleteBy(new String[]{"DF_DT_KEY"}, new String[]{tableKey});
    }

    @Override
    public void batchDeleteByTable(List<String> tableKeys) throws DataAccessException {
        Assert.notNull(tableKeys, "tableKeys must not be null.");
        for (String key : tableKeys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("tableKeys", tableKeys);
        String sql = "delete from " + this.tablename + " where " + "DF_DT_KEY" + " IN ( :tableKeys )";
        this.logger.debug(sql);
        this.namedParameterJdbcTemplate.update(sql, (SqlParameterSource)sqlParameterSource);
    }

    @Override
    public List<DO> getByTableAndType(String table, DataFieldType[] dataFieldType) throws DataAccessException {
        return super.list(new String[]{"DF_DT_KEY", "DF_DATATYPE"}, new Object[]{table, dataFieldType}, this.getClz());
    }

    @Override
    public List<DO> getByTableAndKind(String table, int dataFieldKinds) throws DataAccessException {
        List<Integer> kinds = AbstractDataFieldDao.getKindValues(dataFieldKinds);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("table", (Object)table);
        sqlParameterSource.addValue("kinds", kinds);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " where " + "DF_DT_KEY" + " = :table AND " + "DF_KIND" + " IN ( :kinds ) order by " + "DF_KIND" + " DESC , " + "DF_ORDER";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public void deleteByTableAndKind(String table, int dataFieldKinds) throws DataAccessException {
        Assert.notNull((Object)table, "table must not be null.");
        List<Integer> kinds = AbstractDataFieldDao.getKindValues(dataFieldKinds);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("table", (Object)table);
        sqlParameterSource.addValue("kinds", kinds);
        String sql = "delete from " + this.tablename + " where " + "DF_DT_KEY" + " =  :table AND " + "DF_KIND" + " IN ( :kinds )";
        this.logger.debug(sql);
        this.namedParameterJdbcTemplate.update(sql, (SqlParameterSource)sqlParameterSource);
    }

    @Override
    public void deleteBySchemeAndKind(String scheme, int dataFieldKinds) throws DataAccessException {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<Integer> kinds = AbstractDataFieldDao.getKindValues(dataFieldKinds);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("scheme", (Object)scheme);
        sqlParameterSource.addValue("kinds", kinds);
        String sql = "delete from " + this.tablename + " where " + "DF_DS_KEY" + " =  :scheme AND " + "DF_KIND" + " IN ( :kinds )";
        this.logger.debug(sql);
        this.namedParameterJdbcTemplate.update(sql, (SqlParameterSource)sqlParameterSource);
    }

    @Override
    public List<DO> getBySchemeAndKind(String scheme, int dataFieldKinds) throws DataAccessException {
        List<Integer> kinds = AbstractDataFieldDao.getKindValues(dataFieldKinds);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("scheme", (Object)scheme);
        sqlParameterSource.addValue("kinds", kinds);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " where " + "DF_DS_KEY" + " = :scheme AND " + "DF_KIND" + " IN ( :kinds ) order by " + "DF_KIND" + " DESC , " + "DF_ORDER";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public DO getByTableAndCode(String table, String code) throws DataAccessException {
        return (DO)((DataFieldDO)super.list(new String[]{"DF_DT_KEY", "DF_CODE"}, new Object[]{table, code}, this.getClz()).stream().findFirst().orElse(null));
    }

    @Override
    public List<DO> getByTableAndCode(String table, List<String> codes, int interestKeys) throws DataAccessException {
        Assert.notNull((Object)table, "table must not be null.");
        Assert.notNull(codes, "code must not be null.");
        int number = 1000;
        int limit = (codes.size() + number - 1) / number;
        ArrayList fields = new ArrayList();
        List<Integer> kindValues = AbstractDataFieldDao.getKindValues(interestKeys);
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            List subCodes = codes.stream().skip(i * number).limit(number).collect(Collectors.toList());
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
            sqlParameterSource.addValue("table", (Object)table);
            sqlParameterSource.addValue("codes", subCodes);
            sqlParameterSource.addValue("kind", (Object)kindValues);
            RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
            String sql = this.selectSQL + " where " + "DF_DT_KEY" + " = :table AND " + "DF_CODE" + " IN ( :codes ) AND " + "DF_KIND" + " IN ( :kind )";
            this.logger.debug(sql);
            fields.addAll(this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper));
        });
        return fields;
    }

    @Override
    public List<DO> getByScheme(String scheme) throws DataAccessException {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        return super.list(new String[]{"DF_DS_KEY"}, new Object[]{scheme}, this.getClz());
    }

    @Override
    public List<DO> getByCondition(String scheme, String code) throws DataAccessException {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        return super.list(new String[]{"DF_DS_KEY", "DF_CODE"}, new Object[]{scheme, code}, this.getClz());
    }

    @Override
    public List<DO> getByCondition(String scheme, List<String> code, int interestKeys) throws DataAccessException {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull(code, "code must not be null.");
        int number = 1000;
        int limit = (code.size() + number - 1) / number;
        ArrayList fields = new ArrayList();
        List<Integer> kindValues = AbstractDataFieldDao.getKindValues(interestKeys);
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            List subCodes = code.stream().skip(i * number).limit(number).collect(Collectors.toList());
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
            sqlParameterSource.addValue("scheme", (Object)scheme);
            sqlParameterSource.addValue("code", subCodes);
            sqlParameterSource.addValue("kind", (Object)kindValues);
            RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
            String sql = this.selectSQL + " where " + "DF_DS_KEY" + " = :scheme AND " + "DF_CODE" + " IN ( :code ) AND " + "DF_KIND" + " IN ( :kind )";
            this.logger.debug(sql);
            List query = this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
            fields.addAll(query);
        });
        return fields;
    }

    @Override
    public List<DO> getByCondition(String scheme, String code, int interestKeys) throws DataAccessException {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        List<Integer> kinds = AbstractDataFieldDao.getKindValues(interestKeys);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("scheme", (Object)scheme);
        sqlParameterSource.addValue("code", (Object)code);
        sqlParameterSource.addValue("kinds", kinds);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " where " + "DF_DS_KEY" + " = :scheme AND " + "DF_CODE" + " = :code AND " + "DF_KIND" + " IN ( :kinds )";
        this.logger.debug(sql);
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public DO getByTableCondition(String table, String code, int interestKeys) throws DataAccessException {
        Assert.notNull((Object)table, "table must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        List<Integer> kinds = AbstractDataFieldDao.getKindValues(interestKeys);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("table", (Object)table);
        sqlParameterSource.addValue("code", (Object)code);
        sqlParameterSource.addValue("kinds", kinds);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " where " + "DF_DT_KEY" + " = :table AND " + "DF_CODE" + " = :code AND " + "DF_KIND" + " IN ( :kinds )";
        this.logger.debug(sql);
        return (DO)((DataFieldDO)this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper).stream().findFirst().orElse(null));
    }

    public static List<Integer> getKindValues(int type) {
        DataFieldKind[] values = DataFieldKind.interestType((int)type);
        ArrayList<Integer> kinds = new ArrayList<Integer>(values.length);
        for (DataFieldKind value : values) {
            kinds.add(value.getValue());
        }
        if (kinds.isEmpty()) {
            throw new IllegalArgumentException("interestKeys \u4e0d\u5408\u6cd5");
        }
        return kinds;
    }

    @Override
    public List<DO> searchBy(List<String> schemes, String table, String keyword, int kind, int skip, int limit) {
        StringBuilder stringBuilder = new StringBuilder(this.selectSQL).append(" where ( UPPER(");
        stringBuilder.append("DF_TITLE").append(") like :keyword OR UPPER(").append("DF_CODE").append(") like :keyword )");
        MapSqlParameterSource sqlParameterSource = this.getMapSqlParameterSource(null, table, keyword, kind);
        if (!CollectionUtils.isEmpty(schemes)) {
            stringBuilder.append(" AND ").append("DF_DS_KEY").append(" IN ( :schemes ) ");
            sqlParameterSource.addValue("schemes", schemes);
        }
        if (table != null) {
            stringBuilder.append(" AND ").append("DF_DT_KEY").append(" = :table ");
        }
        stringBuilder.append(" AND ").append("DF_KIND").append(" IN ( :kinds )");
        stringBuilder.append(" ORDER BY ").append("DF_CODE");
        String sql = stringBuilder.toString();
        this.logger.debug(sql);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<DO> limit(int skip, int limit, List<OrderField> orderFields, String sql, MapSqlParameterSource sqlParameterSource) {
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        if (skip < 0) throw new DBParaException();
        if (skip > limit) {
            throw new DBParaException();
        }
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        Assert.notNull((Object)dataSource, "dataSource must not be null.");
        try (Connection connection = dataSource.getConnection();){
            IDatabase iDatabase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            IPagingSQLBuilder sqlBuilder = iDatabase.createPagingSQLBuilder();
            sqlBuilder.setRawSQL(sql);
            if (sqlBuilder.getOrderFields() != null) {
                sqlBuilder.getOrderFields().addAll(orderFields);
            }
            sql = sqlBuilder.buildSQL(skip, limit);
            this.logger.debug(sql);
            List list = this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
            return list;
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u5931\u8d25", e);
        }
    }

    private MapSqlParameterSource getMapSqlParameterSource(String scheme, String table, String keyword, int kind) {
        List<Integer> kinds = AbstractDataFieldDao.getKindValues(kind);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        if (scheme != null) {
            sqlParameterSource.addValue("scheme", (Object)scheme);
        }
        if (table != null) {
            sqlParameterSource.addValue("table", (Object)table);
        }
        if (StringUtils.hasLength(keyword)) {
            keyword = "%" + keyword.toUpperCase() + "%";
            sqlParameterSource.addValue("keyword", (Object)keyword);
        }
        sqlParameterSource.addValue("kinds", kinds);
        return sqlParameterSource;
    }

    @Override
    public int getRowNumber(String table, String field, int kind) {
        Assert.notNull((Object)field, "field must not be null.");
        MapSqlParameterSource mapSqlParameterSource = this.getMapSqlParameterSource(null, table, null, kind);
        mapSqlParameterSource.addValue("field", (Object)field);
        String sqlBuilder = "SELECT PAGO.%s FROM %s PAG INNER JOIN (\tSELECT ROWNUM %s , PAGI.%s FROM (\t\tSELECT %s FROM %s WHERE %s = :table AND %s in (:kinds) \tORDER BY %s DESC, %s \t) PAGI ) PAGO on PAGO.%s = PAG.%s WHERE PAG.%s = :field";
        String sql = String.format(sqlBuilder, ROW_NUM, this.tablename, ROW_NUM, "DF_KEY", "DF_KEY", this.tablename, "DF_DT_KEY", "DF_KIND", "DF_KIND", "DF_ORDER", "DF_KEY", "DF_KEY", "DF_KEY");
        this.logger.debug(sql);
        Integer number = (Integer)this.namedParameterJdbcTemplate.queryForObject(sql, (SqlParameterSource)mapSqlParameterSource, Integer.class);
        return number == null ? 0 : number;
    }

    @Override
    public List<DO> filterField(FieldSearchQuery fieldSearchQuery) {
        Assert.notNull((Object)fieldSearchQuery, "fieldSearchQuery must not be null.");
        StringBuilder filter = new StringBuilder(this.selectSQL);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        this.parseFieldSearchQuery(fieldSearchQuery, filter, sqlParameterSource);
        String order = fieldSearchQuery.getOrder();
        String sql = filter.toString();
        ArrayList<OrderField> orderFields = new ArrayList<OrderField>();
        if (!StringUtils.hasLength(order)) {
            orderFields.add(new OrderField("DF_CODE"));
        } else {
            String[] orderFieldsStr;
            for (String orderFieldStr : orderFieldsStr = order.split(",")) {
                if (!StringUtils.hasLength(orderFieldStr = orderFieldStr.trim())) continue;
                String[] fieldOrder = orderFieldStr.split(" ");
                OrderField orderField = new OrderField(fieldOrder[0]);
                if (fieldOrder.length > 1) {
                    orderField.setOrderMode(fieldOrder[1]);
                }
                orderFields.add(orderField);
            }
        }
        Integer limit = fieldSearchQuery.getLimit();
        Integer skip = fieldSearchQuery.getSkip();
        if (limit == null || skip == null) {
            RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
            sql = !StringUtils.hasLength(order) ? filter.append(" ORDER BY ").append("DF_CODE").toString() : filter.append(" ORDER BY ").append(order).toString();
            this.logger.debug(sql);
            return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
        }
        return this.limit(skip, limit, orderFields, sql, sqlParameterSource);
    }

    @Override
    public int countBy(FieldSearchQuery fieldSearchQuery) {
        StringBuilder filter = new StringBuilder("select count( DF_KEY ) from " + this.tablename);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        this.parseFieldSearchQuery(fieldSearchQuery, filter, sqlParameterSource);
        String sql = filter.toString();
        this.logger.debug(sql);
        Integer count = (Integer)this.namedParameterJdbcTemplate.queryForObject(sql, (SqlParameterSource)sqlParameterSource, Integer.class);
        return count == null ? 0 : count;
    }

    private void parseFieldSearchQuery(FieldSearchQuery fieldSearchQuery, StringBuilder filter, MapSqlParameterSource sqlParameterSource) {
        String keyword;
        Integer kind;
        String table;
        List<String> tables;
        String scheme;
        ArrayList<String> sqlWheres = new ArrayList<String>();
        List<String> schemes = fieldSearchQuery.getSchemes();
        if (CollectionUtils.isEmpty(schemes) && (scheme = fieldSearchQuery.getScheme()) != null) {
            schemes = Collections.singletonList(scheme);
        }
        if (!CollectionUtils.isEmpty(schemes)) {
            sqlWheres.add(" DF_DS_KEY IN ( :schemes )");
            sqlParameterSource.addValue("schemes", schemes);
        }
        if (CollectionUtils.isEmpty(tables = fieldSearchQuery.getTables()) && (table = fieldSearchQuery.getTable()) != null) {
            tables = Collections.singletonList(table);
        }
        if (!CollectionUtils.isEmpty(tables)) {
            sqlWheres.add(" DF_DT_KEY IN ( :tables )");
            sqlParameterSource.addValue("tables", tables);
        }
        if ((kind = fieldSearchQuery.getKind()) != null) {
            List<Integer> kinds = AbstractDataFieldDao.getKindValues(kind);
            sqlParameterSource.addValue("kinds", kinds);
            sqlWheres.add(" DF_KIND IN ( :kinds )");
        }
        if (StringUtils.hasLength(keyword = fieldSearchQuery.getKeyword())) {
            keyword = "%" + keyword.toUpperCase() + "%";
            sqlParameterSource.addValue("keyword", (Object)keyword);
            sqlWheres.add("( UPPER(DF_TITLE) like :keyword OR UPPER(DF_CODE) like :keyword )");
        }
        String and = String.join((CharSequence)" AND ", sqlWheres);
        filter.append(" WHERE ").append(and).append(" ");
    }

    @Override
    public void cancelEncrypted(String dataSchemeKey) {
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", this.tablename, "DF_ENCRYPTED", "DF_DS_KEY");
        this.jdbcTemplate.update(sql, new Object[]{0, dataSchemeKey});
    }

    public abstract Class<DO> getClz();

    @Override
    public List<DO> getByEntity(List<String> entityKeys) {
        if (entityKeys == null || entityKeys.isEmpty()) {
            return Collections.emptyList();
        }
        String sql = this.selectSQL + " where ( UPPER(" + "DF_DATATYPE" + ") = :type AND UPPER(" + "DF_REF_ENTITY_ID" + ") IN ( :ids ) )";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("type", (Object)DataFieldType.STRING.getValue());
        sqlParameterSource.addValue("ids", entityKeys);
        this.logger.debug(sql);
        RowMapper<DO> rowMapper = super.getRowMapper(this.getClz());
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }
}

