/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO
 */
package com.jiuqi.gcreport.listedcompanyauthz.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.listedcompanyauthz.dao.FListedCompanyDao;
import com.jiuqi.gcreport.listedcompanyauthz.domain.ListedCompanySaveParam;
import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyEO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class ListedCompanyDaoImpl
extends GcDbSqlGenericDAO<ListedCompanyEO, String>
implements FListedCompanyDao {
    public ListedCompanyDaoImpl() {
        super(ListedCompanyEO.class);
    }

    @Override
    public List<ListedCompanyEO> query(ListedCompanyVO param) {
        ArrayList<String> paramVals = new ArrayList<String>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" T.ID AS ID,T.ORGCODE AS ORGCODE,T.ORGTITLE AS ORGTITLE, ");
        sql.append(" T.USERID AS USERID,T.USERNAME AS USERNAME, ");
        sql.append(" T.USERTITLE AS USERTITLE,T.CREATETIME AS CREATETIME, ");
        sql.append(" T.CREATEUSER AS CREATEUSER,T.MODIFYTIME AS MODIFYTIME,T.MODIFYUSER AS MODIFYUSER ");
        sql.append(" FROM GC_LISTEDCOMPANY T ");
        sql.append("  WHERE 1=1 ");
        if (!StringUtils.isEmpty(param.getId())) {
            paramVals.add(param.getId());
            sql.append(" AND T.ID <> ? ");
        }
        if (!StringUtils.isEmpty(param.getOrgCode())) {
            paramVals.add(param.getOrgCode());
            sql.append(" AND T.ORGCODE = ? ");
        }
        if (!StringUtils.isEmpty(param.getUserName())) {
            paramVals.add(param.getUserName());
            sql.append(" AND T.USERNAME = ? ");
        }
        if (!StringUtils.isEmpty(param.getSearchText())) {
            String st = "%" + param.getSearchText().replace("'", "''") + "%";
            sql.append(" AND (");
            paramVals.add(st);
            sql.append(" T.ORGCODE like ? ");
            paramVals.add(st);
            sql.append(" OR T.USERNAME like ? ");
            paramVals.add(st);
            sql.append(" OR T.ORGTITLE like ? ");
            paramVals.add(st);
            sql.append(" OR T.USERTITLE like ? ");
            sql.append(") ");
        }
        sql.append(" ORDER BY T.ORGCODE,T.CREATETIME ");
        return this.selectEntity(sql.toString(), paramVals);
    }

    @Override
    public List<String> queryAllListedCompany() {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" T.ORGCODE AS ORGCODE  ");
        sql.append(" FROM GC_LISTEDCOMPANY T ");
        List queryBySql = this.selectFirstList(String.class, sql.toString(), new Object[0]);
        return queryBySql;
    }

    @Override
    public void saveAll(ListedCompanySaveParam<ListedCompanyEO> datas) {
        if (datas.getDeleteDatas().size() > 0) {
            this.deleteBatch(datas.getDeleteDatas());
        }
        if (datas.getUpdateDatas().size() > 0) {
            this.updateAll(datas.getUpdateDatas());
        }
        if (datas.getInsertDatas().size() > 0) {
            this.saveAll(datas.getInsertDatas());
        }
    }
}

