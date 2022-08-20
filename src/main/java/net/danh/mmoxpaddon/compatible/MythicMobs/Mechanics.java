package net.danh.mmoxpaddon.compatible.MythicMobs;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.bukkit.BukkitAdapter;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.event.PlayerResourceUpdateEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import org.bukkit.entity.Player;

import java.util.Random;

public class Mechanics implements ITargetedEntitySkill {

    protected final String action;
    protected final String type;
    protected final String amount;

    public Mechanics(MythicLineConfig config) {
        this.action = config.getString(new String[]{"action", "a"});
        this.type = config.getString(new String[]{"type", "t"});
        this.amount = config.getString(new String[]{"amount", "aa"});
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata skillMetadata, AbstractEntity target) {
        if (target.isPlayer()) {
            Player p = (Player) BukkitAdapter.adapt(target);
            if (p != null) {
                if (action.equalsIgnoreCase("add")) {
                    if (amount.contains("-")) {
                        if (Integer.parseInt(amount.split("-")[1]) - Integer.parseInt(amount.split("-")[0]) > 1) {
                            int xp = new Random().nextInt(Integer.parseInt(amount.split("-")[0]), Integer.parseInt(amount.split("-")[1]));
                            if (type.equalsIgnoreCase("main-xp")) {
                                PlayerData.get(p).giveExperience(xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("mining-xp")) {
                                PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.mineManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("enchant-xp")) {
                                PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.enchantManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("alchemy-xp")) {
                                PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.alchemyManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("fish-xp")) {
                                PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.fishingManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("smith-xp")) {
                                PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.smithingManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("mining-level")) {
                                PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.mineManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("enchant-level")) {
                                PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.enchantManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("alchemy-level")) {
                                PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.alchemyManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("fish-level")) {
                                PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.fishingManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("smith-level")) {
                                PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.smithingManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("main-level")) {
                                PlayerData.get(p).giveLevels(xp, EXPSource.SOURCE);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("class-points")) {
                                PlayerData.get(p).giveClassPoints(xp);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("skill-points")) {
                                PlayerData.get(p).giveSkillPoints(xp);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("attribute-points")) {
                                PlayerData.get(p).giveAttributePoints(xp);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("attribute-reallocation-points")) {
                                PlayerData.get(p).giveAttributeReallocationPoints(xp);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("mana")) {
                                PlayerData.get(p).giveMana(xp, PlayerResourceUpdateEvent.UpdateReason.OTHER);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("stamina")) {
                                PlayerData.get(p).giveStamina(xp, PlayerResourceUpdateEvent.UpdateReason.OTHER);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("stellium")) {
                                PlayerData.get(p).giveStellium(xp, PlayerResourceUpdateEvent.UpdateReason.OTHER);
                                return SkillResult.SUCCESS;
                            }
                        } else {
                            int xp = Integer.parseInt(amount.split("-")[0]);
                            if (type.equalsIgnoreCase("main-xp")) {
                                PlayerData.get(p).giveExperience(xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("mining-xp")) {
                                PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.mineManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("enchant-xp")) {
                                PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.enchantManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("alchemy-xp")) {
                                PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.alchemyManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("fish-xp")) {
                                PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.fishingManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("smith-xp")) {
                                PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.smithingManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("mining-level")) {
                                PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.mineManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("enchant-level")) {
                                PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.enchantManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("alchemy-level")) {
                                PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.alchemyManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("fish-level")) {
                                PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.fishingManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("smith-level")) {
                                PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.smithingManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("main-level")) {
                                PlayerData.get(p).giveLevels(xp, EXPSource.SOURCE);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("class-points")) {
                                PlayerData.get(p).giveClassPoints(xp);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("skill-points")) {
                                PlayerData.get(p).giveSkillPoints(xp);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("attribute-points")) {
                                PlayerData.get(p).giveAttributePoints(xp);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("attribute-reallocation-points")) {
                                PlayerData.get(p).giveAttributeReallocationPoints(xp);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("mana")) {
                                PlayerData.get(p).giveMana(xp, PlayerResourceUpdateEvent.UpdateReason.OTHER);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("stamina")) {
                                PlayerData.get(p).giveStamina(xp, PlayerResourceUpdateEvent.UpdateReason.OTHER);
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("stellium")) {
                                PlayerData.get(p).giveStellium(xp, PlayerResourceUpdateEvent.UpdateReason.OTHER);
                                return SkillResult.SUCCESS;
                            }
                        }
                    } else {
                        int xp = Integer.parseInt(amount);
                        if (type.equalsIgnoreCase("main-xp")) {
                            PlayerData.get(p).giveExperience(xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("mining-xp")) {
                            PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.mineManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("enchant-xp")) {
                            PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.enchantManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("alchemy-xp")) {
                            PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.alchemyManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("fish-xp")) {
                            PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.fishingManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("smith-xp")) {
                            PlayerData.get(p).getCollectionSkills().giveExperience(MMOCore.plugin.smithingManager.getLinkedProfession(), xp, EXPSource.SOURCE, skillMetadata.getCaster().getLocation().toPosition().toLocation(), true);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("mining-level")) {
                            PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.mineManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("enchant-level")) {
                            PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.enchantManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("alchemy-level")) {
                            PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.alchemyManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("fish-level")) {
                            PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.fishingManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("smith-level")) {
                            PlayerData.get(p).getCollectionSkills().giveLevels(MMOCore.plugin.smithingManager.getLinkedProfession(), xp, EXPSource.SOURCE);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("main-level")) {
                            PlayerData.get(p).giveLevels(xp, EXPSource.SOURCE);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("class-points")) {
                            PlayerData.get(p).giveClassPoints(xp);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("skill-points")) {
                            PlayerData.get(p).giveSkillPoints(xp);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("attribute-points")) {
                            PlayerData.get(p).giveAttributePoints(xp);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("attribute-reallocation-points")) {
                            PlayerData.get(p).giveAttributeReallocationPoints(xp);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("mana")) {
                            PlayerData.get(p).giveMana(xp, PlayerResourceUpdateEvent.UpdateReason.OTHER);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("stamina")) {
                            PlayerData.get(p).giveStamina(xp, PlayerResourceUpdateEvent.UpdateReason.OTHER);
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("stellium")) {
                            PlayerData.get(p).giveStellium(xp, PlayerResourceUpdateEvent.UpdateReason.OTHER);
                            return SkillResult.SUCCESS;
                        }
                    }
                }
                if (action.equalsIgnoreCase("remove")) {
                    if (amount.contains("-")) {
                        if (Integer.parseInt(amount.split("-")[1]) - Integer.parseInt(amount.split("-")[0]) > 1) {
                            int xp = new Random().nextInt(Integer.parseInt(amount.split("-")[0]), Integer.parseInt(amount.split("-")[1]));
                            if (type.equalsIgnoreCase("main-xp")) {
                                PlayerData.get(p).setExperience(Math.max(0, PlayerData.get(p).getExperience() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("mining-xp")) {
                                PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.mineManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.mineManager.getLinkedProfession()) - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("enchant-xp")) {
                                PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.enchantManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.enchantManager.getLinkedProfession()) - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("alchemy-xp")) {
                                PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.alchemyManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.alchemyManager.getLinkedProfession()) - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("fish-xp")) {
                                PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.fishingManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.fishingManager.getLinkedProfession()) - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("smith-xp")) {
                                PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.smithingManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.smithingManager.getLinkedProfession()) - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("mining-level")) {
                                PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.mineManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.mineManager.getLinkedProfession()) - xp)));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("enchant-level")) {
                                PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.enchantManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.enchantManager.getLinkedProfession()) - xp)));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("alchemy-level")) {
                                PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.alchemyManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.alchemyManager.getLinkedProfession()) - xp)));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("fish-level")) {
                                PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.fishingManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.fishingManager.getLinkedProfession()) - xp)));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("smith-level")) {
                                PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.smithingManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.smithingManager.getLinkedProfession()) - xp)));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("main-level")) {
                                PlayerData.get(p).setLevel(Math.max(1, PlayerData.get(p).getLevel() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("class-points")) {
                                PlayerData.get(p).setClassPoints(Math.max(0, PlayerData.get(p).getClassPoints() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("skill-points")) {
                                PlayerData.get(p).setSkillPoints(Math.max(0, PlayerData.get(p).getSkillPoints() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("attribute-points")) {
                                PlayerData.get(p).setAttributePoints(Math.max(0, PlayerData.get(p).getAttributePoints() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("attribute-reallocation-points")) {
                                PlayerData.get(p).setAttributeReallocationPoints(Math.max(0, PlayerData.get(p).getAttributeReallocationPoints() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("mana")) {
                                PlayerData.get(p).setMana(Math.max(0, PlayerData.get(p).getMana() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("stamina")) {
                                PlayerData.get(p).setStamina(Math.max(0, PlayerData.get(p).getStellium() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("stellium")) {
                                PlayerData.get(p).setStellium(Math.max(0, PlayerData.get(p).getStellium() - xp));
                                return SkillResult.SUCCESS;
                            }
                        } else {
                            int xp = Integer.parseInt(amount.split("-")[0]);
                            if (type.equalsIgnoreCase("main-xp")) {
                                PlayerData.get(p).setExperience(Math.max(0, PlayerData.get(p).getExperience() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("mining-xp")) {
                                PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.mineManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.mineManager.getLinkedProfession()) - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("enchant-xp")) {
                                PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.enchantManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.enchantManager.getLinkedProfession()) - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("alchemy-xp")) {
                                PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.alchemyManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.alchemyManager.getLinkedProfession()) - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("fish-xp")) {
                                PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.fishingManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.fishingManager.getLinkedProfession()) - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("smith-xp")) {
                                PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.smithingManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.smithingManager.getLinkedProfession()) - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("mining-level")) {
                                PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.mineManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.mineManager.getLinkedProfession()) - xp)));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("enchant-level")) {
                                PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.enchantManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.enchantManager.getLinkedProfession()) - xp)));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("alchemy-level")) {
                                PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.alchemyManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.alchemyManager.getLinkedProfession()) - xp)));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("fish-level")) {
                                PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.fishingManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.fishingManager.getLinkedProfession()) - xp)));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("smith-level")) {
                                PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.smithingManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.smithingManager.getLinkedProfession()) - xp)));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("main-level")) {
                                PlayerData.get(p).setLevel(Math.max(1, PlayerData.get(p).getLevel() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("class-points")) {
                                PlayerData.get(p).setClassPoints(Math.max(0, PlayerData.get(p).getClassPoints() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("skill-points")) {
                                PlayerData.get(p).setSkillPoints(Math.max(0, PlayerData.get(p).getSkillPoints() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("attribute-points")) {
                                PlayerData.get(p).setAttributePoints(Math.max(0, PlayerData.get(p).getAttributePoints() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("attribute-reallocation-points")) {
                                PlayerData.get(p).setAttributeReallocationPoints(Math.max(0, PlayerData.get(p).getAttributeReallocationPoints() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("mana")) {
                                PlayerData.get(p).setMana(Math.max(0, PlayerData.get(p).getMana() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("stamina")) {
                                PlayerData.get(p).setStamina(Math.max(0, PlayerData.get(p).getStellium() - xp));
                                return SkillResult.SUCCESS;
                            }
                            if (type.equalsIgnoreCase("stellium")) {
                                PlayerData.get(p).setStellium(Math.max(0, PlayerData.get(p).getStellium() - xp));
                                return SkillResult.SUCCESS;
                            }
                        }
                    } else {
                        int xp = Integer.parseInt(amount);
                        if (type.equalsIgnoreCase("main-xp")) {
                            PlayerData.get(p).setExperience(Math.max(0, PlayerData.get(p).getExperience() - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("mining-xp")) {
                            PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.mineManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.mineManager.getLinkedProfession()) - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("enchant-xp")) {
                            PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.enchantManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.enchantManager.getLinkedProfession()) - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("alchemy-xp")) {
                            PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.alchemyManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.alchemyManager.getLinkedProfession()) - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("fish-xp")) {
                            PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.fishingManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.fishingManager.getLinkedProfession()) - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("smith-xp")) {
                            PlayerData.get(p).getCollectionSkills().setExperience(MMOCore.plugin.smithingManager.getLinkedProfession(), Math.max(0, PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.smithingManager.getLinkedProfession()) - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("mining-level")) {
                            PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.mineManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.mineManager.getLinkedProfession()) - xp)));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("enchant-level")) {
                            PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.enchantManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.enchantManager.getLinkedProfession()) - xp)));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("alchemy-level")) {
                            PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.alchemyManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.alchemyManager.getLinkedProfession()) - xp)));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("fish-level")) {
                            PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.fishingManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.fishingManager.getLinkedProfession()) - xp)));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("smith-level")) {
                            PlayerData.get(p).getCollectionSkills().setLevel(MMOCore.plugin.smithingManager.getLinkedProfession(), (int) Math.max(0, (PlayerData.get(p).getCollectionSkills().getExperience(MMOCore.plugin.smithingManager.getLinkedProfession()) - xp)));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("main-level")) {
                            PlayerData.get(p).setLevel(Math.max(1, PlayerData.get(p).getLevel() - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("class-points")) {
                            PlayerData.get(p).setClassPoints(Math.max(0, PlayerData.get(p).getClassPoints() - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("skill-points")) {
                            PlayerData.get(p).setSkillPoints(Math.max(0, PlayerData.get(p).getSkillPoints() - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("attribute-points")) {
                            PlayerData.get(p).setAttributePoints(Math.max(0, PlayerData.get(p).getAttributePoints() - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("attribute-reallocation-points")) {
                            PlayerData.get(p).setAttributeReallocationPoints(Math.max(0, PlayerData.get(p).getAttributeReallocationPoints() - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("mana")) {
                            PlayerData.get(p).setMana(Math.max(0, PlayerData.get(p).getMana() - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("stamina")) {
                            PlayerData.get(p).setStamina(Math.max(0, PlayerData.get(p).getStellium() - xp));
                            return SkillResult.SUCCESS;
                        }
                        if (type.equalsIgnoreCase("stellium")) {
                            PlayerData.get(p).setStellium(Math.max(0, PlayerData.get(p).getStellium() - xp));
                            return SkillResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return SkillResult.CONDITION_FAILED;
    }
}
