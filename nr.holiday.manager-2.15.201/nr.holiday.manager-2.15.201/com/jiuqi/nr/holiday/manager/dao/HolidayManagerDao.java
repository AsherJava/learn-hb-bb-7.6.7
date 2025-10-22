/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.holiday.manager.dao;

import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.holiday.manager.bean.HolidayDefine;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HolidayManagerDao
extends BaseDao {
    private final Class<HolidayDefine> implClass = HolidayDefine.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<HolidayDefine> queryByYear(String year) {
        return super.list(new String[]{"year"}, new Object[]{year}, this.implClass);
    }

    public void deleteByYear(String year) throws BeanParaException, DBParaException {
        super.deleteBy(new String[]{"year"}, new Object[]{year});
    }

    public void insert(List<HolidayDefine> defines) throws BeanParaException, DBParaException {
        if (null == defines || defines.isEmpty()) {
            return;
        }
        super.insert(defines.toArray());
    }
}

