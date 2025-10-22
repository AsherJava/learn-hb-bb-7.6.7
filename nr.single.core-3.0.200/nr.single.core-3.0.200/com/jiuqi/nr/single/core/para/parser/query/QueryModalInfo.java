/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.single.core.para.parser.query;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.impl.ReportFormImpl;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintMan;
import com.jiuqi.nr.single.core.para.parser.query.QueryModalCondition;
import com.jiuqi.nr.single.core.para.parser.query.QueryScriptRec;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ReportParser;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryModalInfo {
    private static final Logger logger = LoggerFactory.getLogger(QueryModalInfo.class);
    private String modalName;
    private String groupNames;
    private RepInfo repInfo;
    private GridPrintMan printMan;
    private QueryScriptRec queryScript;
    private String condition;
    private String sortStr;
    private short sortType;
    private String classifyFields;
    private String classifyWidths;
    private String treeSpace;
    private String treeSign;
    private String treeQuery;
    private String chaESign;
    private String zoomCodes;
    private double divisor;
    private String divisorName;
    private boolean keepDetail;
    private List<QueryModalCondition> conditionList;
    private int conditionIndex;
    private int modalType;

    public final void loadFromJQT(Stream stream, JQTFileMap jqtFile) throws IOException, StreamException {
        HashMap<String, ReportFormImpl> repMap = new HashMap<String, ReportFormImpl>();
        ReportParser rpParser = new ReportParser(repMap);
        this.repInfo = rpParser.initReport(stream, jqtFile);
    }

    public final void loadPrint(Grid2Data gridData, Stream stream, JQTFileMap jqtFile) throws IOException, StreamException {
        GridPrintMan printMan = new GridPrintMan(gridData);
        printMan.LoadFromJQT(stream, jqtFile);
    }

    public final void loadFormula(Stream stream, JQTFileMap jqtFile) throws IOException, StreamException {
        if (jqtFile.getCheckFormulasBlock() > 0) {
            stream.seek(0L, 0);
            ReadUtil.skipStream(stream, jqtFile.getCheckFormulasBlock());
            this.queryScript = new QueryScriptRec();
            this.queryScript.LoadFromStream(stream);
            boolean qVersion = false;
            String errorInfo = "";
            if ((this.queryScript.getVersion() & 0xFFFF0000) == 65536) {
                qVersion = true;
                errorInfo = "\u73b0\u5728\u8c03\u5165\u7684\u662f\u8001\u7248\u672c";
            } else if ((this.queryScript.getVersion() & 0xFFFF0000) != 131072) {
                errorInfo = "\u6587\u4ef6\u7248\u672c\u6bd4\u7a0b\u5e8f\u7248\u672c\u65b0\uff0c\u65e0\u6cd5\u8bfb\u53d6\u6570\u636e\uff01";
                throw new IOException(errorInfo);
            }
            this.setCondition(ReadUtil.readStreams(stream));
            this.sortStr = ReadUtil.readStreams(stream);
            if (stream.getPosition() >= stream.getSize()) {
                if (StringUtils.isEmpty((String)this.sortStr)) {
                    this.sortType = 0;
                } else {
                    String newCode;
                    int p = this.sortStr.lastIndexOf(",");
                    if (p > 0 && StringUtils.isNotEmpty((String)(newCode = this.sortStr.substring(p + 1, this.sortStr.length()))) && Integer.parseInt(newCode) == 1) {
                        this.sortType = 1;
                        this.sortStr = this.sortStr.substring(0, p);
                    }
                }
            } else {
                String sortStypeCode = ReadUtil.readStreams(stream);
                if (StringUtils.isNotEmpty((String)sortStypeCode) && Integer.parseInt(sortStypeCode) == 1) {
                    this.sortType = 1;
                }
                this.classifyFields = ReadUtil.readStreams(stream);
                this.classifyWidths = ReadUtil.readStreams(stream);
                try {
                    this.treeSpace = ReadUtil.readStreams(stream);
                    this.treeSign = ReadUtil.readStreams(stream);
                    this.treeQuery = stream.getPosition() < stream.getSize() ? ReadUtil.readStreams(stream) : "0";
                    this.chaESign = stream.getPosition() < stream.getSize() ? ReadUtil.readStreams(stream) : "0";
                }
                catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
            try {
                if (stream.getPosition() < stream.getSize()) {
                    this.zoomCodes = ReadUtil.readStreams(stream);
                    String divisorCode = ReadUtil.readStreams(stream);
                    this.divisor = StringUtils.isNotEmpty((String)divisorCode) && Double.parseDouble(divisorCode) != 0.0 ? Double.parseDouble(divisorCode) : -1.0;
                    this.divisorName = ReadUtil.readStreams(stream);
                } else {
                    this.zoomCodes = "0";
                    this.divisor = -1.0;
                    this.divisorName = "";
                }
                if (stream.getPosition() < stream.getSize()) {
                    this.keepDetail = ReadUtil.readStreams(stream) == "1";
                }
            }
            catch (Exception ex) {
                this.zoomCodes = "0";
                this.divisor = -1.0;
                this.divisorName = "";
                this.keepDetail = true;
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    public String getModalName() {
        return this.modalName;
    }

    public void setModalName(String modalName) {
        this.modalName = modalName;
    }

    public String getGroupNames() {
        return this.groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }

    public RepInfo getRepInfo() {
        return this.repInfo;
    }

    public void setRepInfo(RepInfo repInfo) {
        this.repInfo = repInfo;
    }

    public QueryScriptRec getQueryScript() {
        return this.queryScript;
    }

    public void setQueryScript(QueryScriptRec queryScript) {
        this.queryScript = queryScript;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public GridPrintMan getPrintMan() {
        return this.printMan;
    }

    public void setPrintMan(GridPrintMan printMan) {
        this.printMan = printMan;
    }

    public String getSortStr() {
        return this.sortStr;
    }

    public void setSortStr(String sortStr) {
        this.sortStr = sortStr;
    }

    public short getSortType() {
        return this.sortType;
    }

    public void setSortType(short sortType) {
        this.sortType = sortType;
    }

    public String getClassifyFields() {
        return this.classifyFields;
    }

    public void setClassifyFields(String classifyFields) {
        this.classifyFields = classifyFields;
    }

    public String getClassifyWidths() {
        return this.classifyWidths;
    }

    public void setClassifyWidths(String classifyWidths) {
        this.classifyWidths = classifyWidths;
    }

    public String getTreeSpace() {
        return this.treeSpace;
    }

    public void setTreeSpace(String treeSpace) {
        this.treeSpace = treeSpace;
    }

    public String getTreeSign() {
        return this.treeSign;
    }

    public void setTreeSign(String treeSign) {
        this.treeSign = treeSign;
    }

    public String getTreeQuery() {
        return this.treeQuery;
    }

    public void setTreeQuery(String treeQuery) {
        this.treeQuery = treeQuery;
    }

    public String getChaESign() {
        return this.chaESign;
    }

    public void setChaESign(String chaESign) {
        this.chaESign = chaESign;
    }

    public String getZoomCodes() {
        return this.zoomCodes;
    }

    public void setZoomCodes(String zoomCodes) {
        this.zoomCodes = zoomCodes;
    }

    public double getDivisor() {
        return this.divisor;
    }

    public void setDivisor(double divisor) {
        this.divisor = divisor;
    }

    public String getDivisorName() {
        return this.divisorName;
    }

    public void setDivisorName(String divisorName) {
        this.divisorName = divisorName;
    }

    public boolean isKeepDetail() {
        return this.keepDetail;
    }

    public void setKeepDetail(boolean keepDetail) {
        this.keepDetail = keepDetail;
    }

    public List<QueryModalCondition> getConditionList() {
        return this.conditionList;
    }

    public void setConditionList(List<QueryModalCondition> conditionList) {
        this.conditionList = conditionList;
    }

    public int getConditionIndex() {
        return this.conditionIndex;
    }

    public void setConditionIndex(int conditionIndex) {
        this.conditionIndex = conditionIndex;
    }

    public int getModalType() {
        return this.modalType;
    }

    public void setModalType(int modalType) {
        this.modalType = modalType;
    }
}

