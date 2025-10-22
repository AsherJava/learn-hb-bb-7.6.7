/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sensitive.service;

import java.util.List;

public interface BatchCheckBillDataService {
    public List<String> batchCheckSensitiveSheetTitleInfoOfBillData();

    public List<List<String>> batchCheckSensitiveDataInfoOfBillData();
}

