package com.github.donghune.land.inventory.group

import com.github.donghune.land.extension.getBelongingVillage
import com.github.donghune.land.extension.toUUID
import com.github.donghune.land.inventory.InvIcon.ICON_NEXT
import com.github.donghune.land.inventory.InvIcon.ICON_PREVIOUS
import com.github.donghune.land.model.entity.LandType
import com.github.donghune.land.model.repository.NationRepository
import com.github.donghune.land.model.repository.VillageRepository
import com.github.donghune.land.model.usecase.GroupJoinRequestUseCase
import com.github.donghune.land.model.usecase.GroupJoinRequestUseCaseParam
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.namulibrary.struct.PagingList
import com.github.donghune.plugin
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import kotlin.math.max
import kotlin.math.min

class LandGroupJoinInventory(
    val landType: LandType,
) : GUI(plugin, 54, "가입할 ${landType.korName}을 선택해주세요") {

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    private var page: Int = 0
    private val villageList = PagingList(45, VillageRepository.getList())
    private val nationList = PagingList(45, NationRepository.getList())

    override suspend fun setContent() {
        if (landType == LandType.VILLAGE) {
            villageList.getPage(page).forEachIndexed { index, village ->
                setItem(index, village.toItemStack()) {
                    it.isCancelled = true
                    GroupJoinRequestUseCase.execute(
                        GroupJoinRequestUseCaseParam(
                            village,
                            (it.whoClicked as Player).uniqueId,
                        )
                    )
                    (it.whoClicked as Player).sendInfoMessage("가입 요청을 하였습니다.")
                    it.whoClicked.closeInventory()
                }
            }
        } else {
            nationList.getPage(page).forEachIndexed { index, nation ->
                setItem(index, nation.toItemStack()) {
                    it.isCancelled = true
                    GroupJoinRequestUseCase.execute(
                        GroupJoinRequestUseCaseParam(
                            nation,
                            (it.whoClicked as Player).getBelongingVillage()!!.uuid.toUUID(),
                        )
                    )
                    (it.whoClicked as Player).sendInfoMessage("가입 요청을 하였습니다.")
                    it.whoClicked.closeInventory()
                }
            }
        }
        setItem(48, ICON_PREVIOUS()) {
            it.isCancelled = true
            page = max(page - 1, 0)
            refreshContent()
        }
        setItem(50, ICON_NEXT()) {
            it.isCancelled = true
            page = min(page + 1, villageList.lastPageIndex)
            refreshContent()
        }
    }
}