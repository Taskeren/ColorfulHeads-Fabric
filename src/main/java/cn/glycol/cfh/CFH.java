package cn.glycol.cfh;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;

public class CFH implements ModInitializer {
	
	public static final Logger LOGGER = LogManager.getLogger("cfh");
	
	static {
		LOGGER.info(new Random().nextBoolean() ? "Gradle or Maven?" : "League of Legend or DotA2?");
	}
	
	@Override
	public void onInitialize() {
		LOGGER.info("Colorful Heads in Fabric!");
		SkullTabsFabric.init();
	}
}
