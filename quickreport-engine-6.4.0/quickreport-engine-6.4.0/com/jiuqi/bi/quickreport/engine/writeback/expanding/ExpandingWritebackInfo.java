/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.writeback.expanding;

import com.jiuqi.bi.quickreport.engine.writeback.ReportWritebackException;
import com.jiuqi.bi.quickreport.engine.writeback.ValueValidator;
import com.jiuqi.bi.quickreport.writeback.TableField;

public final class ExpandingWritebackInfo {
    private int col;
    private TableField field;
    private boolean key;
    private ValueValidator validator;

    public ExpandingWritebackInfo(int col, TableField field, boolean isKey) throws ReportWritebackException {
        this.col = col;
        this.field = field;
        this.key = isKey;
        this.validator = ValueValidator.createValidator(field.getDataType());
    }

    public int getCol() {
        return this.col;
    }

    public TableField getField() {
        return this.field;
    }

    public boolean isKey() {
        return this.key;
    }

    public ValueValidator getValidator() {
        return this.validator;
    }

    public String toString() {
        return this.field.toString();
    }
}

