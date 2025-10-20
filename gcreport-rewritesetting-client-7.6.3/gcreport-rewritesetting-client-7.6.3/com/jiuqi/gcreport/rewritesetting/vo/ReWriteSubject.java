/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 */
package com.jiuqi.gcreport.rewritesetting.vo;

import com.jiuqi.bi.util.ArrayKey;
import java.util.HashSet;
import java.util.Set;

public class ReWriteSubject {
    private String systemId;
    private ArrayKey zbCode;
    private Integer orient;
    private boolean fromPrimaryWpSetting;
    private Set<String> originSubjectCodeSet;
    private Set<String> subjectCodeSet;

    public ReWriteSubject(String systemId, ArrayKey zbCode, Integer orient, String subjectCode) {
        this(systemId, zbCode, orient, new HashSet<String>(4));
        this.originSubjectCodeSet.add(subjectCode);
        this.fromPrimaryWpSetting = false;
    }

    public ReWriteSubject(String systemId, ArrayKey zbCode, Integer orient, Set<String> originSubjectCodeSet) {
        this.systemId = systemId;
        this.zbCode = zbCode;
        this.orient = orient;
        this.originSubjectCodeSet = originSubjectCodeSet;
        this.fromPrimaryWpSetting = true;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public ArrayKey getZbCode() {
        return this.zbCode;
    }

    public boolean isFromPrimaryWpSetting() {
        return this.fromPrimaryWpSetting;
    }

    public String generateDebugInfo() {
        String message = this.fromPrimaryWpSetting ? "\u6307\u6807\u6765\u81ea\u4e3b\u8868\u578b\u5de5\u4f5c\u5e95\u7a3f\u8bbe\u7f6e" : "\u6307\u6807\u6765\u81ea\u4f53\u7cfb\u79d1\u76ee";
        return message;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Set<String> getOriginSubjectCodeSet() {
        return this.originSubjectCodeSet;
    }

    public Set<String> getSubjectCodeSet() {
        return this.subjectCodeSet;
    }

    public void setSubjectCodeSet(Set<String> subjectCodeSet) {
        this.subjectCodeSet = subjectCodeSet;
    }
}

