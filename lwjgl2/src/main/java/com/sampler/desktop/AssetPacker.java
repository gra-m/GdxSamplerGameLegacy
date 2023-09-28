package com.sampler.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

// takes all small textures and creates an uber-texture with co-ordinates for them (like bitmap font?)
// Working directory not set so defaults to root directory of project
public class AssetPacker
{
    private static final boolean DRAW_DEBUG_OUTLINE = true;
    public static final String ASSETS_PATH = "assets";
    public static final String RAW_ASSETS_PATH = "lwjgl2/assets-raw";

    public static void main( String[] args )
    {
        TexturePacker.Settings settings =  new TexturePacker.Settings(  );
        settings.debug = DRAW_DEBUG_OUTLINE;
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;

        TexturePacker.process(settings, RAW_ASSETS_PATH + "/skin", ASSETS_PATH + "/ui", "uiskin");
    }


}
