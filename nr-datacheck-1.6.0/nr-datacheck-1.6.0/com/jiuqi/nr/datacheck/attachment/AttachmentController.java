/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.datacheckcommon.param.DataCheckDimInfo
 *  com.jiuqi.nr.datacheckcommon.param.QueryDimParam
 *  com.jiuqi.nr.datacheckcommon.service.IDataCheckCommonService
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.datacheck.attachment;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.datacheck.attachment.service.IAttachmentService;
import com.jiuqi.nr.datacheck.attachment.vo.AttachmentQueryPM;
import com.jiuqi.nr.datacheck.attachment.vo.AttachmentResVO;
import com.jiuqi.nr.datacheck.attachment.vo.QueryResParam;
import com.jiuqi.nr.datacheckcommon.param.DataCheckDimInfo;
import com.jiuqi.nr.datacheckcommon.param.QueryDimParam;
import com.jiuqi.nr.datacheckcommon.service.IDataCheckCommonService;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/datacheck/attachment"})
public class AttachmentController {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);
    @Autowired
    private IDataCheckCommonService dataCheckCommonService;
    @Autowired
    private IAttachmentService attachmentService;

    @PostMapping(value={"/result/getdims"})
    @ApiOperation(value="\u67e5\u8be2\u7ed3\u679c\u7ef4\u5ea6")
    @NRContextBuild
    public DataCheckDimInfo queryDims(@Valid @RequestBody QueryDimParam queryDimParam) {
        try {
            return this.dataCheckCommonService.queryDims(queryDimParam);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u60c5\u666f\u5931\u8d25", e);
            return null;
        }
    }

    @PostMapping(value={"/result/get"})
    @ApiOperation(value="\u67e5\u8be2\u7ed3\u679c")
    public AttachmentResVO queryResult(@Valid @RequestBody QueryResParam queryResParam) {
        return this.attachmentService.queryResult(queryResParam);
    }

    @GetMapping(value={"/result/export"})
    @ApiOperation(value="\u5bfc\u51fa\u67e5\u8be2\u7ed3\u679c")
    public void exportResult(HttpServletResponse response, @RequestBody AttachmentQueryPM exportPM) {
        this.attachmentService.exportResult(response, exportPM);
    }
}

