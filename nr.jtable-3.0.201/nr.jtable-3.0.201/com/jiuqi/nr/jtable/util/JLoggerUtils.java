/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.output.EntityByKeysReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JLoggerUtils {
    private static final Logger logger = LoggerFactory.getLogger(JLoggerUtils.class);
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private PeriodEngineService periodEngineService;

    public void log(String modelTitle, String logTitle, JtableContext context, String extendInfo) {
        StringBuilder logInfo = this.buildBaseLogInfo(context);
        if (StringUtils.isNotEmpty((String)extendInfo)) {
            logInfo.append(extendInfo);
        }
        LogHelper.info((String)modelTitle, (String)logTitle, (String)logInfo.toString());
    }

    /*
     * WARNING - void declaration
     */
    public StringBuilder buildBaseLogInfo(JtableContext context) {
        StringBuilder valueBuilder = new StringBuilder();
        if (null != context) {
            String taskKey = context.getTaskKey();
            if (!StringUtils.isEmpty((String)taskKey)) {
                String[] splits = taskKey.split(";");
                String taskTitle = "";
                for (String oneTaskKey : splits) {
                    TaskDefine queryTaskDefine = null;
                    try {
                        queryTaskDefine = this.runtimeViewController.queryTaskDefine(oneTaskKey);
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    if (null == queryTaskDefine) continue;
                    taskTitle = "".equals(taskTitle) ? queryTaskDefine.getTitle() : taskTitle + ";" + queryTaskDefine.getTitle();
                }
                taskKey = taskTitle;
            } else {
                taskKey = "\u6240\u6709\u4efb\u52a1";
            }
            valueBuilder.append("\u4efb\u52a1\u540d\u79f0:").append(taskKey).append(", ");
            String formSchemeKey = context.getFormSchemeKey();
            String period = "";
            if (!StringUtils.isEmpty((String)formSchemeKey)) {
                String[] splits = formSchemeKey.split(";");
                String formSchemeKeyTitle = "";
                for (String oneFormSchemeKey : splits) {
                    void var12_24;
                    Object var12_25 = null;
                    try {
                        FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(oneFormSchemeKey);
                        period = formSchemeDefine.getDateTime();
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    if (null == var12_24) continue;
                    formSchemeKeyTitle = "".equals(formSchemeKeyTitle) ? var12_24.getTitle() : formSchemeKeyTitle + ";" + var12_24.getTitle();
                }
                formSchemeKey = formSchemeKeyTitle;
            } else {
                formSchemeKey = "\u6240\u6709\u62a5\u8868\u65b9\u6848";
            }
            valueBuilder.append("\u62a5\u8868\u65b9\u6848\u540d\u79f0:").append(formSchemeKey).append(", ");
            String unilType = "";
            String dateType = "";
            HashMap<String, String> entityViewsMap = new HashMap<String, String>();
            if (null != context.getFormSchemeKey() && !"".equals(context.getFormSchemeKey())) {
                EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
                unilType = dwEntity.getDimensionName();
                EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(context.getFormSchemeKey());
                dateType = dataTimeEntity.getDimensionName();
                for (EntityViewData entityViewData : this.jtableParamService.getEntityList(context.getFormSchemeKey())) {
                    entityViewsMap.put(entityViewData.getDimensionName(), entityViewData.getKey());
                }
            }
            FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(context.getFormSchemeKey());
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
            Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
            for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
                String value;
                if (entry.getKey().equals(dateType)) {
                    String periodTitle = periodProvider.getPeriodTitle(entry.getValue().getValue());
                    valueBuilder.append("\u65f6\u671f:").append(entry.getKey()).append("|").append(periodTitle).append(", ");
                    continue;
                }
                if (entry.getKey().equals(unilType)) {
                    value = entry.getValue().getValue();
                    String unitTitle = "\u5355\u4f4d:";
                    if (StringUtils.isEmpty((String)value)) {
                        valueBuilder.append(unitTitle).append(entry.getKey()).append("|").append("\u6240\u6709\u5355\u4f4d").append(", ");
                        continue;
                    }
                    this.appendEntity(context, valueBuilder, entityViewsMap, entry, unitTitle);
                    continue;
                }
                value = entry.getValue().getValue();
                if (StringUtils.isEmpty((String)value)) {
                    valueBuilder.append("\u7ef4\u5ea6\u540d:").append(entry.getKey()).append("|").append("\u6240\u6709\u503c").append(",");
                    continue;
                }
                this.appendEntity(context, valueBuilder, entityViewsMap, entry, "\u7ef4\u5ea6");
            }
            StringBuilder stringBuilder = new StringBuilder("\u62a5\u8868:");
            valueBuilder.append((CharSequence)stringBuilder);
            String formKey = context.getFormKey();
            if (!StringUtils.isEmpty((String)formKey) && !formKey.equals("all")) {
                String[] splits = formKey.split(";");
                boolean addFlag = false;
                for (String oneFormKey : splits) {
                    FormDefine formDefine = null;
                    try {
                        formDefine = this.runtimeViewController.queryFormById(oneFormKey);
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    if (null == formDefine) continue;
                    if (addFlag) {
                        valueBuilder.append(",");
                    }
                    valueBuilder.append(formDefine.getFormCode()).append("|").append(formDefine.getTitle());
                    addFlag = true;
                }
            } else {
                formKey = "\u6240\u6709\u62a5\u8868";
                valueBuilder.append(formKey);
            }
        } else {
            valueBuilder.append("\u672a\u8bb0\u5f55\u73af\u5883\u4fe1\u606f");
        }
        return valueBuilder;
    }

    private void appendEntity(JtableContext jtableContext, StringBuilder valueBuilder, Map<String, String> entityViewsMap, Map.Entry<String, DimensionValue> entry, String title) {
        EntityQueryByKeysInfo entityQueryByKeysInfo = new EntityQueryByKeysInfo();
        String entityViewKey = entityViewsMap.get(entry.getKey());
        Map<String, EntityData> entitys = null;
        if (null != entityViewKey && !"".equals(entityViewKey)) {
            entityQueryByKeysInfo.setEntityViewKey(entityViewKey);
            String[] split = entry.getValue().getValue().split(";");
            entityQueryByKeysInfo.setEntityKeys(Arrays.asList(split));
            entityQueryByKeysInfo.setContext(jtableContext);
            EntityByKeysReturnInfo entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKeys(entityQueryByKeysInfo);
            entitys = entityByKeyReturnInfo.getEntitys();
        }
        valueBuilder.append(title);
        if (null != entitys && entitys.size() > 0) {
            boolean addFlag = true;
            for (Map.Entry retrunEnity : entitys.entrySet()) {
                if (addFlag) {
                    valueBuilder.append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                } else {
                    valueBuilder.append(",").append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                }
                addFlag = false;
            }
        } else {
            valueBuilder.append(title).append("\u503c:").append(entry.getValue().getValue());
        }
        valueBuilder.append(", ");
    }

    public void logDeleteFloatRowInfo(JtableContext jtableContext, Map<String, RegionDataCommitSet> commitData) {
        int num = 0;
        Collection<RegionDataCommitSet> values = commitData.values();
        for (RegionDataCommitSet regionDataCommitSet : values) {
            List<String> deletedata = regionDataCommitSet.getDeletedata();
            num += deletedata.size();
        }
        if (num > 0) {
            StringBuilder buildMessing = new StringBuilder();
            buildMessing.append(" \u5220\u9664\u884c\u6570:").append(num);
            this.log("\u6570\u636e\u5f55\u5165", "\u6d6e\u52a8\u884c\u5220\u9664", jtableContext, buildMessing.toString());
        }
    }

    public void logSensitiveCellLog(JtableContext jtableContext, DataRegionDefine regionDefine, DataField dataField) {
        StringBuilder extendInfo = new StringBuilder();
        extendInfo.append(" \u533a\u57df:").append(regionDefine.getCode()).append("|").append(regionDefine.getTitle()).append(" ");
        extendInfo.append("\u6307\u6807\uff1a").append(dataField.getCode()).append("|").append(dataField.getTitle());
        this.log("\u6570\u636e\u5f55\u5165", "\u8131\u654f\u6307\u6807\u6570\u636e\u67e5\u8be2", jtableContext, extendInfo.toString());
    }
}

