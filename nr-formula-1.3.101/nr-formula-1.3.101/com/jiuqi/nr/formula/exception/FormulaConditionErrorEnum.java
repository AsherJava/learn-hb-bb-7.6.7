/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.formula.exception;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.nr.formula.common.FormulaI18nUtil;
import java.util.StringJoiner;

public enum FormulaConditionErrorEnum implements ErrorEnum
{
    QUERY_ERROR("001", "\u67e5\u8be2\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u5931\u8d25"),
    UPDATE_ERROR("002", "\u4fee\u6539\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u5931\u8d25"),
    INSERT_ERROR("003", "\u65b0\u5efa\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u5931\u8d25"),
    DELETE_ERROR("004", "\u5220\u9664\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u5931\u8d25"),
    IMPORT_ERROR("005", "\u5bfc\u5165\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u5931\u8d25"),
    EXPORT_ERROR("006", "\u5bfc\u51fa\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u5931\u8d25"),
    PUBLISH_ERROR("007", "\u53d1\u5e03\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u5931\u8d25");

    private final String code;
    private final String message;

    private FormulaConditionErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return FormulaI18nUtil.getMessage("jqException.formula.formulaCondition." + this.code, new Object[0]);
    }

    public String toString() {
        return new StringJoiner(", ", FormulaConditionErrorEnum.class.getSimpleName() + "[", "]").add("code='" + this.code + "'").add("message='" + this.message + "'").toString();
    }
}

