namespace csharp_app;

public class AppSession
{
    public static AppSession Instance { get; } = new();

    public AppConfigParams AppConfigParams { get; set; }
    
    public AppConfigParams AppConfigParamsTemp { get; set; }
    
    public string? AppConfigPath { get; set; }
    
    private AppSession()
    {
        AppConfigParams = new AppConfigParams();
        AppConfigParamsTemp = new AppConfigParams();
        
        AppConfigPath = "config.json";
    }
}
