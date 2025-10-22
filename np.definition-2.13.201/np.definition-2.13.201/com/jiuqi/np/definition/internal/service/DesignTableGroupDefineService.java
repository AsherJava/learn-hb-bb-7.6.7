/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.DesignTableGroupDefine;
import com.jiuqi.np.definition.internal.dao.DesignTableGroupDefineDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignTableGroupDefineService {
    @Autowired
    DesignTableGroupDefineDao dao;

    public void setDao(DesignTableGroupDefineDao dao) {
        this.dao = dao;
    }

    public String insertTableGroup(DesignTableGroupDefine tableGroup) throws Exception {
        if (tableGroup.getKey() == null) {
            tableGroup.setKey(UUIDUtils.getKey());
        }
        this.dao.insert(tableGroup);
        return tableGroup.getKey();
    }

    public void updateTableGroup(DesignTableGroupDefine tableGroup) throws Exception {
        this.dao.update(tableGroup);
    }

    public DesignTableGroupDefine queryTableGroup(String tableGroupKey) throws Exception {
        return this.dao.getGroup(tableGroupKey);
    }

    public DesignTableGroupDefine queryTableGroupByName(String tableGroupName) throws Exception {
        DesignTableGroupDefine group = null;
        List<DesignTableGroupDefine> groups = this.dao.getGroupByTitle(tableGroupName);
        if (null != groups && groups.size() > 0) {
            group = groups.get(0);
        }
        return group;
    }

    public List<DesignTableGroupDefine> queryAllRootTableGroups() throws Exception {
        return this.dao.queryAllRoot();
    }

    public List<DesignTableGroupDefine> getChildTableGroups(String tableGroupKey) throws Exception {
        return this.dao.queryChildGroups(tableGroupKey);
    }

    public List<DesignTableGroupDefine> getAllTableGroups() throws Exception {
        return this.dao.queryAll();
    }

    public void deleteGroupByKey(String tableGroupKey) throws BeanParaException, DBParaException {
        this.dao.delete(tableGroupKey);
    }

    public List<DesignTableGroupDefine> fuzzyQueryTableGroups(String keyword) throws Exception {
        return this.dao.fuzzyQueryTableGroups(keyword);
    }
}

