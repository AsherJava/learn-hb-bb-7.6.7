/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.kernel.pdf.PdfDocument
 *  com.itextpdf.kernel.pdf.PdfReader
 *  com.itextpdf.kernel.pdf.PdfWriter
 *  com.itextpdf.kernel.pdf.WriterProperties
 *  com.itextpdf.kernel.utils.PdfMerger
 *  com.jiuqi.bi.office.excel.spire.SpireHelper
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.grid.Font
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.np.period.PeriodLanguage
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.excel.param.ExcelRule
 *  com.jiuqi.nr.data.excel.service.IDataExportService
 *  com.jiuqi.nr.data.text.param.TextParams
 *  com.jiuqi.nr.data.text.param.TextType
 *  com.jiuqi.nr.data.text.service.ExpTextService
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IRunTimePrintController
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.print.common.define.DefaultPageNumberGenerateStrategy
 *  com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy
 *  com.jiuqi.nr.definition.facade.print.common.define.PageNumberGenerateStrategyImpl
 *  com.jiuqi.nr.definition.facade.print.common.other.PrintUtil
 *  com.jiuqi.nr.definition.facade.print.core.DocumentBuildUtil
 *  com.jiuqi.nr.definition.facade.print.core.FontConvertUtil
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.PrintRunTimeWithAuthService
 *  com.jiuqi.nr.definition.print.service.IPrintSchemeService
 *  com.jiuqi.nr.definition.print.service.IPrintTemplateService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.fileupload.exception.FileUploadException
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.input.OptTypes
 *  com.jiuqi.nr.io.service.impl.CsvExportServiceImpl
 *  com.jiuqi.nr.io.service.impl.TxtExportServiceImpl
 *  com.jiuqi.nr.io.util.FileUtil
 *  com.jiuqi.nr.jtable.exception.NotFoundEntityException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.params.output.SecretLevelInfo
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  com.jiuqi.nr.jtable.service.ICustomRegionsGradeService
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.xg.draw2d.IPrintDevice
 *  com.jiuqi.xg.draw2d.XG
 *  com.jiuqi.xg.print.PrinterDevice
 *  com.jiuqi.xg.print.PrinterInfo
 *  com.jiuqi.xg.print.SimplePaintInteractor
 *  com.jiuqi.xg.print.util.AsyncWorkContainnerUtil
 *  com.jiuqi.xg.print.util.PrintProcessUtil
 *  com.jiuqi.xg.process.GraphicalFactoryManager
 *  com.jiuqi.xg.process.IContentStream
 *  com.jiuqi.xg.process.IGraphicalElement
 *  com.jiuqi.xg.process.IPaginateContext
 *  com.jiuqi.xg.process.IPaginateInteractor
 *  com.jiuqi.xg.process.IPaintInteractor
 *  com.jiuqi.xg.process.IProcessMonitor
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplateObjectFactory
 *  com.jiuqi.xg.process.ITemplatePage
 *  com.jiuqi.xg.process.Paper
 *  com.jiuqi.xg.process.SimpleProcessMonitor
 *  com.jiuqi.xg.process.impl.PaginateContext
 *  com.jiuqi.xg.process.obj.DocumentTemplateObject
 *  com.jiuqi.xg.process.obj.PageTemplateObject
 *  com.jiuqi.xg.process.obj.TextTemplateObject
 *  com.jiuqi.xg.process.table.TableLineConfig
 *  com.jiuqi.xg.process.util.ProcessUtil
 *  com.jiuqi.xg.process.util.SerializeUtil
 *  com.jiuqi.xlib.ICallBack
 *  com.jiuqi.xlib.utils.GUID
 *  com.spire.pdf.FileFormat
 *  com.spire.pdf.PdfDocument
 *  javax.annotation.Resource
 *  net.lingala.zip4j.io.outputstream.ZipOutputStream
 *  net.lingala.zip4j.model.ZipParameters
 *  net.lingala.zip4j.model.enums.CompressionLevel
 *  net.lingala.zip4j.model.enums.CompressionMethod
 *  net.lingala.zip4j.model.enums.EncryptionMethod
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.utils.PdfMerger;
import com.jiuqi.bi.office.excel.spire.SpireHelper;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.grid.GridData;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.grid.Font;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.np.period.PeriodLanguage;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.excel.param.ExcelRule;
import com.jiuqi.nr.data.excel.service.IDataExportService;
import com.jiuqi.nr.data.text.param.TextParams;
import com.jiuqi.nr.data.text.param.TextType;
import com.jiuqi.nr.data.text.service.ExpTextService;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataentry.bean.BatchExportData;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.BatchExportTask;
import com.jiuqi.nr.dataentry.bean.BatchPrintInfo;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.ExportHandleCurrParam;
import com.jiuqi.nr.dataentry.bean.ExportHandleParam;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.bean.LogInfo;
import com.jiuqi.nr.dataentry.export.IDataEntryExportService;
import com.jiuqi.nr.dataentry.export.IReportExport;
import com.jiuqi.nr.dataentry.internal.service.CurrencyService;
import com.jiuqi.nr.dataentry.internal.service.ExportExcelNameServiceImpl;
import com.jiuqi.nr.dataentry.internal.service.ExportPdfServiceImpl;
import com.jiuqi.nr.dataentry.internal.service.JioBatchExportExecuter;
import com.jiuqi.nr.dataentry.internal.thread.BatchExpDimThread;
import com.jiuqi.nr.dataentry.internal.thread.BatchExpFormThread;
import com.jiuqi.nr.dataentry.model.BatchDimensionParam;
import com.jiuqi.nr.dataentry.monitor.BatchExportProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.paramInfo.PrintSumCover;
import com.jiuqi.nr.dataentry.print.common.interactor.PrintIPaginateInteractor;
import com.jiuqi.nr.dataentry.print.common.other.PDFPrintUtil2;
import com.jiuqi.nr.dataentry.print.common.param.PrintParam;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.IBatchExportService;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataentry.util.LoggerUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IRunTimePrintController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.print.common.define.DefaultPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.define.PageNumberGenerateStrategyImpl;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.core.DocumentBuildUtil;
import com.jiuqi.nr.definition.facade.print.core.FontConvertUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.PrintRunTimeWithAuthService;
import com.jiuqi.nr.definition.print.service.IPrintSchemeService;
import com.jiuqi.nr.definition.print.service.IPrintTemplateService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.fileupload.exception.FileUploadException;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.input.OptTypes;
import com.jiuqi.nr.io.service.impl.CsvExportServiceImpl;
import com.jiuqi.nr.io.service.impl.TxtExportServiceImpl;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nr.jtable.exception.NotFoundEntityException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.ICustomRegionsGradeService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.xg.draw2d.IPrintDevice;
import com.jiuqi.xg.draw2d.XG;
import com.jiuqi.xg.print.PrinterDevice;
import com.jiuqi.xg.print.PrinterInfo;
import com.jiuqi.xg.print.SimplePaintInteractor;
import com.jiuqi.xg.print.util.AsyncWorkContainnerUtil;
import com.jiuqi.xg.print.util.PrintProcessUtil;
import com.jiuqi.xg.process.GraphicalFactoryManager;
import com.jiuqi.xg.process.IContentStream;
import com.jiuqi.xg.process.IGraphicalElement;
import com.jiuqi.xg.process.IPaginateContext;
import com.jiuqi.xg.process.IPaginateInteractor;
import com.jiuqi.xg.process.IPaintInteractor;
import com.jiuqi.xg.process.IProcessMonitor;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplateObjectFactory;
import com.jiuqi.xg.process.ITemplatePage;
import com.jiuqi.xg.process.Paper;
import com.jiuqi.xg.process.SimpleProcessMonitor;
import com.jiuqi.xg.process.impl.PaginateContext;
import com.jiuqi.xg.process.obj.DocumentTemplateObject;
import com.jiuqi.xg.process.obj.PageTemplateObject;
import com.jiuqi.xg.process.obj.TextTemplateObject;
import com.jiuqi.xg.process.table.TableLineConfig;
import com.jiuqi.xg.process.util.ProcessUtil;
import com.jiuqi.xg.process.util.SerializeUtil;
import com.jiuqi.xlib.ICallBack;
import com.jiuqi.xlib.utils.GUID;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.LambdaMetafactory;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import javax.sql.DataSource;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value="EXPORT_BATCH_EXCEL")
public class BatchExportExcelServiceImpl
implements IBatchExportService {
    public static final Logger logger = LoggerFactory.getLogger(BatchExportExcelServiceImpl.class);
    private static final int BUFFER_SIZE = 2048;
    private static final String MODULEEXCEL = "\u6279\u91cf\u5bfc\u51fa";
    @Resource(name="EXPORT_EXCEL")
    IDataEntryExportService dataEntryExportService;
    @Autowired
    private ExportPdfServiceImpl exportPdfServiceImpl;
    @Autowired
    private IPeriodEntityAdapter iPeriodEntityAdapter;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired(required=false)
    private JioBatchExportExecuter jioBatchExportExecuter;
    @Autowired
    private IPrintDesignTimeController printController;
    @Autowired
    private IPrintRunTimeController printRunTimeController;
    @Autowired
    private IPrintTemplateService printTemplateService;
    @Autowired
    private IRunTimePrintController iRunTimePrintController;
    @Autowired
    private IDesignTimePrintController iDesignTimePrintController;
    @Autowired
    private DimensionValueProvider dimensionValueProvider;
    @Autowired
    private ReadWriteAccessProvider readWriteAccessProvider;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private IPrintSchemeService printSchemeService;
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private TxtExportServiceImpl txtExportService;
    @Autowired
    private CsvExportServiceImpl csvExportService;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private IReportExport reportExportService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ExportExcelNameServiceImpl exportExcelNameService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private ISecretLevelService iSecretLevelService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    @Autowired
    @Qualifier(value="formExcelRule")
    private ExcelRule formRule;
    @Autowired
    @Qualifier(value="defaultExcelRule")
    private ExcelRule dwRule;
    @Autowired
    private IDataExportService dataExportService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private ExpTextService expTextService;
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    @Autowired(required=false)
    private ICustomRegionsGradeService iCustomRegionsGradeService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private PrintRunTimeWithAuthService printRunTimeWithAuthService;
    @Value(value="${jiuqi.nr.dataentry.export.zipWithPassword:false}")
    private boolean zipWithPassword;
    private AtomicInteger ThreadSum = new AtomicInteger(0);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public void export(BatchExportInfo batchExportInfo, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        block253: {
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                return;
            }
            formKeyList = batchExportInfo.getFormKeys();
            noData = "no_data";
            noEligibleData = "no_eligible_data";
            noSubject = "no_subject";
            if (formKeyList != null && formKeyList.size() > 0) {
                analyFormNum = 0;
                for (String formKey : formKeyList) {
                    formDefine = this.runtimeViewController.queryFormById(formKey);
                    if (formDefine.getFormType() != FormType.FORM_TYPE_ANALYSISREPORT) continue;
                    ++analyFormNum;
                }
                if (analyFormNum == formKeyList.size()) {
                    if (batchExportInfo.isExportEmptyTable()) {
                        asyncTaskMonitor.finish(noEligibleData, (Object)"");
                    } else {
                        asyncTaskMonitor.finish(noData, (Object)"");
                    }
                    return;
                }
            }
            singleFileName = batchExportInfo.getFileName();
            dirUU = UUID.randomUUID().toString();
            exportTask = null;
            batchReturnInfo = new BatchReturnInfo();
            selectDateList /* !! */  = new ArrayList<E>();
            multiplePeriodInfoList = new ArrayList<String>();
            dimensionValueMap = new HashMap<String, DimensionValue>();
            keySet = batchExportInfo.getContext().getDimensionSet().keySet();
            schemePeriodLinkDefineList = this.runtimeViewController.querySchemePeriodLinkByTask(batchExportInfo.getContext().getTaskKey());
            for (String set : keySet) {
                dimensionValue = new DimensionValue();
                dimensionValue.setName(((DimensionValue)batchExportInfo.getContext().getDimensionSet().get(set)).getName());
                dimensionValue.setType(((DimensionValue)batchExportInfo.getContext().getDimensionSet().get(set)).getType());
                dimensionValue.setValue(((DimensionValue)batchExportInfo.getContext().getDimensionSet().get(set)).getValue());
                dimensionValueMap.put(set, dimensionValue);
            }
            allIsNull = true;
            selectFormCodeTitle = new HashMap<String, String>();
            formSchemeKey = batchExportInfo.getContext().getFormSchemeKey();
            printSchemeKey = batchExportInfo.getPrintSchemeKey();
            formulaSchemeKey = batchExportInfo.getContext().getFormulaSchemeKey();
            printSchemeTitle = "";
            if (printSchemeKey != null) {
                designPrintTemplateSchemeDefine = this.printController.queryPrintTemplateSchemeDefine(printSchemeKey);
                printSchemeTitle = designPrintTemplateSchemeDefine.getTitle();
            }
            taskDefine = this.runtimeViewController.queryTaskDefine(batchExportInfo.getContext().getTaskKey());
            formKey = batchExportInfo.getContext().getFormKey();
            if (batchExportInfo.isMultiplePeriod()) {
                if (batchExportInfo.getMultiplePeriodInfo().contains("-")) {
                    multiplePeriodInfo = batchExportInfo.getMultiplePeriodInfo();
                    multiplePeriodInfoArr = multiplePeriodInfo.split("-");
                    startTime = multiplePeriodInfoArr[0];
                    endTime = multiplePeriodInfoArr[1].split(",")[0];
                    selectDateList /* !! */  = this.iPeriodEntityAdapter.getPeriodCodeByDataRegion(taskDefine.getDateTime(), (String)startTime, endTime);
                } else {
                    multiplePeriodInfo = batchExportInfo.getMultiplePeriodInfo();
                    selectDateList /* !! */  = Arrays.asList(multiplePeriodInfo.split(","));
                }
                if (StringUtils.isNotEmpty((String)batchExportInfo.getContext().getFormKey())) {
                    selectFormKeys = Arrays.asList(batchExportInfo.getContext().getFormKey().split(";"));
                    formDefines = this.runtimeViewController.queryAllFormDefinesByFormScheme(batchExportInfo.getContext().getFormSchemeKey());
                    for (FormDefine formDefine : formDefines) {
                        if (!selectFormKeys.contains(formDefine.getKey())) continue;
                        selectFormCodeTitle.put(formDefine.getFormCode(), formDefine.getTitle());
                    }
                }
            } else {
                canExportPeriod = false;
                periodInfo = ((DimensionValue)batchExportInfo.getContext().getDimensionSet().get("DATATIME")).getValue();
                if (schemePeriodLinkDefineList != null && StringUtils.isNotEmpty((String)periodInfo)) {
                    for (Object schemePeriodLinkDefine : schemePeriodLinkDefineList) {
                        if (!schemePeriodLinkDefine.getPeriodKey().equals(periodInfo)) continue;
                        canExportPeriod = true;
                        break;
                    }
                }
                if (!canExportPeriod) {
                    asyncTaskMonitor.finish(noSubject, (Object)"");
                    return;
                }
            }
            if ((multiplePeriodInfoListBeforeFormat = this.getmultiplePeriodInfoList(batchExportInfo.getContext(), selectDateList /* !! */ , batchExportInfo.getPeriodType())) == null || multiplePeriodInfoListBeforeFormat.size() <= 0) {
                multiplePeriodInfoListBeforeFormat.addAll(selectDateList /* !! */ );
            }
            for (String periodInfo : multiplePeriodInfoListBeforeFormat) {
                for (SchemePeriodLinkDefine schemePeriodLinkDefine : schemePeriodLinkDefineList) {
                    if (!schemePeriodLinkDefine.getPeriodKey().equals(periodInfo)) continue;
                    multiplePeriodInfoList.add(periodInfo);
                }
            }
            batchExportTaskList = new ArrayList<BatchExportTask>();
            dataentryExportTypeObj = this.iTaskOptionController.getValue(batchExportInfo.getContext().getTaskKey(), "EXPORT_FILE_TYPE");
            exportType = "";
            if (batchExportInfo.getFileType().equals("EXPORT_BATCH_JIO")) {
                exportType = "JIO";
            } else if (batchExportInfo.getFileType().equals("EXPORT_BATCH_PDF")) {
                exportType = "PDF";
            } else if (batchExportInfo.getFileType().equals("EXPORT_BATCH_EXCEL")) {
                exportType = "EXCEL";
            } else if (batchExportInfo.getFileType().equals("EXPORT_BATCH_TXT")) {
                exportType = "TXT";
            } else if (batchExportInfo.getFileType().equals("EXPORT_BATCH_CSV")) {
                exportType = "CSV";
            } else if (batchExportInfo.getFileType().equals("EXPORT_BATCH_OFD")) {
                exportType = "OFD";
            }
            if (StringUtils.isEmpty((String)dataentryExportTypeObj) || !dataentryExportTypeObj.contains(exportType)) {
                taskOption = "task_option_no_start";
                asyncTaskMonitor.error(taskOption, null, exportType);
                return;
            }
            try {
                block259: {
                    block258: {
                        block257: {
                            if (!"EXPORT_BATCH_PDF".equals(batchExportInfo.getFileType()) && !"EXPORT_BATCH_OFD".equals(batchExportInfo.getFileType())) break block257;
                            filePathUuid = UUID.randomUUID().toString();
                            deletePathFile = BatchExportConsts.EXPORTDIR + BatchExportConsts.SEPARATOR + "concatPDF" + BatchExportConsts.SEPARATOR + filePathUuid;
                            if (!batchExportInfo.isMultiplePeriod()) ** GOTO lbl194
                            if (multiplePeriodInfoList != null && multiplePeriodInfoList.size() > 0) {
                                for (i = 0; i < multiplePeriodInfoList.size(); ++i) {
                                    if (asyncTaskMonitor.isCancel()) {
                                        asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                                        LogHelper.info((String)"\u6279\u91cf\u5bfc\u51fa", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                                        return;
                                    }
                                    dimensionValueMapNew = new HashMap<String, DimensionValue>();
                                    keySetNew = dimensionValueMap.keySet();
                                    for (String set : keySetNew) {
                                        dimensionValue = new DimensionValue();
                                        dimensionValue.setName(((DimensionValue)dimensionValueMap.get(set)).getName());
                                        dimensionValue.setType(((DimensionValue)dimensionValueMap.get(set)).getType());
                                        dimensionValue.setValue(((DimensionValue)dimensionValueMap.get(set)).getValue());
                                        dimensionValueMapNew.put(set, dimensionValue);
                                    }
                                    batchExportInfo.getContext().setDimensionSet(dimensionValueMapNew);
                                    ((DimensionValue)batchExportInfo.getContext().getDimensionSet().get("DATATIME")).setValue((String)multiplePeriodInfoList.get(i));
                                    ((DimensionValue)batchExportInfo.getContext().getDimensionSet().get("DATATIME")).setType(batchExportInfo.getPeriodType().intValue());
                                    periodKey = (String)multiplePeriodInfoList.get(i);
                                    filter = schemePeriodLinkDefineList.stream().filter((Predicate<SchemePeriodLinkDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$export$0(java.lang.String com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine ), (Lcom/jiuqi/nr/definition/facade/SchemePeriodLinkDefine;)Z)((String)periodKey)).collect(Collectors.toList());
                                    currSchemeKey = ((SchemePeriodLinkDefine)filter.get(0)).getSchemeKey();
                                    batchExportInfo.getContext().setFormSchemeKey(currSchemeKey);
                                    batchExportInfo.getContext().setFormKey(formKey);
                                    batchExportInfo.setPrintSchemeKey(printSchemeKey);
                                    batchExportInfo.getContext().setFormulaSchemeKey(formulaSchemeKey);
                                    if (!((SchemePeriodLinkDefine)filter.get(0)).getSchemeKey().equals(formSchemeKey) && StringUtils.isNotEmpty((String)formKey)) {
                                        printtiltle = printSchemeTitle;
                                        printSchemes = this.printController.getAllPrintSchemeByFormScheme(currSchemeKey);
                                        if (printSchemes.size() > 0) {
                                            printSchemesFilter = printSchemes.stream().filter((Predicate<DesignPrintTemplateSchemeDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$export$1(java.lang.String com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine ), (Lcom/jiuqi/nr/definition/facade/DesignPrintTemplateSchemeDefine;)Z)((String)printtiltle)).collect(Collectors.toList());
                                            if (printSchemesFilter.size() > 0) {
                                                batchExportInfo.setPrintSchemeKey(((DesignPrintTemplateSchemeDefine)printSchemesFilter.get(0)).getKey());
                                            } else {
                                                batchExportInfo.setPrintSchemeKey(((DesignPrintTemplateSchemeDefine)printSchemes.get(0)).getKey());
                                            }
                                        } else {
                                            batchExportInfo.setPrintSchemeKey(null);
                                        }
                                        defaultFormulaSchemeInFormScheme = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(currSchemeKey);
                                        if (defaultFormulaSchemeInFormScheme != null) {
                                            batchExportInfo.getContext().setFormulaSchemeKey(defaultFormulaSchemeInFormScheme.getKey());
                                        } else {
                                            batchExportInfo.getContext().setFormulaSchemeKey(null);
                                        }
                                        selectFormKey = new HashSet<String>();
                                        currFormDefines = this.runtimeViewController.queryAllFormDefinesByFormScheme(currSchemeKey);
                                        for (Map.Entry<K, V> entry : selectFormCodeTitle.entrySet()) {
                                            titleFilter = currFormDefines.stream().filter((Predicate<FormDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$export$2(java.util.Map$Entry com.jiuqi.nr.definition.facade.FormDefine ), (Lcom/jiuqi/nr/definition/facade/FormDefine;)Z)(entry)).collect(Collectors.toList());
                                            if (titleFilter.size() > 0) {
                                                for (Object formDefine : titleFilter) {
                                                    selectFormKey.add(formDefine.getKey());
                                                }
                                                continue;
                                            }
                                            codeFilter = currFormDefines.stream().filter((Predicate<FormDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$export$3(java.util.Map$Entry com.jiuqi.nr.definition.facade.FormDefine ), (Lcom/jiuqi/nr/definition/facade/FormDefine;)Z)(entry)).collect(Collectors.toList());
                                            formDefine = codeFilter.iterator();
                                            while (formDefine.hasNext()) {
                                                formDefine = (FormDefine)formDefine.next();
                                                selectFormKey.add(formDefine.getKey());
                                            }
                                        }
                                        if (selectFormKey.size() <= 0) continue;
                                        batchExportInfo.getContext().setFormKey(selectFormKey.stream().collect(Collectors.joining(";")));
                                    }
                                    this.handlerBatchExport(batchExportInfo, dirUU);
                                    numOfIndex = (double)(i + 1) / ((double)multiplePeriodInfoList.size() * 1.0);
                                    exportTask = this.exportToDatasByPdf(filePathUuid, batchExportInfo, asyncTaskMonitor, numOfIndex);
                                    if (asyncTaskMonitor.isCancel()) {
                                        asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                                        LogHelper.info((String)"\u6279\u91cf\u5bfc\u51fa", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                                        return;
                                    }
                                    if (null == exportTask.getExportDatas() || exportTask.getExportDatas().size() == 0) continue;
                                    allIsNull = false;
                                    batchExportTaskList.add(exportTask);
                                }
                            } else {
                                asyncTaskMonitor.finish(noSubject, (Object)"");
                                return;
lbl194:
                                // 1 sources

                                this.handlerBatchExport(batchExportInfo, dirUU);
                                exportTask = this.exportToDatasByPdf(filePathUuid, batchExportInfo, asyncTaskMonitor, 1.0);
                                if (asyncTaskMonitor.isCancel()) {
                                    asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                                    LogHelper.info((String)"\u6279\u91cf\u5bfc\u51fa", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                                    return;
                                }
                                if (null != exportTask.getExportDatas() && exportTask.getExportDatas().size() != 0) {
                                    allIsNull = false;
                                    batchExportTaskList.add(exportTask);
                                }
                            }
                            if (allIsNull) {
                                if (batchExportInfo.isExportEmptyTable() && batchExportInfo.isExportZero() || batchExportInfo.isExportEmptyTable() && !batchExportInfo.isExportZero()) {
                                    asyncTaskMonitor.finish(noEligibleData, (Object)"");
                                } else {
                                    asyncTaskMonitor.finish(noData, (Object)"");
                                }
                                return;
                            }
                            fileName = batchExportInfo.getFileName();
                            if (batchExportInfo.getSecretLevel() != null) {
                                fileName = batchExportInfo.getFileName() + this.exportExcelNameService.getSysSeparator() + batchExportInfo.getSecretLevel();
                                fileLocation = this.cacheObjectResourceRemote.find((Object)batchExportInfo.getDownLoadKey()).toString();
                                index = fileLocation.lastIndexOf(batchExportInfo.getFileName());
                                fileLocationAfterFormat = fileLocation.substring(0, index) + fileName + fileLocation.substring(index + batchExportInfo.getFileName().length());
                                this.cacheObjectResourceRemote.create((Object)batchExportInfo.getDownLoadKey(), (Object)fileLocationAfterFormat);
                            }
                            try {
                                suffix = ".pdf";
                                if ("EXPORT_BATCH_OFD".equals(batchExportInfo.getFileType())) {
                                    suffix = ".ofd";
                                }
                                fileInfoKey = "";
                                fileInfoKey = this.zipWithPassword != false ? this.exportToZipWithPassword(batchExportInfo, batchExportTaskList, batchExportInfo.getLocation(), fileName, suffix, batchExportInfo.getCompressionType(), batchExportInfo.getDownLoadKey()) : this.exportToZip(batchExportInfo, batchExportTaskList, batchExportInfo.getLocation(), fileName, suffix, batchExportInfo.getCompressionType(), batchExportInfo.getDownLoadKey());
                                this.cacheObjectResourceRemote.create((Object)(batchExportInfo.getDownLoadKey() + "_fileInfoKey"), (Object)fileInfoKey);
                                break block253;
                            }
                            catch (Exception e) {
                                BatchExportExcelServiceImpl.logger.error("\u5bfc\u51fa\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                                asyncTaskMonitor.error(e.getMessage(), (Throwable)e);
                                return;
                            }
                            finally {
                                if (StringUtils.isNotEmpty((String)deletePathFile) && !batchExportInfo.isSpecifyPath()) {
                                    FileUtils.deleteDirectory(new File(deletePathFile));
                                }
                            }
                        }
                        if (!"EXPORT_BATCH_EXCEL".equals(batchExportInfo.getFileType())) break block258;
                        threadEnable = this.nvwaSystemOptionService.get("nr-data-entry-group", "SELECT_BATCH_EXPORT_EXCEL_THREAD");
                        try {
                            if (threadEnable != null && threadEnable.equals("1")) {
                                this.ThreadSum.getAndIncrement();
                            }
                            if (!batchExportInfo.isMultiplePeriod()) ** GOTO lbl320
                            if (multiplePeriodInfoList != null && multiplePeriodInfoList.size() > 0) {
                                for (i = 0; i < multiplePeriodInfoList.size(); ++i) {
                                    if (asyncTaskMonitor.isCancel()) {
                                        asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                                        LogHelper.info((String)"\u6279\u91cf\u5bfc\u51fa", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                                        return;
                                    }
                                    dimensionValueMapNew = new HashMap<String, DimensionValue>();
                                    keySetNew = dimensionValueMap.keySet();
                                    for (String set : keySetNew) {
                                        dimensionValue = new DimensionValue();
                                        dimensionValue.setName(((DimensionValue)dimensionValueMap.get(set)).getName());
                                        dimensionValue.setType(((DimensionValue)dimensionValueMap.get(set)).getType());
                                        dimensionValue.setValue(((DimensionValue)dimensionValueMap.get(set)).getValue());
                                        dimensionValueMapNew.put(set, dimensionValue);
                                    }
                                    batchExportInfo.getContext().setDimensionSet(dimensionValueMapNew);
                                    ((DimensionValue)batchExportInfo.getContext().getDimensionSet().get("DATATIME")).setValue((String)multiplePeriodInfoList.get(i));
                                    ((DimensionValue)batchExportInfo.getContext().getDimensionSet().get("DATATIME")).setType(batchExportInfo.getPeriodType().intValue());
                                    periodKey = (String)multiplePeriodInfoList.get(i);
                                    filter = schemePeriodLinkDefineList.stream().filter((Predicate<SchemePeriodLinkDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$export$4(java.lang.String com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine ), (Lcom/jiuqi/nr/definition/facade/SchemePeriodLinkDefine;)Z)((String)periodKey)).collect(Collectors.toList());
                                    currSchemeKey = ((SchemePeriodLinkDefine)filter.get(0)).getSchemeKey();
                                    batchExportInfo.getContext().setFormSchemeKey(currSchemeKey);
                                    batchExportInfo.getContext().setFormKey(formKey);
                                    batchExportInfo.setPrintSchemeKey(printSchemeKey);
                                    batchExportInfo.getContext().setFormulaSchemeKey(this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(currSchemeKey).getKey());
                                    if (!((SchemePeriodLinkDefine)filter.get(0)).getSchemeKey().equals(formSchemeKey) && StringUtils.isNotEmpty((String)formKey)) {
                                        printtiltle = printSchemeTitle;
                                        printSchemes = this.printController.getAllPrintSchemeByFormScheme(currSchemeKey);
                                        if (printSchemes.size() > 0) {
                                            printSchemesFilter = printSchemes.stream().filter((Predicate<DesignPrintTemplateSchemeDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$export$5(java.lang.String com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine ), (Lcom/jiuqi/nr/definition/facade/DesignPrintTemplateSchemeDefine;)Z)((String)printtiltle)).collect(Collectors.toList());
                                            if (printSchemesFilter.size() > 0) {
                                                batchExportInfo.setPrintSchemeKey(((DesignPrintTemplateSchemeDefine)printSchemesFilter.get(0)).getKey());
                                            } else {
                                                batchExportInfo.setPrintSchemeKey(((DesignPrintTemplateSchemeDefine)printSchemes.get(0)).getKey());
                                            }
                                        } else {
                                            batchExportInfo.setPrintSchemeKey(null);
                                        }
                                        defaultFormulaSchemeInFormScheme = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(currSchemeKey);
                                        if (defaultFormulaSchemeInFormScheme != null) {
                                            batchExportInfo.getContext().setFormulaSchemeKey(defaultFormulaSchemeInFormScheme.getKey());
                                        } else {
                                            batchExportInfo.getContext().setFormulaSchemeKey(null);
                                        }
                                        selectFormKey = new HashSet<String>();
                                        currFormDefines = this.runtimeViewController.queryAllFormDefinesByFormScheme(currSchemeKey);
                                        for (Map.Entry<K, V> entry : selectFormCodeTitle.entrySet()) {
                                            titleFilter = currFormDefines.stream().filter((Predicate<FormDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$export$6(java.util.Map$Entry com.jiuqi.nr.definition.facade.FormDefine ), (Lcom/jiuqi/nr/definition/facade/FormDefine;)Z)(entry)).collect(Collectors.toList());
                                            if (titleFilter.size() > 0) {
                                                for (Object formDefine : titleFilter) {
                                                    selectFormKey.add(formDefine.getKey());
                                                }
                                                continue;
                                            }
                                            codeFilter = currFormDefines.stream().filter((Predicate<FormDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$export$7(java.util.Map$Entry com.jiuqi.nr.definition.facade.FormDefine ), (Lcom/jiuqi/nr/definition/facade/FormDefine;)Z)(entry)).collect(Collectors.toList());
                                            formDefine = codeFilter.iterator();
                                            while (formDefine.hasNext()) {
                                                formDefine = (FormDefine)formDefine.next();
                                                selectFormKey.add(formDefine.getKey());
                                            }
                                        }
                                        if (selectFormKey.size() <= 0) continue;
                                        batchExportInfo.getContext().setFormKey(selectFormKey.stream().collect(Collectors.joining(";")));
                                    }
                                    this.handlerBatchExport(batchExportInfo, dirUU);
                                    numOfIndex = (double)i / ((double)multiplePeriodInfoList.size() * 1.0) * 0.9;
                                    coefficient = (double)(0.9f / (float)multiplePeriodInfoList.size()) * 1.0;
                                    exportTask = this.exportToDatas(batchExportInfo, asyncTaskMonitor, numOfIndex, coefficient);
                                    if (asyncTaskMonitor.isCancel()) {
                                        asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                                        LogHelper.info((String)"\u6279\u91cf\u5bfc\u51fa", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                                        return;
                                    }
                                    if (exportTask == null || null == exportTask.getExportDatas() || exportTask.getExportDatas().size() == 0) continue;
                                    allIsNull = false;
                                    batchExportTaskList.add(exportTask);
                                }
                            } else {
                                if (!asyncTaskMonitor.isFinish()) {
                                    asyncTaskMonitor.finish(noSubject, (Object)"");
                                }
                                return;
lbl320:
                                // 1 sources

                                this.handlerBatchExport(batchExportInfo, dirUU);
                                exportTask = this.exportToDatas(batchExportInfo, asyncTaskMonitor, 0.0, 0.9);
                                if (asyncTaskMonitor.isCancel()) {
                                    asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                                    LogHelper.info((String)"\u6279\u91cf\u5bfc\u51fa", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                                    return;
                                }
                                if (exportTask != null && null != exportTask.getExportDatas() && exportTask.getExportDatas().size() != 0) {
                                    allIsNull = false;
                                }
                                batchExportTaskList.add(exportTask);
                            }
                            if (allIsNull) {
                                if (batchExportInfo.isExportEmptyTable() && batchExportInfo.isExportZero() || batchExportInfo.isExportEmptyTable() && !batchExportInfo.isExportZero()) {
                                    if (!asyncTaskMonitor.isFinish()) {
                                        asyncTaskMonitor.finish(noEligibleData, (Object)"");
                                    }
                                } else if (!asyncTaskMonitor.isFinish()) {
                                    asyncTaskMonitor.finish(noData, (Object)"");
                                }
                                return;
                            }
                            fileName = batchExportInfo.getFileName();
                            if (batchExportInfo.getSecretLevel() != null) {
                                fileName = batchExportInfo.getFileName() + this.exportExcelNameService.getSysSeparator() + batchExportInfo.getSecretLevel();
                                fileLocation = this.cacheObjectResourceRemote.find((Object)batchExportInfo.getDownLoadKey()).toString();
                                index = fileLocation.lastIndexOf(batchExportInfo.getFileName());
                                fileLocationAfterFormat = fileLocation.substring(0, index) + fileName + fileLocation.substring(index + batchExportInfo.getFileName().length());
                                this.cacheObjectResourceRemote.create((Object)batchExportInfo.getDownLoadKey(), (Object)fileLocationAfterFormat);
                            }
                            suffix = ".xlsx";
                            if (batchExportInfo.isExportETFile()) {
                                suffix = ".et";
                            }
                            if (batchExportInfo.getExcelExpPath() == null) {
                                batchExportInfo.setExcelExpPath(batchExportInfo.getLocation());
                            }
                            infos = new ArrayList<E>();
                            secretLevelEnable = this.iSecretLevelService.secretLevelEnable(batchExportInfo.getContext().getTaskKey());
                            if (secretLevelEnable && exportTask.getUnitCodes().size() > 0) {
                                context = batchExportInfo.getContext();
                                formScheme = this.runtimeViewController.getFormScheme(context.getFormSchemeKey());
                                queryEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
                                values = new StringBuilder();
                                for (String unitCode : exportTask.getUnitCodes()) {
                                    values.append(unitCode + ";");
                                }
                                values.delete(values.length() - 1, values.length());
                                ((DimensionValue)context.getDimensionSet().get(queryEntity.getDimensionName())).setValue(values.toString());
                                secretLevelInfos = this.iSecretLevelService.querySecretLevels(batchExportInfo.getContext());
                                if (secretLevelInfos.size() > 1) {
                                    Collections.sort(secretLevelInfos, new Comparator<SecretLevelInfo>(){

                                        @Override
                                        public int compare(SecretLevelInfo arg0, SecretLevelInfo arg1) {
                                            if (BatchExportExcelServiceImpl.this.iSecretLevelService.compareSercetLevel(arg0.getSecretLevelItem(), arg1.getSecretLevelItem())) {
                                                return -1;
                                            }
                                            return 1;
                                        }
                                    });
                                }
                                if (secretLevelInfos.size() > 0) {
                                    infos = secretLevelInfos;
                                } else {
                                    secretLevelInfo = new SecretLevelInfo();
                                    secretLevelItem = new SecretLevelItem();
                                    secretLevelItem.setTitle("\u975e\u5bc6");
                                    secretLevelInfo.setSecretLevelItem(secretLevelItem);
                                    infos.add(secretLevelInfo);
                                }
                            }
                            if (infos.size() > 0) {
                                fileName = fileName + this.exportExcelNameService.getSysSeparator() + ((SecretLevelInfo)infos.get(0)).getSecretLevelItem().getTitle();
                            }
                            fileInfoKey = "";
                            fileInfoKey = this.zipWithPassword != false ? this.exportExcel2ZipWithPassword(batchExportInfo, batchExportTaskList, batchExportInfo.getLocation(), fileName, suffix, batchExportInfo.getCompressionType(), batchExportInfo.getDownLoadKey()) : this.exportExcel2Zip(batchExportInfo, batchExportTaskList, batchExportInfo.getLocation(), fileName, suffix, batchExportInfo.getCompressionType(), batchExportInfo.getDownLoadKey());
                            this.cacheObjectResourceRemote.create((Object)(batchExportInfo.getDownLoadKey() + "_fileInfoKey"), (Object)fileInfoKey);
                            break block253;
                        }
                        catch (Exception e) {
                            BatchExportExcelServiceImpl.logger.error("\u5bfc\u51fa\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            asyncTaskMonitor.error(e.getMessage(), (Throwable)e);
                            return;
                        }
                        finally {
                            if (threadEnable != null && threadEnable.equals("1")) {
                                this.ThreadSum.getAndDecrement();
                            }
                        }
                    }
                    if (!"EXPORT_BATCH_JIO".equals(batchExportInfo.getFileType())) break block259;
                    if (!batchExportInfo.isMultiplePeriod()) ** GOTO lbl411
                    if (multiplePeriodInfoList != null && multiplePeriodInfoList.size() > 0) {
                        str = (String)multiplePeriodInfoList.get(0);
                        ((DimensionValue)batchExportInfo.getContext().getDimensionSet().get("DATATIME")).setValue(str);
                        ((DimensionValue)batchExportInfo.getContext().getDimensionSet().get("DATATIME")).setType(batchExportInfo.getPeriodType().intValue());
                        this.handlerBatchExport(batchExportInfo, dirUU);
                        exportTask = this.exportToDatasByJio(batchExportInfo, multiplePeriodInfoList, asyncTaskMonitor);
                        if (asyncTaskMonitor.isCancel()) {
                            asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                            LogHelper.info((String)"\u6279\u91cf\u5bfc\u51fa", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                            return;
                        }
                        if (null == exportTask) {
                            return;
                        }
                        batchExportTaskList.add(exportTask);
                    } else {
                        asyncTaskMonitor.finish(noSubject, (Object)"");
                        return;
lbl411:
                        // 1 sources

                        str = ((DimensionValue)batchExportInfo.getContext().getDimensionSet().get("DATATIME")).getValue();
                        PeriodInfoList = new ArrayList<String>();
                        PeriodInfoList.add(str);
                        this.handlerBatchExport(batchExportInfo, dirUU);
                        exportTask = this.exportToDatasByJio(batchExportInfo, PeriodInfoList, asyncTaskMonitor);
                        if (null == exportTask) {
                            return;
                        }
                        batchExportTaskList.add(exportTask);
                    }
                    if (batchExportTaskList.size() == 1 && ((BatchExportTask)batchExportTaskList.get(0)).getExportDatas().size() == 1) {
                        oldFileName = batchExportInfo.getFileName();
                        suffix = ".jio";
                        if (batchExportInfo.getSecretLevel() != null) {
                            suffix = this.exportExcelNameService.getSysSeparator() + batchExportInfo.getSecretLevel() + suffix;
                            newFileName = this.exportToFile(batchExportTaskList, batchExportInfo.getZipLocation(), batchExportInfo.getFileName(), suffix, batchExportInfo.getCompressionType(), batchExportInfo.isSpecifyPath(), singleFileName);
                            newFileName = newFileName + this.exportExcelNameService.getSysSeparator() + batchExportInfo.getSecretLevel();
                        } else {
                            newFileName = this.exportToFile(batchExportTaskList, batchExportInfo.getZipLocation(), batchExportInfo.getFileName(), suffix, batchExportInfo.getCompressionType(), batchExportInfo.isSpecifyPath(), singleFileName);
                        }
                        batchExportTaskList = null;
                        if (batchExportInfo.isSpecifyPath() || this.cacheObjectResourceRemote.find((Object)batchExportInfo.getDownLoadKey()) == null) break block253;
                        fileLocation = this.cacheObjectResourceRemote.find((Object)batchExportInfo.getDownLoadKey()).toString().replaceAll(".zip", ".jio");
                        fileLocation = fileLocation.replace(oldFileName, newFileName);
                        context = NpContextHolder.getContext();
                        fileLocationInfo = BatchExportConsts.EXPORTDIR + BatchExportConsts.SEPARATOR + context.getUser().getName() + BatchExportConsts.SEPARATOR + fileLocation;
                        PathUtils.validatePathManipulation((String)fileLocationInfo);
                        removeFile = new File(fileLocationInfo);
                        fileInfo = null;
                        try {
                            inputStream = new FileInputStream(removeFile.getPath());
                            secretLevelInfos = null;
                            try {
                                fileInfo = this.fileService.tempArea().uploadTemp(newFileName + ".jio", (InputStream)inputStream);
                                this.cacheObjectResourceRemote.create((Object)(batchExportInfo.getDownLoadKey() + "_fileSize"), (Object)removeFile.length());
                            }
                            catch (Throwable secretLevelInfo) {
                                secretLevelInfos = secretLevelInfo;
                                throw secretLevelInfo;
                            }
                            finally {
                                if (inputStream != null) {
                                    if (secretLevelInfos != null) {
                                        try {
                                            inputStream.close();
                                        }
                                        catch (Throwable secretLevelInfo) {
                                            secretLevelInfos.addSuppressed(secretLevelInfo);
                                        }
                                    } else {
                                        inputStream.close();
                                    }
                                }
                            }
                        }
                        catch (Exception e) {
                            BatchExportExcelServiceImpl.logger.error("\u4e0a\u4f20\u6587\u4ef6\u81f3oss\u5f02\u5e38\uff0c\u6279\u91cf\u5bfc\u51fa\u7ec8\u6b62\uff01");
                            asyncTaskMonitor.error(e.getMessage(), (Throwable)e);
                        }
                        if (fileInfo != null && !removeFile.delete()) {
                            BatchExportExcelServiceImpl.logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
                        }
                        fileInfoKey = fileInfo != null ? fileInfo.getKey() : "";
                        this.cacheObjectResourceRemote.create((Object)(batchExportInfo.getDownLoadKey() + "_areaInfo"), (Object)this.fileService.tempArea().getAreaConfig().getName());
                        this.cacheObjectResourceRemote.create((Object)(batchExportInfo.getDownLoadKey() + "_fileInfoKey"), (Object)fileInfoKey);
                        break block253;
                    }
                    fileName = batchExportInfo.getFileName();
                    if (batchExportInfo.getSecretLevel() != null && batchExportInfo.isSpecifyPath()) {
                        fileName = batchExportInfo.getFileName() + this.exportExcelNameService.getSysSeparator() + batchExportInfo.getSecretLevel();
                        fileLocation = this.cacheObjectResourceRemote.find((Object)batchExportInfo.getDownLoadKey()).toString();
                        index = fileLocation.lastIndexOf(batchExportInfo.getFileName());
                        fileLocationAfterFormat = fileLocation.substring(0, index) + fileName + fileLocation.substring(index + batchExportInfo.getFileName().length());
                        this.cacheObjectResourceRemote.create((Object)batchExportInfo.getDownLoadKey(), (Object)fileLocationAfterFormat);
                    }
                    fileInfoKey = "";
                    fileInfoKey = this.zipWithPassword != false ? this.exportToZipWithPassword(batchExportInfo, batchExportTaskList, batchExportInfo.getLocation(), fileName, ".jio", batchExportInfo.getCompressionType(), batchExportInfo.getDownLoadKey()) : this.exportToZip(batchExportInfo, batchExportTaskList, batchExportInfo.getLocation(), fileName, ".jio", batchExportInfo.getCompressionType(), batchExportInfo.getDownLoadKey());
                    this.cacheObjectResourceRemote.create((Object)(batchExportInfo.getDownLoadKey() + "_fileInfoKey"), (Object)fileInfoKey);
                    break block253;
                }
                if (!"EXPORT_BATCH_TXT".equals(batchExportInfo.getFileType()) && !"EXPORT_BATCH_CSV".equals(batchExportInfo.getFileType())) break block253;
                secretLevelInfoList = this.iSecretLevelService.querySecretLevels(batchExportInfo.getContext());
                secretLevelTitleHigher = "";
                if (secretLevelInfoList != null && secretLevelInfoList.size() > 0) {
                    secretLevelInfoHigher = null;
                    for (SecretLevelInfo secretLevelInfo : secretLevelInfoList) {
                        if (secretLevelInfoHigher != null) {
                            if (!this.iSecretLevelService.compareSercetLevel(secretLevelInfo.getSecretLevelItem(), secretLevelInfoHigher.getSecretLevelItem())) continue;
                            secretLevelInfoHigher = secretLevelInfo;
                            continue;
                        }
                        secretLevelInfoHigher = secretLevelInfo;
                    }
                    secretLevelTitleHigher = secretLevelInfoHigher.getSecretLevelItem().getTitle();
                }
                actionLogInfo = LoggerUtil.buildMessing(batchExportInfo.getContext(), null, "\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6", this.runtimeViewController, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
                logInfo = new LogInfo();
                logInfo.setActionName("\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6");
                exportDatas = new ArrayList<ExportData>();
                if (!batchExportInfo.isMultiplePeriod()) ** GOTO lbl566
                if (multiplePeriodInfoList != null && multiplePeriodInfoList.size() > 0) {
                    for (i = 0; i < multiplePeriodInfoList.size(); ++i) {
                        if (asyncTaskMonitor.isCancel()) {
                            asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                            LogHelper.info((String)"\u6279\u91cf\u5bfc\u51fa", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                            return;
                        }
                        dimensionValueMapNew = new HashMap<String, DimensionValue>();
                        keySetNew = dimensionValueMap.keySet();
                        for (String set : keySetNew) {
                            dimensionValue = new DimensionValue();
                            dimensionValue.setName(((DimensionValue)dimensionValueMap.get(set)).getName());
                            dimensionValue.setType(((DimensionValue)dimensionValueMap.get(set)).getType());
                            dimensionValue.setValue(((DimensionValue)dimensionValueMap.get(set)).getValue());
                            dimensionValueMapNew.put(set, dimensionValue);
                        }
                        batchExportInfo.getContext().setDimensionSet(dimensionValueMapNew);
                        ((DimensionValue)batchExportInfo.getContext().getDimensionSet().get("DATATIME")).setValue((String)multiplePeriodInfoList.get(i));
                        ((DimensionValue)batchExportInfo.getContext().getDimensionSet().get("DATATIME")).setType(batchExportInfo.getPeriodType().intValue());
                        periodKey = (String)multiplePeriodInfoList.get(i);
                        filter = schemePeriodLinkDefineList.stream().filter((Predicate<SchemePeriodLinkDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$export$8(java.lang.String com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine ), (Lcom/jiuqi/nr/definition/facade/SchemePeriodLinkDefine;)Z)((String)periodKey)).collect(Collectors.toList());
                        currSchemeKey = ((SchemePeriodLinkDefine)filter.get(0)).getSchemeKey();
                        batchExportInfo.getContext().setFormSchemeKey(currSchemeKey);
                        batchExportInfo.getContext().setFormKey(formKey);
                        batchExportInfo.setPrintSchemeKey(printSchemeKey);
                        batchExportInfo.getContext().setFormulaSchemeKey(formulaSchemeKey);
                        selectFormKey = new HashSet<String>();
                        currFormDefines = this.runtimeViewController.queryAllFormDefinesByFormScheme(currSchemeKey);
                        for (Map.Entry<K, V> entry : selectFormCodeTitle.entrySet()) {
                            titleFilter = currFormDefines.stream().filter((Predicate<FormDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$export$9(java.util.Map$Entry com.jiuqi.nr.definition.facade.FormDefine ), (Lcom/jiuqi/nr/definition/facade/FormDefine;)Z)(entry)).collect(Collectors.toList());
                            if (titleFilter.size() > 0) {
                                for (Object formDefine : titleFilter) {
                                    selectFormKey.add(formDefine.getKey());
                                }
                                continue;
                            }
                            codeFilter = currFormDefines.stream().filter((Predicate<FormDefine>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$export$10(java.util.Map$Entry com.jiuqi.nr.definition.facade.FormDefine ), (Lcom/jiuqi/nr/definition/facade/FormDefine;)Z)(entry)).collect(Collectors.toList());
                            formDefine = codeFilter.iterator();
                            while (formDefine.hasNext()) {
                                formDefine = (FormDefine)formDefine.next();
                                selectFormKey.add(formDefine.getKey());
                            }
                        }
                        defaultFormulaSchemeInFormScheme = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(currSchemeKey);
                        if (defaultFormulaSchemeInFormScheme != null) {
                            batchExportInfo.getContext().setFormulaSchemeKey(defaultFormulaSchemeInFormScheme.getKey());
                        } else {
                            batchExportInfo.getContext().setFormulaSchemeKey(null);
                        }
                        if (selectFormKey.size() <= 0) continue;
                        batchExportInfo.getContext().setFormKey(selectFormKey.stream().collect(Collectors.joining(";")));
                        numOfIndex = (double)i / ((double)multiplePeriodInfoList.size() * 1.0) * 0.9;
                        coefficient = (double)(0.9f / (float)multiplePeriodInfoList.size()) * 1.0;
                        batchExportProgressMonitor = new BatchExportProgressMonitor(asyncTaskMonitor, coefficient, numOfIndex);
                        txtcsvFileLocation = this.exportToDatasByTxt(batchExportInfo, batchExportProgressMonitor, multiplePeriodInfoList);
                        if (null == txtcsvFileLocation || txtcsvFileLocation.equals("")) continue;
                        periodTitle = this.getPeriodTitle(currSchemeKey, (String)multiplePeriodInfoList.get(i));
                        exportData = new ExportData();
                        exportData.setFileLocation(txtcsvFileLocation);
                        exportData.setAlisFileName(periodTitle);
                        exportDatas.add(exportData);
                    }
                } else {
                    asyncTaskMonitor.finish(noSubject, (Object)"");
                    return;
lbl566:
                    // 1 sources

                    txtcsvFileLocation = this.exportToDatasByTxt(batchExportInfo, asyncTaskMonitor, multiplePeriodInfoList);
                    if (asyncTaskMonitor.isCancel()) {
                        asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                        LogHelper.info((String)"\u6279\u91cf\u5bfc\u51fa", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                        return;
                    }
                    if (null != txtcsvFileLocation && !txtcsvFileLocation.equals("")) {
                        periodTitle = this.getPeriodTitle(batchExportInfo.getContext().getFormSchemeKey(), ((DimensionValue)batchExportInfo.getContext().getDimensionSet().get("DATATIME")).getValue());
                        exportData = new ExportData();
                        exportData.setFileLocation(txtcsvFileLocation);
                        exportData.setAlisFileName(periodTitle);
                        exportDatas.add(exportData);
                    }
                }
                zipName = this.exportExcelNameService.compileNameInfoWithSetting(batchExportInfo.getContext().getFormKey(), batchExportInfo.getContext(), "ZIP_NAME", false, batchExportInfo.getContext().getUnitViewKey(), batchExportInfo.getRuleSettings());
                zipNameInfo = "ExportTxtDatas";
                if (zipName != null && !zipName.equals("")) {
                    zipName = zipName + this.exportExcelNameService.getSysSeparator() + secretLevelTitleHigher;
                    zipNameInfo = zipName + ".zip";
                } else {
                    zipNameInfo = zipNameInfo + this.exportExcelNameService.getSysSeparator() + secretLevelTitleHigher;
                    zipNameInfo = zipNameInfo + ".zip";
                }
                ncontext = NpContextHolder.getContext();
                resultLocation = BatchExportConsts.EXPORTDIR + BatchExportConsts.SEPARATOR + ncontext.getUser().getName() + BatchExportConsts.SEPARATOR + GUID.newGUID();
                zipFile = resultLocation + BatchExportConsts.SEPARATOR + zipName;
                try {
                    PathUtils.validatePathManipulation((String)zipFile);
                }
                catch (SecurityContentException e) {
                    throw new RuntimeException(e);
                }
                file = new File(FilenameUtils.normalize(zipFile));
                dir = new File(FilenameUtils.normalize(resultLocation));
                if (!dir.exists()) {
                    currFormDefines = dir.mkdirs();
                }
                if (exportDatas.size() > 0) {
                    if (this.zipWithPassword) {
                        try {
                            fos = new FileOutputStream(new File(zipFile));
                            defaultFormulaSchemeInFormScheme = null;
                            try {
                                zos = new net.lingala.zip4j.io.outputstream.ZipOutputStream((OutputStream)fos, NpContextHolder.getContext().getUserName().toCharArray());
                                var43_131 = null;
                                try {
                                    for (ExportData exportData : exportDatas) {
                                        if (exportData.getFileLocation() == null) continue;
                                        directory = new File(exportData.getFileLocation());
                                        BatchExportExcelServiceImpl.addFilesToZipWithPassword(zos, directory, exportDatas.size() > 1 ? exportData.getAlisFileName() : null);
                                    }
                                }
                                catch (Throwable coefficient) {
                                    var43_131 = coefficient;
                                    throw coefficient;
                                }
                                finally {
                                    if (zos != null) {
                                        if (var43_131 != null) {
                                            try {
                                                zos.close();
                                            }
                                            catch (Throwable coefficient) {
                                                var43_131.addSuppressed(coefficient);
                                            }
                                        } else {
                                            zos.close();
                                        }
                                    }
                                }
                            }
                            catch (Throwable zos) {
                                defaultFormulaSchemeInFormScheme = zos;
                                throw zos;
                            }
                            finally {
                                if (fos != null) {
                                    if (defaultFormulaSchemeInFormScheme != null) {
                                        try {
                                            fos.close();
                                        }
                                        catch (Throwable zos) {
                                            defaultFormulaSchemeInFormScheme.addSuppressed(zos);
                                        }
                                    } else {
                                        fos.close();
                                    }
                                }
                            }
                        }
                        catch (IOException e) {
                            BatchExportExcelServiceImpl.logger.error(e.getMessage());
                        }
                        finally {
                            try {
                                for (Object exportData : exportDatas) {
                                    directory = new File(exportData.getFileLocation());
                                    FileUtils.deleteDirectory(directory);
                                }
                            }
                            catch (IOException e) {
                                BatchExportExcelServiceImpl.logger.error(e.getMessage());
                            }
                        }
                    } else {
                        try {
                            fos = new FileOutputStream(new File(zipFile));
                            exportData = null;
                            try {
                                bos = new BufferedOutputStream(fos);
                                var43_131 = null;
                                try {
                                    zos = new ZipOutputStream(bos);
                                    for (ExportData exportData : exportDatas) {
                                        if (exportData.getFileLocation() == null) continue;
                                        directory = new File(exportData.getFileLocation());
                                        BatchExportExcelServiceImpl.addFilesToZip(zos, directory, exportDatas.size() > 1 ? exportData.getAlisFileName() : null);
                                    }
                                    zos.close();
                                }
                                catch (Throwable var44_142) {
                                    var43_131 = var44_142;
                                    throw var44_142;
                                }
                                finally {
                                    if (bos != null) {
                                        if (var43_131 != null) {
                                            try {
                                                bos.close();
                                            }
                                            catch (Throwable var44_141) {
                                                var43_131.addSuppressed(var44_141);
                                            }
                                        } else {
                                            bos.close();
                                        }
                                    }
                                }
                            }
                            catch (Throwable bos) {
                                exportData = bos;
                                throw bos;
                            }
                            finally {
                                if (fos != null) {
                                    if (exportData != null) {
                                        try {
                                            fos.close();
                                        }
                                        catch (Throwable bos) {
                                            exportData.addSuppressed(bos);
                                        }
                                    } else {
                                        fos.close();
                                    }
                                }
                            }
                        }
                        catch (IOException e) {
                            BatchExportExcelServiceImpl.logger.error(e.getMessage());
                        }
                        finally {
                            try {
                                for (Object exportData : exportDatas) {
                                    directory = new File(exportData.getFileLocation());
                                    FileUtils.deleteDirectory(directory);
                                }
                            }
                            catch (IOException e) {
                                BatchExportExcelServiceImpl.logger.error(e.getMessage());
                            }
                        }
                    }
                }
                logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + file.length() + "byte");
                LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
                removeFile = new File(zipFile);
                if (!removeFile.exists()) {
                    parentDir = removeFile.getParentFile();
                    removeFile.getParentFile().mkdirs();
                    try {
                        if (!removeFile.createNewFile()) {
                            BatchExportExcelServiceImpl.logger.info("\u6587\u4ef6\u521b\u5efa\u5931\u8d25");
                        }
                    }
                    catch (IOException e) {
                        BatchExportExcelServiceImpl.logger.error(e.getMessage(), e);
                    }
                }
                fileInfo = null;
                try {
                    inputStream = new FileInputStream(removeFile.getPath());
                    var43_131 = null;
                    try {
                        fileInfo = this.fileService.tempArea().uploadTemp(zipNameInfo, (InputStream)inputStream);
                    }
                    catch (Throwable var44_144) {
                        var43_131 = var44_144;
                        throw var44_144;
                    }
                    finally {
                        if (inputStream != null) {
                            if (var43_131 != null) {
                                try {
                                    inputStream.close();
                                }
                                catch (Throwable var44_143) {
                                    var43_131.addSuppressed(var44_143);
                                }
                            } else {
                                inputStream.close();
                            }
                        }
                    }
                }
                catch (Exception e) {
                    BatchExportExcelServiceImpl.logger.error("\u4e0a\u4f20\u6587\u4ef6\u81f3oss\u5f02\u5e38\uff0c\u6279\u91cf\u5bfc\u51fa\u7ec8\u6b62\uff01");
                    asyncTaskMonitor.error(e.getMessage(), (Throwable)e);
                }
                fileInfoKey = null;
                fileInfoKey = fileInfo != null ? fileInfo.getKey() : "";
                this.cacheObjectResourceRemote.create((Object)(batchExportInfo.getDownLoadKey() + "_fileInfoKey"), (Object)fileInfoKey);
                this.cacheObjectResourceRemote.create((Object)(batchExportInfo.getDownLoadKey() + "_areaInfo"), (Object)this.fileService.tempArea().getAreaConfig().getName());
                this.cacheObjectResourceRemote.create((Object)(batchExportInfo.getDownLoadKey() + "_fileSize"), (Object)removeFile.length());
                if (removeFile.exists()) {
                    removeFile.delete();
                }
            }
            catch (Exception e) {
                BatchExportExcelServiceImpl.logger.error(e.getMessage(), e);
                asyncTaskMonitor.error(e.getMessage(), (Throwable)e);
                return;
            }
        }
        if (asyncTaskMonitor.isCancel()) {
            asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
            LogHelper.info((String)"\u6279\u91cf\u5bfc\u51fa", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
            return;
        }
        objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
        exportFinish = "export_finish_info";
        if (!asyncTaskMonitor.isFinish()) {
            asyncTaskMonitor.finish(exportFinish, (Object)"");
        }
    }

    private void handlerBatchExport(BatchExportInfo batchExportInfo, String dirUU) {
        String resultLocation;
        String noDateDimensionStr;
        String fileLocation = "";
        NpContext context = NpContextHolder.getContext();
        JtableContext jtableContext = batchExportInfo.getContext();
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        String dateType = dataTimeEntity.getDimensionName();
        String dateDimension = noDateDimensionStr = "\u65e0\u65e5\u671f\u7ef4\u5ea6";
        if (jtableContext.getDimensionSet() != null) {
            Map tempDimens = jtableContext.getDimensionSet();
            for (Map.Entry entry : tempDimens.entrySet()) {
                if (!dateType.equals(((DimensionValue)entry.getValue()).getName())) continue;
                dateDimension = ((DimensionValue)entry.getValue()).getValue();
                break;
            }
        }
        if (!dateDimension.equals(noDateDimensionStr)) {
            dateDimension = this.getPeriodTitle(jtableContext.getFormSchemeKey(), dateDimension);
        }
        long now = Instant.now().toEpochMilli();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = dateFormat.format(date);
        String fileListLocation = resultLocation = BatchExportConsts.EXPORTDIR + BatchExportConsts.SEPARATOR + context.getUser().getName() + BatchExportConsts.SEPARATOR + formatDate + BatchExportConsts.SEPARATOR + dirUU;
        if (!StringUtils.isEmpty((String)batchExportInfo.getLocation())) {
            fileListLocation = batchExportInfo.getLocation();
        }
        String fileType = null;
        if ("EXPORT_BATCH_EXCEL".equals(batchExportInfo.getFileType())) {
            fileType = "EXCEL";
        } else if ("EXPORT_BATCH_PDF".equals(batchExportInfo.getFileType())) {
            fileType = "PDF";
        } else if ("EXPORT_BATCH_JIO".equals(batchExportInfo.getFileType())) {
            fileType = "JIO";
        }
        String fileName = MODULEEXCEL + fileType + dateDimension + now;
        String fileNameInfo = this.exportExcelNameService.compileNameInfoWithSetting(batchExportInfo.getContext().getFormKey(), batchExportInfo.getContext(), "ZIP_NAME", false, batchExportInfo.getContext().getUnitViewKey(), batchExportInfo.getRuleSettings());
        if (fileNameInfo != null && !fileNameInfo.equals("")) {
            fileName = fileNameInfo;
        }
        batchExportInfo.setLocation(resultLocation);
        batchExportInfo.setZipLocation(fileListLocation);
        if (batchExportInfo.isSpecifyPath() && "EXPORT_BATCH_JIO".equals(batchExportInfo.getFileType()) && StringUtils.isNotEmpty((String)batchExportInfo.getFileName())) {
            fileName = batchExportInfo.getFileName();
        }
        batchExportInfo.setFileName(fileName);
        fileLocation = formatDate + BatchExportConsts.SEPARATOR + dirUU + BatchExportConsts.SEPARATOR + fileName + "." + batchExportInfo.getCompressionType();
        this.cacheObjectResourceRemote.create((Object)batchExportInfo.getDownLoadKey(), (Object)fileLocation);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String exportExcel2Zip(BatchExportInfo batchExportInfo, List<BatchExportTask> batchExportTaskList, String resultLocation, String fileName, String suffix, String fileType, String downLoadKey) throws Exception {
        String fileInfoKey;
        block117: {
            fileInfoKey = null;
            try {
                File file = new File(FilenameUtils.normalize(batchExportInfo.getExcelExpPath().replaceAll("\\\\", "/")));
                if (!file.exists()) {
                    file.mkdirs();
                }
                if (fileType.equals("zip")) {
                    String fileNameInfo = FilenameUtils.normalize(batchExportInfo.getZipLocation() + BatchExportConsts.SEPARATOR + fileName + ".zip");
                    fileNameInfo = fileNameInfo.replace("\\\\", "/");
                    File zipFile = FileUtil.createIfNotExists((String)fileNameInfo);
                    logger.info("\u751f\u6210zip\u6587\u4ef6\u6210\u529f\uff1a{}\uff1b\u6587\u4ef6\u8def\u5f84\uff1a{}", (Object)zipFile.exists(), (Object)fileNameInfo);
                    try (FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
                         ZipOutputStream zos = new ZipOutputStream(fileOutputStream);){
                        this.excelFilesZip(resultLocation, "", zos, file, fileNameInfo);
                        zos.close();
                        StringBuilder actionLogInfo = LoggerUtil.buildMessing(batchExportInfo.getContext(), null, "\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6", this.runtimeViewController, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
                        LogInfo logInfo = new LogInfo();
                        logInfo.setActionName("\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6");
                        logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + zipFile.length() + "byte");
                        LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
                    }
                    var13_17 = null;
                    try (InputStream inputStream = Files.newInputStream(Paths.get(zipFile.getPath(), new String[0]), new OpenOption[0]);){
                        if (!batchExportInfo.isSpecifyPath()) {
                            FileInfo fileInfo = this.fileService.tempArea().uploadTemp(fileName + ".zip", inputStream);
                            fileInfoKey = fileInfo.getKey();
                            this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_areaInfo"), (Object)this.fileService.tempArea().getAreaConfig().getName());
                            this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_fileSize"), (Object)zipFile.length());
                        }
                        break block117;
                    }
                    catch (Throwable throwable) {
                        var13_17 = throwable;
                        throw throwable;
                    }
                }
                if (!fileType.equals("tar")) break block117;
                String fileNameInfo = FilenameUtils.normalize(batchExportInfo.getZipLocation() + BatchExportConsts.SEPARATOR + fileName + ".tar");
                fileNameInfo = fileNameInfo.replace("\\\\", "/");
                try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileNameInfo));
                     TarArchiveOutputStream tar = new TarArchiveOutputStream(fileOutputStream);){
                    for (BatchExportTask batchExportTask : batchExportTaskList) {
                        List<BatchExportData> datas = batchExportTask.getExportDatas();
                        for (BatchExportData data : datas) {
                            String filePath = "";
                            String tfileName = "";
                            if (data.getData().getFileName().contains(".")) {
                                filePath = data.getLocation() + data.getData().getFileName();
                                tfileName = data.getLocation() + data.getData().getFileName().replace("/", "-").replace("\\", "-");
                            } else {
                                filePath = data.getLocation() + data.getData().getFileName() + suffix;
                                tfileName = data.getLocation() + data.getData().getFileName().replace("/", "-").replace("\\", "-") + suffix;
                            }
                            if (!filePath.contains(suffix)) {
                                filePath = filePath + suffix;
                            }
                            if (!tfileName.contains(suffix)) {
                                tfileName = tfileName + suffix;
                            }
                            TarArchiveEntry tarEntry = new TarArchiveEntry(new File(filePath), tfileName);
                            tar.putArchiveEntry(tarEntry);
                            if (StringUtils.isNotEmpty((String)data.getData().getFileLocation())) {
                                File jioFileInfo = new File(data.getData().getFileLocation());
                                try (FileInputStream fileStream = new FileInputStream(jioFileInfo);){
                                    long l = IOUtils.copyLarge(fileStream, tar);
                                }
                                catch (Exception e) {
                                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                                }
                                finally {
                                    if (jioFileInfo.exists() && !jioFileInfo.delete()) {
                                        logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
                                    }
                                }
                                tar.closeArchiveEntry();
                                continue;
                            }
                            byte[] databytes = data.getData().getData();
                            if (null == databytes || databytes.length <= 0) continue;
                            byte[] bufs = new byte[0xA00000];
                            tarEntry.setSize(databytes.length);
                            ByteArrayInputStream swapStream = new ByteArrayInputStream(data.getData().getData());
                            try (BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);){
                                int read = 0;
                                while ((read = bis.read(bufs, 0, 10240)) != -1) {
                                    tar.write(bufs, 0, read);
                                }
                            }
                            tar.closeArchiveEntry();
                        }
                        File removeFile = FileUtil.createIfNotExists((String)fileNameInfo);
                        InputStream inputStream = Files.newInputStream(Paths.get(removeFile.getPath(), new String[0]), new OpenOption[0]);
                        Throwable throwable = null;
                        try {
                            StringBuilder actionLogInfo = LoggerUtil.buildMessing(batchExportInfo.getContext(), null, "\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6", this.runtimeViewController, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
                            LogInfo logInfo = new LogInfo();
                            logInfo.setActionName("\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6");
                            logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + removeFile.length() + "byte");
                            LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
                            if (batchExportInfo.isSpecifyPath()) continue;
                            FileInfo fileInfo = this.fileService.tempArea().uploadTemp(fileName + ".tar", inputStream);
                            fileInfoKey = fileInfo.getKey();
                            this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_areaInfo"), (Object)this.fileService.tempArea().getAreaConfig().getName());
                            this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_fileSize"), (Object)removeFile.length());
                            if (removeFile.delete()) continue;
                            logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
                        }
                        catch (Throwable throwable2) {
                            throwable = throwable2;
                            throw throwable2;
                        }
                        finally {
                            if (inputStream == null) continue;
                            if (throwable != null) {
                                try {
                                    inputStream.close();
                                }
                                catch (Throwable throwable3) {
                                    throwable.addSuppressed(throwable3);
                                }
                                continue;
                            }
                            inputStream.close();
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                throw new FileUploadException(e.getMessage());
            }
            finally {
                FileUtils.deleteDirectory(new File(FilenameUtils.normalize(batchExportInfo.getExcelExpPath().replaceAll("\\\\", "/"))));
            }
        }
        return fileInfoKey;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String exportExcel2ZipWithPassword(BatchExportInfo batchExportInfo, List<BatchExportTask> batchExportTaskList, String resultLocation, String fileName, String suffix, String fileType, String downLoadKey) throws Exception {
        String fileInfoKey;
        block106: {
            fileInfoKey = null;
            try {
                File file = new File(FilenameUtils.normalize(batchExportInfo.getExcelExpPath().replaceAll("\\\\", "/")));
                if (!file.exists()) {
                    file.mkdirs();
                }
                if (fileType.equals("zip")) {
                    Throwable throwable;
                    String fileNameInfo = FilenameUtils.normalize(batchExportInfo.getZipLocation() + BatchExportConsts.SEPARATOR + fileName + ".zip");
                    fileNameInfo = fileNameInfo.replace("\\\\", "/");
                    File zipFile = FileUtil.createIfNotExists((String)fileNameInfo);
                    logger.info("\u751f\u6210zip\u6587\u4ef6\u6210\u529f\uff1a{}\uff1b\u6587\u4ef6\u8def\u5f84\uff1a{}", (Object)zipFile.exists(), (Object)fileNameInfo);
                    try {
                        throwable = null;
                        try (net.lingala.zip4j.io.outputstream.ZipOutputStream zos = new net.lingala.zip4j.io.outputstream.ZipOutputStream((OutputStream)new FileOutputStream(fileNameInfo), NpContextHolder.getContext().getUserName().toCharArray());){
                            this.excelFilesZipWithPassword(resultLocation, "", zos, file, fileNameInfo);
                            StringBuilder actionLogInfo = LoggerUtil.buildMessing(batchExportInfo.getContext(), null, "\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6", this.runtimeViewController, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
                            LogInfo logInfo = new LogInfo();
                            logInfo.setActionName("\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6");
                            logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + zipFile.length() + "byte");
                            LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
                        }
                        catch (Throwable actionLogInfo) {
                            throwable = actionLogInfo;
                            throw actionLogInfo;
                        }
                    }
                    catch (Exception e) {
                        logger.error("\u521b\u5efa\u5e26\u5bc6\u7801\u7684ZIP\u6587\u4ef6\u5931\u8d25", e);
                        throw e;
                    }
                    throwable = null;
                    try (InputStream inputStream = Files.newInputStream(Paths.get(zipFile.getPath(), new String[0]), new OpenOption[0]);){
                        if (!batchExportInfo.isSpecifyPath()) {
                            FileInfo fileInfo = this.fileService.tempArea().uploadTemp(fileName + ".zip", inputStream);
                            fileInfoKey = fileInfo.getKey();
                            this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_areaInfo"), (Object)this.fileService.tempArea().getAreaConfig().getName());
                            this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_fileSize"), (Object)zipFile.length());
                        }
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    for (String currFile : file.list()) {
                        boolean delete;
                        File sourceFile = new File(file.getAbsolutePath() + BatchExportConsts.SEPARATOR + currFile);
                        if (!sourceFile.isDirectory() || (delete = sourceFile.delete())) continue;
                        logger.info("\u5220\u9664\u5931\u8d25\u3002");
                    }
                    break block106;
                }
                if (!fileType.equals("tar")) break block106;
                String fileNameInfo = FilenameUtils.normalize(batchExportInfo.getZipLocation() + BatchExportConsts.SEPARATOR + fileName + ".tar");
                fileNameInfo = fileNameInfo.replace("\\\\", "/");
                try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileNameInfo));
                     TarArchiveOutputStream tar = new TarArchiveOutputStream(fileOutputStream);){
                    for (BatchExportTask batchExportTask : batchExportTaskList) {
                        List<BatchExportData> datas = batchExportTask.getExportDatas();
                        for (BatchExportData data : datas) {
                            String filePath = "";
                            String tfileName = "";
                            if (data.getData().getFileName().contains(".")) {
                                filePath = data.getLocation() + data.getData().getFileName();
                                tfileName = data.getLocation() + data.getData().getFileName().replace("/", "-").replace("\\", "-");
                            } else {
                                filePath = data.getLocation() + data.getData().getFileName() + suffix;
                                tfileName = data.getLocation() + data.getData().getFileName().replace("/", "-").replace("\\", "-") + suffix;
                            }
                            if (!filePath.contains(suffix)) {
                                filePath = filePath + suffix;
                            }
                            if (!tfileName.contains(suffix)) {
                                tfileName = tfileName + suffix;
                            }
                            TarArchiveEntry tarEntry = new TarArchiveEntry(new File(filePath), tfileName);
                            tar.putArchiveEntry(tarEntry);
                            if (StringUtils.isNotEmpty((String)data.getData().getFileLocation())) {
                                File jioFileInfo = new File(data.getData().getFileLocation());
                                try (FileInputStream fileStream = new FileInputStream(jioFileInfo);){
                                    long l = IOUtils.copyLarge(fileStream, tar);
                                }
                                catch (Exception e) {
                                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                                }
                                finally {
                                    if (jioFileInfo.exists() && !jioFileInfo.delete()) {
                                        logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
                                    }
                                }
                                tar.closeArchiveEntry();
                                continue;
                            }
                            byte[] databytes = data.getData().getData();
                            if (null == databytes || databytes.length <= 0) continue;
                            byte[] bufs = new byte[0xA00000];
                            tarEntry.setSize(databytes.length);
                            ByteArrayInputStream swapStream = new ByteArrayInputStream(data.getData().getData());
                            try (BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);){
                                int read = 0;
                                while ((read = bis.read(bufs, 0, 10240)) != -1) {
                                    tar.write(bufs, 0, read);
                                }
                            }
                            tar.closeArchiveEntry();
                        }
                        File removeFile = FileUtil.createIfNotExists((String)fileNameInfo);
                        InputStream inputStream = Files.newInputStream(Paths.get(removeFile.getPath(), new String[0]), new OpenOption[0]);
                        Throwable throwable = null;
                        try {
                            StringBuilder actionLogInfo = LoggerUtil.buildMessing(batchExportInfo.getContext(), null, "\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6", this.runtimeViewController, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
                            LogInfo logInfo = new LogInfo();
                            logInfo.setActionName("\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6");
                            logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + removeFile.length() + "byte");
                            LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
                            if (batchExportInfo.isSpecifyPath()) continue;
                            FileInfo fileInfo = this.fileService.tempArea().uploadTemp(fileName + ".tar", inputStream);
                            fileInfoKey = fileInfo.getKey();
                            this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_areaInfo"), (Object)this.fileService.tempArea().getAreaConfig().getName());
                            this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_fileSize"), (Object)removeFile.length());
                            if (removeFile.delete()) continue;
                            logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
                        }
                        catch (Throwable throwable3) {
                            throwable = throwable3;
                            throw throwable3;
                        }
                        finally {
                            if (inputStream == null) continue;
                            if (throwable != null) {
                                try {
                                    inputStream.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable.addSuppressed(throwable4);
                                }
                                continue;
                            }
                            inputStream.close();
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return fileInfoKey;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String exportToZipWithPassword(BatchExportInfo batchExportInfo, List<BatchExportTask> batchExportTaskList, String resultLocation, String fileName, String suffix, String fileType, String downLoadKey) {
        String fileInfoKey;
        block170: {
            fileInfoKey = null;
            File removeFile = null;
            try {
                PathUtils.validatePathManipulation((String)resultLocation);
                File file = new File(FilenameUtils.normalize(resultLocation));
                if (!file.exists()) {
                    file.mkdirs();
                }
                if (fileType.equals("zip")) {
                    String fileNameInfo = FilenameUtils.normalize(batchExportInfo.getZipLocation() + BatchExportConsts.SEPARATOR + fileName + ".zip");
                    fileNameInfo = fileNameInfo.replace("\\\\", "/");
                    String filePath = "";
                    try {
                        ZipParameters zipParameters = new ZipParameters();
                        zipParameters.setEncryptFiles(true);
                        zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
                        String password = NpContextHolder.getContext().getUserName();
                        try (net.lingala.zip4j.io.outputstream.ZipOutputStream zos = new net.lingala.zip4j.io.outputstream.ZipOutputStream((OutputStream)new FileOutputStream(fileNameInfo), password.toCharArray());){
                            for (BatchExportTask batchExportTask : batchExportTaskList) {
                                List<BatchExportData> datas = batchExportTask.getExportDatas();
                                for (BatchExportData data : datas) {
                                    block168: {
                                        filePath = data.getData().isEnclosure() ? data.getLocation() + data.getData().getFileName().replace("/", "-").replace("\\", "-") : data.getLocation() + data.getData().getFileName().replace("/", "-").replace("\\", "-") + suffix;
                                        zipParameters.setFileNameInZip(filePath);
                                        zos.putNextEntry(zipParameters);
                                        if (StringUtils.isNotEmpty((String)data.getData().getFileLocation())) {
                                            File jioFileInfo = new File(data.getData().getFileLocation());
                                            try (FileInputStream fileStream = new FileInputStream(jioFileInfo);){
                                                PathUtils.validatePathManipulation((String)data.getData().getFileLocation());
                                                IOUtils.copyLarge(fileStream, (OutputStream)zos);
                                                break block168;
                                            }
                                            finally {
                                                try {
                                                    boolean jioDelete;
                                                    PathUtils.validatePathManipulation((String)data.getData().getFileLocation());
                                                    jioFileInfo = new File(data.getData().getFileLocation());
                                                    if (jioFileInfo.exists() && !(jioDelete = jioFileInfo.delete())) {
                                                        logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
                                                    }
                                                }
                                                catch (Exception e) {
                                                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                                                }
                                            }
                                        }
                                        try (ByteArrayInputStream swapStream = new ByteArrayInputStream(data.getData().getData());
                                             BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);){
                                            byte[] databytes = data.getData().getData();
                                            if (null != databytes && databytes.length > 0) {
                                                byte[] bufs = new byte[0xA00000];
                                                int read = 0;
                                                while ((read = bis.read(bufs, 0, 10240)) != -1) {
                                                    zos.write(bufs, 0, read);
                                                }
                                            }
                                        }
                                    }
                                    zos.closeEntry();
                                }
                            }
                            this.excelFilesZipWithPassword(resultLocation, "", zos, file, fileNameInfo);
                        }
                        PathUtils.validatePathManipulation((String)fileNameInfo);
                        removeFile = new File(fileNameInfo);
                        var16_27 = null;
                        try (FileInputStream inputStream = new FileInputStream(removeFile.getPath());){
                            StringBuilder actionLogInfo = LoggerUtil.buildMessing(batchExportInfo.getContext(), null, "\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6", this.runtimeViewController, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
                            LogInfo logInfo = new LogInfo();
                            logInfo.setActionName("\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6");
                            logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + removeFile.length() + "byte");
                            LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
                            if (!batchExportInfo.isSpecifyPath()) {
                                FileInfo fileInfo = this.fileService.tempArea().uploadTemp(fileName + ".zip", (InputStream)inputStream);
                                fileInfoKey = fileInfo.getKey();
                                this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_areaInfo"), (Object)this.fileService.tempArea().getAreaConfig().getName());
                                this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_fileSize"), (Object)removeFile.length());
                            }
                            for (FileInfo currFile : file.list()) {
                                boolean delete;
                                PathUtils.validatePathManipulation((String)(file.getAbsolutePath() + BatchExportConsts.SEPARATOR + (String)currFile));
                                File sourceFile = new File(file.getAbsolutePath() + BatchExportConsts.SEPARATOR + (String)currFile);
                                if (!sourceFile.isDirectory() || (delete = sourceFile.delete())) continue;
                                logger.info("\u5220\u9664\u5931\u8d25\u3002");
                            }
                            break block170;
                        }
                        catch (Throwable actionLogInfo) {
                            var16_27 = actionLogInfo;
                            throw actionLogInfo;
                        }
                    }
                    catch (Exception e) {
                        throw new RuntimeException("\u5bfc\u51fa\u6587\u4ef6\u540d\u91cd\u590d" + filePath + ",\u68c0\u67e5\u7cfb\u7edf\u914d\u7f6e\u5bfc\u51faexcel\u540d\u79f0,\u65f6\u671f\u914d\u7f6e\u540d\u79f0\u662f\u5426\u6709\u76f8\u540c\u65f6\u671f", e);
                    }
                }
                if (!fileType.equals("tar")) break block170;
                String fileNameInfo = FilenameUtils.normalize(batchExportInfo.getZipLocation() + BatchExportConsts.SEPARATOR + fileName + ".tar");
                fileNameInfo = fileNameInfo.replace("\\\\", "/");
                try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileNameInfo));
                     TarArchiveOutputStream tar = new TarArchiveOutputStream(fileOutputStream);){
                    for (BatchExportTask batchExportTask : batchExportTaskList) {
                        List<BatchExportData> datas = batchExportTask.getExportDatas();
                        for (BatchExportData data : datas) {
                            String filePath = "";
                            String tfileName = "";
                            if (data.getData().getFileName().contains(".")) {
                                filePath = data.getLocation() + data.getData().getFileName();
                                tfileName = data.getLocation() + data.getData().getFileName().replace("/", "-").replace("\\", "-");
                            } else {
                                filePath = data.getLocation() + data.getData().getFileName() + suffix;
                                tfileName = data.getLocation() + data.getData().getFileName().replace("/", "-").replace("\\", "-") + suffix;
                            }
                            if (!filePath.contains(suffix)) {
                                filePath = filePath + suffix;
                            }
                            if (!tfileName.contains(suffix)) {
                                tfileName = tfileName + suffix;
                            }
                            PathUtils.validatePathManipulation((String)filePath);
                            TarArchiveEntry tarEntry = new TarArchiveEntry(new File(filePath), tfileName);
                            tar.putArchiveEntry(tarEntry);
                            if (StringUtils.isNotEmpty((String)data.getData().getFileLocation())) {
                                PathUtils.validatePathManipulation((String)data.getData().getFileLocation());
                                File jioFileInfo = new File(data.getData().getFileLocation());
                                try {
                                    FileInputStream fileStream = new FileInputStream(jioFileInfo);
                                    Throwable databytes = null;
                                    try {
                                        long copyLarge = IOUtils.copyLarge(fileStream, tar);
                                        tar.closeArchiveEntry();
                                        continue;
                                    }
                                    catch (Throwable throwable) {
                                        databytes = throwable;
                                        throw throwable;
                                    }
                                    finally {
                                        if (fileStream == null) continue;
                                        if (databytes != null) {
                                            try {
                                                fileStream.close();
                                            }
                                            catch (Throwable throwable) {
                                                databytes.addSuppressed(throwable);
                                            }
                                            continue;
                                        }
                                        fileStream.close();
                                        continue;
                                    }
                                }
                                finally {
                                    try {
                                        boolean jioDelete;
                                        jioFileInfo = new File(data.getData().getFileLocation());
                                        if (!jioFileInfo.exists() || (jioDelete = jioFileInfo.delete())) continue;
                                        logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
                                    }
                                    catch (Exception e) {
                                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                                    }
                                    continue;
                                }
                            }
                            ByteArrayInputStream swapStream = new ByteArrayInputStream(data.getData().getData());
                            Throwable throwable = null;
                            try {
                                BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);
                                Throwable throwable2 = null;
                                try {
                                    byte[] databytes = data.getData().getData();
                                    if (null == databytes || databytes.length <= 0) continue;
                                    byte[] bufs = new byte[0xA00000];
                                    tarEntry.setSize(databytes.length);
                                    int read = 0;
                                    while ((read = bis.read(bufs, 0, 10240)) != -1) {
                                        tar.write(bufs, 0, read);
                                    }
                                    tar.closeArchiveEntry();
                                }
                                catch (Throwable throwable3) {
                                    throwable2 = throwable3;
                                    throw throwable3;
                                }
                                finally {
                                    if (bis == null) continue;
                                    if (throwable2 != null) {
                                        try {
                                            bis.close();
                                        }
                                        catch (Throwable throwable4) {
                                            throwable2.addSuppressed(throwable4);
                                        }
                                        continue;
                                    }
                                    bis.close();
                                }
                            }
                            catch (Throwable throwable5) {
                                throwable = throwable5;
                                throw throwable5;
                            }
                            finally {
                                if (swapStream == null) continue;
                                if (throwable != null) {
                                    try {
                                        swapStream.close();
                                    }
                                    catch (Throwable throwable6) {
                                        throwable.addSuppressed(throwable6);
                                    }
                                    continue;
                                }
                                swapStream.close();
                            }
                        }
                        tar.close();
                        PathUtils.validatePathManipulation((String)fileNameInfo);
                        PathUtils.validatePathManipulation((String)removeFile.getPath());
                        removeFile = new File(fileNameInfo);
                        FileInputStream inputStream = new FileInputStream(removeFile.getPath());
                        Throwable throwable = null;
                        try {
                            StringBuilder actionLogInfo = LoggerUtil.buildMessing(batchExportInfo.getContext(), null, "\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6", this.runtimeViewController, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
                            LogInfo logInfo = new LogInfo();
                            logInfo.setActionName("\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6");
                            logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + removeFile.length() + "byte");
                            LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
                            if (batchExportInfo.isSpecifyPath()) continue;
                            FileInfo fileInfo = this.fileService.tempArea().uploadTemp(fileName + ".tar", (InputStream)inputStream);
                            fileInfoKey = fileInfo.getKey();
                            this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_areaInfo"), (Object)this.fileService.tempArea().getAreaConfig().getName());
                            this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_fileSize"), (Object)removeFile.length());
                            boolean removeFlag = removeFile.delete();
                            if (removeFlag) continue;
                            logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
                        }
                        catch (Throwable throwable7) {
                            throwable = throwable7;
                            throw throwable7;
                        }
                        finally {
                            if (inputStream == null) continue;
                            if (throwable != null) {
                                try {
                                    inputStream.close();
                                }
                                catch (Throwable throwable8) {
                                    throwable.addSuppressed(throwable8);
                                }
                                continue;
                            }
                            inputStream.close();
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                logger.error("\u5bfc\u51fa\u6587\u4ef6\u540d\u91cd\u590d,\u68c0\u67e5\u7cfb\u7edf\u914d\u7f6e\u5bfc\u51faexcel\u540d\u79f0,\u65f6\u671f\u914d\u7f6e\u540d\u79f0\u662f\u5426\u6709\u76f8\u540c\u65f6\u671f");
                throw new RuntimeException(e.getMessage(), e);
            }
            finally {
                try {
                    boolean removeDelete;
                    if (removeFile != null && removeFile.exists() && !batchExportInfo.isSpecifyPath() && !(removeDelete = removeFile.delete())) {
                        logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        return fileInfoKey;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String exportToZip(BatchExportInfo batchExportInfo, List<BatchExportTask> batchExportTaskList, String resultLocation, String fileName, String suffix, String fileType, String downLoadKey) throws Exception {
        String fileInfoKey;
        block172: {
            fileInfoKey = null;
            File removeFile = null;
            try {
                PathUtils.validatePathManipulation((String)resultLocation);
                File file = new File(FilenameUtils.normalize(resultLocation));
                if (!file.exists()) {
                    file.mkdirs();
                }
                if (fileType.equals("zip")) {
                    String fileNameInfo = FilenameUtils.normalize(batchExportInfo.getZipLocation() + BatchExportConsts.SEPARATOR + fileName + ".zip");
                    fileNameInfo = fileNameInfo.replace("\\\\", "/");
                    String filePath = "";
                    try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileNameInfo));
                         ZipOutputStream zos = new ZipOutputStream(fileOutputStream);){
                        for (BatchExportTask batchExportTask : batchExportTaskList) {
                            List<BatchExportData> datas = batchExportTask.getExportDatas();
                            for (BatchExportData data : datas) {
                                filePath = data.getData().isEnclosure() ? data.getLocation() + data.getData().getFileName().replace("/", "-").replace("\\", "-") : data.getLocation() + data.getData().getFileName().replace("/", "-").replace("\\", "-") + suffix;
                                zos.putNextEntry(new ZipEntry(filePath));
                                if (StringUtils.isNotEmpty((String)data.getData().getFileLocation())) {
                                    File jioFileInfo = new File(data.getData().getFileLocation());
                                    try {
                                        FileInputStream fileStream = new FileInputStream(jioFileInfo);
                                        Throwable throwable = null;
                                        try {
                                            PathUtils.validatePathManipulation((String)data.getData().getFileLocation());
                                            long throwable2 = IOUtils.copyLarge(fileStream, zos);
                                            continue;
                                        }
                                        catch (Throwable throwable2) {
                                            throwable = throwable2;
                                            throw throwable2;
                                        }
                                        finally {
                                            if (fileStream == null) continue;
                                            if (throwable != null) {
                                                try {
                                                    fileStream.close();
                                                }
                                                catch (Throwable throwable3) {
                                                    throwable.addSuppressed(throwable3);
                                                }
                                                continue;
                                            }
                                            fileStream.close();
                                            continue;
                                        }
                                    }
                                    finally {
                                        try {
                                            PathUtils.validatePathManipulation((String)data.getData().getFileLocation());
                                            jioFileInfo = new File(data.getData().getFileLocation());
                                            if (!jioFileInfo.exists()) continue;
                                            jioFileInfo.delete();
                                        }
                                        catch (Exception e) {
                                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                                        }
                                        continue;
                                    }
                                }
                                ByteArrayInputStream swapStream = new ByteArrayInputStream(data.getData().getData());
                                Throwable e = null;
                                try {
                                    BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);
                                    Throwable throwable = null;
                                    try {
                                        byte[] databytes = data.getData().getData();
                                        if (null == databytes || databytes.length <= 0) continue;
                                        byte[] bufs = new byte[0xA00000];
                                        int read = 0;
                                        while ((read = bis.read(bufs, 0, 10240)) != -1) {
                                            zos.write(bufs, 0, read);
                                        }
                                    }
                                    catch (Throwable databytes) {
                                        throwable = databytes;
                                        throw databytes;
                                    }
                                    finally {
                                        if (bis == null) continue;
                                        if (throwable != null) {
                                            try {
                                                bis.close();
                                            }
                                            catch (Throwable databytes) {
                                                throwable.addSuppressed(databytes);
                                            }
                                            continue;
                                        }
                                        bis.close();
                                    }
                                }
                                catch (Throwable bis) {
                                    e = bis;
                                    throw bis;
                                }
                                finally {
                                    if (swapStream == null) continue;
                                    if (e != null) {
                                        try {
                                            swapStream.close();
                                        }
                                        catch (Throwable bis) {
                                            e.addSuppressed(bis);
                                        }
                                        continue;
                                    }
                                    swapStream.close();
                                }
                            }
                        }
                        this.excelFilesZip(resultLocation, "", zos, file, fileNameInfo);
                        zos.close();
                        PathUtils.validatePathManipulation((String)fileNameInfo);
                        removeFile = new File(fileNameInfo);
                        Throwable throwable = null;
                        try (FileInputStream inputStream = new FileInputStream(removeFile.getPath());){
                            StringBuilder actionLogInfo = LoggerUtil.buildMessing(batchExportInfo.getContext(), null, "\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6", this.runtimeViewController, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
                            LogInfo logInfo = new LogInfo();
                            logInfo.setActionName("\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6");
                            logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + removeFile.length() + "byte");
                            LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
                            if (!batchExportInfo.isSpecifyPath()) {
                                FileInfo fileInfo = this.fileService.tempArea().uploadTemp(fileName + ".zip", (InputStream)inputStream);
                                fileInfoKey = fileInfo.getKey();
                                this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_areaInfo"), (Object)this.fileService.tempArea().getAreaConfig().getName());
                                this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_fileSize"), (Object)removeFile.length());
                            }
                            for (FileInfo currFile : file.list()) {
                                boolean delete;
                                PathUtils.validatePathManipulation((String)(file.getAbsolutePath() + BatchExportConsts.SEPARATOR + (String)currFile));
                                File sourceFile = new File(file.getAbsolutePath() + BatchExportConsts.SEPARATOR + (String)currFile);
                                if (!sourceFile.isDirectory() || (delete = sourceFile.delete())) continue;
                                logger.info("\u5220\u9664\u5931\u8d25\u3002");
                            }
                            break block172;
                        }
                        catch (Throwable actionLogInfo) {
                            Throwable throwable4 = actionLogInfo;
                            throw actionLogInfo;
                        }
                    }
                    catch (Exception e) {
                        throw new RuntimeException("\u5bfc\u51fa\u6587\u4ef6\u540d\u91cd\u590d" + filePath + ",\u68c0\u67e5\u7cfb\u7edf\u914d\u7f6e\u5bfc\u51faexcel\u540d\u79f0,\u65f6\u671f\u914d\u7f6e\u540d\u79f0\u662f\u5426\u6709\u76f8\u540c\u65f6\u671f");
                    }
                }
                if (!fileType.equals("tar")) break block172;
                String fileNameInfo = FilenameUtils.normalize(batchExportInfo.getZipLocation() + BatchExportConsts.SEPARATOR + fileName + ".tar");
                fileNameInfo = fileNameInfo.replace("\\\\", "/");
                try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileNameInfo));
                     TarArchiveOutputStream tar = new TarArchiveOutputStream(fileOutputStream);){
                    for (BatchExportTask batchExportTask : batchExportTaskList) {
                        List<BatchExportData> list = batchExportTask.getExportDatas();
                        for (BatchExportData data : list) {
                            String filePath = "";
                            String tfileName = "";
                            if (data.getData().getFileName().contains(".")) {
                                filePath = data.getLocation() + data.getData().getFileName();
                                tfileName = data.getLocation() + data.getData().getFileName().replace("/", "-").replace("\\", "-");
                            } else {
                                filePath = data.getLocation() + data.getData().getFileName() + suffix;
                                tfileName = data.getLocation() + data.getData().getFileName().replace("/", "-").replace("\\", "-") + suffix;
                            }
                            if (!filePath.contains(suffix)) {
                                filePath = filePath + suffix;
                            }
                            if (!tfileName.contains(suffix)) {
                                tfileName = tfileName + suffix;
                            }
                            PathUtils.validatePathManipulation((String)filePath);
                            TarArchiveEntry tarEntry = new TarArchiveEntry(new File(filePath), tfileName);
                            tar.putArchiveEntry(tarEntry);
                            if (StringUtils.isNotEmpty((String)data.getData().getFileLocation())) {
                                PathUtils.validatePathManipulation((String)data.getData().getFileLocation());
                                File jioFileInfo = new File(data.getData().getFileLocation());
                                try {
                                    FileInputStream fileStream = new FileInputStream(jioFileInfo);
                                    Throwable delete = null;
                                    try {
                                        long copyLarge = IOUtils.copyLarge(fileStream, tar);
                                        tar.closeArchiveEntry();
                                        continue;
                                    }
                                    catch (Throwable throwable) {
                                        delete = throwable;
                                        throw throwable;
                                    }
                                    finally {
                                        if (fileStream == null) continue;
                                        if (delete != null) {
                                            try {
                                                fileStream.close();
                                            }
                                            catch (Throwable throwable4) {
                                                delete.addSuppressed(throwable4);
                                            }
                                            continue;
                                        }
                                        fileStream.close();
                                        continue;
                                    }
                                }
                                finally {
                                    try {
                                        jioFileInfo = new File(data.getData().getFileLocation());
                                        if (!jioFileInfo.exists()) continue;
                                        jioFileInfo.delete();
                                    }
                                    catch (Exception e) {
                                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                                    }
                                    continue;
                                }
                            }
                            ByteArrayInputStream swapStream = new ByteArrayInputStream(data.getData().getData());
                            Throwable throwable = null;
                            try {
                                BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);
                                Throwable throwable5 = null;
                                try {
                                    byte[] databytes = data.getData().getData();
                                    if (null == databytes || databytes.length <= 0) continue;
                                    byte[] bufs = new byte[0xA00000];
                                    tarEntry.setSize(databytes.length);
                                    int read = 0;
                                    while ((read = bis.read(bufs, 0, 10240)) != -1) {
                                        tar.write(bufs, 0, read);
                                    }
                                    tar.closeArchiveEntry();
                                }
                                catch (Throwable throwable6) {
                                    throwable5 = throwable6;
                                    throw throwable6;
                                }
                                finally {
                                    if (bis == null) continue;
                                    if (throwable5 != null) {
                                        try {
                                            bis.close();
                                        }
                                        catch (Throwable throwable7) {
                                            throwable5.addSuppressed(throwable7);
                                        }
                                        continue;
                                    }
                                    bis.close();
                                }
                            }
                            catch (Throwable throwable8) {
                                throwable = throwable8;
                                throw throwable8;
                            }
                            finally {
                                if (swapStream == null) continue;
                                if (throwable != null) {
                                    try {
                                        swapStream.close();
                                    }
                                    catch (Throwable throwable9) {
                                        throwable.addSuppressed(throwable9);
                                    }
                                    continue;
                                }
                                swapStream.close();
                            }
                        }
                        tar.close();
                        PathUtils.validatePathManipulation((String)fileNameInfo);
                        PathUtils.validatePathManipulation((String)removeFile.getPath());
                        removeFile = new File(fileNameInfo);
                        FileInputStream inputStream = new FileInputStream(removeFile.getPath());
                        Throwable throwable = null;
                        try {
                            StringBuilder actionLogInfo = LoggerUtil.buildMessing(batchExportInfo.getContext(), null, "\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6", this.runtimeViewController, this.jtableParamService, this.periodEngineService, this.jtableEntityService);
                            LogInfo logInfo = new LogInfo();
                            logInfo.setActionName("\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6");
                            logInfo.setLogInfo(actionLogInfo.toString() + "\u6587\u4ef6\u5185\u5bb9\u5927\u5c0f\u4e3a\uff1a" + removeFile.length() + "byte");
                            LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)logInfo.getActionName(), (String)logInfo.getLogInfo());
                            if (batchExportInfo.isSpecifyPath()) continue;
                            FileInfo fileInfo = this.fileService.tempArea().uploadTemp(fileName + ".tar", (InputStream)inputStream);
                            fileInfoKey = fileInfo.getKey();
                            this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_areaInfo"), (Object)this.fileService.tempArea().getAreaConfig().getName());
                            this.cacheObjectResourceRemote.create((Object)(downLoadKey + "_fileSize"), (Object)removeFile.length());
                            removeFile.delete();
                        }
                        catch (Throwable throwable10) {
                            throwable = throwable10;
                            throw throwable10;
                        }
                        finally {
                            if (inputStream == null) continue;
                            if (throwable != null) {
                                try {
                                    inputStream.close();
                                }
                                catch (Throwable throwable11) {
                                    throwable.addSuppressed(throwable11);
                                }
                                continue;
                            }
                            inputStream.close();
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                logger.error("\u5bfc\u51fa\u6587\u4ef6\u540d\u91cd\u590d,\u68c0\u67e5\u7cfb\u7edf\u914d\u7f6e\u5bfc\u51faexcel\u540d\u79f0,\u65f6\u671f\u914d\u7f6e\u540d\u79f0\u662f\u5426\u6709\u76f8\u540c\u65f6\u671f");
                throw new RuntimeException(e.getMessage(), e);
            }
            finally {
                try {
                    if (removeFile != null && removeFile.exists() && !batchExportInfo.isSpecifyPath()) {
                        removeFile.delete();
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        return fileInfoKey;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String exportToFile(List<BatchExportTask> batchExportTaskList, String resultLocation, String fileName, String suffix, String fileType, boolean isSpecifyPath, String singleFileName) {
        String newFileName;
        block71: {
            newFileName = "";
            try {
                BatchExportData data = null;
                for (BatchExportTask batchExportTask : batchExportTaskList) {
                    List<BatchExportData> datas = batchExportTask.getExportDatas();
                    if (datas.size() <= 0) continue;
                    data = datas.get(0);
                }
                long now = Instant.now().toEpochMilli();
                Date date = new Date(now);
                SimpleDateFormat dateFormatForFolder = new SimpleDateFormat("yyyyMMddHHmmss");
                String formatDateFolder = dateFormatForFolder.format(date);
                if (data == null) break block71;
                newFileName = data.getData().getAlisFileName();
                if (StringUtils.isEmpty((String)newFileName)) {
                    newFileName = data.getData().getFileName();
                }
                newFileName = newFileName + data.getLocation().replace(BatchExportConsts.SEPARATOR, "") + this.exportExcelNameService.getSysSeparator() + formatDateFolder;
                String filePath = "";
                filePath = StringUtils.isNotEmpty((String)singleFileName) && isSpecifyPath ? FilenameUtils.normalize(resultLocation + BatchExportConsts.SEPARATOR + fileName + suffix).replaceAll("\\\\", "/") : FilenameUtils.normalize(resultLocation + BatchExportConsts.SEPARATOR + newFileName + suffix).replaceAll("\\\\", "/");
                try (FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));){
                    if (StringUtils.isNotEmpty((String)data.getData().getFileLocation())) {
                        PathUtils.validatePathManipulation((String)data.getData().getFileLocation());
                        File jioFileInfo = new File(data.getData().getFileLocation());
                        try (FileInputStream fileStream = new FileInputStream(jioFileInfo);){
                            long l = IOUtils.copyLarge(fileStream, fileOutputStream);
                            break block71;
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            break block71;
                        }
                        finally {
                            try {
                                jioFileInfo = new File(data.getData().getFileLocation());
                                if (jioFileInfo.exists()) {
                                    jioFileInfo.delete();
                                }
                            }
                            catch (Exception e) {
                                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            }
                        }
                    }
                    try (ByteArrayInputStream swapStream = new ByteArrayInputStream(data.getData().getData());
                         BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);){
                        byte[] databytes = data.getData().getData();
                        if (null != databytes && databytes.length > 0) {
                            byte[] bufs = new byte[0xA00000];
                            int read = 0;
                            while ((read = bis.read(bufs, 0, 10240)) != -1) {
                                fileOutputStream.write(bufs, 0, read);
                            }
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return newFileName;
    }

    private void excelFilesZipWithPassword(String resultLocation, String dir, net.lingala.zip4j.io.outputstream.ZipOutputStream zos, File file, String fileNameInfo) throws IOException, FileNotFoundException {
        String uuidInfo = resultLocation.substring(resultLocation.lastIndexOf(BatchExportConsts.SEPARATOR));
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);
        zipParameters.setCompressionLevel(CompressionLevel.NORMAL);
        for (String fileName : file.list()) {
            try {
                PathUtils.validatePathManipulation((String)(file.getAbsolutePath() + BatchExportConsts.SEPARATOR + fileName));
            }
            catch (SecurityContentException e) {
                throw new RuntimeException(e);
            }
            File sourceFile = FileUtil.createIfNotExists((String)(file.getAbsolutePath() + BatchExportConsts.SEPARATOR + fileName));
            if (sourceFile.isDirectory()) {
                this.excelFilesZipWithPassword(resultLocation, fileName, zos, sourceFile, fileNameInfo);
            }
            if (!sourceFile.isFile() || (file.getAbsolutePath() + BatchExportConsts.SEPARATOR + fileName).equals(fileNameInfo)) continue;
            String zipEntityName = "";
            zipEntityName = dir.equals("") ? fileName : sourceFile.getAbsolutePath().substring(sourceFile.getAbsolutePath().lastIndexOf(uuidInfo) + uuidInfo.length() + 1);
            try (FileInputStream in = new FileInputStream(sourceFile);){
                zipParameters.setFileNameInZip(zipEntityName);
                zipParameters.setEncryptFiles(true);
                zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
                zos.putNextEntry(zipParameters);
                IOUtils.copyLarge(in, (OutputStream)zos);
                zos.closeEntry();
            }
            catch (Exception e) {
                logger.error("\u538b\u7f29\u6587\u4ef6\u51fa\u9519\uff1a" + e.getMessage(), e);
            }
        }
    }

    private void excelFilesZip(String resultLocation, String dir, ZipOutputStream zos, File file, String fileNameInfo) throws IOException, FileNotFoundException {
        String uuidInfo = resultLocation.substring(resultLocation.lastIndexOf(BatchExportConsts.SEPARATOR));
        for (String fileName : file.list()) {
            try {
                PathUtils.validatePathManipulation((String)(file.getAbsolutePath() + BatchExportConsts.SEPARATOR + fileName));
            }
            catch (SecurityContentException e) {
                throw new RuntimeException(e);
            }
            File sourceFile = FileUtil.createIfNotExists((String)(file.getAbsolutePath() + BatchExportConsts.SEPARATOR + fileName));
            if (sourceFile.isDirectory()) {
                this.excelFilesZip(resultLocation, fileName, zos, sourceFile, fileNameInfo);
            }
            if (!sourceFile.isFile() || (file.getAbsolutePath() + BatchExportConsts.SEPARATOR + fileName).equals(fileNameInfo)) continue;
            String zipEntityName = "";
            zipEntityName = dir.equals("") ? fileName : sourceFile.getAbsolutePath().substring(sourceFile.getAbsolutePath().lastIndexOf(uuidInfo) + uuidInfo.length() + 1);
            try (FileInputStream in = new FileInputStream(sourceFile);){
                int len;
                zos.putNextEntry(new ZipEntry(zipEntityName));
                byte[] buf = new byte[2048];
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private BatchExportTask exportToDatasByPdf(String filePathUuid, BatchExportInfo info, AsyncTaskMonitor asyncTaskMonitor, double numOfIndex) {
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6279\u91cf\u5bfc\u51fa\u670d\u52a1", OperLevel.USER_OPER);
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(info.getContext().getFormSchemeKey());
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
        LogDimensionCollection logDimension = null;
        try {
            logDimension = new LogDimensionCollection();
            String value = ((DimensionValue)info.getContext().getDimensionSet().get(queryEntity.getDimensionName())).getValue();
            if (value != null && value.contains(";")) {
                String[] split = value.split(";");
                logDimension.setDw(formScheme.getDw(), split);
            } else if (value == null || value.equals("")) {
                List<IEntityRow> entityDataList = this.getEntityDataList(info.getContext().getFormSchemeKey(), info.getContext().getDimensionSet());
                if (entityDataList != null) {
                    String[] values = new String[entityDataList.size()];
                    int i = 0;
                    for (IEntityRow iEntityRow : entityDataList) {
                        values[i] = iEntityRow.getCode();
                        ++i;
                    }
                    logDimension.setDw(formScheme.getDw(), values);
                } else {
                    logDimension.setDw(formScheme.getDw(), new String[]{value});
                }
            } else {
                logDimension.setDw(formScheme.getDw(), new String[]{value});
            }
            logDimension.setPeriod(formScheme.getDateTime(), ((DimensionValue)info.getContext().getDimensionSet().get("DATATIME")).getValue());
        }
        catch (Exception e1) {
            logger.error("\u6784\u9020\u4e1a\u52a1\u65e5\u5fd7\u7ef4\u5ea6\u51fa\u9519");
        }
        logHelper.info(formScheme.getTaskKey(), logDimension, "\u6279\u91cf\u5bfc\u51fa\u5f00\u59cb", "PDF\u6279\u91cf\u5bfc\u51fa");
        String editProgress = "edit_progress_info";
        JtableContext jtableContext = info.getContext();
        BatchDimensionParam dimensionInfoBuild = this.dimensionInfoBuild(info.getContext(), asyncTaskMonitor, false);
        if (null == dimensionInfoBuild) {
            logHelper.info(formScheme.getTaskKey(), logDimension, "\u6279\u91cf\u5bfc\u51fa\u5b8c\u6210", "pdf \u5bfc\u51fa\u5b8c\u6210,\u7ef4\u5ea6\u4fe1\u606f\u4e3a\u7a7a\uff01");
            return null;
        }
        String sysSep = this.exportExcelNameService.getSysSeparator();
        List<Map<String, DimensionValue>> resultDimension = dimensionInfoBuild.getList();
        List<String> entityKeys = dimensionInfoBuild.getEntitys();
        double firstLevel = 0.05;
        asyncTaskMonitor.progressAndMessage(0.05 * numOfIndex * numOfIndex, editProgress);
        ArrayList datas = new ArrayList();
        List<String> formKeys = info.getFormKeys();
        ArrayList<String> formKeys2 = new ArrayList<String>();
        for (int i = 0; i < formKeys.size(); ++i) {
            if (formKeys2.contains(formKeys.get(i))) continue;
            formKeys2.add(formKeys.get(i));
        }
        List entityList = new ArrayList();
        try {
            entityList = this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        EntityViewData dwEntity1 = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        DimensionCollection dimCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)jtableContext.getDimensionSet(), (String)jtableContext.getFormSchemeKey());
        List dimCollectionList = dimCollection.getDimensionCombinations();
        HashSet<String> unitKeys = new HashSet<String>();
        for (DimensionCombination fixedDimensionValues : dimCollectionList) {
            unitKeys.add(fixedDimensionValues.getValue(dwEntity1.getDimensionName()).toString());
        }
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        String dateType = dataTimeEntity.getDimensionName();
        ArrayList<String> listEntity = new ArrayList<String>();
        for (EntityViewData entityInfo : entityList) {
            listEntity.add(entityInfo.getKey());
        }
        formKeys = formKeys2;
        ArrayList<String> allFormKeysAfterFormat = new ArrayList<String>();
        for (int i = 0; i < formKeys.size(); ++i) {
            FormDefine formDefine = this.runtimeViewController.queryFormById(formKeys.get(i));
            if (formDefine.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) continue;
            allFormKeysAfterFormat.add(formKeys.get(i));
        }
        ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, allFormKeysAfterFormat, Consts.FormAccessLevel.FORM_DATA_READ);
        ReadWriteAccessCacheManager readWriteAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
        readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
        double index = 0.0;
        double allSize = 0.0;
        String dateDir = "";
        if (StringUtils.isNotEmpty((String)dimensionInfoBuild.getDate())) {
            String date = this.getPeriodTitle(info.getContext().getFormSchemeKey(), dimensionInfoBuild.getDate());
            dateDir = date + BatchExportConsts.SEPARATOR;
        }
        HashMap<String, String> infoMap = new HashMap<String, String>();
        INvwaSystemOptionService iNvwaSystemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        String value = iNvwaSystemOptionService.get("nr-data-entry-export", "SIMPLIFY_EXPORT_FILE_HIERARCHY");
        boolean simplifyExportFileHierarchy = "1".equals(value);
        if (info.getRuleSettings() != null) {
            simplifyExportFileHierarchy = info.getRuleSettings().isSimplifyExportFileHierarchy();
        }
        if (simplifyExportFileHierarchy) {
            for (Map<String, DimensionValue> map : dimensionInfoBuild.getList()) {
                Set<String> keySet = map.keySet();
                for (String string : keySet) {
                    infoMap.put(map.get(string).getValue(), string);
                }
            }
        }
        HashMap<String, EntityData> locationTitleMap = new HashMap<String, EntityData>();
        String fileLocation = BatchExportConsts.EXPORTDIR + BatchExportConsts.SEPARATOR + "concatPDF" + BatchExportConsts.SEPARATOR;
        ArrayList<BatchExportData> datasAfterConcat = new ArrayList<BatchExportData>();
        for (Map map : dimensionInfoBuild.getList()) {
            File ifNotExists = null;
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                return null;
            }
            if (!unitKeys.contains(((DimensionValue)map.get(dwEntity1.getDimensionName())).getValue())) continue;
            boolean haveData = false;
            long currentTime = System.currentTimeMillis();
            ArrayList beforeFormatData = new ArrayList();
            JtableContext contextQuery = new JtableContext();
            contextQuery.setDimensionSet(map);
            contextQuery.setFormSchemeKey(jtableContext.getFormSchemeKey());
            contextQuery.setTaskKey(jtableContext.getTaskKey());
            contextQuery.setFormSchemeKey(jtableContext.getFormSchemeKey());
            FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(contextQuery, Consts.FormAccessLevel.FORM_READ);
            FormReadWriteAccessData accessForms = this.readWriteAccessProvider.getAccessForms(formReadWriteAccessData, readWriteAccessCacheManager);
            List<String> dimensionValueformKeys = accessForms.getFormKeys();
            if (!formKeys.isEmpty()) {
                Iterator<String> its = dimensionValueformKeys.iterator();
                while (its.hasNext()) {
                    String formKey = its.next();
                    if (formKeys.contains(formKey)) continue;
                    its.remove();
                }
            }
            allSize = (double)resultDimension.size() * (double)dimensionValueformKeys.size();
            HashSet fileNameSet = new HashSet();
            ArrayList<String> formKeyAfterFormat = new ArrayList<String>();
            for (String formKey : dimensionValueformKeys) {
                FormDefine formDefine = this.runtimeViewController.queryFormById(formKey);
                if (formDefine.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) continue;
                formKeyAfterFormat.add(formKey);
            }
            DefaultPageNumberGenerateStrategy strategy = new DefaultPageNumberGenerateStrategy();
            String location = "";
            if (StringUtils.isNotEmpty((String)dateDir)) {
                location = location + dateDir;
            }
            ArrayList<EntityData> entityReturnInfos = null;
            jtableContext.setDimensionSet(map);
            boolean secretLevelEnable = this.iSecretLevelService.secretLevelEnable(jtableContext.getTaskKey());
            if (secretLevelEnable) {
                SecretLevelInfo secretLevelInfo = this.iSecretLevelService.getSecretLevel(jtableContext);
                if (info.getSecretLevel() != null && secretLevelInfo != null && secretLevelInfo.getSecretLevelItem().getTitle() != null) {
                    info.setThisSecretLevel(secretLevelInfo.getSecretLevelItem().getTitle());
                    if (this.iSecretLevelService.compareSercetLevel(secretLevelInfo.getSecretLevelItem(), this.iSecretLevelService.getSecretLevelItem(info.getSecretLevel()))) {
                        info.setSecretLevel(secretLevelInfo.getSecretLevelItem().getTitle());
                    }
                } else if (secretLevelInfo != null && secretLevelInfo.getSecretLevelItem().getTitle() != null) {
                    info.setSecretLevel(secretLevelInfo.getSecretLevelItem().getTitle());
                    info.setThisSecretLevel(secretLevelInfo.getSecretLevelItem().getTitle());
                }
            }
            HashMap tempMap = null;
            if (entityKeys != null && entityKeys.size() > 0) {
                int loc = 0;
                entityReturnInfos = new ArrayList<EntityData>();
                for (int x = 0; x < entityKeys.size(); ++x) {
                    entityReturnInfos.add(null);
                }
                tempMap = new HashMap();
                for (Map.Entry entry : map.entrySet()) {
                    EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                    if (listEntity.indexOf("ADJUST") == -1 && ((String)entry.getKey()).equals("ADJUST")) continue;
                    entityQueryByKeyInfo.setEntityViewKey(entityKeys.get(loc));
                    entityQueryByKeyInfo.setEntityKey(((DimensionValue)entry.getValue()).getValue());
                    entityQueryByKeyInfo.setContext(jtableContext);
                    EntityData entrunInfo = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo).getEntity();
                    int indexYuan = listEntity.indexOf(entityKeys.get(loc));
                    entityReturnInfos.set(indexYuan, entrunInfo);
                    ++loc;
                    tempMap.put(indexYuan, entry.getKey());
                }
            }
            EntityData dwEntity = (EntityData)entityReturnInfos.get(0);
            if (entityReturnInfos != null && tempMap != null) {
                for (int j = 0; j < entityReturnInfos.size(); ++j) {
                    String type = (String)tempMap.get(j);
                    if (dateType.equals(type) || null == entityReturnInfos.get(j)) continue;
                    Object title = ((EntityData)entityReturnInfos.get(j)).getTitle();
                    int strNum = 0;
                    if (simplifyExportFileHierarchy) {
                        String entityId = ((EntityData)entityReturnInfos.get(j)).getId();
                        String entityStr = (String)infoMap.get(entityId);
                        for (String str : infoMap.values()) {
                            if (!entityStr.equals(str)) continue;
                            ++strNum;
                        }
                    }
                    if (StringUtils.isEmpty((String)title)) continue;
                    if (info.getThisSecretLevel() != null && j == 0) {
                        title = (String)title + sysSep + info.getThisSecretLevel();
                    }
                    if (strNum == 1) {
                        int tagIndex = location.lastIndexOf(BatchExportConsts.SEPARATOR);
                        String beforeLocation = location.substring(0, tagIndex);
                        location = beforeLocation + "_" + (String)title + BatchExportConsts.SEPARATOR;
                        continue;
                    }
                    location = location + (String)title + BatchExportConsts.SEPARATOR;
                }
            }
            String entityTitle = this.exportExcelNameService.compileNameInfoWithSetting("", contextQuery, "EXCEL_NAME", false, info.getContext().getUnitViewKey(), info.getRuleSettings());
            if (info.getThisSecretLevel() != null) {
                entityTitle = entityTitle + sysSep + info.getThisSecretLevel();
            }
            if (locationTitleMap.containsKey(location)) {
                String entityTitleWithCode = entityTitle + sysSep + dwEntity.getCode();
                location = location.replace(entityTitle, entityTitleWithCode);
                dwEntity.setTitle(entityTitleWithCode);
                locationTitleMap.put(location, dwEntity);
            } else {
                if (info.getThisSecretLevel() != null) {
                    entityTitle = entityTitle + sysSep + info.getThisSecretLevel();
                }
                int repeatNum = 0;
                for (EntityData entityData : locationTitleMap.values()) {
                    if (!entityData.getTitle().contains(entityTitle)) continue;
                    ++repeatNum;
                }
                if (repeatNum > 0) {
                    entityTitle = entityTitle + "(" + repeatNum + ")";
                }
                dwEntity.setTitle(entityTitle);
                locationTitleMap.put(location, dwEntity);
            }
            com.itextpdf.kernel.pdf.PdfDocument mergedDoc = null;
            PdfWriter writer = null;
            String outFile = fileLocation + filePathUuid + BatchExportConsts.SEPARATOR + location + entityTitle + ".pdf";
            try {
                ifNotExists = FileUtil.createIfNotExists((String)outFile);
                WriterProperties writerProperties = new WriterProperties();
                writerProperties.useSmartMode();
                writer = new PdfWriter(outFile, writerProperties);
                mergedDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
                PdfMerger pdfMerger = new PdfMerger(mergedDoc);
                for (int i = 0; i < formKeyAfterFormat.size(); ++i) {
                    if (asyncTaskMonitor.isCancel()) {
                        String str;
                        asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                        str = null;
                        return str;
                    }
                    index += 1.0;
                    byte[] betys = null;
                    ExportParam param = new ExportParam();
                    jtableContext.setFormKey((String)formKeyAfterFormat.get(i));
                    param.setContext(jtableContext);
                    param.setLabel(info.isTableTab());
                    param.setBackground(info.isCellBackGround());
                    param.setOnlyStyle(info.isEmptyTable());
                    param.setPrintSchemeKey(info.getPrintSchemeKey());
                    param.setExportZero(info.isExportZero());
                    param.setExportEmptyTable(info.isExportEmptyTable());
                    FormData jtableData = this.jtableParamService.getReport((String)formKeyAfterFormat.get(i), jtableContext.getFormSchemeKey());
                    betys = this.exportPdfServiceImpl.exportPdf(param, (IPageNumberGenerateStrategy)strategy);
                    if (betys != null) {
                        haveData = true;
                        try (ByteArrayInputStream formPdfStream = new ByteArrayInputStream(betys);
                             PdfReader reader = new PdfReader((InputStream)formPdfStream);
                             com.itextpdf.kernel.pdf.PdfDocument inputDoc = new com.itextpdf.kernel.pdf.PdfDocument(reader);){
                            pdfMerger.merge(inputDoc, 1, inputDoc.getNumberOfPages());
                            mergedDoc.flushCopiedObjects(inputDoc);
                        }
                    }
                    double percent = index / allSize * 0.7 * numOfIndex * numOfIndex;
                    asyncTaskMonitor.progressAndMessage(firstLevel + percent, editProgress);
                }
                logger.info("success");
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            finally {
                if (mergedDoc != null) {
                    mergedDoc.close();
                }
                if (writer != null) {
                    try {
                        writer.close();
                    }
                    catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
            if (!haveData) continue;
            ExportData exportData = null;
            if (info.getFileType().equals("EXPORT_BATCH_OFD")) {
                SpireHelper.loadSpireLicence();
                PdfDocument pdfDoc = new PdfDocument();
                pdfDoc.loadFromFile(fileLocation + filePathUuid + BatchExportConsts.SEPARATOR + location + entityTitle + ".pdf");
                pdfDoc.saveToFile(fileLocation + filePathUuid + BatchExportConsts.SEPARATOR + location + entityTitle + ".ofd", FileFormat.OFD);
                File file = new File(fileLocation + filePathUuid + BatchExportConsts.SEPARATOR + location + entityTitle + ".pdf");
                boolean delete = file.delete();
                if (!delete) {
                    logger.info("\u5220\u9664\u4e34\u65f6pdf\u6587\u4ef6\u5220\u9664\u5931\u8d25");
                }
                exportData = new ExportData(entityTitle, entityTitle, fileLocation + filePathUuid + BatchExportConsts.SEPARATOR + location + entityTitle + ".ofd");
            } else {
                exportData = new ExportData(entityTitle, entityTitle, fileLocation + filePathUuid + BatchExportConsts.SEPARATOR + location + entityTitle + ".pdf");
            }
            BatchExportData batchExportData = new BatchExportData();
            batchExportData.setData(exportData);
            String entityInfo = BatchExportConsts.SEPARATOR + ((EntityData)locationTitleMap.get(location)).getRowCaption();
            String locationAfterFormat = location.replace(entityInfo, "");
            batchExportData.setLocation(locationAfterFormat);
            datasAfterConcat.add(batchExportData);
        }
        BatchExportTask exportTask = new BatchExportTask(datasAfterConcat);
        asyncTaskMonitor.progressAndMessage(0.95 * numOfIndex * numOfIndex, editProgress);
        logHelper.info(formScheme.getTaskKey(), logDimension, "\u6279\u91cf\u5bfc\u51fa\u5b8c\u6210", "pdf \u5bfc\u51fa\u5b8c\u6210");
        return exportTask;
    }

    /*
     * Exception decompiling
     */
    private byte[] concatPDFsOfPDFList(List<ExportData> pdfList) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private BatchExportTask exportToDatasByJio(BatchExportInfo info, List<String> multiplePeriodList, AsyncTaskMonitor asyncTaskMonitor) throws IOException {
        ArrayList<BatchDimensionParam> batchDimensionParamList = new ArrayList<BatchDimensionParam>();
        ArrayList<String> periodListAfterFilter = new ArrayList<String>();
        periodListAfterFilter.addAll(multiplePeriodList);
        EntityViewData dwEntity1 = this.jtableParamService.getDwEntity(info.getContext().getFormSchemeKey());
        DimensionCollection dimCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)info.getContext().getDimensionSet(), (String)info.getContext().getFormSchemeKey());
        List dimCollectionList = dimCollection.getDimensionCombinations();
        HashSet<String> unitKeys = new HashSet<String>();
        for (DimensionCombination fixedDimensionValues : dimCollectionList) {
            unitKeys.add(fixedDimensionValues.getValue(dwEntity1.getDimensionName()).toString());
        }
        if (multiplePeriodList.size() > 1) {
            for (int i = 0; i < multiplePeriodList.size(); ++i) {
                ((DimensionValue)info.getContext().getDimensionSet().get("DATATIME")).setValue(multiplePeriodList.get(i));
                ((DimensionValue)info.getContext().getDimensionSet().get("DATATIME")).setType(info.getPeriodType().intValue());
                BatchDimensionParam dimensionInfoBuild = this.dimensionInfoBuild(info.getContext(), asyncTaskMonitor, true, true);
                if (dimensionInfoBuild != null) {
                    dimensionInfoBuild.setList(dimensionInfoBuild.getList().stream().filter(item -> unitKeys.contains(((DimensionValue)item.get(dwEntity1.getDimensionName())).getValue())).collect(Collectors.toList()));
                    batchDimensionParamList.add(dimensionInfoBuild);
                    continue;
                }
                periodListAfterFilter.remove(multiplePeriodList.get(i));
            }
        } else {
            BatchDimensionParam dimensionInfoBuild = this.dimensionInfoBuild(info.getContext(), asyncTaskMonitor, false, true);
            dimensionInfoBuild.setList(dimensionInfoBuild.getList().stream().filter(item -> unitKeys.contains(((DimensionValue)item.get(dwEntity1.getDimensionName())).getValue())).collect(Collectors.toList()));
            batchDimensionParamList.add(dimensionInfoBuild);
        }
        if (null == batchDimensionParamList || batchDimensionParamList.size() == 0) {
            String noDimension = "no_dimension";
            asyncTaskMonitor.finish(noDimension, (Object)"");
            return null;
        }
        String editProcess = "edit_progress_info";
        asyncTaskMonitor.progressAndMessage(0.05, editProcess);
        ArrayList<BatchExportData> datas = new ArrayList<BatchExportData>();
        List<String> formKeys = info.getFormKeys();
        ArrayList<String> formKeys2 = new ArrayList<String>();
        if (formKeys == null) {
            formKeys = new ArrayList<String>();
        }
        for (int i = 0; i < formKeys.size(); ++i) {
            if (formKeys2.contains(formKeys.get(i))) continue;
            formKeys2.add(formKeys.get(i));
        }
        formKeys = formKeys2;
        String dateDir = "";
        if (StringUtils.isNotEmpty((String)((BatchDimensionParam)batchDimensionParamList.get(0)).getDate())) {
            String date = this.getPeriodTitle(info.getContext().getFormSchemeKey(), ((BatchDimensionParam)batchDimensionParamList.get(0)).getDate());
            dateDir = date + BatchExportConsts.SEPARATOR;
        }
        try {
            this.jioBatchExportExecuter.exportOfMultiplePeriod(info, asyncTaskMonitor, batchDimensionParamList, periodListAfterFilter, formKeys, dateDir, datas);
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                return null;
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new IOException(ex.getMessage());
        }
        asyncTaskMonitor.progressAndMessage(0.95, editProcess);
        BatchExportTask exportTask = new BatchExportTask(datas);
        return exportTask;
    }

    private String exportToDatasByTxt(BatchExportInfo info, AsyncTaskMonitor asyncTaskMonitor, List<String> multiplePeriodInfoList) throws IOException {
        JtableContext context = info.getContext();
        DimensionValueSet dimensionSet = new DimensionValueSet();
        DimensionCollection dimensionCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)context.getDimensionSet(), (String)context.getFormSchemeKey());
        dimensionSet = dimensionCollection.combineWithoutVarDim();
        ArrayList<String> dimsionNames = new ArrayList<String>();
        for (int i = 0; i < dimensionSet.size(); ++i) {
            if (dimensionSet.getValue(i) != null && !dimensionSet.getValue(i).equals("")) continue;
            dimsionNames.add(dimensionSet.getName(i));
        }
        if (!dimsionNames.isEmpty()) {
            for (String dimsionName : dimsionNames) {
                Iterator iterator = dimensionCollection.iterator();
                HashSet<Object> dimsionNameValue = new HashSet<Object>();
                while (iterator.hasNext()) {
                    DimensionValueSet dimensionValueSet = (DimensionValueSet)iterator.next();
                    dimsionNameValue.add(dimensionValueSet.getValue(dimsionName));
                }
                ArrayList list = new ArrayList(dimsionNameValue);
                dimensionSet.setValue(dimsionName, list.size() > 1 ? list : list.get(0));
            }
        }
        TableContext tbContext = new TableContext(context.getTaskKey(), context.getFormSchemeKey(), context.getFormKey(), dimensionSet, OptTypes.TASK, ".txt");
        tbContext.setSplit(info.getSplitMark().replace("\\t", "\t"));
        if (null != context.getFormKey() && !"".equals(context.getFormKey())) {
            tbContext.setOptType(OptTypes.FORM);
        }
        Object zipFile = null;
        List secretLevelInfoList = this.iSecretLevelService.querySecretLevels(info.getContext());
        String secretLevelTitleHigher = "";
        if (secretLevelInfoList != null && secretLevelInfoList.size() > 0) {
            SecretLevelInfo secretLevelInfoHigher = null;
            for (SecretLevelInfo secretLevelInfo : secretLevelInfoList) {
                if (secretLevelInfoHigher != null) {
                    if (!this.iSecretLevelService.compareSercetLevel(secretLevelInfo.getSecretLevelItem(), secretLevelInfoHigher.getSecretLevelItem())) continue;
                    secretLevelInfoHigher = secretLevelInfo;
                    continue;
                }
                secretLevelInfoHigher = secretLevelInfo;
            }
            if (secretLevelInfoHigher != null) {
                secretLevelTitleHigher = secretLevelInfoHigher.getSecretLevelItem().getTitle();
            }
        }
        TextParams params = new TextParams();
        params.setFormSchemeKey(context.getFormSchemeKey());
        params.setMonitor(asyncTaskMonitor);
        params.setDataMasking(true);
        ArrayList<String> formKeys = new ArrayList<String>();
        String formKey = context.getFormKey();
        if (formKey != null && !formKey.equals("") && formKey.contains(";")) {
            String[] split;
            for (String string : split = formKey.split(";")) {
                formKeys.add(string);
            }
        } else if (formKey != null && !formKey.equals("")) {
            formKeys.add(formKey);
        } else {
            List allReportsByFormScheme = this.jtableParamService.getAllReportsByFormScheme(context.getFormSchemeKey());
            for (FormData formData : allReportsByFormScheme) {
                formKeys.add(formData.getKey());
            }
        }
        params.setFormKeys(formKeys);
        DimensionCollection buildDimensionCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)context.getDimensionSet(), (String)context.getFormSchemeKey());
        params.setDimensionSet(buildDimensionCollection);
        String downloadTextData = null;
        String FileName = "";
        if ("EXPORT_BATCH_CSV".equals(info.getFileType())) {
            FileName = "ExportTxtDatas";
            params.setTextType(TextType.TEXTTYPE_CSV);
            downloadTextData = this.expTextService.downloadTextData(params);
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                return null;
            }
        } else {
            FileName = "ExportTxtDatas";
            params.setTextType(TextType.TEXTTYPE_TXT);
            params.setSplit(info.getSplitMark().replace("\\t", "\t"));
            downloadTextData = this.expTextService.downloadTextData(params);
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                return null;
            }
        }
        return downloadTextData;
    }

    private static void addFilesToZip(ZipOutputStream zos, File file, String parentFolderName) throws IOException {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                BatchExportExcelServiceImpl.addFilesToZip(zos, f, parentFolderName == null ? f.getName() : parentFolderName + "/" + f.getName());
                continue;
            }
            byte[] buffer = new byte[1024];
            try (FileInputStream fis = new FileInputStream(f);
                 BufferedInputStream bis = new BufferedInputStream(fis);){
                int bytesRead;
                zos.putNextEntry(new ZipEntry((parentFolderName != null ? parentFolderName + "/" : "") + f.getName()));
                while ((bytesRead = bis.read(buffer)) > 0) {
                    zos.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    private static void addFilesToZipWithPassword(net.lingala.zip4j.io.outputstream.ZipOutputStream zos, File file, String parentFolderName) throws IOException {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                BatchExportExcelServiceImpl.addFilesToZipWithPassword(zos, f, parentFolderName == null ? f.getName() : parentFolderName + "/" + f.getName());
                continue;
            }
            byte[] buffer = new byte[1024];
            try (FileInputStream fis = new FileInputStream(f);
                 BufferedInputStream bis = new BufferedInputStream(fis);){
                int bytesRead;
                ZipParameters zipParameters = new ZipParameters();
                zipParameters.setEncryptFiles(true);
                zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
                zipParameters.setFileNameInZip((parentFolderName != null ? parentFolderName + "/" : "") + f.getName());
                zos.putNextEntry(zipParameters);
                while ((bytesRead = bis.read(buffer)) > 0) {
                    zos.write(buffer, 0, bytesRead);
                }
                zos.closeEntry();
            }
        }
    }

    private int getThreadCount(int elementCount) {
        String threadCountBySys;
        String threadEnable = this.nvwaSystemOptionService.get("nr-data-entry-group", "SELECT_BATCH_EXPORT_EXCEL_THREAD");
        int threadCount = 1;
        if (threadEnable != null && "1".equals(threadEnable) && (threadCountBySys = this.nvwaSystemOptionService.get("nr-data-entry-group", "BATCH_EXPORT_THREAD_COUNT")) != null) {
            int parseInt = Integer.parseInt(threadCountBySys.toString());
            threadCount = elementCount < parseInt ? 1 : parseInt;
        }
        return threadCount;
    }

    private Map<String, Map<String, String>> getSecretLevelTitleHigher(JtableContext jtableContext, TableContext tableContext) {
        HashMap<String, Map<String, String>> secretLevelTitleMap;
        block5: {
            String secretLevelTitleHigher;
            block4: {
                secretLevelTitleHigher = null;
                secretLevelTitleMap = new HashMap<String, Map<String, String>>();
                if (!tableContext.getOptType().equals((Object)OptTypes.TASK)) break block4;
                List forms = this.runtimeViewController.queryAllFormDefinesByTask(tableContext.getTaskKey());
                DimensionValueSet dimensionValueSet = tableContext.getDimensionSet();
                jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
                for (FormDefine item : forms) {
                    jtableContext.setFormKey(item.getKey());
                    boolean secretLevelEnable = this.iSecretLevelService.secretLevelEnable(jtableContext.getTaskKey());
                    if (!secretLevelEnable) continue;
                    SecretLevelInfo secretLevelInfo = this.iSecretLevelService.getSecretLevel(jtableContext);
                    if (secretLevelTitleHigher != null && secretLevelTitleHigher.compareTo(secretLevelInfo.getSecretLevelItem().getTitle()) < 0) {
                        secretLevelTitleHigher = secretLevelInfo.getSecretLevelItem().getTitle();
                        continue;
                    }
                    secretLevelTitleHigher = secretLevelInfo.getSecretLevelItem().getTitle();
                }
                break block5;
            }
            if (!tableContext.getOptType().equals((Object)OptTypes.FORMSCHEME)) break block5;
            List forms = this.runtimeViewController.queryAllFormDefinesByFormScheme(tableContext.getFormSchemeKey());
            for (FormDefine item : forms) {
                jtableContext.setFormKey(item.getKey());
                SecretLevelInfo secretLevelInfo = this.iSecretLevelService.getSecretLevel(jtableContext);
                if (secretLevelTitleHigher != null && secretLevelTitleHigher.compareTo(secretLevelInfo.getSecretLevelItem().getTitle()) < 0) {
                    secretLevelTitleHigher = secretLevelInfo.getSecretLevelItem().getTitle();
                    continue;
                }
                secretLevelTitleHigher = secretLevelInfo.getSecretLevelItem().getTitle();
            }
        }
        return secretLevelTitleMap;
    }

    private BatchExportTask exportExcelByDimension(BatchExportInfo info, AsyncTaskMonitor asyncTaskMonitor, double numOfIndex) throws IOException {
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6279\u91cf\u5bfc\u51fa\u670d\u52a1", OperLevel.USER_OPER);
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(info.getContext().getFormSchemeKey());
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
        LogDimensionCollection logDimension = null;
        try {
            logDimension = new LogDimensionCollection();
            String value = ((DimensionValue)info.getContext().getDimensionSet().get(queryEntity.getDimensionName())).getValue();
            if (value != null && value.contains(";")) {
                String[] split = value.split(";");
                logDimension.setDw(formScheme.getDw(), split);
            } else if (value == null || value.equals("")) {
                List<IEntityRow> entityDataList = this.getEntityDataList(info.getContext().getFormSchemeKey(), info.getContext().getDimensionSet());
                if (entityDataList != null) {
                    String[] values = new String[entityDataList.size()];
                    int i = 0;
                    for (IEntityRow iEntityRow : entityDataList) {
                        values[i] = iEntityRow.getCode();
                        ++i;
                    }
                    logDimension.setDw(formScheme.getDw(), values);
                } else {
                    logDimension.setDw(formScheme.getDw(), new String[]{value});
                }
            } else {
                logDimension.setDw(formScheme.getDw(), new String[]{value});
            }
            logDimension.setPeriod(formScheme.getDateTime(), ((DimensionValue)info.getContext().getDimensionSet().get("DATATIME")).getValue());
        }
        catch (Exception e1) {
            logger.error("\u6784\u9020\u4e1a\u52a1\u65e5\u5fd7\u7ef4\u5ea6\u51fa\u9519");
        }
        logHelper.info(formScheme.getTaskKey(), logDimension, "\u6279\u91cf\u5bfc\u51fa\u5f00\u59cb", "Excel\u6279\u91cf\u5bfc\u51fa");
        ArrayList<BatchExportData> datas = new ArrayList<BatchExportData>();
        ExportHandleParam exportBeforeHandle = this.ExportBeforeHandle(info, asyncTaskMonitor);
        if (exportBeforeHandle == null) {
            return null;
        }
        BatchDimensionParam dimensionInfoBuild = exportBeforeHandle.getDimensionInfoBuild();
        exportBeforeHandle.setInfo(info);
        exportBeforeHandle.setFormKeys(exportBeforeHandle.getFormKeys() != null && !exportBeforeHandle.getFormKeys().isEmpty() ? exportBeforeHandle.getFormKeys() : info.getFormKeys());
        exportBeforeHandle.setNpContext(NpContextHolder.getContext());
        exportBeforeHandle.setDataSource(this.dataSource);
        exportBeforeHandle.setJdbcTemplate(this.jdbcTemplate);
        exportBeforeHandle.setJtableEntityService(this.jtableEntityService);
        exportBeforeHandle.setJtableParamService(this.jtableParamService);
        exportBeforeHandle.setReadWriteAccessProvider(this.readWriteAccessProvider);
        exportBeforeHandle.setReportExportService(this.reportExportService);
        exportBeforeHandle.setAsyncTaskMonitor(asyncTaskMonitor);
        exportBeforeHandle.setExportExcelNameService(this.exportExcelNameService);
        exportBeforeHandle.setiSecretLevelService(this.iSecretLevelService);
        exportBeforeHandle.setiCustomRegionsGradeService(this.iCustomRegionsGradeService);
        List<Map<String, DimensionValue>> allResultDimension = dimensionInfoBuild.getList();
        DimensionCollection dimCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)info.getContext().getDimensionSet(), (String)info.getContext().getFormSchemeKey());
        List dimCollectionList = dimCollection.getDimensionCombinations();
        HashSet<String> unitKeys = new HashSet<String>();
        EntityViewData dwEntity1 = this.jtableParamService.getDwEntity(info.getContext().getFormSchemeKey());
        for (DimensionCombination fixedDimensionValues : dimCollectionList) {
            unitKeys.add(fixedDimensionValues.getValue(dwEntity1.getDimensionName()).toString());
        }
        allResultDimension = allResultDimension.stream().filter(item -> unitKeys.contains(((DimensionValue)item.get(dwEntity1.getDimensionName())).getValue())).collect(Collectors.toList());
        int threadCount = this.getThreadCount(allResultDimension.size());
        String filePath = exportBeforeHandle.getInfo().getLocation() + BatchExportConsts.SEPARATOR;
        Vector<String> unitCodes = new Vector<String>();
        if (threadCount > 1) {
            CountDownLatch latch = new CountDownLatch(threadCount);
            exportBeforeHandle.setLatch(latch);
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
            logger.info("\u5f00\u59cb\u5bfc\u51faexcel \uff0c\u5bfc\u51fa\u5355\u4f4d \u4e2a\u6570\uff1a" + allResultDimension.size());
            logHelper.info(formScheme.getTaskKey(), logDimension, "\u6279\u91cf\u5bfc\u51fa\u5f02\u5e38", "\u5f00\u59cb\u5bfc\u51faexcel \uff0c\u5bfc\u51fa\u5355\u4f4d \u4e2a\u6570\uff1a" + allResultDimension.size());
            int j = 0;
            while (j < threadCount) {
                List<Map<String, DimensionValue>> currentDimension = this.splitDimension(allResultDimension, j, threadCount);
                int currentIndex = j++;
                ExportHandleCurrParam exportHandleCurrParam = new ExportHandleCurrParam();
                exportHandleCurrParam.setCurrentDimension(currentDimension);
                exportHandleCurrParam.setCurrentIndex(currentIndex);
                executorService.execute(new BatchExpDimThread(exportBeforeHandle, datas, exportHandleCurrParam, numOfIndex, this.dataExportService, this.dwRule, filePath, unitCodes));
            }
            try {
                latch.await();
                executorService.shutdown();
            }
            catch (InterruptedException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                logHelper.error(formScheme.getTaskKey(), logDimension, "\u6279\u91cf\u5bfc\u51fa\u5f02\u5e38", "\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage());
            }
        } else {
            ExportHandleCurrParam exportHandleCurrParam = new ExportHandleCurrParam();
            exportHandleCurrParam.setCurrentDimension(allResultDimension);
            BatchExpDimThread batchExportThread = new BatchExpDimThread(exportBeforeHandle, datas, exportHandleCurrParam, numOfIndex, this.dataExportService, this.dwRule, filePath, unitCodes);
            batchExportThread.exportExcelByDim();
        }
        logger.info("excel \u5bfc\u51fa\u5b8c\u6210");
        logHelper.info(formScheme.getTaskKey(), logDimension, "\u6279\u91cf\u5bfc\u51fa\u5b8c\u6210", "excel \u5bfc\u51fa\u5b8c\u6210");
        String editProcess = "edit_progress_info";
        BatchExportTask exportTask = new BatchExportTask(datas);
        exportTask.setUnitCodes(unitCodes);
        info.setExcelExpPath(filePath);
        return exportTask;
    }

    private ExportHandleParam ExportBeforeHandle(BatchExportInfo info, AsyncTaskMonitor asyncTaskMonitor) {
        ExportHandleParam exportHandleParam = new ExportHandleParam();
        JtableContext jtableContext = info.getContext();
        ArrayList<EntityViewData> entityList = new ArrayList();
        try {
            entityList = this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
            exportHandleParam.setEntityList(entityList);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        String dateType = periodEntityInfo.getDimensionName();
        exportHandleParam.setDateType(dateType);
        String companyKey = targetEntityInfo.getKey();
        String companyType = targetEntityInfo.getDimensionName();
        exportHandleParam.setCompanyType(companyType);
        ArrayList<String> listEntity = new ArrayList<String>();
        for (EntityViewData entityInfo : entityList) {
            listEntity.add(entityInfo.getKey());
        }
        exportHandleParam.setListEntity(listEntity);
        if (StringUtils.isEmpty((String)dateType) || StringUtils.isEmpty((String)companyType)) {
            throw new NotFoundEntityException(new String[]{StringUtils.isEmpty((String)companyType) ? "\u672a\u627e\u5230\u5355\u4f4d\u4e3b\u4f53" : "\u672a\u627e\u5230\u65f6\u671f\u4e3b\u4f53"});
        }
        Map dimensionSet = info.getContext().getDimensionSet();
        BatchDimensionParam dimensionInfoBuild = this.dimensionInfoBuild(info.getContext(), asyncTaskMonitor, false);
        if (null == dimensionInfoBuild) {
            return null;
        }
        exportHandleParam.setDimensionInfoBuild(dimensionInfoBuild);
        List<String> repeatCompanyNameList = DataEntryUtil.getRepeatCompanyName(companyKey, info.getContext());
        exportHandleParam.setRepeatCompanyNameList(repeatCompanyNameList);
        String editProcess = "edit_progress_info";
        asyncTaskMonitor.progressAndMessage(0.05, editProcess);
        ArrayList<String> formKeys = new ArrayList<String>(info.getFormKeys());
        ArrayList<String> noRepeatformKeys = new ArrayList<String>();
        ArrayList formDefineList = new ArrayList();
        List formGroupDefineList = this.runtimeViewController.getAllFormGroupsInFormScheme(info.getContext().getFormSchemeKey());
        if (info.getContext().getFormGroupKey() != null && !info.getContext().getFormGroupKey().equals("") && info.getFormKeys().isEmpty()) {
            Object formDefineList2 = new ArrayList();
            try {
                formDefineList2 = this.runtimeViewController.getAllFormsInGroup(info.getContext().getFormGroupKey());
            }
            catch (Exception e) {
                logger.error("\u62a5\u8868\u67e5\u8be2\u9519\u8bef\uff01");
            }
            if (formDefineList2 != null && !formDefineList2.isEmpty()) {
                StringBuilder formKeyStr = new StringBuilder();
                Iterator iterator = formDefineList2.iterator();
                while (iterator.hasNext()) {
                    FormDefine formDefine = (FormDefine)iterator.next();
                    if (!noRepeatformKeys.contains(formDefine.getKey())) {
                        if (formDefine.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) continue;
                        noRepeatformKeys.add(formDefine.getKey());
                    }
                    formKeyStr.append(formDefine.getKey()).append(";");
                }
                formDefineList.addAll(formDefineList2);
                info.getContext().setFormKey(formKeyStr.toString());
            }
            for (String noRepeatformKey : noRepeatformKeys) {
                if (formKeys.contains(noRepeatformKey)) continue;
                formKeys.add(noRepeatformKey);
            }
        } else {
            for (FormGroupDefine formGroupDefine : formGroupDefineList) {
                try {
                    List formDefineList2 = this.runtimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
                    formDefineList.addAll(formDefineList2);
                }
                catch (Exception e) {
                    logger.error("\u62a5\u8868\u67e5\u8be2\u9519\u8bef\uff01");
                }
            }
            for (FormDefine formDefine : formDefineList) {
                if (noRepeatformKeys.contains(formDefine.getKey()) || !formKeys.contains(formDefine.getKey()) || formDefine.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) continue;
                noRepeatformKeys.add(formDefine.getKey());
            }
            for (String noRepeatformKey : noRepeatformKeys) {
                if (formKeys.contains(noRepeatformKey)) continue;
                formKeys.add(noRepeatformKey);
            }
        }
        exportHandleParam.setFormKeys(formKeys);
        ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, formKeys, Consts.FormAccessLevel.FORM_READ);
        ReadWriteAccessCacheManager readWriteAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
        readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
        exportHandleParam.setReadWriteAccessCacheManager(readWriteAccessCacheManager);
        String dateDir = "";
        if (StringUtils.isNotEmpty((String)dimensionInfoBuild.getDate())) {
            String date = this.getPeriodTitle(jtableContext.getFormSchemeKey(), dimensionInfoBuild.getDate());
            dateDir = date + BatchExportConsts.SEPARATOR;
        }
        ArrayList<String> companyList = new ArrayList<String>();
        for (Map<String, DimensionValue> mapNoUnit : dimensionInfoBuild.getList()) {
            DimensionValue dimensionValue = mapNoUnit.get(companyType);
            if (companyList.contains(dimensionValue.getValue())) continue;
            companyList.add(dimensionValue.getValue());
        }
        exportHandleParam.setDateDir(dateDir);
        exportHandleParam.setCompanyList(companyList);
        return exportHandleParam;
    }

    private BatchExportTask exportExcelByForm(BatchExportInfo info, AsyncTaskMonitor asyncTaskMonitor, double numOfIndex) throws IOException {
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6279\u91cf\u5bfc\u51fa\u670d\u52a1", OperLevel.USER_OPER);
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(info.getContext().getFormSchemeKey());
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
        LogDimensionCollection logDimension = null;
        if (((DimensionValue)info.getContext().getDimensionSet().get(queryEntity.getDimensionName())).getValue() != null) {
            logDimension = new LogDimensionCollection();
            String value = ((DimensionValue)info.getContext().getDimensionSet().get(queryEntity.getDimensionName())).getValue();
            if (value != null && value.contains(";")) {
                String[] split = value.split(";");
                logDimension.setDw(formScheme.getDw(), split);
            } else {
                logDimension.setDw(formScheme.getDw(), new String[]{value});
            }
            logDimension.setPeriod(formScheme.getDateTime(), ((DimensionValue)info.getContext().getDimensionSet().get("DATATIME")).getValue());
        }
        logHelper.info(formScheme.getTaskKey(), logDimension, MODULEEXCEL, "Excel\u6279\u91cf\u5bfc\u51fa");
        ExportHandleParam exportBeforeHandle = this.ExportBeforeHandle(info, asyncTaskMonitor);
        if (exportBeforeHandle == null) {
            return null;
        }
        ArrayList<BatchExportData> datas = new ArrayList<BatchExportData>();
        exportBeforeHandle.setInfo(info);
        exportBeforeHandle.setJtableEntityService(this.jtableEntityService);
        exportBeforeHandle.setJtableParamService(this.jtableParamService);
        exportBeforeHandle.setReadWriteAccessProvider(this.readWriteAccessProvider);
        exportBeforeHandle.setDimensionValueProvider(this.dimensionValueProvider);
        exportBeforeHandle.setReportExportService(this.reportExportService);
        exportBeforeHandle.setAsyncTaskMonitor(asyncTaskMonitor);
        exportBeforeHandle.setExportExcelNameService(this.exportExcelNameService);
        exportBeforeHandle.setiSecretLevelService(this.iSecretLevelService);
        exportBeforeHandle.setiCustomRegionsGradeService(this.iCustomRegionsGradeService);
        List<Map<String, Object>> allResultDimension = exportBeforeHandle.getDimensionInfoBuild().getList();
        DimensionCollection dimCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)info.getContext().getDimensionSet(), (String)info.getContext().getFormSchemeKey());
        List dimCollectionList = dimCollection.getDimensionCombinations();
        HashSet<String> unitKeys = new HashSet<String>();
        EntityViewData dwEntity1 = this.jtableParamService.getDwEntity(info.getContext().getFormSchemeKey());
        for (DimensionCombination fixedDimensionValues : dimCollectionList) {
            unitKeys.add(fixedDimensionValues.getValue(dwEntity1.getDimensionName()).toString());
        }
        exportBeforeHandle.setCompanyList(exportBeforeHandle.getCompanyList().stream().filter(item -> unitKeys.contains(item)).collect(Collectors.toList()));
        allResultDimension = allResultDimension.stream().filter(item -> unitKeys.contains(((DimensionValue)item.get(dwEntity1.getDimensionName())).getValue())).collect(Collectors.toList());
        List<String> formKeys = exportBeforeHandle.getFormKeys();
        exportBeforeHandle.setNpContext(NpContextHolder.getContext());
        JtableContext jtableContext = info.getContext();
        ArrayList<String> allFormKeys = new ArrayList<String>();
        if (formKeys.isEmpty()) {
            allFormKeys.addAll(this.readWriteAccessProvider.getAllFormKeys(jtableContext.getFormSchemeKey()));
        } else {
            allFormKeys.addAll(formKeys);
        }
        ArrayList<String> allFormKeysAfterFormat = new ArrayList<String>();
        for (int i = 0; i < allFormKeys.size(); ++i) {
            FormDefine formDefine = this.runtimeViewController.queryFormById((String)allFormKeys.get(i));
            if (formDefine.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) continue;
            allFormKeysAfterFormat.add((String)allFormKeys.get(i));
        }
        int threadCount = this.getThreadCount(allFormKeysAfterFormat.size());
        double allSize = (double)allResultDimension.size() * (double)allFormKeysAfterFormat.size();
        String filePath = exportBeforeHandle.getInfo().getLocation() + BatchExportConsts.SEPARATOR;
        if (threadCount > 1) {
            CountDownLatch latch = new CountDownLatch(threadCount);
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
            exportBeforeHandle.setLatch(latch);
            for (int j = 0; j < threadCount; ++j) {
                ExportHandleCurrParam exportHandleCurrParam = new ExportHandleCurrParam();
                List<String> splitAllForms = this.splitAllForms(allFormKeysAfterFormat, j, threadCount);
                exportHandleCurrParam.setSplitAllForms(splitAllForms);
                logger.info("\u7b2c{}\u4e2a\u7ebf\u7a0b\u5bfc\u51fa\u7684\u62a5\u8868\u4e2a\u6570\u4e3a\uff1a{}", (Object)j, (Object)splitAllForms.size());
                executorService.execute(new BatchExpFormThread(exportBeforeHandle, allSize, datas, exportHandleCurrParam, numOfIndex, this.dataExportService, this.formRule, filePath));
            }
            try {
                latch.await();
                executorService.shutdown();
            }
            catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                logHelper.error(formScheme.getTaskKey(), logDimension, MODULEEXCEL, "Excel\u6279\u91cf\u5bfc\u51fa\u5f02\u5e38\uff1a" + e.getMessage());
            }
        } else {
            ExportHandleCurrParam exportHandleCurrParam = new ExportHandleCurrParam();
            exportHandleCurrParam.setSplitAllForms(allFormKeysAfterFormat);
            BatchExpFormThread batchExportForm = new BatchExpFormThread(exportBeforeHandle, allSize, datas, exportHandleCurrParam, numOfIndex, this.dataExportService, this.formRule, filePath);
            batchExportForm.ExportExcelByForm();
        }
        logger.info("excel \u5bfc\u51fa\u5b8c\u6210");
        logHelper.info(formScheme.getTaskKey(), logDimension, MODULEEXCEL, "excel \u5bfc\u51fa\u5b8c\u6210");
        String editProcess = "edit_progress_info";
        asyncTaskMonitor.progressAndMessage(0.95 * numOfIndex * numOfIndex, editProcess);
        BatchExportTask exportTask = new BatchExportTask(datas);
        info.setExcelExpPath(filePath);
        return exportTask;
    }

    public BatchExportTask exportToDatas(BatchExportInfo info, AsyncTaskMonitor asyncTaskMonitor, double numOfIndex, double coefficient) throws IOException {
        Object monitor = null;
        monitor = numOfIndex == 1.0 ? asyncTaskMonitor : new BatchExportProgressMonitor(asyncTaskMonitor, coefficient, numOfIndex);
        String threadEnable = this.nvwaSystemOptionService.get("nr-data-entry-group", "SELECT_BATCH_EXPORT_EXCEL_THREAD");
        if (threadEnable.equals("1") && this.ThreadSum.intValue() > 5) {
            throw new IOException("\u5e76\u53d1\u6570\u5df2\u7ecf\u8fbe\u5230\u6700\u5927");
        }
        return info.isCreateFileByReport() ? this.exportExcelByForm(info, (AsyncTaskMonitor)monitor, numOfIndex) : this.exportExcelByDimension(info, (AsyncTaskMonitor)monitor, numOfIndex);
    }

    protected void closeConnection(Connection connection) {
        if (connection != null) {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        }
    }

    protected Connection getConnection() {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
    }

    private List<Map<String, DimensionValue>> splitDimension(List<Map<String, DimensionValue>> allResultDimension, int j, int threadCount) {
        int remainCount = 0;
        ArrayList<Map<String, DimensionValue>> currentDimension = new ArrayList<Map<String, DimensionValue>>();
        int group = allResultDimension.size() / threadCount;
        int e = allResultDimension.size() % threadCount;
        if (j == threadCount - 1) {
            remainCount = e;
        }
        if (group == 0) {
            return new ArrayList<Map<String, DimensionValue>>();
        }
        for (int k = j * group; k < group * (j + 1) + remainCount && allResultDimension.size() > k; ++k) {
            currentDimension.add(allResultDimension.get(k));
        }
        return currentDimension;
    }

    private List<String> splitAllForms(List<String> allFroms, int j, int threadCount) {
        int remainCount = 0;
        ArrayList<String> currentDimension = new ArrayList<String>();
        int group = allFroms.size() / threadCount;
        int e = allFroms.size() % threadCount;
        if (j == threadCount - 1) {
            remainCount = e;
        }
        if (group == 0) {
            return new ArrayList<String>();
        }
        for (int k = j * group; k < group * (j + 1) + remainCount && allFroms.size() > k; ++k) {
            currentDimension.add(allFroms.get(k));
        }
        return currentDimension;
    }

    private BatchDimensionParam dimensionInfoBuild(JtableContext jtableContext, AsyncTaskMonitor asyncTaskMonitor, boolean isPeriodList) {
        return this.dimensionInfoBuild(jtableContext, asyncTaskMonitor, isPeriodList, false);
    }

    private BatchDimensionParam dimensionInfoBuild(JtableContext jtableContext, AsyncTaskMonitor asyncTaskMonitor, boolean isPeriodList, boolean isJio) {
        String buildExportDimension = "build_export_dimension_info";
        asyncTaskMonitor.progressAndMessage(0.02, buildExportDimension);
        List<Map<String, DimensionValue>> resultDimension = null;
        List entityList = this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
        HashMap<String, DimensionValue> orderDimension = new HashMap<String, DimensionValue>();
        for (Map.Entry entityViewData : jtableContext.getDimensionSet().entrySet()) {
            DimensionValue one = new DimensionValue();
            one.setName(((DimensionValue)entityViewData.getValue()).getName());
            one.setValue(((DimensionValue)entityViewData.getValue()).getValue());
            orderDimension.put((String)entityViewData.getKey(), one);
        }
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        String dateType = periodEntityInfo.getDimensionName();
        resultDimension = this.getResultDimension(entityList, orderDimension, jtableContext.getFormSchemeKey());
        if (!isJio && this.iNvwaSystemOptionService.get("nr-data-entry-export", "BATCH_EXPORT_MAX_DW_NUM") != null && StringUtils.isNotEmpty((String)this.iNvwaSystemOptionService.get("nr-data-entry-export", "BATCH_EXPORT_MAX_DW_NUM"))) {
            Iterator<Map<String, DimensionValue>> unitSet = new HashSet();
            Integer maxNum = Integer.valueOf(this.iNvwaSystemOptionService.get("nr-data-entry-export", "BATCH_EXPORT_MAX_DW_NUM"));
            EntityViewData entityViewData = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
            for (Map map : resultDimension) {
                unitSet.add((Map<String, DimensionValue>)((Object)((DimensionValue)map.get(entityViewData.getDimensionName())).getValue()));
            }
            if (unitSet.size() > maxNum) {
                String noDimension = "batch_export_max_dw_num";
                asyncTaskMonitor.error(noDimension + maxNum, null, "");
                return null;
            }
        }
        if (jtableContext.getDimensionSet().containsKey("MD_CURRENCY") && (((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue().equals("PROVIDER_BASECURRENCY") || ((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue().equals("PROVIDER_PBASECURRENCY"))) {
            for (Map<String, DimensionValue> dimensionValueMap : resultDimension) {
                JtableContext jtableContext2 = new JtableContext(jtableContext);
                jtableContext2.setDimensionSet(dimensionValueMap);
                List<String> currencyInfo = this.currencyService.getCurrencyInfo(jtableContext2, ((DimensionValue)jtableContext.getDimensionSet().get("MD_CURRENCY")).getValue());
                if (currencyInfo == null || currencyInfo.size() <= 0) continue;
                ((DimensionValue)dimensionValueMap.get("MD_CURRENCY")).setValue(currencyInfo.get(0));
            }
        }
        if (jtableContext.getDimensionSet().containsKey("ADJUST")) {
            for (Map<String, DimensionValue> dimensionValueMap : resultDimension) {
                if (dimensionValueMap.keySet().contains("ADJUST")) continue;
                dimensionValueMap.put("ADJUST", new DimensionValue((DimensionValue)jtableContext.getDimensionSet().get("ADJUST")));
            }
        }
        if (null == resultDimension || resultDimension.size() == 0) {
            if (!isPeriodList) {
                String noDimension = "no_dimension";
                asyncTaskMonitor.finish(noDimension, (Object)"");
            }
            return null;
        }
        if (resultDimension.size() > 0 && orderDimension.size() > resultDimension.get(0).size()) {
            Set entrySet = orderDimension.entrySet();
            for (Map.Entry entry : entrySet) {
                for (Map map : resultDimension) {
                    if (map.containsKey(entry.getKey())) continue;
                    DimensionValue value = (DimensionValue)entry.getValue();
                    DimensionValue newDimensionValue = new DimensionValue();
                    newDimensionValue.setName(value.getName());
                    newDimensionValue.setValue(value.getValue());
                    map.put(entry.getKey(), newDimensionValue);
                }
            }
        }
        Map<String, DimensionValue> sortEntity = resultDimension.get(0);
        BatchDimensionParam batchDimensionParam = new BatchDimensionParam();
        ArrayList<String> arrayList = new ArrayList<String>();
        block6: for (Map.Entry entry : sortEntity.entrySet()) {
            for (int i = 0; i < entityList.size(); ++i) {
                if (dateType.equals(entry.getKey())) {
                    batchDimensionParam.setDate(((DimensionValue)entry.getValue()).getValue());
                }
                if (!((String)entry.getKey()).equals(((EntityViewData)entityList.get(i)).getDimensionName())) continue;
                arrayList.add(((EntityViewData)entityList.get(i)).getKey());
                continue block6;
            }
        }
        batchDimensionParam.setEntitys(arrayList);
        batchDimensionParam.setList(resultDimension);
        return batchDimensionParam;
    }

    private List<Map<String, DimensionValue>> getResultDimension(List<EntityViewData> entityList, Map<String, DimensionValue> orderDimension, String formSchemeKey) {
        ArrayList<String> errorDimensionSet = new ArrayList<String>();
        List<Map<String, DimensionValue>> splitDimensionValueList = this.dimensionValueProvider.splitDimensionValueList(orderDimension, formSchemeKey, errorDimensionSet, false);
        return splitDimensionValueList;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public void batchPrint(BatchPrintInfo info, PrinterDevice printerDevice, AsyncTaskMonitor asyncTaskMonitor) {
        if (asyncTaskMonitor.isCancel()) {
            if (!asyncTaskMonitor.isFinish()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
            }
            LogHelper.info((String)"\u6279\u91cf\u6253\u5370", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
            return;
        }
        BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
        try {
            JtableContext jtableContext = info.getContext();
            BatchDimensionParam dimensionInfoBuild = this.dimensionInfoBuild(info.getContext(), asyncTaskMonitor, false);
            if (null == dimensionInfoBuild) {
                return;
            }
            List<Map<String, DimensionValue>> resultDimension = dimensionInfoBuild.getList();
            double firstLevel = 0.05;
            asyncTaskMonitor.progressAndMessage(firstLevel, "");
            List<String> formKeys = info.getFormKeys();
            ArrayList<String> formKeys2 = new ArrayList<String>();
            for (int i = 0; i < formKeys.size(); ++i) {
                if (formKeys2.contains(formKeys.get(i))) continue;
                formKeys2.add(formKeys.get(i));
            }
            formKeys = formKeys2;
            if (formKeys.isEmpty()) {
                formKeys = this.readWriteAccessProvider.getAllFormKeys(jtableContext.getFormSchemeKey());
            }
            ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, formKeys, Consts.FormAccessLevel.FORM_DATA_READ);
            ReadWriteAccessCacheManager readWriteAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
            readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
            double allSize = 0.0;
            allSize = (double)resultDimension.size() * (double)formKeys.size();
            String printSchemKey = info.getPrintSchemeKey();
            if (null == printSchemKey) {
                List printSchemes = null;
                try {
                    printSchemes = this.printController.getAllPrintSchemeByFormScheme(info.getContext().getFormSchemeKey());
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                printSchemKey = ((DesignPrintTemplateSchemeDefine)printSchemes.get(0)).getKey();
            }
            PaginateContext paginateContext = new PaginateContext("REPORT_PRINT_NATURE", GraphicalFactoryManager.getPaginateFactory((String)"REPORT_PRINT_NATURE"));
            SimplePaintInteractor simplePaintInteractor = new SimplePaintInteractor();
            SimpleProcessMonitor monitor = new SimpleProcessMonitor(System.err, System.err, System.err);
            ArrayList<IContentStream> contentList = new ArrayList<IContentStream>();
            String paperName = "A4";
            int orientation = 512;
            boolean allIsEmptyTable = true;
            IDataAccessService dataAccessService = this.iDataAccessServiceProvider.getDataAccessService(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey());
            for (Map<String, DimensionValue> mapNoUnit : dimensionInfoBuild.getList()) {
                DimensionCollection dimensionCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection(mapNoUnit, (String)jtableContext.getFormSchemeKey());
                IBatchAccessResult visitAccess = dataAccessService.getVisitAccess(dimensionCollection, formKeys);
                DefaultPageNumberGenerateStrategy pageNumberGenerateStrategy = new DefaultPageNumberGenerateStrategy();
                allSize = (double)resultDimension.size() * (double)formKeys.size();
                for (int i = 0; i < formKeys.size(); ++i) {
                    try {
                        ITemplatePage[] pages;
                        if (asyncTaskMonitor.isCancel()) {
                            IContentStream[] streams = new IContentStream[contentList.size()];
                            if (contentList.size() > 0) {
                                printerDevice.setBatchPrint(false);
                                this.printPaper(printerDevice, contentList, paperName, orientation, simplePaintInteractor, paginateContext, streams, monitor);
                            }
                            if (!asyncTaskMonitor.isFinish()) {
                                asyncTaskMonitor.canceled("stop_execute", (Object)"stop_execute");
                            }
                            LogHelper.info((String)"\u6279\u91cf\u6253\u5370", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                            return;
                        }
                        SimpleProcessMonitor monitorForm = new SimpleProcessMonitor(System.err, System.err, System.err);
                        IAccessResult access = visitAccess.getAccess((DimensionCombination)dimensionCollection.getDimensionCombinations().get(0), formKeys.get(i));
                        if (!access.haveAccess()) continue;
                        PrintTemplateDefine printTemPlate = null;
                        try {
                            printTemPlate = this.printRunTimeController.queryPrintTemplateDefineBySchemeAndForm(printSchemKey, formKeys.get(i));
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                        ITemplateDocument documentTemplateObject = null;
                        if (null == printTemPlate) {
                            try {
                                documentTemplateObject = this.printTemplateService.loadNewRuntimeTempDoc(printSchemKey, formKeys.get(i));
                            }
                            catch (Exception e) {
                                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            }
                        } else {
                            ITemplateObjectFactory factory = GraphicalFactoryManager.getTemplateObjectFactory((String)"REPORT_PRINT_NATURE");
                            documentTemplateObject = (ITemplateDocument)SerializeUtil.deserialize((String)new String(printTemPlate.getTemplateData()), (ITemplateObjectFactory)factory);
                        }
                        if (null == (pages = documentTemplateObject.getPages()) || pages.length == 0 || pages.length > 1) {
                            try {
                                documentTemplateObject = this.iDesignTimePrintController.initTemplateDocument(printSchemKey, formKeys.get(i));
                                pages = documentTemplateObject.getPages();
                            }
                            catch (Exception e) {
                                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            }
                        }
                        ITemplatePage page = pages[0];
                        Paper paper = page.getPaper();
                        String name = paper.getName();
                        Iterator iterator = page.iterator();
                        boolean noMoneyInfo = true;
                        while (iterator.hasNext()) {
                            IGraphicalElement iGraphicalElement = (IGraphicalElement)iterator.next();
                            if (!(iGraphicalElement instanceof TextTemplateObject)) continue;
                            TextTemplateObject textTemplateObject = (TextTemplateObject)iGraphicalElement;
                            String content = ((TextTemplateObject)iGraphicalElement).getContent();
                            if (!content.contains("RPTMONEYUNIT")) continue;
                            noMoneyInfo = false;
                        }
                        JtableContext jtableContextTemp = new JtableContext();
                        jtableContextTemp.setDimensionSet(mapNoUnit);
                        jtableContextTemp.setFormKey(formKeys.get(i));
                        jtableContextTemp.setFormSchemeKey(jtableContext.getFormSchemeKey());
                        jtableContextTemp.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
                        jtableContextTemp.setTaskKey(jtableContext.getTaskKey());
                        jtableContextTemp.setMeasureMap(jtableContext.getMeasureMap());
                        jtableContextTemp.setMillennialDecimal(jtableContext.getMillennialDecimal());
                        if (jtableContext.getDecimal() != null && !jtableContext.getDecimal().equals("")) {
                            jtableContextTemp.setDecimal(jtableContext.getDecimal());
                        }
                        PrintParam pdf = new PrintParam();
                        pdf.setContext(jtableContextTemp);
                        pdf.setPrintPageNum(true);
                        pdf.setDecimal(jtableContext.getDecimal());
                        pdf.setExportEmptyTable(info.isPrintEmptyTable());
                        pdf.setExportZero(info.isExportZero());
                        PrintIPaginateInteractor interactor = new PrintIPaginateInteractor(pdf);
                        interactor.setPageNumberGenerateStrategy((IPageNumberGenerateStrategy)pageNumberGenerateStrategy);
                        int paperOrientation = page.getOrientation();
                        documentTemplateObject.setNature("REPORT_PRINT_NATURE");
                        IContentStream[] streams = ProcessUtil.getPaginateContentStreams((String)"REPORT_PRINT_NATURE", (ITemplateDocument)documentTemplateObject, (IPaginateInteractor)interactor, (IProcessMonitor)monitorForm);
                        if (paperName.equals(name) && orientation == paperOrientation) {
                            if (null != streams && streams.length > 0) {
                                allIsEmptyTable = false;
                                if (contentList.size() >= 5) {
                                    this.printPaper(printerDevice, contentList, paperName, orientation, simplePaintInteractor, paginateContext, streams, monitor);
                                } else {
                                    contentList.addAll(Arrays.asList(streams));
                                }
                            }
                        } else {
                            this.printPaper(printerDevice, contentList, paperName, orientation, simplePaintInteractor, paginateContext, streams, monitor);
                            paperName = name;
                            orientation = paperOrientation;
                        }
                        double percent = (double)(i + 1) / allSize * 0.8;
                        asyncTaskMonitor.progressAndMessage(percent, "");
                        continue;
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        String message = e.getMessage();
                        if (null == message || !message.startsWith("failPrinting")) continue;
                        throw new RuntimeException("failPrinting \u591a\u6b21\u5c1d\u8bd5\u5f00\u542f\u4e0b\u6b21\u6253\u5370\u5931\u8d25");
                    }
                }
            }
            IContentStream[] streams = new IContentStream[contentList.size()];
            if (contentList.size() > 0) {
                printerDevice.setBatchPrint(false);
                this.printPaper(printerDevice, contentList, paperName, orientation, simplePaintInteractor, paginateContext, streams, monitor);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            String batchPrintFail = "batch_print_fail_info";
            asyncTaskMonitor.error(batchPrintFail, (Throwable)e);
            return;
        }
        String objectToJson = JsonUtil.objectToJson((Object)batchReturnInfo);
        String batchPrintSuccess = "batch_print_success_info";
        if (!asyncTaskMonitor.isFinish()) {
            asyncTaskMonitor.finish(batchPrintSuccess, (Object)objectToJson);
        }
    }

    private void printPaper(PrinterDevice printerDevice, List<IContentStream> contentList, String paperName, int orientation, SimplePaintInteractor simplePaintInteractor, PaginateContext paginateContext, IContentStream[] streams, SimpleProcessMonitor monitor) {
        Paper[] supportedPapers;
        IContentStream[] tempStreams = new IContentStream[contentList.size()];
        for (int index = 0; index < contentList.size(); ++index) {
            tempStreams[index] = contentList.get(index);
        }
        PrinterInfo printerInfo = printerDevice.getPrinterInfo();
        for (Paper parper : supportedPapers = printerInfo.getSupportedPapers()) {
            if (!paperName.equals(parper.getName())) continue;
            printerDevice.setPaper(parper);
            break;
        }
        printerDevice.setOrientation(orientation);
        PrintProcessUtil.print((IContentStream[])tempStreams, (IPaintInteractor)simplePaintInteractor, (IPrintDevice)printerDevice, (IPaginateContext)paginateContext, (IProcessMonitor)monitor);
        contentList.clear();
        contentList.addAll(Arrays.asList(streams));
        boolean printIng = true;
        int maxCount = 1800;
        int count = 0;
        while (printIng) {
            try {
                Thread.sleep(1000L);
                if (++count >= maxCount) {
                    throw new RuntimeException("failPrinting \u591a\u6b21\u5c1d\u8bd5\u5f00\u542f\u4e0b\u6b21\u6253\u5370\u5931\u8d25");
                }
            }
            catch (InterruptedException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            PrinterDevice printerDeviceTemp = AsyncWorkContainnerUtil.getPrinterDevice((String)printerDevice.getId());
            if (null != printerDeviceTemp) {
                printIng = printerDeviceTemp.isPrintIng();
                continue;
            }
            printIng = false;
        }
    }

    @Override
    public ReturnInfo batchPrintTask() {
        PrinterDevice device = new PrinterDevice(1, (Object)UUID.randomUUID().toString(), true);
        device.setBatchPrint(true);
        AsyncWorkContainnerUtil.regist((PrinterDevice)device);
        device.getAvailablePrinterNames(new ICallBack(){

            public void performed(Object[] arguments) {
            }
        });
        device.getDefaultPrinter(new ICallBack(){

            public void performed(Object[] arguments) {
            }
        });
        String printId = device.getId();
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage(printId);
        return returnInfo;
    }

    @Override
    public byte[] printSumCover(PrintSumCover param) {
        JtableContext jtableContext = param.getContext();
        ByteArrayOutputStream printSumCover = new ByteArrayOutputStream();
        ByteArrayOutputStream printFormUnit = new ByteArrayOutputStream();
        ITemplateDocument documentTemplateObject = null;
        double height = 0.0;
        double width = 0.0;
        Paper paper = null;
        int orientation = 0;
        double[] margins = null;
        try {
            documentTemplateObject = this.printSchemeService.loadSchemeTemplate(param.getPrintSchemeKey());
            documentTemplateObject.setNature("REPORT_PRINT_NATURE");
            ITemplatePage[] pages = documentTemplateObject.getPages();
            ITemplatePage page = pages[0];
            margins = page.getMargins();
            paper = page.getPaper();
            orientation = page.getOrientation();
            if (orientation == 0) {
                height = paper.getHeight();
                width = paper.getWidth();
            } else {
                width = paper.getHeight();
                height = paper.getWidth();
            }
            PrintParam printParam = new PrintParam();
            printParam.setContext(param.getContext());
            printParam.setPrintPageNum(true);
            PDFPrintUtil2.printPDF(documentTemplateObject, (IPaginateInteractor)new PrintIPaginateInteractor(printParam), (IPaintInteractor)new SimplePaintInteractor(), printSumCover, (IProcessMonitor)new SimpleProcessMonitor(System.out, System.err, System.out));
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        ArrayList<byte[]> dataLists = null;
        try {
            Grid2Data grid2Data = new Grid2Data();
            grid2Data.insertColumns(0, 4);
            grid2Data.insertRows(0, 3);
            GridCellData reportCell = grid2Data.getGridCellData(1, 1);
            reportCell.setEditText("\u6c47     \u603b     \u62a5     \u8868     \u540d     \u79f0");
            reportCell.setShowText("\u6c47     \u603b     \u62a5     \u8868     \u540d     \u79f0");
            reportCell.setFontStyle(2);
            reportCell.setHorzAlign(3);
            GridCellData unitCell = grid2Data.getGridCellData(3, 1);
            unitCell.setEditText("\u5355           \u4f4d           \u540d           \u79f0");
            unitCell.setShowText("\u5355           \u4f4d           \u540d           \u79f0");
            unitCell.setFontStyle(2);
            unitCell.setHorzAlign(3);
            ArrayList<String> formCodeAndNames = new ArrayList<String>();
            ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(jtableContext, null, Consts.FormAccessLevel.FORM_DATA_READ);
            ReadWriteAccessCacheManager readWriteAccessCacheManager = (ReadWriteAccessCacheManager)BeanUtil.getBean(ReadWriteAccessCacheManager.class);
            readWriteAccessCacheManager.initCache(readWriteAccessCacheParams);
            FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(jtableContext, Consts.FormAccessLevel.FORM_DATA_READ);
            FormReadWriteAccessData accessForms = this.readWriteAccessProvider.getAccessForms(formReadWriteAccessData, readWriteAccessCacheManager);
            List<String> formKeys = accessForms.getFormKeys();
            for (String formKey : formKeys) {
                FormData report = this.jtableParamService.getReport(formKey, null);
                if (!report.isDataSum() && !report.getFormType().equals(FormType.FORM_TYPE_NEWFMDM.name()) && !report.getFormType().equals(FormType.FORM_TYPE_FMDM.name())) continue;
                formCodeAndNames.add("[" + report.getCode() + "] " + report.getTitle());
            }
            List entityList = this.jtableParamService.getEntityList(param.getContext().getFormSchemeKey());
            EntityViewData unitEntity = null;
            for (EntityViewData entityViewData : entityList) {
                if (!entityViewData.isMasterEntity()) continue;
                unitEntity = entityViewData;
            }
            ArrayList<String> unitNames = new ArrayList<String>();
            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
            entityQueryInfo.setContext(param.getContext());
            if (null != unitEntity) {
                entityQueryInfo.setEntityViewKey(unitEntity.getKey());
            }
            entityQueryInfo.setParentKey(((DimensionValue)param.getContext().getDimensionSet().get(unitEntity.getDimensionName())).getValue());
            entityQueryInfo.setAllChildren(false);
            EntityReturnInfo entityReturnInfo = this.jtableEntityService.queryEntityData(entityQueryInfo);
            List entitys = entityReturnInfo.getEntitys();
            if (null != entitys && !entitys.isEmpty()) {
                for (EntityData unitData : entitys) {
                    String title = unitData.getTitle();
                    unitNames.add(title);
                }
            }
            grid2Data.insertRows(2, formCodeAndNames.size() > unitNames.size() ? formCodeAndNames.size() - 1 : unitNames.size() - 1, 0);
            this.insertSumCoverRow(formCodeAndNames, grid2Data, 1);
            this.insertSumCoverRow(unitNames, grid2Data, 3);
            int oneWidthPixel = new Double(XG.DEFAULT_LENGTH_UNIT.toPixel(width / 18.0)).intValue();
            int rowCount = grid2Data.getRowCount();
            int columnCount = grid2Data.getColumnCount();
            grid2Data.setColumnWidth(1, 5 * oneWidthPixel);
            grid2Data.setColumnWidth(2, 2 * oneWidthPixel);
            grid2Data.setColumnWidth(3, 8 * oneWidthPixel);
            double reportWidth = XG.DEFAULT_LENGTH_UNIT.fromPixel((double)(15 * oneWidthPixel)) / 2.0;
            for (int col = 0; col < columnCount; ++col) {
                for (int row = 0; row < rowCount; ++row) {
                    GridCellData gridCellData = grid2Data.getGridCellData(col, row);
                    gridCellData.setRightBorderStyle(GridEnums.GridBorderStyle.SOLID.getStyle());
                    gridCellData.setBottomBorderStyle(GridEnums.GridBorderStyle.SOLID.getStyle());
                    gridCellData.setFitFontSize(true);
                }
            }
            grid2Data.mergeCells(2, 0, 2, rowCount - 1);
            ITemplateObjectFactory templateObjectFactory = DocumentBuildUtil.getTemplateObjectFactory();
            DocumentTemplateObject documentObject = (DocumentTemplateObject)templateObjectFactory.create("document");
            documentObject.setNature("REPORT_PRINT_NATURE");
            PageTemplateObject page = (PageTemplateObject)templateObjectFactory.create("page");
            documentObject.add((ITemplatePage)page);
            page.setID("\u6c47\u603b\u5c01\u9762_" + String.valueOf(System.currentTimeMillis()));
            page.setPaper(paper);
            page.setOrientation(orientation);
            page.setMargins(margins);
            ReportTemplateObject reportElement = (ReportTemplateObject)templateObjectFactory.create("element_report");
            reportElement.setID("\u6c47\u603b\u5c01\u9762\u6253\u5370");
            reportElement.setX(width / 2.0 - reportWidth);
            reportElement.setY(margins[0] + 10.0);
            reportElement.setWidth(width - (margins[2] + margins[3]));
            reportElement.setHeight(height - (margins[0] + margins[1] + 10.0 + 10.0));
            reportElement.setReportGuid("\u6c47\u603b\u5c01\u9762\u6253\u5370");
            reportElement.setReportTitle("\u6c47\u603b\u5c01\u9762\u6253\u5370");
            GridData sourceGridData = PrintUtil.grid2DataToGridData((Grid2Data)grid2Data, null);
            reportElement.setGridData(sourceGridData);
            TableLineConfig lineConfig = new TableLineConfig();
            lineConfig.setInsideThickness(0.1);
            lineConfig.setOutsideThickness(0.1);
            reportElement.setLineConfig(lineConfig);
            reportElement.getResizeConfig().setHorizonResizeType(4);
            reportElement.getResizeConfig().setVerticalResizeType(4);
            reportElement.getResizeConfig().setHorizonScaleLocked(false);
            reportElement.getPaginateConfig().setRowPaginateType(0);
            reportElement.getPaginateConfig().setColPaginateType(0);
            page.add((ITemplateElement)reportElement);
            WordLabelTemplateObject labelElement = (WordLabelTemplateObject)templateObjectFactory.create("element_wordLabel");
            labelElement.setID(UUIDUtils.getKey());
            labelElement.setX(width / 2.0 - 40.0);
            labelElement.setY(height - 10.0 - margins[1]);
            labelElement.setWidth(80.0);
            labelElement.setHeight(8.0);
            labelElement.setContent("\u7b2c {#PageNumber} \u9875");
            Font font = new Font();
            font.setSize(12);
            labelElement.setCharSet("UTF-16");
            labelElement.setFont(FontConvertUtil.getDraw2ndFont((Font)font));
            labelElement.setHorizonAlignment(0x1000000);
            labelElement.setDrawScope(0);
            page.add((ITemplateElement)labelElement);
            PrintParam printParam = new PrintParam();
            printParam.setContext(param.getContext());
            printParam.setPrintPageNum(true);
            PrintIPaginateInteractor printIPaginateInteractor = new PrintIPaginateInteractor(printParam);
            PageNumberGenerateStrategyImpl pageNumberGenerateStrategy = new PageNumberGenerateStrategyImpl(1, 20);
            printIPaginateInteractor.setPageNumberGenerateStrategy((IPageNumberGenerateStrategy)pageNumberGenerateStrategy);
            printIPaginateInteractor.setPrintSumCover(true);
            PDFPrintUtil2.printPDF((ITemplateDocument)documentObject, (IPaginateInteractor)printIPaginateInteractor, (IPaintInteractor)new SimplePaintInteractor(), printFormUnit, (IProcessMonitor)new SimpleProcessMonitor(System.out, System.err, System.out));
            dataLists = new ArrayList<byte[]>();
            dataLists.add(printSumCover.toByteArray());
            dataLists.add(printFormUnit.toByteArray());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        byte[] concatPDFs = this.concatPDFs(dataLists);
        return concatPDFs;
    }

    private void insertSumCoverRow(List<String> list, Grid2Data grid2Data, int col) {
        for (int index = 0; index < list.size(); ++index) {
            String value = list.get(index);
            GridCellData gridCellData = grid2Data.getGridCellData(col, index + 2);
            gridCellData.setEditText(value);
            gridCellData.setShowText(value);
            gridCellData.setFitFontSize(true);
        }
    }

    /*
     * Exception decompiling
     */
    private byte[] concatPDFs(List<byte[]> list) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private String getPeriodTitle(String formSchemeKey, String period) {
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(formSchemeKey);
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        if (DataEntryUtil.isChinese()) {
            periodWrapper.setLanguage(PeriodLanguage.Chinese);
        } else {
            periodWrapper.setLanguage(PeriodLanguage.English);
        }
        return periodProvider.getPeriodTitle(periodWrapper);
    }

    public List<String> getTimeFromToEnd(String startTime, String endTime, PeriodType periodType) throws Exception {
        if (startTime == null || endTime == null || "".equals(startTime) || "".equals(endTime)) {
            return null;
        }
        ArrayList<String> result = new ArrayList<String>();
        int yearS = Integer.parseInt(startTime.substring(0, 4));
        int backS = Integer.parseInt(startTime.substring(startTime.length() - 3, startTime.length()));
        int yearE = Integer.parseInt(endTime.substring(0, 4));
        int backE = Integer.parseInt(endTime.substring(endTime.length() - 3, endTime.length()));
        Calendar ca = Calendar.getInstance();
        int sysYear = ca.get(1);
        int sysMonth = ca.get(2);
        boolean flag = startTime.substring(0, 4).equals(endTime.substring(0, 4));
        switch (periodType.type()) {
            case 0: 
            case 1: {
                if (startTime == "" && endTime == "") {
                    result.add(sysYear + "N0001");
                    break;
                }
                for (int i = Integer.parseInt(startTime.substring(0, 4)); i <= Integer.parseInt(endTime.substring(0, 4)); ++i) {
                    result.add(i + "N0001");
                }
                break;
            }
            case 2: {
                for (int i = yearS; i <= yearE; ++i) {
                    int loopStart = 1;
                    int loopEnd = 2;
                    if (i == yearS) {
                        loopStart = backS;
                    }
                    if (i == yearE) {
                        loopEnd = backE;
                    }
                    for (int j = loopStart; j <= loopEnd; ++j) {
                        result.add(i + "H000" + j);
                    }
                }
                break;
            }
            case 3: {
                for (int i = yearS; i <= yearE; ++i) {
                    int loopStart = 1;
                    int loopEnd = 4;
                    if (i == yearS) {
                        loopStart = backS;
                    }
                    if (i == yearE) {
                        loopEnd = backE;
                    }
                    for (int j = loopStart; j <= loopEnd; ++j) {
                        result.add(i + "J000" + j);
                    }
                }
                break;
            }
            case 4: {
                int monthEnd;
                int monthStart;
                int yearEnd;
                int yearStart;
                if (startTime == "" && endTime == "") {
                    yearStart = sysYear;
                    yearEnd = sysYear;
                    monthStart = sysMonth + 1;
                    monthEnd = sysMonth + 1;
                } else {
                    yearStart = Integer.parseInt(startTime.substring(0, 4));
                    monthStart = Integer.parseInt(startTime.substring(startTime.length() - 2, startTime.length()));
                    yearEnd = Integer.parseInt(endTime.substring(0, 4));
                    monthEnd = Integer.parseInt(endTime.substring(endTime.length() - 2, endTime.length()));
                }
                for (int i = yearStart; i <= yearEnd; ++i) {
                    for (int j = 1; j <= 12; ++j) {
                        if (yearStart == yearEnd) {
                            if (monthStart > j || j > monthEnd) continue;
                            result.add(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)));
                            continue;
                        }
                        if (yearStart == i) {
                            if (monthStart > j) continue;
                            result.add(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)));
                            continue;
                        }
                        if (i == yearEnd) {
                            if (j > monthEnd) continue;
                            result.add(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)));
                            continue;
                        }
                        result.add(i + "Y00" + (j < 10 ? "0" + j : Integer.valueOf(j)));
                    }
                }
                break;
            }
            case 5: {
                for (int i = yearS; i <= yearE; ++i) {
                    int loopStart = 1;
                    int loopEnd = 36;
                    if (i == yearS) {
                        loopStart = backS;
                    }
                    if (i == yearE) {
                        loopEnd = backE;
                    }
                    for (int j = loopStart; j <= loopEnd; ++j) {
                        result.add(i + (j >= 10 ? "X00" : "X000") + j);
                    }
                }
                break;
            }
            case 6: {
                for (int i = yearS; i <= yearE; ++i) {
                    int loopStart = 1;
                    int loopEnd = 365;
                    if (i == yearS) {
                        loopStart = backS;
                    }
                    if (i == yearE) {
                        loopEnd = backE;
                    }
                    for (int j = loopStart; j <= loopEnd; ++j) {
                        result.add(i + (j < 10 ? "R000" : (j > 99 ? "R0" : "R00")) + j);
                    }
                }
                break;
            }
        }
        return result;
    }

    private List<IEntityRow> getEntityDataList(String formSchemeKey, Map<String, DimensionValue> dimensionValueSet) {
        IEntityTable iEntityTable = null;
        try {
            FormSchemeDefine formSchemeDefine = this.runtimeViewController.getFormScheme(formSchemeKey);
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeDefine.getKey());
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionValueSet));
            EntityViewDefine entityViewDefine = dwEntity.getEntityViewDefine();
            iEntityQuery.setEntityView(entityViewDefine);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            executorContext.setPeriodView(formSchemeDefine.getDateTime());
            iEntityTable = this.dataEntityFullService.executeEntityReader(iEntityQuery, executorContext, entityViewDefine, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return iEntityTable.getAllRows();
    }

    private List<String> getmultiplePeriodInfoList(JtableContext jtableContext, List<String> selectDateList, Integer periodType) {
        ArrayList<String> multiplePeriodInfoList = new ArrayList<String>();
        String formSchemeKey = jtableContext.getFormSchemeKey();
        Map dimensionValueSet = jtableContext.getDimensionSet();
        for (String str : selectDateList) {
            ((DimensionValue)dimensionValueSet.get("DATATIME")).setValue(str);
            ((DimensionValue)dimensionValueSet.get("DATATIME")).setType(periodType.intValue());
            List<IEntityRow> iEntityRows = this.getEntityDataList(formSchemeKey, dimensionValueSet);
            if (iEntityRows == null || iEntityRows.size() <= 0) continue;
            multiplePeriodInfoList.add(str);
        }
        return multiplePeriodInfoList;
    }

    private static /* synthetic */ boolean lambda$export$10(Map.Entry entry, FormDefine item) {
        return item.getFormCode().equals(entry.getKey());
    }

    private static /* synthetic */ boolean lambda$export$9(Map.Entry entry, FormDefine item) {
        return item.getTitle().equals(entry.getValue());
    }

    private static /* synthetic */ boolean lambda$export$8(String periodKey, SchemePeriodLinkDefine item) {
        return item.getPeriodKey().equals(periodKey);
    }

    private static /* synthetic */ boolean lambda$export$7(Map.Entry entry, FormDefine item) {
        return item.getFormCode().equals(entry.getKey());
    }

    private static /* synthetic */ boolean lambda$export$6(Map.Entry entry, FormDefine item) {
        return item.getTitle().equals(entry.getValue());
    }

    private static /* synthetic */ boolean lambda$export$5(String printtiltle, DesignPrintTemplateSchemeDefine item) {
        return item.getTitle().equals(printtiltle);
    }

    private static /* synthetic */ boolean lambda$export$4(String periodKey, SchemePeriodLinkDefine item) {
        return item.getPeriodKey().equals(periodKey);
    }

    private static /* synthetic */ boolean lambda$export$3(Map.Entry entry, FormDefine item) {
        return item.getFormCode().equals(entry.getKey());
    }

    private static /* synthetic */ boolean lambda$export$2(Map.Entry entry, FormDefine item) {
        return item.getTitle().equals(entry.getValue());
    }

    private static /* synthetic */ boolean lambda$export$1(String printtiltle, DesignPrintTemplateSchemeDefine item) {
        return item.getTitle().equals(printtiltle);
    }

    private static /* synthetic */ boolean lambda$export$0(String periodKey, SchemePeriodLinkDefine item) {
        return item.getPeriodKey().equals(periodKey);
    }
}

