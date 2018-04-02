package com.example.ladysnake.mobile.tools;

/**
 * An interface that describes objects that are tied to a specific resource
 * @author Ludwig GUERIN
 */
public interface ResourceAware {
    ResourceAware withRes(int res);
    int getRes();
}
