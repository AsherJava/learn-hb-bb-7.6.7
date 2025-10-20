/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.datamodel.storage;

import com.jiuqi.va.datamodel.dao.VaDataModelGroupDao;
import com.jiuqi.va.datamodel.domain.DataModelGroupDO;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataModelGroupStorage {
    private static Logger logger = LoggerFactory.getLogger(DataModelGroupStorage.class);

    public static void init(String tenantName) {
        JDialectUtil jDialect = JDialectUtil.getInstance();
        try {
            JTableModel jtm = DataModelGroupStorage.getMaintainCreateJTM(tenantName);
            if (!jDialect.hasTable(jtm)) {
                jDialect.createTable(jtm);
                DataModelGroupStorage.initData(tenantName);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static JTableModel getMaintainCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "DATAMODEL_GROUP");
        jtm.column("ID").NVARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").NVARCHAR(Integer.valueOf(100));
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(100));
        jtm.column("BIZTYPE").NVARCHAR(Integer.valueOf(100));
        jtm.column("PARENTNAME").NVARCHAR(Integer.valueOf(100));
        jtm.column("ORDERNUM").NUMERIC(new Integer[]{19, 6});
        jtm.index("DATAMODAL_GROUP_NAME").columns(new String[]{"BIZTYPE"}).columns(new String[]{"NAME"});
        return jtm;
    }

    private static void initData(String tenantName) {
        VaDataModelGroupDao dataModelGroupDao = (VaDataModelGroupDao)ApplicationContextRegister.getBean(VaDataModelGroupDao.class);
        String[][] dataInfo = new String[][]{{DataModelType.BizType.BASEDATA.toString(), "system", "\u7cfb\u7edf"}, {DataModelType.BizType.BASEDATA.toString(), "public", "\u516c\u7528"}, {DataModelType.BizType.BASEDATA.toString(), "other", "\u5176\u4ed6"}, {DataModelType.BizType.BILL.toString(), "system", "\u7cfb\u7edf"}, {DataModelType.BizType.BILL.toString(), "public", "\u516c\u7528"}, {DataModelType.BizType.BILL.toString(), "other", "\u5176\u4ed6"}, {DataModelType.BizType.OTHER.toString(), "system", "\u7cfb\u7edf"}, {DataModelType.BizType.OTHER.toString(), "public", "\u516c\u7528"}, {DataModelType.BizType.OTHER.toString(), "other", "\u5176\u4ed6"}};
        DataModelGroupDO addParam = new DataModelGroupDO();
        addParam.setTenantName(tenantName);
        for (int i = 0; i < dataInfo.length; ++i) {
            addParam.setId(UUID.randomUUID());
            addParam.setBiztype(dataInfo[i][0]);
            addParam.setName(dataInfo[i][1]);
            addParam.setTitle(dataInfo[i][2]);
            addParam.setParentname("-");
            addParam.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
            dataModelGroupDao.insert((Object)addParam);
        }
    }
}

