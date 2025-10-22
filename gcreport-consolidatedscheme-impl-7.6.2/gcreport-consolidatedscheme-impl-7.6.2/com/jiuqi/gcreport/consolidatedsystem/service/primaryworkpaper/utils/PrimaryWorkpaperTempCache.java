/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 */
package com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.utils;

import com.jiuqi.nr.definition.facade.DataLinkDefine;
import java.util.Map;
import java.util.Set;

public class PrimaryWorkpaperTempCache {
    private Map<String, String> schemeId2TaskIdMap;
    private Map<String, String> zbId2SchemeIdMap;
    private Map<String, DataLinkDefine> zbId2DataLinkDefineMap;
    private Map<String, String> subjectTitle2CodeMap;
    private Set<String> subjectCodeSet;

    public Map<String, String> getSchemeId2TaskIdMap() {
        return this.schemeId2TaskIdMap;
    }

    public void setSchemeId2TaskIdMap(Map<String, String> schemeId2TaskIdMap) {
        this.schemeId2TaskIdMap = schemeId2TaskIdMap;
    }

    public Map<String, String> getZbId2SchemeIdMap() {
        return this.zbId2SchemeIdMap;
    }

    public void setZbId2SchemeIdMap(Map<String, String> zbId2SchemeIdMap) {
        this.zbId2SchemeIdMap = zbId2SchemeIdMap;
    }

    public Map<String, DataLinkDefine> getZbId2DataLinkDefineMap() {
        return this.zbId2DataLinkDefineMap;
    }

    public void setZbId2DataLinkDefineMap(Map<String, DataLinkDefine> zbId2DataLinkDefineMap) {
        this.zbId2DataLinkDefineMap = zbId2DataLinkDefineMap;
    }

    public Map<String, String> getSubjectTitle2CodeMap() {
        return this.subjectTitle2CodeMap;
    }

    public void setSubjectTitle2CodeMap(Map<String, String> subjectTitle2CodeMap) {
        this.subjectTitle2CodeMap = subjectTitle2CodeMap;
    }

    public Set<String> getSubjectCodeSet() {
        return this.subjectCodeSet;
    }

    public void setSubjectCodeSet(Set<String> subjectCodeSet) {
        this.subjectCodeSet = subjectCodeSet;
    }

    public void clear() {
        this.clearMap(this.schemeId2TaskIdMap);
        this.clearMap(this.zbId2SchemeIdMap);
        this.clearMap(this.zbId2DataLinkDefineMap);
        this.clearMap(this.subjectTitle2CodeMap);
    }

    private void clearMap(Map map) {
        if (map != null) {
            map.clear();
        }
    }
}

