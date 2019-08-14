package cn.wode490390.nukkit.customcreativeitems;

import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.Utils;
import java.io.File;
import java.util.List;
import java.util.Map;

public class Main extends PluginBase {

    @Override
    public void onEnable() {
        try {
            new MetricsLite(this);
        } catch (Exception ignore) {

        }
        this.saveResource("creativeitems.json");
        Config config = new Config(new File(this.getDataFolder(), "creativeitems.json"), Config.JSON);
        List<Map> list = config.getMapList("items");
        Item.clearCreativeItems();
        list.forEach((map) -> {
            try {
                int id = ((Number) map.get("id")).intValue();
                int damage = ((Number) map.getOrDefault("damage", 0)).intValue();
                String hex = (String) map.get("nbt_hex");
                byte[] nbt = hex != null ? Utils.parseHexBinary(hex) : new byte[0];
                Item.addCreativeItem(Item.get(id, damage, 1, nbt));
            } catch (Exception e) {
                this.getLogger().warning("An error occurred while reading the configuration file", e);
            }
        });
    }
}
