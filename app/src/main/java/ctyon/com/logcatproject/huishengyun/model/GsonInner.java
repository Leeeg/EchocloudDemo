package ctyon.com.logcatproject.huishengyun.model;

import com.google.gson.Gson;

public class GsonInner {

    private static Gson gson;

    public static Gson getInstance() {
        if (null == gson)
            gson = new Gson();
        return gson;
    }
}
