/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;
import java.util.Map;

public class BatchMergeAccess
implements IBatchMergeAccess {
    private String name;
    private Map<IAccessFormMerge, AccessCode> accessCodeMap;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<IAccessFormMerge, AccessCode> getAccessCodeMap() {
        return this.accessCodeMap;
    }

    public void setAccessCodeMap(Map<IAccessFormMerge, AccessCode> accessCodeMap) {
        this.accessCodeMap = accessCodeMap;
    }

    @Override
    public AccessCode getAccessCode(IAccessFormMerge merge) {
        AccessCode accessCode = this.accessCodeMap.get(merge);
        if (accessCode == null) {
            accessCode = new AccessCode(this.name);
        }
        return accessCode;
    }

    public BatchMergeAccess() {
    }

    public BatchMergeAccess(String name, Map<IAccessFormMerge, AccessCode> accessCodeMap) {
        this.name = name;
        this.accessCodeMap = accessCodeMap;
    }
}

