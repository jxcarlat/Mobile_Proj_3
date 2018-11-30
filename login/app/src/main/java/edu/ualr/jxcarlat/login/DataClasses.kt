package edu.ualr.jxcarlat.login
//Our Data Class will grab the Json object and allow us to access all of the information in it
data class GithubUser(
        val username: Array<String>? = null,
        val password1: Array<String>? = null,
        val password2: Array<String>? = null,
        val password: Array<String>? = null,
        val non_field_errors: Array<String>? = null,
        val key: String? = null)

data class ChatChannel(
        val name: String? = null,
        val pk: Int? = null,
        val detail: String? = null,
        val channel: String? = null,
        val message: String? = null,
        val timestamp: String? = null,
        val user: Array<String>? = null)