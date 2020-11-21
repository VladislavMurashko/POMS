using UI;
using UnityEngine;

namespace Mace
{
    public class MaceController : MonoBehaviour
    {
        private void FixedUpdate()
        {
            transform.Rotate(0, 0, 5f, Space.World);
        }

        private void OnTriggerEnter2D(Collider2D other)
        {
            if (other.gameObject.CompareTag("Player"))
            {
                UIController.FreezeGame();
                UIController.ShowRestartButton();
            }

            if (other.gameObject.CompareTag("Ground"))
            {
                Destroy(gameObject);
            }
        }
    }
}
