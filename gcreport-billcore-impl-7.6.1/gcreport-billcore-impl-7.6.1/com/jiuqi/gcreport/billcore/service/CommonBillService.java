/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 */
package com.jiuqi.gcreport.billcore.service;

import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.util.List;
import java.util.Map;

public interface CommonBillService {
    public List<Map<String, Object>> listColumns(String var1, String var2);

    public String getOrgType(String var1);

    public Map checkCommonParent(String var1, String var2, String var3, String var4, String var5);

    public String getOrgTypeByTableName(String var1);

    public List<DataModelColumn> listAmtFileds(String var1);

    public List<DataModelColumn> listNotSystemFileds(String var1);
}

