/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
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

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.mapping.bean.MappingScheme;
import com.jiuqi.nr.mapping.bean.PeriodMapping;
import com.jiuqi.nr.mapping.common.MappingErrorEnum;
import com.jiuqi.nr.mapping.service.MappingSchemeService;
import com.jiuqi.nr.mapping.service.PeriodMappingService;
import com.jiuqi.nr.mapping.util.ImpExpUtils;
import com.jiuqi.nr.mapping.web.vo.PeriodMappingVO;
import com.jiuqi.nr.mapping.web.vo.Result;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
@RequestMapping(value={"/api/mapping"})
@Api(tags={"\u65f6\u671f\u6620\u5c04"})
public class PeriodMappingController {
    protected final Logger logger = LoggerFactory.getLogger(PeriodMappingController.class);
    @Autowired
    private PeriodMappingService periodService;
    @Autowired
    private MappingSchemeService schemeService;
    @Autowired
    private IRunTimeViewController runTime;
    @Autowired
    private PeriodEngineService periodEngineService;

    @GetMapping(value={"/period/find/{msKey}"})
    @ApiOperation(value="\u83b7\u53d6\u65b9\u6848\u4e0b\u7684\u65f6\u671f\u6620\u5c04")
    public PeriodMappingVO findPeriodMappingByMS(@PathVariable String msKey) throws JQException {
        PeriodMappingVO res = new PeriodMappingVO();
        MappingScheme mappingScheme = this.schemeService.getByKey(msKey);
        res.setMappings(this.periodService.findByMS(msKey));
        try {
            FormSchemeDefine formScheme = this.runTime.getFormScheme(mappingScheme.getFormScheme());
            List periodList = this.runTime.querySchemePeriodLinkByScheme(mappingScheme.getFormScheme());
            if (!CollectionUtils.isEmpty(periodList)) {
                res.setPeriods(periodList.stream().map(p -> p.getPeriodKey()).collect(Collectors.toList()));
            }
            res.setDefaultDate(this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime()).getCurPeriod().getCode());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_PERIOD_001, e.getMessage());
        }
        return res;
    }

    @GetMapping(value={"/period/clear/{msKey}"})
    @ApiOperation(value="\u6e05\u7a7a\u65b9\u6848\u4e0b\u7684\u65f6\u671f\u6620\u5c04")
    public void clearPeriodMappingByMS(@PathVariable String msKey) throws JQException {
        this.periodService.clearByMS(msKey);
    }

    @PostMapping(value={"/period/save/{msKey}"})
    @ApiOperation(value="\u4fdd\u5b58\u65b9\u6848\u4e0b\u7684\u65f6\u671f\u6620\u5c04")
    public void savePeriodMappingByMS(@PathVariable String msKey, @RequestBody List<PeriodMapping> mappings) throws JQException {
        this.periodService.saveByMS(msKey, mappings);
    }

    @GetMapping(value={"/period/export/{msKey}"})
    @ApiOperation(value="\u5bfc\u51faexcel")
    public void export(@PathVariable String msKey, HttpServletResponse response) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("\u65f6\u671f\u6620\u5c04");
        this.createHead(workbook, sheet);
        this.createMapping(sheet, msKey);
        try {
            String fileName = "\u65f6\u671f\u6620\u5c04.xls";
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
    @PostMapping(value={"/period/import/{msKey}"})
    @ApiOperation(value="\u5bfc\u5165\u6570\u636e")
    public Result importData(@PathVariable String msKey, @RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws JQException {
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
            if ("\u5f53\u524d\u65f6\u671f".equals(firstValue) || "\u76ee\u6807\u65f6\u671f".equals(firstValue)) {
                begin = 1;
            }
            MappingScheme mappingScheme = this.schemeService.getByKey(msKey);
            List periodList = this.runTime.querySchemePeriodLinkByScheme(mappingScheme.getFormScheme());
            List periods = periodList.stream().map(SchemePeriodLinkDefine::getPeriodKey).collect(Collectors.toList());
            HashSet<String> newPeriods = new HashSet<String>();
            ArrayList<PeriodMapping> pms = new ArrayList<PeriodMapping>();
            for (int i = begin; i <= size; ++i) {
                Result result;
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String period = ImpExpUtils.getStringValue(row.getCell(0));
                if (!StringUtils.hasText(period)) {
                    result = Result.error(null, "\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a");
                    return result;
                }
                if (!periods.contains(period)) {
                    result = Result.error(null, "\u65f6\u671f\u4e0d\u5b58\u5728:" + period);
                    return result;
                }
                if (!newPeriods.add(period)) {
                    result = Result.error(null, "\u65f6\u671f\u91cd\u590d:" + period);
                    return result;
                }
                PeriodMapping pm = new PeriodMapping();
                pm.setKey(UUID.randomUUID().toString());
                pm.setMsKey(msKey);
                pm.setPeriod(period);
                pm.setMapping(ImpExpUtils.getStringValue(row.getCell(1)));
                pms.add(pm);
            }
            this.periodService.clearByMS(msKey);
            this.periodService.saveByMS(msKey, pms);
            return Result.success(null, "\u5bfc\u5165\u6210\u529f");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return Result.success(null, "\u5bfc\u5165\u6210\u529f");
    }

    private void createMapping(Sheet sheet, String msKey) {
        List<PeriodMapping> mappings = this.periodService.findByMS(msKey);
        int size = mappings.size();
        for (int i = 0; i < size; ++i) {
            Row row = sheet.createRow(i + 1);
            PeriodMapping pm = mappings.get(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(pm.getPeriod());
            cell = row.createCell(1);
            cell.setCellValue(pm.getMapping());
        }
    }

    private void createHead(Workbook workbook, Sheet sheet) {
        Row head = sheet.createRow(0);
        Row row = sheet.createRow(0);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("\u9ed1\u4f53");
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setFont(font);
        Cell cell = head.createCell(0);
        cell.setCellValue("\u5f53\u524d\u65f6\u671f");
        cell.setCellStyle(headStyle);
        cell = head.createCell(1);
        cell.setCellValue("\u76ee\u6807\u65f6\u671f");
        cell.setCellStyle(headStyle);
        int defaultWidth = 6000;
        sheet.setColumnWidth(0, defaultWidth);
        sheet.setColumnWidth(1, defaultWidth);
    }
}

