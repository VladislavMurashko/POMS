using UnityEngine;
using UnityEngine.UI;

namespace UI
{
    public class UIController : MonoBehaviour
    {
        private static Button _restartButton;

        private static void InitUI()
        {
            if (_restartButton == null)
            {
                _restartButton = GameObject.Find("RestartButton").GetComponent<Button>();
            }
        }

        public static void FreezeGame()
        {
            Time.timeScale = 0;
        }

        public static void UnfreezeGame()
        {
            Time.timeScale = 1;
        }

        public static void HideRestartButton()
        {
            InitUI();

            _restartButton.gameObject.SetActive(false);
        }

        public static void ShowRestartButton()
        {
            _restartButton.gameObject.SetActive(true);
        }
    }
}
