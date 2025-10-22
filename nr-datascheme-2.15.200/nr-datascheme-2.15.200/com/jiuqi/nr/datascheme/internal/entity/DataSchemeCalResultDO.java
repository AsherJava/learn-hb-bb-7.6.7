/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.CalResult
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeCalResult
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.core.CalResult;
import com.jiuqi.nr.datascheme.api.core.DataSchemeCalResult;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.time.Instant;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_TABLE_CAL_RESULT")
public class DataSchemeCalResultDO
implements DataSchemeCalResult {
    @DBAnno.DBField(dbField="C_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="DS_KEY")
    private String dataSchemeKey;
    @DBAnno.DBField(dbField="C_RESULT")
    private Integer result;
    private CalResult calResult;
    @DBAnno.DBField(dbField="C_MESSAGE")
    private String message;
    @DBAnno.DBField(dbField="C_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, isOrder=true)
    private Instant updateTime;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public CalResult getCalResult() {
        if (this.calResult == null) {
            this.calResult = CalResult.forValue((int)this.result);
        }
        return this.calResult;
    }

    public void setCalResult(CalResult calResult) {
        this.calResult = calResult;
        if (calResult != null) {
            this.result = calResult.getCode();
        }
    }

    public void setResult(Integer result) {
        this.result = result;
        if (result != null) {
            this.calResult = CalResult.forValue((int)result);
        }
    }

    public Integer getResult() {
        return this.result;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public static DataSchemeCalResultDO valueOf(DataSchemeCalResult o) {
        if (o == null) {
            return null;
        }
        DataSchemeCalResultDO resultDO = new DataSchemeCalResultDO();
        resultDO.setKey(o.getKey());
        resultDO.setDataSchemeKey(o.getDataSchemeKey());
        resultDO.setCalResult(o.getCalResult());
        resultDO.setMessage(o.getMessage());
        resultDO.setUpdateTime(o.getUpdateTime());
        return resultDO;
    }
}

