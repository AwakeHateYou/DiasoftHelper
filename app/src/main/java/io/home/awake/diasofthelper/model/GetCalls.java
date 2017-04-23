package io.home.awake.diasofthelper.model;

import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;
import io.home.awake.diasofthelper.utils.HttpUtils;

public class GetCalls {
    public void getPublicTimeline() throws JSONException {
        HttpUtils.get("statuses/public_timeline.json", null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                System.out.println("Start http");
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                System.out.println(statusCode);
                System.out.println(response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                System.out.println(statusCode);
                try {
                    System.out.println(timeline.get(0));
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

        });
    }
}
