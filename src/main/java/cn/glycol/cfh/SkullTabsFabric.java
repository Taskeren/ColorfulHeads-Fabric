package cn.glycol.cfh;

import cn.glycol.cfh.data.DataManager;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class SkullTabsFabric {

	/**
	 * To create a new SkullTab.
	 * @param name the unlocalized name (itemGroup.cfh.name)
	 * @param icon the item for icon
	 * @param dataName the name of the data file
	 */
	public static void create(String name, ItemStack icon, String dataName) {
		
		FabricItemGroupBuilder.create(new Identifier("cfh", name))
			.icon(()->icon)
			.appendItems(stacks -> stacks.addAll(DataManager.getSkulls(dataName)))
			.build();
		
	}
	
	/**
	 * To load the native tabs and DataManager.
	 */
	public static void init() {
		
		DataManager.defaults();
		
		create("custom", new ItemStack(Items.APPLE), "custom");
		create("colors", new ItemStack(Items.BONE_MEAL), "colors");
		create("blocks", new ItemStack(Blocks.BRICKS), "blocks");
		create("interior", new ItemStack(Items.FLOWER_POT), "interior");
		create("misc", new ItemStack(Items.LAVA_BUCKET), "misc");
		create("animals", new ItemStack(Items.TROPICAL_FISH), "animals");
		create("foods", new ItemStack(Items.BREAD), "foods");
		create("seasonal", new ItemStack(Blocks.BEACON), "seasonal");
		create("letters", new ItemStack(Items.LIGHT_BLUE_BANNER), "letters");
		create("mobs", new ItemStack(Items.SKELETON_SKULL), "mobs");
		
	}
	
}
