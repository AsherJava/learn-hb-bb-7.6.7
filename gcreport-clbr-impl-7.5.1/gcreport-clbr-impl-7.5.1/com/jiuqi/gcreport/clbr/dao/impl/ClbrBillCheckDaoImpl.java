/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrBussinessMode
 *  com.jiuqi.gcreport.clbr.enums.ClbrConfirmStatusEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrTabEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils$Relation
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.clbr.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.clbr.dao.ClbrBillCheckDao;
import com.jiuqi.gcreport.clbr.dao.temptable.ClbrIdRealTempDao;
import com.jiuqi.gcreport.clbr.dao.temptable.TempUtils;
import com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillCheckEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrBussinessMode;
import com.jiuqi.gcreport.clbr.enums.ClbrConfirmStatusEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrTabEnum;
import com.jiuqi.gcreport.clbr.util.ClbrFilterColumnHandlerUtils;
import com.jiuqi.gcreport.clbr.util.RelationUtils;
import com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ClbrBillCheckDaoImpl
extends AbstractEntDbSqlGenericDAO<ClbrBillCheckEO>
implements ClbrBillCheckDao {
    private static final Logger log = LoggerFactory.getLogger(ClbrBillCheckDaoImpl.class);

    public ClbrBillCheckDaoImpl() {
        super(ClbrBillCheckEO.class);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ClbrBillCheckEO> queryGroupByBillIds(Set<String> billIds) {
        if (CollectionUtils.isEmpty(billIds)) {
            return Collections.emptyList();
        }
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILLCHECK", (String)"t");
        ClbrIdRealTempDao clbrIdRealTempDao = ClbrIdRealTempDao.newInstance();
        TempTableCondition billIdCondition = TempUtils.getTempCond(clbrIdRealTempDao, billIds, "s.billid");
        String subQuerySql = "select s.GROUPID from GC_CLBR_BILLCHECK  s where " + billIdCondition.getCondition();
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(allFieldsSQL).append(" from ").append("GC_CLBR_BILLCHECK").append("  t where t.GROUPID in (").append(subQuerySql).append(")");
        List eos = this.selectEntity(sql.toString(), new Object[0]);
        clbrIdRealTempDao.deleteIdRealTempByBatchId(billIdCondition.getTempGroupId());
        return eos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillCheckEO> listConfirmPageByCondition(ClbrProcessCondition clbrProcessCondition) {
        List clbrBills;
        int count;
        AuthType authType = AuthType.ACCESS;
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getSysCode())) {
            authType = AuthType.WRITE;
        }
        ArrayList<Object> paramList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ").append(" GROUPID ").append(" from ").append("GC_CLBR_BILLCHECK").append(" GCA ").append(" where (%1$s 1=1) ");
        ClbrIdRealTempDao clbrIdRealTempDao = ClbrIdRealTempDao.newInstance();
        ArrayList<String> tempGroupIds = new ArrayList<String>();
        TempTableCondition relationTempTableCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrProcessCondition.getRelation(), authType);
        TempTableCondition oppRelationTempTableCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrProcessCondition.getOppRelation(), AuthType.NONE);
        TempTableCondition initiatorRelationTempTableCondition = null;
        TempTableCondition receiverRelationTempTableCondition = null;
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getInitiatorRelation())) {
            initiatorRelationTempTableCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrProcessCondition.getInitiatorRelation(), authType);
        }
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getReceiverRelation())) {
            receiverRelationTempTableCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrProcessCondition.getReceiverRelation(), AuthType.NONE);
        }
        int pageSize = clbrProcessCondition.getPageSize();
        int begin = (clbrProcessCondition.getPageNum() - 1) * pageSize;
        int end = clbrProcessCondition.getPageNum() * pageSize;
        if (Objects.isNull(clbrProcessCondition.getTabFlag())) {
            clbrProcessCondition.setTabFlag(ClbrTabEnum.PROCESS_CONFIRM);
        }
        switch (clbrProcessCondition.getTabFlag()) {
            case PROCESS_INITIATOR_NOT_CONFIRM: {
                sql.append(" and GCA.CONFIRMSTATUS = ").append(ClbrConfirmStatusEnum.SINGLE_CONFIRMED.getCode()).append(" and GCA.CLBRBILLTYPE = ").append(ClbrBillTypeEnum.RECEIVER.getCode());
                break;
            }
            case PROCESS_RECEIVER_NOT_CONFIRM: {
                sql.append(" and GCA.CONFIRMSTATUS = ").append(ClbrConfirmStatusEnum.SINGLE_CONFIRMED.getCode()).append(" and GCA.CLBRBILLTYPE = ").append(ClbrBillTypeEnum.INITIATOR.getCode());
                if (!ClbrBussinessMode.BUSINESS_MODE.getCode().equals(clbrProcessCondition.getMode())) break;
                sql.append(" and GCA.initiateConfirmUsername = '").append(NpContextHolder.getContext().getUserName()).append("'");
                break;
            }
            case PROCESS_CONFIRM: {
                sql.append(" and GCA.CONFIRMSTATUS = ").append(ClbrConfirmStatusEnum.BOTH_CONFIRMED.getCode());
                if (!ClbrBussinessMode.BUSINESS_MODE.getCode().equals(clbrProcessCondition.getMode())) break;
                sql.append(" and GCA.USERNAME = '").append(NpContextHolder.getContext().getUserName()).append("'");
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u9875\u7b7e\u4e0d\u5b58\u5728");
            }
        }
        if ((!StringUtils.isEmpty((String)clbrProcessCondition.getSysCode()) || ClbrBussinessMode.BUSINESS_MODE.getCode().equals(clbrProcessCondition.getMode()) && ClbrTabEnum.PROCESS_INITIATOR_NOT_CONFIRM.equals((Object)clbrProcessCondition.getTabFlag())) && !clbrProcessCondition.getReceiveClbrType2Relations().isEmpty()) {
            sql.append((CharSequence)this.getThroughWhereSql(clbrProcessCondition.getReceiveClbrType2Relations(), clbrProcessCondition.getSysCode(), clbrProcessCondition.getUserName(), clbrProcessCondition.getThroughRelation(), clbrIdRealTempDao, tempGroupIds));
        }
        sql.append(" group by GROUPID ");
        String whereSql = this.getWhereSqlByConditon(clbrProcessCondition, paramList, "GCA", relationTempTableCondition, oppRelationTempTableCondition, initiatorRelationTempTableCondition, receiverRelationTempTableCondition, tempGroupIds, clbrIdRealTempDao);
        String finalSql = String.format(sql.toString(), whereSql);
        try {
            String countSql = String.format("select count(1) from ( %1$s )  g", finalSql);
            count = this.count(countSql, paramList);
            if (count == 0) {
                PageInfo pageInfo = PageInfo.empty();
                return pageInfo;
            }
            clbrBills = clbrProcessCondition.getPageNum() < 0 ? this.selectEntity(finalSql, paramList) : this.selectEntityByPaging(finalSql, begin, end, paramList);
        }
        finally {
            if (null != relationTempTableCondition) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(relationTempTableCondition.getTempGroupId());
            }
            if (null != oppRelationTempTableCondition) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(oppRelationTempTableCondition.getTempGroupId());
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

    private StringBuilder getThroughWhereSql(Map<String, Set<String>> receiveClbrType2Relations, String sysCode, String userName, String relation, ClbrIdRealTempDao clbrIdRealTempDao, List<String> tempGroupIds) {
        StringBuilder whereSql = new StringBuilder();
        receiveClbrType2Relations.forEach((receiveClbrType, receiveRelations) -> {
            TempTableCondition oppRelation = TempUtils.getTempCond(clbrIdRealTempDao, receiveRelations, "oppRelation");
            if (!StringUtils.isEmpty((String)oppRelation.getTempGroupId())) {
                tempGroupIds.add(oppRelation.getTempGroupId());
            }
            whereSql.append(" or ((oppClbrType = '").append((String)receiveClbrType).append("' or oppClbrType like '").append((String)receiveClbrType).append(",%%").append("' or oppClbrType like '%%,").append((String)receiveClbrType).append(",%%").append("' or oppClbrType like '%%,").append((String)receiveClbrType).append("') and ").append(oppRelation.getCondition()).append(")");
        });
        StringBuilder throughWhereSql = new StringBuilder();
        if (!StringUtils.isEmpty((String)sysCode)) {
            throughWhereSql.append(" and sysCode='").append(sysCode).append("'");
        }
        if (!StringUtils.isEmpty((String)relation)) {
            throughWhereSql.append(" and opprelation='").append(relation).append("'");
        }
        throughWhereSql.append(" and ( (1 = 2 " + whereSql + ") ");
        if (!StringUtils.isEmpty((String)userName)) {
            throughWhereSql.append(" or (oppUserName='").append(userName).append("')");
        }
        throughWhereSql.append(")");
        return throughWhereSql;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ClbrBillCheckEO> listByGroupIds(List<String> groupIds) {
        TempTableCondition condition = SqlUtils.getConditionOfIds(groupIds, (String)"GROUPID", (SqlUtils.Relation)SqlUtils.Relation.POSITIVE, (boolean)false);
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILLCHECK", (String)"t");
        String querySql = "select " + allFieldsSQL + " from " + "GC_CLBR_BILLCHECK" + "  t where 1=1 and " + condition.getCondition();
        try {
            List list = this.selectEntity(querySql, new Object[0]);
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)condition.getTempGroupId());
        }
    }

    @Override
    public void deleteByGroupIds(List<String> groupIds) {
        TempTableCondition inSql = SqlUtils.getConditionOfIds(groupIds, (String)"GROUPID", (SqlUtils.Relation)SqlUtils.Relation.POSITIVE, (boolean)false);
        String sql = " delete from GC_CLBR_BILLCHECK where " + inSql.getCondition();
        this.execute(sql);
        IdTemporaryTableUtils.deteteByGroupId((String)inSql.getTempGroupId());
    }

    @Override
    public void updateConfirmStatusByGroupIds(List<String> groupIds, String userName) {
        TempTableCondition inSql = SqlUtils.getConditionOfIds(groupIds, (String)"GROUPID", (SqlUtils.Relation)SqlUtils.Relation.POSITIVE, (boolean)false);
        String sql = "update GC_CLBR_BILLCHECK set CONFIRMSTATUS = 0, receiveConfirmUsername = ?  where " + inSql.getCondition();
        this.execute(sql, new Object[]{userName});
        IdTemporaryTableUtils.deteteByGroupId((String)inSql.getTempGroupId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillCheckEO> listInitiatorConfirmByUser(ClbrBillSsoConditionDTO clbrBillSsoConditionDTO) {
        List eos;
        TempTableCondition oppRelationsCondition;
        TempTableCondition relationsCondition;
        String clbrBillCode = clbrBillSsoConditionDTO.getClbrBillCode();
        String clbrCode = clbrBillSsoConditionDTO.getClbrCode();
        String remark = clbrBillSsoConditionDTO.getRemark();
        Double amountMax = clbrBillSsoConditionDTO.getAmountMax();
        Double amountMin = clbrBillSsoConditionDTO.getAmountMin();
        StringBuilder whereSql = new StringBuilder();
        ClbrIdRealTempDao clbrIdRealTempDao = ClbrIdRealTempDao.newInstance();
        HashSet tempGroupId = new HashSet();
        if (!StringUtils.isEmpty((String)clbrBillSsoConditionDTO.getSysCode())) {
            clbrBillSsoConditionDTO.getReceiveClbrType2Relations().forEach((receiveClbrType, receiveRelations) -> {
                TempTableCondition oppRelation = TempUtils.getTempCond(clbrIdRealTempDao, receiveRelations, "oppRelation");
                if (!StringUtils.isEmpty((String)oppRelation.getTempGroupId())) {
                    tempGroupId.add(oppRelation.getTempGroupId());
                }
                whereSql.append(" or ((oppClbrType = '").append((String)receiveClbrType).append("' or oppClbrType like '").append((String)receiveClbrType).append(",%").append("' or oppClbrType like '%,").append((String)receiveClbrType).append(",%").append("' or oppClbrType like '%,").append((String)receiveClbrType).append("') and ").append(oppRelation.getCondition()).append(")");
            });
        }
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select ").append(" srcId ").append(" from ").append("GC_CLBR_BILLCHECK").append("  t ");
        queryBuilder.append(" where confirmtype = 1 and clbrBillType = 0 ");
        if (ClbrBussinessMode.BUSINESS_MODE.getCode().equals(clbrBillSsoConditionDTO.getMode())) {
            queryBuilder.append(" and initiateConfirmUsername ='").append(clbrBillSsoConditionDTO.getUserName()).append("'");
        } else if (!CollectionUtils.isEmpty((Collection)clbrBillSsoConditionDTO.getConfirmUserCodes())) {
            String userNameInSql = SqlBuildUtil.getStrInCondi((String)"initiateConfirmUsername", (List)clbrBillSsoConditionDTO.getConfirmUserCodes());
            queryBuilder.append(" and ").append(userNameInSql);
        }
        if (!StringUtils.isEmpty((String)clbrBillSsoConditionDTO.getSysCode())) {
            queryBuilder.append(" and sysCode='").append(clbrBillSsoConditionDTO.getSysCode()).append("'");
        }
        if (!StringUtils.isEmpty((String)clbrBillSsoConditionDTO.getRelation())) {
            queryBuilder.append(" and opprelation='").append(clbrBillSsoConditionDTO.getRelation()).append("'");
        }
        if (Objects.isNull(relationsCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrBillSsoConditionDTO.getConditionRelation(), AuthType.NONE)) || Objects.isNull(relationsCondition.getCondition())) {
            queryBuilder.append(" and 1 = 2 ");
        } else {
            queryBuilder.append(" and relation ").append(relationsCondition.getCondition());
        }
        AuthType authType = AuthType.ACCESS;
        if (!StringUtils.isEmpty((String)clbrBillSsoConditionDTO.getSysCode())) {
            authType = AuthType.WRITE;
        }
        if (Objects.isNull(oppRelationsCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrBillSsoConditionDTO.getConditionOppRelation(), authType)) || Objects.isNull(oppRelationsCondition.getCondition())) {
            queryBuilder.append(" and 1 = 2 ");
        } else {
            queryBuilder.append(" and opprelation ").append(oppRelationsCondition.getCondition());
        }
        if (!StringUtils.isEmpty((String)clbrBillSsoConditionDTO.getConditionClbrType())) {
            ArrayList<String> clbrTypes = new ArrayList<String>(Arrays.asList(clbrBillSsoConditionDTO.getConditionClbrType().split(",")));
            String clbrTypeInSql = SqlBuildUtil.getStrInCondi((String)"clbrType", clbrTypes);
            queryBuilder.append("   and ").append(clbrTypeInSql);
        }
        if (!CollectionUtils.isEmpty((Collection)clbrBillSsoConditionDTO.getUserCodes())) {
            String userNameInSql = SqlBuildUtil.getStrInCondi((String)"userName", (List)clbrBillSsoConditionDTO.getUserCodes());
            queryBuilder.append("   and ").append(userNameInSql);
        }
        if (Objects.nonNull(amountMax)) {
            queryBuilder.append(" and t.AMOUNT <= " + amountMax);
        }
        if (Objects.nonNull(amountMin)) {
            queryBuilder.append(" and t.AMOUNT >=" + amountMin);
        }
        if (!StringUtils.isEmpty((String)clbrCode)) {
            queryBuilder.append(" and t.CLBRCODE ='" + clbrCode + "'");
        }
        if (!StringUtils.isEmpty((String)clbrBillCode)) {
            queryBuilder.append(" and t.CLBRBILLCODE like '%" + clbrBillCode + "%'");
        }
        if (!StringUtils.isEmpty((String)remark)) {
            queryBuilder.append(" and t.REMARK like '%" + remark + "%'");
        }
        if (!StringUtils.isEmpty((String)clbrBillSsoConditionDTO.getSysCode())) {
            queryBuilder.append(" and ( (1 = 2 " + whereSql + ") ");
            if (!StringUtils.isEmpty((String)clbrBillSsoConditionDTO.getUserName())) {
                queryBuilder.append(" or (oppUserName='").append(clbrBillSsoConditionDTO.getUserName()).append("')");
            }
            queryBuilder.append(") ");
        }
        queryBuilder.append(" group by srcId  ");
        String querySql = queryBuilder.toString();
        int count = this.count(querySql, new Object[0]);
        if (count == 0) {
            return PageInfo.empty();
        }
        int begin = (clbrBillSsoConditionDTO.getPageNum() - 1) * clbrBillSsoConditionDTO.getPageSize();
        int end = clbrBillSsoConditionDTO.getPageNum() * clbrBillSsoConditionDTO.getPageSize();
        try {
            eos = this.selectEntityByPaging(querySql, begin, end, new Object[0]);
        }
        finally {
            if (Objects.nonNull(relationsCondition)) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(relationsCondition.getTempGroupId());
            }
            if (Objects.nonNull(oppRelationsCondition)) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(oppRelationsCondition.getTempGroupId());
            }
            tempGroupId.forEach(clbrIdRealTempDao::deleteIdRealTempByBatchId);
        }
        if (eos == null) {
            return PageInfo.empty();
        }
        return PageInfo.of((List)eos, (int)clbrBillSsoConditionDTO.getPageNum(), (int)clbrBillSsoConditionDTO.getPageSize(), (int)count);
    }

    private String getWhereSqlByConditon(ClbrProcessCondition clbrProcessCondition, List<Object> paramList, String alias, TempTableCondition relationTempTableCondition, TempTableCondition oppRelationTempTableCondition, TempTableCondition initiatorRelationTempTableCondition, TempTableCondition receiverRelationTempTableCondition, List<String> tempGroupIds, ClbrIdRealTempDao clbrIdRealTempDao) {
        StringBuffer whereSql = new StringBuffer();
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getClbrBillCode())) {
            whereSql.append(alias).append(".CLBRBILLCODE like '%").append(clbrProcessCondition.getClbrBillCode()).append("%'").append(" AND ");
        }
        if (Objects.isNull(relationTempTableCondition) || Objects.isNull(oppRelationTempTableCondition)) {
            whereSql.append(" 1 = 2 ").append(" AND ");
            return whereSql.toString();
        }
        if (!StringUtils.isEmpty((String)relationTempTableCondition.getCondition())) {
            whereSql.append(" ").append(alias).append(".THISRELATION ").append(relationTempTableCondition.getCondition()).append(" AND ");
        }
        if (!StringUtils.isEmpty((String)oppRelationTempTableCondition.getCondition())) {
            whereSql.append(" ").append(alias).append(".THATRELATION ").append(oppRelationTempTableCondition.getCondition()).append(" AND ");
        }
        if (Objects.nonNull(initiatorRelationTempTableCondition) && !StringUtils.isEmpty((String)initiatorRelationTempTableCondition.getCondition())) {
            whereSql.append(" ").append(alias).append(".RELATION ").append(initiatorRelationTempTableCondition.getCondition()).append(" AND ");
        }
        if (Objects.nonNull(receiverRelationTempTableCondition) && !StringUtils.isEmpty((String)receiverRelationTempTableCondition.getCondition())) {
            whereSql.append(" ").append(alias).append(".OPPRELATION ").append(receiverRelationTempTableCondition.getCondition()).append(" AND ");
        }
        if (clbrProcessCondition.getStartTime() != null) {
            whereSql.append(" ").append(alias).append(".CREATETIME >= ?").append(" AND ");
            paramList.add(clbrProcessCondition.getStartTime());
        }
        if (clbrProcessCondition.getEndTime() != null) {
            whereSql.append(" ").append(alias).append(".CREATETIME <= ?").append(" AND ");
            paramList.add(clbrProcessCondition.getEndTime());
        }
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getClbrCode())) {
            whereSql.append(" ").append(alias).append(".CLBRCODE = ? ").append(" AND ");
            paramList.add(clbrProcessCondition.getClbrCode());
        }
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getClbrType())) {
            String[] clbrTypeArray = clbrProcessCondition.getClbrType().split(",");
            String clbrType = SqlUtils.getConditionOfIdsUseOr(new ArrayList<String>(Arrays.asList(clbrTypeArray)), (String)" ");
            whereSql.append(" ").append(alias).append(".CLBRTYPE ").append(clbrType).append(" AND ");
        }
        if (Objects.nonNull(clbrProcessCondition.getAmountMin())) {
            whereSql.append(" ").append(alias).append(".AMOUNT >= ?").append(" AND ");
            paramList.add(clbrProcessCondition.getAmountMin());
        }
        if (Objects.nonNull(clbrProcessCondition.getAmountMax())) {
            whereSql.append(" ").append(alias).append(".AMOUNT <= ?").append(" AND ");
            paramList.add(clbrProcessCondition.getAmountMax());
        }
        if (!CollectionUtils.isEmpty((Collection)clbrProcessCondition.getUserCodes())) {
            TempTableCondition conditionOfIds = TempUtils.getTempCond(clbrIdRealTempDao, clbrProcessCondition.getUserCodes(), alias + ".USERNAME");
            whereSql.append(" ").append(conditionOfIds.getCondition()).append(" AND ");
            if (!StringUtils.isEmpty((String)conditionOfIds.getTempGroupId())) {
                tempGroupIds.add(conditionOfIds.getTempGroupId());
            }
        }
        if (Objects.nonNull(clbrProcessCondition.getOtherColumnsCondition()) && !clbrProcessCondition.getOtherColumnsCondition().isEmpty()) {
            ClbrFilterColumnHandlerUtils.handlerColumns(clbrProcessCondition.getOtherColumnsCondition(), whereSql, alias, paramList);
        }
        return whereSql.toString();
    }
}

