package com.sampler.common;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class SampleFactory {

    public static SampleBase newSample(String name){
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("name param is required");

        SampleInfo info = SampleInfoStore.find(name);

        try {
            return (SampleBase) ClassReflection.newInstance(info.getClazz());
        } catch (ReflectionException e) {
            throw new RuntimeException(String.format("Cannot create sample with name %s", name), e);
        }
    }

    private SampleFactory(){}

}
