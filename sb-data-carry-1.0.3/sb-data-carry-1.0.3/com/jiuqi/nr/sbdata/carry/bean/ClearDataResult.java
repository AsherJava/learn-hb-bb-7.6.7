/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sbdata.carry.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ClearDataResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Set<String> success = new HashSet<String>();
    private Set<String> fail = new HashSet<String>();

    public Set<String> getSuccess() {
        this.success.removeAll(this.fail);
        return this.success;
    }

    public Set<String> getFail() {
        return this.fail;
    }
}

