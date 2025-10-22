/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.client.service.querycheck.extend;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;
import java.util.Map;
import nr.single.client.service.querycheck.bean.QueryModalCheckInfo;
import nr.single.client.service.querycheck.bean.QueryModalCheckParam;
import nr.single.map.data.exception.SingleDataException;

public interface ISingleQueryCheckReadService {
    public List<QueryModalCheckInfo> getQueryCheckResult(String var1, Map<String, DimensionValue> var2, QueryModalCheckParam var3) throws SingleDataException;
}

