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
import com.jiuqi.nr.datacrud.impl.parse.BaseTypeParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;

public class BooleanParseStrategy
extends BaseTypeParseStrategy {
    private static final String[] trueValues = new String[]{"true", "yes", "y", "on", "1", "\u662f", "\u221a"};
    private static final String[] falseValues = new String[]{"false", "no", "n", "off", "0", "\u5426", "\u00d7"};

    public static Boolean parseBoolean(String value) {
        for (int i = 0; i < trueValues.length; ++i) {
            if (value.toLowerCase().equals(trueValues[i])) {
                return true;
            }
            if (!value.toLowerCase().equals(falseValues[i])) continue;
            return false;
        }
        return null;
    }

    @Override
    public ParseReturnRes checkParse(DataLinkDefine link, DataField field, Object data) {
        if (link == null && field == null) {
            return this.okValue(false);
        }
        ReturnRes returnRes = this.checkNonNull(link, field, data);
        if (returnRes != null && returnRes.getCode() != 0) {
            return new ParseReturnRes(returnRes);
        }
        return this.parseData(data, field.getTitle());
    }

    @Override
    public ParseReturnRes checkParse(DataLinkDefine link, IFMDMAttribute attribute, Object data) {
        if (link == null && attribute == null) {
            return this.okValue(false);
        }
        ReturnRes returnRes = this.checkNonNull(link, attribute, data);
        if (returnRes != null && returnRes.getCode() != 0) {
            return new ParseReturnRes(returnRes);
        }
        return this.parseData(data, attribute.getTitle());
    }

    private ParseReturnRes parseData(Object data, String fieldTitle) {
        AbstractData abstractData;
        ParseReturnRes res = new ParseReturnRes();
        if (data != null) {
            if (data instanceof Boolean) {
                abstractData = AbstractData.valueOf((Object)data, (int)1);
            } else {
                Boolean aBoolean = BooleanParseStrategy.parseBoolean(data.toString());
                if (aBoolean == null) {
                    String message = "\u6307\u6807:" + fieldTitle + ";\u53ea\u80fd\u8f93\u5165\u5e03\u5c14\u503c\u3002\u9519\u8bef\u503c:" + data;
                    this.crudLogger.dataCheckFail(message);
                    res.setCode(1211);
                    res.setMessage(message);
                    res.setData(data.toString());
                    return res;
                }
                abstractData = AbstractData.valueOf((Object)aBoolean, (int)1);
            }
        } else {
            abstractData = AbstractData.valueOf(null, (int)1);
        }
        res.setAbstractData(abstractData);
        res.setCode(0);
        return res;
    }

    @Override
    protected int getDataType() {
        return 1;
    }
}

