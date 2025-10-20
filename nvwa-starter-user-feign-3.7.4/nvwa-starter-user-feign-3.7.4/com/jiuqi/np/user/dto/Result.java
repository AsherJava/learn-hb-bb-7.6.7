/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.user.dto;

import com.jiuqi.np.user.dto.UserDTO;
import java.util.List;

public class Result {
    private List<UserDTO> userList;
    private long userCount;

    public List<UserDTO> getUserList() {
        return this.userList;
    }

    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }

    public long getUserCount() {
        return this.userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }
}

