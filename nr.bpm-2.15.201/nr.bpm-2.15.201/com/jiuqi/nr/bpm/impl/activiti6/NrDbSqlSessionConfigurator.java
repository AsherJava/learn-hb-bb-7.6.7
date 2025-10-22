/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.cfg.ProcessEngineConfigurator
 *  org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl
 *  org.activiti.engine.impl.db.DbSqlSessionFactory
 *  org.activiti.engine.impl.util.ReflectUtil
 *  org.apache.ibatis.session.SqlSessionFactory
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.nr.bpm.impl.activiti6.NrDbSqlSessionFactory;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.activiti.engine.cfg.ProcessEngineConfigurator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.util.ReflectUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

@Component
public class NrDbSqlSessionConfigurator
implements ProcessEngineConfigurator {
    private static final String DEFAULT_MYBATIS_MAPPING_FILE = "config/mapper/mappings.xml";
    private static final List<String> commonDbList = Arrays.asList("dameng", "gaussdb100", "gbase", "oscar", "kingbase", "polardb");
    private static final List<String> otherDbList = Arrays.asList("hana", "derby", "gaussdb");

    public void beforeInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        String dbType = processEngineConfiguration.getDatabaseType();
        if (dbType.equals("hana") || dbType.equals("gbase")) {
            processEngineConfiguration.setBulkInsertEnabled(false);
            processEngineConfiguration.setDbSqlSessionFactory((DbSqlSessionFactory)new NrDbSqlSessionFactory());
        }
    }

    public void configure(ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.setSqlSessionFactory(this.createSqlSessionFactory(processEngineConfiguration));
        processEngineConfiguration.initDbSqlSessionFactory();
    }

    /*
     * Exception decompiling
     */
    private SqlSessionFactory createSqlSessionFactory(ProcessEngineConfigurationImpl processEngineConfiguration) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private InputStream getResourceAsStream(String resource) {
        return ReflectUtil.getResourceAsStream((String)resource);
    }

    public int getPriority() {
        return 0;
    }
}

