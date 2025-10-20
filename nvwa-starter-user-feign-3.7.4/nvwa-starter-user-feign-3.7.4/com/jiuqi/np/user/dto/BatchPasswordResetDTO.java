/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.user.dto;

import java.io.Serializable;
import java.util.List;

public class BatchPasswordResetDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> userId;
    private String newPassword;
    private List<String> passwords;
    private Boolean isUserReset = false;

    public BatchPasswordResetDTO() {
    }

    public BatchPasswordResetDTO(List<String> userId, String newPassword) {
        this.newPassword = newPassword;
        this.userId = userId;
    }

    public BatchPasswordResetDTO(List<String> userId, List<String> passwords) {
        this.passwords = passwords;
        this.userId = userId;
    }

    public List<String> getUserId() {
        return this.userId;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setUserId(List<String> userId) {
        this.userId = userId;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public List<String> getPasswords() {
        return this.passwords;
    }

    public void setPasswords(List<String> passwords) {
        this.passwords = passwords;
    }

    public Boolean getUserReset() {
        if (this.isUserReset == null) {
            return false;
        }
        return this.isUserReset;
    }

    public void setUserReset(Boolean userReset) {
        this.isUserReset = userReset;
    }
}

