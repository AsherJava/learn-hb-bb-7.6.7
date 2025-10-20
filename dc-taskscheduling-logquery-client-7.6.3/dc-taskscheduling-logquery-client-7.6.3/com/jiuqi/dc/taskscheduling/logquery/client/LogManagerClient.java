/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.taskscheduling.logquery.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO;
import com.jiuqi.dc.taskscheduling.logquery.client.vo.LogManagerVO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface LogManagerClient {
    public static final String API_BASE_PATH = "/api/datacenter/v1/logmanager";

    @PostMapping(value={"/api/datacenter/v1/logmanager/overViewList"})
    public BusinessResponseEntity<PageVO<LogManagerVO>> getLogList(@RequestBody LogManagerDTO var1);

    @PostMapping(value={"/api/datacenter/v1/logmanager/detailList"})
    public BusinessResponseEntity<PageVO<LogManagerDetailVO>> getLogDetailList(@RequestBody LogManagerDTO var1);

    @PostMapping(value={"/api/datacenter/v1/logmanager/overViewProgress"})
    public BusinessResponseEntity<LogManagerVO> getOverViewProgress(@RequestBody String var1);

    @PostMapping(value={"/api/datacenter/v1/logmanager/detailProgress"})
    public BusinessResponseEntity<LogManagerDetailVO> getDetailProgress(@RequestBody String var1);

    @PostMapping(value={"/api/datacenter/v1/logmanager/getResultLog"})
    public BusinessResponseEntity<LogManagerDetailVO> getResultLog(@RequestBody String var1);

    @PostMapping(value={"/api/datacenter/v1/logmanager/getOverTaskType"})
    public BusinessResponseEntity<List<SelectOptionVO>> getOverTaskType();

    @PostMapping(value={"/api/datacenter/v1/logmanager/getDetailTaskType"})
    public BusinessResponseEntity<List<SelectOptionVO>> getDetailTaskType(@RequestBody LogManagerDTO var1);

    @PostMapping(value={"/api/datacenter/v1/logmanager/getDetailDimType"})
    public BusinessResponseEntity<List<SelectOptionVO>> getDetailDimType();

    @PostMapping(value={"/api/datacenter/v1/logmanager/cancel"})
    public BusinessResponseEntity<String> cancel(@RequestBody String var1);
}

