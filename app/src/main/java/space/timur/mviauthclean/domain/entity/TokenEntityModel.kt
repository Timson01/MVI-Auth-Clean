package space.timur.mviauthclean.domain.entity

data class TokenEntityModel(
    val accessToken: String,
    val refreshToken: String
)
