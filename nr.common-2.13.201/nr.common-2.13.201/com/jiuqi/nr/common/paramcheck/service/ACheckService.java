/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.paramcheck.service;

import com.jiuqi.nr.common.paramcheck.bean.ErrorParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ACheckService {
    private List<ErrorParam> results = new ArrayList<ErrorParam>();

    public abstract boolean execute() throws Exception;

    public abstract boolean fix(Object var1) throws Exception;

    public final void addErrorData(ErrorParam result) {
        Optional<ErrorParam> find = this.results.stream().filter(e -> result.getKey().equals(e.getKey())).findFirst();
        if (!find.isPresent()) {
            this.results.add(result);
        }
    }

    public final void removeErrorData(String key) {
        Optional<ErrorParam> find = this.results.stream().filter(e -> key.equals(e.getKey())).findFirst();
        if (find.isPresent()) {
            this.results.remove(find);
        }
    }

    public final void clearErrorData() {
        this.results = new ArrayList<ErrorParam>();
    }

    public final List<ErrorParam> getErrorData() {
        return this.results;
    }
}

