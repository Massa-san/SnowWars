package massajp.snowwars.customentity

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.vehicle.MinecartChest
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory


class DeathBox (loc: Location) : MinecartChest(EntityType.CHEST_MINECART, (loc.world as CraftWorld).handle) {

    fun setInv(player: Player) {
        this.createMenu(9, ((player as CraftPlayer).handle).inventory)
    }

}