/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.dao;

import com.jiuqi.np.definition.facade.TableIndexDefine;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.np.definition.internal.impl.DesignTableIndexDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignTableIndexDefineDao
extends BaseDao {
    private final String ATTR_NAME = "title";
    private Class<DesignTableIndexDefineImpl> implClz = DesignTableIndexDefineImpl.class;

    @Override
    public Class<?> getClz() {
        return this.implClz;
    }

    public TableIndexDefine getDefineByKey(Object key) throws Exception {
        return this.getByKey(key, this.implClz);
    }

    public void deleteByName(String indexName) throws Exception {
        this.deleteBy(new String[]{"title"}, new Object[]{indexName});
    }

    public TableIndexDefine getByName(String indexName) throws Exception {
        List<DesignTableIndexDefineImpl> list = this.list(new String[]{"title"}, new Object[]{indexName}, this.implClz);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}

