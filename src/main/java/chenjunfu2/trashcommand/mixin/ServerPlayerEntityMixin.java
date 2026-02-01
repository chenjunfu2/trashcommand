package chenjunfu2.trashcommand.mixin;

import chenjunfu2.trashcommand.api.TrashInventoryHolder;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin
{
	@Inject(method = "copyFrom", at = @At(value = "HEAD"))
	private void copyFromInject(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci)//玩家数据拷贝丢失修复
	{
		TrashInventoryHolder newModData = (TrashInventoryHolder)this;
		TrashInventoryHolder oldModData = (TrashInventoryHolder)oldPlayer;
		
		newModData.trashcommand_1_20_1$setTrashInventory(oldModData.trashcommand_1_20_1$getTrashInventory());
	}

}
