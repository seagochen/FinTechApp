using System.Collections.Generic;
using System.Reflection;

namespace csharp_app.Components;

public static class ObjectToDictionaryConverter
{
    public static Dictionary<string, object?> ConvertToDictionary(object obj)
    {
        var dictionary = new Dictionary<string, object?>();

        foreach (PropertyInfo property in obj.GetType().GetProperties())
        {
            // 使用属性的名称作为键
            var propertyName = property.Name;

            // 获取属性的值
            var propertyValue = property.GetValue(obj);

            // 将属性名和值添加到字典中
            dictionary.Add(propertyName, propertyValue);
        }

        return dictionary;
    }
}