/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jackhuang.hellominecraft.svrmgr.cbplugins;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.ho.yaml.Yaml;
import org.jackhuang.hellominecraft.utils.NetUtils;
import org.jackhuang.hellominecraft.utils.StrUtils;

/**
 *
 * @author hyh
 */
public class PluginManager {
    
    public static PluginInformation getPluginYML(File f) {
        try {
            ZipFile file = new ZipFile(f);
            ZipEntry plg = file.getEntry("plugin.yml");
            InputStream is = file.getInputStream(plg);
            return Yaml.loadType(is, PluginInformation.class);
        } catch (Exception ex) {
            Logger.getLogger(PluginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static List<BukkitPlugin> getPlugins() throws Exception {
        String result = NetUtils.doGet("http://api.bukget.org/3//plugins?fields=slug,plugin_name,description,versions.version,versions.game_versions");
        Gson gson = new Gson();
        List<BukkitPlugin> list = gson.fromJson(result, new TypeToken<List<BukkitPlugin>>(){}.getType());
        return list;
    }
    
    public static final String CATEGORY_ADMIN_TOOLS = "Admin Tools",
            CATEGORY_DEVELOPER_TOOLS = "Developer Tools",
            CATEGORY_FUN = "Fun",
            CATEGORY_GENERAL = "General",
            CATEGORY_ANTI_GRIEFING_TOOLS = "Anti Griefing Tools",
            CATEGORY_MECHAICS = "Mechanics",
            CATEGORY_Fixes = "Fixes",
            CATEGORY_ROLE_PLAYING = "Role Playing",
            CATEGORY_WORLD_EDITING_AND_MANAGEMENT = "World Editing and Management",
            CATEGORY_TELEPORTATION = "Teleportation",
            CATEGORY_INFORMATIONAL = "Informational",
            CATEGORY_ECONOMY = "Economy",
            CATEGORY_CHAT_RELATED = "Chat Related",
            CATEGORY_MISCELLANEOUS = "Miscellaneous",
            CATEGORY_WORLD_GENERATORS = "World Generators",
            CATEGORY_WEBSITE_ADMINISTRATION = "Website Administration";
    
    public static List<BukkitPlugin> getPluginsByCategory(String category) throws Exception {
        String result = NetUtils.doGet("http://api.bukget.org/3//categories/" + category + "?fields=slug,plugin_name,description,versions.version,versions.game_versions");
        Gson gson = new Gson();
        List<BukkitPlugin> list = gson.fromJson(result, new TypeToken<List<BukkitPlugin>>(){}.getType());
        return list;
    }
    public static List<Category> getCategories() throws Exception {
        String result = NetUtils.doGet("http://api.bukget.org/3//categories/");
        Gson gson = new Gson();
        List<Category> list = gson.fromJson(result, new TypeToken<List<Category>>(){}.getType());
        return list;
    }
    public static PluginInfo getPluginInfo(String slug) throws Exception {
        if(StrUtils.isNotBlank(slug)) {
            String result = NetUtils.doGet("http://api.bukget.org/3//plugins/bukkit/" + slug.toLowerCase());
            if(StrUtils.isNotBlank(result)) {
                if(!result.equals("null")) {
                    PluginInfo info = new Gson().fromJson(result, PluginInfo.class);
                    return info;
                }
            }
        }
        return null;
    }
}
