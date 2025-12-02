using TMPro;
using UnityEngine;

public class LocationListener : MonoBehaviour
{
    public TMP_Text locationText;
    
    public void GetLocation()
    {
        using (AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
        {
            AndroidJavaObject activity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
            AndroidJavaClass locationPlugin = new AndroidJavaClass("com.mi.locationservice.LocationPlugin");
            locationPlugin.CallStatic("Initialize", activity);
            locationPlugin.CallStatic("RequestLocation");
        }
    }
    
    public void OnLocationReceived(string location)
    {
        Debug.Log("Location received from Android: " + location);
        string[] parts = location.Split(',');
        if (parts.Length == 2)
        {
            if (float.TryParse(parts[0], out float lat) && float.TryParse(parts[1], out float lon))
            {
                Debug.Log($"Lat: {lat}, Lon: {lon}");
            }
        }
    }
}
