/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.vo;

import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import java.util.HashMap;
import java.util.Map;

public class ReportGeneratorVO
extends ReportBaseVO
implements Cloneable {
    private String curTimeStamp;
    private String securityTitle;
    private String versionKey;
    private String arcKey;
    private String contents;
    private Map<String, Object> ext = new HashMap<String, Object>();
    private Boolean isScientific = false;
    private int xDPI = 96;
    private int yDPI = 96;

    public String getArcKey() {
        return this.arcKey;
    }

    public void setArcKey(String arcKey) {
        this.arcKey = arcKey;
    }

    public String getSecurityTitle() {
        return this.securityTitle;
    }

    public void setSecurityTitle(String securityTitle) {
        this.securityTitle = securityTitle;
    }

    public String getVersionKey() {
        return this.versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    public int getxDPI() {
        return this.xDPI;
    }

    public void setxDPI(int xDPI) {
        this.xDPI = xDPI;
    }

    public int getyDPI() {
        return this.yDPI;
    }

    public void setyDPI(int yDPI) {
        this.yDPI = yDPI;
    }

    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCurTimeStamp() {
        return this.curTimeStamp;
    }

    public void setCurTimeStamp(String curTimeStamp) {
        this.curTimeStamp = curTimeStamp;
    }

    public Map<String, Object> getExt() {
        if (this.ext == null) {
            this.ext = new HashMap<String, Object>();
        }
        return this.ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    public Boolean getScientific() {
        return this.isScientific;
    }

    public void setScientific(Boolean scientific) {
        this.isScientific = scientific;
    }
}

