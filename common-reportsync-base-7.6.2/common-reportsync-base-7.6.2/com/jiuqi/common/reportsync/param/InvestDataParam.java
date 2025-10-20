/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.period.PeriodUtil
 */
package com.jiuqi.common.reportsync.param;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.period.PeriodUtil;
import java.util.List;
import java.util.stream.Collectors;

public class InvestDataParam {
    private List<String> unitCodes;
    private List<GcOrgCacheVO> unitVos;
    private String periodStr;
    private Integer periodOffset;

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public Integer year() {
        if (StringUtils.isEmpty((String)this.periodStr)) {
            return null;
        }
        return PeriodUtil.getPeriodWrapper((String)this.periodStr).getYear();
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public Integer getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(Integer periodOffset) {
        this.periodOffset = periodOffset;
    }

    public List<GcOrgCacheVO> getUnitVos() {
        return this.unitVos;
    }

    public void setUnitVos(List<GcOrgCacheVO> unitVos) {
        this.unitVos = unitVos;
    }

    public void convert2EO() {
        if (!CollectionUtils.isEmpty(this.unitVos)) {
            this.setUnitCodes(this.unitVos.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
        }
    }
}

