/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Version
 *  com.jiuqi.bi.util.type.GUID
 */
package com.jiuqi.nvwa.sf.operator;

import com.jiuqi.bi.util.Version;
import com.jiuqi.bi.util.type.GUID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VersionOperator {
    private static final String SQL_SELECT_ALL_MODULE_VERSION = "select * from SF_VERSION where MODULETAG = 1 order by UPDATETIME desc ";
    private static final String SQL_SELECT_MODULE_INIT_VERSION = "select * from SF_VERSION where MODULEID = ? and  FROMVERSION = '0.0.0'";
    private static final String SQL_PRE_UPDATE_MODULE_VERSION = "select * from SF_VERSION where MODULETAG = 2 order by UPDATETIME desc ";
    private static final String SQL_SELECT_ALL_LEGACY_MODULE_VERSION = "select * from NP_DB_VERSION";
    private static final String SQL_INSERT_LEGACY_MODULE_VERSION = "insert into NP_DB_VERSION values (?, ?)";
    private static final String SQL_UPDATE_LEGACY_MODULE_VERSION = "update NP_DB_VERSION set MODULEVERSION = ? where MODULEID = ?";
    private static final String SQL_SELECT_LEGACY_MODULE = "select * from NP_DB_VERSION where MODULEID = ?";
    private static final String SQL_INSERT_MODULE_VERSION = "insert into SF_VERSION values (?,?,?,?,?,?)";
    private static final String SQL_MARK_MODULE_VERSION = "update SF_VERSION set MODULETAG = 1 where GUID = ?";
    private static final String SQL_MARK_MODULE_VERSION_BY_ID = "update SF_VERSION set MODULETAG = 1 where MODULEID = ?";

    private VersionOperator() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void insertLegacyVersion(Connection conn, String moduleId, String version) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_LEGACY_MODULE_VERSION);){
            stmt.setString(1, moduleId);
            stmt.setString(2, version);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean updateLegacyVersion(Connection conn, String moduleId, String version) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_LEGACY_MODULE_VERSION);){
            stmt.setString(1, version);
            stmt.setString(2, moduleId);
            boolean bl = stmt.executeUpdate() > 0;
            return bl;
        }
    }

    /*
     * Exception decompiling
     */
    public static boolean existLegacyVersion(Connection conn, String moduleId) throws SQLException {
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String insertModuleVersion(Connection conn, String moduleId, String version, String oldVersion) throws SQLException {
        long updatetime = new Date().getTime();
        String guid = GUID.newGUID();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_MODULE_VERSION);){
            stmt.setString(1, moduleId);
            stmt.setString(2, version);
            stmt.setLong(3, updatetime);
            if (oldVersion == null) {
                stmt.setNull(4, 12);
            } else {
                stmt.setString(4, oldVersion);
            }
            stmt.setString(5, guid);
            stmt.setInt(6, 0);
            stmt.executeUpdate();
            String string = guid;
            return string;
        }
    }

    public static void markVersion(Connection conn, String guid) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_MARK_MODULE_VERSION);){
            stmt.setString(1, guid);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Map<String, Version> getAllModuleVersion(Connection conn) throws SQLException {
        HashMap<String, Version> versions = new HashMap<String, Version>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL_MODULE_VERSION);
             ResultSet rs = stmt.executeQuery();){
            while (rs.next()) {
                String moduleId = rs.getString("MODULEID");
                String versionString = rs.getString("MODULEVERSION");
                Version version = new Version(versionString);
                if (versions.containsKey(moduleId)) {
                    Version versionInMap = (Version)versions.get(moduleId);
                    if (version.compareTo(versionInMap) <= 0) continue;
                    versions.put(moduleId, version);
                    continue;
                }
                versions.put(moduleId, version);
            }
        }
        return versions;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Map<String, Version> getLegacyModuleVersions(Connection conn) throws SQLException {
        HashMap<String, Version> versions = new HashMap<String, Version>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL_LEGACY_MODULE_VERSION);
             ResultSet rs = stmt.executeQuery();){
            while (rs.next()) {
                String moduleId = rs.getString("MODULEID");
                String versionString = rs.getString("MODULEVERSION");
                Version version = new Version(versionString);
                if (versions.containsKey(moduleId)) {
                    Version versionInMap = (Version)versions.get(moduleId);
                    if (version.compareTo(versionInMap) <= 0) continue;
                    versions.put(moduleId, version);
                    continue;
                }
                versions.put(moduleId, version);
            }
        }
        return versions;
    }

    public static List<String> getPreUpdateModelList(Connection conn) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_PRE_UPDATE_MODULE_VERSION);
             ResultSet rs = stmt.executeQuery();){
            while (rs.next()) {
                String moduleId = rs.getString("MODULEID");
                int tag = rs.getInt("MODULETAG");
                if (tag != 2 || list.contains(moduleId)) continue;
                list.add(moduleId);
            }
        }
        return list;
    }

    public static void markVersionById(Connection conn, String moduleId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_MARK_MODULE_VERSION_BY_ID);){
            stmt.setString(1, moduleId);
            stmt.executeUpdate();
        }
    }

    public static Version getModuleInitialVersion(Connection connection, String moduleId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_SELECT_MODULE_INIT_VERSION);){
            stmt.setString(1, moduleId);
            try (ResultSet rs = stmt.executeQuery();){
                if (rs.next()) {
                    String versionString = rs.getString("MODULEVERSION");
                    Version version = new Version(versionString);
                    return version;
                }
            }
            Version version = null;
            return version;
        }
    }
}

