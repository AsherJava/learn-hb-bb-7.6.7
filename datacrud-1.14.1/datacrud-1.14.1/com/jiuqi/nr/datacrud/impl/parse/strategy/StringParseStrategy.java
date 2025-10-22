/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.parse.strategy;

import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.parse.BaseTypeParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import org.springframework.util.ObjectUtils;

public class StringParseStrategy
extends BaseTypeParseStrategy {
    private boolean overLengthTruncated;

    public boolean isOverLengthTruncated() {
        return this.overLengthTruncated;
    }

    public void setOverLengthTruncated(boolean overLengthTruncated) {
        this.overLengthTruncated = overLengthTruncated;
    }

    @Override
    protected int getDataType() {
        return 6;
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
            returnRes = this.validationRule(field, data);
            if (returnRes.getCode() != 0) {
                return new ParseReturnRes(returnRes);
            }
            return this.okValue(null);
        }
        return this.parse(data.toString(), field);
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
        return this.parse(data.toString(), attribute);
    }

    protected ParseReturnRes parse(String valueStr, DataField field) {
        if (ObjectUtils.isEmpty(valueStr) || "null".equals(valueStr)) {
            return this.okValue(null);
        }
        Integer precision = field.getPrecision();
        if (precision == null) {
            precision = 0;
        }
        if (valueStr.length() > precision) {
            if (this.overLengthTruncated) {
                String subData = valueStr.substring(0, precision);
                ReturnRes returnRes = this.validationRule(field, subData);
                if (returnRes != null && returnRes.getCode() != 0) {
                    return new ParseReturnRes(returnRes);
                }
                return this.okValue(subData);
            }
            String message = "\u6307\u6807:" + field.getTitle() + ";\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + precision + "\u3002\u9519\u8bef\u503c:" + valueStr;
            this.crudLogger.dataCheckFail(message);
            return this.errRes(valueStr, message);
        }
        ReturnRes returnRes = this.validationRule(field, valueStr);
        if (returnRes != null && returnRes.getCode() != 0) {
            return new ParseReturnRes(returnRes);
        }
        return this.okValue(valueStr);
    }

    protected ParseReturnRes parse(String valueStr, IFMDMAttribute attribute) {
        if (ObjectUtils.isEmpty(valueStr) || "null".equals(valueStr)) {
            return this.okValue(null);
        }
        int precision = attribute.getPrecision();
        if (valueStr.length() > precision) {
            if (this.isOverLengthTruncated()) {
                return this.okValue(valueStr.substring(0, precision));
            }
            String message = "\u6307\u6807:" + attribute.getTitle() + ";\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + precision + "\u3002\u9519\u8bef\u503c:" + valueStr;
            this.crudLogger.dataCheckFail(message);
            return this.errRes(valueStr, message);
        }
        return this.okValue(valueStr);
    }

    @Deprecated
    protected ParseReturnRes parse(String valueStr, Integer precision, String fieldTitle) {
        return null;
    }
}

