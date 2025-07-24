package chenjunfu2.trashcommand;

import chenjunfu2.trashcommand.api.TrashInventoryHolder;
import chenjunfu2.trashcommand.gui.TrashInstantInventory;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class Trashcommand implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		registerCommand();
	}
	
	private static void openTrashGui(PlayerEntity player)
	{
		player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
				(syncId, inventory, playerEntity) ->
						GenericContainerScreenHandler.createGeneric9x6(syncId, inventory, ((TrashInventoryHolder)player).trashcommand_1_20_1$getTrashInventory()),
				Text.literal("垃圾桶(Trash can)"))
		);
	}
	
	private static void openTrashInstantGui(PlayerEntity player)
	{
		player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
				(syncId, inventory, playerEntity) ->
						GenericContainerScreenHandler.createGeneric9x6(syncId, inventory, new TrashInstantInventory()),
				Text.literal("无限垃圾桶(Instant trash can)"))
		);
	}
	
	private static void registerCommand()
	{
		CommandRegistrationCallback.EVENT.register(
		(dispatcher, registryAccess, environment) ->
			dispatcher.register(literal("trash")
				.requires(ServerCommandSource::isExecutedByPlayer)
				.executes(context ->
					{
						ServerPlayerEntity player = context.getSource().getPlayer();
						if (player != null)
						{
							openTrashGui(player);
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
								((TrashInventoryHolder)player).trashcommand_1_20_1$getTrashInventory().setUndo();//设置undo防止清除物品
								openTrashGui(player);
							}
							return 1;
						}
					)
				)
				.then(literal("instant")
					.requires(ServerCommandSource::isExecutedByPlayer)
					.executes(context ->
						{
							ServerPlayerEntity player = context.getSource().getPlayer();
							if (player != null)
							{
								openTrashInstantGui(player);
							}
							return 1;
						}
					)
				)
			)
		);
	}
}