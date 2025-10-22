/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.inputdata.offsetdatacheck.api.OffsetDataCheckClient
 *  com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.CheckFailedInputDataVO
 *  com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.OffsetDataCheckVO
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.inputdata.offsetdatacheck.api.OffsetDataCheckClient;
import com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.CheckFailedInputDataVO;
import com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.OffsetDataCheckVO;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class OffsetDataCheckController
implements OffsetDataCheckClient {
    @Autowired
    private InputDataService service;

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<CheckFailedInputDataVO>> check(@RequestBody OffsetDataCheckVO checkCondition) {
        List<CheckFailedInputDataVO> checkFailedItems = this.service.checkOffsetData(checkCondition);
        return BusinessResponseEntity.ok(checkFailedItems);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<CheckFailedInputDataVO>> cancel(@RequestBody Map<String, Object> params) {
        OffsetDataCheckVO checkCondition = (OffsetDataCheckVO)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("checkCondition")), OffsetDataCheckVO.class);
        Boolean cancelAll = (Boolean)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("cancelAll")), Boolean.class);
        Set offsetGroupIds = (Set)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("offsetGroupIds")), (TypeReference)new TypeReference<Set<String>>(){});
        List<CheckFailedInputDataVO> checkFailedItems = this.service.cancelOffset(checkCondition, cancelAll, offsetGroupIds);
        return BusinessResponseEntity.ok(checkFailedItems);
    }
}

