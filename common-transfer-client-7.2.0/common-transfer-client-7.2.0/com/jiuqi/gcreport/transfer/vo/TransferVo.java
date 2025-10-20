/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.transfer.vo;

import java.util.List;
import javax.validation.constraints.NotNull;

public class TransferVo {
    @NotNull(message="\u5217\u9009\u8def\u5f84\u4e0d\u80fd\u4e3a\u7a7a\uff01")
    private @NotNull(message="\u5217\u9009\u8def\u5f84\u4e0d\u80fd\u4e3a\u7a7a\uff01") String path;
    @NotNull(message="\u5217\u9009\u7684\u5217\u4e0d\u80fd\u4e3a\u7a7a\uff01")
    private @NotNull(message="\u5217\u9009\u7684\u5217\u4e0d\u80fd\u4e3a\u7a7a\uff01") List<String> selectColumnKeys;
    private Boolean usingUser;

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getSelectColumnKeys() {
        return this.selectColumnKeys;
    }

    public void setSelectColumnKeys(List<String> selectColumnKeys) {
        this.selectColumnKeys = selectColumnKeys;
    }

    public boolean getUsingUser() {
        return this.usingUser == null ? true : this.usingUser;
    }

    public void setUsingUser(Boolean usingUser) {
        this.usingUser = usingUser;
    }
}

