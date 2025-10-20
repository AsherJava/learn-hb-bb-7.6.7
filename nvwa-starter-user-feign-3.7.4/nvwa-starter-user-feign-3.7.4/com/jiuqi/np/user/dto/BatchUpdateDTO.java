/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.user.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class BatchUpdateDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int userState;
    private boolean fieldValue;
    private List<String> userNames;

    public BatchUpdateDTO() {
    }

    public BatchUpdateDTO(int userState, boolean fieldValue, String ... userNames) {
        this.userState = userState;
        this.fieldValue = fieldValue;
        this.userNames = Arrays.asList(userNames);
    }

    public int getUserState() {
        return this.userState;
    }

    public boolean isFieldValue() {
        return this.fieldValue;
    }

    public List<String> getUserNames() {
        return this.userNames;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public void setFieldValue(boolean fieldValue) {
        this.fieldValue = fieldValue;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }
}

