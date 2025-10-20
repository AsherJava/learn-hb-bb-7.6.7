/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.bde.fetchsetting.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.GeneratingFetchSettingVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CrossFormSettingApi {
    @PostMapping(value={"/api/gcreport/v1/fetch/queryCrossFormData"})
    public BusinessResponseEntity<Object> queryCrossFormData(@RequestBody FetchSettingCond var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/generatingFetchSetting"})
    public BusinessResponseEntity<Object> generatingFetchSetting(@RequestBody GeneratingFetchSettingVO var1);
}

