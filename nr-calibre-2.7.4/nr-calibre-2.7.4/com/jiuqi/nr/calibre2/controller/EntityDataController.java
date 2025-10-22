/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.calibre2.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.calibre2.domain.EntityQueryParam;
import com.jiuqi.nr.calibre2.internal.service.IEntityQueryService;
import com.jiuqi.nr.calibre2.vo.SelectedEntityVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/calibre2/"})
@Api(tags={"\u53e3\u5f84\u7ba1\u7406\uff1a\u5b9e\u4f53\u76f8\u5173\u6570\u636e\u67e5\u8be2"})
public class EntityDataController {
    @Autowired
    private IEntityQueryService entityQueryService;

    @ApiOperation(value="\u67e5\u8be2\u5df2\u9009\u7684\u5b9e\u4f53\u6570\u636e")
    @PostMapping(value={"entity/selected"})
    public List<SelectedEntityVO> getSelectedEntity(@RequestBody EntityQueryParam entityQueryParam) throws JQException {
        return this.entityQueryService.querySelectedEntity(entityQueryParam);
    }
}

