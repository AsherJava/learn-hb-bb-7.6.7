/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.para.ParaInfo
 */
package nr.single.para.paraout.bean;

import com.jiuqi.nr.single.core.para.ParaInfo;
import nr.single.para.paraout.bean.FormSchemeInfoCache;
import nr.single.para.paraout.bean.ParaExportParam;
import nr.single.para.paraout.bean.ParaExportResult;

public class TaskExportContext {
    private String taskKey;
    private String formSchemeKey;
    private ParaExportParam exportParam;
    private ParaExportResult result;
    private ParaInfo paraInfo;
    private FormSchemeInfoCache schemeInfo;

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

    public ParaExportParam getExportParam() {
        return this.exportParam;
    }

    public void setExportParam(ParaExportParam exportParam) {
        this.exportParam = exportParam;
    }

    public ParaExportResult getResult() {
        return this.result;
    }

    public void setResult(ParaExportResult result) {
        this.result = result;
    }

    public ParaInfo getParaInfo() {
        return this.paraInfo;
    }

    public void setParaInfo(ParaInfo paraInfo) {
        this.paraInfo = paraInfo;
    }

    public FormSchemeInfoCache getSchemeInfo() {
        if (this.schemeInfo == null) {
            this.schemeInfo = new FormSchemeInfoCache();
        }
        return this.schemeInfo;
    }

    public void setSchemeInfo(FormSchemeInfoCache formSchemeInfo) {
        this.schemeInfo = formSchemeInfo;
    }
}

