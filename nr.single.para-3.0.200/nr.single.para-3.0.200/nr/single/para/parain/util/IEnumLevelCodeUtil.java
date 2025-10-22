/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.parain.util;

import java.util.List;
import java.util.Set;

public interface IEnumLevelCodeUtil {
    public String getLevelParentCode(String var1, String var2, boolean var3) throws Exception;

    public String getLevelParentCode(String var1, String var2) throws Exception;

    public List<Integer> getLevelLengths(Object var1) throws Exception;

    public int getLevelCodeLevel(String var1, String var2, boolean var3) throws Exception;

    public int getCurLevelIndex(String var1, List<Integer> var2);

    public String getLevelRecode(String var1, int var2, String var3, boolean var4, Set<String> var5) throws Exception;
}

