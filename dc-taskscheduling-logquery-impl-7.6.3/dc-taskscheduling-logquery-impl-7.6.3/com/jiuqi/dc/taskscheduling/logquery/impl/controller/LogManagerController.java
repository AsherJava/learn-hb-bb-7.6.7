/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService
 *  com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO
 *  com.jiuqi.dc.taskscheduling.logquery.client.LogManagerClient
 *  com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO
 *  com.jiuqi.dc.taskscheduling.logquery.client.vo.LogManagerVO
 *  com.jiuqi.nvwa.sf.anno.Licence
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.taskscheduling.logquery.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import com.jiuqi.dc.taskscheduling.logquery.client.LogManagerClient;
import com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO;
import com.jiuqi.dc.taskscheduling.logquery.client.vo.LogManagerVO;
import com.jiuqi.dc.taskscheduling.logquery.impl.service.LogManagerService;
import com.jiuqi.nvwa.sf.anno.Licence;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Licence(module="com.jiuqi.datacenter", point="com.jiuqi.datacenter.base")
public class LogManagerController
implements LogManagerClient {
    @Autowired
    private TaskLogService taskLogService;
    @Autowired
    private LogManagerService managerServe;

    public BusinessResponseEntity<PageVO<LogManagerVO>> getLogList(@RequestBody LogManagerDTO dto) {
        return BusinessResponseEntity.ok(this.managerServe.getOverViewList(dto));
    }

    public BusinessResponseEntity<PageVO<LogManagerDetailVO>> getLogDetailList(@RequestBody LogManagerDTO dto) {
        return BusinessResponseEntity.ok(this.managerServe.getDetailList(dto));
    }

    public BusinessResponseEntity<LogManagerVO> getOverViewProgress(@RequestBody String id) {
        return BusinessResponseEntity.ok((Object)this.managerServe.getOverViewProgress(id));
    }

    public BusinessResponseEntity<LogManagerDetailVO> getDetailProgress(@RequestBody String id) {
        return BusinessResponseEntity.ok((Object)this.managerServe.getDetailProgress(id));
    }

    public BusinessResponseEntity<LogManagerDetailVO> getResultLog(@RequestBody String id) {
        return BusinessResponseEntity.ok((Object)this.managerServe.getResultLog(id));
    }

    public BusinessResponseEntity<List<SelectOptionVO>> getOverTaskType() {
        return BusinessResponseEntity.ok(this.managerServe.getOverTaskType());
    }

    public BusinessResponseEntity<List<SelectOptionVO>> getDetailTaskType(@RequestBody LogManagerDTO dto) {
        return BusinessResponseEntity.ok(this.managerServe.getDetailTaskType(dto));
    }

    public BusinessResponseEntity<List<SelectOptionVO>> getDetailDimType() {
        return BusinessResponseEntity.ok(this.managerServe.getDetailDimType());
    }

    public BusinessResponseEntity<String> cancel(@RequestBody String id) {
        this.taskLogService.cancelTask(id);
        return BusinessResponseEntity.ok((Object)"\u5f53\u524d\u4efb\u52a1\u5df2\u53d6\u6d88");
    }
}

