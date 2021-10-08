package dev.eastar.roomtest.data.source

import android.log.Log
import dev.eastar.roomtest.data.db.UserEntity

class RemoteService {
    fun getUsers(): Array<UserEntity> {
        val users = mutableListOf<UserEntity>()
        repeat(10) {
            users.add(UserEntity.RANDOM)
        }
        Log.e("loaded item 3")
        return users.toTypedArray()
    }
}
