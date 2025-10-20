/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.base.client.rpunitmapping;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.client.rpunitmapping.dto.Org2RpunitMappingSaveDTO;
import com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO;
import com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingReturnVO;
import com.jiuqi.dc.base.client.rpunitmapping.result.Org2RpunitMappingVO;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface Org2RpunitMappingClient {
    public static final String BASE_PATH = "/api/datacenter/v1/dm/mapping/rpunit";

    @PostMapping(value={"/api/datacenter/v1/dm/mapping/rpunit/list"})
    public BusinessResponseEntity<Org2RpunitMappingReturnVO> listAll(@RequestBody Org2RpunitMappingQueryVO var1);

    @PostMapping(value={"/api/datacenter/v1/dm/mapping/rpunit/save"})
    public BusinessResponseEntity<List<Org2RpunitMappingVO>> save(@RequestBody List<Org2RpunitMappingVO> var1);

    @PostMapping(value={"/api/datacenter/v1/dm/mapping/rpunit/save_or_update"})
    public BusinessResponseEntity<Org2RpunitMappingVO> saveOrUpdate(@RequestBody Org2RpunitMappingSaveDTO var1);

    @PostMapping(value={"/api/datacenter/v1/dm/mapping/rpunit/delete"})
    public BusinessResponseEntity<Integer> delete(@RequestBody Org2RpunitMappingQueryVO var1);
}

