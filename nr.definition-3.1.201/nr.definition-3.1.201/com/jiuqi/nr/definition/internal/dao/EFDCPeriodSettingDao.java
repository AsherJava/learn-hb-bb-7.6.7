/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class EFDCPeriodSettingDao
extends BaseDao {
    private static final Class<EFDCPeriodSettingDefineImpl> implClass = EFDCPeriodSettingDefineImpl.class;
    private final String FS_KEY = "formulaSchemeKey";

    public Class<?> getClz() {
        return implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public EFDCPeriodSettingDefineImpl queryByKey(String key) {
        return (EFDCPeriodSettingDefineImpl)this.getByKey(key, implClass);
    }

    public List<EFDCPeriodSettingDefineImpl> list() {
        return this.list(implClass);
    }

    public EFDCPeriodSettingDefineImpl queryByFormulaSchemeKey(String formulaSchemeKey) {
        List list = this.list(new String[]{"formulaSchemeKey"}, new Object[]{formulaSchemeKey}, implClass);
        if (list.size() > 0) {
            return (EFDCPeriodSettingDefineImpl)list.get(0);
        }
        return null;
    }

    public void deleteEFDCPeriodSetting(String key) throws Exception {
        this.delete(key);
    }

    public void deleteEFDCPeriodSettingByFSKey(String formulaSchemeKey) throws Exception {
        this.deleteBy(new String[]{"formulaSchemeKey"}, new Object[]{formulaSchemeKey});
    }
}

