/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.dao;

import com.jiuqi.np.definition.facade.DesignTableGroupDefine;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.np.definition.internal.impl.DesignTableGroupDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignTableGroupDefineDao
extends BaseDao {
    private static String ATTR_PARENT = "parentKey";
    private static String ATTR_TITLE = "title";
    private Class<DesignTableGroupDefineImpl> implClass = DesignTableGroupDefineImpl.class;

    @Override
    public Class<?> getClz() {
        return this.implClass;
    }

    public DesignTableGroupDefine getGroup(String tableGroupKey) throws Exception {
        return this.getByKey(tableGroupKey, this.implClass);
    }

    public List<DesignTableGroupDefine> getGroupByTitle(String tableGroupName) throws Exception {
        return this.list(new String[]{ATTR_TITLE}, new Object[]{tableGroupName}, this.implClass);
    }

    public List<DesignTableGroupDefine> queryAllRoot() throws Exception {
        return this.list(" TG_PARENT_KEY is null or TG_PARENT_KEY='' ", null, this.implClass);
    }

    public List<DesignTableGroupDefine> queryChildGroups(String tableGroupKey) throws Exception {
        return this.list(new String[]{ATTR_PARENT}, new Object[]{tableGroupKey}, this.implClass);
    }

    public List<DesignTableGroupDefine> queryAll() throws Exception {
        return this.list(this.implClass);
    }

    public List<DesignTableGroupDefine> fuzzyQueryTableGroups(String keyword) throws Exception {
        keyword = keyword.replace("/", "//").replace("_", "/_").replace("%", "/%");
        return this.list(" TG_TITLE like ? escape ? ", new Object[]{"%" + keyword + "%", "/"}, this.implClass);
    }
}

