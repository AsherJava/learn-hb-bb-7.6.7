/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.base.client.onlinePeriod;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodCondiVO;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface OnlinePeriodDefineClient {
    public static final String API_BASE_PATH = "/api/datacenter/v1/base/periodmanage";

    @PostMapping(value={"/api/datacenter/v1/base/periodmanage/list"})
    public BusinessResponseEntity<List<OnlinePeriodDefineVO>> getAllTableData(@RequestBody OnlinePeriodCondiVO var1);

    @PostMapping(value={"/api/datacenter/v1/base/periodmanage/insert"})
    public BusinessResponseEntity<Object> insertPeriod(@RequestBody OnlinePeriodDefineVO var1);

    @PostMapping(value={"/api/datacenter/v1/base/periodmanage/update"})
    public BusinessResponseEntity<Object> updatePeriod(@RequestBody OnlinePeriodDefineVO var1);

    @PostMapping(value={"/api/datacenter/v1/base/periodmanage/delete/{id}"})
    public BusinessResponseEntity<Object> deletePeriod(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/datacenter/v1/base/periodmanage/get/{id}"})
    public BusinessResponseEntity<OnlinePeriodDefineVO> getPeriodDataById(@PathVariable(value="id") String var1);
}

