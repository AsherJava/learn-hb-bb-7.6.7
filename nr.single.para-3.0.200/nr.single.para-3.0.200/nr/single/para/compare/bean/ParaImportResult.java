/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.para.compare.bean.ParaImportInfoResult;
import nr.single.para.compare.definition.common.CompareDataType;

public class ParaImportResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private boolean success;
    private int successCount;
    private String logFileKey;
    private String logFile;
    private String formSchemeKey;
    private String mapSchemeKey;
    private List<ParaImportInfoResult> infos;
    private Map<CompareDataType, ParaImportInfoResult> infoDic;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<ParaImportInfoResult> getInfos() {
        if (this.infos == null) {
            this.infos = new ArrayList<ParaImportInfoResult>();
        }
        return this.infos;
    }

    public void setInfos(List<ParaImportInfoResult> infos) {
        this.infos = infos;
    }

    public int getSuccessCount() {
        return this.successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public String getLogFileKey() {
        return this.logFileKey;
    }

    public void setLogFileKey(String logFileKey) {
        this.logFileKey = logFileKey;
    }

    public String getLogFile() {
        return this.logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public Map<CompareDataType, ParaImportInfoResult> getInfoDic() {
        if (this.infoDic == null) {
            this.infoDic = new HashMap<CompareDataType, ParaImportInfoResult>();
        }
        return this.infoDic;
    }

    public void setInfoDic(Map<CompareDataType, ParaImportInfoResult> infoDic) {
        this.infoDic = infoDic;
    }

    public void addLogInfo(ParaImportInfoResult logInfo) {
        if (logInfo != null && !this.getInfoDic().containsKey((Object)logInfo.getDataType())) {
            this.getInfoDic().put(logInfo.getDataType(), logInfo);
            this.getInfos().add(logInfo);
        }
    }

    public ParaImportInfoResult getLogInfo(CompareDataType dataType) {
        ParaImportInfoResult logInfo = null;
        if (this.getInfoDic().containsKey((Object)dataType)) {
            logInfo = this.getInfoDic().get((Object)dataType);
        }
        return logInfo;
    }

    public ParaImportInfoResult getLogInfo(CompareDataType dataType, String singleCode, String singleTitle) {
        ParaImportInfoResult logInfo = null;
        if (this.getInfoDic().containsKey((Object)dataType)) {
            logInfo = this.getInfoDic().get((Object)dataType);
        } else {
            logInfo = new ParaImportInfoResult();
            logInfo.setDataType(dataType);
            logInfo.setSingleCode(singleCode);
            logInfo.setSingleTitle(singleTitle);
            this.addLogInfo(logInfo);
        }
        return logInfo;
    }

    public String getMapSchemeKey() {
        return this.mapSchemeKey;
    }

    public void setMapSchemeKey(String mapSchemeKey) {
        this.mapSchemeKey = mapSchemeKey;
    }
}

