/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveResItem;
import java.util.ArrayList;
import java.util.List;

public class SaveReturnRes
extends ReturnRes {
    private List<SaveResItem> saveResItems;

    public List<SaveResItem> getSaveResItems() {
        if (this.saveResItems == null) {
            this.saveResItems = new ArrayList<SaveResItem>();
        }
        return this.saveResItems;
    }

    public SaveReturnRes() {
    }

    public SaveReturnRes(ReturnRes returnRes) {
        this.code = returnRes.code;
        this.message = returnRes.message;
    }

    public void setSaveResItems(List<SaveResItem> saveResItems) {
        this.saveResItems = saveResItems;
    }

    public static SaveReturnRes build(int code, String message) {
        SaveReturnRes res = new SaveReturnRes();
        res.setCode(code);
        res.setMessage(message);
        return res;
    }

    public static SaveReturnRes ok(String message) {
        SaveReturnRes res = new SaveReturnRes();
        res.setCode(0);
        res.setMessage(message);
        return res;
    }
}

