/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.examine.job.service;

import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

public interface HistoryArchivePlanRegService {
    public void removeFile(File var1, Logger var2);

    public void zipFile(String var1, boolean var2, String var3, ArrayList<File> var4, Logger var5) throws IOException;

    public boolean checkCustomPeriod(Map<String, String> var1);

    public Map<String, String> getArchiveNums(int var1, GregorianCalendar var2);

    public Object getArchiveNums(int var1, GregorianCalendar var2, Map<String, String> var3, IPeriodEntityAdapter var4, Logger var5, String var6, String var7);

    public Map<String, String> getFcCodeDatetimeMap(JdbcTemplate var1, String var2, String var3, String var4, String var5, Logger var6);

    public Map<String, String> getFcCodeDatetimeMap(JdbcTemplate var1, String var2, String var3, String var4, String var5, String var6, Logger var7);

    public Object csvFile(JdbcTemplate var1, Map<String, String> var2, Map<String, String> var3, String var4, Logger var5, String var6, String var7);

    public void zipCSVFiles(JdbcTemplate var1, GregorianCalendar var2, String var3, String var4, boolean var5, String var6, ArrayList<File> var7, Logger var8, ArrayList<String> var9);
}

