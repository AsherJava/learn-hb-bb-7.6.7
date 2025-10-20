/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.query.sql.vo.QueryGroupField
 *  com.jiuqi.va.query.template.enumerate.AlignEnum
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 *  com.jiuqi.va.query.template.enumerate.PluginEnum
 *  com.jiuqi.va.query.template.plugin.QueryFieldsPlugin
 *  com.jiuqi.va.query.template.plugin.ToolBarPlugin
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO
 *  com.jiuqi.va.query.tree.vo.TableHeaderVO
 */
package com.jiuqi.va.query.util.export;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.sql.dto.QueryExportGroupStyleEnum;
import com.jiuqi.va.query.sql.vo.QueryGroupField;
import com.jiuqi.va.query.template.enumerate.AlignEnum;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.template.enumerate.PluginEnum;
import com.jiuqi.va.query.template.plugin.QueryFieldsPlugin;
import com.jiuqi.va.query.template.plugin.ToolBarPlugin;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO;
import com.jiuqi.va.query.tree.vo.TableHeaderVO;
import com.jiuqi.va.query.util.VAQueryI18nUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public final class DCQueryExportUtils {
    private DCQueryExportUtils() {
    }

    public static QueryExportGroupStyleEnum getGroupStyle(QueryTemplate template, String actionID) {
        QueryExportGroupStyleEnum mergeCellByRow = QueryExportGroupStyleEnum.valueOf(QueryExportGroupStyleEnum.MERGE_CELL_BY_ROW.name());
        if (Objects.isNull(template)) {
            return mergeCellByRow;
        }
        ToolBarPlugin toolBarPlugin = (ToolBarPlugin)template.getPluginByClass(ToolBarPlugin.class);
        if (Objects.isNull(toolBarPlugin)) {
            return mergeCellByRow;
        }
        TemplateToolbarInfoVO toolbarInfo = StringUtils.hasText(actionID) ? (TemplateToolbarInfoVO)Optional.ofNullable(toolBarPlugin.getTools()).orElse(Collections.emptyList()).stream().filter(x -> actionID.equals(x.getId())).findAny().orElse(null) : (TemplateToolbarInfoVO)toolBarPlugin.getTools().stream().filter(tool -> "export".equals(tool.getAction())).findAny().orElse(null);
        if (Objects.isNull(toolbarInfo)) {
            return mergeCellByRow;
        }
        String config = toolbarInfo.getConfig();
        if (!StringUtils.hasText(config)) {
            return mergeCellByRow;
        }
        Map map = JSONUtil.parseMap((String)config);
        String groupStyle = (String)map.get("groupStyle");
        if (StringUtils.hasText(groupStyle)) {
            return QueryExportGroupStyleEnum.valueOf(groupStyle);
        }
        return mergeCellByRow;
    }

    public static List<TemplateFieldSettingVO> sortWithGroupColumn(List<TemplateFieldSettingVO> tableHeaderVOList, List<QueryGroupField> groupFields) {
        if (CollectionUtils.isEmpty(groupFields) || CollectionUtils.isEmpty(tableHeaderVOList)) {
            return tableHeaderVOList;
        }
        ArrayList<TemplateFieldSettingVO> columnListTemp = new ArrayList<TemplateFieldSettingVO>();
        for (QueryGroupField groupField : groupFields) {
            for (TemplateFieldSettingVO column : tableHeaderVOList) {
                if (!column.getName().equals(groupField.getFieldName())) continue;
                columnListTemp.add(column);
            }
        }
        for (TemplateFieldSettingVO column : tableHeaderVOList) {
            String fieldName = column.getName();
            boolean flag = false;
            for (TemplateFieldSettingVO tempColumn : columnListTemp) {
                String tempFieldName = tempColumn.getName();
                if (!tempFieldName.equals(fieldName)) continue;
                flag = true;
                break;
            }
            if (flag) continue;
            columnListTemp.add(column);
        }
        return columnListTemp;
    }

    public static List<TemplateFieldSettingVO> getExportFields(QueryTemplate template, List<QueryGroupField> groupFields) {
        List<TemplateFieldSettingVO> fields = ((QueryFieldsPlugin)template.getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields();
        fields = DCQueryExportUtils.sortWithGroupColumn(fields, groupFields);
        return fields;
    }

    public static TableHeaderVO getExcelIndexHeader() {
        TableHeaderVO tableHeaderVO = new TableHeaderVO(AlignEnum.CENTER.getName(), "excel_index", VAQueryI18nUtil.getMessage("va.query.excelIndex"), "30px", new ArrayList());
        TemplateFieldSettingVO settingVO = new TemplateFieldSettingVO();
        settingVO.setDataType(ParamTypeEnum.STRING.getTypeName());
        settingVO.setAlign(AlignEnum.CENTER.getName());
        tableHeaderVO.setFieldSetting(settingVO);
        return tableHeaderVO;
    }
}

