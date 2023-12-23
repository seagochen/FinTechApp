using System.Collections.Generic;

namespace csharp_app.Components;

public static class DictionaryMerger
{
    public static Dictionary<TKey, TValue> MergeDictionaries<TKey, TValue>(params Dictionary<TKey, TValue>[] dictionaries)
    {
        var result = new Dictionary<TKey, TValue>();

        foreach (var dictionary in dictionaries)
        {
            foreach (var pair in dictionary)
            {
                // 如果键已存在，将用新的值覆盖旧值
                result[pair.Key] = pair.Value;
            }
        }

        return result;
    }
}