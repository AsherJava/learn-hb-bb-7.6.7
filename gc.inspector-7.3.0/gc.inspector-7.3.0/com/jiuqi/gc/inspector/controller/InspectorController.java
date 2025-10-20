/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gc.inspector.controller;

import com.jiuqi.gc.inspector.common.BusinessResponseEntity;
import com.jiuqi.gc.inspector.common.TaskTypeEnum;
import com.jiuqi.gc.inspector.domain.InspectParams;
import com.jiuqi.gc.inspector.domain.InspectResultVO;
import com.jiuqi.gc.inspector.service.InpectorService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1//inspector"})
public class InspectorController {
    @Autowired
    private InpectorService inpectorService;

    @PostMapping(value={"/execute"})
    public BusinessResponseEntity<InspectResultVO> executeTask(@RequestBody InspectParams inspectParams) {
        Map<String, Object> params = inspectParams.getParams();
        String code = inspectParams.getCode();
        TaskTypeEnum taskTypeEnum = inspectParams.getTaskType();
        return BusinessResponseEntity.ok(this.inpectorService.execute(taskTypeEnum, code, params));
    }
}

