/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlChangeOrgDateTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO
 */
package com.jiuqi.gcreport.samecontrol.actuator;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlChangeOrgDateTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractOperateEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlExtractTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.job.SameCtrlExtractActuator;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractDataService;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlExtractBdeReport;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlExtractInvestExecuteRule;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlExtractManageServiceImpl;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlOffSetItemServiceImpl;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractLogVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="DISPOSER")
public class DisposerSameCtrlExtract
extends SameCtrlOffSetItemServiceImpl
implements SameCtrlExtractActuator {
    private static final Logger logger = LoggerFactory.getLogger(DisposerSameCtrlExtract.class);
    @Autowired
    private SameCtrlExtractDataService sameCtrlExtractDataService;
    @Autowired
    private SameCtrlExtractInvestExecuteRule sameCtrlExtractInvestExecuteRule;
    @Autowired
    private SameCtrlExtractManageServiceImpl sameCtrlExtractManageService;
    @Autowired
    private SameCtrlExtractBdeReport sameCtrlExtractBdeReport;

    @Override
    public void sameCtrlExtractData(SameCtrlExtractDataVO sameCtrlExtractDataVO) {
        logger.info("\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u63d0\u53d6\u5f00\u59cb\u3002");
        SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl(sameCtrlExtractDataVO.getSn());
        sameCtrlChgEnvContext.setSuccessFlag(true);
        this.sameCtrlExtractDataService.initOffsetSameCtrlChgEnvContextImpl(sameCtrlExtractDataVO, sameCtrlChgEnvContext);
        this.sameCtrlExtractManageService.initSameCtrlExtractManageCond(sameCtrlExtractDataVO, sameCtrlChgEnvContext, SameCtrlSrcTypeEnum.DISPOSER_INVEST);
        SameCtrlExtractLogVO sameCtrlExtractLog = this.addSameCtrlExtractLogVO(sameCtrlChgEnvContext, SameCtrlExtractOperateEnum.VIRTUALPARENT_EXTRACT);
        String vueLog = "";
        try {
            SameCtrlChangeOrgDateTypeEnum sameCtrlChangeOrgDateTypeEnum = this.checkSameCtrlOrgDateType(sameCtrlChgEnvContext);
            switch (sameCtrlChangeOrgDateTypeEnum) {
                case CURR_YEAR_CURR_MONTH: {
                    logger.info("\u5904\u7f6e\u65b9\u6295\u8d44\u5904\u7f6e\u63d0\u53d6\u5f00\u59cb\u3002");
                    sameCtrlChgEnvContext.getSameCtrlExtractManageCond().setSameCtrlSrcTypeEnum(SameCtrlSrcTypeEnum.DISPOSER_INVEST);
                    sameCtrlChgEnvContext.getSameCtrlExtractManageCond().setSameCtrlRuleTypeEnum(SameCtrlRuleTypeEnum.DISPOSER_INVESTMENT);
                    sameCtrlChgEnvContext.getSameCtrlExtractManageCond().setAcquirerCode(this.filterSameCtrlChgOrgEOByOrgType(sameCtrlChgEnvContext.getSameCtrlExtractManageCond().getSameCtrlChgOrgEOList(), SameCtrlExtractTypeEnum.VIRTUAL.getCode()).getVirtualCode());
                    this.sameCtrlExtractInvestExecuteRule.sameCtrlExtractInvest(sameCtrlChgEnvContext);
                    logger.info("\u5904\u7f6e\u65b9\u6295\u8d44\u5904\u7f6e\u63d0\u53d6\u7ed3\u675f\u3002");
                    break;
                }
                case CURR_YEAR_AFTER_MONTH: {
                    logger.info("\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u4e0a\u671f\u62b5\u9500\u5206\u5f55\u63d0\u53d6\u5f00\u59cb\u3002");
                    ArrayList<SameCtrlSrcTypeEnum> sameCtrlSrcTypeEnumList = new ArrayList<SameCtrlSrcTypeEnum>();
                    sameCtrlSrcTypeEnumList.add(SameCtrlSrcTypeEnum.DISPOSER_INVEST);
                    sameCtrlSrcTypeEnumList.add(SameCtrlSrcTypeEnum.DISPOSER_INPUT_ADJUST);
                    sameCtrlSrcTypeEnumList.add(SameCtrlSrcTypeEnum.DISPOSER_LAST_DATE_INVEST);
                    sameCtrlChgEnvContext.getSameCtrlExtractManageCond().setSameCtrlSrcTypeEnumList(sameCtrlSrcTypeEnumList);
                    sameCtrlChgEnvContext.getSameCtrlExtractManageCond().setSameCtrlSrcTypeEnum(SameCtrlSrcTypeEnum.DISPOSER_INVEST);
                    sameCtrlChgEnvContext.getSameCtrlExtractManageCond().setSameCtrlLastDateSrcTypeEnum(SameCtrlSrcTypeEnum.DISPOSER_LAST_DATE_INVEST);
                    this.sameCtrlExtractManageService.extractLastMonthSameCtrlOffSet(sameCtrlChgEnvContext);
                    logger.info("\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u4e0a\u671f\u62b5\u9500\u5206\u5f55\u7ed3\u675f\u3002");
                    break;
                }
            }
            ((List)sameCtrlChgEnvContext.getResult()).add("\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u63d0\u53d6\u6210\u529f\u3002");
            vueLog = vueLog + "\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u63d0\u53d6\u6210\u529f\u3002";
        }
        catch (Exception e) {
            String log = "\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u63d0\u53d6\u5931\u8d25\uff1a" + e.getMessage() + "\u3002";
            logger.error(log, e);
            vueLog = this.updateLogMessage(sameCtrlExtractLog, sameCtrlChgEnvContext, log, vueLog);
            this.updateSameCtrlExtractLog(sameCtrlChgEnvContext, sameCtrlExtractLog);
            throw new BusinessRuntimeException(vueLog);
        }
        try {
            logger.info("\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u56de\u5199\u5f00\u59cb\u3002");
            sameCtrlChgEnvContext.getSameCtrlOffsetCond().setPeriodStr(sameCtrlExtractDataVO.getPeriodStr());
            this.sameCtrlExtractBdeReport.executeBdeExtract(sameCtrlChgEnvContext);
            ((List)sameCtrlChgEnvContext.getResult()).add("\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u56de\u5199\u6210\u529f\u3002");
            logger.info("\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u56de\u5199\u7ed3\u675f\u3002");
            vueLog = vueLog + "\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u56de\u5199\u6210\u529f\u3002";
        }
        catch (Exception e) {
            String log = "\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u56de\u5199\u5931\u8d25\uff1a" + e.getMessage() + "\u3002";
            logger.error(log, e);
            vueLog = this.updateLogMessage(sameCtrlExtractLog, sameCtrlChgEnvContext, log, vueLog);
        }
        this.updateSameCtrlExtractLog(sameCtrlChgEnvContext, sameCtrlExtractLog);
        logger.info("\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500\u63d0\u53d6\u7ed3\u675f\u3002");
        if (!sameCtrlChgEnvContext.isSuccessFlag()) {
            throw new BusinessRuntimeException(vueLog);
        }
    }

    private SameCtrlChgOrgEO filterSameCtrlChgOrgEOByOrgType(List<SameCtrlChgOrgEO> sameCtrlCh, String orgType) {
        List sameCtrlChgOrgEOList = sameCtrlCh.stream().filter(sameCtrlChgOrgEO -> sameCtrlChgOrgEO.getVirtualCodeType().equals(orgType)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(sameCtrlChgOrgEOList)) {
            throw new RuntimeException("\u540c\u63a7\u53d8\u52a8\u4fe1\u606f\u4e0d\u5168");
        }
        return (SameCtrlChgOrgEO)((Object)sameCtrlChgOrgEOList.get(0));
    }
}

