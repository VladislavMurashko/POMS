using UnityEngine;
using UnityEngine.UI;

namespace UI
{
    public class ScoreController : MonoBehaviour
    {
        public Text scoreText;
        public int scoreValue;

        private void OnTriggerEnter2D(Collider2D other)
        {
            if (!other.gameObject.CompareTag("Bomb")) return;

            scoreValue += 100;
            scoreText.text = $"{scoreValue}";
        }
    }
}
