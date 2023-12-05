using System.Threading.Tasks;
using Avalonia.Controls;
using Avalonia.Interactivity;

namespace csharp_app.ViewController.Components;

public partial class MessageBoxWithOkCancel : Window
{
    private MessageBoxResult Result { get; set; }
    
    public MessageBoxWithOkCancel()
    {
        InitializeComponent();
    }

    private MessageBoxWithOkCancel(string message) : this()
    {
        InitializeComponent();
        this.FindControl<TextBlock>("MessageText")!.Text = message;
    }
    
    public static MessageBoxResult Show(Window parentWindow, string message)
    {
        var messageBox = new MessageBoxWithOkCancel(message);
        messageBox.ShowDialog(parentWindow);
        return messageBox.Result;
    }
    
    public static async Task<MessageBoxResult> ShowAsync(Window parentWindow, string message)
    {
        var messageBox = new MessageBoxWithOkCancel(message);
        await messageBox.ShowDialog(parentWindow);
        return messageBox.Result;
    }
    
    private void OnButtonOkClick(object sender, RoutedEventArgs e)
    {
        Result = MessageBoxResult.Ok;
        Close();
    }

    private void OnButtonCancelClick(object sender, RoutedEventArgs e)
    {
        Result = MessageBoxResult.Cancel;
        Close();
    }
}