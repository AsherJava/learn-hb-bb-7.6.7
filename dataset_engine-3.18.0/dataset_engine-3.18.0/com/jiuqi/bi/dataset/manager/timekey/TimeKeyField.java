/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.text.DateFormatEx
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.manager.timekey;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.manager.timekey.AbstractTimeKeyField;
import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeKeyField
extends AbstractTimeKeyField {
    private int index;
    private BIDataSetFieldInfo field;
    private SimpleDateFormat timekeyFormat;
    private boolean isTimekeyPattern;

    public TimeKeyField(int index, BIDataSetFieldInfo field) {
        this.index = index;
        this.field = field;
        if (StringUtils.isNotEmpty((String)field.getDataPattern())) {
            this.timekeyFormat = new DateFormatEx(field.getDataPattern());
            this.isTimekeyPattern = field.getDataPattern().equals("yyyyMMdd");
        } else {
            this.timekeyFormat = new SimpleDateFormat("yyyyMMdd");
            this.isTimekeyPattern = true;
        }
    }

    @Override
    public String generateTimekey(Object[] data) {
        Object timekey = data[this.index];
        if (this.isTimekeyPattern) {
            return timekey instanceof String ? (String)timekey : String.valueOf(timekey);
        }
        int valType = this.field.getValType();
        if (valType == DataType.STRING.value()) {
            return (String)timekey;
        }
        if (valType == DataType.DATETIME.value()) {
            Calendar c = (Calendar)timekey;
            return this.timekeyFormat.format(c.getTime());
        }
        return null;
    }
}

