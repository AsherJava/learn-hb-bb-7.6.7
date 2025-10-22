/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.exception;

public class DeployTableException
extends RuntimeException {
    private static final long serialVersionUID = -2125474720743642287L;
    private String tableTitle;
    private String action;

    public DeployTableException(String message, String tableTitle, String action) {
        super(message);
        this.tableTitle = tableTitle;
        this.action = action;
    }

    public DeployTableException(Exception e, String tableTitle, String action) {
        super(e);
        this.tableTitle = tableTitle;
        this.action = action;
    }

    @Override
    public String getMessage() {
        return String.format("%s\u3010%s\u3011\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a%s", this.action, this.tableTitle, super.getMessage());
    }
}

