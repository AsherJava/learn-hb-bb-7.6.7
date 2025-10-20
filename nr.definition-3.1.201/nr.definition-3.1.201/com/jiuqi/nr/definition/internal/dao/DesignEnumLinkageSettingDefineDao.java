/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignEnumLinkageSettingDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignEnumLinkageSettingDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignEnumLinkageSettingDefineDao
extends BaseDao {
    private static String ATTR_GROUP = "groupName";
    private static String ATTR_CODE = "taskCode";
    private Class<DesignEnumLinkageSettingDefineImpl> implClass = DesignEnumLinkageSettingDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignEnumLinkageSettingDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{id}, this.implClass);
    }

    public List<DesignEnumLinkageSettingDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public DesignEnumLinkageSettingDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignEnumLinkageSettingDefine)defines.get(0);
        }
        return null;
    }

    public DesignEnumLinkageSettingDefine getDefineByKey(String id) throws Exception {
        return (DesignEnumLinkageSettingDefine)this.getByKey(id, this.implClass);
    }
}

