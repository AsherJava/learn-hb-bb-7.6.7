/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.exception;

import com.jiuqi.np.dataengine.common.RowExpressionValidResult;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import java.util.ArrayList;
import java.util.List;

public class ExpressionValidateException
extends IncorrectQueryException {
    private static final long serialVersionUID = -909898184540959481L;
    private List<RowExpressionValidResult> rowExpressionValidResults;

    public ExpressionValidateException(String arg0) {
        super(arg0);
    }

    public ExpressionValidateException(Throwable arg0) {
        super(arg0);
    }

    public ExpressionValidateException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public List<RowExpressionValidResult> getRowExpressionValidResults() {
        return this.rowExpressionValidResults;
    }

    public void setRowExpressionValidResults(List<RowExpressionValidResult> rowExpressionValidResults) {
        this.rowExpressionValidResults = rowExpressionValidResults;
    }

    public void merge(ExpressionValidateException e) {
        if (e.rowExpressionValidResults == null) {
            return;
        }
        if (this.rowExpressionValidResults == null) {
            this.rowExpressionValidResults = new ArrayList<RowExpressionValidResult>();
        }
        this.rowExpressionValidResults.addAll(e.getRowExpressionValidResults());
    }
}

