/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.service.DesignBigDataService
 *  com.jiuqi.nr.definition.paramlanguage.service.I18nDeployService
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.task.i18n.service.impl;

import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.definition.paramlanguage.service.I18nDeployService;
import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import com.jiuqi.nr.task.i18n.bean.I18nColsObj;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nFormSaveVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendResultVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nResultVO;
import com.jiuqi.nr.task.i18n.common.I18nResourceType;
import com.jiuqi.nr.task.i18n.exception.I18nException;
import com.jiuqi.nr.task.i18n.factory.I18nWorkShopFactory;
import com.jiuqi.nr.task.i18n.provider.I18nServiceProvider;
import com.jiuqi.nr.task.i18n.service.I18nService;
import com.jiuqi.nr.task.i18n.workshop.I18nWorkShop;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class I18nServiceImpl
implements I18nService {
    @Autowired
    private I18nServiceProvider serviceProvider;
    @Autowired
    private I18nDeployService deployService;
    @Autowired
    private DesignBigDataService bigDataService;

    @Override
    public I18nInitVO init() {
        I18nInitVO i18nInitVO = new I18nInitVO();
        i18nInitVO.getResourceType().add(1, new I18nBaseObj(String.valueOf(I18nResourceType.TASK_ORG_ALIAS.getValue()), I18nResourceType.TASK_ORG_ALIAS.getTitle()));
        return i18nInitVO;
    }

    @Override
    public I18nInitExtendResultVO initExtend(I18nInitExtendQueryVO conditionQueryVO) {
        I18nWorkShop workShop = this.getWorkShop(conditionQueryVO.getResourceType());
        return workShop.buildCondition(conditionQueryVO);
    }

    @Override
    public I18nResultVO query(I18nQueryVO queryVO) throws I18nException {
        if (queryVO.getLanguageType() == null || queryVO.getResourceType() == null) {
            throw new I18nException("\u8bed\u8a00\u7c7b\u578b\u3001\u8d44\u6e90\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        I18nResultVO i18nResultVO = new I18nResultVO(queryVO.getLanguageType(), queryVO.getResourceType());
        try {
            I18nWorkShop workShop = this.getWorkShop(queryVO.getResourceType());
            List<? extends I18nBaseDTO> product = workShop.produce(queryVO);
            i18nResultVO.setDatas(product);
            List<I18nColsObj> cols = workShop.buildCols();
            i18nResultVO.setI18nCols(cols);
        }
        catch (Exception e) {
            throw new I18nException(e.getMessage());
        }
        return i18nResultVO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(I18nResultVO resultVO) throws I18nException {
        this.getWorkShop((int)resultVO.getResourceType()).save(resultVO);
    }

    @Override
    public void deploy() throws I18nException {
        try {
            this.deployService.I18nDeployByTask();
        }
        catch (Exception e) {
            throw new I18nException("\u591a\u8bed\u8a00\u53d1\u5e03\u5931\u8d25", e);
        }
    }

    @Override
    public void styleSave(I18nFormSaveVO saveVO) throws I18nException {
        try {
            String formKey = saveVO.getKey();
            CellBook cellBook = saveVO.getCellBook();
            for (CellSheet sheet : cellBook.getSheets()) {
                Grid2Data grid2Data = new Grid2Data();
                CellBookGrid2dataConverter.cellBookToGrid2Data((CellSheet)sheet, (Grid2Data)grid2Data);
                if (grid2Data.isRowHidden(0) || grid2Data.isColumnHidden(0)) {
                    grid2Data.setRowHidden(0, false);
                    grid2Data.setColumnHidden(0, false);
                }
                for (int row = 0; row < sheet.getRowCount(); ++row) {
                    for (int col = 0; col < sheet.getColumnCount(); ++col) {
                        Cell cell = sheet.getCell(row, col);
                        if (!"hyperlink".equals(cell.getDataTypeId())) continue;
                        grid2Data.getGridCellData(col, row).setCellMode(4);
                        grid2Data.getGridCellData(col, row).setForeGroundColor(8431551);
                    }
                }
                this.bigDataService.updateBigDataDefine(formKey, "FORM_DATA", saveVO.getLanguageType().intValue(), Grid2Data.gridToBytes((Grid2Data)grid2Data));
            }
        }
        catch (Exception e) {
            throw new I18nException("\u66f4\u65b0\u591a\u8bed\u8a00\u8868\u6837\u5931\u8d25");
        }
    }

    private I18nWorkShop getWorkShop(Integer resourceType) {
        I18nResourceType resource = I18nResourceType.valueOf(resourceType);
        return I18nWorkShopFactory.createWorkShop(resource, this.serviceProvider);
    }
}

