package fun.tbcraft.play.hooks.mmoitems;

import fun.tbcraft.play.TBCPlugin;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.util.AltChar;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.stat.data.StringListData;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.type.StringListStat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModelStat extends StringListStat{
    public ModelStat (String id , Material mat , String name , String[] lore , String[] types , Material... materials) {
        super(id , mat , name , lore , types , materials);
    }


    @Override
    public void whenApplied (@NotNull ItemStackBuilder item , @NotNull StatData data) {
        super.whenApplied(item , data);
    }

    @Override
    public boolean hasValidMaterial (ItemStack item) {
        return super.hasValidMaterial(item);
    }

    @Override
    public StringListData whenInitialized (Object object) {
        return super.whenInitialized(object);
    }

    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT (@NotNull StatData data) {
        return super.getAppliedNBT(data);
    }

    @Override
    public void whenClicked (@NotNull EditionInventory inv , @NotNull InventoryClickEvent event) {
        super.whenClicked(inv , event);
    }

    @Override
    public void whenInput (@NotNull EditionInventory inv , @NotNull String message , Object... info) {
        super.whenInput(inv , message , info);
    }

    @Override
    public void whenLoaded (@NotNull ReadMMOItem mmoitem) {
        super.whenLoaded(mmoitem);
    }

    @Nullable
    @Override
    public StatData getLoadedNBT (@NotNull ArrayList<ItemTag> storedTags) {
        return super.getLoadedNBT(storedTags);
    }

    @Override
    public void whenDisplayed (List<String> lore , Optional<RandomStatData> statData) {
        if ( statData.isEmpty() ){
            TBCPlugin.errorLog("Bad Stat Data! ");
        }
        if (statData.isPresent()) {
            lore.add(ChatColor.GRAY + "Current Value:");
            StringListData data = (StringListData) statData.get();
            data.getList().forEach(element -> lore.add(ChatColor.GRAY + MythicLib.plugin.parseColors(element)));

        } else
            lore.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");

        lore.add("");
        lore.add(ChatColor.YELLOW + AltChar.listDash + " Click to add a line.");
        lore.add(ChatColor.YELLOW + AltChar.listDash + " Right click to remove the last line.");
    }

    @NotNull
    @Override
    public StatData getClearStatData ( ) {
        return super.getClearStatData();
    }
}
