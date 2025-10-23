/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.dto;

import com.jiuqi.nr.task.form.formio.dto.ImportBaseDTO;

public class FormulaParseResult
extends ImportBaseDTO {
    private String pos;
    private String errorValue;
    private String errorMessage;

    public FormulaParseResult(String pos, String errorValue, String errorMessage) {
        this.errorValue = errorValue;
        this.errorMessage = errorMessage;
        this.pos = pos;
    }

    public String getPos() {
        return this.pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getErrorValue() {
        return this.errorValue;
    }

    public void setErrorValue(String errorValue) {
        this.errorValue = errorValue;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

