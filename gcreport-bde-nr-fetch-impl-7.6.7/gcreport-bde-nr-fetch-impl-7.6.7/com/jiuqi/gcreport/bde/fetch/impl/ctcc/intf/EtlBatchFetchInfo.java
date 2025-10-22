/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.bde.fetch.impl.ctcc.intf;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.Set;

public class EtlBatchFetchInfo {
    private Set<String> unitCodes = CollectionUtils.newHashSet();
    private Set<String> formKeys = CollectionUtils.newHashSet();

    public Set<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void addUnitCode(String unitCode) {
        Assert.isNotEmpty((String)unitCode);
        this.unitCodes.add(unitCode);
    }

    public Set<String> getFormKeys() {
        return this.formKeys;
    }

    public void addFormKey(String formKey) {
        Assert.isNotEmpty((String)formKey);
        this.formKeys.add(formKey);
    }

    public void addFormKeys(Set<String> formKeySet) {
        if (CollectionUtils.isEmpty(formKeySet)) {
            return;
        }
        this.formKeys.addAll(formKeySet);
    }
}

