/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.attachment.task.VaAttachementCoreStorageSyncTask
 *  com.jiuqi.va.basedata.task.VaBasedataStorageSyncTask
 *  com.jiuqi.va.bill.bd.core.task.AppRegStorageSyncTask
 *  com.jiuqi.va.bill.bd.core.task.BillChangeRecordStorageSyncTask
 *  com.jiuqi.va.bill.bd.core.task.MaintainBillExceptionStorageSyncTask
 *  com.jiuqi.va.billcode.task.VaBillCodeStorageSyncTask
 *  com.jiuqi.va.billlist.table.VaBillImportTemplateSyncTask
 *  com.jiuqi.va.billref.task.VaBillRefStorageSyncTask
 *  com.jiuqi.va.bizmeta.task.VaBizMetaStorageSyncTask
 *  com.jiuqi.va.datamodel.task.VaDataModelStorageBizTypeSyncTask
 *  com.jiuqi.va.datamodel.task.VaDataModelStorageCoreSyncTask
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.style.task.VaStyleStorageSyncTask
 */
package com.jiuqi.gcreport.billcore.task;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.attachment.task.VaAttachementCoreStorageSyncTask;
import com.jiuqi.va.basedata.task.VaBasedataStorageSyncTask;
import com.jiuqi.va.bill.bd.core.task.AppRegStorageSyncTask;
import com.jiuqi.va.bill.bd.core.task.BillChangeRecordStorageSyncTask;
import com.jiuqi.va.bill.bd.core.task.MaintainBillExceptionStorageSyncTask;
import com.jiuqi.va.billcode.task.VaBillCodeStorageSyncTask;
import com.jiuqi.va.billlist.table.VaBillImportTemplateSyncTask;
import com.jiuqi.va.billref.task.VaBillRefStorageSyncTask;
import com.jiuqi.va.bizmeta.task.VaBizMetaStorageSyncTask;
import com.jiuqi.va.datamodel.task.VaDataModelStorageBizTypeSyncTask;
import com.jiuqi.va.datamodel.task.VaDataModelStorageCoreSyncTask;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.style.task.VaStyleStorageSyncTask;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VaTableInitTask
implements CustomClassExecutor {
    private Logger logger = LoggerFactory.getLogger(VaTableInitTask.class);

    public void execute(DataSource dataSource) {
        try {
            VaBasedataStorageSyncTask basedataStorageSyncTask = (VaBasedataStorageSyncTask)ApplicationContextRegister.getBean(VaBasedataStorageSyncTask.class);
            VaBillCodeStorageSyncTask billCodeStorageSyncTask = (VaBillCodeStorageSyncTask)ApplicationContextRegister.getBean(VaBillCodeStorageSyncTask.class);
            VaBizMetaStorageSyncTask bizMetaStorageSyncTask = (VaBizMetaStorageSyncTask)ApplicationContextRegister.getBean(VaBizMetaStorageSyncTask.class);
            basedataStorageSyncTask.execute();
            billCodeStorageSyncTask.execute();
            bizMetaStorageSyncTask.execute();
            VaDataModelStorageCoreSyncTask dataModelStorageCoreSyncTask = (VaDataModelStorageCoreSyncTask)ApplicationContextRegister.getBean(VaDataModelStorageCoreSyncTask.class);
            VaDataModelStorageBizTypeSyncTask dataModelStorageBizTypeSyncTask = (VaDataModelStorageBizTypeSyncTask)ApplicationContextRegister.getBean(VaDataModelStorageBizTypeSyncTask.class);
            dataModelStorageCoreSyncTask.execute();
            dataModelStorageBizTypeSyncTask.execute();
            VaBillRefStorageSyncTask vaBillRefStorageSyncTask = (VaBillRefStorageSyncTask)ApplicationContextRegister.getBean(VaBillRefStorageSyncTask.class);
            VaAttachementCoreStorageSyncTask vaAttachementCoreStorageSyncTask = (VaAttachementCoreStorageSyncTask)ApplicationContextRegister.getBean(VaAttachementCoreStorageSyncTask.class);
            AppRegStorageSyncTask appRegStorageSyncTask = (AppRegStorageSyncTask)ApplicationContextRegister.getBean(AppRegStorageSyncTask.class);
            BillChangeRecordStorageSyncTask billChangeRecordStorageSyncTask = (BillChangeRecordStorageSyncTask)ApplicationContextRegister.getBean(BillChangeRecordStorageSyncTask.class);
            MaintainBillExceptionStorageSyncTask maintainBillExceptionStorageSyncTask = (MaintainBillExceptionStorageSyncTask)ApplicationContextRegister.getBean(MaintainBillExceptionStorageSyncTask.class);
            vaBillRefStorageSyncTask.execute();
            vaAttachementCoreStorageSyncTask.execute();
            appRegStorageSyncTask.execute();
            billChangeRecordStorageSyncTask.execute();
            maintainBillExceptionStorageSyncTask.execute();
            ((VaBillImportTemplateSyncTask)ApplicationContextRegister.getBean(VaBillImportTemplateSyncTask.class)).execute();
            ((VaStyleStorageSyncTask)ApplicationContextRegister.getBean(VaStyleStorageSyncTask.class)).execute();
        }
        catch (Exception e) {
            this.logger.error("\u5355\u636e\u7cfb\u7edf\u8868\u521d\u59cb\u5316\u5931\u8d25\uff1a" + e.getMessage());
            e.printStackTrace();
        }
    }
}

