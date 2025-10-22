/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.monitor.api.MonitorShowClient
 *  com.jiuqi.gcreport.monitor.api.vo.show.ConditionVO
 *  com.jiuqi.gcreport.monitor.api.vo.show.MonitorTableColumnVO
 *  com.jiuqi.gcreport.monitor.api.vo.show.MonitorTableVO
 *  com.jiuqi.nvwa.sf.anno.Licence
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.monitor.impl.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.monitor.api.MonitorShowClient;
import com.jiuqi.gcreport.monitor.api.vo.show.ConditionVO;
import com.jiuqi.gcreport.monitor.api.vo.show.MonitorTableColumnVO;
import com.jiuqi.gcreport.monitor.api.vo.show.MonitorTableVO;
import com.jiuqi.gcreport.monitor.impl.service.MonitorShowService;
import com.jiuqi.nvwa.sf.anno.Licence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
@Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.monitor")
public class MonitorShowController
implements MonitorShowClient {
    @Autowired
    private MonitorShowService monitorShowService;

    public BusinessResponseEntity<Map<String, Object>> monitorShowNew(@RequestBody ConditionVO conditionVO) {
        List<MonitorTableVO> tableVOTree = this.monitorShowService.monitorShowResult_V2(conditionVO);
        List<MonitorTableColumnVO> columns = this.monitorShowService.getMonitorColumns(conditionVO);
        HashMap<String, List<Object>> retMap = new HashMap<String, List<Object>>();
        retMap.put("columns", columns);
        retMap.put("tree", tableVOTree);
        return BusinessResponseEntity.ok(retMap);
    }

    public void exportExcel(HttpServletResponse response, @RequestBody ConditionVO conditionVO) {
        this.monitorShowService.exportExcel(response, conditionVO);
    }
}

