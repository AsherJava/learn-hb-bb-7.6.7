/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionDesignTimeController2
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.DesignTimeViewController
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.designer.util.EntityDefineObject
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IDesignTimeFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.basedata.service.BaseDataDefineService
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 */
package com.jiuqi.nr.singlequeryimport.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.impl.controller.DataDefinitionDesignTimeController2;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.util.EntityDefineObject;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IDesignTimeFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.singlequeryimport.service.GroupSetService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GroupSetServiceImpl
implements GroupSetService {
    @Autowired
    BaseDataDefineService baseDataDefineService;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private DesignTimeViewController designTimeViewController;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    @Qualifier(value="DataDefinitionDesignTimeController2")
    private DataDefinitionDesignTimeController2 npDesignTimeController;
    @Autowired
    private IDesignTimeFMDMAttributeService iFMDMAttributeServicel;
    @Autowired
    DataModelService designDataModelService;

    @Override
    public String getMnum(String code) {
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        baseDataDefineDTO.setName(code);
        baseDataDefineDTO.setDeepClone(Boolean.valueOf(false));
        BaseDataDefineDO baseDataDefineDO = this.baseDataDefineService.get(baseDataDefineDTO);
        return baseDataDefineDO.getLevelcode();
    }

    @Override
    public List<EntityDefineObject> getLinks(String formKey, String codeEnum) throws JQException {
        ArrayList<String> fieldKeys = new ArrayList<String>();
        fieldKeys.add(codeEnum);
        return this.getLinkEnum(fieldKeys, formKey);
    }

    public List<EntityDefineObject> getLinkEnum(List<String> fieldKeys, String formKey) throws JQException {
        ArrayList<IEntityDefine> entityDefineList = new ArrayList<IEntityDefine>();
        DesignFormDefine queryFormById = this.designTimeViewController.queryFormById(formKey);
        if (FormType.FORM_TYPE_NEWFMDM.getValue() == queryFormById.getFormType().getValue()) {
            FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
            DesignFormSchemeDefine formscheme = this.designTimeViewController.queryFormSchemeDefine(queryFormById.getFormScheme());
            fmdmAttributeDTO.setFormSchemeKey(formscheme.getKey());
            List attributes1 = this.iFMDMAttributeServicel.list(fmdmAttributeDTO);
            for (IFMDMAttribute fmdm : attributes1) {
                for (String code : fieldKeys) {
                    TableModelDefine tableModel;
                    IEntityDefine queryEntity;
                    if (!fmdm.getCode().equals(code) || !StringUtils.isNotEmpty((String)fmdm.getReferTableID()) || null == (queryEntity = this.iEntityMetaService.queryEntityByCode((tableModel = this.designDataModelService.getTableModelDefineById(fmdm.getReferTableID())).getCode()))) continue;
                    entityDefineList.add(queryEntity);
                }
            }
        } else {
            List feilds = this.npDesignTimeController.queryFieldDefines(fieldKeys.toArray(new String[0]));
            for (DesignFieldDefine field : feilds) {
                if (!StringUtils.isNotEmpty((String)field.getEntityKey())) continue;
                try {
                    IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(field.getEntityKey());
                    if (null == queryEntity) continue;
                    entityDefineList.add(queryEntity);
                }
                catch (Exception exception) {}
            }
        }
        List<EntityDefineObject> result = entityDefineList.stream().map(e -> new EntityDefineObject(e)).collect(Collectors.toList());
        return result;
    }
}

