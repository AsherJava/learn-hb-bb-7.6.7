/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.single.core.util.ZipParam
 */
package nr.single.map.data;

import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.single.core.util.ZipParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nr.single.map.data.DataChkInfo;
import nr.single.map.data.DataEntityCache;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.DataMapingCache;
import nr.single.map.data.DimEntityCache;
import nr.single.map.data.SingleDataError;
import nr.single.map.data.SingleDataOption;
import nr.single.map.data.SingleFieldFileInfo;
import nr.single.map.data.bean.RepeatEntityNode;
import nr.single.map.data.bean.RepeatFormNode;
import nr.single.map.data.bean.RepeatImportParam;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.slf4j.Logger;

public class TaskDataContext {
    private String taskKey;
    private String formSchemeKey;
    private String dataSchemeKey;
    private String configKey;
    private FormSchemeDefine formScheme = null;
    private String fmdmFormKey = null;
    private String dwEntityId = null;
    private String currentZDM = "";
    private String firstZDM;
    private String netPeriodCode;
    private String mapNetPeriodCode;
    private String mapCurrentPeriod;
    private Map<String, DimensionValue> otherDims;
    private String currentPeriod;
    private String currentFormTitle;
    private String currentTaskTitle;
    private String taskFilePath;
    private String taskDataPath;
    private String taskDocPath;
    private String taskImgPath;
    private String taskTxtPath;
    private String taskRptPath;
    private String entityDateType = "";
    private String entityCompanyType = "";
    private String currentEntintyKey;
    private String parentEntintyKey;
    private Map<String, String> entityKeyZdmMap;
    private Map<String, String> entityZdmKeyMap;
    private Map<String, String> entityZdmKeyAuthMap;
    private Map<String, String> insertEntityZdmKeyMap;
    private Map<String, String> uploadEntityZdmKeyMap;
    private Map<String, List<String>> entityMergeZdmMap;
    private Map<String, String> entityMergeBenJiMap;
    private Map<String, String> downloadEntityKeyZdmMap;
    private List<String> downloadEntityKeys;
    private Map<String, String> entityCodeKeyMap;
    private Map<String, String> entityKeyCodeMap;
    private Map<String, String> entityKeyParentMap;
    private DataEntityCache entityCache;
    private DimEntityCache dimEntityCache;
    private SingleDataOption dataOption = new SingleDataOption();
    private String singleTaskFlag;
    private String singleFileFlag;
    private String singleTaskYear;
    private String singleTaskTitle;
    private String singlePeriodType;
    private DataMapingCache mapingCache;
    private boolean exportEnclosure = true;
    private boolean deleteEmptyData = false;
    private boolean hasCheckInfo = false;
    private boolean hasEntityCheck = false;
    private boolean hasQueryCheck = false;
    private boolean needCreateDBF = true;
    private boolean needDownLoad = true;
    private boolean needCheckInfo = true;
    private Map<String, Map<String, List<DataChkInfo>>> checkInfos = null;
    private Map<String, List<SingleDataError>> logs = null;
    private Map<String, Object> intfObjects = null;
    private int singleCorpCount = 0;
    private int netCorpCount = 0;
    private int sourceCorpCount = 0;
    private int successCorpCount = 0;
    private Set<String> singleCorpList;
    private Set<String> singleMergeZdms;
    private List<String> canReadNetEntityKeys;
    double progress = 0.0;
    double nextProgressLen = 0.0;
    private String syncTaskID;
    private AsyncTaskMonitor asyncTaskMonitor;
    private String curMessage;
    private Map<String, Object> variableMap;
    private boolean needCheckAuth = true;
    private boolean needCheckRepeat = false;
    private String productName = "";
    private int repeatImportType = 1;
    private RepeatImportParam jioRepeatParam;
    private RepeatImportParam jioRepeatResult;
    private Map<String, RepeatEntityNode> repeatEntityNodes;
    private Map<String, RepeatFormNode> repeatFormNodes;
    private boolean needSelectImport = false;
    private RepeatImportParam jioSelectImportParam;
    private RepeatImportParam jioSelectImportResult;
    private Map<String, RepeatEntityNode> selectEntityNodes;
    private Map<String, RepeatFormNode> selectFormNodes;
    private int fjUploadMode = 0;
    private Map<String, String> fjZdmDirs;
    private String jioZipFile;
    private ZipParam zipParam;
    private Map<String, List<SingleFieldFileInfo>> formFJFileMap;
    private String measureKey;
    private String measureCode;
    private String measureDecimal;
    private boolean needChangeUpper;
    private boolean needCheckCorpTree = false;
    private int attachFileNum = 0;
    private int periodAttachFileNum = 0;
    private int importedFormNum = 0;

