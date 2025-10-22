/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.exception;

import com.jiuqi.nr.dafafill.model.DataFillSaveErrorDataInfo;
import java.util.List;

public class DuplicateRowKeysRuntimeException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    List<DataFillSaveErrorDataInfo> errors;

    public DuplicateRowKeysRuntimeException(String message) {
        super(message);
    }

    public DuplicateRowKeysRuntimeException(List<DataFillSaveErrorDataInfo> errors) {
        super("\u4e1a\u52a1\u4e3b\u952e\u91cd\u590d!");
        this.errors = errors;
    }

    public DuplicateRowKeysRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public List<DataFillSaveErrorDataInfo> getErrors() {
        return this.errors;
    }
}

