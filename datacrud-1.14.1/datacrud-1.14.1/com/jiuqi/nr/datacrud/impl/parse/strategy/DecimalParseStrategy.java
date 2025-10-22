/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.parse.strategy;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.common.NumberConvert;
import com.jiuqi.nr.datacrud.impl.parse.BaseTypeParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

public class DecimalParseStrategy
extends BaseTypeParseStrategy {
    private static final Logger logger = LoggerFactory.getLogger(DecimalParseStrategy.class);
    protected RoundingMode roundingMode;
    protected String numberZeroShow;
    protected NumberFormat numberInstance = NumberFormat.getNumberInstance();
    protected NumberFormat percentInstance = NumberFormat.getPercentInstance();

    public DecimalParseStrategy setRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
        return this;
    }

    public DecimalParseStrategy setNumberZeroShow(String numberZeroShow) {
        this.numberZeroShow = numberZeroShow;
        return this;
    }

    @Override
    protected int getDataType() {
        return 10;
    }

    @Override
    public ParseReturnRes checkParse(DataLinkDefine link, DataField field, Object data) {
        if (link == null && field == null) {
            return this.okValue(null);
        }
        ReturnRes returnRes = this.checkNonNull(link, field, data);
        if (returnRes != null && returnRes.getCode() != 0) {
            return new ParseReturnRes(returnRes);
        }
        if (ObjectUtils.isEmpty(data)) {
            returnRes = this.validationRule(field, null);
            if (returnRes != null && returnRes.getCode() != 0) {
                return new ParseReturnRes(returnRes);
            }
            return this.okValue(null);
        }
        if (data instanceof Number) {
            AbstractData abstractData = AbstractData.valueOf((Object)data, (int)this.getDataType());
            return this.checkSize(data.toString(), field, abstractData.getAsCurrency());
        }
        String valueStr = data.toString();
        ParseReturnRes res = this.numberZeroShow(valueStr);
        if (res != null) {
            returnRes = this.validationRule(field, res.getAbstractData().getAsCurrency());
            if (returnRes != null && returnRes.getCode() != 0) {
                return new ParseReturnRes(returnRes);
            }
            return res;
        }
        return this.parse(valueStr, field);
    }

    protected BigDecimal parse(String valueStr) throws ParseException {
        if (valueStr.matches("-?\\d+(\\.\\d+)?")) {
            return new BigDecimal(valueStr);
        }
        if (valueStr.matches("-?\\d{1,3}(,\\d{3})*(\\.\\d+)?")) {
            valueStr = valueStr.replace(",", "");
            return new BigDecimal(valueStr);
        }
        BigDecimal valueDecimal = null;
        if (valueStr.matches("^(?:[\u00a5$\u20ac]\\s*)?-?\\d{1,3}(?:,\\d{3})*(?:\\.\\d+)?(?:\\s*[\u00a5$\u20ac])?$")) {
            valueStr = valueStr.replaceAll("[\u00a5$\u20ac,]", "");
            valueDecimal = NumberConvert.toBigDecimal(valueStr);
        } else if (valueStr.matches("^(?:[\u00a5$\u20ac]\\s*)?-?\\d{1,3}(?:,\\d{3})*(?:\\.\\d+)?(?:\\s*[\u00a5$\u20ac])?$")) {
            valueStr = valueStr.replaceAll("[\u00a5$\u20ac(),]", "");
            valueDecimal = NumberConvert.toBigDecimal(valueStr).negate();
        } else if (valueStr.matches("-?\\d+(\\.\\d+)?%")) {
            Number number = this.percentInstance.parse(valueStr);
            valueDecimal = NumberConvert.toBigDecimal(number);
        } else if (valueStr.matches("-?\\d+(\\.\\d+)?\u2030")) {
            valueStr = valueStr.replace("\u2030", "");
            Number number = this.numberInstance.parse(valueStr);
            valueDecimal = NumberConvert.toBigDecimal(number).divide(BigDecimal.valueOf(1000L), RoundingMode.HALF_UP);
        }
        return valueDecimal;
    }

    protected ParseReturnRes parse(String valueStr, int precision, int decimal, String fieldTitle) {
        try {
            BigDecimal valueDecimal = this.parse(valueStr);
            if (valueDecimal == null) {
                String message = "\u6307\u6807:" + fieldTitle + ";\u53ea\u80fd\u8f93\u5165\u5c0f\u6570\u3002\u9519\u8bef\u503c:" + valueStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(valueStr, message);
            }
            return this.checkSize(valueStr, precision, decimal, fieldTitle, valueDecimal);
        }
        catch (Exception e) {
            String message = "\u6307\u6807:" + fieldTitle + ";\u53ea\u80fd\u8f93\u5165\u5c0f\u6570\u3002\u9519\u8bef\u503c:" + valueStr;
            this.crudLogger.dataCheckFail(message);
            logger.error(message, e);
            return this.errRes(valueStr, message);
        }
    }

    protected ParseReturnRes parse(String valueStr, DataField field) {
        try {
            BigDecimal valueDecimal = this.parse(valueStr);
            if (valueDecimal == null) {
                String message = "\u6307\u6807:" + field.getTitle() + ";\u53ea\u80fd\u8f93\u5165\u5c0f\u6570\u3002\u9519\u8bef\u503c:" + valueStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(valueStr, message);
            }
            return this.checkSize(valueStr, field, valueDecimal);
        }
        catch (Exception e) {
            String message = "\u6307\u6807:" + field.getTitle() + ";\u53ea\u80fd\u8f93\u5165\u5c0f\u6570\u3002\u9519\u8bef\u503c:" + valueStr;
            this.crudLogger.dataCheckFail(message);
            logger.error(message, e);
            return this.errRes(valueStr, message);
        }
    }

    protected ParseReturnRes checkSize(String input, DataField field, BigDecimal valueDecimal) {
        int decimal;
        int precision = field.getPrecision() == null ? 0 : field.getPrecision();
        int n = decimal = field.getDecimal() == null ? 0 : field.getDecimal();
        if (valueDecimal.scale() > decimal) {
            boolean checkSuccess = false;
            if (this.roundingMode != null) {
                valueDecimal = valueDecimal.setScale(decimal, this.roundingMode);
                checkSuccess = true;
            } else {
                BigDecimal roundValue = valueDecimal.setScale(decimal, RoundingMode.DOWN);
                if (roundValue.compareTo(valueDecimal) == 0) {
                    valueDecimal = roundValue;
                    checkSuccess = true;
                }
            }
            if (!checkSuccess) {
                String message = "\u6307\u6807:" + field.getTitle() + ";\u6570\u636e\u5c0f\u6570\u90e8\u5206\u8d85\u51fa\u6307\u6807\u5c0f\u6570\u90e8\u5206\u4f4d\u6570:" + decimal + "\u3002\u9519\u8bef\u503c:" + input;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(input, message);
            }
        }
        if (valueDecimal.precision() > precision) {
            String message = "\u6307\u6807:" + field.getTitle() + ";\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + precision + "\u3002\u9519\u8bef\u503c:" + input;
            this.crudLogger.dataCheckFail(message);
            return this.errRes(input, message);
        }
        ReturnRes returnRes = this.validationRule(field, valueDecimal);
        if (returnRes != null && returnRes.getCode() != 0) {
            return new ParseReturnRes(returnRes);
        }
        return this.okValue(valueDecimal);
    }

    protected ParseReturnRes checkSize(String valueStr, int precision, int decimal, String fieldTitle, BigDecimal valueDecimal) {
        if (valueDecimal.scale() > decimal) {
            BigDecimal bigDecimal;
            boolean checkSuccess = false;
            if (this.roundingMode != null) {
                valueDecimal = valueDecimal.setScale(decimal, this.roundingMode);
                checkSuccess = true;
            }
            if (decimal == 0 && (bigDecimal = valueDecimal.setScale(0, RoundingMode.DOWN)).compareTo(valueDecimal) == 0) {
                valueDecimal = bigDecimal;
                checkSuccess = true;
            }
            if (!checkSuccess) {
                String message = "\u6307\u6807:" + fieldTitle + ";\u6570\u636e\u5c0f\u6570\u90e8\u5206\u8d85\u51fa\u6307\u6807\u5c0f\u6570\u90e8\u5206\u4f4d\u6570:" + decimal + "\u3002\u9519\u8bef\u503c:" + valueStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(valueStr, message);
            }
        }
        if (valueDecimal.precision() > precision) {
            String message = "\u6307\u6807:" + fieldTitle + ";\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + precision + "\u3002\u9519\u8bef\u503c:" + valueStr;
            this.crudLogger.dataCheckFail(message);
            return this.errRes(valueStr, message);
        }
        return this.okValue(valueDecimal);
    }

    @Override
    public ParseReturnRes checkParse(DataLinkDefine link, IFMDMAttribute attribute, Object data) {
        if (link == null && attribute == null) {
            return this.okValue(null);
        }
        ReturnRes returnRes = this.checkNonNull(link, attribute, data);
        if (returnRes != null && returnRes.getCode() != 0) {
            return new ParseReturnRes(returnRes);
        }
        if (data == null) {
            return this.okValue(null);
        }
        if (data instanceof Number) {
            AbstractData abstractData = AbstractData.valueOf((Object)data, (int)10);
            return this.okValue(abstractData);
        }
        String valueStr = data.toString();
        ParseReturnRes res = this.numberZeroShow(valueStr);
        if (res != null) {
            return res;
        }
        if (valueStr.contains(",")) {
            valueStr = valueStr.replace(",", "");
        }
        int precision = attribute.getPrecision();
        int decimal = attribute.getDecimal();
        return this.parse(valueStr, precision, decimal, attribute.getTitle());
    }

    protected ParseReturnRes numberZeroShow(Object data) {
        String valueStr = data.toString();
        if (valueStr.equals(this.numberZeroShow)) {
            return this.okValue(0);
        }
        return null;
    }
}

