package chenjunfu2.trashcommand.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

//即刻销毁物品的垃圾桶（慎用）
public class TrashInstantInventory implements Inventory
{
	@Override
	public int size()
	{
		return 0;
	}
	
	@Override
	public boolean isEmpty()
	{
		return true;
	}
	
	@Override
	public ItemStack getStack(int slot)
	{
		return ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack removeStack(int slot, int amount)
	{
		return ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack removeStack(int slot)
	{
		return ItemStack.EMPTY;
	}
	
	@Override
	public void setStack(int slot, ItemStack stack)
	{}
	
	@Override
	public void markDirty()
	{}
	
	@Override
	public boolean canPlayerUse(PlayerEntity player)
	{
		return true;
	}
	
	@Override
	public void clear()
	{}
}
