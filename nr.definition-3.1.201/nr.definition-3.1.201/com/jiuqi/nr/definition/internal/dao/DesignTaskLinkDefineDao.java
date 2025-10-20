/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignTaskLinkDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignTaskLinkDefineDao
extends BaseDao {
    private static String ATTR_CurentFormSchemeKey = "currentFormSchemeKey";
    private static String ATTR_RelatedTaskCode = "relatedTaskCode";
    private static String ATTR_linkAlias = "linkAlias";
    private Class<DesignTaskLinkDefineImpl> implClass = DesignTaskLinkDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignTaskLinkDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public void deleteByCurrentFormScheme(String currentFormSchemeKey) throws Exception {
        this.deleteBy(new String[]{ATTR_CurentFormSchemeKey}, new Object[]{currentFormSchemeKey});
    }

    public void deleteByCurrentFormSchemeAndNumber(String currentFormSchemeKey, String linkAlia) throws Exception {
        this.deleteBy(new String[]{ATTR_CurentFormSchemeKey, ATTR_linkAlias}, new Object[]{currentFormSchemeKey, linkAlia});
    }

    public DesignTaskLinkDefine getDefineByKey(String id) throws Exception {
        return (DesignTaskLinkDefine)this.getByKey(id, this.implClass);
    }

    public List<DesignTaskLinkDefine> queryDefinesByCurrentFormScheme(String currentFormSchemeKey) throws Exception {
        return this.list(new String[]{ATTR_CurentFormSchemeKey}, new Object[]{currentFormSchemeKey}, this.implClass);
    }

    public DesignTaskLinkDefine queryDefinesByCurrentFormSchemeAndNumber(String currentFormSchemeKey, String linkAlia) throws Exception {
        List defines = this.list(new String[]{ATTR_CurentFormSchemeKey, ATTR_linkAlias}, new Object[]{currentFormSchemeKey, linkAlia}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignTaskLinkDefine)defines.get(0);
        }
        return null;
    }

    public List<DesignTaskLinkDefine> queryLinksByRelatedTaskCode(String relatedTaskCode) throws Exception {
        return this.list(new String[]{ATTR_RelatedTaskCode}, new Object[]{relatedTaskCode}, this.implClass);
    }
}

