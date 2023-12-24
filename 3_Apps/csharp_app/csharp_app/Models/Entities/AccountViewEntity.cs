namespace csharp_app.Models.Entities;

public class AccountViewEntity : IEntity
{
    public string TableName => "AccountsView";
    public string PrimaryKey => "AccountId";
    
    public string AccountId { get; set; }
    public string ServiceProvider { get; set; }
    public string AccountName { get; set; }
    public string AccountType { get; set; }
    public string AccountPassword { get; set; }
    public string Restriction { get; set; }
    public string Note { get; set; }
}