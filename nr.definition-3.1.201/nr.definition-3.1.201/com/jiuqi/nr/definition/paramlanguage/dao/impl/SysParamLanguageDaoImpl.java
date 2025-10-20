/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.paramlanguage.dao.impl;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.common.TransUtil;
import com.jiuqi.nr.definition.paramlanguage.dao.SysParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.SysParamLanguage;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class SysParamLanguageDaoImpl
extends BaseDao
implements SysParamLanguageDao {
    private final Class<SysParamLanguage> implClass = SysParamLanguage.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    @Override
    public SysParamLanguage query(String paramLanguageKey) {
        if (!StringUtils.isEmpty((String)paramLanguageKey)) {
            return (SysParamLanguage)super.getByKey((Object)paramLanguageKey, this.implClass);
        }
        return null;
    }

    @Override
    public void insert(SysParamLanguage sysParamLanguage) throws DBParaException {
        super.insert((Object)sysParamLanguage);
    }

    @Override
    public void batchInsert(SysParamLanguage[] sysParamLanguages) throws DBParaException {
        super.insert((Object[])sysParamLanguages);
    }

    @Override
    public void delete(String paramLanguageKey) throws DBParaException {
        super.deleteBy(new String[]{"key"}, (Object[])new String[]{paramLanguageKey});
    }

    @Override
    public void batchDelete(SysParamLanguage[] sysParamLanguages) throws DBParaException {
        super.delete((Object[])sysParamLanguages);
    }

    @Override
    public void update(SysParamLanguage sysParamLanguage) throws DBParaException {
        if (sysParamLanguage != null && !StringUtils.isEmpty((String)sysParamLanguage.getKey())) {
            super.update((Object)sysParamLanguage);
        }
    }

    @Override
    public void batchUpdate(SysParamLanguage[] sysParamLanguages) throws DBParaException {
        if (sysParamLanguages.length > 0) {
            super.update((Object[])sysParamLanguages);
        }
    }

    @Override
    public List<SysParamLanguage> queryLanguageByResKey(String resourceKey) {
        return this.list(" PL_RESOURCE_KEY=? ", new Object[]{resourceKey}, this.implClass);
    }

    @Override
    public List<SysParamLanguage> queryLanguageByResKeyAndResType(String resourceKey, String resourceType) {
        return this.list(" PL_RESOURCE_KEY=? and PL_RESOURCE_TYPE=? ", new Object[]{resourceKey, resourceType}, this.implClass);
    }

    @Override
    public List<SysParamLanguage> queryLanguageByLanguageType(String languageType) {
        return this.list(" PL_LANGUAGE_TYPE=?", new Object[]{languageType}, this.implClass);
    }

    @Override
    public List<SysParamLanguage> queryLanguageByResKeyAndLanguageType(String resourceKey, String languageType) {
        return this.list(" PL_RESOURCE_KEY=? and PL_LANGUAGE_TYPE=?", new Object[]{resourceKey, languageType}, this.implClass);
    }

    @Override
    public List<SysParamLanguage> queryLanguage(String resourceKey, LanguageResourceType resourceType, String languageType) {
        return this.list(" PL_RESOURCE_KEY=? and PL_RESOURCE_TYPE=? and PL_LANGUAGE_TYPE=?", new Object[]{resourceKey, resourceType.getValue(), languageType}, this.implClass);
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }
}

