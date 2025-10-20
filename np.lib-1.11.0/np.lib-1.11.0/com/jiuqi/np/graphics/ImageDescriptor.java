/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.np.graphics;

import com.jiuqi.np.graphics.ImageData;
import java.io.IOException;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public interface ImageDescriptor
extends Serializable {
    public String getImageId();

    public int getWidth();

    public int getHeight();

    public ImageData getImageData() throws IOException;

    public void dispose();

    public String getURI();

    public JSONObject toClientObject() throws JSONException;
}

