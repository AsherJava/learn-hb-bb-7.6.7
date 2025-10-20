/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.gcreport.financialcheckcore.sqlutils.dao;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.dao.ReltxIdTemporaryDao;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.entity.ReltxIdTemporary;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReltxIdTemporaryDaoImpl
implements ReltxIdTemporaryDao {
    @Override
    @OuterTransaction
    public String saveAll(Collection<String> codes) throws DataAccessException {
        String batchId = UUIDUtils.newUUIDStr();
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append("GC_RELTX_IDTEMPORARY").append(" (id,group_id,tbcode) values (?, ?, ?)");
        List entities = codes.stream().map(code -> new Object[]{UUIDUtils.newUUIDStr(), batchId, code}).collect(Collectors.toList());
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql.toString(), entities);
        return batchId;
    }

    @Override
    @OuterTransaction
    public void deleteByGroupId(String groupId) {
        String sql = "  delete from GC_RELTX_IDTEMPORARY   where group_Id = ? \n";
        OuterDataSourceUtils.getJdbcTemplate().update(sql, new Object[]{groupId});
    }

    @Override
    @OuterTransaction
    public void deleteByGroupIds(Collection<String> groupIds) {
        String sql = "  delete from GC_RELTX_IDTEMPORARY   where %s \n";
        OuterDataSourceUtils.getJdbcTemplate().update(String.format(sql, SqlUtils.getConditionOfIdsUseOr(groupIds, (String)"group_Id")));
    }

    @Override
    public List<ReltxIdTemporary> listIdTemporaryByGroupId(String groupId) {
        String sql = "  select id from GC_RELTX_IDTEMPORARY   where group_Id = ? \n";
        return OuterDataSourceUtils.getJdbcTemplate().query(sql, (RowMapper)new BeanPropertyRowMapper(ReltxIdTemporary.class), new Object[]{groupId});
    }
}

