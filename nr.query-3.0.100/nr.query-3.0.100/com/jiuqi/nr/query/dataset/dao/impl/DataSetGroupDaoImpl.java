/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dataset.dao.impl;

import com.jiuqi.nr.query.dataset.dao.IDataSetGroupDao;
import com.jiuqi.nr.query.dataset.dao.impl.DataSetGroupDao;
import com.jiuqi.nr.query.dataset.defines.DataSetGroupDefine;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DataSetGroupDaoImpl
implements IDataSetGroupDao {
    @Autowired
    DataSetGroupDao groupDao;

    @Override
    public Boolean InsertDataSetGroup(DataSetGroupDefine group) {
        Assert.notNull((Object)group, "'group' must not be null");
        try {
            this.groupDao.insertGroup(group);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean UpdateDataSetGroup(DataSetGroupDefine group) {
        Assert.notNull((Object)group, "'group' must not be null");
        try {
            this.groupDao.updateGroup(group);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public DataSetGroupDefine QueryDataSetGroupById(String id) {
        try {
            return this.groupDao.getGroupById(id);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DataSetGroupDefine> QueryDataSetGroupByParentIdAndCreator(String parent) {
        try {
            return this.groupDao.getGroupListByParentIdAndCreator(parent);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DataSetGroupDefine> QueryDataSetGroupByParentId(String parent) {
        try {
            return this.groupDao.getGroupListByParentId(parent);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DataSetGroupDefine> QueryDataSetGroupByCreator() {
        try {
            return this.groupDao.getDataSetGroupListByCreator();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DataSetGroupDefine> QueryDataSetGroup() {
        try {
            return this.groupDao.getDataSetGroupList();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean DeleteDataSetGroupById(String id) {
        try {
            this.groupDao.deleteGroupById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<DataSetGroupDefine> QueryDataSetGroupByParentIdAndTitleAndCreator(String parent, String title) {
        try {
            return this.groupDao.getGroupListByParentIdAndTitleAndCreator(parent, title);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public List<DataSetGroupDefine> QueryDataSetGroupByParentIdAndTitle(String parent, String title) {
        try {
            return this.groupDao.getGroupListByParentIdAndTitle(parent, title);
        }
        catch (Exception exception) {
            return null;
        }
    }
}

