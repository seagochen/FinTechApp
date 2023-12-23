using System;
using System.IO;
using System.Net.Http;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Avalonia.Controls;
using Avalonia.Interactivity;
using Avalonia.Platform.Storage;
using csharp_app.Components;
using Microsoft.Data.Sqlite;
using csharp_app.ViewController.Components;

namespace csharp_app.ViewController.Configuration;

public partial class ConfigurationUserControlDatabaseTab : UserControl
{
    private readonly Window _parentWindow;
    private AppConfigParams AppConfigParamsTemp { get; set; }
    public ConfigurationUserControlDatabaseTab(Window　parentWindow)
    {
        InitializeComponent();
    
        // 変量を初期化
        _parentWindow = parentWindow;
        
        // DatabaseConfigTempはDatabaseConfigの一時変数として機能し、現在の設定への変更を記録するために使用される
        AppConfigParamsTemp = AppSession.Instance.AppConfigParamsTemp;
        
        // UIを更新する
        if (FileHandler.FileExists(AppSession.Instance.AppConfigPath ?? throw new InvalidOperationException())) 
        {
            UpdateUi();
        }
    }

    /// <summary>
    /// Databaseの設定を更新する
    /// </summary>
    private void UpdateUi()
    {
        // DatabaseConfigの値はAppSession.Instance.DatabaseConfigによって更新される
        var configMotoDict = ObjectToDictionaryConverter.ConvertToDictionary(AppSession.Instance.AppConfigParams);
        ConfigUpdater.UpdateConfigFromDictionary(AppConfigParamsTemp, configMotoDict);
        
        // 既存の設定をUIに反映する
        var databaseType = AppConfigParamsTemp.DatabaseType;
        var databasePath = AppConfigParamsTemp.DatabasePath;
        var databaseUsername = AppConfigParamsTemp.DatabaseUsername;
        var databasePassword = AppConfigParamsTemp.DatabasePassword;
        var databaseHostIp = AppConfigParamsTemp.DatabaseHostUrl;
        var databaseHostPort = AppConfigParamsTemp.DatabaseHostPort;
        
        // 更新 TextBox:DatabasePath の内容
        if (databasePath != null)
        {
            var textBox = this.FindControl<TextBox>("DatabasePath");
            textBox!.Text = FileHandler.GetFileName(databasePath);
        }
        else
        {
            // データベースのパスがnullの場合、何もしない
            return;
        }
        
        // 更新　ComboBox:DbType の内容
        if (databaseType != null)
        {
            var comboBox = this.FindControl<ComboBox>("DatabaseType");

            // 查找与 databaseType 匹配的 ComboBoxItem
            var matchedItem = comboBox.Items.Cast<ComboBoxItem>().FirstOrDefault(x => x.Content?.ToString() == databaseType);

            // 如果找到匹配项，则设置为选中
            if (matchedItem != null)
            {
                comboBox.SelectedItem = matchedItem;

                // もしDbTypeがSQLiteならば、Username，Password，IP，Port入力欄を無効にする
                var usernameTextBox = this.FindControl<TextBox>("Username");
                var passwordTextBox = this.FindControl<TextBox>("Password");
                var tcpServerTextBox = this.FindControl<TextBox>("TcpServer");
                var tcpPortTextBox = this.FindControl<TextBox>("TcpPort");

                if (databaseType == "SQLite")
                {
                    usernameTextBox!.IsEnabled = false;
                    passwordTextBox!.IsEnabled = false;
                    tcpPortTextBox!.IsEnabled = false;
                    tcpServerTextBox!.IsEnabled = false;
                }
                else
                {
                    usernameTextBox!.IsEnabled = true;
                    passwordTextBox!.IsEnabled = true;
                    tcpPortTextBox!.IsEnabled = true;
                    tcpServerTextBox!.IsEnabled = true;
                }
            }
        }
        else
        {
            // データベースのタイプがnullの場合、何もしない
            return;
        }
        
        // もしDbTypeがSQLiteではないならば、Username, Password, IP, Port入力欄の内容を更新する
        if (databaseType == "SQLite") return;
        
        // 更新 TextBox:Username の内容
        if (databaseUsername != null)
        {
            var textBox = this.FindControl<TextBox>("Username");
            textBox!.Text = databaseUsername;
        }
        
        // 更新 TextBox:Password の内容
        if (databasePassword != null)
        {
            var textBox = this.FindControl<TextBox>("Password");
            textBox!.Text = databasePassword;
            
            // パスワードを星号に変換する
            textBox!.Text = new string('*', databasePassword.Length);
        }
        
        // 更新 TextBox:TcpServer の内容
        if (databaseHostIp != null)
        {
            var textBox = this.FindControl<TextBox>("TcpServer");
            textBox!.Text = databaseHostIp;
        }
        
        // 更新 TextBox:TcpPort の内容
        if (databaseHostPort > 0)
        {
            var textBox = this.FindControl<TextBox>("TcpPort");
            textBox!.Text = databaseHostPort.ToString();
        }
    }

