using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Interactivity;
using csharp_app.Components;
using csharp_app.ViewController.Components;
using csharp_app.ViewController.Configuration;
using csharp_app.ViewController.MainWindowUserControl;
using Newtonsoft.Json;

namespace csharp_app;

public partial class MainWindow : Window
{
    private MainWindowUserControlAccountsManagmentTab? _accountManagementTab;
    private MainWindowUserControlHouseholdTab? _householdTab;
    private MainWindowUserControlStockMarketTab? _stockMarketTab;
    
    public MainWindow()
    {
        InitializeComponent();
#if DEBUG
        this.AttachDevTools();
#endif
        // UserControlsを初期化する        
        _accountManagementTab = new MainWindowUserControlAccountsManagmentTab(this);
        _householdTab = new MainWindowUserControlHouseholdTab();
        _stockMarketTab = new MainWindowUserControlStockMarketTab();
        
        // selectedTabを設定し、初期化する
        var tabControl = this.FindControl<TabControl>("LeftTabControl");
        var selectedTab = tabControl?.Items.OfType<TabItem>().FirstOrDefault();
        if (selectedTab != null) selectedTab.IsSelected = true;
        
        // RightMainAreaを初期化する
        UpdateRightMainArea(selectedTab);
        
        // lambdaを利用し、Json
        Opened += async (sender, args) => await PromptWhenConfigIsEmpty();
        
        // 设置分辨率 1480x720
        Width = 1480; 
        Height = 720;
    }
    
    
    /// <summary>
    /// config.json から設定情報を読み込む。
    /// もし、ファイルが存在しない場合はPrompt提示を表示する。
    /// </summary>
    private async Task PromptWhenConfigIsEmpty()
    {
        // Configを読み込む
        var config = AppSession.Instance.AppConfigPath;
        
        // Configが存在しないと、メッセージを表示する
        if (!FileHandler.FileExists(config ?? throw new InvalidOperationException()))
        {
            // 修改 MessageText 的内容
            // var messageBox = new MessageBox();
            // messageBox.FindControl<TextBlock>("MessageText")!.Text = "Json is empty. Would you like to create a new one?";
            // await messageBox.ShowDialog(this);
            
            if (await MessageBoxWithOkCancel.ShowAsync(this, "Json is empty. Would you like to create a new one?") == MessageBoxResult.Ok)
            {
                var configurationWindow = new Configuration();
                await configurationWindow.ShowDialog(this);
            }
        }
        else // Configが存在すると、内容を読み込む
        {
            // 設定ファイルを読み込む
            var configJson = FileHandler.ReadFile(config);
            
            // 読み込んだ内容をDictionaryに変換する
            var configJsonDict = JsonConvert.DeserializeObject<Dictionary<string, object?>>(configJson);
        
            // 読み込んだ内容をAppSessionに保存する
            ConfigUpdater.UpdateConfigFromDictionary(AppSession.Instance.AppConfigParams, configJsonDict);
        }
    }


    /// <summary>
    ///　画面の左側のタプルを選択した時に、右側のコンテンツを変更する
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void OnTabSelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        var tabControl = sender as TabControl;
        var selectedTab = tabControl?.SelectedItem as TabItem;
        UpdateRightMainArea(selectedTab);
    }
        
    /// <summary>
    /// 右側のコンテンツを更新する
    /// </summary>
    /// <param name="selectedTab"></param>
    private void UpdateRightMainArea(TabItem? selectedTab)
    {
        //　TABが選ばない場合、何もしない
        if (selectedTab == null) return;

        // 選択されたTABによって、RightContentControlの内容を変更する
        var rightContentControl = this.FindControl<ContentControl>("RightContentControl");
        
        // switch文を利用し、選択されたTABによって、RightContentControlの内容を変更する
        if (rightContentControl != null)
        {
            // Debug
            Console.WriteLine(selectedTab.Name);
            
            rightContentControl.Content = selectedTab.Name switch
            {
                "HouseholdLedgerView" => _householdTab,
                "StockAnalysisView" => _stockMarketTab,
                "AccountsView" => _accountManagementTab,
                _ => rightContentControl.Content
            };
        }
    }

    /// <summary>
    /// 設定画面を開く
    /// </summary>
    private async void OnSettingsClick(object sender, RoutedEventArgs e)
    {
        var configurationWindow = new Configuration();
        await configurationWindow.ShowDialog(this);
        
        // 設定画面を閉じた後、画面を更新する
        RefreshConfigData();
    }
    
    private void RefreshConfigData()
    {
        // 清理旧的 UserControl 实例（如果需要）
        DisposeUserControls();

        // 重新初始化 UserControl 实例
        _accountManagementTab = new MainWindowUserControlAccountsManagmentTab(this);
        _householdTab = new MainWindowUserControlHouseholdTab();
        _stockMarketTab = new MainWindowUserControlStockMarketTab();

        // 根据需要更新 UI 或重新绑定数据
        UpdateUi();
    }

    private void DisposeUserControls()
    {
        // 如果 UserControls 实现了 IDisposable，清理它们
        // _accountManagementTab?.Dispose();
        // _householdTab?.Dispose();
        // _stockMarketTab?.Dispose();
    }

    private void UpdateUi()
    {
        // 实现更新 UI 的逻辑，例如，刷新显示的内容或重新绑定数据源
        // ...
    }

    /// <summary>
    /// 何もせずに終了する
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void OnExitClick(object sender, RoutedEventArgs e)
    {
        Close();
    }

    private void OnDataSourceClick(object sender, RoutedEventArgs e)
    {
        // Open Views Editor Window
        throw new NotImplementedException();
    }

    private void OnAccountsClick(object sender, RoutedEventArgs e)
    {
        // Open Account Explorer Window
        throw new NotImplementedException();
    }

    private void OnFinanceClick(object sender, RoutedEventArgs e)
    {
        // Open Finance Explorer Window
        throw new NotImplementedException();
    }

    private void OnStockClick(object sender, RoutedEventArgs e)
    {
        // Open Stock Window
        throw new NotImplementedException();
    }
}