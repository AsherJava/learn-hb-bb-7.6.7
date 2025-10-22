/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.core.ISchemeNode
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 */
package nr.single.para.upload.param;

import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.core.ISchemeNode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class SchemeNodeFilterByPeriod
implements NodeFilter {
    private final String jioPeriodType;
    private IDesignDataSchemeService designDataSchemeService;
    private static final String COMMONPERIOD = "B";

    public SchemeNodeFilterByPeriod(IDesignDataSchemeService designService, String filterPeriodType) {
        if (designService == null) {
            throw new NullPointerException("designService is null");
        }
        if (filterPeriodType == null) {
            throw new NullPointerException("filterPeriodType is null");
        }
        this.jioPeriodType = filterPeriodType;
        this.designDataSchemeService = designService;
    }

    public boolean test(ISchemeNode iSchemeNode) {
        if (this.jioPeriodType.equals(COMMONPERIOD)) {
            return true;
        }
        if (iSchemeNode.getType() == NodeType.SCHEME.getValue()) {
            String s;
            List dimension = this.designDataSchemeService.getDataSchemeDimension(iSchemeNode.getKey(), DimensionType.PERIOD);
            return !CollectionUtils.isEmpty(dimension) && (s = Character.toString((char)((DesignDataDimension)dimension.get(0)).getPeriodType().code())).equals(this.jioPeriodType);
        }
        return iSchemeNode.getType() == NodeType.SCHEME_GROUP.getValue();
    }

    public String getJioPeriodType() {
        return this.jioPeriodType;
    }
}

