/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.common.DataSchemeEnum
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldDesignService
 *  com.jiuqi.nr.datascheme.web.facade.DataFieldPageVO
 *  com.jiuqi.nr.datascheme.web.param.DataFieldMovePM
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeQueryPM
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.util.Assert
 *  org.apache.shiro.util.CollectionUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.query.datascheme.web.rest;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDesignService;
import com.jiuqi.nr.datascheme.web.facade.DataFieldPageVO;
import com.jiuqi.nr.datascheme.web.param.DataFieldMovePM;
import com.jiuqi.nr.datascheme.web.param.DataSchemeQueryPM;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.AbstractDataTableAdapter;
import com.jiuqi.nr.query.datascheme.extend.DataTableFactoryManager;
import com.jiuqi.nr.query.datascheme.extend.DimFieldInfo;
import com.jiuqi.nr.query.datascheme.extend.IDataTableFactory;
import com.jiuqi.nr.query.datascheme.service.IDesignQueryDataSchemeService;
import com.jiuqi.nr.query.datascheme.service.IQueryDataFieldValidator;
import com.jiuqi.nr.query.datascheme.service.dto.QueryDataTableDTO;
import com.jiuqi.nr.query.datascheme.service.impl.QueryDataFieldIOServiceImpl;
import com.jiuqi.nr.query.datascheme.web.base.QueryEntityUtil;
import com.jiuqi.nr.query.datascheme.web.param.QueryBatUpDataFieldVO;
import com.jiuqi.nr.query.datascheme.web.param.QueryDataDimFieldVO;
import com.jiuqi.nr.query.datascheme.web.param.QueryDataFieldVO;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.apache.shiro.util.Assert;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v1/query-datascheme/"})
@Api(tags={"\u67e5\u8be2\u6570\u636e\u65b9\u6848\u6307\u6807\u7ba1\u7406\u670d\u52a1"})
public class QueryDataFieldRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryDataFieldRestController.class);
    @Autowired
    private IDesignQueryDataSchemeService designDataSchemeService;
    @Autowired
    private DataFieldDesignService dataFieldDesignService;
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataModelService modelService;
    @Autowired
    @Qualifier(value="QueryDataFieldValidatorImpl")
    private IQueryDataFieldValidator fieldValidator;
    @Autowired
    private FileService fileService;
    @Autowired
    private QueryDataFieldIOServiceImpl dataFieldIOService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;

    @ApiOperation(value="\u83b7\u53d6\u4e3b\u7ef4\u5ea6\u53ef\u9009\u5b57\u6bb5")
    @GetMapping(value={"column/query/str/{scheme}/{table}/{tableCode}/{tableType}"})
    public List<QueryDataDimFieldVO> queryStrColumns(@PathVariable String scheme, @PathVariable String table, @PathVariable String tableCode, @PathVariable String tableType) throws JQException {
        IDataTableFactory factory = DataTableFactoryManager.getInstance().getFactory(tableType);
        AbstractDataTableAdapter adapter = factory.getDataTableAdapter();
        try {
            List<DimFieldInfo> allStrFields = adapter.getAllStrFields(scheme, table, tableCode);
            if (CollectionUtils.isEmpty(allStrFields)) {
                return Collections.emptyList();
            }
            return allStrFields.stream().map(i -> {
                QueryDataDimFieldVO field = new QueryDataDimFieldVO();
                field.setColumnName(i.getCode());
                field.setColumnTitle(i.getTitle());
                DataDimension matchedDim = i.getMatchedDim();
                if (null != matchedDim) {
                    field.setDimKey(matchedDim.getDimKey());
                    field.setDimType(matchedDim.getDimensionType().getValue());
                }
                return field;
            }).collect(Collectors.toList());
        }
        catch (DataTableAdaptException e) {
            LOGGER.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u6839\u636e\u6570\u636e\u8868-\u5206\u9875\u67e5\u8be2\u6307\u6807")
    @PostMapping(value={"field/query"})
    public DataFieldPageVO<List<QueryDataFieldVO>> queryDataField(@RequestBody DataSchemeQueryPM param) {
        String table = param.getTable();
        if (table == null) {
            return null;
        }
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(table);
        if (dataTable == null) {
            return this.queryFmdmFields(param);
        }
        DataFieldPageVO<List<QueryDataFieldVO>> page = this.createPageVO(param);
        Integer pageCount = param.getPageCount();
        Integer pageNum = param.getPageNum();
        FieldSearchQuery fieldSearchQuery = param.toQuery();
        if (DataTableType.ACCOUNT == dataTable.getDataTableType()) {
            fieldSearchQuery.setOrder("DF_KIND DESC , DF_CHANGE_WITH_PERIOD , DF_ORDER");
        } else {
            fieldSearchQuery.setOrder("DF_KIND DESC , DF_ORDER");
        }
        List designDataFields = this.dataFieldDesignService.filterField(fieldSearchQuery);
        this.fillFieldInfo(table, designDataFields, (List)page.getValue(), this.getSkipIndex(pageCount, pageNum));
        int total = this.dataFieldDesignService.countBy(fieldSearchQuery);
        page.setTotal(Integer.valueOf(total));
        return page;
    }

    private DataFieldPageVO<List<QueryDataFieldVO>> queryFmdmFields(DataSchemeQueryPM param) {
        DataFieldPageVO<List<QueryDataFieldVO>> page = this.createPageVO(param);
        Integer pageCount = param.getPageCount();
        Integer pageNum = param.getPageNum();
        int index = this.getSkipIndex(pageCount, pageNum);
        try {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(param.getTable());
            if (entityModel == null) {
                return null;
            }
            TableModelDefine tableModel = this.entityMetaService.getTableModel(param.getTable());
            String tableName = tableModel.getCode();
            List showFields = entityModel.getShowFields();
            page.setTotal(Integer.valueOf(showFields.size()));
            if (pageCount != null && pageNum != null) {
                showFields = showFields.stream().skip(index).limit(pageCount.intValue()).collect(Collectors.toList());
            }
            for (IEntityAttribute next : showFields) {
                TableModelDefine tableModelDefineById;
                QueryDataFieldVO dataFieldVO = new QueryDataFieldVO(next, tableName);
                String referTable = next.getReferTableID();
                if (referTable != null && (tableModelDefineById = this.modelService.getTableModelDefineById(referTable)) != null) {
                    dataFieldVO.setRefDataEntityTitle(tableModelDefineById.getTitle());
                }
                QueryDataFieldRestController.setDimension(dataFieldVO);
                dataFieldVO.setIndex(++index);
                ((List)page.getValue()).add(dataFieldVO);
            }
        }
        catch (Exception e) {
            throw new SchemeDataException((Throwable)e);
        }
        return page;
    }

    private DataFieldPageVO<List<QueryDataFieldVO>> createPageVO(DataSchemeQueryPM param) {
        DataFieldPageVO page = new DataFieldPageVO();
        page.setPageCount(param.getPageCount());
        page.setPageNum(param.getPageNum());
        page.setValue(new ArrayList());
        return page;
    }

    private int getSkipIndex(Integer pageCount, Integer pageNum) {
        int index = 0;
        if (pageCount != null && pageNum != null) {
            index = pageCount * (pageNum - 1);
        }
        return index;
    }

    private <DF extends DataField> void fillFieldInfo(String tableKey, List<DF> designDataFields, List<QueryDataFieldVO> allFields, Integer index) {
        if (null != designDataFields && !designDataFields.isEmpty()) {
            Map<String, DataFieldDeployInfo> deployInfoMap = this.getDeployInfoMap(tableKey);
            HashMap<String, String> entityInfoMap = new HashMap<String, String>(5);
            for (DataField v : designDataFields) {
                QueryDataFieldVO fieldEntity2VO = new QueryDataFieldVO();
                QueryEntityUtil.fieldEntity2VO(fieldEntity2VO, v);
                if (deployInfoMap.containsKey(fieldEntity2VO.getKey())) {
                    DataFieldDeployInfo deployInfo = deployInfoMap.get(fieldEntity2VO.getKey());
                    fieldEntity2VO.setTableName(deployInfo.getTableName());
                    fieldEntity2VO.setFieldName(deployInfo.getFieldName());
                }
                if (StringUtils.hasLength(v.getRefDataEntityKey()) && DataFieldKind.PUBLIC_FIELD_DIM.getValue() != v.getDataFieldKind().getValue()) {
                    if (!entityInfoMap.containsKey(v.getRefDataEntityKey())) {
                        if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(v.getRefDataEntityKey())) {
                            IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(v.getRefDataEntityKey());
                            entityInfoMap.put(v.getRefDataEntityKey(), periodEntity.getTitle());
                        } else {
                            IEntityDefine queryEntity = this.entityMetaService.queryEntity(v.getRefDataEntityKey());
                            entityInfoMap.put(v.getRefDataEntityKey(), queryEntity.getTitle());
                        }
                    }
                    fieldEntity2VO.setRefDataEntityTitle((String)entityInfoMap.get(v.getRefDataEntityKey()));
                }
                if (index != null) {
                    index = index + 1;
                    fieldEntity2VO.setIndex(index);
                }
                QueryDataFieldRestController.setDimension(fieldEntity2VO);
                allFields.add(fieldEntity2VO);
            }
        }
    }

    public static void setDimension(QueryDataFieldVO dataFieldVO) {
        DataFieldType dataFieldType = dataFieldVO.getDataFieldType();
        if (dataFieldType == DataFieldType.INTEGER || dataFieldType == DataFieldType.BIGDECIMAL) {
            if (!StringUtils.hasLength(dataFieldVO.getMeasureUnit()) || "NotDimession".equals(dataFieldVO.getMeasureUnit())) {
                dataFieldVO.setDimension(1);
            } else {
                dataFieldVO.setDimension(0);
            }
        }
    }

    private Map<String, DataFieldDeployInfo> getDeployInfoMap(String tableKey) {
        HashMap<String, DataFieldDeployInfo> deployInfoMap = new HashMap<String, DataFieldDeployInfo>();
        List dataFieldDeployInfo = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(tableKey);
        if (null != dataFieldDeployInfo && !dataFieldDeployInfo.isEmpty()) {
            for (DataFieldDeployInfo deployInfo : dataFieldDeployInfo) {
                if (!deployInfoMap.containsKey(deployInfo.getDataFieldKey())) {
                    deployInfoMap.put(deployInfo.getDataFieldKey(), deployInfo);
                    continue;
                }
                if (deployInfo.getTableName().contains("_RPT")) continue;
                deployInfoMap.put(deployInfo.getDataFieldKey(), deployInfo);
            }
        }
        return deployInfoMap;
    }

    @ApiOperation(value="\u66f4\u65b0\u6570\u636e\u6307\u6807")
    @PostMapping(value={"field/update"})
    public void updateDataField(@RequestBody QueryDataFieldVO fieldVO) throws JQException {
        LOGGER.debug("\u66f4\u65b0\u6570\u636e\u6307\u6807\uff1a{}[{}]{}\u3002", fieldVO.getTitle(), fieldVO.getCode(), fieldVO.getDataFieldKind());
        try {
            DesignDataField dataField = QueryEntityUtil.fieldVO2Entity(this.designDataSchemeService, fieldVO);
            dataField.setUpdateTime(null);
            this.fieldValidator.levelCheckField(dataField);
            this.designDataSchemeService.updateQueryDataField(dataField);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u6307\u6807/\u5b57\u6bb5\u6279\u91cf\u66f4\u65b0")
    @PostMapping(value={"field/batch/update"})
    public void batchUpdateDataField(@RequestBody QueryBatUpDataFieldVO vo) throws JQException {
        List fieldKeys = vo.getKeys();
        if (org.springframework.util.CollectionUtils.isEmpty(fieldKeys)) {
            return;
        }
        List dataFields = this.designDataSchemeService.getDataFields(fieldKeys);
        try {
            for (DesignDataField designDataField : dataFields) {
                QueryEntityUtil.updateDataField(designDataField, vo);
                this.fieldValidator.levelCheckField(designDataField);
                designDataField.setUpdateTime(null);
            }
            this.designDataSchemeService.updateQueryDataFields(dataFields);
        }
        catch (SchemeDataException e) {
            LOGGER.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u6307\u6807\u4e0a\u4e0b\u79fb\u52a8")
    @PostMapping(value={"field/move"})
    public DataFieldPageVO<List<QueryDataFieldVO>> moveField(@RequestBody DataFieldMovePM move) throws JQException {
        LOGGER.debug("\u79fb\u52a8\u6307\u6807 {}", (Object)move);
        List keys = move.getKeys();
        if (org.springframework.util.CollectionUtils.isEmpty(keys)) {
            throw new SchemeDataException("\u53c2\u6570\u9519\u8bef");
        }
        boolean moveUp = move.isMoveUp();
        Integer limit = move.getLimit();
        if (limit == null) {
            throw new SchemeDataException("\u53c2\u6570\u9519\u8bef");
        }
        if (limit < 1) {
            throw new SchemeDataException("\u53c2\u6570\u9519\u8bef");
        }
        move.setLimit(Integer.valueOf(limit - 1));
        Integer skip = move.getSkip();
        if (skip == null) {
            throw new SchemeDataException("\u53c2\u6570\u9519\u8bef");
        }
        if (skip < 1) {
            throw new SchemeDataException("\u53c2\u6570\u9519\u8bef");
        }
        move.setSkip(Integer.valueOf(skip - 1));
        String table = move.getTable();
        if (table == null) {
            throw new SchemeDataException("\u53c2\u6570\u9519\u8bef");
        }
        try {
            List designDataFields = this.dataFieldDesignService.move(move);
            ArrayList<QueryDataFieldVO> list = new ArrayList<QueryDataFieldVO>();
            this.fillFieldInfo(table, designDataFields, list, null);
            DataFieldPageVO pageVO = new DataFieldPageVO();
            pageVO.setValue(list);
            return pageVO;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @ApiOperation(value="\u5bfc\u5165\u6570\u636e\u6307\u6807")
    @PostMapping(value={"/table/import"})
    @ResponseBody
    public Result<String> importScheme(@RequestParam MultipartFile file, @RequestParam String context) throws JQException {
        Assert.notNull((Object)context, (String)"tableKey must not be null.");
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(context);
        if (!this.dataSchemeAuthService.canWriteScheme(dataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream());){
            Sheet sheet = workbook.getSheetAt(0);
            Assert.notNull((Object)sheet, (String)"sheet must not be null.");
            try {
                this.dataFieldIOService.imports(context, sheet);
            }
            catch (SchemeDataException e) {
                Result result;
                Throwable throwable;
                ByteArrayOutputStream out;
                block34: {
                    LOGGER.error(e.getMessage(), e);
                    out = new ByteArrayOutputStream();
                    throwable = null;
                    try {
                        sheet.autoSizeColumn(23);
                        workbook.write(out);
                        byte[] bytes = out.toByteArray();
                        FileAreaService area = this.fileService.area("DATASCHEME");
                        FileInfo upload = area.upload(file.getOriginalFilename(), XSSFWorkbookType.XLSX.getExtension(), bytes);
                        String key = upload.getKey();
                        result = Result.failed((Object)key, (String)(e.getMessage() + ",\u70b9\u51fb\u4e0b\u8f7d\u7ee7\u7eed\u4fee\u6539"));
                        if (workbook == null) return result;
                        if (var5_6 != null) {
                        }
                        break block34;
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    try {
                        workbook.close();
                        return result;
                    }
                    catch (Throwable throwable3) {
                        var5_6.addSuppressed(throwable3);
                        return result;
                    }
                }
                workbook.close();
                return result;
                finally {
                    if (out != null) {
                        if (throwable != null) {
                            try {
                                out.close();
                            }
                            catch (Throwable throwable4) {
                                throwable.addSuppressed(throwable4);
                            }
                        } else {
                            out.close();
                        }
                    }
                }
            }
            Result result = Result.succeed((String)"\u5bfc\u5165\u6210\u529f\uff01");
            return result;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new Result("410", e.getMessage(), null);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u5bfc\u51fa\u6307\u6807\u5230\u6587\u4ef6")
    @PostMapping(value={"/table/export"})
    public void exportTable(HttpServletResponse response, @RequestParam String tableKey) throws IOException, JQException {
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(tableKey);
        if (!this.dataSchemeAuthService.canReadScheme(dataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        ServletOutputStream outputStream = null;
        try (XSSFWorkbook wb = new XSSFWorkbook();){
            Sheet sheet = wb.createSheet();
            String fileName = this.dataFieldIOService.export(tableKey, sheet);
            outputStream = response.getOutputStream();
            QueryDataFieldRestController.extracted(response, fileName);
            wb.write((OutputStream)outputStream);
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    @ApiOperation(value="\u540c\u6b65\u8868\u7ed3\u6784")
    @PostMapping(value={"/table/sync"})
    public void syncTable(@RequestParam String tableKey) throws JQException {
        QueryDataTableDTO queryDataTable = this.designDataSchemeService.getQueryDataTable(tableKey);
        if (!this.dataSchemeAuthService.canWriteScheme(queryDataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        IDataTableFactory factory = DataTableFactoryManager.getInstance().getFactory(queryDataTable.getTableType());
        AbstractDataTableAdapter adapter = factory.getDataTableAdapter();
        try {
            adapter.flushDataTable(tableKey);
        }
        catch (DataTableAdaptException e) {
            LOGGER.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DS_3, e.getMessage());
        }
    }

    protected static void extracted(HttpServletResponse response, String fileName) throws IOException {
        try {
            fileName = URLEncoder.encode(fileName, "utf-8").replace("\\+", "%20");
            fileName = "attachment;filename=" + fileName + "." + XSSFWorkbookType.XLSX.getExtension();
            HtmlUtils.validateHeaderValue((String)fileName);
            response.setHeader("Content-disposition", fileName);
            response.setContentType("application/octet-stream");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }
}

