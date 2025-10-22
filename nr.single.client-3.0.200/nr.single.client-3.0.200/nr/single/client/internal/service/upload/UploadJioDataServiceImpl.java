/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataentry.bean.JIOUnitImportResult
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider
 *  com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams
 *  com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager
 *  com.jiuqi.nr.dataentry.util.Consts$FormAccessLevel
 *  com.jiuqi.nr.dataentry.util.DataEntryUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.period.service.PeriodDataService
 *  com.jiuqi.nr.period.service.PeriodService
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.Ini
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  javax.annotation.Resource
 *  nr.single.data.util.TaskFileDataOperateUtil
 *  nr.single.data.util.bean.SingleAttachmentFailFile
 *  nr.single.data.util.bean.SingleAttachmentResult
 *  nr.single.data.util.service.ISingleAttachmentService
 *  nr.single.map.configurations.bean.DataImportRule
 *  nr.single.map.configurations.bean.UnitState
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.SingleDataError
 *  nr.single.map.data.SingleFieldFileInfo
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.bean.RepeatImportParam
 *  nr.single.map.data.exception.SingleDataCorpException
 *  nr.single.map.data.exception.SingleDataException
 *  nr.single.map.data.exception.SingleDataRuntimeException
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.facade.dataset.ReportRegionDataSet
 *  nr.single.map.data.facade.dataset.ReportRegionDataSetList
 */
