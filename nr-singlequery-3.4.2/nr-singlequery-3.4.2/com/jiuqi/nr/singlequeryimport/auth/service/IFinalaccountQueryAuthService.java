/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.auth.service;

public interface IFinalaccountQueryAuthService {
    public boolean canReadGroup(String var1);

    public boolean canWriteGroup(String var1);

    public boolean canAuthorizeGroup(String var1);

    public boolean canReadModel(String var1);

    public boolean canWriteModel(String var1);

    public boolean canAuthorizeModel(String var1);

    public boolean canReadGroupByIdentityId(String var1, String var2);

    public boolean canWriteGroupByIdentityId(String var1, String var2);

    public boolean canAuthorizeGroupByIdentityId(String var1, String var2);

    public boolean canReadModelByIdentityId(String var1, String var2);

    public boolean canWriteModelByIdentityId(String var1, String var2);

    public boolean canAuthorizeModelByIdentityId(String var1, String var2);

    public boolean canReadGroupWithChildModels(String var1, String var2);
}

