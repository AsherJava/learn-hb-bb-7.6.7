/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.TableIndexDefine;
import com.jiuqi.np.definition.internal.dao.TableIndexDefineDao;
import com.jiuqi.np.definition.internal.impl.TableIndexDefineImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableIndexDefineService {
    @Autowired
    TableIndexDefineDao dao;

    public String insertIndex(TableIndexDefineImpl tableIndexDefineImpl) throws Exception {
        if (tableIndexDefineImpl.getKey() == null) {
            tableIndexDefineImpl.setKey(UUIDUtils.getKey());
        }
        this.dao.insert(tableIndexDefineImpl);
        return tableIndexDefineImpl.getKey();
    }

    public List<TableIndexDefine> queryIndexs(String[] indexKeys) throws Exception {
        if (indexKeys == null || indexKeys.length == 0) {
            return null;
        }
        ArrayList<TableIndexDefine> defines = new ArrayList<TableIndexDefine>();
        for (String id : indexKeys) {
            TableIndexDefine define = this.dao.getDefineByKey(id);
            if (define == null) continue;
            defines.add(define);
        }
        return defines;
    }

    public TableIndexDefine queryIndex(String indexKey) throws Exception {
        return this.dao.getDefineByKey(indexKey);
    }

    public void removeIndexs(String[] indexKeys) throws Exception {
        this.dao.delete(indexKeys);
    }

    public void removeByName(String indexName) throws Exception {
        this.dao.deleteByName(indexName);
    }

    public TableIndexDefine getByName(String indexName) throws Exception {
        return this.dao.getByName(indexName);
    }

    public void removeIndex(TableIndexDefine define) throws Exception {
        this.dao.delete(define.getKey());
    }

    public TableIndexDefine getByKey(String indexKey) throws Exception {
        return this.dao.getDefineByKey(indexKey);
    }

    public void updateIndexDefine(TableIndexDefine idx) throws BeanParaException, DBParaException {
        this.dao.update(idx);
    }

    public void insert(TableIndexDefine[] defines) throws Exception {
        this.dao.insert(defines);
    }
}

