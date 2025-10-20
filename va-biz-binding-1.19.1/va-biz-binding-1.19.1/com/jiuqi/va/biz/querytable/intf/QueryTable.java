/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.querytable.intf;

import com.jiuqi.va.biz.querytable.intf.QueryTableParamItem;
import com.jiuqi.va.biz.querytable.vo.QueryTableColumnVO;
import com.jiuqi.va.biz.querytable.vo.QueryTableDataDTO;
import java.util.List;
import java.util.Map;

public interface QueryTable {
    public String getName();

    public String getTitle();

    public List<QueryTableColumnVO> getColumns();

    public List<QueryTableParamItem> getQueryParams();

    public List<QueryTableDataDTO> getQueryTableDataDTO(Map<String, Object> var1);
}

