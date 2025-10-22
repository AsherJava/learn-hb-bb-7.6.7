/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.definition.util.ExtentStyle
 *  com.jiuqi.nr.definition.util.LineProp
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.util.ExtentStyle;
import com.jiuqi.nr.definition.util.LineProp;
import com.jiuqi.nr.designer.helper.CommonHelper;
import com.jiuqi.nr.designer.service.ExtentStyleService;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtentStyleServiceImpl
implements ExtentStyleService {
    private static final Logger log = LoggerFactory.getLogger(ExtentStyleServiceImpl.class);
    @Autowired
    private IDesignTimeViewController nrDesignController;
    @Autowired
    private DesignFormDefineService designFormDefineService;
    @Autowired
    private CommonHelper commonHelper;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionDesignTimeController designTimeController;

    @Override
    public ExtentStyle getFormStyle(String formKey, List<IEntityRow> queryItemData, String regionKey, String fieldKey) {
        Map<String, Integer> propMap = this.getRegionProps(regionKey, formKey, fieldKey);
        int begionRow = propMap.get("begionRow");
        int count = propMap.get("regionCount");
        int posX = propMap.get("posX");
        DesignFormDefine formDefine = this.nrDesignController.queryFormById(formKey);
        ArrayList<LineProp> lineProps = new ArrayList<LineProp>();
        Grid2Data gridData = null;
        if (null != formDefine && formDefine.getBinaryData() != null) {
            GridCellData gridCellData;
            gridData = Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData());
            gridData.insertRows(begionRow, queryItemData.size(), begionRow, true);
            gridData.deleteRows(begionRow + queryItemData.size(), count);
            for (int i = 0; i < queryItemData.size(); ++i) {
                for (int j = 1; j < gridData.getColumnCount(); ++j) {
                    gridCellData = gridData.getGridCellData(posX, begionRow + i);
                    if (j == posX) {
                        gridData.setRowHeight(begionRow + i, 30);
                        gridData.setRowAutoHeight(begionRow + i, true);
                        gridCellData.setShowText(queryItemData.get(i).getTitle());
                        gridCellData.setHorzAlign(3);
                        gridCellData.setEditText(queryItemData.get(i).getTitle());
                    }
                    gridCellData.setEditable(true);
                    gridCellData.setCheckable(true);
                }
                LineProp lineProp = new LineProp();
                lineProp.setRowNumber(begionRow + i);
                lineProp.setDataBaseKey(queryItemData.get(i).getEntityKeyData());
                lineProps.add(lineProp);
            }
            for (int rowIndex = 1; rowIndex < gridData.getRowCount(); ++rowIndex) {
                for (int colIndex = 1; colIndex < gridData.getColumnCount(); ++colIndex) {
                    if (rowIndex >= begionRow && rowIndex < begionRow + queryItemData.size()) continue;
                    gridCellData = gridData.getGridCellData(colIndex, rowIndex);
                    gridCellData.setSelectable(false);
                    gridCellData.setEditable(false);
                }
            }
        }
        ExtentStyle extentStyle = new ExtentStyle();
        extentStyle.setLineProps(lineProps);
        gridData.setHeaderRowCount(1);
        extentStyle.setGriddata(gridData);
        return extentStyle;
    }

    @Override
    public void saveEntityStyle(byte[] bytes, String regionKey, String code) {
        DesignDataRegionDefine region = this.nrDesignController.queryDataRegionDefine(regionKey);
        String regionSettingKey = region.getRegionSettingKey();
        try {
            this.designFormDefineService.updateBigDataDefine(regionSettingKey, code, bytes);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public ExtentStyle getFormStyle(String formKey, List<IEntityRow> queryItemData, byte[] bigData, String regionKey, String fieldKey) throws Exception {
        Map<String, Integer> propMap = this.getRegionProps(regionKey, formKey, fieldKey);
        int beginRow = propMap.get("begionRow");
        int count = propMap.get("regionCount");
        ExtentStyle extentStyle = ExtentStyle.bytesToTaskFlowsData((byte[])bigData);
        DesignFormDefine formDefine = this.nrDesignController.queryFormById(formKey);
        Grid2Data extentGrid = extentStyle.getGriddata();
        Grid2Data gridData = Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData());
        List keyList = queryItemData.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        List lineProps = extentStyle.getLineProps();
        Iterator iterator = lineProps.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            LineProp prop = (LineProp)iterator.next();
            if (!"".equals(prop.getDataBaseKey()) && !keyList.contains(prop.getDataBaseKey())) {
                extentGrid.deleteRows(index + 1, 1);
                iterator.remove();
                continue;
            }
            ++index;
        }
        gridData.deleteRows(beginRow, count);
        gridData.insertRows(beginRow, lineProps.size());
        for (int rowIndex = 1; rowIndex < gridData.getRowCount(); ++rowIndex) {
            for (int colIndex = 1; colIndex < gridData.getColumnCount(); ++colIndex) {
                if (rowIndex >= beginRow && rowIndex < beginRow + lineProps.size()) continue;
                GridCellData gridCellData = gridData.getGridCellData(colIndex, rowIndex);
                gridCellData.setSelectable(false);
                gridCellData.setEditable(false);
            }
        }
        gridData.copyFrom(extentGrid, 1, 1, extentGrid.getColumnCount() - 1, lineProps.size(), 1, beginRow);
        for (int i = 0; i < lineProps.size(); ++i) {
            int poxY = beginRow + i;
            int height = extentGrid.getRowHeight(i + 1);
            gridData.setRowHeight(poxY, height);
        }
        extentStyle.setGriddata(gridData);
        return extentStyle;
    }

    private Map<String, Integer> getRegionProps(String regionKey, String formKey, String fieldKey) {
        Optional<DesignDataLinkDefine> findFirst;
        List linksInFormByField;
        HashMap<String, Integer> propMap = new HashMap<String, Integer>();
        DesignDataRegionDefine region = this.nrDesignController.queryDataRegionDefine(regionKey);
        if (null != formKey && (linksInFormByField = this.nrDesignController.getLinksInFormByField(formKey, fieldKey)).size() != 0 && (findFirst = linksInFormByField.stream().filter(e -> e.getRegionKey().equals(regionKey)).findFirst()).isPresent()) {
            int posX = findFirst.get().getPosX();
            propMap.put("posX", posX);
        }
        int begionRow = region.getRegionTop();
        propMap.put("begionRow", begionRow);
        int regionBottom = region.getRegionBottom();
        int count = regionBottom - begionRow == 0 ? 1 : regionBottom - begionRow;
        propMap.put("regionCount", count);
        return propMap;
    }

    @Override
    public String getEntityKey(String regionKey, String formKey, String fieldKey) {
        try {
            DesignFieldDefine designFieldDefine = this.designTimeController.queryFieldDefine(fieldKey);
            if (designFieldDefine == null) {
                return null;
            }
            return designFieldDefine.getEntityKey();
        }
        catch (JQException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<IEntityRow> queryItemData(String entityId) throws JQException {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId));
        entityQuery.setIgnoreViewFilter(true);
        entityQuery.setAuthorityOperations(AuthorityType.None);
        IContext executorContext = null;
        ArrayList<IEntityRow> allRows = new ArrayList();
        try {
            IEntityTable executeReader = entityQuery.executeReader(executorContext);
            allRows = executeReader.getAllRows();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return allRows;
    }
}

