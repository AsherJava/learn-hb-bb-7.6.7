/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.query;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.inputdata.function.sumhb.dao.SumhbTempDao;
import com.jiuqi.gcreport.inputdata.function.sumhb.query.AbstractInputQuery;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class SingleInputQuery
extends AbstractInputQuery {
    public SingleInputQuery(QueryContext queryContext, String tableName, Collection<String> numberFields, Collection<String> noCalcFieldNames, Map<String, String> dims, SumhbTempDao sumhbTempDao) {
        this.init(queryContext, tableName, numberFields, noCalcFieldNames, dims, sumhbTempDao);
    }

    @Override
    public List<Map<String, Object>> query() {
        String sumSql;
        this.params = new ArrayList();
        String detailSql = this.buildDetailSql();
        String querySql = this.concatSql(detailSql, sumSql = this.buildSumSql());
        if (!StringUtils.hasText(querySql)) {
            return CollectionUtils.newArrayList();
        }
        return EntNativeSqlDefaultDao.getInstance().selectMap(querySql, this.params);
    }

    private String buildDetailSql() {
        if (ObjectUtils.isEmpty(this.sumhbTempDao.getBatchId())) {
            return "";
        }
        String sql = "  select %1$s,temp.FORMID GROUPXYZ,'0' SUMXYZ from %2$s i \n    inner join %3$s temp on i.subjectCode = temp.subjectCode and temp.batchId=? \n   where i.reportSystemId = ? %4$s";
        String queryFields = Stream.concat(this.calcFieldName.stream(), this.noCalcFieldNames.stream()).map(fieldName -> "i." + fieldName).collect(Collectors.joining(","));
        this.params.add(this.sumhbTempDao.getBatchId());
        this.params.add(this.systemId);
        StringBuilder conditionBuilder = new StringBuilder();
        this.dims.forEach((key, value) -> {
            conditionBuilder.append(" and i.").append((String)key).append("=?");
            this.params.add(value);
        });
        return String.format("  select %1$s,temp.FORMID GROUPXYZ,'0' SUMXYZ from %2$s i \n    inner join %3$s temp on i.subjectCode = temp.subjectCode and temp.batchId=? \n   where i.reportSystemId = ? %4$s", queryFields, this.inputTableName, this.sumhbTempDao.getTableName(), conditionBuilder);
    }

    private String buildSumSql() {
        if (ObjectUtils.isEmpty(this.sumhbTempDao.getSumBatchId())) {
            return "";
        }
        String sql = "  select %1$s,max(temp.FORMID) GROUPXYZ,'1' SUMXYZ from %2$s isu \n   inner join %3$s temp on isu.subjectCode = temp.subjectCode and temp.batchId=? \n   where isu.reportSystemId = ? %4$s \n   group by temp.FORMID";
        StringBuilder queryFields = new StringBuilder();
        this.calcFieldName.forEach(fieldName -> queryFields.append("sum(isu.").append((String)fieldName).append(") ").append((String)fieldName).append(","));
        this.noCalcFieldNames.forEach(fieldName -> queryFields.append("max(isu.").append((String)fieldName).append(") ").append((String)fieldName).append(","));
        queryFields.deleteCharAt(queryFields.length() - 1);
        this.params.add(this.sumhbTempDao.getSumBatchId());
        this.params.add(this.systemId);
        StringBuilder conditionBuilder = new StringBuilder();
        this.dims.forEach((key, value) -> {
            conditionBuilder.append(" and isu.").append((String)key).append("=?");
            this.params.add(value);
        });
        return String.format("  select %1$s,max(temp.FORMID) GROUPXYZ,'1' SUMXYZ from %2$s isu \n   inner join %3$s temp on isu.subjectCode = temp.subjectCode and temp.batchId=? \n   where isu.reportSystemId = ? %4$s \n   group by temp.FORMID", queryFields, this.inputTableName, this.sumhbTempDao.getTableName(), conditionBuilder);
    }
}

