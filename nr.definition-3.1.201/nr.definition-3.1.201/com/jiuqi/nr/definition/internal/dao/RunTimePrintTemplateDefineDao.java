/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimePrintTemplateDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimePrintTemplateDefineDao
extends BaseDao {
    private static String ATTR_SCHMEME = "printSchemeKey";
    private static String ATTR_FORMKEY = "formKey";
    private Class<RunTimePrintTemplateDefineImpl> implClass = RunTimePrintTemplateDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<PrintTemplateDefine> listByScheme(String id) throws Exception {
        return this.list(new String[]{ATTR_SCHMEME}, new Object[]{id}, this.implClass);
    }

    public List<PrintTemplateDefine> list() throws Exception {
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

    public List<PrintTemplateDefine> queryPrintDefineBySchemeAndForm(String printSchemeKey, String formKey) {
        List defines = this.list(new String[]{ATTR_SCHMEME, ATTR_FORMKEY}, new Object[]{printSchemeKey, formKey}, this.implClass);
        return defines;
    }

    public List<PrintTemplateDefine> queryPrintDefineByScheme(String printSchemeKey) {
        List defines = this.list(new String[]{ATTR_SCHMEME}, new Object[]{printSchemeKey}, this.implClass);
        return defines;
    }

    public PrintTemplateDefine getDefineByKey(String id) {
        return (PrintTemplateDefine)this.getByKey(id, this.implClass);
    }
}

