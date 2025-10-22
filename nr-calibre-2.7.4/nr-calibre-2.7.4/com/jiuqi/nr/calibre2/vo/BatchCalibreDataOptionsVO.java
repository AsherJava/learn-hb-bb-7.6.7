/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.vo;

import com.jiuqi.nr.calibre2.domain.BatchCalibreDataOptionsDTO;
import java.util.List;

public class BatchCalibreDataOptionsVO
extends BatchCalibreDataOptionsDTO {
    private int moveWay;
    private boolean all;
    private List<String> exclude;

    public int getMoveWay() {
        return this.moveWay;
    }

    public void setMoveWay(int moveWay) {
        this.moveWay = moveWay;
    }

    public boolean isAll() {
        return this.all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public List<String> getExclude() {
        return this.exclude;
    }

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }
}

