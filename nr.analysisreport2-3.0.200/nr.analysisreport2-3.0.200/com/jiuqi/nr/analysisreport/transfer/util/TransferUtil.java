/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 */
package com.jiuqi.nr.analysisreport.transfer.util;

import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import com.jiuqi.nr.analysisreport.internal.AnalysisReportGroupDefineImpl;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class TransferUtil {
    public static final Logger logger = LoggerFactory.getLogger(TransferUtil.class);
    public static final String TYPE_ID_GROUP = "G@";
    public static final String TYPE_ID_NRAR = "S@";
    public static final String TYPE_GROUP = "GROUP";
    public static final String TYPE_NRAR = "NRAR";

    public static FolderNode groupToFolderNode(AnalysisReportGroupDefine analysisTemp) {
        FolderNode folder = new FolderNode();
        folder.setGuid(TYPE_ID_GROUP + analysisTemp.getKey());
        folder.setParentGuid(TYPE_ID_GROUP + analysisTemp.getParentgroup());
        folder.setTitle(analysisTemp.getTitle());
        folder.setType(TYPE_GROUP);
        return folder;
    }

    public static AnalysisReportGroupDefineImpl folderNodeToGroup(FolderNode node) {
        AnalysisReportGroupDefineImpl group = new AnalysisReportGroupDefineImpl();
        group.setKey(TransferUtil.getkey(node.getGuid()));
        if (StringUtils.hasText(node.getParentGuid())) {
            group.setParentgroup(TransferUtil.getkey(node.getParentGuid()));
        } else {
            group.setParentgroup("0");
        }
        group.setTitle(node.getTitle());
        return group;
    }

    public static BusinessNode schemeToBusinessNode(AnalysisReportDefine analysisReportDefine) {
        if (analysisReportDefine == null) {
            return null;
        }
        BusinessNode bs = new BusinessNode();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bs.setGuid(TYPE_ID_NRAR + analysisReportDefine.getKey());
        bs.setFolderGuid(TYPE_ID_GROUP + analysisReportDefine.getGroupKey());
        bs.setTitle(analysisReportDefine.getTitle());
        bs.setModifyTime(dateFormat.format(analysisReportDefine.getUpdateTime()));
        bs.setType(TYPE_NRAR);
        bs.setTypeTitle("\u5206\u6790\u62a5\u544a\u6a21\u677f");
        return bs;
    }

    public static String getkey(String nodeId) {
        if (!StringUtils.hasText(nodeId)) {
            return "0";
        }
        if (nodeId.startsWith(TYPE_ID_GROUP) || nodeId.startsWith(TYPE_ID_NRAR)) {
            return nodeId.substring(2);
        }
        return nodeId;
    }
}

