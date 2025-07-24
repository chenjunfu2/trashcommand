package chenjunfu2.trashcommand.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

//模拟原版末影箱，但是打开的时候如果undo标签不是true则清除，以实现垃圾桶功能
public class TrashInventory extends SimpleInventory
{
	boolean undo = false;
	
	public TrashInventory()
	{
		super(9*6);
	}
	
	public void setUndo()
	{
		undo = true;
	}
	
	public void clearTrash()//清理垃圾
	{
		for(int i = 0; i < this.size(); ++i)
		{
			this.setStack(i, ItemStack.EMPTY);
		}
	}
	
	public void readNbtList(NbtList nbtList) {
		clearTrash();
		
		for(int i = 0; i < nbtList.size(); ++i)
		{
			NbtCompound nbtCompound = nbtList.getCompound(i);
			int j = nbtCompound.getByte("Slot") & 255;
			if (j >= 0 && j < this.size())
			{
				this.setStack(j, ItemStack.fromNbt(nbtCompound));
			}
		}
		
	}
	
	public NbtList toNbtList()
	{
		NbtList nbtList = new NbtList();
		
		for(int i = 0; i < this.size(); ++i)
		{
			ItemStack itemStack = this.getStack(i);
			if (!itemStack.isEmpty())
			{
				NbtCompound nbtCompound = new NbtCompound();
				nbtCompound.putByte("Slot", (byte)i);
				itemStack.writeNbt(nbtCompound);
				nbtList.add(nbtCompound);
			}
		}
		
		return nbtList;
	}
	
	public void onOpen(PlayerEntity player)
	{
		if(!undo)
		{
			clearTrash();
		}
		else
		{
			undo = false;
		}
		super.onOpen(player);
	}
	
	public void onClose(PlayerEntity player)
	{
		super.onClose(player);
	}
}
