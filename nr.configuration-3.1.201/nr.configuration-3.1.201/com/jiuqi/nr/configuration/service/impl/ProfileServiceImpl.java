/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.service.impl;

import com.jiuqi.nr.configuration.db.IProfileDao;
import com.jiuqi.nr.configuration.entity.ProfileEntity;
import com.jiuqi.nr.configuration.service.IProfileService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl
implements IProfileService {
    @Autowired
    private IProfileDao profileDao;

    @Override
    public void createProfile(ProfileEntity profile) throws Exception {
        this.profileDao.add(profile);
    }

    @Override
    public void deleteProfile(String userKey, String code) throws Exception {
        this.profileDao.delete(userKey, code);
    }

    @Override
    public void updateProfile(ProfileEntity profile) throws Exception {
        this.profileDao.update(profile);
    }

    @Override
    public List<ProfileEntity> getProfiles(String userKey) throws Exception {
        return this.profileDao.queryByUser(userKey);
    }

    @Override
    public List<ProfileEntity> getProfilesByCode(String code) throws Exception {
        return this.profileDao.queryByCode(code);
    }

    @Override
    public ProfileEntity getProfile(String userKey, String code) throws Exception {
        return this.profileDao.queryUnique(userKey, code);
    }
}