    /// <summary>
    /// SearchPathBtnがクリックされた時、ファイル選択ダイアログを表示する
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private async void OnSearchPathBtnClicked(object? sender, RoutedEventArgs e)
    {
        // 获取顶层窗口
        var topLevel = TopLevel.GetTopLevel(this);
        
        // 使用 Avalonia 的文件选择器
        if (topLevel == null) return;
        var files = await topLevel.StorageProvider.OpenFilePickerAsync(new FilePickerOpenOptions()
        {
            Title = "Select a database file",
            AllowMultiple = false,
        });
    
        // 如果没有选择文件，返回
        if (files.Count < 1) return;
        
        // 获取文件路径
        var path = files[0].Path;
        if (ValidateAndUseFilePath(path.ToString()))
        {
            // 保存数据库路径
            AppConfigParamsTemp.DatabasePath = path.ToString();
            
            // 获取文件名
            var fileName = Path.GetFileName(path.ToString());
            
            // 更新 TextBox:DatabasePath 的内容  
            var textBox = this.FindControl<TextBox>("DatabasePath");
            textBox!.Text = fileName;
        }
        else
        {
            await MessageBoxWithOkCancel.ShowAsync(_parentWindow, "Please select a valid database file.");
            // // 修改 MessageText 的内容
            // var messageBox = new MessageBox();
            // messageBox.FindControl<TextBlock>("MessageText")!.Text = "Please select a valid database file.";
            // await messageBox.ShowDialog(_parentWindow);
        }
    }
    
    /// <summary>
    /// Pathは有効なデータベースファイルパスかどうかを確認する
    /// </summary>
    /// <param name="path"></param>
    /// <returns></returns>
    private bool ValidateAndUseFilePath(string path)
    {
        // 确认文件扩展名
        var validExtensions = new HashSet<string> { ".db", ".sqlite", ".sqlite3" };
        return validExtensions.Contains(Path.GetExtension(path).ToLowerInvariant());
    }

    /// <summary>
    /// 
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private async void OnDBSelectorChanged(object? sender, SelectionChangedEventArgs e)
    {
        var databaseType = this.FindControl<ComboBox>("DatabaseType");
        if (databaseType.SelectedItem is ComboBoxItem selectedDbItem)
        {
            // 获取选中项的内容
            var selectedDbType = selectedDbItem.Content?.ToString();

            // 保存数据库类型
            AppConfigParamsTemp.DatabaseType = selectedDbType;

            // 如果数据库类型是 SQLite，那么Username，Password，IP，Port输入框不可用
            var usernameTextBox = this.FindControl<TextBox>("Username");
            var passwordTextBox = this.FindControl<TextBox>("Password");
            var tcpServerTextBox = this.FindControl<TextBox>("TcpServer");
            var tcpPortTextBox = this.FindControl<TextBox>("TcpPort");

            if (selectedDbType == "SQLite")
            {
                usernameTextBox!.IsEnabled = false;
                passwordTextBox!.IsEnabled = false;
                tcpPortTextBox!.IsEnabled = false;
                tcpServerTextBox!.IsEnabled = false;
            }
            else
            {
                usernameTextBox!.IsEnabled = true;
                passwordTextBox!.IsEnabled = true;
                tcpPortTextBox!.IsEnabled = true;
                tcpServerTextBox!.IsEnabled = true;

                // Messageboxを提示して
                await MessageBoxWithOkCancel.ShowAsync(_parentWindow, "Currently, this app supports Sqlite only");
            }
        }
    }

    /// <summary>
    /// Databaseのパスが変更された時、DatabaseConfigTemp.DatabasePathを更新する
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void OnUserNameChanged(object? sender, TextChangedEventArgs e)
    {
        // 获取用户名文本框的新文本
        if (sender is not TextBox usernameTextBox) return;
        
        // 获取用户名
        var username = usernameTextBox.Text;
            
        // 保存用户名
        AppConfigParamsTemp.DatabaseUsername = username;
    }

    /// <summary>
    /// Databaseのパスワードが変更された時、DatabaseConfigTemp.DatabasePasswordを更新する
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void OnPasswordChanged(object? sender, TextChangedEventArgs e)
    {
        // 获取密码文本框的新文本
        if (sender is not TextBox passwordTextBox) return;
        
        // 获取密码
        var password = passwordTextBox.Text;
            
        // 保存密码
        AppConfigParamsTemp.DatabasePassword = password;
    }

