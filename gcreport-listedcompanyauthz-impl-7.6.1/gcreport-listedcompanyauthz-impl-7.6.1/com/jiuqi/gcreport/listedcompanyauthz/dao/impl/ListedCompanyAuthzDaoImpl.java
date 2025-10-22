/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.gcreport.listedcompanyauthz.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.listedcompanyauthz.dao.FListedCompanyAuthzDao;
import com.jiuqi.gcreport.listedcompanyauthz.domain.ListedCompanySaveParam;
import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyAuthzEO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@DependsOn(value={"GcSpringContextUtils"})
public class ListedCompanyAuthzDaoImpl
extends GcDbSqlGenericDAO<ListedCompanyAuthzEO, String>
implements FListedCompanyAuthzDao {
    public ListedCompanyAuthzDaoImpl() {
        super(ListedCompanyAuthzEO.class);
    }

    @Override
    public List<ListedCompanyAuthzEO> query(ListedCompanyAuthzVO param) {
        ArrayList<String> paramVals = new ArrayList<String>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" T.ID AS ID,T.ORGCODE AS ORGCODE,M.ORGTITLE AS ORGNAME,T.USERID AS USERID,T.USERNAME AS USERNAME,T.ISPENETRATE AS ISPENETRATE, ");
        sql.append(" T.CREATETIME AS CREATETIME,T.CREATEUSER AS CREATEUSER ");
        sql.append(" FROM GC_LISTEDCOMPANY_AUTHZ T ");
        sql.append(" LEFT JOIN GC_LISTEDCOMPANY M ON T.ORGCODE = M.ORGCODE ");
        sql.append(" WHERE 1=1 ");
        if (!StringUtils.isEmpty(param.getId())) {
            paramVals.add(param.getId());
            sql.append(" AND T.ID <> ? ");
        }
        if (!StringUtils.isEmpty(param.getCurrLoginUser())) {
            paramVals.add(param.getCurrLoginUser());
            sql.append(" AND M.USERNAME = ? ");
        }
        if (!StringUtils.isEmpty(param.getOrgCode())) {
            paramVals.add(param.getOrgCode());
            sql.append(" AND T.ORGCODE = ? ");
        }
        if (!StringUtils.isEmpty(param.getUserName())) {
            paramVals.add(param.getUserName());
            sql.append(" AND T.USERNAME = ? ");
        }
        if (!StringUtils.isEmpty(param.getUserId())) {
            paramVals.add(param.getUserId());
            sql.append(" AND T.USERID = ? ");
        }
        if (!StringUtils.isEmpty(param.getSearchText())) {
            String st = "%" + param.getSearchText().replace("'", "''") + "%";
            sql.append(" AND (");
            paramVals.add(st);
            sql.append(" T.USERNAME like ? ");
            sql.append(") ");
        }
        sql.append(" ORDER BY T.ORGCODE,T.CREATETIME ");
        return this.selectEntity(sql.toString(), paramVals);
    }

    @Override
    public List<String> getNoAuthzOrgByUser(String username) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" T.ORGCODE AS ORGCODE  ");
        sql.append(" FROM GC_LISTEDCOMPANY T ");
        sql.append(" WHERE T.ORGCODE NOT IN ");
        sql.append(" (SELECT S.ORGCODE AS ORGCODE FROM GC_LISTEDCOMPANY_AUTHZ S WHERE S. USERNAME = ? )");
        List queryBySql = this.selectEntity(sql.toString(), new Object[]{username});
        return queryBySql.stream().map(v -> v.getOrgCode()).collect(Collectors.toList());
    }

    @Override
    public List<String> queryAuthzOrgByUser(String username) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" T.ORGCODE AS ORGCODE  ");
        sql.append(" FROM GC_LISTEDCOMPANY_AUTHZ T ");
        sql.append(" WHERE T. USERNAME = ?  ");
        List queryBySql = this.selectFirstList(String.class, sql.toString(), new Object[]{username});
        return queryBySql;
    }

    @Override
    public void saveAll(ListedCompanySaveParam<ListedCompanyAuthzEO> datas) throws DataAccessException {
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

