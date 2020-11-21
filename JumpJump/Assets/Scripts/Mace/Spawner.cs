using System.Collections;
using UnityEngine;

namespace Mace
{
    public class Spawner : MonoBehaviour
    {
        public GameObject macePrefab;
        public Vector3 spawnOffset;
        public float repeatTime = 2f;
        public float maxFrequency;

        private void Start()
        {
            StartCoroutine(Spawn());
            InvokeRepeating(nameof(BoostTime), 0, 5f);
        }


        private void BoostTime()
        {
            if (repeatTime < maxFrequency)
            {
                repeatTime = 0.08f;
            }
            else
            {
                repeatTime -= 0.1f;
            }
        }

        private IEnumerator Spawn()
        {
            while (true)
            {
                spawnOffset = new Vector3(Random.Range(-55f, 60f), 0, 0);
                Instantiate(macePrefab, transform.position + spawnOffset, transform.rotation);
                yield return new WaitForSeconds(repeatTime);
            }
        }
    }
}