package nr.single.client.internal.service.upload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.dataentry.bean.JIOUnitImportResult;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.service.PeriodDataService;
import com.jiuqi.nr.period.service.PeriodService;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.Ini;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.annotation.Resource;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.client.internal.service.upload.UploadJioDataUtil;
import nr.single.client.internal.service.upload.UploadTypeJioServiceImpl;
import nr.single.client.service.export.IBatchDeleteRegionDataService;
import nr.single.client.service.upload.IUploadDataLogService;
import nr.single.client.service.upload.IUploadJioDataService;
import nr.single.data.util.TaskFileDataOperateUtil;
import nr.single.data.util.bean.SingleAttachmentFailFile;
import nr.single.data.util.bean.SingleAttachmentResult;
import nr.single.data.util.service.ISingleAttachmentService;
import nr.single.map.configurations.bean.DataImportRule;
import nr.single.map.configurations.bean.UnitState;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.PathUtil;
import nr.single.map.data.SingleDataError;
import nr.single.map.data.SingleFieldFileInfo;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.bean.RepeatImportParam;
import nr.single.map.data.exception.SingleDataCorpException;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.exception.SingleDataRuntimeException;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.dataset.ReportRegionDataSet;
import nr.single.map.data.facade.dataset.ReportRegionDataSetList;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadJioDataServiceImpl
implements IUploadJioDataService {
    private static final Logger logger = LoggerFactory.getLogger(UploadTypeJioServiceImpl.class);
    @Resource
    private IRunTimeViewController runtimeView;
    @Autowired
    private ReadWriteAccessProvider readWriteAccessProvider;
    @Autowired
    private ISingleAttachmentService singleAttachService;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private IDataentryFlowService flowService;
    @Autowired
    private TreeState flowerTreeStateService;
    @Autowired
    private IEntityAuthorityService iEntityAuthorityService;
    @Autowired
    private SystemIdentityService systemdentityService;
    @Autowired
    private IBatchDeleteRegionDataService deleteRegionService;
    @Autowired
    private IUploadDataLogService uploadLogService;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private PeriodService periodService;
    @Autowired
    private PeriodDataService periodDataService;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;

    @Override
    public void setImportTypeByParam(TaskDataContext context, UploadParam param) throws SingleDataException {
        boolean needCheckRepeat = false;
        boolean needSelectImport = false;
        int repeatImportType = 1;
        if (param.getVariableMap() != null) {
            if (param.getVariableMap().containsKey("jioNeedCheckAuth")) {
                boolean needCheckAuth = (Boolean)param.getVariableMap().get("jioNeedCheckAuth");
                context.setNeedCheckAuth(needCheckAuth);
            }
            if (param.getVariableMap().containsKey("jioNeedCheckRepeat")) {
                needCheckRepeat = (Boolean)param.getVariableMap().get("jioNeedCheckRepeat");
            }
            if (param.getVariableMap().containsKey("jioRepeatImportType")) {
                repeatImportType = (Integer)param.getVariableMap().get("jioRepeatImportType");
            } else if (needCheckRepeat) {
                repeatImportType = 2;
            }
            RepeatImportParam jioRepeatParam = null;
            if (param.getVariableMap().containsKey("jioRepeatParm")) {
                try {
                    jioRepeatParam = UploadJioDataUtil.getRepeatImportParam(param, "jioRepeatParm");
                }
                catch (IllegalAccessException | InvocationTargetException e) {
                    context.error(logger, e.getMessage(), (Throwable)e);
                    throw new SingleDataException(e.getMessage(), (Throwable)e);
                }
            } else if (param.getVariableMap().containsKey("jioRepeatParm_Object")) {
                jioRepeatParam = (RepeatImportParam)param.getVariableMap().get("jioRepeatParm_Object");
            }
            context.setJioRepeatParam(jioRepeatParam);
            context.setNeedCheckRepeat(needCheckRepeat);
            if (param.getVariableMap().containsKey("jioNeedSelectImport")) {
                needSelectImport = (Boolean)param.getVariableMap().get("jioNeedSelectImport");
            }
            context.setNeedSelectImport(needSelectImport);
            RepeatImportParam jioSelectParam = null;
            if (param.getVariableMap().containsKey("jioSelectImportParm")) {
                if ("1".equalsIgnoreCase((String)param.getVariableMap().get("jioSelectImportParmSourceType"))) {
                    try {
                        jioSelectParam = UploadJioDataUtil.getNetRepeatImportParam(param, "jioSelectImportParm");
                    }
                    catch (JsonProcessingException e) {
                        context.error(logger, e.getMessage(), (Throwable)e);
                        throw new SingleDataException(e.getMessage(), (Throwable)e);
                    }
                }
                try {
                    jioSelectParam = UploadJioDataUtil.getRepeatImportParam(param, "jioSelectImportParm");
                }
                catch (IllegalAccessException | InvocationTargetException e) {
                    context.error(logger, e.getMessage(), (Throwable)e);
                    throw new SingleDataException(e.getMessage(), (Throwable)e);
                }
            }
            context.setJioSelectImportParam(jioSelectParam);
            boolean needUploadCheckInfo = true;
            if (param.getVariableMap().containsKey("jioNeedCheckInfo")) {
                needUploadCheckInfo = (Boolean)param.getVariableMap().get("jioNeedCheckInfo");
            }
            context.setNeedCheckInfo(needUploadCheckInfo);
        }
        context.setNeedCheckRepeat(needCheckRepeat);
        context.setNeedSelectImport(needSelectImport);
        context.setRepeatImportType(repeatImportType);
    }

    @Override
    public FormReadWriteAccessData formAuthCheck(UploadParam param, Map<String, DimensionValue> dimensionValueSet, ReadWriteAccessCacheManager accessCacheManager, List<String> formKeys) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(param.getTaskKey());
        jtableContext.setFormSchemeKey(param.getFormSchemeKey());
        jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
        jtableContext.setDimensionSet(dimensionValueSet);
        jtableContext.setVariableMap(param.getVariableMap());
        FormReadWriteAccessData formReadWriteAccessData = new FormReadWriteAccessData(jtableContext, Consts.FormAccessLevel.FORM_DATA_WRITE, formKeys);
        return this.readWriteAccessProvider.getAccessForms(formReadWriteAccessData, accessCacheManager);
    }

    @Override
    public List<SingleFieldFileInfo> getFieldFileInfosFromDataSet(TaskDataContext context, ReportRegionDataSetList dataSets, Map<String, FieldData> fieldDefineMap) {
        ArrayList<SingleFieldFileInfo> list = new ArrayList<SingleFieldFileInfo>();
        for (String fieldFlag : dataSets.getFieldDataSetMap().keySet()) {
            ReportRegionDataSet dataSet = (ReportRegionDataSet)dataSets.getFieldDataSetMap().get(fieldFlag);
            SingleFileFieldInfo field = (SingleFileFieldInfo)dataSet.getFieldMap().get(fieldFlag);
            String fieldName = field.getFieldCode();
            SingleFileTableInfo singleTableInfo = (SingleFileTableInfo)dataSet.getTableInfo();
            SingleFieldFileInfo fieldFileInfo = new SingleFieldFileInfo();
            fieldFileInfo.setDataSchemeKey(context.getDataSchemeKey());
            if (fieldDefineMap != null && fieldDefineMap.containsKey(fieldFlag)) {
                fieldFileInfo.setFieldKey(fieldDefineMap.get(fieldFlag).getFieldKey());
            }
            fieldFileInfo.setFormKey(null);
            fieldFileInfo.setFormCode(null);
            fieldFileInfo.setFormSchemeKey(null);
            fieldFileInfo.setDimensionSet(null);
            fieldFileInfo.setTaskKey(context.getTaskKey());
            if (singleTableInfo == null && (fieldName.equalsIgnoreCase("FJFIELD") || fieldName.equalsIgnoreCase("WZFIELD"))) {
                list.add(fieldFileInfo);
            }
            if (fieldName.equalsIgnoreCase("FJFIELD") && (field.getFieldType() == FieldType.FIELD_TYPE_FILE || singleTableInfo != null && singleTableInfo.getSingleTableType() == 3)) {
                list.add(fieldFileInfo);
                continue;
            }
            if (fieldName.equalsIgnoreCase("WZFIELD") && (field.getFieldType() == FieldType.FIELD_TYPE_FILE || singleTableInfo != null && singleTableInfo.getSingleTableType() == 2)) {
                list.add(fieldFileInfo);
                continue;
            }
            if (field.getFieldType() == FieldType.FIELD_TYPE_FILE) {
                list.add(fieldFileInfo);
                continue;
            }
            if (field.getFieldType() != FieldType.FIELD_TYPE_PICTURE) continue;
            list.add(fieldFileInfo);
        }
        return list;
    }

    @Override
    public void batchMarkDeleteFileDataBeforeImport(TaskDataContext context, ReportRegionDataSetList dataSets, SingleFieldFileInfo formFileInfo, Map<String, FieldData> fieldDefineMap) {
        this.markDeleteFileDataBeforeImport2(context, dataSets, formFileInfo, fieldDefineMap, true);
    }

    @Override
    public void markDeleteFileDataBeforeImport(TaskDataContext context, ReportRegionDataSetList dataSets, SingleFieldFileInfo formFileInfo, Map<String, FieldData> fieldDefineMap) {
        this.markDeleteFileDataBeforeImport2(context, dataSets, formFileInfo, fieldDefineMap, false);
    }

    private void markDeleteFileDataBeforeImport2(TaskDataContext context, ReportRegionDataSetList dataSets, SingleFieldFileInfo formFileInfo, Map<String, FieldData> fieldDefineMap, boolean isBatch) {
        DimensionCollection dimensionCollection = null;
        if (isBatch) {
            dimensionCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)formFileInfo.getDimensionSet(), (String)context.getFormSchemeKey());
        }
        for (String fieldFlag : dataSets.getFieldDataSetMap().keySet()) {
            ReportRegionDataSet dataSet = (ReportRegionDataSet)dataSets.getFieldDataSetMap().get(fieldFlag);
            SingleFileFieldInfo field = (SingleFileFieldInfo)dataSet.getFieldMap().get(fieldFlag);
            String fieldName = field.getFieldCode();
            SingleFileTableInfo singleTableInfo = (SingleFileTableInfo)dataSet.getTableInfo();
            SingleFieldFileInfo fieldFileInfo = new SingleFieldFileInfo();
            fieldFileInfo.setDataSchemeKey(formFileInfo.getDataSchemeKey());
            if (fieldDefineMap != null && fieldDefineMap.containsKey(fieldFlag)) {
                fieldFileInfo.setFieldKey(fieldDefineMap.get(fieldFlag).getFieldKey());
            }
            fieldFileInfo.setFormKey(formFileInfo.getFormKey());
            fieldFileInfo.setFormCode(formFileInfo.getFormCode());
            fieldFileInfo.setFormSchemeKey(formFileInfo.getFormSchemeKey());
            fieldFileInfo.setDimensionSet(formFileInfo.getDimensionSet());
            fieldFileInfo.setTaskKey(formFileInfo.getTaskKey());
            if (singleTableInfo == null && (fieldName.equalsIgnoreCase("FJFIELD") || fieldName.equalsIgnoreCase("WZFIELD"))) {
                singleTableInfo = this.getSingleTableInfoByFormCode(context, field.getTableCode());
            }
            if (fieldName.equalsIgnoreCase("FJFIELD") && (field.getFieldType() == FieldType.FIELD_TYPE_FILE || singleTableInfo != null && singleTableInfo.getSingleTableType() == 3)) {
                if (isBatch) {
                    this.batchMarkDeleteFile(context, fieldFileInfo, dimensionCollection);
                    continue;
                }
                this.markDeleteFile(context, fieldFileInfo);
                continue;
            }
            if (fieldName.equalsIgnoreCase("WZFIELD") && (field.getFieldType() == FieldType.FIELD_TYPE_FILE || singleTableInfo != null && singleTableInfo.getSingleTableType() == 2)) {
                if (isBatch) {
                    this.batchMarkDeleteFile(context, fieldFileInfo, dimensionCollection);
                    continue;
                }
                this.markDeleteFile(context, fieldFileInfo);
                continue;
            }
            if (field.getFieldType() == FieldType.FIELD_TYPE_FILE) {
                if (isBatch) {
                    this.batchMarkDeleteFile(context, fieldFileInfo, dimensionCollection);
                    continue;
                }
                this.markDeleteFile(context, fieldFileInfo);
                continue;
            }
            if (field.getFieldType() != FieldType.FIELD_TYPE_PICTURE) continue;
            if (isBatch) {
                this.batchMarkDeleteFile(context, fieldFileInfo, dimensionCollection);
                continue;
            }
            this.markDeleteFile(context, fieldFileInfo);
        }
    }

    @Override
    public void setDataBeforeImport(TaskDataContext context, ReportRegionDataSetList dataSets, String zdm, SingleFieldFileInfo formFileInfo, Map<String, FieldData> fieldDefineMap) throws SingleDataCorpException {
        for (String fieldFlag : dataSets.getFieldDataSetMap().keySet()) {
            ReportRegionDataSet dataSet = (ReportRegionDataSet)dataSets.getFieldDataSetMap().get(fieldFlag);
            SingleFileFieldInfo field = (SingleFileFieldInfo)dataSet.getFieldMap().get(fieldFlag);
            String fieldName = field.getFieldCode();
            SingleFileTableInfo singleTableInfo = (SingleFileTableInfo)dataSet.getTableInfo();
            SingleFieldFileInfo fieldFileInfo = new SingleFieldFileInfo();
            fieldFileInfo.setDataSchemeKey(formFileInfo.getDataSchemeKey());
            if (fieldDefineMap != null && fieldDefineMap.containsKey(fieldFlag)) {
                fieldFileInfo.setFieldKey(fieldDefineMap.get(fieldFlag).getFieldKey());
            }
            fieldFileInfo.setFormKey(formFileInfo.getFormKey());
            fieldFileInfo.setFormCode(formFileInfo.getFormCode());
            fieldFileInfo.setFormSchemeKey(formFileInfo.getFormSchemeKey());
            fieldFileInfo.setDimensionSet(formFileInfo.getDimensionSet());
            fieldFileInfo.setTaskKey(formFileInfo.getTaskKey());
            fieldFileInfo.setZdm(zdm);
            if (singleTableInfo == null && (fieldName.equalsIgnoreCase("FJFIELD") || fieldName.equalsIgnoreCase("WZFIELD"))) {
                singleTableInfo = this.getSingleTableInfoByFormCode(context, field.getTableCode());
            }
            if (fieldName.equalsIgnoreCase("FJFIELD") && (field.getFieldType() == FieldType.FIELD_TYPE_FILE || singleTableInfo != null && singleTableInfo.getSingleTableType() == 3)) {
                this.setFJFieldValue(context, dataSets, dataSet, fieldFileInfo, field, fieldName, zdm);
                continue;
            }
            if (fieldName.equalsIgnoreCase("WZFIELD") && (field.getFieldType() == FieldType.FIELD_TYPE_FILE || singleTableInfo != null && singleTableInfo.getSingleTableType() == 2)) {
                this.setWZFieldValue(context, dataSets, dataSet, fieldFileInfo, field, fieldName, zdm);
                continue;
            }
            if (field.getFieldType() == FieldType.FIELD_TYPE_FILE) {
                this.setFileFieldValue(context, dataSets, dataSet, fieldFileInfo, field, fieldName, zdm);
                continue;
            }
            if (field.getFieldType() == FieldType.FIELD_TYPE_PICTURE) {
                this.setPictureFieldValue(context, dataSets, dataSet, fieldFileInfo, field, fieldName, zdm);
                continue;
            }
            if (field.getFieldType() != FieldType.FIELD_TYPE_TEXT) continue;
            this.setTextFieldValue(context, dataSets, dataSet, fieldFileInfo, field, fieldName, zdm);
        }
    }

    private void markDeleteFile(TaskDataContext context, SingleFieldFileInfo fieldFileInfo) {
    }

    private void batchMarkDeleteFile(TaskDataContext context, SingleFieldFileInfo fieldFileInfo, DimensionCollection dimensionCollection) {
    }

    private void setFJFieldValue(TaskDataContext context, ReportRegionDataSetList dataSets, ReportRegionDataSet dataSet, SingleFieldFileInfo fieldFileInfo, SingleFileFieldInfo field, String fieldName, String zdm) throws SingleDataCorpException {
        try {
            String docFilePath = PathUtil.createNewPath((String)dataSets.getFilePath(), (String)"SYS_DOC");
            String zdmDocPath = SinglePathUtil.getNewPath((String)docFilePath, (String)zdm);
            if (context.getFjZdmDirs().containsKey(zdm)) {
                zdmDocPath = (String)context.getFjZdmDirs().get(zdm);
                zdmDocPath = SinglePathUtil.getPath((String)zdmDocPath);
            }
            String fjIniFileName = zdmDocPath + field.getTableCode() + ".Ini";
            String groupKey = null;
            String groupTypeCode = null;
            if (PathUtil.getFileExists((String)fjIniFileName)) {
                Ini ini = new Ini();
                try {
                    ini.loadIniFile(fjIniFileName);
                }
                catch (IOException e) {
                    context.error(logger, e.getMessage(), (Throwable)e);
                }
                groupKey = ini.readString(field.getTableCode(), "GroupKey", "");
                groupTypeCode = ini.readString(field.getTableCode(), "GroupType", "");
            }
            if (StringUtils.isNotEmpty(groupTypeCode) && StringUtils.isNotEmpty(groupKey)) {
                if ("1".equalsIgnoreCase(groupTypeCode)) {
                    dataSet.getCurDataRow().setValue(fieldName, groupKey);
                } else if ("2".equalsIgnoreCase(groupTypeCode)) {
                    fieldFileInfo.setGroupType(2);
                    fieldFileInfo.setGroupKey(groupKey);
                    this.setFJFieldValueByFileNoExt(context, dataSets, dataSet, fieldFileInfo, fieldName, zdm, field.getTableCode());
                } else {
                    this.setFJFieldValueByFileNoExt(context, dataSets, dataSet, fieldFileInfo, fieldName, zdm, field.getTableCode());
                }
            } else {
                this.setFJFieldValueByFileNoExt(context, dataSets, dataSet, fieldFileInfo, fieldName, zdm, field.getTableCode());
            }
        }
        catch (SingleDataException ex) {
            context.error(logger, ex.getMessage(), (Throwable)ex);
            SingleDataCorpException re = new SingleDataCorpException(ex.getMessage(), (Throwable)ex);
            re.setFieldInfo(zdm, field.getTableCode(), fieldName);
            throw re;
        }
        catch (SingleFileException e) {
            context.error(logger, e.getMessage(), (Throwable)e);
            SingleDataCorpException re = new SingleDataCorpException(e.getMessage(), (Throwable)e);
            re.setFieldInfo(zdm, field.getTableCode(), fieldName);
            throw re;
        }
    }

    private void setWZFieldValue(TaskDataContext context, ReportRegionDataSetList dataSets, ReportRegionDataSet dataSet, SingleFieldFileInfo fieldFileInfo, SingleFileFieldInfo field, String fieldName, String zdm) throws SingleDataCorpException {
        block7: {
            try {
                String wzFileName;
                String docFilePath = PathUtil.createNewPath((String)dataSets.getFilePath(), (String)"SYS_DOC");
                String zdmDocPath = SinglePathUtil.getNewPath((String)docFilePath, (String)zdm);
                if (context.getFjZdmDirs().containsKey(zdm)) {
                    zdmDocPath = (String)context.getFjZdmDirs().get(zdm);
                    zdmDocPath = SinglePathUtil.getPath((String)zdmDocPath);
                }
                if (!PathUtil.getFileExists((String)(wzFileName = zdmDocPath + field.getTableCode() + ".RTF"))) break block7;
                try {
                    ArrayList<String> singleFiles = new ArrayList<String>();
                    singleFiles.add(wzFileName);
                    String groupFileKey = UUID.randomUUID().toString();
                    fieldFileInfo.setGroupKey(groupFileKey);
                    groupFileKey = this.singleAttachService.uploadSingleFiles(singleFiles, fieldFileInfo);
                    if (!dataSet.getCurDataRow().getColumns().containColumName(fieldName)) {
                        dataSet.getCurDataRow().getColumns().addColumn(fieldName, 9);
                    }
                    dataSet.getCurDataRow().setValue(fieldName, (Object)groupFileKey);
                }
                catch (Exception ex) {
                    context.error(logger, ex.getMessage(), (Throwable)ex);
                    SingleDataCorpException re = new SingleDataCorpException(ex.getMessage(), (Throwable)ex);
                    re.setFieldInfo(zdm, field.getTableCode(), fieldName);
                    throw re;
                }
            }
            catch (SingleDataCorpException e1) {
                throw e1;
            }
            catch (SingleFileException e) {
                context.error(logger, e.getMessage(), (Throwable)e);
                SingleDataCorpException re = new SingleDataCorpException(e.getMessage(), (Throwable)e);
                re.setFieldInfo(zdm, field.getTableCode(), fieldName);
                throw re;
            }
        }
    }

    private void setFileFieldValue(TaskDataContext context, ReportRegionDataSetList dataSets, ReportRegionDataSet dataSet, SingleFieldFileInfo fieldFileInfo, SingleFileFieldInfo field, String fieldName, String zdm) throws SingleDataCorpException {
        String fieldValue = null;
        Object obj = dataSet.getCurDataRow().getValue(fieldName);
        if (null != obj) {
            fieldValue = obj.toString();
            if (StringUtils.isNotEmpty((String)fieldValue)) {
                String groupTypeCode = null;
                if (fieldValue.contains(";")) {
                    String[] newValues = fieldValue.split(";");
                    if (newValues.length > 0) {
                        fieldValue = newValues[0];
                    }
                    if (newValues.length > 2) {
                        groupTypeCode = newValues[2];
                    }
                }
                try {
                    if (StringUtils.isNotEmpty(groupTypeCode) && "T=1".equalsIgnoreCase(groupTypeCode)) {
                        dataSet.getCurDataRow().setValue(fieldName, (Object)fieldValue);
                    }
                    if (StringUtils.isNotEmpty(groupTypeCode) && "T=2".equalsIgnoreCase(groupTypeCode)) {
                        fieldFileInfo.setGroupType(2);
                        fieldFileInfo.setGroupKey(fieldValue);
                        this.setFJFieldValueByFileNoExt(context, dataSets, dataSet, fieldFileInfo, fieldName, zdm, fieldValue);
                    }
                    fieldFileInfo.setGroupType(0);
                    this.setFJFieldValueByFileNoExt(context, dataSets, dataSet, fieldFileInfo, fieldName, zdm, fieldValue);
                }
                catch (SingleDataException ex) {
                    context.error(logger, "\u5bfc\u5165\u9644\u4ef6\u6307\u6807" + fieldName + ",\u5355\u4f4d\uff1a" + zdm + ",\u5f02\u5e38\uff1a" + ex.getMessage(), (Throwable)ex);
                    SingleDataCorpException re = new SingleDataCorpException(ex.getMessage(), (Throwable)ex);
                    re.setFieldInfo(zdm, field.getTableCode(), fieldName);
                    throw re;
                }
            } else {
                dataSet.getCurDataRow().setValue(fieldName, null);
            }
        }
    }

    private void setFJFieldValueByFileNoExt(TaskDataContext context, ReportRegionDataSetList dataSets, ReportRegionDataSet dataSet, SingleFieldFileInfo fieldFileInfo, String fieldName, String zdm, String fileNoExt) throws SingleDataException {
        if (context.getFjUploadMode() == 0) {
            List singleFiles = TaskFileDataOperateUtil.getSingleFileInfoFormFileByZdm((TaskDataContext)context, (String)dataSets.getFilePath(), (String)fileNoExt, (String)zdm);
            if (null != singleFiles && !singleFiles.isEmpty()) {
                ArrayList singleFiles2 = new ArrayList();
                String groupFileKey = UUID.randomUUID().toString();
                if (fieldFileInfo.getGroupType() == 2 && StringUtils.isNotEmpty((String)fieldFileInfo.getGroupKey())) {
                    groupFileKey = fieldFileInfo.getGroupKey();
                }
                singleFiles2.addAll(singleFiles);
                fieldFileInfo.setGroupKey(groupFileKey);
                SingleAttachmentResult uploadResult = this.singleAttachService.uploadSingleFileInfosR(singleFiles2, fieldFileInfo);
                if (!uploadResult.isSuccess()) {
                    String entityKey = fieldFileInfo.getEntityKey();
                    if (StringUtils.isEmpty((String)entityKey)) {
                        if (fieldFileInfo.getDimensionSet() != null && StringUtils.isNotEmpty((String)context.getEntityCompanyType())) {
                            DimensionValue dim = (DimensionValue)fieldFileInfo.getDimensionSet().get(context.getEntityCompanyType());
                            if (dim != null) {
                                entityKey = dim.getValue();
                            }
                        } else if (StringUtils.isNotEmpty((String)fieldFileInfo.getZdm()) && context.getUploadEntityZdmKeyMap().containsKey(fieldFileInfo.getZdm())) {
                            entityKey = (String)context.getUploadEntityZdmKeyMap().get(fieldFileInfo.getZdm());
                        }
                    }
                    String errorMsg = this.getFailFileMessage(uploadResult);
                    SingleDataError errorItem = new SingleDataError("", fieldFileInfo.getFormCode(), errorMsg, "importFileFail", entityKey, null, entityKey);
                    errorItem.setFormKey(fieldFileInfo.getFormKey());
                    errorItem.setSingleCode(fieldFileInfo.getZdm());
                    context.recordLog(fieldFileInfo.getFormCode(), errorItem);
                } else {
                    context.updateAttachFileNumAsyn(singleFiles2.size());
                }
                dataSet.getCurDataRow().setValue(fieldName, (Object)groupFileKey);
            } else {
                dataSet.getCurDataRow().setValue(fieldName, null);
            }
        } else if (context.getFjUploadMode() == 1) {
            String groupFileKey = UUID.randomUUID().toString();
            if (fieldFileInfo.getGroupType() == 2 && StringUtils.isNotEmpty((String)fieldFileInfo.getGroupKey())) {
                groupFileKey = fieldFileInfo.getGroupKey();
            }
            fieldFileInfo.setGroupKey(groupFileKey);
            fieldFileInfo.setFjPath("DATA/SYS_DOC");
            fieldFileInfo.setFileName(fileNoExt + ".ZIP");
            context.addFieldFileInfo(fieldFileInfo);
            dataSet.getCurDataRow().setValue(fieldName, (Object)groupFileKey);
        }
    }

    private String getFailFileMessage(SingleAttachmentResult uploadResult) {
        StringBuilder errorMsgs = new StringBuilder();
        errorMsgs.append("\u9644\u4ef6\u5bfc\u5165\u5931\u8d25");
        if (StringUtils.isNotEmpty((String)uploadResult.getMessage())) {
            errorMsgs.append(",").append(uploadResult.getMessage());
        }
        if (!uploadResult.getFailedFileList().isEmpty()) {
            int aNum = 0;
            logger.info("\u9644\u4ef6\u5bfc\u5165\u5931\u8d25\uff0c\u6587\u4ef6\u6570\uff1a" + uploadResult.getFailedFileList().size());
            for (SingleAttachmentFailFile failFile : uploadResult.getFailedFileList()) {
                errorMsgs.append(",").append(failFile.getFileName());
                if (++aNum <= 10) continue;
                errorMsgs.append(",").append("...");
                break;
            }
        }
        return errorMsgs.toString();
    }

    private void setPictureFieldValue(TaskDataContext context, ReportRegionDataSetList dataSets, ReportRegionDataSet dataSet, SingleFieldFileInfo fieldFileInfo, SingleFileFieldInfo field, String fieldName, String zdm) throws SingleDataCorpException {
        String fieldValue = null;
        Object obj = dataSet.getCurDataRow().getValue(fieldName);
        if (null != obj) {
            fieldValue = obj.toString();
            try {
                if (StringUtils.isNotEmpty((String)fieldValue)) {
                    String picFilePath = TaskFileDataOperateUtil.getSingleFieldImgByZdm((TaskDataContext)context, (String)dataSets.getFilePath(), (String)fieldValue, (String)zdm);
                    if (PathUtil.getFileExists((String)picFilePath)) {
                        ArrayList<String> singleFiles = new ArrayList<String>();
                        singleFiles.add(picFilePath);
                        String groupFileKey = UUID.randomUUID().toString();
                        fieldFileInfo.setGroupKey(groupFileKey);
                        groupFileKey = this.singleAttachService.uploadSingleFiles(singleFiles, fieldFileInfo);
                        dataSet.getCurDataRow().setValue(fieldName, (Object)groupFileKey);
                    } else {
                        dataSet.getCurDataRow().setValue(fieldName, null);
                    }
                } else {
                    dataSet.getCurDataRow().setValue(fieldName, null);
                }
            }
            catch (SingleFileException ex) {
                context.error(logger, ex.getMessage(), (Throwable)ex);
                SingleDataCorpException re = new SingleDataCorpException(ex.getMessage(), (Throwable)ex);
                re.setFieldInfo(zdm, field.getTableCode(), fieldName);
                throw re;
            }
            catch (SingleDataException e) {
                context.error(logger, e.getMessage(), (Throwable)e);
                SingleDataCorpException re = new SingleDataCorpException(e.getMessage(), (Throwable)e);
                re.setFieldInfo(zdm, field.getTableCode(), fieldName);
                throw re;
            }
            catch (IOException e) {
                context.error(logger, e.getMessage(), (Throwable)e);
                SingleDataCorpException re = new SingleDataCorpException(e.getMessage(), (Throwable)e);
                re.setFieldInfo(zdm, field.getTableCode(), fieldName);
                throw re;
            }
        }
    }

    private void setTextFieldValue(TaskDataContext context, ReportRegionDataSetList dataSets, ReportRegionDataSet dataSet, SingleFieldFileInfo fieldFileInfo, SingleFileFieldInfo field, String fieldName, String zdm) throws SingleDataCorpException {
        String fieldValue = null;
        Object obj = dataSet.getCurDataRow().getValue(fieldName);
        if (null != obj && StringUtils.isNotEmpty((String)(fieldValue = obj.toString()))) {
            try {
                String textData = TaskFileDataOperateUtil.getSingleFieldTextByZdm((TaskDataContext)context, (String)dataSets.getFilePath(), (String)fieldValue, (String)zdm);
                dataSet.getCurDataRow().setValue(fieldName, (Object)textData);
            }
            catch (Exception ex) {
                context.error(logger, ex.getMessage(), (Throwable)ex);
                SingleDataCorpException re = new SingleDataCorpException(ex.getMessage(), (Throwable)ex);
                re.setFieldInfo(zdm, field.getTableCode(), fieldName);
                throw re;
            }
        }
    }

    private SingleFileTableInfo getSingleTableInfoByFormCode(TaskDataContext context, String formCode) {
        SingleFileTableInfo singleTableInfo = null;
        if (StringUtils.isNotEmpty((String)formCode) && context.getMapingCache().getMapConfig() != null) {
            for (SingleFileTableInfo v : context.getMapingCache().getMapConfig().getTableInfos()) {
                if (!formCode.equalsIgnoreCase(v.getSingleTableCode())) continue;
                singleTableInfo = v;
                break;
            }
        }
        return singleTableInfo;
    }

    @Override
    public void readUnitCount(TaskDataContext context, UploadParam param) {
        boolean isAdmin = this.systemdentityService.isAdmin();
        if (!isAdmin) {
            Set<String> canReadEntityKeys = this.getAuthEntityKeys(context, param.getDimensionSet(), 0);
            if (canReadEntityKeys != null) {
                context.setNetCorpCount(canReadEntityKeys.size());
                context.getCanReadNetEntityKeys().addAll(canReadEntityKeys);
            } else {
                context.setNetCorpCount(0);
            }
        }
    }

    @Override
    public void recordUnitState(TaskDataContext context, JIOImportResultObject result, UploadParam param) {
        if (context.getUploadEntityZdmKeyMap().size() > 0 && context.isNeedCheckAuth()) {
            Map<String, DimensionValue> dimensionSet = UploadJioDataUtil.getNewDimensionSet(param.getDimensionSet());
            HashMap<String, JIOUnitImportResult> errorMap = new HashMap<String, JIOUnitImportResult>();
            HashMap<String, JIOUnitImportResult> successMap = new HashMap<String, JIOUnitImportResult>();
            for (JIOUnitImportResult jioUnitImportResult : result.getErrorUnits()) {
                errorMap.put(null != jioUnitImportResult.getUnitKey() ? jioUnitImportResult.getUnitKey() : jioUnitImportResult.getUnitTitle(), jioUnitImportResult);
            }
            for (JIOUnitImportResult jioUnitImportResult : result.getSuccessUnits()) {
                successMap.put(null != jioUnitImportResult.getUnitKey() ? jioUnitImportResult.getUnitKey() : jioUnitImportResult.getUnitTitle(), jioUnitImportResult);
            }
            HashSet<String> importStatuCodes = new HashSet<String>();
            HashSet<String> unImportStatuCodes = new HashSet<String>();
            DataImportRule dataStatusRule = this.getDataImportRule(context, importStatuCodes, unImportStatuCodes);
            WorkFlowType wflowType = this.flowService.queryStartType(context.getFormSchemeKey());
            Map oldUploadEntityZdmKeyMap = context.getUploadEntityZdmKeyMap();
            HashMap<String, String> uploadEntityZdmKeyMap1 = new HashMap<String, String>();
            ArrayList<String> errorZdms = new ArrayList<String>();
            ArrayList<String> queryEntitys = new ArrayList<String>();
            HashMap<String, String> uploadEnityKeyKeys = new HashMap<String, String>();
            for (Map.Entry entry : oldUploadEntityZdmKeyMap.entrySet()) {
                String zdm = (String)entry.getKey();
                String netEntityKey = (String)entry.getValue();
                queryEntitys.add(netEntityKey);
                uploadEnityKeyKeys.put(netEntityKey, zdm);
            }
            JtableContext jContext = new JtableContext();
            jContext.setDimensionSet(dimensionSet);
            jContext.setFormSchemeKey(param.getFormSchemeKey());
            jContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
            jContext.setTaskKey(param.getTaskKey());
            jContext.setUnitViewKey(context.getDwEntityId());
            jContext.setVariableMap(param.getVariableMap());
            boolean isAdmin = this.systemdentityService.isAdmin();
            if (!isAdmin) {
                Set<String> canWriteEntityKeys = this.getAuthEntityKeys(context, param.getDimensionSet(), 1);
                for (Map.Entry entry : oldUploadEntityZdmKeyMap.entrySet()) {
                    String zdm = (String)entry.getKey();
                    String netEntityKey = (String)entry.getValue();
                    if (canWriteEntityKeys == null || canWriteEntityKeys != null && !canWriteEntityKeys.contains(netEntityKey)) {
                        String errorMessage = "\u5355\u4f4d\u6ca1\u6709\u7f16\u8f91\u6743\u9650";
                        JIOUnitImportResult unitImportResult = this.uploadLogService.getErorrItem(context, netEntityKey, errorMessage);
                        errorMap.put(netEntityKey, unitImportResult);
                        errorZdms.add(zdm);
                        continue;
                    }
                    uploadEntityZdmKeyMap1.put(zdm, netEntityKey);
                }
            } else {
                uploadEntityZdmKeyMap1.putAll(oldUploadEntityZdmKeyMap);
            }
            ArrayList<String> unitList = new ArrayList<String>();
            HashMap<String, String> entityKeysMapZdms = new HashMap<String, String>();
            for (Map.Entry entry : uploadEntityZdmKeyMap1.entrySet()) {
                String zdm = (String)entry.getKey();
                String netEntityKey = (String)entry.getValue();
                unitList.add(netEntityKey);
                entityKeysMapZdms.put(netEntityKey, zdm);
            }
            if (wflowType == WorkFlowType.ENTITY && !unitList.isEmpty()) {
                this.recordUnitState2(context, result, dimensionSet, dataStatusRule, unitList, unImportStatuCodes, errorMap, errorZdms, entityKeysMapZdms);
            }
            result.getErrorUnits().clear();
            for (Map.Entry entry : errorMap.entrySet()) {
                result.getErrorUnits().add(entry.getValue());
                if (!successMap.containsKey(((JIOUnitImportResult)entry.getValue()).getUnitKey())) continue;
                JIOUnitImportResult oldInfo = (JIOUnitImportResult)successMap.get(((JIOUnitImportResult)entry.getValue()).getUnitKey());
                result.getSuccessUnits().remove(oldInfo);
            }
            result.setSuccess(false);
            result.setUploadUnitNum(context.getSingleCorpCount());
            result.setAllSuccesssUnitNum(context.getSingleCorpCount() - result.getErrorUnits().size());
            for (String zdm : errorZdms) {
                oldUploadEntityZdmKeyMap.remove(zdm);
            }
        } else if (!context.isNeedCheckAuth()) {
            context.info(logger, "\u5bfc\u5165JIO\u6570\u636e\uff1a\u672a\u542f\u7528\u6743\u9650\u5224\u65ad\uff0c\u4e0d\u5224\u65ad\u5355\u4f4d\u72b6\u6001");
        }
    }

    private void recordUnitState2(TaskDataContext context, JIOImportResultObject result, Map<String, DimensionValue> dimensionSet, DataImportRule dataStatusRule, List<String> unitList, Set<String> unImportStatuCodes, Map<String, JIOUnitImportResult> errorMap, List<String> errorZdms, Map<String, String> entityKeysMapZdms) {
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme == null) {
            formScheme = this.runtimeView.getFormScheme(context.getFormSchemeKey());
            context.setFormScheme(formScheme);
        }
        if (formScheme != null && formScheme.getFlowsSetting() != null && formScheme.getFlowsSetting().getFlowsType() == FlowsType.NOSTARTUP) {
            return;
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
        dimensionValueSet.setValue(context.getEntityCompanyType(), unitList);
        logger.info("\u901a\u8fc7TreeState\u6279\u91cf\u83b7\u53d6\u4e0a\u62a5\u72b6\u6001");
        Map actionStateMaps = this.flowerTreeStateService.getWorkflowUploadState(dimensionValueSet, null, context.getFormSchemeKey());
        String errorMessage = "";
        for (DimensionValueSet dim : actionStateMaps.keySet()) {
            JIOUnitImportResult unitImportResult;
            String netEntityKey = (String)dim.getValue(context.getEntityCompanyType());
            String zdm = entityKeysMapZdms.get(netEntityKey);
            ActionStateBean actionState = (ActionStateBean)actionStateMaps.get(dim);
            if (dataStatusRule != null && dataStatusRule.isEnable()) {
                if (actionState == null || !StringUtils.isNotEmpty((String)actionState.getCode()) || !unImportStatuCodes.contains(actionState.getCode())) continue;
                errorMessage = "\u5f53\u524d\u5355\u4f4d\u72b6\u6001\u4e0d\u5141\u8bb8\u5bfc\u5165";
                logger.info("\u5355\u4f4d\uff1a" + zdm + "," + errorMessage + "," + actionState.getTitile());
                unitImportResult = this.uploadLogService.getErorrItem(context, netEntityKey, errorMessage);
                errorMap.put(netEntityKey, unitImportResult);
                errorZdms.add(zdm);
                continue;
            }
            if (actionState == null || !StringUtils.isNotEmpty((String)actionState.getCode()) || !actionState.getCode().equals(UploadStateEnum.SUBMITED.getCode()) && !actionState.getCode().equals(UploadStateEnum.UPLOADED.getCode()) && !actionState.getCode().equals(UploadStateEnum.CONFIRMED.getCode())) continue;
            errorMessage = "\u5355\u4f4d\u5df2\u7ecf\u4e0a\u62a5";
            logger.info("\u5355\u4f4d\uff1a" + zdm + "," + errorMessage);
            unitImportResult = this.uploadLogService.getErorrItem(context, netEntityKey, errorMessage);
            errorMap.put(netEntityKey, unitImportResult);
            errorZdms.add(zdm);
        }
    }

    private DataImportRule getDataImportRule(TaskDataContext context, Set<String> importStatuCodes, Set<String> unImportStatuCodes) {
        DataImportRule dataStatusRule = null;
        if (context.getMapingCache().getMapConfig() != null && context.getMapingCache().getMapConfig().getConfig() != null) {
            dataStatusRule = context.getMapingCache().getMapConfig().getConfig().getDataImportRule();
        }
        if (dataStatusRule != null && dataStatusRule.isEnable()) {
            UploadStateEnum stateEnum;
            for (UnitState state : dataStatusRule.getImportData()) {
                stateEnum = this.getUploadStateEnum(state);
                if (stateEnum == null) continue;
                importStatuCodes.add(stateEnum.getCode());
            }
            for (UnitState state : dataStatusRule.getUnImportData()) {
                stateEnum = this.getUploadStateEnum(state);
                if (stateEnum == null) continue;
                importStatuCodes.add(stateEnum.getCode());
            }
        }
        return dataStatusRule;
    }

    private UploadStateEnum getUploadStateEnum(UnitState state) {
        UploadStateEnum result = null;
        if (UnitState.CONFIRM == state) {
            result = UploadStateEnum.CONFIRMED;
        } else if (UnitState.REJECT == state) {
            result = UploadStateEnum.REJECTED;
        } else if (UnitState.UPLOAD == state) {
            result = UploadStateEnum.UPLOADED;
        } else if (UnitState.SUBMIT == state) {
            result = UploadStateEnum.SUBMITED;
        } else if (UnitState.UN_UPLOAD == state) {
            result = UploadStateEnum.ORIGINAL_UPLOAD;
        }
        return result;
    }

    private Set<String> getAuthEntityKeys(TaskDataContext context, Map<String, DimensionValue> dimensionSet, int authType) {
        DimensionValue periodDimensionValue = dimensionSet.get("DATATIME");
        String periodValue = periodDimensionValue.getValue();
        Date[] dates = DataEntryUtil.parseFromPeriod((String)periodValue);
        Date queryVersionStartDate = null;
        Date queryVersionEndDate = null;
        queryVersionStartDate = Consts.DATE_VERSION_MIN_VALUE;
        queryVersionEndDate = Consts.DATE_VERSION_MAX_VALUE;
        if (dates != null && dates.length >= 2) {
            queryVersionStartDate = dates[0];
            queryVersionEndDate = dates[1];
        }
        Set entityKeys = null;
        try {
            if (authType == 0) {
                entityKeys = this.iEntityAuthorityService.getCanReadEntityKeys(context.getDwEntityId(), queryVersionStartDate, queryVersionEndDate);
            } else if (authType == 1) {
                entityKeys = this.iEntityAuthorityService.getCanWriteEntityKeys(context.getDwEntityId(), queryVersionStartDate, queryVersionEndDate);
            }
        }
        catch (UnauthorizedEntityException e) {
            throw new SingleDataRuntimeException((Throwable)e);
        }
        return entityKeys;
    }

    @Override
    public AsyncTaskInfo doDeleteDirs(List<String> paths) {
        String taskId = UUID.randomUUID().toString();
        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, this.cacheObjectResourceRemote);
        this.npApplication.asyncRun(() -> this.lambda$doDeleteDirs$0((AsyncTaskMonitor)asyncTaskMonitor, paths));
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(taskId);
        asyncTaskInfo.setProcess(Double.valueOf(0.0));
        asyncTaskInfo.setResult("");
        asyncTaskInfo.setDetail((Object)"");
        asyncTaskInfo.setState(TaskState.PROCESSING);
        return asyncTaskInfo;
    }

    @Override
    public void deleteDirsAsync(AsyncTaskMonitor asyncTaskMonitor, List<String> paths) {
        double progress = 0.1;
        asyncTaskMonitor.progressAndMessage(progress, "\u51c6\u5907\u5220\u9664\u6587\u4ef6\u5939");
        for (String path : paths) {
            try {
                PathUtil.deleteDir((String)path);
            }
            catch (SingleFileException e) {
                logger.error(e.getMessage(), e);
            }
            asyncTaskMonitor.progressAndMessage(progress += 0.9 / (double)paths.size(), "\u5220\u9664\u6587\u4ef6\u5939:" + path);
        }
        logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a\u5f02\u6b65\u5220\u9664\u4efb\u52a1\u76ee\u5f55,\u65f6\u95f4:" + new Date().toString());
    }

    @Override
    public AsyncTaskInfo doDeleteNetFjFiles(String dataSchemeKey) {
        return null;
    }

    @Override
    public AsyncTaskInfo doDeleteNetMarkFiles(String dataSchemeKey) {
        return null;
    }

    @Override
    public void initReportAccessAuthCache(TaskDataContext importContext, UploadParam param, List<String> formKeys, Map<String, Map<String, IAccessResult>> entityKeyFormReadWritesMap, Map<String, String> uploadEntityZdmKeyMap) {
        Map dimensionSet = param.getDimensionSet();
        Map<String, DimensionValue> newManyUnit = UploadJioDataUtil.getNewDimensionSet(dimensionSet);
        HashMap<String, String> unitDic = new HashMap<String, String>();
        StringBuilder units = new StringBuilder();
        for (Map.Entry<String, String> entry : uploadEntityZdmKeyMap.entrySet()) {
            String unitKey = "";
            if (importContext.getEntityFieldIsCode()) {
                DataEntityInfo entinty = importContext.getEntityCache().findEntityByKey(entry.getValue());
                if (entinty != null) {
                    unitKey = entinty.getEntityCode();
                }
            } else {
                unitKey = entry.getValue();
            }
            if (!StringUtils.isNotEmpty((String)unitKey) || unitDic.containsKey(unitKey)) continue;
            unitDic.put(unitKey, entry.getKey());
            if (units.length() == 0) {
                units.append(unitKey);
                continue;
            }
            units.append(";");
            units.append(unitKey);
        }
        newManyUnit.get(importContext.getEntityCompanyType()).setValue(units.toString());
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(importContext.getFormSchemeKey());
        IBatchAccessResult batchAccess = this.getBatchAuth(importContext, formScheme, newManyUnit, formKeys, 1);
        for (Map.Entry<String, String> entry : uploadEntityZdmKeyMap.entrySet()) {
            String netEntityKey = entry.getValue();
            if (!StringUtils.isNotEmpty((String)netEntityKey) || entityKeyFormReadWritesMap.containsKey(netEntityKey)) continue;
            Map<String, DimensionValue> dimensionValueSet = UploadJioDataUtil.getNewDimensionSet(param.getDimensionSet());
            DimensionValue entityDim = dimensionValueSet.get(importContext.getEntityCompanyType());
            entityDim.setValue(importContext.getEntityMasterCodeBykey(netEntityKey));
            Map<String, IAccessResult> result = this.getAccessFormsResultMap(importContext.getFormSchemeKey(), batchAccess, dimensionValueSet, formKeys);
            entityKeyFormReadWritesMap.put(netEntityKey, result);
        }
    }

    @Override
    public void initReportAuthCache(TaskDataContext importContext, UploadParam param, List<String> formKeys, ReadWriteAccessCacheManager accessCacheManager, Map<String, FormReadWriteAccessData> entityKeyFormReadWritesMap, Map<String, String> uploadEntityZdmKeyMap) {
        Map dimensionSet = param.getDimensionSet();
        Map<String, DimensionValue> newManyUnit = UploadJioDataUtil.getNewDimensionSet(dimensionSet);
        HashMap<String, String> unitDic = new HashMap<String, String>();
        StringBuilder units = new StringBuilder();
        for (Map.Entry<String, String> entry : uploadEntityZdmKeyMap.entrySet()) {
            String unitKey = "";
            if (importContext.getEntityFieldIsCode()) {
                DataEntityInfo entinty = importContext.getEntityCache().findEntityByKey(entry.getValue());
                if (entinty != null) {
                    unitKey = entinty.getEntityCode();
                }
            } else {
                unitKey = entry.getValue();
            }
            if (!StringUtils.isNotEmpty((String)unitKey) || unitDic.containsKey(unitKey)) continue;
            unitDic.put(unitKey, entry.getKey());
            if (units.length() == 0) {
                units.append(unitKey);
                continue;
            }
            units.append(";");
            units.append(unitKey);
        }
        newManyUnit.get(importContext.getEntityCompanyType()).setValue(units.toString());
        JtableContext readWriteAccessContext = new JtableContext();
        readWriteAccessContext.setTaskKey(importContext.getTaskKey());
        readWriteAccessContext.setFormSchemeKey(importContext.getFormSchemeKey());
        readWriteAccessContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
        readWriteAccessContext.setDimensionSet(newManyUnit);
        readWriteAccessContext.setVariableMap(importContext.getVariableMap());
        ReadWriteAccessCacheParams readWriteAccessCacheParams = new ReadWriteAccessCacheParams(readWriteAccessContext, formKeys, Consts.FormAccessLevel.FORM_DATA_WRITE);
        accessCacheManager.initCache(readWriteAccessCacheParams);
        for (Map.Entry<String, String> entry : uploadEntityZdmKeyMap.entrySet()) {
            String netEntityKey = entry.getValue();
            if (!StringUtils.isNotEmpty((String)netEntityKey) || entityKeyFormReadWritesMap.containsKey(netEntityKey)) continue;
            Map<String, DimensionValue> dimensionValueSet = UploadJioDataUtil.getNewDimensionSet(param.getDimensionSet());
            DimensionValue entityDim = dimensionValueSet.get(importContext.getEntityCompanyType());
            entityDim.setValue(importContext.getEntityMasterCodeBykey(netEntityKey));
            FormReadWriteAccessData result = this.formAuthCheck(param, dimensionValueSet, accessCacheManager, formKeys);
            entityKeyFormReadWritesMap.put(netEntityKey, result);
        }
    }

    @Override
    public void deleteFloatRegionData(TaskDataContext importContext, UploadParam param, List<String> deleteRegionUnitCodes, String formKey, String regionKey, int floatingRow) {
        if (importContext.isDeleteEmptyData() && !deleteRegionUnitCodes.isEmpty() && floatingRow > 0) {
            Map<String, DimensionValue> dimensionValueSet = UploadJioDataUtil.getNewDimensionSet(param.getDimensionSet());
            DimensionValue entityDim = dimensionValueSet.get(importContext.getEntityCompanyType());
            String unitCodes = String.join((CharSequence)";", deleteRegionUnitCodes);
            entityDim.setValue(unitCodes);
            JtableContext jtableContext = new JtableContext();
            jtableContext.setTaskKey(param.getTaskKey());
            jtableContext.setFormSchemeKey(param.getFormSchemeKey());
            jtableContext.setDimensionSet(dimensionValueSet);
            jtableContext.setFormKey(formKey);
            this.deleteRegionService.clearRegiondata(jtableContext, formKey, regionKey);
        }
    }

    @Override
    public boolean isCurrentPeriodCanWrite(String formSchemeKey, String currPeriod) throws SingleDataException {
        List schemePeriod = null;
        try {
            schemePeriod = this.runtimeView.querySchemePeriodLinkBySchemeSort(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
        if (schemePeriod != null && schemePeriod.size() > 0) {
            SchemePeriodLinkDefine fromPeriod = (SchemePeriodLinkDefine)schemePeriod.get(0);
            SchemePeriodLinkDefine toPeriod = (SchemePeriodLinkDefine)schemePeriod.get(schemePeriod.size() - 1);
            String from = StringUtils.isEmpty((String)fromPeriod.getPeriodKey()) ? "1970Y0001" : fromPeriod.getPeriodKey();
            String to = StringUtils.isEmpty((String)toPeriod.getPeriodKey()) ? "9999Y0001" : toPeriod.getPeriodKey();
            PeriodWrapper currentPeriodPw = new PeriodWrapper(currPeriod);
            PeriodWrapper fromPeriodPw = new PeriodWrapper(from);
            PeriodWrapper toPeriodPw = new PeriodWrapper(to);
            return currentPeriodPw.compareTo((Object)toPeriodPw) <= 0 && currentPeriodPw.compareTo((Object)fromPeriodPw) >= 0;
        }
        return true;
    }

    @Override
    public boolean checkNetPeriodValid(String formSchemeKey, String netPeriod) throws SingleDataException {
        IPeriodProvider periodProvider;
        String netPeriodType;
        String periodType;
        boolean result = false;
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        TaskDefine taskFine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
        IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(taskFine.getDateTime());
        if (periodEntity != null && PeriodType.CUSTOM == periodEntity.getPeriodType()) {
            try {
                List periodRows = this.periodDataService.queryPeriodDataByPeriodCode(taskFine.getDateTime());
                for (IPeriodRow row : periodRows) {
                    if (!StringUtils.isNotEmpty((String)row.getCode()) || !row.getCode().equalsIgnoreCase(netPeriod)) continue;
                    result = true;
                }
            }
            catch (ParseException e) {
                logger.error("\u65f6\u671f\u8bfb\u53d6\u51fa\u9519\uff1a" + netPeriod + "," + taskFine.getDateTime() + "," + e.getMessage(), e);
            }
            catch (Exception e) {
                logger.error("\u65f6\u671f\u8bfb\u53d6\u51fa\u9519\uff1a" + netPeriod + "," + taskFine.getDateTime() + "," + e.getMessage(), e);
            }
        } else if (StringUtils.isNotEmpty((String)netPeriod) && netPeriod.length() == 9 && periodEntity != null && (periodType = netPeriod.substring(4, 5)).equalsIgnoreCase(netPeriodType = PeriodUtil.convertType2Str((int)periodEntity.getPeriodType().type())) && (periodProvider = this.periodAdapter.getPeriodProvider(taskFine.getDateTime())) != null) {
            List periodRows = periodProvider.getPeriodItems();
            for (IPeriodRow row : periodRows) {
                if (!StringUtils.isNotEmpty((String)row.getCode()) || !row.getCode().equalsIgnoreCase(netPeriod)) continue;
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public void uploadSingleFiles(TaskDataContext context, AsyncTaskMonitor asyncTaskMonitor) {
        JIOImportResultObject result = new JIOImportResultObject();
        this.uploadSingleFiles(context, result, asyncTaskMonitor);
    }

    @Override
    public void uploadSingleFiles(TaskDataContext context, JIOImportResultObject result, AsyncTaskMonitor asyncTaskMonitor) {
        block9: {
            String jioZipFile = context.getJioZipFile();
            try {
                if (!PathUtil.getFileExists((String)jioZipFile)) {
                    return;
                }
                CaseInsensitiveMap<String, SingleFieldFileInfo> fileMaps = new CaseInsensitiveMap<String, SingleFieldFileInfo>();
                for (Map.Entry entry : context.getFormFJFileMap().entrySet()) {
                    List fjList = (List)entry.getValue();
                    for (SingleFieldFileInfo fieldFile : fjList) {
                        String fileCode = fieldFile.getFjPath() + "/" + fieldFile.getZdm() + "/" + fieldFile.getFileName();
                        fileMaps.put(fileCode, fieldFile);
                    }
                }
                File zipFile = new File(SinglePathUtil.normalize((String)jioZipFile));
                if (zipFile.length() < 0xFFFFFFFFL) {
                    this.uploadSingleFilesByZipFile(context, context.getTaskFilePath(), jioZipFile, fileMaps);
                } else {
                    this.uploadSingleFilesByZipFile2(context, context.getTaskFilePath(), jioZipFile, fileMaps);
                }
                PathUtil.deleteFile((String)jioZipFile);
            }
            catch (SingleFileException e) {
                context.error(logger, e.getMessage(), (Throwable)e);
                result.setSuccess(false);
                if (StringUtils.isEmpty((String)result.getMessage())) {
                    result.setMessage("\u9644\u4ef6\u4e8c\u6b21\u4e0a\u4f20\u5931\u8d25");
                }
            }
            catch (SingleDataException e) {
                context.error(logger, e.getMessage(), (Throwable)e);
                result.setSuccess(false);
                if (!StringUtils.isEmpty((String)result.getMessage())) break block9;
                result.setMessage("\u9644\u4ef6\u4e8c\u6b21\u4e0a\u4f20\u5931\u8d25");
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void uploadSingleFilesByZipFile(TaskDataContext context, String outPath, String jioZipFile, Map<String, SingleFieldFileInfo> fileMaps) throws SingleDataException {
        try (ZipFile zipNow = new ZipFile(SinglePathUtil.normalize((String)jioZipFile), Charset.forName("GBK"));){
            Enumeration<? extends ZipEntry> entries = zipNow.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String zipEntryName = entry.getName();
                if (!fileMaps.containsKey(zipEntryName)) {
                    // empty if block
                }
                if (!fileMaps.containsKey(zipEntryName)) continue;
                SingleFieldFileInfo fieldInfo = fileMaps.get(zipEntryName);
                try {
                    InputStream zipIn = zipNow.getInputStream(entry);
                    Throwable throwable = null;
                    try {
                        this.uploadSingleZipEntry(context, zipIn, entry, fieldInfo, outPath, zipEntryName);
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (zipIn == null) continue;
                        if (throwable != null) {
                            try {
                                zipIn.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        zipIn.close();
                    }
                }
                catch (Exception e) {
                    context.error(logger, "\u9644\u4ef6\u5bfc\u5165\u5931\u8d25\uff0c" + fieldInfo.getZdm() + "," + fieldInfo.getFileName() + "," + e.getMessage(), (Throwable)e);
                    throw new SingleDataException(e.getMessage(), (Throwable)e);
                    return;
                }
            }
        }
        catch (SingleDataException e) {
            context.error(logger, e.getMessage(), (Throwable)e);
            throw e;
        }
        catch (IOException e1) {
            context.error(logger, e1.getMessage(), (Throwable)e1);
            throw new SingleDataException(e1.getMessage(), (Throwable)e1);
        }
        catch (SingleFileException e1) {
            context.error(logger, e1.getMessage(), (Throwable)e1);
            throw new SingleDataException(e1.getMessage(), (Throwable)e1);
        }
    }

    private void uploadSingleZipEntry(TaskDataContext context, InputStream zipIn, ZipEntry entry, SingleFieldFileInfo fieldInfo, String outPath, String zipEntryName1) throws FileNotFoundException, IOException, SingleDataException, SingleFileException {
        String zipEntryName = zipEntryName1.replace("/", File.separator);
        String fjZipfilePath = outPath + File.separator + zipEntryName;
        File outPathFile = new File(SinglePathUtil.normalize((String)fjZipfilePath));
        File fileParent = outPathFile.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        try (FileOutputStream out2 = new FileOutputStream(SinglePathUtil.normalize((String)fjZipfilePath));){
            int len;
            byte[] buf1 = new byte[1024];
            while ((len = zipIn.read(buf1)) > 0) {
                out2.write(buf1, 0, len);
            }
            out2.close();
        }
        String docFilePath = outPath + File.separator + UUID.randomUUID().toString() + File.separator;
        SinglePathUtil.makeDir((String)docFilePath);
        List singleFiles = TaskFileDataOperateUtil.getSingleFileInfosFormZipFile((String)fjZipfilePath, (String)docFilePath, (boolean)true);
        if (!singleFiles.isEmpty()) {
            try {
                SingleAttachmentResult uploadResult = this.singleAttachService.uploadSingleFileInfosR(singleFiles, fieldInfo);
                if (!uploadResult.isSuccess() && !uploadResult.getFailedFileList().isEmpty()) {
                    String entityKey = fieldInfo.getEntityKey();
                    if (StringUtils.isEmpty((String)entityKey)) {
                        if (fieldInfo.getDimensionSet() != null && StringUtils.isNotEmpty((String)context.getEntityCompanyType())) {
                            DimensionValue dim = (DimensionValue)fieldInfo.getDimensionSet().get(context.getEntityCompanyType());
                            if (dim != null) {
                                entityKey = dim.getValue();
                            }
                        } else if (StringUtils.isNotEmpty((String)fieldInfo.getZdm()) && context.getUploadEntityZdmKeyMap().containsKey(fieldInfo.getZdm())) {
                            entityKey = (String)context.getUploadEntityZdmKeyMap().get(fieldInfo.getZdm());
                        }
                    }
                    String errorMsg = this.getFailFileMessage(uploadResult);
                    SingleDataError errorItem = new SingleDataError("", fieldInfo.getFormCode(), errorMsg, "importSecordFileFail", entityKey, null, entityKey);
                    errorItem.setFormKey(fieldInfo.getFormKey());
                    context.recordLog("importSecordForm", errorItem);
                } else {
                    context.updateAttachFileNumAsyn(singleFiles.size());
                }
            }
            catch (SingleDataException ex) {
                context.error(logger, "\u9644\u4ef6\u5bfc\u5165\u5931\u8d25\uff0c" + fieldInfo.getZdm() + "," + ex.getMessage(), (Throwable)ex);
                throw ex;
            }
        }
        PathUtil.deleteFile((String)fjZipfilePath);
        PathUtil.deleteDir((String)docFilePath);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void uploadSingleFilesByZipFile2(TaskDataContext context, String outPath, String jioZipFile, Map<String, SingleFieldFileInfo> fileMaps) throws SingleDataException {
        try {
            inStream = new FileInputStream(SinglePathUtil.normalize((String)jioZipFile));
            var6_10 = null;
            try {
                zip = new ZipInputStream((InputStream)inStream, Charset.forName("GBK"));
                var8_14 = null;
                try {
                    block25: while (true) {
                        if ((entry = zip.getNextEntry()) == null) return;
                        if (entry.isDirectory()) continue;
                        zipEntryName = entry.getName();
                        if (!fileMaps.containsKey(zipEntryName)) {
                            // empty if block
                        }
                        if (fileMaps.containsKey(zipEntryName)) {
                            fieldInfo = fileMaps.get(zipEntryName);
                            try {
                                this.uploadSingleZipEntry(context, zip, entry, fieldInfo, outPath, zipEntryName);
                            }
                            catch (SingleDataException e) {
                                context.error(UploadJioDataServiceImpl.logger, "\u9644\u4ef6\u5bfc\u5165\u5931\u8d25\uff0c" + fieldInfo.getZdm() + "," + fieldInfo.getFileName() + "," + e.getMessage(), (Throwable)e);
                                throw e;
                            }
                        }
                        buffer = new byte[1024];
                        while (true) {
                            if (zip.read(buffer) >= 0) ** break;
                            continue block25;
                        }
                        break;
                    }
                }
                catch (Throwable var9_17) {
                    var8_14 = var9_17;
                    throw var9_17;
                }
                finally {
                    if (zip != null) {
                        if (var8_14 != null) {
                            try {
                                zip.close();
                            }
                            catch (Throwable var9_16) {
                                var8_14.addSuppressed(var9_16);
                            }
                        } else {
                            zip.close();
                        }
                    }
                }
            }
            catch (Throwable var7_13) {
                var6_10 = var7_13;
                throw var7_13;
            }
            finally {
                if (inStream != null) {
                    if (var6_10 != null) {
                        try {
                            inStream.close();
                        }
                        catch (Throwable var7_12) {
                            var6_10.addSuppressed(var7_12);
                        }
                    } else {
                        inStream.close();
                    }
                }
            }
        }
        catch (SingleDataException e) {
            context.error(UploadJioDataServiceImpl.logger, e.getMessage(), (Throwable)e);
            return;
        }
        catch (FileNotFoundException e1) {
            context.error(UploadJioDataServiceImpl.logger, e1.getMessage(), (Throwable)e1);
            throw new SingleDataException(e1.getMessage(), (Throwable)e1);
        }
        catch (IOException e1) {
            context.error(UploadJioDataServiceImpl.logger, e1.getMessage(), (Throwable)e1);
            throw new SingleDataException(e1.getMessage(), (Throwable)e1);
        }
        catch (SingleFileException e1) {
            context.error(UploadJioDataServiceImpl.logger, e1.getMessage(), (Throwable)e1);
            throw new SingleDataException(e1.getMessage(), (Throwable)e1);
        }
    }

    private IBatchAccessResult getBatchAuth(TaskDataContext importContext, FormSchemeDefine formScheme, Map<String, DimensionValue> masterDims, List<String> formKeys, int authType) {
        String prefix = "IGNORE_WORKFLOW_STATE_ITEM";
        HashSet<String> importStatuCodes = new HashSet<String>();
        HashSet<String> unImportStatuCodes = new HashSet<String>();
        this.getDataImportRule(importContext, importStatuCodes, unImportStatuCodes);
        if (!importStatuCodes.isEmpty()) {
            String submited;
            if (importStatuCodes.contains(UploadStateEnum.CONFIRMED.getCode())) {
                submited = UploadState.CONFIRMED.toString();
                DsContextHolder.getDsContext().getExtension().put(prefix + "_" + submited, (Serializable)((Object)"1"));
            }
            if (importStatuCodes.contains(UploadStateEnum.REJECTED.getCode())) {
                submited = UploadState.REJECTED.toString();
                DsContextHolder.getDsContext().getExtension().put(prefix + "_" + submited, (Serializable)((Object)"1"));
            }
            if (importStatuCodes.contains(UploadStateEnum.UPLOADED.getCode())) {
                submited = UploadState.UPLOADED.toString();
                DsContextHolder.getDsContext().getExtension().put(prefix + "_" + submited, (Serializable)((Object)"1"));
            }
            if (importStatuCodes.contains(UploadStateEnum.SUBMITED.getCode())) {
                submited = UploadState.SUBMITED.toString();
                DsContextHolder.getDsContext().getExtension().put(prefix + "_" + submited, (Serializable)((Object)"1"));
            }
            if (importStatuCodes.contains(UploadStateEnum.ORIGINAL_UPLOAD.getCode())) {
                submited = UploadState.ORIGINAL_UPLOAD.toString();
                DsContextHolder.getDsContext().getExtension().put(prefix + "_" + submited, (Serializable)((Object)"1"));
            }
        }
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(formScheme.getTaskKey(), formScheme.getKey());
        DimensionCollection masterKey = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection(masterDims, (String)formScheme.getKey());
        IBatchAccessResult batchAccess = null;
        batchAccess = authType == 1 ? dataAccessService.getWriteAccess(masterKey, formKeys) : dataAccessService.getReadAccess(masterKey, formKeys);
        return batchAccess;
    }

    private Map<String, IAccessResult> getAccessFormsResultMap(String formSchemeKey, IBatchAccessResult batchAccess, Map<String, DimensionValue> unitDim, List<String> formKeys) {
        HashMap<String, IAccessResult> canReadForms = new HashMap<String, IAccessResult>();
        DimensionValueSet dimValueSet = DimensionValueSetUtil.getDimensionValueSet(unitDim);
        DimensionCombination unitDimCombination = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCombination((DimensionValueSet)dimValueSet, (String)formSchemeKey);
        for (String formKey : formKeys) {
            IAccessResult accessResult = batchAccess.getAccess(unitDimCombination, formKey);
            canReadForms.put(formKey, accessResult);
        }
        return canReadForms;
    }

    private /* synthetic */ void lambda$doDeleteDirs$0(AsyncTaskMonitor asyncTaskMonitor, List paths) {
        try {
            this.deleteDirsAsync(asyncTaskMonitor, paths);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

