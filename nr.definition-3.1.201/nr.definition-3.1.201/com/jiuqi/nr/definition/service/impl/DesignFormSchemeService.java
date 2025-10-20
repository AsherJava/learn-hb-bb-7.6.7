/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.service.IEntityDefineAssist
 */
package com.jiuqi.nr.definition.service.impl;

import com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.service.IDesignFormSchemeService;
import com.jiuqi.nr.entity.service.IEntityDefineAssist;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DesignFormSchemeService
implements IDesignFormSchemeService {
    @Autowired
    private AdjustPeriodDesignService adjustPeriodDesignService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IEntityDefineAssist entityDefineAssist;

    private String getDataSchemeKey(String formSchemeKey) {
        DesignTaskDefine taskDefine;
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme != null && (taskDefine = this.designTimeViewController.getTask(formScheme.getTaskKey())) != null) {
            return taskDefine.getDataScheme();
        }
        return null;
    }

    @Override
    public boolean enableAdjustPeriod(String formSchemeKey) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (Objects.isNull(dataSchemeKey)) {
            return Boolean.FALSE;
        }
        return this.designDataSchemeService.enableAdjustPeriod(dataSchemeKey);
    }

    @Override
    public boolean isTaskEnableAdjustPeriod(String taskKey) {
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        if (Objects.isNull(task)) {
            return false;
        }
        return this.designDataSchemeService.enableAdjustPeriod(task.getDataScheme());
    }

    @Override
    @NonNull
    public <E extends AdjustPeriod> List<E> queryAdjustPeriods(String formSchemeKey) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (Objects.nonNull(dataSchemeKey)) {
            return this.castList(this.adjustPeriodDesignService.query(dataSchemeKey));
        }
        return Collections.emptyList();
    }

    @Override
    @NonNull
    public <E extends AdjustPeriod> List<E> queryAdjustPeriods(String formSchemeKey, String period) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (Objects.nonNull(dataSchemeKey)) {
            return this.castList(this.adjustPeriodDesignService.query(dataSchemeKey, period));
        }
        return Collections.emptyList();
    }

    @Override
    @Nullable
    public <E extends AdjustPeriod> E queryAdjustPeriods(String formSchemeKey, String period, String code) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (Objects.nonNull(dataSchemeKey)) {
            List dtos = this.adjustPeriodDesignService.query(dataSchemeKey, period);
            return (E)((AdjustPeriod)dtos.stream().filter(x -> code.equals(x.getCode())).findFirst().orElse(null));
        }
        return null;
    }

    @Override
    @NonNull
    public List<String> getReportDimensionKey(String formSchemeKey) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (dataSchemeKey == null) {
            return Collections.emptyList();
        }
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        return dimensions.stream().filter(this::isReportDimension).map(DataDimension::getDimKey).collect(Collectors.toList());
    }

    @Override
    @NonNull
    public List<String> getReportEntityKeys(String formSchemeKey) {
        List<String> list = this.getReportDimensionKey(formSchemeKey);
        DesignFormSchemeDefine schemeDefine = this.designTimeViewController.getFormScheme(formSchemeKey);
        if (schemeDefine == null) {
            return list;
        }
        DesignTaskDefine taskDefine = this.designTimeViewController.getTask(schemeDefine.getTaskKey());
        if (taskDefine == null) {
            return list;
        }
        list.add(taskDefine.getDw());
        list.add(taskDefine.getDateTime());
        return list;
    }

    @Override
    @NonNull
    public Boolean isReportDimension(String formSchemeKey, String dimKey) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (dataSchemeKey == null) {
            return Boolean.FALSE;
        }
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        return dimensions.stream().anyMatch(x -> this.isReportDimension((DesignDataDimension)x) && dimKey.equals(x.getDimKey()));
    }

    @Override
    @Nullable
    public String getDimAttributeByReportDim(String formSchemeKey, String dimKey) {
        String dataSchemeKey = this.getDataSchemeKey(formSchemeKey);
        if (dataSchemeKey == null) {
            return null;
        }
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        DataDimension report = dimensions.stream().filter(x -> this.isReportDimension((DesignDataDimension)x) && dimKey.equals(x.getDimKey())).findFirst().orElse(null);
        return report == null ? null : report.getDimAttribute();
    }

    @Override
    public Boolean existCurrencyAttributes(String formSchemeKey) {
        String dw;
        DesignTaskDefine taskDefine;
        DesignFormSchemeDefine schemeDefine = this.designTimeViewController.getFormScheme(formSchemeKey);
        if (Objects.nonNull(schemeDefine) && Objects.nonNull(taskDefine = this.designTimeViewController.getTask(schemeDefine.getTaskKey())) && StringUtils.hasLength(dw = taskDefine.getDw())) {
            return this.entityDefineAssist.existCurrencyAttributes(dw);
        }
        return Boolean.FALSE;
    }

    private boolean isReportDimension(DesignDataDimension x) {
        return DimensionType.DIMENSION.equals((Object)x.getDimensionType()) && x.getReportDim() != false;
    }

    @Override
    @NonNull
    public Boolean existCurrencyDim(String formSchemeKey) {
        String dims;
        DesignTaskDefine taskDefine;
        DesignFormSchemeDefine schemeDefine = this.designTimeViewController.getFormScheme(formSchemeKey);
        if (Objects.nonNull(schemeDefine) && Objects.nonNull(taskDefine = this.designTimeViewController.getTask(schemeDefine.getTaskKey())) && StringUtils.hasLength(dims = taskDefine.getDims())) {
            return dims.contains("MD_CURRENCY@BASE");
        }
        return Boolean.FALSE;
    }
}

