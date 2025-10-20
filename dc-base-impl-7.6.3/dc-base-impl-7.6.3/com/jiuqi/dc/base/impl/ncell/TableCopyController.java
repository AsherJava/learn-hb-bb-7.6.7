/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.base.impl.ncell;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.impl.ncell.service.TableCopyService;
import com.jiuqi.dc.base.impl.ncell.vo.PasteParamVO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableCopyController {
    static final String GC_API_BASE_PATH = "/api/datacenter/v1/tablePaste/";
    @Autowired
    private TableCopyService tableCopyService;

    @PostMapping(value={"/api/datacenter/v1/tablePaste/transform"})
    public BusinessResponseEntity<List<Map<String, Object>>> transformData(@RequestBody PasteParamVO pasteDatas) {
        List<Map<String, Object>> maps = this.tableCopyService.transformData(pasteDatas);
        return BusinessResponseEntity.ok(maps);
    }
}

