/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.common.DataSchemeUtils
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormSchemeDefineGetterImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Lazy(value=false)
public class DataLinkHelper {
    private static IDesignDataSchemeService designDataSchemeService;
    private static IRuntimeDataSchemeService runtimeDataSchemeService;
    private static IRuntimeDataRegionService runtimeDataRegionService;
    private static IRuntimeFormService runtimeFormService;
    private static IRuntimeFormSchemeService runtimeFormSchemeService;
    private static DataModelService dataModelService;
    private static IEntityMetaService entityMetaService;

    @Autowired
    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        DataLinkHelper.entityMetaService = entityMetaService;
    }

    @Autowired
    public void setDesignDataSchemeService(IDesignDataSchemeService designDataSchemeService) {
        DataLinkHelper.designDataSchemeService = designDataSchemeService;
    }

    @Autowired
    public void setRuntimeDataSchemeService(IRuntimeDataSchemeService runtimeDataSchemeService) {
        DataLinkHelper.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    @Autowired
    public void setRuntimeDataRegionService(IRuntimeDataRegionService runtimeDataRegionService) {
        DataLinkHelper.runtimeDataRegionService = runtimeDataRegionService;
    }

    @Autowired
    public void setRuntimeFormService(IRuntimeFormService runtimeFormService) {
        DataLinkHelper.runtimeFormService = runtimeFormService;
    }

    @Autowired
    public void setRuntimeFormSchemeService(IRuntimeFormSchemeService runtimeFormSchemeService) {
        DataLinkHelper.runtimeFormSchemeService = runtimeFormSchemeService;
    }

    @Autowired
    public void setDataModelService(DataModelService dataModelService) {
        DataLinkHelper.dataModelService = dataModelService;
    }

    public static List<String> getValidationsStr(DesignDataLinkDefine link) {
        if (null == link) {
            return Collections.emptyList();
        }
        if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType() && DataLinkType.DATA_LINK_TYPE_INFO != link.getType()) {
            return Collections.emptyList();
        }
        String linkExpression = link.getLinkExpression();
        if (StringUtils.hasText(linkExpression)) {
            DesignDataField dataField = designDataSchemeService.getDataField(linkExpression);
            return DataSchemeUtils.getValidationsStr((DataField)dataField);
        }
        return Collections.emptyList();
    }

    public static List<String> getValidationsStr(DataLinkDefine link) {
        if (null == link) {
            return Collections.emptyList();
        }
        if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType() && DataLinkType.DATA_LINK_TYPE_INFO != link.getType()) {
            return Collections.emptyList();
        }
        String linkExpression = link.getLinkExpression();
        if (StringUtils.hasText(linkExpression)) {
            DataField dataField = runtimeDataSchemeService.getDataField(linkExpression);
            return DataSchemeUtils.getValidationsStr((DataField)dataField);
        }
        return Collections.emptyList();
    }

    public static List<String> getValidationRulesStr(DesignDataLinkDefine link) {
        if (null == link) {
            return Collections.emptyList();
        }
        if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType() && DataLinkType.DATA_LINK_TYPE_INFO != link.getType()) {
            return Collections.emptyList();
        }
        String linkExpression = link.getLinkExpression();
        if (StringUtils.hasText(linkExpression)) {
            DesignDataField dataField = designDataSchemeService.getDataField(linkExpression);
            return DataSchemeUtils.getValidationRulesStr((DataField)dataField);
        }
        return Collections.emptyList();
    }

    public static List<String> getValidationRulesStr(DataLinkDefine link) {
        if (null == link) {
            return Collections.emptyList();
        }
        if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType() && DataLinkType.DATA_LINK_TYPE_INFO != link.getType()) {
            return Collections.emptyList();
        }
        String linkExpression = link.getLinkExpression();
        if (StringUtils.hasText(linkExpression)) {
            DataField dataField = runtimeDataSchemeService.getDataField(linkExpression);
            return DataSchemeUtils.getValidationRulesStr((DataField)dataField);
        }
        return Collections.emptyList();
    }

    public static Boolean getAllowUndefinedCode(DataLinkDefine link) {
        if (link == null) {
            return false;
        }
        if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType() && DataLinkType.DATA_LINK_TYPE_INFO != link.getType()) {
            return true;
        }
        String linkExpression = link.getLinkExpression();
        if (StringUtils.hasText(linkExpression)) {
            DataField dataField = runtimeDataSchemeService.getDataField(linkExpression);
            if (dataField == null) {
                return true;
            }
            return dataField.isAllowUndefinedCode();
        }
        return true;
    }

    public static FormatProperties getFormatProperties(DataLinkDefine link) {
        if (link == null) {
            return null;
        }
        if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType() && DataLinkType.DATA_LINK_TYPE_INFO != link.getType()) {
            return null;
        }
        String linkExpression = link.getLinkExpression();
        if (StringUtils.hasText(linkExpression)) {
            DataField dataField = runtimeDataSchemeService.getDataField(linkExpression);
            if (dataField == null) {
                return null;
            }
            return dataField.getFormatProperties();
        }
        return null;
    }

    public static Boolean getAllowNullAble(DataLinkDefine link) {
        if (link == null) {
            return false;
        }
        if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType() && DataLinkType.DATA_LINK_TYPE_INFO != link.getType()) {
            return true;
        }
        String linkExpression = link.getLinkExpression();
        if (StringUtils.hasText(linkExpression)) {
            DataField dataField = runtimeDataSchemeService.getDataField(linkExpression);
            if (dataField == null) {
                return null;
            }
            return dataField.isNullable();
        }
        return true;
    }

    public static Boolean getAllowNullAble(String fieldTableId, String linkExpression) {
        if (StringUtils.hasText(linkExpression)) {
            ColumnModelDefine columnModelDefine = dataModelService.getColumnModelDefineByCode(fieldTableId, linkExpression);
            if (columnModelDefine == null) {
                return true;
            }
            return columnModelDefine.isNullAble();
        }
        return true;
    }

    public static String getFieldTableId(DataLinkDefine link) {
        String regionKey = link.getRegionKey();
        DataRegionDefine dataRegionDefine = runtimeDataRegionService.queryDataRegion(regionKey);
        String formKey = dataRegionDefine.getFormKey();
        FormDefine formDefine = runtimeFormService.queryForm(formKey);
        String formSchemeKey = formDefine.getFormScheme();
        FormSchemeDefine formScheme = runtimeFormSchemeService.getFormScheme(formSchemeKey);
        RunTimeFormSchemeDefineGetterImpl runTimeFormSchemeDefineGetter = new RunTimeFormSchemeDefineGetterImpl(formScheme);
        String dw = runTimeFormSchemeDefineGetter.getDw();
        TableModelDefine tableModel = entityMetaService.getTableModel(dw);
        return tableModel.getID();
    }

    public static boolean getAllowMultipleSelect(DataLinkDefine link) {
        if (link == null) {
            return false;
        }
        if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType() && DataLinkType.DATA_LINK_TYPE_INFO != link.getType()) {
            return false;
        }
        String linkExpression = link.getLinkExpression();
        if (StringUtils.hasText(linkExpression)) {
            DataField dataField = runtimeDataSchemeService.getDataField(linkExpression);
            if (dataField == null) {
                return false;
            }
            return dataField.isAllowMultipleSelect();
        }
        return false;
    }

    public static Boolean getAllowUndefinedCode(DesignDataLinkDefine link) {
        if (link == null) {
            return false;
        }
        if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType() && DataLinkType.DATA_LINK_TYPE_INFO != link.getType()) {
            return true;
        }
        String linkExpression = link.getLinkExpression();
        if (StringUtils.hasText(linkExpression)) {
            DesignDataField dataField = designDataSchemeService.getDataField(linkExpression);
            if (dataField == null) {
                return true;
            }
            return dataField.isAllowUndefinedCode();
        }
        return true;
    }

    public static Boolean getAllowNullAble(DesignDataLinkDefine link) {
        if (link == null) {
            return false;
        }
        if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType() && DataLinkType.DATA_LINK_TYPE_INFO != link.getType()) {
            return true;
        }
        String linkExpression = link.getLinkExpression();
        if (StringUtils.hasText(linkExpression)) {
            DesignDataField dataField = designDataSchemeService.getDataField(linkExpression);
            if (dataField == null) {
                return true;
            }
            return dataField.isNullable();
        }
        return true;
    }

    public static boolean getAllowMultipleSelect(DesignDataLinkDefine link) {
        if (link == null) {
            return false;
        }
        if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType() && DataLinkType.DATA_LINK_TYPE_INFO != link.getType()) {
            return false;
        }
        String linkExpression = link.getLinkExpression();
        if (StringUtils.hasText(linkExpression)) {
            DesignDataField dataField = designDataSchemeService.getDataField(linkExpression);
            if (dataField == null) {
                return false;
            }
            return dataField.isAllowMultipleSelect();
        }
        return false;
    }

    public static DataField getDataField(DataLinkDefine link) {
        if (null == link) {
            return null;
        }
        if (DataLinkType.DATA_LINK_TYPE_FIELD != link.getType() && DataLinkType.DATA_LINK_TYPE_INFO != link.getType()) {
            return null;
        }
        String linkExpression = link.getLinkExpression();
        if (StringUtils.hasText(linkExpression)) {
            return runtimeDataSchemeService.getDataField(linkExpression);
        }
        return null;
    }
}

