/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.FloatData
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DataLinkStyleUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class StringLinkData
extends LinkData {
    private int precision;

    public StringLinkData(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        super(dataLinkDefine, fieldDefine);
        this.type = LinkType.LINK_TYPE_STRING.getValue();
        this.precision = fieldDefine.getSize();
        this.style = DataLinkStyleUtil.getStringLinkStyle(dataLinkDefine, fieldDefine);
    }

    public StringLinkData(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        super(dataLinkDefine, columnModelDefine);
        this.type = LinkType.LINK_TYPE_STRING.getValue();
        this.precision = columnModelDefine.getPrecision();
        this.style = DataLinkStyleUtil.getStringLinkStyle(dataLinkDefine, columnModelDefine);
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    @Override
    public Object getFormatData(AbstractData data, DataFormaterCache dataFormaterCache) {
        Object superData = super.getFormatData(data, dataFormaterCache);
        if (!(superData instanceof AbstractData)) {
            return superData;
        }
        if (data instanceof FloatData) {
            try {
                BigDecimal bigDecimal;
                String tempValue = this.defaultValue;
                if (tempValue.contains(",")) {
                    Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(tempValue);
                    bigDecimal = new BigDecimal(number.doubleValue());
                } else {
                    bigDecimal = new BigDecimal(tempValue);
                }
                if (bigDecimal.doubleValue() == data.getAsFloat()) {
                    return this.defaultValue;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return data.getAsString();
    }

    @Override
    public Object getData(Object value, DataFormaterCache dataFormaterCache, SaveErrorDataInfo saveErrorDataInfo) {
        if (saveErrorDataInfo == null) {
            saveErrorDataInfo = new ImportErrorDataInfo();
        }
        super.getData(value, dataFormaterCache, saveErrorDataInfo);
        if (value == null || StringUtils.isEmpty((String)value.toString()) || "null".equals(value.toString())) {
            return null;
        }
        String stringVaule = value.toString();
        if (this.precision < stringVaule.length()) {
            saveErrorDataInfo.setFieldTitle(this.zbtitle);
            saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
            saveErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807:" + this.zbtitle + ";\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + this.precision + "\u3002\u9519\u8bef\u503c\uff1a" + value.toString());
        }
        return stringVaule;
    }
}

