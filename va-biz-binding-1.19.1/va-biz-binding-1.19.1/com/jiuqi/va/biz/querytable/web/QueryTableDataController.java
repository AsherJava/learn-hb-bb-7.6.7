/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.biz.querytable.web;

import com.jiuqi.va.biz.querytable.service.QueryTableService;
import com.jiuqi.va.biz.querytable.vo.QueryItemVO;
import com.jiuqi.va.biz.querytable.vo.QueryTableColumnVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz/querytable"})
public class QueryTableDataController {
    @Autowired
    private QueryTableService queryTableService;

    @GetMapping(value={"/items"})
    public List<QueryItemVO> getQueryItems() {
        return this.queryTableService.getAllQueryItems();
    }

    @GetMapping(value={"/columns/{queryTableName}"})
    public List<QueryTableColumnVO> getQueryItems(@PathVariable(value="queryTableName") String queryTableName) {
        return this.queryTableService.getQueryTableColumns(queryTableName);
    }
}

