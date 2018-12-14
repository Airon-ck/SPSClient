package com.nbxuanma.spsclient.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParse {

    public static int getStatus(String result){
        int status=1;
        JSONObject object=null;
        try {
            object=new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            status=object.getInt("Status");
        } catch (JSONException e) {
            e.printStackTrace();
            status=400;
        }

        return status;

    }

}
