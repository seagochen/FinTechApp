namespace csharp_app.Components;

using System;
using System.Collections.Generic;
using System.Reflection;

public class ConfigUpdater
{
    public static void UpdateConfigFromDictionary<T>(T config, Dictionary<string, object?> updates) where T : class
    {
        Type configType = config.GetType();

        foreach (var item in updates)
        {
            PropertyInfo? property = configType.GetProperty(item.Key);

            if (property != null && property.CanWrite)
            {
                property.SetValue(config, Convert.ChangeType(item.Value, property.PropertyType));
            }
        }
    }
}