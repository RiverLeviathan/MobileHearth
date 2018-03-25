package com.example.ladysnake.mobile.tools;

import java.util.Arrays;
import java.util.stream.Stream;

public interface I_NoNullParams {
    String ERR_MSG = "Arguments must not be null";
    default void assertNoNull(Object... objects){
        for (Object o: objects) {
            if(o == null)
                throw new IllegalArgumentException(ERR_MSG);
        }
    }
}
