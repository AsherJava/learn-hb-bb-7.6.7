/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.reportparam.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.reportparam.service.GcReportParamInitService;
import com.jiuqi.gcreport.reportparam.vo.GcReportParamInitExecuteVO;
import com.jiuqi.gcreport.reportparam.vo.GcReportParamInitVO;
import com.jiuqi.gcreport.reportparam.vo.GcReportParamProgressVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GcReportParamInitWeb {
    private static final String baseUrl = "/api/gcreport/v1/report/paraminit";
    @Autowired
    private ProgressService<ProgressDataImpl<List<GcReportParamProgressVO>>, List<GcReportParamProgressVO>> progressService;
    @Autowired
    private GcReportParamInitService gcReportParamInitService;

    @GetMapping(value={"/api/gcreport/v1/report/paraminit/reportparam/list"})
    public BusinessResponseEntity<List<GcReportParamInitVO>> listReportParamPackage() {
        return BusinessResponseEntity.ok(this.gcReportParamInitService.listReportParamPackage());
    }

    @PostMapping(value={"/api/gcreport/v1/report/paraminit/start"})
    public BusinessResponseEntity startInit(@RequestBody GcReportParamInitExecuteVO executeVO) {
        this.gcReportParamInitService.startInit(executeVO);
        return BusinessResponseEntity.ok();
    }

    @GetMapping(value={"/api/gcreport/v1/report/paraminit/progress/{sn}"})
    public BusinessResponseEntity<ProgressData<List<GcReportParamProgressVO>>> progress(@PathVariable(value="sn") String sn) {
        ProgressData progressData = this.progressService.queryProgressData(sn, false);
        return BusinessResponseEntity.ok((Object)progressData);
    }

    @PostMapping(value={"/api/gcreport/v1/report/paraminit/progress/remove/{sn}"})
    public BusinessResponseEntity removeProgress(@PathVariable(value="sn") String sn) {
        this.progressService.removeProgressData(sn);
        return BusinessResponseEntity.ok();
    }
}

