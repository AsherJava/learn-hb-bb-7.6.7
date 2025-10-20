/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  com.jiuqi.np.definition.internal.db.TransUtil
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.np.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.facade.DesignCaliberGroupDefine;
import com.jiuqi.nr.definition.internal.impl.DesignCaliberGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignCaliberGroupLink;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignCaliberGroupDao
extends BaseDao {
    private static String ATTR_CODE = "taskCode";
    private static String ATTR_TITLE = "title";
    private static String ATTR_PARENT = "parentKey";
    private Class<DesignCaliberGroupDefineImpl> implClass = DesignCaliberGroupDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignCaliberGroupDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_PARENT}, new Object[]{id}, this.implClass);
    }

    public List<DesignCaliberGroupDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public DesignCaliberGroupDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignCaliberGroupDefine)defines.get(0);
        }
        return null;
    }

    public List<DesignCaliberGroupDefine> queryDefinesByParent(String parentKey) throws Exception {
        List defines = this.list(new String[]{ATTR_PARENT}, new Object[]{parentKey}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return null;
    }

    public List<DesignCaliberGroupDefine> queryDefinesByTitle(String formGroupTitle) throws Exception {
        List defines = this.list(new String[]{ATTR_TITLE}, new Object[]{formGroupTitle}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return null;
    }

    public DesignCaliberGroupDefine getDefineByKey(String id) throws Exception {
        return (DesignCaliberGroupDefine)this.getByKey(id, this.implClass);
    }

    public List<DesignCaliberGroupDefine> getAllGroupsFromCaliber(String tableKey) throws Exception {
        DesignCaliberGroupLink link = new DesignCaliberGroupLink();
        link.setObjectKey(tableKey);
        return this.listByForeign(link, new String[]{"tableKey"}, DesignCaliberGroupDefineImpl.class);
    }

    public List<DesignCaliberGroupDefine> getAllGroupsFromLink() throws Exception {
        DesignCaliberGroupLink link = new DesignCaliberGroupLink();
        link.setObjectKey(null);
        return this.listByForeign(link, new String[0], DesignCaliberGroupDefineImpl.class);
    }
}

