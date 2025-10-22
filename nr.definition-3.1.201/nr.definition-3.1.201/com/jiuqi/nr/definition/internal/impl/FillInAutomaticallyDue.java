/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl;

import java.io.Serializable;

public class FillInAutomaticallyDue
implements Serializable {
    private static final long serialVersionUID = 4325106767838103951L;
    private int type;
    private int days;
    private boolean automaticTermination = true;

    public FillInAutomaticallyDue() {
    }

    public FillInAutomaticallyDue(int dataType) {
        this.type = dataType;
    }

    public FillInAutomaticallyDue(int dataType, int days, boolean automaticTermination) {
        this.type = dataType;
        this.days = days;
        this.automaticTermination = automaticTermination;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDays() {
        return this.days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public boolean getAutomaticTermination() {
        return this.automaticTermination;
    }

    public void setAutomaticTermination(boolean automaticTermination) {
        this.automaticTermination = automaticTermination;
    }

    public static enum Type {
        DEFAULT(0),
        CLOSE(1),
        NATURAL(2),
        WORKING(3);

        private int value;

        private Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

