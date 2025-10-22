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
import com.jiuqi.nr.datacrud.common.DateUtils;
import com.jiuqi.nr.datacrud.impl.parse.BaseTypeParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.text.ParseException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public abstract class BaseDateParseStrategy
extends BaseTypeParseStrategy {
    private static final Logger logger = LoggerFactory.getLogger(BaseDateParseStrategy.class);
    private boolean checkDateRange = true;

    public boolean isCheckDateRange() {
        return this.checkDateRange;
    }

    public void setCheckDateRange(boolean checkDateRange) {
        this.checkDateRange = checkDateRange;
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
        return this.parseData(data, field, null);
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
        return this.parseData(data, null, attribute);
    }

    private ParseReturnRes parseData(Object data, DataField field, IFMDMAttribute attribute) {
        AbstractData abstractData;
        ParseReturnRes res = new ParseReturnRes();
        String zbtitle = null;
        if (field != null) {
            zbtitle = field.getTitle();
        } else if (attribute != null) {
            zbtitle = attribute.getTitle();
        }
        if (data != null) {
            Date date = null;
            if (data instanceof Date) {
                date = (Date)data;
                abstractData = AbstractData.valueOf((Object)data, (int)this.getDataType());
            } else {
                try {
                    String dataStr = data.toString();
                    if (StringUtils.hasLength(dataStr)) {
                        date = this.parseDate(dataStr);
                    }
                    abstractData = AbstractData.valueOf((Object)date, (int)this.getDataType());
                }
                catch (Exception e) {
                    String message = "\u6307\u6807:" + zbtitle + " ;\u6570\u636e\u683c\u5f0f\u4e3a" + this.formatStr() + "\u3002\u9519\u8bef\u503c:" + data;
                    this.crudLogger.dataCheckFail(message);
                    res.setMessage(message);
                    res.setCode(1211);
                    res.setData(data.toString());
                    logger.error(res.getMessage(), e);
                    return res;
                }
            }
            if (this.isCheckDateRange() && !DateUtils.checkDate(date)) {
                res.setCode(1211);
                String message = "\u6307\u6807:" + zbtitle + " ;\u6570\u636e\u683c\u5f0f\u4e3a" + this.formatStr() + "\u3002\u8d85\u51fa\u6b63\u5e38\u65e5\u671f\u8303\u56f4\u3002\u9519\u8bef\u503c:" + data;
                this.crudLogger.dataCheckFail(message);
                res.setMessage(message);
                res.setData(data.toString());
                return res;
            }
        } else {
            abstractData = AbstractData.valueOf(null, (int)this.getDataType());
        }
        res.setAbstractData(abstractData);
        res.setCode(0);
        return res;
    }

    protected abstract Date parseDate(String var1) throws ParseException;

    protected abstract String formatStr();
}

