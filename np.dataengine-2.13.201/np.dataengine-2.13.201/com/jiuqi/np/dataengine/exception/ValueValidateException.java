/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.exception;

import com.jiuqi.np.dataengine.common.RowValidateResult;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import java.util.ArrayList;
import java.util.List;

public class ValueValidateException
extends IncorrectQueryException {
    private static final long serialVersionUID = 912619456696993992L;
    private List<RowValidateResult> rowValidateResults;

    public ValueValidateException(String arg0) {
        super(arg0);
    }

    public ValueValidateException(Throwable arg0) {
        super(arg0);
    }

    public ValueValidateException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public List<RowValidateResult> getRowValidateResults() {
        return this.rowValidateResults;
    }

    public void setRowValidateResults(List<RowValidateResult> rowValidateResults) {
        this.rowValidateResults = rowValidateResults;
    }

    public void merge(ValueValidateException e) {
        if (e.rowValidateResults == null) {
            return;
        }
        if (this.rowValidateResults == null) {
            this.rowValidateResults = new ArrayList<RowValidateResult>();
        }
        this.rowValidateResults.addAll(e.getRowValidateResults());
    }
}

