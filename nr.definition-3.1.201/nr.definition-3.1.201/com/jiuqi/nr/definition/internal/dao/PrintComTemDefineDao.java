/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.PrintComTemDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PrintComTemDefineDao
extends BaseDao {
    public Class<PrintComTemDefineImpl> getClz() {
        return PrintComTemDefineImpl.class;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public PrintComTemDefine getByKey(String key) {
        return (PrintComTemDefine)super.getByKey((Object)key, this.getClz());
    }

    public PrintComTemDefine getBySchemeAndCode(String scheme, String code) {
        String where = " PC_PRINT_SCHEME_KEY = ? and PC_CODE = ? ";
        return (PrintComTemDefine)super.getBy(" PC_PRINT_SCHEME_KEY = ? and PC_CODE = ? ", new Object[]{scheme, code}, this.getClz());
    }

    public List<PrintComTemDefine> listByScheme(String scheme) {
        String where = " PC_PRINT_SCHEME_KEY = ? ";
        return super.list(" PC_PRINT_SCHEME_KEY = ? ", new Object[]{scheme}, this.getClz());
    }
}

