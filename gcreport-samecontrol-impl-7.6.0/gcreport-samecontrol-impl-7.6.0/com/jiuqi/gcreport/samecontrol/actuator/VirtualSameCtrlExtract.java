/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 */
package com.jiuqi.gcreport.samecontrol.actuator;

import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.job.SameCtrlExtractActuator;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractDataService;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlManageUtil;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="VIRTUAL")
public class VirtualSameCtrlExtract
implements SameCtrlExtractActuator {
    private static final Logger logger = LoggerFactory.getLogger(VirtualSameCtrlExtract.class);
    @Autowired
    private SameCtrlExtractDataService sameCtrlExtractDataService;

    @Override
    public void sameCtrlExtractData(SameCtrlExtractDataVO sameCtrlExtractDataVO) {
        logger.info("\u540c\u63a7\u865a\u62df\u8868\u63d0\u53d6\u5f00\u59cb\u3002");
        SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl(sameCtrlExtractDataVO.getSn());
        sameCtrlChgEnvContext.setSuccessFlag(true);
        this.sameCtrlExtractDataService.initReportSameCtrlChgEnvContextImpl(sameCtrlExtractDataVO, sameCtrlChgEnvContext);
        sameCtrlChgEnvContext.getSameCtrlExtractReportCond().setDisposalDate(sameCtrlExtractDataVO.getSameCtrlChgOrg().getDisposalDate());
        SameCtrlManageUtil.initAndCheckParam(sameCtrlChgEnvContext, sameCtrlExtractDataVO);
        this.sameCtrlExtractDataService.extractReportData(sameCtrlChgEnvContext);
        logger.info("\u540c\u63a7\u865a\u62df\u8868\u63d0\u53d6\u7ed3\u675f\u3002");
    }
}

