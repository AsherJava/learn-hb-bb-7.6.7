/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.ReturnRes
 */
package com.jiuqi.nr.fielddatacrud.impl.dto;

import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.fielddatacrud.SaveRes;
import java.util.Collection;
import java.util.Map;

public class SaveResDTO
implements SaveRes {
    private Collection<String> saveDw;
    private Collection<String> noPermissionDw;
    private Collection<String> failDw;
    private Map<String, ReturnRes> failRes;
    private int count;

    @Override
    public Collection<String> getSaveDw() {
        return this.saveDw;
    }

    public void setSaveDw(Collection<String> saveDw) {
        this.saveDw = saveDw;
    }

    @Override
    public Collection<String> getNoPermissionDw() {
        return this.noPermissionDw;
    }

    public void setNoPermissionDw(Collection<String> noPermissionDw) {
        this.noPermissionDw = noPermissionDw;
    }

    @Override
    public Collection<String> getFailDw() {
        return this.failDw;
    }

    @Override
    public ReturnRes getFailMessage(String dw) {
        return this.failRes.get(dw);
    }

    @Override
    public Map<String, ReturnRes> getFailMessages() {
        return this.failRes;
    }

    public void setFailDw(Collection<String> failDw) {
        this.failDw = failDw;
    }

    public void setFailMessage(Map<String, ReturnRes> failRes) {
        this.failRes = failRes;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

