package cn.glycol.cfh;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class SkullUtils {

	private static final Logger LOG = LogManager.getLogger("ColorfulHeads");
	
	public static String getSkullOwner(ItemStack stack) {
		if(isPlayerSkull(stack)) {
			CompoundTag nbt = getOrCreateTag(stack);
			
			String owner = nbt.getString("SkullOwner");
			if(owner.equals("")) {
				CompoundTag skullowner = nbt.getCompound("SkullOwner");
				if(skullowner != null) owner = skullowner.getString("Name");
			}
			return owner;
		} else {
			return "";
		}
	}
	
	public static ItemStack setSkullOwner(ItemStack stack, String skullowner) {
		if(stack == null) stack = new ItemStack(Items.PLAYER_HEAD);
		if(isPlayerSkull(stack)) {
			CompoundTag nbt = getOrCreateTag(stack);
			nbt.putString("SkullOwner", skullowner);
		}
		return stack;
	}
	
	public static ItemStack setSkullOwner(ItemStack stack, String id, String texture) {
		if(stack == null) stack = new ItemStack(Items.PLAYER_HEAD);
		if(isPlayerSkull(stack)) {
			CompoundTag root = getOrCreateTag(stack);
			CompoundTag skullowner = new CompoundTag();
			CompoundTag properties = new CompoundTag();
			ListTag textures = new ListTag();
			CompoundTag value = new CompoundTag();
			if(root.containsKey("SkullOwner")) skullowner = root.getCompound("SkullOwner");
			skullowner.putString("Id", id);
			value.putString("Value", texture);
			textures.add(value);
			properties.put("textures", textures);
			skullowner.put("Properties", properties);
			root.put("SkullOwner", skullowner);
		}
		
		return stack;
	}
	
	public static String serialize(ItemStack skull) {
		CompoundTag root = getOrCreateTag(skull);
		CompoundTag skullowner = root.getCompound("SkullOwner");
		String skullid = skullowner.getString("Id");
		CompoundTag properties = skullowner.getCompound("Properties");
		ListTag textures = properties.getList("textures", 10);
		CompoundTag property = textures.getCompoundTag(0);
		String texture = property.getString("Value");
		return skullid+"|"+texture+"|"+skull.getName();
	}
	
	public static ItemStack deserialize(String skullcode) {
		String[] parts = skullcode.split("\\|");
		if(parts.length > 3) {
			LOG.warn("Failed to deserialize the skull code, too many parts: {}", skullcode);
		}
		else if(parts.length < 2) {
			LOG.warn("Failed to deserialize the skull code, too few parts: {}", skullcode);
		}
		else {
			boolean customInfo = false;
			
			String skullid = parts[0];
			String texture = parts[1];
			String display = null;
			if(parts.length == 3) {
				display = parts[2].replace("&", "\u00A7");
				// CUSTOM的提示
				if(display.contains("##CUSTOM##")) {
					customInfo = true;
				}
			}
			ItemStack skull = SkullUtils.setSkullOwner(null, skullid, texture);
			if(customInfo) {
				skull.setCustomName(new TranslatableText("cfh.custom_info"));
			} else {
				if(display != null) {
					skull.setCustomName(new LiteralText(display));
				}
			}
			return skull;
		}
		return ItemStack.EMPTY;
	}
	
	public static boolean isPlayerSkull(ItemStack stack) {
		return stack.getItem() == Items.PLAYER_HEAD;
	}
	
	public static CompoundTag getOrCreateTag(ItemStack stack) {
		
		if(!stack.hasTag()) {
			stack.setTag(new CompoundTag());
		}
		
		return stack.getTag();
	}
	
}
