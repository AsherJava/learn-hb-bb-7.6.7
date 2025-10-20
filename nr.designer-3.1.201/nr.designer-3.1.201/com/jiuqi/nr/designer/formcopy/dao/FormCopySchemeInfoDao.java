/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.designer.formcopy.dao;

import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyAttSchemeInfo;
import com.jiuqi.nr.designer.formcopy.bean.impl.FormCopyAttSchemeInfoImpl;
import com.jiuqi.nr.designer.formcopy.common.FormCopyTransUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FormCopySchemeInfoDao
extends BaseDao {
    private Class<FormCopyAttSchemeInfoImpl> implClz = FormCopyAttSchemeInfoImpl.class;

    public Class<?> getClz() {
        return this.implClz;
    }

    public Class<?> getExternalTransCls() {
        return FormCopyTransUtils.class;
    }

    public IFormCopyAttSchemeInfo getByKey(String key) {
        return (IFormCopyAttSchemeInfo)super.getByKey((Object)key, this.implClz);
    }

    public List<IFormCopyAttSchemeInfo> getByFormSchemeKey(String formSchemeKey, String srcFormSchemeKey) {
        return super.list(new String[]{"formSchemeKey", "srcFormSchemeKey"}, new Object[]{formSchemeKey, srcFormSchemeKey}, this.implClz);
    }

    public void deleteByFormSchemeKey(String formSchemeKey) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"formSchemeKey"}, new Object[]{formSchemeKey});
        super.deleteBy(new String[]{"srcFormSchemeKey"}, new Object[]{formSchemeKey});
    }

    public void deleteByFormSchemeKey(String formSchemeKey, String srcFormSchemeKey) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"formSchemeKey", "srcFormSchemeKey"}, new Object[]{formSchemeKey, srcFormSchemeKey});
    }
}

