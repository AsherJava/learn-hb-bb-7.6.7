/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

import com.jiuqi.nr.migration.transferdata.bean.CheckErrorDesc;
import java.util.ArrayList;
import java.util.List;

public class TransCheckError {
    private String dataTime;
    private String unitCode;
    private List<CheckErrorDesc> checkErrorDescList = new ArrayList<CheckErrorDesc>();

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public List<CheckErrorDesc> getCheckErrorDescList() {
        return this.checkErrorDescList;
    }

    public void setCheckErrorDescList(List<CheckErrorDesc> checkErrorDescList) {
        this.checkErrorDescList = checkErrorDescList;
    }

    public void addCheckErrorDescList(CheckErrorDesc checkErrorDescList) {
        this.checkErrorDescList.add(checkErrorDescList);
    }
}

