package com.calyrsoft.ucbp1.features.profile.domain.model

data class ProfileModel(
    val pathUrl: String,
    val name: String,
    val email: String,
    val cellphone: String,
    val summary: String
)

// Value Objects


@JvmInline
value class PathUrl private constructor(val value: String) {
    companion object {
        fun create(raw: String): Result<PathUrl> {
            val v = raw.trim()
            return if (v.startsWith("http://") || v.startsWith("https://"))
                Result.success(PathUrl(v))
            else Result.failure(IllegalArgumentException("URL inválida"))
        }
    }
}

@JvmInline
value class FullName private constructor(val value: String) {
    companion object {
        fun create(raw: String): Result<FullName> {
            val v = raw.trim()
            if (v.length < 2) return Result.failure(IllegalArgumentException("Nombre muy corto"))
            if (!v.contains(' ')) return Result.failure(IllegalArgumentException("Debe tener nombre y apellido"))
            return Result.success(FullName(v))
        }
    }
}

@JvmInline
value class Email private constructor(val value: String) {
    companion object {
        private val REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        fun create(raw: String): Result<Email> =
            if (REGEX.matches(raw.trim())) Result.success(Email(raw.trim()))
            else Result.failure(IllegalArgumentException("Email inválido"))
    }
}

@JvmInline
value class Cellphone private constructor(val value: String) {
    companion object {
        private val REGEX = Regex("^\\+?[0-9]{7,15}\$")
        fun create(raw: String): Result<Cellphone> =
            if (REGEX.matches(raw.trim())) Result.success(Cellphone(raw.trim()))
            else Result.failure(IllegalArgumentException("Teléfono inválido"))
    }
}

@JvmInline
value class Summary private constructor(val value: String) {
    companion object {
        private const val MAX = 160
        fun create(raw: String): Result<Summary> {
            val v = raw.trim()
            return if (v.length <= MAX) Result.success(Summary(v))
            else Result.failure(IllegalArgumentException("Resumen supera $MAX caracteres"))
        }
    }
}

data class ProfileValidated(
    val pathUrl: PathUrl,
    val name: FullName,
    val email: Email,
    val cellphone: Cellphone,
    val summary: Summary
)
