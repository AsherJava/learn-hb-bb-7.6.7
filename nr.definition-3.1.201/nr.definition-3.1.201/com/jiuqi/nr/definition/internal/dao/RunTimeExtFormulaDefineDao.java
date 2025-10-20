/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.apache.commons.collections4.map.HashedMap
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaSchemeDefineDao;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormulaDefineImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeExtFormulaDefineDao
extends BaseDao {
    private static String ATTR_GROUP = "groupName";
    private static String ATTR_CODE = "code";
    private static String ATTR_SCHMEME = "formulaSchemeKey";
    private static String ATTR_FORMKEY = "formKey";
    private static String ATTR_UseCheck = "useCheck";
    private static String ATTR_SCHEMETITLE = "formulaSchemeTitle";
    private static String ATTR_PRIVATE = "isPrivate";
    private static String ATTR_UNIT = "unit";
    private Class<RunTimeFormulaDefineImpl> implClass = RunTimeFormulaDefineImpl.class;
    @Autowired
    private RunTimeFormulaSchemeDefineDao formulaSchemeDao;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<FormulaDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP, ATTR_PRIVATE}, new Object[]{id, 1}, this.implClass);
    }

    public List<FormulaDefine> list() throws Exception {
        return this.list(new String[]{ATTR_PRIVATE}, new Object[]{1}, this.implClass);
    }

    public void deleteByForm(String formKey) throws Exception {
        this.deleteBy(new String[]{ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formKey, 1});
    }

    public void deleteBySchemeAndForm(String formSchemeKey, String formKey) throws Exception {
        this.deleteBy(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formSchemeKey, formKey, 1});
    }

    public void deleteByScheme(String formSchemeKey) throws Exception {
        this.deleteBy(new String[]{ATTR_SCHMEME, ATTR_PRIVATE}, new Object[]{formSchemeKey, 1});
    }

    public FormulaDefine queryDefineByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE, ATTR_PRIVATE}, new Object[]{code, 1}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormulaDefine)defines.get(0);
        }
        return null;
    }

    public List<FormulaDefine> queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE, ATTR_PRIVATE}, new Object[]{code, 1}, this.implClass);
        return defines;
    }

    public FormulaDefine queryFormulaDefineBySchemeAndCode(String formulaDefineCode, String formulaSchemeKey) throws Exception {
        List defines = this.list(new String[]{ATTR_SCHMEME, ATTR_CODE, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formulaDefineCode, 1}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormulaDefine)defines.get(0);
        }
        return null;
    }

    public FormulaDefine queryFormulaDefineByFormAndCode(String formulaDefineCode, String formKey) throws Exception {
        List defines = this.list(new String[]{ATTR_FORMKEY, ATTR_CODE, ATTR_PRIVATE}, new Object[]{formKey, formulaDefineCode, 1}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormulaDefine)defines.get(0);
        }
        return null;
    }

    public List<FormulaDefine> queryFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey) throws Exception {
        List defines = this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formKey, 1}, this.implClass);
        return defines;
    }

    public List<FormulaDefine> queryFormulaDefineByScheme(String formulaSchemeKey) {
        List defines = this.list(new String[]{ATTR_SCHMEME, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, 1}, this.implClass);
        return defines;
    }

    public FormulaDefine getDefineByKey(String id) {
        RunTimeFormulaDefineImpl byKey = (RunTimeFormulaDefineImpl)this.getByKey(id, this.implClass);
        if (null == byKey) {
            return null;
        }
        return byKey.getIsPrivate() ? byKey : null;
    }

    public int updateFormulaCheckType(int oldType, int newType) {
        String sql = "update NR_PARAM_FORMULA set FL_CHECK_TYPE=? where FL_CHECK_TYPE=? AND FL_PRIVATE=1";
        int update = this.jdbcTemplate.update(sql, new Object[]{newType, oldType});
        return update;
    }

    public List<FormulaDefine> searchFormula(String formulaCode, String formulaSchemeKey) {
        return this.list("FL_SCHEME_KEY=? and FL_CODE like ? AND FL_PRIVATE=1", new Object[]{formulaSchemeKey, "%".concat(formulaCode).concat("%")}, this.implClass);
    }

    public List<FormulaDefine> listGhostFormula() {
        List<FormulaDefine> result = new ArrayList();
        String sqlWhere = " not exists (select 1 from NR_PARAM_FORM fm where FL_FORM_KEY = fm.FM_KEY) and FL_FORM_KEY is not null and fl_private=1";
        List result1 = this.list(sqlWhere, null, this.implClass);
        sqlWhere = " not exists(select 1 from NR_PARAM_FORMULASCHEME fs where FL_SCHEME_KEY = fs.FS_KEY) and fl_private=1";
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

    private List<DesignFormulaDefine> filterFormulaBySchemeType(DesignFormulaSchemeDefine designFormulaSchemeDefine, List<DesignFormulaDefine> oldDefines) throws Exception {
        ArrayList<DesignFormulaDefine> defines = new ArrayList<DesignFormulaDefine>();
        if (oldDefines == null || oldDefines.size() == 0) {
            return defines;
        }
        FormulaSchemeType formulaSchemeType = designFormulaSchemeDefine.getFormulaSchemeType();
        HashedMap formulaSchemeByType = new HashedMap();
        for (DesignFormulaDefine designFormulaDefine : oldDefines) {
            String currFormulaSchemeKey = designFormulaDefine.getFormulaSchemeKey();
            FormulaSchemeType currFormulaSchemeType = (FormulaSchemeType)((Object)formulaSchemeByType.get(currFormulaSchemeKey));
            if (currFormulaSchemeType == null) {
                currFormulaSchemeType = this.formulaSchemeDao.getDefineByKey(currFormulaSchemeKey).getFormulaSchemeType();
                formulaSchemeByType.put(currFormulaSchemeKey, currFormulaSchemeType);
            }
            if (formulaSchemeType != currFormulaSchemeType) continue;
            defines.add(designFormulaDefine);
        }
        return defines;
    }

    private List<FormulaDefine> filterFormulaBySchemeTypeAndPublic(FormulaSchemeDefine designFormulaSchemeDefine, List<FormulaDefine> oldDefines) throws Exception {
        ArrayList<FormulaDefine> defines = new ArrayList<FormulaDefine>();
        if (oldDefines == null || oldDefines.size() == 0) {
            return defines;
        }
        FormulaSchemeType formulaSchemeType = designFormulaSchemeDefine.getFormulaSchemeType();
        HashedMap formulaSchemeByType = new HashedMap();
        for (FormulaDefine designFormulaDefine : oldDefines) {
            String currFormulaSchemeKey = designFormulaDefine.getFormulaSchemeKey();
            FormulaSchemeType currFormulaSchemeType = (FormulaSchemeType)((Object)formulaSchemeByType.get(currFormulaSchemeKey));
            if (currFormulaSchemeType == null) {
                currFormulaSchemeType = this.formulaSchemeDao.getDefineByKey(currFormulaSchemeKey).getFormulaSchemeType();
                formulaSchemeByType.put(currFormulaSchemeKey, currFormulaSchemeType);
            }
            if (formulaSchemeType != currFormulaSchemeType || designFormulaDefine.getFormulaSchemeKey().equals(designFormulaSchemeDefine.getKey())) continue;
            defines.add(designFormulaDefine);
        }
        return defines;
    }

    public List<FormulaDefine> getPublicFormulasInForm(String formulaSchemeTitle, String formKey) {
        List formulaDefines = this.list(new String[]{ATTR_FORMKEY, ATTR_SCHEMETITLE, ATTR_PRIVATE}, new Object[]{formKey, formulaSchemeTitle, 1}, this.implClass);
        return formulaDefines;
    }

    public List<FormulaDefine> getFormulaByUnit(String formulaSchemeKey, String formId, String unit) throws Exception {
        List defines = this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_PRIVATE, ATTR_UNIT}, new Object[]{formulaSchemeKey, formId, 1, unit}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return Collections.emptyList();
    }

    public void deleteBySchemeAndFormAndUnit(String formSchemeKey, String formKey, String unit) throws Exception {
        this.deleteBy(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_PRIVATE, ATTR_UNIT}, new Object[]{formSchemeKey, formKey, 1, unit});
    }
}

