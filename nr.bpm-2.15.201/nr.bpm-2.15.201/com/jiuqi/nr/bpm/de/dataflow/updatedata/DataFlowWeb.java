/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.bpm.de.dataflow.updatedata;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.bpm.de.dataflow.updatedata.IUpdateStateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value={"/nr/workflow/unitstate"})
@Api(tags={"\u6d41\u7a0b\u72b6\u6001"})
@JQRestController
public class DataFlowWeb {
    @Autowired
    private IUpdateStateService updateStateService;

    @GetMapping(value={"/refresh"})
    @ApiOperation(value="\u66f4\u65b0\u4fee\u590d\u72b6\u6001\u8868\u7684\u6570\u636e")
    public boolean updateState(@RequestParam(value="taskKey") String taskKey, @RequestParam(value="period") String period) {
        return this.updateStateService.updateState(taskKey, period);
    }

    @GetMapping(value={"/refreshbyunit"})
    @ApiOperation(value="\u66f4\u65b0\u4fee\u590d\u72b6\u6001\u8868\u7684\u6570\u636e")
    public boolean updateState(@RequestParam(value="taskKey") String taskKey, @RequestParam(value="period") String period, @RequestParam(value="unitId") String unitId) {
        return this.updateStateService.updateState(taskKey, period, unitId);
    }

    @GetMapping(value={"/refreshbyunits"})
    @ApiOperation(value="\u6279\u91cf\u66f4\u65b0\u4fee\u590d\u72b6\u6001\u8868\u7684\u6570\u636e")
    public StringBuffer batchUpdateState(@RequestParam(value="taskKey") String taskKey, @RequestParam(value="period") String period) {
        return this.updateStateService.batchUpdateState(taskKey, period);
    }
}

