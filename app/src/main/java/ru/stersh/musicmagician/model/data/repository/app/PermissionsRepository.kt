package ru.stersh.musicmagician.model.data.repository.app

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class PermissionsRepository(private val context: Context) {
    private val permissions = BehaviorSubject.createDefault<MutableMap<String, Boolean>>(mutableMapOf())
    private var activity: Activity? = null

    fun hasPermission(permission: String): Observable<Boolean> {
        checkPermission(permission)
        return permissions
            .filter { it.contains(permission) }
            .map { it[permission] }
    }

    fun request(vararg permission: String) {
        activity?.let {
            ActivityCompat.requestPermissions(it, permission, 1)
        }
    }

    fun attachActivity(activity: Activity) {
        this.activity = activity
    }

    fun detachActivity() {
        this.activity = null
    }

    fun onRequestPermissionResult(permissions: Array<String>, grantResults: IntArray) {
        val update = this.permissions.value!!
        permissions.forEachIndexed { index, s ->
            update[s] = grantResults[index] == PackageManager.PERMISSION_GRANTED
        }
        this.permissions.onNext(update)
    }

    private fun checkPermission(permission: String) {
        val update = if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            permissions.value.apply {
                this!![permission] = true
            }
        } else {
            permissions.value.apply {
                this!![permission] = false
            }
        }
        permissions.onNext(update!!)
    }
}