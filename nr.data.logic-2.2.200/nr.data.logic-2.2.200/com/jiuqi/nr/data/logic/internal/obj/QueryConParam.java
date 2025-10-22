/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.obj;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class QueryConParam {
    private final Map<Integer, Boolean> checkTypes;
    private final Integer desCheckStateCode;
    private final String ckdKeyWordPattern;
    private final Boolean haveCKD;

    public QueryConParam(Map<Integer, Boolean> checkTypes, Integer desCheckStateCode, String ckdKeyWordPattern) {
        this.checkTypes = checkTypes;
        this.desCheckStateCode = desCheckStateCode;
        this.ckdKeyWordPattern = ckdKeyWordPattern;
        this.haveCKD = this.initHaveCKD();
    }

    private Boolean initHaveCKD() {
        Optional<Boolean> any = this.checkTypes.values().stream().filter(Objects::nonNull).findAny();
        return any.orElse(null);
    }

    public Map<Integer, Boolean> getCheckTypes() {
        return this.checkTypes;
    }

    public Integer getDesCheckStateCode() {
        return this.desCheckStateCode;
    }

    public String getCkdKeyWordPattern() {
        return this.ckdKeyWordPattern;
    }

    public Boolean getHaveCKD() {
        return this.haveCKD;
    }
}

