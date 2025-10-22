/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlChangeOrgDateTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlQueryParamsVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO
 */
package com.jiuqi.gcreport.samecontrol.actuator;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlChangeOrgDateTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.job.SameCtrlExtractActuator;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractDataService;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlExtractBdeReport;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlExtractManageServiceImpl;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlExtractOffsetData;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlOffSetItemServiceImpl;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlQueryParamsVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="ACQUIRER_PARENT")
public class AcquirerParentSameCtrlExtract
extends SameCtrlOffSetItemServiceImpl
implements SameCtrlExtractActuator {
    private static final Logger logger = LoggerFactory.getLogger(AcquirerParentSameCtrlExtract.class);
    @Autowired
    private SameCtrlExtractOffsetData sameCtrlExtractOffsetData;
    @Autowired
    private SameCtrlExtractDataService sameCtrlExtractDataService;
    @Autowired
    private SameCtrlExtractManageServiceImpl sameCtrlExtractManageService;
    @Autowired
    private SameCtrlExtractBdeReport sameCtrlExtractBdeReport;

    @Override
    public void sameCtrlExtractData(SameCtrlExtractDataVO sameCtrlExtractDataVO) {
        logger.info("\u6536\u8d2d\u65b9\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u63d0\u53d6\u5f00\u59cb\u3002");
        SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl(sameCtrlExtractDataVO.getSn());
        sameCtrlChgEnvContext.setSuccessFlag(true);
        this.sameCtrlExtractDataService.initOffsetSameCtrlChgEnvContextImpl(sameCtrlExtractDataVO, sameCtrlChgEnvContext);
        this.sameCtrlExtractManageService.initSameCtrlExtractManageCond(sameCtrlExtractDataVO, sameCtrlChgEnvContext, SameCtrlSrcTypeEnum.ACQUIRER_PARENT_BEGIN_EXTRACT);
        SameCtrlExtractLogVO sameCtrlExtractLog = this.addSameCtrlExtractLogVO(sameCtrlChgEnvContext, SameCtrlExtractOperateEnum.CHANGEDPARENT_EXTRACT);
        String vueLog = "";
        try {
            SameCtrlChangeOrgDateTypeEnum sameCtrlChangeOrgDateTypeEnum = this.checkSameCtrlOrgDateType(sameCtrlChgEnvContext);
            SameCtrlQueryParamsVO queryParamsVO = this.getQueryParams(sameCtrlExtractDataVO, sameCtrlChgEnvContext);
            switch (sameCtrlChangeOrgDateTypeEnum) {
                case CURR_YEAR_CURR_MONTH: 
                case CURR_YEAR_AFTER_MONTH: {
                    logger.info("\u540c\u63a7\u53d8\u52a8\u5355\u4f4d\u65f6\u671f\u7c7b\u578b\u3010{}\u3011", (Object)sameCtrlChangeOrgDateTypeEnum.getName());
                    this.sameCtrlExtractBeginOffset(sameCtrlChgEnvContext, queryParamsVO);
                    this.sameCtrlExtractLastYearOffset(sameCtrlChgEnvContext, queryParamsVO);
                    break;
                }
                case AFTER_YEAR_CURR_MONTH: {
                    this.sameCtrlExtractLastYearOffset(sameCtrlChgEnvContext, queryParamsVO);
                    break;
                }
                case AFTER_YEAR_AFTER_MONTH: {
                    logger.info("\u5f53\u524d\u865a\u62df\u5355\u4f4d\u4e0a\u671f\u62b5\u9500\u5206\u5f55\u63d0\u53d6\u5f00\u59cb\u3002");
                    ArrayList<SameCtrlSrcTypeEnum> sameCtrlSrcTypeEnumList = new ArrayList<SameCtrlSrcTypeEnum>();
                    sameCtrlSrcTypeEnumList.add(SameCtrlSrcTypeEnum.ACQUIRER_PARENT_BEFORE_EXTRACT);
                    sameCtrlSrcTypeEnumList.add(SameCtrlSrcTypeEnum.ACQUIRER_PARENT_INPUT_ADJUST);
                    sameCtrlSrcTypeEnumList.add(SameCtrlSrcTypeEnum.ACQUIRER_PARENT_DATE_EXTRACT);
                    sameCtrlChgEnvContext.getSameCtrlExtractManageCond().setSameCtrlSrcTypeEnumList(sameCtrlSrcTypeEnumList);
                    sameCtrlChgEnvContext.getSameCtrlExtractManageCond().setSameCtrlSrcTypeEnum(SameCtrlSrcTypeEnum.ACQUIRER_PARENT_BEFORE_EXTRACT);
                    sameCtrlChgEnvContext.getSameCtrlExtractManageCond().setSameCtrlLastDateSrcTypeEnum(SameCtrlSrcTypeEnum.ACQUIRER_PARENT_DATE_EXTRACT);
                    this.sameCtrlExtractManageService.extractLastMonthSameCtrlOffSet(sameCtrlChgEnvContext);
                    logger.info("\u5f53\u524d\u865a\u62df\u5355\u4f4d\u4e0a\u671f\u62b5\u9500\u5206\u5f55\u63d0\u53d6\u7ed3\u675f\u3002");
                    break;
                }
            }
            ((List)sameCtrlChgEnvContext.getResult()).add("\u6536\u8d2d\u65b9\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u63d0\u53d6\u6210\u529f\u3002");
            vueLog = vueLog + "\u6536\u8d2d\u65b9\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u63d0\u53d6\u6210\u529f\u3002";
        }
        catch (Exception e) {
            String log = "\u6536\u8d2d\u65b9\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u63d0\u53d6\u5931\u8d25\uff1a" + e.getMessage() + "\u3002";
            logger.error(log, e);
            vueLog = this.updateLogMessage(sameCtrlExtractLog, sameCtrlChgEnvContext, log, vueLog);
            this.updateSameCtrlExtractLog(sameCtrlChgEnvContext, sameCtrlExtractLog);
            throw new BusinessRuntimeException(vueLog);
        }
        try {
            logger.info("\u6536\u8d2d\u65b9\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u56de\u5199\u5f00\u59cb\u3002");
            sameCtrlChgEnvContext.getSameCtrlOffsetCond().setPeriodStr(sameCtrlExtractDataVO.getPeriodStr());
            this.sameCtrlExtractBdeReport.executeBdeExtract(sameCtrlChgEnvContext);
            logger.info("\u6536\u8d2d\u65b9\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u56de\u5199\u7ed3\u675f\u3002");
            ((List)sameCtrlChgEnvContext.getResult()).add("\u6536\u8d2d\u65b9\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u56de\u5199\u6210\u529f\u3002");
            vueLog = vueLog + "\u6536\u8d2d\u65b9\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u56de\u5199\u6210\u529f\u3002";
        }
        catch (Exception e) {
            String log = "\u6536\u8d2d\u65b9\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u56de\u5199\u5931\u8d25\uff1a" + e.getMessage() + "\u3002";
            logger.error(log, e);
            vueLog = this.updateLogMessage(sameCtrlExtractLog, sameCtrlChgEnvContext, log, vueLog);
        }
        this.updateSameCtrlExtractLog(sameCtrlChgEnvContext, sameCtrlExtractLog);
        logger.info("\u6536\u8d2d\u65b9\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u63d0\u53d6\u7ed3\u675f\u3002");
        if (!sameCtrlChgEnvContext.isSuccessFlag()) {
            throw new BusinessRuntimeException(vueLog);
        }
    }

    private void sameCtrlExtractBeginOffset(SameCtrlChgEnvContextImpl sameCtrlChgEnvContext, SameCtrlQueryParamsVO queryParamsVO) {
        logger.info("\u671f\u521d\u8d44\u4ea7\u8d1f\u503a\u5206\u5f55\u63d0\u53d6\u5f00\u59cb\u3002");
        sameCtrlChgEnvContext.getSameCtrlExtractManageCond().setSameCtrlSrcTypeEnum(SameCtrlSrcTypeEnum.ACQUIRER_PARENT_BEGIN_EXTRACT);
        this.sameCtrlExtractOffsetData.sameCtrlExtractOffset(sameCtrlChgEnvContext, queryParamsVO);
        logger.info("\u671f\u521d\u8d44\u4ea7\u8d1f\u503a\u5206\u5f55\u63d0\u53d6\u7ed3\u675f\u3002");
    }

    private void sameCtrlExtractLastYearOffset(SameCtrlChgEnvContextImpl sameCtrlChgEnvContext, SameCtrlQueryParamsVO queryParamsVO) {
        logger.info("\u4e0a\u5e74\u540c\u671f\u635f\u76ca\u548c\u73b0\u6d41\u5206\u5f55\u63d0\u53d6\u5f00\u59cb\u3002");
        sameCtrlChgEnvContext.getSameCtrlExtractManageCond().setSameCtrlSrcTypeEnum(SameCtrlSrcTypeEnum.ACQUIRER_PARENT_BEFORE_EXTRACT);
        this.sameCtrlExtractOffsetData.sameCtrlExtractOffset(sameCtrlChgEnvContext, queryParamsVO);
        logger.info("\u4e0a\u5e74\u540c\u671f\u635f\u76ca\u548c\u73b0\u6d41\u5206\u5f55\u63d0\u53d6\u7ed3\u675f\u3002");
    }
}

