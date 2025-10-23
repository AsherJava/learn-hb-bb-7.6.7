/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
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
package com.jiuqi.nr.mapping2.web;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.common.MappingErrorEnum;
import com.jiuqi.nr.mapping2.service.FormulaMappingService;
import com.jiuqi.nr.mapping2.util.ImpExpUtils;
import com.jiuqi.nr.mapping2.web.dto.FormDTO;
import com.jiuqi.nr.mapping2.web.dto.FormulaMappingDTO;
import com.jiuqi.nr.mapping2.web.vo.CommonTreeNode;
import com.jiuqi.nr.mapping2.web.vo.FormulaMappingVO;
import com.jiuqi.nr.mapping2.web.vo.Result;
import com.jiuqi.nr.mapping2.web.vo.SelectOptionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.springframework.util.CollectionUtils;
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
@RequestMapping(value={"/api/mapping2/formula"})
@Api(tags={"\u516c\u5f0f\u6620\u5c04"})
public class FormulaMappingController {
    protected final Logger logger = LoggerFactory.getLogger(FormulaMappingController.class);
    @Autowired
    private IFormulaRunTimeController formulaRunTime;
    @Autowired
    private IRunTimeViewController runTime;
    @Autowired
    private IDesignTimeViewController designTime;
    @Autowired
    private FormulaMappingService formulaMappingService;
    private static final String PATTERN_STRING = "^(\\{\\d+[,~=\\d+]*\\}){1,2}$";
    private static final Pattern PATTERN = Pattern.compile("^(\\{\\d+[,~=\\d+]*\\}){1,2}$");

