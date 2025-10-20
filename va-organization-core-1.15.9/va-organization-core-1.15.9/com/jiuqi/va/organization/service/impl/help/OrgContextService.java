/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 */
package com.jiuqi.va.organization.service.impl.help;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrgContextService {
    private static Logger logger = LoggerFactory.getLogger(OrgContextService.class);
    @Autowired
    private AuthUserClient userClient;
    @Value(value="${va-datamodel-check-tablename-length:true}")
    private boolean checkTableNameLength;
    @Value(value="${nvwa.organization.datasync.batch-size:500}")
    private int batchSubmitSize = 500;
    @Value(value="${nvwa.organization.modify-org-code.allow:false}")
    private boolean modifyOrgcodeAllow;
    @Value(value="${nvwa.organization.add-from-other-category:false}")
    private boolean addFromOtherAllow;
    @Value(value="${nvwa.organization.modify-historical-data.allow:false}")
    private boolean modifyHistoricalDataAllow;
    private boolean autoCreateAdminOrgFromOther;
    @Value(value="${nvwa.organization.record-change-log.ui-editor:false}")
    private boolean recordChangeLog;

    public boolean isCheckTableNameLength() {
        return this.checkTableNameLength;
    }

    public int getBatchSubmitSize() {
        return this.batchSubmitSize;
    }

    public boolean isModifyOrgcodeAllow() {
        return this.modifyOrgcodeAllow;
    }

    public boolean isAddFromOtherAllow() {
        return this.addFromOtherAllow;
    }

    public boolean isModifyHistoricalDataAllow() {
        return this.modifyHistoricalDataAllow;
    }

    public boolean isAutoCreateAdminOrgFromOther() {
        return this.autoCreateAdminOrgFromOther;
    }

    public boolean isRecordChangeLog() {
        return this.recordChangeLog;
    }

    public boolean isSuperMan() {
        String mgrFlag = "normal";
        try {
            UserLoginDTO user = ShiroUtil.getUser();
            mgrFlag = user.getMgrFlag();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "super".equals(mgrFlag);
    }

    public boolean canMgr() {
        if (this.isSuperMan()) {
            return true;
        }
        boolean flag = false;
        try {
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                Object isAdmin;
                UserDTO param = new UserDTO();
                param.setUsername(user.getUsername());
                param.addExtInfo("onlyNeedBasicInfo", (Object)true);
                param.addExtInfo("judgeBizMgr", (Object)true);
                UserDO userDO = this.userClient.get(param);
                Object isBizMgr = userDO.getExtInfo("isBizMgr");
                if (isBizMgr == null || ((Boolean)isBizMgr).booleanValue()) {
                    flag = true;
                }
                if ((isAdmin = user.getExtInfo("isAdmin")) != null && ((Boolean)isAdmin).booleanValue()) {
                    flag = true;
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }
}

