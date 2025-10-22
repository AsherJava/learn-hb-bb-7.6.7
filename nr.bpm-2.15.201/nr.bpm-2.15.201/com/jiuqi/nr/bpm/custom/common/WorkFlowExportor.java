/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.bpm.custom.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.common.WorkFlowInfoObj;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WorkFlowExportor {
    private static final Logger logger = LoggerFactory.getLogger(WorkFlowExportor.class);

    public String getWorkFlowDefineFieldStr(WorkFlowDefine define, List<WorkFlowNodeSet> nodesets, List<WorkFlowLine> lines, List<WorkFlowAction> actions, List<WorkFlowParticipant> particis) {
        WorkFlowInfoObj obj = new WorkFlowInfoObj(define, nodesets, lines, actions, particis);
        ObjectMapper mapper = new ObjectMapper();
        String str = "";
        try {
            str = mapper.writeValueAsString((Object)obj);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return str;
    }
}

