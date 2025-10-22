/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.dafafill.dao.DataFillDataDao;
import com.jiuqi.nr.dafafill.dao.DataFillDefinitionDao;
import com.jiuqi.nr.dafafill.dao.DataFillGroupDao;
import com.jiuqi.nr.dafafill.entity.DataFillGroup;
import com.jiuqi.nr.dafafill.service.IDataFillGroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DataFillGroupServiceImpl
implements IDataFillGroupService {
    @Autowired
    DataFillGroupDao dataFillGroupDao;
    @Autowired
    DataFillDefinitionDao dataFillDefinitionDao;
    @Autowired
    private DataFillDataDao dataFillDataDao;

    @Override
    public String add(DataFillGroup group) throws JQException {
        return this.dataFillGroupDao.add(group);
    }

    @Override
    public String modify(DataFillGroup group) throws JQException {
        return this.dataFillGroupDao.modify(group);
    }

    @Override
    public void delete(String id) throws JQException {
        this.dataFillGroupDao.deleteById(id);
    }

    @Override
    public DataFillGroup findById(String id) {
        return this.dataFillGroupDao.findById(id);
    }

    @Override
    public List<DataFillGroup> findByParentId(String pid) {
        return this.dataFillGroupDao.findByParentId(pid);
    }

    @Override
    public List<DataFillGroup> query() {
        return this.dataFillGroupDao.query();
    }

    @Override
    public void batchDelete(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        this.dataFillGroupDao.batchDelete(ids);
        this.batchDeleteChildren(ids);
    }

    private void batchDeleteChildren(List<String> ids) {
        List<String> definitionIds = this.dataFillDefinitionDao.batchFindByParentIds(ids);
        this.dataFillDefinitionDao.batchDelete(definitionIds);
        this.dataFillDataDao.batchDeleteByDefinitionIds(definitionIds);
        List<String> groupIds = this.dataFillGroupDao.batchFindByParentIds(ids);
        this.dataFillGroupDao.batchDelete(ids);
        if (CollectionUtils.isEmpty(groupIds)) {
            return;
        }
        this.batchDeleteChildren(groupIds);
    }

    @Override
    public List<DataFillGroup> fuzzySearch(String fuzzyKey) {
        return this.dataFillGroupDao.fuzzySearch(fuzzyKey);
    }

    @Override
    public List<String> getAllParentId() {
        return this.dataFillGroupDao.getAllParentId();
    }

    @Override
    public void batchModifyParentId(List<String> ids, String parentId) {
        this.dataFillGroupDao.batchModifyParentId(ids, parentId);
    }
}

