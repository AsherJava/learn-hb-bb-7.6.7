/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.client.rpunitmapping.Org2RpunitMappingClient
 *  com.jiuqi.dc.base.client.rpunitmapping.dto.Org2RpunitMappingSaveDTO
 *  com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO
 *  com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingReturnVO
 *  com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingVO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.base.impl.rpunitmapping.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.client.rpunitmapping.Org2RpunitMappingClient;
import com.jiuqi.dc.base.client.rpunitmapping.dto.Org2RpunitMappingSaveDTO;
import com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO;
import com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingReturnVO;
import com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingVO;
import com.jiuqi.dc.base.impl.rpunitmapping.service.Org2RpunitMappingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Org2RpunitMappingController
implements Org2RpunitMappingClient {
    @Autowired
    Org2RpunitMappingService service;

    public BusinessResponseEntity<Org2RpunitMappingReturnVO> listAll(@RequestBody Org2RpunitMappingQueryVO queryVO) {
        return BusinessResponseEntity.ok((Object)this.service.query(queryVO));
    }

    public BusinessResponseEntity<List<Org2RpunitMappingVO>> save(@RequestBody List<Org2RpunitMappingVO> voList) {
        return BusinessResponseEntity.ok(this.service.save(voList));
    }

    public BusinessResponseEntity<Org2RpunitMappingVO> saveOrUpdate(@RequestBody Org2RpunitMappingSaveDTO saveDto) {
        return BusinessResponseEntity.ok((Object)this.service.saveOrUpdate(saveDto));
    }

    public BusinessResponseEntity<Integer> delete(@RequestBody Org2RpunitMappingQueryVO queryVO) {
        return BusinessResponseEntity.ok((Object)this.service.deleteByIds(queryVO));
    }
}

