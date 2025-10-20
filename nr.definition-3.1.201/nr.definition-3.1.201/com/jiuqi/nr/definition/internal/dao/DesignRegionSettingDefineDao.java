/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineImpl;
import com.jiuqi.nr.definition.util.DefinitionUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class DesignRegionSettingDefineDao
extends BaseDao {
    private static String ATTR_GROUP = "groupName";
    private static String ATTR_CODE = "taskCode";
    private Class<DesignRegionSettingDefineImpl> implClass = DesignRegionSettingDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignRegionSettingDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{id}, this.implClass);
    }

    public List<DesignRegionSettingDefine> listByKeys(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyList();
        }
        return DefinitionUtils.limitExe(keys, subKeys -> {
            String sbr = "RS_KEY IN (" + subKeys.stream().map(s -> "?").collect(Collectors.joining(",")) + ")";
            return this.list(sbr, subKeys.toArray(), this.implClass);
        });
    }

    public List<DesignRegionSettingDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public DesignRegionSettingDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignRegionSettingDefine)defines.get(0);
        }
        return null;
    }

    public DesignRegionSettingDefine getDefineByKey(String id) throws Exception {
        return (DesignRegionSettingDefine)this.getByKey(id, this.implClass);
    }
}

