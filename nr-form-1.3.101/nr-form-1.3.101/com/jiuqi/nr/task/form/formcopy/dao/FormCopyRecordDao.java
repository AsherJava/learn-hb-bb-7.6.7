/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.task.form.formcopy.dao;

import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyRecord;
import com.jiuqi.nr.task.form.formcopy.bean.impl.FormCopyRecordImpl;
import com.jiuqi.nr.task.form.formcopy.common.FormCopyTransUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FormCopyRecordDao
extends BaseDao {
    private Class<FormCopyRecordImpl> implClz = FormCopyRecordImpl.class;

    public Class<?> getClz() {
        return this.implClz;
    }

    public Class<?> getExternalTransCls() {
        return FormCopyTransUtils.class;
    }

    public List<IFormCopyRecord> getByFormSchemeKey(String formSchemeKey) {
        return super.list(new String[]{"formSchemeKey"}, new Object[]{formSchemeKey}, this.implClz);
    }

    public void deleteByFormSchemeKey(String formSchemeKey) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"formSchemeKey"}, new Object[]{formSchemeKey});
    }
}

