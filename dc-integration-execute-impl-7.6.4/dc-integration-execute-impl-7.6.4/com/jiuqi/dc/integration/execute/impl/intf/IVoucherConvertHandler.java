/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.VchrMasterDim
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 */
package com.jiuqi.dc.integration.execute.impl.intf;

import com.jiuqi.dc.base.common.intf.impl.VchrMasterDim;
import com.jiuqi.dc.integration.execute.impl.data.DataConvertDim;
import com.jiuqi.dc.integration.execute.impl.intf.IBizDataConvertHandler;
import com.jiuqi.dc.integration.execute.impl.mq.BizDataExecuteParam;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import java.util.List;
import java.util.Map;

public interface IVoucherConvertHandler
extends IBizDataConvertHandler {
    public List<VchrMasterDim> handleVchrChangeInfo(DataMappingDefineDTO var1, IBizDataConvertHandler var2, DataConvertDim var3, String var4);

    public List<DataConvertDim> getVchrConvertParam(BizDataExecuteParam var1, DataConvertDim var2);

    public Map<String, Integer> getAcctPeriodRefMap();
}

