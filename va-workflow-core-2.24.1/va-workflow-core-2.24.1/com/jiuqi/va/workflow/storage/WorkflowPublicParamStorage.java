/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.workflow.storage;

import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.workflow.dao.WorkflowPublicParamDao;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkflowPublicParamStorage {
    private static final Logger log = LoggerFactory.getLogger(WorkflowPublicParamStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        String publicParamTableName = "WORKFLOW_PUBPARAM";
        String publicParamDataTableName = "WORKFLOW_PUBPARAM_DATA";
        String publicParamRelTableName = "WORKFLOW_PUBPARAM_REL";
        JTableModel jtm = new JTableModel(tenantName, publicParamTableName);
        try {
            WorkflowPublicParamStorage.getCreateWorkflowPublicParam(jtm);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
            WorkflowPublicParamStorage.initPublicParam(tenantName);
        }
        catch (JTableException e) {
            log.error("\u540c\u6b65{}\u8868\u7ed3\u6784\u5931\u8d25", (Object)publicParamTableName, (Object)e);
        }
        jtm = new JTableModel(tenantName, publicParamDataTableName);
        try {
            WorkflowPublicParamStorage.getCreateWorkflowPublicParamData(jtm);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            log.error("\u540c\u6b65{}\u8868\u7ed3\u6784\u5931\u8d25", (Object)publicParamDataTableName, (Object)e);
        }
        jtm = new JTableModel(tenantName, publicParamRelTableName);
        try {
            WorkflowPublicParamStorage.getCreateWorkflowPublicParamRelation(jtm);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
            } else {
                jDialect.updateTable(jtm);
            }
        }
        catch (JTableException e) {
            log.error("\u540c\u6b65{}\u8868\u7ed3\u6784\u5931\u8d25", (Object)publicParamRelTableName, (Object)e);
        }
    }

    private static void initPublicParam(String tenantName) {
        WorkflowPublicParamDao publicParamDao = (WorkflowPublicParamDao)ApplicationContextRegister.getBean(WorkflowPublicParamDao.class);
        WorkflowPublicParamDTO publicParamDTO = new WorkflowPublicParamDTO();
        publicParamDTO.setParamname("UNITCODE");
        publicParamDTO.setTenantName(tenantName);
        if (publicParamDao.selectCount(publicParamDTO) == 0) {
            publicParamDTO.setId(UUID.randomUUID());
            publicParamDTO.setParamtitle("\u7ec4\u7ec7\u673a\u6784");
            publicParamDTO.setMappingtype(new BigDecimal("4"));
            publicParamDTO.setMapping("MD_ORG");
            publicParamDTO.setModifytime(new Date());
            publicParamDTO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            publicParamDTO.setParamtype("STRING");
            publicParamDTO.setFixedflag(BigDecimal.ONE);
            publicParamDTO.setRemoveflag(BigDecimal.ZERO);
            publicParamDao.insert(publicParamDTO);
        }
        publicParamDTO = new WorkflowPublicParamDTO();
        publicParamDTO.setParamname("PROJECTCODE");
        publicParamDTO.setTenantName(tenantName);
        if (publicParamDao.selectCount(publicParamDTO) == 0) {
            publicParamDTO.setId(UUID.randomUUID());
            publicParamDTO.setParamtitle("\u9879\u76ee");
            publicParamDTO.setMappingtype(BigDecimal.ONE);
            publicParamDTO.setMapping("MD_PROJECT");
            publicParamDTO.setModifytime(new Date());
            publicParamDTO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            publicParamDTO.setParamtype("STRING");
            publicParamDTO.setFixedflag(BigDecimal.ONE);
            publicParamDTO.setRemoveflag(BigDecimal.ZERO);
            publicParamDao.insert(publicParamDTO);
        }
        publicParamDTO = new WorkflowPublicParamDTO();
        publicParamDTO.setParamname("DEPTCODE");
        publicParamDTO.setTenantName(tenantName);
        if (publicParamDao.selectCount(publicParamDTO) == 0) {
            publicParamDTO.setId(UUID.randomUUID());
            publicParamDTO.setParamtitle("\u90e8\u95e8");
            publicParamDTO.setMappingtype(BigDecimal.ONE);
            publicParamDTO.setMapping("MD_DEPARTMENT");
            publicParamDTO.setModifytime(new Date());
            publicParamDTO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            publicParamDTO.setParamtype("STRING");
            publicParamDTO.setFixedflag(BigDecimal.ONE);
            publicParamDTO.setRemoveflag(BigDecimal.ZERO);
            publicParamDao.insert(publicParamDTO);
        }
        publicParamDTO = new WorkflowPublicParamDTO();
        publicParamDTO.setTenantName(tenantName);
        publicParamDTO.setParamname("STAFFCODE");
        if (publicParamDao.selectCount(publicParamDTO) == 0) {
            publicParamDTO.setId(UUID.randomUUID());
            publicParamDTO.setParamtitle("\u804c\u5458");
            publicParamDTO.setMappingtype(BigDecimal.ONE);
            publicParamDTO.setMapping("MD_STAFF");
            publicParamDTO.setModifytime(new Date());
            publicParamDTO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            publicParamDTO.setParamtype("STRING");
            publicParamDTO.setFixedflag(BigDecimal.ONE);
            publicParamDTO.setRemoveflag(BigDecimal.ZERO);
            publicParamDao.insert(publicParamDTO);
        }
    }

    private static void getCreateWorkflowPublicParam(JTableModel jtm) {
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("PARAMNAME").NVARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("PARAMTITLE").NVARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("PARAMTYPE").NVARCHAR(Integer.valueOf(20)).notNull();
        jtm.column("MAPPINGTYPE").INTEGER(new Integer[]{1});
        jtm.column("MAPPING").NVARCHAR(Integer.valueOf(100));
        jtm.column("MODIFYTIME").TIMESTAMP();
        jtm.column("ORDERNUM").NUMERIC(new Integer[]{19, 6});
        jtm.column("FIXEDFLAG").INTEGER(new Integer[]{1});
        jtm.column("REMOVEFLAG").INTEGER(new Integer[]{1});
        jtm.index("IDX_WFPP_PARAMNAME").columns(new String[]{"PARAMNAME"}).unique();
    }

    private static void getCreateWorkflowPublicParamData(JTableModel jtm) {
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("PARAMDATA").CLOB();
    }

    private static void getCreateWorkflowPublicParamRelation(JTableModel jtm) {
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("DEFINEKEY").NVARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("DEFINEVERSION").LONG().notNull();
        jtm.column("PARAMNAME").NVARCHAR(Integer.valueOf(100)).notNull();
        jtm.column("USERNAME").NVARCHAR(Integer.valueOf(50));
        jtm.index("IDX_WFPPR_DEFINEKEY").columns(new String[]{"DEFINEKEY"});
    }
}

