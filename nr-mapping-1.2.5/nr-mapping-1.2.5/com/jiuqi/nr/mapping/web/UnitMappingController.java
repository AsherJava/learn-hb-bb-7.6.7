/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.transaction.annotation.Transactional
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

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.mapping.bean.MappingScheme;
import com.jiuqi.nr.mapping.bean.UnitMapping;
import com.jiuqi.nr.mapping.bean.UnitRule;
import com.jiuqi.nr.mapping.common.MappingErrorEnum;
import com.jiuqi.nr.mapping.common.UnitRuleType;
import com.jiuqi.nr.mapping.service.JIOConfigService;
import com.jiuqi.nr.mapping.service.MappingSchemeService;
import com.jiuqi.nr.mapping.service.UnitMappingService;
import com.jiuqi.nr.mapping.util.ConvertUtils;
import com.jiuqi.nr.mapping.util.ImpExpUtils;
import com.jiuqi.nr.mapping.web.dto.UnitMappingDTO;
import com.jiuqi.nr.mapping.web.vo.Result;
import com.jiuqi.nr.mapping.web.vo.UnitMappingVO;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.OrgVersionClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
import org.springframework.transaction.annotation.Transactional;
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
@Api(tags={"\u5355\u4f4d\u6620\u5c04"})
public class UnitMappingController {
    protected final Logger logger = LoggerFactory.getLogger(UnitMappingController.class);
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private MappingSchemeService schemeService;
    @Autowired
    private UnitMappingService unitMappingService;
    @Autowired
    private IRunTimeViewController runTime;
    @Autowired
    private USelectorResultSet uSelectorResultSet;
    @Autowired
    private JIOConfigService jioConfigService;
    @Autowired
    private OrgVersionClient orgVersionClient;

    @GetMapping(value={"/unit/find/{msKey}"})
    @ApiOperation(value="\u83b7\u53d6\u65b9\u6848\u4e0b\u7684\u5355\u4f4d\u6620\u5c04")
    public UnitMappingVO findUnitMappingByMS(@PathVariable String msKey) throws JQException {
        UnitMappingVO res = new UnitMappingVO();
        this.buildEntityPeriod(msKey, res);
        this.buildUnitMappingDTO(msKey, res);
        this.buildRules(msKey, res);
        res.setJIO(this.jioConfigService.isJIO(msKey));
        return res;
    }

