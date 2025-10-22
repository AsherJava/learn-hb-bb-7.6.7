/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 */
package com.jiuqi.nr.formtype.internal.impl;

import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.formtype.common.EntityRowBizCodeGetter;
import com.jiuqi.nr.formtype.common.EntityUnitNatureGetter;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.common.UnitNatureGetter;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.internal.system.FormTypeOptionsService;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.formtype.service.IFormTypeCacheService;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import com.jiuqi.nr.formtype.service.IUnitTreeIconStorage;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class FormTypeApplyServiceImpl
implements IFormTypeApplyService {
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFormTypeCacheService iFormTypeCacheService;
    @Autowired
    private IFormTypeService iFormTypeService;
    @Autowired
    private IUnitTreeIconStorage iUnitTreeIconStorage;
    @Autowired
    private FormTypeOptionsService formTypeOptionsService;
    @Autowired
    private DataModelService dataModelService;

    @Override
    public boolean enableNrFormTypeMgr() {
        return this.formTypeOptionsService.enableNrFormTypeMgr();
    }

    public ZB getFormTypeZb(String orgName) {
        ZB formTypeZb = null;
        OrgCategoryDO param = new OrgCategoryDO();
        param.setName(orgName);
        PageVO pageVO = this.orgCategoryClient.list(param);
        if (pageVO != null && pageVO.getTotal() > 0) {
            OrgCategoryDO orgCategory = (OrgCategoryDO)pageVO.getRows().get(0);
            formTypeZb = this.getNrFormTypeZb(orgCategory);
        }
        return formTypeZb;
    }

    public ColumnModelDefine getFormTypeZbColumn(String orgName) {
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(orgName);
        List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        Set<String> allFormTypeCodes = this.iFormTypeCacheService.getAllFormTypeCodes();
        Optional<ColumnModelDefine> findBBLX = columnModelDefines.stream().filter(e -> StringUtils.hasText(e.getReferTableID()) && allFormTypeCodes.contains(e.getName())).findFirst();
        return findBBLX.orElse(null);
    }

    private ZB getDefaultFormTypeZb(OrgCategoryDO orgCategory) {
        for (ZB zb : orgCategory.getZbs()) {
            if (!"BBLX".equalsIgnoreCase(zb.getName())) continue;
            return zb;
        }
        return null;
    }

    private ZB getNrFormTypeZb(OrgCategoryDO orgCategory) {
        if (this.isMdOrg(orgCategory.getName())) {
            return null;
        }
        Set<String> allFormTypeCodes = this.iFormTypeCacheService.getAllFormTypeCodes();
        for (ZB zb : orgCategory.getZbs()) {
            String reltablename = zb.getReltablename();
            if (!StringUtils.hasLength(reltablename) || null == zb.getRelatetype() || 1 != zb.getRelatetype() || !allFormTypeCodes.contains(reltablename)) continue;
            return zb;
        }
        return null;
    }

    @Override
    public String getEntityFormTypeCode(String entityId) {
        String orgName = this.entityMetaService.getEntityCode(entityId);
        ZB formTypeZb = this.getFormTypeZb(orgName);
        if (null == formTypeZb) {
            return null;
        }
        return formTypeZb.getReltablename();
    }

    @Override
    public UnitNatureGetter getUnitNatureGetter(String formTypeCode) {
        List<FormTypeDataDefine> formTypeDatas = null;
        formTypeDatas = this.isFormType(formTypeCode) ? this.iFormTypeService.queryFormTypeDatas(formTypeCode) : Collections.emptyList();
        return new UnitNatureGetter(this, formTypeDatas);
    }

    @Override
    public EntityUnitNatureGetter getEntityFormTypeGetter(String entityId) {
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        IEntityAttribute bblxField = entityModel.getBblxField();
        if (null == bblxField) {
            return null;
        }
        String formTypeCode = this.getEntityFormTypeCode(entityId);
        List<FormTypeDataDefine> formTypeDatas = null;
        formTypeDatas = this.isFormType(formTypeCode) ? this.iFormTypeService.queryFormTypeDatas(formTypeCode) : Collections.emptyList();
        return new EntityUnitNatureGetter(entityId, formTypeCode, bblxField.getCode(), formTypeDatas);
    }

    @Override
    public String getAutoGenUnitCode(String parentCode, UnitNature unitNature) {
        Assert.notNull((Object)parentCode, "parentCode must not be null.");
        Assert.notNull((Object)unitNature, "unitNature must not be null.");
        return parentCode + unitNature.getValue();
    }

    @Override
    public String getIcon(FormTypeDataDefine formTypeData) {
        String iconBase64 = formTypeData.getIcon();
        if (!StringUtils.hasText(iconBase64)) {
            iconBase64 = this.getSysIconByUnitNature(formTypeData.getUnitNatrue());
        }
        return null == iconBase64 ? "" : iconBase64;
    }

    @Override
    public String getSysIconByUnitNature(UnitNature unitNature) {
        return this.iUnitTreeIconStorage.getBase64Icon(String.valueOf(unitNature.getValue()));
    }

    @Override
    public String getSysIcon(String code) {
        return this.iUnitTreeIconStorage.getBase64Icon(code);
    }

    @Override
    public Map<String, String> getAllSysIcon() {
        return this.iUnitTreeIconStorage.getAllBase64Icon();
    }

    @Override
    public Map<String, String> getAllSysIcon(String schemeKey) {
        return this.iUnitTreeIconStorage.getAllBase64Icon(schemeKey);
    }

    @Override
    public boolean isFormType(String formTypeCode) {
        return this.iFormTypeCacheService.isFormType(formTypeCode);
    }

    @Override
    public Map<String, String> getIcon(String defineCode) {
        if (this.isFormType(defineCode)) {
            List<FormTypeDataDefine> formTypeDatas = this.iFormTypeService.queryFormTypeDatas(defineCode);
            return formTypeDatas.stream().collect(Collectors.toMap(FormTypeDataDefine::getCode, this::getIcon));
        }
        return this.getAllSysIcon();
    }

    @Override
    public String getIcon(String defineCode, String dataCode) {
        if (this.isFormType(defineCode)) {
            FormTypeDataDefine formTypeData = this.iFormTypeService.queryFormTypeData(defineCode, dataCode);
            if (null == formTypeData) {
                return "";
            }
            return this.getIcon(formTypeData);
        }
        return this.getSysIcon(dataCode);
    }

    @Override
    public EntityRowBizCodeGetter getEntityRowBizCodeGetter(IEntityTable iEntityTable) {
        boolean enableNrFormTypeMgr = this.enableNrFormTypeMgr();
        IEntityAttribute bblxField = iEntityTable.getEntityModel().getBblxField();
        if (null == bblxField) {
            return new EntityRowBizCodeGetter(iEntityTable, enableNrFormTypeMgr, Collections.emptyList());
        }
        String formTypeCode = this.getEntityFormTypeCode(iEntityTable.getEntityModel().getEntityId());
        List<FormTypeDataDefine> formTypeDatas = null;
        formTypeDatas = this.isFormType(formTypeCode) ? this.iFormTypeService.queryFormTypeDatas(formTypeCode) : Collections.emptyList();
        return new EntityRowBizCodeGetter(iEntityTable, enableNrFormTypeMgr, formTypeDatas);
    }
}

