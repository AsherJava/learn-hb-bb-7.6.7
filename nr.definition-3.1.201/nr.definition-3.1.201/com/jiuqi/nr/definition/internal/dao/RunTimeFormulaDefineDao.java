/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormulaDefineImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeFormulaDefineDao
extends BaseDao {
    private static String ATTR_GROUP = "groupName";
    private static String ATTR_CODE = "code";
    private static String ATTR_SCHMEME = "formulaSchemeKey";
    private static String ATTR_FORMKEY = "formKey";
    private static String ATTR_UseCalculate = "useCalculate";
    private static String ATTR_UseCheck = "useCheck";
    private static String ATTR_UseBalance = "useBalance";
    private static String ATTR_SCHEMETITLE = "formulaSchemeTitle";
    private static String ATTR_PRIVATE = "isPrivate";
    private Class<RunTimeFormulaDefineImpl> implClass = RunTimeFormulaDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<FormulaDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP, ATTR_PRIVATE}, new Object[]{id, 0}, this.implClass);
    }

    public List<FormulaDefine> list() throws Exception {
        return this.list(new String[]{ATTR_PRIVATE}, new Object[]{0}, this.implClass);
    }

    public void deleteByForm(String formKey) throws Exception {
        this.deleteBy(new String[]{ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formKey, 0});
    }

    public void deleteBySchemeAndForm(String formSchemeKey, String formKey) throws Exception {
        this.deleteBy(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formSchemeKey, formKey, 0});
    }

    public void deleteByScheme(String formSchemeKey) throws Exception {
        this.deleteBy(new String[]{ATTR_SCHMEME, ATTR_PRIVATE}, new Object[]{formSchemeKey, 0});
    }

    public FormulaDefine queryDefineByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE, ATTR_PRIVATE}, new Object[]{code, 0}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormulaDefine)defines.get(0);
        }
        return null;
    }

    public List<FormulaDefine> queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE, ATTR_PRIVATE}, new Object[]{code, 0}, this.implClass);
        return defines;
    }

    public FormulaDefine queryFormulaDefineBySchemeAndCode(String formulaDefineCode, String formulaSchemeKey) throws Exception {
        List defines = this.list(new String[]{ATTR_SCHMEME, ATTR_CODE, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formulaDefineCode, 0}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormulaDefine)defines.get(0);
        }
        return null;
    }

    public FormulaDefine queryFormulaDefineByFormAndCode(String formulaDefineCode, String formKey) throws Exception {
        List defines = this.list(new String[]{ATTR_FORMKEY, ATTR_CODE, ATTR_PRIVATE}, new Object[]{formKey, formulaDefineCode, 0}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormulaDefine)defines.get(0);
        }
        return null;
    }

    public List<FormulaDefine> queryFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey) throws Exception {
        List defines = this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formKey, 0}, this.implClass);
        return defines;
    }

    public List<FormulaDefine> queryFormulaDefineByScheme(String formulaSchemeKey) {
        List defines = this.list(new String[]{ATTR_SCHMEME, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, 0}, this.implClass);
        return defines;
    }

    public FormulaDefine getDefineByKey(String id) {
        RunTimeFormulaDefineImpl byKey = (RunTimeFormulaDefineImpl)this.getByKey(id, this.implClass);
        if (null == byKey) {
            return null;
        }
        return byKey.getIsPrivate() ? null : byKey;
    }

    public int updateFormulaCheckType(int oldType, int newType) {
        String sql = "update NR_PARAM_FORMULA set FL_CHECK_TYPE=? where FL_CHECK_TYPE=? AND FL_PRIVATE=0";
        int update = this.jdbcTemplate.update(sql, new Object[]{newType, oldType});
        return update;
    }

    public List<FormulaDefine> searchFormula(String formulaCode, String formulaSchemeKey) {
        return this.list("FL_SCHEME_KEY=? and FL_CODE like ? AND FL_PRIVATE=0", new Object[]{formulaSchemeKey, "%".concat(formulaCode).concat("%")}, this.implClass);
    }

    public List<FormulaDefine> listGhostFormula() {
        List<FormulaDefine> result = new ArrayList();
        String sqlWhere = " not exists (select 1 from NR_PARAM_FORM fm where FL_FORM_KEY = fm.FM_KEY) and FL_FORM_KEY is not null and fl_private=0";
        List result1 = this.list(sqlWhere, null, this.implClass);
        sqlWhere = " not exists(select 1 from NR_PARAM_FORMULASCHEME fs where FL_SCHEME_KEY = fs.FS_KEY) and fl_private=0";
        List result2 = this.list(sqlWhere, null, this.implClass);
        if (result1.size() == 0) {
            result = result2;
        } else if (result2.size() == 0) {
            result = result1;
        } else {
            HashSet<String> keySet = new HashSet<String>();
            for (FormulaDefine formula : result1) {
                if (!keySet.add(formula.getKey())) continue;
                result.add(formula);
            }
            for (FormulaDefine formula : result2) {
                if (!keySet.add(formula.getKey())) continue;
                result.add(formula);
            }
        }
        return result;
    }

    public List<FormulaDefine> getPublicFormulasInForm(String formulaSchemeTitle, String formKey) {
        List formulaDefines = this.list(new String[]{ATTR_FORMKEY, ATTR_SCHEMETITLE, ATTR_PRIVATE}, new Object[]{formKey, formulaSchemeTitle, 0}, this.implClass);
        return formulaDefines;
    }
}

