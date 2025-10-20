/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.clbr.dao.impl;

import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.gcreport.clbr.dao.ClbrReceiveSettingDao;
import com.jiuqi.gcreport.clbr.entity.ClbrReceiveSettingEO;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ClbrReceiveSettingDaoImpl
extends AbstractEntDbSqlGenericDAO<ClbrReceiveSettingEO>
implements ClbrReceiveSettingDao {
    public ClbrReceiveSettingDaoImpl() {
        super(ClbrReceiveSettingEO.class);
    }

    @Override
    public List<ClbrReceiveSettingEO> listByUserOrRole(String sysCode, String userName, String roleCode) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_RECEIVE_SETTING", (String)"t");
        String querySql = "";
        if (StringUtils.isNotBlank((CharSequence)userName)) {
            querySql = "  select " + allFieldsSQL + " from " + "GC_CLBR_RECEIVE_SETTING" + "  t where usernames like '%" + userName + "%' \n";
        }
        if (StringUtils.isNotBlank((CharSequence)userName) && StringUtils.isNotBlank((CharSequence)roleCode)) {
            querySql = querySql + " union \n  select " + allFieldsSQL + " from " + "GC_CLBR_RECEIVE_SETTING" + "  t where rolecodes like '%" + roleCode + "%' \n";
        }
        if (StringUtils.isBlank((CharSequence)userName) && StringUtils.isNotBlank((CharSequence)roleCode)) {
            querySql = "  select " + allFieldsSQL + " from " + "GC_CLBR_RECEIVE_SETTING" + "  t where rolecodes like '%" + roleCode + "%' \n";
        }
        if (StringUtils.isBlank((CharSequence)querySql)) {
            return Collections.emptyList();
        }
        List eos = this.selectEntity(querySql, new Object[0]);
        return eos;
    }

    @Override
    public void deleteByIds(List<String> settingIds) {
        String inSql = SqlBuildUtil.getStrInCondi((String)"ID", settingIds);
        String sql = " delete from GC_CLBR_RECEIVE_SETTING where " + inSql;
        this.execute(sql);
    }

    @Override
    public List<ClbrReceiveSettingEO> selectOrderListByPaging(Integer pageNum, Integer pageSize) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_RECEIVE_SETTING", (String)"crs");
        String sql = "select " + allFieldsSQL + " from " + "GC_CLBR_RECEIVE_SETTING" + " crs  order by crs.oppRelation,crs.oppClbrTypes";
        return this.selectEntityByPaging(sql, pageNum, pageSize, new Object[0]);
    }
}

