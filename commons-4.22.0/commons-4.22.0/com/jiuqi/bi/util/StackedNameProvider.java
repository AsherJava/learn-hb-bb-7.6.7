/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import com.jiuqi.bi.util.NameProvider;
import java.util.EmptyStackException;
import java.util.Stack;

public class StackedNameProvider {
    private Stack<NameProvider> providers = new Stack();

    public void beginNames() {
        this.providers.push(new NameProvider());
    }

    public void endNames() {
        this.providers.pop();
    }

    public String uniqueOf(String name) {
        if (this.providers.isEmpty()) {
            throw new EmptyStackException();
        }
        NameProvider provider = this.providers.peek();
        return provider.uniqueOf(name);
    }

    public String nameOf(String prefix) {
        if (this.providers.isEmpty()) {
            throw new EmptyStackException();
        }
        NameProvider provider = this.providers.peek();
        return provider.nameOf(prefix);
    }

    public void useName(String name) {
        if (this.providers.isEmpty()) {
            throw new EmptyStackException();
        }
        NameProvider provider = this.providers.peek();
        provider.useName(name);
    }
}

