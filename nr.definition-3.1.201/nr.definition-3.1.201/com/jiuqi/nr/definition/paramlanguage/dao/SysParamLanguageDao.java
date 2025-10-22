/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.definition.paramlanguage.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.entity.SysParamLanguage;
import java.util.List;

public interface SysParamLanguageDao {
    public void insert(SysParamLanguage var1) throws DBParaException;

    public void batchInsert(SysParamLanguage[] var1) throws DBParaException;

    public void delete(String var1) throws DBParaException;

    public void batchDelete(SysParamLanguage[] var1) throws DBParaException;

    public void update(SysParamLanguage var1) throws DBParaException;

    public void batchUpdate(SysParamLanguage[] var1) throws DBParaException;

    public SysParamLanguage query(String var1);

    public List<SysParamLanguage> queryLanguageByResKey(String var1);

    public List<SysParamLanguage> queryLanguageByResKeyAndResType(String var1, String var2);

    public List<SysParamLanguage> queryLanguageByLanguageType(String var1);

    public List<SysParamLanguage> queryLanguageByResKeyAndLanguageType(String var1, String var2);

    public List<SysParamLanguage> queryLanguage(String var1, LanguageResourceType var2, String var3);
}

