/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.parser.restriction;

import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.util.StringUtils;

public final class DSFieldInfo
implements Comparable<DSFieldInfo> {
    public final String dataSetName;
    public final String fieldName;
    public final DSField field;

    public DSFieldInfo(String dataSetName, DSField field) {
        if (dataSetName == null && field == null) {
            throw new IllegalArgumentException("\u6570\u636e\u96c6\u548c\u5b57\u6bb5\u540d\u4e0d\u80fd\u540c\u65f6\u4e3a\u7a7a\u3002");
        }
        this.dataSetName = dataSetName;
        this.fieldName = field == null ? null : field.getName();
        this.field = field;
    }

    public String toString() {
        if (this.dataSetName == null) {
            return this.fieldName;
        }
        return this.dataSetName + "." + this.fieldName;
    }

    public int hashCode() {
        int hash = this.dataSetName == null ? 0 : this.dataSetName.hashCode();
        return hash * 31 + (this.fieldName == null ? 0 : this.fieldName.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DSFieldInfo)) {
            return false;
        }
        DSFieldInfo that = (DSFieldInfo)obj;
        if (this.dataSetName == null) {
            return that.dataSetName == null;
        }
        return StringUtils.equals((String)this.dataSetName, (String)that.dataSetName) && StringUtils.equals((String)this.fieldName, (String)that.fieldName);
    }

    @Override
    public int compareTo(DSFieldInfo anotherInfo) {
        int c = this.strCompare(this.dataSetName, anotherInfo.dataSetName);
        if (c != 0) {
            return c;
        }
        return this.strCompare(this.fieldName, anotherInfo.fieldName);
    }

    private int strCompare(String s1, String s2) {
        if (s1 == s2) {
            return 0;
        }
        if (s1 == null) {
            return -1;
        }
        if (s2 == null) {
            return 1;
        }
        return s1.compareTo(s2);
    }
}

