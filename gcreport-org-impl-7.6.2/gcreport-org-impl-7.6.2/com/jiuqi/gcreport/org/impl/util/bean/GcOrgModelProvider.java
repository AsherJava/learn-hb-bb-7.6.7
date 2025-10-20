/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.util.bean;

import com.jiuqi.gcreport.org.impl.util.bean.GcOrgManageModel;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgOtherModel;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgQueryModel;

public class GcOrgModelProvider {
    private static GcOrgQueryModel gcOrgQueryModel;
    private static GcOrgManageModel gcOrgManageModel;
    private static GcOrgOtherModel gcOrgOtherModel;

    public static GcOrgQueryModel getGcOrgQueryModel() {
        return gcOrgQueryModel;
    }

    public static GcOrgManageModel getGcOrgManageModel() {
        return gcOrgManageModel;
    }

    public static GcOrgOtherModel getGcOrgOtherModel() {
        return gcOrgOtherModel;
    }

    static void setGcOrgQueryModel(GcOrgQueryModel gcOrgQueryModel) {
        GcOrgModelProvider.gcOrgQueryModel = gcOrgQueryModel;
    }

    static void setGcOrgManageModel(GcOrgManageModel gcOrgManageModel) {
        GcOrgModelProvider.gcOrgManageModel = gcOrgManageModel;
    }

    static void setGcOrgOtherModel(GcOrgOtherModel gcOrgOtherModel) {
        GcOrgModelProvider.gcOrgOtherModel = gcOrgOtherModel;
    }
}

