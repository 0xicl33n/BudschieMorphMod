package de.budschie.bmorph.morph.functionality;

import java.util.HashSet;
import java.util.UUID;
import java.util.function.BiConsumer;

import de.budschie.bmorph.capabilities.IMorphCapability;
import de.budschie.bmorph.capabilities.MorphCapabilityAttacher;
import de.budschie.bmorph.main.ServerSetup;
import de.budschie.bmorph.morph.MorphItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

public class PassiveTickAbility extends AbstractEventAbility 
{
	private int updateDuration = 0;
	private int lastUpdate = 0;
	private BiConsumer<PlayerEntity, IMorphCapability> handleUpdate;
	
	public PassiveTickAbility(int updateDuration, BiConsumer<PlayerEntity, IMorphCapability> handleUpdate)
	{
		this.updateDuration = updateDuration;
		this.handleUpdate = handleUpdate;
	}
	
	@SubscribeEvent
	public void onServerTick(ServerTickEvent event)
	{
		int tickCounter = ServerSetup.server.getTickCounter();
		
		if((lastUpdate + updateDuration) >= tickCounter)
		{
			lastUpdate = tickCounter;
			
			for(UUID uuid : trackedPlayers)
			{
				PlayerEntity player = ServerSetup.server.getPlayerList().getPlayerByUUID(uuid);
				
				if(player != null)
				{
					LazyOptional<IMorphCapability> cap = player.getCapability(MorphCapabilityAttacher.MORPH_CAP);
					
					if(cap.isPresent())
					{
						IMorphCapability resolved = cap.resolve().get();
						handleUpdate.accept(player, resolved);
					}
				}
			}
		}
	}

	@Override
	public void onUsedAbility(PlayerEntity player, MorphItem currentMorph)
	{
		
	}
}