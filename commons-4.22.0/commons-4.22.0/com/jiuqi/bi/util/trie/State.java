/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.trie;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class State {
    protected final int depth;
    private State failure = null;
    private Set<Integer> emits = null;
    private Map<Character, State> success = new TreeMap<Character, State>();
    private int index;

    public State() {
        this(0);
    }

    public State(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return this.depth;
    }

    public void addEmit(int keyword) {
        if (this.emits == null) {
            this.emits = new TreeSet(Collections.reverseOrder());
        }
        this.emits.add(keyword);
    }

    public Integer getLargestValueId() {
        if (this.emits == null || this.emits.size() == 0) {
            return null;
        }
        return this.emits.iterator().next();
    }

    public void addEmit(Collection<Integer> emits) {
        for (int emit : emits) {
            this.addEmit(emit);
        }
    }

    public Collection<Integer> emit() {
        return this.emits == null ? Collections.emptyList() : this.emits;
    }

    public boolean isAcceptable() {
        return this.depth > 0 && this.emits != null;
    }

    public State failure() {
        return this.failure;
    }

    public void setFailure(State failState, int[] fail) {
        this.failure = failState;
        fail[this.index] = failState.index;
    }

    private State nextState(Character character, boolean ignoreRootState) {
        State nextState = this.success.get(character);
        if (!ignoreRootState && nextState == null && this.depth == 0) {
            nextState = this;
        }
        return nextState;
    }

    public State nextState(Character character) {
        return this.nextState(character, false);
    }

    public State nextStateIgnoreRootState(Character character) {
        return this.nextState(character, true);
    }

    public State addState(Character character) {
        State nextState = this.nextStateIgnoreRootState(character);
        if (nextState == null) {
            nextState = new State(this.depth + 1);
            this.success.put(character, nextState);
        }
        return nextState;
    }

    public Collection<State> getStates() {
        return this.success.values();
    }

    public Collection<Character> getTransitions() {
        return this.success.keySet();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("State{");
        sb.append("depth=").append(this.depth);
        sb.append(", ID=").append(this.index);
        sb.append(", emits=").append(this.emits);
        sb.append(", success=").append(this.success.keySet());
        sb.append(", failureID=").append(this.failure == null ? "-1" : Integer.valueOf(this.failure.index));
        sb.append(", failure=").append(this.failure);
        sb.append('}');
        return sb.toString();
    }

    public Map<Character, State> getSuccess() {
        return this.success;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

