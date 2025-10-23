/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.nr.singlequeryimport.service;

import com.jiuqi.nr.singlequeryimport.bean.BatchQueryConfigInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryConfigInfo;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface SingleQueryService {
    public void export(@RequestBody QueryConfigInfo var1, HttpServletResponse var2);

    public void batchExport(BatchQueryConfigInfo var1, HttpServletResponse var2);

    public void templateExport(List<String> var1, HttpServletResponse var2) throws Exception;
}

