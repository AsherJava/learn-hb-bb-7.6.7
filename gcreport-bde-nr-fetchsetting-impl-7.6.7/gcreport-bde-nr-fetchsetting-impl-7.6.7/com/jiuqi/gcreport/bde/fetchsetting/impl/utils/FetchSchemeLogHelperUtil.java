/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.BizTypeValidator
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.period.service.PeriodDataService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.utils;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.BizTypeValidator;
import com.jiuqi.gcreport.bde.fetchsetting.impl.web.FetchSchemeController;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.service.PeriodDataService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetchSchemeLogHelperUtil {
    private static final Logger log = LoggerFactory.getLogger(FetchSchemeLogHelperUtil.class);
    private static final String FETCH_SCHEME_MODULE = "BDE\u53d6\u6570\u8bbe\u7f6e";

    public static void saveFetchSchemeLogInfo(FetchSchemeVO fetchSchemeVO) {
        try {
            log.debug("\u65b0\u5efaBDE\u53d6\u6570\u65b9\u6848{}", (Object)JsonUtils.writeValueAsString((Object)fetchSchemeVO));
            String formSchemeId = fetchSchemeVO.getFormSchemeId();
            String fetchSchemeName = fetchSchemeVO.getName();
            String bizType = fetchSchemeVO.getBizType();
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
            FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeId);
            if (Objects.nonNull(formScheme)) {
                TaskDefine taskDefine = runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                LogHelper.info((String)FETCH_SCHEME_MODULE, (String)String.format("\u65b0\u5efaBDE\u53d6\u6570\u65b9\u6848-\u53d6\u6570\u65b9\u6848\u3010%1$s\u3011", fetchSchemeName), (String)String.format("\u65b0\u5efaBDE\u53d6\u6570\u65b9\u6848-\u4efb\u52a1\u3010%1$s\u3011-\u62a5\u8868\u65b9\u6848\u3010%2$s\u3011-\u53d6\u6570\u65b9\u6848\u3010%3$s\u3011", taskDefine.getTitle(), formScheme.getTitle(), fetchSchemeName));
            } else {
                BizTypeValidator.isValidBud((String)bizType, (String)String.format("\u6839\u636e\u62a5\u8868\u65b9\u6848ID%1$s\u672a\u83b7\u53d6\u5230\u62a5\u8868\u65b9\u6848", formSchemeId));
                LogHelper.info((String)FETCH_SCHEME_MODULE, (String)String.format("\u65b0\u5efaBDE\u53d6\u6570\u65b9\u6848-\u53d6\u6570\u65b9\u6848\u3010%1$s\u3011", fetchSchemeName), (String)String.format("\u65b0\u5efaBDE\u53d6\u6570\u65b9\u6848-\u53d6\u6570\u65b9\u6848\u3010%1$s\u3011", fetchSchemeName));
            }
        }
        catch (Exception e) {
            log.error("\u65b0\u5efaBDE\u53d6\u6570\u65b9\u6848\u8bb0\u5f55\u65e5\u5fd7\u5f02\u5e38", e);
        }
    }

    public static void deleteFetchSchemeLogInfo(FetchSchemeVO fetchSchemeVO) {
        try {
            log.debug("\u5220\u9664BDE\u53d6\u6570\u65b9\u6848{}", (Object)JsonUtils.writeValueAsString((Object)fetchSchemeVO));
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
            String formSchemeId = fetchSchemeVO.getFormSchemeId();
            String fetchSchemeName = fetchSchemeVO.getName();
            String bizType = fetchSchemeVO.getBizType();
            FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeId);
            if (Objects.nonNull(formScheme)) {
                TaskDefine taskDefine = runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                LogHelper.info((String)FETCH_SCHEME_MODULE, (String)String.format("\u5220\u9664BDE\u53d6\u6570\u65b9\u6848-\u53d6\u6570\u65b9\u6848\u3010%1$s\u3011", fetchSchemeName), (String)String.format("\u5220\u9664BDE\u53d6\u6570\u65b9\u6848-\u4efb\u52a1\u3010%1$s\u3011-\u62a5\u8868\u65b9\u6848\u3010%2$s\u3011-\u53d6\u6570\u65b9\u6848\u3010%3$s\u3011", taskDefine.getTitle(), formScheme.getTitle(), fetchSchemeName));
            } else {
                BizTypeValidator.isValidBud((String)bizType, (String)String.format("\u6839\u636e\u62a5\u8868\u65b9\u6848ID%1$s\u672a\u83b7\u53d6\u5230\u62a5\u8868\u65b9\u6848", formSchemeId));
                LogHelper.info((String)FETCH_SCHEME_MODULE, (String)String.format("\u5220\u9664BDE\u53d6\u6570\u65b9\u6848-\u53d6\u6570\u65b9\u6848\u3010%1$s\u3011", fetchSchemeName), (String)String.format("\u5220\u9664BDE\u53d6\u6570\u65b9\u6848-\u53d6\u6570\u65b9\u6848\u3010%1$s\u3011", fetchSchemeName));
            }
        }
        catch (Exception e) {
            log.error("\u5220\u9664BDE\u53d6\u6570\u65b9\u6848\u8bb0\u5f55\u65e5\u5fd7\u5f02\u5e38", e);
        }
    }

    public static void updateFetchSchemeLogInfo(FetchSchemeVO fetchSchemeVO, String oldFetchSchemeName) {
        try {
            log.debug("\u4fee\u6539BDE\u53d6\u6570\u65b9\u6848{}", (Object)JsonUtils.writeValueAsString((Object)fetchSchemeVO));
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
            String formSchemeId = fetchSchemeVO.getFormSchemeId();
            String fetchSchemeName = fetchSchemeVO.getName();
            String bizType = fetchSchemeVO.getBizType();
            FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeId);
            if (Objects.nonNull(formScheme)) {
                TaskDefine taskDefine = runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                LogHelper.info((String)FETCH_SCHEME_MODULE, (String)String.format("\u4fee\u6539BDE\u53d6\u6570\u65b9\u6848-\u53d6\u6570\u65b9\u6848\u3010%1$s\u3011", fetchSchemeName), (String)String.format("\u4fee\u6539BDE\u53d6\u6570\u65b9\u6848-\u4efb\u52a1\u3010%1$s\u3011-\u62a5\u8868\u65b9\u6848\u3010%2$s\u3011-\u53d6\u6570\u65b9\u6848\u3010%3$s\u3011\u4e3a \u53d6\u6570\u65b9\u6848\u3010%4$s\u3011", taskDefine.getTitle(), formScheme.getTitle(), oldFetchSchemeName, fetchSchemeName));
            } else {
                BizTypeValidator.isValidBud((String)bizType, (String)String.format("\u6839\u636e\u62a5\u8868\u65b9\u6848ID%1$s\u672a\u83b7\u53d6\u5230\u62a5\u8868\u65b9\u6848", formSchemeId));
                LogHelper.info((String)FETCH_SCHEME_MODULE, (String)String.format("\u4fee\u6539BDE\u53d6\u6570\u65b9\u6848-\u53d6\u6570\u65b9\u6848\u3010%1$s\u3011", fetchSchemeName), (String)String.format("\u4fee\u6539BDE\u53d6\u6570\u65b9\u6848-\u53d6\u6570\u65b9\u6848\u3010%1$s\u3011", fetchSchemeName));
            }
        }
        catch (Exception e) {
            log.error("\u4fee\u6539BDE\u53d6\u6570\u65b9\u6848\u8bb0\u5f55\u65e5\u5fd7\u5f02\u5e38", e);
        }
    }

    public static void copyFetchSchemeLogInfo(String formSchemeId, String fetchSchemeName, String sourceFetchSchemeName, String bizType) {
        try {
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
            FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeId);
            if (Objects.nonNull(formScheme)) {
                TaskDefine taskDefine = runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                LogHelper.info((String)FETCH_SCHEME_MODULE, (String)String.format("\u590d\u5236BDE\u53d6\u6570\u65b9\u6848-\u53d6\u6570\u65b9\u6848\u3010%1$s\u3011", fetchSchemeName), (String)String.format("\u590d\u5236BDE\u53d6\u6570\u65b9\u6848-\u4efb\u52a1\u3010%1$s\u3011-\u62a5\u8868\u65b9\u6848\u3010%2$s\u3011-\u539f\u53d6\u6570\u65b9\u6848\u3010%3$s\u3011\u4e3a\u53d6\u6570\u65b9\u6848\u3010%4$s\u3011", taskDefine.getTitle(), formScheme.getTitle(), sourceFetchSchemeName, fetchSchemeName));
            } else {
                BizTypeValidator.isValidBud((String)bizType, (String)String.format("\u6839\u636e\u62a5\u8868\u65b9\u6848ID%1$s\u672a\u83b7\u53d6\u5230\u62a5\u8868\u65b9\u6848", formSchemeId));
                LogHelper.info((String)FETCH_SCHEME_MODULE, (String)String.format("\u590d\u5236BDE\u53d6\u6570\u65b9\u6848-\u53d6\u6570\u65b9\u6848\u3010%1$s\u3011", fetchSchemeName), (String)String.format("\u590d\u5236BDE\u53d6\u6570\u65b9\u6848-\u539f\u53d6\u6570\u65b9\u6848\u3010%1$s\u3011\u4e3a\u53d6\u6570\u65b9\u6848\u3010%2$s\u3011", sourceFetchSchemeName, fetchSchemeName));
            }
        }
        catch (Exception e) {
            log.error("\u590d\u5236BDE\u53d6\u6570\u65b9\u6848\u8bb0\u5f55\u65e5\u5fd7\u5f02\u5e38", e);
        }
    }

    public static void saveAdjustPeriodSettingLogInfo(List<AdjustPeriodSettingVO> adjustPeriodSettingVOS, String fetchSchemeId) {
        try {
            PeriodDataService PeriodDataService2 = (PeriodDataService)ApplicationContextRegister.getBean(PeriodDataService.class);
            log.debug("BDE\u53d6\u6570\u8bbe\u7f6e-\u8bbe\u7f6e\u8c03\u6574\u671fadjustPeriodSettingVOS:{};fetchSchemeId={}", (Object)JsonUtils.writeValueAsString(adjustPeriodSettingVOS), (Object)fetchSchemeId);
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class);
            FetchSchemeController fetchSchemeController = (FetchSchemeController)ApplicationContextRegister.getBean(FetchSchemeController.class);
            FetchSchemeVO fetchSchemeVO = (FetchSchemeVO)fetchSchemeController.queryFetchSchemeById(fetchSchemeId).getData();
            if (Objects.isNull(fetchSchemeVO)) {
                return;
            }
            FormSchemeDefine formScheme = runTimeViewController.getFormScheme(fetchSchemeVO.getFormSchemeId());
            String formate = "${adjustPeriod} \u5f00\u59cb\u65f6\u671f${startAdjustPeriod}\u6708 \u7ed3\u675f\u65f6\u671f${endAdjustPeriod}\u6708;";
            StringBuilder adjustPeriodString = new StringBuilder();
            for (AdjustPeriodSettingVO adjustPeriodSettingVO : adjustPeriodSettingVOS) {
                String adjustPeriod = adjustPeriodSettingVO.getAdjustPeriod();
                IPeriodRow iPeriodRow = PeriodDataService2.queryPeriodDataByPeriodCodeAndDataCode(String.valueOf(adjustPeriod.charAt(4)), adjustPeriod);
                String formateStr = formate;
                adjustPeriodString.append(formateStr.replace("${adjustPeriod}", iPeriodRow.getTitle()).replace("${startAdjustPeriod}", adjustPeriodSettingVO.getStartAdjustPeriod()).replace("${endAdjustPeriod}", adjustPeriodSettingVO.getEndAdjustPeriod()));
            }
            if (Objects.nonNull(formScheme)) {
                TaskDefine taskDefine = runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                LogHelper.info((String)FETCH_SCHEME_MODULE, (String)"\u8bbe\u7f6e\u8d26\u671f\u8c03\u6574\u65b9\u6848", (String)String.format("\u8bbe\u7f6e\u8d26\u671f\u8c03\u6574\u65b9\u6848-\u4efb\u52a1\u3010%1$s\u3011-\u62a5\u8868\u65b9\u6848\u3010%2$s\u3011-\u53d6\u6570\u65b9\u6848\u3010%3$s\u3011\u7684\u8c03\u6574\u671f\u65b9\u6848\u4e3a: %4$s", taskDefine.getTitle(), formScheme.getTitle(), fetchSchemeVO.getName(), adjustPeriodString));
            }
        }
        catch (Exception e) {
            log.error("BDE\u53d6\u6570\u8bbe\u7f6e-\u8bbe\u7f6e\u8c03\u6574\u671f\u8bb0\u5f55\u65e5\u5fd7\u5f02\u5e38", e);
        }
    }
}

