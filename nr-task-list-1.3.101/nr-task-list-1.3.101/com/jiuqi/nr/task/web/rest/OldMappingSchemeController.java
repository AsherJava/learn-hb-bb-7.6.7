/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.exception.FormSchemeException;
import com.jiuqi.nr.task.mapping.dto.MappingSchemeInfo;
import com.jiuqi.nr.task.mapping.service.OldMappingSchemeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@ApiOperation(value="\u6620\u5c04\u65b9\u6848\uff08\u65e7\uff09\u67e5\u8be2\u63a5\u53e3")
@RequestMapping(value={"/api/v1/old-mapping-scheme"})
public class OldMappingSchemeController {
    @Autowired
    private OldMappingSchemeService mappingSchemeService;

    @GetMapping(value={"get/{key}"})
    public MappingSchemeInfo get(@PathVariable String key) throws JQException {
        try {
            return this.mappingSchemeService.get(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.OLD_MAPPING_SCHEME_QUERY, e.getMessage());
        }
    }

    @GetMapping(value={"del/{key}"})
    public boolean delScheme(@PathVariable String key) throws JQException {
        try {
            return this.mappingSchemeService.del(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormSchemeException.OLD_MAPPING_SCHEME_DEL, e.getMessage());
        }
    }
}

