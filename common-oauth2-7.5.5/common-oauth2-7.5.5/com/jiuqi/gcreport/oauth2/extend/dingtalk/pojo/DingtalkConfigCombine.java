/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.jiuqi.gcreport.oauth2.extend.dingtalk.pojo;

import com.jiuqi.gcreport.oauth2.extend.dingtalk.DingtalkCertifyExtInfo;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DingtalkConfigCombine {
    private NvwaCertify nvwaCertify;
    private DingtalkCertifyExtInfo extInfo;

    public NvwaCertify getNvwaCertify() {
        return this.nvwaCertify;
    }

    public void setNvwaCertify(NvwaCertify nvwaCertify) {
        this.nvwaCertify = nvwaCertify;
    }

    public DingtalkCertifyExtInfo getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(DingtalkCertifyExtInfo extInfo) {
        this.extInfo = extInfo;
    }

    public DingtalkConfigCombine() {
    }

    public DingtalkConfigCombine(NvwaCertify nvwaCertify, DingtalkCertifyExtInfo extInfo) {
        this.nvwaCertify = nvwaCertify;
        this.extInfo = extInfo;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

