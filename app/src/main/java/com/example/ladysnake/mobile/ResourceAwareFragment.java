package com.example.ladysnake.mobile;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

/**
 * Created by Ludwig on 24/03/2018.
 */

public abstract class ResourceAwareFragment extends Fragment implements ResourceAware {
    protected int res = -404;


    public ResourceAwareFragment withRes(int res){
        this.res = res;
        return this;
    }

    public int getRes(){ return this.res; }

    public Drawable getIcon(){ return this.getActivity().getDrawable(this.getRes()); }
}
