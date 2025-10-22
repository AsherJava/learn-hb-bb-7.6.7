/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.bean;

import com.jiuqi.nr.reminder.bean.AutoUserEntityElement;
import java.util.List;

public class AutoUserEntitys {
    private List<AutoUserEntityElement> userEntityElements;
    private String identityId;

    public int getUserEntityCount() {
        int count = 0;
        if (this.userEntityElements != null) {
            for (AutoUserEntityElement userEntityElement : this.userEntityElements) {
                List<AutoUserEntityElement> children = userEntityElement.getChildren();
                if (children == null) continue;
                count += children.size();
            }
        }
        return count;
    }

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public List<AutoUserEntityElement> getUserEntityElements() {
        return this.userEntityElements;
    }

    public void setUserEntityElements(List<AutoUserEntityElement> userEntityElements) {
        this.userEntityElements = userEntityElements;
    }
}

