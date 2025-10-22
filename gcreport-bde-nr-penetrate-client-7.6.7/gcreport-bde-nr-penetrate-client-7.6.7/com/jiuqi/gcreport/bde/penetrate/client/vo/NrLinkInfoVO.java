/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.penetrate.client.vo;

public class NrLinkInfoVO {
    private String linkId;
    private String regionId;
    private String zbid;
    private String zbtitle;
    private Object value;

    public String getLinkId() {
        return this.linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getZbid() {
        return this.zbid;
    }

    public void setZbid(String zbid) {
        this.zbid = zbid;
    }

    public String getZbtitle() {
        return this.zbtitle;
    }

    public void setZbtitle(String zbtitle) {
        this.zbtitle = zbtitle;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String toString() {
        return "NrLinkInfoVO [linkId=" + this.linkId + ", regionId=" + this.regionId + ", zbid=" + this.zbid + ", zbtitle=" + this.zbtitle + ", value=" + this.value + "]";
    }
}

