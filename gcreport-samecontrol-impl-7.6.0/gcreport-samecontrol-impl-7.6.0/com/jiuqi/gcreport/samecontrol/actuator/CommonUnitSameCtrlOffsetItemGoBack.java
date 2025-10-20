/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO
 */
package com.jiuqi.gcreport.samecontrol.actuator;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.job.SameCtrlExtractActuator;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractDataService;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlExtractBdeReport;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlExtractManageServiceImpl;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlOffSetItemServiceImpl;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="COMMON_UNIT_OFFSET_GO_BACK")
public class CommonUnitSameCtrlOffsetItemGoBack
extends SameCtrlOffSetItemServiceImpl
implements SameCtrlExtractActuator {
    private static final Logger logger = LoggerFactory.getLogger(CommonUnitSameCtrlOffsetItemGoBack.class);
    @Autowired
    private SameCtrlExtractDataService sameCtrlExtractDataService;
    @Autowired
    private SameCtrlExtractManageServiceImpl sameCtrlExtractManageService;
    @Autowired
    private SameCtrlExtractBdeReport sameCtrlExtractBdeReport;

    @Override
    public void sameCtrlExtractData(SameCtrlExtractDataVO sameCtrlExtractDataVO) {
        logger.info("\u5171\u540c\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u51b2\u56de\u5f00\u59cb\u3002");
        SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl(sameCtrlExtractDataVO.getSn());
        sameCtrlChgEnvContext.setSuccessFlag(true);
        this.sameCtrlExtractDataService.initOffsetSameCtrlChgEnvContextImpl(sameCtrlExtractDataVO, sameCtrlChgEnvContext);
        this.sameCtrlExtractManageService.initSameCtrlExtractManageCond(sameCtrlExtractDataVO, sameCtrlChgEnvContext, null);
        SameCtrlExtractLogVO sameCtrlExtractLog = this.addSameCtrlExtractLogVO(sameCtrlChgEnvContext, SameCtrlExtractOperateEnum.GO_BACK);
        String vueLog = "";
        try {
            logger.info("\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u56de\u5199\u5f00\u59cb\u3002");
            sameCtrlChgEnvContext.getSameCtrlOffsetCond().setPeriodStr(sameCtrlExtractDataVO.getPeriodStr());
            this.sameCtrlExtractBdeReport.executeBdeExtract(sameCtrlChgEnvContext);
            ((List)sameCtrlChgEnvContext.getResult()).add("\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u56de\u5199\u6210\u529f\u3002");
            logger.info("\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u56de\u5199\u7ed3\u675f\u3002");
            vueLog = vueLog + "\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u56de\u5199\u6210\u529f\u3002";
        }
        catch (Exception e) {
            String log = "\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u63d0\u53d6\u5931\u8d25\uff1a" + e.getMessage() + "\u3002";
            logger.error(log, e);
            vueLog = this.updateLogMessage(sameCtrlExtractLog, sameCtrlChgEnvContext, log, vueLog);
        }
        this.updateSameCtrlExtractLog(sameCtrlChgEnvContext, sameCtrlExtractLog);
        logger.info("\u5171\u540c\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u51b2\u56de\u7ed3\u675f\u3002");
        if (!sameCtrlChgEnvContext.isSuccessFlag()) {
            throw new BusinessRuntimeException(vueLog);
        }
    }
}

