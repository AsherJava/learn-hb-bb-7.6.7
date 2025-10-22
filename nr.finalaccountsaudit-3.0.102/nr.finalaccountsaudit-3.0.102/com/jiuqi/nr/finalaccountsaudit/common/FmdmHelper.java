/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 */
package com.jiuqi.nr.finalaccountsaudit.common;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class FmdmHelper {
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataCtrl;
    @Autowired
    private IEntityViewRunTimeController entityViewCtrl;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    private RuntimeViewController viewController;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    private List<IFMDMAttribute> fieldList;
    private HashMap<String, IFMDMAttribute> dicfield = new HashMap();
    private String entityId;

    private FmdmHelper() {
    }

    public static FmdmHelper newHelper(String formSchemeKey) {
        FmdmHelper result = (FmdmHelper)BeanUtil.getBean(FmdmHelper.class);
        result.init(formSchemeKey);
        return result;
    }

    public void init(String formSchemeKey) {
        this.entityId = this.entityQueryHelper.getDwEntityView(formSchemeKey).getEntityId();
        FormSchemeDefine formSchemeDefine = this.viewController.getFormScheme(formSchemeKey);
        TaskDefine task = this.viewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setEntityId(this.entityId);
        fmdmAttributeDTO.setFormSchemeKey(formSchemeKey);
        this.fieldList = this.fmdmAttributeService.list(fmdmAttributeDTO);
        for (IFMDMAttribute fmAttr : this.fieldList) {
            this.dicfield.put(fmAttr.getCode(), fmAttr);
        }
    }

    public List<IFMDMAttribute> getAllFmAttr() {
        return this.fieldList;
    }

    public String getFmTableName() throws Exception {
        return this.dataSchemeService.getDeployInfoByColumnKey(this.fieldList.get(0).getID()).getTableName();
    }

    public String getBblxCode() throws JQException {
        IEntityModel entityModel = this.metaService.getEntityModel(this.entityId);
        if (entityModel != null && entityModel.getBblxField() != null) {
            return entityModel.getBblxField().getCode();
        }
        return null;
    }

    public IFMDMAttribute queryByCode(String fmfdCode) {
        return this.dicfield.get(fmfdCode);
    }
}

