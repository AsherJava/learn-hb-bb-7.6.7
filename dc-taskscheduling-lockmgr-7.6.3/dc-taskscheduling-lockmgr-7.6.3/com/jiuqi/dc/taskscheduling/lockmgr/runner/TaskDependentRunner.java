/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.taskscheduling.lockmgr.runner;

import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@PlanTaskRunner(id="F1C4783DB3A54C1C9C7EECA3BFFCF59C", name="taskDependentRunner", title="\u4efb\u52a1\u9879\u7ba1\u7406\u8ba1\u5212\u4efb\u52a1", group="\u51ed\u8bc1")
@Component(value="taskDependentRunner")
public class TaskDependentRunner
extends Runner {
    @Autowired
    private TaskManageService taskMgrService;
    @Autowired
    private OrgDataClient orgDataApi;

    @Transactional(rollbackFor={Exception.class})
    public boolean excute(String runnerParameter) {
        OrgDTO orgDataDTO = new OrgDTO();
        orgDataDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDataDTO.setCategoryname("MD_ORG");
        orgDataDTO.setTenantName(ShiroUtil.getUser().getTenantName());
        orgDataDTO.setPagination(Boolean.valueOf(false));
        PageVO result = this.orgDataApi.list(orgDataDTO);
        List<String> unitCodes = result.getRows().stream().map(OrgDO::getCode).collect(Collectors.toList());
        this.taskMgrService.initTaskManageByUnitCodes(unitCodes);
        this.appendLog("\u672c\u6b21\u5904\u7406\u65f6\u95f4\u8303\u56f4\u5185\u65b0\u589e\u4efb\u52a1\u9879\u7ec4\u5408\u5b8c\u6210\u3002");
        return true;
    }
}

