/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.data.bean.RepeatEntityNode;
import nr.single.map.data.bean.RepeatFormNode;

public class RepeatImportParam
implements Serializable {
    private static final long serialVersionUID = -591801954163156517L;
    private List<RepeatEntityNode> entityNodes;
    private List<RepeatFormNode> formNodes;
    private boolean keepOldEnityTree;
    private boolean hasOtherPeriod;
    private String curNetPeriod;
    private List<String> singlePeriods;
    private String fileKey;
    private String filePath;
    private String fileName;
    private int netCorpCount;
    private boolean repeatByFile;

    public List<RepeatEntityNode> getEntityNodes() {
        if (this.entityNodes == null) {
            this.entityNodes = new ArrayList<RepeatEntityNode>();
        }
        return this.entityNodes;
    }

    public void setEntityNodes(List<RepeatEntityNode> entityNodes) {
        this.entityNodes = entityNodes;
    }

    public List<RepeatFormNode> getFormNodes() {
        if (this.formNodes == null) {
            this.formNodes = new ArrayList<RepeatFormNode>();
        }
        return this.formNodes;
    }

    public void setFormNodes(List<RepeatFormNode> formNodes) {
        this.formNodes = formNodes;
    }

    public boolean isKeepOldEnityTree() {
        return this.keepOldEnityTree;
    }

    public void setKeepOldEnityTree(boolean keepOldEnityTree) {
        this.keepOldEnityTree = keepOldEnityTree;
    }

    public boolean isHasOtherPeriod() {
        return this.hasOtherPeriod;
    }

    public void setHasOtherPeriod(boolean hasOtherPeriod) {
        this.hasOtherPeriod = hasOtherPeriod;
    }

    public String getCurNetPeriod() {
        return this.curNetPeriod;
    }

    public void setCurNetPeriod(String curNetPeriod) {
        this.curNetPeriod = curNetPeriod;
    }

    public List<String> getSinglePeriods() {
        return this.singlePeriods;
    }

    public void setSinglePeriods(List<String> singlePeriods) {
        this.singlePeriods = singlePeriods;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isRepeatByFile() {
        return this.repeatByFile;
    }

    public void setRepeatByFile(boolean repeatByFile) {
        this.repeatByFile = repeatByFile;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getNetCorpCount() {
        return this.netCorpCount;
    }

    public void setNetCorpCount(int netCorpCount) {
        this.netCorpCount = netCorpCount;
    }
}

