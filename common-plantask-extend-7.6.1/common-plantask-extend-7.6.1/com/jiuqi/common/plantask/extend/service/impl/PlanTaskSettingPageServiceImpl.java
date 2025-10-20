/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.extension.ILogGenerator$LogItem
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.jobmanager.api.vo.JobLogDetailVO
 */
package com.jiuqi.common.plantask.extend.service.impl;

import com.jiuqi.bi.core.jobs.extension.ILogGenerator;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.plantask.extend.dao.PlanTaskLogDetailTempDao;
import com.jiuqi.common.plantask.extend.service.PlanTaskSettingPageHandler;
import com.jiuqi.common.plantask.extend.service.PlanTaskSettingPageService;
import com.jiuqi.common.plantask.extend.service.SettingPageTemplateInterceptor;
import com.jiuqi.common.plantask.extend.vo.SettingPageTemplateVO;
import com.jiuqi.nvwa.jobmanager.api.vo.JobLogDetailVO;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanTaskSettingPageServiceImpl
implements PlanTaskSettingPageService,
InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(PlanTaskSettingPageServiceImpl.class);
    @Autowired
    private PlanTaskLogDetailTempDao planTaskLogDetailTempDao;
    @Autowired
    private List<PlanTaskSettingPageHandler> settingPageHandlerList;
    @Autowired(required=false)
    private List<SettingPageTemplateInterceptor> settingPageTemplateInterceptorList;
    private final Map<String, SettingPageTemplateVO> allSettingPageTemplateMap = new ConcurrentHashMap<String, SettingPageTemplateVO>();

    @Override
    public SettingPageTemplateVO getTemplateByCode(String code) {
        if (!CollectionUtils.isEmpty(this.settingPageTemplateInterceptorList)) {
            for (SettingPageTemplateInterceptor settingPageTemplateInterceptor : this.settingPageTemplateInterceptorList) {
                if (!Objects.equals(settingPageTemplateInterceptor.getTemplateCode(), code)) continue;
                SettingPageTemplateVO settingPageTemplateVO = this.allSettingPageTemplateMap.get(code);
                return settingPageTemplateInterceptor.intercept(settingPageTemplateVO);
            }
        }
        return this.allSettingPageTemplateMap.get(code);
    }

    @Override
    public JobLogDetailVO queryPlanTaskLogDetailById(String id) {
        List<ILogGenerator.LogItem> logItemList = this.planTaskLogDetailTempDao.getLogItemByInstanceId(id);
        if (CollectionUtils.isEmpty(logItemList)) {
            return new JobLogDetailVO("", true);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        StringBuffer resultLog = new StringBuffer();
        for (ILogGenerator.LogItem logItem : logItemList) {
            resultLog.append("\u3010").append(logItem.getLevel()).append("\u3011 ");
            String format = logItem.getTimestamp() == 0L ? "" : df.format(logItem.getTimestamp());
            resultLog.append(format).append("\uff1a");
            if (!logItem.hasDetail()) {
                resultLog.append(logItem.getMessage());
                continue;
            }
            String detail = this.planTaskLogDetailTempDao.getDetailById(logItem.getId());
            if (StringUtils.isEmpty((String)logItem.getMessage()) || logItem.getMessage().endsWith("...")) {
                resultLog.append(detail);
                continue;
            }
            resultLog.append(logItem.getMessage() + "\n" + detail);
        }
        return new JobLogDetailVO(resultLog.toString(), true);
    }

    @Override
    public void afterPropertiesSet() {
        this.initSettingPageTemplateMap();
    }

    private void initSettingPageTemplateMap() {
        for (PlanTaskSettingPageHandler handler : this.settingPageHandlerList) {
            Map<String, SettingPageTemplateVO> settingPageTemplateMap = handler.getSettingPageTemplateMap();
            if (settingPageTemplateMap == null || settingPageTemplateMap.size() == 0) continue;
            for (String code : settingPageTemplateMap.keySet()) {
                if (this.allSettingPageTemplateMap.containsKey(code)) {
                    this.logger.error("\u8ba1\u5212\u4efb\u52a1\u9ad8\u7ea7\u754c\u9762\u6a21\u7248\u4fe1\u606f\u521d\u59cb\u5316\u5f02\u5e38\uff0c\u53d1\u73b0\u91cd\u590d\u7684\u6a21\u677f\u4ee3\u7801\u3010{}\u3011", (Object)code);
                    continue;
                }
                this.allSettingPageTemplateMap.put(code, settingPageTemplateMap.get(code));
            }
        }
    }
}

