/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark;

import java.util.function.Supplier;

public class NvwaWatermarkVariableDTO {
    String code;
    String desc;
    Supplier<String> valueSupplier;

    public NvwaWatermarkVariableDTO(String code, String desc, Supplier<String> valueSupplier) {
        this.code = code;
        this.desc = desc;
        this.valueSupplier = valueSupplier;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Supplier<String> getValueSupplier() {
        return this.valueSupplier;
    }

    public void setValueSupplier(Supplier<String> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }
}

