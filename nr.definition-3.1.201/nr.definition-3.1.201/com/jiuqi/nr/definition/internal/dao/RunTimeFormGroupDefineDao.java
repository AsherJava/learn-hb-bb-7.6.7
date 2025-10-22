/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeFormGroupDefineDao
extends BaseDao {
    private static String ATTR_CODE = "taskCode";
    private static String ATTR_FORMSCHEMECODE = "formSchemeKey";
    private static String ATTR_FilePrefix = "filePrefix";
    private static String ATTR_TITLE = "title";
    private static String ATTR_PARENT = "parentKey";
    private Class<RunTimeFormGroupDefineImpl> implClass = RunTimeFormGroupDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<FormGroupDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_PARENT}, new Object[]{id}, this.implClass);
    }

    public List<FormGroupDefine> list() {
        return this.list(this.implClass);
    }

    public FormGroupDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormGroupDefine)defines.get(0);
        }
        return null;
    }

    public List<FormGroupDefine> queryDefinesByFormScheme(String formSchemeId) {
        List defines = this.list(new String[]{ATTR_FORMSCHEMECODE}, new Object[]{formSchemeId}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return new ArrayList<FormGroupDefine>();
    }

    public List<FormGroupDefine> queryDefinesByFormSchemeAndParent(String formSchemeId, String parentKey) throws Exception {
        List defines = this.list(new String[]{ATTR_FORMSCHEMECODE, ATTR_PARENT}, new Object[]{formSchemeId, parentKey}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return new ArrayList<FormGroupDefine>();
    }

    public List<FormGroupDefine> queryDefinesByParent(String parentKey) throws Exception {
        List defines = this.list(new String[]{ATTR_PARENT}, new Object[]{parentKey}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return new ArrayList<FormGroupDefine>();
    }

    public List<FormGroupDefine> queryDefinesByFormScheme(String formSchemeId, String formGroupTitle) throws Exception {
        List defines = this.list(new String[]{ATTR_FORMSCHEMECODE, ATTR_TITLE}, new Object[]{formSchemeId, formGroupTitle}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return new ArrayList<FormGroupDefine>();
    }

    public List<FormGroupDefine> queryDefinesByTitle(String formGroupTitle) throws Exception {
        List defines = this.list(new String[]{ATTR_TITLE}, new Object[]{formGroupTitle}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return new ArrayList<FormGroupDefine>();
    }

    public void deleteByScheme(String formSchemeKey) throws Exception {
    }

    public FormGroupDefine queryDefinesByfilePrefix(String filePrefix) throws Exception {
        List defines = this.list(new String[]{ATTR_FilePrefix}, new Object[]{filePrefix}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormGroupDefine)defines.get(0);
        }
        return null;
    }

    public FormGroupDefine getDefineByKey(String id) {
        return (FormGroupDefine)this.getByKey(id, this.implClass);
    }

    public List<FormGroupDefine> getAllGroupsFromForm(String formKey) throws Exception {
        RunTimeFormGroupLink link = new RunTimeFormGroupLink();
        link.setFormKey(formKey);
        return this.listByForeign(link, new String[]{"formKey"}, RunTimeFormGroupDefineImpl.class);
    }

    public List<FormGroupDefine> getAllGroupsFromLink() throws Exception {
        RunTimeFormGroupLink link = new RunTimeFormGroupLink();
        link.setFormKey(null);
        return this.listByForeign(link, new String[0], RunTimeFormGroupDefineImpl.class);
    }

    public List<FormGroupDefine> listGhostGroup() {
        String sqlWhere = " not exists (select 1 from NR_PARAM_FORMSCHEME fc where FG_FORM_SCHEME_KEY = fc.FC_KEY)";
        return this.list(sqlWhere, null, this.implClass);
    }
}

