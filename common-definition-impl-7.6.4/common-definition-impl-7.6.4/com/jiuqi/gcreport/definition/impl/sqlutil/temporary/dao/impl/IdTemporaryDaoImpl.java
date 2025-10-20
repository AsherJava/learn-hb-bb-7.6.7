/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.gcreport.definition.impl.sqlutil.temporary.dao.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.temporary.dao.IdTemporaryDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.temporary.entity.IdTemporary;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class IdTemporaryDaoImpl
extends AbstractEntDbSqlGenericDAO<IdTemporary>
implements IdTemporaryDao {
    private String INSERTIDTEMPORARYSQL = "  insert into GC_IDTEMPORARY (id,recver,group_Id,tbId,tbCode,tbNum,createTime) \n  values (?,?,?,?,?,?,?) \n";
    private String DELETEIDTEMPORARYBYIDSQL = "  delete from GC_IDTEMPORARY   where id = ? \n";

    public IdTemporaryDaoImpl() {
        super(IdTemporary.class);
    }

    @Override
    public List<IdTemporary> saveAll(List<IdTemporary> entities) throws DataAccessException {
        Date createTime = new Date();
        entities.forEach(v -> {
            if (v.getId() == null) {
                v.setId(UUIDUtils.newUUIDStr());
            }
            v.setCreateTime(createTime);
        });
        this.addBatch(entities);
        return entities;
    }

    @Override
    public int delete(String id) throws DataAccessException {
        if (id == null) {
            return 0;
        }
        return this.execute(this.DELETEIDTEMPORARYBYIDSQL, id);
    }

    @Override
    public void deleteByGroupId(String groupId) {
        String sql = "  delete from GC_IDTEMPORARY   where group_Id = ? \n";
        this.execute(sql, groupId);
    }

    @Override
    public void deleteByGroupIds(Collection<String> groupIds) {
        String sql = "  delete from GC_IDTEMPORARY   where %s \n";
        this.execute(String.format(sql, SqlUtils.getConditionOfIdsUseOr(groupIds, "group_Id")));
    }

    @Override
    public List<IdTemporary> listIdTemporaryByGroupId(String groupId) {
        String sql = "  select id from GC_IDTEMPORARY   where group_Id = ? \n";
        return this.selectEntity(sql, groupId);
    }

    @Override
    public void deleteByCreateTime(Date createTime) {
        String sql = "  delete from GC_IDTEMPORARY   where CREATETIME < ? \n";
        this.execute(sql, createTime);
    }
}

