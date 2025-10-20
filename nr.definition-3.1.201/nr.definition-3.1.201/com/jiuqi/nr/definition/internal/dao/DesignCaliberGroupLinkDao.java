/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  com.jiuqi.np.definition.internal.db.TransUtil
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.np.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignCaliberGroupLink;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignCaliberGroupLinkDao
extends BaseDao {
    private static String ATTR_GROUP = "groupKey";
    private static String ATTR_TABLEKEY = "tableKey";
    private Class<DesignCaliberGroupLink> implClass = DesignCaliberGroupLink.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public void deleteLink(DesignCaliberGroupLink define) throws Exception {
        this.deleteBy(new String[]{ATTR_GROUP, ATTR_TABLEKEY}, new Object[]{define.getGroupKey(), define.getObjectKey()});
    }

    public List<DesignCaliberGroupLink> getCaliberGroupLinksByCaliberId(String CaliberKey) throws Exception {
        return this.list(new String[]{ATTR_TABLEKEY}, new Object[]{CaliberKey}, this.implClass);
    }

    public DesignCaliberGroupLink queryDesignCaliberGroupLink(String CaliberKey, String CaliberGroupKey) throws Exception {
        List defines = this.list(new String[]{ATTR_TABLEKEY, ATTR_GROUP}, new Object[]{CaliberKey, CaliberGroupKey}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignCaliberGroupLink)defines.get(0);
        }
        return null;
    }

    public void updateLink(DesignCaliberGroupLink define) throws BeanParaException, DBParaException {
        this.update(define, new String[]{ATTR_TABLEKEY}, new Object[]{define.getObjectKey()});
    }

    public List<DesignCaliberGroupLink> getAllGroupLinkByTableGroupKey(String tableGroupKey) {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{tableGroupKey}, this.implClass);
    }
}

