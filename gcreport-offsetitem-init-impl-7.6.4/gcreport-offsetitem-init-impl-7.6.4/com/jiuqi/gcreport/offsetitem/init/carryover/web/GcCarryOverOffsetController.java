/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.init.carryover.api.GcCarryOverOffsetClient
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectMappingVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.init.carryover.api.GcCarryOverOffsetClient;
import com.jiuqi.gcreport.offsetitem.init.carryover.service.GcCarryOverOffsetService;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectMappingVO;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GcCarryOverOffsetController
implements GcCarryOverOffsetClient {
    private static final Logger logger = LoggerFactory.getLogger(GcCarryOverOffsetController.class);
    @Autowired
    private GcCarryOverOffsetService gcCarryOverOffsetService;

    public BusinessResponseEntity<String> checkSubjectMapping(String systemId, Map<String, List<CarryOverOffsetSubjectMappingVO>> mappingVOS) {
        return BusinessResponseEntity.ok((Object)this.gcCarryOverOffsetService.checkSubjectMapping(systemId, mappingVOS));
    }
}

