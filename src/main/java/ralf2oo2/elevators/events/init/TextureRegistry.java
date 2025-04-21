package ralf2oo2.elevators.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.elevators.Elevators;

public class TextureRegistry {
    public static Atlas.Sprite directionalArrowTexture;
    @EventListener
    public void registerTextures(TextureRegisterEvent event){
        directionalArrowTexture = Atlases.getTerrain().addTexture(Identifier.of(Elevators.NAMESPACE, "block/directional_arrow"));
    }
}
