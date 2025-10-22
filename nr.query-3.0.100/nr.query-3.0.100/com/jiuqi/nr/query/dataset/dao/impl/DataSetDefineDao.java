/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nvwa.definition.interval.dao.BaseDao
 *  org.springframework.jdbc.core.RowCallbackHandler
 */
package com.jiuqi.nr.query.dataset.dao.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.query.common.TransUtil;
import com.jiuqi.nr.query.dataset.defines.DataSetDefine;
import com.jiuqi.nvwa.definition.interval.dao.BaseDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

@Repository
public class DataSetDefineDao
extends BaseDao {
    private static String ds_Id = "id";
    private static String ds_Title = "title";
    private static String ds_Name = "name";
    private static String ds_Parent = "parent";
    private static String ds_Creator = "creator";
    private Class<DataSetDefine> implClass = DataSetDefine.class;
    @Autowired
    private SystemUserService sysUserService;

    String setCreator() {
        if (NpContextHolder.getContext().getUser() != null && this.sysUserService.get(NpContextHolder.getContext().getUser().getId()) != null) {
            return ((SystemUser)this.sysUserService.get(NpContextHolder.getContext().getUser().getId())).getId();
        }
        return NpContextHolder.getContext().getUserId();
    }

    private User isAdminUser() {
        if (NpContextHolder.getContext().getUser() != null) {
            User user = this.sysUserService.get(NpContextHolder.getContext().getUser().getId());
            return user;
        }
        return null;
    }

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public int insertDefine(DataSetDefine dataSetDefine) throws Exception {
        String creator = this.setCreator();
        dataSetDefine.setCreator(creator);
        return this.insert(dataSetDefine);
    }

    public int updateDefine(DataSetDefine dataSetDefine) throws Exception {
        return this.update(dataSetDefine);
    }

    public int deleteDefineById(String id) throws Exception {
        return this.delete(id);
    }

    public int deleteDefineByName(String name) throws Exception {
        return this.delete(name);
    }

    public DataSetDefine getDefineById(String id) throws Exception {
        return (DataSetDefine)this.getByKey(id, this.implClass);
    }

    public List<DataSetDefine> getDefineByNameAndCreator(String name) throws Exception {
        String creator = this.setCreator();
        if (this.isAdminUser() != null) {
            return this.list(new String[]{ds_Name}, new Object[]{name}, this.implClass);
        }
        return this.list(new String[]{ds_Name, ds_Creator}, new Object[]{name, creator}, this.implClass);
    }

    public List<DataSetDefine> getDefineByName(String name) throws Exception {
        return this.list(new String[]{ds_Name}, new Object[]{name}, this.implClass);
    }

    public List<DataSetDefine> getDefineByNameAndCreatorWithoutModel(String name) throws Exception {
        String creator = this.setCreator();
        String sql = "SELECT ds_id ,ds_name,ds_title,ds_parent,ds_type,ds_order,updatetime FROM sys_dataset  WHERE ds_name = ? and ds_creator= ? ";
        Object[] params = new Object[]{name, creator};
        if (this.isAdminUser() != null) {
            sql = "SELECT ds_id ,ds_name,ds_title,ds_parent,ds_type,ds_order,updatetime FROM sys_dataset  WHERE ds_name = ? ";
            params = new Object[]{name};
        }
        List maps = this.jdbcTemplate.queryForList(sql, params);
        return this.getDataSetDefineList(maps);
    }

    public List<DataSetDefine> getDefineByNameWithoutModel(String name) throws Exception {
        String sql = "SELECT ds_id ,ds_name,ds_title,ds_parent,ds_type,ds_order,updatetime FROM sys_dataset  WHERE ds_name = ? ";
        Object[] params = new Object[]{name};
        List maps = this.jdbcTemplate.queryForList(sql, params);
        return this.getDataSetDefineList(maps);
    }

    public List<DataSetDefine> getDefineByTitleAndParentIdAndCreator(String title, String parentId) throws Exception {
        String creator = this.setCreator();
        if (this.isAdminUser() != null) {
            return this.list(new String[]{ds_Title, ds_Parent}, new Object[]{title, parentId}, this.implClass);
        }
        return this.list(new String[]{ds_Title, ds_Parent, ds_Creator}, new Object[]{title, parentId, creator}, this.implClass);
    }

    public List<DataSetDefine> getDefineByTitleAndParentId(String title, String parentId) throws Exception {
        return this.list(new String[]{ds_Title, ds_Parent}, new Object[]{title, parentId}, this.implClass);
    }

    public List<DataSetDefine> getDefineByNameAndParentIdAndCreatorWithoutModel(String name, String parentId) throws Exception {
        String creator = this.setCreator();
        String sql = "SELECT ds_id ,ds_name,ds_title,ds_parent,ds_type,ds_order,updatetime FROM sys_dataset  WHERE ds_name = ? and ds_parent = ? and ds_creator= ?  ";
        Object[] params = new Object[]{name, parentId, creator};
        if (this.isAdminUser() != null) {
            sql = "SELECT ds_id ,ds_name,ds_title,ds_parent,ds_type,ds_order,updatetime FROM sys_dataset  WHERE ds_name = ? and ds_parent = ?  ";
            params = new Object[]{name, parentId};
        }
        List maps = this.jdbcTemplate.queryForList(sql, params);
        return this.getDataSetDefineList(maps);
    }

    public List<DataSetDefine> getDefineByNameAndParentIdWithoutModel(String name, String parentId) throws Exception {
        String sql = "SELECT ds_id ,ds_name,ds_title,ds_parent,ds_type,ds_order,updatetime FROM sys_dataset  WHERE ds_name = ? and ds_parent = ? ";
        Object[] params = new Object[]{name, parentId};
        List maps = this.jdbcTemplate.queryForList(sql, params);
        return this.getDataSetDefineList(maps);
    }

    public List<DataSetDefine> getDataSetListByCreator() throws Exception {
        String creator = this.setCreator();
        if (this.isAdminUser() != null) {
            return this.list(this.implClass);
        }
        return this.list(new String[]{ds_Creator}, new Object[]{creator}, this.implClass);
    }

    public List<DataSetDefine> getDataSetList() throws Exception {
        return this.list(this.implClass);
    }

    public List<DataSetDefine> listDefineByParentIdAndCreator(String id) throws Exception {
        String creator = this.setCreator();
        if (this.isAdminUser() != null) {
            return this.list(new String[]{ds_Parent}, new Object[]{id}, this.implClass);
        }
        return this.list(new String[]{ds_Parent, ds_Creator}, new Object[]{id, creator}, this.implClass);
    }

    public List<DataSetDefine> listDefineByParentId(String id) throws Exception {
        return this.list(new String[]{ds_Parent}, new Object[]{id}, this.implClass);
    }

    public List<DataSetDefine> listDefineByParentIdAndCreatorWithoutModel(String id) throws Exception {
        String creator = this.setCreator();
        String sql = "SELECT ds_id ,ds_name,ds_title,ds_parent,ds_type,ds_order,updatetime FROM sys_dataset  WHERE ds_parent = ? and ds_creator= ?  order by ds_order";
        Object[] params = new Object[]{id, creator};
        if (this.isAdminUser() != null) {
            sql = "SELECT ds_id ,ds_name,ds_title,ds_parent,ds_type,ds_order,updatetime FROM sys_dataset  WHERE ds_parent = ?   order by ds_order";
            params = new Object[]{id};
        }
        List maps = this.jdbcTemplate.queryForList(sql, params);
        List<DataSetDefine> dataSetDefineList = this.getDataSetDefineList(maps);
        return dataSetDefineList;
    }

    public List<DataSetDefine> listDefineByParentIdWithoutModel(String id) throws Exception {
        String sql = "SELECT ds_id ,ds_name,ds_title,ds_parent,ds_type,ds_order,updatetime FROM sys_dataset  WHERE ds_parent = ? order by ds_order";
        Object[] params = new Object[]{id};
        List maps = this.jdbcTemplate.queryForList(sql, params);
        List<DataSetDefine> dataSetDefineList = this.getDataSetDefineList(maps);
        return dataSetDefineList;
    }

    public DataSetDefine getDataSetModelById(String id) {
        String sql = "SELECT ds_id ,ds_model FROM sys_dataset  WHERE ds_id = ? ";
        Object[] params = new Object[]{id};
        final DataSetDefine define = new DataSetDefine();
        this.jdbcTemplate.query(sql, params, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                define.setId(rs.getString("ds_id"));
                define.setModel(rs.getString("ds_model"));
            }
        });
        return define.getId() == null ? null : define;
    }

    public DataSetDefine getDataSetDefineById(String id) {
        String sql = "SELECT ds_id ,ds_name,ds_title,ds_parent,ds_type,ds_order,ds_model,updatetime FROM sys_dataset  WHERE ds_id = ? ";
        Object[] params = new Object[]{id};
        final DataSetDefine define = new DataSetDefine();
        this.jdbcTemplate.query(sql, params, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                define.setId(rs.getString("ds_id"));
                define.setName(rs.getString("ds_name"));
                define.setTitle(rs.getString("ds_title"));
                define.setParent(rs.getString("ds_parent"));
                define.setType(rs.getString("ds_type"));
                define.setOrder(rs.getString("ds_order"));
                define.setModel(rs.getString("ds_model"));
                Timestamp updatetime = rs.getTimestamp("updatetime");
                define.setUpdatetime(new Date(updatetime.getTime()));
            }
        });
        return define.getId() == null ? null : define;
    }

    public List<DataSetDefine> getDefineList(String id) {
        String sql = "SELECT ds_id ,ds_name,ds_title,ds_parent,ds_type,ds_order,updatetime FROM sys_dataset  WHERE ds_id = ? order by ds_order";
        Object[] params = new Object[]{id};
        List maps = this.jdbcTemplate.queryForList(sql, params);
        List<DataSetDefine> dataSetDefineList = this.getDataSetDefineList(maps);
        return dataSetDefineList;
    }

    public int updateDefineModel(String id, String model) {
        String sql = "UPDATE sys_dataset SET ds_model = ? WHERE ds_id = ?";
        Object[] args = new Object[]{model, id};
        return this.jdbcTemplate.update(sql, args);
    }

    protected List<DataSetDefine> getDataSetDefineList(List<Map<String, Object>> results) {
        return results.stream().map(this::createDataSetDefine).collect(Collectors.toList());
    }

    protected DataSetDefine createDataSetDefine(Map<String, Object> map) {
        DataSetDefine define = new DataSetDefine();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            define.setProperty(entry.getKey(), entry.getValue());
        }
        return define;
    }
}

