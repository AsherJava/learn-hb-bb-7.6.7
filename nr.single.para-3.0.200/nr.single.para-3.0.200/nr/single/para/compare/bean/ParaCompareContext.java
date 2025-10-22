/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.JIOParamParser
 */
package nr.single.para.compare.bean;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.JIOParamParser;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nr.single.para.compare.bean.ParaCompareCache;
import nr.single.para.compare.bean.ParaCompareOption;
import nr.single.para.compare.bean.ParaCompareResult;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.parain.internal.cache.SchemeInfoCache;

public class ParaCompareContext {
    private AsyncTaskMonitor asyncMonitor;
    private double curProgress = 0.0;
    private double curProgressLength;
    private JIOParamParser jioParser;
    private ParaInfo paraInfo;
    private ParaCompareOption option;
    private ParaCompareResult comapreResult;
    private String taskKey;
    private String formSchemeKey;
    private String dataSchemeKey;
    private CompareDataType compareDataType;
    protected CompareContextType enumCompareType;
    protected Map<String, Object> params;
    private ParaCompareCache cache;
    private SchemeInfoCache schemeInfoCache;
    private boolean uniqueField = false;
    private Map<String, Set<String>> singleRepeatRepField;

    public AsyncTaskMonitor getAsyncMonitor() {
        return this.asyncMonitor;
    }

    public void setAsyncMonitor(AsyncTaskMonitor asyncMonitor) {
        this.asyncMonitor = asyncMonitor;
    }

    public double getCurProgress() {
        return this.curProgress;
    }

    public void setCurProgress(double curProgress) {
        this.curProgress = curProgress;
    }

    public void onProgress(double progress) {
        if (this.curProgress >= progress) {
            return;
        }
        this.curProgress = progress;
        if (null != this.asyncMonitor) {
            this.asyncMonitor.progressAndMessage(progress, "");
        }
    }

    public void onProgress(double progress, String msg) {
        if (this.curProgress >= progress) {
            return;
        }
        this.curProgress = progress;
        if (null != this.asyncMonitor) {
            this.asyncMonitor.progressAndMessage(progress, msg);
        }
    }

    public JIOParamParser getJioParser() {
        return this.jioParser;
    }

    public void setJioParser(JIOParamParser jioParser) {
        this.jioParser = jioParser;
    }

    public ParaInfo getParaInfo() {
        return this.paraInfo;
    }

    public void setParaInfo(ParaInfo paraInfo) {
        this.paraInfo = paraInfo;
    }

    public ParaCompareOption getOption() {
        return this.option;
    }

    public void setOption(ParaCompareOption option) {
        this.option = option;
    }

    public ParaCompareResult getComapreResult() {
        return this.comapreResult;
    }

    public void setComapreResult(ParaCompareResult comapreResult) {
        this.comapreResult = comapreResult;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public ParaCompareCache getCache() {
        if (this.cache == null) {
            this.cache = new ParaCompareCache();
        }
        return this.cache;
    }

    public void setCache(ParaCompareCache cache) {
        this.cache = cache;
    }

    public CompareContextType getEnumCompareType() {
        return this.enumCompareType;
    }

    public void setEnumCompareType(CompareContextType enumCompareType) {
        this.enumCompareType = enumCompareType;
    }

    public double getCurProgressLength() {
        return this.curProgressLength;
    }

    public void setCurProgressLength(double curProgressLength) {
        this.curProgressLength = curProgressLength;
    }

    public SchemeInfoCache getSchemeInfoCache() {
        if (this.schemeInfoCache == null) {
            this.schemeInfoCache = new SchemeInfoCache();
        }
        return this.schemeInfoCache;
    }

    public void setSchemeInfoCache(SchemeInfoCache schemeInfoCache) {
        this.schemeInfoCache = schemeInfoCache;
    }

    public Map<String, Object> getParams() {
        if (this.params == null) {
            this.params = new HashMap<String, Object>();
        }
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public boolean isUniqueField() {
        return this.uniqueField;
    }

    public void setUniqueField(boolean uniqueField) {
        this.uniqueField = uniqueField;
    }

    public CompareDataType getCompareDataType() {
        return this.compareDataType;
    }

    public void setCompareDataType(CompareDataType compareDataType) {
        this.compareDataType = compareDataType;
    }

    public Map<String, Set<String>> getSingleRepeatRepField() {
        if (this.singleRepeatRepField == null) {
            this.singleRepeatRepField = new HashMap<String, Set<String>>();
        }
        return this.singleRepeatRepField;
    }

    public void setSingleRepeatRepField(Map<String, Set<String>> singleRepeatRepField) {
        this.singleRepeatRepField = singleRepeatRepField;
    }
}

