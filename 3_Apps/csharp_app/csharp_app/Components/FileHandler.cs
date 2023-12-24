using System;
using System.Globalization;
using System.IO;

namespace csharp_app.Components;

public static class FileHandler
{
    // 检测文件是否存在
    public static bool FileExists(string filePath)
    {
        return File.Exists(filePath);
    }
    
    // 创建文件
    public static void CreateFile(string filePath)
    {
        File.Create(filePath);
    }
    
    // 删除文件
    public static void DeleteFile(string filePath)
    {
        File.Delete(filePath);
    }
    
    // 读取文件
    public static string ReadFile(string filePath)
    {
        return File.ReadAllText(filePath);
    }
    
    // 写入文件
    public static void WriteFile(string filePath, string content)
    {
        File.WriteAllText(filePath, content);
    }
    
    // 追加文件
    public static void AppendFile(string filePath, string content)
    {
        File.AppendAllText(filePath, content);
    }
    
    // 复制文件
    public static void CopyFile(string sourceFilePath, string destinationFilePath)
    {
        File.Copy(sourceFilePath, destinationFilePath);
    }
    
    // 移动文件
    public static void MoveFile(string sourceFilePath, string destinationFilePath)
    {
        File.Move(sourceFilePath, destinationFilePath);
    }
    
    // 获取文件信息
    public static FileInfo GetFileInfo(string filePath)
    {
        return new FileInfo(filePath);
    }
    
    // 获取文件大小
    public static long GetFileSize(string filePath)
    {
        return GetFileInfo(filePath).Length;
    }
    
    // 获取文件扩展名
    public static string GetFileExtension(string filePath)
    {
        return GetFileInfo(filePath).Extension;
    }
    
    // 获取文件名
    public static string GetFileName(string filePath)
    {
        return GetFileInfo(filePath).Name;
    }
    
    // 获取文件路径
    public static string GetFilePath(string filePath)
    {
        return GetFileInfo(filePath).DirectoryName ?? throw new InvalidOperationException();
    }
    
    // 获取文件创建时间
    public static string GetFileCreationTime(string filePath)
    {
        return GetFileInfo(filePath).CreationTime.ToString(CultureInfo.CurrentCulture);
    }
    
    // 获取文件最后修改时间
    public static string GetFileLastWriteTime(string filePath)
    {
        return GetFileInfo(filePath).LastWriteTime.ToString(CultureInfo.CurrentCulture);
    }
    
    // 获取文件最后访问时间
    public static string GetFileLastAccessTime(string filePath)
    {
        return GetFileInfo(filePath).LastAccessTime.ToString(CultureInfo.CurrentCulture);
    }
}