package com.flemmli97.advancedgolems.proxy;

import com.flemmli97.advancedgolems.AdvancedGolems;
import com.flemmli97.advancedgolems.entity.EntityGolemBase;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GolemMsg implements IMessage {

    public String uuid;
    public int[] pos;

    public GolemMsg() {
    }

    public GolemMsg(EntityGolem entity) {
        this.uuid = entity.getCachedUniqueIdString();
        this.pos = new int[] {entity.getHomePosition().getX(), entity.getHomePosition().getY(), entity.getHomePosition().getZ()};
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound compound = ByteBufUtils.readTag(buf);
        this.uuid = compound.getString("UUID");
        this.pos = compound.getIntArray("Pos");
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("UUID", uuid);
        compound.setIntArray("Pos", this.pos);
        ByteBufUtils.writeTag(buf, compound);
    }

    public static class Handler implements IMessageHandler<GolemMsg,IMessage> {

        @Override
        public IMessage onMessage(GolemMsg msg, MessageContext ctx) {
            EntityPlayer player = AdvancedGolems.proxy.getPlayerEntity(ctx);
            if (player != null)
                for (Entity e : player.world.loadedEntityList)
                    if (e instanceof EntityGolemBase && e.getCachedUniqueIdString().equals(msg.uuid))
                        ((EntityGolemBase) e).setHomePosAndDistance(new BlockPos(msg.pos[0], msg.pos[1], msg.pos[2]), 9);
            ;
            return null;
        }
    }
}