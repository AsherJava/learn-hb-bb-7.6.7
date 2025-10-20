/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  net.sf.jsqlparser.JSQLParserException
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.parser.CCJSqlParserUtil
 *  net.sf.jsqlparser.statement.select.PlainSelect
 *  net.sf.jsqlparser.statement.select.Select
 *  net.sf.jsqlparser.statement.select.SelectExpressionItem
 *  net.sf.jsqlparser.statement.select.SelectItem
 */
package com.jiuqi.bde.bizmodel.execute.model.tfv.utils;

import com.jiuqi.bde.bizmodel.execute.model.tfv.intf.OptimizeOperator;
import com.jiuqi.bde.bizmodel.execute.model.tfv.intf.TfvOptimKey;
import com.jiuqi.bde.bizmodel.execute.util.SqlParseUtil;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class TfvExecuteUtil {
    private static final Pattern PATTERN_MATCH_SUM = Pattern.compile("^SUM\\s*\\(\\s*([A-Za-z0-9_\\s*]+)\\s*\\)");
    private static final String MATCH_IDENTICALLY_EQUAL = "^(\\s*)(1)(\\s*)=(\\s*)1(\\s*)(AND)?";

    public static TfvOptimKey parseOptimKey(String srcSql) {
        Assert.isNotEmpty((String)srcSql);
        try {
            String formatSql = SqlParseUtil.formatSql(srcSql);
            Select select = (Select)CCJSqlParserUtil.parse((String)formatSql);
            PlainSelect plainSelect = (PlainSelect)select.getSelectBody();
            return TfvExecuteUtil.parseOptimKey(plainSelect);
        }
        catch (JSQLParserException e) {
            throw new BusinessRuntimeException("SQL\u89e3\u6790\u51fa\u73b0\u9519\u8bef", (Throwable)e);
        }
    }

    public static TfvOptimKey parseOptimKey(PlainSelect plainSelect) {
        Assert.isNotNull((Object)plainSelect);
        TfvOptimKey tfvOptimKey = new TfvOptimKey();
        List selectItems = plainSelect.getSelectItems();
        Assert.isNotEmpty((Collection)selectItems, (String)"SQL\u8bed\u53e5\u6ca1\u6709\u5305\u542b\u67e5\u8be2\u5b57\u6bb5\uff0c\u8bf7\u68c0\u67e5\u662f\u5426\u6b63\u786e\u3002", (Object[])new Object[0]);
        SelectItem selectItemNode = (SelectItem)selectItems.get(0);
        Assert.isTrue((!selectItemNode.toString().trim().equals("*") ? 1 : 0) != 0, (String)"\u81ea\u5b9a\u4e49\u53d6\u6570\u516c\u5f0f\u4e2d\u4e0d\u80fd\u4f7f\u7528\"*\"\u901a\u914d\uff0c\u8bf7\u66ff\u6362\u4e3a\u5177\u4f53\u5b57\u6bb5\u3002", (Object[])new Object[0]);
        Assert.isTrue((boolean)(selectItemNode instanceof SelectExpressionItem), (String)String.format("SQL\u7684\u67e5\u8be2\u5b57\u6bb5\u3010%1$s\u3011\u6682\u672a\u652f\u6301\uff0c\u8bf7\u68c0\u67e5\u662f\u5426\u6b63\u786e\u3002", selectItemNode.toString()), (Object[])new Object[0]);
        SelectExpressionItem selectItem = (SelectExpressionItem)selectItemNode;
        String column = selectItem.getExpression().toString();
        tfvOptimKey.setSelectColumn(column);
        String alias = "";
        alias = selectItem.getAlias() != null && !StringUtils.isEmpty((String)selectItem.getAlias().getName()) ? selectItem.getAlias().getName() : (!column.contains(" ") && !column.contains("(") && !column.contains(")") && !column.contains("SUM") ? column : "\"" + selectItem.getExpression().toString().replace(" ", "") + "\"");
        tfvOptimKey.setSelectAlias(alias);
        tfvOptimKey.setSelectContainsSum(PATTERN_MATCH_SUM.matcher(tfvOptimKey.getSelectColumn()).matches());
        Expression whereExpression = plainSelect.getWhere();
        if (whereExpression == null) {
            return tfvOptimKey;
        }
        String srcWhereSqlStr = whereExpression.toString();
        String whereSqlStr = srcWhereSqlStr.replaceAll(MATCH_IDENTICALLY_EQUAL, "");
        OptimizeOperator operator = null;
        String[] condiArray = whereSqlStr.split(" AND ");
        switch (condiArray.length) {
            case 1: {
                operator = TfvExecuteUtil.optimizablePredicate(condiArray[0]);
                if (operator != null) {
                    tfvOptimKey.setMainDimName(String.format("%1$s@%2$s", condiArray[0].split("(?i:=|LIKE)")[0].trim(), operator.getCode()));
                    tfvOptimKey.setMainDimValue(condiArray[0].split("(?i:=|LIKE)")[1].trim().replaceAll("(?i)'", ""));
                    tfvOptimKey.setMainDimOperator(operator);
                    break;
                }
                tfvOptimKey.setOtherCondition(whereSqlStr);
                break;
            }
            case 2: {
                operator = TfvExecuteUtil.optimizablePredicate(condiArray[0]);
                if (operator == null) {
                    tfvOptimKey.setOtherCondition(whereSqlStr);
                    break;
                }
                tfvOptimKey.setMainDimName(String.format("%1$s@%2$s", condiArray[0].split("(?i:=|LIKE)")[0].trim(), operator.getCode()));
                tfvOptimKey.setMainDimValue(condiArray[0].split("(?i:=|LIKE)")[1].trim().replaceAll("(?i)'", ""));
                tfvOptimKey.setMainDimOperator(operator);
                tfvOptimKey.setOtherCondition(whereSqlStr.replace(condiArray[0].trim(), "").replaceFirst("(?i)AND", "").trim());
                operator = TfvExecuteUtil.optimizablePredicate(condiArray[1]);
                if (operator == null) break;
                tfvOptimKey.setSecondDimName(String.format("%1$s@%2$s", condiArray[1].split("(?i:=|LIKE)")[0].trim(), operator.getCode()));
                tfvOptimKey.setSecondDimValue(condiArray[1].split("(?i:=|LIKE)")[1].trim().replaceAll("(?i)'", ""));
                tfvOptimKey.setSecondDimOperator(operator);
                tfvOptimKey.setOtherCondition("");
                break;
            }
            default: {
                operator = TfvExecuteUtil.optimizablePredicate(condiArray[0]);
                if (operator == null) {
                    tfvOptimKey.setOtherCondition(whereSqlStr);
                    break;
                }
                tfvOptimKey.setMainDimName(String.format("%1$s@%2$s", condiArray[0].split("(?i:=|LIKE)")[0].trim(), operator.getCode()));
                tfvOptimKey.setMainDimValue(condiArray[0].split("(?i:=|LIKE)")[1].trim().replaceAll("(?i)'", ""));
                tfvOptimKey.setMainDimOperator(operator);
                tfvOptimKey.setOtherCondition(whereSqlStr.replace(condiArray[0].trim(), "").replaceFirst("(?i)AND", "").trim());
                operator = TfvExecuteUtil.optimizablePredicate(condiArray[1]);
                if (operator == null) break;
                tfvOptimKey.setSecondDimName(String.format("%1$s@%2$s", condiArray[1].split("(?i:=|LIKE)")[0].trim(), operator.getCode()));
                tfvOptimKey.setSecondDimValue(condiArray[1].split("(?i:=|LIKE)")[1].trim().replaceAll("(?i)'", ""));
                tfvOptimKey.setSecondDimOperator(operator);
                tfvOptimKey.setOtherCondition(tfvOptimKey.getOtherCondition().replace(condiArray[1].trim(), "").replaceFirst("(?i)AND", "").trim());
            }
        }
        return tfvOptimKey;
    }

    private static OptimizeOperator optimizablePredicate(String condi) {
        if (!(condi.contains("(") || condi.contains(")") || condi.contains(" OR ") || condi.contains(">=") || condi.contains("<="))) {
            if (condi.contains("=")) {
                return OptimizeOperator.EQ;
            }
            if (condi.contains(" LIKE ")) {
                return OptimizeOperator.LIKE;
            }
        }
        return null;
    }

    public static Object getValByFieldDefineType(ExecuteSettingVO settingVo) {
        Object val = BdeCommonUtil.fieldDefineTypeIsNum((Integer)settingVo.getFieldDefineType()) ? BigDecimal.ZERO : (BdeCommonUtil.fieldDefineTypeIsDate((Integer)settingVo.getFieldDefineType()) ? "9999-00-00" : "0");
        return val;
    }

    public static Object getValByResultType(ResultSet rs) throws SQLException {
        Object val = rs.getObject(1) instanceof BigDecimal || rs.getObject(1) instanceof Integer ? (rs.getObject(1) == null ? BigDecimal.ZERO : rs.getBigDecimal(1)) : rs.getString(1);
        return val;
    }
}

