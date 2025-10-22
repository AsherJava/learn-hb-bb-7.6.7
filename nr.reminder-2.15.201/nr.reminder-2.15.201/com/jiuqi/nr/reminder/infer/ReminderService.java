/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.springframework.dao.DataAccessException
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.reminder.infer;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.reminder.bean.AutoUserEntitys;
import com.jiuqi.nr.reminder.bean.DropTreeResult;
import com.jiuqi.nr.reminder.bean.TreeNodeImpl;
import com.jiuqi.nr.reminder.internal.CreateReminderCommand;
import com.jiuqi.nr.reminder.internal.Reminder;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ReminderService {
    public boolean send(Reminder var1) throws Exception;

    public void createReminder(CreateReminderCommand var1) throws Exception;

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor={DataAccessException.class})
    public void deleteReminder(String var1);

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor={DataAccessException.class})
    public void batchDelete(List<String> var1);

    public AutoUserEntitys getUserEntitys(String var1);

    public List<DropTreeResult> queryDropTreeData(String var1, String var2);

    public List<EntityViewDefine> queryAdminEntitieList(String var1);

    public String queryUnitName(String var1, String var2);

    public List<TreeNodeImpl> getReportTask() throws Exception;

    public List<TreeNodeImpl> getFormSchemeList(TaskDefine var1) throws Exception;
}

