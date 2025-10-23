/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.mapping.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nr.mapping.bean.BaseDataMapping;
import com.jiuqi.nr.mapping.service.BaseDataMappingService;
import com.jiuqi.nr.mapping.util.ImpExpUtils;
import com.jiuqi.nr.mapping.web.FormulaMappingController;
import com.jiuqi.nr.mapping.web.vo.BaseDataVO;
import com.jiuqi.nr.mapping.web.vo.CommonTreeNode;
import com.jiuqi.nr.mapping.web.vo.Result;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/mapping/baseData"})
@Api(tags={"\u679a\u4e3e\u6620\u5c04"})
public class BaseDataMappingController {
    protected final Logger logger = LoggerFactory.getLogger(FormulaMappingController.class);
    @Autowired
    private BaseDataMappingService baseDataMappingService;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeCtrl;
    @Autowired
    private IRunTimeViewController runtimeCtrl;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private BaseDataDefineClient basedataDefineClient;

    @GetMapping(value={"/query-all/{msKey}/{formSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u6620\u5c04\u548c\u8868\u6837\u6570\u636e")
    public Map<String, Object> getAllBaseData(@PathVariable String msKey, @PathVariable String formSchemeKey) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        List<BaseDataVO> baseDataVOList = this.queryAllBaseData(msKey, formSchemeKey);
        List<CommonTreeNode> baseDataTree = this.buildBaseDataTree(baseDataVOList);
        map.put("data", baseDataVOList);
        map.put("tree", baseDataTree);
        return map;
    }

    private List<BaseDataVO> queryAllBaseData(String msKey, String formSchemeKey) throws Exception {
        ArrayList<BaseDataVO> baseDataVOList = new ArrayList<BaseDataVO>();
        List<BaseDataMapping> mappings = this.baseDataMappingService.getBaseDataMapping(msKey);
        ArrayList formList = new ArrayList();
        List allFormGroups = this.runtimeCtrl.getAllFormGroupsInFormScheme(formSchemeKey);
        for (Object formGroup : allFormGroups) {
            List forms = this.runtimeCtrl.getAllFormsInGroup(formGroup.getKey());
            formList.addAll(forms);
        }
        ArrayList fieldKeys = new ArrayList();
        for (FormDefine formDefine : formList) {
            List keys = this.runtimeCtrl.getFieldKeysInForm(formDefine.getKey());
            fieldKeys.addAll(keys);
        }
        List fields = this.dataRuntimeCtrl.queryFieldDefinesInRange(fieldKeys);
        HashSet<String> set = new HashSet<String>();
        for (FieldDefine field : fields) {
            String entityId = field.getEntityKey();
            if (!StringUtils.hasText(entityId) || !BaseDataAdapterUtil.isBaseData((String)entityId)) continue;
            String entityCode = this.entityMetaService.getEntityCode(entityId);
            BaseDataDefineDTO dto = new BaseDataDefineDTO();
            dto.setName(entityCode);
            BaseDataDefineDO defineDO = this.basedataDefineClient.get(dto);
            if (!set.add(defineDO.getName())) continue;
            BaseDataVO baseDataVO = new BaseDataVO();
            baseDataVO.setCode(defineDO.getName());
            baseDataVO.setTitle(defineDO.getTitle());
            Optional<BaseDataMapping> mapping = mappings.stream().filter(e -> e.getBaseDataCode().equals(defineDO.getName())).findFirst();
            if (mapping.isPresent()) {
                baseDataVO.setMpCode(mapping.get().getMappingCode());
                baseDataVO.setMpTitle(mapping.get().getMappingTitle());
            }
            baseDataVOList.add(baseDataVO);
        }
        return baseDataVOList;
    }

