/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaVariableDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignFormulaVariableDefineDao
extends BaseDao {
    private static String FORM_SCHEME_KEY = "formSchemeKey";
    private static String ATTR_CODE = "code";
    private Class<DesignFormulaVariableDefineImpl> implClass = DesignFormulaVariableDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<FormulaVariDefine> queryAllFormulaVariable(String formSchemeKey) {
        List allDefines = this.list(new String[]{FORM_SCHEME_KEY}, new Object[]{formSchemeKey}, this.implClass);
        return allDefines;
    }

    public List<FormulaVariDefine> queryFormulaVariableByCodeOrTitle(String formSchemeKey, String searchContent) throws Exception {
        return this.list("(upper(FA_CODE) like upper(?) OR upper(FA_TITLE) like upper(?)) AND FA_FS_KEY = ?", new Object[]{"%".concat(searchContent).concat("%"), "%".concat(searchContent).concat("%"), formSchemeKey}, this.implClass);
    }

    public List<FormulaVariDefine> queryFormulaVariableByCode(String formSchemeKey, String code) {
        List allDefines = this.list(new String[]{FORM_SCHEME_KEY, ATTR_CODE}, new Object[]{formSchemeKey, code}, this.implClass);
        return allDefines;
    }
}

