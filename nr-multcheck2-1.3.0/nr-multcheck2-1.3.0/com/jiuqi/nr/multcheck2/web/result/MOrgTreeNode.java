/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.web.result.OrgTreeNode;
import java.util.List;
import java.util.Map;

public class MOrgTreeNode
extends OrgTreeNode {
    private String scheme;

    public MOrgTreeNode() {
    }

    public MOrgTreeNode(IEntityRow row, Map<String, List<String>> successWithExplainMap, Map<String, Map<String, FailedOrgInfo>> failedMap, Map<String, List<String>> ignoreMap, String scheme, String entityKey) {
        this.setKey(entityKey);
        this.setCode(entityKey);
        this.setTitle(row.getCode() + " | " + row.getTitle());
        this.setIgnore(ignoreMap.get(entityKey));
        this.setFailed(failedMap.get(entityKey));
        this.setSuccessWithExplain(successWithExplainMap.get(entityKey));
        this.scheme = scheme;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
}

