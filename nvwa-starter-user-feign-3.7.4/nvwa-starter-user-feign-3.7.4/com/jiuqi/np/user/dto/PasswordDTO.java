/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.Password
 *  com.jiuqi.np.user.Password$ModifyByType
 *  com.jiuqi.np.user.PasswordFeature
 */
package com.jiuqi.np.user.dto;

import com.jiuqi.np.user.Password;
import com.jiuqi.np.user.PasswordFeature;
import java.io.Serializable;
import java.time.Instant;

public class PasswordDTO
implements Password,
Serializable {
    private static final long serialVersionUID = -331178238428972966L;
    protected String id;
    protected String userId;
    protected String password;
    protected String type;
    protected Instant modifyTime;
    protected Password.ModifyByType modifyByType;
    protected PasswordFeature passwordFeature;
    protected String sign;

    public PasswordDTO() {
    }

    public PasswordDTO(String userId, String password, String type) {
        this.userId = userId;
        this.password = password;
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public Password.ModifyByType getModifyByType() {
        return this.modifyByType;
    }

    public PasswordFeature getPasswordFeature() {
        return this.passwordFeature;
    }

    public void setPasswordFeature(PasswordFeature passwordFeature) {
        this.passwordFeature = passwordFeature;
    }

    public void setModifyByType(Password.ModifyByType modifyByType) {
        this.modifyByType = modifyByType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        PasswordDTO other = (PasswordDTO)obj;
        return !(this.id == null ? other.id != null : !this.id.equals(other.id));
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

