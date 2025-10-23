/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.dto;

public class AutoCloseFill {
    private int type = 0;
    private int days = 0;
    private boolean automaticTermination = true;

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

    public boolean isAutomaticTermination() {
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

