/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  com.jiuqi.nr.single.core.internal.file.SingleFileImpl
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nr.single.core.util.ZipParam
 *  com.jiuqi.nr.single.core.util.ZipUtil
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  javax.annotation.Resource
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.service.SingleJioFileService
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.data.datain.internal.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.internal.file.SingleFileImpl;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.ZipParam;
import com.jiuqi.nr.single.core.util.ZipUtil;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Resource;
import nr.single.data.datain.service.ITaskFileImportDataService;
import nr.single.data.datain.service.ITaskFileImportEntityService;
import nr.single.data.datain.service.TaskFileImportEntityServiceManager;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.data.PathUtil;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.service.SingleJioFileService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileImportDataService
implements ITaskFileImportDataService {
    private static final Logger logger = LoggerFactory.getLogger(TaskFileImportDataService.class);
    @Autowired
    private IRunTimeViewController viewController;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private SingleJioFileService jioFileService;

    @Override
    public String exactSingleFileToPath(TaskDataContext importContext, String path, String file) throws SingleDataException {
        SingleFile singleFile = this.exactSingleFileToPathReturn(importContext, path, file);
        return singleFile.getInfo().readString("General", "Flag", "");
    }

    @Override
    public SingleFile exactSingleFileToPathReturn(TaskDataContext importContext, String path, String file) throws SingleDataException {
        boolean includeFj = true;
        try {
            File file2 = new File(SinglePathUtil.normalize((String)file));
            includeFj = file2.length() < 0x40000000L;
        }
        catch (SingleFileException e) {
            importContext.error(logger, e.getMessage(), (Throwable)e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
        if (importContext != null) {
            if (StringUtils.isNotEmpty((CharSequence)importContext.getTaskDocPath())) {
                includeFj = true;
            }
            if (includeFj) {
                importContext.setFjUploadMode(0);
            } else {
                importContext.setFjUploadMode(1);
            }
        }
        SingleFile singleFile = this.exactSingleFileToPath2(importContext, path, file, includeFj, includeFj);
        return singleFile;
    }

    @Override
    public SingleFile exactSingleFileToPath2(TaskDataContext importContext, String path, String file, boolean includeFj, boolean deleteZipFile) throws SingleDataException {
        String taskCode = null;
        SingleFileImpl singleFile = new SingleFileImpl();
        try {
            String zipFileName = file + "temp.zip";
            singleFile.infoLoad(file);
            singleFile.unMakeJio(file, zipFileName);
            taskCode = singleFile.getInfo().readString("General", "Flag", "");
            String fileFlag = singleFile.getInfo().readString("General", "FileFlag", "");
            String taskYear = singleFile.getInfo().readString("General", "Year", "");
            String taskTitle = singleFile.getInfo().readString("General", "Name", "");
            String periodType = singleFile.getInfo().readString("General", "Period", "");
            if (importContext != null) {
                importContext.setSingleTaskFlag(taskCode);
                importContext.setSingleFileFlag(fileFlag);
                importContext.setSingleTaskYear(taskYear);
                importContext.setSingleTaskTitle(taskTitle);
                importContext.setSinglePeriodType(periodType);
                if (!deleteZipFile) {
                    importContext.setJioZipFile(zipFileName);
                }
            }
            if (includeFj) {
                ZipUtil.unzipFile((String)path, (String)zipFileName, (String)"GBK");
            } else {
                ZipParam zipParam = new ZipParam();
                if (importContext != null) {
                    importContext.setZipParam(zipParam);
                }
                HashSet<String> filerNames = new HashSet<String>();
                filerNames.add("DATA/SYS_DOC");
                ZipUtil.unzipFile((String)path, (String)zipFileName, (String)"GBK", (String[])ZipUtil.TRY_CHARSETS, filerNames, (ZipParam)zipParam);
            }
            PathUtil.reNameFile((String)path, (String)"Data", (String)"DATA");
            if (deleteZipFile) {
                PathUtil.deleteFile((String)zipFileName);
            }
            if (importContext != null) {
                importContext.info(logger, "\u5bfc\u5165:\u89e3\u538bJIO,\u65f6\u95f4:" + new Date().toString());
            }
        }
        catch (Exception e) {
            if (importContext != null) {
                importContext.error(logger, e.getMessage(), (Throwable)e);
            }
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
        return singleFile;
    }

    @Override
    public SingleFile readSingleFileFromPath(TaskDataContext importContext, String taskPath) throws SingleDataException {
        SingleFileImpl singleFile = null;
        if (importContext != null) {
            importContext.setFjUploadMode(0);
            singleFile = this.jioFileService.getSingleFileByTaskDir(taskPath);
            importContext.setSingleTaskFlag(singleFile.getTaskFlag());
            importContext.setSingleFileFlag(singleFile.getFileFlag());
            importContext.setSingleTaskYear(singleFile.getTaskYear());
            importContext.setSingleTaskTitle(singleFile.getTaskName());
            importContext.setSinglePeriodType(singleFile.getTaskPeriod());
        } else {
            singleFile = new SingleFileImpl();
        }
        return singleFile;
    }

    @Override
    public void importSingleReportData(TaskDataContext importContext, String path) throws SingleDataException {
        try {
            String dataPath = PathUtil.createNewPath((String)path, (String)"DATA");
            if (importContext.getMapingCache().getMapConfig() != null) {
                this.importSingleReportDataByMap(importContext, importContext.getMapingCache().getMapConfig().getTableInfos(), dataPath, importContext.getMapingCache().getMapConfig().getTaskInfo().getSingleFileFlag());
            } else {
                this.importSingleReportDataByNoMap(importContext, dataPath);
            }
        }
        catch (SingleFileException e) {
            importContext.error(logger, e.getMessage(), (Throwable)e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
    }

    private void importSingleReportDataByMap(TaskDataContext importContext, List<SingleFileTableInfo> singleTables, String dataPath, String fileFlag) throws SingleDataException {
        for (int i = 0; i < singleTables.size(); ++i) {
            SingleFileTableInfo tableInfo = singleTables.get(i);
            String singleTableCode = tableInfo.getSingleTableCode();
            String netFormCode = tableInfo.getNetFormCode();
            FormDefine formDefine = null;
            try {
                formDefine = this.viewController.queryFormByCodeInScheme(importContext.getFormSchemeKey(), netFormCode);
            }
            catch (Exception e) {
                importContext.error(logger, e.getMessage(), (Throwable)e);
                throw new SingleDataException(e.getMessage(), (Throwable)e);
            }
            if (null == formDefine) continue;
            SingleFileRegionInfo singleRegion = tableInfo.getRegion();
            String dbfFileName = dataPath + fileFlag + singleTableCode + ".DBF";
            this.importSingleReportRegionData(importContext, dbfFileName, singleRegion);
            for (int j = 0; j < tableInfo.getRegion().getSubRegions().size(); ++j) {
                SingleFileRegionInfo singleSubRegion = (SingleFileRegionInfo)tableInfo.getRegion().getSubRegions().get(j);
                dbfFileName = dataPath + fileFlag + singleTableCode + "_F" + singleSubRegion.getFloatingIndex() + ".DBF";
                this.importSingleReportRegionData(importContext, dbfFileName, singleSubRegion);
            }
        }
    }

    private void importSingleReportDataByNoMap(TaskDataContext importContext, String dataPath) throws SingleDataException, SingleFileException {
        List dbfFiles = PathUtil.getFileList((String)dataPath, (boolean)false, (String)"DBF");
        HashMap<String, String> tableCodeMap = new HashMap<String, String>();
        String singleFileFlag = importContext.getSingleFileFlag();
        for (int i = 0; i < dbfFiles.size(); ++i) {
            String dbfFileName = (String)dbfFiles.get(i);
            File file = new File(dbfFileName);
            String singleTableCode = file.getName().replaceAll(".DBF", "");
            singleTableCode = singleTableCode.replaceFirst(singleFileFlag, "");
            tableCodeMap.put(singleTableCode, dbfFileName);
        }
        List forms = this.viewController.queryAllFormDefinesByFormScheme(importContext.getFormSchemeKey());
        for (FormDefine form : forms) {
            if (form.getKey() == importContext.getFmdmFormKey()) continue;
            String dbfFileName = "";
            List netRegions = this.viewController.getAllRegionsInForm(form.getKey());
            for (int j = 0; j < netRegions.size(); ++j) {
                DataRegionDefine netRegion = (DataRegionDefine)netRegions.get(j);
                String aCode = form.getFormCode();
                if (netRegion.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
                    aCode = aCode + "_F" + String.valueOf(netRegion.getRegionTop());
                }
                if (!tableCodeMap.containsKey(aCode)) continue;
                dbfFileName = (String)tableCodeMap.get(aCode);
                this.importSingleReportRegionData(importContext, dbfFileName, null);
            }
        }
    }

    @Override
    public void importSingleReportRegionData(TaskDataContext importContext, String dbfFileName, SingleFileRegionInfo singleRegion) throws SingleDataException {
    }

    @Override
    public void importSingleEnityData(TaskDataContext importContext, String path, AsyncTaskMonitor asyncTaskMonitor) throws SingleDataException {
        String category;
        ITaskFileImportEntityService service = null;
        if (StringUtils.isNotEmpty((CharSequence)importContext.getDwEntityId()) && StringUtils.isNotEmpty((CharSequence)(category = EntityUtils.getCategory((String)importContext.getDwEntityId())))) {
            service = TaskFileImportEntityServiceManager.getInstance().findService("FMDM");
            if (category.equalsIgnoreCase("BASE") || category.equalsIgnoreCase("NRE") || category.equalsIgnoreCase("ORG")) {
                // empty if block
            }
            service.importSingleEnityData(importContext, path, asyncTaskMonitor);
        }
    }

    @Override
    public List<String> getSinglePeriods(TaskDataContext importContext, String path) throws SingleDataException {
        ArrayList<String> periodCodes = new ArrayList<String>();
        try {
            ISingleMappingConfig mapConfig;
            String dataPath = PathUtil.createNewPath((String)path, (String)"DATA");
            String dbfFileName = dataPath + importContext.getSingleFileFlag() + "FMDM.DBF";
            SingleFileFmdmInfo fmTable = null;
            String taskYear = "";
            String taskPeriodType = "";
            if (null != importContext.getMapingCache().getMapConfig() && (mapConfig = importContext.getMapingCache().getMapConfig()).getTableInfos().size() > 0) {
                fmTable = (SingleFileFmdmInfo)mapConfig.getTableInfos().get(0);
                taskYear = mapConfig.getTaskInfo().getSingleTaskYear();
                taskPeriodType = mapConfig.getTaskInfo().getSingleTaskPeriod();
            }
            if (fmTable != null) {
                boolean isYearReport = false;
                if ("N".equalsIgnoreCase(taskPeriodType)) {
                    isYearReport = true;
                } else if (StringUtils.isEmpty((CharSequence)fmTable.getPeriodField())) {
                    isYearReport = true;
                }
                if (!isYearReport) {
                    this.getPeriodsFromDbf(dbfFileName, fmTable, periodCodes);
                } else {
                    periodCodes.add(taskYear + "@");
                }
            }
        }
        catch (SingleFileException e) {
            importContext.error(logger, e.getMessage(), (Throwable)e);
        }
        return periodCodes;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void getPeriodsFromDbf(String dbfFileName, SingleFileFmdmInfo fmTable, List<String> periodCodes) throws SingleDataException {
        try (IDbfTable dbf = DbfTableUtil.getDbfTable((String)dbfFileName);){
            int periodIndex = dbf.getFieldIndex(fmTable.getPeriodField());
            if (periodIndex > 0) {
                ArrayList<Integer> loadFields = new ArrayList<Integer>();
                loadFields.add(0);
                loadFields.add(periodIndex);
                HashMap<String, String> periodDic = new HashMap<String, String>();
                for (int i = 0; i < dbf.getRecordCount(); ++i) {
                    DataRow dbfRow = dbf.getRecordByIndex(i);
                    if (!dbf.isHasLoadAllRec()) {
                        dbf.loadDataRowByIndexs(dbfRow, loadFields);
                    }
                    String periodCode = dbfRow.getValueString(fmTable.getPeriodField());
                    String zdm = dbfRow.getValueString(0);
                    if (StringUtils.isNotEmpty((CharSequence)periodCode) && !periodDic.containsKey(periodCode)) {
                        periodCodes.add(periodCode);
                        periodDic.put(periodCode, zdm);
                    }
                    if (dbf.isHasLoadAllRec()) continue;
                    dbf.clearDataRow(dbfRow);
                }
            } else {
                logger.info("\u6587\u4ef6\uff1a" + dbfFileName + ",\u65e0\u65f6\u671f\u5b57\u6bb5" + fmTable.getPeriodField());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
    }
}

