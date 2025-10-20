/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.bd.bill.common;

import java.util.ArrayList;
import java.util.List;

public class BillAlterModelUpdateConst {
    public static final String SRCBILLCODE = "SRCBILLCODE";
    public static final String SRCBILLDEFINE = "SRCBILLDEFINE";
    public static final String SRCMRECID = "SRCBILLID";

    public static List<String> getUnAssignFields() {
        ArrayList<String> fields = new ArrayList<String>();
        fields.add("ID");
        fields.add("VER");
        fields.add("DEFINECODE");
        fields.add("BILLCODE");
        fields.add("BILLDATE");
        fields.add("UNITCODE");
        fields.add("BILLSTATE");
        fields.add("CREATEUSER");
        fields.add("CREATETIME");
        fields.add("MODIFYUSER");
        fields.add("MODIFYTIME");
        fields.add(SRCBILLCODE);
        fields.add(SRCBILLDEFINE);
        fields.add(SRCMRECID);
        return fields;
    }
}

