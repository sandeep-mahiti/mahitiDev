package com.parentof.mai.api;




import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Feeds;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class SignApiRestAdapter {

    private static RestAdapter sharedInstance = null;

    public static RestAdapter getInstance() {
        if (sharedInstance == null) {

            sharedInstance = new RestAdapter.Builder()
                    .setEndpoint(Feeds.BASEURL)
                    .setRequestInterceptor(new MyRetrofitInterceptor())
                    .build();
        }
        return sharedInstance;
    }

    public static class MyRetrofitInterceptor implements RequestInterceptor {

        @Override
        public void intercept(RequestFacade req) {
            String token = "application/json"; // "application/json";//"application/x-www-form-urlencoded"; // get token logic  -- ;

                req.addHeader("Content-Type", token);
               // req.addHeader("X-Authorization",Constants.AUTH);
        }
    }
}
