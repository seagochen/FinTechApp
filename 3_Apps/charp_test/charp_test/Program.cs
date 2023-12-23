// See https://aka.ms/new-console-template for more information

using charp_test.Models;
using charp_test.Models.DAO.Entities;

public class Program
{
    private static string connectionString = "Data Source=file:///Users/orlando/Library/CloudStorage/OneDrive-Personal/data/database.db";
    
    public static void Main(string[] args)
    {
        var sqliteCrudService = new SqliteCrudService(connectionString);
        
        // // Insert a new item into Accounts
        // sqliteCrudService.Insert(new AccountEntity
        // {
        //     AccountId = "202211051622590014",
        //     ServiceProviderId = "1",
        //     AccountName = "Test",
        //     AccountTypeId = "1",
        //     AccountPassword = "Test Password",
        //     RestrictionId = "1",
        //     Note = "Test Note"
        // });
        //
        // // Update item 202211051622590014 in Accounts
        // sqliteCrudService.Update(new AccountEntity
        // {
        //     AccountId = "202211051622590014",
        //     ServiceProviderId = "1",
        //     AccountName = "Test",
        //     AccountTypeId = "1",
        //     AccountPassword = "Test Password",
        //     RestrictionId = "1",
        //     Note = "Test Note Updated"
        // });
        
        // Pick item 202211051622590014 from Accounts
        // var testItem = sqliteCrudService.Get("202211051622590014", new AccountEntity());
        
        // Print AccountId, AccountName, Note
        // Console.WriteLine($"{testItem.AccountId} {testItem.AccountName} {testItem.Note}");
        // Console.WriteLine();
        
        // Delete item 202211051622590014 from Accounts
        // sqliteCrudService.Delete("202211051622590014", new AccountEntity());
        
        // Get all items from Accounts
        var accountEntities = sqliteCrudService.GetAll<AccountViewEntity>();
        
        foreach (var accountEntity in accountEntities)
        {
            // Print AccountId, AccountName, Note
            Console.WriteLine($"{accountEntity.AccountId} {accountEntity.AccountName} {accountEntity.Note}");            
        }
    }
}
