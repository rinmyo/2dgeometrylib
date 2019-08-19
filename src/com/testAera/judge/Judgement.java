package com.testAera.judge;

import com.testAera.Exception.AreaTypeException;

@FunctionalInterface
public interface Judgement {
    boolean judge() throws AreaTypeException;
}
