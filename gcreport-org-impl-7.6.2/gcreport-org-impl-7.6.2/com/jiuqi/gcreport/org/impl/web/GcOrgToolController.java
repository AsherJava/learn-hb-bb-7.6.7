/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataAuthzChangeEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataCacheBaseEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent
 *  com.jiuqi.gcreport.org.api.intf.GcOrgToolClient
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgToolApiParam
 *  com.jiuqi.np.dataengine.intf.EntityResetCacheService
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.va.domain.org.OrgCategoryDTO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  com.jiuqi.va.organization.service.impl.help.OrgDataCacheService
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.org.impl.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.event.GcOrgDataAuthzChangeEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataCacheBaseEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent;
import com.jiuqi.gcreport.org.api.intf.GcOrgToolClient;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgToolApiParam;
import com.jiuqi.gcreport.org.impl.cache.dao.impl.GcOrgTypeVersionDaoImpl;
import com.jiuqi.gcreport.org.impl.cache.listener.GcOrgCacheListener;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.va.domain.org.OrgCategoryDTO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.OrgVersionClient;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
class GcOrgToolController
implements GcOrgToolClient {
    @Autowired
    private OrgDataCacheService cacheService;
    @Autowired
    private GcOrgCacheListener messageService;
    @Autowired(required=false)
    private EntityResetCacheService nrCacheService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private OrgVersionClient orgVersionClient;

    GcOrgToolController() {
    }

    public BusinessResponseEntity<String> initOrgCache(GcOrgToolApiParam param) {
        OrgCategoryDTO orgCatDTO = new OrgCategoryDTO();
        orgCatDTO.setName(param.getOrgType());
        this.orgDataClient.cleanCache(orgCatDTO);
        OrgVersionDO orgVerDTO = new OrgVersionDO();
        orgVerDTO.setCategoryname(param.getOrgType());
        this.orgVersionClient.syncCache(orgVerDTO);
        if (this.nrCacheService != null) {
            this.nrCacheService.resetCache(param.getOrgType());
        }
        GcOrgDataItemChangeEvent changeEvent = new GcOrgDataItemChangeEvent(param.getOrgType());
        this.messageService.publishRefreshMessage((GcOrgDataCacheBaseEvent)changeEvent);
        GcOrgTypeVersionDaoImpl versionDao = (GcOrgTypeVersionDaoImpl)SpringContextUtils.getBean(GcOrgTypeVersionDaoImpl.class);
        versionDao.setGcOrgCodeConfig(null);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<String> refreshOrgCache(GcOrgToolApiParam param) {
        User u;
        GcOrgDataAuthzChangeEvent gcEvent = new GcOrgDataAuthzChangeEvent();
        if (!StringUtils.isEmpty((String)param.getUserName()) && (u = this.userService.getByUsername(param.getUserName())) != null) {
            gcEvent.addUser(u.getId());
        }
        this.messageService.publishRefreshMessage((GcOrgDataCacheBaseEvent)gcEvent);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public String getOperateMode() {
        String modes = "[{\"title\": \"\u7ba1\u7406\u5458\", \"code\": \"admin\"},{\"title\": \"\u975e\u7ba1\u7406\u5458\", \"code\": \"custom\"}]";
        return modes;
    }
}

