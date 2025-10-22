/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nvwa.definition.interval.dao.BaseDao
 */
package com.jiuqi.nr.query.dataset.dao.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.query.dataset.defines.DataSetGroupDefine;
import com.jiuqi.nvwa.definition.interval.dao.BaseDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataSetGroupDao
extends BaseDao {
    private static String dsg_Id = "id";
    private static String dsg_Parent = "parent";
    private static String dsg_Title = "title";
    private static String dsg_Creator = "creator";
    private Class<DataSetGroupDefine> implClass = DataSetGroupDefine.class;
    @Autowired
    private SystemUserService sysUserService;

    public Class<?> getClz() {
        return this.implClass;
    }

    public int insertGroup(DataSetGroupDefine dataSetgroup) throws Exception {
        String creator = this.setCreator();
        dataSetgroup.setCreator(creator);
        return this.insert(dataSetgroup);
    }

    public int updateGroup(DataSetGroupDefine dataSetGroup) throws Exception {
        return this.update(dataSetGroup);
    }

    public int deleteGroupById(String id) throws Exception {
        return this.delete(id);
    }

    public DataSetGroupDefine getGroupById(String id) throws Exception {
        return (DataSetGroupDefine)this.getByKey(id, this.implClass);
    }

    public List<DataSetGroupDefine> getGroupListByParentIdAndCreator(String parent) throws Exception {
        String creator = this.setCreator();
        if (this.isAdminUser() != null) {
            return this.list(new String[]{dsg_Parent}, new Object[]{parent}, this.implClass);
        }
        return this.list(new String[]{dsg_Parent, dsg_Creator}, new Object[]{parent, creator}, this.implClass);
    }

    public List<DataSetGroupDefine> getGroupListByParentId(String parent) throws Exception {
        return this.list(new String[]{dsg_Parent}, new Object[]{parent}, this.implClass);
    }

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

    public List<DataSetGroupDefine> getGroupListByParentIdAndTitleAndCreator(String parent, String title) throws Exception {
        String creator = this.setCreator();
        if (this.isAdminUser() != null) {
            return this.list(new String[]{dsg_Parent, dsg_Title}, new Object[]{parent, title}, this.implClass);
        }
        return this.list(new String[]{dsg_Parent, dsg_Title, dsg_Creator}, new Object[]{parent, title, creator}, this.implClass);
    }

    public List<DataSetGroupDefine> getGroupListByParentIdAndTitle(String parent, String title) throws Exception {
        return this.list(new String[]{dsg_Parent, dsg_Title}, new Object[]{parent, title}, this.implClass);
    }

    public List<DataSetGroupDefine> getDataSetGroupListByCreator() throws Exception {
        String creator = this.setCreator();
        if (this.isAdminUser() != null) {
            return this.list(this.implClass);
        }
        return this.list(new String[]{dsg_Creator}, new Object[]{creator}, this.implClass);
    }

    public List<DataSetGroupDefine> getDataSetGroupList() throws Exception {
        return this.list(this.implClass);
    }
}

