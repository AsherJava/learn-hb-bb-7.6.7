/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formcopy.common;

import com.jiuqi.nr.task.form.formcopy.common.SchemeType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormCopyTransUtils {
    private static final Logger log = LoggerFactory.getLogger(FormCopyTransUtils.class);

    public Date transTimeStamp(Timestamp time) {
        return new Date(time.getTime());
    }

    public Timestamp transTimeStamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static int transSchemeType(SchemeType type) {
        if (null == type) {
            return 0;
        }
        return type.getValue();
    }

    public static SchemeType transSchemeType(Integer type) {
        if (null == type) {
            return null;
        }
        return SchemeType.valueOf(type);
    }

    public String transClob(String type) throws SQLException {
        return type;
    }

    public String transClob(Clob type) {
        try {
            Reader is = type.getCharacterStream();
            BufferedReader br = new BufferedReader(is);
            String s = br.readLine();
            StringBuffer sb = new StringBuffer();
            while (s != null) {
                sb.append(s);
                s = br.readLine();
            }
            return sb.toString();
        }
        catch (SQLException se) {
            return "";
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }
}