    public TaskDataContext() {
        this.entityKeyZdmMap = new LinkedHashMap<String, String>();
        this.entityZdmKeyMap = new CaseInsensitiveMap<String, String>();
        this.insertEntityZdmKeyMap = new CaseInsensitiveMap<String, String>();
        this.uploadEntityZdmKeyMap = new CaseInsensitiveMap<String, String>();
        this.entityCodeKeyMap = new LinkedHashMap<String, String>();
        this.entityKeyCodeMap = new LinkedHashMap<String, String>();
        this.entityKeyParentMap = new LinkedHashMap<String, String>();
        this.dataOption = new SingleDataOption();
        this.logs = new LinkedHashMap<String, List<SingleDataError>>();
    }

    public boolean isExportEnclosure() {
        return this.exportEnclosure;
    }

    public void setExportEnclosure(boolean exportEnclosure) {
        this.exportEnclosure = exportEnclosure;
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public boolean isHasCheckInfo() {
        return this.hasCheckInfo;
    }

    public void setHasCheckInfo(boolean hasCheckInfo) {
        this.hasCheckInfo = hasCheckInfo;
    }

    public SingleDataOption getDataOption() {
        return this.dataOption;
    }

    public void setDataOption(SingleDataOption dataOption) {
        this.dataOption = dataOption;
    }

    public Map<String, String> getEntityZdmKeyMap() {
        return this.entityZdmKeyMap;
    }

    public Map<String, String> getEntityCodeKeyMap() {
        return this.entityCodeKeyMap;
    }

    public Map<String, String> getEntityKeyCodeMap() {
        return this.entityKeyCodeMap;
    }

    public String getSingleTaskFlag() {
        return this.singleTaskFlag;
    }

    public void setSingleTaskFlag(String singleTaskFlag) {
        this.singleTaskFlag = singleTaskFlag;
    }

    public String getSingleFileFlag() {
        return this.singleFileFlag;
    }

    public void setSingleFileFlag(String singleFileFlag) {
        this.singleFileFlag = singleFileFlag;
    }

    public String getEntityDateType() {
        if (StringUtils.isEmpty((String)this.entityDateType)) {
            this.entityDateType = "DATATIME";
        }
        return this.entityDateType;
    }

    public void setEntityDateType(String entityDateType) {
        this.entityDateType = entityDateType;
    }

    public String getEntityCompanyType() {
        return this.entityCompanyType;
    }

    public void setEntityCompanyType(String entityCompanyType) {
        this.entityCompanyType = entityCompanyType;
    }

    public Map<String, String> getEntityKeyZdmMap() {
        return this.entityKeyZdmMap;
    }

    public String getCurrentEntintyKey() {
        return this.currentEntintyKey;
    }

    public void setCurrentEntintyKey(String currentEntintyKey) {
        this.currentEntintyKey = currentEntintyKey;
    }

    public String getTaskDataPath() {
        return this.taskDataPath;
    }

    public void setTaskDataPath(String taskDataPath) {
        this.taskDataPath = taskDataPath;
    }

    public String getCurrentTaskTitle() {
        return this.currentTaskTitle;
    }

    public void setCurrentTaskTitle(String currentTaskTitle) {
        this.currentTaskTitle = currentTaskTitle;
    }

    public String getCurrentFormTitle() {
        return this.currentFormTitle;
    }

    public void setCurrentFormTitle(String currentFomTitle) {
        this.currentFormTitle = currentFomTitle;
    }

    public boolean getNeedCreateDBF() {
        return this.needCreateDBF;
    }

    public void setNeedCreateDBF(boolean needCreateDBF) {
        this.needCreateDBF = needCreateDBF;
    }

    public String getParentEntintyKey() {
        return this.parentEntintyKey;
    }

    public void setParentEntintyKey(String parentEntintyKey) {
        this.parentEntintyKey = parentEntintyKey;
    }

    public String getCurrentPeriod() {
        return this.currentPeriod;
    }

    public void setCurrentPeriod(String currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public String getNetPeriodCode() {
        return this.netPeriodCode;
    }

    public void setNetPeriodCode(String periodCode) {
        this.netPeriodCode = periodCode;
    }

    public String getCurrentZDM() {
        return this.currentZDM;
    }

    public void setCurrentZDM(String currentZDM) {
        this.currentZDM = currentZDM;
    }

    public String getFmdmFormKey() {
        return this.fmdmFormKey;
    }

    public void setFmdmFormKey(String fmdmFormKey) {
        this.fmdmFormKey = fmdmFormKey;
    }

    public String getFileFlag() {
        String fileFlag = "";
        if (null != this.getMapingCache().getMapConfig()) {
            fileFlag = this.getMapingCache().getMapConfig().getTaskInfo().getSingleFileFlag();
        } else if (null != this.getFormScheme()) {
            fileFlag = this.getFormScheme().getFilePrefix();
        }
        return fileFlag;
    }

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(FormSchemeDefine formScheme) {
        this.formScheme = formScheme;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public Map<String, String> getEntityKeyParentMap() {
        return this.entityKeyParentMap;
    }

    public void setEntityKeyParentMap(Map<String, String> entityKeyParentMap) {
        this.entityKeyParentMap = entityKeyParentMap;
    }

    public Map<String, String> getInsertEntityZdmKeyMap() {
        return this.insertEntityZdmKeyMap;
    }

    public void setInsertEntityZdmKeyMap(Map<String, String> insertEntityZdmKeyMap) {
        this.insertEntityZdmKeyMap = insertEntityZdmKeyMap;
    }

    public Map<String, String> getEntityZdmKeyAuthMap() {
        return this.entityZdmKeyAuthMap;
    }

    public void setEntityZdmKeyAuthMap(Map<String, String> entityZdmKeyAuthMap) {
        this.entityZdmKeyAuthMap = entityZdmKeyAuthMap;
    }

    public Map<String, String> getUploadEntityZdmKeyMap() {
        return this.uploadEntityZdmKeyMap;
    }

    public void setUploadEntityZdmKeyMap(Map<String, String> uploadEntityZdmKeyMap) {
        this.uploadEntityZdmKeyMap = uploadEntityZdmKeyMap;
    }

    public boolean isNeedDownLoad() {
        return this.needDownLoad;
    }

    public void setNeedDownLoad(boolean needDownLoad) {
        this.needDownLoad = needDownLoad;
    }

    public Map<String, List<SingleDataError>> getLogs() {
        return this.logs;
    }

    public void setLogs(Map<String, List<SingleDataError>> logs) {
        this.logs = logs;
    }

    public void recordLog(String formCode, SingleDataError info) {
        List<Object> logList = null;
        if (this.logs.containsKey(formCode)) {
            logList = this.logs.get(formCode);
        } else {
            logList = new ArrayList();
            this.logs.put(formCode, logList);
        }
        logList.add(info);
    }

    public Map<String, Map<String, List<DataChkInfo>>> getCheckInfos() {
        if (null == this.checkInfos) {
            this.checkInfos = new HashMap<String, Map<String, List<DataChkInfo>>>();
        }
        return this.checkInfos;
    }

    public void setCheckInfos(Map<String, Map<String, List<DataChkInfo>>> checkInfos) {
        this.checkInfos = checkInfos;
    }

    public double getProgress() {
        return this.progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void addProgress(double addProgress) {
        this.progress += addProgress;
    }

    public double getNextProgressLen() {
        return this.nextProgressLen;
    }

    public void setNextProgressLen(double nextProgressLen) {
        this.nextProgressLen = nextProgressLen;
    }

    public String getSyncTaskID() {
        return this.syncTaskID;
    }

    public void setSyncTaskID(String syncTaskID) {
        this.syncTaskID = syncTaskID;
    }

    public Map<String, String> getDownloadEntityKeyZdmMap() {
        if (null == this.downloadEntityKeyZdmMap) {
            this.downloadEntityKeyZdmMap = new LinkedHashMap<String, String>();
        }
        return this.downloadEntityKeyZdmMap;
    }

    public void setDownloadEntityKeyZdmMap(Map<String, String> downloadEntityKeyZdmMap) {
        this.downloadEntityKeyZdmMap = downloadEntityKeyZdmMap;
    }

    public DataEntityCache getEntityCache() {
        if (null == this.entityCache) {
            this.entityCache = new DataEntityCache();
        }
        return this.entityCache;
    }

    public void setEntityCache(DataEntityCache entityCache) {
        this.entityCache = entityCache;
    }

    public Map<String, Object> getIntfObjects() {
        if (null == this.intfObjects) {
            this.intfObjects = new HashMap<String, Object>();
        }
        return this.intfObjects;
    }

    public void setIntfObjects(Map<String, Object> intfObjects) {
        this.intfObjects = intfObjects;
    }

    public String getSingleTaskYear() {
        return this.singleTaskYear;
    }

    public void setSingleTaskYear(String singleTaskYear) {
        this.singleTaskYear = singleTaskYear;
    }

    public String getMapCurrentPeriod() {
        return this.mapCurrentPeriod;
    }

    public void setMapCurrentPeriod(String mapCurrentPeriod) {
        this.mapCurrentPeriod = mapCurrentPeriod;
    }

    public String getMapNetPeriodCode() {
        return this.mapNetPeriodCode;
    }

    public void setMapNetPeriodCode(String mapNetPeriodCode) {
        this.mapNetPeriodCode = mapNetPeriodCode;
    }

    public String getFirstZDM() {
        return this.firstZDM;
    }

    public void setFirstZDM(String firstZDM) {
        this.firstZDM = firstZDM;
    }

    public boolean getEntityFieldIsCode() {
        return true;
    }

    public String getEntityMasterCodeBykey(String netEntityKey) {
        DataEntityInfo entity;
        String newEntityKey = netEntityKey;
        if (this.getEntityFieldIsCode() && null != (entity = this.getEntityCache().findEntityByKey(netEntityKey))) {
            newEntityKey = entity.getEntityCode();
        }
        return newEntityKey;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public int getSourceCorpCount() {
        return this.sourceCorpCount;
    }

    public void setSourceCorpCount(int sourceCorpCount) {
        this.sourceCorpCount = sourceCorpCount;
    }

    public int getSuccessCorpCount() {
        return this.successCorpCount;
    }

    public void setSuccessCorpCount(int successCorpCount) {
        this.successCorpCount = successCorpCount;
    }

    public int getNetCorpCount() {
        return this.netCorpCount;
    }

    public void setNetCorpCount(int netCorpCount) {
        this.netCorpCount = netCorpCount;
    }

    public int getSingleCorpCount() {
        return this.singleCorpCount;
    }

    public void setSingleCorpCount(int singleCorpCount) {
        this.singleCorpCount = singleCorpCount;
    }

    public String getDwEntityId() {
        return this.dwEntityId;
    }

    public void setDwEntityId(String dwEntityId) {
        this.dwEntityId = dwEntityId;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public boolean isNeedCheckRepeat() {
        return this.needCheckRepeat;
    }

    public void setNeedCheckRepeat(boolean needCheckRepeat) {
        this.needCheckRepeat = needCheckRepeat;
    }

    public int getRepeatImportType() {
        return this.repeatImportType;
    }

    public void setRepeatImportType(int repeatImportType) {
        this.repeatImportType = repeatImportType;
    }

    public RepeatImportParam getJioRepeatParam() {
        return this.jioRepeatParam;
    }

    public void setJioRepeatParam(RepeatImportParam jioRepeatParam) {
        this.jioRepeatParam = jioRepeatParam;
    }

    public RepeatImportParam getJioRepeatResult() {
        return this.jioRepeatResult;
    }

    public void setJioRepeatResult(RepeatImportParam jioRepeatResult) {
        this.jioRepeatResult = jioRepeatResult;
    }

    public Map<String, RepeatEntityNode> getRepeatEntityNodes() {
        if (this.repeatEntityNodes == null) {
            this.repeatEntityNodes = new HashMap<String, RepeatEntityNode>();
        }
        if (this.repeatEntityNodes.size() == 0 && this.jioRepeatParam != null && this.jioRepeatParam.getEntityNodes() != null) {
            for (RepeatEntityNode node : this.jioRepeatParam.getEntityNodes()) {
                this.repeatEntityNodes.put(node.getSingleZdm(), node);
            }
        }
        return this.repeatEntityNodes;
    }

    public Map<String, RepeatFormNode> getRepeatFormNodes() {
        if (this.repeatFormNodes == null) {
            this.repeatFormNodes = new HashMap<String, RepeatFormNode>();
        }
        if (this.repeatFormNodes.size() == 0 && this.jioRepeatParam != null && this.jioRepeatParam.getFormNodes() != null) {
            for (RepeatFormNode node : this.jioRepeatParam.getFormNodes()) {
                this.repeatFormNodes.put(node.getFormKey(), node);
            }
        }
        return this.repeatFormNodes;
    }

    public List<String> getDownloadEntityKeys() {
        if (this.downloadEntityKeys == null) {
            this.downloadEntityKeys = new ArrayList<String>();
        }
        return this.downloadEntityKeys;
    }

    public void setDownloadEntityKeys(List<String> downloadEntityKeys) {
        this.downloadEntityKeys = downloadEntityKeys;
    }

    public Set<String> getSingleCorpList() {
        if (this.singleCorpList == null) {
            this.singleCorpList = new HashSet<String>();
        }
        return this.singleCorpList;
    }

    public void setSingleCorpList(Set<String> singleCorpList) {
        this.singleCorpList = singleCorpList;
    }

    public boolean isNeedCheckAuth() {
        return this.needCheckAuth;
    }

    public void setNeedCheckAuth(boolean needCheckAuth) {
        this.needCheckAuth = needCheckAuth;
    }

    public boolean isNeedCheckInfo() {
        return this.needCheckInfo;
    }

    public void setNeedCheckInfo(boolean needCheckInfo) {
        this.needCheckInfo = needCheckInfo;
    }

    public List<String> getCanReadNetEntityKeys() {
        if (this.canReadNetEntityKeys == null) {
            this.canReadNetEntityKeys = new ArrayList<String>();
        }
        return this.canReadNetEntityKeys;
    }

    public void setCanReadNetEntityKeys(List<String> canReadNetEntityKeys) {
        this.canReadNetEntityKeys = canReadNetEntityKeys;
    }

    public boolean isNeedSelectImport() {
        return this.needSelectImport;
    }

    public RepeatImportParam getJioSelectImportParam() {
        return this.jioSelectImportParam;
    }

    public RepeatImportParam getJioSelectImportResult() {
        return this.jioSelectImportResult;
    }

    public Map<String, RepeatEntityNode> getSelectEntityNodes() {
        if (this.selectEntityNodes == null) {
            this.selectEntityNodes = new HashMap<String, RepeatEntityNode>();
        }
        if (this.selectEntityNodes.size() == 0 && this.jioSelectImportParam != null && this.jioSelectImportParam.getEntityNodes() != null) {
            for (RepeatEntityNode node : this.jioSelectImportParam.getEntityNodes()) {
                this.selectEntityNodes.put(node.getSingleZdm(), node);
            }
        }
        return this.selectEntityNodes;
    }

    public Map<String, RepeatFormNode> getSelectFormNodes() {
        if (this.selectFormNodes == null) {
            this.selectFormNodes = new HashMap<String, RepeatFormNode>();
        }
        if (this.selectFormNodes.size() == 0 && this.jioSelectImportParam != null && this.jioSelectImportParam.getFormNodes() != null) {
            for (RepeatFormNode node : this.jioSelectImportParam.getFormNodes()) {
                this.selectFormNodes.put(node.getFormKey(), node);
            }
        }
        return this.selectFormNodes;
    }

    public void setNeedSelectImport(boolean needSelectImport) {
        this.needSelectImport = needSelectImport;
    }

    public void setJioSelectImportParam(RepeatImportParam jioSelectImportParam) {
        this.jioSelectImportParam = jioSelectImportParam;
    }

    public void setJioSelectImportResult(RepeatImportParam jioSelectImportResult) {
        this.jioSelectImportResult = jioSelectImportResult;
    }

    public Map<String, List<String>> getEntityMergeZdmMap() {
        if (this.entityMergeZdmMap == null) {
            this.entityMergeZdmMap = new HashMap<String, List<String>>();
        }
        return this.entityMergeZdmMap;
    }

    public Set<String> getSingleMergeZdms() {
        if (this.singleMergeZdms == null) {
            this.singleMergeZdms = new HashSet<String>();
        }
        return this.singleMergeZdms;
    }

    public void setSingleMergeZdms(Set<String> singleMergeZdms) {
        this.singleMergeZdms = singleMergeZdms;
    }

    public Map<String, DimensionValue> getOtherDims() {
        if (this.otherDims == null) {
            this.otherDims = new HashMap<String, DimensionValue>();
        }
        return this.otherDims;
    }

    public void setOtherDims(Map<String, DimensionValue> otherDims) {
        this.otherDims = otherDims;
    }

    public Map<String, String> getEntityMergeBenJiMap() {
        if (this.entityMergeBenJiMap == null) {
            this.entityMergeBenJiMap = new HashMap<String, String>();
        }
        return this.entityMergeBenJiMap;
    }

    public void setEntityMergeBenJiMap(Map<String, String> entityMergeBenJiMap) {
        this.entityMergeBenJiMap = entityMergeBenJiMap;
    }

    public boolean isDeleteEmptyData() {
        return this.deleteEmptyData;
    }

    public void setDeleteEmptyData(boolean deleteEmptyData) {
        this.deleteEmptyData = deleteEmptyData;
    }

    public DimEntityCache getDimEntityCache() {
        if (this.dimEntityCache == null) {
            this.dimEntityCache = new DimEntityCache();
        }
        return this.dimEntityCache;
    }

    public void setDimEntityCache(DimEntityCache dimEntityCache) {
        this.dimEntityCache = dimEntityCache;
    }

    public DataMapingCache getMapingCache() {
        if (this.mapingCache == null) {
            this.mapingCache = new DataMapingCache();
        }
        return this.mapingCache;
    }

    public void setMapingCache(DataMapingCache mapingCache) {
        this.mapingCache = mapingCache;
    }

    public int getFjUploadMode() {
        return this.fjUploadMode;
    }

    public void setFjUploadMode(int fjUploadMode) {
        this.fjUploadMode = fjUploadMode;
    }

    public void addFieldFileInfo(SingleFieldFileInfo fileInfo) {
        List<Object> formList = null;
        if (this.getFormFJFileMap().containsKey(fileInfo.getFormKey())) {
            formList = this.getFormFJFileMap().get(fileInfo.getFormKey());
        } else {
            formList = new ArrayList();
            this.getFormFJFileMap().put(fileInfo.getFormKey(), formList);
        }
        formList.add(fileInfo);
    }

    public Map<String, List<SingleFieldFileInfo>> getFormFJFileMap() {
        if (this.formFJFileMap == null) {
            this.formFJFileMap = new HashMap<String, List<SingleFieldFileInfo>>();
        }
        return this.formFJFileMap;
    }

    public void setFormFJFileMap(Map<String, List<SingleFieldFileInfo>> formFJFileMap) {
        this.formFJFileMap = formFJFileMap;
    }

    public String getJioZipFile() {
        return this.jioZipFile;
    }

    public void setJioZipFile(String jioZipFile) {
        this.jioZipFile = jioZipFile;
    }

    public String getTaskFilePath() {
        return this.taskFilePath;
    }

    public void setTaskFilePath(String taskFilePath) {
        this.taskFilePath = taskFilePath;
    }

    public String getTaskDocPath() {
        return this.taskDocPath;
    }

    public void setTaskDocPath(String taskDocPath) {
        this.taskDocPath = taskDocPath;
    }

    public String getTaskImgPath() {
        return this.taskImgPath;
    }

    public void setTaskImgPath(String taskImgPath) {
        this.taskImgPath = taskImgPath;
    }

    public String getTaskTxtPath() {
        return this.taskTxtPath;
    }

    public void setTaskTxtPath(String taskTxtPath) {
        this.taskTxtPath = taskTxtPath;
    }

    public String getTaskRptPath() {
        return this.taskRptPath;
    }

    public void setTaskRptPath(String taskRptPath) {
        this.taskRptPath = taskRptPath;
    }

    public String getSingleTaskTitle() {
        return this.singleTaskTitle;
    }

    public void setSingleTaskTitle(String singleTaskTitle) {
        this.singleTaskTitle = singleTaskTitle;
    }

    public String getSinglePeriodType() {
        return this.singlePeriodType;
    }

    public void setSinglePeriodType(String singlePeriodType) {
        this.singlePeriodType = singlePeriodType;
    }

    public String getMeasureKey() {
        return this.measureKey;
    }

    public void setMeasureKey(String measureKey) {
        this.measureKey = measureKey;
    }

    public String getMeasureCode() {
        return this.measureCode;
    }

    public void setMeasureCode(String measureCode) {
        this.measureCode = measureCode;
    }

    public String getMeasureDecimal() {
        return this.measureDecimal;
    }

    public void setMeasureDecimal(String measureDecimal) {
        this.measureDecimal = measureDecimal;
    }

    public boolean isHasEntityCheck() {
        return this.hasEntityCheck;
    }

    public void setHasEntityCheck(boolean hasEntityCheck) {
        this.hasEntityCheck = hasEntityCheck;
    }

    public ZipParam getZipParam() {
        if (this.zipParam == null) {
            this.zipParam = new ZipParam();
        }
        return this.zipParam;
    }

    public void setZipParam(ZipParam zipParam) {
        this.zipParam = zipParam;
    }

    public AsyncTaskMonitor getAsyncTaskMonitor() {
        return this.asyncTaskMonitor;
    }

    public void setAsyncTaskMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateProgressAsyn(TaskDataContext importContext, AsyncTaskMonitor asyncTaskMonitor, double addProgress) {
        if (asyncTaskMonitor != null) {
            TaskDataContext taskDataContext = this;
            synchronized (taskDataContext) {
                this.addProgress(addProgress);
                asyncTaskMonitor.progressAndMessage(importContext.getProgress(), "\u62a5\u8868\u6570\u636e\u5bfc\u5165");
            }
        }
    }

    public void info(Logger logger, String message) {
        this.info(message);
        if (logger != null) {
            logger.info(message);
        }
    }

    public void info(String message) {
        ILogger biLogger;
        this.curMessage = message;
        if (this.asyncTaskMonitor != null && (biLogger = this.asyncTaskMonitor.getBILogger()) != null) {
            biLogger.info(message);
        }
    }

    public void error(Logger logger, String message, Throwable t) {
        this.error(message, t);
        if (logger != null) {
            logger.error(message, t);
        }
    }

    public void error(String message, Throwable t) {
        ILogger biLogger;
        this.curMessage = message;
        if (this.asyncTaskMonitor != null && (biLogger = this.asyncTaskMonitor.getBILogger()) != null) {
            biLogger.error(message);
        }
    }

    public boolean isNeedChangeUpper() {
        return this.needChangeUpper;
    }

    public void setNeedChangeUpper(boolean needChangeUpper) {
        this.needChangeUpper = needChangeUpper;
    }

    public Map<String, String> getFjZdmDirs() {
        if (this.fjZdmDirs == null) {
            this.fjZdmDirs = new CaseInsensitiveMap<String, String>();
        }
        return this.fjZdmDirs;
    }

    public void setFjZdmDirs(Map<String, String> fjZdmDirs) {
        this.fjZdmDirs = fjZdmDirs;
    }

    public boolean isNeedCheckCorpTree() {
        return this.needCheckCorpTree;
    }

    public void setNeedCheckCorpTree(boolean needCheckCorpTree) {
        this.needCheckCorpTree = needCheckCorpTree;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isHasQueryCheck() {
        return this.hasQueryCheck;
    }

    public void setHasQueryCheck(boolean hasQueryCheck) {
        this.hasQueryCheck = hasQueryCheck;
    }

    public int getAttachFileNum() {
        return this.attachFileNum;
    }

    public void setAttachFileNum(int attachFileNum) {
        this.attachFileNum = attachFileNum;
    }

    public int getPeriodAttachFileNum() {
        return this.periodAttachFileNum;
    }

    public void setPeriodAttachFileNum(int periodAttachFileNum) {
        this.periodAttachFileNum = periodAttachFileNum;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateAttachFileNumAsyn(int addFileCount) {
        if (this.asyncTaskMonitor != null) {
            TaskDataContext taskDataContext = this;
            synchronized (taskDataContext) {
                this.attachFileNum += addFileCount;
                this.periodAttachFileNum += addFileCount;
            }
        }
    }

    public int getImportedFormNum() {
        return this.importedFormNum;
    }

    public void setImportedFormNum(int importedFormNum) {
        this.importedFormNum = importedFormNum;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updatImportedFormNumAsyn(int addFormCount) {
        if (this.asyncTaskMonitor != null) {
            TaskDataContext taskDataContext = this;
            synchronized (taskDataContext) {
                this.importedFormNum += addFormCount;
            }
        }
    }

    public String getCurMessage() {
        return this.curMessage;
    }

    public void setCurMessage(String curMessage) {
        this.curMessage = curMessage;
    }
}

