/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.dataservice.core.datastatus.IDataStatusPreInitService
 *  com.jiuqi.nr.dataservice.core.datastatus.monitor.IDataStatusMonitor
 *  com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.logic.monitor;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.dataservice.core.datastatus.IDataStatusPreInitService;
import com.jiuqi.nr.dataservice.core.datastatus.monitor.IDataStatusMonitor;
import com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalculateMonitor
extends FmlEngineBaseMonitor
implements IDataStatusMonitor {
    private static final Logger logger = LoggerFactory.getLogger(CalculateMonitor.class);
    private DataStatusPresetInfo dataStatusPresetInfo;

    public CalculateMonitor(String formSchemeKey, String formulaSchemeKey, DimensionValueSet dimensionValueSet, Collection<IParsedExpression> expressions) {
        if (DimensionUtil.dimSingle(dimensionValueSet)) {
            try {
                IDataStatusPreInitService dataStatusPreInitService = (IDataStatusPreInitService)BeanUtil.getBean(IDataStatusPreInitService.class);
                DimensionCombination combination = new DimensionCombinationBuilder(dimensionValueSet).getCombination();
                Set formulaKeys = expressions.stream().map(o -> o.getSource().getId()).collect(Collectors.toSet());
                this.dataStatusPresetInfo = dataStatusPreInitService.initInfo(combination, formSchemeKey, formulaSchemeKey, formulaKeys);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public DataStatusPresetInfo getPresetInfo() {
        return this.dataStatusPresetInfo;
    }
}

