/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.FormSchemeAndTaskKeysObj
 */
package com.jiuqi.nr.formulamapping.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine;
import com.jiuqi.nr.definition.formulamapping.facade.FormSchemeAndTaskKeysObj;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FormulaMappingSchemeDaoImpl
extends BaseDao {
    private static final String TASK_TITLE = "TK_TITLE";
    private static final String TASK_KEY = "TK_KEY";
    private static final String FORMSCHEME_TITLE = "FC_TITLE";
    private static final String FORMSCHEME_KEY = "FC_KEY";
    private static final String FORMULASCHEME_TITLE = "FS_TITLE";
    private static final String SPLIT_LINE = "|";
    private static final String FMS_TARGET_FS_KEY = "targetFSKey";
    private Class<FormulaMappingSchemeDefine> implClass = FormulaMappingSchemeDefine.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public FormSchemeAndTaskKeysObj getTaskAndFormSchemeObj(String formulaSchemeKey) {
        String s = new String("select t.TK_KEY, f.FC_KEY from nr_param_task t left join nr_param_formscheme f on  t.TK_KEY =f.FC_TASK_KEY left join nr_param_formulascheme s on f.FC_KEY = s.FS_FORM_SCHEME_KEY where s.FS_KEY = ?");
        return (FormSchemeAndTaskKeysObj)this.jdbcTemplate.query(s, rs -> {
            FormSchemeAndTaskKeysObj formSchemeAndTaskKeysObj = new FormSchemeAndTaskKeysObj();
            if (rs.next()) {
                formSchemeAndTaskKeysObj.setTaskKey(rs.getString(TASK_KEY));
                formSchemeAndTaskKeysObj.setFormSchemeKey(rs.getString(FORMSCHEME_KEY));
            }
            return formSchemeAndTaskKeysObj;
        }, new Object[]{formulaSchemeKey});
    }

    public String queryFormulaMappingSchemeTitle(String formulaSchemeKey) {
        String s = new String("select t.TK_TITLE, f.FC_TITLE, s.FS_TITLE from nr_param_task t left join nr_param_formscheme f on  t.TK_KEY =f.FC_TASK_KEY left join nr_param_formulascheme s on f.FC_KEY = s.FS_FORM_SCHEME_KEY where s.FS_KEY = ?");
        return (String)this.jdbcTemplate.query(s, rs -> {
            String formulaSchemeLinkTitle = "";
            if (rs.next()) {
                formulaSchemeLinkTitle = formulaSchemeLinkTitle + rs.getString(TASK_TITLE) + SPLIT_LINE + rs.getString(FORMSCHEME_TITLE) + SPLIT_LINE + rs.getString(FORMULASCHEME_TITLE);
            }
            return formulaSchemeLinkTitle;
        }, new Object[]{formulaSchemeKey});
    }

    public List<FormulaMappingSchemeDefine> queryFormulaMappingSchemeObjs() {
        return this.list(this.implClass);
    }

    public void insertFormulaMappingSchemeDefine(FormulaMappingSchemeDefine formulaMappingSchemeDefine) throws Exception {
        this.insert(formulaMappingSchemeDefine);
    }

    public void updateFormulaMappingSchemeDefine(FormulaMappingSchemeDefine formulaMappingSchemeDefine) throws Exception {
        this.update(formulaMappingSchemeDefine);
    }

    public void deletsFormulaMappingSchemeDefine(String[] keys) throws Exception {
        this.delete(keys);
    }

    public FormulaMappingSchemeDefine queryFormulaMappingSchemeDefine(String key) {
        return (FormulaMappingSchemeDefine)super.getByKey((Object)key, this.implClass);
    }

    public List<FormulaMappingSchemeDefine> queryFMSDefine(String targetFormulaSchemeKey) {
        return super.list(new String[]{FMS_TARGET_FS_KEY}, new Object[]{targetFormulaSchemeKey}, this.implClass);
    }

    public FormulaMappingSchemeDefine queryFormulaMappingSchemeObjsByKey(String key) {
        return (FormulaMappingSchemeDefine)super.getByKey((Object)key, this.implClass);
    }
}

