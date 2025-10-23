/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.bi.database.metadata.SQLMetadataException
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 *  com.jiuqi.bi.database.statement.SqlStatement
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.PreparedStatementCallback
 *  org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback
 *  org.springframework.jdbc.datasource.DataSourceUtils
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobCreator
 *  org.springframework.jdbc.support.lob.LobHandler
 *  org.springframework.jdbc.support.rowset.SqlRowSet
 */
package com.jiuqi.np.blob.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.np.blob.BlobContainer;
import com.jiuqi.np.blob.util.BlobUtils;
import com.jiuqi.np.blob.util.InputStreamFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.StringUtils;

public class BlobContainerDbImpl
implements BlobContainer {
    private static final Logger logger = LoggerFactory.getLogger(BlobContainerDbImpl.class);
    private JdbcTemplate _jdbcTemplate;
    private String _tableName;
    private static final String FD_KEY = "b_key";
    private static final String FD_CONTENT = "b_content";
    private static final String FD_EXTENTION = "b_extention";
    private static final String FD_UPDATETIME = "updatetime";

    public BlobContainerDbImpl(String name, JdbcTemplate jdbcTemplate) {
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("name");
        }
        if (jdbcTemplate == null) {
            throw new IllegalArgumentException("jdbcTemplate");
        }
        this._tableName = ("fs_" + name.replace("/", "_")).toUpperCase();
        if (this._tableName.length() > 30) {
            throw new IllegalArgumentException("Too long a definition of container and area.");
        }
        this._jdbcTemplate = jdbcTemplate;
        this.checkTable(this._tableName);
    }

    @Override
    public boolean exists(String key) {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        String sql = String.format("SELECT 1 FROM %s WHERE %s=?", this._tableName, FD_KEY);
        SqlRowSet result = this._jdbcTemplate.queryForRowSet(sql, new Object[]{key});
        return result.next();
    }

    @Override
    public boolean exists(String key, String directory) {
        key = this.joinKey(key, directory);
        return this.exists(key);
    }

    @Override
    public void uploadFromStream(String key, InputStream source) throws IOException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        this.putData(key, source);
    }

    @Override
    public String uploadFromStreamExten(String extension, InputStream source) throws IOException {
        String key = BlobUtils.generateFileKey(extension);
        this.putData(key, source);
        return key;
    }

    @Override
    public void uploadFromStream(String key, String directory, InputStream source) throws IOException {
        key = this.joinKey(key, directory);
        this.putData(key, source);
    }

    @Override
    public void uploadText(String key, String text) throws IOException {
        try (ByteArrayInputStream is = new ByteArrayInputStream(text.getBytes());){
            this.putData(key, is);
        }
    }

    @Override
    public void uploadText(String key, String directory, String text) throws IOException {
        key = this.joinKey(key, directory);
        this.uploadText(key, text);
    }

    @Override
    public void downloadToStream(String key, OutputStream outStream) throws IOException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        if (outStream == null) {
            throw new IllegalArgumentException("outStream");
        }
        this._jdbcTemplate.query(this.getQuerySql(), new Object[]{key}, resultSet -> {
            try (InputStream is = resultSet.getBinaryStream(FD_CONTENT);){
                if (is != null) {
                    this.writeInput2Output(outStream, is);
                    try {
                        is.close();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public void downloadToStream(String key, String directory, OutputStream outStream) throws IOException {
        key = this.joinKey(key, directory);
        this.downloadToStream(key, outStream);
    }

    @Override
    public String downloadText(String key) throws IOException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        return (String)this._jdbcTemplate.query(this.getQuerySql(), new Object[]{key}, resultSet -> {
            if (!resultSet.next()) return null;
            try (InputStream is = resultSet.getBinaryStream(FD_CONTENT);){
                String result = null;
                if (is != null) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(is));){
                        String line;
                        StringBuilder sb = new StringBuilder();
                        String lineSep = System.getProperty("line.separator");
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append(lineSep);
                        }
                        if (sb.length() > 0) {
                            sb.delete(sb.length() - lineSep.length(), sb.length());
                        }
                        result = sb.toString();
                    }
                    try {
                        is.close();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                String string = result;
                return string;
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            return null;
        });
    }

    @Override
    public String downloadText(String key, String directory) throws IOException {
        key = this.joinKey(key, directory);
        return this.downloadText(key);
    }

    @Override
    public byte[] downloadBytes(String key) throws IOException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        return (byte[])this._jdbcTemplate.query(this.getQuerySql(), new Object[]{key}, resultSet -> {
            if (resultSet.next()) {
                InputStream in = resultSet.getBinaryStream(FD_CONTENT);
                try {
                    InputStreamFactory inputFactory = new InputStreamFactory(in);
                    byte[] byArray = inputFactory.getBytes();
                    return byArray;
                }
                catch (IOException e1) {
                    logger.error(e1.getMessage(), e1);
                }
                finally {
                    if (in != null) {
                        try {
                            in.close();
                        }
                        catch (Exception exception) {}
                    }
                }
            }
            return null;
        });
    }

    @Override
    public byte[] downloadBytes(String key, String directory) throws IOException {
        key = this.joinKey(key, directory);
        return this.downloadBytes(key);
    }

    @Override
    public void deleteIfExists(String key) {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        String sql = String.format("DELETE FROM %s WHERE %s=?", this._tableName, FD_KEY);
        this._jdbcTemplate.update(sql, new Object[]{key});
    }

    public void deleteIfExists(String key, String directory) {
        key = this.joinKey(key, directory);
        this.deleteIfExists(key);
    }

    @Override
    public void deleteAllBlobs() {
        String sql = String.format("DELETE FROM %s WHERE 1=1 ", this._tableName);
        this._jdbcTemplate.update(sql);
    }

    @Override
    public void deleteBlobs(String directory) {
        if (directory == null || "".equals(directory)) {
            throw new IllegalArgumentException("directory");
        }
        if (!this.tableExists(this._tableName)) {
            return;
        }
        directory = StringUtils.trimTrailingCharacter(directory, '\\');
        String sql = String.format("DELETE FROM %s WHERE %s like ? ", this._tableName, FD_KEY);
        this._jdbcTemplate.update(sql, new Object[]{directory + "%"});
    }

    @Override
    public URI getURI() throws URISyntaxException {
        return null;
    }

    @Override
    public URI getURI(String key) throws URISyntaxException {
        return null;
    }

    @Override
    public URI getURI(String key, String directory) throws URISyntaxException {
        return null;
    }

    @Override
    public void startCopyFromBlob(String desDirectory, String sourceDirectory) throws IOException {
    }

    private String joinKey(String key, String directory) {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        return directory == null || "".equals(directory) ? key : directory + "\\" + key;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void putData(final String key, InputStream source) throws IOException {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        if (source == null) {
            throw new IllegalArgumentException("source");
        }
        if (!this.tableExists(this._tableName)) {
            try {
                this.createTable(this._tableName);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        String tmpFileName = "tmp" + System.currentTimeMillis();
        File temp = File.createTempFile(tmpFileName, ".tmp");
        try (FileOutputStream fos = new FileOutputStream(temp);){
            int length;
            byte[] b = new byte[1024];
            while ((length = source.read(b)) > 0) {
                fos.write(b, 0, length);
            }
            source.close();
        }
        Connection conn = DataSourceUtils.getConnection((DataSource)this._jdbcTemplate.getDataSource());
        String dateFunctions = null;
        try (final FileInputStream stream = new FileInputStream(temp);){
            String insertSql = "INSERT INTO " + this._tableName + " (b_key, b_content, b_extention, updatetime) VALUES (?, ?, ?, " + dateFunctions + ")";
            DefaultLobHandler lobHandler = new DefaultLobHandler();
            AbstractLobCreatingPreparedStatementCallback cb = new AbstractLobCreatingPreparedStatementCallback((LobHandler)lobHandler){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
                    ps.setString(1, key);
                    ps.setBinaryStream(2, stream);
                    ps.setString(3, "");
                }
            };
            this._jdbcTemplate.execute(insertSql, (PreparedStatementCallback)cb);
            temp.deleteOnExit();
        }
        catch (DataAccessException e) {
            try (final FileInputStream stream2 = new FileInputStream(temp);){
                String updateSql = "UPDATE " + this._tableName + " SET b_extention = ?,b_content = ? , updatetime = " + dateFunctions + " where b_key= ?";
                DefaultLobHandler lobHandlers = new DefaultLobHandler();
                AbstractLobCreatingPreparedStatementCallback ucb = new AbstractLobCreatingPreparedStatementCallback((LobHandler)lobHandlers){

                    protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
                        ps.setString(1, "");
                        ps.setBinaryStream(2, stream2);
                        ps.setString(3, key);
                    }
                };
                this._jdbcTemplate.execute(updateSql, (PreparedStatementCallback)ucb);
            }
            catch (DataAccessException e1) {
                logger.error(e.getMessage(), e);
                logger.error(e1.getMessage(), e1);
            }
        }
        finally {
            temp.deleteOnExit();
            DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this._jdbcTemplate.getDataSource());
        }
    }

    private void writeInput2Output(OutputStream os, InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
    }

    private void checkTable(String tableName) {
        if (!this.tableExists(tableName)) {
            try {
                this.createTable(tableName);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean tableExists(String tableName) {
        if (tableName.equalsIgnoreCase("FS_DEFAULT") || tableName.equalsIgnoreCase("FS_JTABLEAREA")) {
            return true;
        }
        Connection conn = null;
        try {
            conn = DataSourceUtils.getConnection((DataSource)this._jdbcTemplate.getDataSource());
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            ISQLMetadata isqlMetadata = database.createMetadata(conn);
            String defaultSchema = isqlMetadata.getDefaultSchema();
            LogicTable tableByName = isqlMetadata.getTableByName(tableName, defaultSchema);
            if (null != tableByName) {
                boolean bl = true;
                return bl;
            }
            boolean bl = false;
            return bl;
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        catch (SQLMetadataException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (conn != null) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this._jdbcTemplate.getDataSource());
            }
        }
        return false;
    }

    private void createTable(String tableName) throws Exception {
        CreateTableStatement sqlStmt = new CreateTableStatement(null, tableName);
        LogicField fdKey = new LogicField();
        fdKey.setDataType(6);
        fdKey.setFieldName(FD_KEY);
        fdKey.setSize(255);
        fdKey.setNullable(false);
        sqlStmt.addColumn(fdKey);
        LogicField fdContent = new LogicField();
        fdContent.setDataType(9);
        fdContent.setFieldName(FD_CONTENT);
        fdContent.setNullable(true);
        sqlStmt.addColumn(fdContent);
        LogicField fdExtention = new LogicField();
        fdExtention.setDataType(6);
        fdExtention.setFieldName(FD_EXTENTION);
        fdExtention.setSize(255);
        fdExtention.setNullable(true);
        sqlStmt.addColumn(fdExtention);
        LogicField fdUpdatetime = new LogicField();
        fdUpdatetime.setDataType(2);
        fdUpdatetime.setFieldName(FD_UPDATETIME);
        fdUpdatetime.setNullable(true);
        sqlStmt.addColumn(fdUpdatetime);
        sqlStmt.getPrimaryKeys().add(fdKey.getFieldName());
        this.execStatement((SqlStatement)sqlStmt);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void execStatement(SqlStatement sqlStmt) throws SQLException, SQLInterpretException {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DataSourceUtils.getConnection((DataSource)this._jdbcTemplate.getDataSource());
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            List sqls = sqlStmt.interpret(database, conn);
            stmt = conn.createStatement();
            for (String sql1 : sqls) {
                stmt.execute(sql1);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (null != stmt) {
                stmt.close();
            }
            if (null != conn) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this._jdbcTemplate.getDataSource());
            }
        }
    }

    private String getQuerySql() {
        return String.format("SELECT b_key, b_content, b_extention, updatetime FROM %s WHERE b_key=? ", this._tableName);
    }

    public static void main(String[] args) {
    }
}

