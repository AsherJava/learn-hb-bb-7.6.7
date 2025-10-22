/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import com.jiuqi.nr.enumcheck.common.EnumDataCheckResultItem;
import java.sql.SQLException;
import java.util.List;

public class EnumCheckException
extends SQLException {
    private static final long serialVersionUID = -8167290364213928605L;
    private String hasTooManyError;
    private List<EnumDataCheckResultItem> result;

    public EnumCheckException(String hasTooManyError, List<EnumDataCheckResultItem> subList) {
        super(hasTooManyError);
        this.setHasTooManyError(hasTooManyError);
        this.setResult(subList);
    }

    public String getHasTooManyError() {
        return this.hasTooManyError;
    }

    public void setHasTooManyError(String hasTooManyError) {
        this.hasTooManyError = hasTooManyError;
    }

    public List<EnumDataCheckResultItem> getResult() {
        return this.result;
    }

    public void setResult(List<EnumDataCheckResultItem> result) {
        this.result = result;
    }
}

