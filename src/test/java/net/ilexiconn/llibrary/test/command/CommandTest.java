package net.ilexiconn.llibrary.test.command;

import net.ilexiconn.llibrary.server.command.Command;
import net.ilexiconn.llibrary.server.command.CommandHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "CommandTest")
public class CommandTest {
    public static final Logger LOGGER = LogManager.getLogger("CommandTest");

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        Command command = Command.create("testcommand")
                .addRequiredArgument("required_player", EntityPlayer.class)
                .addRequiredArgument("required_double", double.class)
                .addOptionalArgument("optional_integer", int.class);

        CommandHandler.INSTANCE.registerCommand(event, command, (server, sender, arguments) -> {
            CommandTest.LOGGER.info("=== Test command ===");
            CommandTest.LOGGER.info("== Player ==");
            EntityPlayer required_player = arguments.getPlayer("required_player");
            CommandTest.LOGGER.info(" -> " + required_player.getUniqueID());
            CommandTest.LOGGER.info("== Double ==");
            double required_double = arguments.getArgument("required_double", double.class);
            CommandTest.LOGGER.info(" -> " + required_double);
            CommandTest.LOGGER.info("== Integer ==");
            boolean has_optional_integer = arguments.hasArgument("optional_integer");
            CommandTest.LOGGER.info("hasArgument: " + has_optional_integer);
            if (has_optional_integer) {
                int optional_integer = arguments.getInteger("optional_integer");
                CommandTest.LOGGER.info(" -> " + optional_integer);
            }
        });
    }
}
