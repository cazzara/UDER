package uder.uder.HelperClasses;

import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.Response;


import org.json.JSONObject;

/**
 * Created by cazza223 on 2/9/2017.
 */

public class RequestClass extends JsonObjectRequest {


    public RequestClass(String URL, JSONObject post_data, Response.Listener<JSONObject> listener, Response.ErrorListener error){
        super(URL, post_data, listener, error);


    }

}
