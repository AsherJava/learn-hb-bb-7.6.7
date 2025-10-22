/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.gson.Gson
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.exception.TaskExsitsException
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.logic.internal.obj.EntityData
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 *  com.jiuqi.nr.dataentity_ext.api.IEntityDataService
 *  com.jiuqi.nr.dataentity_ext.common.EntityDataException
 *  com.jiuqi.nr.dataentity_ext.dto.SaveParam
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.PrintRunTimeWithAuthService
 *  com.jiuqi.nr.definition.print.service.IPrintSchemeService
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  com.jiuqi.nr.jtable.annotation.LevelAuthRead
 *  com.jiuqi.nr.jtable.annotation.LevelAuthWrite
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.FormulaData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.base.SearchFormulaData
 *  com.jiuqi.nr.jtable.params.input.BatchSaveFormulaCheckDesInfo
 *  com.jiuqi.nr.jtable.params.input.FieldQueryInfo
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesCopyInfo
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo
 *  com.jiuqi.nr.jtable.params.input.FuzzyQueryFormulaParam
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.service.ICustomRegionsGradeService
 *  com.jiuqi.nr.jtable.service.IFormulaCheckDesService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nr.snapshot.bean.FormCompareDifference
 *  com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.ModelAttribute
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.dataentry.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.exception.TaskExsitsException;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.dataentity_ext.api.IEntityDataService;
import com.jiuqi.nr.dataentity_ext.common.EntityDataException;
import com.jiuqi.nr.dataentity_ext.dto.SaveParam;
import com.jiuqi.nr.dataentry.asynctask.AllCalculateAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.AllCheckAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchCalculateAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchCheckAnalysisFormAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchCheckAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchClearAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchCopyAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchCopyBTWEntityAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchDataPublishAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchDataSumAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchDownLoadEnclosureExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchExportAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchFormLockAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchPrintAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.BatchRollbackAccountDataAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.CopyCurrencyDesAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.CopyPeriodDesAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.DataSumAndCalcAllAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.ExportAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.FmlMonitoringAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.NodeCheckAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.RollbackAccountDataAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.bean.AuthorityOptions;
import com.jiuqi.nr.dataentry.bean.BatchCheckExportInfo;
import com.jiuqi.nr.dataentry.bean.BatchCheckParam;
import com.jiuqi.nr.dataentry.bean.BatchDownLoadEnclosureInfo;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.BatchPrintInfo;
import com.jiuqi.nr.dataentry.bean.DataPublishParam;
import com.jiuqi.nr.dataentry.bean.DataVersionData;
import com.jiuqi.nr.dataentry.bean.DataVersionParam;
import com.jiuqi.nr.dataentry.bean.DesCheckResult;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.bean.ExportPdfType;
import com.jiuqi.nr.dataentry.bean.FilesCheckUploadResult;
import com.jiuqi.nr.dataentry.bean.FormLockParam;
import com.jiuqi.nr.dataentry.bean.IRepeatEntityNode;
import com.jiuqi.nr.dataentry.bean.IRepeatImportParam;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.NodeCheckInfo;
import com.jiuqi.nr.dataentry.bean.OrderedNodeCheckResultInfo;
import com.jiuqi.nr.dataentry.bean.ResultObject;
import com.jiuqi.nr.dataentry.bean.ReviewInfoParam;
import com.jiuqi.nr.dataentry.bean.SearchFieldItem;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.config.SystemConfig;
import com.jiuqi.nr.dataentry.copydes.CheckDesCopyParam;
import com.jiuqi.nr.dataentry.copydes.CopyDesResult;
import com.jiuqi.nr.dataentry.copydes.ICopyDesService;
import com.jiuqi.nr.dataentry.funcVerificated.annotation.FuncVerificated;
import com.jiuqi.nr.dataentry.internal.service.util.CheckReviewTransformUtil;
import com.jiuqi.nr.dataentry.lockDetail.param.LockDetailParamInfo;
import com.jiuqi.nr.dataentry.lockDetail.param.LockDetailReturnInfo;
import com.jiuqi.nr.dataentry.lockDetail.param.NewLockDetailReturnInfo;
import com.jiuqi.nr.dataentry.lockDetail.service.LockDetailTableService;
import com.jiuqi.nr.dataentry.model.BatchCheckObj;
import com.jiuqi.nr.dataentry.model.DimensionObj;
import com.jiuqi.nr.dataentry.model.EntityDataObj;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.options.DataentryOptionsUtil;
import com.jiuqi.nr.dataentry.paramInfo.AccountRollBackParam;
import com.jiuqi.nr.dataentry.paramInfo.AllCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchAccountRollBack;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckResultGroupInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchClearInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCopyBTWEntityInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCopyInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;
import com.jiuqi.nr.dataentry.paramInfo.DataSumAndCalcAllInfo;
import com.jiuqi.nr.dataentry.paramInfo.DataVersionCompareParam;
import com.jiuqi.nr.dataentry.paramInfo.FileUploadParams;
import com.jiuqi.nr.dataentry.paramInfo.FmlDebugNode;
import com.jiuqi.nr.dataentry.paramInfo.FmlMonitorAndDebugParam;
import com.jiuqi.nr.dataentry.paramInfo.FmlMonitoringExportParam;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.paramInfo.FormulaCheckGroupReturnInfo;
import com.jiuqi.nr.dataentry.paramInfo.PrintSchemeData;
import com.jiuqi.nr.dataentry.paramInfo.PrintSumCover;
import com.jiuqi.nr.dataentry.paramInfo.ReviewSelectedInfo;
import com.jiuqi.nr.dataentry.paramInfo.SelectorParam;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.dataentry.service.IAllCheckService;
import com.jiuqi.nr.dataentry.service.IBatchCheckDesService;
import com.jiuqi.nr.dataentry.service.IBatchCheckResultService;
import com.jiuqi.nr.dataentry.service.IBatchCheckService;
import com.jiuqi.nr.dataentry.service.IBatchDataSumService;
import com.jiuqi.nr.dataentry.service.IBatchDownLoadEnclosureService;
import com.jiuqi.nr.dataentry.service.IBatchExportService;
import com.jiuqi.nr.dataentry.service.ICpoyErrorDesService;
import com.jiuqi.nr.dataentry.service.IDataEntryFileService;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.service.IDataPublishService;
import com.jiuqi.nr.dataentry.service.IDataVersionService;
import com.jiuqi.nr.dataentry.service.IFmlMonitorAndDebugService;
import com.jiuqi.nr.dataentry.service.IFormLockService;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.dataentry.service.IReviewInfoService;
import com.jiuqi.nr.dataentry.service.IUploadService;
import com.jiuqi.nr.dataentry.service.JioUploadExtService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.dataentry.util.ExcelImportThreadCount;
import com.jiuqi.nr.dataentry.util.authUtil.CheckAuthOfResourceUtil;
import com.jiuqi.nr.dataentry.web.WorkFlowExecuteParamTransfer;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.PrintRunTimeWithAuthService;
import com.jiuqi.nr.definition.print.service.IPrintSchemeService;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nr.jtable.annotation.LevelAuthRead;
import com.jiuqi.nr.jtable.annotation.LevelAuthWrite;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.FormulaData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.SearchFormulaData;
import com.jiuqi.nr.jtable.params.input.BatchSaveFormulaCheckDesInfo;
import com.jiuqi.nr.jtable.params.input.FieldQueryInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesCopyInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo;
import com.jiuqi.nr.jtable.params.input.FuzzyQueryFormulaParam;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.service.ICustomRegionsGradeService;
import com.jiuqi.nr.jtable.service.IFormulaCheckDesService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.snapshot.bean.FormCompareDifference;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.common.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/v1/dataentry/actions"})
@Api(tags={"\u6570\u636e\u5f55\u5165\u52a8\u4f5c,\u5305\u62ec\uff1a\u5bfc\u5165\u5bfc\u51fa\u3001\u6279\u91cf\u8fd0\u7b97\u3001\u6279\u91cf\u5ba1\u6838\u3001\u6279\u91cf\u8fd0\u7b97\u3001\u8282\u70b9\u68c0\u67e5\u3001\u9009\u62e9\u6c47\u603b\u3001\u5ba1\u6838\u8bf4\u660e\u3001\u6307\u6807\u641c\u7d22\u3001\u62a5\u8868\u9501\u5b9a\u7b49"})
public class ActionController {
    private static final Logger logger = LoggerFactory.getLogger(ActionController.class);
    @Autowired
    private IBatchDataSumService batchDataSumService;
    @Autowired
    private IBatchCheckService batchCheckService;
    @Autowired
    private IAllCheckService allCheckService;
    @Autowired
    private IBatchExportService batchExportService;
    @Autowired
    private IBatchDownLoadEnclosureService batchDownLoadEnclosureService;
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private IUploadService iUploadService;
    @Autowired
    private IFormulaCheckDesService formulaCheckDesService;
    @Autowired
    private ICopyDesService copyDesService;
    @Autowired
    private IFormLockService service;
    @Autowired
    private IDataPublishService dataPublishService;
    @Autowired
    private IDataVersionService dataVersionService;
    @Autowired
    private PrintRunTimeWithAuthService printRunTimeWithAuthService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IPrintSchemeService printSchemeService;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IReviewInfoService reviewInfoService;
    @Autowired
    private ExcelImportThreadCount excelImportThreadCount;
    @Autowired
    private DataentryOptionsUtil dataentryOptionsUtil;
    @Autowired
    private USelectorResultSet uSelectorResultSet;
    @Autowired
    private IBatchCheckResultService batchCheckResultService;
    @Autowired
    private IBatchCheckDesService batchCheckDesService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private CheckAuthOfResourceUtil checkAuthOfResourceUtil;
    @Autowired
    private LockDetailTableService lockDetailTableService;
    @Autowired
    private IDataEntryFileService dataEntryFileService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private FileUploadOssService fileUploadOssService;
    @Autowired
    private AsyncThreadExecutor asyncThreadExecutor;
    @Autowired
    private ICpoyErrorDesService cpoyErrorDesService;
    @Autowired
    private ExportExcelNameService exportExcelNameService;
    @Autowired(required=false)
    private ICustomRegionsGradeService iCustomRegionsGradeService;
    @Resource
    private WorkFlowExecuteParamTransfer workFlowExecuteParamTransfer;
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IAdjustPeriodService adjustPeriodService;
    @Autowired
    private CheckReviewTransformUtil checkReviewTransformUtil;
    @Autowired
    private JioUploadExtService jioUploadExtService;
    @Autowired
    private IEntityDataService extEntityDataService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IFmlMonitorAndDebugService fmlMonitorAndDebugService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Resource
    private IFormulaRunTimeController runtimeFormulaController;
    private Semaphore exportSemaphore;
    private final Object exportLock = new Object();
    private Semaphore printSemaphore;
    private final Object semaphoreLock = new Object();
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] BASE64_CHARS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/', '='};

    @PostMapping(value={"/getAllPiercePlugin"})
    public List<Map<String, Object>> getAllPiercePlugin(@RequestBody List<String> pluginCodes) {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        String tableData = this.nvwaSystemOptionService.get("pierce-manage-declar", "tableData");
        List tableDatas = JSONUtil.parseArray((String)tableData, Map.class);
        if (tableDatas != null) {
            for (String pluginCode : pluginCodes) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("pierceCode", pluginCode);
                map.put("pierceName", this.i18nHelper.getMessage(pluginCode));
                List originCodes = pluginCodes.stream().filter(item -> !item.equals(pluginCode)).collect(Collectors.toList());
                List filter = tableDatas.stream().filter(item -> item.get("pierceCode").toString().equals(pluginCode)).collect(Collectors.toList());
                if (filter.size() > 0) {
                    map.put("pierceMenu", ((Map)filter.get(0)).get("pierceMenu").toString());
                } else {
                    map.put("pierceMenu", String.join((CharSequence)";", String.join((CharSequence)";", originCodes)));
                }
                result.add(map);
            }
        } else {
            for (String pluginCode : pluginCodes) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("pierceCode", pluginCode);
                map.put("pierceName", this.i18nHelper.getMessage(pluginCode));
                result.add(map);
            }
        }
        return result;
    }

    @LevelAuthRead
    @RequestMapping(value={"/batch-export"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u6279\u91cf\u5bfc\u51fa")
    @NRContextBuild
    public AsyncTaskInfo batchExport(@Valid @RequestBody BatchExportInfo batchExportInfo, HttpServletResponse response) {
        boolean hasAuth = this.checkAuthOfResourceUtil.checkResourceAuthOfType(batchExportInfo.getContext().getTaskKey(), AuthorityOptions.BATCHEXPORT);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        if (!hasAuth) {
            asyncTaskInfo.setId("");
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setDetail((Object)"noAuth");
            asyncTaskInfo.setState(TaskState.ERROR);
            asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        } else {
            NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
            npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)batchExportInfo));
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchExportAsyncTaskExecutor());
            npRealTimeTaskInfo.setTaskKey(batchExportInfo.getContext().getTaskKey());
            String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
            DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(batchExportInfo.getContext().getDimensionSet(), batchExportInfo.getContext().getFormSchemeKey());
            int dimensionSize = dimensionCollection.getDimensionCombinations().size();
            if (dimensionSize * batchExportInfo.getFormSize() > 50000) {
                asyncTaskInfo.setDetail((Object)"exportHintMessage");
            } else {
                asyncTaskInfo.setDetail((Object)"");
            }
            asyncTaskInfo.setId(asyncTaskId);
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setState(TaskState.PROCESSING);
            asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        }
        return asyncTaskInfo;
    }

    @GetMapping(value={"/batch-download-export"})
    @ApiOperation(value="\u6279\u91cf\u4e0b\u8f7d\u524d\u5bfc\u51fa")
    @NRContextBuild
    public void batchDownloadExport(String downLoadKey, @ModelAttribute NRContext nrContext, HttpServletResponse response, HttpServletRequest request) {
        Object downLoadFileKey = this.cacheObjectResourceRemote.find((Object)downLoadKey);
        FileAreaService fileAreaService = this.fileService.tempArea();
        FileInfo fileInfo = fileAreaService.getInfo((String)downLoadFileKey);
        if (null != fileInfo) {
            byte[] bytes = fileAreaService.download((String)downLoadFileKey);
            try (ByteArrayInputStream ins = new ByteArrayInputStream(bytes);
                 BufferedOutputStream ous = new BufferedOutputStream((OutputStream)response.getOutputStream());){
                response.setContentType("application/octet-stream");
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(fileInfo.getName(), "UTF-8").replaceAll("\\+", "%20"));
                response.addHeader("Content-Length", "" + fileInfo.getSize());
                IOUtils.copyLarge(ins, ous);
                ((OutputStream)ous).flush();
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    @GetMapping(value={"/download-batch-export"})
    @ApiOperation(value="\u6279\u91cf\u5bfc\u51fa")
    @NRContextBuild
    public void downloadBatchExport(String downLoadKey, @ModelAttribute NRContext nrContext, HttpServletResponse response, HttpServletRequest request) {
        Object fileInfoKey = this.cacheObjectResourceRemote.find((Object)(downLoadKey + "_fileInfoKey"));
        Object areaInfo = this.cacheObjectResourceRemote.find((Object)(downLoadKey + "_areaInfo"));
        if (fileInfoKey != null) {
            try (BufferedOutputStream ous = new BufferedOutputStream((OutputStream)response.getOutputStream());){
                Object fileSize = this.cacheObjectResourceRemote.find((Object)(downLoadKey + "_fileSize"));
                FileInfo fileInfo = this.fileService.area(areaInfo.toString()).getInfo((String)fileInfoKey);
                if (!NpContextHolder.getContext().getUser().getName().equals(fileInfo.getCreater())) {
                    return;
                }
                response.setContentType("application/octet-stream");
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(fileInfo.getName(), "UTF-8").replaceAll("\\+", "%20"));
                response.addHeader("Content-Length", "" + fileSize.toString());
                this.fileService.area(areaInfo.toString()).download(fileInfoKey.toString(), (OutputStream)ous);
                ((OutputStream)ous).flush();
                response.flushBuffer();
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @LevelAuthRead
    @PostMapping(value={"/exportAsync"})
    @ApiOperation(value="\u5bfc\u51fa\u6587\u4ef6\uff1a\u76ee\u5f55\u53ef\u4ee5\u4fdd\u7559\u6700\u8fd1\u51e0\u5929\u7684\u6587\u4ef6")
    @NRContextBuild
    public AsyncTaskInfo exportAsync(@RequestBody ExportParam param) throws Exception {
        boolean hasAuth = this.checkAuthOfResourceUtil.checkResourceAuthOfType(param.getContext().getTaskKey(), AuthorityOptions.EXPORT);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        if (!hasAuth) {
            asyncTaskInfo.setDetail((Object)"EXPORT_NO_AUTH");
            return asyncTaskInfo;
        }
        String asynTaskID = null;
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(param.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(param.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)param));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new ExportAsyncTaskExecutor());
        if (this.dataentryOptionsUtil.exportAutoExe()) {
            int expThreadCount = this.excelImportThreadCount.getExportThreadCount();
            if (expThreadCount > 0) {
                Object object = this.exportLock;
                synchronized (object) {
                    if (this.exportSemaphore == null) {
                        logger.info("\u8bbe\u7f6e\u63a7\u5236\u5bfc\u51fa\u5e76\u53d1\u7684\u4fe1\u53f7\u91cf");
                        this.exportSemaphore = new Semaphore(expThreadCount);
                        logger.info("\u8bbe\u7f6e\u63a7\u5236\u5bfc\u51fa\u5e76\u53d1\u7684\u4fe1\u53f7\u91cf\uff0c\u5355\u4e2a\u8282\u70b9\u5bfc\u51fa\u7684\u5e76\u53d1\u6570\u4e3a\uff1a" + expThreadCount);
                    }
                }
            }
            if (this.exportSemaphore == null || this.exportSemaphore.tryAcquire()) {
                try {
                    asynTaskID = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
                }
                catch (Exception e) {
                    AsyncTaskInfo asyncTaskInfo2 = asyncTaskInfo;
                    return asyncTaskInfo2;
                }
                finally {
                    if (this.exportSemaphore != null) {
                        this.exportSemaphore.release();
                    }
                }
            }
        } else {
            asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        }
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @LevelAuthRead
    @PostMapping(value={"/export"})
    @ApiOperation(value="\u5bfc\u51fa\u6587\u4ef6\uff1a\u76ee\u5f55\u53ef\u4ee5\u4fdd\u7559\u6700\u8fd1\u51e0\u5929\u7684\u6587\u4ef6")
    @NRContextBuild
    public void export(@RequestBody ExportParam param, HttpServletResponse response, HttpServletRequest request) throws Exception {
        Object dataByteArr2;
        int expThreadCount = this.excelImportThreadCount.getExportThreadCount();
        boolean hasAuth = this.checkAuthOfResourceUtil.checkResourceAuthOfType(param.getContext().getTaskKey(), AuthorityOptions.EXPORT);
        if (!hasAuth) {
            response.setStatus(203);
            response.setContentType("text/html;charset=UTF-8");
            byte[] dataByteArr2 = "\u6ca1\u6709\u6743\u9650\uff01".getBytes(StandardCharsets.UTF_8);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(dataByteArr2);
            outputStream.flush();
            outputStream.close();
            response.flushBuffer();
            return;
        }
        if (expThreadCount > 0) {
            dataByteArr2 = this.exportLock;
            synchronized (dataByteArr2) {
                if (this.exportSemaphore == null) {
                    logger.info("\u8bbe\u7f6e\u63a7\u5236\u5bfc\u51fa\u5e76\u53d1\u7684\u4fe1\u53f7\u91cf");
                    this.exportSemaphore = new Semaphore(expThreadCount);
                    logger.info("\u8bbe\u7f6e\u63a7\u5236\u5bfc\u51fa\u5e76\u53d1\u7684\u4fe1\u53f7\u91cf\uff0c\u5355\u4e2a\u8282\u70b9\u5bfc\u51fa\u7684\u5e76\u53d1\u6570\u4e3a\uff1a" + expThreadCount);
                }
            }
        }
        if (this.exportSemaphore == null || this.exportSemaphore.tryAcquire()) {
            String agent = request.getHeader("User-Agent").toLowerCase();
            try {
                String dataentryExportTypeObj = this.taskOptionController.getValue(param.getContext().getTaskKey(), "EXPORT_FILE_TYPE");
                String exportType = "";
                if (param.getType().equals("EXPORT_JIO")) {
                    exportType = "JIO";
                } else if (param.getType().equals("EXPORT_PDF")) {
                    exportType = "PDF";
                } else if (param.getType().equals("EXPORT_EXCEL")) {
                    exportType = "EXCEL";
                } else if (param.getType().equals("EXPORT_TXT")) {
                    exportType = "TXT";
                } else if (param.getType().equals("EXPORT_CSV")) {
                    exportType = "CSV";
                } else if (param.getType().equals("EXPORT_OFD")) {
                    exportType = "OFD";
                }
                if (StringUtils.isEmpty((String)dataentryExportTypeObj) || !dataentryExportTypeObj.contains(exportType)) {
                    response.setStatus(204);
                    response.setContentType("text/html;charset=UTF-8");
                    String returnInfo = "\u4efb\u52a1\u9009\u9879\u672a\u5f00\u542f" + exportType + "\u5bfc\u51fa\u914d\u7f6e\uff01";
                    byte[] dataByteArr3 = returnInfo.getBytes(StandardCharsets.UTF_8);
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write(dataByteArr3);
                    outputStream.flush();
                    outputStream.close();
                    response.flushBuffer();
                    return;
                }
                if (param.isExportAllForms()) {
                    String schemeKey = param.getContext().getFormSchemeKey();
                    JtableContext jtableContext = new JtableContext();
                    jtableContext.setTaskKey(param.getContext().getTaskKey());
                    jtableContext.setFormKey(param.getContext().getFormKey());
                    jtableContext.setFormulaSchemeKey(param.getContext().getFormulaSchemeKey());
                    jtableContext.setFormSchemeKey(schemeKey);
                    jtableContext.setDimensionSet(param.getContext().getDimensionSet());
                    jtableContext.setVariableMap(param.getContext().getVariableMap());
                    List<FormGroupData> runtimeFormList = this.dataEntryParamService.getRuntimeFormList(jtableContext);
                    StringBuilder formKeys = new StringBuilder();
                    for (FormGroupData formGroupData : runtimeFormList) {
                        if (formGroupData.getReports().size() <= 0) continue;
                        for (FormData formData : formGroupData.getReports()) {
                            formKeys = formKeys.append(formData.getKey()).append(";");
                        }
                    }
                    String[] formKeyArray = formKeys.toString().split(";");
                    param.getContext().setFormKey(formKeys.toString());
                }
                param.setExportPdfType(ExportPdfType.EXPORT_PDF);
                ExportData result = this.funcExecuteService.export(param);
                if (result != null && result.isEmpty()) {
                    byte[] dataByteArr4;
                    response.setContentType("text/html;charset=UTF-8");
                    if (param.isExportEmptyTable()) {
                        response.setStatus(203);
                        dataByteArr4 = "\u65e0\u7b26\u5408\u6761\u4ef6\u6570\u636e\uff01".getBytes(StandardCharsets.UTF_8);
                    } else {
                        response.setStatus(202);
                        dataByteArr4 = "\u65e0\u6570\u636e\uff01".getBytes(StandardCharsets.UTF_8);
                    }
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write(dataByteArr4);
                    outputStream.flush();
                    outputStream.close();
                    response.flushBuffer();
                    return;
                }
                String suffix = ".xlsx";
                if ("EXPORT_JIO".equals(param.getType())) {
                    suffix = ".jio";
                } else if ("EXPORT_PDF".equals(param.getType())) {
                    suffix = ".pdf";
                } else if (!"EXPORT_TXT".equals(param.getType()) && !"EXPORT_CSV".equals(param.getType()) && "EXPORT_OFD".equals(param.getType())) {
                    suffix = ".ofd";
                }
                if (suffix.equals(".xlsx") && param.isExportETFile()) {
                    suffix = ".et";
                }
                String fileName = "";
                fileName = result == null ? param.getSheetName() + suffix : result.getFileName() + suffix;
                if (agent.indexOf("firefox") >= 0) {
                    fileName.replace(" ", "_");
                }
                if (fileName.contains("\uff0c")) {
                    fileName.replace("\uff0c", ",");
                }
                String resultFileName = new String(fileName.getBytes(), "iso8859-1");
                response.setContentType("application/octet-stream");
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment;filename=\"" + resultFileName + "\"");
                ServletOutputStream outputStream = response.getOutputStream();
                if (result == null) {
                    outputStream.write(new byte[0]);
                } else {
                    outputStream.write(result.getData());
                }
                response.flushBuffer();
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            finally {
                if (this.exportSemaphore != null) {
                    this.exportSemaphore.release();
                }
            }
        } else {
            response.setStatus(226);
            response.setContentType("text/html;charset=UTF-8");
            dataByteArr2 = "ERROR:\u7cfb\u7edf\u7e41\u5fd9\uff0c\u8bf7\u60a8\u7a0d\u540e\u518d\u8bd5\uff01".getBytes(StandardCharsets.UTF_8);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write((byte[])dataByteArr2);
            outputStream.flush();
            outputStream.close();
            response.flushBuffer();
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @LevelAuthRead
    @JLoggable(value="\u9884\u6253\u5370\u6587\u4ef6", ignoreArgs=false)
    @PostMapping(value={"/preparePrint"})
    @ApiOperation(value="\u9884\u6253\u5370\uff0c\u6253\u5370\u53c2\u6570\u653e\u5230\u7f13\u5b58\u4e2d")
    @NRContextBuild
    public Map<String, String> preparePrint(@RequestBody ExportParam param) throws Exception {
        String printUid = UUID.randomUUID().toString();
        String pdfFileName = this.exportExcelNameService.compileNameInfoWithSetting(param.getContext().getFormKey(), param.getContext(), "EXCEL_NAME", false, param.getContext().getUnitViewKey(), param.getRuleSettings()) + ".pdf";
        HashMap<String, String> result = new HashMap<String, String>();
        boolean hasAuth = this.checkAuthOfResourceUtil.checkResourceAuthOfType(param.getContext().getTaskKey(), AuthorityOptions.PRINT);
        if (!hasAuth) {
            printUid = "ERROR:\u6ca1\u6709\u6743\u9650\uff01";
        } else {
            int expThreadCount = this.excelImportThreadCount.getPrintThreadCount();
            if (expThreadCount > 0) {
                Object object = this.semaphoreLock;
                synchronized (object) {
                    if (this.printSemaphore == null) {
                        logger.info("\u8bbe\u7f6e\u63a7\u5236\u6253\u5370\u5e76\u53d1\u7684\u4fe1\u53f7\u91cf");
                        this.printSemaphore = new Semaphore(expThreadCount);
                        logger.info("\u8bbe\u7f6e\u63a7\u5236\u6253\u5370\u5e76\u53d1\u7684\u4fe1\u53f7\u91cf\uff0c\u5355\u4e2a\u8282\u70b9\u6253\u5370\u7684\u5e76\u53d1\u6570\u4e3a\uff1a" + expThreadCount);
                    }
                }
            }
            if (this.printSemaphore == null || this.printSemaphore.tryAcquire()) {
                try {
                    this.cacheObjectResourceRemote.create((Object)printUid, (Object)param);
                }
                finally {
                    if (this.printSemaphore != null) {
                        this.printSemaphore.release();
                    }
                }
            } else {
                printUid = "ERROR:\u7cfb\u7edf\u7e41\u5fd9\uff0c\u8bf7\u60a8\u7a0d\u540e\u518d\u8bd5\uff01";
            }
        }
        result.put("printUid", printUid);
        result.put("pdfFileName", pdfFileName);
        return result;
    }

    @JLoggable(value="\u9884\u6253\u5370\u6c47\u603b\u5c01\u9762\u6587\u4ef6", ignoreArgs=false)
    @PostMapping(value={"/preparePrintSumCover"})
    @ApiOperation(value="\u9884\u6253\u5370\u6c47\u603b\u5c01\u9762\u6587\u4ef6\uff0c\u6253\u5370\u53c2\u6570\u653e\u5230\u7f13\u5b58\u4e2d")
    @NRContextBuild
    public String preparePrintSumCover(@Valid @RequestBody PrintSumCover param) throws Exception {
        String printUid = UUID.randomUUID().toString();
        this.cacheObjectResourceRemote.create((Object)printUid, (Object)param);
        return printUid;
    }

    @GetMapping(value={"/printForm/{printUid}"})
    @ApiOperation(value="\u6253\u5370\uff0c\u8fd4\u56depdf\u6587\u4ef6\u6d41")
    @NRContextBuild
    public void printForm(@PathVariable String printUid, @ModelAttribute NRContext nrContext, HttpServletResponse response, HttpServletRequest request) throws Exception {
        if (StringUtils.isEmpty((String)printUid)) {
            return;
        }
        try {
            String agent = request.getHeader("User-Agent").toLowerCase();
            Object printParam = this.cacheObjectResourceRemote.find((Object)printUid);
            if (printParam != null && printParam instanceof ExportParam) {
                Object formKeys;
                ExportParam param = (ExportParam)printParam;
                param.setType("EXPORT_PDF");
                if (param.isExportAllForms()) {
                    DimensionValue DATA_SNAPSHOT_ID = null;
                    if (param.getContext().getDimensionSet().containsKey("DATASNAPSHOTID")) {
                        DATA_SNAPSHOT_ID = (DimensionValue)param.getContext().getDimensionSet().get("DATASNAPSHOTID");
                    }
                    String schemeKey = param.getContext().getFormSchemeKey();
                    JtableContext jtableContext = new JtableContext();
                    jtableContext.setTaskKey(param.getContext().getTaskKey());
                    jtableContext.setFormKey(param.getContext().getFormKey());
                    jtableContext.setFormulaSchemeKey(param.getContext().getFormulaSchemeKey());
                    jtableContext.setFormSchemeKey(schemeKey);
                    jtableContext.setDimensionSet(param.getContext().getDimensionSet());
                    jtableContext.setVariableMap(param.getContext().getVariableMap());
                    List<FormGroupData> runtimeFormList = this.dataEntryParamService.getRuntimeFormList(jtableContext);
                    formKeys = new StringBuilder();
                    for (FormGroupData formGroupData : runtimeFormList) {
                        if (formGroupData.getReports().size() <= 0) continue;
                        for (FormData formData : formGroupData.getReports()) {
                            formKeys = ((StringBuilder)formKeys).append(formData.getKey()).append(";");
                        }
                    }
                    param.getContext().setFormKey(((StringBuilder)formKeys).toString());
                    if (DATA_SNAPSHOT_ID != null) {
                        param.getContext().getDimensionSet().put("DATASNAPSHOTID", DATA_SNAPSHOT_ID);
                    }
                }
                DsContext context = DsContextHolder.getDsContext();
                DsContextImpl dsContext = (DsContextImpl)context;
                dsContext.setVariables(param.getVariables());
                param.setExportPdfType(ExportPdfType.PRINT_PDF);
                HashMap<String, RegionGradeInfo> customRegionsGrade = new HashMap<String, RegionGradeInfo>();
                IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
                formKeys = Arrays.asList(param.getFormKeys().split(";"));
                IPeriodEntityAdapter iPeriodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(param.getContext().getFormSchemeKey());
                String dataTimeDimensionName = iPeriodEntityAdapter.getPeriodEntity(formScheme.getDateTime()).getDimensionName();
                String dataTime = ((DimensionValue)param.getContext().getDimensionSet().get(dataTimeDimensionName)).getValue();
                if (this.iCustomRegionsGradeService != null) {
                    Iterator iterator = formKeys.iterator();
                    while (iterator.hasNext()) {
                        String formKey = (String)iterator.next();
                        List regionDataList = jtableParamService.getRegions(formKey);
                        List regionKeys = regionDataList.stream().map(RegionData::getKey).collect(Collectors.toList());
                        Map customRegionsGradeSingleForm = this.iCustomRegionsGradeService.getCustomRegionsGrade(regionKeys, dataTime);
                        customRegionsGrade.putAll(customRegionsGradeSingleForm);
                    }
                } else {
                    logger.info("iCustomRegionsGradeService\u65b9\u6cd5\u672a\u5b9e\u73b0,\u65e0\u6cd5\u83b7\u53d6iCustomRegionsGradeService\u7ec4\u4ef6");
                }
                param.setGradeInfos(customRegionsGrade);
                ExportData result = this.funcExecuteService.export(param);
                if (result != null && result.isEmpty()) {
                    response.setStatus(202);
                    response.setContentType("text/html;charset=UTF-8");
                    byte[] dataByteArr = param.isExportEmptyTable() && param.isExportZero() || param.isExportEmptyTable() && !param.isExportZero() ? "<h1>\u65e0\u7b26\u5408\u6761\u4ef6\u6570\u636e\uff01\u65e0\u6cd5\u6253\u5370\u9884\u89c8</h1>".getBytes(StandardCharsets.UTF_8) : "<h1>\u65e0\u6570\u636e\uff01\u65e0\u6cd5\u6253\u5370\u9884\u89c8</h1>".getBytes(StandardCharsets.UTF_8);
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write(dataByteArr);
                    outputStream.flush();
                    outputStream.close();
                    response.flushBuffer();
                    return;
                }
                String suffix = ".pdf";
                String fileName = "";
                fileName = result == null ? param.getSheetName() + ".pdf" : result.getFileName() + ".pdf";
                if (agent.indexOf("firefox") >= 0) {
                    fileName.replace(" ", "_");
                }
                if (fileName.contains("\uff0c")) {
                    fileName.replace("\uff0c", ",");
                }
                String resultFileName = new String(fileName.getBytes(), "UTF-8");
                response.setContentType("application/pdf");
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "inline;filename=\"" + resultFileName + "\"");
                ServletOutputStream outputStream = response.getOutputStream();
                if (result == null) {
                    outputStream.write(new byte[0]);
                } else {
                    outputStream.write(result.getData());
                }
            } else {
                response.setStatus(202);
                response.setContentType("text/html;charset=UTF-8");
                byte[] dataByteArr = "<h1>\u83b7\u53d6\u6253\u5370\u4fe1\u606f\u51fa\u9519\uff0c\u8bf7\u91cd\u65b0\u5c1d\u8bd5\uff01</h1>".getBytes(StandardCharsets.UTF_8);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(dataByteArr);
                outputStream.flush();
                outputStream.close();
                response.flushBuffer();
                return;
            }
            response.flushBuffer();
        }
        catch (Exception e) {
            logger.error("\u6253\u5370\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            response.setStatus(500);
            response.setContentType("text/html;charset=UTF-8");
            String logMsg = "<h1>\u6253\u5370\u51fa\u9519\uff0c\u9519\u8bef\u539f\u56e0\u8bf7\u53c2\u8003\u65e5\u5fd7</h1>";
            byte[] dataByteArr = logMsg.getBytes(StandardCharsets.UTF_8);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(dataByteArr);
            outputStream.flush();
            outputStream.close();
            response.flushBuffer();
            return;
        }
    }

    @FuncVerificated(value="importFormData")
    @PostMapping(value={"/import"})
    @ApiOperation(value="\u5bfc\u5165")
    @NRContextBuild
    public AsyncTaskInfo importFormData(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        String paramJson = request.getParameter("param");
        UploadParam param = null;
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            param = (UploadParam)mapper.readValue(paramJson, UploadParam.class);
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return this.iUploadService.upload(file, param);
    }

    @LevelAuthWrite
    @FuncVerificated(value="importFormDataFileUpload")
    @PostMapping(value={"/importFileUpload"})
    @ApiOperation(value="\u5bfc\u5165(\u6587\u4ef6\u4e0a\u4f20\u7ec4\u4ef6)")
    @NRContextBuild
    public AsyncTaskInfo importFormData(@RequestBody UploadParam param) {
        return this.iUploadService.upload(param);
    }

    @PostMapping(value={"/getJioRepeatImportList"})
    @NRContextBuild
    @ApiOperation(value="\u83b7\u53d6\u9009\u62e9\u88c5\u5165resourceKey", notes="\u83b7\u53d6\u9009\u62e9\u88c5\u5165resourceKey")
    public String getJioRepeatImportList(@Valid @RequestBody UploadParam param) {
        param.getVariableMap().put("jioNeedSelectImport", true);
        IRepeatImportParam jioRepeatImportParam = this.jioUploadExtService.getJioRepeatImportParam(param);
        List<IRepeatEntityNode> entityNodes = jioRepeatImportParam.getEntityNodes();
        SaveParam saveParam = new SaveParam();
        saveParam.setFormSchemeKey(param.getFormSchemeKey());
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(param.getTaskKey());
        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(taskDefine.getDateTime());
        saveParam.setPeriod(param.getDimensionSet().get(periodEntity.getDimensionName()).getValue());
        ArrayList<IRepeatEntityNode> entityDataRows = new ArrayList<IRepeatEntityNode>(entityNodes);
        try {
            String resourceId = this.extEntityDataService.save(saveParam, entityDataRows);
            return resourceId;
        }
        catch (EntityDataException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value={"/deletSelectImportResources"})
    @NRContextBuild
    @ApiOperation(value="\u5220\u9664\u9009\u62e9\u88c5\u5165\u4e34\u65f6\u8868", notes="\u5220\u9664\u9009\u62e9\u88c5\u5165\u4e34\u65f6\u8868")
    public void deletSelectImportResources(@Valid @RequestBody List<String> resourceKeys) {
        for (String resourceKey : resourceKeys) {
            this.extEntityDataService.drop(resourceKey);
        }
    }

    @GetMapping(value={"/import-result"})
    @NRContextBuild
    @ApiOperation(value="\u5bfc\u5165\u7ed3\u679c", notes="\u5bfc\u5165\u7ed3\u679c")
    public ImportResultObject importResult(@ModelAttribute NRContext nrContext, HttpServletRequest request) {
        String id = request.getParameter("id");
        return this.iUploadService.importResult(id);
    }

    @GetMapping(value={"/download-fail-file"})
    @ApiOperation(value="\u4e0b\u8f7d\u5931\u8d25\u7684\u5bfc\u5165\u6587\u4ef6")
    @NRContextBuild
    public void downloadFailFile(String info, @ModelAttribute NRContext nrContext, HttpServletResponse response) {
        byte[] result;
        info = info.replace("..\\", "");
        info = info.replace("../", "");
        String resultLocation = info = info.replaceAll("\\r?\\n", "");
        ObjectInfo objectInfo = this.fileUploadOssService.getInfo(info);
        try {
            result = this.fileUploadOssService.downloadFileByteFormTemp(info);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (result != null) {
            try {
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(objectInfo.getName(), "UTF-8"));
                response.addHeader("Content-Length", "" + objectInfo.getSize());
                response.setContentType("application/octet-stream");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(result);
                response.flushBuffer();
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        } else {
            try {
                PathUtils.validatePathManipulation((String)resultLocation);
            }
            catch (SecurityContentException e) {
                throw new RuntimeException(e);
            }
            File file = new File(FilenameUtils.normalize(resultLocation));
            if (file.exists() && file.isFile()) {
                try (BufferedInputStream ins = new BufferedInputStream(new FileInputStream(file));
                     BufferedOutputStream ous = new BufferedOutputStream((OutputStream)response.getOutputStream());){
                    byte[] buffer = new byte[((InputStream)ins).available()];
                    ((InputStream)ins).read(buffer);
                    response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                    response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(file.getName(), "UTF-8"));
                    response.addHeader("Content-Length", "" + file.length());
                    response.setContentType("application/octet-stream");
                    ((OutputStream)ous).write(buffer);
                    ((OutputStream)ous).flush();
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
    }

    @JLoggable(value="\u6279\u91cf\u4e0b\u8f7d\u9644\u4ef6", ignoreArgs=false)
    @RequestMapping(value={"/batch-download-enclosure"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u6279\u91cf\u4e0b\u8f7d\u9644\u4ef6")
    @NRContextBuild
    public AsyncTaskInfo batchDownLoadEnclosure(@Valid @RequestBody BatchDownLoadEnclosureInfo batchDownLoadEnclosureInfo, HttpServletResponse response) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)batchDownLoadEnclosureInfo));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchDownLoadEnclosureExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @FuncVerificated(value="nodeCheck")
    @JLoggable(value="\u6267\u884c\u8282\u70b9\u68c0\u67e5", ignoreArgs=false)
    @PostMapping(value={"/node-check"})
    @ApiOperation(value="\u8282\u70b9\u68c0\u67e5", notes="\u8282\u70b9\u68c0\u67e5")
    @NRContextBuild
    public AsyncTaskInfo nodeCheck(@Valid @RequestBody NodeCheckInfo nodeCheckInfo) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(nodeCheckInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(nodeCheckInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)nodeCheckInfo));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new NodeCheckAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @GetMapping(value={"/node-check-result"})
    @ApiOperation(value="\u8282\u70b9\u68c0\u67e5\u7ed3\u679c", notes="\u8282\u70b9\u68c0\u67e5\u7ed3\u679c")
    @NRContextBuild
    public OrderedNodeCheckResultInfo nodecheckResult(@ModelAttribute NRContext nrContext, HttpServletRequest request) {
        String id = request.getParameter("id");
        return this.batchDataSumService.nodecheckOrderResult(id);
    }

    @GetMapping(value={"/node-check-export"})
    @ApiOperation(value="\u8282\u70b9\u68c0\u67e5\u7ed3\u679c\u5bfc\u51fa", notes="\u8282\u70b9\u68c0\u67e5\u7ed3\u679c\u5bfc\u51fa")
    @NRContextBuild
    public void nodecheckExport(@ModelAttribute NRContext nrContext, HttpServletResponse response, HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            ExportData exportData = this.batchDataSumService.nodecheckExport(id);
            if (null != exportData) {
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(exportData.getFileName(), "UTF-8"));
                response.setContentType("application/octet-stream");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(exportData.getData());
                response.flushBuffer();
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @JLoggable(value="\u6267\u884c\u6279\u91cf\u6c47\u603b")
    @RequestMapping(value={"/batch-data-sum-form"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u6279\u91cf\u6c47\u603b")
    @NRContextBuild
    public AsyncTaskInfo batchDataSumForm(@Valid @RequestBody BatchDataSumInfo batchDataSumInfo) {
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(batchDataSumInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(batchDataSumInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)batchDataSumInfo)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchDataSumAsyncTaskExecutor());
        String masterKey = "";
        masterKey = masterKey + "batchdatasumform" + batchDataSumInfo.getContext().getTaskKey() + batchDataSumInfo.getContext().getFormKey() + batchDataSumInfo.getContext().getFormSchemeKey();
        Map dimensionSet = batchDataSumInfo.getContext().getDimensionSet();
        ArrayList<String> dimKey = new ArrayList<String>();
        for (String dimensionSetKey : dimensionSet.keySet()) {
            dimKey.add(dimensionSetKey);
        }
        Collections.sort(dimKey);
        for (String dimensionSetKey : dimKey) {
            masterKey = masterKey + dimensionSetKey + dimensionSet.get(dimensionSetKey);
        }
        for (String sourceKey : batchDataSumInfo.getSourceKeys()) {
            masterKey = masterKey + sourceKey;
        }
        for (Iterator<Object> iterator : batchDataSumInfo.getFormKeys().split(";")) {
            masterKey = masterKey + iterator;
        }
        masterKey = masterKey + batchDataSumInfo.getContextEntityId();
        masterKey = masterKey + batchDataSumInfo.isRecursive();
        masterKey = masterKey + batchDataSumInfo.isDifference();
        String dimensionIdentity = ActionController.tofakeGUID(masterKey);
        try {
            String asynTaskID = this.asyncTaskManager.publishUniqueTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_BATCHDATASUM.getName(), dimensionIdentity);
            asyncTaskInfo.setId(asynTaskID);
            asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        }
        catch (TaskExsitsException e) {
            asyncTaskInfo.setId(e.getTaskId());
            asyncTaskInfo.setState(TaskState.ERROR);
            asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
            asyncTaskInfo.setResult("\u60a8\u521b\u5efa\u7684\u4efb\u52a1\u5df2\u7ecf\u5728\u7b49\u5f85\u6216\u6267\u884c\u4e2d\u3002");
        }
        return asyncTaskInfo;
    }

    @JLoggable(value="\u6267\u884c\u6279\u91cf\u8fd0\u7b97")
    @RequestMapping(value={"/batch-calculate-form"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u6279\u91cf\u8fd0\u7b97")
    @NRContextBuild
    public AsyncTaskInfo batchCalculateForm(@Valid @RequestBody BatchCalculateInfo batchCalculateInfo) throws InterruptedException {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(batchCalculateInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(batchCalculateInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)batchCalculateInfo)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchCalculateAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_BATCHCALCULATE.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u6267\u884c\u5168\u7b97")
    @RequestMapping(value={"/all-calculate-form"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u5168\u7b97")
    @NRContextBuild
    public AsyncTaskInfo allCalculateForm(@Valid @RequestBody BatchCalculateInfo batchCalculateInfo) throws InterruptedException {
        String asynTaskID = null;
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(batchCalculateInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(batchCalculateInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)batchCalculateInfo)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new AllCalculateAsyncTaskExecutor());
        if (this.dataentryOptionsUtil.allCheckCaclAutoExe()) {
            asynTaskID = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
        } else {
            String masterKey = "";
            masterKey = masterKey + "allcalculateform" + batchCalculateInfo.getContext().getTaskKey() + batchCalculateInfo.getContext().getFormKey() + batchCalculateInfo.getContext().getFormSchemeKey();
            Map dimensionSet = batchCalculateInfo.getContext().getDimensionSet();
            ArrayList<String> dimKey = new ArrayList<String>();
            for (String dimensionSetKey : dimensionSet.keySet()) {
                dimKey.add(dimensionSetKey);
            }
            Collections.sort(dimKey);
            for (String dimensionSetKey : dimKey) {
                masterKey = masterKey + dimensionSetKey + dimensionSet.get(dimensionSetKey);
            }
            String dimensionIdentity = ActionController.tofakeGUID(masterKey);
            try {
                asynTaskID = this.asyncTaskManager.publishUniqueTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_ALLCALCULATE.getName(), dimensionIdentity);
            }
            catch (TaskExsitsException e) {
                AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
                asyncTaskInfo.setId(dimensionIdentity);
                asyncTaskInfo.setState(TaskState.ERROR);
                asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
                asyncTaskInfo.setResult("\u60a8\u521b\u5efa\u7684\u4efb\u52a1\u5df2\u7ecf\u5728\u7b49\u5f85\u6216\u6267\u884c\u4e2d\u3002");
                return asyncTaskInfo;
            }
        }
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u6267\u884c\u6c47\u603b\u540e\u518d\u6267\u884c\u5168\u7b97")
    @RequestMapping(value={"/dataSum-calcAll-form"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u6c47\u603b\u540e\u5168\u7b97")
    @NRContextBuild
    public AsyncTaskInfo dataSumAndCalcAllForm(@Valid @RequestBody DataSumAndCalcAllInfo dataSumAndCalcAllInfo) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(dataSumAndCalcAllInfo.getBatchDataSumInfo().getContext().getTaskKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)dataSumAndCalcAllInfo));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new DataSumAndCalcAllAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNCTASK_DATASUMANDCALCULATEALL");
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u6267\u884c\u6279\u91cf\u5ba1\u6838")
    @RequestMapping(value={"/batch-check-form"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u6279\u91cf\u5ba1\u6838")
    @NRContextBuild
    public AsyncTaskInfo batchCheckForm(@Valid @RequestBody BatchCheckInfo batchCheckInfo) throws InterruptedException {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(batchCheckInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(batchCheckInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)batchCheckInfo)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchCheckAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_BATCHCHECK.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u6267\u884c\u5206\u6790\u8868\u6279\u91cf\u5ba1\u6838")
    @RequestMapping(value={"/batch-check-analysis-form"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u5206\u6790\u8868\u6279\u91cf\u5ba1\u6838")
    @NRContextBuild
    public AsyncTaskInfo batchCheckAnalysisForm(@Valid @RequestBody BatchCheckInfo batchCheckInfo) throws InterruptedException {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(batchCheckInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(batchCheckInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)batchCheckInfo)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchCheckAnalysisFormAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u6267\u884c\u5168\u5ba1")
    @RequestMapping(value={"/all-check-form"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u5168\u5ba1")
    @NRContextBuild
    public AsyncTaskInfo allCheckForm(@Valid @RequestBody AllCheckInfo allCheckInfo) throws InterruptedException {
        String asynTaskID = null;
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(allCheckInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(allCheckInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)allCheckInfo)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new AllCheckAsyncTaskExecutor());
        if (this.dataentryOptionsUtil.allCheckCaclAutoExe()) {
            asynTaskID = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
        } else {
            String masterKey = "";
            masterKey = masterKey + "allcheckform" + allCheckInfo.getContext().getTaskKey() + allCheckInfo.getContext().getFormKey() + allCheckInfo.getContext().getFormSchemeKey();
            Map dimensionSet = allCheckInfo.getContext().getDimensionSet();
            ArrayList<String> dimKey = new ArrayList<String>();
            for (String dimensionSetKey : dimensionSet.keySet()) {
                dimKey.add(dimensionSetKey);
            }
            Collections.sort(dimKey);
            for (String dimensionSetKey : dimKey) {
                masterKey = masterKey + dimensionSetKey + dimensionSet.get(dimensionSetKey);
            }
            String dimensionIdentity = ActionController.tofakeGUID(masterKey);
            try {
                asynTaskID = this.asyncTaskManager.publishUniqueTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_ALLCHECK.getName(), dimensionIdentity);
            }
            catch (TaskExsitsException e) {
                AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
                asyncTaskInfo.setId(dimensionIdentity);
                asyncTaskInfo.setState(TaskState.ERROR);
                asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
                asyncTaskInfo.setResult("\u60a8\u521b\u5efa\u7684\u4efb\u52a1\u5df2\u7ecf\u5728\u7b49\u5f85\u6216\u6267\u884c\u4e2d\u3002");
                return asyncTaskInfo;
            }
        }
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @RequestMapping(value={"/batch-check-result-group"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u67e5\u8be2\u5206\u7ec4")
    @NRContextBuild
    public FormulaCheckGroupReturnInfo batchCheckResultGroup(@Valid @RequestBody BatchCheckResultGroupInfo batchCheckResultGroupInfo) {
        this.workFlowExecuteParamTransfer.checkBatchCheckResultGroupInfo(batchCheckResultGroupInfo);
        return this.batchCheckResultService.batchCheckResultGroup(batchCheckResultGroupInfo);
    }

    @RequestMapping(value={"/batch-check-result"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u6279\u91cf\u5ba1\u6838\u7ed3\u679c\u67e5\u8be2")
    @NRContextBuild
    public FormulaCheckReturnInfo batchCheckResult(@Valid @RequestBody BatchCheckInfo batchCheckInfo) {
        this.workFlowExecuteParamTransfer.checkBatchCheckInfo(batchCheckInfo);
        return this.batchCheckResultService.batchCheckResult(batchCheckInfo);
    }

    @RequestMapping(value={"/all-check-result"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u5168\u5ba1\u7ed3\u679c\u67e5\u8be2")
    @NRContextBuild
    public FormulaCheckReturnInfo allCheckResult(@Valid @RequestBody @SFDecrypt AllCheckInfo allCheckInfo) {
        return this.allCheckService.allCheckResult(allCheckInfo);
    }

    @RequestMapping(value={"/save-review-info"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u4fdd\u5b58\u5ba1\u6838\u65b9\u6848\u4fe1\u606f")
    @NRContextBuild
    public ReturnInfo saveReviewInfo(@Valid @RequestBody ReviewInfoParam param) {
        return this.reviewInfoService.saveOrUpdatetRevienInfo(param);
    }

    @RequestMapping(value={"/get-review-info"}, method={RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value="\u83b7\u5f97\u5ba1\u6838\u65b9\u6848\u4fe1\u606f")
    @NRContextBuild
    public ReviewInfoParam getReviewInfo(String formSchemeKey, @ModelAttribute NRContext nrContext) {
        return this.reviewInfoService.getReviewInfoTable(formSchemeKey);
    }

    @RequestMapping(value={"/get-units-Info"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u5355\u4f4d\u7684\u8be6\u7ec6\u4fe1\u606f")
    @ResponseBody
    @NRContextBuild
    public List<ReviewSelectedInfo> getUnitsInfo(@Valid @RequestBody SelectorParam param) {
        List rows = this.uSelectorResultSet.getFilterEntityRows(param.getSelectorKey());
        List<ReviewSelectedInfo> units = rows.stream().map(row -> {
            ReviewSelectedInfo unitInfo = new ReviewSelectedInfo();
            unitInfo.setCode(row.getCode());
            unitInfo.setTitle(row.getTitle());
            return unitInfo;
        }).collect(Collectors.toList());
        return units;
    }

    @RequestMapping(value={"/description-check-result"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u51fa\u9519\u8bf4\u660e\u68c0\u67e5")
    @NRContextBuild
    public DesCheckResult desCheckResult(@Valid @RequestBody BatchCheckInfo batchCheckInfo) {
        return this.batchCheckDesService.desCheckResult(batchCheckInfo);
    }

    @PostMapping(value={"/description-check-result-export"})
    @ApiOperation(value="\u51fa\u9519\u8bf4\u660e\u68c0\u67e5\u7ed3\u679c\u5bfc\u51fa")
    @NRContextBuild
    public void desCheckResultExport(@RequestBody String info, HttpServletResponse response, HttpServletRequest request) {
        try {
            Gson gson = new Gson();
            BatchCheckInfo batchCheckInfo = (BatchCheckInfo)((Object)gson.fromJson(info, BatchCheckInfo.class));
            ExportData formulaExportData = this.batchCheckDesService.desCheckResultExport(batchCheckInfo);
            if (null != formulaExportData) {
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(formulaExportData.getFileName(), "UTF-8"));
                response.setContentType("application/octet-stream");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(formulaExportData.getData());
                response.flushBuffer();
            }
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @RequestMapping(value={"/get-batch-check-param"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u6279\u91cf\u5ba1\u6838\u7684\u53c2\u6570")
    @NRContextBuild
    public BatchCheckParam getBatchCheckParam(@Valid @RequestBody JtableContext context) {
        return this.funcExecuteService.getBatchCheckParam(context);
    }

    @RequestMapping(value={"/query-formula"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6570\u636e\u5f55\u5165\uff1a\u67e5\u8be2\u516c\u5f0f\u5ba1\u6838\u8bf4\u660e ")
    @ResponseBody
    @NRContextBuild
    public List<FormulaCheckDesInfo> query(@Valid @RequestBody FormulaCheckDesQueryInfo reportDataQueryInfo) {
        return this.formulaCheckDesService.queryFormulaCheckDes(reportDataQueryInfo);
    }

    @JLoggable(value="\u4fdd\u5b58\u516c\u5f0f\u5ba1\u6838\u8bf4\u660e")
    @RequestMapping(value={"/save-checkdesc"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6570\u636e\u5f55\u5165\uff1a\u4fdd\u5b58\u516c\u5f0f\u5ba1\u6838\u8bf4\u660e ")
    @ResponseBody
    @NRContextBuild
    public FormulaCheckDesInfo save(@Valid @RequestBody FormulaCheckDesInfo descriptionInfo) {
        NpContext context = NpContextHolder.getContext();
        descriptionInfo.getDescriptionInfo().setUserId(context.getUserId());
        descriptionInfo.getDescriptionInfo().setUserName(context.getUser().getName());
        Date updateTime = new Date();
        descriptionInfo.getDescriptionInfo().setUpdateTime(updateTime.toInstant());
        return this.formulaCheckDesService.saveCKD(descriptionInfo);
    }

    @JLoggable(value="\u6279\u91cf\u4fdd\u5b58\u516c\u5f0f\u5ba1\u6838\u51fa\u9519\u8bf4\u660e")
    @RequestMapping(value={"/batchsave-checkdesc"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6570\u636e\u5f55\u5165\uff1a\u6279\u91cf\u4fdd\u5b58\u516c\u5f0f\u5ba1\u6838\u51fa\u9519\u8bf4\u660e ")
    @ResponseBody
    @NRContextBuild
    public ReturnInfo batchsave(@Valid @RequestBody BatchSaveFormulaCheckDesInfo checkDesInfo) {
        return this.formulaCheckDesService.batchSaveCKD(checkDesInfo);
    }

    @JLoggable(value="\u5220\u9664\u516c\u5f0f\u5ba1\u6838\u8bf4\u660e")
    @RequestMapping(value={"/remove-checkdesc"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6570\u636e\u5f55\u5165\uff1a\u5220\u9664\u516c\u5f0f\u5ba1\u6838\u8bf4\u660e ")
    @ResponseBody
    @NRContextBuild
    public ReturnInfo remove(@Valid @RequestBody FormulaCheckDesInfo descriptionInfo) {
        return this.formulaCheckDesService.removeFormulaCheckDes(descriptionInfo);
    }

    @JLoggable(value=" \u67e5\u8be2\u5ba1\u6838\u7ed3\u679c\u516c\u5f0f\u5217\u8868")
    @RequestMapping(value={"/queryformula-checkdesc"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6570\u636e\u5f55\u5165\uff1a \u67e5\u8be2\u5ba1\u6838\u7ed3\u679c\u516c\u5f0f\u5217\u8868 ")
    @ResponseBody
    @NRContextBuild
    public List<FormulaData> queryformula(@Valid @RequestBody BatchSaveFormulaCheckDesInfo checkDesInfo) {
        return this.formulaCheckDesService.queryCheckResultFormulaIds(checkDesInfo);
    }

    @JLoggable(value=" \u67e5\u8be2\u5ba1\u6838\u7ed3\u679c\u516c\u5f0f\u5217\u8868")
    @RequestMapping(value={"/fuzzyqueryformula-checkResult"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6570\u636e\u5f55\u5165\uff1a \u6a21\u7cca\u67e5\u8be2\u5ba1\u6838\u7ed3\u679c\u516c\u5f0f\u5217\u8868 ")
    @ResponseBody
    @NRContextBuild
    public List<SearchFormulaData> fuzzyQueryFormula(@Valid @RequestBody FuzzyQueryFormulaParam checkDesInfo) {
        return this.formulaCheckDesService.fuzzyQueryFormula(checkDesInfo);
    }

    @JLoggable(value="\u4fee\u6b63\u5ba1\u6838\u9519\u8bef\u8bf4\u660ekey")
    @RequestMapping(value={"/revise-checkdesc"}, method={RequestMethod.GET})
    @ApiOperation(value="\u6570\u636e\u5f55\u5165\uff1a\u4fee\u6b63\u5ba1\u6838\u9519\u8bef\u8bf4\u660ekey ")
    @ResponseBody
    @NRContextBuild
    public ReturnInfo reviseCheckDesKey(@ModelAttribute NRContext nrContext) {
        return this.formulaCheckDesService.reviseCheckDesKey();
    }

    @JLoggable(value="\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e")
    @RequestMapping(value={"/copy-checkdesc"}, method={RequestMethod.GET})
    @ApiOperation(value="\u6570\u636e\u5f55\u5165\uff1a\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e")
    @ResponseBody
    @NRContextBuild
    public ReturnInfo copyCheckDes(@Valid FormulaCheckDesCopyInfo copyInfo, @ModelAttribute NRContext nrContext) {
        CheckDesCopyParam checkDesCopyParam = new CheckDesCopyParam();
        checkDesCopyParam.setTargetDimensionSet(copyInfo.getContext().getDimensionSet());
        checkDesCopyParam.setTargetFormSchemeKey(copyInfo.getContext().getFormSchemeKey());
        checkDesCopyParam.setTargetFormulaSchemeKey(copyInfo.getContext().getFormulaSchemeKey());
        CopyDesResult copyDesResult = this.copyDesService.copy(checkDesCopyParam);
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setMessage(copyDesResult.getSysLogText());
        return returnInfo;
    }

    @PostMapping(value={"/check-result-export"})
    @ApiOperation(value="\u6279\u91cf\u5ba1\u6838\u7ed3\u679cEXCEL\u5bfc\u51fa")
    @NRContextBuild
    public void checkResultExport(@RequestBody BatchCheckExportInfo batchCheckExportInfo, HttpServletResponse response, HttpServletRequest request) {
        try {
            ExportData formulaExportData = this.funcExecuteService.checkResultExport(batchCheckExportInfo);
            if (null != formulaExportData) {
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(formulaExportData.getFileName(), "UTF-8"));
                response.setContentType("application/octet-stream");
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(formulaExportData.getData());
                response.flushBuffer();
            }
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @JLoggable(value="\u67e5\u8be2\u5176\u4ed6\u591a\u9009\u60c5\u666f", ignoreArgs=false)
    @RequestMapping(value={"/queryOtherDims"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u5176\u4ed6\u591a\u9009\u60c5\u666f")
    @NRContextBuild
    public List<DimensionObj> queryOtherDims(@RequestBody JtableContext context) {
        return this.checkReviewTransformUtil.queryOtherDims(context);
    }

    @JLoggable(value="\u6267\u884c\u6279\u91cf\u6e05\u9664")
    @RequestMapping(value={"/batch-clear-form"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u6279\u91cf\u6e05\u9664")
    @NRContextBuild
    public AsyncTaskInfo batchClearForm(@Valid @RequestBody BatchClearInfo batchClearInfo) throws InterruptedException {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(batchClearInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(batchClearInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)batchClearInfo)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchClearAsyncTaskExecutor());
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @PostMapping(value={"/search-field"})
    @ApiOperation(value="\u6307\u6807\u641c\u7d22")
    @NRContextBuild
    public List<SearchFieldItem> searchField(@Valid @RequestBody FieldQueryInfo fieldQueryInfo, HttpServletResponse response) {
        return this.funcExecuteService.searchFieldsInForm(fieldQueryInfo);
    }

    @PostMapping(value={"/form-lock"})
    @ApiOperation(value="\u62a5\u8868\u9501\u5b9a\u3001\u89e3\u9501")
    @NRContextBuild
    public ResultObject formLock(@Valid @RequestBody FormLockParam param) throws Exception {
        ResultObject result = this.service.lockForm(param);
        return result;
    }

    @GetMapping(value={"/getPeriodTitle"})
    @ApiOperation(value="\u83b7\u53d6\u65f6\u671f\u540d\u79f0")
    @NRContextBuild
    public String getPeriodTitle(String formSchemeKey, String period) {
        return this.exportExcelNameService.getPeriodTitle(formSchemeKey, period);
    }

    @JLoggable(value="\u6267\u884c\u6279\u91cf\u9501\u5b9a/\u89e3\u9501")
    @PostMapping(value={"/batch-form-lock"})
    @ResponseBody
    @ApiOperation(value="\u6279\u91cf\u9501\u5b9a")
    @NRContextBuild
    public AsyncTaskInfo batchFormLock(@Valid @RequestBody FormLockParam param) throws InterruptedException {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(param.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(param.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)param)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchFormLockAsyncTaskExecutor());
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @PostMapping(value={"/query-forceUnLock"})
    @ApiOperation(value="\u67e5\u8be2\u80fd\u5426\u5f3a\u5236\u89e3\u9501")
    @NRContextBuild
    public Boolean queryForceUnLock(@Valid @RequestBody FormLockParam param) {
        Boolean result = this.service.queryForceUnLock(param);
        return result;
    }

    @FuncVerificated(value="dataPublish")
    @JLoggable(value="\u6570\u636e\u53d1\u5e03\u3001\u53d6\u6d88\u53d1\u5e03")
    @PostMapping(value={"/data-publish"})
    @ApiOperation(value="\u6570\u636e\u53d1\u5e03\u3001\u53d6\u6d88\u53d1\u5e03")
    @NRContextBuild
    public AsyncTaskInfo dataPublish(@Valid @RequestBody DataPublishParam param) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(param.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(param.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)param)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchDataPublishAsyncTaskExecutor());
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @PostMapping(value={"/data-publish/status"})
    @ApiOperation(value="\u6570\u636e\u53d1\u5e03\u72b6\u6001")
    @NRContextBuild
    public Boolean getDataPublishStatus(@Valid @RequestBody DataPublishParam param) {
        return this.dataPublishService.isDataPublished(param);
    }

    @PostMapping(value={"/data-versions"})
    @ApiOperation(value="\u83b7\u53d6\u73b0\u6709\u7684\u6240\u6709\u6570\u636e\u7248\u672c")
    @NRContextBuild
    public List<DataVersionData> dataVersions(@Valid @RequestBody JtableContext context) {
        List<DataVersionData> list = this.dataVersionService.queryAll(context);
        return list;
    }

    @JLoggable(value="\u624b\u52a8\u521b\u5efa\u548c\u4fee\u6539\u6570\u636e\u7248\u672c")
    @PostMapping(value={"/data-version/save"})
    @ApiOperation(value="\u83b7\u53d6\u73b0\u6709\u7684\u6240\u6709\u6570\u636e\u7248\u672c")
    @NRContextBuild
    public ReturnInfo saveDataVersions(@RequestBody DataVersionParam param) {
        return this.dataVersionService.createOrUpdateDataVersion(param);
    }

    @JLoggable(value="\u5220\u9664\u6570\u636e\u7248\u672c")
    @PostMapping(value={"/data-version/delete"})
    @ApiOperation(value="\u5220\u9664\u4e00\u4e2a\u6570\u636e\u7248\u672c")
    @NRContextBuild
    public ReturnInfo delete(@RequestBody DataVersionParam param) {
        return this.dataVersionService.deleteDataVersion(param);
    }

    @JLoggable(value="\u6570\u636e\u7248\u672c\u8986\u76d6\u5f53\u524d\u6570\u636e")
    @PostMapping(value={"/data-version/overwrite"})
    @ApiOperation(value="\u8986\u76d6\u5f53\u524d\u6570\u636e")
    @NRContextBuild
    public AsyncTaskInfo overwrite(@RequestBody DataVersionParam param) {
        return this.dataVersionService.overwriteDataVersion(param);
    }

    @PostMapping(value={"/data-version/compare"})
    @ApiOperation(value="\u6bd4\u8f83\u4e24\u4e2a\u6570\u636e\u7248\u672c")
    @NRContextBuild
    public List<FormCompareDifference> compare(@RequestBody DataVersionCompareParam param) {
        return this.dataVersionService.compareDataVersion(param);
    }

    @GetMapping(value={"/print/schemes"})
    @ApiOperation(value="\u83b7\u53d6\u4e00\u4e2a\u65b9\u6848\u4e0b\u6240\u6709\u7684\u6253\u5370\u65b9\u6848")
    @NRContextBuild
    public List<PrintSchemeData> printSchemes(String schemeKey, @ModelAttribute NRContext nrContext) {
        List printSchemes = null;
        ArrayList<PrintSchemeData> results = new ArrayList<PrintSchemeData>();
        try {
            printSchemes = this.printRunTimeWithAuthService.getAllPrintSchemeByFormScheme(schemeKey);
            for (PrintTemplateSchemeDefine designPrintTemplateSchemeDefine : printSchemes) {
                PrintSchemeData printSchemeData = new PrintSchemeData();
                printSchemeData.setKey(designPrintTemplateSchemeDefine.getKey());
                printSchemeData.setTitle(designPrintTemplateSchemeDefine.getTitle());
                boolean exsitSchemeTemplate = this.printSchemeService.exsitSchemeTemplate(designPrintTemplateSchemeDefine.getKey());
                if (exsitSchemeTemplate) {
                    printSchemeData.setSummary(true);
                }
                results.add(printSchemeData);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return results;
    }

    @JLoggable(value="\u6c47\u603b\u5c01\u9762\u6253\u5370")
    @GetMapping(value={"/print/sumcover/{printUid}"})
    @PostMapping(value={"/print/sumcover"})
    @ApiOperation(value="\u6c47\u603b\u5c01\u9762\u6253\u5370")
    @NRContextBuild
    public void printSumCover(@PathVariable String printUid, @ModelAttribute NRContext nrContext, HttpServletResponse response, HttpServletRequest request) throws Exception {
        try {
            Object printParam = this.cacheObjectResourceRemote.find((Object)printUid);
            if (printParam != null && printParam instanceof PrintSumCover) {
                PrintSumCover printSumCover = (PrintSumCover)printParam;
                byte[] result = this.batchExportService.printSumCover(printSumCover);
                String resultFileName = new String("\u6c47\u603b\u5c01\u9762".getBytes(), "iso8859-1");
                response.setContentType("application/pdf");
                response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
                response.setHeader("Content-disposition", "inline;filename=" + resultFileName);
                ServletOutputStream outputStream = response.getOutputStream();
                if (result == null) {
                    outputStream.write(new byte[0]);
                } else {
                    outputStream.write(result);
                }
            } else {
                response.setStatus(202);
                response.setContentType("text/html;charset=UTF-8");
                byte[] dataByteArr = "<h1>\u83b7\u53d6\u6253\u5370\u4fe1\u606f\u51fa\u9519\uff0c\u8bf7\u91cd\u65b0\u5c1d\u8bd5\uff01</h1>".getBytes(StandardCharsets.UTF_8);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(dataByteArr);
                outputStream.flush();
                outputStream.close();
                response.flushBuffer();
                return;
            }
            response.flushBuffer();
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @JLoggable(value="\u5efa\u7acb\u6279\u91cf\u6253\u5370\u4efb\u52a1")
    @PostMapping(value={"/batch-print/task"})
    @ApiOperation(value="\u5efa\u7acb\u6279\u91cf\u6253\u5370\u4efb\u52a1")
    public ReturnInfo batchPrint() {
        return this.batchExportService.batchPrintTask();
    }

    @LevelAuthRead
    @JLoggable(value="\u5f00\u59cb\u6267\u884c\u6279\u91cf\u6253\u5370")
    @PostMapping(value={"/batch-print/start"})
    @ApiOperation(value="\u5f00\u59cb\u6279\u91cf\u6253\u5370")
    @NRContextBuild
    public AsyncTaskInfo startbatchPrint(@Valid @RequestBody BatchPrintInfo batchPrintInfo) {
        boolean hasAuth = this.checkAuthOfResourceUtil.checkResourceAuthOfType(batchPrintInfo.getContext().getTaskKey(), AuthorityOptions.BATCHPRINT);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        if (!hasAuth) {
            asyncTaskInfo.setId("");
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setDetail((Object)"noAuth");
            asyncTaskInfo.setState(TaskState.ERROR);
            asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        } else {
            NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
            npRealTimeTaskInfo.setTaskKey(batchPrintInfo.getContext().getTaskKey());
            npRealTimeTaskInfo.setFormSchemeKey(batchPrintInfo.getContext().getFormSchemeKey());
            npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)batchPrintInfo));
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchPrintAsyncTaskExecutor());
            String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
            new SimpleAsyncProgressMonitor(asyncTaskId, this.cacheObjectResourceRemote);
            asyncTaskInfo.setId(asyncTaskId);
            asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        }
        return asyncTaskInfo;
    }

    @JLoggable(value="\u6267\u884c\u6279\u91cf\u590d\u5236", ignoreArgs=false)
    @RequestMapping(value={"/batch-copy"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u6279\u91cf\u590d\u5236")
    @NRContextBuild
    public AsyncTaskInfo batchCopy(@Valid @RequestBody BatchCopyInfo batchCopyInfo) throws InterruptedException {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(batchCopyInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(batchCopyInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)batchCopyInfo)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchCopyAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @GetMapping(value={"/progress/query"})
    @ApiOperation(value="\u67e5\u8be2\u4e00\u4e2a\u8fdb\u5ea6\u6761")
    @NRContextBuild
    public AsyncTaskInfo progressQuery(String progressId, @ModelAttribute NRContext nrContext) {
        Object find = this.cacheObjectResourceRemote.find((Object)progressId);
        if (null != find) {
            return (AsyncTaskInfo)find;
        }
        return null;
    }

    @GetMapping(value={"/getShow"})
    public List<Map<String, String>> getShow() {
        ArrayList<Map<String, String>> re = new ArrayList<Map<String, String>>();
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("key", "1");
        item.put("title", "\u662f");
        HashMap<String, String> item2 = new HashMap<String, String>();
        item2.put("key", "0");
        item2.put("title", "\u5426");
        re.add(item);
        re.add(item2);
        return re;
    }

    @JLoggable(value="\u56de\u6eda\u53f0\u8d26\u6570\u636e")
    @ApiOperation(value="\u56de\u6eda\u53f0\u8d26\u6570\u636e")
    @NRContextBuild
    public AsyncTaskInfo batchRollBackAccountData(@RequestBody BatchAccountRollBack batchAccountRollBack) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey("");
        npRealTimeTaskInfo.setFormSchemeKey(batchAccountRollBack.getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)batchAccountRollBack));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchRollbackAccountDataAsyncTaskExecutor());
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u56de\u6eda\u53f0\u8d26\u6570\u636e")
    @PostMapping(value={"/rollback-account-data"})
    @ApiOperation(value="\u56de\u6eda\u53f0\u8d26\u6570\u636e")
    @NRContextBuild
    public AsyncTaskInfo rollBackAccountData(@Valid @RequestBody AccountRollBackParam accountRollBackParam) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(accountRollBackParam.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(accountRollBackParam.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)accountRollBackParam));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new RollbackAccountDataAsyncTaskExecutor());
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u5173\u8054\u5b9e\u4f53\u662f\u5426\u591a\u9009")
    @GetMapping(value={"/get-entity-multiple"})
    @ApiOperation(value="\u5173\u8054\u5b9e\u4f53\u662f\u5426\u591a\u9009")
    @NRContextBuild
    public boolean getEntityMultiple(String param) {
        String formSchemeKey = param.split(",")[0];
        String dimensionEntity = this.iRunTimeViewController.getFormScheme(formSchemeKey).getDw();
        String entityKey = param.split(",")[2];
        String id = this.formSchemeService.getDimAttributeByReportDim(formSchemeKey, entityKey);
        if (org.springframework.util.StringUtils.hasText(id)) {
            return this.entityMetaService.getEntityModel(dimensionEntity).getAttribute(id).isMultival();
        }
        return true;
    }

    @JLoggable(value="\u83b7\u53d6\u9501\u5b9a\u8be6\u60c5\u4fe1\u606f")
    @PostMapping(value={"/query-lockDetail"})
    @ApiOperation(value="\u83b7\u53d6\u9501\u5b9a\u8be6\u60c5\u4fe1\u606f")
    @NRContextBuild
    public NewLockDetailReturnInfo queryLockDetailInfo(@Valid @RequestBody LockDetailParamInfo lockDetailParamInfo) {
        NewLockDetailReturnInfo newLockDetailReturnInfo = new NewLockDetailReturnInfo();
        List<LockDetailReturnInfo> lockDetailReturnInfos = this.lockDetailTableService.queryLockDetailList(lockDetailParamInfo);
        newLockDetailReturnInfo.setLockDetailReturnInfos(lockDetailReturnInfos);
        FormLockParam formLockParam = new FormLockParam();
        lockDetailParamInfo.getJtableContext().setFormKey(lockDetailParamInfo.getFormKey());
        formLockParam.setContext(lockDetailParamInfo.getJtableContext());
        Boolean locked = this.service.isFormLocked(formLockParam);
        if (!locked.booleanValue()) {
            newLockDetailReturnInfo.setLocked(false);
        } else {
            newLockDetailReturnInfo.setLocked(true);
            Map<String, String> maps = this.service.getLockedFormKeysMap(formLockParam, true);
            newLockDetailReturnInfo.setLockUsers(maps.get(lockDetailParamInfo.getFormKey()));
        }
        return newLockDetailReturnInfo;
    }

    @PostMapping(value={"/checkFileAndUpload"})
    public FilesCheckUploadResult checkUploadFileInfo(@RequestParam(value="file") MultipartFile[] files, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        String paramJson = request.getParameter("param");
        FileUploadParams fileUploadParams = null;
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            fileUploadParams = (FileUploadParams)mapper.readValue(paramJson, FileUploadParams.class);
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return this.dataEntryFileService.checkAndUploadFiles(files, fileUploadParams);
    }

    @PostMapping(value={"/getAllUploadSysConfig"})
    public Map<String, Object> getAllCheckInfo() {
        return this.dataEntryFileService.getAllSysUploadConfig();
    }

    @GetMapping(value={"/getSystemConfig"})
    public SystemConfig getSystemConfig() {
        return this.systemConfig;
    }

    @PostMapping(value={"/singleImportCheck"})
    public boolean singleImportCheck(@RequestBody UploadParam param) {
        return this.iUploadService.singleImportCheck(param);
    }

    @JLoggable(value="\u67e5\u8be2\u4e0a\u4e00\u671f\u65f6\u671fcode")
    @PostMapping(value={"/queryLastPeriod"})
    @ApiOperation(value="\u67e5\u8be2\u4e0a\u4e00\u671f\u65f6\u671fcode")
    @NRContextBuild
    public String queryLastPeriod(@Valid @RequestBody JtableContext jtableContext) {
        return this.cpoyErrorDesService.queryLastPeriod(jtableContext);
    }

    @JLoggable(value="\u540c\u6b65\u4e0a\u671f\u51fa\u9519\u8bf4\u660e")
    @PostMapping(value={"/copyErrorDesfromLastPeriod"})
    @ApiOperation(value="\u540c\u6b65\u4e0a\u671f\u51fa\u9519\u8bf4\u660e")
    @NRContextBuild
    public AsyncTaskInfo copyErrorDesToLastPeriod(@Valid @RequestBody JtableContext jtableContext) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(jtableContext.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)jtableContext));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new CopyPeriodDesAsyncTaskExecutor());
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u5e01\u79cd\u95f4\u540c\u6b65\u51fa\u9519\u8bf4\u660e")
    @PostMapping(value={"/copyErrorDesToOtherCurrency"})
    @ApiOperation(value="\u5e01\u79cd\u95f4\u540c\u6b65\u51fa\u9519\u8bf4\u660e")
    @NRContextBuild
    public AsyncTaskInfo copyErrorDesToOtherCurrency(@Valid @RequestBody JtableContext jtableContext) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(jtableContext.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)jtableContext));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new CopyCurrencyDesAsyncTaskExecutor());
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u6267\u884c\u8de8\u5355\u4f4d\u53e3\u5f84\u6279\u91cf\u590d\u5236", ignoreArgs=false)
    @RequestMapping(value={"/batchCopyBTWEntity"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u8de8\u5355\u4f4d\u53e3\u5f84\u6279\u91cf\u590d\u5236")
    @NRContextBuild
    public AsyncTaskInfo batchCopyBTWENtity(@Valid @RequestBody BatchCopyBTWEntityInfo batchCopyBTWEntityInfo) throws InterruptedException {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(batchCopyBTWEntityInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(batchCopyBTWEntityInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)batchCopyBTWEntityInfo)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchCopyBTWEntityAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u975e\u5f55\u5165\u73af\u5883\u4e0b\u67e5\u8be2\u5141\u8bb8\u586b\u5199\u51fa\u9519\u8bf4\u660e\u7684\u5ba1\u6838\u7c7b\u578b", ignoreArgs=false)
    @RequestMapping(value={"/queryAllowAddErrDes/{formSchemeKey}"}, method={RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value="\u975e\u5f55\u5165\u73af\u5883\u4e0b\u67e5\u8be2\u5141\u8bb8\u586b\u5199\u51fa\u9519\u8bf4\u660e\u7684\u5ba1\u6838\u7c7b\u578b")
    @NRContextBuild
    public List<String> queryAllowAddErrDes(@PathVariable(value="formSchemeKey") String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        String value = this.taskOptionController.getValue(taskDefine.getKey(), "ALLOW_ADD_ERROR_DES");
        if (Objects.isNull(value) || org.apache.commons.lang3.StringUtils.isBlank((CharSequence)value)) {
            return new ArrayList<String>();
        }
        TypeReference<List<String>> typeReference = new TypeReference<List<String>>(){};
        return (List)JacksonUtils.jsonToObject((String)value, (TypeReference)typeReference);
    }

    public static String tofakeGUID(String string) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException nsae) {
            throw new InternalError("MD5 not supported", nsae);
        }
        byte[] data = md.digest(string.getBytes());
        assert (data.length == 16) : "data must be 16 bytes in length";
        StringBuffer str = new StringBuffer(32);
        for (int i = 0; i < data.length; ++i) {
            int code = data[i] >> 4 & 0xF;
            str.append(HEX_CHARS[code]);
            code = data[i] & 0xF;
            str.append(HEX_CHARS[code]);
        }
        return str.toString();
    }

    @JLoggable(value="\u67e5\u8be2\u53ef\u9009\u60c5\u666f", ignoreArgs=false)
    @RequestMapping(value={"/querySeletableDims"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u53ef\u9009\u60c5\u666f")
    @NRContextBuild
    public List<DimensionObj> querySeletableDims(@RequestBody JtableContext context) {
        ArrayList<DimensionObj> result = new ArrayList<DimensionObj>();
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(this.dimensionCollectionUtil.getDimensionCollection(context.getDimensionSet(), context.getFormSchemeKey()));
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(context.getFormSchemeKey());
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        String periodDimensionName = this.dataAccesslUtil.getPeriodDimensionName(entityId);
        List dimEntities = this.entityUtil.getDimEntities(formScheme);
        for (EntityData dimEntity : dimEntities) {
            String originalDimValue;
            String dimensionName = dimEntity.getDimensionName();
            String id = this.formSchemeService.getDimAttributeByReportDim(context.getFormSchemeKey(), dimEntity.getKey());
            if (!org.springframework.util.StringUtils.hasText(id) || !this.entityMetaService.getEntityModel(formScheme.getDw()).getAttribute(id).isMultival() || (originalDimValue = ((DimensionValue)context.getDimensionSet().get(dimensionName)).getValue()).equals("PROVIDER_BASECURRENCY") || originalDimValue.equals("PROVIDER_PBASECURRENCY")) continue;
            Object dimValues = dimensionValueSet.getValue(dimensionName);
            ArrayList<String> split = new ArrayList<String>();
            if (dimValues instanceof String) {
                if (!org.springframework.util.StringUtils.hasText((String)dimValues)) continue;
                for (String value : ((String)dimValues).split(";")) {
                    split.add(value);
                }
            } else {
                split.addAll((ArrayList)dimValues);
            }
            if (split.size() <= 1) continue;
            String dimensionTitle = "";
            ArrayList<EntityDataObj> dims = new ArrayList<EntityDataObj>();
            if ("ADJUST_ENTITY_KIND".equals(dimEntity.getKind())) {
                dimensionTitle = "\u8c03\u6574\u671f";
                String dataScheme = this.iRunTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
                List adjustPeriods = this.adjustPeriodService.queryAdjustPeriods(dataScheme, ((DimensionValue)context.getDimensionSet().get(periodDimensionName)).getValue());
                HashSet valueSet = new HashSet(split);
                adjustPeriods.forEach(adjustPeriod -> {
                    if (valueSet.contains(adjustPeriod.getCode())) {
                        dims.add(new EntityDataObj(adjustPeriod.getCode(), adjustPeriod.getTitle()));
                    }
                });
            } else {
                if (dimEntity.getEntityDefine() != null) {
                    dimensionTitle = dimEntity.getEntityDefine().getTitle();
                }
                Date entityQueryVersionDate = this.entityUtil.period2Date(formScheme.getDateTime(), ((DimensionValue)context.getDimensionSet().get(periodDimensionName)).getValue());
                IEntityQuery query = this.entityUtil.getEntityQuery(dimEntity.getKey(), entityQueryVersionDate, dimensionValueSet, null);
                query.sorted(true);
                query.sortedByQuery(false);
                ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                try {
                    IEntityTable entityTable = query.executeReader((IContext)executorContext);
                    entityTable.getAllRows().forEach(row -> dims.add(new EntityDataObj(row.getEntityKeyData(), row.getTitle())));
                }
                catch (Exception e) {
                    logger.error("\u5b9e\u4f53ID\u4e3a{}\u7684\u5b9e\u4f53\u6570\u636e\u672a\u627e\u5230:{}", dimEntity.getKey(), e.getMessage(), e);
                }
            }
            result.add(new DimensionObj(dimensionName, dimensionTitle, dims));
        }
        return result;
    }

    @JLoggable(value="\u67e5\u8be2\u53ef\u9009\u60c5\u666f", ignoreArgs=false)
    @RequestMapping(value={"/queryBatchCheckObj"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u53ef\u9009\u60c5\u666f")
    @NRContextBuild
    public BatchCheckObj queryBatchCheckObj(@RequestBody BatchCheckInfo batchCheckInfo) {
        BatchCheckObj result = new BatchCheckObj();
        result.setAllowAddErrDesCheckTypeList(this.queryAllowAddErrDes(batchCheckInfo.getContext().getFormSchemeKey()));
        result.setDimensionObjList(this.querySeletableDims(batchCheckInfo.getContext()));
        return result;
    }

    @JLoggable(value="\u516c\u5f0f\u76d1\u63a7", ignoreArgs=false)
    @PostMapping(value={"/fmlMonitoring"})
    @ApiOperation(value="\u516c\u5f0f\u76d1\u63a7", notes="\u516c\u5f0f\u76d1\u63a7")
    @NRContextBuild
    public AsyncTaskInfo fmlMonitoring(@Valid @RequestBody FmlMonitorAndDebugParam fmlMonitorAndDebugParam) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(fmlMonitorAndDebugParam.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(fmlMonitorAndDebugParam.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)fmlMonitorAndDebugParam));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new FmlMonitoringAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @RequestMapping(value={"/preview_fmlMonitoring_result"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u516c\u5f0f\u76d1\u63a7\u6982\u8ff0\u9884\u89c8")
    @NRContextBuild
    public String previewFmlMonitoringResult(@RequestBody FmlMonitoringExportParam fmlMonitoringExportParam) {
        return this.fmlMonitorAndDebugService.previewFmlMonitorResult(fmlMonitoringExportParam.getAsyncTaskId());
    }

    @CrossOrigin(value={"*"})
    @PostMapping(value={"/export_fmlMonitoring_result"})
    @ApiOperation(value="\u516c\u5f0f\u76d1\u63a7\u5185\u5bb9\u5bfc\u51fa")
    @NRContextBuild
    public void exportFmlMonitoringResult(@RequestBody FmlMonitoringExportParam fmlMonitoringExportParam, HttpServletResponse response, HttpServletRequest request) {
        try {
            List<ExportData> exportData = this.fmlMonitorAndDebugService.exportFmlMonitorResult(fmlMonitoringExportParam.getAsyncTaskId());
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String formatDate = dateFormat.format(date);
            String resultLocation = BatchExportConsts.EXPORTDIR + BatchExportConsts.SEPARATOR + NpContextHolder.getContext().getUser().getName() + BatchExportConsts.SEPARATOR + formatDate + BatchExportConsts.SEPARATOR + UUID.randomUUID().toString();
            File filei = new File(FilenameUtils.normalize(resultLocation));
            if (!filei.exists()) {
                filei.mkdirs();
            }
            PathUtils.validatePathManipulation((String)(resultLocation + BatchExportConsts.SEPARATOR + fmlMonitoringExportParam.getUnitTitle()));
            File file = new File(FilenameUtils.normalize(resultLocation + BatchExportConsts.SEPARATOR + fmlMonitoringExportParam.getUnitTitle() + ".zip"));
            try (FileOutputStream fileOutputStream = new FileOutputStream(FilenameUtils.normalize(resultLocation + BatchExportConsts.SEPARATOR + fmlMonitoringExportParam.getUnitTitle() + ".zip"));
                 ZipOutputStream zos = new ZipOutputStream(fileOutputStream);){
                for (ExportData data : exportData) {
                    byte[] databytes;
                    if (data == null || null == (databytes = data.getData()) || databytes.length <= 0) continue;
                    byte[] bufs = new byte[0xA00000];
                    String filePath = data.getFileName();
                    zos.putNextEntry(new ZipEntry(filePath));
                    ByteArrayInputStream swapStream = new ByteArrayInputStream(data.getData());
                    Throwable throwable = null;
                    try {
                        BufferedInputStream bis = new BufferedInputStream(swapStream, 10240);
                        Throwable throwable2 = null;
                        try {
                            int read = 0;
                            while ((read = bis.read(bufs, 0, 10240)) != -1) {
                                zos.write(bufs, 0, read);
                            }
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
            }
            byte[] bytes = new byte[(int)file.length()];
            try (FileInputStream fis = new FileInputStream(file);){
                fis.read(bytes);
            }
            HtmlUtils.validateHeaderValue((String)("attachment; filename*=UTF-8''" + URLEncoder.encode(fmlMonitoringExportParam.getUnitTitle())));
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(fmlMonitoringExportParam.getUnitTitle(), "UTF-8"));
            response.setContentType("application/octet-stream");
            var13_15 = null;
            try (ServletOutputStream outputStream = response.getOutputStream();){
                outputStream.write(bytes);
            }
            catch (Throwable throwable) {
                var13_15 = throwable;
                throw throwable;
            }
            response.flushBuffer();
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        catch (SecurityContentException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value={"/queryFmlDebugTitle"}, method={RequestMethod.POST})
    public String getMasterTitle(@RequestBody Map<String, Object> param) {
        String title = "";
        try {
            String taskId = param.get("taskId").toString();
            String dims = param.get("dims").toString();
            DimensionValueSet dim = new DimensionValueSet();
            dim.parseString(dims);
            DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(dim, param.get("formSchemeKey").toString());
            DimensionCombination dimensionCombination = (DimensionCombination)dimensionCollection.getDimensionCombinations().get(0);
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
            boolean isCustom = taskDefine.getPeriodType() == PeriodType.CUSTOM;
            String[] masters = taskDefine.getMasterEntitiesKey().split(";");
            StringBuilder builder = new StringBuilder();
            builder.append(this.fmlMonitorAndDebugService.getDimsTitle(taskId, dimensionCombination));
            String formulaSchemeKey = param.get("formulaSchemeKey").toString();
            FormulaSchemeDefine formulaSchemeDefine = this.runtimeFormulaController.queryFormulaSchemeDefine(formulaSchemeKey);
            builder.append("  \u516c\u5f0f\u65b9\u6848\uff1a" + formulaSchemeDefine.getTitle() + "\uff1b   ");
            title = builder.toString();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return title;
    }

    @JLoggable(value="\u67e5\u8be2\u516c\u5f0f\u5217\u8868", ignoreArgs=false)
    @RequestMapping(value={"/queryFmlList"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u5217\u8868")
    @NRContextBuild
    public List<FormulaData> queryFmlList(@RequestBody FmlMonitorAndDebugParam fmlMonitorAndDebugParam) {
        return this.fmlMonitorAndDebugService.queryFmlList(fmlMonitorAndDebugParam);
    }

    @JLoggable(value="\u67e5\u8be2\u516c\u5f0f\u8c03\u8bd5\u7ed3\u679c", ignoreArgs=false)
    @RequestMapping(value={"/queryFmlValue"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u8c03\u8bd5\u7ed3\u679c")
    @NRContextBuild
    public FmlDebugNode queryFmlValue(@RequestBody FmlMonitorAndDebugParam fmlMonitorAndDebugParam) {
        return this.fmlMonitorAndDebugService.queryFmlValue(fmlMonitorAndDebugParam);
    }
}

