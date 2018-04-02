package com.example.ladysnake.mobile.tools;

/**
 * An interface that describes objects that have methods which don't allow null parameters
 * @deprecated since API23 doesn't allow default implementations
 * @author Ludwig GUERIN
 */
public interface I_NoNullParams {
    String ERR_MSG = "Arguments must not be null";
//    default void assertNoNull(Object... objects){
//        for (Object o: objects) {
//            if(o == null)
//                throw new IllegalArgumentException(ERR_MSG);
//        }
//    }
}
