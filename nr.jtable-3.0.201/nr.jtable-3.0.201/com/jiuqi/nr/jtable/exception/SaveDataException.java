/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.output.SaveResult;

public class SaveDataException
extends JTableException {
    private SaveResult saveResult;

    public SaveResult getSaveResult() {
        return this.saveResult;
    }

    public void setSaveResult(SaveResult saveResult) {
        this.saveResult = saveResult;
    }

    public SaveDataException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public SaveDataException(String[] datas) {
        super(JtableExceptionCodeCost.UPDATE_CHECKRESULT, datas);
    }

    public SaveDataException(SaveResult saveResult) {
        super(JtableExceptionCodeCost.UPDATE_CHECKRESULT, null);
        this.saveResult = saveResult;
    }
}

