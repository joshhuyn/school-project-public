package com.school.utils.tools.config;

import java.util.HashMap;

public class ConfigProvider extends HashMap<String, String>
{
    public ConfigProvider()
    {
        this("main.properties");
    }

    public ConfigProvider(String context)
    {
        ConfigProviderInitializer.initialize(this, context);
    }
}
