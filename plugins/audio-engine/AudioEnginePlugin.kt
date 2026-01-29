package com.lumus.musicplayer

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.annotation.CapacitorPlugin
import com.getcapacitor.annotation.PluginMethod

@CapacitorPlugin(name = "AudioEngine")
class AudioEnginePlugin : Plugin() {

    private var player: ExoPlayer? = null

    override fun load() {
        player = ExoPlayer.Builder(context).build()
    }

    @PluginMethod
    fun play(call: PluginCall) {
        val path = call.getString("path")

        if (path == null) {
            call.reject("Path is required")
            return
        }

        val mediaItem = MediaItem.fromUri(Uri.parse(path))
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()

        call.resolve()
    }

    @PluginMethod
    fun pause(call: PluginCall) {
        player?.pause()
        call.resolve()
    }

    @PluginMethod
    fun stop(call: PluginCall) {
        player?.stop()
        call.resolve()
    }

    @PluginMethod
    fun isPlaying(call: PluginCall) {
        val playing = player?.isPlaying ?: false
        call.resolve(mapOf("playing" to playing))
    }
}
