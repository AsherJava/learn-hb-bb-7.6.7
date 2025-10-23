/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.multcheck2.common.SchemeExecuteResult;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import java.util.List;
import java.util.Map;

public class OrgTreeNode
implements INode {
    private String key;
    private String code;
    private String title;
    private Map<String, FailedOrgInfo> failed;
    private List<String> successWithExplain;
    private List<String> ignore;

    public OrgTreeNode() {
    }

    public OrgTreeNode(IEntityRow row, SchemeExecuteResult eResult, String entityKey) {
        this.key = entityKey;
        this.code = entityKey;
        this.title = row.getCode() + " | " + row.getTitle();
        this.ignore = eResult.getIgnoreMap().get(entityKey);
        this.failed = eResult.getFailedMap().get(entityKey);
        this.successWithExplain = eResult.getSuccessWithExplainMap().get(entityKey);
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, FailedOrgInfo> getFailed() {
        return this.failed;
    }

    public void setFailed(Map<String, FailedOrgInfo> failed) {
        this.failed = failed;
    }

    public List<String> getSuccessWithExplain() {
        return this.successWithExplain;
    }

    public void setSuccessWithExplain(List<String> successWithExplain) {
        this.successWithExplain = successWithExplain;
    }

    public List<String> getIgnore() {
        return this.ignore;
    }

    public void setIgnore(List<String> ignore) {
        this.ignore = ignore;
    }
}

