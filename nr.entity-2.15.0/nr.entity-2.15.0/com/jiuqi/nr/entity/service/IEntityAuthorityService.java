/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.service;

import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface IEntityAuthorityService {
    public boolean isEnableAuthority(String var1);

    @Deprecated
    public boolean isSystemIdentity();

    public boolean canReadEntity(String var1, String var2, Date var3, Date var4) throws UnauthorizedEntityException;

    public boolean canEditEntity(String var1, String var2, Date var3, Date var4) throws UnauthorizedEntityException;

    public Map<String, Boolean> canReadEntity(String var1, Set<String> var2, Date var3) throws UnauthorizedEntityException;

    public Map<String, Boolean> canEditEntity(String var1, Set<String> var2, Date var3) throws UnauthorizedEntityException;

    public Map<String, Boolean> canAuditEntity(String var1, Set<String> var2, Date var3) throws UnauthorizedEntityException;

    public Map<String, Boolean> canSubmitEntity(String var1, Set<String> var2, Date var3) throws UnauthorizedEntityException;

    public Map<String, Boolean> canUploadEntity(String var1, Set<String> var2, Date var3) throws UnauthorizedEntityException;

    public boolean canWriteEntity(String var1, String var2, Date var3, Date var4) throws UnauthorizedEntityException;

    public Map<String, Boolean> canWriteEntity(String var1, Set<String> var2, Date var3) throws UnauthorizedEntityException;

    public boolean canSubmitEntity(String var1, String var2, Date var3, Date var4) throws UnauthorizedEntityException;

    public boolean canSubmitEntity(String var1, String var2, String var3, Date var4, Date var5) throws UnauthorizedEntityException;

    public boolean canUploadEntity(String var1, String var2, Date var3, Date var4) throws UnauthorizedEntityException;

    public boolean canUploadEntity(String var1, String var2, String var3, Date var4, Date var5) throws UnauthorizedEntityException;

    public boolean canAuditEntity(String var1, String var2, Date var3, Date var4) throws UnauthorizedEntityException;

    public boolean canAuditEntity(String var1, String var2, String var3, Date var4, Date var5) throws UnauthorizedEntityException;

    public boolean canPublishEntity(String var1, String var2, Date var3, Date var4) throws UnauthorizedEntityException;

    public boolean canReadUnPublishEntity(String var1, String var2, Date var3, Date var4) throws UnauthorizedEntityException;

    public Set<String> getCanReadEntityKeys(String var1, Date var2, Date var3) throws UnauthorizedEntityException;

    public Set<String> getCanEditEntityKeys(String var1, Date var2, Date var3) throws UnauthorizedEntityException;

    public Set<String> getCanWriteEntityKeys(String var1, Date var2, Date var3) throws UnauthorizedEntityException;

    public Set<String> getCanAuditEntityKeys(String var1, Date var2, Date var3) throws UnauthorizedEntityException;

    public Set<String> getCanSubmitEntityKeys(String var1, Date var2, Date var3) throws UnauthorizedEntityException;

    public Set<String> getCanUploadEntityKeys(String var1, Date var2, Date var3) throws UnauthorizedEntityException;

    public Set<String> getCanUploadIdentityKeys(String var1, String var2, Date var3, Date var4) throws UnauthorizedEntityException;

    public Set<String> getCanAuditIdentityKeys(String var1, String var2, Date var3, Date var4) throws UnauthorizedEntityException;

    public Set<String> getCanSubmitIdentityKeys(String var1, String var2, Date var3, Date var4) throws UnauthorizedEntityException;

    public Set<String> getCanReadIdentityKeys(String var1, String var2, Date var3, Date var4) throws UnauthorizedEntityException;

    public void grantAllPrivilegesToEntityTable(String var1);

    public void grantAllPrivilegesToEntityData(String var1, String ... var2);
}

