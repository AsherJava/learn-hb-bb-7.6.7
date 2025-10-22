/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datacrud.Measure
 */
package com.jiuqi.nr.data.excel.obj;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datacrud.Measure;
import java.io.Serializable;
import org.springframework.lang.Nullable;

public class ExportMeasureSetting
implements Serializable {
    private static final long serialVersionUID = 8969179185644213700L;
    private String key;
    private String code;
    private int decimal = -1;

    @Nullable
    public Measure toMeasure() {
        if (StringUtils.isEmpty((String)this.key) || StringUtils.isEmpty((String)this.code)) {
            return null;
        }
        Measure measure = new Measure();
        measure.setKey(this.key);
        measure.setCode(this.code);
        return measure;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }
}

