/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.LicenceException
 *  com.jiuqi.bi.authz.licence.LicenceManager
 *  com.jiuqi.bi.authz.licence.MachineCodeGenerator$MachineInfo
 */
package com.jiuqi.nvwa.sf.operator;

import com.jiuqi.bi.authz.LicenceException;
import com.jiuqi.bi.authz.licence.LicenceManager;
import com.jiuqi.bi.authz.licence.MachineCodeGenerator;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.models.LicenceObj;
import com.jiuqi.nvwa.sf.operator.FrameworkOperator;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LicenceOperator {
    private static final String SQL_SELECT_ALL_LICENCE = "select L_PRODUCTID, L_DATA from SF_LICENCE";
    private static final String SQL_DELETE_LICENCE = "delete from SF_LICENCE where L_PRODUCTID=?";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void insertLicenceToDB(Connection conn, byte[] data, String productId) throws SQLException {
        PreparedStatement stmt2;
        String sql = "select * from SF_LICENCE where L_PRODUCTID=?";
        boolean isUpdate = false;
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setString(1, productId);
            try (ResultSet rs = stmt.executeQuery();){
                isUpdate = rs.next();
            }
        }
        if (isUpdate) {
            sql = "update SF_LICENCE set L_DATA=? where L_PRODUCTID=?";
            stmt2 = conn.prepareStatement(sql);
            try {
                stmt2.setBytes(1, data);
                stmt2.setString(2, productId);
                stmt2.executeUpdate();
            }
            finally {
                stmt2.close();
            }
        }
        sql = "insert into SF_LICENCE (L_PRODUCTID,L_DATA) values (?, ?)";
        stmt2 = conn.prepareStatement(sql);
        try {
            stmt2.setString(1, productId);
            stmt2.setBytes(2, data);
            stmt2.executeUpdate();
        }
        finally {
            stmt2.close();
        }
    }

    public static void addLicence(Connection conn, LicenceManager licenceManager, String productId, byte[] data) throws Exception {
        LicenceManager local = new LicenceManager();
        local.load((InputStream)new ByteArrayInputStream(data), productId);
        try {
            MachineCodeGenerator.MachineInfo machineInfo = FrameworkOperator.getMachineCode(productId);
            if (machineInfo == null) {
                throw new LicenceException("\u672a\u751f\u6210\u673a\u5668\u4fe1\u606f\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
            }
            local.validateMachineCode(productId, machineInfo.getMachineCode());
            LicenceOperator.insertLicenceToDB(conn, data, productId);
        }
        catch (Exception e) {
            throw new LicenceException((Throwable)e);
        }
        licenceManager.load((InputStream)new ByteArrayInputStream(data), productId);
        Framework.getInstance().setLicenceValidate(true);
        Framework.getInstance().setLicenceSystemState(false, "\u91cd\u65b0\u5b89\u88c5\u6388\u6743");
        Framework.getInstance().updateServerStatus();
    }

    public static void loadLicence(Connection conn, String productId, LicenceManager licenceManager) throws SQLException, LicenceException {
        LicenceObj[] all;
        for (LicenceObj obj : all = LicenceOperator.getAllLicences(conn)) {
            if (!productId.equals(obj.getProductId())) continue;
            licenceManager.load((InputStream)new ByteArrayInputStream(obj.getData()), productId);
            return;
        }
        throw new LicenceException("\u672a\u5b89\u88c5\u6388\u6743");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static LicenceObj[] getAllLicences(Connection conn) throws SQLException {
        ArrayList<LicenceObj> list = new ArrayList<LicenceObj>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_LICENCE);){
            while (rs.next()) {
                LicenceObj obj = new LicenceObj();
                obj.setProductId(rs.getString("L_PRODUCTID"));
                obj.setData(rs.getBytes("L_DATA"));
                list.add(obj);
            }
        }
        return list.toArray(new LicenceObj[list.size()]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void delLicence(Connection conn, String productId, LicenceManager licenceManager) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_LICENCE);){
            stmt.setString(1, productId);
            stmt.executeUpdate();
            licenceManager.remove(productId);
        }
    }

    public static void addLicenceToMemory(LicenceManager licenceManager, String productId, byte[] data) throws Exception {
        LicenceManager local = new LicenceManager();
        local.load((InputStream)new ByteArrayInputStream(data), productId);
        try {
            MachineCodeGenerator.MachineInfo machineInfo = FrameworkOperator.getMachineCode(productId);
            if (machineInfo == null) {
                throw new LicenceException("\u672a\u751f\u6210\u673a\u5668\u4fe1\u606f\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
            }
            local.validateMachineCode(productId, machineInfo.getMachineCode());
        }
        catch (Exception e) {
            throw new LicenceException((Throwable)e);
        }
        licenceManager.load((InputStream)new ByteArrayInputStream(data), productId);
        Framework.getInstance().setLicenceValidate(true);
        Framework.getInstance().setLicenceSystemState(false, "\u91cd\u65b0\u83b7\u53d6\u6388\u6743");
    }
}

