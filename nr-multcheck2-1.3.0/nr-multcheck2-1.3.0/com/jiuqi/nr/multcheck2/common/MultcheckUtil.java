/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.query.ResourceGroup
 *  com.jiuqi.nvwa.resourceview.utils.AppConditionUtil
 */
package com.jiuqi.nr.multcheck2.common;

import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.multcheck2.common.GlobalType;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.query.ResourceGroup;
import com.jiuqi.nvwa.resourceview.utils.AppConditionUtil;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class MultcheckUtil {
    protected static final Logger logger = LoggerFactory.getLogger(MultcheckUtil.class);

    public static ModalActionInteractSetting getModalActionInteractSetting() {
        ModalActionInteractSetting interactSetting = new ModalActionInteractSetting();
        interactSetting.setProdLine("@nr");
        interactSetting.setPluginName("nr-multcheck2-plugin");
        interactSetting.setPluginType("nr-multcheck2-plugin");
        return interactSetting;
    }

    public static ResourceGroup convertDesignTaskGroup(TaskGroupDefine group) {
        ResourceGroup rg = new ResourceGroup();
        rg.setId("G@" + group.getKey());
        rg.setName(group.getCode());
        rg.setTitle(group.getTitle());
        rg.setGroup(group.getParentKey());
        rg.setIcon("#icon-16_SHU_A_NR_fenzu");
        return rg;
    }

    public static ResourceGroup convertTaskDefine(TaskDefine task, String group) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ResourceGroup rg = new ResourceGroup();
        rg.setId("T@" + task.getKey());
        rg.setName(task.getTaskCode());
        rg.setTitle(task.getTitle());
        rg.setGroup(group);
        rg.setIcon("#icon-16_SHU_A_NR_shujufangan");
        rg.setModifyTime(dateFormat.format(task.getUpdateTime()));
        return rg;
    }

    public static ResourceGroup convertFormScheme(FormSchemeDefine formScheme, TaskDefine task, PeriodEngineService periodEngineService, IRunTimeViewController runTimeViewController) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ResourceGroup rg = new ResourceGroup();
        rg.setId("F@" + formScheme.getKey());
        rg.setName(formScheme.getFormSchemeCode());
        rg.setTitle(formScheme.getTitle());
        rg.setGroup(formScheme.getTaskKey());
        rg.setIcon("#icon-16_SHU_A_NR_fengmiandaimabiao");
        rg.setModifyTime(dateFormat.format(formScheme.getUpdateTime()));
        String period = MultcheckUtil.buildPeriod(formScheme, task, periodEngineService, runTimeViewController);
        rg.putCustomValue("period", period);
        return rg;
    }

    public static String buildPeriod(FormSchemeDefine formScheme, TaskDefine task, PeriodEngineService periodEngineService, IRunTimeViewController runTimeViewController) {
        String period = null;
        try {
            List periodLinkDefines = runTimeViewController.querySchemePeriodLinkBySchemeSort(formScheme.getKey());
            if (!CollectionUtils.isEmpty(periodLinkDefines)) {
                return ((SchemePeriodLinkDefine)periodLinkDefines.get(periodLinkDefines.size() - 1)).getPeriodKey();
            }
            period = formScheme.getToPeriod();
            if (!StringUtils.hasText(period)) {
                if (StringUtils.hasText(task.getToPeriod())) {
                    period = task.getToPeriod();
                } else {
                    IPeriodProvider periodProvider = periodEngineService.getPeriodAdapter().getPeriodProvider(StringUtils.hasText(formScheme.getDateTime()) ? formScheme.getDateTime() : task.getDateTime());
                    String[] region = periodProvider.getPeriodCodeRegion();
                    period = region[region.length - 1];
                }
            }
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u65f6\u671f\u5f02\u5e38\uff1a\uff1a" + e.getMessage(), e);
        }
        return period;
    }

    public static String getType() {
        String type = AppConditionUtil.getConitionValue();
        if (!StringUtils.hasText(type)) {
            type = GlobalType.EXIST.value();
        }
        return type;
    }

    @Deprecated
    public static UUID generateUUID(String ... strings) {
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s);
        }
        String combined = sb.toString();
        try {
            int i;
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(combined.getBytes(StandardCharsets.UTF_8));
            long mostSigBits = 0L;
            long leastSigBits = 0L;
            for (i = 0; i < 8; ++i) {
                mostSigBits = mostSigBits << 8 | (long)(hash[i] & 0xFF);
            }
            for (i = 8; i < 16; ++i) {
                leastSigBits = leastSigBits << 8 | (long)(hash[i] & 0xFF);
            }
            return new UUID(mostSigBits, leastSigBits);
        }
        catch (NoSuchAlgorithmException e) {
            try {
                int i;
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                byte[] hash = md5.digest(combined.getBytes(StandardCharsets.UTF_8));
                long mostSigBits = 0L;
                long leastSigBits = 0L;
                for (i = 0; i < 8; ++i) {
                    mostSigBits = mostSigBits << 8 | (long)(hash[i] & 0xFF);
                }
                for (i = 8; i < 16; ++i) {
                    leastSigBits = leastSigBits << 8 | (long)(hash[i] & 0xFF);
                }
                return new UUID(mostSigBits, leastSigBits);
            }
            catch (NoSuchAlgorithmException ex) {
                return UUID.randomUUID();
            }
        }
    }
}

