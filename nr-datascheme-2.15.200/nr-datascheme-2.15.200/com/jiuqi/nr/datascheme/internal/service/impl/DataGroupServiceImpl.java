/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.entity.DataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import com.jiuqi.nr.datascheme.internal.service.DataGroupDesignService;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataGroupServiceImpl
implements DataGroupDesignService {
    @Autowired
    private IDataGroupDao<DesignDataGroupDO> dataGroupDao;
    @Autowired
    private IDataSchemeDao<DesignDataSchemeDO> dataSchemeDao;

    @Override
    public List<DesignDataGroup> searchBy(String scheme, String title, int kind) {
        List<DesignDataGroupDO> r = this.dataGroupDao.searchBy(scheme, title, kind);
        return new ArrayList<DesignDataGroup>(r);
    }

    @Override
    public List<DesignDataGroup> searchBy(List<String> schemes, String title, int kind) {
        List<DesignDataGroupDO> r = this.dataGroupDao.searchBy(schemes, title, kind);
        return new ArrayList<DesignDataGroup>(r);
    }

    @Override
    public List<DesignDataGroup> searchBy(String title) {
        List<DesignDataGroupDO> r = this.dataGroupDao.searchBy((String)null, title, DataGroupKind.SCHEME_GROUP.getValue());
        return new ArrayList<DesignDataGroup>(r);
    }

    @Override
    public List<DesignDataGroup> searchBy(String scheme, String title) {
        List<DesignDataGroupDO> r = this.dataGroupDao.searchBy(scheme, title, DataGroupKind.TABLE_GROUP.getValue());
        return new ArrayList<DesignDataGroup>(r);
    }

    @Override
    public boolean existScheme(String key) {
        DesignDataGroupDO dataGroupDO = this.dataGroupDao.get(key);
        if (dataGroupDO == null) {
            return false;
        }
        DataGroupKind dataGroupKind = dataGroupDO.getDataGroupKind();
        if (DataGroupKind.SCHEME_GROUP == dataGroupKind) {
            String next;
            Stack<String> stack = new Stack<String>();
            stack.push(key);
            while (!stack.isEmpty() && (next = (String)stack.pop()) != null) {
                List<DesignDataSchemeDO> scheme = this.dataSchemeDao.getByParent(next);
                if (!scheme.isEmpty()) {
                    return true;
                }
                List<DesignDataGroupDO> byParent = this.dataGroupDao.getByParent(next);
                List children = byParent.stream().map(DataGroupDO::getKey).collect(Collectors.toList());
                if (children.isEmpty()) {
                    stack.addAll(children);
                }
                if (stack.size() <= 1000) continue;
                throw new RuntimeException("\u5b58\u5728\u73af\u5f62\u6570\u636e");
            }
        }
        return false;
    }
}

