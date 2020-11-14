using System.Collections;
using UnityEngine;

public class Spawner : MonoBehaviour
{
    public GameObject MacePrefab;
    public Vector3 spawnOffset;
    public float repeatTime = 2f;
    public float maxFrequency;

    void Start()
    {
        StartCoroutine(Spawn());
        InvokeRepeating("BoostTime", 0, 5f);
    }


    void BoostTime()
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
            Instantiate(MacePrefab, transform.position + spawnOffset, transform.rotation);
            yield return new WaitForSeconds(repeatTime);
        }
    }
}
