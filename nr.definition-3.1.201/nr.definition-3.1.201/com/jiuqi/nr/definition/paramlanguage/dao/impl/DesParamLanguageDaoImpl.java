/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.definition.paramlanguage.dao.impl;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.common.TransUtil;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.definition.util.DefinitionUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class DesParamLanguageDaoImpl
extends BaseDao
implements DesParamLanguageDao {
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final Class<DesParamLanguage> implClass = DesParamLanguage.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    @Override
    public DesParamLanguage query(String paramLanguageKey) {
        if (!StringUtils.isEmpty((String)paramLanguageKey)) {
            return (DesParamLanguage)super.getByKey((Object)paramLanguageKey, this.implClass);
        }
        return null;
    }

    @Override
    public void insert(DesParamLanguage desParamLanguage) throws DBParaException {
        super.insert((Object)desParamLanguage);
    }

    @Override
    public void batchInsert(DesParamLanguage[] desParamLanguages) throws DBParaException {
        super.insert((Object[])desParamLanguages);
    }

    @Override
    public void delete(String paramLanguageKey) throws DBParaException {
        super.deleteBy(new String[]{"key"}, (Object[])new String[]{paramLanguageKey});
    }

    @Override
    public void batchDelete(List<Object[]> needDeleteList) throws DBParaException {
        StringBuffer deleteSQL = new StringBuffer("delete from DES_PARAM_LANGUAGE where PL_RESOURCE_KEY =? and PL_LANGUAGE_TYPE =?");
        this.jdbcTemplate.batchUpdate(deleteSQL.toString(), needDeleteList);
    }

    @Override
    public void batchDelete(String[] keys) throws DBParaException {
        super.delete((Object[])keys);
    }

    @Override
    public void update(DesParamLanguage desParamLanguage) throws DBParaException {
        if (desParamLanguage != null && !StringUtils.isEmpty((String)desParamLanguage.getKey())) {
            super.update((Object)desParamLanguage);
        }
    }

    @Override
    public void batchUpdate(DesParamLanguage[] desParamLanguages) throws DBParaException {
        if (desParamLanguages.length > 0) {
            super.update((Object[])desParamLanguages);
        }
    }

    @Override
    public List<DesParamLanguage> queryLanguageByResKey(String resourceKey) {
        return this.list(" PL_RESOURCE_KEY=? ", new Object[]{resourceKey}, this.implClass);
    }

    @Override
    public List<DesParamLanguage> queryLanguageByResKeyAndResType(String resourceKey, String resourceType) {
        return this.list(" PL_RESOURCE_KEY=? and PL_RESOURCE_TYPE=? ", new Object[]{resourceKey, resourceType}, this.implClass);
    }

    @Override
    public List<DesParamLanguage> queryLanguageByLanguageType(String languageType) {
        return this.list(" PL_LANGUAGE_TYPE=?", new Object[]{languageType}, this.implClass);
    }

    @Override
    public List<DesParamLanguage> queryLanguageByResKeyAndLanguageType(String resourceKey, String languageType) {
        return this.list(" PL_RESOURCE_KEY=? and PL_LANGUAGE_TYPE=?", new Object[]{resourceKey, languageType}, this.implClass);
    }

    @Override
    public List<DesParamLanguage> queryLanguage(String resourceKey, LanguageResourceType resourceType, String languageType) {
        return this.list(" PL_RESOURCE_KEY=? and PL_RESOURCE_TYPE=? and PL_LANGUAGE_TYPE=?", new Object[]{resourceKey, resourceType.getValue(), languageType}, this.implClass);
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    @Override
    public List<DesParamLanguage> queryLanguageByResKeys(List<String> resourceKeys) {
        return DefinitionUtils.limitExe(resourceKeys, this::listByResKey);
    }

    public List<DesParamLanguage> listByResKey(List<String> resourceKeys) {
        String sql = "SELECT T.* FROM DES_PARAM_LANGUAGE T WHERE T.PL_RESOURCE_KEY IN (:resourceKeys) ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("resourceKeys", resourceKeys);
        RowMapper rowMapper = (rs, rowNum) -> {
            DesParamLanguage obj = new DesParamLanguage();
            this.readRecord(rs, obj);
            return obj;
        };
        this.buildMethod();
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    @Override
    public void deleteLanguageByResKeys(List<String> resourceKeys) {
        DefinitionUtils.limitExe(resourceKeys, subResKeys -> {
            this.deleteByResKey((List<String>)subResKeys);
            return null;
        });
    }

    public void deleteByResKey(List<String> resourceKeys) {
        String sql = "DELETE FROM DES_PARAM_LANGUAGE T WHERE T.PL_RESOURCE_KEY IN (:resourceKeys) ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("resourceKeys", resourceKeys);
        this.namedParameterJdbcTemplate.update(sql, (SqlParameterSource)sqlParameterSource);
    }
}

