package net.danh.mmoxpaddon.Compatible.MythicMobs;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.bukkit.BukkitAdapter;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.entity.Player;

public class Condition implements IEntityCondition {
    protected final String condition_type;
    protected final Integer amount;

    public Condition(MythicLineConfig config) {
        this.condition_type = config.getString(new String[]{"type", "t"});
        this.amount = config.getInteger(new String[]{"amount", "a"});
    }

    @Override
    public boolean check(AbstractEntity abstractEntity) {
        if (abstractEntity.isPlayer()) {
            Player p = (Player) BukkitAdapter.adapt(abstractEntity);
            if (p != null) {
                if (condition_type.equalsIgnoreCase("main-xp")) {
                    return PlayerData.get(p).getExperience() >= amount;
                }
                if (condition_type.equalsIgnoreCase("mining-xp")) {
                    return PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.mineManager.getLinkedProfession()) >= amount;
                }
                if (condition_type.equalsIgnoreCase("enchant-xp")) {
                    return PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.enchantManager.getLinkedProfession()) >= amount;
                }
                if (condition_type.equalsIgnoreCase("fish-xp")) {
                    return PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.fishingManager.getLinkedProfession()) >= amount;
                }
                if (condition_type.equalsIgnoreCase("alchemy-xp")) {
                    return PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.alchemyManager.getLinkedProfession()) >= amount;
                }
                if (condition_type.equalsIgnoreCase("smith-xp")) {
                    return PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.smithingManager.getLinkedProfession()) >= amount;
                }
                if (condition_type.equalsIgnoreCase("mining-level")) {
                    return PlayerData.get(p).getCollectionSkills().getLevel(MMOCore.plugin.mineManager.getLinkedProfession()) >= amount;
                }
                if (condition_type.equalsIgnoreCase("enchant-level")) {
                    return PlayerData.get(p).getCollectionSkills().getLevel(MMOCore.plugin.enchantManager.getLinkedProfession()) >= amount;
                }
                if (condition_type.equalsIgnoreCase("fish-level")) {
                    return PlayerData.get(p).getCollectionSkills().getLevel(MMOCore.plugin.fishingManager.getLinkedProfession()) >= amount;
                }
                if (condition_type.equalsIgnoreCase("alchemy-level")) {
                    return PlayerData.get(p).getCollectionSkills().getLevel(MMOCore.plugin.alchemyManager.getLinkedProfession()) >= amount;
                }
                if (condition_type.equalsIgnoreCase("smith-level")) {
                    return PlayerData.get(p).getCollectionSkills().getLevel(MMOCore.plugin.smithingManager.getLinkedProfession()) >= amount;
                }
                if (condition_type.equalsIgnoreCase("main-level")) {
                    return PlayerData.get(p).getLevel() >= amount;
                }
                if (condition_type.equalsIgnoreCase("class-points")) {
                    return PlayerData.get(p).getClassPoints() >= amount;
                }
                if (condition_type.equalsIgnoreCase("skill-points")) {
                    return PlayerData.get(p).getSkillPoints() >= amount;
                }
                if (condition_type.equalsIgnoreCase("attribute-points")) {
                    return PlayerData.get(p).getAttributePoints() >= amount;
                }
                if (condition_type.equalsIgnoreCase("attribute-reallocation-points")) {
                    return PlayerData.get(p).getAttributeReallocationPoints() >= amount;
                }
                if (condition_type.equalsIgnoreCase("mana")) {
                    return PlayerData.get(p).getMana() >= amount;
                }
                if (condition_type.equalsIgnoreCase("stamina")) {
                    return PlayerData.get(p).getStamina() >= amount;
                }
                if (condition_type.equalsIgnoreCase("stellium")) {
                    return PlayerData.get(p).getStellium() >= amount;
                }
            }
            return false;
        }
        return false;
    }
}

