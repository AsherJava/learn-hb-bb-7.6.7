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
import com.jiuqi.nr.definition.exception.DefinitonException;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPrintSettingDefineDao<T extends PrintSettingDefine>
extends BaseDao {
    public abstract Class<T> getClz();

    public Class<TransUtil> getExternalTransCls() {
        return TransUtil.class;
    }

    public T query(String printSchemeKey, String formKey) {
        String where = " PS_PRINT_SCHEME_KEY=? AND PS_FORM_KEY=? ";
        Object[] args = new Object[]{printSchemeKey, formKey};
        return (T)((PrintSettingDefine)super.getBy(" PS_PRINT_SCHEME_KEY=? AND PS_FORM_KEY=? ", args, this.getClz()));
    }

    public List<T> list(String printSchemeKey) {
        String where = " PS_PRINT_SCHEME_KEY=? ";
        Object[] args = new Object[]{printSchemeKey};
        return super.list(" PS_PRINT_SCHEME_KEY=? ", args, this.getClz());
    }

    public void delete(String printSchemeKey) {
        String[] fieldNames = new String[]{"printSchemeKey"};
        Object[] args = new Object[]{printSchemeKey};
        try {
            super.deleteBy(fieldNames, args);
        }
        catch (DBParaException e) {
            throw new DefinitonException(e);
        }
    }

    public void delete(String printSchemeKey, String formKey) {
        String[] fieldNames = new String[]{"printSchemeKey", "formKey"};
        Object[] args = new Object[]{printSchemeKey, formKey};
        try {
            super.deleteBy(fieldNames, args);
        }
        catch (DBParaException e) {
            throw new DefinitonException(e);
        }
    }

    public void insert(T printSettingDefine) {
        try {
            super.insert(printSettingDefine);
        }
        catch (DBParaException e) {
            throw new DefinitonException(e);
        }
    }

    public void insert(List<T> printSettingDefines) {
        try {
            super.insert(printSettingDefines.stream().filter(Objects::nonNull).toArray());
        }
        catch (DBParaException e) {
            throw new DefinitonException(e);
        }
    }

    public void update(T printSettingDefine) {
        String[] fieldNames = new String[]{"printSchemeKey", "formKey"};
        Object[] args = new Object[]{printSettingDefine.getPrintSchemeKey(), printSettingDefine.getFormKey()};
        try {
            super.update(printSettingDefine, fieldNames, args);
        }
        catch (DBParaException e) {
            throw new DefinitonException(e);
        }
    }

    public void update(List<T> printSettingDefines) {
        for (PrintSettingDefine printSettingDefine : printSettingDefines) {
            this.update(printSettingDefine);
        }
    }
}

