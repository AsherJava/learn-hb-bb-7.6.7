/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.common.DataLinkEditMode
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataLinkMappingDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.jtable.params.base.BooleanLinkData;
import com.jiuqi.nr.jtable.params.base.DateLinkData;
import com.jiuqi.nr.jtable.params.base.DateTimeLinkData;
import com.jiuqi.nr.jtable.params.base.DecimalLinkData;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.ErrorLinkData;
import com.jiuqi.nr.jtable.params.base.FileLinkData;
import com.jiuqi.nr.jtable.params.base.FloatLinkData;
import com.jiuqi.nr.jtable.params.base.FormulaLinkData;
import com.jiuqi.nr.jtable.params.base.IntgeterLinkData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.PictureLinkData;
import com.jiuqi.nr.jtable.params.base.StringLinkData;
import com.jiuqi.nr.jtable.params.base.TextLinkData;
import com.jiuqi.nr.jtable.params.base.TimeLinkData;
import com.jiuqi.nr.jtable.params.base.UUIDLinkData;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkDataFactory {
    private static final Logger logger = LoggerFactory.getLogger(LinkDataFactory.class);
    private static IRunTimeViewController runtimeController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
    private static IFMDMAttributeService fMDMAttributeService = (IFMDMAttributeService)BeanUtil.getBean(IFMDMAttributeService.class);

    public static LinkData linkData(DataLinkDefine dataLinkDefine) {
        String regionKey = dataLinkDefine.getRegionKey();
        DataRegionDefine dataRegionDefine = runtimeController.queryDataRegionDefine(regionKey);
        FormDefine form = runtimeController.queryFormById(dataRegionDefine.getFormKey());
        List queryDataLinkMapping = runtimeController.queryDataLinkMapping(form.getKey());
        FormSchemeDefine formscheme = runtimeController.getFormScheme(form.getFormScheme());
        return LinkDataFactory.linkData(formscheme, form, dataLinkDefine, queryDataLinkMapping);
    }

    public static LinkData linkData(FormSchemeDefine formscheme, FormDefine form, DataLinkDefine dataLinkDefine, List<DataLinkMappingDefine> queryDataLinkMapping) {
        String error = "";
        if (dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
            try {
                FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
                fmdmAttributeDTO.setEntityId(formscheme.getDw());
                fmdmAttributeDTO.setFormSchemeKey(formscheme.getKey());
                fmdmAttributeDTO.setAttributeCode(dataLinkDefine.getLinkExpression());
                fmdmAttributeDTO.setZBKey(dataLinkDefine.getLinkExpression());
                IFMDMAttribute fmdmAttribute = fMDMAttributeService.queryByZbKey(fmdmAttributeDTO);
                if (fmdmAttribute != null) {
                    return LinkDataFactory.fieldDefine(dataLinkDefine, fmdmAttribute, queryDataLinkMapping, form);
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        } else {
            if (dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FIELD || dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_INFO) {
                FieldDefine fieldDefine = null;
                try {
                    fieldDefine = runtimeController.queryFieldDefine(dataLinkDefine.getLinkExpression());
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                return LinkDataFactory.fieldDefine(dataLinkDefine, fieldDefine, queryDataLinkMapping, form);
            }
            if (dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FORMULA) {
                return new FormulaLinkData(dataLinkDefine, form);
            }
        }
        if (StringUtils.isEmpty((String)error)) {
            error = "\u5355\u5143\u683c (key:" + dataLinkDefine.getKey() + ";title:" + dataLinkDefine.getTitle() + "; \u5750\u6807[" + dataLinkDefine.getRowNum() + "," + dataLinkDefine.getColNum() + "])\u914d\u7f6e\u7684\u94fe\u63a5\u8868\u8fbe\u5f0f\u914d\u7f6e\u4e0d\u6b63\u786e, \u94fe\u63a5\u8868\u8fbe\u5f0f:" + dataLinkDefine.getLinkExpression();
            logger.error(error);
        }
        return new ErrorLinkData(dataLinkDefine, error);
    }

    private static String checkEnumLink(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        String linkInfo = "\u5355\u5143\u683c (key:" + dataLinkDefine.getKey() + ";title:" + dataLinkDefine.getTitle() + "; \u5750\u6807[" + dataLinkDefine.getRowNum() + "," + dataLinkDefine.getColNum() + "])\u914d\u7f6e\u7684";
        try {
            String pname;
            IEntityModel entityModel = entityMetaService.getEntityModel(fieldDefine.getEntityKey());
            Iterator attributes = entityModel.getAttributes();
            HashMap<String, IEntityAttribute> attributeMap = new HashMap<String, IEntityAttribute>();
            while (attributes.hasNext()) {
                IEntityAttribute attribute = (IEntityAttribute)attributes.next();
                attributeMap.put(attribute.getCode(), attribute);
            }
            if (StringUtils.isNotEmpty((String)dataLinkDefine.getEnumShowFullPath()) && !attributeMap.containsKey(pname = dataLinkDefine.getEnumShowFullPath())) {
                return linkInfo + "\u5168\u8def\u5f84\u6307\u6807\u672a\u627e\u5230:" + pname;
            }
            if (StringUtils.isNotEmpty((String)dataLinkDefine.getCaptionFieldsString())) {
                String[] captionFields;
                for (String captionField : captionFields = dataLinkDefine.getCaptionFieldsString().split(";")) {
                    if (attributeMap.containsKey(captionField)) continue;
                    return linkInfo + "\u663e\u793a\u6307\u6807\u672a\u627e\u5230:" + captionField;
                }
            }
            if (StringUtils.isNotEmpty((String)dataLinkDefine.getDropDownFieldsString())) {
                String[] dropDownFields;
                for (String dropDownField : dropDownFields = dataLinkDefine.getDropDownFieldsString().split(";")) {
                    if (attributeMap.containsKey(dropDownField)) continue;
                    return linkInfo + "\u4e0b\u62c9\u6307\u6807\u672a\u627e\u5230:" + dropDownField;
                }
            }
        }
        catch (Exception e) {
            return linkInfo + e.getMessage();
        }
        return "";
    }

    private static String checkEnumLink(DataLinkDefine dataLinkDefine, IFMDMAttribute fmdmAttribute) {
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        String linkInfo = "\u5355\u5143\u683c (key:" + dataLinkDefine.getKey() + ";title:" + dataLinkDefine.getTitle() + "; \u5750\u6807[" + dataLinkDefine.getRowNum() + "," + dataLinkDefine.getColNum() + "])\u914d\u7f6e\u7684";
        try {
            String pname;
            IEntityModel entityModel = entityMetaService.getEntityModel(fmdmAttribute.getReferEntityId());
            Iterator attributes = entityModel.getAttributes();
            HashMap<String, IEntityAttribute> attributeMap = new HashMap<String, IEntityAttribute>();
            while (attributes.hasNext()) {
                IEntityAttribute attribute = (IEntityAttribute)attributes.next();
                attributeMap.put(attribute.getCode(), attribute);
            }
            if (StringUtils.isNotEmpty((String)dataLinkDefine.getEnumShowFullPath()) && !attributeMap.containsKey(pname = dataLinkDefine.getEnumShowFullPath())) {
                return linkInfo + "\u5168\u8def\u5f84\u6307\u6807\u672a\u627e\u5230:" + pname;
            }
            if (StringUtils.isNotEmpty((String)dataLinkDefine.getCaptionFieldsString())) {
                String[] captionFields;
                for (String captionField : captionFields = dataLinkDefine.getCaptionFieldsString().split(";")) {
                    if (attributeMap.containsKey(captionField)) continue;
                    return linkInfo + "\u663e\u793a\u6307\u6807\u672a\u627e\u5230:" + captionField;
                }
            }
            if (StringUtils.isNotEmpty((String)dataLinkDefine.getDropDownFieldsString())) {
                String[] dropDownFields;
                for (String dropDownField : dropDownFields = dataLinkDefine.getDropDownFieldsString().split(";")) {
                    if (attributeMap.containsKey(dropDownField)) continue;
                    return linkInfo + "\u4e0b\u62c9\u6307\u6807\u672a\u627e\u5230:" + dropDownField;
                }
            }
        }
        catch (Exception e) {
            return linkInfo + e.getMessage();
        }
        return "";
    }

    public static LinkData fieldDefine(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine, List<DataLinkMappingDefine> queryDataLinkMapping, FormDefine formDefine) {
        String error = "";
        if (fieldDefine != null) {
            FieldType fieldType = fieldDefine.getType();
            switch (fieldType) {
                case FIELD_TYPE_FLOAT: {
                    return new FloatLinkData(dataLinkDefine, fieldDefine, formDefine);
                }
                case FIELD_TYPE_STRING: {
                    boolean linkInput;
                    DataLinkEditMode editMode = dataLinkDefine.getEditMode();
                    boolean bl = linkInput = editMode != null && editMode == DataLinkEditMode.DATA_LINK_INPUT;
                    if (StringUtils.isNotEmpty((String)fieldDefine.getEntityKey()) && !linkInput) {
                        String checkEnumLink = LinkDataFactory.checkEnumLink(dataLinkDefine, fieldDefine);
                        if (StringUtils.isNotEmpty((String)checkEnumLink)) {
                            error = checkEnumLink;
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + error);
                            break;
                        }
                        return new EnumLinkData(dataLinkDefine, fieldDefine, queryDataLinkMapping);
                    }
                    return new StringLinkData(dataLinkDefine, fieldDefine);
                }
                case FIELD_TYPE_TEXT: {
                    return new TextLinkData(dataLinkDefine, fieldDefine);
                }
                case FIELD_TYPE_INTEGER: {
                    return new IntgeterLinkData(dataLinkDefine, fieldDefine, formDefine);
                }
                case FIELD_TYPE_LOGIC: {
                    return new BooleanLinkData(dataLinkDefine, fieldDefine);
                }
                case FIELD_TYPE_DATE: {
                    return new DateLinkData(dataLinkDefine, fieldDefine);
                }
                case FIELD_TYPE_DATE_TIME: {
                    return new DateTimeLinkData(dataLinkDefine, fieldDefine);
                }
                case FIELD_TYPE_TIME: {
                    return new TimeLinkData(dataLinkDefine, fieldDefine);
                }
                case FIELD_TYPE_UUID: {
                    return new UUIDLinkData(dataLinkDefine, fieldDefine);
                }
                case FIELD_TYPE_DECIMAL: {
                    return new DecimalLinkData(dataLinkDefine, fieldDefine, formDefine);
                }
                case FIELD_TYPE_PICTURE: {
                    return new PictureLinkData(dataLinkDefine, fieldDefine);
                }
                case FIELD_TYPE_FILE: {
                    return new FileLinkData(dataLinkDefine, fieldDefine);
                }
            }
        }
        return null;
    }

    public static LinkData fieldDefine(DataLinkDefine dataLinkDefine, IFMDMAttribute fmdmAttribute, List<DataLinkMappingDefine> queryDataLinkMapping, FormDefine formDefine) {
        if (fmdmAttribute != null) {
            ColumnModelType columnModelType = fmdmAttribute.getColumnType();
            switch (columnModelType) {
                case DOUBLE: {
                    return new FloatLinkData(dataLinkDefine, (ColumnModelDefine)fmdmAttribute, formDefine);
                }
                case STRING: {
                    if (fmdmAttribute.getReferEntityId() != null) {
                        String error = LinkDataFactory.checkEnumLink(dataLinkDefine, fmdmAttribute);
                        if (StringUtils.isNotEmpty((String)error)) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + error);
                            break;
                        }
                        return new EnumLinkData(dataLinkDefine, fmdmAttribute, queryDataLinkMapping);
                    }
                    return new StringLinkData(dataLinkDefine, (ColumnModelDefine)fmdmAttribute);
                }
                case CLOB: 
                case BLOB: {
                    return new TextLinkData(dataLinkDefine, (ColumnModelDefine)fmdmAttribute);
                }
                case INTEGER: {
                    return new IntgeterLinkData(dataLinkDefine, (ColumnModelDefine)fmdmAttribute, formDefine);
                }
                case BOOLEAN: {
                    return new BooleanLinkData(dataLinkDefine, (ColumnModelDefine)fmdmAttribute);
                }
                case DATETIME: {
                    return new DateLinkData(dataLinkDefine, (ColumnModelDefine)fmdmAttribute);
                }
                case UUID: {
                    return new UUIDLinkData(dataLinkDefine, (ColumnModelDefine)fmdmAttribute);
                }
                case BIGDECIMAL: {
                    return new DecimalLinkData(dataLinkDefine, (ColumnModelDefine)fmdmAttribute, formDefine);
                }
                case ATTACHMENT: {
                    return new FileLinkData(dataLinkDefine, (ColumnModelDefine)fmdmAttribute);
                }
            }
        }
        return null;
    }
}

