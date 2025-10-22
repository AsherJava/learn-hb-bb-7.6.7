/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 */
package com.jiuqi.nr.query.dataset.dao.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.query.dataset.dao.IDataSetDefineDao;
import com.jiuqi.nr.query.dataset.dao.impl.DataSetDefineDao;
import com.jiuqi.nr.query.dataset.defines.DataSetDefine;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DataSetDefineDaoImpl
implements IDataSetDefineDao {
    @Autowired
    DataSetDefineDao dataSetDao;
    private Logger logger = LoggerFactory.getLogger(DataSetDefineDaoImpl.class);

    @Override
    public Boolean InsertDataSet(DataSetDefine daDefine) {
        Assert.notNull((Object)daDefine, "'daDefine' must not be null");
        try {
            daDefine.setOrder(OrderGenerator.newOrder());
            daDefine.setUpdatetime(new Date());
            this.dataSetDao.insertDefine(daDefine);
            return true;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean DeleteDataSetById(String id) {
        try {
            this.dataSetDao.deleteDefineById(id);
            return true;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean DeleteDataSetByName(String name) {
        try {
            this.dataSetDao.deleteDefineByName(name);
            return true;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean UpdateDataSet(DataSetDefine daDefine) {
        Assert.notNull((Object)daDefine, "'daDefine' must not be null");
        try {
            daDefine.setUpdatetime(new Date());
            this.dataSetDao.updateDefine(daDefine);
            return true;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public DataSetDefine QueryDataSetDefineById(String id) {
        try {
            return this.dataSetDao.getDataSetDefineById(id);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataSetDefine> QueryDataSetDefinesByNameAndCreator(String name) {
        try {
            return this.dataSetDao.getDefineByNameAndCreatorWithoutModel(name);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataSetDefine> QueryDataSetDefinesByName(String name) {
        try {
            return this.dataSetDao.getDefineByNameWithoutModel(name);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public DataSetDefine QueryDataSetDefineByNameAndCreator(String name) {
        try {
            List<DataSetDefine> defines = this.dataSetDao.getDefineByNameAndCreatorWithoutModel(name);
            if (defines.size() > 0) {
                return defines.get(0);
            }
            return null;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public DataSetDefine QueryDataSetDefineByName(String name) {
        try {
            List<DataSetDefine> defines = this.dataSetDao.getDefineByNameWithoutModel(name);
            if (defines.size() > 0) {
                return defines.get(0);
            }
            return null;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataSetDefine> QueryDataSetDefineByCreator() {
        try {
            return this.dataSetDao.getDataSetListByCreator();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataSetDefine> QueryDataSetDefine() {
        try {
            return this.dataSetDao.getDataSetList();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataSetDefine> QueryDataSetDefineByParentIdAndCreator(String id) {
        try {
            return this.dataSetDao.listDefineByParentIdAndCreatorWithoutModel(id);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataSetDefine> QueryDataSetDefineByParentId(String id) {
        try {
            return this.dataSetDao.listDefineByParentIdWithoutModel(id);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public DataSetDefine QueryDataSetModelById(String id) {
        try {
            return this.dataSetDao.getDataSetModelById(id);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataSetDefine> QueryDataSetDefineByTitleAndParentIdAndCreator(String title, String parentId) {
        try {
            return this.dataSetDao.getDefineByTitleAndParentIdAndCreator(title, parentId);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<DataSetDefine> QueryDataSetDefineByTitleAndParentId(String title, String parentId) {
        try {
            return this.dataSetDao.getDefineByTitleAndParentId(title, parentId);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Boolean UpdateDataSetModel(String id, String model) {
        try {
            this.dataSetDao.updateDefineModel(id, model);
            return true;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return false;
        }
    }
}

