namespace csharp_app.Components;

using Newtonsoft.Json;
using System;

public static class JsonFileHandler
{
    // 读取JSON文件并转换为特定类型的对象
    // public static T ReadJsonFile<T>(string filePath)
    // {
    //     try
    //     {
    //         // 读取JSON文件
    //         var json = FileHandler.ReadFile(filePath);
    //         
    //         // 将JSON反序列化为对象            
    //         return JsonConvert.DeserializeObject<T>(json) ?? throw new InvalidOperationException();
    //     }
    //     catch (Exception ex)
    //     {
    //         throw new ApplicationException("Error reading from JSON file", ex);
    //     }
    // }

    // 将对象序列化为JSON并写入文件
    public static void WriteJsonFile<T>(string filePath, T objectToWrite)
    {
        try
        {
            // 将对象序列化为JSON
            var json = JsonConvert.SerializeObject(objectToWrite, Formatting.Indented);
            
            // 将JSON写入文件
            FileHandler.WriteFile(filePath, json);
        }
        catch (Exception ex)
        {
            throw new ApplicationException("Error writing to JSON file", ex);
        }
    }
}