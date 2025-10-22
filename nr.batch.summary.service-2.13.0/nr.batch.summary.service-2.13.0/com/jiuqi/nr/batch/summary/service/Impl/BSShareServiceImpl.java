/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.batch.summary.storage.ShareSummaryDao
 *  com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryGroup
 *  com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummaryGroupDefine
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummarySchemeDefine
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.Impl;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nr.batch.summary.service.enumeration.ShareSchemeServiceState;
import com.jiuqi.nr.batch.summary.storage.ShareSummaryDao;
import com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummaryGroupDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummarySchemeDefine;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BSShareServiceImpl
implements BSShareService {
    @Resource
    private ShareSummaryDao shareSummaryDao;
    @Resource
    private UserService<User> userService;
    @Resource
    private SystemUserService systemUserService;
    @Resource
    private SystemIdentityService systemIdentityService;

    @Override
    public List<ShareSummaryGroup> findChildGroups(String task, String groupKey) {
        if (StringUtils.isEmpty((String)task) || StringUtils.isEmpty((String)groupKey)) {
            return new ArrayList<ShareSummaryGroup>();
        }
        List shareSummaryGroups = this.shareSummaryDao.findChildGroups(task, groupKey);
        ArrayList<ShareSummaryGroup> shareSummaryGroupList = new ArrayList<ShareSummaryGroup>();
        for (ShareSummaryGroup shareSummaryGroup : shareSummaryGroups) {
            String userName = null;
            if (this.systemIdentityService.isSystemIdentity(shareSummaryGroup.getCode())) {
                if (this.systemUserService.find(shareSummaryGroup.getCode()).isPresent()) {
                    userName = ((SystemUser)this.systemUserService.find(shareSummaryGroup.getCode()).get()).getName();
                }
            } else if (this.userService.find(shareSummaryGroup.getCode()).isPresent()) {
                userName = ((User)this.userService.find(shareSummaryGroup.getCode()).get()).getName();
            }
            ShareSummaryGroupDefine shareSummaryGroupDefine = new ShareSummaryGroupDefine();
            shareSummaryGroupDefine.setCode(shareSummaryGroup.getCode());
            shareSummaryGroupDefine.setTask(shareSummaryGroup.getTask());
            shareSummaryGroupDefine.setTitle(userName);
            shareSummaryGroupList.add((ShareSummaryGroup)shareSummaryGroupDefine);
        }
        return shareSummaryGroupList;
    }

    @Override
    public List<ShareSummaryScheme> findChildSchemeByGroup(String task) {
        if (StringUtils.isEmpty((String)task)) {
            return new ArrayList<ShareSummaryScheme>();
        }
        List shareSummarySchemes = this.shareSummaryDao.findSchemes(task);
        ArrayList<ShareSummaryScheme> shareSummarySchemeList = new ArrayList<ShareSummaryScheme>();
        return this.replaceUserName(shareSummarySchemes, shareSummarySchemeList);
    }

    @Override
    public List<ShareSummaryScheme> findChildSchemeByGroup(String task, String groupKey) {
        if (StringUtils.isEmpty((String)task) || StringUtils.isEmpty((String)groupKey)) {
            return new ArrayList<ShareSummaryScheme>();
        }
        List shareSummarySchemes = this.shareSummaryDao.findGroupSchemes(task, groupKey);
        ArrayList<ShareSummaryScheme> shareSummarySchemeList = new ArrayList<ShareSummaryScheme>();
        return this.replaceUserName(shareSummarySchemes, shareSummarySchemeList);
    }

    private List<ShareSummaryScheme> replaceUserName(List<ShareSummaryScheme> shareSummarySchemes, List<ShareSummaryScheme> shareSummarySchemeList) {
        for (ShareSummaryScheme shareSummaryScheme : shareSummarySchemes) {
            String fromUserName = null;
            if (this.systemIdentityService.isSystemIdentity(shareSummaryScheme.getFromUser())) {
                if (this.systemUserService.find(shareSummaryScheme.getFromUser()).isPresent()) {
                    fromUserName = ((SystemUser)this.systemUserService.find(shareSummaryScheme.getFromUser()).get()).getName();
                }
            } else if (this.userService.find(shareSummaryScheme.getFromUser()).isPresent()) {
                fromUserName = ((User)this.userService.find(shareSummaryScheme.getFromUser()).get()).getName();
            }
            String toUserName = null;
            if (this.systemIdentityService.isSystemIdentity(shareSummaryScheme.getToUser())) {
                if (this.systemUserService.find(shareSummaryScheme.getToUser()).isPresent()) {
                    toUserName = ((SystemUser)this.systemUserService.find(shareSummaryScheme.getToUser()).get()).getName();
                }
            } else if (this.userService.find(shareSummaryScheme.getToUser()).isPresent()) {
                toUserName = ((User)this.userService.find(shareSummaryScheme.getToUser()).get()).getName();
            }
            ShareSummarySchemeDefine shareSummarySchemeDefine = new ShareSummarySchemeDefine();
            shareSummarySchemeDefine.setCode(shareSummaryScheme.getCode());
            shareSummarySchemeDefine.setFromUser(fromUserName);
            shareSummarySchemeDefine.setToUser(toUserName);
            shareSummarySchemeDefine.setShareTime(shareSummaryScheme.getShareTime());
            shareSummarySchemeDefine.setTitle(shareSummaryScheme.getTitle());
            shareSummarySchemeList.add((ShareSummaryScheme)shareSummarySchemeDefine);
        }
        return shareSummarySchemeList;
    }

    @Override
    public ShareSummaryGroup findGroup(String groupKey) {
        return null;
    }

    @Override
    public boolean findScheme(String schemeKey) {
        return this.shareSummaryDao.findScheme(schemeKey) > 0;
    }

    @Override
    public ShareSummaryScheme findScheme(String schemeKey, int index) {
        return null;
    }

    @Override
    public ShareSchemeServiceState removeShareSummaryScheme(ShareSummarySchemeDefine shareSummarySchemeDefine) {
        String toUserId = null;
        toUserId = this.userService.findByUsername(shareSummarySchemeDefine.getToUser()).isPresent() ? ((User)this.userService.findByUsername(shareSummarySchemeDefine.getToUser()).get()).getId() : (this.systemUserService.findByUsername(shareSummarySchemeDefine.getToUser()).isPresent() ? ((SystemUser)this.systemUserService.findByUsername(shareSummarySchemeDefine.getToUser()).get()).getId() : shareSummarySchemeDefine.getToUser());
        shareSummarySchemeDefine.setToUser(toUserId);
        return this.shareSummaryDao.removeShareSummaryScheme(shareSummarySchemeDefine) == 1 ? ShareSchemeServiceState.SUCCESS : ShareSchemeServiceState.FAIL;
    }

    @Override
    public ShareSchemeServiceState addShareSummaryScheme(ShareSummarySchemeDefine shareSummarySchemeDefine) {
        if (this.shareSummaryDao.findScheme(shareSummarySchemeDefine.getCode(), shareSummarySchemeDefine.getFromUser(), shareSummarySchemeDefine.getToUser()) == 0) {
            return this.shareSummaryDao.addShareSummaryScheme(shareSummarySchemeDefine) == 1 ? ShareSchemeServiceState.SUCCESS : ShareSchemeServiceState.FAIL;
        }
        return ShareSchemeServiceState.SUCCESS;
    }

    @Override
    public Set<String> findToUsers(String task, String[] scheme) {
        if (scheme.length > 0) {
            if (scheme.length == 1) {
                return this.shareSummaryDao.findToUsers(task, scheme[0]);
            }
            Set toUsers = this.shareSummaryDao.findToUsers(task, scheme[0]);
            for (int i = 1; i < scheme.length; ++i) {
                Set currToUsers = this.shareSummaryDao.findToUsers(task, scheme[i]);
                toUsers.retainAll(currToUsers);
            }
        }
        return new HashSet<String>();
    }

    @Override
    public ShareSchemeServiceState removeShareSummaryScheme(String key) {
        return this.shareSummaryDao.removeShareSummaryScheme(key) == 1 ? ShareSchemeServiceState.SUCCESS : ShareSchemeServiceState.FAIL;
    }
}

