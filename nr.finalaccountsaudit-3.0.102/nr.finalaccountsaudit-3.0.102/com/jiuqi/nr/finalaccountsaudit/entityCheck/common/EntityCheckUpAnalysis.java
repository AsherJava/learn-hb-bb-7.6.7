/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpRecord;
import java.util.List;
import java.util.Map;

public class EntityCheckUpAnalysis {
    private int _lastYCount;
    private int _currentCount;
    private int _decreaseCount;
    private int _increaseCount;
    private Map<String, List<EntityCheckUpRecord>> _decrease;
    private Map<String, List<EntityCheckUpRecord>> _increase;

    public int getLastYCount() {
        return this._lastYCount;
    }

    public void setLastYCount(int _lastYCount) {
        this._lastYCount = _lastYCount;
    }

    public int getCurrentCount() {
        return this._currentCount;
    }

    public void setCurrentCount(int _currentCount) {
        this._currentCount = _currentCount;
    }

    public int getDecreaseCount() {
        return this._decreaseCount;
    }

    public void setDecreaseCount(int _decreaseCount) {
        this._decreaseCount = _decreaseCount;
    }

    public int getIncreaseCount() {
        return this._increaseCount;
    }

    public void setIncreaseCount(int _increaseCount) {
        this._increaseCount = _increaseCount;
    }

    public Map<String, List<EntityCheckUpRecord>> getDecrease() {
        return this._decrease;
    }

    public void setDecrease(Map<String, List<EntityCheckUpRecord>> _decrease) {
        this._decrease = _decrease;
    }

    public Map<String, List<EntityCheckUpRecord>> getIncrease() {
        return this._increase;
    }

    public void setIncrease(Map<String, List<EntityCheckUpRecord>> _increase) {
        this._increase = _increase;
    }
}

