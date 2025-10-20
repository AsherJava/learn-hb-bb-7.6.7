/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.tree.vo.GroupVO
 */
package com.jiuqi.va.query.tree.dao.impl;

import com.jiuqi.va.query.common.dao.impl.UserDefinedBaseDaoImpl;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import com.jiuqi.va.query.tree.dao.QueryGroupDao;
import com.jiuqi.va.query.tree.vo.GroupVO;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QueryGroupDaoImpl
extends UserDefinedBaseDaoImpl
implements QueryGroupDao {
    @Autowired
    private DynamicDataSourceService dataSourceService;

    @Override
    public List<GroupVO> getGroupsByParentGroupId(String parentId) {
        String sql = " select id,title,code,description,sortOrder from DC_QUERY_GROUP where parentid = ? order by sortOrder ";
        return this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> ps.setString(1, parentId), (rs, rowNum) -> {
            GroupVO menuTree = new GroupVO();
            menuTree.setParentId(parentId);
            menuTree.setId(rs.getString(1));
            menuTree.setTitle(rs.getString(2));
            menuTree.setCode(rs.getString(3));
            menuTree.setDescription(rs.getString(4));
            menuTree.setSortOrder(rs.getInt(5));
            return menuTree;
        });
    }

    @Override
    public GroupVO getGroupById(String id) {
        String sql = " select parentid,title,code,description,sortOrder from DC_QUERY_GROUP where id = ? order by sortOrder ";
        return (GroupVO)this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> ps.setString(1, id), rs -> {
            if (rs.next()) {
                GroupVO groupVO = new GroupVO();
                groupVO.setId(id);
                groupVO.setParentId(rs.getString(1));
                groupVO.setTitle(rs.getString(2));
                groupVO.setCode(rs.getString(3));
                groupVO.setDescription(rs.getString(4));
                groupVO.setSortOrder(rs.getInt(5));
                return groupVO;
            }
            return null;
        });
    }

    @Override
    public GroupVO getGroupByCode(String code) {
        String sql = " select id,parentid,title,description,sortOrder from DC_QUERY_GROUP where code = ? ";
        return (GroupVO)this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), ps -> ps.setString(1, code), rs -> {
            if (rs.next()) {
                GroupVO groupVO = new GroupVO();
                groupVO.setId(rs.getString(1));
                groupVO.setParentId(rs.getString(2));
                groupVO.setTitle(rs.getString(3));
                groupVO.setCode(code);
                groupVO.setDescription(rs.getString(4));
                groupVO.setSortOrder(rs.getInt(5));
                return groupVO;
            }
            return null;
        });
    }

    @Override
    public void save(GroupVO groupVO) {
        String sql = "insert into DC_QUERY_GROUP \n  (ID,TITLE,CODE,DESCRIPTION,SORTORDER,PARENTID) \n  values (?,?,?,?,?,?) \n";
        Object[] args = new Object[]{groupVO.getId(), groupVO.getTitle(), groupVO.getCode(), DCQueryStringHandle.isEmpty(groupVO.getDescription()) ? "" : groupVO.getDescription(), groupVO.getSortOrder(), groupVO.getParentId()};
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), args);
    }

    @Override
    public void delete(String groupId) {
        String sql = "delete from DC_QUERY_GROUP where id = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), new Object[]{groupId});
    }

    @Override
    public boolean hasGroupByGroupId(String id) {
        String sql = " Select * from DC_QUERY_GROUP where id = ? ";
        return (Boolean)this.dataSourceService.pageQuery(DataSourceEnum.CURRENT.getName(), sql, 1, 1, new Object[]{id}, ResultSet::next);
    }

    @Override
    public void update(GroupVO groupVO) {
        Object[] args = new Object[]{groupVO.getTitle(), groupVO.getCode(), DCQueryStringHandle.isEmpty(groupVO.getDescription()) ? "" : groupVO.getDescription(), groupVO.getId()};
        String sql = "update DC_QUERY_GROUP \n   set title = ? , code = ? , description = ? \n where id = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), args);
    }

    @Override
    public void updateByCode(GroupVO groupVO) {
        Object[] args = new Object[]{groupVO.getTitle(), DCQueryStringHandle.isEmpty(groupVO.getDescription()) ? "" : groupVO.getDescription(), groupVO.getCode()};
        String sql = "update DC_QUERY_GROUP \n   set title = ? , description = ? \n where code = ? \n";
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), args);
    }

    @Override
    public boolean hasGroupByCode(String code) {
        String sql = " Select * from DC_QUERY_GROUP where code = ? ";
        return (Boolean)this.dataSourceService.pageQuery(DataSourceEnum.CURRENT.getName(), sql, 1, 1, new Object[]{code}, ResultSet::next);
    }

    @Override
    public List<GroupVO> getAllGroups() {
        String sql = " select id,title,code,description,parentid,sortOrder from DC_QUERY_GROUP order by sortOrder";
        return this.getJdbcTemplate().query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), (rs, rowNum) -> {
            GroupVO menuTree = new GroupVO();
            menuTree.setId(rs.getString(1));
            menuTree.setTitle(rs.getString(2));
            menuTree.setCode(rs.getString(3));
            menuTree.setDescription(rs.getString(4));
            menuTree.setParentId(rs.getString(5));
            menuTree.setSortOrder(rs.getInt(6));
            return menuTree;
        });
    }
}

