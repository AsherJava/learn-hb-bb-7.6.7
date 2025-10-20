/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.clbr.api.ClbrSchemeClient
 *  com.jiuqi.gcreport.clbr.dto.ClbrSchemeBatchQueryDTO
 *  com.jiuqi.gcreport.clbr.dto.ClbrSchemeDTO
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeTreeVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeVO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.clbr.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.clbr.api.ClbrSchemeClient;
import com.jiuqi.gcreport.clbr.dto.ClbrSchemeBatchQueryDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrSchemeDTO;
import com.jiuqi.gcreport.clbr.service.ClbrSchemeService;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeTreeVO;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeVO;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class ClbrSchemeController
implements ClbrSchemeClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClbrSchemeController.class);
    @Autowired
    private ClbrSchemeService clbrSchemeService;

    public BusinessResponseEntity<ClbrSchemeVO> add(@RequestBody ClbrSchemeVO clbrSchemeVO) {
        return BusinessResponseEntity.ok((Object)this.clbrSchemeService.save(clbrSchemeVO));
    }

    public BusinessResponseEntity<Boolean> edit(@RequestBody ClbrSchemeVO clbrSchemeVO) {
        return BusinessResponseEntity.ok((Object)this.clbrSchemeService.edit(clbrSchemeVO));
    }

    public BusinessResponseEntity<PageInfo<ClbrSchemeVO>> query(@RequestBody ClbrSchemeCondition clbrSchemeCondition) {
        return BusinessResponseEntity.ok(this.clbrSchemeService.list(clbrSchemeCondition));
    }

    public BusinessResponseEntity<Boolean> delete(@RequestBody List<String> schemeIds) {
        this.clbrSchemeService.delete(schemeIds);
        return BusinessResponseEntity.ok((Object)true);
    }

    public BusinessResponseEntity<Map<String, List<ClbrSchemeDTO>>> queryScheme(ClbrSchemeBatchQueryDTO clbrSchemeBatchQueryDTO) {
        LOGGER.info("\u5171\u4eab\u67e5\u8be2\u65b9\u6848\u53c2\u6570\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)clbrSchemeBatchQueryDTO));
        List clbrSchemeQueryDTOs = clbrSchemeBatchQueryDTO.getClbrSchemeQueryDTOs();
        if (CollectionUtils.isEmpty((Collection)clbrSchemeQueryDTOs)) {
            return BusinessResponseEntity.ok(Collections.emptyMap());
        }
        HashMap map = new HashMap();
        clbrSchemeQueryDTOs.stream().forEach(clbrSchemeQueryDTO -> {
            ClbrSchemeCondition schemeCondition = new ClbrSchemeCondition();
            schemeCondition.setClbrTypes(clbrSchemeQueryDTO.getClbrType());
            schemeCondition.setRelations(clbrSchemeQueryDTO.getRelation());
            schemeCondition.setOppRelations(clbrSchemeQueryDTO.getOppRelation());
            schemeCondition.setPageNum(Integer.valueOf(-1));
            schemeCondition.setPageSize(Integer.valueOf(-1));
            List<ClbrSchemeDTO> clbrSchemeDTOS = this.clbrSchemeService.listByConditionToDTO(schemeCondition);
            map.put(clbrSchemeQueryDTO.getKey(), clbrSchemeDTOS);
        });
        return BusinessResponseEntity.ok(map);
    }

    public BusinessResponseEntity<List<ClbrSchemeTreeVO>> listTree() {
        return BusinessResponseEntity.ok(this.clbrSchemeService.listTree());
    }
}

