package com.myspringcore.core.env;

public interface Environment {

    boolean acceptsProfiles(String... specifiedProfiles);
}
