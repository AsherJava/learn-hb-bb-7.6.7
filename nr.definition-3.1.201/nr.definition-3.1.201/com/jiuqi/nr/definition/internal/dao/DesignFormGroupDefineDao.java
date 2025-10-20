/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignFormGroupDefineDao
extends BaseDao {
    private static String ATTR_CODE = "taskCode";
    private static String ATTR_FORMSCHEMECODE = "formSchemeKey";
    private static String ATTR_FilePrefix = "filePrefix";
    private static String ATTR_TITLE = "title";
    private static String ATTR_PARENT = "parentKey";
    private Class<DesignFormGroupDefineImpl> implClass = DesignFormGroupDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignFormGroupDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_PARENT}, new Object[]{id}, this.implClass);
    }

    public List<DesignFormGroupDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public DesignFormGroupDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignFormGroupDefine)defines.get(0);
        }
        return null;
    }

    public List<DesignFormGroupDefine> queryDefinesByFormScheme(String formSchemeId) throws JQException {
        List groups = null;
        try {
            groups = this.list(new String[]{ATTR_FORMSCHEMECODE}, new Object[]{formSchemeId}, this.implClass);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_030, (Throwable)e);
        }
        return groups;
    }

    public List<DesignFormGroupDefine> queryDefinesByFormSchemeAndParent(String formSchemeId, String parentKey) throws Exception {
        List defines = this.list(new String[]{ATTR_FORMSCHEMECODE, ATTR_PARENT}, new Object[]{formSchemeId, parentKey}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return null;
    }

    public List<DesignFormGroupDefine> queryDefinesByParent(String parentKey) throws Exception {
        List defines = this.list(new String[]{ATTR_PARENT}, new Object[]{parentKey}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return null;
    }

    public List<DesignFormGroupDefine> queryDefinesByFormScheme(String formSchemeId, String formGroupTitle) throws Exception {
        List defines = this.list(new String[]{ATTR_FORMSCHEMECODE, ATTR_TITLE}, new Object[]{formSchemeId, formGroupTitle}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return null;
    }

    public List<DesignFormGroupDefine> queryDefinesByTitle(String formGroupTitle) throws Exception {
        List defines = this.list(new String[]{ATTR_TITLE}, new Object[]{formGroupTitle}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return defines;
        }
        return null;
    }

    public void deleteByScheme(String formSchemeKey) throws Exception {
    }

    public DesignFormGroupDefine queryDefinesByfilePrefix(String filePrefix) throws Exception {
        List defines = this.list(new String[]{ATTR_FilePrefix}, new Object[]{filePrefix}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignFormGroupDefine)defines.get(0);
        }
        return null;
    }

    public DesignFormGroupDefine getDefineByKey(String id) throws Exception {
        return (DesignFormGroupDefine)this.getByKey(id, this.implClass);
    }

    public List<DesignFormGroupDefine> getAllGroupsFromForm(String formKey) throws Exception {
        DesignFormGroupLink link = new DesignFormGroupLink();
        link.setFormKey(formKey);
        return this.listByForeign(link, new String[]{"formKey"}, DesignFormGroupDefineImpl.class);
    }

    public List<DesignFormGroupDefine> getAllGroupsFromLink() throws Exception {
        DesignFormGroupLink link = new DesignFormGroupLink();
        link.setFormKey(null);
        return this.listByForeign(link, new String[0], DesignFormGroupDefineImpl.class);
    }

    public List<DesignFormGroupDefine> listGhostGroup() {
        String sqlWhere = " not exists (select 1 from NR_PARAM_FORMSCHEME_DES fc where FG_FORM_SCHEME_KEY = fc.FC_KEY)";
        return this.list(sqlWhere, null, this.implClass);
    }
}

