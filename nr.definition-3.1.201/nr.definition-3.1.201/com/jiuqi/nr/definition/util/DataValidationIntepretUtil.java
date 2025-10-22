/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.validation.CompareType;
import com.jiuqi.nr.definition.validation.DataValidationExpression;
import com.jiuqi.nr.definition.validation.DataValidationExpressionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class DataValidationIntepretUtil {
    private static final Logger logger = LoggerFactory.getLogger(DataValidationIntepretUtil.class);
    public static final String ZERO = ".0";

    public static String intepret(IDataDefinitionRuntimeController dataDefinitionController, String checkFormula) {
        Assert.notNull((Object)checkFormula, "check formula must not be null");
        DataValidationExpression dataValidationExpression = DataValidationIntepretUtil.intepretValidationExpression(dataDefinitionController, checkFormula);
        if (dataValidationExpression == null) {
            return null;
        }
        FieldDefine fieldDefine = dataValidationExpression.getFieldDefine();
        FieldType type = fieldDefine.getType();
        StringBuffer msg = new StringBuffer();
        String maxValue = null;
        String minValue = null;
        String compareValue = null;
        CompareType compareType = dataValidationExpression.getCompareType();
        if (compareType == CompareType.BETWEEN || compareType == CompareType.NOT_BETWEEN) {
            maxValue = String.valueOf(dataValidationExpression.getMax());
            minValue = String.valueOf(dataValidationExpression.getMin());
            maxValue = maxValue.endsWith(ZERO) ? maxValue.substring(0, maxValue.length() - 2) : maxValue;
            minValue = minValue.endsWith(ZERO) ? minValue.substring(0, minValue.length() - 2) : minValue;
        } else {
            compareValue = String.valueOf(dataValidationExpression.getCompareValue());
            String string = compareValue = compareValue.endsWith(ZERO) ? compareValue.substring(0, compareValue.length() - 2) : compareValue;
        }
        if (compareType == CompareType.NOTNULL) {
            msg.append("\u503c\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
            return msg.toString();
        }
        switch (type) {
            case FIELD_TYPE_STRING: {
                DataValidationIntepretUtil.varcahrCheckMsg(msg, compareType, minValue, maxValue, compareValue);
                break;
            }
            case FIELD_TYPE_DECIMAL: {
                DataValidationIntepretUtil.numberCheckMsg(msg, compareType, minValue, maxValue, compareValue);
                break;
            }
            case FIELD_TYPE_TEXT: {
                DataValidationIntepretUtil.textCheckMsg(msg, compareType, minValue, maxValue, compareValue);
                break;
            }
            default: {
                throw new RuntimeException("\u4e0d\u652f\u6301\u7684\u6821\u9a8c\u7c7b\u578b\uff01");
            }
        }
        return msg.toString();
    }

    private static void numberCheckMsg(StringBuffer msg, CompareType compareType, String minValue, String maxValue, String compareValue) {
        if (compareType == CompareType.BETWEEN || compareType == CompareType.NOT_BETWEEN) {
            msg.append("\u8bf7\u8f93\u5165").append(compareType.getTitle());
            msg.append("\u201c").append(minValue);
            msg.append("\u201d\u81f3\u201c").append(maxValue);
            msg.append("\u201d\u7684\u503c");
        } else if (compareType == CompareType.EQUAL || compareType == CompareType.NOT_EQUAL) {
            DataValidationIntepretUtil.appendMsg(msg, compareType, compareValue);
        } else if (compareType == CompareType.MORE_THAN || compareType == CompareType.LESS_THAN) {
            DataValidationIntepretUtil.appendMsg(msg, compareType, compareValue);
        } else if (compareType == CompareType.MORE_THAN_OR_EQUAL || compareType == CompareType.LESS_THAN_OR_EQUAL) {
            DataValidationIntepretUtil.appendMsg(msg, compareType, compareValue);
        } else if (compareType == CompareType.NOTNULL) {
            msg.append("\u503c\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
    }

    private static void appendMsg(StringBuffer msg, CompareType compareType, String compareValue) {
        msg.append("\u8bf7\u8f93\u5165").append(compareType.getTitle());
        msg.append("\u201c").append(compareValue);
        msg.append("\u201d\u7684\u503c");
    }

    private static void varcahrCheckMsg(StringBuffer msg, CompareType compareType, String minValue, String maxValue, String compareValue) {
        if (compareType == CompareType.LESS_THAN) {
            msg.append("\u5b57\u7b26\u957f\u5ea6\u5e94");
            msg.append(compareType.getTitle()).append("\u201c");
            msg.append(compareValue);
            msg.append("\u201d");
        } else if (compareType == CompareType.CONTAINS || compareType == CompareType.NOT_CONTAINS) {
            msg.append("\u8bf7\u8f93\u5165").append(compareType.getTitle());
            msg.append("\u201c").append(compareValue);
            msg.append("\u201d\u7684\u5b57\u7b26");
        } else if (compareType == CompareType.MOBILEPHONE) {
            msg.append("\u8bf7\u8f93\u5165\u5408\u6cd5\u7684\u624b\u673a\u53f7");
        } else if (compareType == CompareType.MAXLEN) {
            msg.append("\u5b57\u7b26\u8d85\u8fc7");
            msg.append(compareType.getTitle()).append("\u201c");
            msg.append(compareValue);
            msg.append("\u201d");
        } else if (compareType == CompareType.NOTNULL) {
            msg.append("\u503c\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
    }

    private static void textCheckMsg(StringBuffer msg, CompareType compareType, String minValue, String maxValue, String compareValue) {
        if (compareType == CompareType.BETWEEN || compareType == CompareType.NOT_BETWEEN) {
            msg.append("\u6587\u672c\u957f\u5ea6\u5e94");
            msg.append(compareType.getTitle()).append("\u201c");
            msg.append(minValue);
            msg.append("\u201d\u81f3\u201c").append(maxValue).append("\u201d");
        } else if (compareType == CompareType.MORE_THAN || compareType == CompareType.LESS_THAN) {
            msg.append("\u6587\u672c\u957f\u5ea6\u5e94").append(compareType.getTitle());
            msg.append("\u201c").append(compareValue).append("\u201d");
        } else if (compareType == CompareType.CONTAINS || compareType == CompareType.NOT_CONTAINS) {
            msg.append("\u8bf7\u8f93\u5165").append(compareType.getTitle());
            msg.append("\u201c").append(compareValue).append("\u201d\u7684\u6587\u672c");
        } else if (compareType == CompareType.MOBILEPHONE) {
            msg.append("\u8bf7\u8f93\u5165\u5408\u6cd5\u7684\u624b\u673a\u53f7");
        } else if (compareType == CompareType.MAXLEN) {
            msg.append("\u6587\u672c\u8d85\u8fc7");
            msg.append(compareType.getTitle()).append("\u201c");
            msg.append(compareValue);
            msg.append("\u201d");
        } else if (compareType == CompareType.NOTNULL) {
            msg.append("\u503c\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
    }

    public static DataValidationExpression intepretValidationExpression(IDataDefinitionRuntimeController dataDefinitionController, String validationExpression) {
        ExecutorContext context = new ExecutorContext(dataDefinitionController);
        try {
            DataValidationExpression dataValidationExpression = DataValidationExpressionFactory.createExpression(context, validationExpression);
            return dataValidationExpression;
        }
        catch (SyntaxException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

