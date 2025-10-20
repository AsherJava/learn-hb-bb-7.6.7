/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.script;

import com.jiuqi.bi.script.IScriptObject;

public abstract class AbstractScriptObject
implements IScriptObject {
    @Override
    public void delProperty(String name) {
    }

    @Override
    public Object getProperty(String name) {
        return null;
    }

    @Override
    public String[] getPropertyNames() {
        return new String[0];
    }

    @Override
    public boolean hasProperty(String name) {
        return false;
    }

    @Override
    public void setProperty(String name, Object value) {
    }

    @Override
    public String[] getFunctionNames() {
        return new String[0];
    }

    @Override
    public boolean hasFunction(String name) {
        return false;
    }

    @Override
    public Object invoke(String name, Object[] args) {
        return null;
    }
}

