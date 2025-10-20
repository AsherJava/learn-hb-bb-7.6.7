/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.entity.FloatBalanceEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.offsetitem.dao.impl.OffsetQuerySqlBuilder
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition
 *  com.jiuqi.gcreport.onekeymerge.vo.QueryCondition
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.rewritesetting.consts.RewriteSettingConst$FieldMappingEnum
 *  com.jiuqi.gcreport.rewritesetting.dto.RewriteFieldMappingDTO
 *  com.jiuqi.gcreport.rewritesetting.entity.RewriteSettingEO
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.onekeymerge.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.entity.FloatBalanceEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.offsetitem.dao.impl.OffsetQuerySqlBuilder;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.onekeymerge.service.FloatBalanceDiffService;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition;
import com.jiuqi.gcreport.onekeymerge.vo.QueryCondition;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.rewritesetting.consts.RewriteSettingConst;
import com.jiuqi.gcreport.rewritesetting.dto.RewriteFieldMappingDTO;
import com.jiuqi.gcreport.rewritesetting.entity.RewriteSettingEO;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

public class GcDiffProcess {
    private ConsolidatedSubjectService subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
    private GcOffSetAppOffsetService offSetItemAdjustService = (GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class);
    private FloatBalanceDiffService floatBalanceDiffService = (FloatBalanceDiffService)SpringContextUtils.getBean(FloatBalanceDiffService.class);
    private GcOffSetItemAdjustCoreService offsetCoreService = (GcOffSetItemAdjustCoreService)SpringContextUtils.getBean(GcOffSetItemAdjustCoreService.class);

    protected List<String> getNumberAndGatherFiledCodes(String tableCode, List<String> currTableAllFieldCodes) {
        return this.getNumberAndGatherFiledCodes(tableCode, currTableAllFieldCodes, null);
    }

    protected List<String> getNumberAndGatherFiledCodes(String tableCode, List<String> currTableAllFieldCodes, Map<String, String> fieldDefaultValueMap) {
        List<String> numberAndGatherFiledCodes = new ArrayList<String>();
        if (StringUtils.isEmpty((String)tableCode)) {
            return numberAndGatherFiledCodes;
        }
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        try {
            TableModelDefine tableDefine = dataModelService.getTableModelDefineByName(tableCode);
            if (tableDefine == null) {
                return numberAndGatherFiledCodes;
            }
            List columnModels = dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            if (CollectionUtils.isEmpty((Collection)columnModels)) {
                return numberAndGatherFiledCodes;
            }
            if (null != currTableAllFieldCodes) {
                currTableAllFieldCodes.addAll(columnModels.stream().map(ColumnModelDefine::getName).collect(Collectors.toList()));
            }
            if (null != fieldDefaultValueMap) {
                fieldDefaultValueMap.putAll(columnModels.stream().filter(fieldDefine -> !StringUtils.isEmpty((String)fieldDefine.getDefaultValue())).collect(Collectors.toMap(ColumnModelDefine::getName, ColumnModelDefine::getDefaultValue, (v1, v2) -> v1)));
            }
            numberAndGatherFiledCodes = columnModels.stream().filter(field -> this.isNumberAndGatherType((ColumnModelDefine)field)).map(ColumnModelDefine::getName).collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u6309\u8bb0\u8d26\u5355\u4f4d\u3001\u5bf9\u65b9\u5355\u4f4d\u3001\u79d1\u76ee\u6c47\u603b\u76f4\u63a5\u4e0b\u7ea7\u6d6e\u52a8\u4f59\u989d\u8868\u6570\u636e\u5f02\u5e38", (Throwable)e);
        }
        return numberAndGatherFiledCodes;
    }

    protected boolean isNumberAndGatherType(ColumnModelDefine columnModel) {
        ColumnModelType fieldType = columnModel.getColumnType();
        boolean isNumber = fieldType == ColumnModelType.DOUBLE || fieldType == ColumnModelType.INTEGER || fieldType == ColumnModelType.BIGDECIMAL;
        FieldGatherType gatherType = columnModel.getAggrType() == null ? FieldGatherType.FIELD_GATHER_NONE : FieldGatherType.forValue((int)columnModel.getAggrType().getValue());
        boolean isGatherType = gatherType != null && !FieldGatherType.FIELD_GATHER_NONE.equals((Object)gatherType);
        return isNumber && isGatherType;
    }

