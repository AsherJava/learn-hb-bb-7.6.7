/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringHelper
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors;

import com.jiuqi.bi.util.StringHelper;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.form.common.FormExportVtEnum;
import com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors.AbstractFormExportCellExtractor;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

public class FieldTypeCellExtractor
extends AbstractFormExportCellExtractor {
    public static final FormExportVtEnum viewType = FormExportVtEnum.FIELD_TYPE;
    public static final Map<Integer, String> fileTypeMap = new HashMap<Integer, String>();
    public static final Map<Integer, String> fmdmFieldTypeMap;
    private IDesignTimeViewController designTimeViewController;
    private IEntityMetaService entityMetaService;

    public FieldTypeCellExtractor(IDesignTimeViewController designTimeViewController, IEntityMetaService entityMetaService) {
        this.designTimeViewController = designTimeViewController;
        this.entityMetaService = entityMetaService;
    }

    @Override
    public void setCellStyle(GridCellData gridCellData, XSSFCell cell, XSSFSheet sheet, XSSFWorkbook workbook) {
        this.setDefaultStyle(gridCellData, cell, sheet, workbook);
    }

    @Override
    public void setCellText(String showText, DesignDataLinkDefine dataLinkDefine, DesignFieldDefine fieldDefine, Cell cell) {
        String useShowText = showText;
        if (fieldDefine != null) {
            switch (fieldDefine.getValueType()) {
                case FIELD_VALUE_PARENT_UNIT: {
                    useShowText = "\u4e0a\u7ea7\u4ee3\u7801";
                    break;
                }
                case FIELD_VALUE_UNIT_CODE: {
                    useShowText = "\u5355\u4f4d\u4ee3\u7801";
                    break;
                }
                case FIELD_VALUE_UNIT_NAME: {
                    useShowText = "\u5355\u4f4d\u540d\u79f0";
                    break;
                }
                case FIELD_VALUE_UNIT_TYPE: {
                    useShowText = "\u5355\u4f4d\u7c7b\u578b";
                    break;
                }
                default: {
                    useShowText = StringHelper.notNull((String)fieldDefine.getEntityKey()) ? "\u4e0b\u62c9\u7c7b\u578b" : (fieldDefine.getType() == FieldType.FIELD_TYPE_DECIMAL ? fileTypeMap.get(fieldDefine.getType().getValue()) + "(" + fieldDefine.getSize() + "," + fieldDefine.getFractionDigits() + ")" : (fieldDefine.getType() == FieldType.FIELD_TYPE_INTEGER || fieldDefine.getType() == FieldType.FIELD_TYPE_TEXT ? fileTypeMap.get(fieldDefine.getType().getValue()) + "(" + fieldDefine.getSize() + ")" : (fileTypeMap.get(fieldDefine.getType().getValue()) != null ? fileTypeMap.get(fieldDefine.getType().getValue()) : "")));
                }
            }
            cell.setShowText(useShowText);
        }
        if (dataLinkDefine != null && DataLinkType.DATA_LINK_TYPE_FMDM.equals((Object)dataLinkDefine.getType())) {
            DesignDataRegionDefine dataRegion = this.designTimeViewController.getDataRegion(dataLinkDefine.getRegionKey());
            IEntityModel model = this.getModel(dataRegion.getFormKey(), this.designTimeViewController, this.entityMetaService);
            IEntityAttribute attribute = model.getAttribute(dataLinkDefine.getLinkExpression());
            cell.setShowText(this.getFmdmCellText(attribute));
        }
    }

    private String getFmdmCellText(IEntityAttribute attribute) {
        String showText = "";
        switch (attribute.getColumnType()) {
            case BIGDECIMAL: 
            case DOUBLE: {
                showText = fmdmFieldTypeMap.get(attribute.getColumnType().getValue()) + "(" + attribute.getPrecision() + "," + attribute.getDecimal() + ")";
                break;
            }
            case INTEGER: 
            case STRING: {
                if (StringUtils.hasText(attribute.getReferColumnID())) {
                    showText = "\u4e0b\u62c9\u7c7b\u578b";
                    break;
                }
                showText = fmdmFieldTypeMap.get(attribute.getColumnType().getValue()) + "(" + attribute.getPrecision() + ")";
                break;
            }
            default: {
                showText = fmdmFieldTypeMap.get(attribute.getColumnType().getValue());
            }
        }
        return showText;
    }

    static {
        fileTypeMap.put(0, "\u901a\u7528\u7c7b\u578b");
        fileTypeMap.put(1, "\u6d6e\u70b9\u578b");
        fileTypeMap.put(2, "\u5b57\u7b26\u578b");
        fileTypeMap.put(3, "\u6574\u6570\u7c7b\u578b");
        fileTypeMap.put(4, "\u5e03\u5c14\u7c7b\u578b");
        fileTypeMap.put(5, "\u65e5\u671f\u7c7b\u578b");
        fileTypeMap.put(19, "\u65f6\u95f4\u7c7b\u578b");
        fileTypeMap.put(6, "\u65e5\u671f\u65f6\u95f4\u7c7b\u578b");
        fileTypeMap.put(7, "UUID\u7c7b\u578b");
        fileTypeMap.put(8, "\u6570\u503c\u578b");
        fileTypeMap.put(9, "\u65f6\u95f4\u6233");
        fileTypeMap.put(16, "\u6587\u672c\u7c7b\u578b");
        fileTypeMap.put(17, "\u56fe\u7247\u7c7b\u578b");
        fileTypeMap.put(22, "\u9644\u4ef6\u7c7b\u578b");
        fmdmFieldTypeMap = new HashMap<Integer, String>();
        fmdmFieldTypeMap.put(1, "\u5e03\u5c14\u7c7b\u578b");
        fmdmFieldTypeMap.put(2, "\u65e5\u671f\u65f6\u95f4\u7c7b\u578b");
        fmdmFieldTypeMap.put(3, "\u6d6e\u70b9\u7c7b\u578b");
        fmdmFieldTypeMap.put(5, "\u6574\u6570\u7c7b\u578b");
        fmdmFieldTypeMap.put(6, "\u5b57\u7b26\u7c7b\u578b");
        fmdmFieldTypeMap.put(9, "\u4e8c\u8fdb\u5236\u7c7b\u578b");
        fmdmFieldTypeMap.put(10, "\u6570\u503c\u7c7b\u578b\u578b");
        fmdmFieldTypeMap.put(12, "UUID\u7c7b\u578b");
        fmdmFieldTypeMap.put(5006, "\u9644\u4ef6\u7c7b\u578b");
    }
}

