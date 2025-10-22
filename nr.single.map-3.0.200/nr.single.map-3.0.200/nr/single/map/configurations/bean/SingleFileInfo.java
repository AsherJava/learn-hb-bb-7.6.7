/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.bean;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SingleFileInfo
implements Serializable {
    private static final long serialVersionUID = 1495377769528469661L;
    private String key;
    private String jioKey;
    private String zbKey;
    private String entityKey;
    private String formulaKey;
    private Date lastTime;

    public SingleFileInfo() {
    }

    public SingleFileInfo(String key, String jioKey, String zbKey, String entityKey, String formulaKey, Date lastTime) {
        this.key = key;
        this.jioKey = jioKey;
        this.zbKey = zbKey;
        this.entityKey = entityKey;
        this.formulaKey = formulaKey;
        this.lastTime = lastTime;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getJioKey() {
        return this.jioKey;
    }

    public void setJioKey(String jioKey) {
        this.jioKey = jioKey;
    }

    public String getZbKey() {
        return this.zbKey;
    }

    public void setZbKey(String zbKey) {
        this.zbKey = zbKey;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }

    public Date getLastTime() {
        return this.lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public static SingleFileInfo buildFileInfo(ResultSet rs) throws SQLException {
        int index = 1;
        SingleFileInfo info = new SingleFileInfo();
        info.setKey(rs.getString(index));
        info.setJioKey(rs.getString(++index));
        info.setZbKey(rs.getString(++index));
        info.setEntityKey(rs.getString(++index));
        info.setFormulaKey(rs.getString(++index));
        info.setLastTime(rs.getTimestamp(++index));
        return info;
    }
}

