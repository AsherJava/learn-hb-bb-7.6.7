/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.querytable.service;

import com.jiuqi.va.biz.querytable.vo.QueryItemVO;
import com.jiuqi.va.biz.querytable.vo.QueryTableColumnVO;
import com.jiuqi.va.biz.querytable.vo.QueryTableDataDTO;
import java.util.List;
import java.util.Map;

public interface QueryTableService {
    public List<QueryItemVO> getAllQueryItems();

    public List<QueryTableDataDTO> getQueryTableDataByName(String var1, Map<String, Object> var2);

    public List<QueryTableColumnVO> getQueryTableColumns(String var1);
}

