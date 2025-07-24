package chenjunfu2.trashcommand.mixin;

import chenjunfu2.trashcommand.api.TrashInventoryHolder;
import chenjunfu2.trashcommand.gui.TrashInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements TrashInventoryHolder
{
	//添加新字段
	@Unique
	private final TrashInventory trashInventory = new TrashInventory(); // 9格垃圾桶
	
	//添加访问方法
	@Override
	public TrashInventory trashcommand_1_20_1$getTrashInventory()
	{
		return this.trashInventory;
	}
	
	//添加读取方法
	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void loadTrashInventory(NbtCompound nbt, CallbackInfo ci)
	{
		if (nbt.contains("TrashInventory", NbtElement.LIST_TYPE))
		{
			this.trashInventory.readNbtList(nbt.getList("TrashInventory", NbtElement.COMPOUND_TYPE));
		}
	}
	
	//添加写入方法
	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void saveTrashInventory(NbtCompound nbt, CallbackInfo ci)
	{
		nbt.put("TrashInventory", this.trashInventory.toNbtList());
	}
}