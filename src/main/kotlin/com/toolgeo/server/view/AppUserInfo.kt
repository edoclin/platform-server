package com.toolgeo.server.view

class AppUserInfo {
    var userId: String? = null
    var username: String? = null
    var wechatUid: String? = null
    var sessionKey: String? = null
    var name: String? = null
    var nickName: String? = null
    var avatarUrl: String? = null
    var sex: String? = null
    var idNumber: String? = null
    var mobile: String? = null
    var audited: Boolean? = null
    var bases: MutableList<AppBase> = mutableListOf()
    var updatedTime: String? = null
    var createdTime: String? = null
    var token: String? = null
}