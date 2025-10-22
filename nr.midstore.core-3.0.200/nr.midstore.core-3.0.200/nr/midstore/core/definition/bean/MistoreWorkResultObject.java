/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nr.midstore.core.definition.bean.MistoreWorkFailInfo;
import nr.midstore.core.definition.bean.MistoreWorkUnitInfo;

public class MistoreWorkResultObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nrPeriodCode;
    private String nrPeriodTitle;
    private String midstorePeriodCode;
    private int unitCount;
    private List<MistoreWorkFailInfo> failInfoList;
    private Map<String, MistoreWorkFailInfo> failInfos;
    private Map<String, MistoreWorkUnitInfo> failUnits;
    private Map<String, MistoreWorkUnitInfo> successUnits;
    private Set<String> midstoreTableUnits;

    public void addFailInfo(MistoreWorkFailInfo info) {
        if (!this.getFailInfos().containsKey(info.getMessage())) {
            this.getFailInfos().put(info.getMessage(), info);
            this.getFailInfoList().add(info);
        }
    }

    public Map<String, MistoreWorkFailInfo> getFailInfos() {
        if (this.failInfos == null) {
            this.failInfos = new HashMap<String, MistoreWorkFailInfo>();
        }
        return this.failInfos;
    }

    public void setFailInfos(Map<String, MistoreWorkFailInfo> failInfos) {
        this.failInfos = failInfos;
    }

    public Map<String, MistoreWorkUnitInfo> getFailUnits() {
        if (this.failUnits == null) {
            this.failUnits = new HashMap<String, MistoreWorkUnitInfo>();
        }
        return this.failUnits;
    }

    public void setFailUnits(Map<String, MistoreWorkUnitInfo> failUnits) {
        this.failUnits = failUnits;
    }

    public Map<String, MistoreWorkUnitInfo> getSuccessUnits() {
        if (this.successUnits == null) {
            this.successUnits = new HashMap<String, MistoreWorkUnitInfo>();
        }
        return this.successUnits;
    }

    public void setSuccessUnits(Map<String, MistoreWorkUnitInfo> successUnits) {
        this.successUnits = successUnits;
    }

    public List<MistoreWorkFailInfo> getFailInfoList() {
        if (this.failInfoList == null) {
            this.failInfoList = new ArrayList<MistoreWorkFailInfo>();
        }
        return this.failInfoList;
    }

    public void setFailInfoList(List<MistoreWorkFailInfo> failInfoList) {
        this.failInfoList = failInfoList;
    }

    public String getNrPeriodCode() {
        return this.nrPeriodCode;
    }

    public void setNrPeriodCode(String nrPeriodCode) {
        this.nrPeriodCode = nrPeriodCode;
    }

    public String getMidstorePeriodCode() {
        return this.midstorePeriodCode;
    }

    public void setMidstorePeriodCode(String midstorePeriodCode) {
        this.midstorePeriodCode = midstorePeriodCode;
    }

    public String getNrPeriodTitle() {
        return this.nrPeriodTitle;
    }

    public void setNrPeriodTitle(String nrPeriodTitle) {
        this.nrPeriodTitle = nrPeriodTitle;
    }

    public int getUnitCount() {
        return this.unitCount;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }

    public Set<String> getMidstoreTableUnits() {
        if (this.midstoreTableUnits == null) {
            this.midstoreTableUnits = new HashSet<String>();
        }
        return this.midstoreTableUnits;
    }

    public void setMidstoreTableUnits(Set<String> midstoreTableUnits) {
        this.midstoreTableUnits = midstoreTableUnits;
    }
}