    @GetMapping(value={"/build-formula-mapping/{msKey}/{taskKey}/{formSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u6811\u3001\u516c\u5f0f\u65b9\u6848\u3001\u8868\u95f4\u516c\u5f0f\u6620\u5c04")
    public FormulaMappingVO buildFormulaMapping(@PathVariable String msKey, @PathVariable String taskKey, @PathVariable String formSchemeKey) throws JQException {
        FormulaMappingVO res = new FormulaMappingVO();
        TaskDefine task = this.runTime.queryTaskDefine(taskKey);
        if (Objects.isNull(task)) {
            res.setSuccess(false);
            DesignTaskDefine designTask = this.designTime.queryTaskDefine(taskKey);
            if (Objects.isNull(designTask)) {
                res.setMessage(MappingErrorEnum.MAPPING_003.getMessage());
            } else {
                res.setMessage(MappingErrorEnum.MAPPING_002.getMessage());
            }
            return res;
        }
        FormSchemeDefine formScheme = this.runTime.getFormScheme(formSchemeKey);
        if (Objects.isNull(formScheme)) {
            res.setSuccess(false);
            DesignFormSchemeDefine designForm = this.designTime.queryFormSchemeDefine(formSchemeKey);
            if (Objects.isNull(designForm)) {
                res.setMessage(MappingErrorEnum.MAPPING_0033.getMessage());
            } else {
                res.setMessage(MappingErrorEnum.MAPPING_0022.getMessage());
            }
            return res;
        }
        List formulaSchemes = this.formulaRunTime.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        if (CollectionUtils.isEmpty(formulaSchemes)) {
            res.setSuccess(false);
            res.setMessage(MappingErrorEnum.MAPPING_004.getMessage());
            return res;
        }
        res.setFormulaSchemes(formulaSchemes.stream().map(f -> new SelectOptionVO(f.getKey(), f.getTitle())).collect(Collectors.toList()));
        res.setNodes(this.buildFormGroupTree(formSchemeKey));
        String firstFormulaSchemeKey = res.getFormulaSchemes().get(0).getValue();
        res.setMappings(this.buildFormulaMappings(msKey, firstFormulaSchemeKey, "FORMULA-MAPPING-BETWEEN", "FORMULA-MAPPING-BETWEEN"));
        return res;
    }

    @GetMapping(value={"/query-formula"})
    @ApiOperation(value="\u83b7\u53d6\u516c\u5f0f\u65b9\u6848\u4e0b\u67d0\u62a5\u8868\u7684\u516c\u5f0f\u6620\u5c04\u96c6\u5408")
    public List<FormulaMappingDTO> queryFormula(String msKey, String formCode, String formKey, String formulaSchemeKey) throws JQException {
        return this.buildFormulaMappings(msKey, formulaSchemeKey, formKey, formCode);
    }

    private List<FormulaMappingDTO> buildFormulaMappings(String msKey, String formulaSchemeKey, String formKey, String formCode) {
        ArrayList<FormulaMappingDTO> res = new ArrayList<FormulaMappingDTO>();
        List<FormulaMapping> mappings = this.formulaMappingService.findByMSFormulaForm(msKey, formulaSchemeKey, formCode);
        Map maps = mappings.stream().collect(Collectors.toMap(FormulaMapping::getFormulaCode, Function.identity()));
        if ("FORMULA-MAPPING-BETWEEN".equals(formKey)) {
            formKey = null;
        }
        List allFormulasInForm = this.formulaRunTime.getAllFormulasInForm(formulaSchemeKey, formKey);
        allFormulasInForm = allFormulasInForm.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
        for (FormulaDefine f : allFormulasInForm) {
            FormulaMappingDTO fdto = new FormulaMappingDTO(f, msKey, formCode);
            FormulaMapping mapping = (FormulaMapping)maps.get(fdto.getFormulaCode());
            if (mapping != null) {
                fdto.setKey(mapping.getKey());
                fdto.setmFormulaScheme(mapping.getmFormulaScheme());
                fdto.setmFormulaCode(mapping.getmFormulaCode());
                fdto.setmFormulaWildcard(mapping.getmFormulaWildcard());
            }
            res.add(fdto);
        }
        return res;
    }

    @PostMapping(value={"/save"})
    @ApiOperation(value="\u4fdd\u5b58\u516c\u5f0f\u6620\u5c04")
    public void savePeriodMappingByMS(String msKey, String fsKey, String fcKey, @RequestBody List<FormulaMappingDTO> fmDTOs) throws JQException {
        ArrayList<FormulaMapping> mps = new ArrayList<FormulaMapping>();
        if (!CollectionUtils.isEmpty(fmDTOs)) {
            for (FormulaMappingDTO dto : fmDTOs) {
                FormulaMapping mp = new FormulaMapping();
                mp.setKey(dto.getKey());
                mp.setFormulaCode(dto.getFormulaCode());
                mp.setmFormulaCode(dto.getmFormulaCode());
                mp.setmFormulaScheme(dto.getmFormulaScheme());
                mp.setmFormulaWildcard(dto.getmFormulaWildcard());
                mps.add(mp);
            }
            this.formulaMappingService.save(msKey, fsKey, fcKey, mps);
        }
    }

    @PostMapping(value={"/clear"})
    @ApiOperation(value="\u6e05\u7a7a\u516c\u5f0f\u6620\u5c04")
    public void clearFormulaMapping(String msKey, String fsKey, String fcKey) throws Exception {
        this.formulaMappingService.clear(msKey, fsKey, fcKey);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @PostMapping(value={"/import"})
    @ApiOperation(value="\u5bfc\u5165\u6570\u636e")
    public Result importItemData(String msKey, String formulaSchemeKey, String formSchemeKey, @RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws JQException {
        try (InputStream in = file.getInputStream();){
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            String wildcardPattern = "^(?:\\{\\d+[,=~\\d]*\\d\\}){1,2}$";
            Pattern PATTERN = Pattern.compile(wildcardPattern);
            int i = 0;
            while (i < workbook.getNumberOfSheets()) {
                Sheet sheet = workbook.getSheetAt(i);
                String formKey = null;
                String formCode = "FORMULA-MAPPING-BETWEEN";
                if (!"\u8868\u95f4\u516c\u5f0f".equals(sheet.getSheetName())) {
                    formCode = sheet.getSheetName().substring(0, sheet.getSheetName().indexOf("|"));
                    FormDefine formDefine = this.runTime.queryFormByCodeInScheme(formSchemeKey, formCode);
                    formKey = formDefine.getKey();
                }
                int size = sheet.getLastRowNum();
                int begin = 0;
                String firstValue = ImpExpUtils.getStringValue(sheet.getRow(0).getCell(0));
                if ("\u8868\u8fbe\u5f0f".equals(firstValue)) {
                    begin = 1;
                }
                ArrayList<FormulaMapping> fms = new ArrayList<FormulaMapping>();
                List formulaCodes = this.formulaRunTime.getAllFormulasInForm(formulaSchemeKey, formKey).stream().map(FormulaDefine::getCode).collect(Collectors.toList());
                HashSet<String> newFormulaCodes = new HashSet<String>();
                HashSet<String> mFormulaCodes = new HashSet<String>();
                for (int index = begin; index <= size; ++index) {
                    Result result;
                    Row row = sheet.getRow(index);
                    if (row == null) continue;
                    String formulaCode = ImpExpUtils.getStringValue(row.getCell(1));
                    String mFormulaCode = ImpExpUtils.getStringValue(row.getCell(2));
                    String mFormulaScheme = ImpExpUtils.getStringValue(row.getCell(3));
                    String mWildcard = ImpExpUtils.getStringValue(row.getCell(4));
                    if (!StringUtils.hasText(formulaCode)) {
                        result = Result.error(null, "\u516c\u5f0f\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a");
                        return result;
                    }
                    if (!formulaCodes.contains(formulaCode)) {
                        result = Result.error(null, "\u516c\u5f0f\u7f16\u53f7\u4e0d\u5b58\u5728:" + formulaCode);
                        return result;
                    }
                    if (!newFormulaCodes.add(formulaCode)) {
                        result = Result.error(null, "\u516c\u5f0f\u7f16\u53f7\u91cd\u590d:" + formulaCode);
                        return result;
                    }
                    if (StringUtils.hasText(mFormulaCode) && StringUtils.hasText(mFormulaScheme) && !mFormulaCodes.add(mFormulaCode + mFormulaScheme)) {
                        result = Result.error(null, "\u76ee\u6807\u516c\u5f0f\u7f16\u53f7[" + mFormulaCode + "]\uff0c\u76ee\u6807\u516c\u5f0f\u65b9\u6848\uff1a:[" + mFormulaScheme + "]\u91cd\u590d");
                        return result;
                    }
                    if (!FormulaMappingController.validate(mWildcard)) {
                        result = Result.error(null, "\u516c\u5f0f\u7f16\u53f7:" + formulaCode + "\u7684\u901a\u914d\u6620\u5c04\u3010" + mWildcard + "\u3011\u4e0d\u7b26\u5408\u89c4\u5219");
                        return result;
                    }
                    if (!StringUtils.hasText(mFormulaCode)) continue;
                    FormulaMapping fm = new FormulaMapping();
                    fm.setKey(UUID.randomUUID().toString());
                    fm.setFormulaScheme(formulaSchemeKey);
                    fm.setMappingScheme(msKey);
                    fm.setFormCode(formCode);
                    fm.setFormulaCode(formulaCode);
                    fm.setmFormulaCode(mFormulaCode);
                    fm.setmFormulaScheme(mFormulaScheme);
                    fm.setmFormulaWildcard(mWildcard);
                    fms.add(fm);
                }
                this.formulaMappingService.save(msKey, formulaSchemeKey, formCode, fms);
                ++i;
            }
            return Result.success(null, "\u5bfc\u5165\u6210\u529f");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return Result.success(null, "\u5bfc\u5165\u6210\u529f");
    }

    private static boolean validate(String input) {
        if (input == null) {
            return true;
        }
        Matcher matcher = PATTERN.matcher(input);
        return matcher.matches();
    }

    @PostMapping(value={"/export"})
    @ApiOperation(value="\u5bfc\u51faexcel")
    public void export(String msKey, String formulaSchemeKey, @RequestBody List<FormDTO> forms, HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        for (FormDTO form : forms) {
            Sheet sheet = workbook.createSheet(form.getTitle());
            List<FormulaMappingDTO> fmlMappings = this.buildFormulaMappings(msKey, formulaSchemeKey, form.getKey(), form.getCode());
            this.createHead(workbook, sheet);
            this.createMapping(sheet, fmlMappings);
        }
        String fileName = "\u516c\u5f0f\u6620\u5c04.xls";
        try {
            ImpExpUtils.export(fileName, response, workbook);
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private List<CommonTreeNode> buildFormGroupTree(String formSchemeKey) {
        List formGroups = this.runTime.getAllFormGroupsInFormScheme(formSchemeKey);
        HashMap<String, List<FormGroupDefine>> maps = new HashMap<String, List<FormGroupDefine>>();
        for (FormGroupDefine g : formGroups) {
            List formGroupDefines = maps.computeIfAbsent(g.getParentKey(), k -> new ArrayList());
            formGroupDefines.add(g);
        }
        ArrayList<CommonTreeNode> treeNodes = new ArrayList<CommonTreeNode>();
        treeNodes.add(this.buildFormBetweenNode());
        this.buildFormGroupTreeByParent(null, maps, treeNodes);
        return treeNodes;
    }

    private CommonTreeNode buildFormBetweenNode() {
        CommonTreeNode formBetweenGroup = new CommonTreeNode();
        formBetweenGroup.setCode("FORMULA-MAPPING-BETWEEN-GROUP");
        formBetweenGroup.setTitle("\u8868\u95f4\u516c\u5f0f\u5206\u7ec4");
        formBetweenGroup.setExpand(true);
        CommonTreeNode formBetween = new CommonTreeNode();
        formBetween.setKey("FORMULA-MAPPING-BETWEEN");
        formBetween.setCode("FORMULA-MAPPING-BETWEEN");
        formBetween.setTitle("\u8868\u95f4\u516c\u5f0f");
        formBetween.setSelected(true);
        formBetweenGroup.addChildren(formBetween);
        return formBetweenGroup;
    }

    private void buildFormGroupTreeByParent(String parentId, Map<String, List<FormGroupDefine>> formGroupDefineMap, List<CommonTreeNode> childrenNode) {
        List<FormGroupDefine> formGroupDefines = formGroupDefineMap.get(parentId);
        if (!CollectionUtils.isEmpty(formGroupDefines)) {
            for (FormGroupDefine g : formGroupDefines) {
                CommonTreeNode gNdoe = new CommonTreeNode(g.getCode(), g.getTitle());
                gNdoe.setExpand(true);
                childrenNode.add(gNdoe);
                ArrayList<CommonTreeNode> childrenNode1 = new ArrayList<CommonTreeNode>();
                gNdoe.setChildren(childrenNode1);
                this.buildFormGroupTreeByParent(g.getKey(), formGroupDefineMap, childrenNode1);
            }
        }
        try {
            List forms = this.runTime.getAllFormsInGroup(parentId);
            if (!CollectionUtils.isEmpty(forms)) {
                for (FormDefine f : forms) {
                    CommonTreeNode fNdoe = new CommonTreeNode(f.getKey(), f.getFormCode(), f.getFormCode() + "|" + f.getTitle());
                    childrenNode.add(fNdoe);
                }
            }
        }
        catch (Exception e) {
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
        cell.setCellValue("\u8868\u8fbe\u5f0f");
        cell.setCellStyle(headStyle);
        cell = head.createCell(1);
        cell.setCellValue("\u516c\u5f0f\u7f16\u53f7");
        cell.setCellStyle(headStyle);
        cell = head.createCell(2);
        cell.setCellValue("\u76ee\u6807\u516c\u5f0f\u7f16\u53f7");
        cell.setCellStyle(headStyle);
        cell = head.createCell(3);
        cell.setCellValue("\u76ee\u6807\u516c\u5f0f\u65b9\u6848");
        cell.setCellStyle(headStyle);
        cell = head.createCell(4);
        cell.setCellValue("\u76ee\u6807\u901a\u914d");
        cell.setCellStyle(headStyle);
        int defaultWidth = 8000;
        sheet.setColumnWidth(0, defaultWidth * 3);
        sheet.setColumnWidth(1, defaultWidth);
        sheet.setColumnWidth(2, defaultWidth);
        sheet.setColumnWidth(3, defaultWidth);
        sheet.setColumnWidth(4, defaultWidth * 2);
    }

    private void createMapping(Sheet sheet, List<FormulaMappingDTO> formulaMappingDTOS) throws Exception {
        int size = formulaMappingDTOS.size();
        for (int i = 0; i < size; ++i) {
            Row row = sheet.createRow(i + 1);
            FormulaMappingDTO fm = formulaMappingDTOS.get(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(fm.getFormulaExp());
            cell = row.createCell(1);
            cell.setCellValue(fm.getFormulaCode());
            cell = row.createCell(2);
            cell.setCellValue(fm.getmFormulaCode());
            cell = row.createCell(3);
            cell.setCellValue(fm.getmFormulaScheme());
            cell = row.createCell(4);
            cell.setCellValue(fm.getmFormulaWildcard());
        }
    }
}

