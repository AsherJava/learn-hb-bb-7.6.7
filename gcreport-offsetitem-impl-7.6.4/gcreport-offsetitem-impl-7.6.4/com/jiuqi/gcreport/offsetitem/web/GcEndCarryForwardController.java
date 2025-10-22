/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.api.GcEndCarryForwardClient
 *  com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardParamVO
 *  com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardResultVO
 *  com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.offsetitem.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.api.GcEndCarryForwardClient;
import com.jiuqi.gcreport.offsetitem.service.GcEndCarryForwardService;
import com.jiuqi.gcreport.offsetitem.service.impl.EndCarryForwardDataSourceServiceImpl;
import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardParamVO;
import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardResultVO;
import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GcEndCarryForwardController
implements GcEndCarryForwardClient {
    @Autowired
    private GcEndCarryForwardService endCarryForwardService;
    @Autowired
    private EndCarryForwardDataSourceServiceImpl endCarryForwardDataSourceService;

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> saveEndCarryForward(@RequestBody EndCarryForwardParamVO paramVO) {
        QueryParamsVO queryParamsVO = paramVO.getQueryParamsVO();
        LossGainOffsetVO lossGainOffsetVO = paramVO.getLossGainOffsetVO();
        return BusinessResponseEntity.ok((Object)this.endCarryForwardDataSourceService.saveEndCarryForward(queryParamsVO, lossGainOffsetVO));
    }

    public BusinessResponseEntity<EndCarryForwardResultVO> queryEndCarryForwardResult(@RequestBody QueryParamsVO queryParamsVO) {
        return BusinessResponseEntity.ok((Object)this.endCarryForwardService.queryEndCarryForward(queryParamsVO));
    }

    public BusinessResponseEntity<Pagination<Map<String, Object>>> listMinRecoveryPentrateDatas(MinorityRecoveryParamsVO queryParamsVO) {
        return BusinessResponseEntity.ok(this.endCarryForwardService.listMinRecoveryPentrateDatas(queryParamsVO));
    }
}

