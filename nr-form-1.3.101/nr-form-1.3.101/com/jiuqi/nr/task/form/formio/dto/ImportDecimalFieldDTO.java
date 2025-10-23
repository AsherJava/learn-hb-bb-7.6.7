/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.dto;

import com.jiuqi.nr.task.form.formio.dto.ImportFieldDTO;

public class ImportDecimalFieldDTO
extends ImportFieldDTO {
    private int decimal;

    public ImportDecimalFieldDTO() {
    }

    public ImportDecimalFieldDTO(int decimal, String code) {
        this.setCode(code);
        this.decimal = decimal;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }
}

