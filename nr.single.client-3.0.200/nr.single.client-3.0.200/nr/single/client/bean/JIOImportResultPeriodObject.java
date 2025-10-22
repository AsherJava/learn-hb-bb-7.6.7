/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.ReportImportResultObject
 *  nr.single.map.data.bean.RepeatImportParam
 */
package nr.single.client.bean;

import com.jiuqi.nr.dataentry.bean.ReportImportResultObject;
import java.io.Serializable;
import nr.single.map.data.bean.RepeatImportParam;

public class JIOImportResultPeriodObject
extends ReportImportResultObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String singlePeriodCode;
    private String singlePeriodTitle;
    private String netPeriodCode;
    private String netPeriodTitle;
    private RepeatImportParam jioRepeatParm = null;
    private RepeatImportParam jioSelectParm = null;
    private String jioTaskFlag;
    private String jioFileFlag;
    private int errorLevel = 0;
    private int attachFileNum = 0;

    public RepeatImportParam getJioRepeatParm() {
        return this.jioRepeatParm;
    }

    public void setJioRepeatParm(RepeatImportParam jioRepeatParm) {
        this.jioRepeatParm = jioRepeatParm;
    }

    public String getJioTaskFlag() {
        return this.jioTaskFlag;
    }

    public void setJioTaskFlag(String jioTaskFlag) {
        this.jioTaskFlag = jioTaskFlag;
    }

    public String getJioFileFlag() {
        return this.jioFileFlag;
    }

    public void setJioFileFlag(String jioFileFlag) {
        this.jioFileFlag = jioFileFlag;
    }

    public RepeatImportParam getJioSelectParm() {
        return this.jioSelectParm;
    }

    public void setJioSelectParm(RepeatImportParam jioSelectParm) {
        this.jioSelectParm = jioSelectParm;
    }

    public String getSinglePeriodCode() {
        return this.singlePeriodCode;
    }

    public void setSinglePeriodCode(String singlePeriodCode) {
        this.singlePeriodCode = singlePeriodCode;
    }

    public String getNetPeriodCode() {
        return this.netPeriodCode;
    }

    public void setNetPeriodCode(String netPeriodCode) {
        this.netPeriodCode = netPeriodCode;
    }

    public String getNetPeriodTitle() {
        return this.netPeriodTitle;
    }

    public void setNetPeriodTitle(String netPeriodTitle) {
        this.netPeriodTitle = netPeriodTitle;
    }

    public String getSinglePeriodTitle() {
        return this.singlePeriodTitle;
    }

    public void setSinglePeriodTitle(String singlePeriodTitle) {
        this.singlePeriodTitle = singlePeriodTitle;
    }

    public int getErrorLevel() {
        return this.errorLevel;
    }

    public void setErrorLevel(int errorLevel) {
        this.errorLevel = errorLevel;
    }

    public int getAttachFileNum() {
        return this.attachFileNum;
    }

    public void setAttachFileNum(int attachFileNum) {
        this.attachFileNum = attachFileNum;
    }
}

