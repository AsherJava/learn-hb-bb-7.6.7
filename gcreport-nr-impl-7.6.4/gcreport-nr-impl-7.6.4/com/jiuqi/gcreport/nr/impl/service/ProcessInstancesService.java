/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.businesskey.BusinessKey
 */
package com.jiuqi.gcreport.nr.impl.service;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;

public interface ProcessInstancesService {
    public void restoreProcessInstances(String var1, String var2, String var3);

    public void deleteFlow(String var1);

    public void deleteUpload(BusinessKey var1);

    public void insertUploadRecordByQuery(String var1, String var2, String var3);
}

