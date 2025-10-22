/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.common;

import java.io.Serializable;

public class BpmCacheChangedMessage
implements Serializable {
    private static final long serialVersionUID = 5941107931922846013L;
    public static final String BPM_MESSAGE_CHANNEL = "com.jiuqi.np.dataengine.auth.authgo.AuthgoCacheChangedMessage";
    private String processDefinitionKey;

    public BpmCacheChangedMessage(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getProcessDefinitionKey() {
        return this.processDefinitionKey;
    }
}

