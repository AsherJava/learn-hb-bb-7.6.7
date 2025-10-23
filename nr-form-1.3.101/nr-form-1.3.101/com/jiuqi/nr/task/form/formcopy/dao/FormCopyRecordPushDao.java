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
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyPushRecord;
import com.jiuqi.nr.task.form.formcopy.bean.impl.FormCopyPushRecordImpl;
import com.jiuqi.nr.task.form.formcopy.common.FormCopyTransUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FormCopyRecordPushDao
extends BaseDao {
    private Class<FormCopyPushRecordImpl> implClz = FormCopyPushRecordImpl.class;

    public Class<?> getClz() {
        return this.implClz;
    }

    public Class<?> getExternalTransCls() {
        return FormCopyTransUtils.class;
    }

    public List<IFormCopyPushRecord> getByFormSchemeKey(String srcFormSchemeKey) {
        return super.list(new String[]{"srcFormSchemeKey"}, new Object[]{srcFormSchemeKey}, this.implClz);
    }

    public void deleteByFormSchemeKey(String srcFormSchemeKey) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"srcFormSchemeKey"}, new Object[]{srcFormSchemeKey});
    }
}