    @PostMapping(value={"/unit/custom-unit/{msKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5355\u4f4d\u4ee3\u7801\u5bf9\u5e94\u7684\u5355\u4f4d\u7f16\u7801\u548c\u540d\u79f0")
    public UnitMappingVO findCustomUnitByMS(@PathVariable String msKey, @RequestBody List<UnitMappingDTO> unitMappingDTOS) throws JQException {
        UnitMappingVO res = new UnitMappingVO();
        this.buildEntityPeriod(msKey, res);
        this.buildAddRowDTO(unitMappingDTOS, res);
        return res;
    }

    private void buildRules(String msKey, UnitMappingVO res) {
        List<UnitRule> rules = this.unitMappingService.findRuleByMS(msKey);
        if (CollectionUtils.isEmpty(rules)) {
            return;
        }
        for (UnitRule r : rules) {
            switch (r.getType()) {
                case EXPORT: {
                    res.setExportRule(r);
                    break;
                }
                case IMPORT: {
                    res.setImportRule(r);
                }
            }
        }
    }

    private void buildEntityPeriod(String msKey, UnitMappingVO res) throws JQException {
        MappingScheme scheme = this.schemeService.getByKey(msKey);
        TaskDefine task = this.runTime.queryTaskDefine(scheme.getTask());
        String entityId = task.getDw();
        try {
            res.setEntityId(entityId);
            IEntityDefine entity = this.entityMetaService.queryEntity(entityId);
            res.setIsVersion(entity.getVersion() == 1);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_UNIT_001, e.getMessage());
        }
        try {
            FormSchemeDefine formScheme = this.runTime.getFormScheme(scheme.getFormScheme());
            res.setDatetime(formScheme.getDateTime());
            res.setPeriod(formScheme.getToPeriod());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_UNIT_001, e.getMessage());
        }
    }

    private void buildUnitMappingDTO(String msKey, UnitMappingVO res) throws JQException {
        List<UnitMapping> mappings = this.unitMappingService.findByMS(msKey);
        ArrayList<UnitMappingDTO> unitMappingDTOS = new ArrayList<UnitMappingDTO>();
        ArrayList<UnitMapping> customMappings = new ArrayList<UnitMapping>();
        if (!CollectionUtils.isEmpty(mappings)) {
            this.findCurrPeriodMappings(res, mappings, unitMappingDTOS, customMappings);
            if (res.isVersion() && !CollectionUtils.isEmpty(customMappings)) {
                List customUnitMappings = customMappings.stream().map(UnitMappingDTO::new).distinct().collect(Collectors.toList());
                Map<String, UnitMappingDTO> customEntitiesMaps = this.getCustomEntity(res.getEntityId(), res.getDatetime(), customMappings.stream().map(UnitMapping::getUnitCode).collect(Collectors.toList())).stream().collect(Collectors.toMap(IEntityItem::getEntityKeyData, UnitMappingDTO::new));
                for (UnitMappingDTO m : customUnitMappings) {
                    UnitMappingDTO em = customEntitiesMaps.get(m.getUnitCode());
                    if (em != null) {
                        m.setOrgCode(em.getOrgCode());
                        m.setUnitTitle(em.getUnitTitle());
                    }
                    m.setCurrPeriod(false);
                    unitMappingDTOS.add(m);
                }
            }
        }
        res.setMappings(unitMappingDTOS);
    }

    public void buildAddRowDTO(List<UnitMappingDTO> addMappingDTOS, UnitMappingVO res) throws JQException {
        List<UnitMapping> mappings = addMappingDTOS.stream().map(ConvertUtils::DTO2UnitMapping).collect(Collectors.toList());
        ArrayList<UnitMappingDTO> unitMappingDTOS = new ArrayList<UnitMappingDTO>();
        ArrayList<UnitMapping> customMappings = new ArrayList<UnitMapping>();
        if (!CollectionUtils.isEmpty(mappings)) {
            this.findCurrPeriodMappings(res, mappings, unitMappingDTOS, customMappings);
            if (res.isVersion() && !CollectionUtils.isEmpty(customMappings)) {
                List customUnitMappings = customMappings.stream().map(UnitMappingDTO::new).distinct().collect(Collectors.toList());
                Map<String, UnitMappingDTO> customEntitiesMaps = this.getCustomEntity(res.getEntityId(), res.getDatetime(), customMappings.stream().map(UnitMapping::getUnitCode).collect(Collectors.toList())).stream().collect(Collectors.toMap(IEntityItem::getEntityKeyData, UnitMappingDTO::new));
                Map<String, UnitMappingDTO> oldCustomEntitiesMaps = addMappingDTOS.stream().collect(Collectors.toMap(UnitMappingDTO::getUnitCode, unitMappingDTO -> unitMappingDTO));
                for (UnitMappingDTO m : customUnitMappings) {
                    UnitMappingDTO em = customEntitiesMaps.get(m.getUnitCode());
                    if (em != null) {
                        m.setOrgCode(em.getOrgCode());
                        m.setUnitTitle(em.getUnitTitle());
                    } else {
                        UnitMappingDTO oldUnitMappingDTO = oldCustomEntitiesMaps.get(m.getUnitCode());
                        m.setOrgCode(oldUnitMappingDTO.getOrgCode());
                        m.setUnitTitle(oldUnitMappingDTO.getUnitTitle());
                    }
                    m.setCurrPeriod(false);
                    unitMappingDTOS.add(m);
                }
            }
        }
        res.setMappings(unitMappingDTOS);
    }

    private void findCurrPeriodMappings(UnitMappingVO res, List<UnitMapping> mappings, List<UnitMappingDTO> unitMappingDTOS, List<UnitMapping> customMappings) throws JQException {
        List<String> queryUnits = mappings.stream().map(UnitMapping::getUnitCode).collect(Collectors.toList());
        List<IEntityRow> entities = this.getCurrPeriodEntity(res.getEntityId(), res.getDatetime(), res.getPeriod(), queryUnits);
        Map<String, UnitMappingDTO> entityMaps = entities.stream().collect(Collectors.toMap(IEntityItem::getEntityKeyData, UnitMappingDTO::new));
        for (UnitMapping m : mappings) {
            UnitMappingDTO unitMappingDTO = entityMaps.get(m.getUnitCode());
            if (unitMappingDTO != null) {
                unitMappingDTO.setMapping(m.getMapping());
                unitMappingDTO.setKey(m.getKey());
                unitMappingDTO.setMsKey(m.getMsKey());
                unitMappingDTO.setCurrPeriod(true);
                unitMappingDTOS.add(unitMappingDTO);
                continue;
            }
            customMappings.add(m);
        }
    }

    private IEntityQuery getEntityQuery(String entityId) {
        IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
        IEntityViewRunTimeController runTimeController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);
        EntityViewDefine entityViewDefine = runTimeController.buildEntityView(entityId);
        IEntityQuery entityQuery = entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        return entityQuery;
    }

    private List<IEntityRow> getCustomEntity(String entityId, String dataTime, List<String> queryUnit) throws JQException {
        OrgVersionDTO param = new OrgVersionDTO();
        String entityCode = this.entityMetaService.getEntityCode(entityId);
        param.setCategoryname(entityCode);
        PageVO orgVersions = this.orgVersionClient.list(param);
        List rows = orgVersions.getRows();
        ArrayList<IEntityRow> allRows = new ArrayList<IEntityRow>();
        try {
            IEntityQuery entityQuery = this.getEntityQuery(entityId);
            IDataDefinitionRuntimeController definitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
            String dimensionName = this.entityMetaService.getDimensionName(entityId);
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            ExecutorContext executorContext = new ExecutorContext(definitionRuntimeController);
            executorContext.setPeriodView(dataTime);
            for (OrgVersionDO row : rows) {
                dimensionValueSet.setValue(dimensionName, queryUnit);
                entityQuery.setMasterKeys(dimensionValueSet);
                Date validTime = row.getValidtime();
                entityQuery.setQueryVersionDate(validTime);
                IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
                List entityRows = iEntityTable.getAllRows();
                if (entityRows.isEmpty()) continue;
                allRows.addAll(entityRows);
                Set queriedUnitSet = entityRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet());
                ArrayList<String> resultList = new ArrayList<String>();
                for (String q : queryUnit) {
                    if (queriedUnitSet.contains(q)) continue;
                    resultList.add(q);
                }
                if (!resultList.isEmpty()) {
                    queryUnit = resultList;
                    continue;
                }
                break;
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_UNIT_001, e.getMessage());
        }
        return allRows;
    }

    private List<IEntityRow> getCurrPeriodEntity(String entityId, String dataTime, String period, List<String> queryUnit) throws JQException {
        List allRows;
        try {
            IEntityQuery entityQuery = this.getEntityQuery(entityId);
            IDataDefinitionRuntimeController definitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
            String dimensionName = this.entityMetaService.getDimensionName(entityId);
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)period);
            dimensionValueSet.setValue(dimensionName, queryUnit);
            entityQuery.setMasterKeys(dimensionValueSet);
            ExecutorContext executorContext = new ExecutorContext(definitionRuntimeController);
            executorContext.setPeriodView(dataTime);
            IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
            allRows = iEntityTable.getAllRows();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_UNIT_001, e.getMessage());
        }
        return allRows;
    }

    @GetMapping(value={"/unit/get-unit-title"})
    @ApiOperation(value="\u9009\u62e9\u5355\u4f4d\u540e\u7ec4\u7ec7\u65b0\u7684\u5355\u4f4d\u6620\u5c04")
    public List<Map<String, String>> buildMappingBySelectUnits(String selectorKey) throws JQException {
        ArrayList<Map<String, String>> res = new ArrayList<Map<String, String>>();
        List allRows = this.uSelectorResultSet.getFilterEntityRows(selectorKey);
        for (IEntityRow r : allRows) {
            HashMap<String, String> m = new HashMap<String, String>();
            m.put("unitCode", r.getEntityKeyData());
            m.put("orgCode", r.getCode());
            m.put("title", r.getTitle());
            res.add(m);
        }
        return res;
    }

    @GetMapping(value={"/unit/clear/{msKey}"})
    @ApiOperation(value="\u6e05\u7a7a\u65b9\u6848\u4e0b\u7684\u5355\u4f4d\u6620\u5c04")
    @Transactional(rollbackFor={Exception.class})
    public void clearUnitMappingByMS(@PathVariable String msKey) throws JQException {
        this.unitMappingService.clearByMS(msKey);
        this.unitMappingService.clearRuleByMS(msKey);
    }

    @PostMapping(value={"/unit/save/{msKey}"})
    @ApiOperation(value="\u4fdd\u5b58\u65b9\u6848\u4e0b\u7684\u5355\u4f4d\u6620\u5c04")
    @Transactional(rollbackFor={Exception.class})
    public void savePeriodMappingByMS(@PathVariable String msKey, @Valid @RequestBody UnitMappingVO mappingVO) throws JQException {
        List<UnitMappingDTO> mappings = mappingVO.getMappings();
        ArrayList<UnitMapping> mps = new ArrayList<UnitMapping>();
        if (!CollectionUtils.isEmpty(mappings)) {
            for (UnitMappingDTO dto : mappings) {
                UnitMapping mp = new UnitMapping();
                mp.setKey(dto.getKey());
                mp.setUnitCode(dto.getUnitCode());
                mp.setMapping(dto.getMapping());
                mps.add(mp);
            }
        }
        this.unitMappingService.saveByMS(msKey, mps);
        ArrayList<UnitRule> rules = new ArrayList<UnitRule>();
        UnitRule importRule = mappingVO.getImportRule();
        UnitRule exportRule = mappingVO.getExportRule();
        if (importRule != null) {
            importRule.setType(UnitRuleType.IMPORT);
            rules.add(importRule);
        }
        if (exportRule != null) {
            exportRule.setType(UnitRuleType.EXPORT);
            rules.add(exportRule);
        }
        this.unitMappingService.saveRule(msKey, rules);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @PostMapping(value={"/unit/import/{msKey}"})
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
            if ("\u5355\u4f4d\u540d\u79f0".equals(firstValue)) {
                begin = 1;
            }
            HashSet<String> newUnitCodes = new HashSet<String>();
            ArrayList<UnitMapping> ums = new ArrayList<UnitMapping>();
            for (int i = begin; i <= size; ++i) {
                Result result;
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String unitCode = ImpExpUtils.getStringValue(row.getCell(2));
                if (!StringUtils.hasText(unitCode)) {
                    result = Result.error(null, "\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a");
                    return result;
                }
                if (!newUnitCodes.add(unitCode)) {
                    result = Result.error(null, "\u5355\u4f4d\u4ee3\u7801\u91cd\u590d:" + unitCode);
                    return result;
                }
                UnitMapping um = new UnitMapping();
                um.setKey(UUID.randomUUID().toString());
                um.setMsKey(msKey);
                um.setUnitCode(unitCode);
                um.setMapping(ImpExpUtils.getStringValue(row.getCell(3)));
                um.setOrder(i);
                ums.add(um);
            }
            this.unitMappingService.clearByMS(msKey);
            this.unitMappingService.saveByMS(msKey, ums);
            return Result.success(null, "\u5bfc\u5165\u6210\u529f");
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
        }
        return Result.success(null, "\u5bfc\u5165\u6210\u529f");
    }

    @GetMapping(value={"/unit/export/{msKey}"})
    @ApiOperation(value="\u5bfc\u51faexcel")
    public void export(@PathVariable String msKey, HttpServletResponse response) throws JQException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("\u5355\u4f4d\u6620\u5c04");
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
        cell.setCellValue("\u5355\u4f4d\u540d\u79f0");
        cell.setCellStyle(headStyle);
        cell = head.createCell(1);
        cell.setCellValue("\u5355\u4f4d\u7f16\u7801");
        cell.setCellStyle(headStyle);
        cell = head.createCell(2);
        cell.setCellValue("\u5355\u4f4d\u4ee3\u7801");
        cell.setCellStyle(headStyle);
        cell = head.createCell(3);
        cell.setCellValue("\u6620\u5c04\u4ee3\u7801");
        cell.setCellStyle(headStyle);
        int defaultWidth = 6000;
        sheet.setColumnWidth(0, defaultWidth);
        sheet.setColumnWidth(1, defaultWidth);
        sheet.setColumnWidth(2, defaultWidth);
        sheet.setColumnWidth(3, defaultWidth);
    }

    private void createMapping(Sheet sheet, String msKey) throws JQException {
        UnitMappingVO umv = new UnitMappingVO();
        this.buildEntityPeriod(msKey, umv);
        this.buildUnitMappingDTO(msKey, umv);
        List<UnitMappingDTO> ums = umv.getMappings();
        int size = ums.size();
        for (int i = 0; i < size; ++i) {
            Row row = sheet.createRow(i + 1);
            UnitMappingDTO um = ums.get(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(um.getUnitTitle());
            cell = row.createCell(1);
            cell.setCellValue(um.getOrgCode());
            cell = row.createCell(2);
            cell.setCellValue(um.getUnitCode());
            cell = row.createCell(3);
            cell.setCellValue(um.getMapping());
        }
    }
}

