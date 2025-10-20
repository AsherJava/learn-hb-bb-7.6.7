/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils$Relation
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.clbr.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.clbr.dao.ClbrBillDeleteDao;
import com.jiuqi.gcreport.clbr.dao.temptable.ClbrIdRealTempDao;
import com.jiuqi.gcreport.clbr.dao.temptable.TempUtils;
import com.jiuqi.gcreport.clbr.entity.ClbrBillDeleteEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum;
import com.jiuqi.gcreport.clbr.util.ClbrFilterColumnHandlerUtils;
import com.jiuqi.gcreport.clbr.util.RelationUtils;
import com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ClbrBillDeleteDaoImpl
extends AbstractEntDbSqlGenericDAO<ClbrBillDeleteEO>
implements ClbrBillDeleteDao {
    public ClbrBillDeleteDaoImpl() {
        super(ClbrBillDeleteEO.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public Integer getRejectClbrBillCountByRelationCodeAndBillType(Set<String> codes, Integer clbrBillType) {
        String sql = "  SELECT count(1) \n  FROM GC_CLBR_BILL_DELETE  cl  WHERE   cl.BILLSTATE= ? ";
        ClbrBillTypeEnum enumByCode = ClbrBillTypeEnum.getEnumByCode((Integer)clbrBillType);
        if (Objects.nonNull(enumByCode)) {
            sql = sql + " and cl.CLBRBILLTYPE =  " + enumByCode.getCode();
        }
        TempTableCondition relationTempTableCondition = null;
        ClbrIdRealTempDao clbrIdRealTempDao = ClbrIdRealTempDao.newInstance();
        if (!CollectionUtils.isEmpty(codes)) {
            relationTempTableCondition = TempUtils.getTempCond(clbrIdRealTempDao, codes, " ");
        }
        if (relationTempTableCondition != null) {
            sql = sql + " AND  cl.THISRELATION" + relationTempTableCondition.getCondition();
        }
        try {
            Integer n = this.count(sql, new Object[]{ClbrBillStateEnum.REJECT.getCode()});
            return n;
        }
        finally {
            if (null != relationTempTableCondition) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(relationTempTableCondition.getTempGroupId());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillDeleteEO> listClbrBillDetailsByRelationCodeAndBillType(ClbrDataQueryConditon clbrDataQueryConditon, ClbrBillStateEnum clbrBillStateEnum) {
        List clbrBills;
        int count;
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL_DELETE", (String)"cl");
        String sql = "  SELECT  " + allFieldsSQL + "  FROM " + "GC_CLBR_BILL_DELETE" + "  cl  WHERE %1$s 1=1 ";
        ClbrBillTypeEnum enumByCode = ClbrBillTypeEnum.getEnumByCode((Integer)clbrDataQueryConditon.getClbrBillType());
        if (Objects.nonNull(enumByCode)) {
            sql = sql + " and cl.CLBRBILLTYPE =  " + enumByCode.getCode();
        }
        if (clbrBillStateEnum != null) {
            sql = sql + " AND cl.BILLSTATE= " + clbrBillStateEnum.getCode();
        }
        ClbrIdRealTempDao clbrIdRealTempDao = ClbrIdRealTempDao.newInstance();
        TempTableCondition relationTempTableCondition = null;
        if (!StringUtils.isEmpty((String)clbrDataQueryConditon.getRelation())) {
            relationTempTableCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrDataQueryConditon.getRelation(), AuthType.ACCESS);
        }
        if (relationTempTableCondition != null) {
            sql = sql + " AND  cl.THISRELATION" + relationTempTableCondition.getCondition();
        }
        TempTableCondition filterRelationTempTableCondition = null;
        TempTableCondition filterOppRelationTempTableCondition = null;
        TempTableCondition initiatorRelationTempTableCondition = null;
        TempTableCondition receiverRelationTempTableCondition = null;
        if (!StringUtils.isEmpty((String)clbrDataQueryConditon.getFilterRelation())) {
            filterRelationTempTableCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrDataQueryConditon.getFilterRelation(), AuthType.ACCESS);
        }
        if (!StringUtils.isEmpty((String)clbrDataQueryConditon.getFilterOppRelation())) {
            filterOppRelationTempTableCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrDataQueryConditon.getFilterOppRelation(), AuthType.NONE);
        }
        if (!StringUtils.isEmpty((String)clbrDataQueryConditon.getInitiatorRelation())) {
            initiatorRelationTempTableCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrDataQueryConditon.getInitiatorRelation(), AuthType.ACCESS);
        }
        if (!StringUtils.isEmpty((String)clbrDataQueryConditon.getReceiverRelation())) {
            receiverRelationTempTableCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrDataQueryConditon.getReceiverRelation(), AuthType.NONE);
        }
        ArrayList<Object> paramList = new ArrayList<Object>();
        ArrayList<String> tempGroupIds = new ArrayList<String>();
        String conditionSql = this.getDataQueryWhereSqlByConditon(clbrDataQueryConditon, paramList, "cl", filterRelationTempTableCondition, filterOppRelationTempTableCondition, initiatorRelationTempTableCondition, receiverRelationTempTableCondition, tempGroupIds);
        String formatSql = String.format(sql, conditionSql);
        int begin = (clbrDataQueryConditon.getPageNum() - 1) * clbrDataQueryConditon.getPageSize();
        int end = clbrDataQueryConditon.getPageNum() * clbrDataQueryConditon.getPageSize();
        try {
            count = this.count(formatSql, paramList);
            if (count == 0) {
                PageInfo pageInfo = PageInfo.empty();
                return pageInfo;
            }
            clbrBills = this.selectEntityByPaging(formatSql, begin, end, paramList);
        }
        finally {
            if (null != relationTempTableCondition) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(relationTempTableCondition.getTempGroupId());
            }
            if (null != filterRelationTempTableCondition) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(filterRelationTempTableCondition.getTempGroupId());
            }
            if (null != filterOppRelationTempTableCondition) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(filterOppRelationTempTableCondition.getTempGroupId());
            }
            if (null != initiatorRelationTempTableCondition) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(initiatorRelationTempTableCondition.getTempGroupId());
            }
            if (null != receiverRelationTempTableCondition) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(receiverRelationTempTableCondition.getTempGroupId());
            }
            tempGroupIds.forEach(clbrIdRealTempDao::deleteIdRealTempByBatchId);
        }
        return PageInfo.of((List)clbrBills, (int)count);
    }

    private String getDataQueryWhereSqlByConditon(ClbrDataQueryConditon clbrDataQueryConditon, List<Object> paramList, String alias, TempTableCondition relationTempTableCondition, TempTableCondition oppRelationTempTableCondition, TempTableCondition initiatorRelationTempTableCondition, TempTableCondition receiverRelationTempTableCondition, List<String> tempGroupIds) {
        StringBuffer whereSql = new StringBuffer();
        if (!StringUtils.isEmpty((String)clbrDataQueryConditon.getClbrBillCode())) {
            whereSql.append(alias).append(".CLBRBILLCODE like '%").append(clbrDataQueryConditon.getClbrBillCode()).append("%'").append(" AND ");
        }
        if (Objects.nonNull(relationTempTableCondition) && !StringUtils.isEmpty((String)relationTempTableCondition.getCondition())) {
            whereSql.append(" ").append(alias).append(".THISRELATION ").append(relationTempTableCondition.getCondition()).append(" AND ");
        }
        if (Objects.nonNull(oppRelationTempTableCondition) && !StringUtils.isEmpty((String)oppRelationTempTableCondition.getCondition())) {
            whereSql.append(" ").append(alias).append(".THATRELATION ").append(oppRelationTempTableCondition.getCondition()).append(" AND ");
        }
        if (Objects.nonNull(initiatorRelationTempTableCondition) && !StringUtils.isEmpty((String)initiatorRelationTempTableCondition.getCondition())) {
            whereSql.append(" ").append(alias).append(".RELATION ").append(initiatorRelationTempTableCondition.getCondition()).append(" AND ");
        }
        if (Objects.nonNull(receiverRelationTempTableCondition) && !StringUtils.isEmpty((String)receiverRelationTempTableCondition.getCondition())) {
            whereSql.append(" ").append(alias).append(".OPPRELATION ").append(receiverRelationTempTableCondition.getCondition()).append(" AND ");
        }
        if (!StringUtils.isEmpty((String)clbrDataQueryConditon.getClbrCode())) {
            whereSql.append(" ").append(alias).append(".CLBRCODE = ? ").append(" AND ");
            paramList.add(clbrDataQueryConditon.getClbrCode());
        }
        if (!StringUtils.isEmpty((String)clbrDataQueryConditon.getClbrType())) {
            String[] clbrTypeArray = clbrDataQueryConditon.getClbrType().split(",");
            String clbrType = SqlUtils.getConditionOfIdsUseOr(new ArrayList<String>(Arrays.asList(clbrTypeArray)), (String)" ");
            whereSql.append(" ").append(alias).append(".CLBRTYPE ").append(clbrType).append(" AND ");
        }
        if (Objects.nonNull(clbrDataQueryConditon.getAmountMin())) {
            whereSql.append(" ").append(alias).append(".AMOUNT >= ?").append(" AND ");
            paramList.add(clbrDataQueryConditon.getAmountMin());
        }
        if (Objects.nonNull(clbrDataQueryConditon.getAmountMax())) {
            whereSql.append(" ").append(alias).append(".AMOUNT <= ?").append(" AND ");
            paramList.add(clbrDataQueryConditon.getAmountMax());
        }
        if (Objects.nonNull(clbrDataQueryConditon.getVerifyedAmountMin())) {
            whereSql.append(" ").append(alias).append(".VERIFYEDAMOUNT >= ?").append(" AND ");
            paramList.add(clbrDataQueryConditon.getVerifyedAmountMin());
        }
        if (Objects.nonNull(clbrDataQueryConditon.getVerifyedAmountMax())) {
            whereSql.append(" ").append(alias).append(".VERIFYEDAMOUNT <= ?").append(" AND ");
            paramList.add(clbrDataQueryConditon.getVerifyedAmountMax());
        }
        if (Objects.nonNull(clbrDataQueryConditon.getNoverifyAmountMin())) {
            whereSql.append(" ").append(alias).append(".NOVERIFYAMOUNT >= ?").append(" AND ");
            paramList.add(clbrDataQueryConditon.getNoverifyAmountMin());
        }
        if (Objects.nonNull(clbrDataQueryConditon.getNoverifyAmountMax())) {
            whereSql.append(" ").append(alias).append(".NOVERIFYAMOUNT <= ?").append(" AND ");
            paramList.add(clbrDataQueryConditon.getNoverifyAmountMax());
        }
        if (!CollectionUtils.isEmpty((Collection)clbrDataQueryConditon.getUserCodes())) {
            TempTableCondition conditionOfIds = SqlUtils.getConditionOfIds((Collection)clbrDataQueryConditon.getUserCodes(), (String)(alias + ".USERNAME"), (SqlUtils.Relation)SqlUtils.Relation.POSITIVE, (boolean)false);
            whereSql.append(" ").append(conditionOfIds.getCondition()).append(" AND ");
            if (!StringUtils.isEmpty((String)conditionOfIds.getTempGroupId())) {
                tempGroupIds.add(conditionOfIds.getTempGroupId());
            }
        }
        if (Objects.nonNull(clbrDataQueryConditon.getOtherColumnsCondition()) && !clbrDataQueryConditon.getOtherColumnsCondition().isEmpty()) {
            ClbrFilterColumnHandlerUtils.handlerColumns(clbrDataQueryConditon.getOtherColumnsCondition(), whereSql, alias, paramList);
        }
        return whereSql.toString();
    }
}

