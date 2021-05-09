package com.andresaftari.apiexample.data.remote.api.model

// User
data class UserResponse(
    var avatar_url: String = "",
    var bio: String = "",
    var blog: String = "",
    var company: String = "",
    var created_at: String = "",
    var email: Any = Any(),
    var events_url: String = "",
    var followers: Int = 0,
    var followers_url: String = "",
    var following: Int = 0,
    var following_url: String = "",
    var gists_url: String = "",
    var gravatar_id: String = "",
    var hireable: Any = Any(),
    var html_url: String = "",
    var id: Int = 0,
    var location: String = "",
    var login: String = "",
    var name: String = "",
    var node_id: String = "",
    var organizations_url: String = "",
    var public_gists: Int = 0,
    var public_repos: Int = 0,
    var received_events_url: String = "",
    var repos_url: String = "",
    var site_admin: Boolean = false,
    var starred_url: String = "",
    var subscriptions_url: String = "",
    var twitter_username: Any = Any(),
    var type: String = "",
    var updated_at: String = "",
    var url: String = ""
)

// Followers
class FollowersResponse : ArrayList<FollowersResponseItem>()

data class FollowersResponseItem(
    var avatar_url: String = "",
    var events_url: String = "",
    var followers_url: String = "",
    var following_url: String = "",
    var gists_url: String = "",
    var gravatar_id: String = "",
    var html_url: String = "",
    var id: Int = 0,
    var login: String = "",
    var node_id: String = "",
    var organizations_url: String = "",
    var received_events_url: String = "",
    var repos_url: String = "",
    var site_admin: Boolean = false,
    var starred_url: String = "",
    var subscriptions_url: String = "",
    var type: String = "",
    var url: String = ""
)

// Following
class FollowingResponse : ArrayList<FollowingResponseItem>()

data class FollowingResponseItem(
    var avatar_url: String = "",
    var events_url: String = "",
    var followers_url: String = "",
    var following_url: String = "",
    var gists_url: String = "",
    var gravatar_id: String = "",
    var html_url: String = "",
    var id: Int = 0,
    var login: String = "",
    var node_id: String = "",
    var organizations_url: String = "",
    var received_events_url: String = "",
    var repos_url: String = "",
    var site_admin: Boolean = false,
    var starred_url: String = "",
    var subscriptions_url: String = "",
    var type: String = "",
    var url: String = ""
)

// Search
data class SearchResponse(
    var incomplete_results: Boolean = false,
    var items: ArrayList<Item> = arrayListOf(),
    var total_count: Int = 0
)

data class Item(
    var avatar_url: String = "",
    var events_url: String = "",
    var followers_url: String = "",
    var following_url: String = "",
    var gists_url: String = "",
    var gravatar_id: String = "",
    var html_url: String = "",
    var id: Int = 0,
    var login: String = "",
    var node_id: String = "",
    var organizations_url: String = "",
    var received_events_url: String = "",
    var repos_url: String = "",
    var score: Double = 0.0,
    var site_admin: Boolean = false,
    var starred_url: String = "",
    var subscriptions_url: String = "",
    var type: String = "",
    var url: String = ""
)