/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
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
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DataLinkStyleUtil;
import com.jiuqi.nr.jtable.util.DateUtils;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.Time;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeLinkData
extends LinkData {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeLinkData.class);

    public DateTimeLinkData(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        super(dataLinkDefine, fieldDefine);
        this.type = LinkType.LINK_TYPE_DATETIME.getValue();
        this.style = DataLinkStyleUtil.getOtherLinkStyle(dataLinkDefine, fieldDefine);
    }

    public DateTimeLinkData(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        super(dataLinkDefine, columnModelDefine);
        this.type = LinkType.LINK_TYPE_DATETIME.getValue();
        this.style = DataLinkStyleUtil.getOtherLinkStyle(dataLinkDefine, columnModelDefine);
    }

    @Override
    public Object getFormatData(AbstractData data, DataFormaterCache dataFormaterCache) {
        Object superData = super.getFormatData(data, dataFormaterCache);
        if (!(superData instanceof AbstractData)) {
            return superData;
        }
        long dateTime = 0L;
        try {
            dateTime = data.getAsDateTime();
        }
        catch (DataTypeException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
        return DateUtils.dateToStringTime(new Date(dateTime));
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
        String formatStr = "yyyy-MM-dd HH:mm:ss";
        try {
            Date date = null;
            date = value instanceof Date ? (Date)value : DateUtils.stringTimeToDate(value.toString());
            if (DateUtils.checkDate(date)) {
                return new Time(date.getTime());
            }
            saveErrorDataInfo.setFieldTitle(this.zbtitle);
            saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
            saveErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807:" + this.zbtitle + " ;\u6570\u636e\u683c\u5f0f\u4e3a" + formatStr + "\u3002\u8d85\u51fa\u6b63\u5e38\u65e5\u671f\u8303\u56f4\u3002\u9519\u8bef\u503c:" + value.toString());
            return null;
        }
        catch (Exception e) {
            saveErrorDataInfo.setFieldTitle(this.zbtitle);
            saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
            saveErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807:" + this.zbtitle + " ;\u6570\u636e\u683c\u5f0f\u4e3a" + formatStr + "\u3002\u9519\u8bef\u503c:" + value.toString());
            return null;
        }
    }
}

