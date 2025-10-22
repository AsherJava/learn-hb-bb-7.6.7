/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.summary.define;

import java.util.HashMap;
import java.util.Map;

public class RelationInfo {
    private String srcTableName;
    private Map<String, String> relationFieldMap = new HashMap<String, String>();
    private String destTableName;
    private RelationType relationType;

    public String getSrcTableName() {
        return this.srcTableName;
    }

    public void setSrcTableName(String srcTableName) {
        this.srcTableName = srcTableName;
    }

    public String getDestTableName() {
        return this.destTableName;
    }

    public void setDestTableName(String destTableName) {
        this.destTableName = destTableName;
    }

    public RelationType getRelationType() {
        return this.relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public Map<String, String> getRelationFieldMap() {
        return this.relationFieldMap;
    }

    public static enum RelationType {
        R_1V1,
        R_1VN,
        R_NV1;

    }
}

