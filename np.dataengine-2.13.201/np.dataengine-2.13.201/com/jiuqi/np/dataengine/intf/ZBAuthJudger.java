/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.setting.AuthorityType;
import java.util.Map;

public class ZBAuthJudger {
    private Map<String, AuthorityType> authMap;

    public ZBAuthJudger(Map<String, AuthorityType> authMap) {
        this.authMap = authMap;
    }

    public boolean canRead(String fieldKey) {
        if (this.authMap == null) {
            return true;
        }
        AuthorityType auth = this.authMap.get(fieldKey);
        return auth == null || auth != AuthorityType.NONE;
    }

    public boolean canModify(String fieldKey) {
        if (this.authMap == null) {
            return true;
        }
        AuthorityType auth = this.authMap.get(fieldKey);
        return auth == null || auth == AuthorityType.MODIFY;
    }
}

