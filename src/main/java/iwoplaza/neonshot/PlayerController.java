package iwoplaza.neonshot;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.Window;
import iwoplaza.neonshot.world.entity.PlayerEntity;

public class PlayerController
{
    private final int upKey;
    private final int rightKey;
    private final int downKey;
    private final int leftKey;
    private final int shootKey;

    public PlayerController(int upKey, int rightKey, int downKey, int leftKey, int shootKey)
    {
        this.upKey = upKey;
        this.rightKey = rightKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.shootKey = shootKey;
    }

    public void handleControls(PlayerEntity player, Window window)
    {
        if (window.isKeyPressed(this.shootKey))
        {
            player.shoot();
        }

        if (window.isKeyPressed(this.leftKey))
        {
            player.setMoveDirection(Direction.WEST);
        }
        else if (window.isKeyPressed(this.rightKey))
        {
            player.setMoveDirection(Direction.EAST);
        }
        else if (window.isKeyPressed(this.upKey))
        {
            player.setMoveDirection(Direction.NORTH);
        }
        else if (window.isKeyPressed(this.downKey))
        {
            player.setMoveDirection(Direction.SOUTH);
        }
        else
        {
            player.setMoveDirection(null);
        }
    }
}
