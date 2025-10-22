/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.domain;

import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CalibreDataRegion
implements Comparable<CalibreDataRegion> {
    private CalibreDataDO calibreData;
    private List<String> entityKeys = new ArrayList<String>();
    private List<CalibreDataRegion> childRegions = new ArrayList<CalibreDataRegion>();
    private boolean merged = false;
    private int index;

    public CalibreDataRegion(CalibreDataDO calibreData) {
        this.calibreData = calibreData;
    }

    public CalibreDataDO getCalibreData() {
        return this.calibreData;
    }

    public void setCalibreData(CalibreDataDO calibreData) {
        this.calibreData = calibreData;
    }

    public List<String> getEntityKeys() {
        return this.entityKeys;
    }

    public String getKey() {
        return this.calibreData.getKey();
    }

    public List<CalibreDataRegion> getChildRegions() {
        return this.childRegions;
    }

    public void mergeEntityKeys() {
        if (this.merged || this.childRegions.isEmpty()) {
            return;
        }
        for (CalibreDataRegion childRegion : this.childRegions) {
            childRegion.mergeEntityKeys();
        }
        HashSet<String> filteredKeys = null;
        if (this.calibreData.getValue().getExpression() != null) {
            filteredKeys = new HashSet<String>(this.entityKeys);
        }
        HashSet<String> mergedEntityKeys = new HashSet<String>();
        for (CalibreDataRegion childRegion : this.childRegions) {
            for (String entityKey : childRegion.getEntityKeys()) {
                if (filteredKeys != null && !filteredKeys.contains(entityKey)) continue;
                mergedEntityKeys.add(entityKey);
            }
        }
        this.entityKeys.clear();
        this.entityKeys.addAll(mergedEntityKeys);
        this.merged = true;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int compareTo(CalibreDataRegion o) {
        int l = this.index - o.index;
        if (l > 0) {
            return 1;
        }
        if (l < 0) {
            return -1;
        }
        return 0;
    }

    public String toString() {
        return "CalibreDataRegion [calibreData=" + this.calibreData + ", entityKeys=" + this.entityKeys + "]";
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.calibreData == null ? 0 : this.calibreData.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        CalibreDataRegion other = (CalibreDataRegion)obj;
        return !(this.calibreData == null ? other.calibreData != null : !this.calibreData.equals(other.calibreData));
    }
}

