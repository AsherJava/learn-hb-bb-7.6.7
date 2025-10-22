/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaSchemeDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignFormulaSchemeDefineDao
extends BaseDao {
    private static String ATTR_CODE = "taskCode";
    private static String ATTR_FORM = "formSchemeKey";
    private Class<DesignFormulaSchemeDefineImpl> implClass = DesignFormulaSchemeDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignFormulaSchemeDefine> listByFormScheme(String id) throws Exception {
        return this.list(new String[]{ATTR_FORM}, new Object[]{id}, this.implClass);
    }

    public List<DesignFormulaSchemeDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public DesignFormulaSchemeDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignFormulaSchemeDefine)defines.get(0);
        }
        return null;
    }

    public DesignFormulaSchemeDefine getDefineByKey(String id) {
        return (DesignFormulaSchemeDefine)this.getByKey(id, this.implClass);
    }

    public List<DesignFormulaSchemeDefine> listGhostScheme() {
        String sqlWhere = " not exists (select 1 from NR_PARAM_FORMSCHEME_DES fc where FS_FORM_SCHEME_KEY = fc.FC_KEY)";
        return this.list(sqlWhere, null, this.implClass);
    }
}

