/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 */
package com.jiuqi.gcreport.samecontrol.actuator;

import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.job.SameCtrlExtractActuator;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractDataService;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlManageUtil;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import java.util.GregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="COMMON_UNIT_GO_BACK")
public class CommonUnitSameCtrlGoBack
implements SameCtrlExtractActuator {
    private static final Logger logger = LoggerFactory.getLogger(CommonUnitSameCtrlGoBack.class);
    @Autowired
    private SameCtrlExtractDataService sameCtrlExtractDataService;

    @Override
    public void sameCtrlExtractData(SameCtrlExtractDataVO sameCtrlExtractDataVO) {
        logger.info("\u5171\u540c\u4e0a\u7ea7\u540c\u63a7\u8868\u51b2\u56de\u5f00\u59cb\u3002");
        SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl(sameCtrlExtractDataVO.getSn());
        sameCtrlChgEnvContext.setSuccessFlag(true);
        this.sameCtrlExtractDataService.initReportSameCtrlChgEnvContextImpl(sameCtrlExtractDataVO, sameCtrlChgEnvContext);
        YearPeriodObject yearPeriodObject = new YearPeriodObject(sameCtrlExtractDataVO.getSchemeId(), sameCtrlExtractDataVO.getPeriodStr());
        GregorianCalendar gregorianCalendar = new GregorianCalendar(yearPeriodObject.getYear(), yearPeriodObject.getPeriod() - 1, 1);
        sameCtrlChgEnvContext.getSameCtrlExtractReportCond().setChangeDate(gregorianCalendar.getTime());
        sameCtrlChgEnvContext.getSameCtrlExtractReportCond().setDisposalDate(gregorianCalendar.getTime());
        sameCtrlChgEnvContext.getSameCtrlExtractReportCond().setChangedCode(sameCtrlExtractDataVO.getSameCtrlChgOrg().getCorrespondVirtualCode());
        sameCtrlChgEnvContext.getSameCtrlExtractReportCond().setGoBack(true);
        SameCtrlManageUtil.initAndCheckParam(sameCtrlChgEnvContext, sameCtrlExtractDataVO);
        this.sameCtrlExtractDataService.extractReportData(sameCtrlChgEnvContext);
        logger.info("\u5171\u540c\u4e0a\u7ea7\u540c\u63a7\u8868\u51b2\u56de\u7ed3\u675f\u3002");
    }
}

