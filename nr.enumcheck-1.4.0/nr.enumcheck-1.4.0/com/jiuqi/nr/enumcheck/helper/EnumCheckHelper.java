/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.enumcheck.helper;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.enumcheck.common.EnumFieldType;
import com.jiuqi.nr.enumcheck.common.FormFieldWrapper;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnumCheckHelper {
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IRunTimeViewController viewCtrl;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;

    public FormFieldWrapper getFormField(String dataSchemeId, String entityId, FormDefine fm, DataLinkDefine dl) throws Exception {
        DataField dataFd;
        FormFieldWrapper result = null;
        if (dl == null || StringUtils.isEmpty((String)dl.getLinkExpression())) {
            return null;
        }
        if (dl.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
            FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
            fmdmAttributeDTO.setEntityId(entityId);
            fmdmAttributeDTO.setFormSchemeKey(fm.getFormScheme());
            fmdmAttributeDTO.setCode(dl.getLinkExpression());
            IFMDMAttribute fmAttr = this.fmdmAttributeService.queryByCode(fmdmAttributeDTO);
            if (null != fmAttr && StringUtils.isNotEmpty((String)fmAttr.getReferTableID())) {
                result = new FormFieldWrapper(dl, fmAttr);
                result.setTableName(this.entityMetaService.queryEntity(fmAttr.getEntityId()).getCode());
                IEntityDefine refEntity = this.entityMetaService.queryEntity(fmAttr.getReferEntityId());
                result.setRefTableName(refEntity.getCode());
                result.setRefEntity(refEntity);
                result.setIsEntityTable(true);
                result.setEnumFieldType(EnumFieldType.ENTITY_ENUM);
            }
        } else if ((dl.getType() == DataLinkType.DATA_LINK_TYPE_FIELD || dl.getType() == DataLinkType.DATA_LINK_TYPE_INFO) && null != (dataFd = this.runtimeDataSchemeService.getDataField(dl.getLinkExpression())) && StringUtils.isNotEmpty((String)dataFd.getRefDataEntityKey())) {
            result = new FormFieldWrapper(dl, dataFd);
            List fds = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataFd.getKey()});
            if (null != fds && fds.size() > 0) {
                DataFieldDeployInfo dfDeployInfo = (DataFieldDeployInfo)fds.get(0);
                result.setTableName(dfDeployInfo.getTableName());
                result.setRefEntity(this.metaService.queryEntity(dataFd.getRefDataEntityKey()));
                result.setRefTableName(result.getRefEntity().getCode());
                result.setIsEntityTable(false);
                if (dl.getType() == DataLinkType.DATA_LINK_TYPE_FIELD) {
                    result.setEnumFieldType(EnumFieldType.DATA_ENUM);
                } else {
                    result.setEnumFieldType(EnumFieldType.ENTITY_INFO_ENUM);
                }
            }
        }
        return result;
    }

    public List<FormFieldWrapper> getAllEnumOfForm(String dataSchemeId, String entityId, String formKey) throws Exception {
        ArrayList<FormFieldWrapper> result = new ArrayList<FormFieldWrapper>();
        FormDefine fm = this.viewCtrl.queryFormById(formKey);
        for (DataLinkDefine dl : this.viewCtrl.getAllLinksInForm(formKey)) {
            FormFieldWrapper fw;
            if (dl.getPosX() < 1 || dl.getPosY() < 1) continue;
            DataRegionDefine dataRegionDefine = this.viewCtrl.queryDataRegionDefine(dl.getRegionKey());
            if (dl.getPosY() > dataRegionDefine.getRegionBottom() || dl.getPosX() > dataRegionDefine.getRegionRight() || (fw = this.getFormField(dataSchemeId, entityId, fm, dl)) == null) continue;
            result.add(fw);
        }
        return result;
    }
}

