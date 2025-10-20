/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gc.financial.status.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gc.financial.status.dto.FinancialGroupStatusDTO;
import com.jiuqi.gc.financial.status.dto.FinancialUnitStatusDTO;
import com.jiuqi.gc.financial.status.intf.IFinancialStatusModulePlugin;
import com.jiuqi.gc.financial.status.intf.IFinancialStatusModuleQueryExecute;
import com.jiuqi.gc.financial.status.service.FinancialStatusQueryService;
import com.jiuqi.gc.financial.status.service.impl.IFinancialStatusModuleGather;
import com.jiuqi.gc.financial.status.service.impl.IFinancialStatusModuleQueryExecuteGather;
import com.jiuqi.gc.financial.status.vo.FinancialStatusQueryParam;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialStatusQueryServiceImpl
implements FinancialStatusQueryService {
    @Autowired
    private IFinancialStatusModuleQueryExecuteGather executeGather;
    @Autowired
    private IFinancialStatusModuleGather moduleGather;

    @Override
    public List<FinancialGroupStatusDTO> listFinancialGroupStatusData(FinancialStatusQueryParam param) {
        this.paramCheck(param);
        return this.getModuleQueryExecute(param).listFinancialGroupStatusData(param);
    }

    @Override
    public List<FinancialUnitStatusDTO> listFinancialUnitStatusData(FinancialStatusQueryParam param) {
        this.paramCheck(param);
        return this.getModuleQueryExecute(param).listFinancialUnitStatusData(param);
    }

    private IFinancialStatusModuleQueryExecute getModuleQueryExecute(FinancialStatusQueryParam param) {
        IFinancialStatusModulePlugin pluginByModuleCode = this.moduleGather.getPluginByModuleCode(param.getModuleCode());
        if (pluginByModuleCode == null) {
            throw new RuntimeException("\u672a\u627e\u5230\u5bf9\u5e94\u7684\u5f00\u5173\u8d26\u529f\u80fd\u6a21\u5757");
        }
        IFinancialStatusModuleQueryExecute pluginByExecuteName = this.executeGather.getPluginByExecuteName(pluginByModuleCode.getFinancialStatusModuleQueryExecute());
        if (pluginByExecuteName == null) {
            throw new RuntimeException("\u672a\u627e\u5230\u5bf9\u5e94\u7684\u6a21\u5757\u67e5\u8be2\u6267\u884c\u7c7b");
        }
        return pluginByExecuteName;
    }

    private void paramCheck(FinancialStatusQueryParam param) {
        Assert.isNotEmpty((String)param.getModuleCode(), (String)"\u6a21\u5757\u7f16\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isFalse((StringUtils.isEmpty((String)param.getPeriodType()) && StringUtils.isEmpty((String)param.getDataTime()) ? 1 : 0) != 0, (String)"\u65f6\u671f\u7c7b\u578b\u548c\u65f6\u671f\u4e0d\u80fd\u540c\u65f6\u4e3a\u7a7a", (Object[])new Object[0]);
        if (StringUtils.isEmpty((String)param.getPeriodType()) && !StringUtils.isEmpty((String)param.getDataTime())) {
            PeriodWrapper periodWrapper = new PeriodWrapper(param.getDataTime());
            param.setPeriodType(PeriodUtil.convertType2Str((int)periodWrapper.getType()));
        }
    }
}

