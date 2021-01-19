package iwoplaza.neonshot.loader;

import iwoplaza.meatengine.Direction;

import java.util.List;

public class LevelContentData
{
    public List<ChallengeRoomData> challengeRooms;

    public static class ChallengeRoomData
    {
        public int entranceX;
        public int entranceY;
        public Direction entranceDirection;
        public int exitX;
        public int exitY;
        public Direction exitDirection;
        public List<Entry> entries;

        public static class Entry
        {
            public int x;
            public int y;
            public String entityKey;
        }
    }
}
