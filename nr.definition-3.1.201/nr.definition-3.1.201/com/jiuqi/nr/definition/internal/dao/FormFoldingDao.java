/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.FormFoldingDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.FormFoldingDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FormFoldingDao
extends BaseDao {
    private static String ATTR_FORM_KEY = "formKey";
    private Class<FormFoldingDefineImpl> implClass = FormFoldingDefineImpl.class;

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public Class<?> getClz() {
        return this.implClass;
    }

    public FormFoldingDefine getFormFoldingDefineByKey(String key) {
        return (FormFoldingDefine)this.getByKey(key, this.implClass);
    }

    public List<FormFoldingDefine> getFormFoldingDefineByFormKey(String formKey) {
        return this.list(new String[]{ATTR_FORM_KEY}, new String[]{formKey}, this.implClass);
    }

    public void deleteFormFoldingDefine(String[] keys) throws DBParaException {
        this.delete(keys);
    }
}

