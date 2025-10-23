/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.common.DataSchemeEnum
 *  com.jiuqi.nr.datascheme.web.base.EntityUtil
 *  com.jiuqi.nr.datascheme.web.facade.DataGroupVO
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.query.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.web.base.EntityUtil;
import com.jiuqi.nr.datascheme.web.facade.DataGroupVO;
import com.jiuqi.nr.query.datascheme.service.IDesignQueryDataSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/query-datascheme/"})
@Api(tags={"\u67e5\u8be2\u6570\u636e\u65b9\u6848\u6570\u636e\u5206\u7ec4\u7ba1\u7406\u670d\u52a1"})
public class QueryDataGroupRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(QueryDataGroupRestController.class);
    @Autowired
    private IDesignQueryDataSchemeService designDataSchemeService;

    @ApiOperation(value="\u65b0\u589e\u6570\u636e\u5206\u7ec4")
    @PostMapping(value={"group/add"})
    public String addDataGroup(@RequestBody DataGroupVO vo) throws JQException {
        this.LOGGER.debug("\u65b0\u589e\u6570\u636e\u5206\u7ec4\uff1a{}[{}]{}\u3002", vo.getTitle(), vo.getCode(), vo.isSchemeGroup());
        DesignDataGroup group = EntityUtil.groupVO2Entity((IDesignDataSchemeService)this.designDataSchemeService, (DataGroupVO)vo, null);
        if (vo.isSchemeGroup()) {
            group.setDataGroupKind(DataGroupKind.QUERY_SCHEME_GROUP);
        }
        try {
            return this.designDataSchemeService.insertDataGroup(group);
        }
        catch (SchemeDataException e) {
            this.LOGGER.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u6570\u636e\u5206\u7ec4")
    @PostMapping(value={"group/update"})
    public void updateDataGroup(@RequestBody DataGroupVO vo) throws JQException {
        this.LOGGER.debug("\u66f4\u65b0\u6570\u636e\u5206\u7ec4\uff1a{}[{}]{}\u3002", vo.getTitle(), vo.getCode(), vo.isSchemeGroup());
        DesignDataGroup dataGroup = this.designDataSchemeService.getDataGroup(vo.getKey());
        DesignDataGroup group = EntityUtil.groupVO2Entity((IDesignDataSchemeService)this.designDataSchemeService, (DataGroupVO)vo, (DesignDataGroup)dataGroup);
        if (vo.isSchemeGroup()) {
            group.setDataGroupKind(DataGroupKind.QUERY_SCHEME_GROUP);
        }
        try {
            this.designDataSchemeService.updateDataGroup(group);
        }
        catch (SchemeDataException e) {
            this.LOGGER.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u6570\u636e\u5206\u7ec4")
    @PostMapping(value={"group/delete/{key}"})
    public void deleteDataGroup(@PathVariable String key) throws JQException {
        this.LOGGER.debug("\u5220\u9664\u6570\u636e\u5206\u7ec4\uff1a{}\u3002", (Object)key);
        try {
            this.designDataSchemeService.deleteQueryDataGroup(key);
        }
        catch (SchemeDataException e) {
            this.LOGGER.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }
}

