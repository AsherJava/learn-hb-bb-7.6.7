/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.offsetitem.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.offsetitem.dao.GcUnOffsetSelectOptionDao;
import com.jiuqi.gcreport.offsetitem.entity.GcUnOffsetSelectOptionEO;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GcUnOffsetSelectOptionDaoImpl
extends GcDbSqlGenericDAO<GcUnOffsetSelectOptionEO, String>
implements GcUnOffsetSelectOptionDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public GcUnOffsetSelectOptionDaoImpl() {
        super(GcUnOffsetSelectOptionEO.class);
    }

    private GcUnOffsetSelectOptionEO extractedRsToEntity(ResultSet rs) throws SQLException {
        GcUnOffsetSelectOptionEO gcUnOffsetSelectOption = new GcUnOffsetSelectOptionEO();
        gcUnOffsetSelectOption.setId(rs.getString("id"));
        gcUnOffsetSelectOption.setCode(rs.getString("code"));
        gcUnOffsetSelectOption.setTitle(rs.getString("title"));
        gcUnOffsetSelectOption.setEffectRange(rs.getString("effectrange"));
        gcUnOffsetSelectOption.setContent(rs.getString("content"));
        gcUnOffsetSelectOption.setOrdinal(rs.getInt("ordinal"));
        return gcUnOffsetSelectOption;
    }

    @Override
    public List<Map<String, Object>> listUnOffsetConfigDatas(String dataSource) {
        String sql = "\tselect e.id,e.code,e.title,e.effectrange,e.content,e.ordinal,e.sorttype from GC_UNOFFSETSELECTOPTION  e \nwhere e.dataSource = ? order by e.ordinal";
        return this.selectMap(sql, new Object[]{dataSource});
    }

    @Override
    public GcUnOffsetSelectOptionEO getUnOffsetConfigDataById(String id) {
        StringBuffer sql = new StringBuffer(64);
        sql.append("select e.id,e.code,e.title,e.effectrange,e.content,e.ordinal from ").append("GC_UNOFFSETSELECTOPTION").append(" e \n");
        sql.append("where id = ?");
        List getResultList = this.jdbcTemplate.query(sql.toString(), (rs, rowNum) -> this.extractedRsToEntity(rs), new Object[]{id});
        return (GcUnOffsetSelectOptionEO)((Object)getResultList.get(0));
    }

    @Override
    public GcUnOffsetSelectOptionEO getPreNodeByIdAndOrder(Integer ordinal, String dataSource, String pageCode) {
        String sql = "select  e.id,e.code,e.title,e.effectrange,e.content,e.ordinal \nfrom GC_UNOFFSETSELECTOPTION  e \nwhere e.ordinal<? \n and e.dataSource = ? and e.effectRange = ? order by e.ordinal desc\n";
        List getResultList = this.jdbcTemplate.query(sql, (rs, rowNum) -> this.extractedRsToEntity(rs), new Object[]{ordinal, dataSource, pageCode});
        if (CollectionUtils.isEmpty((Collection)getResultList)) {
            return null;
        }
        return (GcUnOffsetSelectOptionEO)((Object)getResultList.get(0));
    }

    @Override
    public GcUnOffsetSelectOptionEO getNextNodeByIdAndOrder(Integer ordinal, String dataSource, String pageCode) {
        String sql = "select e.id,e.code,e.title,e.effectrange,e.content,e.ordinal \nfrom GC_UNOFFSETSELECTOPTION  e \nwhere e.ordinal>? \n and e.dataSource = ? and e.effectRange = ? order by e.ordinal asc\n";
        List getResultList = this.jdbcTemplate.query(sql, (rs, rowNum) -> this.extractedRsToEntity(rs), new Object[]{ordinal, dataSource, pageCode});
        if (CollectionUtils.isEmpty((Collection)getResultList)) {
            return null;
        }
        return (GcUnOffsetSelectOptionEO)((Object)getResultList.get(0));
    }

    @Override
    public void deleteOfId(GcUnOffsetSelectOptionEO gcUnOffsetSelectOptionEO) {
        String sql = "DELETE FROM GC_UNOFFSETSELECTOPTION WHERE ID = ? ";
        this.jdbcTemplate.update(sql, ps -> ps.setString(1, gcUnOffsetSelectOptionEO.getId()));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateDataBatch(List<GcUnOffsetSelectOptionEO> gcUnOffsetSelectOptionEOS) {
        String insertSql = "INSERT INTO GC_UNOFFSETSELECTOPTION(ID,CODE,TITLE,EFFECTRANGE,CONTENT,ORDINAL) values(?,?,?,?,?,?)";
        String updateSql = "UPDATE GC_UNOFFSETSELECTOPTION set code = ?, title = ?, effectRange = ?,content = ?,ordinal = ? where id = ?";
        final ArrayList<GcUnOffsetSelectOptionEO> insertList = new ArrayList<GcUnOffsetSelectOptionEO>();
        final ArrayList<GcUnOffsetSelectOptionEO> updateList = new ArrayList<GcUnOffsetSelectOptionEO>();
        for (GcUnOffsetSelectOptionEO gcUnOffsetSelectOptionEO : gcUnOffsetSelectOptionEOS) {
            if (gcUnOffsetSelectOptionEO.getId() == null) {
                gcUnOffsetSelectOptionEO.setId(UUIDUtils.newUUIDStr());
                insertList.add(gcUnOffsetSelectOptionEO);
                continue;
            }
            updateList.add(gcUnOffsetSelectOptionEO);
        }
        if (!CollectionUtils.isEmpty(insertList)) {
            this.jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter(){

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, ((GcUnOffsetSelectOptionEO)((Object)insertList.get(i))).getId());
                    ps.setString(2, ((GcUnOffsetSelectOptionEO)((Object)insertList.get(i))).getCode());
                    ps.setString(3, ((GcUnOffsetSelectOptionEO)((Object)insertList.get(i))).getTitle());
                    ps.setString(4, ((GcUnOffsetSelectOptionEO)((Object)insertList.get(i))).getEffectRange());
                    ps.setString(5, ((GcUnOffsetSelectOptionEO)((Object)insertList.get(i))).getContent());
                    ps.setInt(6, ((GcUnOffsetSelectOptionEO)((Object)insertList.get(i))).getOrdinal());
                }

                public int getBatchSize() {
                    return insertList.size();
                }
            });
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            this.jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter(){

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, ((GcUnOffsetSelectOptionEO)((Object)updateList.get(i))).getCode());
                    ps.setString(2, ((GcUnOffsetSelectOptionEO)((Object)updateList.get(i))).getTitle());
                    ps.setString(3, ((GcUnOffsetSelectOptionEO)((Object)updateList.get(i))).getEffectRange());
                    ps.setString(4, ((GcUnOffsetSelectOptionEO)((Object)updateList.get(i))).getContent());
                    ps.setInt(5, ((GcUnOffsetSelectOptionEO)((Object)updateList.get(i))).getOrdinal());
                    ps.setString(6, ((GcUnOffsetSelectOptionEO)((Object)updateList.get(i))).getId());
                }

                public int getBatchSize() {
                    return updateList.size();
                }
            });
        }
    }

    @Override
    public void updateData(GcUnOffsetSelectOptionEO gcUnOffsetSelectOptionEO) {
        String updateSql = "UPDATE GC_UNOFFSETSELECTOPTION set code = ?, title = ?, effectRange = ?,content = ?,ordinal = ? where id = ?";
        this.jdbcTemplate.update(updateSql, ps -> {
            ps.setString(1, gcUnOffsetSelectOptionEO.getCode());
            ps.setString(2, gcUnOffsetSelectOptionEO.getTitle());
            ps.setString(3, gcUnOffsetSelectOptionEO.getEffectRange());
            ps.setString(4, gcUnOffsetSelectOptionEO.getContent());
            ps.setInt(5, gcUnOffsetSelectOptionEO.getOrdinal());
            ps.setString(6, gcUnOffsetSelectOptionEO.getId());
        });
    }

    @Override
    public Integer getLastNode() {
        String sql = "select t.ordinal \nfrom GC_UNOFFSETSELECTOPTION  t \norder by t.ordinal desc \n";
        return ((BigDecimal)((Map)this.jdbcTemplate.queryForList(sql).get(0)).get("ORDINAL")).intValue();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveSelectOption(Map<Object, List<Map<String, Object>>> selectOptions, final String dataSource) {
        boolean sorttype;
        final List<Map<String, Object>> unOffsetSelectOptionList = selectOptions.get("unOffset");
        final List<Map<String, Object>> unOffsetParentSelectOptionList = selectOptions.get("unOffsetParent");
        boolean finalSorttype = sorttype = false;
        String deleteSql = "DELETE FROM GC_UNOFFSETSELECTOPTION WHERE DATASOURCE = ? ";
        String insertSql = "INSERT INTO GC_UNOFFSETSELECTOPTION(ID,CODE,TITLE,EFFECTRANGE,ORDINAL,SORTTYPE,DATASOURCE,DATASOURCESTATE) values(?,?,?,?,?,?,?,?)";
        String updateSql = "UPDATE GC_UNOFFSETSELECTOPTION SET DATASOURCESTATE = '0' WHERE DATASOURCE != ? ";
        this.jdbcTemplate.update(deleteSql, new Object[]{dataSource});
        if (!CollectionUtils.isEmpty(unOffsetSelectOptionList)) {
            this.jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter(){

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, UUIDUtils.newUUIDStr());
                    ps.setString(2, (String)((Map)unOffsetSelectOptionList.get(i)).get("value"));
                    ps.setString(3, (String)((Map)unOffsetSelectOptionList.get(i)).get("showType"));
                    ps.setString(4, "NOT_OFFSETPAGE");
                    ps.setInt(5, i);
                    ps.setInt(6, "1".equals(((Map)unOffsetSelectOptionList.get(i)).get("isUnitTreeSort")) ? 1 : 0);
                    ps.setString(7, dataSource);
                    ps.setString(8, "1");
                }

                public int getBatchSize() {
                    return unOffsetSelectOptionList.size();
                }
            });
        }
        if (!CollectionUtils.isEmpty(unOffsetParentSelectOptionList)) {
            this.jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter(){

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, UUIDUtils.newUUIDStr());
                    ps.setString(2, (String)((Map)unOffsetParentSelectOptionList.get(i)).get("value"));
                    ps.setString(3, (String)((Map)unOffsetParentSelectOptionList.get(i)).get("showType"));
                    ps.setString(4, "NOT_OFFSET_PARENTPAGE");
                    ps.setInt(5, i);
                    ps.setInt(6, "1".equals(((Map)unOffsetParentSelectOptionList.get(i)).get("isUnitTreeSort")) ? 1 : 0);
                    ps.setString(7, dataSource);
                    ps.setString(8, "1");
                }

                public int getBatchSize() {
                    return unOffsetParentSelectOptionList.size();
                }
            });
        }
        this.jdbcTemplate.update(updateSql, new Object[]{dataSource});
    }

    @Override
    public String getCurDataSource() {
        String sql = "select e.DATASOURCE from GC_UNOFFSETSELECTOPTION e where e.DATASOURCESTATE = '1' group by e.DATASOURCE";
        return (String)((Map)this.selectMap(sql, new Object[0]).get(0)).get("DATASOURCE");
    }
}

