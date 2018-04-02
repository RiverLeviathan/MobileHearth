package com.example.ladysnake.mobile.tools;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

/**
 * A {@link Fragment} that provides a default implementation of {@link ResourceAware}
 * @author Ludwig GUERIN
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
