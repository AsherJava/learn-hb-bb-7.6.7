/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.vo;

import com.jiuqi.nr.task.form.controller.dto.CheckFieldCodeBatchDTO;

public class CheckFieldCodeBatchVO {
    private int posX;
    private int posY;
    private String message;
    private Boolean success;

    public CheckFieldCodeBatchVO(CheckFieldCodeBatchDTO fieldData, Boolean success, String message) {
        this.posX = fieldData.getPosX();
        this.posY = fieldData.getPosY();
        this.message = message;
        this.success = success;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}

