/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.transmission.data.controller;

import com.jiuqi.nr.transmission.data.intf.RejectMessageResult;
import com.jiuqi.nr.transmission.data.reject.RejectParamDTO;
import com.jiuqi.nr.transmission.data.service.RejectService;
import com.jiuqi.nr.transmission.data.vo.RejectParamVO;
import com.jiuqi.va.message.domain.VaMessageOption;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"api/v1/sync/scheme/reject/"})
@Api(tags={"\u591a\u7ea7\u90e8\u7f72\uff0c\u9000\u56de\u901a\u77e5"})
public class RejectController {
    @Autowired
    private RejectService rejectService;

    @ApiOperation(value="\u63a5\u6536\u9000\u56de\u6d88\u606f")
    @PostMapping(value={"accept"})
    public RejectMessageResult acceptRejectMessage(@RequestBody RejectParamVO rejectParamVO) {
        RejectParamDTO rejectParamDTO = RejectParamVO.rejectParamVoToDto(rejectParamVO);
        this.rejectService.acceptRejectAction(rejectParamDTO, VaMessageOption.MsgChannel.PC, "");
        RejectMessageResult rejectMessageResult = new RejectMessageResult();
        rejectMessageResult.setMessage("\u6210\u529f");
        return rejectMessageResult;
    }

    @ApiOperation(value="\u63a5\u6536\u9000\u56de\u6d88\u606f\u524d\u68c0\u67e5\u4e0b\u7ea7\u670d\u52a1\u662f\u5426\u5f00\u542f\uff0c\u4e0d\u5f00\u542f\u7684\u8bdd\u5c31\u4f1a\u53d1\u90ae\u4ef6")
    @PostMapping(value={"subserver_exist"})
    public RejectMessageResult checkSubserverExist() {
        RejectMessageResult result = new RejectMessageResult();
        result.setResult(true);
        return result;
    }
}

