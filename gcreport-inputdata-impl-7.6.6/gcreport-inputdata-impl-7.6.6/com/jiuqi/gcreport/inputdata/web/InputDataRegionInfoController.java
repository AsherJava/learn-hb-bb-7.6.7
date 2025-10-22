/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.inputdata.api.InputDataRegionInfoClient
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.inputdata.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.inputdata.api.InputDataRegionInfoClient;
import com.jiuqi.gcreport.inputdata.inputdata.service.InputDataRegionInfoService;
import com.jiuqi.gcreport.inputdata.util.OffsetVchrItemUtils;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class InputDataRegionInfoController
implements InputDataRegionInfoClient {
    @Autowired
    private InputDataRegionInfoService inputDataRegionInfoService;

    public BusinessResponseEntity<Set<String>> listInputDataRegionKeyByFromKey(List<String> formKeys) {
        return BusinessResponseEntity.ok(this.inputDataRegionInfoService.listInputDataRegionKeyByFromKey(formKeys));
    }

    @ApiOperation(value="\u83b7\u53d6\u62b5\u6d88\u5206\u7ec4\u5b57\u6bb5", httpMethod="GET")
    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List> getOffsetGroupingField(@PathVariable(value="systemId") String systemId) {
        List offsetGroupingField = OffsetVchrItemUtils.getOffsetGroupingField(systemId);
        return BusinessResponseEntity.ok((Object)offsetGroupingField);
    }
}

