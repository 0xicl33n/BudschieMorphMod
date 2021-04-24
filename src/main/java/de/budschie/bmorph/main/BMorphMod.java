package de.budschie.bmorph.main;

import de.budschie.bmorph.capabilities.MorphCapabilityAttacher;
import de.budschie.bmorph.entity.EntityRegistry;
import de.budschie.bmorph.morph.FallbackMorphItem;
import de.budschie.bmorph.morph.MorphHandler;
import de.budschie.bmorph.morph.MorphManagerHandlers;
import de.budschie.bmorph.morph.PlayerMorphItem;
import de.budschie.bmorph.morph.VanillaFallbackMorphData;
import de.budschie.bmorph.morph.functionality.Ability;
import de.budschie.bmorph.morph.functionality.AbilityLookupTableHandler;
import de.budschie.bmorph.morph.functionality.AbilityRegistry;
import de.budschie.bmorph.network.MainNetworkChannel;
import net.minecraft.entity.EntityType;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanValue;
import net.minecraft.world.GameRules.Category;
import net.minecraft.world.GameRules.RuleKey;
import net.minecraft.world.GameRules.RuleType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = References.MODID)
@EventBusSubscriber(bus = Bus.MOD)
public class BMorphMod
{
	public static RuleKey<BooleanValue> KEEP_MORPH_INVENTORY;
	
	public BMorphMod()
	{
		EntityRegistry.ENTITY_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
		EntityRegistry.SERIALIZER_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
		AbilityRegistry.ABILITY_REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	@SubscribeEvent
	public static void onCommonSetup(final FMLCommonSetupEvent event)
	{
		MorphCapabilityAttacher.register();
		System.out.println("Registered capabilities.");
		
		KEEP_MORPH_INVENTORY = GameRules.register("keepMorphInventory", Category.PLAYER, BooleanValue.create(true));
		
		MainNetworkChannel.registerMainNetworkChannels();
		
		MorphHandler.addMorphItem("player_morph_item", () -> new PlayerMorphItem());
		MorphHandler.addMorphItem("fallback_morph_item", () -> new FallbackMorphItem());
		
		MorphManagerHandlers.registerDefaultManagers();
		
		VanillaFallbackMorphData.intialiseFallbackData();
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.BAT, AbilityRegistry.FLY_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.BAT, AbilityRegistry.NIGHT_VISION_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.BLAZE, AbilityRegistry.FLY_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.BLAZE, AbilityRegistry.FIRE_SHOOTING_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.BLAZE, AbilityRegistry.NO_FIRE_DAMAGE_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.GHAST, AbilityRegistry.FLY_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.GHAST, AbilityRegistry.GHAST_SHOOTING_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.GHAST, AbilityRegistry.NO_FIRE_DAMAGE_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.CREEPER, AbilityRegistry.BOOM.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.IRON_GOLEM, AbilityRegistry.MOB_ATTACK_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.IRON_GOLEM, AbilityRegistry.YEET_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.IRON_GOLEM, AbilityRegistry.SLOWNESS_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.IRON_GOLEM, AbilityRegistry.NO_KNOCKBACK_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.VILLAGER, AbilityRegistry.MOB_ATTACK_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.PLAYER, AbilityRegistry.MOB_ATTACK_ABILITY.get());		
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.CHICKEN, AbilityRegistry.SLOWFALL_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.LLAMA, AbilityRegistry.LLAMA_SPIT_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.HORSE, AbilityRegistry.INSTAJUMP_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.HORSE, AbilityRegistry.SWIFTNESS_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.HORSE, AbilityRegistry.JUMPBOOST_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.SPIDER, AbilityRegistry.CLIMBING_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.SPIDER, AbilityRegistry.NO_FALL_DAMAGE_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.SPIDER, AbilityRegistry.SWIFTNESS_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.OCELOT, AbilityRegistry.EXTREME_SWIFTNESS_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.OCELOT, AbilityRegistry.INSTAJUMP_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.OCELOT, AbilityRegistry.NIGHT_VISION_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.OCELOT, AbilityRegistry.EAT_REGEN_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.OCELOT, AbilityRegistry.NO_FALL_DAMAGE_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.RABBIT, AbilityRegistry.NO_FALL_DAMAGE_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.RABBIT, AbilityRegistry.SWIFTNESS_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.RABBIT, AbilityRegistry.JUMPBOOST_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.CAT, AbilityRegistry.SWIFTNESS_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.CAT, AbilityRegistry.NIGHT_VISION_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.CAT, AbilityRegistry.EAT_REGEN_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.WOLF, AbilityRegistry.EAT_REGEN_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.WITHER_SKELETON, AbilityRegistry.WITHER_ON_HIT_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.PARROT, AbilityRegistry.FLY_ABILITY.get());
		
		AbilityLookupTableHandler.addAbilityFor(EntityType.PAINTING, AbilityRegistry.FLY_ABILITY.get());
		AbilityLookupTableHandler.addAbilityFor(EntityType.PAINTING, AbilityRegistry.EXTREME_SWIFTNESS_ABILITY.get());
	}	
}
