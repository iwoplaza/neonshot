package iwoplaza.neonshot.graphics.entity;

import iwoplaza.meatengine.graphics.Color;
import iwoplaza.meatengine.graphics.IGameRenderContext;
import iwoplaza.meatengine.graphics.sprite.Sprite;
import iwoplaza.neonshot.world.entity.LivingEntity;

public abstract class LivingRenderer<E extends LivingEntity> extends SlidingEntityRenderer<E>
{
    public boolean isVisible(IGameRenderContext context, E entity)
    {
        int invincibilityFrames = entity.getInvincibilityFrames();
        if (invincibilityFrames > 0)
        {
            return (invincibilityFrames/2) % 2 == 0;
        }

        return true;
    }

    public void applyDamageOverlayColor(IGameRenderContext context, E entity, Sprite sprite)
    {
        int invincibilityFrames = entity.getInvincibilityFrames();
        float intensity = (float) invincibilityFrames / entity.getInvincibilityDuration();

        sprite.setOverlayColor(new Color(1, 0, 0, intensity));
    }
}
