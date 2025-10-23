/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.zbquery.rest;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/zbquery/calibre"})
public class CalibreController {
    @Autowired
    private ICalibreDefineService calibreDefineService;
    @Autowired
    private IEntityMetaService iEntityMetaService;

    @ApiOperation(value="\u83b7\u53d6\u5b9e\u4f53\u7684\u53e3\u5f84\u5217\u8868")
    @GetMapping(value={"/getCalibres/{entityCode}"})
    public List<CalibreDefineDTO> getByRefer(@PathVariable String entityCode) {
        if (StringUtils.isNotEmpty((String)entityCode)) {
            String entityId = this.iEntityMetaService.getEntityIdByCode(entityCode);
            CalibreDefineDTO defineDTO = new CalibreDefineDTO();
            defineDTO.setEntityId(entityId);
            return (List)this.calibreDefineService.getByRefer(defineDTO).getData();
        }
        return null;
    }
}

