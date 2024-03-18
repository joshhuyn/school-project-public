package com.school.utils.tools.config;

import java.util.Scanner;

public class ConfigProviderInitializer
{
    public static void initialize(ConfigProvider provider, String context)
    {
        Scanner scanner = getScanner(context);
        populate(provider, scanner);
    }
    
    private static Scanner getScanner(String context)
    {
        return new Scanner(ConfigProviderInitializer.class.getClassLoader().getResourceAsStream(context));
    }

    private static void populate(ConfigProvider provider, Scanner scanner)
    {
        try
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (line.equals("") || !line.contains("="))
                {
                    continue;
                }

                String[] keyValuePair = line.split("=");

                String value = keyValuePair[1].trim();
                if (value.startsWith("\"") && value.endsWith("\""))
                {
                    value = value.substring(1, value.length() - 1);
                }

                if (System.getenv(keyValuePair[0].trim()) != null)
                {
                    value = System.getenv(keyValuePair[0].trim());
                }

                provider.put(keyValuePair[0].trim(), value);
            }
        }
        finally
        {
            scanner.close();
        }
    }
}
