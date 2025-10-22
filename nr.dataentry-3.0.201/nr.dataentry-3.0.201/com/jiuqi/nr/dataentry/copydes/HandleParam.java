/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.copydes;

import com.jiuqi.nr.dataentry.copydes.CheckDesFmlObj;
import java.util.List;
import java.util.Map;

public class HandleParam {
    private String srcFormSchemeKey;
    private String dstFormSchemeKey;
    private String srcFmlSchemeKey;
    private String dstFmlSchemeKey;
    private List<CheckDesFmlObj> unsupportedSrcDes;
    private Map<String, List<CheckDesFmlObj>> unsupportedDstDesMap;
    private boolean updateUserTime;

    public String getDstFmlSchemeKey() {
        return this.dstFmlSchemeKey;
    }

    public void setDstFmlSchemeKey(String dstFmlSchemeKey) {
        this.dstFmlSchemeKey = dstFmlSchemeKey;
    }

    public String getDstFormSchemeKey() {
        return this.dstFormSchemeKey;
    }

    public void setDstFormSchemeKey(String dstFormSchemeKey) {
        this.dstFormSchemeKey = dstFormSchemeKey;
    }

    public String getSrcFmlSchemeKey() {
        return this.srcFmlSchemeKey;
    }

    public void setSrcFmlSchemeKey(String srcFmlSchemeKey) {
        this.srcFmlSchemeKey = srcFmlSchemeKey;
    }

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public Map<String, List<CheckDesFmlObj>> getUnsupportedDstDesMap() {
        return this.unsupportedDstDesMap;
    }

    public void setUnsupportedDstDesMap(Map<String, List<CheckDesFmlObj>> unsupportedDstDesMap) {
        this.unsupportedDstDesMap = unsupportedDstDesMap;
    }

    public List<CheckDesFmlObj> getUnsupportedSrcDes() {
        return this.unsupportedSrcDes;
    }

    public void setUnsupportedSrcDes(List<CheckDesFmlObj> unsupportedSrcDes) {
        this.unsupportedSrcDes = unsupportedSrcDes;
    }

    public boolean isUpdateUserTime() {
        return this.updateUserTime;
    }

    public void setUpdateUserTime(boolean updateUserTime) {
        this.updateUserTime = updateUserTime;
    }
}

