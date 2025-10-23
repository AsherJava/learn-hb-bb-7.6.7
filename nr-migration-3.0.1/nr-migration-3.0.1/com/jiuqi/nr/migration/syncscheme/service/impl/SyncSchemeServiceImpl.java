/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.migration.syncscheme.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.migration.syncscheme.bean.SyncScheme;
import com.jiuqi.nr.migration.syncscheme.dao.ISyncSchemeDao;
import com.jiuqi.nr.migration.syncscheme.exception.SyncSchemeException;
import com.jiuqi.nr.migration.syncscheme.service.ISyncSchemeService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(rollbackFor={Exception.class})
public class SyncSchemeServiceImpl
implements ISyncSchemeService {
    @Autowired
    private ISyncSchemeDao syncSchemeDao;

    @Override
    public void check(boolean isUpdate, SyncScheme syncScheme) throws SyncSchemeException {
        if (isUpdate) {
            SyncScheme oldScheme;
            if (StringUtils.hasLength(syncScheme.getCode()) && (oldScheme = this.syncSchemeDao.getByCode(syncScheme.getCode())) != null && !oldScheme.getKey().equals(syncScheme.getKey())) {
                throw new SyncSchemeException("\u6570\u636e\u540c\u6b65\u65b9\u6848\u6807\u8bc6\u91cd\u590d" + syncScheme.getCode());
            }
            if (StringUtils.hasLength(syncScheme.getTitle()) && (oldScheme = this.syncSchemeDao.getByTitle(syncScheme.getTitle())) != null && !oldScheme.getKey().equals(syncScheme.getKey())) {
                throw new SyncSchemeException("\u6570\u636e\u540c\u6b65\u65b9\u6848\u6807\u9898\u91cd\u590d" + syncScheme.getTitle());
            }
        } else {
            SyncScheme byCode = this.syncSchemeDao.getByCode(syncScheme.getCode());
            if (byCode != null) {
                throw new SyncSchemeException("\u6570\u636e\u540c\u6b65\u65b9\u6848\u6807\u8bc6\u91cd\u590d" + syncScheme.getCode());
            }
            SyncScheme byTitle = this.syncSchemeDao.getByTitle(syncScheme.getTitle());
            if (byTitle != null) {
                throw new SyncSchemeException("\u6570\u636e\u540c\u6b65\u65b9\u6848\u6807\u9898\u91cd\u590d" + syncScheme.getTitle());
            }
        }
    }

    @Override
    public void add(SyncScheme syncScheme) throws SyncSchemeException {
        this.check(false, syncScheme);
        syncScheme.setKey(UUIDUtils.getKey());
        syncScheme.setUpdateTime(new Date().getTime());
        syncScheme.setOrder(OrderGenerator.newOrder());
        this.syncSchemeDao.add(syncScheme);
    }

    @Override
    public void update(SyncScheme syncScheme) throws SyncSchemeException {
        this.check(true, syncScheme);
        this.syncSchemeDao.update(syncScheme);
    }

    @Override
    public void updateData(SyncScheme syncScheme) throws SyncSchemeException {
        this.check(true, syncScheme);
        this.syncSchemeDao.updateData(syncScheme);
    }

    @Override
    public void batchDelete(List<String> keys) throws SyncSchemeException {
        this.syncSchemeDao.batchDelete(keys);
    }

    @Override
    public void moveUp(String key) throws SyncSchemeException {
        SyncScheme scheme = this.syncSchemeDao.get(key);
        List<SyncScheme> schemes = this.syncSchemeDao.getByParent(scheme.getGroup());
        int index = schemes.indexOf(scheme);
        if (index == -1) {
            throw new SyncSchemeException(String.format("\u540c\u6b65\u65b9\u6848[%s]\u4e0d\u5b58\u5728", key));
        }
        if (index == 0) {
            throw new SyncSchemeException("\u5df2\u7ecf\u662f\u7b2c\u4e00\u4e2a\uff0c\u65e0\u6cd5\u4e0a\u79fb");
        }
        SyncScheme beforeScheme = schemes.get(index - 1);
        String tempOrder = scheme.getOrder();
        scheme.setOrder(beforeScheme.getOrder());
        beforeScheme.setOrder(tempOrder);
        this.syncSchemeDao.update(beforeScheme);
        this.syncSchemeDao.update(scheme);
    }

    @Override
    public void moveDown(String key) throws SyncSchemeException {
        SyncScheme scheme = this.syncSchemeDao.get(key);
        List<SyncScheme> schemes = this.syncSchemeDao.getByParent(scheme.getGroup());
        int index = schemes.indexOf(scheme);
        if (index == -1) {
            throw new SyncSchemeException(String.format("\u540c\u6b65\u65b9\u6848[%s]\u4e0d\u5b58\u5728", key));
        }
        if (index == schemes.size() - 1) {
            throw new SyncSchemeException("\u5df2\u7ecf\u662f\u6700\u540e\u4e00\u4e2a\uff0c\u65e0\u6cd5\u4e0b\u79fb");
        }
        SyncScheme afterScheme = schemes.get(index + 1);
        String tempOrder = scheme.getOrder();
        scheme.setOrder(afterScheme.getOrder());
        afterScheme.setOrder(tempOrder);
        this.syncSchemeDao.update(afterScheme);
        this.syncSchemeDao.update(scheme);
    }

    @Override
    public SyncScheme getByKey(String key) throws SyncSchemeException {
        return this.syncSchemeDao.get(key);
    }

    @Override
    public List<SyncScheme> getByGroup(String group) throws SyncSchemeException {
        return this.syncSchemeDao.getByParent(group);
    }
}

