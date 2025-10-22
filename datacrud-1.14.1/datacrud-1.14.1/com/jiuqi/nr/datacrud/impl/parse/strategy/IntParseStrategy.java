/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.datacrud.impl.parse.strategy;

import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DecimalParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntParseStrategy
extends DecimalParseStrategy {
    private static final Logger logger = LoggerFactory.getLogger(IntParseStrategy.class);
    private static final BigDecimal max = new BigDecimal(Integer.MAX_VALUE);
    private static final BigDecimal min = new BigDecimal(Integer.MIN_VALUE);

    @Override
    protected ParseReturnRes parse(String valueStr, int precision, int decimal, String fieldTitle) {
        try {
            BigDecimal valueDecimal = this.parse(valueStr);
            if (valueDecimal == null) {
                String message = "\u6307\u6807:" + fieldTitle + ";\u53ea\u80fd\u8f93\u5165\u6574\u6570\u3002\u9519\u8bef\u503c:" + valueStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(valueStr, message);
            }
            if (valueDecimal.precision() > precision) {
                String message = "\u6307\u6807:" + fieldTitle + ";\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + precision + "\u3002\u9519\u8bef\u503c:" + valueStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(valueStr, message);
            }
            if (valueDecimal.scale() > decimal) {
                BigDecimal bigDecimal = valueDecimal.setScale(0, RoundingMode.DOWN);
                if (bigDecimal.compareTo(valueDecimal) == 0) {
                    valueDecimal = bigDecimal;
                } else {
                    String message = "\u6307\u6807:" + fieldTitle + ";\u53ea\u80fd\u8f93\u5165\u6574\u6570\u3002\u9519\u8bef\u503c:" + valueStr;
                    this.crudLogger.dataCheckFail(message);
                    return this.errRes(valueStr, message);
                }
            }
            if (valueDecimal.compareTo(max) > 0 || valueDecimal.compareTo(min) < 0) {
                String message = "\u6307\u6807:" + fieldTitle + ";\u53ea\u80fd\u8f93\u5165\u6574\u6570\u4e14\u6700\u5927\u503c\u4e3a\uff1a2147483647\uff0c\u6700\u5c0f\u503c\u4e3a\uff1a-2147483648\u3002\u9519\u8bef\u503c:" + valueStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(valueStr, message);
            }
            return this.okValue(valueDecimal);
        }
        catch (Exception e) {
            String message = "\u6307\u6807:" + fieldTitle + ";\u53ea\u80fd\u8f93\u5165\u6574\u6570\u3002\u9519\u8bef\u503c:" + valueStr;
            this.crudLogger.dataCheckFail(message);
            ParseReturnRes res = this.errRes(valueStr, message);
            logger.error(res.getMessage(), e);
            return res;
        }
    }

    @Override
    protected ParseReturnRes parse(String valueStr, DataField field) {
        int precision = field.getPrecision() == null ? 0 : field.getPrecision();
        int decimal = field.getDecimal() == null ? 0 : field.getDecimal();
        String fieldTitle = field.getTitle();
        try {
            BigDecimal valueDecimal = this.parse(valueStr);
            if (valueDecimal == null) {
                String message = "\u6307\u6807:" + fieldTitle + ";\u53ea\u80fd\u8f93\u5165\u6574\u6570\u3002\u9519\u8bef\u503c:" + valueStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(valueStr, message);
            }
            if (valueDecimal.precision() > precision) {
                String message = "\u6307\u6807:" + fieldTitle + ";\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + precision + "\u3002\u9519\u8bef\u503c:" + valueStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(valueStr, message);
            }
            if (valueDecimal.scale() > decimal) {
                BigDecimal bigDecimal = valueDecimal.setScale(0, RoundingMode.DOWN);
                if (bigDecimal.compareTo(valueDecimal) == 0) {
                    valueDecimal = bigDecimal;
                } else {
                    String message = "\u6307\u6807:" + fieldTitle + ";\u53ea\u80fd\u8f93\u5165\u6574\u6570\u3002\u9519\u8bef\u503c:" + valueStr;
                    this.crudLogger.dataCheckFail(message);
                    return this.errRes(valueStr, message);
                }
            }
            if (valueDecimal.compareTo(max) > 0 || valueDecimal.compareTo(min) < 0) {
                String message = "\u6307\u6807:" + fieldTitle + ";\u53ea\u80fd\u8f93\u5165\u6574\u6570\u4e14\u6700\u5927\u503c\u4e3a\uff1a2147483647\uff0c\u6700\u5c0f\u503c\u4e3a\uff1a-2147483648\u3002\u9519\u8bef\u503c:" + valueStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(valueStr, message);
            }
            ReturnRes returnRes = this.validationRule(field, valueDecimal);
            if (returnRes != null && returnRes.getCode() != 0) {
                return new ParseReturnRes(returnRes);
            }
            return this.okValue(valueDecimal);
        }
        catch (Exception e) {
            String message = "\u6307\u6807:" + fieldTitle + ";\u53ea\u80fd\u8f93\u5165\u6574\u6570\u3002\u9519\u8bef\u503c:" + valueStr;
            this.crudLogger.dataCheckFail(message);
            ParseReturnRes res = this.errRes(valueStr, message);
            logger.error(res.getMessage(), e);
            return res;
        }
    }
}

