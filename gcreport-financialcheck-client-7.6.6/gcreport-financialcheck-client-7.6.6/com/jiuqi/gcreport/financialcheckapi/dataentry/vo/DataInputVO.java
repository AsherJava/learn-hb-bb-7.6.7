/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.dataentry.vo;

import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO;
import java.util.Collections;
import java.util.List;

public class DataInputVO {
    private List<GcRelatedItemVO> allVchrData = Collections.emptyList();
    private Integer allVchrTotal = 0;
    private List<GcRelatedItemVO> uncheckVchrData = Collections.emptyList();
    private Integer uncheckVchrTotal = 0;
    private List<GcRelatedItemVO> checkedVchrData = Collections.emptyList();
    private Integer checkedVchrTotal = 0;
    private List<GcRelatedItemVO> oppUncheckedData = Collections.emptyList();
    private Integer oppUncheckedTotal = 0;

    public List<GcRelatedItemVO> getAllVchrData() {
        return this.allVchrData;
    }

    public void setAllVchrData(List<GcRelatedItemVO> allVchrData) {
        this.allVchrData = allVchrData;
    }

    public Integer getAllVchrTotal() {
        return this.allVchrTotal;
    }

    public void setAllVchrTotal(Integer allVchrTotal) {
        this.allVchrTotal = allVchrTotal;
    }

    public List<GcRelatedItemVO> getUncheckVchrData() {
        return this.uncheckVchrData;
    }

    public void setUncheckVchrData(List<GcRelatedItemVO> uncheckVchrData) {
        this.uncheckVchrData = uncheckVchrData;
    }

    public Integer getUncheckVchrTotal() {
        return this.uncheckVchrTotal;
    }

    public void setUncheckVchrTotal(Integer uncheckVchrTotal) {
        this.uncheckVchrTotal = uncheckVchrTotal;
    }

    public List<GcRelatedItemVO> getCheckedVchrData() {
        return this.checkedVchrData;
    }

    public void setCheckedVchrdData(List<GcRelatedItemVO> checkedVchrData) {
        this.checkedVchrData = checkedVchrData;
    }

    public Integer getCheckedVchrTotal() {
        return this.checkedVchrTotal;
    }

    public void setCheckedVchrTotal(Integer checkedVchrTotal) {
        this.checkedVchrTotal = checkedVchrTotal;
    }

    public List<GcRelatedItemVO> getOppUncheckedData() {
        return this.oppUncheckedData;
    }

    public void setOppUncheckedData(List<GcRelatedItemVO> oppUncheckedData) {
        this.oppUncheckedData = oppUncheckedData;
    }

    public Integer getOppUncheckedTotal() {
        return this.oppUncheckedTotal;
    }

    public void setOppUncheckedTotal(Integer oppUncheckedTotal) {
        this.oppUncheckedTotal = oppUncheckedTotal;
    }
}

