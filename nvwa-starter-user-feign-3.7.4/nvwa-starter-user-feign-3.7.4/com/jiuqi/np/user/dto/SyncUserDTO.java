/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.user.dto;

import com.jiuqi.np.user.dto.UserDTO;

public class SyncUserDTO
extends UserDTO {
    private static final long serialVersionUID = 1L;
    private boolean del;

    public boolean isDel() {
        return this.del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }
}

