/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jackhuang.hellominecraft.launcher.utils.installers.optifine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipFile;
import org.jackhuang.hellominecraft.C;
import org.jackhuang.hellominecraft.launcher.utils.settings.Profile;
import org.jackhuang.hellominecraft.tasks.Task;
import org.jackhuang.hellominecraft.tasks.communication.PreviousResult;
import org.jackhuang.hellominecraft.tasks.communication.PreviousResultRegistrator;
import org.jackhuang.hellominecraft.utils.FileUtils;
import org.jackhuang.hellominecraft.launcher.utils.version.MinecraftLibrary;
import org.jackhuang.hellominecraft.launcher.utils.version.MinecraftVersion;

/**
 *
 * @author hyh
 */
public class OptiFineInstaller extends Task implements PreviousResultRegistrator<File>  {

    public File installer;
    public Profile profile;
    public String version;

    public OptiFineInstaller(Profile profile, String version) {
        this(profile, version, null);
    }

    public OptiFineInstaller(Profile profile, String version, File installer) {
        this.profile = profile;
        this.installer = installer;
        this.version = version;
    }

    @Override
    public boolean executeTask() {
        if(profile == null || profile.getSelectedMinecraftVersion() == null) {
            setFailReason(new RuntimeException(C.i18n("install.no_version")));
            return false;
        }
        MinecraftVersion mv = (MinecraftVersion)profile.getSelectedMinecraftVersion().clone();
        
        try {
            mv.inheritsFrom = mv.id;
            mv.jar = mv.jar == null ? mv.id : mv.jar;
            mv.libraries.clear();
            mv.libraries.add(0, new MinecraftLibrary("optifine:OptiFine:" + version));
            FileUtils.copyFile(installer, new File(profile.getCanonicalGameDir(), "libraries/optifine/OptiFine/" + version + "/OptiFine-" + version + ".jar"));
                
            mv.id += "-" + version;
            if(new ZipFile(installer).getEntry("optifine/OptiFineTweaker.class") != null) {
                if(!mv.mainClass.startsWith("net.minecraft.launchwrapper.")) {
                    mv.mainClass = "net.minecraft.launchwrapper.Launch";
                    mv.minecraftArguments += " --tweakClass optifine.OptiFineTweaker";
                    mv.libraries.add(1, new MinecraftLibrary("net.minecraft:launchwrapper:1.7"));
                }
            }
            File loc = new File(profile.getCanonicalGameDir(), "versions/" + mv.id);
            loc.mkdirs();
            File json = new File(loc, mv.id + ".json");
            FileUtils.writeStringToFile(json, C.gsonPrettyPrinting.toJson(mv, MinecraftVersion.class));
        } catch (IOException ex) {
            setFailReason(ex);
            return false;
        }
        return true;
    }

    @Override
    public String getInfo() {
        return "Optifine Installer";
    }
    
    ArrayList<PreviousResult<File>> pre = new ArrayList();
    @Override
    public Task registerPreviousResult(PreviousResult pr) {
        pre.add(pr);
        return this;
    }
    
}