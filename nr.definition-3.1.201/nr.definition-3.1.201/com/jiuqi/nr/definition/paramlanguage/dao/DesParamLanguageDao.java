/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.definition.paramlanguage.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import java.util.List;

public interface DesParamLanguageDao {
    public void insert(DesParamLanguage var1) throws DBParaException;

    public void batchInsert(DesParamLanguage[] var1) throws DBParaException;

    public void delete(String var1) throws DBParaException;

    public void batchDelete(List<Object[]> var1) throws DBParaException;

    public void batchDelete(String[] var1) throws DBParaException;

    public void update(DesParamLanguage var1) throws DBParaException;

    public void batchUpdate(DesParamLanguage[] var1) throws DBParaException;

    public DesParamLanguage query(String var1);

    public void deleteLanguageByResKeys(List<String> var1);

    public List<DesParamLanguage> queryLanguageByResKeys(List<String> var1);

    public List<DesParamLanguage> queryLanguageByResKey(String var1);

    public List<DesParamLanguage> queryLanguageByResKeyAndResType(String var1, String var2);

    public List<DesParamLanguage> queryLanguageByLanguageType(String var1);

    public List<DesParamLanguage> queryLanguageByResKeyAndLanguageType(String var1, String var2);

    public List<DesParamLanguage> queryLanguage(String var1, LanguageResourceType var2, String var3);
}

