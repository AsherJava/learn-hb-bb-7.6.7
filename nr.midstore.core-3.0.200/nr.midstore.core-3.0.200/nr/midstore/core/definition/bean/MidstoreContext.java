/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package nr.midstore.core.definition.bean;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.midstore.core.definition.bean.MappingCache;
import nr.midstore.core.definition.bean.MistoreWorkResultObject;
import nr.midstore.core.definition.bean.MistoreWorkUnitInfo;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;

public class MidstoreContext {
    private AsyncTaskMonitor asyncMonitor;
    private String schemeKey;
    private String formSchemeKey;
    private String taskKey;
    private String excutePeriod;
    private String excuteUserName;
    private String configKey;
    private String entityTypeName;
    private String dateTypeName;
    private MidstoreSchemeDTO midstoreScheme;
    private MidstoreSchemeInfoDTO SchemeInfo;
    private TaskDefine taskDefine;
    private MappingCache mappingCache;
    private Map<String, Object> intfObjects = null;
    private List<String> exchangeEnityCodes;
    private Map<String, Object> excuteParams;
    private MistoreWorkResultObject workResult;
    private Map<String, MistoreWorkUnitInfo> unitCache;
    private boolean deleteEmptyData = false;

    public MidstoreContext() {
    }

    public MidstoreContext(String midstoreSchemeKey) {
        this.schemeKey = midstoreSchemeKey;
    }

    public AsyncTaskMonitor getAsyncMonitor() {
        return this.asyncMonitor;
    }

    public void setAsyncMonitor(AsyncTaskMonitor asyncMonitor) {
        this.asyncMonitor = asyncMonitor;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public MidstoreSchemeDTO getMidstoreScheme() {
        return this.midstoreScheme;
    }

    public void setMidstoreScheme(MidstoreSchemeDTO midstoreScheme) {
        this.midstoreScheme = midstoreScheme;
    }

    public MidstoreSchemeInfoDTO getSchemeInfo() {
        return this.SchemeInfo;
    }

    public void setSchemeInfo(MidstoreSchemeInfoDTO schemeInfo) {
        this.SchemeInfo = schemeInfo;
    }

    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        if (taskDefine != null) {
            this.taskKey = taskDefine.getKey();
        }
        this.taskDefine = taskDefine;
    }

    public String getExcutePeriod() {
        return this.excutePeriod;
    }

    public void setExcutePeriod(String excutePeriod) {
        this.excutePeriod = excutePeriod;
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public MappingCache getMappingCache() {
        if (this.mappingCache == null) {
            this.mappingCache = new MappingCache();
        }
        return this.mappingCache;
    }

    public void setMappingCache(MappingCache mappingCache) {
        this.mappingCache = mappingCache;
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

    public String getEntityTypeName() {
        return this.entityTypeName;
    }

    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getDateTypeName() {
        return this.dateTypeName;
    }

    public void setDateTypeName(String dateTypeName) {
        this.dateTypeName = dateTypeName;
    }

    public List<String> getExchangeEnityCodes() {
        if (this.exchangeEnityCodes == null) {
            this.exchangeEnityCodes = new ArrayList<String>();
        }
        return this.exchangeEnityCodes;
    }

    public void setExchangeEnityCodes(List<String> exchangeEnityCodes) {
        this.exchangeEnityCodes = exchangeEnityCodes;
    }

    public Map<String, Object> getExcuteParams() {
        if (this.excuteParams == null) {
            this.excuteParams = new HashMap<String, Object>();
        }
        return this.excuteParams;
    }

    public void setExcuteParams(Map<String, Object> excuteParams) {
        this.excuteParams = excuteParams;
    }

    public MistoreWorkResultObject getWorkResult() {
        return this.workResult;
    }

    public void setWorkResult(MistoreWorkResultObject workResult) {
        this.workResult = workResult;
    }

    public Map<String, MistoreWorkUnitInfo> getUnitCache() {
        if (this.unitCache == null) {
            this.unitCache = new HashMap<String, MistoreWorkUnitInfo>();
        }
        return this.unitCache;
    }

    public void setUnitCache(Map<String, MistoreWorkUnitInfo> unitCache) {
        this.unitCache = unitCache;
    }

    public String getExcuteUserName() {
        return this.excuteUserName;
    }

    public void setExcuteUserName(String excuteUserName) {
        this.excuteUserName = excuteUserName;
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

    public boolean isDeleteEmptyData() {
        return this.deleteEmptyData;
    }

    public void setDeleteEmptyData(boolean deleteEmptyData) {
        this.deleteEmptyData = deleteEmptyData;
    }
}

