package edu.ualr.jxcarlat.login
//Our Data Class will grab the Json object and allow us to access all of the information in it
data class GithubUser(
        val username: Array<String>? = null,
        val password1: Array<String>? = null,
        val password2: Array<String>? = null,
        val key: String? = null)