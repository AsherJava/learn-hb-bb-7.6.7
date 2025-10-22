/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.editor.Service.impl;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.nr.definition.editor.EditorParam;
import com.jiuqi.nr.definition.editor.LinkData;
import com.jiuqi.nr.definition.editor.Service.EditorStyleCustom;
import com.jiuqi.nr.definition.editor.StyleData;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultEditorDataService
implements EditorStyleCustom {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;

    @Override
    public String getKey() {
        return "default";
    }

    @Override
    public StyleData getEditData(EditorParam editorParam) throws Exception {
        System.out.println(this.getClass());
        StyleData formData = new StyleData();
        String formKey = "63442ba4-0862-4c7b-956f-31867640111a";
        List<DesignDataLinkDefine> allLinksInForm = this.nrDesignTimeController.getAllLinksInForm(formKey);
        ArrayList<LinkData> links = new ArrayList<LinkData>();
        HashMap<String, DesignTableDefine> dataTables = new HashMap<String, DesignTableDefine>();
        for (DesignDataLinkDefine link : allLinksInForm) {
            LinkData linkData = new LinkData();
            linkData.setCol(link.getColNum());
            linkData.setRow(link.getRowNum());
            linkData.setX(link.getPosX());
            linkData.setY(link.getPosY());
            linkData.setLinkExpression(link.getLinkExpression());
            linkData.setLinkType(link.getType().getValue());
            DesignFieldDefine fieldDefine = null;
            if (StringUtils.isNotEmpty((String)link.getLinkExpression())) {
                fieldDefine = this.nrDesignTimeController.queryFieldDefine(link.getLinkExpression());
            }
            if (fieldDefine != null) {
                if (dataTables.get(fieldDefine.getOwnerTableKey()) != null) {
                    linkData.setTableCode(((DesignTableDefine)dataTables.get(fieldDefine.getOwnerTableKey())).getCode());
                } else {
                    DesignTableDefine designTableDefine = this.nrDesignTimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                    if (null != designTableDefine) {
                        dataTables.put(designTableDefine.getKey(), designTableDefine);
                        linkData.setTableCode(designTableDefine.getCode());
                    }
                }
                linkData.setDatatype(fieldDefine.getType());
                linkData.setFieldcode(fieldDefine.getCode());
                linkData.setFieldtitle(fieldDefine.getTitle());
                if (StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) {
                    linkData.setReferField(fieldDefine.getEntityKey());
                }
            } else {
                linkData.setDatatype(FieldType.FIELD_TYPE_STRING);
                linkData.setFieldcode(link.getLinkExpression());
                linkData.setFieldtitle("");
                linkData.setTableCode("");
            }
            links.add(linkData);
        }
        Grid2Data griddata = this.getGridData(formKey);
        formData.setLinks(links);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)module);
        String result = mapper.writeValueAsString((Object)griddata);
        formData.setGriddata(result.getBytes());
        return formData;
    }

    public Grid2Data getGridData(String formKey) {
        DesignFormDefine formDefine = this.nrDesignTimeController.queryFormById(formKey);
        Grid2Data gridData = null;
        if (null != formDefine) {
            if (formDefine.getBinaryData() != null) {
                gridData = Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData());
            } else {
                gridData = new Grid2Data();
                gridData.setRowCount(10);
                gridData.setColumnCount(10);
            }
        }
        return gridData;
    }
}

