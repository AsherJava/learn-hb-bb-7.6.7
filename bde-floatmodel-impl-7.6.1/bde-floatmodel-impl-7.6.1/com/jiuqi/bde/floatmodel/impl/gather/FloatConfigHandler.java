/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 */
package com.jiuqi.bde.floatmodel.impl.gather;

import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import java.util.List;

public interface FloatConfigHandler {
    public String getCode();

    public String getTitle();

    public String getProdLine();

    public String getAppName();

    public Integer getOrder();

    public FetchFloatRowResult queryFloatRowDatas(FetchTaskContext var1, QueryConfigInfo var2);

    public List<FetchQueryFiledVO> parseFloatRowFields(QueryConfigInfo var1);

    default public boolean enable() {
        return true;
    }
}

