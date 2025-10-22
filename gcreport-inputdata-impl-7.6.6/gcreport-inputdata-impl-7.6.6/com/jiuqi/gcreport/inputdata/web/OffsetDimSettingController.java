/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.inputdata.api.OffsetDimSettingClient
 *  com.jiuqi.gcreport.inputdata.formsetting.OffsetDimSettingVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.inputdata.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.inputdata.api.OffsetDimSettingClient;
import com.jiuqi.gcreport.inputdata.formsetting.OffsetDimSettingVO;
import com.jiuqi.gcreport.inputdata.formsetting.service.OffsetDimSettingService;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
public class OffsetDimSettingController
implements OffsetDimSettingClient {
    private final OffsetDimSettingService offsetDimSettingService;

    public OffsetDimSettingController(OffsetDimSettingService offsetDimSettingService) {
        this.offsetDimSettingService = offsetDimSettingService;
    }

    public BusinessResponseEntity<OffsetDimSettingVO> getOffsetDims(String formId) {
        return BusinessResponseEntity.ok((Object)this.offsetDimSettingService.getOffsetDimSettingByFormId(formId));
    }

    public BusinessResponseEntity<OffsetDimSettingVO> saveOffsetDims(OffsetDimSettingVO offsetDimSettingVO) {
        return BusinessResponseEntity.ok((Object)this.offsetDimSettingService.save(offsetDimSettingVO));
    }
}

