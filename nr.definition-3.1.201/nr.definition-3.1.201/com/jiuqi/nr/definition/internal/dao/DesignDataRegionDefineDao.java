/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignDataRegionDefineDao
extends BaseDao {
    private static final String ATTR_FORMKEY = "formKey";
    private Class<DesignDataRegionDefineImpl> implClass = DesignDataRegionDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignDataRegionDefine> list() {
        return this.list(this.implClass);
    }

    public DesignDataRegionDefine getDefineByKey(String id) {
        return (DesignDataRegionDefine)this.getByKey(id, this.implClass);
    }

    public List<DesignDataRegionDefine> getAllRegionsInForm(String formKey) {
        return this.list(new String[]{ATTR_FORMKEY}, new Object[]{formKey}, this.implClass);
    }

    public DesignDataRegionDefine getDataRegion(String formKey, String regionCode) {
        String sqlWhere = " DR_FORM_KEY=? AND DR_CODE=? ";
        return (DesignDataRegionDefine)this.getBy(sqlWhere, new Object[]{formKey, regionCode}, this.implClass);
    }

    public List<DesignDataRegionDefine> listGhostRegion() {
        String sqlWhere = " not exists (select 1 from NR_PARAM_FORM_DES fm where DR_FORM_KEY = fm.FM_KEY)";
        return this.list(sqlWhere, null, this.implClass);
    }
}

