/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.subdatabase.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.nr.subdatabase.facade.impl.SubDataBaseImpl;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Repository
public class SubDataBaseDao
extends BaseDao {
    private Class<SubDataBaseImpl> implClz = SubDataBaseImpl.class;

    public Class<?> getClz() {
        return this.implClz;
    }

    public SubDataBase getSubDataBaseObjByCode(String dataSchemeKey, String code) {
        SubDataBase subDataBase = (SubDataBase)super.getBy("SD_DS_KEY=? AND SD_CODE=?", new Object[]{dataSchemeKey, code}, this.implClz);
        return subDataBase;
    }

    public List<SubDataBase> getSubDataBaseObjByCode(String code) {
        return super.list("ORG_CATEGORY_NAME=?", new Object[]{code}, this.implClz);
    }

    public List<SubDataBase> getSubDataBaseObjByDataScheme(String dataSchemeKey) {
        return super.list("SD_DS_KEY=?", new Object[]{dataSchemeKey}, this.implClz);
    }

    public void updateTitle(SubDataBase subDataBase) throws DBParaException {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("NR_SUBDATABASE_SERVICE").append(" SET ").append("SD_TITLE").append(" = ? ").append(" WHERE ").append("SD_DS_KEY").append(" = ? AND ").append("SD_CODE").append(" = ?");
        this.jdbcTemplate.update(sql.toString(), new Object[]{subDataBase.getTitle(), subDataBase.getDataScheme(), subDataBase.getCode()});
    }

    public void insertSubDataBase(SubDataBase subDataBase) throws DBParaException {
        super.insert((Object)subDataBase);
    }

    public void deleteSubDataBase(SubDataBase subDataBase) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append("NR_SUBDATABASE_SERVICE").append(" WHERE ").append("SD_DS_KEY").append(" = ? AND ").append("SD_CODE").append(" = ?");
        this.jdbcTemplate.update(sql.toString(), new Object[]{subDataBase.getDataScheme(), subDataBase.getCode()});
    }

    public List<SubDataBase> getAllSubDataBases() {
        return super.list(this.implClz);
    }

    public void updateStatus(SubDataBase subDataBase) {
        Assert.notNull((Object)subDataBase, "subDataBase must not be null.");
        this.jdbcTemplate.update(this.getUpdateStatusSql(), new Object[]{subDataBase.getDSDeployStatus(), subDataBase.getDataScheme(), subDataBase.getCode()});
    }

    public void batchUpdateStatus(List<SubDataBase> subDataBaseList) {
        for (SubDataBase subDataBase : subDataBaseList) {
            this.jdbcTemplate.update(this.getUpdateStatusSql(), new Object[]{subDataBase.getDSDeployStatus(), subDataBase.getDataScheme(), subDataBase.getCode()});
        }
    }

    private String getUpdateStatusSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("NR_SUBDATABASE_SERVICE").append(" SET ").append("DS_STATUS").append(" = ? ").append(" WHERE ").append("SD_DS_KEY").append(" = ? AND ").append("SD_CODE").append(" = ?");
        return sql.toString();
    }

    public List<SubDataBaseImpl> getAllSubDataBases(String fieldName, boolean isDesc) {
        StringBuilder sbr = new StringBuilder();
        sbr.append("select * from ").append("NR_SUBDATABASE_SERVICE");
        sbr.append(String.format(" order by %s", fieldName.toUpperCase()));
        if (isDesc) {
            sbr.append(" desc");
        } else {
            sbr.append(" asc");
        }
        return super.queryList(sbr.toString(), null, super.getRowMapper(this.implClz));
    }

    public List<SubDataBase> getSameTitleSDB(String title) {
        return super.list("SD_TITLE=?", (Object[])new String[]{title}, this.implClz);
    }

    public SubDataBase getSameTitleSDBByDsAndTitle(String dataSchemeKey, String title) {
        List subDataBaseList = super.list("SD_DS_KEY=? and SD_TITLE=?", (Object[])new String[]{dataSchemeKey, title}, this.implClz);
        if (!CollectionUtils.isEmpty(subDataBaseList)) {
            return (SubDataBase)subDataBaseList.get(0);
        }
        return null;
    }

    public void updateSDSyncTime(SubDataBase subDataBase) {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE ").append("NR_SUBDATABASE_SERVICE").append(" SET ").append("SD_SYNC_ORDER").append(" = ? ").append(", ").append("SD_SYNC_TIME").append(" = ? ").append(" WHERE ").append("SD_DS_KEY").append(" = ? AND ").append("SD_CODE").append(" = ?");
        this.jdbcTemplate.update(updateSql.toString(), new Object[]{subDataBase.getSyncOrder(), subDataBase.getSDSyncTime(), subDataBase.getDataScheme(), subDataBase.getCode()});
    }
}

