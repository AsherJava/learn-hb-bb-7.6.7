/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeFormDefineDao
extends BaseDao {
    private static String ATTR_GROUP = "groupName";
    private static String ATTR_CODE = "formCode";
    private static String ATTR_SCHEME = "formScheme";
    private Class<RunTimeFormDefineImpl> implClass = RunTimeFormDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<FormDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{id}, this.implClass);
    }

    public List<FormDefine> listByGroup(String id, String formDefineCode) throws Exception {
        return this.list(new String[]{ATTR_GROUP, ATTR_CODE}, new Object[]{id, formDefineCode}, this.implClass);
    }

    public List<FormDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public void deleteByTask(String taskKey) throws Exception {
    }

    public FormDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormDefine)defines.get(0);
        }
        return null;
    }

    public List<FormDefine> queryDefinesListByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines) {
            return defines;
        }
        return null;
    }

    public FormDefine queryFormDefineByScheme(String formSchemeKey, String formDefineCode) throws Exception {
        List defines = this.list(new String[]{ATTR_SCHEME, ATTR_CODE}, new Object[]{formSchemeKey, formDefineCode}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormDefine)defines.get(0);
        }
        return null;
    }

    public FormDefine queryDefineBytask(String taskKey, String formDefineCode) throws Exception {
        String sqlWhere = " FM_FORMSCHEME in (select fc_key from nr_param_formscheme where fc_task_key=?) and fm_code=?";
        List defines = this.list(sqlWhere, new String[]{taskKey, formDefineCode}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormDefine)defines.get(0);
        }
        return null;
    }

    public List<FormDefine> queryDefinesBytask(String taskKey, String formDefineCode) throws Exception {
        String sqlWhere = " FM_FORMSCHEME in (select fc_key from nr_param_formscheme where fc_task_key=?) and fm_code=?";
        List defines = this.list(sqlWhere, new String[]{taskKey, formDefineCode}, this.implClass);
        return defines;
    }

    public List<FormDefine> queryDefinesBytask(String taskKey) {
        String sqlWhere = " FM_FORMSCHEME in (select fc_key from nr_param_formscheme where fc_task_key=?)";
        List defines = this.list(sqlWhere, new String[]{taskKey}, this.implClass);
        return defines;
    }

    public List<FormDefine> queryDefinesByFormScheme(String formSchemeKey) {
        if (formSchemeKey == null) {
            throw new IllegalArgumentException("'formSchemeKey' must not be null.");
        }
        return this.list(new String[]{ATTR_SCHEME}, new Object[]{formSchemeKey}, this.implClass);
    }

    public List<FormDefine> queryDefinesByFormGroupe(String formGroupKey) {
        if (formGroupKey == null) {
            throw new IllegalArgumentException("'formGroupKey' must not be null.");
        }
        String sql = "select t_form.* from NR_PARAM_FORM t_form inner join NR_PARAM_FORMGROUPLINK t_grouplink on t_form.FM_KEY=t_grouplink.FGL_FORM_KEY where t_grouplink.FGL_GROUP_KEY=?";
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeFormDefineImpl formDefine = new RunTimeFormDefineImpl();
            this.readRecord(rs, formDefine);
            return formDefine;
        };
        this.buildMethod();
        return this.jdbcTemplate.query(sql, rowMapper, new Object[]{formGroupKey.toString()});
    }

    public FormDefine getDefineByKey(String id) {
        return (FormDefine)this.getByKey(id, this.implClass);
    }

    public void deleteAllFromDefines() throws Exception {
        this.deleteBy(null, new Object[0]);
    }

    public List<FormDefine> getAllFormsInGroup(String formGroupKey) throws Exception {
        RunTimeFormGroupLink link = new RunTimeFormGroupLink();
        link.setGroupKey(formGroupKey);
        return this.listByForeign(link, new String[]{"groupKey"}, RunTimeFormDefineImpl.class);
    }

    public List<FormDefine> listGhostForm() {
        String sqlWhere = " not exists (select 1 from NR_PARAM_FORMGROUPLINK fgl where FM_KEY = fgl.FGL_FORM_KEY) and (FM_TYPE <> 4 or FM_TYPE <> 7)";
        return this.list(sqlWhere, null, this.implClass);
    }

    public List<FormDefine> search(String keyword) {
        if (StringUtils.isEmpty((String)keyword)) {
            return Collections.emptyList();
        }
        keyword = "%" + keyword + "%";
        String sqlWhere = " UPPER(FM_CODE) LIKE ? OR UPPER(FM_TITLE) LIKE ? ";
        return this.list(sqlWhere, new Object[]{keyword, keyword}, this.implClass);
    }
}

