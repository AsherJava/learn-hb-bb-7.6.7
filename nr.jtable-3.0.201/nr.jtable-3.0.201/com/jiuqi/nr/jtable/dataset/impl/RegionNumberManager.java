/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.jtable.params.base.RegionNumber;
import java.util.ArrayList;
import java.util.List;

public class RegionNumberManager {
    private final RegionNumber regionNumber;
    private int numberIndex;
    private int currNumber;
    private List<String> numberList = new ArrayList<String>();

    public RegionNumberManager(RegionNumber regionNumber) {
        this.regionNumber = regionNumber;
        this.currNumber = 0;
        this.resetNumber();
    }

    public RegionNumber getRegionNumber() {
        return this.regionNumber;
    }

    public String next() {
        if (this.currNumber < 0 || this.currNumber >= this.numberList.size()) {
            return "1";
        }
        return this.numberList.get(this.currNumber++);
    }

    public void setNumberStr(String numberStr) {
        if (StringUtils.isEmpty((String)numberStr)) {
            this.numberList.add(this.numberIndex + "");
            this.numberIndex += this.regionNumber == null ? 1 : this.regionNumber.getIncrement();
        } else {
            this.numberList.add("");
            this.numberIndex = this.regionNumber == null ? 1 : this.regionNumber.getStart();
        }
    }

    public void resetNumber() {
        this.numberIndex = this.regionNumber == null ? 1 : this.regionNumber.getStart();
    }
}

