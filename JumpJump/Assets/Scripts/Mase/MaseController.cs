using UnityEngine;
using UnityEngine.SceneManagement;

namespace Mase
{
    public class MaseController : MonoBehaviour
    {

        void FixedUpdate()
        {
            transform.Rotate(0, 0, 5f, Space.World);
        }

        private void OnTriggerEnter2D(Collider2D other)
        {
            if (other.gameObject.CompareTag("Player"))
            {
                // SceneManager.LoadScene("Main");
                Time.timeScale = 0;
            }

            if (other.gameObject.CompareTag("Ground"))
            {
                Destroy(gameObject);
            }
        }
    }
}
