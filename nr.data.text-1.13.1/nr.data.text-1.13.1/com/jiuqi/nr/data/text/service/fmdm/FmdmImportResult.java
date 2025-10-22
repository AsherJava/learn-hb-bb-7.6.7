/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.Result
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.text.service.fmdm;

import com.jiuqi.nr.data.common.service.Result;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FmdmImportResult
implements Result {
    private String dwDimName;
    private boolean addSuccess;
    private final Set<String> addSuccessfulUnits = new HashSet<String>();
    private final Set<String> addFailedUnits = new HashSet<String>();
    private boolean updateSuccess;
    private final Set<String> updateSuccessUnits = new HashSet<String>();
    private final Set<DimensionCombination> noAccessDims = new HashSet<DimensionCombination>();

    public String getMessage() {
        StringBuilder message = new StringBuilder("\u5bfc\u5165\u5b8c\u6210\uff01");
        if (!this.addSuccessfulUnits.isEmpty() || !this.addFailedUnits.isEmpty()) {
            message.append("\u5355\u4f4d\u65b0\u589e").append(this.addSuccess ? "\u6210\u529f" : "\u5931\u8d25").append("\uff1b\u5269\u4f59\u7ef4\u5ea6");
        }
        message.append("\u5c01\u9762\u6570\u636e\u66f4\u65b0").append(this.updateSuccess ? "\u6210\u529f" : "\u5931\u8d25").append("\u3002");
        if (!this.addSuccessfulUnits.isEmpty()) {
            message.append("\u65b0\u589e\u6210\u529f").append(this.addSuccessfulUnits.size()).append("\u5bb6\u5355\u4f4d\u3002");
        }
        if (!this.addFailedUnits.isEmpty()) {
            message.append("\u65b0\u589e\u5931\u8d25").append(this.addFailedUnits.size()).append("\u5bb6\u5355\u4f4d\u3002");
        }
        if (!this.updateSuccessUnits.isEmpty()) {
            message.append("\u66f4\u65b0\u6210\u529f").append(this.updateSuccessUnits.size()).append("\u5bb6\u5355\u4f4d\u3002");
        }
        return message.toString();
    }

    public List<String> getFailUnits() {
        HashSet<String> failUnits = new HashSet<String>(this.addFailedUnits);
        failUnits.addAll(this.noAccessDims.stream().map(e -> e.getValue(this.dwDimName).toString()).collect(Collectors.toList()));
        return new ArrayList<String>(failUnits);
    }

    public String getDwDimName() {
        return this.dwDimName;
    }

    public void setDwDimName(String dwDimName) {
        this.dwDimName = dwDimName;
    }

    public boolean isAddSuccess() {
        return this.addSuccess;
    }

    public void setAddSuccess(boolean addSuccess) {
        this.addSuccess = addSuccess;
    }

    public Set<String> getAddSuccessfulUnits() {
        return this.addSuccessfulUnits;
    }

    public Set<String> getAddFailedUnits() {
        return this.addFailedUnits;
    }

    public boolean isUpdateSuccess() {
        return this.updateSuccess;
    }

    public void setUpdateSuccess(boolean updateSuccess) {
        this.updateSuccess = updateSuccess;
    }

    public Set<String> getUpdateSuccessUnits() {
        return this.updateSuccessUnits;
    }

    public Set<DimensionCombination> getNoAccessDims() {
        return this.noAccessDims;
    }
}

