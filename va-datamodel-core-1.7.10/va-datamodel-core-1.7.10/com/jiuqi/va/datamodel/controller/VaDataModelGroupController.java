/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 *  com.jiuqi.va.domain.datamodel.DataModelGroupExternalDO
 *  com.jiuqi.va.domain.datamodel.DataModelGroupExternalDTO
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.datamodel.controller;

import com.jiuqi.va.datamodel.common.DataModelCoreI18nUtil;
import com.jiuqi.va.datamodel.domain.DataModelGroupDO;
import com.jiuqi.va.datamodel.domain.DataModelGroupDTO;
import com.jiuqi.va.datamodel.service.VaDataModelGroupService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDO;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDTO;
import java.util.HashSet;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaDataModelGroupController")
@RequestMapping(value={"/dataModel/defineGroup/binary"})
public class VaDataModelGroupController {
    private String errorParamMissing = "datamodel.error.parameter.missing";
    private String errorOperate = "datamodel.error.common.operate";
    private String successOperate = "datamodel.success.common.operate";
    @Autowired
    private VaDataModelGroupService vaDataModelGroupService;

    @PostMapping(value={"/list"})
    Object list(@RequestBody byte[] binaryData) {
        DataModelGroupDTO param = (DataModelGroupDTO)((Object)JSONUtil.parseObject((byte[])binaryData, DataModelGroupDTO.class));
        List<DataModelGroupDO> rs = this.vaDataModelGroupService.list(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/tree"})
    Object tree(@RequestBody byte[] binaryData) {
        DataModelGroupDTO param = (DataModelGroupDTO)((Object)JSONUtil.parseObject((byte[])binaryData, DataModelGroupDTO.class));
        PageVO<TreeVO<DataModelGroupDO>> rs = this.vaDataModelGroupService.tree(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object add(@RequestBody byte[] binaryData) {
        DataModelGroupDO param = (DataModelGroupDO)((Object)JSONUtil.parseObject((byte[])binaryData, DataModelGroupDO.class));
        R rs = null;
        if (!StringUtils.hasText(param.getBiztype()) || !StringUtils.hasText(param.getName())) {
            rs = R.error((String)DataModelCoreI18nUtil.getMessage(this.errorParamMissing, new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        DataModelGroupDO oldParam = new DataModelGroupDO();
        oldParam.setBiztype(param.getBiztype());
        oldParam.setName(param.getName());
        if (this.vaDataModelGroupService.exist(oldParam)) {
            rs = R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.group.add.existed", new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        if (this.vaDataModelGroupService.add(param) > 0) {
            rs = R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0]));
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object update(@RequestBody byte[] binaryData) {
        DataModelGroupDO param = (DataModelGroupDO)((Object)JSONUtil.parseObject((byte[])binaryData, DataModelGroupDO.class));
        R rs = null;
        if (!StringUtils.hasText(param.getBiztype()) || !StringUtils.hasText(param.getName())) {
            rs = R.error((String)DataModelCoreI18nUtil.getMessage(this.errorParamMissing, new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        if (this.isFixedGroup(param)) {
            rs = R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.group.update.fixed.groups", new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        DataModelGroupDTO oldParam = new DataModelGroupDTO();
        oldParam.setBiztype(param.getBiztype());
        oldParam.setName(param.getName());
        List<DataModelGroupDO> old = this.vaDataModelGroupService.list(oldParam);
        if (old == null || old.isEmpty()) {
            rs = R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.group.update.not.found", new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        param.setId(old.get(0).getId());
        if (this.vaDataModelGroupService.update(param) > 0) {
            rs = R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0]));
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/delete"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object delete(@RequestBody byte[] binaryData) {
        DataModelGroupDO param = (DataModelGroupDO)((Object)JSONUtil.parseObject((byte[])binaryData, DataModelGroupDO.class));
        R rs = null;
        if (!StringUtils.hasText(param.getBiztype()) || !StringUtils.hasText(param.getName())) {
            rs = R.error((String)DataModelCoreI18nUtil.getMessage(this.errorParamMissing, new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        if (this.isFixedGroup(param)) {
            rs = R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.group.delete.fixed.groups", new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        DataModelGroupDTO oldParam = new DataModelGroupDTO();
        oldParam.setBiztype(param.getBiztype());
        oldParam.setName(param.getName());
        List<DataModelGroupDO> old = this.vaDataModelGroupService.list(oldParam);
        if (old == null || old.isEmpty()) {
            rs = R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.group.delete.not.exist", new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        DataModelGroupDO delparam = new DataModelGroupDO();
        delparam.setId(old.get(0).getId());
        if (this.vaDataModelGroupService.delete(delparam) > 0) {
            rs = R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
            return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
        }
        rs = R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0]));
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    private boolean isFixedGroup(@RequestBody DataModelGroupDO param) {
        HashSet<String> curingGroupSet = new HashSet<String>();
        curingGroupSet.add("system");
        curingGroupSet.add("public");
        curingGroupSet.add("other");
        return curingGroupSet.contains(param.getName());
    }

    @PostMapping(value={"/external/add"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object externalAdd(@RequestBody byte[] binaryData) {
        DataModelGroupExternalDTO param = (DataModelGroupExternalDTO)JSONUtil.parseObject((byte[])binaryData, DataModelGroupExternalDTO.class);
        R rs = this.vaDataModelGroupService.externalAdd(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/external/list"})
    Object externalList(@RequestBody byte[] binaryData) {
        DataModelGroupExternalDTO param = (DataModelGroupExternalDTO)JSONUtil.parseObject((byte[])binaryData, DataModelGroupExternalDTO.class);
        List<DataModelGroupExternalDO> rs = this.vaDataModelGroupService.externalList(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/external/get"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object externalGet(@RequestBody byte[] binaryData) {
        DataModelGroupExternalDTO param = (DataModelGroupExternalDTO)JSONUtil.parseObject((byte[])binaryData, DataModelGroupExternalDTO.class);
        DataModelGroupExternalDO rs = this.vaDataModelGroupService.externalGet(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }
}

