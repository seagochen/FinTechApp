namespace csharp_app;

public class AppConfigParams
{
    public string? DatabasePath { get; set; }
    public string? DatabaseType { get; set; }
    public string? DatabaseUsername { get; set; }
    public string? DatabasePassword { get; set; }
    public string? DatabaseHostUrl { get; set; }
    public int DatabaseHostPort { get; set; }
    
    public int TwsSocketPort { get; set; }
    public string? TwsSocketUrl { get; set; }
}