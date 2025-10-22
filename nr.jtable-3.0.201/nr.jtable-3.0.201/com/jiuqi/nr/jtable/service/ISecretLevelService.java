/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.BatchSecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface ISecretLevelService {
    public boolean secretLevelEnable(String var1);

    public List<SecretLevelItem> getSecretLevelItems();

    public SecretLevelItem getSecretLevelItem(String var1);

    public boolean canAccess(SecretLevelItem var1);

    public boolean compareSercetLevel(SecretLevelItem var1, SecretLevelItem var2);

    public SecretLevelInfo getSecretLevel(JtableContext var1);

    public List<SecretLevelItem> getSecretLevelItems(JtableContext var1);

    public void setSecretLevel(SecretLevelInfo var1);

    public List<SecretLevelInfo> querySecretLevels(JtableContext var1);

    public void batchSaveBatchSecretLevel(BatchSecretLevelInfo var1);

    public void extractPrePeriodSecretLevel(JtableContext var1);

    public void exportSecretLevel(JtableContext var1, HttpServletResponse var2);

    public ReturnInfo importSecretLevel(String var1, JtableContext var2);
}

