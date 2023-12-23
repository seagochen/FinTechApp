namespace csharp_app.Models.Entities;

public interface IEntity
{
    string TableName { get; }
    string PrimaryKey { get; }
}