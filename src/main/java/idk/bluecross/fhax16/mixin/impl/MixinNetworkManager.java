package idk.bluecross.fhax16.mixin.impl;

import idk.bluecross.fhax16.event.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Inject(method = "sendPacket(Lnet/minecraft/network/IPacket;)V", at = @At("HEAD"), cancellable = true)
    public void onSendPacket(IPacket<?> packet1, CallbackInfo ci) {
        PacketEvent ev = new PacketEvent.Send(packet1);
        MinecraftForge.EVENT_BUS.post(ev);
        if (ev.isCanceled()) ci.cancel();
    }

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/IPacket;)V", at = @At("HEAD"), cancellable = true)
    public void onReceivedPacket(ChannelHandlerContext p_channelRead0_1_, IPacket<?> packet, CallbackInfo ci) {
        PacketEvent ev = new PacketEvent.Get(packet);
        MinecraftForge.EVENT_BUS.post(ev);
        if (ev.isCanceled()) ci.cancel();
        packet = ev.getPacket();
    }
}
