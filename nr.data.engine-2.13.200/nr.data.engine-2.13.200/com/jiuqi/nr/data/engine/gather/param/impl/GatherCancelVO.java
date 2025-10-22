/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather.param.impl;

import java.io.Serializable;

public class GatherCancelVO
implements Serializable {
    private String code;
    private Param param;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Param getParam() {
        return this.param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    public static class Param
    implements Serializable {
        private int count;

        public Param(int count) {
            this.count = count;
        }

        public int getCount() {
            return this.count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}

