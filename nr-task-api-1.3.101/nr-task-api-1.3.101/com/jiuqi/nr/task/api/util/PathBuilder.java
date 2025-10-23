/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.util;

import java.util.Iterator;
import java.util.LinkedList;

public class PathBuilder {
    private final LinkedList<String> value = new LinkedList();
    private static final String SPLIT = " / ";

    public PathBuilder append(String str) {
        this.value.add(str);
        return this;
    }

    public PathBuilder appendFirst(String str) {
        this.value.addFirst(str);
        return this;
    }

    public PathBuilder appendLast(String str) {
        this.value.addLast(str);
        return this;
    }

    public String toString() {
        Iterator it = this.value.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while (true) {
            sb.append((String)it.next());
            if (!it.hasNext()) {
                return sb.toString();
            }
            sb.append(SPLIT);
        }
    }
}

