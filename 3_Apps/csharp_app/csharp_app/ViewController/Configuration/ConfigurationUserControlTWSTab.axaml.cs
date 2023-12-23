using System;
using Avalonia.Controls;
using csharp_app.Components;

namespace csharp_app.ViewController.Configuration;

public partial class ConfigurationUserControlTwsTab : UserControl
{
    private readonly Window _parentWindow;
    private AppConfigParams appConfigParamsTemp { get; set; }

    public ConfigurationUserControlTwsTab(Window parentWindow)
    {
        InitializeComponent();

        // 変量を初期化
        _parentWindow = parentWindow;
        
        // IBTWSConfigTempはIBTWSConfigの一時変数として機能し、現在の設定への変更を記録するために使用される
        appConfigParamsTemp = AppSession.Instance.AppConfigParamsTemp;
        
        // UIを更新する
        if (FileHandler.FileExists(AppSession.Instance.AppConfigPath ?? throw new InvalidOperationException())) 
        {
            UpdateUi();
        }
    }

    /// <summary>
    /// IB TWSの設定を更新する
    /// </summary>
    private void UpdateUi()
    {
        // IBTWSConfigTempの値はAppSession.Instance.IBTWSConfigによって更新される
        var configMotoDict = ObjectToDictionaryConverter.ConvertToDictionary(AppSession.Instance.AppConfigParams);
        ConfigUpdater.UpdateConfigFromDictionary(appConfigParamsTemp, configMotoDict);

        // 既存の設定をUIに反映する
        var tcpServer = appConfigParamsTemp.TwsSocketUrl;
        var tcpPort = appConfigParamsTemp.TwsSocketPort;
        
        // もしサーバーが設定されている場合、UIの値を更新する
        if (!string.IsNullOrEmpty(tcpServer))
        {
            var tcpServerTextBox = this.FindControl<TextBox>("TcpServer");
            if (tcpServerTextBox != null) tcpServerTextBox.Text = tcpServer;
        }
        
        // もしポートが設定されている場合、UIの値を更新する
        if (tcpPort != 0)
        {
            var tcpPortTextBox = this.FindControl<TextBox>("TcpPort");
            if (tcpPortTextBox != null) tcpPortTextBox.Text = tcpPort.ToString();
        }
    }

    private void OnTcpServerChanged(object? sender, TextChangedEventArgs e)
    {
        // 获取 TCP 服务器文本框的新文本
        if (sender is not TextBox tcpServerTextBox) return;
        
        // 获取 TCP 服务器
        var tcpServer = tcpServerTextBox.Text;
        
        // 保存 TCP 服务器
        appConfigParamsTemp.TwsSocketUrl = tcpServer;
    }

    private void OnTcpPortChanged(object? sender, TextChangedEventArgs e)
    {
        // 获取 TCP 端口文本框的新文本
        if (sender is not TextBox tcpPortTextBox) return;
        
        // 获取 TCP 端口
        var tcpPort = tcpPortTextBox.Text;
        
        // 如果用户输入的不是数字，那么将其替换为空
        if (!int.TryParse(tcpPort, out int port))
        {
            tcpPortTextBox.Text = "";
            return;
        }
        
        // 保存 TCP 端口
        appConfigParamsTemp.TwsSocketPort = port;
    }
}