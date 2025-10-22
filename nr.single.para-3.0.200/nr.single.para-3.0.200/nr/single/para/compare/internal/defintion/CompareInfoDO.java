/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion;

import java.nio.charset.StandardCharsets;
import java.sql.Clob;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import nr.single.para.compare.definition.ICompareInfo;
import nr.single.para.compare.definition.common.CompareStatusType;
import nr.single.para.compare.definition.exception.DBAnno;
import org.springframework.beans.BeanUtils;

@DBAnno.DBTable(dbTable="NR_SINGLE_COMPARE_INFO")
public class CompareInfoDO
implements ICompareInfo {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="CI_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="CI_CODE")
    protected String code;
    @DBAnno.DBField(dbField="CI_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="CI_FILEFLAG")
    protected String fileFlag;
    @DBAnno.DBField(dbField="CI_TASKYEAR")
    protected String taskYear;
    @DBAnno.DBField(dbField="CI_TASKTITLE")
    protected String taskTitle;
    @DBAnno.DBField(dbField="CI_TASK_KEY")
    protected String taskKey;
    @DBAnno.DBField(dbField="CI_FORMSCHEME_KEY")
    protected String formSchemeKey;
    @DBAnno.DBField(dbField="CI_DATASCHEME_KEY")
    protected String dataSchemeKey;
    @DBAnno.DBField(dbField="CI_STATUS", tranWith="transCompareStatusType", dbType=Integer.class, appType=CompareStatusType.class)
    protected CompareStatusType status;
    @DBAnno.DBField(dbField="CI_JIOFILE")
    protected String jioFile;
    @DBAnno.DBField(dbField="CI_JIOFILE_KEY")
    protected String jioFileKey;
    @DBAnno.DBField(dbField="CI_LOGFILE")
    protected String logFile;
    @DBAnno.DBField(dbField="CI_LOGFILE_KEY")
    protected String logFileKey;
    @DBAnno.DBField(dbField="CI_JIODATA", tranWith="transClob", dbType=Clob.class, appType=String.class)
    protected String jioData;
    @DBAnno.DBField(dbField="CI_MAPDATA", tranWith="transClob", dbType=Clob.class, appType=String.class)
    protected String mapData;
    @DBAnno.DBField(dbField="CI_OPTIONDATA")
    protected String optionData;
    @DBAnno.DBField(dbField="CI_MESSAGE")
    protected String message;
    @DBAnno.DBField(dbField="CI_ORDER", isOrder=true)
    protected String order;
    @DBAnno.DBField(dbField="CI_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    protected Instant updateTime;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public Instant getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public CompareStatusType getStatus() {
        return this.status;
    }

    @Override
    public String getJioData() {
        return this.jioData;
    }

    @Override
    public String getMapData() {
        return this.mapData;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public void setStatus(CompareStatusType status) {
        this.status = status;
    }

    public void setJioData(byte[] jioData) {
        this.jioData = new String(jioData, StandardCharsets.UTF_8);
    }

    public void setMapData(byte[] mapData) {
        this.mapData = new String(mapData, StandardCharsets.UTF_8);
    }

    public void setJioData(String jioData) {
        this.jioData = jioData;
    }

    public void setMapData(String mapData) {
        this.mapData = mapData;
    }

    public CompareInfoDO clone() {
        try {
            return (CompareInfoDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519");
        }
    }

    public String toString() {
        return "CompareInfoDO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", taskKey='" + this.taskKey + '\'' + ", formSchemeKey='" + this.formSchemeKey + '\'' + ", status=" + (Object)((Object)this.status) + ", jioData='" + this.jioData + '\'' + ", mapData='" + this.mapData + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CompareInfoDO that = (CompareInfoDO)o;
        return Objects.equals(this.key, that.getKey());
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static CompareInfoDO valueOf(CompareInfoDO o) {
        if (o == null) {
            return null;
        }
        CompareInfoDO t = new CompareInfoDO();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    @Override
    public String getJioFile() {
        return this.jioFile;
    }

    public void setJioFile(String jioFile) {
        this.jioFile = jioFile;
    }

    @Override
    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    @Override
    public String getOptionData() {
        return this.optionData;
    }

    public void setOptionData(String optionData) {
        this.optionData = optionData;
    }

    @Override
    public String getJioFileKey() {
        return this.jioFileKey;
    }

    public void setJioFileKey(String jioFileKey) {
        this.jioFileKey = jioFileKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String getLogFile() {
        return this.logFile;
    }

    @Override
    public String getLogFileKey() {
        return this.logFileKey;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public void setLogFileKey(String logFileKey) {
        this.logFileKey = logFileKey;
    }

    @Override
    public String getTaskYear() {
        return this.taskYear;
    }

    public void setTaskYear(String taskYear) {
        this.taskYear = taskYear;
    }

    @Override
    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    @Override
    public String getFileFlag() {
        return this.fileFlag;
    }

    public void setFileFlag(String fileFlag) {
        this.fileFlag = fileFlag;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

