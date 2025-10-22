/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Identifiable
 */
package authority;

import com.jiuqi.np.authz2.Identifiable;

public class DashBoardResource
implements Identifiable {
    private String id;
    private String type;
    private String title;

    public DashBoardResource() {
    }

    public DashBoardResource(String id, String type, String title) {
        this.id = id;
        this.type = type;
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

