/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.vertical_blank.sqlformatter.SqlFormatter
 *  com.github.vertical_blank.sqlformatter.core.FormatConfig
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.va.domain.common.MD5Util
 *  net.sf.jsqlparser.JSQLParserException
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.parser.CCJSqlParserUtil
 *  net.sf.jsqlparser.statement.select.FromItem
 *  net.sf.jsqlparser.statement.select.GroupByElement
 *  net.sf.jsqlparser.statement.select.PlainSelect
 *  net.sf.jsqlparser.statement.select.Select
 */
package com.jiuqi.bde.bizmodel.execute.util;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.va.domain.common.MD5Util;
import java.util.List;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.GroupByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class SqlParseUtil {
    private static final FormatConfig config = FormatConfig.builder().indent("    ").uppercase(true).maxColumnLength(100).build();

    public static final String formatSql(String sql) {
        try {
            return SqlFormatter.format((String)sql, (FormatConfig)config);
        }
        catch (Exception e) {
            return sql;
        }
    }

    public static void main(String[] args) {
        String withSql = "WITH TMP AS (select * FROM GLA_BAL WHERE MOF_DIV_CODE =     '211400000' AND  AGENCY_CODE = '211400000129001'),TMP1 AS (SELECT * FROM GLA_BAL WHERE MOF_DIV_CODE = '211400000' AND  AGENCY_CODE = '211400000129001') SELECT MOF_DIV_CODE AS MOF_DIV_CODE1 FROM (SELECT * FROM TMP) T JOIN (SELECT * FROM TMP1) TMP1 ON TMP1.MOF_DIV_CODE = T.MOF_DIV_CODE   WHERE 1 = 1  AND T.MOF_DIV_CODE LIKE '211400000%' AND  T.AGENCY_CODE = '211400000129001' AND (T.FISCAL_YEAR = 2021 AND T.AGENCY_CODE = '01')  AND (T.FISCAL_YEAR = 2022 AND T.AGENCY_CODE = '02') ";
        SqlParseUtil.clacOptimizeRule(withSql);
    }

    private static String clacOptimizeRule(String srcSql) {
        Assert.isNotEmpty((String)srcSql);
        try {
            StringBuffer flag = new StringBuffer(srcSql.length());
            String formatSql = SqlParseUtil.formatSql(srcSql);
            System.out.println("formatSql" + formatSql);
            Select select = (Select)CCJSqlParserUtil.parse((String)formatSql);
            String withSql = select.getWithItemsList().toString();
            flag.append("withSql:{").append(withSql).append("}");
            PlainSelect plainSelect = (PlainSelect)select.getSelectBody();
            plainSelect.getSelectItems();
            FromItem fromItem = plainSelect.getFromItem();
            flag.append(";fromSql:{").append(fromItem).append("}");
            List joins = plainSelect.getJoins();
            flag.append(";joinSql:{").append(joins).append("}");
            GroupByElement groupBy = plainSelect.getGroupBy();
            flag.append(";groupBySql:{").append(groupBy).append("}");
            Expression having = plainSelect.getHaving();
            flag.append(";havingSql:{").append(having).append("}");
            Expression whereExpression = plainSelect.getWhere();
            String whereSqlStr = whereExpression.toString();
            String mainDimName = "";
            String mainDimValue = "";
            String assistDimName = "";
            String assistDimValue = "";
            String otherCondition = "";
            String operator = null;
            String[] condiArray = whereSqlStr.split(" AND ");
            switch (condiArray.length) {
                case 1: {
                    operator = SqlParseUtil.optimizablePredicate(condiArray[0]);
                    if (!StringUtils.isEmpty((String)operator)) {
                        mainDimName = String.format("%1$s@%2$s", condiArray[0].split("(?i:=|LIKE)")[0].trim(), operator);
                        mainDimValue = condiArray[0].split("(?i:=|LIKE)")[1].trim().replaceAll("(?i)'", "");
                        break;
                    }
                    otherCondition = whereSqlStr;
                    break;
                }
                case 2: {
                    operator = SqlParseUtil.optimizablePredicate(condiArray[0]);
                    if (StringUtils.isEmpty((String)operator)) {
                        otherCondition = whereSqlStr;
                        break;
                    }
                    mainDimName = String.format("%1$s@%2$s", condiArray[0].split("(?i:=|LIKE)")[0].trim(), operator);
                    mainDimValue = condiArray[0].split("(?i:=|LIKE)")[1].trim().replaceAll("(?i)'", "");
                    otherCondition = whereSqlStr.replace(condiArray[0].trim(), "").replaceFirst("(?i)AND", "").trim();
                    operator = SqlParseUtil.optimizablePredicate(condiArray[1]);
                    if (StringUtils.isEmpty((String)operator)) break;
                    assistDimName = String.format("%1$s@%2$s", condiArray[1].split("(?i:=|LIKE)")[0].trim(), operator);
                    assistDimValue = condiArray[1].split("(?i:=|LIKE)")[1].trim().replaceAll("(?i)'", "");
                    otherCondition = "";
                    break;
                }
                default: {
                    operator = SqlParseUtil.optimizablePredicate(condiArray[0]);
                    if (StringUtils.isEmpty((String)operator)) {
                        otherCondition = whereSqlStr;
                        break;
                    }
                    mainDimName = String.format("%1$s@%2$s", condiArray[0].split("(?i:=|LIKE)")[0].trim(), operator);
                    mainDimValue = condiArray[0].split("(?i:=|LIKE)")[1].trim().replaceAll("(?i)'", "");
                    otherCondition = whereSqlStr.replace(condiArray[0].trim(), "").replaceFirst("(?i)AND", "").trim();
                    operator = SqlParseUtil.optimizablePredicate(condiArray[1]);
                    if (StringUtils.isEmpty((String)operator)) break;
                    assistDimName = String.format("%1$s@%2$s", condiArray[1].split("(?i:=|LIKE)")[0].trim(), operator);
                    assistDimValue = condiArray[1].split("(?i:=|LIKE)")[1].trim().replaceAll("(?i)'", "");
                    otherCondition = otherCondition.replace(condiArray[1].trim(), "").replaceFirst("(?i)AND", "").trim();
                }
            }
            flag.append(";whereSql:{").append("");
            flag.append(",").append(mainDimName.toLowerCase());
            flag.append(",").append(assistDimName.toLowerCase());
            flag.append(",").append(otherCondition.toLowerCase()).append("}");
            return MD5Util.encrypt((String)flag.toString());
        }
        catch (JSQLParserException e) {
            return MD5Util.encrypt((String)UUIDUtils.newUUIDStr());
        }
    }

    private static String optimizablePredicate(String condi) {
        if (!(condi.contains("(") || condi.contains(")") || condi.contains(" or ") || condi.contains(">=") || condi.contains("<="))) {
            if (condi.contains("=")) {
                return "=";
            }
            if (condi.toLowerCase().contains(" like ")) {
                return "like";
            }
        }
        return null;
    }
}

