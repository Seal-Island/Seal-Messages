package com.focamacho.sealforgemessages.mixin;

import com.focamacho.sealconfig.SealConfig;
import com.focamacho.sealforgemessages.config.SealForgeMessagesConfig;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(value = FMLCommonHandler.class, remap = false)
public class MixinFMLCommonHandler {

    private SealForgeMessagesConfig config;

    @Inject(method = "<init>()V", at = @At("RETURN"))
    private void init(CallbackInfo info) {
        try {
            File jsonFile = new File("./config/SealForgeMessages.json");
            SealConfig sealConfig = new SealConfig();
            config = sealConfig.getConfig(jsonFile, SealForgeMessagesConfig.class);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @ModifyConstant(method = "handleServerHandshake")
    private String getMessage(String original) {
        if(original.equalsIgnoreCase("Server is still starting! Please wait before reconnecting.")) return config.serverStarting.replace("&", "§");
        else if(original.equalsIgnoreCase("This server has mods that require FML/Forge to be installed on the client. Contact your server admin for more details.")) return config.missingMods.replace("&", "§");
        else return original;
    }

}
