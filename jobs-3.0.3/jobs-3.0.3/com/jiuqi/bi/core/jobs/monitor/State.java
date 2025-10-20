/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.monitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum State {
    RUNNING(0, "\u6b63\u5728\u8fd0\u884c"),
    FINISHED(1, "\u8fd0\u884c\u5b8c\u6bd5"),
    WAITING(-1, "\u7b49\u5f85\u8c03\u5ea6"),
    CANCELING(-2, "\u53d6\u6d88\u4e2d");

    private int value;
    private String title;

    private State(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static final State valueOf(int value) {
        for (State s : State.values()) {
            if (s.getValue() != value) continue;
            return s;
        }
        return null;
    }

    public static boolean isRunning(State state) {
        return state != FINISHED;
    }

    public static List<State> getAllStates() {
        ArrayList<State> states = new ArrayList<State>();
        states.addAll(Arrays.asList(State.values()));
        return states;
    }
}

