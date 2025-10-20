/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DBException
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.bi.database.paging.UnsupportPagingException
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.common.queryscheme.dao.impl;

import com.jiuqi.bi.database.DBException;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.paging.UnsupportPagingException;
import com.jiuqi.common.queryscheme.common.QuerySchemeStoreTypeEnum;
import com.jiuqi.common.queryscheme.dao.QuerySchemeDao;
import com.jiuqi.common.queryscheme.eo.QuerySchemeEO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QuerySchemeDaoImpl
implements QuerySchemeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @Override
    public List<QuerySchemeEO> listQuerySchemesByUserName(String userName, String resourceId, String optionType) {
        String sql = "select id,schemeName,selectFlag,sortOrder,storeType,optionData, userId from gc_query_scheme where userName = ? and resourceId = ? and optionType = ? order by sortOrder ";
        return this.jdbcTemplate.query(sql, new Object[]{userName, resourceId, optionType}, (rs, rowNum) -> {
            QuerySchemeEO eo = new QuerySchemeEO();
            eo.setId(rs.getString(1));
            eo.setUsername(userName);
            eo.setResourceId(resourceId);
            eo.setOptionType(optionType);
            eo.setSchemeName(rs.getString(2));
            eo.setSelectFlag(rs.getInt(3));
            eo.setSortOrder(rs.getString(4));
            eo.setStoreType(rs.getInt(5));
            eo.setOptionData(rs.getString(6));
            eo.setUserId(rs.getString(7));
            return eo;
        });
    }

    @Override
    public QuerySchemeEO getQuerySchemeById(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select userId,resourceId,schemeName,selectFlag,sortOrder,storeType,optionType, optionData, userName \n");
        sql.append("  from gc_query_scheme  t \n");
        sql.append(" where t.id = ? \n");
        QuerySchemeEO result = (QuerySchemeEO)this.jdbcTemplate.query(sql.toString(), new Object[]{id}, rs -> {
            if (rs.next()) {
                QuerySchemeEO eo = new QuerySchemeEO();
                eo.setId(id);
                eo.setUserId(rs.getString(1));
                eo.setResourceId(rs.getString(2));
                eo.setSchemeName(rs.getString(3));
                eo.setSelectFlag(rs.getInt(4));
                eo.setSortOrder(rs.getString(5));
                eo.setStoreType(rs.getInt(6));
                eo.setOptionType(rs.getString(7));
                eo.setOptionData(rs.getString(8));
                eo.setUsername(rs.getString(9));
                return eo;
            }
            return null;
        });
        if (result == null) {
            return null;
        }
        if (QuerySchemeStoreTypeEnum.CLOB_TABLE.getStoreValue().equals(result.getStoreType())) {
            result.setOptionData(this.getOptionDataById(id));
        }
        return result;
    }

    private String getOptionDataById(String id) {
        String sql = " SELECT OPTIONDATA FROM GC_QUERY_SCHEME_OPTIONDATA WHERE ID = ? ";
        return (String)this.jdbcTemplate.query(sql, new Object[]{id}, rs -> {
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        });
    }

    @Override
    public void insert(QuerySchemeEO eo) {
        String optionData = eo.getOptionData();
        if (QuerySchemeStoreTypeEnum.CLOB_TABLE.getStoreValue().equals(eo.getStoreType())) {
            this.saveOptionData(eo.getId(), eo.getOptionData());
            optionData = "";
        }
        String sql = "insert into gc_query_scheme (id,userId,resourceId,schemeName,selectFlag,sortOrder,storeType,optionType,optionData,username) values (?,?,?,?,?,?,?,?,?,?) ";
        Object[] args = new Object[]{eo.getId(), eo.getUserId(), eo.getResourceId(), eo.getSchemeName(), eo.getSelectFlag(), eo.getSortOrder(), eo.getStoreType(), eo.getOptionType(), optionData, eo.getUsername()};
        this.jdbcTemplate.update(sql, args);
    }

    @Override
    public void update(QuerySchemeEO eo) {
        String optionData = eo.getOptionData();
        if (QuerySchemeStoreTypeEnum.CLOB_TABLE.getStoreValue().equals(eo.getStoreType())) {
            if (!this.hasOptionDataById(eo.getId())) {
                this.saveOptionData(eo.getId(), eo.getOptionData());
            } else {
                this.updateOptionData(eo.getId(), eo.getOptionData());
            }
            optionData = "";
        }
        String sql = "update gc_query_scheme set schemeName=?,selectFlag=?,sortOrder=?,storeType=?,optionType=?,optionData=? where id=? ";
        Object[] args = new Object[]{eo.getSchemeName(), eo.getSelectFlag(), eo.getSortOrder(), eo.getStoreType(), eo.getOptionType(), optionData, eo.getId()};
        this.jdbcTemplate.update(sql, args);
    }

    @Override
    public QuerySchemeEO getPriorQuerySchemeByCondition(String username, String resourceId, String optionType, String sortOrder) {
        String sql = "select id,schemeName,selectFlag,sortOrder,storeType,optionType,optionData,userId \n     from gc_query_scheme \n    where username = ? and resourceId = ? and optionType = ? and sortOrder < ?  order by sortOrder desc  ";
        return (QuerySchemeEO)this.jdbcTemplate.query(sql, new Object[]{username, resourceId, optionType, sortOrder}, rs -> {
            if (rs.next()) {
                QuerySchemeEO eo = new QuerySchemeEO();
                eo.setId(rs.getString(1));
                eo.setUsername(username);
                eo.setResourceId(resourceId);
                eo.setSchemeName(rs.getString(2));
                eo.setSelectFlag(rs.getInt(3));
                eo.setSortOrder(rs.getString(4));
                eo.setStoreType(rs.getInt(5));
                eo.setOptionType(rs.getString(6));
                eo.setOptionData(rs.getString(7));
                eo.setUserId(rs.getString(8));
                return eo;
            }
            return null;
        });
    }

    @Override
    public QuerySchemeEO getNextQuerySchemeByCondition(String userName, String resourceId, String optionType, String sortOrder) {
        String sql = "select id,schemeName,selectFlag,sortOrder,storeType,optionType,optionData,userId  \n  from gc_query_scheme \n where username = ? and resourceId = ? and optionType = ?  and sortOrder > ?  order by sortOrder  \n";
        return (QuerySchemeEO)this.jdbcTemplate.query(sql, new Object[]{userName, resourceId, optionType, sortOrder}, rs -> {
            if (rs.next()) {
                QuerySchemeEO eo = new QuerySchemeEO();
                eo.setId(rs.getString(1));
                eo.setUsername(userName);
                eo.setResourceId(resourceId);
                eo.setSchemeName(rs.getString(2));
                eo.setSelectFlag(rs.getInt(3));
                eo.setSortOrder(rs.getString(4));
                eo.setStoreType(rs.getInt(5));
                eo.setOptionType(rs.getString(6));
                eo.setOptionData(rs.getString(7));
                eo.setUserId(rs.getString(8));
                return eo;
            }
            return null;
        });
    }

    @Override
    public void removeSelectByCondition(String userName, String resourceId, String optionType) {
        String sql = "update gc_query_scheme set selectFlag = 0 where  userName = ? and resourceId = ? and optionType = ?  ";
        this.jdbcTemplate.update(sql, new Object[]{userName, resourceId, optionType});
    }

    private void saveOptionData(String id, String optionData) {
        String sql = "insert into gc_query_scheme_optionData (id,optionData) values (?,?) ";
        Object[] args = new Object[]{id, optionData};
        this.jdbcTemplate.update(sql, args);
    }

    private void updateOptionData(String id, String optionData) {
        String sql = "update gc_query_scheme_optionData  set optionData = ?  where  id = ?  ";
        Object[] args = new Object[]{optionData, id};
        this.jdbcTemplate.update(sql, args);
    }

    private boolean hasOptionDataById(String id) {
        String sql = "select id from gc_query_scheme_optionData where  id = ?  ";
        return (Boolean)this.jdbcTemplate.query(sql, new Object[]{id}, rs -> rs.next());
    }

    @Override
    public void delete(String id) {
        String deleteSchemeSql = "delete from  gc_query_scheme where id = ?  ";
        this.jdbcTemplate.update(deleteSchemeSql, new Object[]{id});
        String deleteOptionDataSql = "delete from  gc_query_scheme_optionData where id = ?  ";
        this.jdbcTemplate.update(deleteOptionDataSql, new Object[]{id});
    }

    @Override
    public void updateSortOrderById(String id, String sortOrder) {
        String sql = "update gc_query_scheme set sortOrder=?  where id=? ";
        Object[] args = new Object[]{sortOrder, id};
        this.jdbcTemplate.update(sql, args);
    }

    @Override
    public void updateSelectFlagById(String id, boolean selectFlag) {
        String sql = "update gc_query_scheme set selectFlag=?  where id=? ";
        Object[] args = new Object[]{selectFlag ? 1 : 0, id};
        this.jdbcTemplate.update(sql, args);
    }

    @Override
    public void rename(String id, String name) {
        String sql = "update gc_query_scheme set schemeName=?  where id=? ";
        Object[] args = new Object[]{name, id};
        this.jdbcTemplate.update(sql, args);
    }

    @Override
    public QuerySchemeEO getLastQueryScheme(String userName, String resourceId, String optionType) {
        String limitedSql;
        String sql = "select id,schemeName,selectFlag,sortOrder,storeType,optionData, userId from gc_query_scheme where userName = ? and resourceId = ? and optionType = ? order by sortOrder desc";
        try (Connection connection = this.dataSource.getConnection();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            IPagingSQLBuilder pagingSqlBuilder = database.createPagingSQLBuilder();
            pagingSqlBuilder.setRawSQL(sql);
            limitedSql = pagingSqlBuilder.buildSQL(0, 1);
        }
        catch (SQLException ex) {
            throw new RuntimeException("\u8fde\u63a5\u6570\u636e\u5e93\u5931\u8d25\uff1a" + ex.getMessage());
        }
        catch (UnsupportPagingException ex) {
            throw new RuntimeException("\u521b\u5efa\u5206\u9875SQL\u751f\u6210\u5668\u5931\u8d25\uff1a" + ex.getMessage());
        }
        catch (DBException ex) {
            throw new RuntimeException("\u751f\u6210SQL\u5931\u8d25\uff1a" + ex.getMessage());
        }
        return (QuerySchemeEO)this.jdbcTemplate.query(limitedSql, ps -> {
            ps.setString(1, userName);
            ps.setString(2, resourceId);
            ps.setString(3, optionType);
        }, rs -> {
            if (rs.next()) {
                QuerySchemeEO eo = new QuerySchemeEO();
                eo.setUsername(userName);
                eo.setResourceId(resourceId);
                eo.setOptionType(optionType);
                eo.setId(rs.getString(1));
                eo.setSchemeName(rs.getString(2));
                eo.setSelectFlag(rs.getInt(3));
                eo.setSortOrder(rs.getString(4));
                eo.setStoreType(rs.getInt(5));
                eo.setOptionData(rs.getString(6));
                eo.setUserId(rs.getString(7));
                return eo;
            }
            return null;
        });
    }
}

