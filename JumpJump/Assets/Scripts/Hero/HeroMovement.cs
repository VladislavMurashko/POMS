using UnityEngine;

namespace Hero
{
    public class HeroMovement : MonoBehaviour
    {
        public Animator animator;
        public Vector3 rayCastOffset;
        public bool isOnGround;
        private bool _isFacingRight = true;
        private const float DownGravityScale = 200;
        public Rigidbody2D rb;
        public LayerMask groundLayer;
        private float _directionX;
        [SerializeField] private float speed;
        [SerializeField] private float jumpForceScale;
        [SerializeField] private float rayCastLength;
        [SerializeField] private float gravityScale;
        private static readonly int HorizontalSpeed = Animator.StringToHash("HorizontalSpeed");
        private static readonly int VerticalSpeed = Animator.StringToHash("VerticalSpeed");
        private static readonly int IsOnGround = Animator.StringToHash("IsOnGround");

        private void Update()
        {
            if (_isFacingRight && _directionX < 0 || !_isFacingRight && _directionX > 0)
            {
                RotateHero();
            }

            animator.SetFloat(HorizontalSpeed, Mathf.Abs(rb.velocity.x));
            animator.SetFloat(VerticalSpeed, rb.velocity.y);
            animator.SetBool(IsOnGround, isOnGround);

            if (isOnGround && Input.GetKeyDown(KeyCode.Space))
            {
                rb.AddForce(Vector2.up * jumpForceScale, ForceMode2D.Impulse);
            }

            if (!isOnGround && Input.GetKeyDown(KeyCode.S))
            {
                rb.AddForce(Vector2.down * DownGravityScale, ForceMode2D.Impulse);
            }

            if (Mathf.Abs(rb.velocity.x) >= 30)
            {
                rb.velocity = new Vector2(Mathf.Sign(rb.velocity.x) * speed, rb.velocity.y);
            }
        }

        private void RotateHero()
        {
            _isFacingRight = !_isFacingRight;
            transform.rotation = Quaternion.Euler(0, _isFacingRight ? 0 : 180, 0);
        }

        private void FixedUpdate()
        {
            SetFastDownward();

            _directionX = Input.GetAxis("Horizontal");
            
            if (isOnGround)
            {
                rb.velocity = new Vector2(_directionX * speed, rb.velocity.y);
            }
            else
            {
                rb.AddForce(Vector2.right * (_directionX * 60f));
            }

            var position = transform.position;
            isOnGround = CheckGround(position + rayCastOffset) ||
                         CheckGround(position - rayCastOffset);
        }

        private void SetFastDownward()
        {
            var isFalling = rb.velocity.y < 0 && !isOnGround && !Input.GetKey(KeyCode.S);

            if (isFalling)
            {
                rb.gravityScale = gravityScale * 4f;
            }
            else
            {
                rb.gravityScale = gravityScale;
            }
        }

        private bool CheckGround(Vector3 positionOffset)
        {
            return Physics2D.Raycast(positionOffset, Vector2.down, rayCastLength, groundLayer);
        }

        private void OnDrawGizmos()
        {
            var positionPlus = transform.position + rayCastOffset;
            var positionMinus = transform.position - rayCastOffset;
            Gizmos.color = Color.red;
            Gizmos.DrawLine(positionPlus, positionPlus + Vector3.down * rayCastLength);
            Gizmos.DrawLine(positionMinus, positionMinus + Vector3.down * rayCastLength);
        }
    }
}
