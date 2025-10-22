/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignPrintTemplateDefineDao
extends BaseDao {
    private static String ATTR_SCHMEME = "printSchemeKey";
    private static String ATTR_FORMKEY = "formKey";
    private Class<DesignPrintTemplateDefineImpl> implClass = DesignPrintTemplateDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignPrintTemplateDefine> listByScheme(String id) {
        return this.list(new String[]{ATTR_SCHMEME}, new Object[]{id}, this.implClass);
    }

    public List<DesignPrintTemplateDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public void deleteByForm(String formKey) throws Exception {
        this.deleteBy(new String[]{ATTR_FORMKEY}, new Object[]{formKey});
    }

    public void deleteBySchemeAndForm(String formSchemeKey, String formKey) throws Exception {
        this.deleteBy(new String[]{ATTR_SCHMEME, ATTR_FORMKEY}, new Object[]{formSchemeKey, formKey});
    }

    public void deleteByScheme(String formSchemeKey) throws Exception {
        this.deleteBy(new String[]{ATTR_SCHMEME}, new Object[]{formSchemeKey});
    }

    public List<DesignPrintTemplateDefine> queryPrintDefineBySchemeAndForm(String printSchemeKey, String formKey) {
        List defines = this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY}, new Object[]{printSchemeKey, formKey}, this.implClass);
        return defines;
    }

    public List<DesignPrintTemplateDefine> queryPrintDefineByForm(String formKey) {
        List defines = this.list(new String[]{ATTR_FORMKEY}, new Object[]{formKey}, this.implClass);
        return defines;
    }

    public List<DesignPrintTemplateDefine> queryPrintDefineByScheme(String printSchemeKey) {
        List defines = this.list(new String[]{ATTR_SCHMEME}, new Object[]{printSchemeKey}, this.implClass);
        return defines;
    }

    public DesignPrintTemplateDefine getDefineByKey(String id) {
        return (DesignPrintTemplateDefine)this.getByKey(id, this.implClass);
    }
}

