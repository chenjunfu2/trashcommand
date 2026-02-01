package chenjunfu2.trashcommand.api;

import chenjunfu2.trashcommand.gui.TrashInventory;
import org.spongepowered.asm.mixin.Unique;

public interface TrashInventoryHolder//访问器
{
	@Unique
	TrashInventory trashcommand_1_20_1$getTrashInventory();
	
	@Unique
	void trashcommand_1_20_1$setTrashInventory(TrashInventory trashInventory);
}
