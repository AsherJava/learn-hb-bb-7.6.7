/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.parser;

import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.FormulaParse;
import com.jiuqi.nr.single.core.para.parser.JQTParser;
import com.jiuqi.nr.single.core.para.parser.SolutionParser;
import com.jiuqi.nr.single.core.para.parser.TaskLinkParser;
import com.jiuqi.nr.single.core.para.parser.anal.AnalParse;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumParser;
import com.jiuqi.nr.single.core.para.parser.print.PrintParse;
import com.jiuqi.nr.single.core.para.parser.query.QueryParse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JIOParamParser {
    private static final Logger logger = LoggerFactory.getLogger(JIOParamParser.class);
    private ParaInfo paraInfo;
    private String filePath;
    private boolean isModify;
    private boolean succeed;
    private Map<String, String> linkList;
    private boolean hasWarning = false;
    private String taskName;
    private List<InOutDataType> inOutData;

    public JIOParamParser(String path, boolean isModify) {
        this.filePath = path.charAt(path.length() - 1) == File.separatorChar ? path : path + File.separatorChar;
        this.paraInfo = new ParaInfo(this.filePath);
        this.paraInfo.setParaType(0);
        this.isModify = isModify;
    }

    public final void parse() throws Exception {
        try {
            this.paraInfo.setModify(this.isModify);
            this.importSolution();
            if (this.getInOutData().indexOf((Object)InOutDataType.BBCS) >= 0) {
                this.importEnum();
                this.importJQT();
                this.importFormula();
                this.importTaskLink();
                this.importPrint();
                this.importInnerAnalTable();
            }
            if (this.getInOutData().indexOf((Object)InOutDataType.CXMB) >= 0) {
                this.importQueryModal();
            }
            if (this.getInOutData().indexOf((Object)InOutDataType.CSCS) >= 0) {
                this.importAnalTable();
            }
            if (this.paraInfo.getExceptionList().size() > 0) {
                this.hasWarning = true;
            }
            this.setSucceed(true);
        }
        catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            this.setSucceed(false);
            this.paraInfo.getExceptionList().add("\u53c2\u6570\u89e3\u6790\u5b58\u5728\u5f02\u5e38\uff1a" + e.getMessage());
            throw e;
        }
    }

    private void importSolution() throws StreamException {
        SolutionParser solutionParser = new SolutionParser();
        solutionParser.InitDirName(this.filePath);
        solutionParser.parse(this.paraInfo);
    }

    private void importEnum() throws Exception {
        EnumParser baseDataParser = new EnumParser();
        baseDataParser.InitDirName(this.filePath);
        baseDataParser.initInfos(this.paraInfo);
    }

    private void importJQT() throws Exception {
        JQTParser parser = new JQTParser();
        parser.InitDirName(this.filePath);
        parser.InitInfos(this.paraInfo);
    }

    private void importFormula() throws IOException, StreamException {
        FormulaParse parser = new FormulaParse();
        parser.InitDirName(this.filePath);
        parser.parse(this.paraInfo);
    }

    private void importPrint() throws IOException, StreamException {
        PrintParse parser = new PrintParse();
        parser.InitDirName(this.filePath);
        parser.parse(this.paraInfo);
    }

    private void importTaskLink() throws IOException, StreamException {
        TaskLinkParser parser = new TaskLinkParser();
        parser.InitDirName(this.filePath);
        parser.parse(this.paraInfo);
    }

    private void importQueryModal() throws IOException, StreamException {
        QueryParse parser = new QueryParse();
        parser.InitDirName(this.filePath);
        parser.parse(this.paraInfo);
    }

    private void importInnerAnalTable() throws Exception {
        AnalParse parser = new AnalParse();
        parser.InitDirName(this.filePath);
        parser.parseInner(this.paraInfo);
    }

    private void importAnalTable() throws Exception {
        AnalParse parser = new AnalParse();
        parser.InitDirName(this.filePath);
        parser.parse(this.paraInfo);
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

    public List<InOutDataType> getInOutData() {
        if (this.inOutData == null) {
            this.inOutData = new ArrayList<InOutDataType>();
        }
        return this.inOutData;
    }

    public void setInOutData(List<InOutDataType> inOutData) {
        this.inOutData = inOutData;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

