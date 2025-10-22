/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormulaSchemeDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeFormulaSchemeDefineDao
extends BaseDao {
    private static String ATTR_CODE = "taskCode";
    private static String ATTR_FORM = "formSchemeKey";
    private Class<RunTimeFormulaSchemeDefineImpl> implClass = RunTimeFormulaSchemeDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<FormulaSchemeDefine> listByFormScheme(String id) {
        return this.list(new String[]{ATTR_FORM}, new Object[]{id}, this.implClass);
    }

    public List<FormulaSchemeDefine> list() {
        return this.list(this.implClass);
    }

    public FormulaSchemeDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormulaSchemeDefine)defines.get(0);
        }
        return null;
    }

    public FormulaSchemeDefine getDefineByKey(String id) {
        return (FormulaSchemeDefine)this.getByKey(id, this.implClass);
    }

    public List<FormulaSchemeDefine> listGhostScheme() {
        String sqlWhere = " not exists (select 1 from NR_PARAM_FORMSCHEME fc where FS_FORM_SCHEME_KEY = fc.FC_KEY)";
        return this.list(sqlWhere, null, this.implClass);
    }
}

