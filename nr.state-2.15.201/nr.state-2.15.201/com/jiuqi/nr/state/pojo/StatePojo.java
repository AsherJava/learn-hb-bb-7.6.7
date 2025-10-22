/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.state.pojo;

import com.jiuqi.nr.state.pojo.StateEntites;
import java.io.Serializable;
import java.time.Instant;

public class StatePojo
extends StateEntites
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int state;
    private Instant dataTime;

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Instant getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(Instant dataTime) {
        this.dataTime = dataTime;
    }
}

