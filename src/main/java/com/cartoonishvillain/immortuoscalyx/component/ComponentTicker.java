package com.cartoonishvillain.immortuoscalyx.component;

import com.cartoonishvillain.immortuoscalyx.ImmortuosCalyx;
import com.cartoonishvillain.immortuoscalyx.damage.InfectionDamage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentStarter.INFECTION;

public class ComponentTicker {

    public static void tickEntity(LivingEntity livingEntity){
        if(!livingEntity.level.isClientSide){
            resistanceDownTick(livingEntity);
            InfectionTicker(livingEntity);
            InfectionTickPotionEffects(livingEntity);


        }
    }


    private static void resistanceDownTick(LivingEntity livingEntity){
        InfectionComponent h = INFECTION.get(livingEntity);
        if((!(livingEntity instanceof Player) || ValidPlayer((Player) livingEntity)))
            h.addResistance(-0.001);
            if(h.getResistance() < 1) {
                h.setResistance(1);
            }
    }


    public static boolean ValidPlayer(Player player){
        return !player.isCreative() && !player.isSpectator();
    }

    private static void InfectionTicker(LivingEntity entity){
        InfectionComponent h = INFECTION.get(entity);
            if(h.getInfectionProgress() >= 1 && (!(entity instanceof Player) || ValidPlayer((Player) entity))){
                h.addInfectionTimer(1);
                int timer = ImmortuosCalyx.config.otherDetails.INFECTIONTIMER;
                if(h.getInfectionTimer() >= timer){
                    h.addInfectionProgress(1);
                    h.addInfectionTimer(-timer);
                    if(entity instanceof Player) {PlayerMessageSender((Player) entity);}
                }

            }
    }

