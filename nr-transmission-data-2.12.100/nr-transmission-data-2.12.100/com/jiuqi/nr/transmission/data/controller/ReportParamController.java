/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.dataentry.bean.FormsQueryResult
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.transmission.data.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.dataentry.bean.FormsQueryResult;
import com.jiuqi.nr.transmission.data.api.ITransmissionDataGather;
import com.jiuqi.nr.transmission.data.domain.TaskNodeParam;
import com.jiuqi.nr.transmission.data.service.IReportParamService;
import com.jiuqi.nr.transmission.data.vo.TransmissionDataGatherVO;
import com.jiuqi.nr.transmission.data.vo.TransmissionDataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/sync/scheme/param/"})
@Api(tags={"\u591a\u7ea7\u90e8\u7f72\uff0c\u62a5\u8868\u53c2\u6570"})
public class ReportParamController {
    @Value(value="${jiuqi.nr.transmission.show.export.content:false}")
    private boolean showExportContent;
    @Autowired
    private IReportParamService reportParamService;
    private List<ITransmissionDataGather> gatherList;

    @Autowired
    private void setGatherList(List<ITransmissionDataGather> gatherList) {
        this.gatherList = gatherList;
        this.gatherList.sort(Comparator.comparing(ITransmissionDataGather::getOrder));
    }

    @ApiOperation(value="\u4efb\u52a1\u5217\u8868")
    @PostMapping(value={"task"})
    public List<TaskNodeParam> taskList(@RequestBody List<String> keys) {
        return this.reportParamService.initTask(keys);
    }

    @ApiOperation(value="\u7528\u6237\u5217\u8868")
    @PostMapping(value={"user"})
    public List<TaskNodeParam> userList(@RequestBody List<String> keys) {
        return this.reportParamService.initUser(keys);
    }

    @ApiOperation(value="\u7528\u6237\u5b58\u5728\u68c0\u67e5")
    @PostMapping(value={"check_user"})
    public List<String> userCheck(@RequestBody List<String> keys) {
        return this.reportParamService.checkUser(keys);
    }

    @ApiOperation(value="\u62a5\u8868\u5217\u8868")
    @GetMapping(value={"forms/{formSchemeKey}"})
    public FormsQueryResult userCheck(@PathVariable String formSchemeKey) {
        return this.reportParamService.getForms(formSchemeKey);
    }

    @ApiOperation(value="\u67e5\u8be2\u6570\u636e\u7c7b\u578b")
    @GetMapping(value={"get_all_data_type"})
    public TransmissionDataVO getTransmissionDataGatherVO() {
        ArrayList<TransmissionDataGatherVO> transmissionDataGatherVOs = new ArrayList<TransmissionDataGatherVO>();
        for (ITransmissionDataGather iTransmissionDataGather : this.gatherList) {
            transmissionDataGatherVOs.add(TransmissionDataGatherVO.syncSchemeParamVoToDto(iTransmissionDataGather));
        }
        TransmissionDataVO result = new TransmissionDataVO();
        result.setDataGathers(transmissionDataGatherVOs);
        result.setShowExportContent(this.showExportContent);
        return result;
    }
}

