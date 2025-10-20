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
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignPrintComTemDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignPrintComTemDefineDao
extends BaseDao {
    public Class<DesignPrintComTemDefineImpl> getClz() {
        return DesignPrintComTemDefineImpl.class;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public DesignPrintComTemDefine getByKey(String key) {
        return (DesignPrintComTemDefine)super.getByKey((Object)key, this.getClz());
    }

    public DesignPrintComTemDefine getBySchemeAndCode(String scheme, String code) {
        String where = " PC_PRINT_SCHEME_KEY = ? and PC_CODE = ? ";
        return (DesignPrintComTemDefine)super.getBy(" PC_PRINT_SCHEME_KEY = ? and PC_CODE = ? ", new Object[]{scheme, code}, this.getClz());
    }

    public List<DesignPrintComTemDefine> listByScheme(String scheme) {
        String where = " PC_PRINT_SCHEME_KEY = ? ";
        return super.list(" PC_PRINT_SCHEME_KEY = ? ", new Object[]{scheme}, this.getClz());
    }

    public void deleteByKey(String key) throws DBParaException {
        super.deleteBy(new String[]{"key"}, new Object[]{key});
    }

    public void deleteByScheme(String scheme) throws DBParaException {
        super.deleteBy(new String[]{"printSchemeKey"}, new Object[]{scheme});
    }

    public void deleteBySchemeAndCode(String scheme, String code) throws DBParaException {
        super.deleteBy(new String[]{"printSchemeKey", "code"}, new Object[]{scheme, code});
    }
}

