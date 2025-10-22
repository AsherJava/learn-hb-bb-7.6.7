/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.service;

import com.jiuqi.nr.configuration.entity.ProfileEntity;
import java.util.List;

public interface IProfileService {
    public void createProfile(ProfileEntity var1) throws Exception;

    public void deleteProfile(String var1, String var2) throws Exception;

    public void updateProfile(ProfileEntity var1) throws Exception;

    public List<ProfileEntity> getProfiles(String var1) throws Exception;

    public List<ProfileEntity> getProfilesByCode(String var1) throws Exception;

    public ProfileEntity getProfile(String var1, String var2) throws Exception;
}

