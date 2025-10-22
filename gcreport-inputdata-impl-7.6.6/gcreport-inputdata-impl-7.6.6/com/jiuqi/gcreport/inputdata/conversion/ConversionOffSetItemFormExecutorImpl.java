/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.conversion.common.GcConversionResult
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO
 *  com.jiuqi.gcreport.conversion.executor.AbstractConversionFormExecutor
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package com.jiuqi.gcreport.inputdata.conversion;

import com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.common.GcConversionResult;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.conversion.executor.AbstractConversionFormExecutor;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.nr.definition.common.DataRegionKind;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ConversionOffSetItemFormExecutorImpl
extends AbstractConversionFormExecutor {
    public GcConversionResult conversion(GcConversionOrgAndFormContextEnv env, ConversionSystemTaskEO taskSchemeEO, List<ConversionSystemItemEO> systemItems, List<GcConversionIndexRateInfo> indexRateInfos, TableModelRunInfo tableInfo, Set<String> converisonFieldNames) {
        return new GcConversionResult(0, 0, 0);
    }

    protected boolean isMatch(GcConversionOrgAndFormContextEnv formContextEnv) {
        String tableName = formContextEnv.getTableDefine().getCode();
        boolean isFloatRegion = !DataRegionKind.DATA_REGION_SIMPLE.equals((Object)formContextEnv.getDataRegionDefine().getRegionKind());
        return isFloatRegion && "GC_OFFSETVCHRITEM".equals(tableName);
    }

    protected int matchOrder() {
        return Integer.MIN_VALUE;
    }
}

