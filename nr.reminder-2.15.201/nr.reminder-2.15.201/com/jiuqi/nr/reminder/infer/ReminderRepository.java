/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.infer;

import com.jiuqi.nr.reminder.internal.Reminder;
import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository {
    public String save(Reminder var1);

    public void updateReminder(Reminder var1);

    public Reminder find(String var1);

    public boolean findUserState(String var1);

    public void updateExecuteTime(String var1, LocalDateTime var2);

    public void updateExecuteTimeAndStatus(String var1, LocalDateTime var2, int var3);

    public void updateExecuteStatus(String var1, int var2);

    public List<Reminder> find(int var1, int var2);

    public void delete(String var1);

    public void batchDelete(List<String> var1);

    public List<Reminder> findAll();

    public int findAllNums();

    public int getPageInfo(String var1, int var2);

    public void updateRegularTime(String var1, String var2);

    public String findUserName(String var1);

    public String findUserEmail(String var1);
}

