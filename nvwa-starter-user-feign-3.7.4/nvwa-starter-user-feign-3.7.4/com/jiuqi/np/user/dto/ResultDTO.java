/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.user.dto;

import java.io.Serializable;

public class ResultDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean state;
    private String message;

    public static ResultDTO Ok() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setState(true);
        resultDTO.setMessage("\u64cd\u4f5c\u6210\u529f");
        return resultDTO;
    }

    public static ResultDTO Error(String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setState(false);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public boolean isState() {
        return this.state;
    }

    public String getMessage() {
        return this.message;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