    private List<CommonTreeNode> buildBaseDataTree(List<BaseDataVO> baseDataVOList) {
        ArrayList<CommonTreeNode> baseDataTree = new ArrayList<CommonTreeNode>();
        CommonTreeNode rootNode = new CommonTreeNode();
        rootNode.setCode("-");
        rootNode.setTitle("\u679a\u4e3e\u5b57\u5178");
        rootNode.setExpand(true);
        rootNode.setSelected(true);
        baseDataTree.add(rootNode);
        for (BaseDataVO vo : baseDataVOList) {
            rootNode.addChildren(new CommonTreeNode(vo.getCode(), vo.getTitle()));
        }
        return baseDataTree;
    }

    @GetMapping(value={"/query-item/{msKey}/{tableName}"})
    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u4e0b\u7684\u6240\u6709\u679a\u4e3e\u9879")
    public List<BaseDataVO> queryBaseDataItem(@PathVariable String msKey, @PathVariable String tableName) throws Exception {
        return this.baseDataMappingService.getBaseDataItem(msKey, tableName);
    }

    @PostMapping(value={"/save/{mappingSchemeId}"})
    @ApiOperation(value="\u4fdd\u5b58\u57fa\u7840\u6570\u636e\u6620\u5c04")
    public void save(@PathVariable String mappingSchemeId, @RequestBody List<BaseDataVO> vos) {
        ArrayList<BaseDataMapping> list = new ArrayList<BaseDataMapping>();
        for (BaseDataVO vo : vos) {
            BaseDataMapping bdMapping = new BaseDataMapping();
            bdMapping.setKey(UUID.randomUUID().toString());
            bdMapping.setMsKey(mappingSchemeId);
            bdMapping.setBaseDataCode(vo.getCode());
            if (!StringUtils.hasText(vo.getMpCode())) continue;
            bdMapping.setMappingCode(vo.getMpCode());
            bdMapping.setMappingTitle(vo.getMpTitle());
            list.add(bdMapping);
        }
        this.baseDataMappingService.saveBaseDataMapping(mappingSchemeId, list);
    }

    @PostMapping(value={"/saveItem/{mappingSchemeId}/{baseDataCode}"})
    @ApiOperation(value="\u4fdd\u5b58\u57fa\u7840\u6570\u636e\u9879\u6620\u5c04")
    public void save(@PathVariable String mappingSchemeId, @PathVariable String baseDataCode, @RequestBody List<BaseDataVO> vos) {
        ArrayList<BaseDataItemMapping> list = new ArrayList<BaseDataItemMapping>();
        for (BaseDataVO vo : vos) {
            BaseDataItemMapping bdMapping = new BaseDataItemMapping();
            bdMapping.setKey(UUID.randomUUID().toString());
            bdMapping.setBaseDataCode(baseDataCode);
            bdMapping.setMsKey(mappingSchemeId);
            bdMapping.setBaseItemCode(vo.getCode());
            if (!StringUtils.hasText(vo.getMpCode())) continue;
            bdMapping.setMappingCode(vo.getMpCode());
            bdMapping.setMappingTitle(vo.getMpTitle());
            list.add(bdMapping);
        }
        this.baseDataMappingService.saveBaseDataItemMapping(mappingSchemeId, baseDataCode, list);
    }

    @GetMapping(value={"/clear/{msKey}"})
    @ApiOperation(value="\u6e05\u7a7a\u57fa\u7840\u6570\u636e\u6620\u5c04")
    public void clearBaseData(@PathVariable String msKey) throws Exception {
        this.baseDataMappingService.clearByMS(msKey);
    }

