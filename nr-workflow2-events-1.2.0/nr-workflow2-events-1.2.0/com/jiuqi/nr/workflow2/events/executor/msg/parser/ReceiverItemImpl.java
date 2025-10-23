/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.executor.msg.parser;

import com.jiuqi.nr.workflow2.events.executor.msg.parser.ReceiverItem;
import java.util.List;

public class ReceiverItemImpl
implements ReceiverItem {
    private String strategy;
    private List<String> users;
    private List<String> roles;

    @Override
    public String getStrategy() {
        return this.strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    @Override
    public List<String> getUsers() {
        return this.users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

