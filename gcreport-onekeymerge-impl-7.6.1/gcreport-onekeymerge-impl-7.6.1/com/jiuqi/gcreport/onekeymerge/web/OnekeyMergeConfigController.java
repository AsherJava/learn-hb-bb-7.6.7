/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.onekeymerge.api.OnekeyMergeConfigClient
 *  com.jiuqi.gcreport.onekeymerge.vo.GcConfigVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.onekeymerge.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.onekeymerge.api.OnekeyMergeConfigClient;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeConfigService;
import com.jiuqi.gcreport.onekeymerge.vo.GcConfigVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
public class OnekeyMergeConfigController
implements OnekeyMergeConfigClient {
    @Autowired
    private GcOnekeyMergeConfigService configService;

    public BusinessResponseEntity<Object> saveConfig(GcConfigVO configVO) {
        this.configService.saveConfig(configVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> deleteConfig(String id) {
        this.configService.deleteConfig(id);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> updateConfig(GcConfigVO configVO) {
        this.configService.updateConfig(configVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<List<GcConfigVO>> getTaskResultAndLogs() {
        List<GcConfigVO> configByUser = this.configService.getConfigByUser();
        return BusinessResponseEntity.ok(configByUser);
    }
}

