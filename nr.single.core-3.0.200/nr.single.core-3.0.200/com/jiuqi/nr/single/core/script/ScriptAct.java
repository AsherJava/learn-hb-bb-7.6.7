/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.nr.single.lib.reg.internal.SingleElaesImpl
 */
package com.jiuqi.nr.single.core.script;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.lib.reg.internal.SingleElaesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptAct {
    private static final Logger logger = LoggerFactory.getLogger(ScriptAct.class);
    private static final String TC_SCRIPT_KEY = "@BwLQjTJW[E^DuCRzWqTlvmSJU]OtQ^IwRq~_xtAII`Ae@qiqmcMke}i\u007fOkREq_x`d}kU@m\u007fqoLIzrewlREhQxJQb}KJQKd_RIgooEDCFrsXg[o_UttIEDCLkNdPWuTT";
    private static final String TC_SCRIPT_KEY2 = "GZRGCBN18" + String.valueOf(Integer.parseInt("211115", 16));

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadFromFile(String fileName) {
        MemStream source = new MemStream();
        try {
            source.loadFromFile(fileName);
            source.seek(0L, 0);
            int size = source.readInt();
            byte[] data = new byte[(int)(source.getSize() - source.getPosition())];
            source.read(data, 0, data.length);
            SingleElaesImpl aes = new SingleElaesImpl();
            String s = aes.decryptAESString(data, TC_SCRIPT_KEY, false);
            logger.info("\u5bc6\u540e\uff1a" + s);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        finally {
            source = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void testIniFormFile(String fileName) {
        byte[] infos = null;
        MemStream source = new MemStream();
        try {
            source.loadFromFile(fileName);
            source.seek(0L, 0);
            int size = source.readInt();
            byte[] data = new byte[(int)(source.getSize() - source.getPosition())];
            source.read(data, 0, data.length);
            SingleElaesImpl aes = new SingleElaesImpl();
            infos = aes.decryptAESStringToBytes(data, TC_SCRIPT_KEY2, false);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        finally {
            source = null;
        }
        Ini ini = new Ini();
        try {
            ini.loadIniFromBytes(infos, "");
        }
        catch (Exception e1) {
            logger.info(e1.getMessage(), e1);
        }
    }
}

