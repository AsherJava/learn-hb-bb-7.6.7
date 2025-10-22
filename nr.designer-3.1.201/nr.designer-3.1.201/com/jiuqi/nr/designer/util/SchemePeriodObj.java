/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.util.SpringUtil
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.nr.designer.util;

import com.jiuqi.np.definition.internal.util.SpringUtil;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.designer.util.PeriodRangeSortUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.xlib.utils.StringUtils;

public class SchemePeriodObj
implements PeriodRangeSortUtils.IPeriodRange {
    public static final String PERIOD_SPLIT = "-";
    private String scheme;
    private String start;
    private String end;
    private String entity;

    public SchemePeriodObj() {
    }

    public SchemePeriodObj(String scheme, String start, String end) {
        this.scheme = scheme;
        this.start = start;
        this.end = end;
        this.autoSetEntity();
    }

    private void autoSetEntity() {
        IDesignTimeViewController bean;
        DesignFormSchemeDefine formScheme;
        String dateTime;
        if (StringUtils.isNotEmpty((String)this.scheme) && StringUtils.isEmpty((String)(dateTime = (formScheme = (bean = (IDesignTimeViewController)SpringUtil.getBean(IDesignTimeViewController.class)).queryFormSchemeDefine(this.scheme)).getDateTime()))) {
            dateTime = bean.queryTaskDefine(formScheme.getTaskKey()).getDateTime();
            PeriodEngineService periodBean = (PeriodEngineService)SpringUtil.getBean(PeriodEngineService.class);
            this.entity = periodBean.getPeriodAdapter().getPeriodEntity(dateTime).getKey();
        }
    }

    public SchemePeriodObj(String scheme, String periodRange) {
        this.scheme = scheme;
        if (StringUtils.hasLength((String)periodRange)) {
            String[] split = periodRange.split(PERIOD_SPLIT);
            this.start = split[0];
            this.end = split[1];
        }
        this.autoSetEntity();
    }

    @Override
    public int compareTo(PeriodRangeSortUtils.IPeriodRange periodRange) {
        if (StringUtils.isNotEmpty((String)this.entity)) {
            PeriodEngineService bean = (PeriodEngineService)SpringUtil.getBean(PeriodEngineService.class);
            IPeriodProvider periodProvider = bean.getPeriodAdapter().getPeriodProvider(this.entity);
            return periodProvider.comparePeriod(this.getStart(), periodRange.getStart());
        }
        return PeriodRangeSortUtils.IPeriodRange.super.compareTo(periodRange);
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
        this.autoSetEntity();
    }

    @Override
    public String getStart() {
        return this.start;
    }

    @Override
    public void setStart(String start) {
        this.start = start;
    }

    @Override
    public String getEnd() {
        return this.end;
    }

    @Override
    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public SchemePeriodObj copy() {
        SchemePeriodObj obj = new SchemePeriodObj();
        obj.setScheme(this.scheme);
        obj.setStart(this.start);
        obj.setEnd(this.end);
        return obj;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.end == null ? 0 : this.end.hashCode());
        result = 31 * result + (this.scheme == null ? 0 : this.scheme.hashCode());
        result = 31 * result + (this.start == null ? 0 : this.start.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        SchemePeriodObj other = (SchemePeriodObj)obj;
        if (this.end == null ? other.end != null : !this.end.equals(other.end)) {
            return false;
        }
        if (this.scheme == null ? other.scheme != null : !this.scheme.equals(other.scheme)) {
            return false;
        }
        return !(this.start == null ? other.start != null : !this.start.equals(other.start));
    }

    public String toString() {
        return "SchemePeriodObj [scheme=" + this.scheme + ", start=" + this.start + ", end=" + this.end + "]";
    }
}

