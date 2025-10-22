/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils
 *  com.jiuqi.nr.unit.treestore.enumeration.FieldDataType
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.EntityFiledInfo
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldSettingInfo
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldsSaveInfo
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldsUpdateInfo
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFiledInfo
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplaySchemeImpl
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.intf.FMDMDisplaySchemeService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.treeimpl.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils;
import com.jiuqi.nr.unit.treeimpl.web.service.ICaptionFieldsSettingService;
import com.jiuqi.nr.unit.treestore.enumeration.FieldDataType;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.EntityFiledInfo;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldSettingInfo;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldsSaveInfo;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldsUpdateInfo;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFiledInfo;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplaySchemeImpl;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.intf.FMDMDisplaySchemeService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CaptionFieldsSettingService
implements ICaptionFieldsSettingService {
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IRunTimeViewController formRtCtl;
    @Resource
    private FMDMDisplaySchemeService displaySchemeService;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;

    @Override
    public FMDMCaptionFieldSettingInfo inquireCaptionFields(String formSchemeKey) {
        FMDMCaptionFieldSettingInfo settings = new FMDMCaptionFieldSettingInfo();
        settings.setSysUser(NRSystemUtils.isSystemIdentity((String)NRSystemUtils.getCurrentUserId()));
        FormSchemeDefine formScheme = this.formRtCtl.getFormScheme(formSchemeKey);
        IEntityDefine entityDefine = this.getContextEntityDefine(formScheme);
        IEntityModel entityModel = this.metaService.getEntityModel(entityDefine.getId());
        FMDMDisplayScheme displayScheme = this.displaySchemeService.getCurrentDisplayScheme(formScheme.getKey(), entityDefine.getId());
        if (displayScheme == null) {
            settings.setCaptionFields(this.initList(this.contextWrapper.getFMDMShowAttribute(formScheme, entityDefine, IEntityQueryPloy.MAIN_DIM_QUERY), entityModel));
        } else {
            settings.setCaptionFields(this.updateList(this.contextWrapper.getFMDMShowAttribute(formScheme, entityDefine, IEntityQueryPloy.MAIN_DIM_QUERY), entityModel, displayScheme));
        }
        return settings;
    }

    private List<FMDMCaptionFiledInfo> initList(List<IFMDMAttribute> fmdmShowAttribute, IEntityModel entityModel) {
        ArrayList<FMDMCaptionFiledInfo> fields = new ArrayList<FMDMCaptionFiledInfo>();
        IEntityAttribute nameField = entityModel.getNameField();
        for (IFMDMAttribute attr : fmdmShowAttribute) {
            FMDMCaptionFiledInfo filedInfo = this.buildFiledInfo(attr);
            filedInfo.setChecked(nameField != null && attr.getID().equals(nameField.getID()));
            fields.add(filedInfo);
        }
        return fields;
    }

    private List<FMDMCaptionFiledInfo> updateList(List<IFMDMAttribute> fmdmShowAttribute, IEntityModel entityModel, FMDMDisplayScheme displayScheme) {
        ArrayList<FMDMCaptionFiledInfo> fields = new ArrayList<FMDMCaptionFiledInfo>();
        for (IFMDMAttribute attr : fmdmShowAttribute) {
            FMDMCaptionFiledInfo filedInfo = this.buildFiledInfo(attr);
            filedInfo.setChecked(this.isChecked(displayScheme, entityModel, attr));
            fields.add(filedInfo);
        }
        List captionFields = displayScheme.getFields();
        HashMap key2IndexMap = new HashMap();
        for (int i = 0; i < captionFields.size(); ++i) {
            key2IndexMap.put(captionFields.get(i), i);
        }
        FMDMCaptionFiledInfo[] arr = fields.toArray(new FMDMCaptionFiledInfo[0]);
        for (int i = 0; i < arr.length; ++i) {
            FMDMCaptionFiledInfo pre = null;
            int preIndex = 0;
            for (int j = 0; j < arr.length - i; ++j) {
                FMDMCaptionFiledInfo fdi = arr[j];
                if (fdi.isChecked() && null != pre) {
                    int beforeIndexAtOri = (Integer)key2IndexMap.get(pre.getKey());
                    int afterIndexAtOri = (Integer)key2IndexMap.get(fdi.getKey());
                    if (afterIndexAtOri < beforeIndexAtOri) {
                        FMDMCaptionFiledInfo temp = arr[j];
                        arr[j] = arr[preIndex];
                        arr[preIndex] = temp;
                    }
                    pre = arr[j];
                    preIndex = j;
                    continue;
                }
                if (!fdi.isChecked()) continue;
                pre = fdi;
                preIndex = j;
            }
        }
        return Arrays.asList(arr);
    }

    private boolean isChecked(FMDMDisplayScheme displayScheme, IEntityModel entityModel, IFMDMAttribute attr) {
        List fields = displayScheme.getFields();
        if (fields != null && !fields.isEmpty()) {
            return fields.contains(attr.getID());
        }
        IEntityAttribute nameField = entityModel.getNameField();
        if (nameField != null) {
            ((FMDMDisplaySchemeImpl)displayScheme).setFields(Collections.singletonList(nameField.getID()));
            return attr.getID().equals(nameField.getID());
        }
        return false;
    }

    @Override
    public int saveCaptionFieldsScheme(FMDMCaptionFieldsSaveInfo saveInfo) {
        return this.saveCaptionFieldsScheme(saveInfo, NRSystemUtils.getCurrentUserId());
    }

    @Override
    public int saveCaptionFieldsScheme(FMDMCaptionFieldsSaveInfo saveInfo, String currentUserId) {
        FormSchemeDefine formScheme = this.formRtCtl.getFormScheme(saveInfo.getFormSchemeKey());
        IEntityDefine entityDefine = this.getContextEntityDefine(formScheme);
        FMDMDisplayScheme displayScheme = this.displaySchemeService.findDisplayScheme(formScheme.getKey(), entityDefine.getId(), currentUserId);
        if (displayScheme != null) {
            FMDMDisplaySchemeImpl impl = new FMDMDisplaySchemeImpl();
            impl.setKey(displayScheme.getKey());
            impl.setFormScheme(displayScheme.getFormScheme());
            impl.setEntityId(entityDefine.getId());
            impl.setOwner(displayScheme.getOwner());
            impl.setFields(saveInfo.getCaptionFields());
            return this.displaySchemeService.updateFMDMDisplayScheme((FMDMDisplayScheme)impl);
        }
        FMDMDisplaySchemeImpl impl = new FMDMDisplaySchemeImpl();
        impl.setKey(UUID.randomUUID().toString());
        impl.setFormScheme(formScheme.getKey());
        impl.setEntityId(entityDefine.getId());
        impl.setOwner(currentUserId);
        impl.setFields(saveInfo.getCaptionFields());
        return this.displaySchemeService.saveFMDMDisplayScheme((FMDMDisplayScheme)impl);
    }

    public IEntityDefine getContextEntityDefine(FormSchemeDefine formScheme) {
        DsContext dsContext = DsContextHolder.getDsContext();
        if (StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
            return this.metaService.queryEntity(dsContext.getContextEntityId());
        }
        return this.metaService.queryEntity(formScheme.getDw());
    }

    @Override
    public int updateCaptionFieldsScheme(FMDMCaptionFieldsUpdateInfo updateInfo) {
        FMDMCaptionFieldSettingInfo captionScheme = this.inquireCaptionFields(updateInfo.getFormSchemeKey());
        List addCaptionFields = updateInfo.getAddCaptionFields();
        List delCaptionFields = updateInfo.getDelCaptionFields();
        List captionFields = captionScheme.getCaptionFields();
        captionFields.stream().filter(field -> addCaptionFields.contains(field.getKey())).forEach(field -> field.setChecked(addCaptionFields.contains(field.getKey())));
        captionFields.stream().filter(field -> delCaptionFields.contains(field.getKey())).forEach(field -> field.setChecked(!delCaptionFields.contains(field.getKey())));
        List captionFieldKeys = captionFields.stream().filter(FMDMCaptionFiledInfo::isChecked).map(EntityFiledInfo::getKey).collect(Collectors.toList());
        if (!captionFieldKeys.isEmpty() && captionFieldKeys.size() < 5) {
            FMDMCaptionFieldsSaveInfo saveInfo = new FMDMCaptionFieldsSaveInfo();
            saveInfo.setFormSchemeKey(updateInfo.getFormSchemeKey());
            saveInfo.setCaptionFields(captionFieldKeys);
            return this.saveCaptionFieldsScheme(saveInfo);
        }
        return 0;
    }

    private FMDMCaptionFiledInfo buildFiledInfo(IFMDMAttribute attr) {
        FMDMCaptionFiledInfo filedInfo = new FMDMCaptionFiledInfo();
        filedInfo.setKey(attr.getID());
        filedInfo.setCode(attr.getCode());
        filedInfo.setTitle(attr.getTitle());
        filedInfo.setDataType(FieldDataType.getName((ColumnModelType)attr.getColumnType()));
        return filedInfo;
    }

    @Override
    public List<EntityFiledInfo> querySortFields(String entityId) {
        ArrayList<EntityFiledInfo> filedInfos = new ArrayList<EntityFiledInfo>();
        IEntityModel entityModel = this.metaService.getEntityModel(entityId);
        List showFields = entityModel.getShowFields();
        for (IEntityAttribute showField : showFields) {
            EntityFiledInfo entityFiledInfo = new EntityFiledInfo();
            entityFiledInfo.setTitle(showField.getTitle());
            entityFiledInfo.setCode(showField.getCode());
            entityFiledInfo.setKey(showField.getID());
            filedInfos.add(entityFiledInfo);
        }
        return filedInfos;
    }
}

