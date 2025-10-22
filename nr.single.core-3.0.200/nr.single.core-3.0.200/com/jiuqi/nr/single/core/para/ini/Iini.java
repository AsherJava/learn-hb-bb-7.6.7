/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.ini;

import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.ini.Section;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Iini {
    public ArrayList<Section> ReadSections();

    public ArrayList<String> ReadSection(String var1);

    public String ReadString(String var1, String var2, String var3);

    public void WriteString(String var1, String var2, String var3);

    public void loadIni(String var1);

    public void loadIniFile(String var1) throws StreamException, IOException;

    public void loadIniFromBytes(byte[] var1, String var2) throws StreamException, IOException;

    public void saveIni(String var1);

    public byte[] saveIniToBytes(String var1);

    public List<String> iniLoadStrs(String var1);
}

