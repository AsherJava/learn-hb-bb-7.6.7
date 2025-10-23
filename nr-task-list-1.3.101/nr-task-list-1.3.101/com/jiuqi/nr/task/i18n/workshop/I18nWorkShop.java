/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 */
package com.jiuqi.nr.task.i18n.workshop;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import com.jiuqi.nr.task.i18n.bean.I18nColsObj;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nExportParam;
import com.jiuqi.nr.task.i18n.bean.dto.I18nFormDTO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendResultVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nResultVO;
import com.jiuqi.nr.task.i18n.common.I18nResourceType;
import com.jiuqi.nr.task.i18n.exception.I18nException;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class I18nWorkShop {
    public abstract I18nInitExtendResultVO buildCondition(I18nInitExtendQueryVO var1);

    public abstract List<I18nColsObj> buildCols();

    public abstract List<? extends I18nBaseDTO> produce(I18nQueryVO var1) throws I18nException;

    public abstract void save(I18nResultVO var1) throws I18nException;

    public abstract void dataImport(Workbook var1, DesignTaskDefine var2) throws I18nException;

    public abstract void dataExport(I18nExportParam var1) throws I18nException;

    protected I18nInitExtendResultVO buildCommonResultVO(I18nInitExtendQueryVO queryVO, IDesignTimeViewController designTimeViewController) {
        if (!StringUtils.hasText(queryVO.getTaskKey())) {
            List taskDefines = designTimeViewController.listAllTask();
            ArrayList<I18nBaseObj> taskObjs = new ArrayList<I18nBaseObj>();
            taskDefines.forEach(taskDefine -> {
                I18nBaseObj taskObj = new I18nBaseObj(taskDefine.getKey(), taskDefine.getTitle());
                taskObjs.add(taskObj);
            });
            return new I18nInitExtendResultVO(taskObjs);
        }
        if (StringUtils.hasText(queryVO.getTaskKey()) && !StringUtils.hasText(queryVO.getFormSchemeKey())) {
            List formSchemeDefines = designTimeViewController.listFormSchemeByTask(queryVO.getTaskKey());
            ArrayList<I18nBaseObj> formSchemeObjs = new ArrayList<I18nBaseObj>();
            formSchemeDefines.forEach(formSchemeDefine -> {
                I18nBaseObj formSchemeObj = new I18nBaseObj(formSchemeDefine.getKey(), formSchemeDefine.getTitle());
                formSchemeObjs.add(formSchemeObj);
            });
            return new I18nInitExtendResultVO(formSchemeObjs);
        }
        return null;
    }

    protected List<I18nColsObj> initCols() {
        ArrayList<I18nColsObj> cols = new ArrayList<I18nColsObj>();
        return cols;
    }

    protected I18nColsObj buildColsObj(String key, String title, boolean disabled, boolean operate, Integer colWidth) {
        return new I18nColsObj(key, title, disabled, operate, colWidth);
    }

    protected DesParamLanguage buildDesParamLanguage(I18nBaseDTO dto, I18nResultVO resultVO) {
        DesParamLanguage language = new DesParamLanguage();
        language.setKey(UUIDUtils.getKey());
        language.setLanguageInfo(dto.getOtherLanguageInfo());
        language.setLanguageType(resultVO.getTrulyLanguageType());
        language.setResourceType(LanguageResourceType.valueOf((int)resultVO.getResourceType()));
        return language;
    }

    protected I18nBaseDTO initDTO(DesParamLanguage language) {
        I18nFormDTO i18nBaseDTO = new I18nFormDTO();
        if (language != null) {
            i18nBaseDTO.setOtherLanguageInfo(language.getLanguageInfo());
            i18nBaseDTO.setLanguageKey(language.getKey());
        }
        return i18nBaseDTO;
    }

    protected boolean needDelete(I18nBaseDTO baseDTO) {
        return StringUtils.hasText(baseDTO.getLanguageKey());
    }

    protected boolean needAdd(I18nBaseDTO baseDTO) {
        return StringUtils.hasText(baseDTO.getOtherLanguageInfo());
    }

    protected Sheet createSheet(Workbook workbook, String sheetTitle, List<I18nColsObj> cols) {
        Sheet sheet = workbook.createSheet(sheetTitle);
        sheet.setDefaultColumnWidth(30);
        Row row = sheet.createRow(0);
        row.setHeightInPoints(25.0f);
        for (int i = 0; i < cols.size(); ++i) {
            Cell cell = row.createCell(i);
            cell.setCellValue(cols.get(i).getTitle());
        }
        return sheet;
    }

    protected String getOtherLanguageInfo(String resourceKey, Map<String, DesParamLanguage> languageMap) {
        return languageMap.get(resourceKey) == null ? "" : languageMap.get(resourceKey).getLanguageInfo();
    }

    protected DesParamLanguage buildLanguageObj(String languageInfo, Integer resourceValue, Integer languageValue, String resourceKey) {
        DesParamLanguage desParamLanguage = new DesParamLanguage();
        desParamLanguage.setKey(UUIDUtils.getKey());
        desParamLanguage.setLanguageInfo(languageInfo);
        desParamLanguage.setLanguageType(String.valueOf(languageValue));
        desParamLanguage.setResourceType(LanguageResourceType.valueOf((int)resourceValue));
        desParamLanguage.setResourceKey(resourceKey);
        return desParamLanguage;
    }

    protected Map<String, DesParamLanguage> getLanguageMap(List<? extends IMetaItem> defines, DesParamLanguageDao dao, I18nResourceType resourceType) throws I18nException {
        List keys = defines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        List languages = dao.queryLanguageByResKeys(keys);
        HashMap<String, DesParamLanguage> languageMap = new HashMap();
        try {
            languageMap = languages.stream().collect(Collectors.toMap(DesParamLanguage::getResourceKey, v -> v));
        }
        catch (Exception e) {
            throw new I18nException(String.format("[%s]\u5b58\u5728\u91cd\u590d\u591a\u8bed\u8a00\u8bb0\u5f55", resourceType.getTitle()));
        }
        return languageMap;
    }

    protected String getFormSchemeSheetName(DesignFormSchemeDefine formScheme) {
        return formScheme.getTitle() + '_' + formScheme.getFormSchemeCode();
    }

    protected String getResourceCodeFromSheet(Sheet sheet) {
        String sheetName = sheet.getSheetName();
        String sheetCode = sheetName.split("_")[1];
        return sheetCode;
    }

    protected String getCellValueWithImport(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            return "";
        }
        cell.setCellType(CellType.STRING);
        String cellValue = cell.getStringCellValue();
        return cellValue;
    }

    protected String getFileName(I18nResourceType resourceType) {
        return resourceType.toString() + ".xlsx";
    }

    protected void writeToZip(I18nExportParam exportParam, String fileName, Workbook workbook) throws I18nException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();){
            ZipOutputStream zipOut = exportParam.getZipOut();
            ZipEntry zipEntry = new ZipEntry(exportParam.getParentEntry().getName() + fileName);
            zipOut.putNextEntry(zipEntry);
            workbook.write(outputStream);
            zipOut.write(outputStream.toByteArray());
            zipOut.closeEntry();
        }
        catch (Exception e) {
            throw new I18nException(String.format("\u5bfc\u51fa\u6587\u4ef6{%s}\u6570\u636e\u5199\u5165\u6d41\u5931\u8d25", fileName));
        }
    }

    protected int doDelete(DesParamLanguageDao dao, List<String> resourceKeys) throws Exception {
        if (!CollectionUtils.isEmpty(resourceKeys)) {
            List needDeleteParamLanguages = dao.queryLanguageByResKeys(resourceKeys);
            if (!CollectionUtils.isEmpty(needDeleteParamLanguages)) {
                dao.batchDelete((String[])needDeleteParamLanguages.stream().map(DesParamLanguage::getKey).toArray(String[]::new));
            }
            return needDeleteParamLanguages.size();
        }
        return 0;
    }

    protected int doInsert(DesParamLanguageDao dao, List<DesParamLanguage> addParamLanguages) throws Exception {
        if (!CollectionUtils.isEmpty(addParamLanguages)) {
            dao.batchInsert(addParamLanguages.toArray(new DesParamLanguage[0]));
            return addParamLanguages.size();
        }
        return 0;
    }
}

