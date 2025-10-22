/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.org.api.enums.GcOrgConst
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.domain.TableColumnDO
 *  com.jiuqi.va.organization.service.OrgCategoryService
 */
package com.jiuqi.gcreport.org.impl.fieldManager.dao.impl;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.org.api.enums.GcOrgConst;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.fieldManager.dao.GcOrgTypeDao;
import com.jiuqi.gcreport.org.impl.fieldManager.entity.GcOrgTypeEO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.domain.TableColumnDO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GcOrgTypeDaoImpl
extends AbstractEntDbSqlGenericDAO<GcOrgTypeEO>
implements GcOrgTypeDao {
    @Autowired
    private OrgCategoryService typeService;

    public GcOrgTypeDaoImpl() {
        super(GcOrgTypeEO.class);
    }

    @Override
    public void copyTypeVerData(OrgVersionVO version, String table) {
        Date validTime = version.getValidTime();
        JDialectUtil jDialectUtil = JDialectUtil.getInstance();
        List newColumns = jDialectUtil.getTableColumns(ShiroUtil.getTenantName(), table).stream().map(TableColumnDO::getColumnName).collect(Collectors.toList());
        List oldColumns = jDialectUtil.getTableColumns(ShiroUtil.getTenantName(), version.getOrgType().getTable()).stream().map(TableColumnDO::getColumnName).collect(Collectors.toList());
        newColumns.retainAll(oldColumns);
        newColumns = newColumns.stream().distinct().collect(Collectors.toList());
        String columnSql = String.join((CharSequence)",", newColumns);
        String formatSQLDate = InspectOrgUtils.getDateFormat(validTime);
        String sql = "insert into " + table + " (" + columnSql + ") select " + columnSql + " from " + version.getOrgType().getTable() + "  b  where b.VALIDTIME <= " + formatSQLDate + " and " + formatSQLDate + " < b.INVALIDTIME";
        this.execute(sql, new ArrayList());
        String updateSql = "update " + table + " set CREATETIME = ? , VALIDTIME =  ? ,INVALIDTIME=? where 1=1";
        ArrayList<Date> args = new ArrayList<Date>();
        args.add(DateUtils.now());
        args.add(GcOrgConst.ORG_VERDATE_MIN);
        args.add(GcOrgConst.ORG_VERDATE_MAX);
        this.execute(updateSql, args);
        String updateOrgTypeSql = "update " + table + " set ORGTYPEID = ?  where ORGTYPEID <> ? ";
        ArrayList<String> args2 = new ArrayList<String>();
        args2.add(table);
        args2.add("MD_ORG_CORPORATE");
        this.execute(updateOrgTypeSql, args2);
        String currencySql = "insert into " + table + "_SUBLIST select * from " + version.getOrgType().getTable() + "_SUBLIST";
        this.execute(currencySql, new ArrayList());
    }
}

