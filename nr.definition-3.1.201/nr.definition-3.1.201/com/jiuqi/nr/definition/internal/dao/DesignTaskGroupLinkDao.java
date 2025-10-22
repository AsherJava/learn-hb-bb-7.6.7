/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignTaskGroupLinkDao
extends BaseDao {
    private static String ATTR_GROUP = "groupKey";
    private static String ATTR_FIELD = "taskKey";
    private Class<DesignTaskGroupLink> implClass = DesignTaskGroupLink.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public void deleteLink(DesignTaskGroupLink define) throws Exception {
        this.deleteBy(new String[]{ATTR_GROUP, ATTR_FIELD}, new Object[]{define.getGroupKey(), define.getTaskKey()});
    }

    public List<DesignTaskGroupLink> getGroupLinkByTaskKey(String taskKey) {
        return super.list(new String[]{ATTR_FIELD}, new Object[]{taskKey}, this.implClass);
    }

    public List<DesignTaskGroupLink> getGroupLinkByGroupKey(String groupKey) {
        return super.list(new String[]{ATTR_GROUP}, new Object[]{groupKey}, this.implClass);
    }

    public List<DesignTaskGroupLink> getAllGroupLink() {
        return super.list(this.implClass);
    }

    public void deleteLinkByGroup(String groupKey) throws Exception {
        this.deleteBy(new String[]{ATTR_GROUP}, new Object[]{groupKey});
    }

    public void deleteLinkByTask(String taskKey) throws Exception {
        this.deleteBy(new String[]{ATTR_FIELD}, new Object[]{taskKey});
    }
}

