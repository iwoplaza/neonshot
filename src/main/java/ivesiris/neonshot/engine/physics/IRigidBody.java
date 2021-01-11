package ivesiris.neonshot.engine.physics;

import org.joml.Vector2f;

public interface IRigidBody
{

    Vector2f getPrevPosition();
    Vector2f getNextPosition();
    Vector2f getVelocity();
    void collideWith(WorldCollider collider);

}
