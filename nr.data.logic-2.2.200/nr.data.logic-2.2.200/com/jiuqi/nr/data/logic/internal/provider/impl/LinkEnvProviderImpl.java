/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.common.EnumTransUtils
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.data.logic.internal.provider.impl;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.logic.internal.obj.LinkEnv;
import com.jiuqi.nr.data.logic.internal.provider.LinkEnvProvider;
import com.jiuqi.nr.datascheme.api.common.EnumTransUtils;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkEnvProviderImpl
implements LinkEnvProvider {
    private final Map<String, LinkEnv> cache = new HashMap<String, LinkEnv>();
    private final IRunTimeViewController runtimeViewController;
    private final IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private final IFMDMAttributeService fMDMAttributeService;
    private static final Logger logger = LoggerFactory.getLogger(LinkEnvProviderImpl.class);

    public LinkEnvProviderImpl(IRunTimeViewController runtimeViewController, IDataDefinitionRuntimeController dataDefinitionRuntimeController, IFMDMAttributeService fMDMAttributeService) {
        this.runtimeViewController = runtimeViewController;
        this.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
        this.fMDMAttributeService = fMDMAttributeService;
    }

    @Override
    public LinkEnv get(String dataLinkKey) {
        if (this.cache.containsKey(dataLinkKey)) {
            return this.cache.get(dataLinkKey);
        }
        LinkEnv linkEnv = new LinkEnv(dataLinkKey);
        DataLinkDefine dataLinkDefine = this.runtimeViewController.queryDataLinkDefine(dataLinkKey);
        if (dataLinkDefine != null) {
            DataRegionDefine regionDefine = this.runtimeViewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
            linkEnv.setRegionKey(regionDefine.getKey());
            FormDefine formDefine = this.runtimeViewController.queryFormById(regionDefine.getFormKey());
            if (formDefine != null) {
                linkEnv.setFormKey(formDefine.getKey());
                linkEnv.setFormTitle(formDefine.getTitle());
            } else {
                logger.error("\u62a5\u8868{}\u4e0d\u5b58\u5728", (Object)regionDefine.getFormKey());
            }
            if (dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FIELD) {
                try {
                    FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(dataLinkDefine.getLinkExpression());
                    linkEnv.setFieldKey(fieldDefine.getKey());
                    linkEnv.setFieldTitle(fieldDefine.getTitle());
                    linkEnv.setFieldType(fieldDefine.getType().name());
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            } else if (DataLinkType.DATA_LINK_TYPE_FMDM == dataLinkDefine.getType()) {
                try {
                    assert (formDefine != null) : "\u62a5\u8868\u4e0d\u5b58\u5728\uff01";
                    List formScheme = this.runtimeViewController.queryFormSchemeByForm(formDefine.getKey());
                    TaskDefine taskDefine = this.runtimeViewController.queryTaskDefine(((FormSchemeDefine)formScheme.get(0)).getTaskKey());
                    FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
                    fmdmAttributeDTO.setEntityId(taskDefine.getDw());
                    fmdmAttributeDTO.setFormSchemeKey(((FormSchemeDefine)formScheme.get(0)).getKey());
                    fmdmAttributeDTO.setAttributeCode(dataLinkDefine.getLinkExpression());
                    IFMDMAttribute queryByCode = this.fMDMAttributeService.queryByCode(fmdmAttributeDTO);
                    if (queryByCode != null) {
                        linkEnv.setFieldKey(queryByCode.getID());
                        linkEnv.setFieldTitle(queryByCode.getTitle());
                        linkEnv.setFieldType(EnumTransUtils.valueOf((ColumnModelType)queryByCode.getColumnType()).name());
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        this.cache.put(dataLinkKey, linkEnv);
        return linkEnv;
    }
}

