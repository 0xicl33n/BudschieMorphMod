package de.budschie.bmorph.morph;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.budschie.bmorph.events.Events;
import de.budschie.bmorph.main.ServerSetup;
import de.budschie.bmorph.morph.functionality.Ability;
import de.budschie.bmorph.util.BudschieUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class MorphItem
{
	protected final Logger LOGGER = LogManager.getLogger();
	
	private String morphItemId;
	
	// We will control this vars through a new ability
	private int stunnedUntil = -1;
	private int totalStunTime = 1;
	
	private Optional<ResourceLocation> customAbilityList = Optional.empty();
	private Optional<EntityType<?>> abilityListFromEntity = Optional.empty();
	
	protected MorphItem(String morphItemId)
	{
		this.morphItemId = morphItemId;
	}
	
	public void setCustomAbilityList(ResourceLocation customAbilityList)
	{
		this.customAbilityList = Optional.of(customAbilityList);
	}
	
	public void setAbilityListFromEntity(EntityType<?> abilityListFromEntity)
	{
		this.abilityListFromEntity = Optional.of(abilityListFromEntity);
	}
	
	public CompoundTag serialize()
	{
		CompoundTag nbt = new CompoundTag();
		nbt.putString("id", getMorphItemId());
		nbt.put("additional", serializeAdditional());
		
		// If we are stunned, save all of this stun stuff
		if(isStunned())
		{
			nbt.putInt("stunned_until", BudschieUtils.convertToRelativeTime(stunnedUntil));
			nbt.putInt("total_stun_time", totalStunTime);
		}
		
		if(customAbilityList.isPresent())
		{
			nbt.putString("custom_ability_list", customAbilityList.get().toString());
		}
		else if(abilityListFromEntity.isPresent())
		{
			nbt.putString("ability_list_from_entity", abilityListFromEntity.get().getRegistryName().toString());
		}
		
		return nbt;
	}
	
	public void deserialize(CompoundTag nbt)
	{
		if(!nbt.getString("id").equals(getMorphItemId()))
			throw new IllegalArgumentException("The wrong morph item is being serialized. Please report this bug to the developer.");
		else
		{
			deserializeAdditional(nbt.getCompound("additional"));
			
			if(nbt.contains("stunned_until", Tag.TAG_INT))
			{
				stunnedUntil = BudschieUtils.convertToAbsoluteTime(nbt.getInt("stunned_until"));
				totalStunTime = nbt.getInt("total_stun_time");
			}
			
			if(nbt.contains("custom_ability_list", Tag.TAG_STRING))
			{
				setCustomAbilityList(new ResourceLocation(nbt.getString("custom_ability_list")));
			}
			else if(nbt.contains("ability_list_from_entity", Tag.TAG_STRING))
			{
				setAbilityListFromEntity(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(nbt.getString("ability_list_from_entity"))));
			}
		}
	}
	
	public List<Ability> getAbilities()
	{
		if(customAbilityList.isPresent())
		{
			List<Ability> abilities = Events.MORPH_ABILITY_MANAGER.getAbilitiesForCustomAbilityList(customAbilityList.get());
			
			if(abilities == null)
			{
				LOGGER.warn(MessageFormat.format("The custom ability list {0} for the entity type {1} does not exist. Defaulting to an empty list of abilities.", customAbilityList.get().toString(), getEntityType().getRegistryName().toString()));
				return Arrays.asList();
			}
			
			return abilities;
		}
		
		EntityType<?> toGetAbilitiesFrom = abilityListFromEntity.isPresent() ? abilityListFromEntity.get() : getEntityType();
		
		List<Ability> defaultAbilities = Events.MORPH_ABILITY_MANAGER.getAbilitiesForEntity(toGetAbilitiesFrom);
		
		return defaultAbilities == null ? Arrays.asList() : defaultAbilities;
	}
	
	protected Optional<ResourceLocation> getCustomAbilityList()
	{
		return customAbilityList;
	}
	
	public boolean isStunned()
	{
		return ServerSetup.server.getTickCount() < stunnedUntil;
	}
	
	public float getStunProgress()
	{
		return 1f - (BudschieUtils.convertToRelativeTime(stunnedUntil) / ((float)totalStunTime));
	}
	
	public abstract void deserializeAdditional(CompoundTag nbt);
	public abstract CompoundTag serializeAdditional();
	
	public abstract EntityType<?> getEntityType();
	public abstract Entity createEntity(Level world) throws NullPointerException;
	
	public boolean isAllowedToPickUp(Player picker)
	{
		return true;
	}
	
	public String getMorphItemId()
	{
		return morphItemId;
	}
}
