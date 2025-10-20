/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.trace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResourceDescriptor {
    private String catagory;
    private String type;
    private List<Class<?>> classes;
    private List<String> actions = new ArrayList<String>();

    public ResourceDescriptor() {
        this.classes = new ArrayList();
    }

    public ResourceDescriptor(String catagory, String type, Class<?> klass) {
        this();
        this.catagory = catagory;
        this.type = type;
        this.classes.add(klass);
    }

    public ResourceDescriptor(String catagory, String type, Collection<Class<?>> classes) {
        this();
        this.catagory = catagory;
        this.type = type;
        this.classes.addAll(classes);
    }

    public ResourceDescriptor(String catagory, String type, Class<?> klass, String ... actions) {
        this();
        this.catagory = catagory;
        this.type = type;
        this.classes.add(klass);
        for (String action : actions) {
            this.actions.add(action);
        }
    }

    public String getCatagory() {
        return this.catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getActions() {
        return this.actions;
    }

    public List<Class<?>> getClasses() {
        return this.classes;
    }

    public boolean isInstance(Object obj) {
        for (Class<?> klass : this.classes) {
            if (!klass.isInstance(obj)) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.catagory).append('.').append(this.type).append('.').append(this.actions).append('.').append(this.classes);
        return buffer.toString();
    }
}

