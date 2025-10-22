/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.calibre2.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.calibre2.ICalibreReferService;
import com.jiuqi.nr.calibre2.service.ICalibreDefineManageService;
import com.jiuqi.nr.calibre2.vo.ReferenceCalibreVO;
import com.jiuqi.nr.calibre2.vo.ReferenceCheckVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/calibre2/"})
@Api(tags={"\u53e3\u5f84\u7ba1\u7406\uff1a\u53e3\u5f84\u5f15\u7528\u68c0\u67e5"})
public class CalibreReferController {
    @Autowired
    private ICalibreReferService calibreReferService;
    @Autowired
    private ICalibreDefineManageService defineManageService;

    @ApiOperation(value="\u68c0\u67e5\u53e3\u5f84\u5f15\u7528")
    @PostMapping(value={"refer/check"})
    public List<ReferenceCalibreVO> check(@RequestBody ReferenceCalibreVO referenceCalibreVO) {
        return this.calibreReferService.getRefer(referenceCalibreVO.getCalibreDefineCode(), referenceCalibreVO.getCalibreKeys());
    }

    @ApiOperation(value="\u53e3\u5f84\u5220\u9664\u524d\u5f15\u7528\u68c0\u67e5")
    @PostMapping(value={"refer/deleteCheck"})
    public ReferenceCheckVO deletCheck(@RequestBody ReferenceCalibreVO referenceCalibreVO) {
        return this.defineManageService.deleteCheck(referenceCalibreVO);
    }

    @ApiOperation(value="\u591a\u4e2a\u53e3\u5f84\u5b9a\u4e49\u5220\u9664\u524d\u5f15\u7528\u68c0\u67e5")
    @PostMapping(value={"refer/batch_deleteCheck"})
    public ReferenceCheckVO batchDeletCheck(@RequestBody List<String> deleteDefineCodes) {
        return this.defineManageService.betchDeleteCheck(deleteDefineCodes);
    }
}

