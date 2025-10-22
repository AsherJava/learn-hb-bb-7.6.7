/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.org.api.intf.GcOrgTypeClient
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeApiParam
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeCopyApiParam
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.va.domain.common.R
 *  javax.validation.Valid
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.org.impl.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.org.api.intf.GcOrgTypeClient;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeApiParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeCopyApiParam;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.impl.fieldManager.service.GcOrgTypeService;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
class GcOrgTypeController
implements GcOrgTypeClient {
    @Autowired
    private GcOrgTypeService orgTypeService;

    GcOrgTypeController() {
    }

    private GcOrgTypeTool getTypeTool() {
        return GcOrgTypeTool.getInstance();
    }

    public BusinessResponseEntity<String> deleteUnitType(@RequestBody OrgTypeVO orgType) {
        OrgTypeVO type = this.getTypeTool().getOrgTypeByName(orgType.getName());
        if (type == null) {
            return BusinessResponseEntity.error((String)"\u4e0d\u5b58\u5728\u5bf9\u5e94\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b");
        }
        this.getTypeTool().removeOrgType(orgType.getName());
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    public BusinessResponseEntity<OrgTypeVO> findUnitType(@RequestBody GcOrgTypeApiParam param) {
        OrgTypeVO type = this.getTypeTool().getOrgTypeByName(param.getOrgType());
        if (type == null) {
            return BusinessResponseEntity.error((String)"\u4e0d\u5b58\u5728\u5bf9\u5e94\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b");
        }
        return BusinessResponseEntity.ok((Object)type);
    }

    public BusinessResponseEntity<List<OrgTypeVO>> queryAllUnitType() {
        List<OrgTypeVO> type = this.getTypeTool().listOrgType();
        if (type == null) {
            return BusinessResponseEntity.error((String)"\u4e0d\u5b58\u5728\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b");
        }
        return BusinessResponseEntity.ok(type);
    }

    public BusinessResponseEntity<String> updateUnitType(@Valid @RequestBody OrgTypeVO orgType) {
        Objects.requireNonNull(orgType);
        String id = orgType.getId();
        if (id == null) {
            throw new BusinessRuntimeException("\u5f53\u524d\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u64cd\u4f5c\u3002");
        }
        OrgTypeVO type = this.getTypeTool().getOrgTypeByName(orgType.getName());
        if (type != null && !type.getId().equals(id)) {
            return BusinessResponseEntity.error((String)"\u5df2\u7ecf\u5b58\u5728\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b");
        }
        this.getTypeTool().modifyOrgType(orgType);
        this.orgTypeService.updateOrgTypeBaseData(orgType);
        return BusinessResponseEntity.ok((Object)id);
    }

    public BusinessResponseEntity<OrgTypeVO> addUnitType(@Valid @RequestBody OrgTypeVO orgType) {
        OrgTypeVO type;
        String name = orgType.getName();
        if (name.toUpperCase().startsWith("MD_ORG")) {
            orgType.setName(name);
        } else {
            orgType.setName("MD_ORG_" + orgType.getName());
        }
        String id = orgType.getId();
        if (id == null) {
            orgType.setId(UUIDUtils.newUUIDStr());
        }
        if ((type = this.getTypeTool().getOrgTypeByName(orgType.getName())) != null) {
            return BusinessResponseEntity.error((String)"\u5df2\u7ecf\u5b58\u5728\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b");
        }
        this.getTypeTool().createOrgType(orgType);
        this.orgTypeService.addOrgTypeBaseData(orgType);
        return BusinessResponseEntity.ok((Object)orgType);
    }

    public BusinessResponseEntity<OrgTypeVO> copyUnitType(@Valid @RequestBody GcOrgTypeCopyApiParam param) {
        OrgTypeVO type;
        String id = param.getOrgTypeVo().getId();
        if (id == null) {
            param.getOrgTypeVo().setId(UUIDUtils.newUUIDStr());
        }
        if ((type = this.getTypeTool().getOrgTypeByName(param.getOrgTypeVo().getName())) != null) {
            return BusinessResponseEntity.error((String)"\u5df2\u7ecf\u5b58\u5728\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b");
        }
        String name = param.getOrgTypeVo().getName();
        if (name.toUpperCase().startsWith("MD_ORG")) {
            param.getOrgTypeVo().setName(name);
        } else {
            param.getOrgTypeVo().setName("MD_ORG_" + param.getOrgTypeVo().getName());
        }
        if (StringUtils.isEmpty((CharSequence)param.getOrgType()) || StringUtils.isEmpty((CharSequence)param.getOrgVerCode())) {
            this.getTypeTool().createOrgType(param.getOrgTypeVo());
        } else {
            R r = this.orgTypeService.copyOrgType(param.getOrgTypeVo(), param.getOrgType(), param.getOrgVerCode());
            if (r.getCode() == 1) {
                throw new BusinessRuntimeException("\u64cd\u4f5c\u5931\u8d25\uff1a" + r.getMsg());
            }
        }
        return BusinessResponseEntity.ok();
    }
}

