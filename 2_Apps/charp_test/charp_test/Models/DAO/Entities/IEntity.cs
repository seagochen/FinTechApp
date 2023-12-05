namespace charp_test.Models.DAO.Entities;

public interface IEntity
{
    string TableName { get; }
    string PrimaryKey { get; }
}