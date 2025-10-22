/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.EnumLinkageSettingDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeEnumLinkageSettingDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeEnumLinkageSettingDefineDao
extends BaseDao {
    private static String ATTR_MASTERLINK = "masterLink";
    private static String ATTR_CODE = "taskCode";
    private Class<RunTimeEnumLinkageSettingDefineImpl> implClass = RunTimeEnumLinkageSettingDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<EnumLinkageSettingDefine> listByLink(String linkId) throws Exception {
        return this.list(new String[]{ATTR_MASTERLINK}, new Object[]{linkId}, this.implClass);
    }

    public List<EnumLinkageSettingDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public EnumLinkageSettingDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (EnumLinkageSettingDefine)defines.get(0);
        }
        return null;
    }

    public EnumLinkageSettingDefine getDefineByKey(String id) throws Exception {
        return (EnumLinkageSettingDefine)this.getByKey(id, this.implClass);
    }
}

