package com.example.ladysnake.mobile.tools;

import android.content.Context;

//import com.example.ladysnake.mobile.R;

/**
 * An interface describing objects that are aware of the API
 * @deprecated since API23 doesn't allow default implementations
 * @author Ludwig GUERIN
 */
public interface ApiAware {
    Context getContext();

//    default String api(String uri){
//        return getContext().getString(R.string.api) + uri;
//    }
}