    protected Map<String, BigDecimal> sumGroupFloatBalance(List<Map<String, Object>> floatBalanceEOList, List<String> numberAndGatherFiledCodes) {
        HashMap<String, BigDecimal> sumFieldsMap = new HashMap<String, BigDecimal>();
        for (Map<String, Object> fields : floatBalanceEOList) {
            for (String fieldCode : numberAndGatherFiledCodes) {
                String fieldValue = fields.get(fieldCode) == null ? "0" : fields.get(fieldCode).toString();
                BigDecimal sumFiledValue = sumFieldsMap.containsKey(fieldCode) ? ((BigDecimal)sumFieldsMap.get(fieldCode)).add(new BigDecimal(fieldValue)) : BigDecimal.ZERO.add(new BigDecimal(fieldValue));
                sumFieldsMap.put(fieldCode, sumFiledValue);
            }
        }
        return sumFieldsMap;
    }

    protected boolean isNeedRewritre(FloatBalanceEO floatBalanceEO, String diffUnitId) {
        return !floatBalanceEO.getMdOrg().equals(diffUnitId) && !StringUtils.isEmpty((String)floatBalanceEO.getAcctOrgCode()) && !StringUtils.isEmpty((String)floatBalanceEO.getOppUnitCode()) && !StringUtils.isEmpty((String)floatBalanceEO.getSubjectCode());
    }

    protected boolean isNeedRewritre(Map item, String diffUnitId) {
        return !item.get("MDCODE").equals(diffUnitId) && !StringUtils.isEmpty((String)((String)item.get("ACCTORGCODE"))) && !StringUtils.isEmpty((String)((String)item.get("OPPUNITCODE"))) && !StringUtils.isEmpty((String)((String)item.get("SUBJECTCODE")));
    }

    protected boolean isNeedRewritre(Map item, String diffUnitId, List<String> summaryColumnCodes) {
        if (item.get("MDCODE").equals(diffUnitId)) {
            return false;
        }
        for (String columnCode : summaryColumnCodes) {
            if (!StringUtils.isEmpty((String)((String)item.get(columnCode)))) continue;
            return false;
        }
        return true;
    }

    protected List<Map<String, Object>> getOffsetSumData(GcActionParamsVO paramsVO, String hbUnitId, List<String> finishCalcRuleIds) {
        QueryCondition queryCondition = OneKeyMergeUtils.buildQueryCondition(paramsVO, hbUnitId);
        QueryParamsVO queryParamsVO = this.covertQueryParamsVO(queryCondition);
        queryParamsVO.setSchemeId(queryCondition.getSchemeID());
        List<Map<String, Object>> sumOffsetDataByOppunitId = this.sumOffsetValueGroupByOppunitCode(queryParamsVO, finishCalcRuleIds);
        if (null == sumOffsetDataByOppunitId) {
            sumOffsetDataByOppunitId = new ArrayList<Map<String, Object>>();
        }
        return sumOffsetDataByOppunitId;
    }

