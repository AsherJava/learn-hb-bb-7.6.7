/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.datascheme.internal.dao;

import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import java.util.Collections;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface IDataFieldDao<DO extends DataFieldDO> {
    public String insert(DO var1) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public void deleteByDataScheme(String var1) throws DataAccessException;

    public void deleteByTable(String var1) throws DataAccessException;

    public void update(DO var1) throws DataAccessException;

    public DO get(String var1) throws DataAccessException;

    public List<DO> getByCode(String var1);

    public String[] batchInsert(List<DO> var1) throws DataAccessException;

    public void batchDelete(List<String> var1) throws DataAccessException;

    public void batchDeleteByTable(List<String> var1) throws DataAccessException;

    public void batchUpdate(List<DO> var1) throws DataAccessException;

    public List<DO> batchGet(List<String> var1) throws DataAccessException;

    public List<DO> getAll() throws DataAccessException;

    public List<DO> getByTable(String var1) throws DataAccessException;

    public List<DO> batchGetByTable(List<String> var1) throws DataAccessException;

    public DO getByTableAndCode(String var1, String var2) throws DataAccessException;

    public List<DO> getByTableAndCode(String var1, List<String> var2, int var3) throws DataAccessException;

    public List<DO> getByTableAndType(String var1, DataFieldType[] var2) throws DataAccessException;

    public List<DO> getByTableAndKind(String var1, int var2) throws DataAccessException;

    public void deleteByTableAndKind(String var1, int var2) throws DataAccessException;

    public void deleteBySchemeAndKind(String var1, int var2) throws DataAccessException;

    public List<DO> getBySchemeAndKind(String var1, int var2) throws DataAccessException;

    public List<DO> getByScheme(String var1) throws DataAccessException;

    public List<DO> getByCondition(String var1, String var2) throws DataAccessException;

    public List<DO> getByCondition(String var1, List<String> var2, int var3) throws DataAccessException;

    public List<DO> getByCondition(String var1, String var2, int var3) throws DataAccessException;

    public DO getByTableCondition(String var1, String var2, int var3) throws DataAccessException;

    public List<DO> searchBy(List<String> var1, String var2, String var3, int var4, int var5, int var6);

    public int getRowNumber(String var1, String var2, int var3);

    public List<DO> filterField(FieldSearchQuery var1);

    public int countBy(FieldSearchQuery var1);

    public void cancelEncrypted(String var1);

    default public List<DO> getByEntity(List<String> entityKeys) {
        return Collections.emptyList();
    }
}

