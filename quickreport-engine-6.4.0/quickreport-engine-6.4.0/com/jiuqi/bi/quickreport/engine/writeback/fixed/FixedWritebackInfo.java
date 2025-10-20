/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.bi.quickreport.engine.writeback.fixed;

import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.writeback.ReportWritebackException;
import com.jiuqi.bi.quickreport.engine.writeback.ValueValidator;
import com.jiuqi.bi.quickreport.writeback.TableField;
import com.jiuqi.bi.syntax.cell.Position;

public final class FixedWritebackInfo {
    private Position position;
    private IReportExpression expression;
    private boolean key;
    private ValueValidator validator;

    public FixedWritebackInfo(Position position, IReportExpression expression, boolean isKey, TableField field) throws ReportWritebackException {
        this.position = position;
        this.expression = expression;
        this.key = isKey;
        this.validator = ValueValidator.createValidator(field.getDataType());
    }

    public Position getPosition() {
        return this.position;
    }

    public IReportExpression getExpression() {
        return this.expression;
    }

    public ValueValidator getValidator() {
        return this.validator;
    }

    public boolean isKey() {
        return this.key;
    }
}

