/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.zb.scheme.core.ZbInfo
 *  com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.dataresource.web.rest;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.authority.DataResourceAuthorityService;
import com.jiuqi.nr.dataresource.common.DataResourceEnum;
import com.jiuqi.nr.dataresource.common.DataResourceException;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.dataresource.web.vo.DataResourceVO;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/dataresource/"})
@Api(tags={"\u6570\u636e\u8d44\u6e90\uff1a\u76ee\u5f55API"})
public class DataResourceController {
    @Autowired
    private IDataResourceService resourceService;
    @Autowired
    private DataResourceAuthorityService auth;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IZbSchemeService zbSchemeService;

    @ApiOperation(value="\u76ee\u5f55\u67e5\u8be2")
    @GetMapping(value={"resource/get"})
    public DataResourceVO queryDataResource(@RequestParam String key, @RequestParam(required=false) String period) {
        DataField dataField;
        DataResource dataResource = this.resourceService.get(key);
        if (dataResource == null) {
            return null;
        }
        DataResourceVO res = new DataResourceVO(dataResource);
        if (StringUtils.isNotEmpty((String)dataResource.getLinkZb()) && (dataField = this.iRuntimeDataSchemeService.getDataField(dataResource.getLinkZb())) != null) {
            DataScheme dataScheme;
            ZbSchemeVersion zbSchemeVersion;
            ZbInfo zbInfo;
            String title = dataField.getTitle();
            if (StringUtils.isNotEmpty((String)period) && (zbInfo = this.zbSchemeService.getZbInfoByVersionAndCode((zbSchemeVersion = this.zbSchemeService.getZbSchemeVersion((dataScheme = this.iRuntimeDataSchemeService.getDataScheme(dataField.getDataSchemeKey())).getZbSchemeKey(), period)).getKey(), dataField.getCode())) != null) {
                title = zbInfo.getTitle();
            }
            res.setLinkZbTitle(title);
            res.setLinkZbCode(dataField.getCode());
        }
        return res;
    }

    @ApiOperation(value="\u76ee\u5f55\u6dfb\u52a0")
    @PostMapping(value={"resource/add"})
    public String addDataResource(@RequestBody DataResourceVO resourceVO) throws JQException {
        try {
            return this.resourceService.insert(resourceVO.toDr(this.resourceService));
        }
        catch (DataResourceException e) {
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }

    @ApiOperation(value="\u76ee\u5f55\u5220\u9664")
    @PostMapping(value={"resource/del"})
    public void deleteDataResource(@RequestParam String key) throws JQException {
        try {
            this.resourceService.delete(key);
        }
        catch (DataResourceException e) {
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }

    @ApiOperation(value="\u76ee\u5f55\u66f4\u65b0")
    @PostMapping(value={"resource/update"})
    public void updateDataResource(@RequestBody DataResourceVO resourceVO) throws JQException {
        try {
            this.resourceService.update(resourceVO.toDr(this.resourceService));
        }
        catch (DataResourceException e) {
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }

    @ApiOperation(value="\u76ee\u5f55\u66f4\u65b0")
    @PostMapping(value={"resource/action/move"})
    @Transactional(rollbackFor={Exception.class})
    public void move(@RequestBody List<DataResourceVO> resourceVO) throws JQException {
        try {
            if (resourceVO.isEmpty()) {
                return;
            }
            if (!this.auth.canWrite(resourceVO.get(0).getResourceDefineKey(), NodeType.TREE.getValue())) {
                throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DG_3_3.getMessage());
            }
            ArrayList<String> keys = new ArrayList<String>(resourceVO.size());
            HashMap<String, DataResourceVO> map = new HashMap<String, DataResourceVO>(resourceVO.size());
            for (DataResourceVO dataResourceVO : resourceVO) {
                keys.add(dataResourceVO.getKey());
                map.put(dataResourceVO.getKey(), dataResourceVO);
            }
            List<DataResource> dataResources = this.resourceService.get(keys);
            for (DataResource dataResource : dataResources) {
                DataResourceVO vo = (DataResourceVO)map.get(dataResource.getKey());
                dataResource.setOrder(vo.getOrder());
            }
            this.resourceService.update(dataResources);
        }
        catch (DataResourceException e) {
            throw new JQException((ErrorEnum)DataResourceEnum.DATA_RESOURCE, e.getMessage());
        }
    }
}

