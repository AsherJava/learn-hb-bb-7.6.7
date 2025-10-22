/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.fielddatacrud.SaveRes
 */
package com.jiuqi.nr.data.text.param;

import com.jiuqi.nr.data.text.DataFileSaveRes;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.fielddatacrud.SaveRes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class DataFileSaveResImpl
implements DataFileSaveRes {
    private Set<String> saveDw;
    private Set<String> noPermissionDw;
    private Set<String> failDw;
    private Map<String, ReturnRes> failRes = new HashMap<String, ReturnRes>();
    private Map<String, SaveRes> saveRes = new HashMap<String, SaveRes>();
    private int importCount = 0;
    private boolean isAddFail = false;

    public Set<String> getSaveDw() {
        if (CollectionUtils.isEmpty(this.saveDw)) {
            this.saveDw = new HashSet<String>();
        }
        return this.saveDw;
    }

    public Set<String> getNoPermissionDw() {
        if (CollectionUtils.isEmpty(this.noPermissionDw)) {
            this.noPermissionDw = new HashSet<String>();
        }
        return this.noPermissionDw;
    }

    public Set<String> getFailDw() {
        if (CollectionUtils.isEmpty(this.failDw)) {
            this.failDw = new HashSet<String>();
        }
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

    @Override
    public int getImportCount() {
        return this.importCount;
    }

    public void setAddFail(boolean addFail) {
        this.isAddFail = addFail;
    }

    @Override
    public boolean isAddFail() {
        return this.isAddFail;
    }

    public Map<String, SaveRes> getSaveRes() {
        return this.saveRes;
    }

    public void dealResult() {
        if (!CollectionUtils.isEmpty(this.saveRes)) {
            HashSet<String> saveDwSet = new HashSet<String>();
            HashSet<String> noPermissionDwSet = new HashSet<String>();
            HashSet<String> failDwSet = new HashSet<String>();
            for (String tableCode : this.saveRes.keySet()) {
                SaveRes res = this.saveRes.get(tableCode);
                this.importCount += res.getCount();
                failDwSet.addAll(res.getFailDw());
                noPermissionDwSet.addAll(res.getNoPermissionDw());
                noPermissionDwSet.removeAll(failDwSet);
                saveDwSet.addAll(res.getSaveDw());
                saveDwSet.removeAll(failDwSet);
                saveDwSet.removeAll(noPermissionDwSet);
                this.failRes.putAll(res.getFailMessages());
            }
            this.saveDw = saveDwSet;
            this.noPermissionDw = noPermissionDwSet;
            this.failDw = failDwSet;
        }
    }
}

