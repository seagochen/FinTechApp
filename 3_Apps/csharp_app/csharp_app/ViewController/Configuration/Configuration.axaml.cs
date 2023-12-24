using System;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Interactivity;
using csharp_app.Components;

namespace csharp_app.ViewController.Configuration;

public partial class Configuration : Window
{
    // ConfigurationUserControlDatabaseTab.xaml.cs
    readonly ConfigurationUserControlDatabaseTab? _userControlDbConfig;
    
    // ConfigurationUserControlTwsTab.xaml.cs
    readonly ConfigurationUserControlTwsTab? _userControlTwsConfig;
    
    public Configuration()
    {
        InitializeComponent();
        
#if DEBUG
        this.AttachDevTools();
#endif
        
        // 次、設定画面の右側のコンテンツを初期化する
        _userControlDbConfig = new ConfigurationUserControlDatabaseTab(this);
        _userControlTwsConfig = new ConfigurationUserControlTwsTab(this);
        
        // 画面の解析度を設定する
        Width = 640;
        Height = 480;
        
        // 右側のDefaultのコンテンツを設定する
        ContentPanel.Content = _userControlDbConfig;
        Opened += (sender, e) => { Navigation.SelectedIndex = 0; };
    }

    /// <summary>
    /// 選択されたタブによって右側のコンテンツを変更する
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void Navigation_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        // 获取选项卡的索引
        var index = Navigation.SelectedIndex;
        
        // 根据索引设置右侧内容
        ContentPanel.Content = index switch
        {
            0 => _userControlDbConfig,
            1 => _userControlTwsConfig,
            _ => ContentPanel.Content
        };
    }
    
    /// <summary>
    /// 設定された内容を保存しながら、設定画面を閉じる
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void OnApplyClick(object? sender, RoutedEventArgs e)
    {
        // 保存された一時データから本番設定を取得する
        var appConfigParamsTemp = AppSession.Instance.AppConfigParamsTemp;
        
        // 本番設定を保存する
        AppSession.Instance.AppConfigParams = appConfigParamsTemp;
        
        // 本番設定をJSON化にする
        var appConfigParamsDict = ObjectToDictionaryConverter.ConvertToDictionary(appConfigParamsTemp);
        
        // DatabaseのタイプがSQLiteのであれば、他のデータは空になる
        if (appConfigParamsDict["DatabaseType"] as string == "SQLite")
        {
            appConfigParamsDict["DatabaseUsername"] = "";
            appConfigParamsDict["DatabasePassword"] = "";
            appConfigParamsDict["DatabaseHostUrl"] = "";
            appConfigParamsDict["DatabaseHostPort"] = 0;
        }
        else
        {
            // もし他に値が空であれば、デフォルト値を設定する
            if (string.IsNullOrEmpty(appConfigParamsDict["DatabaseUsername"] as string))
            {
                appConfigParamsDict["DatabaseUsername"] = "";
            }
            if (string.IsNullOrEmpty(appConfigParamsDict["DatabasePassword"] as string))
            {
                appConfigParamsDict["DatabasePassword"] = "";
            }

            if (string.IsNullOrEmpty(appConfigParamsDict["DatabaseHostUrl"] as string))
            {
                appConfigParamsDict["DatabaseHostUrl"] = "";
            }
        }
        
        // IB TWSの値が空であれば、デフォルト値を設定する
        if (string.IsNullOrEmpty(appConfigParamsDict["TwsSocketUrl"] as string))
        {
            appConfigParamsDict["TwsSocketUrl"] = "";
        }
        
        // アプリケーションの設定ファイルのパスを取得する
        var appConfigPath = AppSession.Instance.AppConfigPath;
        
        // 設定をエクスポートして保存する
        JsonFileHandler.WriteJsonFile(appConfigPath ?? throw new InvalidOperationException(), appConfigParamsDict);
        
        // 設定画面を閉じる
        Close();
    }

    /// <summary>
    /// キャンセルボタンを押した時に設定画面を閉じる
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void OnCancelClick(object? sender, RoutedEventArgs e)
    {
        Close();
    }
}