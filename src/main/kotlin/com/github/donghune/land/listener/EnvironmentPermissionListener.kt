package com.github.donghune.land.listener

import com.github.donghune.land.extension.getChunk
import com.github.donghune.land.extension.getLand
import com.github.donghune.land.model.entity.LandOption
import com.github.donghune.land.model.entity.hasPermission
import com.github.donghune.land.model.entity.hasProtection
import com.github.donghune.land.schduler.previousLocation
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.Container
import org.bukkit.block.Dispenser
import org.bukkit.block.data.Directional
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.*
import org.bukkit.event.entity.*
import org.bukkit.event.hanging.HangingBreakByEntityEvent
import org.bukkit.event.hanging.HangingPlaceEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.*
import org.bukkit.event.vehicle.VehicleDestroyEvent
import org.bukkit.event.vehicle.VehicleMoveEvent
import org.bukkit.event.world.StructureGrowEvent
import org.bukkit.projectiles.BlockProjectileSource
import java.util.*

class EventListener : Listener {

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val toLoc = event.to

        if (player.isOp) return

        val from = event.from.chunk.getLand()
        val to = toLoc.chunk.getLand()

        if (from !== to) {
            if (!from.hasPermission(player, LandOption.EXIT) || !to.hasPermission(player, LandOption.ENTRANCE)) {
                event.isCancelled = true
            }
        } else {
            if (from != null) {
                if (!from.hasPermission(player, LandOption.EXIT) || !from.hasPermission(player, LandOption.ENTRANCE)) {
                    event.isCancelled = true
                }
            } else if (to != null) {
                if (!to.hasPermission(player, LandOption.EXIT) || !to.hasPermission(player, LandOption.ENTRANCE)) {
                    event.isCancelled = true
                }
            } else {
                if (!from.hasPermission(player, LandOption.EXIT) || !to.hasPermission(player, LandOption.ENTRANCE)) {
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onVehicleMove(event: VehicleMoveEvent) {
        val from = event.from
        val to = event.to
        val fromArea = from.chunk.getLand()
        val toArea = to.chunk.getLand()

        if (fromArea !== toArea) {
            val vehicle = event.vehicle

            for (passenger in vehicle.passengers) {
                if (passenger is Player) {
                    if (passenger.isOp) continue

                    if (!fromArea.hasPermission(passenger, LandOption.EXIT) ||
                        !toArea.hasPermission(passenger, LandOption.ENTRANCE)
                    ) {
                        passenger.eject()
                        passenger.teleport(from)
                    }
                }
            }

            if (vehicle.passengers.isEmpty()) {
                if (fromArea.hasProtection(LandOption.VEHICLE_EXIT) ||
                    toArea.hasProtection(LandOption.VEHICLE_ENTRANCE)
                ) {
                    vehicle.teleport(from)
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onVehicleDestroy(event: VehicleDestroyEvent) {
        val vehicle = event.vehicle
        val area = vehicle.chunk.getLand()
        val attacker = event.attacker

        if (attacker is Player) {
            if (!attacker.isOp && !area.hasPermission(attacker, LandOption.ATTACK_ENTITY)) {
                event.isCancelled = true
            }
        } else {
            if (area.hasProtection(LandOption.DAMAGE)) {
                event.isCancelled = true
            }
        }
    }

    private val checkTeleportCauses = EnumSet.of(
        PlayerTeleportEvent.TeleportCause.UNKNOWN,
        PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT,
        PlayerTeleportEvent.TeleportCause.ENDER_PEARL
    )

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerTeleport(event: PlayerTeleportEvent) {
        if (event.cause in checkTeleportCauses) {
            val player = event.player
            val toLoc = event.to

            if (player.isOp) return

            val from = event.from.chunk.getLand()
            val to = toLoc.chunk.getLand()

            if (from !== to) {
                if (!from.hasPermission(player, LandOption.EXIT) ||
                    !to.hasPermission(player, LandOption.ENTRANCE)
                ) {
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerTeleportMonitor(event: PlayerTeleportEvent) {
        if (event.cause !in checkTeleportCauses) {
            event.player.previousLocation = event.to
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player

        if (player.isOp) return

        event.clickedBlock?.let { clickedBlock ->
            val area = clickedBlock.chunk.getLand()

            if (!area.hasPermission(player, LandOption.INTERACTION))
                event.isCancelled = true

            return
        }

        if (!player.chunk.getLand().hasPermission(player, LandOption.INTERACTION)) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        val player = event.player

        if (player.isOp) return

        val target = event.rightClicked

        if (!target.chunk.getLand().hasPermission(target, LandOption.INTERACTION)) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerArmorStandManipulate(event: PlayerArmorStandManipulateEvent) {
        val player = event.player.also { if (it.isOp) return }
        val target = event.rightClicked

        if (!target.chunk.getLand().hasPermission(player, LandOption.ARMOR_STAND_MANIPULATION))
            event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerBedLeave(event: PlayerBedLeaveEvent) {
        val player = event.player.also { if (it.isOp) return }
        val bed = event.bed
        val playerArea = player.chunk.getLand()
        val bedArea = bed.chunk.getLand()

        if (playerArea !== bedArea) {
            if (bedArea.hasPermission(player, LandOption.EXIT) ||
                !playerArea.hasPermission(player, LandOption.ENTRANCE)
            ) {
                player.teleport(bed.location.add(0.5, 0.56250, 0.5))
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        val entity = event.entity
        var damager = event.damager

        if (damager is Projectile) {
            val shooter = damager.shooter

            if (shooter is Entity)
                damager = shooter
        }

        val area = entity.chunk.getLand()

        if (damager is Player) {
            if (damager.isOp) return

            val permission = if (entity is Player) LandOption.ATTACK_PLAYER else LandOption.ATTACK_ENTITY

            if (!area.hasPermission(entity, permission)) {
                event.isCancelled = true
                return
            }
        }

        if (area.hasProtection(LandOption.DAMAGE)) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onProjectileLaunch(event: ProjectileLaunchEvent) {
        val projectile = event.entity
        val shooter = projectile.shooter

        if (shooter is Player) {
            if (shooter.isOp) return

            if (!projectile.chunk.getLand().hasPermission(shooter, LandOption.PROJECTILE_LAUNCH)) {
                event.isCancelled = true
            }
        } else if (shooter is BlockProjectileSource) {
            val block = shooter.block
            val area = block.chunk.getLand()

            if (area.hasProtection(LandOption.DISPENSER)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockCanBuild(event: BlockCanBuildEvent) {
        val player = event.player
        if (player == null || player.isOp) return

        if (!event.block.chunk.getLand().hasProtection(LandOption.BLOCK_PLACE)) {
            event.isBuildable = false
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        if (player.isOp) return

        if (!event.block.chunk.getLand().hasPermission(player, LandOption.BLOCK_BREAK)) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        if (player.isOp) return

        if (!event.block.chunk.getLand().hasPermission(player, LandOption.BLOCK_PLACE)) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockIgnite(event: BlockIgniteEvent) {
        val area = event.block.chunk.getLand()
        val player = event.player

        if (player == null) {
            if (area.hasProtection(LandOption.FIRE)) {
                event.isCancelled = true
            }
        } else {
            if (player.isOp) return

            if (!area.hasPermission(player, LandOption.BLOCK_IGNITING)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerBucketFill(event: PlayerBucketFillEvent) {
        val player = event.player
        if (player.isOp) return

        if (!event.blockClicked.chunk.getLand().hasPermission(player, LandOption.BUCKET_FILL)) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerBucketEmpty(event: PlayerBucketEmptyEvent) {
        val player = event.player
        if (player.isOp) return

        if (!event.blockClicked.getRelative(event.blockFace).chunk.getLand()
                .hasPermission(player, LandOption.BUCKET_EMPTY)
        ) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onHangingBreak(event: HangingBreakByEntityEvent) {
        var remover = event.remover ?: return

        if (remover is Projectile) {
            val shooter = remover.shooter

            if (shooter is Entity)
                remover = shooter
        }

        if (remover is Player) {
            if (remover.isOp) return

            if (!event.entity.chunk.getLand().hasPermission(remover, LandOption.HANGING_BREAK)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onHangingPlace(event: HangingPlaceEvent) {
        val player = event.player

        if (player == null || player.isOp) return

        if (!event.block.chunk.getLand().hasPermission(player, LandOption.HANGING_PLACE)) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player
        if (player.isOp) return

        if (!event.itemDrop.chunk.getLand().hasPermission(player, LandOption.DROP_ITEM)) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPlayerPickupItem(event: EntityPickupItemEvent) {
        val entity = event.entity

        if (entity is Player) {
            if (entity.isOp) return

            val item = event.item

            if (!item.chunk.getLand().hasPermission(entity, LandOption.PICKUP_ITEM)) {
                event.isCancelled = true
                item.pickupDelay = 10
            }
        } else {
            val item = event.item

            if (item.chunk.getLand().hasProtection(LandOption.ENTITY_PICKUP_ITEM)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onPotionSplash(event: PotionSplashEvent) {
        for (affectedEntity in event.affectedEntities) {
            val area = affectedEntity.chunk.getLand()

            if (area.hasProtection(LandOption.POTION)) {
                event.setIntensity(affectedEntity, 0.0)
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onLingeringPotionSplash(event: LingeringPotionSplashEvent) {
        if (event.areaEffectCloud.chunk.getLand().hasProtection(LandOption.POTION)) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onAreaEffectCloudApply(event: AreaEffectCloudApplyEvent) {
        event.affectedEntities.removeIf { entity ->
            entity.chunk.getLand().hasProtection(LandOption.POTION)
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockFade(event: BlockFadeEvent) {
        if (event.block.chunk.getLand().hasProtection(LandOption.FADE)) {
            event.isCancelled = true
        }
    }

    private val stemsByFruit = EnumMap<Material, Material>(Material::class.java).apply {
        this[Material.PUMPKIN] = Material.PUMPKIN_STEM
        this[Material.MELON] = Material.MELON_STEM
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockGrow(event: BlockGrowEvent) {
        val block = event.block
        val area = block.chunk.getLand()

        if (area.hasProtection(LandOption.GROWTH)) {
            event.isCancelled = true
        } else {
            val type = event.newState.type
            stemsByFruit[type]?.let { stemType ->
                val directions = arrayOf(BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH)

                for (direction in directions) {
                    val relativeBlock = block.getRelative(direction)

                    if (relativeBlock.type == stemType) {
                        val relativeArea = relativeBlock.chunk.getLand()

                        if (area !== relativeArea && relativeArea.hasProtection(LandOption.OVERFLOW)) {
                            event.isCancelled = true
                            break
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onSpongeAbsorb(event: SpongeAbsorbEvent) {
        val block = event.block
        val area = block.chunk.getLand()

        event.blocks.removeIf { state ->
            val stateArea = state.block.chunk.getLand()
            area !== stateArea && stateArea.hasProtection(LandOption.SPONGE)
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockForm(event: BlockFormEvent) {
        if (event.block.chunk.getLand().hasProtection(LandOption.FORM)) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockSpread(event: BlockSpreadEvent) {
        val source = event.source.chunk.getLand()
        val block = event.block.chunk.getLand()

        if (source !== block) {
            if (source.hasProtection(LandOption.SPREAD) ||
                block.hasProtection(LandOption.SPREAD)
            ) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockRedstone(event: BlockRedstoneEvent) {
        if (event.block.chunk.getLand().hasProtection(LandOption.REDSTONE)) {
            event.newCurrent = 0
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onStructureGrow(event: StructureGrowEvent) {
        val area = event.location.chunk.getLand()

        if (area.hasProtection(LandOption.OVERFLOW)) {
            val blocks = event.blocks
            blocks.removeIf { area!!.getChunk().contains(it.blockData) }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockPistonExtend(event: BlockPistonExtendEvent) {
        val direction = event.direction
        val piston = event.block
        val head = piston.getRelative(direction)

        val pistonArea = piston.chunk.getLand()
        val headArea = head.chunk.getLand()
        val pistonProtection = pistonArea.hasProtection(LandOption.PISTON)

        if (pistonArea !== headArea &&
            (pistonProtection || headArea.hasProtection(LandOption.PISTON))
        ) {
            event.isCancelled = true
            return
        }

        if (pistonProtection) {
            for (block in event.blocks) {
                if (block.chunk.getLand() !== pistonArea || block.getRelative(direction).chunk.getLand() !== pistonArea) {
                    event.isCancelled = true
                    break
                }
            }
        } else {
            for (block in event.blocks) {
                val blockArea = block.chunk.getLand()

                if (blockArea !== pistonArea && blockArea.hasProtection(LandOption.PISTON)) {
                    event.isCancelled = true
                    break
                }

                val toBlockArea = block.getRelative(direction).chunk.getLand()

                if (toBlockArea !== pistonArea && toBlockArea.hasProtection(LandOption.PISTON)) {
                    event.isCancelled = true
                    break
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockPistonRetract(event: BlockPistonRetractEvent) {
        val blocks = event.blocks

        if (blocks.isEmpty())
            return

        val direction = event.direction
        val piston = event.block
        val pistonArea = piston.chunk.getLand()
        val pistonProtection = pistonArea.hasProtection(LandOption.PISTON)

        if (pistonProtection) {
            for (block in event.blocks) {
                if (block.chunk.getLand() !== pistonArea || block.getRelative(direction).chunk.getLand() !== pistonArea) {
                    event.isCancelled = true
                    break
                }
            }
        } else {
            for (block in event.blocks) {
                val blockArea = block.chunk.getLand()

                if (blockArea !== pistonArea && blockArea.hasProtection(LandOption.PISTON)) {
                    event.isCancelled = true
                    break
                }

                val toBlockArea = block.getRelative(direction).chunk.getLand()

                if (toBlockArea !== pistonArea && toBlockArea.hasProtection(LandOption.PISTON)) {
                    event.isCancelled = true
                    break
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockFromTo(event: BlockFromToEvent) {
        val from = event.block.chunk.getLand()
        val to = event.toBlock.chunk.getLand()

        if (from !== to) {
            if (from.hasProtection(LandOption.FLOOD) ||
                to.hasProtection(LandOption.FLOOD)
            ) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onEntityBlockChange(event: EntityChangeBlockEvent) {
        if (event.block.chunk.getLand().hasProtection(LandOption.ENTITY_CHANGE_BLOCK)) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onEntityExplode(event: EntityExplodeEvent) {
        if (event.entity.chunk.getLand().hasProtection(LandOption.EXPLOSION)) {
            event.isCancelled = true
        } else {
            event.blockList().removeIf { block ->
                block.chunk.getLand().hasProtection(LandOption.EXPLOSION)
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockExplode(event: BlockExplodeEvent) {
        event.blockList().removeIf { it.chunk.getLand().hasProtection(LandOption.EXPLOSION) }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onBlockDispense(event: BlockDispenseEvent) {
        val block = event.block
        val state = block.state

        if (state is Dispenser) {
            val data = block.blockData
            if (data is Directional) {
                val from = block.chunk.getLand()
                val to = block.getRelative(data.facing).chunk.getLand()

                if (from !== to &&
                    (from.hasProtection(LandOption.DISPENSER) || to.hasProtection(LandOption.DISPENSER))
                ) {
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    fun onInventoryMoveItem(event: InventoryMoveItemEvent) {
        val sourceHolder = event.source.holder
        val destinationHolder = event.destination.holder

        if (sourceHolder is Container && destinationHolder is Container) {
            val sourceArea = sourceHolder.block.chunk.getLand()

            val destinationArea = destinationHolder.block.chunk.getLand()

            if (sourceArea !== destinationArea &&
                (sourceArea.hasProtection(LandOption.ITEM_TRANSFER) || destinationArea.hasProtection(LandOption.ITEM_TRANSFER))
            ) {
                event.isCancelled = true
            }
        }
    }
}
