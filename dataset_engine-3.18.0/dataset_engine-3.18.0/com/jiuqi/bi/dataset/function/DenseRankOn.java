/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.function.RankOn;

public class DenseRankOn
extends RankOn {
    private static final long serialVersionUID = 1L;

    @Override
    public String name() {
        return "DS_DENSE_RANKON";
    }

    @Override
    public String title() {
        return "\u83b7\u53d6\u9650\u5b9a\u8868\u8fbe\u5f0f\u9650\u5b9a\u540e\u7684\u6570\u636e\u96c6\u5728\u539f\u59cb\u6570\u636e\u96c6\u4e2d\uff0c\u6307\u5b9a\u5ea6\u91cf\u7684\u6392\u540d\uff0c\u6392\u540d\u503c\u8fde\u7eed";
    }

    @Override
    boolean unbrokenRank() {
        return true;
    }
}

