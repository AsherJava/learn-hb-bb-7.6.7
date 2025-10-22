/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormulaSchemeDefineDao;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaDefineImpl;
import com.jiuqi.nr.definition.util.DefinitionUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class DesignFormulaDefineDao
extends BaseDao {
    private static String ATTR_GROUP = "groupName";
    private static String ATTR_CODE = "code";
    private static String ATTR_SCHMEME = "formulaSchemeKey";
    private static String ATTR_FORMKEY = "formKey";
    private static String ATTR_UseCalculate = "useCalculate";
    private static String ATTR_UseCheck = "useCheck";
    private static String ATTR_UseBalance = "useBalance";
    private static String ATTR_PRIVATE = "isPrivate";
    private static String ATTR_KEY = "key";
    private Class<DesignFormulaDefineImpl> implClass = DesignFormulaDefineImpl.class;
    @Autowired
    private DesignFormulaSchemeDefineDao formulaSchemeDao;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignFormulaDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP, ATTR_PRIVATE}, new Object[]{id, 0}, this.implClass);
    }

    public List<DesignFormulaDefine> list() throws Exception {
        return this.list(new String[]{ATTR_PRIVATE}, new Object[]{0}, this.implClass);
    }

    public void deleteByForm(String formKey) throws Exception {
        this.deleteBy(new String[]{ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formKey, 0});
    }

    public void deleteBySchemeAndForm(String formSchemeKey, String formKey) throws Exception {
        this.deleteBy(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formSchemeKey, formKey, 0});
    }

    public void deletePrivateFormulaBySchemeAndForm(String formSchemeKey, String formKey) throws Exception {
        this.deleteBy(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formSchemeKey, formKey, 0});
    }

    public void deletePrivateFormulaByForm(String formKey) throws Exception {
        this.deleteBy(new String[]{ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formKey, 0});
    }

    public void deleteByScheme(String formSchemeKey) throws Exception {
        this.deleteBy(new String[]{ATTR_SCHMEME, ATTR_PRIVATE}, new Object[]{formSchemeKey, 0});
    }

    public DesignFormulaDefine queryDefineByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE, ATTR_PRIVATE}, new Object[]{code, 0}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignFormulaDefine)defines.get(0);
        }
        return null;
    }

    public List<DesignFormulaDefine> queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE, ATTR_PRIVATE}, new Object[]{code, 0}, this.implClass);
        return defines;
    }

    public DesignFormulaDefine queryFormulaDefineBySchemeAndCode(String formulaDefineCode, String formulaSchemeKey) throws Exception {
        List defines = this.list(new String[]{ATTR_SCHMEME, ATTR_CODE, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formulaDefineCode, 0}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignFormulaDefine)defines.get(0);
        }
        return null;
    }

    public List<DesignFormulaDefine> queryFormulaDefineBySchemeAndCodes(String formulaDefineCode, String formulaSchemeKey) throws Exception {
        List defines = this.list(new String[]{ATTR_SCHMEME, ATTR_CODE, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formulaDefineCode, 0}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return Collections.emptyList();
    }

    public List<DesignFormulaDefine> queryBJFormulaBySchemeAndForm(String formulaSchemeKey, String formKey, String formCode) {
        String regular = "(^" + formCode + "[ ]*\\[)|([^0-9a-zA-Z]" + formCode + "[ ]*\\[)";
        String whereSql = "FL_SCHEME_KEY=? AND (FL_FORM_KEY IS NULL OR FL_FORM_KEY <>?) AND FL_EXPRESSION like '%" + formCode + "%' AND FL_PRIVATE=0";
        List list = this.list(whereSql, new Object[]{formulaSchemeKey, formKey}, this.implClass);
        ArrayList<DesignFormulaDefine> result = new ArrayList<DesignFormulaDefine>();
        Pattern pattern = Pattern.compile(regular);
        for (DesignFormulaDefineImpl designFormulaDefine : list) {
            Matcher matcher = pattern.matcher(designFormulaDefine.getExpression());
            if (!matcher.find()) continue;
            result.add(designFormulaDefine);
        }
        return result;
    }

    public List<DesignFormulaDefine> queryFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey, DesignFormDefine designFormDefine) throws Exception {
        return this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formKey, 0}, this.implClass);
    }

    public List<DesignFormulaDefine> queryCalcFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey, DesignFormDefine designFormDefine) throws Exception {
        return this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_UseCalculate, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formKey, 1, 0}, this.implClass);
    }

    public List<DesignFormulaDefine> queryCheckFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey, DesignFormDefine designFormDefine) throws Exception {
        return this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_UseCheck, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formKey, 1, 0}, this.implClass);
    }

    public List<DesignFormulaDefine> queryBalanceFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey, DesignFormDefine designFormDefine) throws Exception {
        return this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_UseBalance, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formKey, 1, 0}, this.implClass);
    }

    public List<DesignFormulaDefine> queryFormulaDefineByScheme(String formulaSchemeKey, List<DesignFormDefine> forms) throws Exception {
        List allDefines = this.list(new String[]{ATTR_SCHMEME, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, 0}, this.implClass);
        return allDefines;
    }

    public List<DesignFormulaDefine> querySimpleFormulaDefineByScheme(String formulaScheme) {
        String sql = "SELECT FL_KEY,FL_CODE,FL_EXPRESSION FROM nr_param_formula_des WHERE FL_SCHEME_KEY=? AND FL_PRIVATE=0";
        List designFormulaDefines = this.queryList(sql, new String[]{formulaScheme}, (RowMapper)new RowMapper<DesignFormulaDefine>(){

            public DesignFormulaDefine mapRow(ResultSet rs, int rowNum) throws SQLException {
                DesignFormulaDefineImpl def = new DesignFormulaDefineImpl();
                def.setKey(rs.getString("FL_KEY"));
                def.setCode(rs.getString("FL_CODE"));
                def.setExpression(rs.getString("FL_EXPRESSION"));
                return def;
            }
        });
        return designFormulaDefines;
    }

    public List<DesignFormulaDefine> queryCalcFormulaDefineByScheme(String formulaSchemeKey, List<DesignFormDefine> forms) throws Exception {
        List allDefines = this.list(new String[]{ATTR_SCHMEME, ATTR_UseCalculate, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, 1, 0}, this.implClass);
        ArrayList<DesignFormulaDefine> defines = new ArrayList<DesignFormulaDefine>();
        DesignFormulaSchemeDefine designFormulaSchemeDefine = this.formulaSchemeDao.getDefineByKey(formulaSchemeKey);
        String formulaSchemeTitle = designFormulaSchemeDefine.getTitle();
        if ((forms = (List)forms.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<DesignFormDefine>(Comparator.comparing(IBaseMetaItem::getKey))), ArrayList::new))) != null && forms.size() > 0) {
            for (DesignFormDefine designFormDefine : forms) {
                ArrayList newDefines;
                if (!designFormDefine.getQuoteType() || (newDefines = new ArrayList()) == null || newDefines.size() <= 0) continue;
                defines.addAll(newDefines);
            }
        }
        if (defines != null && defines.size() > 0) {
            allDefines.addAll(defines);
        }
        return defines;
    }

    public List<DesignFormulaDefine> queryCheckFormulaDefineByScheme(String formulaSchemeKey, List<DesignFormDefine> forms) throws Exception {
        List allDefines = this.list(new String[]{ATTR_SCHMEME, ATTR_UseCheck, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, 1, 0}, this.implClass);
        ArrayList defines = new ArrayList();
        DesignFormulaSchemeDefine designFormulaSchemeDefine = this.formulaSchemeDao.getDefineByKey(formulaSchemeKey);
        String formulaSchemeTitle = designFormulaSchemeDefine.getTitle();
        if ((forms = (List)forms.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<DesignFormDefine>(Comparator.comparing(IBaseMetaItem::getKey))), ArrayList::new))) != null && forms.size() > 0) {
            for (DesignFormDefine designFormDefine : forms) {
                ArrayList newDefines;
                if (!designFormDefine.getQuoteType() || (newDefines = new ArrayList()) == null || newDefines.size() <= 0) continue;
                defines.addAll(newDefines);
            }
        }
        if (defines != null && defines.size() > 0) {
            allDefines.addAll(defines);
        }
        return allDefines;
    }

    public List<DesignFormulaDefine> queryBalanceFormulaDefineByScheme(String formulaSchemeKey, List<DesignFormDefine> forms) throws Exception {
        List allDefines = this.list(new String[]{ATTR_SCHMEME, ATTR_UseBalance, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, 1, 0}, this.implClass);
        ArrayList<DesignFormulaDefine> defines = new ArrayList<DesignFormulaDefine>();
        DesignFormulaSchemeDefine designFormulaSchemeDefine = this.formulaSchemeDao.getDefineByKey(formulaSchemeKey);
        String formulaSchemeTitle = designFormulaSchemeDefine.getTitle();
        if ((forms = (List)forms.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<DesignFormDefine>(Comparator.comparing(IBaseMetaItem::getKey))), ArrayList::new))) != null && forms.size() > 0) {
            for (DesignFormDefine designFormDefine : forms) {
                ArrayList newDefines;
                if (!designFormDefine.getQuoteType() || (newDefines = new ArrayList()) == null || newDefines.size() <= 0) continue;
                defines.addAll(newDefines);
            }
        }
        if (defines != null && defines.size() > 0) {
            allDefines.addAll(defines);
        }
        return defines;
    }

    public int updateFormulaCheckType(int oldType, int newType) {
        String sql = "update NR_PARAM_FORMULA_DES set FL_CHECK_TYPE=? where FL_CHECK_TYPE=? AND FL_PRIVATE=0";
        int update = this.jdbcTemplate.update(sql, new Object[]{newType, oldType});
        return update;
    }

    public DesignFormulaDefine getDefineByKey(String id) throws Exception {
        DesignFormulaDefineImpl byKey = (DesignFormulaDefineImpl)this.getByKey(id, this.implClass);
        if (null == byKey) {
            return null;
        }
        return byKey.getIsPrivate() ? null : byKey;
    }

    public List<DesignFormulaDefine> listGhostFormula() {
        List<DesignFormulaDefine> result = new ArrayList();
        String sqlWhere = " not exists (select 1 from NR_PARAM_FORM_DES fm where FL_FORM_KEY = fm.FM_KEY) and FL_FORM_KEY is not null and FL_PRIVATE = 0";
        List result1 = this.list(sqlWhere, null, this.implClass);
        sqlWhere = " not exists(select 1 from NR_PARAM_FORMULASCHEME_DES fs where FL_SCHEME_KEY = fs.FS_KEY) AND FL_PRIVATE=0";
        List result2 = this.list(sqlWhere, null, this.implClass);
        if (result1.size() == 0) {
            result = result2;
        } else if (result2.size() == 0) {
            result = result1;
        } else {
            HashSet<String> keySet = new HashSet<String>();
            for (DesignFormulaDefine formula : result1) {
                if (!keySet.add(formula.getKey())) continue;
                result.add(formula);
            }
            for (DesignFormulaDefine formula : result2) {
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
        HashedMap<String, FormulaSchemeType> formulaSchemeByType = new HashedMap<String, FormulaSchemeType>();
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

    private List<DesignFormulaDefine> filterFormulaBySchemeTypeAndPublic(DesignFormulaSchemeDefine designFormulaSchemeDefine, List<DesignFormulaDefine> oldDefines) throws Exception {
        ArrayList<DesignFormulaDefine> defines = new ArrayList<DesignFormulaDefine>();
        if (oldDefines == null || oldDefines.size() == 0) {
            return defines;
        }
        FormulaSchemeType formulaSchemeType = designFormulaSchemeDefine.getFormulaSchemeType();
        HashedMap<String, FormulaSchemeType> formulaSchemeByType = new HashedMap<String, FormulaSchemeType>();
        for (DesignFormulaDefine designFormulaDefine : oldDefines) {
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

    public int getBJFormulaCountByFormulaSchemeKey(String formulaSchemeKey, String formKey) {
        String sql = "SELECT COUNT(*) FROM  NR_PARAM_FORMULA_DES WHERE FL_SCHEME_KEY=?  AND FL_FORM_KEY=? AND FL_PRIVATE=0";
        int count = (Integer)this.jdbcTemplate.queryForObject(sql, (Object[])new String[]{formulaSchemeKey, formKey}, Integer.class);
        return count;
    }

    public List<DesignFormulaDefine> listFormulaDefineByScheme(String formulaSchemeKey) throws Exception {
        List allDefines = this.list(new String[]{ATTR_SCHMEME, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, 0}, this.implClass);
        return allDefines;
    }

    public List<DesignFormulaDefine> listCalcFormulaDefineByScheme(String formulaSchemeKey) throws Exception {
        List allDefines = this.list(new String[]{ATTR_SCHMEME, ATTR_UseCalculate, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, 1, 0}, this.implClass);
        return allDefines;
    }

    public List<DesignFormulaDefine> listCheckFormulaDefineByScheme(String formulaSchemeKey) throws Exception {
        return this.list(new String[]{ATTR_SCHMEME, ATTR_UseCheck, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, 1, 0}, this.implClass);
    }

    public List<DesignFormulaDefine> listBalanceFormulaDefineByScheme(String formulaSchemeKey) throws Exception {
        List allDefines = this.list(new String[]{ATTR_SCHMEME, ATTR_UseBalance, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, 1, 0}, this.implClass);
        return allDefines;
    }

    public List<DesignFormulaDefine> listFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey) throws Exception {
        return this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formKey, 0}, this.implClass);
    }

    public List<DesignFormulaDefine> listCalcFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey) throws Exception {
        return this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_UseCalculate, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formKey, 1, 0}, this.implClass);
    }

    public List<DesignFormulaDefine> listCheckFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey) throws Exception {
        return this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_UseCheck, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formKey, 1, 0}, this.implClass);
    }

    public List<DesignFormulaDefine> listBalanceFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey) throws Exception {
        return this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY, ATTR_UseBalance, ATTR_PRIVATE}, new Object[]{formulaSchemeKey, formKey, 1, 0}, this.implClass);
    }

    public List<DesignFormulaDefine> search(Collection<String> formulaSchemeKeys, String keyword) {
        String fsKeys = formulaSchemeKeys.stream().map(f -> "?").collect(Collectors.joining(","));
        String sqlWhere = "FL_SCHEME_KEY IN (" + fsKeys + ") AND FL_EXPRESSION LIKE ? ";
        ArrayList<String> args = new ArrayList<String>(formulaSchemeKeys);
        args.add("%" + keyword + "%");
        return this.list(sqlWhere, args.toArray(), this.implClass);
    }

    public List<DesignFormulaDefine> listFormulaDefineByForm(String formKey) {
        return this.list(new String[]{ATTR_FORMKEY, ATTR_PRIVATE}, new Object[]{formKey, 0}, this.implClass);
    }

    public List<DesignFormulaDefine> listByKey(List<String> keys) {
        return DefinitionUtils.limitExe(keys, this::listByFormKeys);
    }

    private List<DesignFormulaDefine> listByFormKeys(List<String> keys) {
        String sql = "SELECT T.* FROM NR_PARAM_FORMULA_DES T WHERE T.FL_KEY IN (:keys) ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("keys", keys);
        RowMapper rowMapper = (rs, rowNum) -> {
            DesignFormulaDefineImpl dataLinkDefine = new DesignFormulaDefineImpl();
            this.readRecord(rs, dataLinkDefine);
            return dataLinkDefine;
        };
        this.buildMethod();
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }
}

