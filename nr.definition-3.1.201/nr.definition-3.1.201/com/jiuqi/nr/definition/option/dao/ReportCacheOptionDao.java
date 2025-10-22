/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.option.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.option.dto.ReportCacheOption;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReportCacheOptionDao
extends BaseDao {
    private Class<ReportCacheOption> implClass = ReportCacheOption.class;
    public static final String OPTION_TYPE = "currentOptionType";

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<ReportCacheOption> listAll() {
        return super.list(this.implClass);
    }

    public List<ReportCacheOption> listByOptionType(int optionType) {
        return super.list(new String[]{OPTION_TYPE}, new Object[]{optionType}, this.implClass);
    }

    public void deleteByOptionType(int optionType) throws DBParaException {
        super.deleteBy(new String[]{OPTION_TYPE}, new Object[]{optionType});
    }

    public void insert(ReportCacheOption[] cacheOptions) throws DBParaException {
        super.insert((Object[])cacheOptions);
    }

    public void update(ReportCacheOption[] cacheOptions) throws DBParaException {
        super.update((Object[])cacheOptions);
    }
}

