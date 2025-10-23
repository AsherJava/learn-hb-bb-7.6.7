/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.calibre2.service.impl;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.calibre2.ICalibreDataIOService;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.CalibreDataOption;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.common.UpdateResult;
import com.jiuqi.nr.calibre2.domain.BatchCalibreDataOptionsDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.domain.CalibreExpressionDO;
import com.jiuqi.nr.calibre2.exception.CalibreDataException;
import com.jiuqi.nr.calibre2.exception.CalibreException;
import com.jiuqi.nr.calibre2.service.ICalibreDataManageService;
import com.jiuqi.nr.calibre2.vo.CalibreDataVO;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class calibreDataIOServiceImpl
implements ICalibreDataIOService {
    @Autowired
    private ICalibreDataService calibreDataService;
    @Autowired
    private ICalibreDefineService calibreDefineService;
    @Autowired
    private ICalibreDataManageService calibreDataManageService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    private static final Logger log = LoggerFactory.getLogger(calibreDataIOServiceImpl.class);
    public static final String CJHECK_CODE = "^[a-zA-Z_][a-zA-Z0-9_]{4,10}$";
    public static final String CHECK_NAME = "^[a-zA-Z_][a-zA-Z0-9_]{1,20}$";

    @Override
    public void exportCalibreData(String key, HttpServletResponse response) throws JQException {
        CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
        calibreDefineDTO.setKey(key);
        calibreDefineDTO = this.calibreDefineService.get(calibreDefineDTO).getData();
        CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
        calibreDataDTO.setDefineKey(key);
        calibreDataDTO.setCalibreCode(calibreDefineDTO.getCode());
        calibreDataDTO.setEntityId(calibreDefineDTO.getEntityId());
        String exportFileName = calibreDefineDTO.getName() + "\u3010" + calibreDefineDTO.getCode() + "\u3011";
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("products");
        sheet.setDefaultColumnWidth(20);
        XSSFCellStyle style = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setFont(font);
        style.setWrapText(true);
        this.addData(calibreDataDTO, calibreDefineDTO, sheet, style);
        try (ServletOutputStream outputStream = response.getOutputStream();){
            this.extracted(response, exportFileName);
            wb.write((OutputStream)outputStream);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)CalibreDataException.EXPORT_ERROR, (Throwable)e);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void importCalibreData(MultipartFile file, String defineKey) throws JQException {
        XSSFWorkbook workbook;
        block23: {
            Assert.notNull((Object)file, "\u6587\u4ef6\u4e3a\u7a7a");
            workbook = null;
            try (InputStream inputStream = file.getInputStream();){
                String fileName = file.getOriginalFilename();
                if (fileName.endsWith("xls") || fileName.endsWith("et") || fileName.endsWith("xlsx")) {
                    workbook = new XSSFWorkbook(inputStream);
                    break block23;
                }
                throw new IOException(fileName + "\u4e0d\u662fexcel\u6587\u4ef6");
            }
            catch (Exception e2) {
                throw new JQException((ErrorEnum)CalibreDataException.IMPORT_FILE_TYPE_ERROR, "\u4e0d\u662fexcel\u6216et\u6587\u4ef6");
            }
        }
        ArrayList<CalibreDataDTO> calibreDataDTOList = new ArrayList<CalibreDataDTO>();
        Sheet sheet = workbook.getSheetAt(0);
        CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
        calibreDefineDTO.setKey(defineKey);
        calibreDefineDTO = this.calibreDefineService.get(calibreDefineDTO).getData();
        this.getCalibreData(calibreDataDTOList, calibreDefineDTO, sheet);
        ArrayList<CalibreDataVO> errorData = new ArrayList<CalibreDataVO>();
        ArrayList<CalibreDataVO> hasUnitNotExist = new ArrayList<CalibreDataVO>();
        if (calibreDefineDTO.getType() == 0) {
            for (CalibreDataDTO calibreDataDTO : calibreDataDTOList) {
                List<String> noExistUnit;
                Object value = calibreDataDTO.getValue().getExpression();
                List aaa = (List)value;
                if (CollectionUtils.isEmpty(aaa) || CollectionUtils.isEmpty(noExistUnit = this.querySelectedEntity(calibreDefineDTO.getEntityId(), aaa))) continue;
                CalibreDataVO calibreDataVO = CalibreDataVO.getInstance(calibreDataDTO);
                calibreDataVO.setReason("\u5305\u542b\u4e0d\u5b58\u5728\u7684\u5355\u4f4d");
                hasUnitNotExist.add(calibreDataVO);
            }
        }
        ArrayList<CalibreDataVO> notExistParent = new ArrayList<CalibreDataVO>();
        HashSet<String> sameCode = new HashSet<String>();
        ArrayList errorCode = new ArrayList();
        HashMap sameCodeMap = new HashMap();
        ArrayList<String> allRightDataCodeWithoutSame = new ArrayList<String>();
        ArrayList<String> allDataCodes = new ArrayList<String>();
        HashSet allDataParents = new HashSet();
        HashMap<String, String> allCodeToParents = new HashMap<String, String>();
        HashMap<String, CalibreDataDTO> allCodeToData = new HashMap<String, CalibreDataDTO>();
        ArrayList allDataNames = new ArrayList();
        calibreDataDTOList.forEach(e -> {
            allDataCodes.add(e.getCode());
            allDataParents.add(e.getParent());
            allCodeToParents.put(e.getCode(), e.getParent());
            allCodeToData.put(e.getCode(), (CalibreDataDTO)e);
            allDataNames.add(e.getName());
        });
        List<CalibreDataVO> sameDataDTOS = this.checkSameCodeAndErrorCode(calibreDataDTOList, allDataCodes, allRightDataCodeWithoutSame, sameCode);
        errorData.addAll(sameDataDTOS);
        errorData.addAll(hasUnitNotExist);
        if (calibreDefineDTO.getStructType() == 1) {
            this.checkNotExistParent(allCodeToData, allDataCodes, allCodeToParents, notExistParent);
            errorData.addAll(notExistParent);
            List<String> stringStringMap = this.checkRing(allRightDataCodeWithoutSame, allCodeToParents);
            ArrayList<CalibreDataVO> ringDateDTO = new ArrayList<CalibreDataVO>();
            for (String string : stringStringMap) {
                CalibreDataVO calibreDataVO = CalibreDataVO.getInstance((CalibreDataDTO)allCodeToData.get(string));
                calibreDataVO.setReason("\u5b58\u5728\u73af\u8def");
                ringDateDTO.add(calibreDataVO);
            }
            errorData.addAll(ringDateDTO);
        }
        if (errorData.size() > 0) {
            throw new JQException((ErrorEnum)CalibreDataException.CALIBRE_ERROR_DATA, errorData);
        }
        BatchCalibreDataOptionsDTO batchCalibreDataOptionsDTO = new BatchCalibreDataOptionsDTO();
        batchCalibreDataOptionsDTO.setDefineKey(defineKey);
        batchCalibreDataOptionsDTO.setDefineCode(calibreDefineDTO.getCode());
        CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
        calibreDataDTO.setDefineKey(defineKey);
        calibreDataDTO.setCalibreCode(calibreDefineDTO.getCode());
        List<CalibreDataDTO> dataDTOList = this.calibreDataService.list(calibreDataDTO).getData();
        batchCalibreDataOptionsDTO.setCalibreDataDTOS(dataDTOList);
        try {
            if (!CollectionUtils.isEmpty(dataDTOList)) {
                this.calibreDataService.batchDelete(batchCalibreDataOptionsDTO, true);
            }
            batchCalibreDataOptionsDTO.setCalibreDataDTOS(calibreDataDTOList);
            Result<List<UpdateResult>> result = this.calibreDataService.batchAdd(batchCalibreDataOptionsDTO);
        }
        catch (Exception exception) {
            log.error("\u5bfc\u5165\u53e3\u5f84\u5931\u8d25");
            throw new JQException((ErrorEnum)CalibreDataException.IMPORT_ERROR, (Throwable)exception);
        }
        log.info("\u5bfc\u5165\u53e3\u5f84\u6210\u529f");
    }

    private List<String> querySelectedEntity(String entityId, List<String> unitKeys) throws JQException {
        IEntityTable entityTable;
        if (CollectionUtils.isEmpty(unitKeys)) {
            return null;
        }
        ArrayList<String> unExistunitKeys = new ArrayList<String>();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        String dimensionName = this.entityMetaService.getDimensionName(entityViewDefine.getEntityId());
        DimensionValueSet dimensionValueset = new DimensionValueSet();
        dimensionValueset.setValue(dimensionName, unitKeys);
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.setMasterKeys(dimensionValueset);
        try {
            entityTable = iEntityQuery.executeReader(null);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)CalibreException.ENTITY_QUERY, "\u67e5\u8be2\u5355\u4f4d\u6570\u636e\u5f02\u5e38");
        }
        List allRows = entityTable.getAllRows();
        List collect = allRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        for (String unitKey : unitKeys) {
            if (collect.contains(unitKey)) continue;
            unExistunitKeys.add(unitKey);
        }
        return unExistunitKeys;
    }

    private void checkNotExistParent(Map<String, CalibreDataDTO> allCodeToData, List<String> allDataCodes, Map<String, String> allCodeToParents, List<CalibreDataVO> notExistParent) {
        for (Map.Entry<String, String> entry : allCodeToParents.entrySet()) {
            if (allDataCodes.contains(entry.getValue()) || !StringUtils.hasLength(entry.getValue())) continue;
            CalibreDataVO calibreDataDTO = CalibreDataVO.getInstance(allCodeToData.get(entry.getKey()));
            calibreDataDTO.setReason("\u4e0a\u7ea7\u4e0d\u5b58\u5728");
            notExistParent.add(calibreDataDTO);
        }
        for (CalibreDataDTO calibreDataDTO : notExistParent) {
            allCodeToParents.put(calibreDataDTO.getKey(), null);
        }
    }

    private List<CalibreDataVO> checkSameCodeAndErrorCode(List<CalibreDataDTO> calibreDataDTOList, List<String> allDataCodes, List<String> allRightDataCodeWithoutSame, Set<String> sameCode) {
        ArrayList<CalibreDataVO> sameAndErrorCodeDataDTO = new ArrayList<CalibreDataVO>();
        ArrayList errorCodeDataDTO = new ArrayList();
        HashSet allDataParentWithoutSame = new HashSet();
        String regex = "^\\w{1,10}$";
        calibreDataDTOList.forEach(e -> {
            String code = e.getCode();
            if (!allDataParentWithoutSame.add(code)) {
                CalibreDataVO instance = CalibreDataVO.getInstance(e);
                instance.setReason("\u4ee3\u7801\u91cd\u590d");
                sameAndErrorCodeDataDTO.add(instance);
            } else if (!code.matches(regex)) {
                CalibreDataVO instance = CalibreDataVO.getInstance(e);
                instance.setReason("\u4ee3\u7801\u7ec4\u6210\u4e0d\u7b26\u5408\u89c4\u8303");
                errorCodeDataDTO.add(instance);
            } else {
                allRightDataCodeWithoutSame.add(code);
            }
        });
        sameAndErrorCodeDataDTO.addAll(errorCodeDataDTO);
        return sameAndErrorCodeDataDTO;
    }

    private List<String> checkRing(List<String> allRightDataCodeWithoutSame, Map<String, String> allCodeToParents) {
        ArrayList<String> ringDate = new ArrayList<String>();
        for (String code : allRightDataCodeWithoutSame) {
            ArrayList<String> paths = new ArrayList<String>();
            String codeParent = allCodeToParents.get(code);
            if (!StringUtils.hasText(codeParent)) continue;
            if (codeParent.equals(code)) {
                ringDate.add(code);
                continue;
            }
            if (ringDate.contains(code)) continue;
            if (ringDate.contains(codeParent)) {
                ringDate.add(code);
                continue;
            }
            paths.add(code);
            Boolean aBoolean = this.searchParent(code, codeParent, allCodeToParents, paths, ringDate);
            if (!aBoolean.booleanValue()) continue;
            ringDate.addAll(paths);
        }
        return ringDate;
    }

    private Boolean searchParent(String code, String codeParent, Map<String, String> allCodeToParents, List<String> paths, List<String> ringDate) {
        paths.add(codeParent);
        String newCodeParent = allCodeToParents.get(codeParent);
        if (!StringUtils.hasText(codeParent)) {
            return false;
        }
        if (codeParent.equals(code)) {
            return true;
        }
        if (paths.contains(newCodeParent) || ringDate.contains(newCodeParent)) {
            return true;
        }
        return this.searchParent(code, newCodeParent, allCodeToParents, paths, ringDate);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void getCalibreData(List<CalibreDataDTO> calibreDataDTOList, CalibreDefineDTO calibreDefineDTO, Sheet sheet) throws JQException {
        String column3;
        String column2;
        String column1;
        String column0;
        Row row0 = sheet.getRow(0);
        Cell entityRowCell2 = row0.getCell(1);
        if (entityRowCell2 == null) throw new JQException((ErrorEnum)CalibreDataException.CALIBRE_ENTITY_EMPTY);
        String s = this.celltoString(row0, 1);
        if (!StringUtils.hasText(s)) throw new JQException((ErrorEnum)CalibreDataException.CALIBRE_ENTITY_EMPTY);
        if (!s.equals(calibreDefineDTO.getEntityId())) {
            throw new JQException((ErrorEnum)CalibreDataException.CALIBRE_ENTITY_ERROR);
        }
        Row row1 = sheet.getRow(1);
        int structType = calibreDefineDTO.getStructType();
        Integer type = calibreDefineDTO.getType();
        if (structType == 0) {
            String column22;
            String column12;
            String column02;
            try {
                column02 = this.celltoString(row1, 0);
                column12 = this.celltoString(row1, 1);
                column22 = this.celltoString(row1, 2);
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)CalibreDataException.FILE_ERROR);
            }
            boolean isSameType = this.isSameCalibreType(type, column22);
            if (!"\u4ee3\u7801".equals(column02) || !"\u540d\u79f0".equals(column12) || !isSameType) throw new JQException((ErrorEnum)CalibreDataException.FILE_ERROR);
            if (calibreDefineDTO.getType() == 2) {
                try {
                    String column32 = this.celltoString(row1, 3);
                    String column4 = this.celltoString(row1, 4);
                    if (!"\u662f\u5426\u5408\u8ba1\u9879".equals(column32) || !"\u5b9e\u4f53\u6570\u636e\u4e3b\u952e".equals(column4)) {
                        throw new JQException((ErrorEnum)CalibreDataException.FILE_ERROR);
                    }
                }
                catch (Exception e) {
                    throw new JQException((ErrorEnum)CalibreDataException.FILE_ERROR);
                }
            }
            try {
                for (int i = 2; i < sheet.getPhysicalNumberOfRows(); ++i) {
                    CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
                    Row row = sheet.getRow(i);
                    String cell1 = this.celltoString(row, 0);
                    String cell2 = this.celltoString(row, 1);
                    if (!StringUtils.hasText(cell1) || !StringUtils.hasText(cell2)) {
                        throw new JQException((ErrorEnum)CalibreDataException.CALIBRE_CODE_ERROR);
                    }
                    calibreDataDTO.setCode(this.celltoString(row, 0));
                    calibreDataDTO.setName(this.celltoString(row, 1));
                    Cell cell4 = row.getCell(2);
                    if (cell4 != null) {
                        if (StringUtils.hasText(this.celltoString(row, 2))) {
                            CalibreExpressionDO calibreExpressionDO = new CalibreExpressionDO();
                            if (calibreDefineDTO.getType() == 0) {
                                String stringCellValue = this.celltoString(row, 2);
                                if (!"null".equals(stringCellValue)) {
                                    List<String> stringCellValues = Arrays.asList(stringCellValue.split(";"));
                                    calibreExpressionDO.setExpression(stringCellValues);
                                    calibreDataDTO.setValue(calibreExpressionDO);
                                }
                            } else {
                                calibreExpressionDO.setExpression(this.celltoString(row, 2));
                                if (calibreDefineDTO.getType() == 2) {
                                    calibreExpressionDO.setSum("TRUE".equals(this.celltoString(row, 3)));
                                    calibreExpressionDO.setEntityKey(this.celltoString(row, 4));
                                }
                                calibreDataDTO.setValue(calibreExpressionDO);
                            }
                        } else {
                            calibreDataDTO.setValue(new CalibreExpressionDO());
                        }
                    } else {
                        calibreDataDTO.setValue(new CalibreExpressionDO());
                    }
                    calibreDataDTO.setKey(UUIDUtils.getKey());
                    calibreDataDTO.setCalibreCode(calibreDefineDTO.getCode());
                    calibreDataDTO.setOrder(OrderGenerator.newOrderID());
                    calibreDataDTOList.add(calibreDataDTO);
                }
                return;
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)CalibreDataException.CALIBRE_CODE_ERROR);
            }
        }
        try {
            column0 = this.celltoString(row1, 0);
            column1 = this.celltoString(row1, 1);
            column2 = this.celltoString(row1, 2);
            column3 = this.celltoString(row1, 3);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)CalibreDataException.FILE_ERROR);
        }
        boolean isSameType = this.isSameCalibreType(type, column3);
        if (!"\u4ee3\u7801".equals(column0) || !"\u540d\u79f0".equals(column1) || !"\u4e0a\u7ea7".equals(column2) || !isSameType) throw new JQException((ErrorEnum)CalibreDataException.FILE_ERROR);
        if (calibreDefineDTO.getType() == 2) {
            try {
                String column4 = this.celltoString(row1, 4);
                String column5 = this.celltoString(row1, 5);
                if (!"\u662f\u5426\u5408\u8ba1\u9879".equals(column4) || !"\u5b9e\u4f53\u6570\u636e\u4e3b\u952e".equals(column5)) {
                    throw new JQException((ErrorEnum)CalibreDataException.FILE_ERROR);
                }
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)CalibreDataException.FILE_ERROR);
            }
        }
        try {
            for (int i = 2; i < sheet.getPhysicalNumberOfRows(); ++i) {
                CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
                Row row = sheet.getRow(i);
                String cell1 = this.celltoString(row, 0);
                String cell2 = this.celltoString(row, 1);
                if (!StringUtils.hasText(cell1) || !StringUtils.hasText(cell2)) {
                    throw new JQException((ErrorEnum)CalibreDataException.CALIBRE_CODE_ERROR);
                }
                calibreDataDTO.setCode(cell1);
                calibreDataDTO.setName(cell2);
                Cell cell3 = row.getCell(2);
                if (cell3 != null) {
                    calibreDataDTO.setParent(this.celltoString(row, 2));
                } else {
                    calibreDataDTO.setParent(null);
                }
                Cell cell4 = row.getCell(3);
                if (cell4 != null) {
                    if (StringUtils.hasText(this.celltoString(row, 3))) {
                        CalibreExpressionDO calibreExpressionDO = new CalibreExpressionDO();
                        if (calibreDefineDTO.getType() == 0) {
                            String stringCellValue = this.celltoString(row, 3);
                            if (!"null".equals(stringCellValue)) {
                                List<String> stringCellValues = Arrays.asList(stringCellValue.split(";"));
                                calibreExpressionDO.setExpression(stringCellValues);
                                calibreDataDTO.setValue(calibreExpressionDO);
                            }
                        } else {
                            calibreExpressionDO.setExpression(this.celltoString(row, 3));
                            if (calibreDefineDTO.getType() == 2) {
                                calibreExpressionDO.setSum("TRUE".equals(this.celltoString(row, 4)));
                                calibreExpressionDO.setEntityKey(this.celltoString(row, 5));
                            }
                            calibreDataDTO.setValue(calibreExpressionDO);
                        }
                    } else {
                        calibreDataDTO.setValue(new CalibreExpressionDO());
                    }
                } else {
                    calibreDataDTO.setValue(new CalibreExpressionDO());
                }
                calibreDataDTO.setKey(UUIDUtils.getKey());
                calibreDataDTO.setCalibreCode(calibreDefineDTO.getCode());
                calibreDataDTO.setOrder(OrderGenerator.newOrderID());
                calibreDataDTOList.add(calibreDataDTO);
            }
            return;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)CalibreDataException.CALIBRE_CODE_ERROR);
        }
    }

    private boolean isSameCalibreType(Integer type, String column) {
        boolean isSameType = type == 0 ? "\u6307\u5b9a\u6570\u636e\u9879".equals(column) : "\u8868\u8fbe\u5f0f".equals(column);
        return isSameType;
    }

    private void extracted(HttpServletResponse response, String fileName) throws Exception {
        fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
        fileName = HtmlUtils.cleanHeaderValue((String)("attachment;filename=" + fileName + "." + XSSFWorkbookType.XLSX.getExtension()));
        HtmlUtils.validateHeaderValue((String)fileName);
        response.setHeader("Content-disposition", fileName);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
    }

    private List<CalibreDataVO> getAllDataByOrder(CalibreDataDTO calibreDataDTO, CalibreDefineDTO calibreDefineDTO) throws JQException {
        List<Object> dataList = new ArrayList();
        CalibreDataVO calibreDataVO = new CalibreDataVO();
        calibreDataVO.setDefineKey(calibreDataDTO.getDefineKey());
        calibreDataVO.setCalibreCode(calibreDataDTO.getCalibreCode());
        calibreDataVO.setSetContent(false);
        if (calibreDefineDTO.getStructType() == 0) {
            dataList = this.calibreDataManageService.searchData(calibreDataVO);
            dataList.sort(Comparator.comparingLong(CalibreDataDO::getOrder));
        } else {
            calibreDataVO.setDataTreeType(CalibreDataOption.DataTreeType.ROOT);
            List<CalibreDataVO> firstGradeDataVOS = this.calibreDataManageService.searchData(calibreDataVO);
            firstGradeDataVOS.sort(Comparator.comparingLong(CalibreDataDO::getOrder));
            this.getAllDataByExportOrder(firstGradeDataVOS, dataList, calibreDefineDTO.getCode());
        }
        return dataList;
    }

    private void getAllDataByExportOrder(List<CalibreDataVO> firstGradeDataVOS, List<CalibreDataVO> dataList, String calibreCode) throws JQException {
        for (CalibreDataVO calibreDataVO : firstGradeDataVOS) {
            dataList.add(calibreDataVO);
            CalibreDataVO calibreDataVO1 = new CalibreDataVO();
            calibreDataVO1.setDataTreeType(CalibreDataOption.DataTreeType.DIRECT_CHILDREN);
            calibreDataVO1.setDefineKey(calibreDataVO.getDefineKey());
            calibreDataVO1.setCode(calibreDataVO.getCode());
            calibreDataVO1.setCalibreCode(calibreCode);
            calibreDataVO1.setSetContent(false);
            List<CalibreDataVO> childDataVOS = this.calibreDataManageService.searchData(calibreDataVO1);
            if (CollectionUtils.isEmpty(childDataVOS)) continue;
            childDataVOS.sort(Comparator.comparingLong(CalibreDataDO::getOrder));
            this.getAllDataByExportOrder(childDataVOS, dataList, calibreCode);
        }
    }

    private void addData(CalibreDataDTO calibreDataDTO, CalibreDefineDTO calibreDefineDTO, XSSFSheet sheet, XSSFCellStyle style) throws JQException {
        XSSFCell cell4;
        XSSFCell cell2;
        XSSFCell cell1;
        XSSFCell cell0;
        XSSFRow row;
        int i;
        List<CalibreDataVO> dataList = this.getAllDataByOrder(calibreDataDTO, calibreDefineDTO);
        String regEx = "[\\[\\]]";
        XSSFRow row1 = sheet.createRow(0);
        XSSFCell row2Cell1 = row1.createCell(0, CellType.STRING);
        row2Cell1.setCellValue("\u5173\u8054\u7ef4\u5ea6\u4ee3\u7801:");
        XSSFCell row2Cell2 = row1.createCell(1, CellType.STRING);
        String entityId = calibreDataDTO.getEntityId();
        row2Cell2.setCellValue(calibreDataDTO.getEntityId());
        XSSFCell row2Cell3 = row1.createCell(2, CellType.STRING);
        row2Cell3.setCellValue("\u5173\u8054\u7ef4\u5ea6\u540d\u79f0:");
        XSSFCell row2Cell4 = row1.createCell(3, CellType.STRING);
        IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(calibreDataDTO.getEntityId());
        row2Cell4.setCellValue(iEntityDefine.getTitle());
        XSSFRow row2 = sheet.createRow(1);
        if (calibreDefineDTO.getStructType() == 1) {
            if (!dataList.isEmpty()) {
                for (i = 0; i < dataList.size(); ++i) {
                    row = sheet.createRow(i + 2);
                    cell0 = row.createCell(0, CellType.STRING);
                    cell0.setCellStyle(style);
                    cell0.setCellValue(dataList.get(i).getCode());
                    cell1 = row.createCell(1, CellType.STRING);
                    cell1.setCellStyle(style);
                    cell1.setCellValue(dataList.get(i).getName());
                    cell2 = row.createCell(2, CellType.STRING);
                    cell2.setCellStyle(style);
                    cell2.setCellValue(dataList.get(i).getParent());
                    XSSFCell cell3 = row.createCell(3, CellType.STRING);
                    cell3.setCellStyle(style);
                    Object value = dataList.get(i).getValue().getExpression();
                    if (calibreDefineDTO.getType() == 0) {
                        if (value != null) {
                            String s = value.toString().replace(" ", "").replaceAll(regEx, "").replaceAll("[\\,]", ";");
                            cell3.setCellValue(s);
                            continue;
                        }
                        cell3.setCellValue((String)null);
                        continue;
                    }
                    if (value != null) {
                        cell3.setCellValue(value.toString());
                    } else {
                        cell3.setCellValue((String)null);
                    }
                    if (calibreDefineDTO.getType() != 2) continue;
                    cell4 = row.createCell(4, CellType.BOOLEAN);
                    cell4.setCellStyle(style);
                    cell4.setCellValue(dataList.get(i).getValue().getSum());
                    XSSFCell cell5 = row.createCell(5, CellType.STRING);
                    cell5.setCellStyle(style);
                    cell5.setCellValue(dataList.get(i).getValue().getEntityKey());
                }
            }
        } else if (!dataList.isEmpty()) {
            for (i = 0; i < dataList.size(); ++i) {
                row = sheet.createRow(i + 2);
                cell0 = row.createCell(0, CellType.STRING);
                cell0.setCellStyle(style);
                cell0.setCellValue(dataList.get(i).getCode());
                cell1 = row.createCell(1, CellType.STRING);
                cell1.setCellStyle(style);
                cell1.setCellValue(dataList.get(i).getName());
                cell2 = row.createCell(2, CellType.STRING);
                Object value = dataList.get(i).getValue().getExpression();
                cell2.setCellStyle(style);
                if (calibreDefineDTO.getType() == 0) {
                    if (value != null) {
                        String s = value.toString().replace(" ", "").replaceAll(regEx, "").replaceAll("[\\,]", ";");
                        cell2.setCellValue(s);
                        continue;
                    }
                    cell2.setCellValue((String)null);
                    continue;
                }
                if (value != null) {
                    cell2.setCellValue(value.toString());
                } else {
                    cell2.setCellValue((String)null);
                }
                if (calibreDefineDTO.getType() != 2) continue;
                XSSFCell cell3 = row.createCell(3, CellType.BOOLEAN);
                cell3.setCellStyle(style);
                cell3.setCellValue(dataList.get(i).getValue().getSum());
                cell4 = row.createCell(4, CellType.STRING);
                cell4.setCellStyle(style);
                cell4.setCellValue(dataList.get(i).getValue().getEntityKey());
            }
        }
        ArrayList<String> labelList = new ArrayList<String>();
        labelList.add("\u4ee3\u7801");
        labelList.add("\u540d\u79f0");
        if (calibreDefineDTO.getStructType() == 1) {
            labelList.add("\u4e0a\u7ea7");
        }
        labelList.add("\u8868\u8fbe\u5f0f");
        if (calibreDefineDTO.getType() == 2) {
            labelList.add("\u662f\u5426\u5408\u8ba1\u9879");
            labelList.add("\u5b9e\u4f53\u6570\u636e\u4e3b\u952e");
        }
        String[] label = labelList.toArray(new String[labelList.size()]);
        int columnNum = label.length;
        for (int n = 0; n < columnNum; ++n) {
            cell1 = row2.createCell(n);
            cell1.setCellType(CellType.STRING);
            if ("\u8868\u8fbe\u5f0f".equals(label[n]) && calibreDefineDTO.getType() == 0) {
                cell1.setCellValue("\u6307\u5b9a\u6570\u636e\u9879");
            } else {
                cell1.setCellValue(label[n]);
            }
            cell1.setCellStyle(style);
        }
    }

    private String celltoString(Row row, int i) {
        Cell cell = row.getCell(i);
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC: {
                DecimalFormat df = new DecimalFormat("0");
                String s = df.format(cell.getNumericCellValue());
                return s;
            }
            case STRING: {
                return cell.getRichStringCellValue().toString().trim();
            }
            case FORMULA: {
                return cell.getCellFormula();
            }
            case BLANK: {
                return "";
            }
            case BOOLEAN: {
                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            }
            case ERROR: {
                return ErrorEval.getText(cell.getErrorCellValue());
            }
        }
        return "Unknown Cell Type: " + (Object)((Object)cellType);
    }
}

