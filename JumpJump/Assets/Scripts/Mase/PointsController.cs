using UnityEngine;
using UnityEngine.UI;

public class PointsController : MonoBehaviour
{
    public Text pointsCounter;
    public int pointsCount;

    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.gameObject.CompareTag("Bomb"))
        {
            pointsCount += 100;
            pointsCounter.text = $"{pointsCount}";
        }
    }
}
