/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.dc.base.common.utils.SqlUtil
 */
package com.jiuqi.bde.bizmodel.client.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.dc.base.common.utils.SqlUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum MatchingRuleEnum {
    EQUAL("EQUAL", "=", "\u7b49\u4e8e", "SINGLE,MULTIPLE,RANGE"){

        @Override
        public String buildSqlByRuleAndValue(String conditionField, String value) {
            return String.format(MatchingRuleEnum.buildWhereSql(value), conditionField, this.getRuleShow(), "");
        }

        @Override
        public boolean match(String resultVal, String value) {
            if (resultVal == null) {
                resultVal = "";
            }
            if (value == null) {
                value = "";
            }
            if (value.contains(MatchingRuleEnum.FN_DELIMITER_COLON)) {
                String[] ruleArr = value.split(MatchingRuleEnum.FN_DELIMITER_COLON);
                return resultVal.compareTo(ruleArr[0]) >= 0 && resultVal.compareTo(ruleArr[1] + "ZZZ") <= 0;
            }
            if (value.contains(MatchingRuleEnum.FN_DELIMITER_COMMA)) {
                StringTokenizer stringTokenizer = new StringTokenizer(value, MatchingRuleEnum.FN_DELIMITER_COMMA);
                while (stringTokenizer.hasMoreElements()) {
                    String rule = (String)stringTokenizer.nextElement();
                    if (!resultVal.equals(rule)) continue;
                    return true;
                }
                return false;
            }
            return resultVal.equals(value);
        }
    }
    ,
    LIKE("LIKE", "LIKE", "\u6a21\u7cca\u5339\u914d", "SINGLE,MULTIPLE"){

        @Override
        public String buildSqlByRuleAndValue(String conditionField, String value) {
            return String.format(MatchingRuleEnum.buildWhereSql(value), conditionField, this.getRuleShow(), "%%");
        }

        @Override
        public boolean match(String resultVal, String value) {
            if (resultVal == null) {
                resultVal = "";
            }
            if (value == null) {
                value = "";
            }
            if (value.contains(MatchingRuleEnum.FN_DELIMITER_COMMA)) {
                String[] ruleArr;
                for (String rule : ruleArr = value.split(MatchingRuleEnum.FN_DELIMITER_COMMA)) {
                    if (!resultVal.startsWith(rule)) continue;
                    return true;
                }
                return false;
            }
            return resultVal.startsWith(value);
        }
    }
    ,
    NOTEQUAL("NOTEQUAL", "<>", "\u4e0d\u7b49\u4e8e", "SINGLE,MULTIPLE"){

        @Override
        public String buildSqlByRuleAndValue(String conditionField, String value) {
            Assert.isNotEmpty((String)value);
            String valueStr = value.replace("'", "");
            String[] valueArr = valueStr.split(MatchingRuleEnum.FN_DELIMITER_COMMA);
            if (valueArr.length == 1) {
                return String.format(MatchingRuleEnum.WHERE_TEMPLATE_SQL, conditionField, this.getRuleShow(), value);
            }
            return String.format(" AND %1$s ", SqlBuildUtil.getStrNotInCondi((String)String.format(" MAINTABLE.%1$s ", conditionField), Arrays.asList(valueArr)));
        }

        @Override
        public boolean match(String resultVal, String value) {
            if (resultVal == null) {
                resultVal = "";
            }
            if (value == null) {
                value = "";
            }
            if (value.contains(MatchingRuleEnum.FN_DELIMITER_COMMA)) {
                StringTokenizer stringTokenizer = new StringTokenizer(value, MatchingRuleEnum.FN_DELIMITER_COMMA);
                while (stringTokenizer.hasMoreElements()) {
                    String rule = (String)stringTokenizer.nextElement();
                    if (!resultVal.equals(rule)) continue;
                    return false;
                }
                return true;
            }
            return !resultVal.equals(value);
        }
    }
    ,
    LESS("LESS", "<", "\u5c0f\u4e8e", "SINGLE"){

        @Override
        public String buildSqlByRuleAndValue(String conditionField, String value) {
            return String.format(MatchingRuleEnum.WHERE_TEMPLATE_SQL, conditionField, this.getRuleShow(), value);
        }

        @Override
        public boolean match(String resultVal, String value) {
            if (resultVal == null) {
                resultVal = "";
            }
            if (value == null) {
                value = "";
            }
            return resultVal.compareTo(value) < 0;
        }
    }
    ,
    LESSANDEQUAL("LESSANDEQUAL", "<=", "\u5c0f\u4e8e\u7b49\u4e8e", "SINGLE"){

        @Override
        public String buildSqlByRuleAndValue(String conditionField, String value) {
            return String.format(MatchingRuleEnum.WHERE_TEMPLATE_SQL, conditionField, this.getRuleShow(), value);
        }

        @Override
        public boolean match(String resultVal, String value) {
            if (resultVal == null) {
                resultVal = "";
            }
            if (value == null) {
                value = "";
            }
            return resultVal.compareTo(value) <= 0;
        }
    }
    ,
    GREATER("GREATER", ">", "\u5927\u4e8e", "SINGLE"){

        @Override
        public String buildSqlByRuleAndValue(String conditionField, String value) {
            return String.format(MatchingRuleEnum.WHERE_TEMPLATE_SQL, conditionField, this.getRuleShow(), value);
        }

        @Override
        public boolean match(String resultVal, String value) {
            if (resultVal == null) {
                resultVal = "";
            }
            if (value == null) {
                value = "";
            }
            return resultVal.compareTo(value) > 0;
        }
    }
    ,
    GREATERANDEQUAL("GREATERANDEQUAL", ">=", "\u5927\u4e8e\u7b49\u4e8e", "SINGLE"){

        @Override
        public String buildSqlByRuleAndValue(String conditionField, String value) {
            return String.format(MatchingRuleEnum.WHERE_TEMPLATE_SQL, conditionField, this.getRuleShow(), value);
        }

        @Override
        public boolean match(String resultVal, String value) {
            if (resultVal == null) {
                resultVal = "";
            }
            if (value == null) {
                value = "";
            }
            return resultVal.compareTo(value) >= 0;
        }
    }
    ,
    IN("IN", "IN", "\u5728\u8303\u56f4\u5185\uff08\u5e9f\u5f03\uff09", "SINGLE,MULTIPLE"){

        @Override
        public String buildSqlByRuleAndValue(String conditionField, String value) {
            Assert.isNotEmpty((String)value);
            String valueStr = value.replace("'", "");
            String[] valueArr = valueStr.split(MatchingRuleEnum.FN_DELIMITER_COMMA);
            return String.format(" AND %1$s ", SqlBuildUtil.getStrInCondi((String)String.format(" MAINTABLE.%1$s ", conditionField), Arrays.asList(valueArr)));
        }

        @Override
        public boolean match(String resultVal, String value) {
            if (resultVal == null) {
                resultVal = "";
            }
            if (value == null) {
                value = "";
            }
            if (value.contains(MatchingRuleEnum.FN_DELIMITER_COLON)) {
                String[] ruleArr = value.split(MatchingRuleEnum.FN_DELIMITER_COLON);
                return resultVal.compareTo(ruleArr[0]) >= 0 && resultVal.compareTo(ruleArr[1] + "ZZZ") <= 0;
            }
            if (value.contains(MatchingRuleEnum.FN_DELIMITER_COMMA)) {
                StringTokenizer stringTokenizer = new StringTokenizer(value, MatchingRuleEnum.FN_DELIMITER_COMMA);
                while (stringTokenizer.hasMoreElements()) {
                    String rule = (String)stringTokenizer.nextElement();
                    if (!resultVal.equals(rule)) continue;
                    return true;
                }
                return false;
            }
            return resultVal.equals(value);
        }
    };

    private static final String WHERE_TEMPLATE_SQL = " AND MAINTABLE.%1$s %2$s '%3$s' ";
    private final String ruleCode;
    private final String ruleShow;
    private final String ruleName;
    private final String dataRanges;
    public static final String FN_DELIMITER_COLON = ":";
    public static final String FN_DELIMITER_COMMA = ",";

    private MatchingRuleEnum(String ruleCode, String ruleShow, String ruleName, String dataRanges) {
        this.ruleCode = ruleCode;
        this.ruleShow = ruleShow;
        this.ruleName = ruleName;
        this.dataRanges = dataRanges;
    }

    public String getRuleCode() {
        return this.ruleCode;
    }

    public String getRuleShow() {
        return this.ruleShow;
    }

    public String getRuleName() {
        return this.ruleName;
    }

    public String getDataRanges() {
        return this.dataRanges;
    }

    public static MatchingRuleEnum getEnumByCode(String ruleCode) {
        if (IN.getRuleCode().equals(ruleCode)) {
            return IN;
        }
        if (EQUAL.getRuleCode().equals(ruleCode)) {
            return EQUAL;
        }
        if (LIKE.getRuleCode().equals(ruleCode)) {
            return LIKE;
        }
        if (NOTEQUAL.getRuleCode().equals(ruleCode)) {
            return NOTEQUAL;
        }
        if (LESS.getRuleCode().equals(ruleCode)) {
            return LESS;
        }
        if (LESSANDEQUAL.getRuleCode().equals(ruleCode)) {
            return LESSANDEQUAL;
        }
        if (GREATER.getRuleCode().equals(ruleCode)) {
            return GREATER;
        }
        if (GREATERANDEQUAL.getRuleCode().equals(ruleCode)) {
            return GREATERANDEQUAL;
        }
        for (MatchingRuleEnum matchingRuleEnum : MatchingRuleEnum.values()) {
            if (!matchingRuleEnum.getRuleCode().equalsIgnoreCase(ruleCode)) continue;
            return matchingRuleEnum;
        }
        throw new BusinessRuntimeException(String.format("\u672a\u627e\u5230\u3010%1$s\u3011\u5bf9\u5e94\u7684MatchingRulesEnum\u679a\u4e3e", ruleCode));
    }

    public abstract String buildSqlByRuleAndValue(String var1, String var2);

    private static String buildWhereSql(String conditionValue) {
        if (conditionValue.contains(FN_DELIMITER_COMMA)) {
            String[] dimValues = conditionValue.split(FN_DELIMITER_COMMA);
            ArrayList<String> likeSqlList = new ArrayList<String>();
            for (String value : dimValues) {
                likeSqlList.add(" MAINTABLE.%1$s %2$s '" + value + "%3$s' ");
            }
            return SqlUtil.concatCondi(likeSqlList, (boolean)false);
        }
        if (conditionValue.contains(FN_DELIMITER_COLON)) {
            String[] values = conditionValue.split(FN_DELIMITER_COLON);
            return " AND MAINTABLE.%1$s >='" + values[0] + "' AND MAINTABLE.%1$s <='" + values[1] + "ZZZ' ";
        }
        return " AND MAINTABLE.%1$s %2$s '" + conditionValue + "%3$s' ";
    }

    public abstract boolean match(String var1, String var2);
}

