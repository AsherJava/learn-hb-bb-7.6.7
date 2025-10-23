/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nrdt.parampacket.manage.config;

import org.springframework.stereotype.Component;

@Component
public class ParamPacketSuffixConfig {
    private String suffixName = ".nvdata";

    public String getSuffixName() {
        return this.suffixName;
    }

    public void setSuffixName(String suffixName) {
        this.suffixName = suffixName;
    }
}

