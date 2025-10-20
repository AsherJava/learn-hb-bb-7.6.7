/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.script.Script
 *  com.jiuqi.bi.script.ScriptException
 *  com.jiuqi.bi.script.ScriptManager
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.quickreport.engine.script;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.script.IScriptObj;
import com.jiuqi.bi.quickreport.engine.script.ScriptContext;
import com.jiuqi.bi.quickreport.engine.script.ScriptGrid;
import com.jiuqi.bi.quickreport.engine.script.ScriptObjManager;
import com.jiuqi.bi.quickreport.engine.script.ScriptParamEnv;
import com.jiuqi.bi.quickreport.engine.script.ScriptReport;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.script.Script;
import com.jiuqi.bi.script.ScriptException;
import com.jiuqi.bi.script.ScriptManager;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;

public final class ScriptRunner {
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String FUNCTION_BEFORE = "before";
    public static final String FUNCTION_ATFER = "after";
    public static final String FUNCTION_EXPORT = "exp";
    private final QuickReport report;
    private final ILogger log;
    private Script script;

    public ScriptRunner(QuickReport report, ILogger log) {
        this.report = report;
        this.log = log;
        String engineScript = report.getScript().getEngineScript();
        try {
            if (StringUtils.isNotEmpty((String)engineScript)) {
                this.script = ScriptManager.getScript((String)"js");
                this.script.evaluate(engineScript, "script");
            }
        }
        catch (ScriptException e) {
            log.error("\u811a\u672c\u5f02\u5e38", (Throwable)e);
        }
    }

    public boolean has(String name) {
        if (this.script == null) {
            return false;
        }
        return this.script.get(name) != null;
    }

    public void before(QuickReport report, IParameterEnv paramEnv, ReportContext context) throws ReportEngineException {
        if (!this.has(FUNCTION_BEFORE)) {
            return;
        }
        this.script.put("report", (Object)new ScriptReport(report));
        this.script.put("paramEnv", (Object)new ScriptParamEnv(paramEnv, this.log));
        this.script.put("context", (Object)new ScriptContext(context));
        for (String id : ScriptObjManager.getInstance().getFactoryIds()) {
            try {
                IScriptObj scriptObj = ScriptObjManager.getInstance().newScriptObj(id, report);
                this.script.put(id, (Object)scriptObj);
            }
            catch (Exception e) {
                this.log.error("\u521b\u5efa\u811a\u672c\u5bf9\u8c61\u5931\u8d25\uff1a" + id, (Throwable)e);
            }
        }
        try {
            this.script.call(FUNCTION_BEFORE, new Object[0]);
        }
        catch (Exception e) {
            throw new ReportEngineException(e);
        }
    }

    public void after(QuickReport report, IParameterEnv paramEnv, GridData grid) throws ReportEngineException {
        if (!this.has(FUNCTION_ATFER)) {
            return;
        }
        this.script.put("report", (Object)new ScriptReport(report));
        this.script.put("paramEnv", (Object)new ScriptParamEnv(paramEnv, this.log));
        this.script.put("grid", (Object)new ScriptGrid(grid));
        try {
            this.script.call(FUNCTION_ATFER, new Object[0]);
        }
        catch (Exception e) {
            throw new ReportEngineException(e);
        }
    }

    public void exp(IParameterEnv paramEnv, OutputStream outStream) throws ReportEngineException {
        if (!this.has(FUNCTION_EXPORT)) {
            return;
        }
        String directory = USER_HOME;
        String prefix = Guid.newGuid();
        String suffix = "xls";
        String fileName = prefix + "." + suffix;
        String ID_ETL = "etl";
        IScriptObj sObj = null;
        try {
            sObj = ScriptObjManager.getInstance().newScriptObj(ID_ETL, this.report);
        }
        catch (Exception e) {
            this.log.error("\u811a\u672c\u5f02\u5e38", (Throwable)e);
        }
        this.script.put("paramEnv", (Object)new ScriptParamEnv(paramEnv, this.log));
        this.script.put(ID_ETL, (Object)sObj);
        this.script.put("dir", (Object)directory);
        this.script.put("fileName", (Object)fileName);
        try {
            this.script.call(FUNCTION_EXPORT, new Object[0]);
            String filePath = directory + "\\" + fileName;
            if (filePath.contains("..")) {
                throw new IllegalArgumentException("\u975e\u6cd5\u7684\u6587\u4ef6\u8def\u5f84");
            }
            File xlsFile = new File(filePath);
            if (!xlsFile.exists()) {
                return;
            }
            try (InputStream is = Files.newInputStream(xlsFile.toPath(), new OpenOption[0]);){
                int count;
                byte[] buffer = new byte[8192];
                while ((count = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, count);
                }
            }
            Files.delete(xlsFile.toPath());
        }
        catch (Exception e) {
            throw new ReportEngineException(e);
        }
    }
}

