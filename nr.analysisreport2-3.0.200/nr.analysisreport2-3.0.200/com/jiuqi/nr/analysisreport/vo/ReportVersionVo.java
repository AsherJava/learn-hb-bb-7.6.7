/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.vo;

import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;

public class ReportVersionVo
extends ReportBaseVO {
    String name;
    String content;
    String arcKey;
    String versionKey;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArcKey() {
        return this.arcKey;
    }

    public void setArcKey(String arcKey) {
        this.arcKey = arcKey;
    }

    public String getVersionKey() {
        return this.versionKey;
    }

    public void setVersionKey(String versionkey) {
        this.versionKey = versionkey;
    }
}

