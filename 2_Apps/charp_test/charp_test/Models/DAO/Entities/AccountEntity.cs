namespace charp_test.Models.DAO.Entities;

public class AccountEntity : IEntity
{
    public string TableName => "Accounts";
    public string PrimaryKey => "AccountId";
    
    public string AccountId { get; set; }
    public string ServiceProviderId { get; set; }
    public string AccountName { get; set; }
    public string AccountTypeId { get; set; }
    public string AccountPassword { get; set; }
    public string RestrictionId { get; set; }
    public string Note { get; set; }
}