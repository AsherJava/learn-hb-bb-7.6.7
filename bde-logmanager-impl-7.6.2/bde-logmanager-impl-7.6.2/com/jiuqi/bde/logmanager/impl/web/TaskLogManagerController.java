/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.logmanager.client.LogDetailRefreshCondition
 *  com.jiuqi.bde.logmanager.client.LogManagerCondition
 *  com.jiuqi.bde.logmanager.client.LogManagerInfoItemVO
 *  com.jiuqi.bde.logmanager.client.LogManagerInfoVO
 *  com.jiuqi.bde.logmanager.client.LogResultMsgVO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.domain.common.PageVO
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.logmanager.impl.web;

import com.jiuqi.bde.logmanager.client.LogDetailRefreshCondition;
import com.jiuqi.bde.logmanager.client.LogManagerCondition;
import com.jiuqi.bde.logmanager.client.LogManagerInfoItemVO;
import com.jiuqi.bde.logmanager.client.LogManagerInfoVO;
import com.jiuqi.bde.logmanager.client.LogResultMsgVO;
import com.jiuqi.bde.logmanager.impl.intf.FetchSqlLogDTO;
import com.jiuqi.bde.logmanager.impl.service.TaskLogManagerService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskLogManagerController {
    private final String LOG_MANAGER_API = "/api/bde/v1/log";
    @Autowired
    private TaskLogManagerService logManagerService;

    @PostMapping(value={"/api/bde/v1/log/list"})
    public BusinessResponseEntity<PageVO<LogManagerInfoVO>> listLogData(@RequestBody LogManagerCondition logManagerCondition) {
        return BusinessResponseEntity.ok(this.logManagerService.listLogData(logManagerCondition));
    }

    @GetMapping(value={"/api/bde/v1/log/getDetailListById/{requestInstcId}"})
    public BusinessResponseEntity<LogResultMsgVO> getLogDetailListById(@PathVariable(value="requestInstcId") String requestInstcId) {
        return BusinessResponseEntity.ok((Object)this.logManagerService.getLogDetailListById(requestInstcId));
    }

    @PostMapping(value={"/api/bde/v1/log/detail/refresh"})
    public BusinessResponseEntity<Map<String, LogManagerInfoItemVO>> refreshLogDetailData(@RequestBody LogDetailRefreshCondition logDetailRefreshCondition) {
        return BusinessResponseEntity.ok(this.logManagerService.refreshLogDetailData(logDetailRefreshCondition));
    }

    @GetMapping(value={"/api/bde/v1/log/item/list/{requestInstcId}/{requestTaskId}"})
    public BusinessResponseEntity<List<LogManagerInfoItemVO>> listLogItemData(@PathVariable(value="requestInstcId") String requestInstcId, @PathVariable(value="requestTaskId") String requestTaskId) {
        return BusinessResponseEntity.ok(this.logManagerService.listLogItemData(requestInstcId, requestTaskId));
    }

    @GetMapping(value={"/api/bde/v1/log/item/getResultLog/{id}"})
    public BusinessResponseEntity<String> getLogResultLogById(@PathVariable(value="id") String id) {
        return BusinessResponseEntity.ok((Object)this.logManagerService.getLogResultLogById(id));
    }

    @GetMapping(value={"/api/bde/v1/log/sql/list/{requestInstcId}/{requestTaskId}"})
    public BusinessResponseEntity<List<FetchSqlLogDTO>> getExecuteSqlById(@PathVariable(value="requestInstcId") String requestInstcId, @PathVariable(value="requestTaskId") String requestTaskId) {
        return BusinessResponseEntity.ok(this.logManagerService.getExecuteSqlById(requestInstcId, requestTaskId));
    }

    @GetMapping(value={"/api/bde/v1/log/exportErrorLog/{requestInstcId}/{requestTaskId}"})
    public void exportErrorLog(@PathVariable(value="requestInstcId") String requestInstcId, @PathVariable(value="requestTaskId") String requestTaskId, HttpServletResponse response) {
        this.logManagerService.exportErrorLog(requestInstcId, requestTaskId, response);
    }
}

