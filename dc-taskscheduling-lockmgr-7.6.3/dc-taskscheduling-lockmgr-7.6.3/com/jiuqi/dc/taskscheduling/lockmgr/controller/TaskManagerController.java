/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.taskscheduling.lockmgr.controller;

import com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskManagerController {
    private static final String API_BASE_PATH = "/api/datacenter/v1/dm";
    @Autowired
    private OrgDataClient orgDataApi;
    @Autowired
    private TaskManageService taskMgrService;

    @PostMapping(value={"/api/datacenter/v1/dm/taskMgr/init"})
    public void initTaskManage() {
        OrgDTO orgDataDTO = new OrgDTO();
        orgDataDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDataDTO.setCategoryname("MD_ORG");
        orgDataDTO.setTenantName(ShiroUtil.getUser().getTenantName());
        orgDataDTO.setPagination(Boolean.valueOf(false));
        PageVO result = this.orgDataApi.list(orgDataDTO);
        List<String> unitCodes = result.getRows().stream().map(OrgDO::getCode).collect(Collectors.toList());
        this.taskMgrService.initTaskManageByUnitCodes(unitCodes);
    }
}

