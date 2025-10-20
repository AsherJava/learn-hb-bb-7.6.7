/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.nr.definition.common.DataLinkEditMode
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.EnumDisplayMode
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.excel.importexcel;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.excel.importexcel.cache.ExcelImportContext;
import com.jiuqi.nr.designer.excel.importexcel.util.FormHelper;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegionObjImportHelper {
    @Autowired
    private FormHelper formHelper;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    private static final Logger logger = LoggerFactory.getLogger(RegionObjImportHelper.class);
    private static final String REGION_DEFAUL_SIMPLE = "\u56fa\u5b9a\u533a\u57df";
    private static final String REGION_DEFAUL_ROW_LIST = "\u884c\u6d6e\u52a8\u533a\u57df";
    private static final String REGION_DEFAUL_COLUMN_LIST = "\u5217\u6d6e\u52a8\u533a\u57df";
    private static final String BIZKEYORDER = "BIZKEYORDER";
    private static final String FIELDFLOATORDER = "FLOATORDER";

    public DesignDataRegionDefine getRegionByPos(int col, int row, ExcelImportContext importContext) {
        List<DesignDataRegionDefine> regoinDataList = importContext.getRegoinData();
        if (!this.formHelper.listIsEmpty(regoinDataList)) {
            for (DesignDataRegionDefine designDataRegionDefine : regoinDataList) {
                if (designDataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE || !this.contains(col, row, designDataRegionDefine)) continue;
                return designDataRegionDefine;
            }
            for (DesignDataRegionDefine designDataRegionDefine : regoinDataList) {
                if (designDataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE || !this.contains(col, row, designDataRegionDefine)) continue;
                return designDataRegionDefine;
            }
        }
        return null;
    }

    public DesignDataRegionDefine createDefaultRegion(int left, int top, int right, int bottom, ExcelImportContext importContext) {
        List<DesignDataRegionDefine> regoinDataList;
        if (left > right || top > bottom || left < 0 || top < 0 || right < 0 || bottom < 0) {
            logger.error("\u6784\u9020Region\u53c2\u6570\u6709\u8bef");
        }
        if (!this.formHelper.listIsEmpty(regoinDataList = importContext.getRegoinData())) {
            for (DesignDataRegionDefine designDataRegionDefine : regoinDataList) {
                if (designDataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE || designDataRegionDefine.getRegionLeft() == left && designDataRegionDefine.getRegionTop() == top && designDataRegionDefine.getRegionRight() == right && designDataRegionDefine.getRegionBottom() == bottom) continue;
                designDataRegionDefine.setRegionLeft(left);
                designDataRegionDefine.setRegionTop(top);
                designDataRegionDefine.setRegionRight(right);
                designDataRegionDefine.setRegionBottom(bottom);
                return designDataRegionDefine;
            }
        }
        DesignDataRegionDefine designDataRegionDefine = this.nrDesignTimeController.createDataRegionDefine();
        designDataRegionDefine.setRegionLeft(left);
        designDataRegionDefine.setRegionTop(top);
        designDataRegionDefine.setRegionRight(right);
        designDataRegionDefine.setRegionBottom(bottom);
        designDataRegionDefine.setRegionKind(DataRegionKind.DATA_REGION_SIMPLE);
        designDataRegionDefine.setFormKey(importContext.getFormKey());
        designDataRegionDefine.setTitle(this.getRegionTitle(designDataRegionDefine.getRegionKind(), importContext));
        regoinDataList.add(designDataRegionDefine);
        return designDataRegionDefine;
    }

    public void setUserDefinedFloatRegion(int row, int height, ExcelImportContext importContext) {
        importContext.getGrid2Data();
        Grid2Data grid2Data = importContext.getGrid2Data();
        int rowCount = grid2Data.getRowCount();
        int colCount = grid2Data.getColumnCount();
        this.setFloatRegionDefaultValueIsAvailable(row);
        this.setFloatRegionByPos(1, colCount, row, height, true, colCount, rowCount, importContext);
    }

    private DesignDataRegionDefine setFloatRegionByPos(int x, int width, int y, int height, boolean rowList, int colCount, int rowCount, ExcelImportContext importContext) {
        int tmpTop = y;
        int tmpLeft = x;
        int tmpRight = x + width - 1;
        int tmpBottom = y + height - 1;
        DataRegionKind regKind = null;
        DesignFormDefine designFormDefine = importContext.getDesignFormDefine();
        List<DesignDataRegionDefine> regoinDataList = importContext.getRegoinData();
        if (rowList) {
            tmpLeft = 1;
            tmpRight = colCount;
            regKind = DataRegionKind.DATA_REGION_ROW_LIST;
        } else {
            tmpTop = 1;
            tmpBottom = rowCount;
            regKind = DataRegionKind.DATA_REGION_COLUMN_LIST;
        }
        DesignDataRegionDefine formDefaultRegion = this.getDefaultRegion(importContext);
        if (null == formDefaultRegion) {
            formDefaultRegion = this.createDefaultRegion(1, 1, colCount - 1, rowCount - 1, importContext);
        }
        DesignDataRegionDefine existedRegion = this.nrDesignTimeController.createDataRegionDefine();
        existedRegion.setFormKey(importContext.getFormKey());
        if (rowList) {
            existedRegion.setRegionLeft(1);
            existedRegion.setRegionTop(y);
            existedRegion.setRegionRight(colCount - 1);
            existedRegion.setRegionBottom(y + height - 1);
            existedRegion.setRegionKind(DataRegionKind.DATA_REGION_ROW_LIST);
            existedRegion.setTitle(designFormDefine.getTitle() + REGION_DEFAUL_ROW_LIST);
        } else {
            existedRegion.setRegionLeft(x);
            existedRegion.setRegionTop(1);
            existedRegion.setRegionRight(x + width - 1);
            existedRegion.setRegionBottom(rowCount - 1);
            existedRegion.setRegionKind(DataRegionKind.DATA_REGION_COLUMN_LIST);
            existedRegion.setTitle(designFormDefine.getTitle() + REGION_DEFAUL_COLUMN_LIST);
        }
        regoinDataList.add(existedRegion);
        existedRegion.setAllowDuplicateKey(true);
        existedRegion.setMaxRowCount(0);
        existedRegion.setRowsInFloatRegion(1);
        existedRegion.setCanInsertRow(true);
        return existedRegion;
    }

    private DesignDataRegionDefine getDefaultRegion(ExcelImportContext importContext) {
        List<DesignDataRegionDefine> regoinDataList = importContext.getRegoinData();
        if (!this.formHelper.listIsEmpty(regoinDataList)) {
            for (DesignDataRegionDefine designDataRegionDefine : regoinDataList) {
                if (designDataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) continue;
                return designDataRegionDefine;
            }
        }
        return null;
    }

    private void setFloatRegionDefaultValueIsAvailable(int row) {
    }

    public DesignDataLinkDefine getLinkByPos(int col, int row, ExcelImportContext importContext) {
        if (!this.formHelper.mapIsEmpty(importContext.getLinkData())) {
            for (Map<String, DesignDataLinkDefine> designDataLinkDefineMap : importContext.getLinkData().values()) {
                for (DesignDataLinkDefine designDataLinkDefine : designDataLinkDefineMap.values()) {
                    if (designDataLinkDefine.getPosX() != col || designDataLinkDefine.getPosY() != row) continue;
                    return designDataLinkDefine;
                }
            }
        }
        return null;
    }

    public DesignFieldDefine getFieldByKey(String regionKey, String fieldKey, ExcelImportContext importContext) {
        Map<String, DesignTableDefine> designTableDefineMap = importContext.getTableData().get(regionKey);
        Iterator<String> iterator = designTableDefineMap.keySet().iterator();
        if (iterator.hasNext()) {
            String tableKey = iterator.next();
            Map<String, DesignFieldDefine> designFieldDefineMap = importContext.getFieldData().get(tableKey);
            return designFieldDefineMap.get(fieldKey);
        }
        return null;
    }

    public DesignDataLinkDefine createLinkByPos(int col, int row, String fieldKey, String regionKey, ExcelImportContext importContext) {
        DesignDataLinkDefine designDataLinkDefine = this.nrDesignTimeController.createDataLinkDefine();
        String tmpCode = this.formHelper.newUniqueCode();
        while (this.formHelper.checkUniqueCodeExist(tmpCode)) {
            tmpCode = this.formHelper.newUniqueCode();
        }
        designDataLinkDefine.setUniqueCode(tmpCode);
        designDataLinkDefine.setPosX(col);
        designDataLinkDefine.setPosY(row);
        designDataLinkDefine.setColNum(col);
        designDataLinkDefine.setRowNum(row);
        designDataLinkDefine.setLinkExpression(fieldKey);
        designDataLinkDefine.setRegionKey(regionKey);
        designDataLinkDefine.setDisplayMode(EnumDisplayMode.DISPLAY_MODE_DEFAULT);
        designDataLinkDefine.setEditMode(DataLinkEditMode.DATA_LINK_NONE);
        Map<String, DesignDataLinkDefine> designDataLinkDefineMap = importContext.getLinkData().get(regionKey);
        if (designDataLinkDefineMap == null) {
            designDataLinkDefineMap = new HashMap<String, DesignDataLinkDefine>();
            importContext.getLinkData().put(regionKey, designDataLinkDefineMap);
        }
        designDataLinkDefineMap.put(designDataLinkDefine.getKey(), designDataLinkDefine);
        return designDataLinkDefine;
    }

    private boolean contains(int col, int row, DesignDataRegionDefine designDataRegionDefine) {
        return designDataRegionDefine.getRegionLeft() <= col && designDataRegionDefine.getRegionRight() >= col && designDataRegionDefine.getRegionTop() <= row && designDataRegionDefine.getRegionBottom() >= row;
    }

    private String getRegionTitle(DataRegionKind dataRegionKind, ExcelImportContext importContext) {
        DesignFormDefine designFormDefine = importContext.getDesignFormDefine();
        String newRegionTitle = designFormDefine.getTitle() + (dataRegionKind == DataRegionKind.DATA_REGION_SIMPLE ? REGION_DEFAUL_SIMPLE : (dataRegionKind == DataRegionKind.DATA_REGION_ROW_LIST ? REGION_DEFAUL_ROW_LIST : REGION_DEFAUL_COLUMN_LIST));
        return newRegionTitle;
    }

    private Map<String, String> initRegionDefine(DesignDataRegionDefine designDataRegionDefine, ExcelImportContext importContext) throws JQException {
        HashMap<String, String> attributeMap = new HashMap<String, String>();
        if (designDataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
            attributeMap.put("floatID", this.queryAndCreateFloatID(designDataRegionDefine, importContext));
            attributeMap.put("BizKeyFieldsID", "");
            attributeMap.put("bizKeyOrderID", UUIDUtils.getKey().toString());
        }
        return attributeMap;
    }

    private String queryAndCreateFloatID(DesignDataRegionDefine designDataRegionDefine, ExcelImportContext importContext) throws JQException {
        String floatID = null;
        Map<String, DesignTableDefine> designTableDefineMap = importContext.getTableData().get(designDataRegionDefine.getKey());
        if (!this.formHelper.mapIsEmpty(designTableDefineMap)) {
            block2: for (DesignTableDefine designTableDefine : designTableDefineMap.values()) {
                try {
                    List fields = this.nrDesignTimeController.getAllFieldsInTable(designTableDefine.getKey());
                    if (this.formHelper.listIsEmpty(fields)) continue;
                    for (DesignFieldDefine field : fields) {
                        if (field.getValueType() != FieldValueType.FIELD_VALUE_INPUT_ORDER) continue;
                        floatID = field.getKey();
                        continue block2;
                    }
                }
                catch (Exception e) {
                    throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_004, (Throwable)e);
                }
            }
        }
        if (floatID == null) {
            floatID = UUIDUtils.getKey();
        }
        return floatID;
    }

    private Map<String, DesignFieldDefine> getAllFieldsByTableKey(String tableKey, ExcelImportContext importContext) {
        return importContext.getFieldData().get(tableKey);
    }
}

