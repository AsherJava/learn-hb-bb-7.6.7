/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.common.DimensionType
 *  com.jiuqi.nr.definition.facade.analysis.DimensionAttribute
 *  com.jiuqi.nr.definition.facade.analysis.DimensionConfig
 *  com.jiuqi.nr.definition.facade.analysis.DimensionInfo
 *  com.jiuqi.nr.definition.internal.impl.anslysis.DimensionConfigImpl
 *  com.jiuqi.nr.definition.internal.impl.anslysis.DimensionInfoImpl
 */
package nr.single.para.parain.internal.util;

import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.DimensionType;
import com.jiuqi.nr.definition.facade.analysis.DimensionAttribute;
import com.jiuqi.nr.definition.facade.analysis.DimensionConfig;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionConfigImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionInfoImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnalImportUtils {
    public static DimensionConfig getDesignDefaultDimConfig(Map<String, TableDefine> tableMap, PeriodType periodType, String fromPeriod, String toPeriod) {
        DimensionConfigImpl dimConfig = new DimensionConfigImpl();
        List<DimensionInfo> dims = AnalImportUtils.getDesignDimensionObjList(tableMap, periodType, fromPeriod, toPeriod);
        dimConfig.setDestDims(dims);
        dimConfig.setSrcDims(dims);
        return dimConfig;
    }

    public static List<DimensionInfo> getDesignDimensionObjList(Map<String, TableDefine> tableMap, PeriodType periodType, String fromPeriod, String toPeriod) {
        ArrayList<DimensionInfo> dims = new ArrayList<DimensionInfo>();
        DimensionInfoImpl periodDim = new DimensionInfoImpl();
        for (Map.Entry<String, TableDefine> entry : tableMap.entrySet()) {
            DimensionInfoImpl dim;
            DimensionAttribute config;
            String viewKey = entry.getKey();
            TableDefine tableDefine = entry.getValue();
            if (TableKind.TABLE_KIND_ENTITY_PERIOD.getValue() == tableDefine.getKind().getValue()) {
                periodDim.setKey(tableDefine.getKey());
                periodDim.setCode(tableDefine.getCode());
                periodDim.setName(tableDefine.getCode());
                periodDim.setTitle(tableDefine.getTitle());
                periodDim.setType(DimensionType.ENTITY_PERIOD);
                periodDim.setViewKey(viewKey);
                config = periodDim.getConfig();
                if (periodType.type() == PeriodType.DEFAULT.type()) {
                    config.setPeriodType(PeriodType.YEAR.type());
                } else {
                    config.setPeriodType(periodType.type());
                }
                config.setPeriodRange(fromPeriod + "-" + toPeriod);
                continue;
            }
            if (TableKind.TABLE_KIND_ENTITY_VERSION.getValue() == tableDefine.getKind().getValue()) {
                dim = new DimensionInfoImpl();
                dim.setKey(tableDefine.getKey());
                dim.setCode(tableDefine.getCode());
                dim.setName(tableDefine.getCode());
                dim.setTitle(tableDefine.getTitle());
                dim.setType(DimensionType.ENTITY_VERSION);
                dim.setViewKey(viewKey);
                config = periodDim.getConfig();
                config.setLinkEntityKey(dim.getKey());
                dims.add((DimensionInfo)dim);
                continue;
            }
            dim = new DimensionInfoImpl();
            dim.setKey(tableDefine.getKey());
            dim.setCode(tableDefine.getCode());
            dim.setName(tableDefine.getCode());
            dim.setTitle(tableDefine.getTitle());
            dim.setType(DimensionType.ENTITY);
            dim.setViewKey(viewKey);
            config = periodDim.getConfig();
            config.setLinkEntityKey(dim.getKey());
            dims.add((DimensionInfo)dim);
        }
        dims.add((DimensionInfo)periodDim);
        return dims;
    }
}

