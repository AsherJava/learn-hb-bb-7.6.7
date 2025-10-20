/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.TaskGroupLinkDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskGroupLink;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeTaskGroupLinkDao
extends BaseDao {
    private static String ATTR_GROUP = "groupKey";
    private static String ATTR_FIELD = "formKey";
    private Class<RunTimeTaskGroupLink> implClass = RunTimeTaskGroupLink.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public void deleteLink(RunTimeTaskGroupLink define) throws Exception {
        super.deleteBy(new String[]{ATTR_GROUP, ATTR_FIELD}, new Object[]{define.getGroupKey(), define.getTaskKey()});
    }

    public List<TaskGroupLinkDefine> list() {
        return super.list(this.implClass);
    }
}

