/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.bizmodel.impl.model.gather.BizModelServiceGather
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.va.domain.common.PageVO
 *  org.apache.commons.collections4.CollectionUtils
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.penetrate.impl.core.content;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.bizmodel.impl.model.gather.BizModelServiceGather;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.common.RowTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.intf.CustomFetchBalancePenetrateResultExtractor;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.model.IPenetrateContentProvider;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.va.domain.common.PageVO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class CustomFetchBalancePenetrateContentProvider
implements IPenetrateContentProvider<PenetrateBaseDTO, PenetrateBalance> {
    private static final Logger log = LoggerFactory.getLogger(CustomFetchBalancePenetrateContentProvider.class);
    @Autowired
    private BizModelServiceGather bizModelServiceGather;
    @Autowired
    private DataSourceService dataSourceService;

    @Override
    public String getPluginType() {
        return ComputationModelEnum.CUSTOMFETCH.getCode();
    }

    @Override
    public String getBizModel() {
        return ComputationModelEnum.CUSTOMFETCH.getCode();
    }

    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.BALANCE;
    }

    @Override
    public PageVO<PenetrateBalance> doQuery(PenetrateBaseDTO condi) {
        String selectSqlTrim;
        List customConditions;
        AggregateFuncEnum func;
        String fieldCode;
        String fetchType = condi.getFetchType();
        String fetchSourceCode = condi.getFetchSourceCode();
        CustomBizModelDTO customBizModelDTO = (CustomBizModelDTO)this.bizModelServiceGather.getByCode(BizModelCategoryEnum.BIZMODEL_CUSTOM.getCode()).getByCode(fetchSourceCode);
        List selectFieldList = customBizModelDTO.getSelectFieldList();
        StringBuilder selectSql = new StringBuilder("SELECT ");
        StringBuilder fromSql = new StringBuilder(" FROM ");
        StringBuilder whereSql = new StringBuilder(" WHERE 1=1 ");
        StringBuilder groupBySql = new StringBuilder(" GROUP BY ");
        String oriGroupBySql = groupBySql.toString();
        Assert.isNotEmpty((Collection)selectFieldList, (String)"\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b\u7684\u53d6\u6570\u5b57\u6bb5\u4e3a\u7a7a\uff01", (Object[])new Object[0]);
        Integer fieldDefineFractionDigits = condi.getFieldDefineFractionDigits() == null ? 2 : condi.getFieldDefineFractionDigits();
        if (fieldDefineFractionDigits < 0) {
            throw new BusinessRuntimeException("\u4f20\u8fc7\u6765\u7684\u5c0f\u6570\u90e8\u5206\u4f4d\u6570\u4e0d\u80fd\u5c0f\u4e8e0\uff01");
        }
        for (SelectField selectField : selectFieldList) {
            fieldCode = selectField.getFieldCode();
            func = AggregateFuncEnum.getEnumByCode((String)selectField.getAggregateFuncCode());
            if (AggregateFuncEnum.SUM == func) {
                selectSql.append(this.createSelectSumSql(fieldCode, fieldDefineFractionDigits));
                continue;
            }
            if (AggregateFuncEnum.AVG == func) {
                selectSql.append(this.createSelectAvgSql(fieldCode, fieldDefineFractionDigits));
                continue;
            }
            if (AggregateFuncEnum.ORIGINAL == func) {
                selectSql.append(fieldCode).append(",");
                groupBySql.append(fieldCode).append(",");
                continue;
            }
            if (AggregateFuncEnum.COUNT != func) continue;
            selectSql.append(this.createSelectCountSql(fieldCode));
        }
        Assert.isNotEmpty((String)fetchType, (String)"\u53d6\u6570\u7c7b\u578bfetchType\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        String fixedCondition = customBizModelDTO.getFixedCondition();
        if (!StringUtils.isEmpty((String)fixedCondition) && (fixedCondition = fixedCondition.trim()).length() >= 3) {
            String prefix = fixedCondition.substring(0, 3);
            if (prefix.equalsIgnoreCase("and")) {
                whereSql.append(fixedCondition);
            } else {
                whereSql.append("AND ").append(fixedCondition);
            }
        }
        if (CollectionUtils.isNotEmpty((Collection)(customConditions = customBizModelDTO.getCustomConditions()))) {
            String formula = condi.getFormula();
            Map formulaMap = (Map)JsonUtils.readValue((String)formula, (TypeReference)new TypeReference<Map<String, String>>(){});
            Assert.isNotEmpty((Map)formulaMap, (String)"\u4f20\u8fc7\u6765\u7684formula\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u539f\u56e0\uff01", (Object[])new Object[0]);
            Set filedCodeSet = selectFieldList.stream().map(SelectField::getFieldCode).collect(Collectors.toSet());
            for (CustomCondition customCondition : customConditions) {
                String paramsCodeStrVal;
                String paramsCode = customCondition.getParamsCode();
                if (!CollectionUtils.containsAny(filedCodeSet, (Object[])new String[]{paramsCode})) {
                    selectSql.append(paramsCode).append(",");
                    groupBySql.append(paramsCode).append(",");
                }
                if (StringUtils.isEmpty((String)(paramsCodeStrVal = (String)formulaMap.get(paramsCode)))) continue;
                String customConditionSql = CustomFetchBalancePenetrateContentProvider.getCustomConditionSqlByVal(customCondition, paramsCodeStrVal, paramsCode);
                whereSql.append(customConditionSql);
            }
        }
        if ((selectSqlTrim = selectSql.toString().trim()).endsWith(",")) {
            selectSql.deleteCharAt(selectSql.lastIndexOf(","));
        }
        StringBuilder sqlStringBuilder = selectSql.append((CharSequence)fromSql).append(customBizModelDTO.getFetchTable()).append(" MAINTABLE").append((CharSequence)whereSql);
        if (!oriGroupBySql.contentEquals(groupBySql)) {
            String groupBySqlTrim = groupBySql.toString().trim();
            if (groupBySqlTrim.endsWith(",")) {
                groupBySql.deleteCharAt(groupBySql.lastIndexOf(","));
            }
            sqlStringBuilder.append((CharSequence)groupBySql);
        }
        String sql = PenetrateUtil.replaceContext(sqlStringBuilder.toString(), condi);
        String dataSourceCode = !StringUtils.isEmpty((String)customBizModelDTO.getDataSourceCode()) ? customBizModelDTO.getDataSourceCode() : condi.getDataScheme().getDataSourceCode();
        List<PenetrateBalance> penetrateBalanceList = this.doQuery(dataSourceCode, sql);
        PageVO vo = new PageVO();
        if (CollectionUtils.isEmpty(penetrateBalanceList)) {
            return vo;
        }
        PenetrateBalance total = new PenetrateBalance();
        total.setRowType(RowTypeEnum.TOTAL.ordinal());
        for (SelectField selectField : selectFieldList) {
            BigDecimal sum = new BigDecimal("0").setScale((int)fieldDefineFractionDigits, RoundingMode.HALF_UP);
            fieldCode = selectField.getFieldCode();
            func = AggregateFuncEnum.getEnumByCode((String)selectField.getAggregateFuncCode());
            if (AggregateFuncEnum.SUM == func || AggregateFuncEnum.COUNT == func) {
                for (PenetrateBalance penetrateBalance : penetrateBalanceList) {
                    BigDecimal sumOrCountBigDecimal = CustomFetchBalancePenetrateContentProvider.getBigDecimalVal(penetrateBalance.get(fieldCode), fieldDefineFractionDigits);
                    sum = sum.add(sumOrCountBigDecimal);
                    penetrateBalance.put("ROWTYPE", 0);
                }
                total.put(fieldCode, sum);
                continue;
            }
            if (AggregateFuncEnum.AVG == func) {
                BigDecimal count = new BigDecimal("0").setScale((int)fieldDefineFractionDigits, RoundingMode.HALF_UP);
                for (PenetrateBalance penetrateBalance : penetrateBalanceList) {
                    BigDecimal avgSumBigDecimal = CustomFetchBalancePenetrateContentProvider.getBigDecimalVal(penetrateBalance.get("SUM_" + fieldCode), fieldDefineFractionDigits);
                    sum = sum.add(avgSumBigDecimal);
                    BigDecimal avgCountBigDecimal = CustomFetchBalancePenetrateContentProvider.getBigDecimalVal(penetrateBalance.get("COUNT_" + fieldCode), fieldDefineFractionDigits);
                    count = count.add(avgCountBigDecimal);
                    penetrateBalance.put("ROWTYPE", 0);
                }
                BigDecimal avg = count.compareTo(BigDecimal.ZERO) == 0 ? count : sum.divide(count, 2, RoundingMode.HALF_UP);
                total.put(fieldCode, avg);
                continue;
            }
            if (penetrateBalanceList.get(0).containsKey("ROWTYPE")) continue;
            for (PenetrateBalance penetrateBalance : penetrateBalanceList) {
                penetrateBalance.put("ROWTYPE", 0);
            }
        }
        vo.setTotal(penetrateBalanceList.size());
        if (Boolean.FALSE.equals(condi.getPagination())) {
            vo.setRows(penetrateBalanceList);
            penetrateBalanceList.add(0, total);
        } else {
            List collect = penetrateBalanceList.stream().skip(condi.getOffset().intValue()).limit(condi.getLimit().intValue()).collect(Collectors.toList());
            vo.setRows(collect);
            collect.add(0, total);
        }
        return vo;
    }

    protected List<PenetrateBalance> doQuery(String dataSourceCode, String sql) {
        return (List)this.dataSourceService.query(dataSourceCode, sql, new Object[0], (ResultSetExtractor)new CustomFetchBalancePenetrateResultExtractor());
    }

    private static String getCustomConditionSqlByVal(CustomCondition customCondition, String paramsCodeStrVal, String paramsCode) {
        String prefix;
        paramsCodeStrVal = paramsCodeStrVal.trim();
        String ruleCode = customCondition.getRuleCode();
        String customConditionSql = MatchingRuleEnum.getEnumByCode((String)ruleCode).buildSqlByRuleAndValue(paramsCode, paramsCodeStrVal);
        String customConditionSqlTrim = customConditionSql.trim();
        customConditionSql = customConditionSqlTrim.length() >= 3 ? (!(prefix = customConditionSqlTrim.substring(0, 3)).equalsIgnoreCase("AND") ? " AND " + customConditionSql : " " + customConditionSql) : " " + customConditionSql;
        return customConditionSql;
    }

    private static BigDecimal getBigDecimalVal(Object fieldCodeVal, Integer fieldDefineFractionDigits) {
        BigDecimal bigDecimalVal;
        if (fieldCodeVal instanceof BigDecimal) {
            bigDecimalVal = ((BigDecimal)fieldCodeVal).setScale((int)fieldDefineFractionDigits, RoundingMode.HALF_UP);
        } else {
            try {
                bigDecimalVal = new BigDecimal(String.valueOf(fieldCodeVal)).setScale((int)fieldDefineFractionDigits, RoundingMode.HALF_UP);
            }
            catch (Exception e) {
                log.error("{} \u8f6c\u6362BigDecimal\u5931\u8d25", fieldCodeVal, (Object)e);
                throw new BusinessRuntimeException(fieldCodeVal + "\u8f6c\u6362BigDecimal\u5931\u8d25", (Throwable)e);
            }
        }
        return bigDecimalVal;
    }

    private String createSelectAvgSql(String colName, Integer fieldDefineFractionDigits) {
        String avgSql = "ROUND(SUM(" + colName + ")/COUNT(1)," + fieldDefineFractionDigits + ") AS " + colName + ",";
        String sumSql = "ROUND(SUM(" + colName + ")," + fieldDefineFractionDigits + ") AS SUM_" + colName + ",";
        String countSql = "COUNT(1) AS COUNT_" + colName + ",";
        return avgSql + sumSql + countSql;
    }

    private String createSelectCountSql(String colName) {
        return "COUNT(1) AS " + colName + ",";
    }

    private String createSelectSumSql(String colName, Integer fieldDefineFractionDigits) {
        return "ROUND(SUM(" + colName + ")," + fieldDefineFractionDigits + ") AS " + colName + ",";
    }
}

