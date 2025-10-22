/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrlExtractDataClient
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlEnvContextResultVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond
 *  com.jiuqi.nvwa.sf.anno.Licence
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.samecontrol.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.samecontrol.api.SameCtrlExtractDataClient;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractDataService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractLogService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlOffSetItemService;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlEnvContextResultVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond;
import com.jiuqi.nvwa.sf.anno.Licence;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
@Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.samecontrol")
public class SameCtrlExtractDataController
implements SameCtrlExtractDataClient {
    @Autowired
    private SameCtrlExtractDataService sameCtrlExtractDataService;
    @Autowired
    @Qualifier(value="sameCtrlBeginOffSetItemServiceImpl")
    private SameCtrlOffSetItemService beginOffSetItemService;
    @Autowired
    @Qualifier(value="sameCtrlEndOffSetItemServiceImpl")
    private SameCtrlOffSetItemService endOffSetItemService;
    @Autowired
    private ProgressService<SameCtrlChgEnvContextImpl, List<String>> progressService;
    @Autowired
    private SameCtrlExtractLogService sameCtrlExtractLogService;

    public BusinessResponseEntity<SameCtrlEnvContextResultVO> sameCtrlExtractData(SameCtrlExtractDataVO condition) {
        SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl(condition.getSn());
        sameCtrlChgEnvContext.setSuccessFlag(true);
        this.progressService.createProgressData((ProgressData)sameCtrlChgEnvContext);
        this.extractReportData(condition, sameCtrlChgEnvContext);
        this.extractBeginAndEndOffset(condition, sameCtrlChgEnvContext);
        SameCtrlEnvContextResultVO sameCtrlEnvContextResult = this.convertEnvResultVO(sameCtrlChgEnvContext);
        return BusinessResponseEntity.ok((Object)sameCtrlEnvContextResult);
    }

    public BusinessResponseEntity<ProgressData<List<String>>> querySnStartProgress(String sn) {
        ProgressData progressData = this.progressService.queryProgressData(sn);
        return BusinessResponseEntity.ok((Object)progressData);
    }

    public BusinessResponseEntity<Object> extractReportData(SameCtrlExtractReportCond condition) {
        SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl();
        sameCtrlChgEnvContext.setSameCtrlExtractReportCond(condition);
        this.sameCtrlExtractDataService.extractReportData(sameCtrlChgEnvContext);
        return BusinessResponseEntity.ok((Object)"\u6267\u884c\u6210\u529f");
    }

    public BusinessResponseEntity<Object> querySameCtrlExtractLog(SameCtrlExtractDataVO sameCtrlExtractDataVO) {
        SameCtrlExtractLogVO sameCtrlExtractLog = new SameCtrlExtractLogVO();
        BeanUtils.copyProperties(sameCtrlExtractDataVO, sameCtrlExtractLog);
        sameCtrlExtractLog.setChangedCode(sameCtrlExtractDataVO.getSameCtrlChgOrg().getChangedCode());
        SameCtrlExtractLogVO sameCtrlExtractLogResult = this.sameCtrlExtractLogService.querySameCtrlExtractLog(sameCtrlExtractLog);
        return BusinessResponseEntity.ok((Object)sameCtrlExtractLogResult);
    }

    private void extractReportData(SameCtrlExtractDataVO condition, SameCtrlChgEnvContextImpl sameCtrlChgEnvContext) {
        this.sameCtrlExtractDataService.initReportSameCtrlChgEnvContextImpl(condition, sameCtrlChgEnvContext);
        this.sameCtrlExtractDataService.extractReportData(sameCtrlChgEnvContext);
    }

    private void extractBeginAndEndOffset(SameCtrlExtractDataVO sameCtrlOffsetCond, SameCtrlChgEnvContextImpl sameCtrlChgEnvContext) {
        this.sameCtrlExtractDataService.initOffsetSameCtrlChgEnvContextImpl(sameCtrlOffsetCond, sameCtrlChgEnvContext);
        this.endOffSetItemService.extractData(sameCtrlChgEnvContext);
        SameCtrlOffsetCond sameCtrlOffsetCondBegin = new SameCtrlOffsetCond();
        BeanUtils.copyProperties(sameCtrlChgEnvContext.getSameCtrlOffsetCond(), sameCtrlOffsetCondBegin);
        sameCtrlOffsetCondBegin.setMergeUnitCode(sameCtrlOffsetCond.getSameCtrlChgOrg().getChangedParentCode());
        sameCtrlChgEnvContext.setSameCtrlOffsetCond(sameCtrlOffsetCondBegin);
        sameCtrlChgEnvContext.setResult(CollectionUtils.newArrayList());
        this.beginOffSetItemService.extractData(sameCtrlChgEnvContext);
    }

    private SameCtrlEnvContextResultVO convertEnvResultVO(SameCtrlChgEnvContextImpl sameCtrlChgEnvContext) {
        SameCtrlEnvContextResultVO contextResultVO = new SameCtrlEnvContextResultVO();
        if (sameCtrlChgEnvContext != null) {
            BeanUtils.copyProperties(sameCtrlChgEnvContext, contextResultVO);
        } else {
            contextResultVO.setSuccessFlag(false);
        }
        sameCtrlChgEnvContext.setProgressValueAndRefresh(1.0);
        return contextResultVO;
    }
}