    @GetMapping(value={"/clearItem/{msKey}/{baseDataCode}"})
    @ApiOperation(value="\u6e05\u7a7a\u57fa\u7840\u6570\u636e\u9879\u6620\u5c04")
    public void clearBaseDataItem(@PathVariable String msKey, @PathVariable String baseDataCode) throws Exception {
        this.baseDataMappingService.clearByMSAndBaseData(msKey, baseDataCode);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @PostMapping(value={"/import/{msKey}/{formSchemeKey}"})
    @ApiOperation(value="\u5bfc\u5165\u6570\u636e")
    public Result importData(@PathVariable String msKey, @PathVariable String formSchemeKey, @RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws JQException {
        try (InputStream in = file.getInputStream();){
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            Sheet sheet = workbook.getSheetAt(0);
            int size = sheet.getLastRowNum();
            if (size == 0) {
                Result.error(null, "\u7a7a\u6587\u4ef6\uff01");
            }
            int begin = 0;
            String firstValue = ImpExpUtils.getStringValue(sheet.getRow(0).getCell(0));
            if ("\u6807\u8bc6".equals(firstValue)) {
                begin = 1;
            }
            List baseDataCodes = this.queryAllBaseData(msKey, formSchemeKey).stream().map(BaseDataVO::getCode).collect(Collectors.toList());
            ArrayList<BaseDataMapping> bms = new ArrayList<BaseDataMapping>();
            HashSet<String> newBaseDataCodes = new HashSet<String>();
            for (int i = begin; i <= size; ++i) {
                Result result;
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String baseDataCode = ImpExpUtils.getStringValue(row.getCell(0));
                if (!StringUtils.hasText(baseDataCode)) {
                    result = Result.error(null, "\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
                    return result;
                }
                if (!baseDataCodes.contains(baseDataCode)) {
                    result = Result.error(null, "\u6807\u8bc6\u4e0d\u5b58\u5728:" + baseDataCode);
                    return result;
                }
                if (!newBaseDataCodes.add(baseDataCode)) {
                    result = Result.error(null, "\u6807\u8bc6\u91cd\u590d:" + baseDataCode);
                    return result;
                }
                String mappingCode = ImpExpUtils.getStringValue(row.getCell(2));
                if (!StringUtils.hasText(mappingCode)) continue;
                BaseDataMapping bm = new BaseDataMapping();
                bm.setKey(UUID.randomUUID().toString());
                bm.setMsKey(msKey);
                bm.setBaseDataCode(baseDataCode);
                bm.setMappingCode(mappingCode);
                bm.setMappingTitle(ImpExpUtils.getStringValue(row.getCell(3)));
                bms.add(bm);
            }
            this.baseDataMappingService.saveBaseDataMapping(msKey, bms);
            return Result.success(null, "\u5bfc\u5165\u6210\u529f");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return Result.success(null, "\u5bfc\u5165\u6210\u529f");
    }

    @GetMapping(value={"/export/{msKey}/{formSchemeKey}"})
    @ApiOperation(value="\u5bfc\u51faexcel")
    public void export(@PathVariable String msKey, @PathVariable String formSchemeKey, HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("\u679a\u4e3e\u6620\u5c04");
        List<BaseDataVO> baseDataVOs = this.queryAllBaseData(msKey, formSchemeKey);
        this.createHead(workbook, sheet);
        this.createMapping(sheet, baseDataVOs);
        try {
            String fileName = "\u679a\u4e3e\u6620\u5c04.xls";
            ImpExpUtils.export(fileName, response, workbook);
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @PostMapping(value={"/importItem/{msKey}/{baseDataCode}"})
    @ApiOperation(value="\u5bfc\u5165\u6570\u636e")
    public Result importItemData(@PathVariable String msKey, @PathVariable String baseDataCode, @RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws JQException {
        try (InputStream in = file.getInputStream();){
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            Sheet sheet = workbook.getSheetAt(0);
            int size = sheet.getLastRowNum();
            if (size == 0) {
                Result result = Result.success(null, "\u7a7a\u6587\u4ef6\uff01");
                return result;
            }
            int begin = 0;
            String firstValue = ImpExpUtils.getStringValue(sheet.getRow(0).getCell(0));
            if ("\u6807\u8bc6".equals(firstValue)) {
                begin = 1;
            }
            ArrayList<BaseDataItemMapping> bms = new ArrayList<BaseDataItemMapping>();
            ArrayList<String> newBaseDataItemCodes = new ArrayList<String>();
            List baseDataItemCodes = this.baseDataMappingService.getBaseDataItem(msKey, baseDataCode).stream().map(BaseDataVO::getCode).collect(Collectors.toList());
            for (int i = begin; i <= size; ++i) {
                Result result;
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String baseDataItemCode = ImpExpUtils.getStringValue(row.getCell(0));
                if (!StringUtils.hasText(baseDataItemCode)) {
                    result = Result.error(null, "\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
                    return result;
                }
                if (!baseDataItemCodes.contains(baseDataItemCode)) {
                    result = Result.error(null, "\u6807\u8bc6\u4e0d\u5b58\u5728\uff1a" + baseDataItemCode);
                    return result;
                }
                if (newBaseDataItemCodes.contains(baseDataItemCode)) {
                    result = Result.error(null, "\u6807\u8bc6\u91cd\u590d\uff1a" + baseDataItemCode);
                    return result;
                }
                newBaseDataItemCodes.add(baseDataItemCode);
                String mappingCode = ImpExpUtils.getStringValue(row.getCell(2));
                if (!StringUtils.hasText(mappingCode)) continue;
                BaseDataItemMapping bm = new BaseDataItemMapping();
                bm.setKey(UUID.randomUUID().toString());
                bm.setMsKey(msKey);
                bm.setBaseDataCode(baseDataCode);
                bm.setBaseItemCode(baseDataItemCode);
                bm.setMappingCode(mappingCode);
                bm.setMappingTitle(ImpExpUtils.getStringValue(row.getCell(3)));
                bms.add(bm);
            }
            this.baseDataMappingService.saveBaseDataItemMapping(msKey, baseDataCode, bms);
            return Result.success(null, "\u5bfc\u5165\u6210\u529f");
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
        }
        return Result.success(null, "\u5bfc\u5165\u6210\u529f");
    }

    @GetMapping(value={"/exportItem/{msKey}/{baseDataCode}"})
    @ApiOperation(value="\u5bfc\u51faexcel")
    public void exportItem(@PathVariable String msKey, @PathVariable String baseDataCode, HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("\u679a\u4e3e\u6620\u5c04");
        this.createHead(workbook, sheet);
        this.createMapping(sheet, this.baseDataMappingService.getBaseDataItem(msKey, baseDataCode));
        try {
            String fileName = "\u679a\u4e3e\u6620\u5c04.xls";
            ImpExpUtils.export(fileName, response, workbook);
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private void createHead(Workbook workbook, Sheet sheet) {
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
        Cell cell = head.createCell(0);
        cell.setCellValue("\u6807\u8bc6");
        cell.setCellStyle(headStyle);
        cell = head.createCell(1);
        cell.setCellValue("\u540d\u79f0");
        cell.setCellStyle(headStyle);
        cell = head.createCell(2);
        cell.setCellValue("\u76ee\u6807\u6807\u8bc6");
        cell.setCellStyle(headStyle);
        cell = head.createCell(3);
        cell.setCellValue("\u76ee\u6807\u540d\u79f0");
        cell.setCellStyle(headStyle);
        int defaultWidth = 6000;
        sheet.setColumnWidth(0, defaultWidth);
        sheet.setColumnWidth(1, defaultWidth);
        sheet.setColumnWidth(2, defaultWidth);
        sheet.setColumnWidth(3, defaultWidth);
    }

    private void createMapping(Sheet sheet, List<BaseDataVO> baseDataVOs) throws Exception {
        int size = baseDataVOs.size();
        for (int i = 0; i < size; ++i) {
            Row row = sheet.createRow(i + 1);
            BaseDataVO um = baseDataVOs.get(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(um.getCode());
            cell = row.createCell(1);
            cell.setCellValue(um.getTitle());
            cell = row.createCell(2);
            cell.setCellValue(um.getMpCode());
            cell = row.createCell(3);
            cell.setCellValue(um.getMpTitle());
        }
    }
}

