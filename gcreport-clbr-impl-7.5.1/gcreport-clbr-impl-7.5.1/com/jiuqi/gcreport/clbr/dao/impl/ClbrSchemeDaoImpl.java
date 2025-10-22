/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.clbr.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.gcreport.clbr.dao.ClbrSchemeDao;
import com.jiuqi.gcreport.clbr.entity.ClbrSchemeEO;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class ClbrSchemeDaoImpl
extends AbstractEntDbSqlGenericDAO<ClbrSchemeEO>
implements ClbrSchemeDao {
    public ClbrSchemeDaoImpl() {
        super(ClbrSchemeEO.class);
    }

    @Override
    public void deleteByIds(List<String> schemeIds) {
        String inSql = SqlBuildUtil.getStrInCondi((String)"ID", schemeIds);
        String sql = " delete from GC_CLBR_SCHEME where " + inSql;
        this.execute(sql);
    }

    @Override
    public PageInfo<ClbrSchemeEO> selectForPages(ClbrSchemeCondition clbrSchemeCondition) {
        int count;
        int pageNum = clbrSchemeCondition.getPageNum();
        int pageSize = clbrSchemeCondition.getPageSize();
        StringBuilder sql = new StringBuilder();
        sql.append(" select * from ");
        sql.append("GC_CLBR_SCHEME");
        sql.append(" where 1 = 1 ");
        if (Objects.nonNull(clbrSchemeCondition.getClbrTypes())) {
            sql.append(" and CLBRINFO like '%").append(clbrSchemeCondition.getClbrTypes()).append("%' ");
        }
        if (Objects.nonNull(clbrSchemeCondition.getRelations())) {
            sql.append(" and CLBRINFO like '%").append(clbrSchemeCondition.getRelations()).append("%' ");
        }
        if (Objects.nonNull(clbrSchemeCondition.getOppRelations())) {
            sql.append(" and CLBRINFO like '%").append(clbrSchemeCondition.getOppRelations()).append("%' ");
        }
        if ((count = this.count(sql.toString(), new Object[0])) == 0) {
            return PageInfo.empty();
        }
        List clbrSchemeEOList = this.selectEntityByPaging(sql.toString(), (pageNum - 1) * pageSize, pageNum * pageSize, new Object[0]);
        return PageInfo.of((List)clbrSchemeEOList, (int)pageNum, (int)pageSize, (int)count);
    }

    @Override
    public List<ClbrSchemeEO> selectDirectChildSchemes(String parentId) {
        String sql = " select * from GC_CLBR_SCHEME where PARENTID = ?";
        return this.selectEntity(sql, new Object[]{parentId});
    }

    @Override
    public List<ClbrSchemeEO> selectByIds(List<String> schemeIds) {
        String fieldsSql = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_SCHEME", (String)"");
        String inSql = SqlBuildUtil.getStrInCondi((String)"ID", schemeIds);
        String sql = " select " + fieldsSql + " from " + "GC_CLBR_SCHEME" + " where " + inSql;
        return this.selectEntity(sql, new Object[0]);
    }
}

