package uder.uder;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cazza223 on 2/9/2017.
 */

public class RequestClass extends StringRequest {
    private Map<String, String> params;

    public RequestClass(String URL, Map<String, String> post_data, Response.Listener<String> listener, Response.ErrorListener error){
        super(Method.POST, URL, listener, error);
        params = new HashMap<>();
        params = post_data;

    }

    public Map<String, String> getParams() { return params; }

}
