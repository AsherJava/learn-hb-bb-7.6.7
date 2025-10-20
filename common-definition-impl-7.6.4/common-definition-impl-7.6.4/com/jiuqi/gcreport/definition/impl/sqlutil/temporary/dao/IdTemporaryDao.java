/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.sqlutil.temporary.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.temporary.entity.IdTemporary;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface IdTemporaryDao
extends IBaseSqlGenericDAO<IdTemporary> {
    public List<IdTemporary> saveAll(List<IdTemporary> var1);

    public void deleteByGroupId(String var1);

    public void deleteByGroupIds(Collection<String> var1);

    public List<IdTemporary> listIdTemporaryByGroupId(String var1);

    public void deleteByCreateTime(Date var1);
}

