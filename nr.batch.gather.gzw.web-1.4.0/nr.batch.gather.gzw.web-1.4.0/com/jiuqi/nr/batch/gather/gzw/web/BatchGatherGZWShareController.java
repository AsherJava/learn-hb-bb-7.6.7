/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSShareService
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummaryParam
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.batch.gather.gzw.web;

import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummaryParam;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/batch-gather-GZW/share"})
@Api(tags={"\u6c47\u603b\u65b9\u6848-\u5206\u4eab-\u901a\u7528API"})
public class BatchGatherGZWShareController {
    @Resource
    private BSShareService service;

    @ResponseBody
    @ApiOperation(value="\u5df2\u5206\u4eab\u7528\u6237\u67e5\u8be2")
    @PostMapping(value={"/find-toUser"})
    public IReturnObject<Set<String>> findToUsers(@RequestBody ShareSummaryParam param) {
        IReturnObject res = null;
        Set toUsers = null;
        try {
            toUsers = this.service.findToUsers(param.getTaskId(), param.getScheme());
            res = IReturnObject.getSuccessInstance((Object)toUsers);
        }
        catch (Exception e) {
            res = IReturnObject.getErrorInstance((String)e.getMessage(), toUsers);
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return res;
    }
}

