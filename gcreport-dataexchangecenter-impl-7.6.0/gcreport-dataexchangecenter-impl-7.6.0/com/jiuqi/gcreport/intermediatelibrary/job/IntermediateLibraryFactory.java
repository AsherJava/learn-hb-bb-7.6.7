/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.job;

import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateLibraryBulkService;

public class IntermediateLibraryFactory {
    private static final IntermediateLibraryFactory instance = new IntermediateLibraryFactory();
    private IntermediateLibraryBulkService intermediateLibraryBulkService;

    public static IntermediateLibraryFactory getInstance() {
        return instance;
    }

    public IntermediateLibraryBulkService getIntermediateLibraryBulkService() {
        return this.intermediateLibraryBulkService;
    }

    public void setIntermediateLibraryBulkService(IntermediateLibraryBulkService intermediateLibraryBulkService) {
        this.intermediateLibraryBulkService = intermediateLibraryBulkService;
    }
}