    /// <summary>
    /// DatabaseのIPが変更された時、DatabaseConfigTemp.DatabaseHostIpを更新する
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void OnTcpServerChanged(object? sender, TextChangedEventArgs e)
    {
        // 获取 TCP 服务器文本框的新文本
        if (sender is not TextBox tcpServerTextBox) return;
        
        // 获取 TCP 服务器
        var tcpServer = tcpServerTextBox.Text;
        
        // 保存 TCP 服务器
        AppConfigParamsTemp.DatabaseHostUrl = tcpServer;
    }

    /// <summary>
    /// DatabaseのPortが変更された時、DatabaseConfigTemp.DatabaseHostPortを更新する
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
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
        AppConfigParamsTemp.DatabaseHostPort = port;
    }
    
    /// <summary>
    /// 新しいデータベースファイルを作成する
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private async void OnCreateDBBtnClicked(object? sender, RoutedEventArgs e)
    {
        // 如果该按钮被点击后，首先检测当前程序目录下是否存在数据库文件
        if (File.Exists("database.db"))
        {
            // 修改 MessageText 的内容
            // var messageBox = new MessageBox();
            // messageBox.FindControl<TextBlock>("MessageText")!.Text = "The database file already exists.";
            // await messageBox.ShowDialog(_parentWindow);
            await MessageBoxWithOkOnly.ShowAsync(_parentWindow, "The database file already exists.");
            
            // 将数据库路径设置为当前程序目录下的 database.db
            AppConfigParamsTemp.DatabasePath = "database.db";
            
            // 更新 TextBox:DatabasePath 的内容
            var textBox = this.FindControl<TextBox>("DatabasePath");
            textBox!.Text = "database.db";
        }
        else
        {
            // 创建数据库文件
            //await using (var connection = new SqliteConnection("Data Source=database.db"))
            //{
            //    // 打开数据库连接
            //    connection.Open();
            //
            //    // GitHub 上 SQL 文件的原始内容链接
            //    const string sqlUrl = "https://raw.githubusercontent.com/seagochen/CSharpFinTech/master/1_DataCenter/sqlite/create_db.sql";
            //
            //    // 下载 SQL 文件
            //    var sql = await DownloadSqlScript(sqlUrl);
            //
            //    // 执行 SQL 命令
            //    await using var command = new SqliteCommand(sql, connection);
            //    command.ExecuteNonQuery();
            //}

            // Perform SQL scripts
            ExecuteSQLScript("https://raw.githubusercontent.com/seagochen/CSharpFinTech/master/1_Database/create_db.sql");
            ExecuteSQLScript("https://raw.githubusercontent.com/seagochen/CSharpFinTech/master/1_Database/create_account_view.sql");
            ExecuteSQLScript("https://raw.githubusercontent.com/seagochen/CSharpFinTech/master/1_Database/trigger_record_logs.sql");

            // 持续检测数据库文件是否存在，当存在后，跳出循环
            while (!File.Exists("database.db"))
            {
                // 等待 500 毫秒
                await Task.Delay(500);
            }
            
            // 弹出消息框，告知用户数据库文件已经创建
            // var messageBox = new MessageBox();
            // messageBox.FindControl<TextBlock>("MessageText")!.Text = "The database file has been created.";
            // await messageBox.ShowDialog(_parentWindow);
            await MessageBoxWithOkCancel.ShowAsync(_parentWindow, "The database file has been created.");
            
            // 将数据库路径设置为当前程序目录下的 database.db
            AppConfigParamsTemp.DatabasePath = "database.db";
            
            // 更新 TextBox:DatabasePath 的内容
            var textBox = this.FindControl<TextBox>("DatabasePath");
            textBox!.Text = "database.db";
        }
    }

    /// <summary>
    /// 
    /// </summary>
    /// <param name="sqlUrl"></param>
    private async void ExecuteSQLScript(string sqlUrl)
    {
        // 创建数据库文件
        await using (var connection = new SqliteConnection("Data Source=database.db"))
        {
            // 打开数据库连接
            connection.Open();

            // GitHub 上 SQL 文件的原始内容链接
            //const string sqlUrl = "https://raw.githubusercontent.com/seagochen/CSharpFinTech/master/1_DataCenter/sqlite/create_db.sql";

            // 下载 SQL 文件
            var sql = await DownloadSqlScript(sqlUrl);

            // 执行 SQL 命令
            await using var command = new SqliteCommand(sql, connection);
            command.ExecuteNonQuery();
        }
    }
    
    /// <summary>
    /// GithubからSQLファイルをダウンロードする
    /// </summary>
    /// <param name="url"></param>
    /// <returns></returns>
    static async Task<string> DownloadSqlScript(string url)
    {
        using var client = new HttpClient();
        return await client.GetStringAsync(url);
    }
}