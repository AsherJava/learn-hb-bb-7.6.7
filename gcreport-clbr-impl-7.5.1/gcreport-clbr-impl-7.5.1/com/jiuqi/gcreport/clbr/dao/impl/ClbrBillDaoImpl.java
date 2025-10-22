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
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.clbr.dto.ClbrArbitrationQueryParamDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillGenerateQueryDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillReceBillCodeDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrBussinessMode
 *  com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon
 *  com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
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
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.clbr.dao.ClbrBillDao;
import com.jiuqi.gcreport.clbr.dao.temptable.ClbrIdRealTempDao;
import com.jiuqi.gcreport.clbr.dao.temptable.TempUtils;
import com.jiuqi.gcreport.clbr.dto.ClbrArbitrationQueryParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillGenerateQueryDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillReceBillCodeDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillSsoConditionDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrBillEO;
import com.jiuqi.gcreport.clbr.enums.ClbrBillStateEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrBillTypeEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrBussinessMode;
import com.jiuqi.gcreport.clbr.util.ClbrFilterColumnHandlerUtils;
import com.jiuqi.gcreport.clbr.util.RelationUtils;
import com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon;
import com.jiuqi.gcreport.clbr.vo.ClbrProcessCondition;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Repository
public class ClbrBillDaoImpl
extends AbstractEntDbSqlGenericDAO<ClbrBillEO>
implements ClbrBillDao {
    private static final Logger log = LoggerFactory.getLogger(ClbrBillDaoImpl.class);

    public ClbrBillDaoImpl() {
        super(ClbrBillEO.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ClbrBillEO> listInitiatorNotConfirmByUser(ClbrBillSsoConditionDTO clbrBillSsoConditionDTO) {
        List eos;
        TempTableCondition oppRelationsCondition;
        TempTableCondition relationsCondition;
        String clbrBillCode = clbrBillSsoConditionDTO.getClbrBillCode();
        String clbrCode = clbrBillSsoConditionDTO.getClbrCode();
        String remark = clbrBillSsoConditionDTO.getRemark();
        Double amountMax = clbrBillSsoConditionDTO.getAmountMax();
        Double amountMin = clbrBillSsoConditionDTO.getAmountMin();
        StringBuilder whereSql = new StringBuilder();
        HashSet tempGroupId = new HashSet();
        ClbrIdRealTempDao clbrIdRealTempDao = ClbrIdRealTempDao.newInstance();
        if (!StringUtils.isEmpty((String)clbrBillSsoConditionDTO.getSysCode()) || ClbrBussinessMode.BUSINESS_MODE.getCode().equals(clbrBillSsoConditionDTO.getMode())) {
            clbrBillSsoConditionDTO.getReceiveClbrType2Relations().forEach((receiveClbrType, receiveRelations) -> {
                TempTableCondition oppRelation = TempUtils.getTempCond(clbrIdRealTempDao, receiveRelations, "oppRelation");
                if (!StringUtils.isEmpty((String)oppRelation.getTempGroupId())) {
                    tempGroupId.add(oppRelation.getTempGroupId());
                }
                whereSql.append(" or ((oppClbrType = '").append((String)receiveClbrType).append("' or oppClbrType like '").append((String)receiveClbrType).append(",%").append("' or oppClbrType like '%,").append((String)receiveClbrType).append(",%").append("' or oppClbrType like '%,").append((String)receiveClbrType).append("') and ").append(oppRelation.getCondition()).append(")");
            });
        }
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"t");
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select ").append(allFieldsSQL).append(" from ").append("GC_CLBR_BILL").append("  t ");
        queryBuilder.append(" where (t.billState = 0 or (t.billState = 1 and confirmtype = 1)) and clbrBillType = 0 ");
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
        TempTableCondition sysClbrTypeCond = null;
        if (!CollectionUtils.isEmpty((Collection)clbrBillSsoConditionDTO.getSysClbrType())) {
            sysClbrTypeCond = SqlUtils.getConditionOfIds((Collection)clbrBillSsoConditionDTO.getSysClbrType(), (String)"clbrType");
            queryBuilder.append("   and ").append(sysClbrTypeCond.getCondition());
        }
        TempTableCondition sysRelationCond = null;
        if (!CollectionUtils.isEmpty((Collection)clbrBillSsoConditionDTO.getSysRelation())) {
            sysRelationCond = SqlUtils.getConditionOfIds((Collection)clbrBillSsoConditionDTO.getSysRelation(), (String)"opprelation");
            queryBuilder.append("   and ").append(sysRelationCond.getCondition());
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
        if (!StringUtils.isEmpty((String)clbrBillSsoConditionDTO.getSysCode()) || ClbrBussinessMode.BUSINESS_MODE.getCode().equals(clbrBillSsoConditionDTO.getMode())) {
            queryBuilder.append(" and ( (1 = 2 " + whereSql + ") ");
            if (!StringUtils.isEmpty((String)clbrBillSsoConditionDTO.getUserName())) {
                queryBuilder.append(" or ((','||t.oppUserName||',') like '%,").append(clbrBillSsoConditionDTO.getUserName()).append(",%')");
            }
            queryBuilder.append(") ");
        }
        queryBuilder.append(" order by  createTime desc ,syscode, relation ");
        String querySql = queryBuilder.toString();
        try {
            eos = this.selectEntity(querySql, new Object[0]);
        }
        finally {
            if (Objects.nonNull(relationsCondition)) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(relationsCondition.getTempGroupId());
            }
            if (Objects.nonNull(oppRelationsCondition)) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(oppRelationsCondition.getTempGroupId());
            }
            if (Objects.nonNull(sysClbrTypeCond)) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(sysClbrTypeCond.getTempGroupId());
            }
            if (Objects.nonNull(sysRelationCond)) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(sysRelationCond.getTempGroupId());
            }
            tempGroupId.forEach(clbrIdRealTempDao::deleteIdRealTempByBatchId);
        }
        if (eos == null) {
            return Collections.emptyList();
        }
        return eos;
    }

    @Override
    public List<ClbrBillEO> listByIds(Set<String> clbrBillIds) {
        String idCondition = SqlUtils.getConditionOfIdsUseOr(clbrBillIds, (String)"id");
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"t");
        String querySql = "select " + allFieldsSQL + " from " + "GC_CLBR_BILL" + "  t where 1=1 and " + idCondition;
        List eos = this.selectEntity(querySql, new Object[0]);
        return eos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillEO> listNotConfirmInitiatorPageByCondition(ClbrProcessCondition clbrProcessCondition) {
        List clbrBills;
        int count;
        Set<String> receiverRelation;
        Set<String> initiatorRelations;
        AuthType authType = AuthType.ACCESS;
        ArrayList<Object> paramList = new ArrayList<Object>();
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"cl");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" SELECT ").append(allFieldsSQL).append("  FROM ").append("GC_CLBR_BILL").append("  cl  WHERE  %1$s  cl.CLBRBILLTYPE = ").append(ClbrBillTypeEnum.INITIATOR.getCode());
        if (ClbrBussinessMode.BUSINESS_MODE.getCode().equals(clbrProcessCondition.getMode())) {
            sqlBuilder.append(" and cl.USERNAME = '").append(NpContextHolder.getContext().getUserName()).append("' ");
        }
        sqlBuilder.append(" AND (cl.BILLSTATE = 0 OR (cl.BILLSTATE = 1 AND cl.CONFIRMTYPE = 2)) order by cl.CREATETIME DESC ");
        ClbrIdRealTempDao clbrIdRealTempDao = ClbrIdRealTempDao.newInstance();
        Set<String> relationCodes = this.getRelationCodesCondition(clbrProcessCondition.getRelation(), authType);
        Set<String> oppRelationCodes = this.getRelationCodesCondition(clbrProcessCondition.getOppRelation(), AuthType.NONE);
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getInitiatorRelation()) && !CollectionUtils.isEmpty(initiatorRelations = this.getRelationCodesCondition(clbrProcessCondition.getInitiatorRelation(), authType))) {
            relationCodes.retainAll(initiatorRelations);
        }
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getReceiverRelation()) && !CollectionUtils.isEmpty(receiverRelation = this.getRelationCodesCondition(clbrProcessCondition.getReceiverRelation(), AuthType.NONE))) {
            oppRelationCodes.retainAll(receiverRelation);
        }
        if (CollectionUtils.isEmpty(relationCodes) || CollectionUtils.isEmpty(oppRelationCodes)) {
            return PageInfo.empty();
        }
        TempTableCondition relationTempTableCondition = TempUtils.getTempCond(clbrIdRealTempDao, relationCodes, " ");
        TempTableCondition oppRelationTempTableCondition = TempUtils.getTempCond(clbrIdRealTempDao, oppRelationCodes, " ");
        ArrayList<String> tempGroupIds = new ArrayList<String>();
        String sql = String.format(sqlBuilder.toString(), this.getWhereSqlByConditon(clbrProcessCondition, paramList, "cl", relationTempTableCondition, oppRelationTempTableCondition, tempGroupIds));
        int begin = (clbrProcessCondition.getPageNum() - 1) * clbrProcessCondition.getPageSize();
        int end = clbrProcessCondition.getPageNum() * clbrProcessCondition.getPageSize();
        try {
            count = this.count(sql, paramList);
            if (count == 0) {
                PageInfo pageInfo = PageInfo.empty();
                return pageInfo;
            }
            clbrBills = this.selectEntityByPaging(sql, begin, end, paramList);
        }
        finally {
            clbrIdRealTempDao.deleteIdRealTempByBatchId(relationTempTableCondition.getTempGroupId());
            clbrIdRealTempDao.deleteIdRealTempByBatchId(oppRelationTempTableCondition.getTempGroupId());
            tempGroupIds.forEach(clbrIdRealTempDao::deleteIdRealTempByBatchId);
        }
        return PageInfo.of((List)clbrBills, (int)clbrProcessCondition.getPageNum(), (int)clbrProcessCondition.getPageSize(), (int)count);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillEO> listNotConfirmReceiverPageByCondition(ClbrProcessCondition clbrProcessCondition) {
        List clbrBills;
        int count;
        Set<String> receiverRelation;
        Set<String> initiatorRelations;
        AuthType authType = AuthType.ACCESS;
        ArrayList<Object> paramList = new ArrayList<Object>();
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"cl");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" SELECT ").append(allFieldsSQL).append("  FROM ").append("GC_CLBR_BILL").append("  cl  WHERE  %1$s  cl.CLBRBILLTYPE = ").append(ClbrBillTypeEnum.INITIATOR.getCode());
        ClbrIdRealTempDao clbrIdRealTempDao = ClbrIdRealTempDao.newInstance();
        ArrayList<String> tempGroupIds = new ArrayList<String>();
        if (ClbrBussinessMode.BUSINESS_MODE.getCode().equals(clbrProcessCondition.getMode())) {
            StringBuilder whereSql = new StringBuilder();
            if (!clbrProcessCondition.getReceiveClbrType2Relations().isEmpty()) {
                clbrProcessCondition.getReceiveClbrType2Relations().forEach((receiveClbrType, receiveRelations) -> {
                    TempTableCondition oppRelation = TempUtils.getTempCond(clbrIdRealTempDao, receiveRelations, "oppRelation");
                    if (!StringUtils.isEmpty((String)oppRelation.getTempGroupId())) {
                        tempGroupIds.add(oppRelation.getTempGroupId());
                    }
                    whereSql.append(" or ((oppClbrType = '").append((String)receiveClbrType).append("' or oppClbrType like '").append((String)receiveClbrType).append(",%%").append("' or oppClbrType like '%%,").append((String)receiveClbrType).append(",%%").append("' or oppClbrType like '%%,").append((String)receiveClbrType).append("') and ").append(oppRelation.getCondition()).append(")");
                });
                sqlBuilder.append(" and ( (1 = 2 " + whereSql + ") ");
                sqlBuilder.append(" or (oppUserName='").append(NpContextHolder.getContext().getUserName()).append("')");
                sqlBuilder.append(") ");
            }
        }
        sqlBuilder.append(" AND (cl.BILLSTATE = 0 OR (cl.BILLSTATE = 1 AND cl.CONFIRMTYPE = 2)) ");
        Set<String> relationCodes = this.getRelationCodesCondition(clbrProcessCondition.getRelation(), AuthType.NONE);
        Set<String> oppRelationCodes = this.getRelationCodesCondition(clbrProcessCondition.getOppRelation(), authType);
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getInitiatorRelation()) && !CollectionUtils.isEmpty(initiatorRelations = this.getRelationCodesCondition(clbrProcessCondition.getInitiatorRelation(), AuthType.NONE))) {
            relationCodes.retainAll(initiatorRelations);
        }
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getReceiverRelation()) && !CollectionUtils.isEmpty(receiverRelation = this.getRelationCodesCondition(clbrProcessCondition.getReceiverRelation(), authType))) {
            oppRelationCodes.retainAll(receiverRelation);
        }
        if (CollectionUtils.isEmpty(relationCodes) || CollectionUtils.isEmpty(oppRelationCodes)) {
            return PageInfo.empty();
        }
        if (relationCodes.containsAll(oppRelationCodes)) {
            relationCodes.removeAll(oppRelationCodes);
        }
        if (oppRelationCodes.containsAll(relationCodes)) {
            oppRelationCodes.removeAll(relationCodes);
        }
        if (CollectionUtils.isEmpty(relationCodes) || CollectionUtils.isEmpty(oppRelationCodes)) {
            return PageInfo.empty();
        }
        List<String> commonCodes = ListUtils.retainAll(relationCodes, oppRelationCodes);
        TempTableCondition relationTempTableCondition = TempUtils.getTempCond(clbrIdRealTempDao, relationCodes, " ");
        TempTableCondition oppRelationTempTableCondition = TempUtils.getTempCond(clbrIdRealTempDao, oppRelationCodes, " ");
        String sql = String.format(sqlBuilder.toString(), this.getWhereSqlByConditon(clbrProcessCondition, paramList, "cl", relationTempTableCondition, oppRelationTempTableCondition, tempGroupIds));
        TempTableCondition relationNotInCondition = null;
        TempTableCondition oppRelationNotInCondition = null;
        if (!CollectionUtils.isEmpty(commonCodes)) {
            relationNotInCondition = TempUtils.getTempCond(clbrIdRealTempDao, commonCodes, " cl.RELATION ", false);
            oppRelationNotInCondition = TempUtils.getTempCond(clbrIdRealTempDao, commonCodes, " cl.OPPRELATION ", false);
            sql = sql + " and (" + relationNotInCondition.getCondition() + " or " + oppRelationNotInCondition.getCondition() + ")";
        }
        sql = sql + " order by cl.CREATETIME DESC ";
        int begin = (clbrProcessCondition.getPageNum() - 1) * clbrProcessCondition.getPageSize();
        int end = clbrProcessCondition.getPageNum() * clbrProcessCondition.getPageSize();
        try {
            count = this.count(sql, paramList);
            if (count == 0) {
                PageInfo pageInfo = PageInfo.empty();
                return pageInfo;
            }
            clbrBills = this.selectEntityByPaging(sql, begin, end, paramList);
        }
        finally {
            clbrIdRealTempDao.deleteIdRealTempByBatchId(relationTempTableCondition.getTempGroupId());
            clbrIdRealTempDao.deleteIdRealTempByBatchId(oppRelationTempTableCondition.getTempGroupId());
            if (null != relationNotInCondition) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(relationNotInCondition.getTempGroupId());
            }
            if (null != oppRelationNotInCondition) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(oppRelationNotInCondition.getTempGroupId());
            }
            tempGroupIds.forEach(clbrIdRealTempDao::deleteIdRealTempByBatchId);
        }
        return PageInfo.of((List)clbrBills, (int)count);
    }

    @Override
    public PageInfo<ClbrBillEO> listClbrBillByClbrCodeAndClbrType(ClbrProcessCondition clbrProcessCondition, ClbrBillTypeEnum clbrBillTypeEnum) {
        String sql = "  SELECT ID,RELATION,OPPRELATION,CLBRTYPE,CURRENCY,AMOUNT,VERIFYEDAMOUNT,USERNAME,CLBRTIME \n  FROM GC_CLBR_BILL  cl  WHERE  cl.CLBRBILLTYPE = ?  and cl.CLBRCODE = ?  AND cl.BILLSTATE=2 \n";
        int begin = (clbrProcessCondition.getPageNum() - 1) * clbrProcessCondition.getPageSize();
        int end = clbrProcessCondition.getPageNum() * clbrProcessCondition.getPageSize();
        int count = this.count(sql, new Object[]{clbrBillTypeEnum.getCode(), clbrProcessCondition.getClbrBillCode()});
        if (count == 0) {
            return PageInfo.empty();
        }
        List clbrBills = this.selectEntityByPaging(sql, begin, end, new Object[]{clbrBillTypeEnum.getCode(), clbrProcessCondition.getClbrBillCode()});
        return PageInfo.of((List)clbrBills, (int)clbrProcessCondition.getPageNum(), (int)clbrProcessCondition.getPageSize(), (int)count);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public Map<ClbrBillStateEnum, Integer> getClbrBillCountByRelationCodeAndBillTypeGroupByBillState(Set<String> codes, Integer clbrBillType) {
        String sql = "  SELECT count(1) BILLCOUNT, min(BILLSTATE) BILLSTATE \n  FROM GC_CLBR_BILL  cl  WHERE 1=1 ";
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
        sql = sql + "  GROUP BY BILLSTATE \n";
        HashMap<ClbrBillStateEnum, Integer> billStateCountMap = new HashMap<ClbrBillStateEnum, Integer>();
        try {
            List datas = this.selectMap(sql, new Object[0]);
            if (!CollectionUtils.isEmpty((Collection)datas)) {
                for (Map map : datas) {
                    Double billCount = Double.valueOf(map.get("BILLCOUNT").toString());
                    billStateCountMap.put(ClbrBillStateEnum.getEnumByCode((Integer)((Integer)map.get("BILLSTATE"))), billCount.intValue());
                }
            }
        }
        finally {
            if (null != relationTempTableCondition) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(relationTempTableCondition.getTempGroupId());
            }
        }
        return billStateCountMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public Map<String, Double> getClbrBillAmountSumByRelationCodeAndBillType(Set<String> codes, Integer clbrBillType) {
        String sql = "  SELECT sum(verifyedAmount) VERIFYEDAMOUNT, sum(noverifyAmount) NOVERIFYAMOUNT \n  FROM GC_CLBR_BILL  cl  WHERE  1=1 ";
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
        HashMap<String, Double> verifyedAmountMap = new HashMap<String, Double>();
        try {
            List datas = this.selectMap(sql, new Object[0]);
            if (!CollectionUtils.isEmpty((Collection)datas)) {
                verifyedAmountMap.put("VERIFYEDAMOUNT", (Double)((Map)datas.get(0)).get("VERIFYEDAMOUNT"));
                verifyedAmountMap.put("NOVERIFYAMOUNT", (Double)((Map)datas.get(0)).get("NOVERIFYAMOUNT"));
            }
        }
        finally {
            if (null != relationTempTableCondition) {
                clbrIdRealTempDao.deleteIdRealTempByBatchId(relationTempTableCondition.getTempGroupId());
            }
        }
        return verifyedAmountMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillEO> listClbrBillDetailsByRelationCodeAndBillType(ClbrDataQueryConditon clbrDataQueryConditon, ClbrBillStateEnum clbrBillStateEnum) {
        List clbrBills;
        int count;
        ClbrIdRealTempDao clbrIdRealTempDao;
        TempTableCondition relationTempTableCondition;
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"cl");
        String sql = "  SELECT  " + allFieldsSQL + "  FROM " + "GC_CLBR_BILL" + "  cl  WHERE %1$s 1=1 ";
        ClbrBillTypeEnum enumByCode = ClbrBillTypeEnum.getEnumByCode((Integer)clbrDataQueryConditon.getClbrBillType());
        if (Objects.nonNull(enumByCode)) {
            sql = sql + " and cl.CLBRBILLTYPE =  " + enumByCode.getCode();
        }
        if (clbrBillStateEnum != null) {
            sql = sql + " AND cl.BILLSTATE= " + clbrBillStateEnum.getCode();
        }
        if ((relationTempTableCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao = ClbrIdRealTempDao.newInstance(), clbrDataQueryConditon.getRelation(), AuthType.ACCESS)) != null) {
            sql = sql + " AND  cl.THISRELATION" + relationTempTableCondition.getCondition();
        }
        if (ClbrBillStateEnum.PARTCONFIRM.equals((Object)clbrBillStateEnum) || ClbrBillStateEnum.CONFIRM.equals((Object)clbrBillStateEnum)) {
            sql = sql + " ORDER BY cl.CLBRTIME ";
        } else if (ClbrBillStateEnum.INIT.equals((Object)clbrBillStateEnum)) {
            sql = sql + " ORDER BY cl.CREATETIME ";
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
        String conditionSql = this.getDataQueryWhereSqlByConditon(clbrDataQueryConditon, paramList, "cl", filterRelationTempTableCondition, filterOppRelationTempTableCondition, initiatorRelationTempTableCondition, receiverRelationTempTableCondition, tempGroupIds, clbrIdRealTempDao);
        String formatSql = String.format(sql, conditionSql);
        int begin = -1;
        int end = -1;
        if (clbrDataQueryConditon.isPageSelect()) {
            begin = (clbrDataQueryConditon.getPageNum() - 1) * clbrDataQueryConditon.getPageSize();
            end = clbrDataQueryConditon.getPageNum() * clbrDataQueryConditon.getPageSize();
        }
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
        return PageInfo.of((List)clbrBills, (int)clbrDataQueryConditon.getPageNum(), (int)clbrDataQueryConditon.getPageSize(), (int)count);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillEO> listAllClbrBillDetailsByRelationCodeAndBillType(ClbrDataQueryConditon clbrDataQueryConditon) {
        ClbrIdRealTempDao clbrIdRealTempDao;
        TempTableCondition relationTempTableCondition;
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"cl");
        StringBuilder billSqlBuilder = new StringBuilder();
        billSqlBuilder.append("  SELECT  ").append(allFieldsSQL).append("  FROM ").append("GC_CLBR_BILL").append("  cl  WHERE %1$s 1=1 ");
        StringBuilder billDeleteSqlBuilder = new StringBuilder();
        billDeleteSqlBuilder.append("  SELECT  ").append(allFieldsSQL).append("  FROM ").append("GC_CLBR_BILL_DELETE").append("  cl  WHERE %2$s ").append("  cl.BILLSTATE= ").append(ClbrBillStateEnum.REJECT.getCode());
        ClbrBillTypeEnum enumByCode = ClbrBillTypeEnum.getEnumByCode((Integer)clbrDataQueryConditon.getClbrBillType());
        if (Objects.nonNull(enumByCode)) {
            billSqlBuilder.append(" and cl.CLBRBILLTYPE = ").append(enumByCode.getCode());
            billDeleteSqlBuilder.append(" and cl.CLBRBILLTYPE = ").append(enumByCode.getCode());
        }
        if ((relationTempTableCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao = ClbrIdRealTempDao.newInstance(), clbrDataQueryConditon.getRelation(), AuthType.ACCESS)) != null) {
            billSqlBuilder.append(" and cl.THISRELATION ").append(relationTempTableCondition.getCondition());
            billDeleteSqlBuilder.append(" and cl.THISRELATION ").append(relationTempTableCondition.getCondition());
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
        String finalSql = billSqlBuilder.append(" UNION ALL ").append((CharSequence)billDeleteSqlBuilder).toString();
        ArrayList<Object> paramList = new ArrayList<Object>();
        ArrayList<String> tempGroupIds = new ArrayList<String>();
        String conditionSql = this.getDataQueryWhereSqlByConditon(clbrDataQueryConditon, paramList, "cl", filterRelationTempTableCondition, filterOppRelationTempTableCondition, initiatorRelationTempTableCondition, receiverRelationTempTableCondition, tempGroupIds, clbrIdRealTempDao);
        ArrayList<Object> allParamList = new ArrayList<Object>();
        allParamList.addAll(paramList);
        allParamList.addAll(paramList);
        String formatSql = String.format(finalSql, conditionSql, conditionSql);
        int begin = (clbrDataQueryConditon.getPageNum() - 1) * clbrDataQueryConditon.getPageSize();
        int end = clbrDataQueryConditon.getPageNum() * clbrDataQueryConditon.getPageSize();
        List clbrBills = new ArrayList();
        int count = 0;
        try {
            count = this.count(formatSql, allParamList);
            if (count == 0) {
                PageInfo pageInfo = PageInfo.empty();
                return pageInfo;
            }
            clbrBills = this.selectEntityByPaging(formatSql, begin, end, allParamList);
        }
        catch (Exception e) {
            log.error("\u534f\u540c\u6570\u636e\u67e5\u8be2\u5f02\u5e38sql={}\uff0c \u5f02\u5e38\u4fe1\u606f={}", (Object)formatSql, (Object)e.getMessage());
            throw new BusinessRuntimeException("\u8bf7\u68c0\u67e5\u3010GC_CLBR_BILL\u3011\u8868\u4e0e\u3010GC_CLBR_BILL_DELETE\u3011\u6269\u5c55\u5b57\u6bb5\u662f\u5426\u4e00\u81f4");
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
        return PageInfo.of(clbrBills, (int)count);
    }

    private String getWhereSqlByConditon(ClbrProcessCondition clbrProcessCondition, List<Object> paramList, String alias, TempTableCondition relationTempTableCondition, TempTableCondition oppRelationTempTableCondition, List<String> tempGroupIds) {
        StringBuffer whereSql = new StringBuffer();
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getClbrBillCode())) {
            whereSql.append(alias).append(".CLBRBILLCODE like '%").append(clbrProcessCondition.getClbrBillCode()).append("%'").append(" AND ");
        }
        if (Objects.isNull(relationTempTableCondition) || Objects.isNull(oppRelationTempTableCondition)) {
            whereSql.append(" 1 = 2 ").append(" AND ");
            return whereSql.toString();
        }
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getRelation()) && !StringUtils.isEmpty((String)relationTempTableCondition.getCondition())) {
            whereSql.append(" ").append(alias).append(".RELATION ").append(relationTempTableCondition.getCondition()).append(" AND ");
        }
        if (!StringUtils.isEmpty((String)clbrProcessCondition.getOppRelation()) && !StringUtils.isEmpty((String)oppRelationTempTableCondition.getCondition())) {
            whereSql.append(" ").append(alias).append(".OPPRELATION ").append(oppRelationTempTableCondition.getCondition()).append(" AND ");
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
        if (Objects.nonNull(clbrProcessCondition.getVerifyedAmountMin())) {
            whereSql.append(" ").append(alias).append(".VERIFYEDAMOUNT >= ?").append(" AND ");
            paramList.add(clbrProcessCondition.getVerifyedAmountMin());
        }
        if (Objects.nonNull(clbrProcessCondition.getVerifyedAmountMax())) {
            whereSql.append(" ").append(alias).append(".VERIFYEDAMOUNT <= ?").append(" AND ");
            paramList.add(clbrProcessCondition.getVerifyedAmountMax());
        }
        if (Objects.nonNull(clbrProcessCondition.getNoverifyAmountMin())) {
            whereSql.append(" ").append(alias).append(".NOVERIFYAMOUNT >= ?").append(" AND ");
            paramList.add(clbrProcessCondition.getNoverifyAmountMin());
        }
        if (Objects.nonNull(clbrProcessCondition.getNoverifyAmountMax())) {
            whereSql.append(" ").append(alias).append(".NOVERIFYAMOUNT <= ?").append(" AND ");
            paramList.add(clbrProcessCondition.getNoverifyAmountMax());
        }
        if (!CollectionUtils.isEmpty((Collection)clbrProcessCondition.getUserCodes())) {
            TempTableCondition conditionOfIds = SqlUtils.getConditionOfIds((Collection)clbrProcessCondition.getUserCodes(), (String)(alias + ".USERNAME"), (SqlUtils.Relation)SqlUtils.Relation.POSITIVE, (boolean)false);
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

    private String getDataQueryWhereSqlByConditon(ClbrDataQueryConditon clbrDataQueryConditon, List<Object> paramList, String alias, TempTableCondition relationTempTableCondition, TempTableCondition oppRelationTempTableCondition, TempTableCondition initiatorRelationTempTableCondition, TempTableCondition receiverRelationTempTableCondition, List<String> tempGroupIds, ClbrIdRealTempDao clbrIdRealTempDao) {
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
            TempTableCondition conditionOfIds = TempUtils.getTempCond(clbrIdRealTempDao, clbrDataQueryConditon.getUserCodes(), alias + ".USERNAME");
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

    @Override
    public List<ClbrBillEO> listByClbrCode(Set<String> clbrCodes) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"tt");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("  select ").append(allFieldsSQL).append(" from ").append("GC_CLBR_BILL").append(" tt");
        sqlBuilder.append(" where tt.billstate != ").append(ClbrBillStateEnum.REJECT.getCode());
        String clbrCoderWhere = SqlUtils.getConditionOfIdsUseOr(clbrCodes, (String)"tt.clbrCode");
        sqlBuilder.append(" and ").append(clbrCoderWhere);
        String querySql = sqlBuilder.toString();
        List clbrBillEOs = this.selectEntity(querySql, new Object[0]);
        return clbrBillEOs;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillEO> listInitiatorByRelation(ClbrBillGenerateQueryDTO clbrBillGenerateQueryDTO) {
        Integer pageNum = clbrBillGenerateQueryDTO.getPageNum();
        Integer pageSize = clbrBillGenerateQueryDTO.getPageSize();
        String clbrType = clbrBillGenerateQueryDTO.getClbrType();
        Set relations = clbrBillGenerateQueryDTO.getRelations();
        Set oppRelations = clbrBillGenerateQueryDTO.getOppRelations();
        String clbrBillCode = clbrBillGenerateQueryDTO.getClbrBillCode();
        String clbrCode = clbrBillGenerateQueryDTO.getClbrCode();
        String remark = clbrBillGenerateQueryDTO.getRemark();
        Double amountMax = clbrBillGenerateQueryDTO.getAmountMax();
        Double amountMin = clbrBillGenerateQueryDTO.getAmountMin();
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"tt");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("  select ").append(allFieldsSQL).append(" from ").append("GC_CLBR_BILL").append(" tt");
        sqlBuilder.append(" where tt.clbrBillType = 0 ");
        if (clbrBillGenerateQueryDTO.isQueryConfirmFlag()) {
            sqlBuilder.append(" and (tt.billState in (2,1) and confirmtype = 1)");
        } else {
            sqlBuilder.append(" and (tt.billState = 0 or (tt.billState = 1 and confirmtype = 1)) ");
        }
        if (!ObjectUtils.isEmpty(clbrType)) {
            ArrayList<String> clbrTypes = new ArrayList<String>(Arrays.asList(clbrType.split(",")));
            String clbrTypeInSql = SqlBuildUtil.getStrInCondi((String)"tt.clbrType", clbrTypes);
            sqlBuilder.append("   and ").append(clbrTypeInSql);
        }
        if (!CollectionUtils.isEmpty((Collection)relations)) {
            String relationSql = SqlUtils.getConditionOfIdsUseOr((Collection)relations, (String)"tt.relation");
            sqlBuilder.append("   and ").append(relationSql);
        }
        if (!CollectionUtils.isEmpty((Collection)oppRelations)) {
            String oppRelationSql = SqlUtils.getConditionOfIdsUseOr((Collection)oppRelations, (String)"tt.oppRelation");
            sqlBuilder.append("   and ").append(oppRelationSql);
        }
        if (!CollectionUtils.isEmpty((Collection)clbrBillGenerateQueryDTO.getUserCodes())) {
            String userNameSql = SqlUtils.getConditionOfIdsUseOr((Collection)clbrBillGenerateQueryDTO.getUserCodes(), (String)"tt.userName");
            sqlBuilder.append("   and ").append(userNameSql);
        }
        if (Objects.nonNull(amountMax)) {
            sqlBuilder.append(" and tt.AMOUNT <= " + amountMax);
        }
        if (Objects.nonNull(amountMin)) {
            sqlBuilder.append(" and tt.AMOUNT >=" + amountMin);
        }
        if (!StringUtils.isEmpty((String)clbrCode)) {
            sqlBuilder.append(" and tt.CLBRCODE ='" + clbrCode + "'");
        }
        if (!StringUtils.isEmpty((String)clbrBillCode)) {
            sqlBuilder.append(" and tt.CLBRBILLCODE like '%" + clbrBillCode + "%'");
        }
        if (!StringUtils.isEmpty((String)remark)) {
            sqlBuilder.append(" and tt.REMARK like '%" + remark + "%'");
        }
        if (clbrBillGenerateQueryDTO.isQueryConfirmFlag()) {
            sqlBuilder.append(" order by  tt.clbrTime desc, tt.syscode, tt.relation, tt.createTime desc ");
        } else {
            sqlBuilder.append(" order by  tt.createTime desc ,tt.syscode, tt.relation ");
        }
        String querySql = sqlBuilder.toString();
        int count = this.count(querySql, new Object[0]);
        if (count == 0) {
            return PageInfo.empty();
        }
        List clbrBillEOS = this.selectEntityByPaging(querySql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[0]);
        return PageInfo.of((List)clbrBillEOS, (int)pageNum, (int)pageSize, (int)count);
    }

    @Override
    public ClbrBillEO queryBySrcId(String srcId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"tt");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("  select ").append(allFieldsSQL).append(" from ").append("GC_CLBR_BILL").append(" tt");
        sqlBuilder.append(" where tt.srcId = ? ");
        String querySql = sqlBuilder.toString();
        List clbrBillEOs = this.selectEntity(querySql, new Object[]{srcId});
        if (CollectionUtils.isEmpty((Collection)clbrBillEOs)) {
            return null;
        }
        return (ClbrBillEO)((Object)clbrBillEOs.get(0));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ClbrBillEO> listBySrcIds(List<String> srcIds) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"tt");
        ClbrIdRealTempDao clbrIdRealTempDao = ClbrIdRealTempDao.newInstance();
        TempTableCondition conditionOfIds = TempUtils.getTempCond(clbrIdRealTempDao, srcIds, "tt.srcId");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("  select ").append(allFieldsSQL).append(" from ").append("GC_CLBR_BILL").append(" tt");
        sqlBuilder.append(" where ");
        sqlBuilder.append(conditionOfIds.getCondition());
        String querySql = sqlBuilder.toString();
        List clbrBillEOs = this.selectEntity(querySql, new Object[0]);
        clbrIdRealTempDao.deleteIdRealTempByBatchId(conditionOfIds.getTempGroupId());
        return clbrBillEOs;
    }

    @Override
    public ClbrBillEO getClbrBillByClbrBillCode(String clbrBillCode) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"t");
        String querySql = "select " + allFieldsSQL + " from " + "GC_CLBR_BILL" + "  t where  t.clbrBillCode= ? ";
        List clbrBills = this.selectEntity(querySql, new Object[]{clbrBillCode});
        if (CollectionUtils.isEmpty((Collection)clbrBills)) {
            return null;
        }
        return (ClbrBillEO)((Object)clbrBills.get(0));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public PageInfo<ClbrBillEO> queryArbitrationListPage(ClbrArbitrationQueryParamDTO clbrArbitrationQueryParamDTO) {
        Integer pageNum = clbrArbitrationQueryParamDTO.getPageNum();
        Integer pageSize = clbrArbitrationQueryParamDTO.getPageSize();
        String clbrType = clbrArbitrationQueryParamDTO.getClbrType();
        String relation = clbrArbitrationQueryParamDTO.getRelation();
        String oppRelation = clbrArbitrationQueryParamDTO.getOppRelation();
        String billCode = clbrArbitrationQueryParamDTO.getBillCode();
        String arbitrationUserName = clbrArbitrationQueryParamDTO.getArbitrationUserName();
        String sysCode = clbrArbitrationQueryParamDTO.getSysCode();
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"tt");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("  select ").append(allFieldsSQL).append(" from ").append("GC_CLBR_BILL").append(" tt");
        sqlBuilder.append(" where tt.billState = 6 ");
        if (!ObjectUtils.isEmpty(clbrType)) {
            ArrayList<String> clbrTypes = new ArrayList<String>(Arrays.asList(clbrType.split(",")));
            String clbrTypeInSql = SqlBuildUtil.getStrInCondi((String)"tt.clbrType", clbrTypes);
            sqlBuilder.append("   and ").append(clbrTypeInSql);
        }
        ClbrIdRealTempDao clbrIdRealTempDao = ClbrIdRealTempDao.newInstance();
        TempTableCondition relationsCondition = null;
        TempTableCondition oppRelationsCondition = null;
        if (!ObjectUtils.isEmpty(sysCode)) {
            relationsCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrArbitrationQueryParamDTO.getRelation(), AuthType.NONE);
            if (Objects.isNull(relationsCondition) || Objects.isNull(relationsCondition.getCondition())) {
                sqlBuilder.append(" and 1 = 2 ");
            } else {
                sqlBuilder.append(" and relation ").append(relationsCondition.getCondition());
            }
            AuthType authType = AuthType.ACCESS;
            if (!StringUtils.isEmpty((String)sysCode)) {
                authType = AuthType.WRITE;
            }
            if (Objects.isNull(oppRelationsCondition = RelationUtils.getTempTableCondition(clbrIdRealTempDao, clbrArbitrationQueryParamDTO.getOppRelation(), authType)) || Objects.isNull(oppRelationsCondition.getCondition())) {
                sqlBuilder.append(" and 1 = 2 ");
            } else {
                sqlBuilder.append(" and opprelation ").append(oppRelationsCondition.getCondition());
            }
        } else {
            if (!ObjectUtils.isEmpty(relation)) {
                ArrayList<String> relations = new ArrayList<String>(Arrays.asList(relation.split(",")));
                String relationInSql = SqlBuildUtil.getStrInCondi((String)"tt.relation", relations);
                sqlBuilder.append("   and ").append(relationInSql);
            }
            if (!ObjectUtils.isEmpty(oppRelation)) {
                ArrayList<String> oppRelations = new ArrayList<String>(Arrays.asList(oppRelation.split(",")));
                String oppRelationsInSql = SqlBuildUtil.getStrInCondi((String)"tt.oppRelation", oppRelations);
                sqlBuilder.append("   and ").append(oppRelationsInSql);
            }
        }
        if (!ObjectUtils.isEmpty(arbitrationUserName)) {
            sqlBuilder.append("   and (tt.arbitrationUserName ='" + arbitrationUserName + "'");
            sqlBuilder.append("   or tt.arbitrationUserName like '" + arbitrationUserName + ",%'");
            sqlBuilder.append("   or tt.arbitrationUserName like '%," + arbitrationUserName + ",%'");
            sqlBuilder.append("   or tt.arbitrationUserName like '%," + arbitrationUserName + "')");
        }
        if (!ObjectUtils.isEmpty(billCode)) {
            sqlBuilder.append("   and tt.clbrBillCode like '%" + billCode + "%'");
        }
        String querySql = sqlBuilder.toString();
        int count = this.count(querySql, new Object[0]);
        List clbrBillEOS = this.selectEntityByPaging(querySql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[0]);
        if (Objects.nonNull(relationsCondition)) {
            clbrIdRealTempDao.deleteIdRealTempByBatchId(relationsCondition.getTempGroupId());
        }
        if (Objects.nonNull(oppRelationsCondition)) {
            clbrIdRealTempDao.deleteIdRealTempByBatchId(oppRelationsCondition.getTempGroupId());
        }
        return PageInfo.of((List)clbrBillEOS, (int)pageNum, (int)pageSize, (int)count);
    }

    @Override
    public List<ClbrBillEO> selectArbitrationTodoNum(String arbitrationUserName) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"tt");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("  select ").append(allFieldsSQL).append(" from ").append("GC_CLBR_BILL").append(" tt");
        sqlBuilder.append(" where tt.billState = 6 ");
        if (!ObjectUtils.isEmpty(arbitrationUserName)) {
            sqlBuilder.append("   and tt.arbitrationUserName like '%" + arbitrationUserName + "%'");
        }
        String querySql = sqlBuilder.toString();
        List clbrBillEOS = this.selectEntity(querySql, new Object[0]);
        return clbrBillEOS;
    }

    @Override
    public List<ClbrBillEO> selectUnClbrReceBillCode(Set<String> clbrBillIds) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from ").append("GC_CLBR_BILL").append(" t");
        sqlBuilder.append(" where t.unClbrReceBillCode is not null and t.unClbrReceBillCode!= '' ");
        String clbrCoderWhere = SqlUtils.getConditionOfIdsUseOr(clbrBillIds, (String)"t.id");
        sqlBuilder.append(" and ").append(clbrCoderWhere);
        String querySql = sqlBuilder.toString();
        List clbrBillEOs = this.selectEntity(querySql, new Object[0]);
        return clbrBillEOs;
    }

    private Set<String> getRelationCodesCondition(String code, AuthType authType) {
        List baseDatas;
        Set<String> codes = null;
        if (StringUtils.isEmpty((String)code) && !CollectionUtils.isEmpty((Collection)(baseDatas = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_RELATION", authType)))) {
            codes = baseDatas.stream().map(GcBaseData::getCode).collect(Collectors.toSet());
            return codes;
        }
        GcBaseData baseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_RELATION", code);
        if (null == baseData) {
            return null;
        }
        List baseDatas2 = GcBaseDataCenterTool.getInstance().queryAllWithSelfItemsByParentid("MD_RELATION", code, authType);
        if (!CollectionUtils.isEmpty((Collection)baseDatas2)) {
            codes = baseDatas2.stream().map(GcBaseData::getCode).collect(Collectors.toSet());
            return codes;
        }
        return null;
    }

    @Override
    public List<ClbrBillEO> queryBillMessage(ClbrBillReceBillCodeDTO clbrBillReceBillCodeDTO) {
        Set sendBillIdList = clbrBillReceBillCodeDTO.getSendBillIdList();
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CLBR_BILL", (String)"t");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("  select ").append(allFieldsSQL).append(" from ").append("GC_CLBR_BILL").append(" t");
        sqlBuilder.append(" where t.CLBRBILLTYPE = 0 ");
        if (!CollectionUtils.isEmpty((Collection)sendBillIdList)) {
            String relationSql = SqlUtils.getConditionOfIdsUseOr((Collection)sendBillIdList, (String)"t.SRCID");
            sqlBuilder.append("   and ").append(relationSql);
        }
        String querySql = sqlBuilder.toString();
        List eos = this.selectEntity(querySql, new Object[0]);
        return eos;
    }
}

