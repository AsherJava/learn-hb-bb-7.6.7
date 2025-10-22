/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.resource.bean;

public class UserAppContext {
    public static final String RIGOROUS_ENTITY = "rigorousEntity";
    protected boolean rigorousEntity;
    protected String defaultEntityKey;
    protected String orgTableKey;

    public boolean isRigorousEntity() {
        return this.rigorousEntity;
    }

    public void setRigorousEntity(boolean rigorousEntity) {
        this.rigorousEntity = rigorousEntity;
    }

    public String getDefaultEntityKey() {
        return this.defaultEntityKey;
    }

    public void setDefaultEntityKey(String defaultEntityKey) {
        this.defaultEntityKey = defaultEntityKey;
    }

    public String getOrgTableKey() {
        return this.orgTableKey;
    }

    public void setOrgTableKey(String orgTableKey) {
        this.orgTableKey = orgTableKey;
    }
}