    private QueryParamsVO covertQueryParamsVO(QueryCondition condition) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        queryParamsVO.setAcctPeriod(condition.getAcctPeriod());
        queryParamsVO.setAcctYear(condition.getAcctYear());
        queryParamsVO.setOrgId(condition.getOrgid());
        queryParamsVO.setCurrency(condition.getCurrency());
        queryParamsVO.setElmModes(condition.getElmModes());
        queryParamsVO.setFilterCondition(condition.getFilterCondition());
        if (!queryParamsVO.getOrgId().equals(condition.getOppUnitId())) {
            LinkedList<String> uuids = new LinkedList<String>();
            uuids.add(condition.getOppUnitId());
            queryParamsVO.setOppUnitIdList(uuids);
        }
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (int)condition.getAcctYear(), (int)condition.getPeriodType(), (int)condition.getAcctPeriod());
        condition.setPeriodStr(yearPeriodUtil.toString());
        queryParamsVO.setOrgType(condition.getOrg_type());
        queryParamsVO.setPageSize(-1);
        queryParamsVO.setPageNum(-1);
        queryParamsVO.setTaskId(condition.getTaskID());
        if (!queryParamsVO.getOrgId().equals(condition.getUnitId())) {
            LinkedList<String> unitIdList = new LinkedList<String>();
            unitIdList.add(condition.getUnitId());
            queryParamsVO.setUnitIdList(unitIdList);
        }
        ArrayList<String> showColumns = new ArrayList<String>();
        showColumns.add("OFFSETSRCTYPE");
        List otherColumnKeys = condition.getOtherShowColumnKeys();
        if (otherColumnKeys != null && otherColumnKeys.size() > 0) {
            showColumns.addAll(otherColumnKeys);
        }
        queryParamsVO.setOtherShowColumns(showColumns);
        queryParamsVO.setPeriodStr(condition.getPeriodStr());
        queryParamsVO.setArbitrarilyMerge(condition.getArbitrarilyMerge());
        queryParamsVO.setOrgBatchId(condition.getOrgBatchId());
        queryParamsVO.setOrgComSupLength(condition.getOrgComSupLength());
        return queryParamsVO;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected List<GcOffSetVchrItemAdjustEO> listRewriteOffsets(GcActionParamsVO paramsVO, String hbUnitId, List<String> finishCalcRuleIds) {
        QueryCondition queryCondition = OneKeyMergeUtils.buildQueryCondition(paramsVO, hbUnitId);
        queryCondition.getFilterCondition().put("ruleId", finishCalcRuleIds);
        QueryParamsVO queryParamsVO = this.covertQueryParamsVO(queryCondition);
        queryParamsVO.setSchemeId(queryCondition.getSchemeID());
        ArrayList params = new ArrayList();
        List ruleIds = (List)queryParamsVO.getFilterCondition().get("ruleId");
        queryParamsVO.getFilterCondition().remove("ruleId");
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        String mergeUnitRangeSql = this.offsetCoreService.mergeUnitRangeSql(queryParamsDTO, params);
        String selectFields = SqlUtils.getColumnsSqlByTableDefine((String)"GC_OFFSETVCHRITEM", (String)"record");
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(mergeUnitRangeSql);
        sql.append(" and (").append(SqlUtils.getConditionOfIdsUseOr((Collection)ruleIds, (String)"record.RULEID"));
        sql.append(" or record.offSetSrcType = ").append(OffSetSrcTypeEnum.SUBJECT_RECLASSIFY.getSrcTypeValue()).append(")\n");
        sql.append("order by record.mrecid desc,record.SUBJECTORIENT desc\n");
        try {
            List list = EntNativeSqlDefaultDao.newInstance((String)"GC_OFFSETVCHRITEM", GcOffSetVchrItemAdjustEO.class).selectEntity(sql.toString(), params);
            return list;
        }
        finally {
            queryParamsDTO.getTempGroupIdList().forEach(IdTemporaryTableUtils::deteteByGroupId);
        }
    }

    protected BigDecimal getGroupOutsideSumAmt(List<Map<String, Object>> offsetSumData) {
        BigDecimal sumAmt = BigDecimal.ZERO;
        for (Map<String, Object> sumData : offsetSumData) {
            BigDecimal creditValue = BigDecimal.valueOf(sumData.get("CREDITVALUE") == null ? 0.0 : (Double)sumData.get("CREDITVALUE"));
            BigDecimal debitValue = BigDecimal.valueOf(sumData.get("DEBITVALUE") == null ? 0.0 : (Double)sumData.get("DEBITVALUE"));
            BigDecimal amt = BigDecimal.ZERO.add(debitValue).subtract(creditValue);
            int subjectOrient = (Integer)sumData.get("SUBJECTORIENT");
            sumAmt = sumAmt.add(amt.multiply(new BigDecimal(subjectOrient)));
        }
        return sumAmt.setScale(2, 4);
    }

    protected Set<String> getSubject2TableNameMap(List<RewriteSettingEO> rewriteSettings, String reportSystemId) {
        HashSet<String> allRelateSubjectCodes = new HashSet<String>();
        for (RewriteSettingEO eo : rewriteSettings) {
            String subjectCode = eo.getSubjectCode();
            allRelateSubjectCodes.add(subjectCode);
            allRelateSubjectCodes.addAll(this.subjectService.listAllChildrenCodes(subjectCode, reportSystemId));
        }
        return allRelateSubjectCodes;
    }

    protected void batchDeleteAllOutsideTableDatas(GcActionParamsVO paramsVO, String diffUnitId, String oppUnitTitle, Map<String, List<RewriteSettingEO>> floatBalacesMap) {
        for (Map.Entry<String, List<RewriteSettingEO>> entry : floatBalacesMap.entrySet()) {
            RewriteSettingEO rewriteSettingEO = entry.getValue().get(0);
            if (StringUtils.isEmpty((String)rewriteSettingEO.getOutsideTableName())) continue;
            String oppUnitCode = "oppunittitle";
            Map<String, String> outsideFieldMapping = this.getFieldMappingByType(rewriteSettingEO, RewriteSettingConst.FieldMappingEnum.OUTSIDE.getCode());
            if (!ObjectUtils.isEmpty(outsideFieldMapping)) {
                oppUnitCode = outsideFieldMapping.get("OPPUNITID");
            }
            GcDiffProcessCondition condition = new GcDiffProcessCondition();
            BeanUtils.copyProperties(paramsVO, condition);
            this.floatBalanceDiffService.batchDeleteAllBalanceByOppunitTitle(diffUnitId, oppUnitCode, oppUnitTitle, this.getTableNameByCode(rewriteSettingEO.getOutsideTableName()), condition);
        }
    }

    private Map<String, String> getFieldMappingByType(RewriteSettingEO eo, String type) {
        return Optional.ofNullable(eo.getFieldMapping()).map(fieldMappingStr -> (List)JsonUtils.readValue((String)fieldMappingStr, (TypeReference)new TypeReference<List<RewriteFieldMappingDTO>>(){})).map(fieldMappings -> fieldMappings.stream().collect(Collectors.groupingBy(RewriteFieldMappingDTO::getType))).map(typeMap -> (List)typeMap.get(type)).orElseGet(Collections::emptyList).stream().collect(Collectors.toMap(RewriteFieldMappingDTO::getOffsetField, RewriteFieldMappingDTO::getZbField, (existing, replacement) -> replacement));
    }

    public List<Map<String, Object>> sumOffsetValueGroupByOppunitCode(QueryParamsVO queryParamsVO, List<String> finishCalcRuleIds) {
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        String offsetValueStr = "\nsum(record.offset_DEBIT) as debitValue,sum(record.offset_Credit) as creditValue ";
        String selectFields = " record.unitid,record.oppUnitId,record.subjectCode,record.sjbz,record.subjectOrient,(case when record.offsetSrcType=30 or record.offsetSrcType=26 then 1 else 0 end) as offsetSrcType," + offsetValueStr;
        String groupStr = " record.unitid,record.oppUnitId,record.subjectCode,record.sjbz,record.subjectOrient,(case when record.offsetSrcType=30 or record.offsetSrcType=26 then 1 else 0 end)";
        ArrayList params = new ArrayList();
        StringBuilder whereSql = new StringBuilder(32);
        whereSql.append(" 1 = 1 \n");
        OffsetQuerySqlBuilder sqlBuilder = OffsetQuerySqlBuilder.builder((QueryParamsDTO)queryParamsDTO);
        sqlBuilder.buildMergeUnitCondition(whereSql, params);
        if (whereSql.length() < 1) {
            return new ArrayList<Map<String, Object>>();
        }
        sqlBuilder.buildOtherCondition(whereSql, params);
        if (!CollectionUtils.isEmpty(finishCalcRuleIds)) {
            whereSql.append(" and (").append(SqlUtils.getConditionOfIdsUseOr(finishCalcRuleIds, (String)"record.RULEID"));
            whereSql.append(" or record.offSetSrcType = ").append(OffSetSrcTypeEnum.SUBJECT_RECLASSIFY.getSrcTypeValue()).append(")\n");
        }
        StringBuilder sql = new StringBuilder(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_OFFSETVCHRITEM").append("  record \n");
        sqlBuilder.buildOrgTableJoin(sql);
        sql.append(" where ").append((CharSequence)whereSql);
        sql.append("group by ").append(groupStr).append(" \n");
        return EntNativeSqlDefaultDao.newInstance((String)"GC_OFFSETVCHRITEM", GcOffSetVchrItemAdjustEO.class).selectMap(sql.toString(), params);
    }

    public String getTableNameByCode(String tableCode) {
        if (StringUtils.isEmpty((String)tableCode)) {
            return tableCode;
        }
        TableModelDefine tableModelDefine = ((DataModelService)SpringContextUtils.getBean(DataModelService.class)).getTableModelDefineByCode(tableCode);
        if (tableModelDefine == null) {
            return tableCode;
        }
        return tableModelDefine.getName();
    }
}

