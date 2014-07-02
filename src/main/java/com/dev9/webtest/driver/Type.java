package com.dev9.webtest.driver;

/**
 * User: yurodivuie
 * Date: 6/2/13
 * Time: 4:32 PM
 */
public enum Type {

    LOCAL, REMOTE;

    //@JsonCreator
    public static Type fromJson(String text) {
        return valueOf(text.toUpperCase());
    }

}
