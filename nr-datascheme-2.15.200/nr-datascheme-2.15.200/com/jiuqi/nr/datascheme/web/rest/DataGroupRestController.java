/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.web.base.EntityUtil;
import com.jiuqi.nr.datascheme.web.facade.DataGroupVO;
import com.jiuqi.nr.datascheme.web.param.MoveSchemeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6570\u636e\u65b9\u6848:\u6570\u636e\u5206\u7ec4\u670d\u52a1"})
public class DataGroupRestController {
    private final Logger logger = LoggerFactory.getLogger(DataGroupRestController.class);
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;

    @ApiOperation(value="\u6839\u636e\u4e3b\u952e\u67e5\u8be2\u5206\u7ec4\u4fe1\u606f")
    @GetMapping(value={"group/query/{key}"})
    public DataGroupVO queryDataGroup(@PathVariable String key) throws JQException {
        DesignDataGroup group = this.designDataSchemeService.getDataGroup(key);
        if (group == null) {
            return null;
        }
        if (DataGroupKind.SCHEME_GROUP == group.getDataGroupKind() || DataGroupKind.QUERY_SCHEME_GROUP == group.getDataGroupKind() ? !this.dataSchemeAuthService.canReadGroup(key) : !this.dataSchemeAuthService.canReadScheme(group.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        return EntityUtil.groupEntity2VO(group);
    }

    @ApiOperation(value="\u65b0\u589e\u6570\u636e \u65b9\u6848\u5206\u7ec4")
    @PostMapping(value={"group/add"})
    public String addDataGroup(@RequestBody DataGroupVO vo) throws JQException {
        if (vo.isSchemeGroup() ? !this.dataSchemeAuthService.canWriteGroup(vo.getParentKey()) : !this.dataSchemeAuthService.canWriteScheme(vo.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        this.logger.debug("\u65b0\u589e\u6570\u636e\u5206\u7ec4\uff1a{}[{}]{}\u3002", vo.getTitle(), vo.getCode(), vo.isSchemeGroup());
        DesignDataGroup group = EntityUtil.groupVO2Entity(this.designDataSchemeService, vo, null);
        try {
            return this.designDataSchemeService.insertDataGroup(group);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u6570\u636e\u5206\u7ec4")
    @PostMapping(value={"group/update"})
    public void updateDataGroup(@RequestBody DataGroupVO vo) throws JQException {
        if (vo.isSchemeGroup() ? !this.dataSchemeAuthService.canWriteGroup(vo.getKey()) : !this.dataSchemeAuthService.canWriteScheme(vo.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        this.logger.debug("\u66f4\u65b0\u6570\u636e\u5206\u7ec4\uff1a{}[{}]{}\u3002", vo.getTitle(), vo.getCode(), vo.isSchemeGroup());
        DesignDataGroup dataGroup = this.designDataSchemeService.getDataGroup(vo.getKey());
        DesignDataGroup group = EntityUtil.groupVO2Entity(this.designDataSchemeService, vo, dataGroup);
        try {
            this.designDataSchemeService.updateDataGroup(group);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u6570\u636e\u5206\u7ec4")
    @PostMapping(value={"group/delete/{key}"})
    public void deleteDataGroup(@PathVariable String key) throws JQException {
        DesignDataGroup dataGroup = this.designDataSchemeService.getDataGroup(key);
        if (null == dataGroup) {
            return;
        }
        if (DataGroupKind.SCHEME_GROUP == dataGroup.getDataGroupKind() ? !this.dataSchemeAuthService.canWriteGroup(key) : !this.dataSchemeAuthService.canWriteScheme(dataGroup.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        this.logger.debug("\u5220\u9664\u6570\u636e\u5206\u7ec4\uff1a{}\u3002", (Object)key);
        try {
            this.designDataSchemeService.deleteDataGroup(key);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @PostMapping(value={"move_data_group"})
    @ApiOperation(value="\u79fb\u52a8\u6570\u636e\u5206\u7ec4")
    @Transactional(rollbackFor={Exception.class})
    public void moveDataGroup(@RequestBody MoveSchemeVO vo) throws JQException {
        this.logger.debug("\u79fb\u52a8\u6570\u636e\u65b9\u6848\u5206\u7ec4\uff1akey: {}, parent: {}, move: {} \u3002", vo.getKey(), vo.getParentKey(), vo.getMove());
        List<Object> groups = vo.getType() == NodeType.GROUP && vo.getSchemeKey() != null ? (vo.getSchemeKey().equals(vo.getParentKey()) ? this.designDataSchemeService.getDataGroupByScheme(vo.getParentKey()) : this.designDataSchemeService.getDataGroupByParent(vo.getParentKey())) : this.designDataSchemeService.getDataGroupByParent(vo.getParentKey());
        groups = groups.stream().filter(t -> this.dataSchemeAuthService.canReadGroup(vo.getParentKey())).collect(Collectors.toList());
        for (int i = 0; i < groups.size(); ++i) {
            int nextIndex = i + vo.getMove();
            if (!((DesignDataGroup)groups.get(i)).getKey().equals(vo.getKey())) continue;
            if (nextIndex < 0 || nextIndex >= groups.size()) {
                throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, "\u79fb\u52a8\u4f4d\u7f6e\u8d85\u51fa\u8303\u56f4");
            }
            if (!this.dataSchemeAuthService.canWriteGroup(((DesignDataGroup)groups.get(i)).getKey())) {
                throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
            }
            String order = ((DesignDataGroup)groups.get(i)).getOrder();
            ((DesignDataGroup)groups.get(i)).setOrder(((DesignDataGroup)groups.get(nextIndex)).getOrder());
            ((DesignDataGroup)groups.get(nextIndex)).setOrder(order);
            this.designDataSchemeService.updateDataGroups(Arrays.asList((DesignDataGroup)groups.get(i), (DesignDataGroup)groups.get(nextIndex)));
            return;
        }
    }
}

