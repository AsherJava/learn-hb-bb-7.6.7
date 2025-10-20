/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.nr.definition.facade.BigDataDefine;
import java.util.Date;

public class I18nRunTimeBigDataDefine
implements BigDataDefine {
    private final BigDataDefine bigDataDefine;
    private byte[] data;

    public I18nRunTimeBigDataDefine(BigDataDefine bigDataDefine) {
        this.bigDataDefine = bigDataDefine;
    }

    @Override
    public String getKey() {
        return this.bigDataDefine.getKey();
    }

    @Override
    public String getCode() {
        return this.bigDataDefine.getCode();
    }

    @Override
    public int getLang() {
        return this.bigDataDefine.getLang();
    }

    @Override
    public String getVersion() {
        return this.bigDataDefine.getVersion();
    }

    @Override
    public Date getUpdateTime() {
        return this.bigDataDefine.getUpdateTime();
    }

    @Override
    public byte[] getData() {
        return this.data == null ? this.bigDataDefine.getData() : this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

