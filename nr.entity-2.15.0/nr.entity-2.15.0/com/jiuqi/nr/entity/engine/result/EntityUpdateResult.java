/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.result;

import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityUpdateResult {
    private Map<String, String> codeToKey;
    private EntityCheckResult checkResult;

    public Map<String, String> getCodeToKey() {
        if (this.codeToKey == null) {
            this.codeToKey = new HashMap<String, String>(16);
        }
        return this.codeToKey;
    }

    public String getSuccessKey(String code) {
        return this.getCodeToKey().get(code);
    }

    public List<String> getSuccessKeys() {
        return this.getCodeToKey().values().stream().collect(Collectors.toList());
    }

    public EntityCheckResult getCheckResult() {
        if (this.checkResult == null) {
            this.checkResult = new EntityCheckResult();
        }
        return this.checkResult;
    }

    public void setCheckResult(EntityCheckResult checkResult) {
        this.checkResult = checkResult;
    }

    public void addCodeToKey(String code, String key) {
        if (this.codeToKey == null) {
            this.codeToKey = new HashMap<String, String>(16);
        }
        this.codeToKey.put(code, key);
    }

    public void addUpdateResult(EntityUpdateResult updateResult) {
        this.getCodeToKey().putAll(updateResult.getCodeToKey());
        EntityCheckResult checkResult = updateResult.getCheckResult();
        if (checkResult == null) {
            return;
        }
        this.getCheckResult().getFailInfos().addAll(checkResult.getFailInfos());
    }
}

