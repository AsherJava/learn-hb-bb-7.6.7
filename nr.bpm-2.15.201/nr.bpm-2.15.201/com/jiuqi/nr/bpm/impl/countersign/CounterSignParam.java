/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.countersign;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import java.io.Serializable;
import java.util.Set;

public class CounterSignParam
implements Serializable {
    private static final long serialVersionUID = -6007342145585785307L;
    private String[] specialSignUser;
    private String[] specialSignRole;
    private Integer signCount;
    private Boolean countSignAllUser;
    private String userOrRole;
    private String actionCode;
    private Set<String> actors;
    private boolean signStartMode;
    private String taskCode;
    private BusinessKey businessKey;

    public String[] getSpecialSignUser() {
        return this.specialSignUser;
    }

    public void setSpecialSignUser(String[] specialSignUser) {
        this.specialSignUser = specialSignUser;
    }

    public Integer getSignCount() {
        return this.signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Boolean isCountSignAllUser() {
        return this.countSignAllUser;
    }

    public void setCountSignAllUser(Boolean countSignAllUser) {
        this.countSignAllUser = countSignAllUser;
    }

    public String getUserOrRole() {
        return this.userOrRole;
    }

    public void setUserOrRole(String userOrRole) {
        this.userOrRole = userOrRole;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Set<String> getActors() {
        return this.actors;
    }

    public void setActors(Set<String> actors) {
        this.actors = actors;
    }

    public String[] getSpecialSignRole() {
        return this.specialSignRole;
    }

    public void setSpecialSignRole(String[] specialSignRole) {
        this.specialSignRole = specialSignRole;
    }

    public boolean isSignStartMode() {
        return this.signStartMode;
    }

    public void setSignStartMode(boolean signStartMode) {
        this.signStartMode = signStartMode;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public BusinessKey getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(BusinessKey businessKey) {
        this.businessKey = businessKey;
    }
}

