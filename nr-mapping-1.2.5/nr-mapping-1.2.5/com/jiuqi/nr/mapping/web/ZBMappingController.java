/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
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

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.mapping.bean.ZBMapping;
import com.jiuqi.nr.mapping.service.ZBMappingService;
import com.jiuqi.nr.mapping.util.ImpExpUtils;
import com.jiuqi.nr.mapping.web.vo.ReportFormTree;
import com.jiuqi.nr.mapping.web.vo.Result;
import com.jiuqi.nr.mapping.web.vo.ZBMappingVO;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.concurrent.atomic.AtomicBoolean;
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
import org.springframework.beans.BeanUtils;
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
@RequestMapping(value={"/api/mapping/zb"})
@Api(tags={"\u6307\u6807\u6620\u5c04"})
public class ZBMappingController {
    protected final Logger logger = LoggerFactory.getLogger(ZBMappingController.class);
    @Autowired
    private IRunTimeViewController runtimeCtrl;
    @Autowired
    private ZBMappingService zBMappingService;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeCtrl;
    @Autowired
    private IFMDMAttributeService ifmdmAttributeService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value={"/query-form-tree/{schemeKey}"})
    @ApiOperation(value="\u6784\u9020\u62a5\u8868\u6811\u5f62")
    public List<ReportFormTree> buildFormTree(@PathVariable(value="schemeKey") String schemeKey) {
        ArrayList<ReportFormTree> tree = new ArrayList<ReportFormTree>();
        AtomicBoolean flag = new AtomicBoolean(false);
        List<FormGroupDefine> groupDefines = this.runtimeCtrl.queryRootGroupsByFormScheme(schemeKey).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
        groupDefines.forEach(item -> {
            ReportFormTree node = new ReportFormTree();
            node.setId(item.getKey());
            node.setTitle(item.getTitle());
            node.setCode(item.getCode());
            node.setType("NODE_TYPE_GROUP");
            List<Object> forms = new ArrayList();
            try {
                forms = this.runtimeCtrl.getAllFormsInGroup(item.getKey(), true).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            node.setExpand(forms.size() > 0);
            ArrayList<ReportFormTree> childs = new ArrayList<ReportFormTree>();
            forms.forEach(f -> {
                ReportFormTree children = new ReportFormTree();
                children.setId(f.getKey());
                children.setTitle(f.getTitle());
                children.setCode(f.getFormCode());
                children.setType("NODE_TYPE_FORM");
                children.setExpand(false);
                children.setFormType(f.getFormType().getValue());
                childs.add(children);
            });
            node.setChildren(childs);
            if (childs.size() > 0 && !flag.get()) {
                ((ReportFormTree)childs.get(0)).setSelected(true);
                flag.set(true);
            }
            tree.add(node);
        });
        return tree;
    }

    @GetMapping(value={"/query-form-data/{msKey}/{formSchemeKey}/{formCode}"})
    @ApiOperation(value="\u83b7\u53d6\u6620\u5c04\u548c\u8868\u6837\u6570\u636e")
    public Map<String, Object> getFormData(@PathVariable String msKey, @PathVariable String formSchemeKey, @PathVariable String formCode) throws Exception {
        ArrayList<ZBMappingVO> formFields = new ArrayList<ZBMappingVO>();
        FormDefine form = this.runtimeCtrl.queryFormByCodeInScheme(formSchemeKey, formCode);
        String formKey = form.getKey();
        List keys = this.runtimeCtrl.getFieldKeysInForm(formKey);
        boolean isFMDM = FormType.FORM_TYPE_NEWFMDM.equals((Object)form.getFormType());
        List attributes = new ArrayList();
        if (isFMDM) {
            FormSchemeDefine formSchemeDefine = this.runtimeCtrl.getFormScheme(form.getFormScheme());
            TaskDefine taskDefine = this.runtimeCtrl.queryTaskDefine(formSchemeDefine.getTaskKey());
            FMDMAttributeDTO fMDMAttributeDTO = new FMDMAttributeDTO();
            fMDMAttributeDTO.setEntityId(taskDefine.getDw());
            fMDMAttributeDTO.setFormSchemeKey(formSchemeDefine.getKey());
            attributes = this.ifmdmAttributeService.list(fMDMAttributeDTO);
        }
        List runtimeFields = this.dataRuntimeCtrl.queryFieldDefinesInRange((Collection)keys);
        List<ZBMapping> zbMappings = this.zBMappingService.findByMSAndForm(msKey, formCode);
        List allLinks = this.runTimeAuthViewController.getAllLinksInForm(formKey);
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
        ArrayList finalAttributes = attributes;
        for (DataLinkDefine link : sortLinks) {
            String zbCode;
            String tableCode;
            Optional<ZBMapping> zbMapping2;
            ZBMappingVO infoVO = new ZBMappingVO();
            infoVO.setCol(link.getPosX());
            infoVO.setRow(link.getPosY());
            infoVO.setRegionKey(link.getRegionKey());
            if (this.fmdmField(isFMDM, link)) {
                Optional<IFMDMAttribute> findAttribute = finalAttributes.stream().filter(e -> e.getCode().equals(link.getLinkExpression())).findFirst();
                if (!findAttribute.isPresent()) continue;
                zbMapping2 = zbMappings.stream().filter(e -> e.getZbCode().equals(((IFMDMAttribute)findAttribute.get()).getCode())).findFirst();
                if (zbMapping2.isPresent()) {
                    zbMappings.remove(zbMapping2.get());
                    BeanUtils.copyProperties(zbMapping2.get(), infoVO);
                    formFields.add(infoVO);
                    continue;
                }
                IFMDMAttribute attribute = findAttribute.get();
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(attribute.getTableID());
                tableCode = tableModelDefine != null ? tableModelDefine.getCode() : attribute.getTableID();
                zbCode = attribute.getCode();
            } else {
                Optional<FieldDefine> findField = runtimeFields.stream().filter(e -> e.getKey().equals(link.getLinkExpression())).findFirst();
                if (!findField.isPresent()) continue;
                zbMapping2 = zbMappings.stream().filter(e -> e.getZbCode().equals(((FieldDefine)findField.get()).getCode())).findFirst();
                if (zbMapping2.isPresent()) {
                    zbMappings.remove(zbMapping2.get());
                    BeanUtils.copyProperties(zbMapping2.get(), infoVO);
                    formFields.add(infoVO);
                    continue;
                }
                TableDefine tableDefine = this.dataRuntimeCtrl.queryTableDefine(findField.get().getOwnerTableKey());
                tableCode = tableDefine != null ? tableDefine.getCode() : findField.get().getOwnerTableKey();
                zbCode = findField.get().getCode();
            }
            infoVO.setZbCode(zbCode);
            infoVO.setTable(tableCode);
            infoVO.setForm(form.getFormCode());
            formFields.add(infoVO);
        }
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        Grid2Data gridData = this.getGridData(formKey);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)module);
        this.objectMapper.registerModule((Module)module);
        String fromDataStr = this.objectMapper.writeValueAsString((Object)gridData);
        HashMap<String, Object> map = new HashMap<String, Object>();
        List historyFields = zbMappings.stream().map(zbMapping -> {
            ZBMappingVO zbVO = new ZBMappingVO((ZBMapping)zbMapping);
            zbVO.setRegionKey(zbMapping.getRegionKey());
            zbVO.setIsHistory(true);
            return zbVO;
        }).collect(Collectors.toList());
        map.put("griddata", fromDataStr);
        map.put("links", sortLinks);
        map.put("mappings", formFields);
        map.put("historyMappings", historyFields);
        return map;
    }

    @PostMapping(value={"/save/{mappingSchemeId}/{formCode}"})
    @ApiOperation(value="\u4fdd\u5b58\u6307\u6807\u6620\u5c04")
    public void save(@PathVariable String mappingSchemeId, @PathVariable String formCode, @RequestBody List<ZBMapping> zbMappings) {
        this.zBMappingService.save(mappingSchemeId, formCode, zbMappings);
    }

    @GetMapping(value={"/clear/{msKey}/{formCode}"})
    @ApiOperation(value="\u6e05\u7a7a\u5f53\u524d\u8868\u7684\u6620\u5c04")
    public void getFormData(@PathVariable String msKey, @PathVariable String formCode) {
        this.zBMappingService.clearByMSAndForm(msKey, formCode);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @PostMapping(value={"/import/{msKey}/{formCode}"})
    @ApiOperation(value="\u5bfc\u5165\u6570\u636e")
    public Result importData(@PathVariable String msKey, @PathVariable String formCode, @RequestParam(value="file") MultipartFile file, HttpServletRequest request) {
        try (InputStream in = file.getInputStream();){
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            Sheet sheet = workbook.getSheetAt(0);
            int size = sheet.getLastRowNum();
            if (size == 0) {
                Result result = Result.error(null, "\u7a7a\u6587\u4ef6\uff01");
                return result;
            }
            int begin = 0;
            String firstValue = ImpExpUtils.getStringValue(sheet.getRow(0).getCell(0));
            if ("\u5b58\u50a8\u8868".equals(firstValue)) {
                begin = 1;
            }
            HashSet<String> newZbCodes = new HashSet<String>();
            ArrayList<ZBMapping> zms = new ArrayList<ZBMapping>();
            for (int i = begin; i <= size; ++i) {
                String tableKey;
                FieldDefine newZb;
                Result result;
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String zbCode = ImpExpUtils.getStringValue(row.getCell(1));
                if (!StringUtils.hasText(zbCode)) {
                    result = Result.error(null, "\u6307\u6807\u4e0d\u80fd\u4e3a\u7a7a");
                    return result;
                }
                if (!newZbCodes.add(zbCode)) {
                    result = Result.error(null, "\u6307\u6807\u91cd\u590d:" + zbCode);
                    return result;
                }
                String tableCode = ImpExpUtils.getStringValue(row.getCell(0));
                TableDefine table = this.dataRuntimeCtrl.queryTableDefineByCode(tableCode);
                if (table == null || (newZb = this.dataRuntimeCtrl.queryFieldByCodeInTable(zbCode, tableKey = table.getKey())) == null) continue;
                ZBMapping zm = new ZBMapping();
                zm.setKey(UUID.randomUUID().toString());
                zm.setMsKey(msKey);
                zm.setForm(formCode);
                zm.setZbCode(zbCode);
                zm.setTable(tableCode);
                zm.setMapping(ImpExpUtils.getStringValue(row.getCell(2)));
                zms.add(zm);
            }
            this.zBMappingService.clearByMs(msKey);
            this.zBMappingService.save(msKey, formCode, zms);
            return Result.success(null, "\u5bfc\u5165\u6210\u529f");
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
            return Result.success(null, "\u5bfc\u5165\u6210\u529f");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(null, "\u5bfc\u5165\u6210\u529f");
    }

    @PostMapping(value={"/export/{msKey}/{formSchemeKey}"})
    @ApiOperation(value="\u5bfc\u51faexcel")
    public void export(@PathVariable String msKey, @PathVariable String formSchemeKey, @RequestBody List<String> formCodes, HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        for (String formCode : formCodes) {
            FormDefine form = this.runtimeCtrl.queryFormByCodeInScheme(formSchemeKey, formCode);
            Sheet sheet = workbook.createSheet(form.getTitle());
            this.createHead(workbook, sheet);
            this.createMapping(sheet, msKey, form, formSchemeKey);
        }
        try {
            String fileName = "\u6307\u6807\u6620\u5c04.xls";
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
        cell.setCellValue("\u5b58\u50a8\u8868");
        cell.setCellStyle(headStyle);
        cell = head.createCell(1);
        cell.setCellValue("\u6307\u6807");
        cell.setCellStyle(headStyle);
        cell = head.createCell(2);
        cell.setCellValue("\u6620\u5c04");
        cell.setCellStyle(headStyle);
        int defaultWidth = 6000;
        sheet.setColumnWidth(0, defaultWidth);
        sheet.setColumnWidth(1, defaultWidth);
        sheet.setColumnWidth(2, defaultWidth);
    }

    private void createMapping(Sheet sheet, String msKey, FormDefine form, String formSchemeKey) {
        String formCode = form.getFormCode();
        List<ZBMapping> zbMappings = this.zBMappingService.findByMSAndForm(msKey, formCode);
        if (zbMappings.isEmpty()) {
            String formKey = form.getKey();
            boolean isFMDM = FormType.FORM_TYPE_NEWFMDM.equals((Object)form.getFormType());
            if (isFMDM) {
                FormSchemeDefine formSchemeDefine = this.runtimeCtrl.getFormScheme(form.getFormScheme());
                TaskDefine taskDefine = this.runtimeCtrl.queryTaskDefine(formSchemeDefine.getTaskKey());
                FMDMAttributeDTO fMDMAttributeDTO = new FMDMAttributeDTO();
                fMDMAttributeDTO.setEntityId(taskDefine.getDw());
                fMDMAttributeDTO.setFormSchemeKey(formSchemeDefine.getKey());
                List attributes = this.ifmdmAttributeService.list(fMDMAttributeDTO);
                int size = attributes.size();
                for (int i = 0; i < size; ++i) {
                    Row row = sheet.createRow(i + 1);
                    IFMDMAttribute attribute = (IFMDMAttribute)attributes.get(i);
                    Cell cell = row.createCell(0);
                    cell.setCellValue(formCode);
                    cell = row.createCell(1);
                    cell.setCellValue(attribute.getCode());
                }
            } else {
                List keys = this.runtimeCtrl.getFieldKeysInForm(formKey);
                List runtimeFields = this.dataRuntimeCtrl.queryFieldDefinesInRange((Collection)keys);
                int size = runtimeFields.size();
                for (int i = 0; i < size; ++i) {
                    Row row = sheet.createRow(i + 1);
                    FieldDefine fd = (FieldDefine)runtimeFields.get(i);
                    Cell cell = row.createCell(0);
                    cell.setCellValue(formCode);
                    cell = row.createCell(1);
                    cell.setCellValue(fd.getCode());
                }
            }
        } else {
            int size = zbMappings.size();
            for (int i = 0; i < size; ++i) {
                Row row = sheet.createRow(i + 1);
                ZBMapping zm = zbMappings.get(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(formCode);
                cell = row.createCell(1);
                cell.setCellValue(zm.getZbCode());
                cell = row.createCell(2);
                cell.setCellValue(zm.getMapping());
            }
        }
    }

    private boolean fmdmField(boolean isFMDM, DataLinkDefine link) {
        return isFMDM && link.getType() == DataLinkType.DATA_LINK_TYPE_FMDM;
    }

    private Grid2Data getGridData(String formKey) {
        BigDataDefine formData = this.runTimeAuthViewController.getReportDataFromForm(formKey);
        if (formData != null) {
            return Grid2Data.bytesToGrid((byte[])formData.getData());
        }
        Grid2Data griddata = new Grid2Data();
        griddata.setRowCount(10);
        griddata.setColumnCount(10);
        return griddata;
    }
}

