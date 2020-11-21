using UnityEngine;
using UnityEngine.SceneManagement;

namespace UI
{
    public class RestartButtonController : MonoBehaviour
    {
        public void RestartGame()
        {
            UIController.UnfreezeGame();
            SceneManager.LoadScene(SceneManager.GetActiveScene().name);
        }
    }
}
