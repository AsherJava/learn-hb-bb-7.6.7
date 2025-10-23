/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.workflow2.instance.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.workflow2.instance.context.InstanceDetailDataContext;
import com.jiuqi.nr.workflow2.instance.context.InstanceTableDataContext;
import com.jiuqi.nr.workflow2.instance.entity.InstanceFormDetailData;
import com.jiuqi.nr.workflow2.instance.service.Workflow2InstanceQueryService;
import com.jiuqi.nr.workflow2.instance.vo.InstanceInitDataVO;
import com.jiuqi.nr.workflow2.instance.vo.InstanceTableDataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/nr/workflow2/instance"})
@Api(tags={"\u6d41\u7a0b2.0-\u6d41\u7a0b\u5b9e\u4f8b\u670d\u52a1"})
public class Workflow2InstanceQueryController {
    @Autowired
    private Workflow2InstanceQueryService service;

    @NRContextBuild
    @PostMapping(value={"/table-data"})
    @ApiOperation(value="\u67e5\u8be2\u8868\u683c\u6570\u636e\u63a5\u53e3")
    public InstanceTableDataVO queryTableData(@RequestBody InstanceTableDataContext context) {
        return this.service.queryTableData(context);
    }

    @NRContextBuild
    @PostMapping(value={"/form-detail-data"})
    @ApiOperation(value="\u67e5\u8be2\u5355\u4f4d\u7684\u62a5\u8868\u6216\u62a5\u8868\u5206\u7ec4\u8be6\u60c5\u6570\u636e\u63a5\u53e3")
    public List<InstanceFormDetailData> queryFormDetailData(@RequestBody InstanceDetailDataContext context) {
        return this.service.queryFormDetailData(context);
    }

    @NRContextBuild
    @GetMapping(value={"/init"})
    @ApiOperation(value="\u6d41\u7a0b\u5b9e\u4f8b\u7ba1\u7406\u6570\u636e\u521d\u59cb\u5316\u63a5\u53e3")
    public InstanceInitDataVO queryFormDetailData(@RequestParam String taskKey, @RequestParam String period) {
        return this.service.initInstanceData(taskKey, period);
    }
}

