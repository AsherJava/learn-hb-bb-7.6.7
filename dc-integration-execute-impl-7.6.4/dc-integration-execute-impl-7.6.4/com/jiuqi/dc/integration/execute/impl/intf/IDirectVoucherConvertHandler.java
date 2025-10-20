/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.utils.Pair
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider
 */
package com.jiuqi.dc.integration.execute.impl.intf;

import com.jiuqi.dc.base.common.utils.Pair;
import com.jiuqi.dc.integration.execute.impl.data.DataConvertDim;
import com.jiuqi.dc.integration.execute.impl.data.QuerySimpleDirectResult;
import com.jiuqi.dc.integration.execute.impl.intf.IVoucherConvertHandler;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider;
import java.util.Date;

public interface IDirectVoucherConvertHandler
extends IVoucherConvertHandler {
    public Pair<Boolean, String> handleProcessUnitRange(String var1, Date var2, String var3);

    public Pair<Boolean, String> handleProcessByOdsUnitId(String var1, DataConvertDim var2);

    public void handleBeforeVchrChangeByOdsUnitId(String var1, String var2, String var3, Date var4);

    public QuerySimpleDirectResult queryDirectResultByDim(DataConvertDim var1, DataMappingDefineDTO var2, IFieldMappingProvider var3, DataSchemeDTO var4);
}

