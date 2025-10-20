/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimePrintTemplateSchemeDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimePrintTemplateSchemeDefineDao
extends BaseDao {
    private static String ATTR_FORMSCHMEME = "formSchemeKey";
    private static String ATTR_TASKKEY = "taskKey";
    private Class<RunTimePrintTemplateSchemeDefineImpl> implClass = RunTimePrintTemplateSchemeDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<PrintTemplateSchemeDefine> listByScheme(String id) throws Exception {
        return this.list(new String[]{ATTR_FORMSCHMEME}, new Object[]{id}, this.implClass);
    }

    public List<PrintTemplateSchemeDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public void deleteByScheme(String formSchemeKey) throws Exception {
        this.deleteBy(new String[]{ATTR_FORMSCHMEME}, new Object[]{formSchemeKey});
    }

    public List<PrintTemplateSchemeDefine> queryPrintSchemeDefineByScheme(String formSchemeKey) {
        return this.list(new String[]{ATTR_FORMSCHMEME}, new Object[]{formSchemeKey}, this.implClass);
    }

    public List<PrintTemplateSchemeDefine> queryPrintSchemeDefineByTask(String taskKey) {
        return this.list(new String[]{ATTR_TASKKEY}, new Object[]{taskKey}, this.implClass);
    }

    public PrintTemplateSchemeDefine getDefineByKey(String id) {
        return (PrintTemplateSchemeDefine)this.getByKey(id, this.implClass);
    }
}

