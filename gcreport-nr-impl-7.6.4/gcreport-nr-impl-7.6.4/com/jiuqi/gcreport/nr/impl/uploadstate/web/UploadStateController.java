/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  io.swagger.annotations.Api
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.nr.impl.uploadstate.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value="UploadState", description="\u4e0a\u62a5\u72b6\u6001")
@CrossOrigin
@RestController
@RequestMapping(value={"/api/gcreport/v1/uploadState"})
public class UploadStateController {
    @PostMapping(value={"/query"})
    @ResponseBody
    public BusinessResponseEntity<UploadState> getUploadState(@Valid @RequestBody DimensionParamsVO queryParamsVO) {
        UploadState uploadSate = new UploadStateTool().getUploadSate(queryParamsVO, queryParamsVO.getOrgId());
        return BusinessResponseEntity.ok((Object)uploadSate);
    }

    @PostMapping(value={"/queryActionState"})
    @ResponseBody
    public BusinessResponseEntity<ActionState> getActionState(@Valid @RequestBody DimensionParamsVO queryParamsVO) {
        ActionState actionState = new UploadStateTool().getActionState(queryParamsVO, queryParamsVO.getOrgId());
        return BusinessResponseEntity.ok((Object)actionState);
    }

    @PostMapping(value={"/writeable"})
    @ResponseBody
    public BusinessResponseEntity<ReadWriteAccessDesc> writeable(@Valid @RequestBody DimensionParamsVO queryParamsVO) {
        return BusinessResponseEntity.ok((Object)new UploadStateTool().writeable(queryParamsVO));
    }
}

