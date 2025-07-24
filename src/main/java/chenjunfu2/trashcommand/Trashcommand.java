package chenjunfu2.trashcommand;

import chenjunfu2.trashcommand.api.TrashInventoryHolder;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class Trashcommand implements ModInitializer
{
	public static final String MOD_ID = "trashcommand";
	//public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{
		registerCommand();
	}
	
	private static void openGui(PlayerEntity player)
	{
		player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
				(syncId, inventory, playerEntity) ->
						GenericContainerScreenHandler.createGeneric9x6(syncId, inventory, ((TrashInventoryHolder)player).getTrashInventory()),
				Text.literal("垃圾桶(trash can)"))
		);
	}
	
	
	private static void registerCommand()
	{
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
		{
			dispatcher.register(literal("trash")
				.requires(ServerCommandSource::isExecutedByPlayer)
				.executes(context ->
					{
						ServerPlayerEntity player = context.getSource().getPlayer();
						if (player != null)
						{
							openGui(player);
						}
						return 1;
					}
				)
				.then(literal("undo")
				.requires(ServerCommandSource::isExecutedByPlayer)
				.executes(context ->
					{
						ServerPlayerEntity player = context.getSource().getPlayer();
						if (player != null)
						{
							((TrashInventoryHolder)player).getTrashInventory().setUndo();//设置undo防止清除物品
							openGui(player);
						}
						return 1;
					}
				))
			);
		});
	}
}