using System.Collections.ObjectModel;
using System.Threading.Tasks;
using Avalonia.Controls;
using Avalonia.Threading;
using csharp_app.Models;
using csharp_app.Models.Entities;

namespace csharp_app.ViewController.MainWindowUserControl;

public partial class MainWindowUserControlAccountsManagmentTab : UserControl
{
    public class AccountData
    {
        public string AccountId { get; set; }
        public string ServiceProvider { get; set; }
        public string AccountName { get; set; }
        public string AccountType { get; set; }
        public string AccountPassword { get; set; }
        public string Restriction { get; set; }
        public string Note { get; set; }
    }

    public ObservableCollection<AccountData> Accounts { get; set; } = new();
    private SqliteCrudService sqliteCrudService = null;
    private readonly Window _parentWindow;

    public MainWindowUserControlAccountsManagmentTab(Window　parentWindow)
    {
        InitializeComponent();
        
        _parentWindow = parentWindow;
        DataContext = this;
        
        // この UserControl はプログラムによって読み込まれる最初のページであるため、この遅延が追加されないとエラーが発生します。
        Task.Run(async () => await Task.Delay(2000)).ContinueWith(_ => RetrieveConfigurationFromSession());
    }

    private void RetrieveConfigurationFromSession()
    {
        var dbPath = AppSession.Instance.AppConfigParams.DatabasePath;
        var dbType = AppSession.Instance.AppConfigParams.DatabaseType;

        if (dbType == "SQLite")
        {
            // SQLiteを使用してデータを読み込む
            var dbPathConfig = "Data Source=" + dbPath;
            sqliteCrudService = new SqliteCrudService(dbPathConfig);

            // まず、AccountsTabのデータをクリアする
            ScheduledRefressData();
        }
    }

    private void ScheduledRefressData()
    {
        Dispatcher.UIThread.InvokeAsync(() =>
        {
            // AccountsTabのデータをクリアする
            Accounts.Clear();
            
            // データベースからデータを読み込む
            var accountEntities = sqliteCrudService.GetAll<AccountViewEntity>();
            foreach (var accountEntity in accountEntities)
            {
                Accounts.Add(new AccountData
                {
                    AccountId = accountEntity.AccountId,
                    ServiceProvider = accountEntity.ServiceProvider,
                    AccountName = accountEntity.AccountName,
                    AccountType = accountEntity.AccountType,
                    AccountPassword = accountEntity.AccountPassword,
                    Restriction = accountEntity.Restriction,
                    Note = accountEntity.Note
                });           
            }
        });
    }
}
