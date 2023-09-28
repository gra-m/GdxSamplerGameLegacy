package com.sampler.common;

import com.sampler.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SampleInfoStore {
    public static final List<SampleInfo> ALL = Arrays.asList(
        ApplicationListenerSample.SAMPLE_INFO,
        GdxGeneratedSample.SAMPLE_INFO,
        GdxModuleInfoSample.SAMPLE_INFO,
        GdxReflectionSample.SAMPLE_INFO,
        GdxSamplerGame.SAMPLE_INFO,
        InputListeningSample.SAMPLE_INFO,
        InputPollingSample.SAMPLE_INFO,
        OrthographicCameraSample.SAMPLE_INFO,
        ViewportSample.SAMPLE_INFO,
        SpriteBatchSample.SAMPLE_INFO,
        ShapeRendererSample.SAMPLE_INFO,
        BitmapFontSample.SAMPLE_INFO,
        PoolingSampleOne.SAMPLE_INFO,
        PoolingSampleOneB.SAMPLE_INFO,
        AssetManagerSample.SAMPLE_INFO,
        TextureAtlasSample.SAMPLE_INFO,
        CustomActorSample.SAMPLE_INFO,
        ActionsSample.SAMPLE_INFO,
        TableSample.SAMPLE_INFO,
        SkinSample.SAMPLE_INFO,
        AshleyEngineSample.SAMPLE_INFO,
        AshleySystemSample.SAMPLE_INFO

    );

    public static List<String> getSampleNames() {
        List<String> ret = new ArrayList<>();
        for (SampleInfo info : ALL) {
            ret.add(info.getName());
        }
        Collections.sort(ret);

        return ret;
    }

    public static SampleInfo find(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("name argument is required");

        SampleInfo ret = null;

        for (SampleInfo info : ALL) {
            if (info.getName().equals(name)) {
                ret = info;
                break;
            }
        }

        if (ret == null)
            throw new IllegalArgumentException(String.format("Could not find sample with name %s", name));

        return ret;
    }


}