    private static void InfectionTickPotionEffects(LivingEntity entity){
        InfectionComponent h = INFECTION.get(entity);
        if(entity instanceof Player){
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.playerSymptomProgression.EFFECTSPEED){
                BlockPos CurrentPosition = new BlockPos(entity.getX(), entity.getY(), entity.getZ());
                float temperature = entity.level.getBiomeManager().getBiome(CurrentPosition).getTemperature(CurrentPosition);
                if(temperature > 0.9 && ImmortuosCalyx.config.playerToggles.HEATSLOW){entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 0, true, false));}
                else if(temperature < 0.275 && ImmortuosCalyx.config.playerToggles.COLDFAST){entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, 0, true, false));}
            }
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.playerSymptomProgression.EFFECTSTRENGTH){
                BlockPos CurrentPosition = new BlockPos(entity.getX(), entity.getY(), entity.getZ());
                float temperature = entity.level.getBiomeManager().getBiome(CurrentPosition).getTemperature(CurrentPosition);
                if(temperature > 0.275 && ImmortuosCalyx.config.playerToggles.WARMWEAKNESS){entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5, 0, true, false));}
                else if (temperature <= 0.275 && ImmortuosCalyx.config.playerToggles.COLDSTRENGTH) {entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5, 0, true, false));}
            }
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.playerSymptomProgression.EFFECTBLIND && ImmortuosCalyx.config.playerToggles.BLINDNESS){
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50, 1, true, false));
            }
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.playerSymptomProgression.EFFECTDAMAGE){
                Random random = new Random();
                int randomValue = random.nextInt(100);
                if(randomValue < 1 && ImmortuosCalyx.config.otherDetails.INFECTIONDAMAGE > 0){
                    entity.hurt(InfectionDamage.causeInfectionDamage(entity), ImmortuosCalyx.config.otherDetails.INFECTIONDAMAGE);
                }
            }
        } else if(entity instanceof Villager){
            if(!h.isFollower()) {
                if (h.getInfectionProgress() >= ImmortuosCalyx.config.entityToggles.VILLAGERSLOWTWO) { // greater than or equal to 25
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 2, false, false));
                } else if (h.getInfectionProgress() >= ImmortuosCalyx.config.entityToggles.VILLAGERSLOWONE) { //5-24%
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 1, false, false));
                }
                if (h.getInfectionProgress() >= ImmortuosCalyx.config.entityToggles.VILLAGERLETHAL) {
                    Random rand = new Random();
                    int random = rand.nextInt(100);
                    if (random < 1 && ImmortuosCalyx.config.otherDetails.INFECTIONDAMAGE > 0) {
                        entity.hurt(InfectionDamage.causeInfectionDamage(entity), ImmortuosCalyx.config.otherDetails.INFECTIONDAMAGE);
                    }
                }
            }
            else {
                if (h.getInfectionProgress() >= ImmortuosCalyx.config.entityToggles.VILLAGERSLOWTWO * ImmortuosCalyx.config.entityToggles.VILLAGERFOLLOWERIMMUNITY) { // greater than or equal to 25
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 2, false, false));
                } else if (h.getInfectionProgress() >= ImmortuosCalyx.config.entityToggles.VILLAGERSLOWONE * ImmortuosCalyx.config.entityToggles.VILLAGERFOLLOWERIMMUNITY) { //5-24%
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 1, false, false));
                }
                if (h.getInfectionProgress() >= ImmortuosCalyx.config.entityToggles.VILLAGERLETHAL * ImmortuosCalyx.config.entityToggles.VILLAGERFOLLOWERIMMUNITY) {
                    Random rand = new Random();
                    int random = rand.nextInt(100);
                    if (random < 1 && ImmortuosCalyx.config.otherDetails.INFECTIONDAMAGE > 0) {
                        entity.hurt(InfectionDamage.causeInfectionDamage(entity), ImmortuosCalyx.config.otherDetails.INFECTIONDAMAGE);
                    }
                }
            }
        } else if(entity instanceof IronGolem){
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.entityToggles.IRONGOLEMSLOW){ entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 1, false, false)); }
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.entityToggles.IRONGOLEMWEAK){ entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5, 1, false, false)); }
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.entityToggles.IRONGOLEMLETHAL){
                Random rand = new Random();
                int random = rand.nextInt(100);
                if(random < 1 && ImmortuosCalyx.config.otherDetails.INFECTIONDAMAGE > 0){
                    entity.hurt(InfectionDamage.causeInfectionDamage(entity), ImmortuosCalyx.config.otherDetails.INFECTIONDAMAGE);
                }
            }
        }
    }

    private static void PlayerMessageSender(Player afflictedPlayer){
        InfectionComponent h = INFECTION.get(afflictedPlayer);

        int progressionLogic = h.getInfectionProgress(); //this used to be a switch. I love switches. But switches require constants. These are not constant values anymore. Too bad.
        if(progressionLogic == ImmortuosCalyx.config.playerSymptomProgression.EFFECTMESSAGEONE){
            afflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "Your throat feels sore"), afflictedPlayer.getUUID());}

        else if(progressionLogic == ImmortuosCalyx.config.playerSymptomProgression.EFFECTMESSAGETWO){
            afflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "Your mind feels foggy"), afflictedPlayer.getUUID());}

        else if(progressionLogic == ImmortuosCalyx.config.playerSymptomProgression.EFFECTCHAT){
            if (ImmortuosCalyx.config.playerToggles.ANTICHAT)
                afflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You feel something moving around in your head, you try to yell, but nothing comes out"), afflictedPlayer.getUUID());}

        else if(progressionLogic == ImmortuosCalyx.config.playerSymptomProgression.PLAYERINFECTIONTHRESHOLD){
            if (ImmortuosCalyx.config.playerToggles.PVPCONTAGION)
                afflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "There is something on your skin and you can't get it off.."), afflictedPlayer.getUUID());}

        else if(progressionLogic == ImmortuosCalyx.config.playerSymptomProgression.EFFECTSPEED){
            if (ImmortuosCalyx.config.playerToggles.COLDFAST && ImmortuosCalyx.config.playerToggles.HEATSLOW)
                afflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You start feeling ill in warm environments, and better in cool ones.."), afflictedPlayer.getUUID());
            else if (ImmortuosCalyx.config.playerToggles.COLDFAST && !ImmortuosCalyx.config.playerToggles.HEATSLOW)
                afflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You begin to feel better in cool environments.."), afflictedPlayer.getUUID());
            else if (ImmortuosCalyx.config.playerToggles.HEATSLOW && !ImmortuosCalyx.config.playerToggles.COLDFAST)
                afflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You begin feeling ill in warm environments..."), afflictedPlayer.getUUID());}

        else if(progressionLogic == ImmortuosCalyx.config.playerSymptomProgression.EFFECTSTRENGTH){
            if (ImmortuosCalyx.config.playerToggles.COLDSTRENGTH && ImmortuosCalyx.config.playerToggles.WARMWEAKNESS)
                afflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You begin to feel weak in all but the coldest environments.."), afflictedPlayer.getUUID());
            else if (ImmortuosCalyx.config.playerToggles.COLDSTRENGTH && !ImmortuosCalyx.config.playerToggles.WARMWEAKNESS)
                afflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You begin to feel strong in cold environments.."), afflictedPlayer.getUUID());
            else if (ImmortuosCalyx.config.playerToggles.WARMWEAKNESS && !ImmortuosCalyx.config.playerToggles.COLDSTRENGTH)
                afflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You begin to feel weak in warm environments.."), afflictedPlayer.getUUID());}

        else if(progressionLogic == ImmortuosCalyx.config.playerSymptomProgression.EFFECTBLIND){
            if(ImmortuosCalyx.config.playerToggles.BLINDNESS)
                afflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "Your vision gets darker and darker.."), afflictedPlayer.getUUID());}

        else if(progressionLogic == ImmortuosCalyx.config.playerSymptomProgression.EFFECTDAMAGE){
            if(ImmortuosCalyx.config.otherDetails.INFECTIONDAMAGE > 0)
                afflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You feel an overwhelming pain in your head..."), afflictedPlayer.getUUID());}
    }
}
