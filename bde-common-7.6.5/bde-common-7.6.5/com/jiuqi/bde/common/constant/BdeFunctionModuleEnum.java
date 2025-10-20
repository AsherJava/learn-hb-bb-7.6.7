/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

import com.jiuqi.bde.common.constant.SourceTypeEnum;

public enum BdeFunctionModuleEnum {
    BIZMODEL("BIZMODEL", "\u4e1a\u52a1\u6a21\u578b"),
    FETCHRESULTCLEAN("FETCHRESULTCLEAN", "\u7ed3\u679c\u6e05\u7406\u8868"),
    HOTSPOTDATACLEAN("HOTSPOTDATACLEAN", "\u7ed3\u679c\u70ed\u70b9\u6570\u636e\u8868");

    private String code;
    private String name;

    private BdeFunctionModuleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullModuleName() {
        return SourceTypeEnum.BDE.getCode() + "-" + this.name;
    }
}

