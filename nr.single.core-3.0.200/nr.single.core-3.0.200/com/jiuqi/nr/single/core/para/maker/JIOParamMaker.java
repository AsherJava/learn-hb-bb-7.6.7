/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.maker;

import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.maker.EnumMaker;
import com.jiuqi.nr.single.core.para.maker.JQTMaker;
import com.jiuqi.nr.single.core.para.maker.SolutionMaker;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JIOParamMaker {
    private static final Logger logger = LoggerFactory.getLogger(EnumMaker.class);
    private ParaInfo paraInfo;
    private String filePath;
    private boolean succeed;
    private Map<String, String> linkList;
    private boolean hasWarning = false;
    private String taskName;

    public JIOParamMaker(ParaInfo paraInfo, String path) {
        this.filePath = path.charAt(path.length() - 1) == File.separatorChar ? path : path + File.separatorChar;
        this.paraInfo = paraInfo;
    }

    public final void make() throws Exception {
        try {
            this.exportSolution();
            this.exportEnum();
            this.exportJQT();
            this.exportFormula();
            this.exportTaskLink();
            this.exportPrint();
            this.exportQueryModal();
            if (this.paraInfo.getExceptionList().size() > 0) {
                this.hasWarning = true;
            }
            this.setSucceed(true);
        }
        catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            this.setSucceed(false);
            this.paraInfo.getExceptionList().add("\u53c2\u6570\u4fdd\u5b58\u5b58\u5728\u5f02\u5e38\uff1a" + e.getMessage());
            throw e;
        }
    }

    private void exportSolution() throws StreamException {
        SolutionMaker solutionParser = new SolutionMaker();
        solutionParser.initDirName(this.filePath);
        solutionParser.make(this.paraInfo);
    }

    private void exportEnum() throws Exception {
        EnumMaker baseDataMaker = new EnumMaker();
        baseDataMaker.InitDirName(this.filePath);
        baseDataMaker.make(this.paraInfo);
    }

    private void exportJQT() throws Exception {
        JQTMaker jqtMaker = new JQTMaker();
        jqtMaker.InitDirName(this.filePath);
        jqtMaker.saveInfos(this.paraInfo);
    }

    private void exportFormula() throws IOException, StreamException {
    }

    private void exportPrint() throws IOException, StreamException {
    }

    private void exportTaskLink() throws IOException, StreamException {
    }

    private void exportQueryModal() throws IOException, StreamException {
    }

    public final boolean isSucceed() {
        return this.succeed;
    }

    public final void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public final Map<String, String> getLinkList() {
        return this.linkList;
    }

    public final void setLinkList(Map<String, String> linkList) {
        this.linkList = linkList;
    }

    public final boolean isHasWarning() {
        return this.hasWarning;
    }

    public final void setHasWarning(boolean hasWarning) {
        this.hasWarning = hasWarning;
    }

    public final String getTaskName() {
        return this.taskName;
    }

    public final void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public final ParaInfo getParaInfo() {
        return this.paraInfo;
    }
}

