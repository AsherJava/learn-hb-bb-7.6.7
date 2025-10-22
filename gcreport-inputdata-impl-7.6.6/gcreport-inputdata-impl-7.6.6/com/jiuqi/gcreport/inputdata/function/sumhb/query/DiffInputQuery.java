/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.query;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.inputdata.function.sumhb.dao.SumhbTempDao;
import com.jiuqi.gcreport.inputdata.function.sumhb.query.AbstractInputQuery;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.util.ObjectUtils;

public class DiffInputQuery
extends AbstractInputQuery {
    private final int nextLevelOrgParentsLength;
    private final GcOrgCacheVO mergeUnit;

    public DiffInputQuery(QueryContext queryContext, String tableName, Collection<String> calFieldName, Collection<String> noCalcFieldNames, Map<String, String> dims, SumhbTempDao sumhbTempDao) {
        this.init(queryContext, tableName, calFieldName, noCalcFieldNames, dims, sumhbTempDao);
        this.mergeUnit = this.orgCenterTool.getMergeUnitByDifference(dims.get("MDCODE"));
        this.nextLevelOrgParentsLength = this.mergeUnit.getGcParentStr().length() + this.orgCodeLength + 1;
    }

    @Override
    public List<Map<String, Object>> query() {
        this.params = new ArrayList();
        String inputSql = this.buildInputQuery();
        String offsetSql = this.buildOffsetQuery();
        String querySql = inputSql + "\n union all \n" + offsetSql;
        return EntNativeSqlDefaultDao.getInstance().selectMap(querySql, this.params);
    }

    private String buildInputQuery() {
        String detailSql = this.buildInputDetailSql();
        String sumSql = this.buildInputSumSql();
        return this.concatSql(detailSql, sumSql);
    }

    private String buildInputDetailSql() {
        if (ObjectUtils.isEmpty(this.sumhbTempDao.getBatchId())) {
            return "";
        }
        String sql = "select %1$s,temp.FORMID GROUPXYZ,'0' SUMXYZ from " + this.inputTableName + " i \n inner join " + this.orgCategory + " org1 on org1.code=i." + "MDCODE" + "    and org1.validtime<=date'" + this.normalDate + "' and org1.invalidtime>=date'" + this.normalDate + "' and org1.orgtypeid = i.md_gcorgtype  \n inner join " + this.orgCategory + " org2 on org2.code=i.oppunitid   and org2.validtime<=date'" + this.normalDate + "' and org2.invalidtime>=date'" + this.normalDate + "' and org2.orgtypeid = i.md_gcorgtype  \n inner join " + this.sumhbTempDao.getTableName() + " temp on i.subjectCode = temp.subjectCode and temp.batchId=?  where org1.GCPARENTS like '" + this.mergeUnit.getGcParentStr() + "%%' \n   and org2.GCPARENTS like '" + this.mergeUnit.getGcParentStr() + "%%' \n   and substr(org1.GCPARENTS,1," + this.nextLevelOrgParentsLength + ") <> substr(org2.GCPARENTS,1," + this.nextLevelOrgParentsLength + ") \n   and i.offsetstate='1'    and i.reportSystemId = ? \n   %2$s";
        StringBuilder queryFields = new StringBuilder();
        this.calcFieldName.forEach(fieldName -> {
            if ("amt".equalsIgnoreCase((String)fieldName)) {
                queryFields.append("(case when i.OFFSETSTATE='1' then -i.OFFSETAMT else -i.AMT end) ").append((String)fieldName).append(",");
            } else {
                queryFields.append("-i.").append((String)fieldName).append(" ").append((String)fieldName).append(",");
            }
        });
        this.noCalcFieldNames.forEach(fieldName -> queryFields.append("i.").append((String)fieldName).append(","));
        queryFields.deleteCharAt(queryFields.length() - 1);
        this.params.add(this.sumhbTempDao.getBatchId());
        this.params.add(this.systemId);
        StringBuilder conditionBuilder = new StringBuilder();
        this.dims.entrySet().stream().filter(entry -> !"MDCODE".equals(entry.getKey()) && !"MD_GCORGTYPE".equals(entry.getKey())).forEach(entry -> {
            conditionBuilder.append(" and i.").append((String)entry.getKey()).append("=?");
            this.params.add(entry.getValue());
        });
        return String.format(sql, queryFields, conditionBuilder);
    }

    private String buildInputSumSql() {
        if (ObjectUtils.isEmpty(this.sumhbTempDao.getSumBatchId())) {
            return "";
        }
        String sql = "select %1$s,max(temp.FORMID) GROUPXYZ,'1' SUMXYZ from " + this.inputTableName + " isu \n inner join " + this.orgCategory + " org1 on org1.code=isu." + "MDCODE" + "    and org1.validtime<=date'" + this.normalDate + "' and org1.invalidtime>=date'" + this.normalDate + "' and org1.orgtypeid = isu.md_gcorgtype  \n inner join " + this.orgCategory + " org2 on org2.code=isu.oppunitid   and org2.validtime<=date'" + this.normalDate + "' and org2.invalidtime>=date'" + this.normalDate + "' and org2.orgtypeid = isu.md_gcorgtype  \n inner join " + this.sumhbTempDao.getTableName() + " temp on isu.subjectCode = temp.subjectCode and temp.batchId=?  where org1.GCPARENTS like '" + this.mergeUnit.getGcParentStr() + "%%' \n   and org2.GCPARENTS like '" + this.mergeUnit.getGcParentStr() + "%%' \n   and substr(org1.GCPARENTS,1," + this.nextLevelOrgParentsLength + ") <> substr(org2.GCPARENTS,1," + this.nextLevelOrgParentsLength + ") \n   and isu.offsetstate='1'    and isu.reportSystemId = ? \n   %2$s \n group by temp.FORMID";
        StringBuilder queryFields = new StringBuilder();
        this.calcFieldName.forEach(fieldName -> {
            if ("amt".equalsIgnoreCase((String)fieldName)) {
                queryFields.append("sum(case when isu.OFFSETSTATE='1' then -isu.OFFSETAMT else -isu.AMT end) ").append((String)fieldName).append(",");
            } else {
                queryFields.append("sum(-isu.").append((String)fieldName).append(") ").append((String)fieldName).append(",");
            }
        });
        this.noCalcFieldNames.forEach(fieldName -> queryFields.append("max(isu.").append((String)fieldName).append(") ").append((String)fieldName).append(","));
        queryFields.deleteCharAt(queryFields.length() - 1);
        this.params.add(this.sumhbTempDao.getSumBatchId());
        this.params.add(this.systemId);
        StringBuilder conditionBuilder = new StringBuilder();
        this.dims.entrySet().stream().filter(entry -> !"MDCODE".equals(entry.getKey()) && !"MD_GCORGTYPE".equals(entry.getKey())).forEach(entry -> {
            conditionBuilder.append(" and isu.").append((String)entry.getKey()).append("=?");
            this.params.add(entry.getValue());
        });
        return String.format(sql, queryFields, conditionBuilder);
    }

    private String buildOffsetQuery() {
        String detailSql = this.buildOffsetDetailSql();
        String sumSql = this.buildOffsetSumSql();
        return this.concatSql(detailSql, sumSql);
    }

    private String buildOffsetDetailSql() {
        if (ObjectUtils.isEmpty(this.sumhbTempDao.getBatchId())) {
            return "";
        }
        String sql = "select %1$s,temp.FORMID GROUPXYZ,'0' SUMXYZ from GC_OFFSETVCHRITEM v \n inner join " + this.orgCategory + " org1 on org1.code=v.unitId    and org1.validtime<=date'" + this.normalDate + "' and org1.invalidtime>=date'" + this.normalDate + "' \n inner join " + this.orgCategory + " org2 on org2.code=v.oppunitid   and org2.validtime<=date'" + this.normalDate + "' and org2.invalidtime>=date'" + this.normalDate + "' \n inner join " + this.sumhbTempDao.getTableName() + " temp on v.subjectCode = temp.subjectCode and temp.batchId=?  where org1.GCPARENTS like '" + this.mergeUnit.getGcParentStr() + "%%' \n   and org2.GCPARENTS like '" + this.mergeUnit.getGcParentStr() + "%%' \n   and substr(org1.GCPARENTS,1," + this.nextLevelOrgParentsLength + ") <> substr(org2.GCPARENTS,1," + this.nextLevelOrgParentsLength + ") \n   and v.offsetSrcType in  (31, 30, 0, 26, 21) \n   and v.disableFlag <> '1' \n   and v.systemId = ? \n   and v.md_gcorgtype in('NONE','" + (String)this.dims.get("MD_GCORGTYPE") + "') \n   %2$s";
        String currencyCode = (String)this.dims.get("MD_CURRENCY");
        StringBuilder queryFields = new StringBuilder();
        this.calcFieldName.forEach(fieldName -> {
            if ("amt".equalsIgnoreCase((String)fieldName)) {
                queryFields.append("v.subjectOrient*(v.offset_debit-v.offset_credit) ").append((String)fieldName).append(",");
            } else if (!CollectionUtils.isEmpty((Collection)this.managementDimCodes) && this.managementDimCodes.contains(fieldName)) {
                queryFields.append("v.").append((String)fieldName).append(" ").append((String)fieldName).append(",");
            } else {
                queryFields.append("0 ").append((String)fieldName).append(",");
            }
        });
        Map<String, String> input2OffsetFields = this.getInputAndOffsetFieldMapping("v");
        this.noCalcFieldNames.forEach(fieldName -> queryFields.append((String)input2OffsetFields.get(fieldName.toUpperCase())).append(" ").append((String)fieldName).append(","));
        queryFields.deleteCharAt(queryFields.length() - 1);
        this.params.add(this.sumhbTempDao.getBatchId());
        this.params.add(this.systemId);
        String condition = " and v.DATATIME=? ";
        this.params.add(this.dims.get("DATATIME"));
        if (DimensionUtils.isExistAdjust((String)this.taskDefine.getKey())) {
            condition = condition + " and v.ADJUST=? ";
            this.params.add(this.dims.get("ADJUST"));
        }
        condition = condition + " and v.OFFSETCURR =? ";
        this.params.add(this.dims.get("MD_CURRENCY"));
        return String.format(sql, queryFields, condition);
    }

    private String buildOffsetSumSql() {
        if (ObjectUtils.isEmpty(this.sumhbTempDao.getSumBatchId())) {
            return "";
        }
        String sql = "select %1$s,max(temp.FORMID) GROUPXYZ,'1' SUMXYZ from GC_OFFSETVCHRITEM vsu \n inner join " + this.orgCategory + " org1 on org1.code=vsu.unitId    and org1.validtime<=date'" + this.normalDate + "' and org1.invalidtime>=date'" + this.normalDate + "' \n inner join " + this.orgCategory + " org2 on org2.code=vsu.oppunitid   and org2.validtime<=date'" + this.normalDate + "' and org2.invalidtime>=date'" + this.normalDate + "' \n inner join " + this.sumhbTempDao.getTableName() + " temp on vsu.subjectCode = temp.subjectCode and temp.batchId=?  where org1.GCPARENTS like '" + this.mergeUnit.getGcParentStr() + "%%' \n   and org2.GCPARENTS like '" + this.mergeUnit.getGcParentStr() + "%%' \n   and substr(org1.GCPARENTS,1," + this.nextLevelOrgParentsLength + ") <> substr(org2.GCPARENTS,1," + this.nextLevelOrgParentsLength + ") \n   and vsu.offsetSrcType in  (31, 30, 0, 26, 21) \n   and vsu.disableFlag <> '1' \n   and vsu.systemId = ? \n   and vsu.md_gcorgtype in('NONE','" + (String)this.dims.get("MD_GCORGTYPE") + "') \n   %2$s group by temp.FORMID";
        String currencyCode = (String)this.dims.get("MD_CURRENCY");
        StringBuilder queryFields = new StringBuilder();
        this.calcFieldName.forEach(fieldName -> {
            if ("amt".equalsIgnoreCase((String)fieldName)) {
                queryFields.append("sum(vsu.subjectOrient*(vsu.offset_debit-vsu.offset_credit)) ").append((String)fieldName).append(",");
            } else if (!CollectionUtils.isEmpty((Collection)this.managementDimCodes) && this.managementDimCodes.contains(fieldName)) {
                queryFields.append("sum(vsu.").append((String)fieldName).append(") ").append((String)fieldName).append(",");
            } else {
                queryFields.append("0 ").append((String)fieldName).append(",");
            }
        });
        Map<String, String> input2OffsetFields = this.getInputAndOffsetFieldMapping("vsu");
        this.noCalcFieldNames.forEach(fieldName -> {
            String replace = (String)input2OffsetFields.get(fieldName.toUpperCase());
            queryFields.append(replace == null ? "" : "max(").append((String)input2OffsetFields.get(fieldName.toUpperCase())).append(replace == null ? "" : ")").append(" ").append((String)fieldName).append(",");
        });
        queryFields.deleteCharAt(queryFields.length() - 1);
        this.params.add(this.sumhbTempDao.getSumBatchId());
        this.params.add(this.systemId);
        String condition = " and vsu.DATATIME=? ";
        this.params.add(this.dims.get("DATATIME"));
        if (DimensionUtils.isExistAdjust((String)this.taskDefine.getKey())) {
            condition = condition + " and vsu.ADJUST=? ";
            this.params.add(this.dims.get("ADJUST"));
        }
        condition = condition + " and vsu.OFFSETCURR =? ";
        this.params.add(this.dims.get("MD_CURRENCY"));
        return String.format(sql, queryFields, condition);
    }
}

