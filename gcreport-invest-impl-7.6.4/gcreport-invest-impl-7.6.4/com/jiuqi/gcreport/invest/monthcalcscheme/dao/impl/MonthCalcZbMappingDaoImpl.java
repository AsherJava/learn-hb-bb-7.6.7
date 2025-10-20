/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingCond
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.invest.monthcalcscheme.dao.MonthCalcZbMappingDao;
import com.jiuqi.gcreport.invest.monthcalcscheme.entity.MonthCalcZbMappingEO;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingCond;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MonthCalcZbMappingDaoImpl
extends GcDbSqlGenericDAO<MonthCalcZbMappingEO, String>
implements MonthCalcZbMappingDao {
    public MonthCalcZbMappingDaoImpl() {
        super(MonthCalcZbMappingEO.class);
    }

    @Override
    public PageInfo<MonthCalcZbMappingEO> listZbMappings(MonthCalcZbMappingCond cond) {
        int count;
        ArrayList<String> params = new ArrayList<String>();
        String sql = "select * from GC_MONTHCALCZBMAPPING i where monthcalcSchemeId=?\n";
        params.add(cond.getMonthCalcSchemeId());
        if (!StringUtils.isEmpty((String)cond.getSearchText())) {
            sql = sql + " and (zbTitle like ?  or zb_Y like ? or zb_J like ? or zb_H like ? or zb_N like ?)";
            String searchStr = "%" + cond.getSearchText() + "%";
            params.addAll(Arrays.asList(searchStr, searchStr, searchStr, searchStr, searchStr));
        }
        if ((count = this.count(sql = sql + " order by sortorder asc \n", params)) == 0) {
            return PageInfo.empty();
        }
        int pageNum = cond.getPageNum();
        int pageSize = cond.getPageSize();
        List monthCalcZbMappingEOS = pageNum > 1 ? this.selectEntityByPaging(sql, (pageNum - 1) * pageSize, pageNum * pageSize, params) : this.selectEntity(sql, params);
        return PageInfo.of((List)monthCalcZbMappingEOS, (int)count);
    }

    @Override
    public void delBySchemeId(String schemeId) {
        String sql = "delete  from GC_MONTHCALCZBMAPPING where monthCalcSchemeId=?";
        this.execute(sql, new Object[]{schemeId});
    }

    @Override
    public List<MonthCalcZbMappingEO> getZbMappingsBySchemeID(String monthCalcSchemeId) {
        String sql = "select * form GC_MONTHCALCZBMAPPINGwhere monthCalcSchemeId=?";
        return this.selectEntity(sql, new Object[]{monthCalcSchemeId});
    }

    @Override
    public void deleteBatchByIds(List<String> monthCalcZbMappingIds) {
        if (CollectionUtils.isEmpty(monthCalcZbMappingIds)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(monthCalcZbMappingIds, (String)"ID");
        String sql = "delete from GC_MONTHCALCZBMAPPING where " + inSql;
        int execute = EntNativeSqlDefaultDao.getInstance().execute(sql);
    }

    @Override
    public MonthCalcZbMappingEO getPreNodeBySchemeIdAndOrder(String monthCalcSchemeId, String sortOrder) {
        String sql = "select %1$s \nfrom GC_MONTHCALCZBMAPPING  t \nwhere t.monthCalcSchemeId=? \nand t.sortOrder<? \norder by t.sortOrder desc\n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(MonthCalcZbMappingEO.class, (String)"t"));
        List eos = this.selectEntityByPaging(sql, 0, 1, new Object[]{monthCalcSchemeId, sortOrder});
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return null;
        }
        return (MonthCalcZbMappingEO)((Object)eos.get(0));
    }

    @Override
    public MonthCalcZbMappingEO getNextNodeBySchemeIdAndOrder(String monthCalcSchemeId, String sortOrder) {
        String sql = "select %1$s \nfrom GC_MONTHCALCZBMAPPING  t \nwhere t.monthCalcSchemeId=? \nand t.sortOrder>? \norder by t.sortOrder asc\n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(MonthCalcZbMappingEO.class, (String)"t"));
        List eos = this.selectEntityByPaging(sql, 0, 1, new Object[]{monthCalcSchemeId, sortOrder});
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return null;
        }
        return (MonthCalcZbMappingEO)((Object)eos.get(0));
    }
}

