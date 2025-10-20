/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelGroupExternalDTO
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.datamodel.controller.abandoned;

import com.jiuqi.va.datamodel.common.DataModelCoreI18nUtil;
import com.jiuqi.va.datamodel.domain.DataModelGroupDO;
import com.jiuqi.va.datamodel.domain.DataModelGroupDTO;
import com.jiuqi.va.datamodel.service.VaDataModelGroupService;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDTO;
import java.util.HashSet;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="deprecatedDataModelGroupController")
@Deprecated
@ConditionalOnProperty(name={"va.datamodel.binary.compatible"}, havingValue="true", matchIfMissing=true)
@RequestMapping(value={"/dataModel/defineGroup"})
public class VaDataModelGroupController {
    private String errorParamMissing = "datamodel.error.parameter.missing";
    private String errorOperate = "datamodel.error.common.operate";
    private String successOperate = "datamodel.success.common.operate";
    @Autowired
    VaDataModelGroupService vaDataModelGroupService;

    @PostMapping(value={"/list"})
    Object list(@RequestBody DataModelGroupDTO param) {
        return MonoVO.just(this.vaDataModelGroupService.list(param));
    }

    @PostMapping(value={"/tree"})
    Object tree(@RequestBody DataModelGroupDTO param) {
        return MonoVO.just(this.vaDataModelGroupService.tree(param));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object add(@RequestBody DataModelGroupDO param) {
        if (!StringUtils.hasText(param.getBiztype()) || !StringUtils.hasText(param.getName())) {
            return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage(this.errorParamMissing, new Object[0])));
        }
        DataModelGroupDO oldParam = new DataModelGroupDO();
        oldParam.setBiztype(param.getBiztype());
        oldParam.setName(param.getName());
        if (this.vaDataModelGroupService.exist(oldParam)) {
            return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.group.add.existed", new Object[0])));
        }
        if (this.vaDataModelGroupService.add(param) > 0) {
            return MonoVO.just((Object)R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0])));
        }
        return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0])));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object update(@RequestBody DataModelGroupDO param) {
        if (!StringUtils.hasText(param.getBiztype()) || !StringUtils.hasText(param.getName())) {
            return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage(this.errorParamMissing, new Object[0])));
        }
        if (this.isFixedGroup(param)) {
            return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.group.update.fixed.groups", new Object[0])));
        }
        DataModelGroupDTO oldParam = new DataModelGroupDTO();
        oldParam.setBiztype(param.getBiztype());
        oldParam.setName(param.getName());
        List<DataModelGroupDO> old = this.vaDataModelGroupService.list(oldParam);
        if (old == null || old.isEmpty()) {
            return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.group.update.not.found", new Object[0])));
        }
        param.setId(old.get(0).getId());
        if (this.vaDataModelGroupService.update(param) > 0) {
            return MonoVO.just((Object)R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0])));
        }
        return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0])));
    }

    @PostMapping(value={"/delete"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object delete(@RequestBody DataModelGroupDO param) {
        if (!StringUtils.hasText(param.getBiztype()) || !StringUtils.hasText(param.getName())) {
            return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage(this.errorParamMissing, new Object[0])));
        }
        if (this.isFixedGroup(param)) {
            return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.group.delete.fixed.groups", new Object[0])));
        }
        DataModelGroupDTO oldParam = new DataModelGroupDTO();
        oldParam.setBiztype(param.getBiztype());
        oldParam.setName(param.getName());
        List<DataModelGroupDO> old = this.vaDataModelGroupService.list(oldParam);
        if (old == null || old.isEmpty()) {
            return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.group.delete.not.exist", new Object[0])));
        }
        DataModelGroupDO delparam = new DataModelGroupDO();
        delparam.setId(old.get(0).getId());
        if (this.vaDataModelGroupService.delete(delparam) > 0) {
            return MonoVO.just((Object)R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0])));
        }
        return MonoVO.just((Object)R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0])));
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
    Object externalAdd(@RequestBody DataModelGroupExternalDTO param) {
        return MonoVO.just((Object)this.vaDataModelGroupService.externalAdd(param));
    }

    @PostMapping(value={"/external/list"})
    Object externalList(@RequestBody DataModelGroupExternalDTO param) {
        return MonoVO.just(this.vaDataModelGroupService.externalList(param));
    }

    @PostMapping(value={"/external/get"})
    @RequiresPermissions(value={"vaDatamodel:define:mgr"})
    Object externalGet(@RequestBody DataModelGroupExternalDTO param) {
        return MonoVO.just((Object)this.vaDataModelGroupService.externalGet(param));
    }
}

