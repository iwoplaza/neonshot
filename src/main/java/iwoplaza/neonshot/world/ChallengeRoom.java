package iwoplaza.neonshot.world;

import iwoplaza.meatengine.Direction;
import iwoplaza.meatengine.world.World;
import iwoplaza.neonshot.Tiles;
import iwoplaza.neonshot.world.entity.IDeathListener;
import iwoplaza.neonshot.world.entity.LivingEntity;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChallengeRoom implements IDeathListener
{
    private World world;
    private final Vector2ic entrance;
    private final Direction entranceDirection;
    private final Vector2ic exit;
    private final List<ChallengeRoomEntry> entries = new ArrayList<>();

    private boolean active = false;
    private Set<LivingEntity> livingChallengers = new HashSet<>();
    private boolean completed = false;

    public ChallengeRoom(Vector2ic entrance, Direction entranceDirection, Vector2ic exit)
    {
        this.entrance = entrance;
        this.entranceDirection = entranceDirection;
        this.exit = exit;
    }

    public void assignTo(World world)
    {
        this.world = world;
    }

    public void addEntry(Vector2ic position, IChallengeEntityFactory entityFactory)
    {
        this.entries.add(new ChallengeRoomEntry(position, entityFactory));
    }

    public void addEntry(ChallengeRoomEntry entry)
    {
        this.entries.add(entry);
    }

    public void activate()
    {
        if (this.active || this.completed)
            return;

        this.active = true;

        Vector2i entranceWall = new Vector2i(this.entrance);
        entranceWall.sub(this.entranceDirection.getAsVector());
        this.world.getTileMap().setTile(entranceWall.x, entranceWall.y, Tiles.VOID);

        this.world.getTileMap().setTile(exit.x(), exit.y(), Tiles.VOID);

        this.entries.forEach(e -> {
            LivingEntity entity = e.entityFactory.create(e.position);
            this.world.spawnEntity(entity);
            entity.registerDeathListener(this);
            livingChallengers.add(entity);
        });
    }

    @Override
    public void onDeath(LivingEntity entity)
    {
        this.livingChallengers.remove(entity);

        if (this.livingChallengers.isEmpty())
        {
            onCompleted();
        }
    }

    private void onCompleted()
    {
        this.active = false;
        this.completed = true;

        Vector2i entranceWall = new Vector2i(this.entrance);
        entranceWall.sub(this.entranceDirection.getAsVector());
        this.world.getTileMap().setTile(entranceWall.x, entranceWall.y, Tiles.CHESSBOARD_FLOOR);

        this.world.getTileMap().setTile(exit.x(), exit.y(), Tiles.CHESSBOARD_FLOOR);
    }

    public Vector2ic getEntrance()
    {
        return entrance;
    }

    public Direction getEntranceDirection()
    {
        return entranceDirection;
    }

    public Vector2ic getExit()
    {
        return exit;
    }

    public List<ChallengeRoomEntry> getEntries()
    {
        return entries;
    }

    public boolean isActive()
    {
        return active;
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public static class ChallengeRoomEntry
    {
        private final Vector2ic position;
        private final IChallengeEntityFactory entityFactory;

        public ChallengeRoomEntry(Vector2ic position, IChallengeEntityFactory entityFactory)
        {
            this.position = position;
            this.entityFactory = entityFactory;
        }

        public Vector2ic getPosition()
        {
            return position;
        }

        public IChallengeEntityFactory getEntityFactory()
        {
            return entityFactory;
        }
    }
}
