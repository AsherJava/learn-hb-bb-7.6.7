/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.mapping2.service.impl;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.dto.ExpZBMapping;
import com.jiuqi.nr.mapping2.service.JIOConfigService;
import com.jiuqi.nr.mapping2.service.ZBMappingImpExpService;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nr.mapping2.service.ZbIniProvider;
import com.jiuqi.nr.mapping2.util.ImpExpUtils;
import com.jiuqi.nr.mapping2.web.vo.Result;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ZBMappingImpExpServiceImpl
implements ZBMappingImpExpService {
    protected final Logger logger = LoggerFactory.getLogger(ZBMappingImpExpServiceImpl.class);
    @Autowired
    private ZbIniProvider iniProvider;
    @Autowired
    private ZBMappingService zBMappingService;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeCtrl;
    @Autowired
    private IRunTimeViewController runtimeCtrl;
    @Autowired
    private IFMDMAttributeService ifmdmAttributeService;
    @Autowired
    JIOConfigService jioService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    IMappingSchemeService service;

    @Override
    public Result importMapping(String msKey, String formSchemeKey, String formCode, MultipartFile file) {
        String fileName = file.getOriginalFilename().toUpperCase();
        try {
            PathUtils.validatePathManipulation((String)fileName);
        }
        catch (SecurityContentException e) {
            return Result.error(null, e.getMessage());
        }
        File sourcefile = new File(fileName);
        fileName = sourcefile.getName();
        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        if (!StringUtils.hasText(suffix)) {
            return Result.error(null, "\u9009\u62e9\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\uff0c\u8bf7\u91cd\u65b0\u9009\u62e9\uff01");
        }
        if ("INI".equals(suffix)) {
            return this.importINI(msKey, formCode, file);
        }
        return this.importXLS(msKey, formSchemeKey, formCode, file);
    }

    private Result importINI(String msKey, String formCode, MultipartFile file) {
        byte[] files = null;
        try {
            files = file.getBytes();
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
            return Result.error(null, "\u9009\u62e9\u6587\u4ef6\u683c\u5f0fINI\u9519\u8bef\uff0c\u8bf7\u91cd\u65b0\u9009\u62e9\uff01");
        }
        if (files == null) {
            return Result.error(null, "\u9009\u62e9\u6587\u4ef6\u683c\u5f0fINI\u9519\u8bef\uff0c\u8bf7\u91cd\u65b0\u9009\u62e9\uff01");
        }
        return this.iniProvider.uploadZbByINI(msKey, formCode, files);
    }

    private String importSheet(Sheet sheet, String msKey, String formSchemeKey, String formCode, boolean jio) throws Exception {
        FormDefine fDefine;
        int size = sheet.getLastRowNum();
        if (size == 0) {
            return "\u7a7a\u6587\u4ef6\uff01";
        }
        boolean hasRegionCol = false;
        int begin = 0;
        String firstValue = ImpExpUtils.getStringValue(sheet.getRow(0).getCell(0));
        if ("\u5b58\u50a8\u8868".equals(firstValue)) {
            begin = 1;
        }
        if ("\u533a\u57df".equals(firstValue)) {
            begin = 1;
            hasRegionCol = true;
        }
        if ((fDefine = this.runtimeCtrl.queryFormByCodeInScheme(formSchemeKey, formCode)) == null) {
            return "\u62a5\u8868 " + formCode + " \u4e0d\u5b58\u5728\uff1b";
        }
        HashMap<String, String> fieldRegionMap = new HashMap<String, String>();
        this.buildFRMap(hasRegionCol, fDefine, fieldRegionMap);
        HashSet<String> zbSet = new HashSet<String>();
        ArrayList<ZBMapping> zms = new ArrayList<ZBMapping>();
        ArrayList<String> mappingList = new ArrayList<String>();
        for (int i = begin; i <= size; ++i) {
            TableDefine tableDefine;
            String saveMapping;
            Row row = sheet.getRow(i);
            if (row == null) continue;
            String region = ImpExpUtils.getStringValue(row.getCell(0));
            if (hasRegionCol && !StringUtils.hasText(region)) continue;
            String table = ImpExpUtils.getStringValue(row.getCell(hasRegionCol ? 1 : 0));
            if (!StringUtils.hasText(table)) {
                return "\u7b2c" + i + "\u884c\uff0c\u5b58\u50a8\u8868\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff1b";
            }
            String zb = ImpExpUtils.getStringValue(row.getCell(hasRegionCol ? 2 : 1));
            if (!StringUtils.hasText(zb)) {
                return "\u7b2c" + i + "\u884c\uff0c\u6307\u6807\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff1b";
            }
            String mapping = ImpExpUtils.getStringValue(row.getCell(hasRegionCol ? 3 : 2));
            if (!StringUtils.hasText(mapping)) {
                return "\u7b2c" + i + "\u884c\uff0c\u6620\u5c04\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff1b";
            }
            String string = saveMapping = jio && StringUtils.hasText(mapping) ? this.getSaveText(formCode, mapping) : mapping;
            if (hasRegionCol) {
                if (!zbSet.add(region + zb)) {
                    return "\u7b2c" + i + "\u884c\uff0c\u533a\u57df\u52a0\u6307\u6807\u91cd\u590d\uff1b";
                }
                if (zms.stream().filter(e -> saveMapping.equals(e.getMapping()) && zb.equals(e.getZbCode())).findFirst().isPresent()) {
                    return "\u7b2c" + i + "\u884c\uff0c\u8868\u4e2d\u5df2\u5b58\u5728\u76f8\u540c\u7684\u6620\u5c04\u503c\uff1a" + mapping + "\uff1b";
                }
                DataRegionDefine dataRegion = this.runtimeCtrl.getDataRegion(region, fDefine.getKey(), formSchemeKey);
                if (dataRegion == null) {
                    continue;
                }
            } else {
                if (!zbSet.add(table + zb)) {
                    return "\u7b2c" + i + "\u884c\uff0c\u50a8\u5b58\u8868\u52a0\u6307\u6807\u91cd\u590d\uff1b";
                }
                if (zms.stream().filter(e -> e.getMapping().equals(saveMapping)).findFirst().isPresent()) {
                    return "\u7b2c" + i + "\u884c\uff0c\u8868\u4e2d\u5df2\u5b58\u5728\u76f8\u540c\u7684\u6620\u5c04\u503c\uff1a" + mapping + "\uff1b";
                }
            }
            if ((tableDefine = this.dataRuntimeCtrl.queryTableDefineByCode(table)) == null) {
                FMDMAttributeDTO fMDMAttributeDTO = new FMDMAttributeDTO();
                fMDMAttributeDTO.setEntityId(table);
                fMDMAttributeDTO.setFormSchemeKey(formSchemeKey);
                List attributes = this.ifmdmAttributeService.list(fMDMAttributeDTO);
                if (CollectionUtils.isEmpty(attributes) || !attributes.stream().filter(e -> e.getCode().equals(zb)).findFirst().isPresent()) {
                    return "\u7b2c" + i + "\u884c\uff0c\u5b58\u50a8\u8868\uff1a" + table + " \u4e0d\u5b58\u5728\uff1b";
                }
            } else {
                FieldDefine newZb = this.dataRuntimeCtrl.queryFieldByCodeInTable(zb, tableDefine.getKey());
                if (newZb == null) {
                    return "\u7b2c" + i + "\u884c\uff0c\u6307\u6807\uff1a" + zb + " \u4e0d\u5b58\u5728\uff1b";
                }
            }
            ZBMapping zm = new ZBMapping();
            zm.setKey(UUID.randomUUID().toString());
            zm.setMsKey(msKey);
            String fRegion = (String)fieldRegionMap.get(zb);
            zm.setRegionCode(hasRegionCol ? region : (StringUtils.hasText(fRegion) ? fRegion : ""));
            zm.setForm(formCode);
            zm.setZbCode(zb);
            zm.setTable(table);
            zm.setMapping(saveMapping);
            zms.add(zm);
            mappingList.add(saveMapping);
        }
        if (CollectionUtils.isEmpty(zms)) {
            return "\u6620\u5c04\u4e3a\u7a7a\uff1b";
        }
        List<ZBMapping> list = this.zBMappingService.findByMSAndMapping(msKey, mappingList);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, List<ZBMapping>> mMap = list.stream().collect(Collectors.groupingBy(ZBMapping::getMapping));
            HashMap cache = new HashMap();
            HashMap<String, FormDefine> cFormMap = new HashMap<String, FormDefine>();
            ArrayList<String> invalidList = new ArrayList<String>();
            for (ZBMapping zmv : zms) {
                String form = zmv.getForm();
                String table = zmv.getTable();
                String zb = zmv.getZbCode();
                List<ZBMapping> sameList = mMap.get(zmv.getMapping());
                if (CollectionUtils.isEmpty(sameList)) continue;
                for (ZBMapping szm : sameList) {
                    HashMap<String, String> zbCache;
                    if (form.equals(szm.getForm()) || table.equals(szm.getTable()) && zb.equals(szm.getZbCode())) continue;
                    HashMap regionCache = (HashMap)cache.get(szm.getForm());
                    if (regionCache == null) {
                        FormDefine formDefine = this.runtimeCtrl.queryFormByCodeInScheme(formSchemeKey, szm.getForm());
                        if (formDefine == null) {
                            invalidList.add(szm.getKey());
                            cache.put(szm.getForm(), new HashMap());
                            continue;
                        }
                        regionCache = new HashMap();
                        cache.put(szm.getForm(), regionCache);
                        cFormMap.put(formDefine.getFormCode(), formDefine);
                    }
                    if ((zbCache = (HashMap<String, String>)regionCache.get(szm.getRegionCode())) == null) {
                        if (cFormMap.get(szm.getForm()) == null) {
                            invalidList.add(szm.getKey());
                            continue;
                        }
                        DataRegionDefine dataRegion = this.runtimeCtrl.getDataRegion(szm.getRegionCode(), ((FormDefine)cFormMap.get(szm.getForm())).getKey(), formSchemeKey);
                        if (dataRegion == null) {
                            invalidList.add(szm.getKey());
                            regionCache.put(szm.getRegionCode(), new HashMap());
                            continue;
                        }
                        zbCache = new HashMap<String, String>();
                        regionCache.put(szm.getRegionCode(), zbCache);
                        List keys = this.runtimeCtrl.getFieldKeysInRegion(dataRegion.getKey());
                        for (String key1 : keys) {
                            FieldDefine fieldDefine = this.runtimeCtrl.queryFieldDefine(key1);
                            if (fieldDefine == null) continue;
                            zbCache.put(fieldDefine.getCode(), fieldDefine.getTitle());
                        }
                    }
                    if (zbCache.containsKey(szm.getZbCode())) {
                        return "\u6620\u5c04\u91cd\u590d\uff1a" + szm.getMapping() + "\uff1b";
                    }
                    invalidList.add(szm.getKey());
                }
            }
            if (!CollectionUtils.isEmpty(invalidList)) {
                this.zBMappingService.deleteByKeys(invalidList);
            }
        }
        this.zBMappingService.clearByMSAndForm(msKey, formCode);
        this.zBMappingService.save(msKey, formCode, zms);
        return null;
    }

    private void buildFRMap(boolean hasRegionCol, FormDefine form, Map<String, String> fieldRegionMap) throws Exception {
        if (hasRegionCol) {
            return;
        }
        List allRegions = this.runtimeCtrl.getAllRegionsInForm(form.getKey());
        for (DataRegionDefine region : allRegions) {
            List fieldKeys = this.runtimeCtrl.getFieldKeysInRegion(region.getKey());
            List fields = this.dataRuntimeCtrl.queryFieldDefines((Collection)fieldKeys);
            for (FieldDefine field : fields) {
                fieldRegionMap.put(field.getCode(), region.getCode());
            }
        }
    }

    private Result importXLS(String msKey, String formSchemeKey, String formCode, MultipartFile file) {
        int num;
        MappingScheme scheme = this.service.getSchemeByKey(msKey);
        boolean jio = this.jioService.isJIOScheme(scheme);
        StringBuffer error = new StringBuffer();
        int errorNum = 0;
        try (InputStream in = file.getInputStream();){
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            num = workbook.getNumberOfSheets();
            for (int i = 0; i < workbook.getNumberOfSheets(); ++i) {
                Sheet sheet = workbook.getSheetAt(i);
                String msg = this.importSheet(sheet, msKey, formSchemeKey, sheet.getSheetName().split(" ")[0], jio);
                if (!StringUtils.hasText(msg)) continue;
                ++errorNum;
                error.append(sheet.getSheetName()).append(" \u5bfc\u5165\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a").append(msg).append("\n");
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return Result.error(null, "\u5bfc\u5165\u51fa\u73b0\u9519\u8bef\uff01");
        }
        if (error.toString().length() > 0) {
            StringBuffer msg = new StringBuffer("\u5bfc\u5165\u62a5\u8868" + num + " \uff0c\u6210\u529f\uff1a" + (num - errorNum) + " \uff0c\u5931\u8d25\uff1a" + errorNum + "\n");
            return Result.error(null, msg.append(error).toString());
        }
        return Result.success(null, "\u5bfc\u5165\u6210\u529f");
    }

    @Override
    public void export(String msKey, String formSchemeKey, List<String> formCodes, HttpServletResponse response) throws Exception {
        MappingScheme scheme = this.service.getSchemeByKey(msKey);
        boolean jio = this.jioService.isJIOScheme(scheme);
        HSSFWorkbook workbook = new HSSFWorkbook();
        for (String formCode : formCodes) {
            FormDefine form = this.runtimeCtrl.queryFormByCodeInScheme(formSchemeKey, formCode);
            boolean hasRegionCol = this.hasRegionCol(form.getKey());
            Sheet sheet = workbook.createSheet(form.getFormCode() + " " + form.getTitle());
            this.createHead(workbook, sheet, hasRegionCol);
            this.createMapping(sheet, msKey, form, formSchemeKey, jio, hasRegionCol);
        }
        try {
            String fileName = "\u6307\u6807\u6620\u5c04.xls";
            ImpExpUtils.export(fileName, response, workbook);
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private boolean hasRegionCol(String formKey) {
        boolean hasRegionCol = false;
        HashSet fieldSet = new HashSet();
        List allRegions = this.runtimeCtrl.getAllRegionsInForm(formKey);
        for (DataRegionDefine region : allRegions) {
            List list = this.runtimeCtrl.getFieldKeysInRegion(region.getKey());
            if (fieldSet.addAll(new HashSet(list))) continue;
            hasRegionCol = true;
            break;
        }
        return hasRegionCol;
    }

    private void createHead(Workbook workbook, Sheet sheet, boolean hasRegionCol) {
        Row head = sheet.createRow(0);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setFontName("\u9ed1\u4f53");
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setFont(font);
        String[] col = hasRegionCol ? new String[]{"\u533a\u57df", "\u5b58\u50a8\u8868", "\u6307\u6807", "\u6620\u5c04"} : new String[]{"\u5b58\u50a8\u8868", "\u6307\u6807", "\u6620\u5c04"};
        for (int i = 0; i < col.length; ++i) {
            Cell cell = head.createCell(i);
            cell.setCellValue(col[i]);
            cell.setCellStyle(headStyle);
            sheet.setColumnWidth(i, 6000);
        }
    }

    private void createMapping(Sheet sheet, String msKey, FormDefine form, String formSchemeKey, boolean jio, boolean hasRegionCol) throws Exception {
        List<ZBMapping> zbMappings = this.zBMappingService.findByMSAndForm(msKey, form.getFormCode());
        if (FormType.FORM_TYPE_NEWFMDM.equals((Object)form.getFormType())) {
            FormSchemeDefine formSchemeDefine = this.runtimeCtrl.getFormScheme(formSchemeKey);
            TaskDefine taskDefine = this.runtimeCtrl.queryTaskDefine(formSchemeDefine.getTaskKey());
            FMDMAttributeDTO fMDMAttributeDTO = new FMDMAttributeDTO();
            fMDMAttributeDTO.setEntityId(taskDefine.getDw());
            fMDMAttributeDTO.setFormSchemeKey(formSchemeDefine.getKey());
            List attributes = this.ifmdmAttributeService.list(fMDMAttributeDTO);
            List allLinks = this.runTimeAuthViewController.getAllLinksInForm(form.getKey());
            List sortLinks = allLinks.stream().sorted((o1, o2) -> {
                if (o1.getPosX() > o2.getPosX()) {
                    return 1;
                }
                if (o1.getPosX() == o2.getPosX()) {
                    if (o1.getPosY() > o2.getPosY()) {
                        return 1;
                    }
                    if (o1.getPosY() == o2.getPosY()) {
                        return 0;
                    }
                }
                return -1;
            }).collect(Collectors.toList());
            int rowNum = 1;
            for (DataLinkDefine link : sortLinks) {
                Optional<IFMDMAttribute> findAttribute = link.getType().getValue() == DataLinkType.DATA_LINK_TYPE_FMDM.getValue() ? attributes.stream().filter(e -> e.getCode().equals(link.getLinkExpression())).findFirst() : attributes.stream().filter(e -> e.getZBKey().equals(link.getLinkExpression())).findFirst();
                if (!findAttribute.isPresent()) continue;
                IFMDMAttribute attribute = findAttribute.get();
                Row row = sheet.createRow(rowNum++);
                Cell cell = row.createCell(0);
                TableModelDefine table = this.dataModelService.getTableModelDefineById(attribute.getTableID());
                cell.setCellValue(table == null ? attribute.getTableID() : table.getCode());
                cell = row.createCell(1);
                cell.setCellValue(attribute.getCode());
                this.buildMappingCell(jio, zbMappings, attribute.getCode(), row);
            }
        } else {
            Map<String, ZBMapping> zbMappingMap = zbMappings.stream().collect(Collectors.toMap(e -> e.getRegionCode() + e.getZbCode(), e -> e));
            List<Object> expList = new ArrayList();
            List regions = this.runtimeCtrl.getAllRegionsInForm(form.getKey());
            for (DataRegionDefine region : regions) {
                List fieldKeys = this.runtimeCtrl.getFieldKeysInRegion(region.getKey());
                List fields = this.dataRuntimeCtrl.queryFieldDefines((Collection)fieldKeys);
                for (FieldDefine fieldDefine : fields) {
                    TableDefine tableDefine;
                    if (!StringUtils.hasText(fieldDefine.getOwnerTableKey()) || (tableDefine = this.dataRuntimeCtrl.queryTableDefine(fieldDefine.getOwnerTableKey())) == null) continue;
                    ExpZBMapping exp = new ExpZBMapping();
                    exp.setRegion(region.getCode());
                    exp.setTable(tableDefine.getCode());
                    exp.setZb(fieldDefine.getCode());
                    if (zbMappingMap.containsKey(region.getCode() + fieldDefine.getCode())) {
                        ZBMapping zbMapping = zbMappingMap.get(region.getCode() + fieldDefine.getCode());
                        exp.setMapping(jio && StringUtils.hasText(zbMapping.getMapping()) ? this.getShowText(zbMapping.getMapping()) : zbMapping.getMapping());
                        zbMappingMap.remove(region.getCode() + fieldDefine.getCode());
                    }
                    expList.add(exp);
                }
            }
            expList = expList.stream().sorted(Comparator.comparing(e -> e.getRegion() + e.getTable() + e.getZb())).collect(Collectors.toList());
            for (int i = 0; i < expList.size(); ++i) {
                String[] stringArray;
                ExpZBMapping exp = (ExpZBMapping)expList.get(i);
                Row row = sheet.createRow(i + 1);
                if (hasRegionCol) {
                    String[] stringArray2 = new String[4];
                    stringArray2[0] = exp.getRegion();
                    stringArray2[1] = exp.getTable();
                    stringArray2[2] = exp.getZb();
                    stringArray = stringArray2;
                    stringArray2[3] = exp.getMapping();
                } else {
                    String[] stringArray3 = new String[3];
                    stringArray3[0] = exp.getTable();
                    stringArray3[1] = exp.getZb();
                    stringArray = stringArray3;
                    stringArray3[2] = exp.getMapping();
                }
                String[] col = stringArray;
                for (int j = 0; j < col.length; ++j) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(col[j]);
                }
            }
            int rowNum = sheet.getLastRowNum();
            for (ZBMapping zm : zbMappingMap.values()) {
                String[] stringArray;
                Row row = sheet.createRow(++rowNum);
                if (hasRegionCol) {
                    String[] stringArray4 = new String[4];
                    stringArray4[0] = zm.getRegionCode();
                    stringArray4[1] = zm.getTable();
                    stringArray4[2] = zm.getZbCode();
                    stringArray = stringArray4;
                    stringArray4[3] = zm.getMapping();
                } else {
                    String[] stringArray5 = new String[3];
                    stringArray5[0] = zm.getTable();
                    stringArray5[1] = zm.getZbCode();
                    stringArray = stringArray5;
                    stringArray5[2] = zm.getMapping();
                }
                String[] col = stringArray;
                for (int j = 0; j < col.length + 1; ++j) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(j == col.length ? "\u8868\u5355\u4e2d\u4e0d\u5b58\u5728\u7684\u6307\u6807" : col[j]);
                }
            }
        }
    }

    private void buildMappingCell(boolean jio, List<ZBMapping> zbMappings, String code, Row row) {
        Optional<ZBMapping> zbMapping = zbMappings.stream().filter(e -> e.getZbCode().equals(code)).findFirst();
        if (zbMapping.isPresent()) {
            zbMappings.remove(zbMapping.get());
            Cell cell = row.createCell(2);
            String mapping = zbMapping.get().getMapping();
            cell.setCellValue(jio && StringUtils.hasText(mapping) ? this.getShowText(mapping) : mapping);
        }
    }

    private String getShowText(String mapping) {
        String pattern = "^.+\\[.+\\]$";
        if (mapping.matches(pattern)) {
            return mapping.substring(mapping.indexOf(91) + 1, mapping.indexOf(93));
        }
        return mapping;
    }

    private String getSaveText(String formCode, String mapping) {
        String pattern = "^.+\\[.+\\]$";
        if (!mapping.matches(pattern)) {
            return formCode + "[" + mapping + "]";
        }
        return mapping;
    }
}

