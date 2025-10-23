/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityDefine
 */
package com.jiuqi.nr.summary.tree.preview;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.service.SummaryParamService;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import com.jiuqi.nr.summary.tree.entityRow.EntityRowDataProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PreviewUnitDataProvider
extends EntityRowDataProvider {
    @Autowired
    private SummaryParamService summaryParamService;

    @Override
    public List<IEntityRow> queryData(TreeQueryParam treeQueryParam) throws SummaryCommonException {
        String taskId = treeQueryParam.getCustomValue("taskId").toString();
        String targetDimension = treeQueryParam.getCustomValue("targetDimension").toString();
        String period = treeQueryParam.getPeriod();
        if (StringUtils.hasLength(period)) {
            TaskDefine taskDefine = this.summaryParamService.getTaskDefine(taskId);
            PeriodType periodType = taskDefine.getPeriodType();
            treeQueryParam.setPeriod(this.toNrPeriod(period, periodType));
        }
        if (StringUtils.hasLength(targetDimension)) {
            treeQueryParam.putCustomParam("entityId", targetDimension);
        } else {
            IEntityDefine dimension = this.summaryParamService.getDimension(taskId);
            treeQueryParam.putCustomParam("entityId", dimension.getId());
        }
        return super.queryData(treeQueryParam);
    }

    private String toNrPeriod(String biPeriod, PeriodType periodType) {
        return TimeDimUtils.getDataTimeByTimeDim((String)biPeriod, (String)String.valueOf((char)Character.toUpperCase(periodType.code())));
    }
}

