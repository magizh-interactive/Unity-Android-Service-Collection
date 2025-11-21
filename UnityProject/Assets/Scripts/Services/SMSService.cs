using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class SMSListener : MonoBehaviour
{

    public TMP_Text text;
    public void OnSmsReceived(string message)
    {
        Debug.Log("SMS Received in Unity: " + message);
        text.text = message;
    }
}
