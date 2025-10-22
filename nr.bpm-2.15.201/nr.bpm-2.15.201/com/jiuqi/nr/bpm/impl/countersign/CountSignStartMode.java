/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.countersign;

import java.io.Serializable;
import java.util.Set;

public class CountSignStartMode
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Set<String> actors;

    public Set<String> getActors() {
        return this.actors;
    }

    public void setActors(Set<String> actors) {
        this.actors = actors;
    }
}

