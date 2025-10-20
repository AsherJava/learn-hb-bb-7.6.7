/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateSchemeDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignPrintTemplateSchemeDefineDao
extends BaseDao {
    private static String ATTR_FORMSCHMEME = "formSchemeKey";
    private static String ATTR_TASKKEY = "taskKey";
    private Class<DesignPrintTemplateSchemeDefineImpl> implClass = DesignPrintTemplateSchemeDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignPrintTemplateSchemeDefine> listByScheme(String id) throws Exception {
        return this.list(new String[]{ATTR_FORMSCHMEME}, new Object[]{id}, this.implClass);
    }

    public List<DesignPrintTemplateSchemeDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public void deleteByScheme(String formSchemeKey) throws Exception {
        this.deleteBy(new String[]{ATTR_FORMSCHMEME}, new Object[]{formSchemeKey});
    }

    public List<DesignPrintTemplateSchemeDefine> queryPrintSchemeDefineByScheme(String printSchemeKey) throws Exception {
        List defines = this.list(new String[]{ATTR_FORMSCHMEME}, new Object[]{printSchemeKey}, this.implClass);
        return defines;
    }

    public List<DesignPrintTemplateSchemeDefine> queryPrintSchemeDefineByTask(String taskKey) throws Exception {
        List defines = this.list(new String[]{ATTR_TASKKEY}, new Object[]{taskKey}, this.implClass);
        return defines;
    }

    public DesignPrintTemplateSchemeDefine getDefineByKey(String id) {
        return (DesignPrintTemplateSchemeDefine)this.getByKey(id, this.implClass);
    }
}

